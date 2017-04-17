package com.ztravel.product.weixin.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.site.lookup.util.StringUtils;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.DateUtils;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.constants.ProductBookConstans;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.ContactorInfo;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.entity.PassengerInfo;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.common.enums.ProductRangeType;
import com.ztravel.common.mail.MailEntity;
import com.ztravel.common.mail.MailEnums;
import com.ztravel.common.payment.CouponAccountInfoBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.util.MailSender;
import com.ztravel.common.util.ProductUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.SmsContentUtil;
import com.ztravel.common.util.SystemNoticeContentUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.member.client.service.TravelerInfoClientService;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.po.TravelerEntity.Credentials;
import com.ztravel.order.client.service.IOrderClientService;
import com.ztravel.order.po.OrderComContactor;
import com.ztravel.payment.service.IAccountService;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Flight;
import com.ztravel.product.back.freetravel.entity.FlightInfo;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.enums.AirRange;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.client.entity.HotelClientEntity;
import com.ztravel.product.client.service.IActivityClientService;
import com.ztravel.product.client.service.IHotelClientService;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.product.dao.ProductDao;
import com.ztravel.product.weixin.service.IWxProductBookService;
import com.ztravel.product.weixin.vo.PriceCretria;
import com.ztravel.reuse.order.entity.DetailToOrderCriteria;
import com.ztravel.reuse.order.service.IOrderProductStockReuseService;
import com.ztravel.reuse.order.service.IPieceProductBookConvertReuseService;
import com.ztravel.reuse.order.service.IPieceProductPriceReuseService;
import com.ztravel.reuse.product.entity.ProductBookVo;
import com.ztravel.reuse.product.entity.ProductFlightInfo;
import com.ztravel.reuse.product.entity.ProductHotelInfo;
import com.ztravel.sso.client.entity.SSOClientEntity;
import com.ztravel.sso.client.service.SSOClientService;

@Service
public class WxProductBookServiceImpl implements IWxProductBookService {

	@Resource
	ProductDao productDao;

	@Resource
	IOrderClientService orderClientService;

	@Resource
	IProductClientService productClientService;

	@Resource
	private IMemberClientService memberClientServiceImpl;

	@Resource
	private TravelerInfoClientService travelInfoClientSeriveImpl;

	@Resource
	private IHotelClientService hotelClientServiceImpl;

	@Resource
	private SSOClientService ssoClientService;

	@Resource
	private ICouponService couponService;

	@Resource
	private IActivityClientService activityClientService;

	@Resource
	private ISystemNoticeClientService systemNoticeClientServiceImpl;
	
	@Resource
	private IOrderProductStockReuseService orderProductStockReuseService;

	@Resource
	private IPieceProductBookConvertReuseService pieceProductBookConvertReuseService;
	
	@Resource
	private IAccountService accountService;
	
	private static Logger LOGGER = RequestIdentityLogger.getLogger(WxProductBookServiceImpl.class);

    private final String BED_TIP_COUNTRY = "日本";
    
    @Resource
	private IPieceProductPriceReuseService pieceProductPriceReuseService;

	static  String SERVICE_EMAIL1 = null;
    static  String SERVICE_EMAIL2 = null;
    static  String SERVICE_EMAIL3 = null;

	static{
    	Properties mailProp = TopsConfReader.getConfProperties("properties/mail.properties", ConfScope.R) ;
    	SERVICE_EMAIL1 = mailProp.getProperty("mail.service1","");
    	SERVICE_EMAIL2 = mailProp.getProperty("mail.service2","");
		SERVICE_EMAIL3 = mailProp.getProperty("mail.service3","");
    }

	/**
	 * 提交订单
	 * */
	@Override
	public AjaxResponse applyOrder(ProductBookItem productBookItem) throws Exception {
		AjaxResponse applyOrderResult = AjaxResponse.instance(ProductBookConstans.APPLY_ORDER_FAILURE, "");
		DetailToOrderCriteria criteria = pieceProductBookConvertReuseService.getDetailToOrderCriteria(productBookItem);
		// 验证登录用户是否挂起
		applyOrderResult = getCheckActiveResponse(applyOrderResult);
		if (applyOrderResult.getRes_code().equals(ProductBookConstans.NOT_ACTIVE_TO_APPLY_ORDER_FAILURE)) {
			return applyOrderResult;
		}
		// 验证用户是否登录
		applyOrderResult = getLoginResponse(applyOrderResult, productBookItem);
		if (applyOrderResult.getRes_code().equals(ProductBookConstans.USER_NOT_LOGIN)
				|| applyOrderResult.getRes_code().equals(ResponseConstants.MEMB_MOBILE_NOT_FOUND_ERROR_CODE)) {
			return applyOrderResult;
		}
		// 验证产品库存
		if (!checkStockByProduct(productBookItem)) {
			applyOrderResult.setRes_code(ProductBookConstans.PRODUCT_STOCK_NOT_ENOUGH);
			return applyOrderResult;
		}
		// 校验产品价格
		if (checkPriceByProduct(productBookItem) == 1) {
			Double price = getTotalPrice(productBookItem);
			applyOrderResult.setRes_code(ProductBookConstans.PRODUCT_PRICE_CHANGED);
			applyOrderResult.setRes_msg(String.valueOf((price.longValue() - productBookItem.getCouponSub() - productBookItem.getIntegralSub()) / 100));
			return applyOrderResult;
		}
		// 冻结代金券及代金券金额校验
		applyOrderResult = getCouponResponse(applyOrderResult, productBookItem);
		if (applyOrderResult.getRes_code().equals(ActivityConstants.COUPONPRICECHANGED_TO_APPLY_ORDER_FAILED)
				|| applyOrderResult.getRes_code().equals(ActivityConstants.COUPONSHARED_TO_APPLY_ORDER_FAILED)
				|| applyOrderResult.getRes_code().equals(ActivityConstants.COUPONUSED_TO_APPLY_ORDER_FAILED)) {// 代金券价格不一致,代金券已分享,代金券已使用
			return applyOrderResult;
		}
		// 构建订单产品快照
		ProductBookVo productSnapshot = product2BookVo(criteria);
		productBookItem.setProductShapshotId(ProductUtil.genProductSnapshotId());
		productBookItem.setShapShot(JSONObject.toJSONString(productSnapshot));
		// 保证orderProductStock表中有一条库存锁记录;lock格式为:55a8b87ae4b03ed81890af9c:2015-07-30
		String lock = productBookItem.getProductId() + ":" + productBookItem.getBookDate();
		try {
			orderProductStockReuseService.insertLock(lock);
		} catch (Exception e) {
			LOGGER.error("{}库存锁已经存在", lock);
		}
		;
		// 开始建单
		Map<String, Object> applyResult = orderClientService.applyOrder(productBookItem);
		applyOrderResult = (AjaxResponse) applyResult.get("result");
		// 建单成功,发送邮件,短信,系统消息
		if (applyOrderResult.getRes_code().equals(OrderConstans.ORDER_CREATE_SUCCESS)) {
			String orderNo = (String) applyResult.get("orderNo");
			opsAfterApplied(applyOrderResult, productBookItem, orderNo);
			orderClientService.sendWeiXinSubmitMsg(applyOrderResult.getRes_msg());
		}
		// 返回建单结果
		return applyOrderResult;
	}

