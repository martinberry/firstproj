package com.ztravel.member.front.controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.common.enums.VoucherOrderStatus;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.payment.CouponItemQueryBean;
import com.ztravel.common.payment.DNCouponGrantBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.SystemNoticeContentUtil;
import com.ztravel.member.client.service.ICouponClientService;
import com.ztravel.member.front.service.ISystemNoticeService;
import com.ztravel.member.front.service.MemberService;
import com.ztravel.member.front.vo.CouponSearchCriteria;
import com.ztravel.member.po.Member;
import com.ztravel.order.client.service.IVoucherOrderClientService;
import com.ztravel.order.po.VoucherOrder;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.entity.vo.MyWalletVo;
import com.ztravel.product.client.service.IActivityClientService;
//import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.client.service.IVoucherClientService;

/**
 * C端首页
 * @author haofan.wan
 */

@Controller
@RequestMapping("/electronicWallet/coupon")
public class CouponController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(CouponController.class);


	@Resource
	private MemberService memberService;

	@Resource
	private ICouponService couponService;

	@Resource
	IActivityClientService activityClientService;
	@Resource
	ISystemNoticeService systemNoticeServiceImpl;

	@Resource
	private ICouponClientService couponClientService ;

	@Resource
	private IVoucherClientService voucherClientService ;

	@Resource
	private IVoucherOrderClientService voucherOrderClientService ;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, Model model) throws Exception{
		String memberId =SSOUtil.getMemberId();
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		if(memberSessionBean == null || memberSessionBean.getIsLogin() == false || StringUtils.isBlank(memberSessionBean.getMid()) || memberSessionBean.getIsActive() == false){
			throw ZtrBizException.instance(Const.FF_MEMB_CODE_1001, Const.FF_MEMB_REASON_1001) ;
		}

		Member member = memberService.getMemberById(memberId);
		couponClientService.grantCoupon(memberId);
		if(null != member){
			String selfMobile = member.getMobile();
			model.addAttribute("selfMobile", selfMobile);
		}

		return new ModelAndView("member/front/electronicWallet/coupon/index") ;
	}

	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String  search(@RequestBody CouponSearchCriteria searchCriteria,	 Model model) throws Exception{

		List<MyWalletVo> myWalletVos = null ;

		CouponItemQueryBean couponItemQuery = buildCouponItemQuery(searchCriteria);

		 Pagination<CouponItemBean> paginationResult = couponService.getCouponItems(couponItemQuery);

		 int pageNo = searchCriteria.getPageNo() ;
		 long totalItemCount = paginationResult.getTotalItemCount();
		 Integer totalPageCount = (int) Math.ceil(new Double(totalItemCount)/searchCriteria.getPageSize() );

		 if(null!= paginationResult.getData() && paginationResult.getData().size()>0){
			 List<CouponItemBean> couponItems = (List<CouponItemBean>)paginationResult.getData();
			 myWalletVos = getMyWalletVosByCopuItems(couponItems);
		 }

		 if(null==myWalletVos|| myWalletVos.size()==0){
			 pageNo = 1;
			 totalItemCount = 0;
			 totalPageCount=1;
		}

		model.addAttribute("couponItems", myWalletVos);
		model.addAttribute("pageNo",  pageNo);
		model.addAttribute("pageSize", searchCriteria.getPageSize());
		model.addAttribute("totalItemCount", totalItemCount);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("status", searchCriteria.getStatus());

		return "member/front/electronicWallet/coupon/couponItems_table";
	}

	public CouponItemQueryBean  buildCouponItemQuery(CouponSearchCriteria searchCriteria) throws Exception{
		String memberId = SSOUtil.getMemberId();
		CouponItemQueryBean couponItemQuery = new CouponItemQueryBean();
		couponItemQuery.setPageNo(searchCriteria.getPageNo());
		couponItemQuery.setPageSize(searchCriteria.getPageSize());
		List<CouponItemStatus> couponItemStatusList = Lists.newArrayList();
		CouponItemStatus status = searchCriteria.getStatus();
		if(status != null){
			if(searchCriteria.getStatus().equals(CouponItemStatus.USED)){
				couponItemStatusList.add(CouponItemStatus.USED);
				couponItemStatusList.add(CouponItemStatus.FROZEN);
			}else if(searchCriteria.getStatus().equals(CouponItemStatus.INVALID)){
				couponItemStatusList.add(CouponItemStatus.EXPIRED);
				couponItemStatusList.add(CouponItemStatus.INVALID);
			}
			else{
				couponItemStatusList.add(searchCriteria.getStatus());
			}
		}
		couponItemQuery.setStatusList(couponItemStatusList);
		couponItemQuery.setMemberId(memberId);
		return couponItemQuery;
	}



	private List<MyWalletVo> getMyWalletVosByCopuItems(List<CouponItemBean> couponItems) throws Exception{
		List<MyWalletVo> myWalletVos = Lists.newArrayList();

		for(CouponItemBean couponItemBean :	couponItems ){
			String couponItemId = couponItemBean.getCouponItemId();
			Voucher voucher = voucherClientService.getVoucherByCouponItemId(couponItemId) ;
			MyWalletVo myWalletVo = new MyWalletVo();
			//DN活动
			if(voucher != null){
				if(CouponItemStatus.valueOf(couponItemBean.getStatus()) == CouponItemStatus.INVALID
						&& voucher.getVoucherType() == VoucherType.NORMAL){
					List<VoucherOrder> voucherOrderList = voucherOrderClientService.selectVoucherOrderByVoucherCode(voucher.getVoucherCode()) ;
					for(VoucherOrder voucherOrder : voucherOrderList){
						if(voucherOrder.getStatus() != VoucherOrderStatus.CANCELED){
							couponItemBean.setStatus(voucherOrder.getStatus().toString());
						}
					}
				}
				myWalletVo.setPrice(voucher.getPrice());
				myWalletVo.setVoucherType(voucher.getVoucherType());
			}

			CouponClientEntity entity = activityClientService.getCouponById(couponItemBean.getActivityId(), couponItemBean.getCouponCode()) ;

			if(entity != null){
				myWalletVo.setProductRange(entity.getProductRange());
				myWalletVo.setValidTimeFrom(entity.getValidTimeFrom().substring(0, 10));
				myWalletVo.setValidTimeTo(entity.getValidTimeTo().substring(0, 10));
				myWalletVo.setOrderLeast(entity.getOrderLeast());
			}
			myWalletVo.setCouponCode(couponItemBean.getCouponCode());
			myWalletVo.setDescription(couponItemBean.getDescription());
			myWalletVo.setName(couponItemBean.getName());
			myWalletVo.setAmount(couponItemBean.getAmount());
			myWalletVo.setCouponItemId(couponItemBean.getCouponItemId());
			myWalletVo.setStatus(couponItemBean.getStatus());
			myWalletVos.add(myWalletVo);

		}
		return myWalletVos;
	}

	@RequestMapping(value="/getNickNameByPhone",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  getNickNameByPhone(String phone) throws Exception{
		Map<String,Object> map =		Maps.newHashMap();
		try{
			map =memberService.getNickNameByPhone(phone);
		}catch(Exception e){
			LOGGER.info("通过手机号{}获取昵称失败: "+e,phone);
		}
		return map;
	}


	@RequestMapping(value="/transferCouponItem",method=RequestMethod.GET)
	@ResponseBody
	public CommonResponse  transferCouponItem(String couponItemId,String destMembeId ,String shareAmount) throws Exception{
		CommonResponse commonResponse = new CommonResponse();

		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
		String nickName = memberSessionBean.getNickName();

		commonResponse = couponService.transfer(couponItemId, destMembeId);
		try{
			if(commonResponse.isSuccess()){
				systemNoticeServiceImpl.add(destMembeId, NoticeType.COUPONTYPE.toString(), SystemNoticeContentUtil.getCpContent(nickName, Double.parseDouble(shareAmount)/100));
			}
		}catch(Exception e){
			LOGGER.info("发送分享系统消息失败： ",e);
		}

		return commonResponse;
	}

	@RequestMapping(value="/membersIsActive",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  membersIsActive(String destMembeId) throws Exception{
		Map<String, Object> resultMap = Maps.newHashMap();
		String isActive = "no";
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
		boolean destIsActive = true;
		if(memberSessionBean !=null && memberSessionBean.getIsActive() && destIsActive) {
			isActive = "yes";
		}
		resultMap.put("isActive", isActive);
		return resultMap;
	}

	@RequestMapping(value="/checkMobile",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResponse isMobileAlreadyExists(String mobile) throws Exception{
			return memberService.checkMobile(mobile) ;
	}

	@RequestMapping(value="/exchange",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResponse exchange(String exchangeCode) throws Exception{
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setRes_code("success");
		exchangeCode = exchangeCode.replaceAll(" ", "");
		boolean validateResult = validateExchangeCode(exchangeCode);
		if(!validateResult){
			ajaxResponse.setRes_code("failed");
			ajaxResponse.setRes_msg("兑换码输入有误，请重新输入");
			return ajaxResponse;
		}
		try{
			LOGGER.info("兑换代金券: exchangeCode is {}",exchangeCode);
			exchangeCode = exchangeCode.toUpperCase();
			String memberId = SSOUtil.getMemberId();
			String couponId = "";
			String activityId = "";
			int limitNum = 0;
			Voucher voucher = voucherClientService.getVoucherByCode(exchangeCode);
			if(voucher != null){
				couponId = voucher.getCouponId();
				activityId =  voucher.getActivityId();
			}
			CouponClientEntity couponClientEntity = activityClientService.getCouponById(activityId, couponId);
			if(couponClientEntity != null){
				limitNum = couponClientEntity.getUnit();
			}

			LOGGER.info("判断能不能购买券 params::  memberId: [{}] , couponId: [{}] , limitNum: [{}], orderNum: [{}]", memberId, couponId, limitNum, 1);
			boolean canBuy = voucherOrderClientService.canBuy(memberId, couponId, limitNum, 1);
			LOGGER.info("判断能不能购买券 result:: [{}]", canBuy);
			if(canBuy){
				LOGGER.info("判断能不能购买券 result: [{}]", canBuy);
				CommonResponse commonResponse = DNGrantCoupon(exchangeCode);
				LOGGER.info(TZBeanUtils.describe(commonResponse));
				if(commonResponse.isSuccess()){
					LOGGER.info("兑换代金券 exchangeCode : [{}] success", exchangeCode);
				}else{
					LOGGER.info("兑换代金券 exchangeCode : [{}] failed, reason is: [{}] ", exchangeCode, commonResponse.getErrMsg());
				}
			}else{
				ajaxResponse.setRes_code("failed");
				ajaxResponse.setRes_msg("不能再购买，不能再兑换");
			}


		}catch(Exception e){
			ajaxResponse.setRes_code("failed");
			ajaxResponse.setRes_msg("兑换码输入有误，请重新输入");
			LOGGER.error("exchange coupon by exchangeCode : {} failed,"+e.toString(), exchangeCode);
		}
		return ajaxResponse;
	}

	public CommonResponse DNGrantCoupon(String voucherCode) throws Exception {
		DNCouponGrantBean bean = buildDNCouponGrantBean(voucherCode) ;
		LOGGER.info(TZBeanUtils.describe(bean));
		return couponService.grant(bean) ;
	}

	private DNCouponGrantBean buildDNCouponGrantBean(String voucherCode) throws Exception{
		DNCouponGrantBean bean = new DNCouponGrantBean() ;
		Map<String, Object> params = Maps.newHashMap();
		params.put("voucherType", VoucherType.SYSTEM);
		List<VoucherStatus> statusList = Lists.newArrayList();
		statusList.add(VoucherStatus.INIT);
		params.put("statusList", statusList);
		params.put("voucherCode", voucherCode);
		List<Voucher> voucherList = voucherClientService.getVoucherListByParams(params) ;
		Voucher voucher = voucherList.get(0);
		CouponClientEntity couponClientEntity = activityClientService.getCouponById(voucher.getActivityId(), voucher.getCouponId()) ;
		bean.setActivityId(voucher.getActivityId());
		bean.setMemberId(SSOUtil.getMemberId());
		bean.setAmount(couponClientEntity.getAmount());
		bean.setDescription(couponClientEntity.getDescription());
		bean.setName(couponClientEntity.getName());
		bean.setCouponCode(couponClientEntity.getCouponId());
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		bean.setValidDateFrom(DateTime.parse(couponClientEntity.getValidTimeFrom(), format));
		bean.setValidDateTo(DateTime.parse(couponClientEntity.getValidTimeTo(), format));
		bean.setVoucherCode(voucherCode);
		bean.setVoucherType(voucher.getVoucherType());
		return bean ;
	}

	private boolean validateExchangeCode(String exchangeCode){
		boolean result = true;
	    String regEx = "[0-9a-zA-Z]{6}";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(exchangeCode);
	     result = matcher.matches();
	     return result;
	}

	@RequestMapping(value="/refundVoucher",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResponse refundVoucher(String couponItemId) throws Exception{
		LOGGER.info("refund voucher start, couponCode is {}",couponItemId);
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setRes_code("success");
		try{
			boolean applyRefundResult = voucherOrderClientService.applyRefund(couponItemId);
			LOGGER.info("voucherOrder applyRefund result: [{}]", applyRefundResult);
		}catch(Exception e){
			ajaxResponse.setRes_code("failed");
			ajaxResponse.setRes_msg("代金券退款失败");
			LOGGER.error("refund voucher by couponItemId : {} failed,"+e.toString(), couponItemId);
		}
		LOGGER.info("refund voucher [{}] end, response is {}", couponItemId, TZBeanUtils.describe(ajaxResponse));
		return ajaxResponse;
	}
}
