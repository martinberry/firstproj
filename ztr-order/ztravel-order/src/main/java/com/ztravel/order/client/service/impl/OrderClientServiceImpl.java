package com.ztravel.order.client.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.constants.ProductBookConstans;
import com.ztravel.common.entity.ContactorInfo;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.util.OperatorMessageContentUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.common.weixin.notice.OrderSubmitNotice;
import com.ztravel.common.weixin.notice.PaySuccessNotice;
import com.ztravel.common.weixin.notice.TravelEndNotice;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.order.client.convert.ProductBookItemConvert;
import com.ztravel.order.client.service.IOrderClientService;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.dao.IOrderComContactorDao;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderOpenIdDao;
import com.ztravel.order.dao.IOrderPassengerDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.dao.IOrderProductStockDao;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderComContactor;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderOpenId;
import com.ztravel.order.po.OrderPassenger;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.payment.service.IOrderPaymentService;
import com.ztravel.product.client.entity.ProductClientEntity;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.reuse.order.converter.Order2DetailConverter;
import com.ztravel.reuse.order.entity.OrderDetailWo;
import com.ztravel.reuse.order.entity.OrderPayVo;
import com.ztravel.reuse.order.service.IOrderLogReuseService;
import com.ztravel.reuse.product.entity.ProductBookVo;

@Service
public class OrderClientServiceImpl implements IOrderClientService{

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(OrderClientServiceImpl.class);
	private static final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	IOrderDao orderDao;

	@Resource
	private IOperatorMessageClientService  operatorMessageClientService;

	@Resource
	IOrderProductDao orderProductDao;

	@Resource
	IOrderComContactorDao orderComContactorDao;

	@Resource
	IOrderProductStockDao orderProductStockDao;

	@Resource
	IOrderPassengerDao orderPassengerDao;

	@Resource
	IOrderContactorDao orderContactorDao;

	@Resource
	IProductClientService productClientSerivceImpl;

	@Resource
	private IOrderLogReuseService orderLogReuseService;

	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Resource
	private ICouponService couponService;

	@Resource
	private IOrderPaymentService orderPaymentService;

	@Resource
	IOrderOpenIdDao orderOpenIdDaoImpl;

	@Resource
	IMemberClientService memberClientServiceImpl;