	/**
	 * 代金券冻结操作及代金券金额校验
	 * */
	public AjaxResponse getCouponResponse(AjaxResponse applyOrderResult, ProductBookItem productBookItem) {
		if (StringUtils.isNotEmpty(productBookItem.getDiscountcouponId())) {
			CouponItemBean couponItem = couponService.getCouponItem(productBookItem.getDiscountcouponId());
			if (couponItem.getAmount() != productBookItem.getCouponSub()) {
				LOGGER.error("代金券[{}]优惠价格不正确", couponItem.getCouponItemId());
				applyOrderResult.setRes_code(ActivityConstants.COUPONPRICECHANGED_TO_APPLY_ORDER_FAILED);
				applyOrderResult.setRes_msg(ActivityConstants.COUPONPRICECHANGED_TO_APPLY_ORDER_FAILED_MSG);
				return applyOrderResult;
			}
			if (couponItem.getStatus().equals(CouponItemStatus.SHARED.name())) {// 已分享
				LOGGER.error("代金券[{}]已被分享", couponItem.getCouponItemId());
				applyOrderResult.setRes_code(ActivityConstants.COUPONSHARED_TO_APPLY_ORDER_FAILED);
				applyOrderResult.setRes_msg(ActivityConstants.COUPONSHARED_TO_APPLY_ORDER_FAILED_MSG);
				return applyOrderResult;
			}
		}
		return applyOrderResult;
	}

	/**
	 * 用户是否被挂起
	 * */
	public AjaxResponse getCheckActiveResponse(AjaxResponse applyOrderResult) {
		Boolean isActive = checkIsActive();
		if (!isActive) {// 用户被挂起
			LOGGER.info("用户[{}]处于挂起状态,不能建单", SSOUtil.getMemberSessionBean().getMobile());
			applyOrderResult.setRes_code(ProductBookConstans.NOT_ACTIVE_TO_APPLY_ORDER_FAILURE);
			applyOrderResult.setRes_msg("咦?账号异常,请与客服联系~");
		}
		return applyOrderResult;
	}

	/**
	 * 用户是否登录
	 * */
	public AjaxResponse getLoginResponse(AjaxResponse applyOrderResult, ProductBookItem productBookItem) throws Exception {
		if (!SSOUtil.isLogin()) {// 未登录
			SSOClientEntity entity = buildSSOClientEntity(productBookItem.getContactorInfo());
			Integer checkMemBer = checkMemberByContatorPhone(entity);
			if (checkMemBer == 0 || checkMemBer == 2) {// 用户已存在，但未登陆
				applyOrderResult.setRes_code(ProductBookConstans.USER_NOT_LOGIN);
				applyOrderResult.setRes_msg(checkMemBer == 0 ? productBookItem.getContactorInfo().getPhone() : productBookItem.getContactorInfo().getEmail());
			} else {
				applyOrderResult.setRes_code(ResponseConstants.MEMB_MOBILE_NOT_FOUND_ERROR_CODE);
				applyOrderResult.setRes_msg(productBookItem.getContactorInfo().getPhone());
			}
		}
		return applyOrderResult;
	}

	/**
	 * 检验库存
	 *
	 * @param productBookItem
	 * @return
	 * @throws Exception
	 */
	public boolean checkStockByProduct(ProductBookItem productBookItem) throws Exception {
		Boolean result = true;
		if(Nature.COMBINATION.name().equals(productBookItem.getNature()) || Nature.PACKAGE.name().equals(productBookItem.getNature())){
			Integer productAvailableStock = productClientService.getStock(productBookItem.getProductId(), productBookItem.getBookDate());
			Integer childNum = productBookItem.getChildrenNum() == null ? 0 : productBookItem.getChildrenNum();
			Integer adultNum = productBookItem.getAdaultNum() == null ? 0 : productBookItem.getAdaultNum();
			LOGGER.debug("=========>产品{}可用库存为{},建单需占用库存{}", productBookItem.getProductTitle(), adultNum + childNum);
			result = productAvailableStock >=  (childNum + adultNum);
		}
		return result;
	}

	/**
	 * 检验价格
	 *
	 * @param productBookItem
	 * @return 0 --成功，1--产品价格变动
	 * @throws Exception
	 */
	public Integer checkPriceByProduct(ProductBookItem productBookItem) throws Exception {
		Double totalPrice = getTotalPrice(productBookItem);

		LOGGER.debug("===========>订单最新价格为{},下单时的订单价格为{}", totalPrice, productBookItem.getTotalPrice());
		Long oldTotalPrice = 0l;
		if (StringUtils.isNotEmpty(productBookItem.getDiscountcouponId())) {
			oldTotalPrice = productBookItem.getPayAmount() + productBookItem.getCouponSub();// 订单总价为　应付金额＋代金券抵消金额
		} else {
			oldTotalPrice = productBookItem.getPayAmount();
		}
		return totalPrice.longValue() == oldTotalPrice ? 0 : 1;
	}

