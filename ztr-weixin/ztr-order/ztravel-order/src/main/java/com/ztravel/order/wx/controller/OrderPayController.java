package com.ztravel.order.wx.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.util.UriEncoder;

import com.google.common.collect.Maps;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.enums.OrderPayStatus;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.OrderPayInfoBean;
import com.ztravel.common.payment.PaymentResponse;
import com.ztravel.common.payment.PaymentTestArgs;
import com.ztravel.common.payment.WxPaymentResponse;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.common.util.WeixinSignUtil;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.order.client.service.IOrderClientService;
import com.ztravel.order.dao.IOrderOpenIdDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderOpenId;
import com.ztravel.order.wx.service.IWxOrderService;
import com.ztravel.payment.service.IOrderPaymentService;
import com.ztravel.reuse.order.converter.Convert2OrderPayBean;
import com.ztravel.reuse.order.entity.OrderPayFormBean;
import com.ztravel.reuse.order.entity.OrderPayResultFormBean;
import com.ztravel.reuse.order.entity.OrderPayVo;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
import com.ztravel.reuse.order.service.IOrderOpenIdReuseService;
import com.ztravel.reuse.order.service.IOrderPayReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;

@Controller
@RequestMapping("/weixin/orderPay")
public class OrderPayController {
	private static Logger logger = RequestIdentityLogger
			.getLogger(OrderPayController.class);

	private static final String wxServer = WebEnv.get("server.path.wxServer", "");

	@Resource
	IOrderPaymentService orderPaymentService;

	@Resource
	IOrderOpenIdReuseService orderOpenIdReuseService;

	@Resource
	IOrderPayReuseService orderPayReuseService;

	@Resource
	IMemberClientService memberClientService;

	@Resource
	IOrderClientService orderClientService;
	
	@Resource
	IOrderOpenIdDao orderOpenIdDaoImpl;
	
	@Resource
	ICommonOrderReuseService commonOrderReuseService;
	
	@Resource
	IWxOrderService wxOrderServiceImpl;
	
	@Resource
	IOrderReuseService orderReuseService;