	@Resource
	ICommonOrderDao commonOrderDao;

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public Map<String,Object> applyOrder(ProductBookItem productBookItem) throws Exception{
		Map<String,Object> applyResult = new HashMap<String,Object>();
		AjaxResponse result = AjaxResponse.instance(ProductBookConstans.APPLY_ORDER_FAILURE,"");
		//构建订单信息
		Order order = ProductBookItemConvert.buildOrderByBookitem(productBookItem,idGeneratorUtil.getOrderId(),idGeneratorUtil.getOrderNo());
		String orderId = order.getOrderId();
		//构建订单产品
		ProductClientEntity productClientEntity = new ProductClientEntity();
		if(Nature.PACKAGE.name().equals(productBookItem.getNature()) || Nature.COMBINATION.name().equals(productBookItem.getNature())){
			productClientEntity = productClientSerivceImpl.getProductById(productBookItem.getProductId());
		}
		OrderProduct orderProduct = ProductBookItemConvert.buildOrderProductByBookitem(productBookItem,orderId,productClientEntity);
		//构建订单乘客
		List<OrderPassenger> passengerList = ProductBookItemConvert.buildOrderPassengerByBookitem(productBookItem,orderId);
		//构建订单联系人
		OrderContactor orderContactor = ProductBookItemConvert.buildOrderContactorByBookitem(productBookItem,orderId);
		//构建常用建单联系人,每次都保存用户最后一次建单的联系人信息
		OrderComContactor orderComContactor = orderComContactorDao.selectByMemberIdAndName(SSOUtil.getMemberSessionBean().getMemberId(), orderContactor.getContactor());
		if(null == orderComContactor){
			orderComContactor = ProductBookItemConvert.buildComContactorByBookitem(productBookItem,null);
			orderComContactorDao.insert(orderComContactor);
		}else{
			orderComContactor = ProductBookItemConvert.buildComContactorByBookitem(productBookItem,orderComContactor.getId());
			orderComContactorDao.update(orderComContactor);
		}
		//保存原订单JSON
		OrderDetailWo orderDetailWo = new OrderDetailWo();
		Order2DetailConverter.convertOrderDetailWo(order, orderProduct, orderDetailWo);
		orderDetailWo.setPassengers(passengerList);
		orderDetailWo.setOrderContactor(orderContactor);
		order.setOriginalOrder(JSONObject.toJSONString(orderDetailWo));
		//插入订单信息
		orderDao.insert(order);
		orderProductDao.insert(orderProduct);
		orderLogReuseService.save(orderId, SSOUtil.getMemberSessionBean().getMid(), "创建","");
		orderContactorDao.insert(orderContactor);
		if(CollectionUtils.isNotEmpty(productBookItem.getPassengerList())){
			orderPassengerDao.batchInsert(passengerList);
		}
		//微信建单操作
		if(StringUtils.isNotBlank(OpenIdUtil.getOpenId())){
			OrderOpenId orderOpenId = new OrderOpenId();
			orderOpenId.setOrderId(order.getOrderId());
			orderOpenId.setOpenId(OpenIdUtil.getOpenId());
			orderOpenIdDaoImpl.insert(orderOpenId);
		}
		//建单时,利用乐观锁对产品某天的产品库存上锁
		String params = productBookItem.getProductId() + ":" + productBookItem.getBookDate();
		orderProductStockDao.forUpdateByIdAndBookDate(params);
		//冻结优惠券
		if(StringUtils.isNotBlank(productBookItem.getDiscountcouponId())){
			CommonResponse freezeResponse = couponService.freeze(productBookItem.getDiscountcouponId());
			if(!freezeResponse.isSuccess()){
				LOGGER.error("优惠券[{}]冻结结果[{}]",productBookItem.getDiscountcouponId(),TZBeanUtils.describe(freezeResponse));
				throw ZtrBizException.instance(ActivityConstants.COUPONUSED_TO_APPLY_ORDER_FAILED, "建单冻结优惠券{"+productBookItem.getDiscountcouponId()+"错误:"+freezeResponse.getErrMsg());
			}
		}
		//构建,保存支付页需要显示的相关信息
		OrderPayVo orderPayVo = ProductBookItemConvert.buildOrderPayVo(productBookItem,order.getOrderId(),order.getOrderNo());
		redisClient.set(orderId, orderPayVo);
		//校验,更新产品剩余库存
		if(Nature.PACKAGE.getName().equals(productBookItem.getProductType()) || Nature.COMBINATION.getName().equals(productBookItem.getProductType())){
			Integer leftStock = productClientSerivceImpl.getStock(productBookItem.getProductId(), productBookItem.getBookDate());
			Integer needStock = productBookItem.getAdaultNum() + productBookItem.getChildrenNum();
			if(needStock > leftStock){
				throw ZtrBizException.instance(ProductBookConstans.PRODUCT_STOCK_NOT_ENOUGH,"产品{"+productBookItem.getProductNo()+"}库存不足");
			}
			productClientSerivceImpl.updateAndModifyStock(productBookItem.getProductId(),productBookItem.getBookDate(), needStock);
		}
		result.setRes_code(OrderConstans.ORDER_CREATE_SUCCESS);
		result.setRes_msg(order.getOrderId());
		applyResult.put("result", result);
		applyResult.put("orderNo", order.getOrderNo());
		return applyResult;
	}

	@Override
	public Boolean orderProductIsGone(String mid, String productId) throws SQLException {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("creator", mid);
		paramMap.put("state", ZtOrderStatus.COMPLETED.getCode());
		paramMap.put("productId", productId);
		List<OrderProduct> list = orderProductDao.selectUnion(paramMap);
		 return list.size() > 0 ? true : false;
	}


	@Override
	public ContactorInfo getContactorInfoByMemId(String memberId)throws SQLException {
		ContactorInfo contactorInfo = new ContactorInfo();
		OrderComContactor comContactor = orderComContactorDao.selectByMemberId(memberId);
		if(null != comContactor){
			contactorInfo.setAddressDetail(comContactor.getAddress());
			contactorInfo.setCity(comContactor.getCity());
			contactorInfo.setCounty(comContactor.getCounty());
			contactorInfo.setEmail(comContactor.getEmail());
			contactorInfo.setName(comContactor.getContactor());
			contactorInfo.setPhone(comContactor.getPhone());
			contactorInfo.setProvince(comContactor.getProvince());
			return contactorInfo;
		}
		return null;
	}
	