	private Double getTotalPrice(ProductBookItem productBookItem) throws Exception{
		Double totalPrice = 0D;
		if(Nature.PACKAGE.name().equals(productBookItem.getNature()) || Nature.COMBINATION.name().equals(productBookItem.getNature())){
			if(StringUtils.isNotEmpty(productBookItem.getPackageId())){
				totalPrice = getProductPackagePrice(productBookItem.getProductId(), productBookItem.getBookDate(), productBookItem.getPackageId());
			}else{
				totalPrice = getTotalPrice(productBookItem.getProductId(), productBookItem.getBookDate(), productBookItem.getAdaultNum(), productBookItem.getChildrenNum());
			}
		}else{
    		return pieceProductPriceReuseService.getPieceProductTotalPrice(productBookItem.getProductId(), productBookItem.getPriceId(), productBookItem.getAdaultNum(), productBookItem.getChildrenNum(), productBookItem.getNature());
    	}
		return totalPrice;
	}

	/**
	 * 查询产品价格信息
	 *
	 * @param productId
	 *            --产品id
	 * @param bookDate
	 *            --预订日期(格式yyyy-MM-dd)
	 * @param packageId
	 *            --套餐id
	 * @return--{套餐价格
	 * @throws Exception
	 */
	public Double getProductPackagePrice(String productId, String bookDate, String packageId) throws Exception {
		Double packagePrice = 0D;
		Product product = productDao.getProductById(productId);
		Sale sale = product.getCalendar().get(bookDate).getSale();
		if (null != sale) {
			for (SalesPackage salesPackage : sale.getSalesPackages()) {
				if (packageId != null && packageId.equals(salesPackage.getPkgId())) {
					packagePrice = salesPackage.getPrice() * 100;
				}
			}
		}
		return packagePrice;
	}

	public Double getTotalPrice(String productId, String bookDate, Integer adultNum, Integer childNum) throws Exception {
		Double totalPrice = 0D;
		Product product = productDao.getProductById(productId);
		Sale sale = product.getCalendar().get(bookDate).getSale();
		if (sale != null) {

			if(adultNum != null){
				int singleRooms = adultNum %2;
				if(sale.getSingleRoomPrice() != null){
					totalPrice += singleRooms * sale.getSingleRoomPrice();
				}
				if(sale.getAdultPrice() != null ){
					totalPrice += adultNum * sale.getAdultPrice();
				}
			}
			if(childNum != null){
				if(sale.getChildPrice() != null){
					totalPrice += childNum * sale.getChildPrice();
				}
			}

			totalPrice = totalPrice * 100;

		}
		return totalPrice;
	}

	/**
	 * 建单成功后邮件发送;消息发送等操作
	 *
	 * @throws Exception
	 * */
	public void opsAfterApplied(AjaxResponse applyOrderResult, ProductBookItem productBookItem, String orderNo) throws Exception {
		saveCommonPassenger(productBookItem);// 保存常旅客
		MobileCaptchaEntity mobileCapEntity = buildSmsEntity(productBookItem.getContactorInfo().getPhone(), SmsContentUtil.getCreateOrderSmsContent(orderNo));
		SmsAdapter.sendMessage(mobileCapEntity);// 发送通知短信,之前被注释
		MailEntity mailEntity = buildMailEntity(productBookItem, orderNo, applyOrderResult.getRes_msg());
		MailSender.send(mailEntity);
		// 用于接受客户建单信息的服务邮件，以便服务用户
		MailEntity serviceMailEntity1 = buildMailEntityForMaintain(productBookItem, orderNo, applyOrderResult.getRes_msg(), SERVICE_EMAIL1);
		MailEntity serviceMailEntity2 = buildMailEntityForMaintain(productBookItem, orderNo, applyOrderResult.getRes_msg(), SERVICE_EMAIL2);
		MailEntity serviceMailEntity3 = buildMailEntityForMaintain(productBookItem, orderNo, applyOrderResult.getRes_msg(), SERVICE_EMAIL3);
		MailSender.send(serviceMailEntity1);
		MailSender.send(serviceMailEntity2);
		MailSender.send(serviceMailEntity3);
		// 发送系统提醒
		MemberSessionBean member = SSOUtil.getMemberSessionBean();
		systemNoticeClientServiceImpl.add(member.getMemberId(), NoticeType.ORDERTYPE,
				SystemNoticeContentUtil.getOdContent(1, member.getNickName(), applyOrderResult.getRes_msg(),productBookItem.getNature()));
	}

	/**
	 * 检查用户手机是否注册，是--skip，否--用联系人手机号注册，并发送账号密码到改手机
	 *
	 * @param phonNum
	 * @Return 0 --手机已经注册,
	 * @Return 2--邮箱已经注册,
	 */
	public Integer checkMemberByContatorPhone(SSOClientEntity entity) throws Exception {
		if (memberClientServiceImpl.isMobileAlreadyExists(entity.getMobile())) {
			return 0;
		} else if (memberClientServiceImpl.isEmailAlreadyExists(entity.getEmail())) {
			return 2;
		} else {
			return 1;
		}
	}

	public PriceCretria getOrderPriceInfo(PriceCretria criteria) {
		try {
			Double packagePrice = 0.0;
			Double totalPriceTmp = 0.0;
			//套餐
			if(StringUtils.isNotEmpty(criteria.getPackageId())){
				packagePrice  = getProductPackagePrice(criteria.getProductId(), criteria.getBookDate(), criteria.getPackageId());
				criteria.setPackagePrice(MoneyUtil.cent2Yuan(packagePrice));
				criteria.setTotalPackagePrice(MoneyUtil.cent2Yuan(packagePrice));
				totalPriceTmp = packagePrice;
				criteria.setTotalPrice(MoneyUtil.cent2Yuan(packagePrice));
			}else{
				totalPriceTmp = countPrice(criteria,totalPriceTmp);
			}
			criteria.setContainCoupon(false);
			if (criteria.getCouponAmount() != null) {
				totalPriceTmp =(totalPriceTmp - criteria.getCouponAmount()) < 0 ? 0 : (totalPriceTmp - criteria.getCouponAmount());
				criteria.setCouponAmount(criteria.getCouponAmount() / 100);
				criteria.setContainCoupon(true);
				criteria.setCouponName(criteria.getCouponName());
			}
			criteria.setTotalPrice(MoneyUtil.cent2Yuan(totalPriceTmp));

		} catch (Exception e) {
			LOGGER.error("计算产品价格错误", e);
		}
		return criteria;
	}
	