	@RequestMapping(value = "/selectPayType")
	public ModelAndView selectPayType(String orderId,HttpServletRequest request,ModelAndView model) throws Exception {
		logger.info("=======微信订单{}跳转选择支付页面开始=======",orderId);
		String checkSum = "";
		OrderPayVo orderPayVo = new OrderPayVo();
		String openId = null ;
		try {
			openId = OpenIdUtil.getOpenId();//get openId from redis
			saveOpenId(orderId, openId);
			logger.info("==get openId:::{}", openId);
			if(StringUtils.isNotEmpty(orderId)){
				Order order = orderReuseService.getOrderById(orderId);
				CommonOrder commonOrder = commonOrderReuseService.selectByOrderId(orderId);
				if(null == order && null != commonOrder){//补款单
					order = orderReuseService.getOrderById(commonOrder.getOrderIdOrigin());
					if(commonOrder.getStatus().equals(CommonOrderStatus.PAID)){
						model.addObject("realOrderId", orderId);
						String payAmount ="";
						if(null != commonOrder.getPrice()){
							payAmount = MoneyUtil.cent2Yuan(commonOrder.getPrice());
						}
						model.addObject("payAmount", payAmount);
						model.addObject("orderId", commonOrder.getOrderId());
						model.addObject("orderStatus", commonOrder.getStatus());
						model.addObject("productNature", "");
						if(order.getPayType() != null){
							model.addObject("payType", commonOrder.getPayType().getDescription());
						}
						model.setViewName("order/weixin/orderPay/paysuccessed");
					}else if(commonOrder.getStatus().equals(CommonOrderStatus.UNPAY)){
						orderPayVo = orderPayReuseService.buildOrderPayVoByOrdeAndCommonOrder(order, commonOrder,memberClientService.getMemberIdByMid(order.getCreator()));
						if(PaymentTestArgs.payAmount !=0l){
							orderPayVo.setPayAmount(2l);
						}
						OrderPayBean orderPayBean = new OrderPayBean();
						if(orderPayVo != null){
							orderPayBean = Convert2OrderPayBean.buildOrderPayBeanByOrderPayVo(orderPayVo);
						}

						if (orderPayBean.getMemberId().equals(orderPayVo.getMemberId())) {
							checkSum = WeixinSignUtil.sign(getParams(orderPayBean)) ;
						} else {
							logger.info("======跳转到选择支付方式页面失败!订单{}支付人不是订单创建人", orderId);
							throw new Exception();
						}
						model.addObject("checkSum", checkSum);
						model.addObject("orderPayVo", orderPayVo);
						model.addObject("openId", openId) ;
						logger.info("=======订单{}跳转选择支付页面成功=======",orderId);
						model.setViewName("order/weixin/orderPay/selectpaytype");
					}
				}else if(null != order && null == commonOrder){//主订单
					String orderStatus = order.getFrontState();
					if(orderStatus.equals(ZtOrderStatus.PAYED.getCode())){
						model.addObject("realOrderId", orderId);
						String payAmount ="";
						if(null != order.getPayAmount()){
							payAmount = MoneyUtil.cent2Yuan(order.getPayAmount());
						}
						model.addObject("payAmount", payAmount);
						model.addObject("productNature", order.getProductNature());
						model.addObject("orderId",order.getOrderNo());
						model.addObject("orderStatus", orderStatus);
						if(order.getPayType() != null){
							model.addObject("payType", PaymentType.valueOf(order.getPayType()).getDescription());
						}
						model.setViewName("order/weixin/orderPay/paysuccessed");
					}else if(orderStatus.equals(ZtOrderStatus.UNPAY.getCode())){
						orderPayVo = orderPayReuseService.buildOrderPayVoByOrderId(order,memberClientService.getMemberIdByMid(order.getCreator()));
						if(PaymentTestArgs.payAmount !=0l){
							orderPayVo.setPayAmount(2l);
						}
						if(order != null){
							long countDown = getCountDown(order);
							model.addObject("countDown",countDown);
						}
						OrderPayBean orderPayBean = new OrderPayBean();
						if(orderPayVo != null){
							orderPayBean = Convert2OrderPayBean.buildOrderPayBeanByOrderPayVo(orderPayVo);
						}
						if (orderPayBean.getMemberId().equals(orderPayVo.getMemberId())) {
							checkSum = WeixinSignUtil.sign(getParams(orderPayBean)) ;
						} else {
							logger.info("======跳转到选择支付方式页面失败!订单{}支付人不是订单创建人", orderId);
							throw new Exception();
						}
						model.addObject("checkSum", checkSum);
						model.addObject("orderPayVo", orderPayVo);
						model.addObject("openId", openId) ;
						logger.info("=======订单{}跳转选择支付页面成功=======",orderId);
						model.setViewName("order/weixin/orderPay/selectpaytype");
					}
				}else if(null == order && null == commonOrder){//订单不存在
					logger.info("订单{}不存在",orderId);
					model.setViewName("redirect:"+wxServer+"/order/weixin/noOrder");
				}
			}else{
				logger.info("微信订单Id为空，跳转到无订单页面");
				model.setViewName("redirect:"+wxServer+"/order/weixin/noOrder");
			}
		} catch (Exception e) {
			logger.info("======跳转到选择支付方式页面失败！=====:", e);
			throw new Exception(e);
		}
		return model;
	}
	
	
	
	private void saveOpenId(String orderId, String openId) throws Exception{
		String openIdDb = orderOpenIdReuseService.getOpenIdByOrderId(orderId) ;
		if(StringUtils.isEmpty(openIdDb)){
			if(StringUtils.isNotEmpty(openId)){
				OrderOpenId orderOpenId = new OrderOpenId();
				orderOpenId.setOrderId(orderId);
				orderOpenId.setOpenId(openId);
				orderOpenIdDaoImpl.insert(orderOpenId);
			}
			
		}
	}

	private Map<String, String> getParams(OrderPayBean orderPayBean){
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("memberId", orderPayBean.getMemberId()) ;
		params.put("orderId", orderPayBean.getOrderId()) ;
		params.put("orderAmount", orderPayBean.getOrderAmount() + "") ;
		params.put("useRewardPoint", orderPayBean.getUseRewardPoint() + "") ;
		params.put("useCoupon", orderPayBean.getUseCoupon() + "") ;
		params.put("couponItemId", orderPayBean.getCouponItemId() + "") ;
		params.put("payAmount", orderPayBean.getPayAmount() + "") ;
		return params ;
	}