	@Override
	public List<OrderComContactor> getContactorsByMemId(String memberId)throws SQLException {
		LOGGER.info("查询用户[{}]常用联系人",memberId);
		List<OrderComContactor> contactors = orderComContactorDao.selectContactorsByMemberId(memberId);
		LOGGER.info("用户[{}]常用联系人信息:[{}]",memberId,TZBeanUtils.describe(contactors));
		return contactors;
	}

	@Override
	public void sendWeiXinSubmitMsg(String orderId){
		OrderSubmitNotice orderSubmitNotice = buildWeiXinSubmitMsg(orderId);
		if(null != orderSubmitNotice){
			LOGGER.info("订单[{}]提交的推送消息为:",orderId,TZBeanUtils.describe(orderSubmitNotice));
			String msgUrl = WebEnv.get("server.path.wxServer","") + "/weixinController/orderSubmit";
			HttpPost httppost = new HttpPost(msgUrl);
			StringEntity entity = new StringEntity(JSONObject.toJSONString(orderSubmitNotice), "UTF-8");
			httppost.setEntity(entity);
			HttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(httppost);
				InputStream in  = response.getEntity().getContent();
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
					LOGGER.info("微信订单[{}]建单消息推送成功",orderId);
				}
				else {
					LOGGER.info(IOUtils.toString(in));
				}
			} catch (ClientProtocolException e) {
				LOGGER.error(TZMarkers.p2, "http post ClientProtocolException", e);
			} catch (IOException e) {
				LOGGER.error(TZMarkers.p2, "http post IOException", e);
			}
		}
	}

	@Override
	public void sendWeiXinPayMsg(String orderId){
		PaySuccessNotice orderPayNotice = buildWeiXinPayMsg(orderId);
		if(null != orderPayNotice){
			LOGGER.info("订单[{}]支付的推送消息为:",orderId,TZBeanUtils.describe(orderPayNotice));
			String msgUrl = WebEnv.get("server.path.wxServer","") + "/weixinController/paySuccess";
			HttpPost httppost = new HttpPost(msgUrl);
			StringEntity entity = new StringEntity(JSONObject.toJSONString(orderPayNotice), "UTF-8");
			httppost.setEntity(entity);
			HttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(httppost);
				InputStream in  = response.getEntity().getContent();
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
					LOGGER.info("微信订单[{}]支付消息推送成功",orderId);
				}
				else {
					LOGGER.info(IOUtils.toString(in));
				}
			} catch (ClientProtocolException e) {
				LOGGER.error(TZMarkers.p2, "http post ClientProtocolException", e);
			} catch (IOException e) {
				LOGGER.error(TZMarkers.p2, "http post IOException", e);
			}
		}
	}

	@Override
	public Map<String,String> OrderSearchBycouponItem(String couponItem)throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		Order order=orderDao.selectBycouponItem(couponItem);
        map.put("creator", order.getCreator());
        map.put("paystate",order.getBackState());
        map.put("orderno",order.getOrderNo());
		return map;
	}

	public OrderSubmitNotice buildWeiXinSubmitMsg(String orderId ){
		OrderSubmitNotice notice = new OrderSubmitNotice();
		try {
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			if(null == orderOpenId){
				LOGGER.info("订单{"+orderId+"}没有对应的openId信息");
			}else{
				if(StringUtils.isBlank(orderOpenId.getOpenId())){
					throw ZtrBizException.instance("", "微信端订单{"+orderId+"}对应的openId不存在,消息推送失败");
				}
				Order order = orderDao.selectById(orderId);
				OrderProduct orderProduct = orderProductDao.selectByOrderId(orderId);
				if(null != order){
					notice.setOpenId(orderOpenId.getOpenId());
					notice.setOrderId(order.getOrderNo());
					notice.setPayAmount(MoneyUtil.cent2Yuan(order.getPayAmount()));
					String wxServer = WebEnv.get("server.path.wxServer","") ;
					String url = "" ;
					if(wxServer != null && "/".equals(String.valueOf(wxServer.charAt(wxServer.length() - 1)))){
						url = wxServer+"weixin/orderPay/selectPayType?orderId=" + orderId;
					}else{
						url = wxServer+"/weixin/orderPay/selectPayType?orderId=" + orderId;
					}
					notice.setPayUrl(url);
					notice.setUserName(SSOUtil.getMemberSessionBean().getNickName());
					if(null != orderProduct){
						notice.setProductContent(orderProduct.getProductTitle());
						notice.setBookDate(DateTimeUtil.date10(orderProduct.getBookDate()).replaceFirst("-", "年").replaceFirst("-", "月")+"日");
					}else{
						LOGGER.error("订单[{}]产品信息不存在,推送微信消息失败",orderId);
						throw ZtrBizException.instance("", "订单{"+orderId+"}不存在");
					}
				}else{
					LOGGER.error("订单[{}]不存在,推送微信消息失败",orderId);
					throw ZtrBizException.instance("", "订单{"+orderId+"}不存在");
				}

			}
		} catch (SQLException e) {
			LOGGER.error("订单{}推送微信消息操作错误", orderId,e);
			return null;
		}
		return notice;
	}

	public PaySuccessNotice buildWeiXinPayMsg(String orderId ){
		PaySuccessNotice notice = null ;
		try {
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			if(null == orderOpenId){
				LOGGER.info("订单{"+orderId+"}没有对应的openId信息");
			}else{
				if(StringUtils.isBlank(orderOpenId.getOpenId())){
					throw ZtrBizException.instance("", "微信端订单{"+orderId+"}对应的openId不存在,消息推送失败");
				}
				Order order = orderDao.selectById(orderId);
				OrderProduct orderProduct = orderProductDao.selectByOrderId(orderId);

				if(null != order){
					notice = new PaySuccessNotice();
					notice.setOpenId(orderOpenId.getOpenId());
					notice.setOrderCode(order.getOrderNo());
					notice.setPayAmount(MoneyUtil.cent2Yuan(order.getPayAmount()));
					String url = WebEnv.get("server.path.wxServer","")+"/order/weixin/detail/" + order.getOrderId();
					notice.setDetailUrl(url);
					notice.setUserName(memberClientServiceImpl.getNickNameByMid(order.getCreator()));
					if(null != orderProduct){
						notice.setProductContent(orderProduct.getProductTitle());
						notice.setBookDate(DateTimeUtil.date(DateTimeUtil.DATE_PATTERN, orderProduct.getBookDate().getTime()));
					}else{
						LOGGER.error("订单[{}]产品信息不存在,推送微信消息失败",order.getOrderId());
						throw ZtrBizException.instance("", "订单{"+orderId+"}不存在");
					}
				}else{
						LOGGER.error("订单[{}]不存在,推送微信消息失败",orderId);
						throw ZtrBizException.instance("", "订单{"+orderId+"}不存在");
				}
			}
		} catch (SQLException e) {
			LOGGER.error("订单{}推送微信消息操作错误", orderId,e);
			return null;
		}
		return notice;
	}

	public void sendTravelEndMsg(String orderId){
		TravelEndNotice travelEndNotice = buildTravelEndMsg(orderId);
		if(null != travelEndNotice){
			LOGGER.info("订单[{}]行程结束的推送消息为:",orderId,TZBeanUtils.describe(travelEndNotice));
			String msgUrl = WebEnv.get("server.path.wxServer","") + "/weixinController/travelEnd";
			HttpPost httppost = new HttpPost(msgUrl);
			StringEntity entity = new StringEntity(JSONObject.toJSONString(travelEndNotice), "UTF-8");
			httppost.setEntity(entity);
			HttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(httppost);
				InputStream in  = response.getEntity().getContent();
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
					LOGGER.info("微信订单[{}行程结束的消息推送成功",orderId);
				}
				else {
					LOGGER.info(IOUtils.toString(in));
				}
			} catch (ClientProtocolException e) {
				LOGGER.error(TZMarkers.p2, "http post ClientProtocolException", e);
			} catch (IOException e) {
				LOGGER.error(TZMarkers.p2, "http post IOException", e);
			}
		}
	}

	public TravelEndNotice buildTravelEndMsg(String orderId){
		TravelEndNotice notice = null;
		try {
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			if(null == orderOpenId){
				LOGGER.info("订单{"+orderId+"}没有对应的openId信息");
			}else{
				if(StringUtils.isBlank(orderOpenId.getOpenId())){
					throw ZtrBizException.instance("", "微信端订单{"+orderId+"}对应的openId不存在,消息推送失败");
				}
				Order order = orderDao.selectById(orderId);
				OrderProduct orderProduct = orderProductDao.selectByOrderId(orderId);
				if(null != order){
					notice = new TravelEndNotice();
					String url = WebEnv.get("server.path.wxServer","")+"/order/weixin/detail/" + orderId;
					notice.setDetailUtl(url);

					notice.setEndDate(DateTimeUtil.date10(orderProduct.getBackDate()).replaceFirst("-", "年").replaceFirst("-", "月")+"日");
					notice.setProductContent(orderProduct.getProductTitle());
					notice.setUserName(memberClientServiceImpl.getNickNameByMid(order.getCreator()));
					notice.setOpenId(orderOpenId.getOpenId());
				}else{
					LOGGER.error("订单[{}]不存在,推送微信消息失败",orderId);
					throw ZtrBizException.instance("", "订单{"+orderId+"}不存在");
				}

			}
		} catch (SQLException e) {
			LOGGER.error("订单{}推送微信消息操作错误", orderId,e);
			return null;
		}
		return notice;
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public CommonResponse cancleOrder(String orderId, String operator) throws Exception {
		CommonResponse response = new CommonResponse();
		response.setSuccess(false);
		Order order = orderDao.selectById(orderId);
		OrderProduct orderProduct = orderProductDao.selectByOrderId(orderId);
		if(null != order && order.getBackState().equals(ZtOrderStatus.UNPAY.getCode())){
			orderLogReuseService.save(orderId,operator, "取消", "");
			if(order.getIsToPay()){//跳转支付后,取消订单时才调用thrift,否则找不到订单
				LOGGER.info("订单[]曾跳转到支付页,开始进入orderPaymentService中订单取消流程",orderId);
				response = orderPaymentService.cancelOrder(order.getOrderNo(), operator ,order.getPayType());
				if(response == null || (response != null && !response.isSuccess())){
					LOGGER.debug("调用orderPaymentService thrift 服务取消订单[{}]失败",orderId);
					throw new RuntimeException("调用orderPaymentService thrift 服务失败");
				}
				LOGGER.info("调用orderPaymentService取消订单[]结束",orderId);
			}else{
				String backState = order.getBackState();
				order.setBackState(ZtOrderStatus.CANCELED.getCode());
				order.setFrontState(ZtOrderStatus.CANCELED.getCode());
				order.setOperator(operator);
				order.setStateChangeHistory(order.getStateChangeHistory()+"-->"+ZtOrderStatus.CANCELED.getCode());
				Boolean updateResult = orderDao.updateOrder(order);
				if(updateResult){
					if(StringUtils.isNotBlank(order.getDiscountCouponId())){
						LOGGER.info("定时器取消订单:[],订单有使用优惠券，开始进入优惠券解冻服务，优惠券ID:[]",orderId,order.getDiscountCouponId());
						response = couponService.unfreeze(order.getDiscountCouponId());
						if(response == null || (response != null && !response.isSuccess())){
							LOGGER.debug("取消订单时调用couponService thrift 服务解冻代金券[{}]失败",order.getDiscountCouponId());
							throw new RuntimeException("调用couponService thrift 服务解冻代金券失败 ");
						}
						LOGGER.info("定时器取消订单:[],解冻优惠券:[]结束",orderId,order.getDiscountCouponId());
					}
					if(backState.equals(ZtOrderStatus.UNPAY.getCode())){
						//自由行产品订单需要释放库存
						if(StringUtils.isEmpty(order.getProductNature()) || order.getProductNature().equals(Nature.COMBINATION.name()) || order.getProductNature().equals(Nature.PACKAGE.name())){
							LOGGER.info("定时器取消订单:[],订单产品类型：[],开始释放订单库存.",orderId,order.getProductNature());
							ProductBookVo productBook = JSON.parseObject(orderProduct.getProductSnapshot(), ProductBookVo.class);
							Integer usedStock = productBook.getAdultNum() + productBook.getChildNum();
							productClientSerivceImpl.updateAndModifyStock(productBook.getProductId(), productBook.getDepartDay(), -usedStock);//释放库存
							LOGGER.info("定时器取消订单:[],订单产品类型：[],完成订单库存释放.",orderId,order.getProductNature());
						}
					}
					operatorMessageClientService.add(MessageTitleType.CANCLE, order.getCreator(), orderProduct.getProductTitle(), OperatorMessageContentUtil.getOrderUrl(order.getOrderId(),order.getProductNature()));
				}
			}
			//发送后台消息
			LOGGER.debug("业务员{}取消订单{}操作成功",operator,orderId);
		}else{
			LOGGER.info("取消订单操作失败,订单[{}]已取消",orderId);
		}
		return response ;
	}
}