	public Double countPrice(PriceCretria criteria,Double totalPriceTmp)throws Exception{
		if((Nature.PACKAGE.name().equals(criteria.getProductNature()) || Nature.COMBINATION.name().equals(criteria.getProductNature()))){
			Product product = productDao.getProductById(criteria.getProductId());
			Day day = product.getCalendar().get(criteria.getBookDate());
			Sale sale = new Sale();
			if(day != null){
				sale = day.getSale();
				if(sale != null){
					int singleRooms = criteria.getAdultNum()%2;
					Double totalAdultPrice = Double.valueOf(sale.getAdultPrice()) * criteria.getAdultNum();
					Double totalChildPrice = 0D;
					if(sale.getChildPrice() !=null){
						totalChildPrice = Double.valueOf(sale.getChildPrice()) * criteria.getChildNum();
					}
					
					Double totalSingleRoomPrice = Double.valueOf(sale.getSingleRoomPrice()) * singleRooms;
					criteria.setTotalAdultPrice(MoneyUtil.cent2Yuan(totalAdultPrice*100));
					criteria.setTotalChildPrice(MoneyUtil.cent2Yuan(totalChildPrice*100));
					criteria.setSinglePrice(MoneyUtil.cent2Yuan(sale.getSingleRoomPrice()*100));
					criteria.setSingleNum(singleRooms);
					criteria.setAdultPrice(MoneyUtil.cent2Yuan(sale.getAdultPrice()*100));
					if(sale.getChildPrice() !=null){
						criteria.setChildPrice(MoneyUtil.cent2Yuan(sale.getChildPrice()*100));
					}
					criteria.setTotalSinglePrice(MoneyUtil.cent2Yuan(totalSingleRoomPrice*100));
					totalPriceTmp = (totalAdultPrice + totalChildPrice + totalSingleRoomPrice)*100;
				}
			}
		}else{
			Double priceArray[] = null;
			if(Nature.VISA.name().equals(criteria.getProductNature())){
				priceArray = pieceProductPriceReuseService.getVisaProductPriceInfo(criteria.getProductId(), criteria.getCostPriceId());
			}else{
				priceArray = pieceProductPriceReuseService.getUnVisaProductPriceInfo(criteria.getProductId(), criteria.getCostPriceId());
			}
			Double totalAdultPrice = priceArray[0] * criteria.getAdultNum();
			Double totalChildPrice = 0D;
			criteria.setTotalAdultPrice(MoneyUtil.cent2Yuan(priceArray[0]*criteria.getAdultNum()*100));
			criteria.setAdultPrice(MoneyUtil.cent2Yuan(priceArray[0]*100));
			if(null != priceArray[1]){
				totalChildPrice = priceArray[1] * criteria.getChildNum();
				criteria.setChildPrice(MoneyUtil.cent2Yuan(priceArray[1]*100));
				criteria.setTotalChildPrice(MoneyUtil.cent2Yuan(priceArray[1]*criteria.getChildNum()*100));
			}
			totalPriceTmp = (totalAdultPrice + totalChildPrice )*100;
		}
		return totalPriceTmp;
	}


	/**
	 * 保存常旅客，不影响建单流程，保存异常时，自动捕获异常，打印异常日志
	 * */
	public void saveCommonPassenger(ProductBookItem productBookItem) {
		if (!CollectionUtils.isEmpty(productBookItem.getPassengerList())) {
			try {
				for (PassengerInfo passenger : productBookItem.getPassengerList()) {
					if (passenger.isSavePassenger()) {// 保存常旅客
						TravelerEntity traveller = new TravelerEntity();
						List<Credentials> credentialList = new ArrayList<Credentials>();
						Credentials credential = new Credentials();
						credential.setType(passenger.getCredentialType());
						credential.setNumber(passenger.getCredentialNum());
						if (null != passenger.getCredentialsDeadLine()) {
							credential.setDeadLineDay(passenger.getCredentialsDeadLine());
						}
						credentialList.add(credential);
						traveller.setCredentials(credentialList);
						traveller.setTravelType(passenger.getType());
						if (StringUtils.isNotEmpty(passenger.getPassengerName())) {
							traveller.setTravelerNameCn(passenger.getPassengerName().trim());
						}
						if (StringUtils.isNotEmpty(passenger.getPassengerEnName())) {
							traveller.setTravelerNameEn(passenger.getPassengerEnName().trim());
						}
						if (!StringUtils.isEmpty(passenger.getBirthday())) {
							traveller.setBirthday(passenger.getBirthday());
						}
						if (StringUtils.isNotEmpty(passenger.getFirstName())) {
							traveller.setFirstNameCn(passenger.getFirstName().trim());
						}
						if (StringUtils.isNotEmpty(passenger.getLastName())) {
							traveller.setLastNameCn(passenger.getLastName().trim());
						}
						if (StringUtils.isNotEmpty(passenger.getFirstNameEn())) {
							traveller.setFirstEnName(passenger.getFirstNameEn());
						}
						if (StringUtils.isNotEmpty(passenger.getLastNameEn())) {
							traveller.setLastEnName(passenger.getLastNameEn());
						}

						traveller.setMemberId(SSOUtil.getMemberSessionBean().getMemberId());
						traveller.setIsActive(true);
						traveller.setGender(passenger.getGender());
						traveller.setNationality(passenger.getCountry());
						travelInfoClientSeriveImpl.saveTravelerForBookOrder(productBookItem.getIsDomestic(), traveller);
						// travelInfoClientSeriveImpl.save(traveller);
					}
				}
			} catch (Exception e) {
				LOGGER.debug("保存常旅客出现异常:", e);
			}
		}
	}