	private long getCountDown(Order order) throws Exception{
		logger.info("获取倒计时"+TZBeanUtils.describe(order));
		long countDown = 0l;
		Date createDate  = order.getCreateDate();
		Date now = new Date();
		long offTime  = createDate.getTime() + 30*60*1000;
		if(offTime > now.getTime()){
			countDown = offTime - now.getTime();
		}
		logger.info("获取倒计时 countDown={} ",countDown);
		return countDown;
	}

	@RequestMapping("/jumpToWechatPay")
	@ResponseBody
	public Map<String, Object> jumpToPay(OrderPayFormBean orderPayFormBean,
			HttpServletRequest request) {
		logger.info("===========微信支付跳转第三方支付页面开始==============="+TZBeanUtils.describe(orderPayFormBean));
		Map<String, Object> map = Maps.newHashMap();
		Boolean verfyChecksumResult = true;
		WxPaymentResponse paymentResponse = new WxPaymentResponse();
		try {

			OrderPayBean orderPayBean = Convert2OrderPayBean
					.convert2OrderPayBean(orderPayFormBean, request);
			CommonResponse resCheck = checkPay(orderPayFormBean, request) ;
			paymentResponse.setSuccess(resCheck.isSuccess());
			paymentResponse.setErrMsg(resCheck.getErrMsg());
			if(!paymentResponse.isSuccess()){
				map.put("paymentResponse", paymentResponse) ;
				return map ;
			}

			// 校验checkSum
			logger.info("orderPay verfyChecksumResult start .."+TZBeanUtils.describe(orderPayBean));
			verfyChecksumResult = WeixinSignUtil.verify(
					getParams(orderPayBean), orderPayFormBean.getCheckSum());

			if (verfyChecksumResult) {
				logger.info("==verfyChecksumResult is true");
				logger.info("orderPay placeOrder start.. "+TZBeanUtils.describe(orderPayBean));
				orderPayBean.setTradeType("JSAPI");
				String openId = orderOpenIdReuseService.getOpenIdByOrderId(orderPayFormBean.getOrderId()) ;
				logger.info("==get openId:::{}", openId);
				orderPayBean.setOpenId(openId);
				paymentResponse = orderPaymentService.placeWxOrder(orderPayBean);
				logger.info("orderPay placeOrder end .. "+TZBeanUtils.describe(paymentResponse));
				if(null != paymentResponse && paymentResponse.isSuccess() && StringUtils.isNotEmpty(paymentResponse.getValue())){
					String orderId = orderPayFormBean.getOrderId();
					if(StringUtils.isNotEmpty(orderId)){
						setOrderIsToPayTrue(orderId);
					}
				}else{
					paymentResponse.setErrMsg("微信支付打了个盹，请选择其他支付方式");
					paymentResponse.setSuccess(false);
					map.put("paymentResponse", paymentResponse) ;
					return map;
				}
			}else{
				logger.info("=======订单跳转支付页面失败======  :校验verifyCheckSum 不匹配");
				paymentResponse.setErrMsg("订单跳转支付页面失败");
				paymentResponse.setSuccess(false);
				map.put("paymentResponse", paymentResponse) ;
				return map ;
			}
		} catch (Exception e) {
			logger.info("=======订单跳转支付页面失败======  :" + e);
			paymentResponse.setErrMsg("订单跳转支付页面失败!");
			paymentResponse.setSuccess(false);
			map.put("paymentResponse", paymentResponse) ;
			return map ;
		}
		String timeStamp = Integer.parseInt(new Date().getTime()/1000 + "") + "" ;
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "") ;
		String paySign = "" ;
		map.put("paymentResponse", paymentResponse) ;
		if(paymentResponse.isSuccess()){
			Map<String, String> params = new HashMap<String, String>() ;
			params.put("appId", WechatAccountHolder.APP_ID) ;
			params.put("timeStamp", timeStamp) ;
			params.put("nonceStr", nonceStr) ;
			params.put("signType", "MD5") ;
			params.put("package", "prepay_id=" + paymentResponse.getValue()) ;
			paySign = WeixinSignUtil.sign(params) ;
			map.put("appId", WechatAccountHolder.APP_ID) ;
			map.put("timeStamp", timeStamp) ;
			map.put("nonceStr", nonceStr) ;
			map.put("signType", "MD5") ;
			map.put("pkg", "prepay_id=" + paymentResponse.getValue()) ;
			map.put("paySign", paySign) ;
		}
		logger.info("===========跳转第三方支付页面结束===============");
		return map;
	}

	private CommonResponse checkPay(OrderPayFormBean orderPayFormBean, HttpServletRequest request) throws Exception{
		CommonResponse paymentResponse = new CommonResponse() ;
		paymentResponse.setSuccess(true);

		// 判断订单在支付系统是否已支付成功
		OrderPayInfoBean orderPayInfoBean = orderPaymentService
				.getOrderPayInfo(orderPayFormBean.getOrderNo());
		logger.info("========orderPayInfoBean======="+TZBeanUtils.describe(orderPayInfoBean));
		Order order = orderReuseService.getOrderById(orderPayFormBean.getOrderId());

		if(order != null){
			if(!order.getFrontState().equals(ZtOrderStatus.UNPAY.getCode())){
				logger.info("=======订单{}已支付或已取消======  :", orderPayFormBean.getOrderId());
				paymentResponse.setErrMsg("订单已支付或已取消!");
				paymentResponse.setSuccess(false);
				return paymentResponse ;
			}
		}else{
			CommonOrder commonOrder = commonOrderReuseService.selectByOrderId(orderPayFormBean.getOrderId());
			if(commonOrder != null){
				if(!commonOrder.getStatus().equals(CommonOrderStatus.UNPAY)){
					logger.info("=======订单{}已支付或已取消======  :", orderPayFormBean.getOrderId());
					paymentResponse.setErrMsg("订单已支付或已取消!");
					paymentResponse.setSuccess(false);
					return paymentResponse ;
				}
			}
		}

		if (null != orderPayInfoBean ) {
			OrderPayStatus orderPayStatus = orderPayInfoBean.getOrderPayStatus();
			if (!orderPayStatus.equals(OrderPayStatus.AWAIT)) {
				logger.info("=======订单{}在payment已支付或已取消======  :", orderPayFormBean.getOrderId());
				paymentResponse.setErrMsg("订单在支付系统中已支付或已取消!");
				paymentResponse.setSuccess(false);
				return paymentResponse ;
			}
		}
		return paymentResponse ;
	}

	@RequestMapping("/jumpToAliPay")
	@ResponseBody
	public Map<String, Object> jumpToAliPay(OrderPayFormBean orderPayFormBean,
			HttpServletRequest request) {
		logger.info("===========支付宝跳转第三方支付页面开始==============="+TZBeanUtils.describe(orderPayFormBean));
		Map<String, Object> map = Maps.newHashMap();
		Boolean verfyChecksumResult = true;
		PaymentResponse paymentResponse = new PaymentResponse();
		try {
			OrderPayBean orderPayBean = Convert2OrderPayBean
					.convert2OrderPayBean(orderPayFormBean, request);
			CommonResponse resCheck = checkPay(orderPayFormBean, request) ;
			paymentResponse.setSuccess(resCheck.isSuccess());
			paymentResponse.setErrMsg(resCheck.getErrMsg());
			if(!paymentResponse.isSuccess()){
				map.put("paymentResponse", paymentResponse) ;
				return map ;
			}

			orderPayBean.setFgNotifyUrl(wxServer + "/weixin/orderPay/payResult");
			// 校验checkSum
			logger.info("orderPay verfyChecksumResult start .."+TZBeanUtils.describe(orderPayBean));
			verfyChecksumResult = WeixinSignUtil.verify(
					getParams(orderPayBean), orderPayFormBean.getCheckSum());

			if (verfyChecksumResult) {
				logger.info("==verfyChecksumResult is true");
				logger.info("orderPay placeOrder start.. "+TZBeanUtils.describe(orderPayBean));
				orderPayBean.setProductType(orderPayFormBean.getProductType());
				paymentResponse = orderPaymentService.placeOrder(orderPayBean);
				logger.info("orderPay placeOrder end .. "+TZBeanUtils.describe(paymentResponse));
				if(null != paymentResponse && paymentResponse.isSuccess() && StringUtils.isNotEmpty(paymentResponse.getPaymentUrl())){
					String orderId = orderPayFormBean.getOrderId();
					if(StringUtils.isNotEmpty(orderId)){
						setOrderIsToPayTrue(orderId);
					}
				}
			}else{
				logger.info("=======订单跳转支付页面失败======  :校验verifyCheckSum 不匹配");
				paymentResponse.setErrMsg("订单跳转支付页面失败");
				paymentResponse.setSuccess(false);
				map.put("paymentResponse", paymentResponse) ;
				return map ;
			}
		} catch (Exception e) {
			logger.info("=======订单跳转支付页面失败======  :" + e);
			paymentResponse.setErrMsg("订单跳转支付页面失败!");
			paymentResponse.setSuccess(false);
			map.put("paymentResponse", paymentResponse) ;
			return map ;
		}
		map.put("paymentResponse", paymentResponse) ;
		logger.info("===========支付宝跳转第三方支付页面结束===============");
		return map;
	}

	public void setOrderIsToPayTrue(String orderId) {
		try{
			if(orderId.indexOf("-") == -1){
				wxOrderServiceImpl.updateOrderIsToPay(true, orderId);
			}
		}catch(Exception e){
			logger.error("设置{}订单isToPay失败", orderId, e);
		}
	}

	@RequestMapping(value = "/closeOrder")
	public void closeOrder(String orderId) {
		logger.info("close wx order start:{}", orderId);
		orderPaymentService.closeWxOrder(orderId);
		logger.info("close wx order end:{}", orderId);
	}

	@RequestMapping(value = "/payResult", method = RequestMethod.POST)
	public ModelAndView payResult(@ModelAttribute OrderPayResultFormBean orderPayResultFormBean)throws Exception {
		logger.info("==订单在第三方支付成功返回=="+TZBeanUtils.describe(orderPayResultFormBean));
		ModelAndView model = new ModelAndView();
		Order order = new Order();
		String orderStatus = null;
		String payAmount = null;
		String payType = null;

		CommonOrder commonOrder = commonOrderReuseService.selectByOrderId(orderPayResultFormBean.getOrderNum());
		if(commonOrder != null){
			String orderNoOrigin = commonOrder.getOrderNoOrigin();
			order = orderReuseService.getOrderByNo(orderNoOrigin);
			if(commonOrder.getPrice() != null){
				payAmount = MoneyUtil.cent2Yuan(commonOrder.getPrice());
			}
			if(commonOrder.getStatus() != null){
				orderStatus = commonOrder.getStatus().toString();
			}
			
			if(commonOrder.getType() != null){
				payType = commonOrder.getPayType().getDescription();
			}
			model.addObject("productNature", "");
		}else{
			order = orderReuseService.getOrderByNo(orderPayResultFormBean.getOrderNum());
			orderStatus = order.getFrontState();
			if(null != order.getPayAmount()){
				payAmount = MoneyUtil.cent2Yuan(order.getPayAmount());
			}
			try{
				payType = PaymentType.valueOf(order.getPayType()).getDescription();
			}catch(Exception e){
				logger.error("order.getPayType() not define...", e);
			}
			model.addObject("productNature", order.getProductNature());
		}

		String orderCreator = memberClientService.getMemberIdByMid(order.getCreator());
		if (StringUtils.isEmpty(SSOUtil.getMemberId())) {
			logger.info("====跳转到微信列表页==");
			return new ModelAndView("redirect:"+wxServer+"/weixin/product/list");
		}else if(StringUtils.isNotEmpty(SSOUtil.getMemberId()) && !SSOUtil.getMemberId().equals(orderCreator)){
			throw new Exception();
		}

		model.addObject("realOrderId", order.getOrderId());

		model.addObject("payAmount", payAmount);
		model.addObject("orderId",orderPayResultFormBean.getOrderNum());
		model.addObject("orderStatus", orderStatus);
		model.addObject("payType", payType);
	
		model.setViewName("order/weixin/orderPay/paysuccessed");
		return model;
	}
	
	@RequestMapping(value = "/blank")
	public ModelAndView blank(String url) throws Exception {
		String decodeURL = UriEncoder.decode(url) ;
		String param = UriEncoder.encode(decodeURL.split("[?]")[1]) ;
		String path = UriEncoder.encode(decodeURL.split("[?]")[0]) ;
		Map<String, String> params = Maps.newHashMap();
		params.put("url", path + "?" + param) ;
		return new ModelAndView("order/weixin/orderPay/blank", params);
	}

	@RequestMapping(value = "/payfailed")
	public ModelAndView payfailed(String orderId) throws Exception {
		Map<String, String> params = Maps.newHashMap();
		params.put("orderId", orderId) ;
		return new ModelAndView("order/weixin/orderPay/payfailed", params);
	}


}