	/**
	 * 获取航班的飞行时间 时间格式必须是HH:mm
	 *
	 * @param start
	 *            :航班起飞时间
	 * @param end
	 *            : 航班到达时间
	 * @param addDays
	 *            : 飞行偏移天数
	 * */
	public String getFlyTime(String start, String end, Integer addDays) {
		String rex = "[0-9]{2}[:][0-9]{2}";
		Integer addDay = addDays == null ? 0 : addDays;
		Integer endHours = 0;
		Integer endMin = 0;
		Integer startMin = 0;
		Integer startHour = 0;
		Integer totalMin = 0;// 总的飞行分钟数
		if (Pattern.compile(rex).matcher(start.trim()).matches() && Pattern.compile(rex).matcher(end.trim()).matches()) {
			if (StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
				startHour = Integer.parseInt(start.substring(0, 2));
				startMin = Integer.parseInt(start.substring(3, 5));
				endMin = Integer.parseInt(end.substring(3, 5));
				if (addDay > 0) {
					endHours = addDay * 24 + Integer.parseInt(end.substring(0, 2));
				} else {
					endHours = Integer.parseInt(end.substring(0, 2));
				}
				totalMin = (endHours * 60 + endMin) - (startHour * 60 + startMin);
			} else {
				throw new NullPointerException("开始时间和结束时间不能为空");
			}
		} else {
			throw new IllegalArgumentException("时间格式不正确");
		}
		return (totalMin / 60) + "h" + (totalMin % 60) + "m";
	}

	@Override
	public List<TravelerEntity> getTravelList(String travellerType) {
		List<TravelerEntity> travellerList = new ArrayList<TravelerEntity>();
		if (checkIsActive()) {// 未挂起用户
			String memberId = SSOUtil.getMemberSessionBean().getMemberId();
			if (StringUtils.isNotEmpty(memberId)) {
				try {
					travellerList = travelInfoClientSeriveImpl.getTravelerEntityListByMemberIdAndTravelType(memberId, travellerType);
				} catch (Exception e) {
					travellerList = null;
					LOGGER.debug("查询常旅客列表异常", e);
				}
			}
		}
		return travellerList;
	}

	public SSOClientEntity buildSSOClientEntity(ContactorInfo contactor) {
		SSOClientEntity entity = new SSOClientEntity();
		entity.setRealName(contactor.getName());
		entity.setMobile(contactor.getPhone());
		entity.setEmail(contactor.getEmail());
		entity.setProvince(contactor.getProvince());
		entity.setCity(contactor.getCity());
		entity.setArea(contactor.getCounty());
		entity.setDetailAddress(contactor.getAddressDetail());
		return entity;
	}

	public MobileCaptchaEntity buildSmsEntity(String mobilPhone, String smsContent) {
		MobileCaptchaEntity mobileCapEntity = new MobileCaptchaEntity();
		mobileCapEntity.setMobileNum(mobilPhone);
		mobileCapEntity.setContent(smsContent);
		return mobileCapEntity;
	}

	public MailEntity buildMailEntity(ProductBookItem productBookItem, String orderNo, String orderId) {
		Map<String, String> params = new HashMap<String, String>();
		String email = productBookItem.getContactorInfo().getEmail();
		String url = WebEnv.get("server.path.media", "") + "/imageservice?mediaImageId=" + productBookItem.getFirstImageId();// need
																																// to
																																// change
		String payUrl = WebEnv.get("server.path.memberServer", "") + "/orderPay/selectPayType/" + orderId;
		DateTime bookDate = DateTimeUtil.parseDate10(productBookItem.getBookDate());
		String detailUrl = WebEnv.get("server.path.memberServer") + "/order/front/detail/" + orderId;
		params.put("DETAILURL", detailUrl);
		params.put("DATE", DateTime.now().toString("yyyy-MM-dd"));
		params.put("URL", url);
		params.put("PAYURL", payUrl);
		params.put("ORDERNO", orderNo);
		params.put("PRODUCTNAME", productBookItem.getProductTitle());
		params.put("CREATETIME", DateTime.now().toString("yyyy-MM-dd"));
		params.put("OFFTIME", bookDate.toString("yyyy-MM-dd"));
		params.put("TOTALPRICE", String.valueOf(productBookItem.getTotalPrice() / 100));
		params.put("PAYMENT", String.valueOf(productBookItem.getPayAmount() / 100));
		Long couponSub = productBookItem.getCouponSub() == null ? 0l : productBookItem.getCouponSub();
		params.put("DISCOUNT", String.valueOf(couponSub / 100));
		params.put("HOMEPAGE", WebEnv.get("server.path.memberServer", ""));
		params.put("MEMBERINFO", WebEnv.get("server.path.memberServer", "") + "/member/info");
		MailEntity entity = new MailEntity(email, null, MailEnums.ContentType.UNPAYHTML, params, MailEnums.BizType.UNPAY);
		return entity;
	}

	/**
	 * 临时添加需求，对国庆期间建单信息进行提示，以便服务
	 * */

	public MailEntity buildMailEntityForMaintain(ProductBookItem productBookItem, String orderNo, String orderId, String email) {
		Map<String, String> params = new HashMap<String, String>();
		// String email = productBookItem.getContactorInfo().getEmail();
		String url = WebEnv.get("server.path.media", "") + "/imageservice?mediaImageId=" + productBookItem.getFirstImageId();// need
																																// to
																																// change
		String payUrl = WebEnv.get("server.path.memberServer", "") + "/orderPay/selectPayType/" + orderId;
		DateTime bookDate = DateTimeUtil.parseDate10(productBookItem.getBookDate());
		String detailUrl = WebEnv.get("server.path.memberServer") + "/order/front/detail/" + orderId;
		params.put("DETAILURL", detailUrl);
		params.put("DATE", DateTime.now().toString("yyyy-MM-dd"));
		params.put("URL", url);
		params.put("PAYURL", payUrl);
		params.put("ORDERNO", orderNo);
		params.put("PRODUCTNAME", productBookItem.getProductTitle());
		params.put("CREATETIME", DateTime.now().toString("yyyy-MM-dd"));
		params.put("OFFTIME", bookDate.toString("yyyy-MM-dd"));
		params.put("TOTALPRICE", String.valueOf(productBookItem.getTotalPrice() / 100));
		params.put("PAYMENT", String.valueOf(productBookItem.getPayAmount() / 100));
		Long couponSub = productBookItem.getCouponSub() == null ? 0l : productBookItem.getCouponSub();
		params.put("DISCOUNT", String.valueOf(couponSub / 100));
		params.put("HOMEPAGE", WebEnv.get("server.path.memberServer", ""));
		params.put("MEMBERINFO", WebEnv.get("server.path.memberServer", "") + "/member/info");
		MailEntity entity = new MailEntity(email, null, MailEnums.ContentType.UNPAYHTML, params, MailEnums.BizType.UNPAY);
		return entity;
	}

	@Override
	public Boolean getLoginIngo() {
		Boolean isLogin = false;
		try {
			isLogin = memberClientServiceImpl.getMemberFromRedisBySID().getIsLogin();
		} catch (Exception e) {
		}
		return isLogin;
	}

	@Override
	public List<CouponClientEntity> getAllCoupons(String productId, String bookDate, String packageId, Integer adultNum, Integer childNum,String productNature,String priceId) throws Exception{
		String memberId = null;
		List<CouponClientEntity> couponClientEntityList = new ArrayList<>();
		List<CouponItemBean> items = null;
		try {
			memberId = memberClientServiceImpl.getMemberFromRedisBySID().getMemberId();
			Double totalPrice = 0.0;
			if (null != memberId) {
				String productNo = null;
            	if(productNature.equals(Nature.COMBINATION.name()) || productNature.equals(Nature.PACKAGE.name())){
            		productNo = productClientService.getProductById(productId).getProductCode();
            	}else{
            		productNo = pieceProductPriceReuseService.getPidById(productId, productNature);
            	}
				if(StringUtils.isNotEmpty(packageId)){
					totalPrice = getProductPackagePrice(productId, bookDate, packageId);
				}else{
					if(productNature.equals(Nature.COMBINATION.name()) || productNature.equals(Nature.PACKAGE.name())){
						totalPrice = getTotalPrice(productId, bookDate, adultNum, childNum);
					}else{
						totalPrice = pieceProductPriceReuseService.getPieceProductTotalPrice(productId, priceId, adultNum, childNum, productNature);
					}
				}
				LOGGER.info("订单金额{},产品编号{}", totalPrice.longValue() / 100, productNo);
				CouponAccountInfoBean couponAccountInfo = accountService.getCouponAccountInfo(memberId);
				if(couponAccountInfo != null){
					boolean isActive = couponAccountInfo.isActive();
					if(!isActive){
						return couponClientEntityList;
					}else{
						items = couponAccountInfo.getAvailableCouponItems();
					}
				}
//				items = couponService.getAvailableCouponItems(memberId);
				if (!CollectionUtils.isEmpty(items)) {
					for (CouponItemBean itemBean : items) {
						CouponClientEntity couponClientEntity = activityClientService.getCouponByIdWithProductName(itemBean.getActivityId(),
								itemBean.getCouponCode());
						if (!StringUtils.isEmpty(couponClientEntity.getCouponId())) {
							couponClientEntity.setAccountCouponId(itemBean.getCouponItemId());
							couponClientEntity.setAmount(couponClientEntity.getAmount() / 100);
							if (couponClientEntity.getOrderLeast() == 0) {
								couponClientEntity.setDescription("全场通用");
							} else {
								couponClientEntity.setDescription("满" + String.valueOf(couponClientEntity.getOrderLeast() / 100) + "元可用");
							}
							if (couponClientEntity.getProductRangeType().equals(ProductRangeType.ALLPRODUCTS.name())) {
								if (totalPrice.longValue() >= couponClientEntity.getOrderLeast()) {
									couponClientEntityList.add(couponClientEntity);
								} else {
									continue;
								}
							} else {
								if (totalPrice.longValue() >= couponClientEntity.getOrderLeast()
										&& Arrays.asList(couponClientEntity.getProductRange().split(",")).indexOf(productNo) >= 0) {
									couponClientEntityList.add(couponClientEntity);
								}
							}
						}
					}
					LOGGER.info("用户可用的代金券信息{}", TZBeanUtils.describe(couponClientEntityList));
					return couponClientEntityList;
				}
			}
		} catch (Exception e) {
			LOGGER.info("建单时获取用户代金券异常", e);
		}
		return couponClientEntityList;
	}

	@Override
	public List<CouponClientEntity> couponsSort(List<CouponClientEntity> couponList) {
		if (!CollectionUtils.isEmpty(couponList)) {
			for (int i = 0; i < couponList.size(); i++) {
				for (int j = 0; j < couponList.size() - i - 1; j++) {
					if (couponList.get(j).getAmount() < couponList.get(j + 1).getAmount()) {
						CouponClientEntity coupon = couponList.get(j);
						couponList.set(j, couponList.get(j + 1));
						couponList.set(j + 1, coupon);
					}
					if (couponList.get(j).getAmount() == couponList.get(j + 1).getAmount()) {// 面额相同时比较有效期
						long timeNow = new DateTime().getMillis();
						long preValidateTo = DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, couponList.get(j).getValidTimeTo()).getMillis();
						long nexValidateTo = DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, couponList.get(j + 1).getValidTimeTo()).getMillis();
						if ((preValidateTo - timeNow) > (nexValidateTo - timeNow)) {
							CouponClientEntity coupon = couponList.get(j);
							couponList.set(j, couponList.get(j + 1));
							couponList.set(j + 1, coupon);
						}
					}
				}
			}
		}
		return couponList;
	}

	/**
	 * 用户未登录时默认为未挂起
	 * */
	public Boolean checkIsActive() {
		Boolean isActive = true;
		if (null != SSOUtil.getMemberSessionBean() && StringUtils.isNotEmpty(SSOUtil.getMemberSessionBean().getMemberId())) {// 用户被挂起
			isActive = SSOUtil.getMemberSessionBean().getIsActive();
		}
		return isActive;
	}

	@Override
	public List<OrderComContactor> getContactors() throws Exception {
		if (getLoginIngo()) {
			// return
			// orderClientService.getContactorInfoByMemId("MEMB1506191021159807");//test

            return orderClientService.getContactorsByMemId(SSOUtil.getMemberSessionBean().getMemberId());
		} else {
			return null;
		}
	}
	
	public ProductBookVo product2BookVo(DetailToOrderCriteria criteria) throws Exception{
		ProductBookVo bookVo = null;
		if(StringUtils.isNotEmpty(criteria.getProductNature())){
			if(Nature.PACKAGE.name().equals(criteria.getProductNature()) || Nature.COMBINATION.name().equals(criteria.getProductNature())){
				bookVo = freeTravelProduct2BookVo(criteria);
			}else{
				bookVo = pieceProductBookConvertReuseService.pieceProduct2BookVo(criteria);
			}
		}else{
			bookVo = freeTravelProduct2BookVo(criteria);
		}
		return bookVo;
	}

	/**
	 * 获取预订产品信息
	 * */
	public ProductBookVo freeTravelProduct2BookVo(DetailToOrderCriteria criteria) throws Exception {
		ProductBookVo bookVo = new ProductBookVo();
		Product product = productDao.getProductById(criteria.getProductId());
		Assert.isTrue(product != null, "产品{" + criteria.getProductId() + "}不存在");
		DecimalFormat moneyFormat = new DecimalFormat("#####0.00");
		// 设置产品信息
		bookVo.setProductId(product.getId().toString());
		bookVo.setProductTitle(product.getpName());
		bookVo.setProductNo(product.getPid());
		bookVo.setNature(product.getNature().name());
		bookVo.setIsDomestic(product.getDestinationType().getName());
		bookVo.setProductType(product.getNature().getName());// 产品类型
		bookVo.setDepartDay(criteria.getBookDate());

		Integer tripDays = product.getTripDays() == null ? 0 : product.getTripDays();
		bookVo.setReturnDay(DateUtils.addDate(criteria.getBookDate(), 3, tripDays - 1));
		bookVo.setTripDays(tripDays);

		Map<String, Day> calendar = product.getCalendar();
		Assert.isTrue(!CollectionUtils.isEmpty(calendar), "产品{" + criteria.getProductId() + "}价格日历为空");
		Day dayInfo = calendar.get(criteria.getBookDate());
		Assert.isTrue(dayInfo != null, "产品{" + criteria.getProductId() + "}未设置{" + criteria.getBookDate() + "}价格信息");
		// 价格信息
		Double totalPrice = 0d;
		Sale sale = dayInfo.getSale();
		if (null != sale) {
			boolean existsPackageFlag = false;

			// 遍历当日套餐，按套餐id获取套餐名称
			if (StringUtils.isNotEmpty(criteria.getPackageId())) {
				for (SalesPackage salesPackage : sale.getSalesPackages()) {
					if (criteria.getPackageId().equals(salesPackage.getPkgId())) {
						bookVo.setPackageId(salesPackage.getPkgId());
						bookVo.setPackageName(salesPackage.getName());
						bookVo.setAdultNum(salesPackage.getAdultNum());
						bookVo.setChildNum(salesPackage.getChildrenNum());
						bookVo.setPackagePrice(moneyFormat.format(salesPackage.getPrice()));
						bookVo.setPackagePerPrice(moneyFormat.format(salesPackage.getPerPrice()));
						bookVo.setSumNum(salesPackage.getSumNum());
						bookVo.setTotalPackagePrice(moneyFormat.format(1 * salesPackage.getPrice())); // 当前套餐只允许订购一份，故套餐总价与套餐价格一样
						totalPrice += (1 * salesPackage.getPrice());
						existsPackageFlag = true;
						break;
					}
				}
				Assert.isTrue(existsPackageFlag, "产品{" + criteria.getProductId() + "}在日期为{" + criteria.getBookDate() + "}时查询不到{" + criteria.getPackageId()
						+ "}套餐信息");

			} else{
				Double totalAdultPrice = 0d;
				Double totalChildPrice = 0d;
				Double totalSinglePrice = 0d;
				bookVo.setAdultNum(criteria.getAdultNum() == null ? 0 : criteria.getAdultNum());
				bookVo.setChildNum(criteria.getChildNum() == null ? 0 : criteria.getChildNum());
				bookVo.setSingleNum(bookVo.getAdultNum() % 2);
				if(null != dayInfo.getSale()){
					Double adultPrice = dayInfo.getSale().getAdultPrice() == null ? 0d : dayInfo.getSale().getAdultPrice();
					Double childPrice = dayInfo.getSale().getChildPrice() == null ? 0d : dayInfo.getSale().getChildPrice();
					Double singPrice = dayInfo.getSale().getSingleRoomPrice() == null ? 0d : dayInfo.getSale().getSingleRoomPrice();
					totalAdultPrice = adultPrice* bookVo.getAdultNum();
					totalChildPrice = childPrice * bookVo.getChildNum();
					totalSinglePrice = singPrice * (bookVo.getAdultNum() % 2);
					bookVo.setAdultPrice(moneyFormat.format(adultPrice));
					bookVo.setChildrenPrice(moneyFormat.format(childPrice));
					bookVo.setSingleRoomDiff(moneyFormat.format(singPrice));
				}
				bookVo.setTotalAdultPrice(moneyFormat.format(totalAdultPrice));
				bookVo.setTotalChildPrice(moneyFormat.format(totalChildPrice));
				bookVo.setTotalSingleDiff(moneyFormat.format(totalSinglePrice));
				totalPrice = totalAdultPrice + totalChildPrice + totalSinglePrice;
			}
		}

		bookVo.setTotalPrice(moneyFormat.format(totalPrice));
		Boolean isContainFlight = product.isContainFlight();
		Boolean isContainHotel = product.isContainHotel();
		bookVo.setIsContainFlight(isContainFlight);
		bookVo.setIsContainHotel(isContainHotel);
		bookVo.setAdditionalInfos(product.getAdditionalInfos());
		// 设置航班信息
		if (isContainFlight) {
			Flight flight = product.getFlight();
			List<FlightInfo> flightInfoList = flight.getInfos();
			List<ProductFlightInfo> goFlightList = new ArrayList<ProductFlightInfo>();
			List<ProductFlightInfo> backFlightList = new ArrayList<ProductFlightInfo>();
			List<ProductFlightInfo> midleFlightList = new ArrayList<ProductFlightInfo>();
			if (!CollectionUtils.isEmpty(flightInfoList)) {
				for (FlightInfo flightInfo : flightInfoList) {
					ProductFlightInfo productFlight = new ProductFlightInfo();
					// 航班信息
					productFlight.setFlightNum(flightInfo.getFlightNo());
					productFlight.setPlaneCode(flightInfo.getFlightModel());
					productFlight.setAirLineName(flightInfo.getAirLine());
					productFlight.setCabin(flightInfo.getCabin());
					productFlight.setFromCity(flightInfo.getFromCity());
					productFlight.setToCity(flightInfo.getToCity());
					if (null != flightInfo.getAirRangeIndex()) {
						productFlight.setAirRangeIndex(String.valueOf(flightInfo.getAirRangeIndex()));
					} else {
						productFlight.setAirRangeIndex("");
					}

					productFlight.setDepartAirPort(flightInfo.getFromAirPort());
					productFlight.setDepartTime(flightInfo.getDepartureTime());

					String toAirPort = flightInfo.getToAirPort();
					productFlight.setArriveAirPort(toAirPort);
					productFlight.setArriveTime(flightInfo.getArrivalTime());
					productFlight.setStop(flightInfo.getStop());
					productFlight.setOffsetDays(flightInfo.getOffsetDays());

					Integer addDays = flightInfo.getAddDays() == null ? 0 : flightInfo.getAddDays();
					productFlight.setAddDays(addDays);
					productFlight.setFlightTimeCost(getFlyTime(flightInfo.getDepartureTime(), flightInfo.getArrivalTime(), flightInfo.getAddDays()));// 写一个tool
					if (flightInfo.getAirRange().getName().equals(AirRange.GO.getName())) {
						productFlight.setDepartDate(DateUtils.addDate(criteria.getBookDate(), 3,
								flightInfo.getOffsetDays() == null ? 0 : flightInfo.getOffsetDays() - 1));
						Integer diffDay = DateTimeUtil.diffInDay(DateTimeUtil.convertStringToDate(productFlight.getDepartDate()),
								DateTimeUtil.convertStringToDate(criteria.getBookDate()));
						productFlight.setDepartDayIndex(diffDay + 1);
						goFlightList.add(productFlight);
					} else if (flightInfo.getAirRange().getName().equals(AirRange.BACK.getName())) {
						productFlight.setDepartDate(DateUtils.addDate(criteria.getBookDate(), 3,
								flightInfo.getOffsetDays() == null ? 0 : flightInfo.getOffsetDays() - 1));
						Integer diffDay = DateTimeUtil.diffInDay(DateTimeUtil.convertStringToDate(productFlight.getDepartDate()),
								DateTimeUtil.convertStringToDate(criteria.getBookDate()));
						productFlight.setDepartDayIndex(diffDay + 1);
						backFlightList.add(productFlight);
					} else if (flightInfo.getAirRange().getName().equals(AirRange.MIDDLE.getName())) {
						productFlight.setDepartDate(DateUtils.addDate(criteria.getBookDate(), 3,
								flightInfo.getOffsetDays() == null ? 0 : flightInfo.getOffsetDays() - 1));// 中间程的航班出发日期待定
						Integer diffDay = DateTimeUtil.diffInDay(DateTimeUtil.convertStringToDate(productFlight.getDepartDate()),
								DateTimeUtil.convertStringToDate(criteria.getBookDate()));
						productFlight.setDepartDayIndex(diffDay + 1);
						midleFlightList.add(productFlight);
					}
				}
			}
			bookVo.setGoFlightList(goFlightList);
			bookVo.setBackFlightList(backFlightList);
			bookVo.setMidlFlightList(midleFlightList);
		}
		// 设置产品第一张图片ID
		if (!CollectionUtils.isEmpty(product.getImages())) {
			bookVo.setFirstImageId(product.getImages().get(0));
		}
		// 设置酒店信息
		if (isContainHotel) {
            List<String> countries = product.getToCountry();
            if(!CollectionUtils.isEmpty(countries) && countries.contains(BED_TIP_COUNTRY)){
                bookVo.setIsBedTips(true);
            }
			List<Hotel> hotelList = product.getHotels();
			List<ProductHotelInfo> productHotelList = new ArrayList<ProductHotelInfo>();
			if (!CollectionUtils.isEmpty(hotelList)) {
				for (Hotel hotel : hotelList) {
					ProductHotelInfo productHotelInfo = new ProductHotelInfo();
					HotelClientEntity hotelEntity = hotelClientServiceImpl.getClientEntity(hotel.getId());
					productHotelInfo.setHotelName(hotelEntity.getHotelNameCN());
					productHotelInfo.setHotelType(hotel.getBedType().getDesc());
					productHotelInfo.setRoomType(hotel.getRoomType());
					List<Integer> checkinDays = hotel.getCheckinDays();
					if (!CollectionUtils.isEmpty(checkinDays)) {
						productHotelInfo.setCheckInDate(DateUtils.addDate(criteria.getBookDate(), 3, checkinDays.get(0) - 1));
						productHotelInfo.setCheckOutDate(DateUtils.addDate(criteria.getBookDate(), 3, checkinDays.get(checkinDays.size() - 1)));
					} else {
						productHotelInfo.setCheckInDate(criteria.getBookDate());
						productHotelInfo.setCheckOutDate(DateUtils.addDate(criteria.getBookDate(), 3, 1));
					}
					productHotelInfo.setTripNights(DateTimeUtil.diffInDay(DateTimeUtil.convertStringToDate(productHotelInfo.getCheckOutDate()),
							DateTimeUtil.convertStringToDate(productHotelInfo.getCheckInDate())));
					productHotelList.add(productHotelInfo);
				}
				bookVo.setHotelList(productHotelList);
			}
		}

		return bookVo;
	}
	
	public Boolean isReleased(String productId,String productNature){
		   Boolean isReleased = false;
		   Assert.hasText(productId, "查询产品状态时，产品ID为空");
		   Assert.hasText(productNature, "查询产品[{"+productId+"}]状态时,产品类型为空");
		   if(productNature.trim().equals(Nature.COMBINATION.name()) || productNature.trim().equals(Nature.PACKAGE.name())){
			   Product product = productDao.getProductById(productId);
			   isReleased = product == null ? false : product.getStatus().equals(Status.RELEASED);
		   }else{
			   isReleased = pieceProductPriceReuseService.isReleased(productNature, productId);
		   }
		   return isReleased;
	   }

}
