package com.ztravel.order.front.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.enums.OrderPayStatus;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.OrderPayInfoBean;
import com.ztravel.common.payment.PaymentResponse;
import com.ztravel.common.payment.PaymentTestArgs;
import com.ztravel.common.payment.WxPaymentResponse;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.order.front.service.IOrderProductService;
import com.ztravel.order.front.service.IOrderService;
import com.ztravel.order.front.vo.OrderPayResult;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.payment.service.IOrderPaymentService;
import com.ztravel.reuse.order.converter.Convert2OrderPayBean;
import com.ztravel.reuse.order.entity.OrderPayFormBean;
import com.ztravel.reuse.order.entity.OrderPayResultFormBean;
import com.ztravel.reuse.order.entity.OrderPayVo;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
import com.ztravel.reuse.order.service.IOrderPayReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;

@Controller
@RequestMapping("/orderPay")
public class OrderPayController {
	private static Logger logger = RequestIdentityLogger.getLogger(OrderPayController.class);

	private static final String memberServer = WebEnv.get("server.path.memberServer", "");

	@Resource
	IOrderPaymentService orderPaymentService;

	@Resource
	IOrderService orderService;
	@Resource
	IOrderProductService orderProductService;
	@Resource
	IOrderPayReuseService orderPayReuseService;

	@Resource
	IMemberClientService memberClientService;
	
	@Resource
	private IOrderReuseService orderReuseService;
	
	@Resource
	private ICommonOrderReuseService commonOrderReuseService;

	
	@RequestMapping(value = "/selectPayType/{orderId}")
	public ModelAndView selectPayType(@PathVariable String orderId, HttpServletRequest request,ModelAndView model) throws Exception {
		logger.info("订单{}跳转选择支付页面开始",orderId);
		OrderPayVo orderPayVo = new OrderPayVo();
		OrderPayResult orderPayResult = new OrderPayResult();
		try {
			String currentMemberId = SSOUtil.getMemberId();
			if(StringUtils.isEmpty(currentMemberId)){
				return new ModelAndView("redirect:"+memberServer+"/home");
			}else{
				if(StringUtils.isNotEmpty(orderId)){
					String checkSum = "";
					Order order = orderReuseService.getOrderById(orderId);
					CommonOrder commonOrder = commonOrderReuseService.selectByOrderId(orderId);
					if(null == order && null != commonOrder){//补款单
						order = orderReuseService.getOrderById(commonOrder.getOrderIdOrigin());
						if(commonOrder.getStatus().equals(CommonOrderStatus.PAID)){
							logger.info("补款订单已支付跳转支付成功页面", TZBeanUtils.describe(commonOrder));
							String payAmount ="";
							if(null != commonOrder.getPrice()){
								payAmount = MoneyUtil.cent2Yuan(commonOrder.getPrice());
							}
							orderPayResult.setOrderStatus(CommonOrderStatus.PAID.toString());
							orderPayResult.setOrderId(commonOrder.getOrderId());
							orderPayResult.setPayAmount(payAmount);
							orderPayResult.setRealOrderId(order.getOrderId());
							model.addObject("orderPayResult", orderPayResult);
							model.setViewName("order/front/orderPay/paysuccessed");
							return model;
						}else if(commonOrder.getStatus().equals(CommonOrderStatus.UNPAY)){
							logger.info("补款订单跳转选择支付页面",TZBeanUtils.describe(commonOrder));
							orderPayVo = orderPayReuseService.buildOrderPayVoByOrdeAndCommonOrder(order, commonOrder,memberClientService.getMemberIdByMid(order.getCreator()));
							OrderPayBean orderPayBean = buildOrderPayBean(orderPayVo);
							if(currentMemberId.equals(orderPayVo.getMemberId())) {
								checkSum = orderPaymentService.reqChecksum(orderPayBean);
								model.addObject("checkSum", checkSum);
								model.addObject("orderPayVo", orderPayVo);
								model.setViewName("order/front/orderPay/selectpaytype");
								logger.info("=======补款订单{}跳转选择支付页面成功=======", commonOrder.getOrderId());
							} else {
								logger.info("======跳转到选择支付方式页面失败!补款订单{}支付人不是订单创建人", orderId);
								throw new RuntimeException("补款订单{}支付人不是订单创建人");
							}
						}
					}else if(null != order && null == commonOrder){//主订单
						String orderStatus = order.getFrontState();
						if(orderStatus.equals(ZtOrderStatus.PAYED.getCode())){
							logger.info("主订单{}已支付跳转支付成功页面", TZBeanUtils.describe(order));
							String payAmount = "";
							if(null != order.getPayAmount()){
								payAmount = MoneyUtil.cent2Yuan(order.getPayAmount());
							}
							orderPayResult.setProductNature(order.getProductNature());
							orderPayResult.setPayAmount(payAmount);
							orderPayResult.setOrderStatus(ZtOrderStatus.PAYED.toString());
							orderPayResult.setOrderId(order.getOrderNo());
							orderPayResult.setRealOrderId(order.getOrderId());
							model.addObject("orderPayResult", orderPayResult);
							model.setViewName("order/front/orderPay/paysuccessed");
						}else if(orderStatus.equals(ZtOrderStatus.UNPAY.getCode())){
							logger.info("主订单跳转选择支付页面",TZBeanUtils.describe(order));
							orderPayVo = orderPayReuseService.buildOrderPayVoByOrderId(order,memberClientService.getMemberIdByMid(order.getCreator()));
							OrderPayBean orderPayBean = buildOrderPayBean(orderPayVo);
							if(currentMemberId.equals(orderPayVo.getMemberId())) {
								checkSum = orderPaymentService.reqChecksum(orderPayBean);
								model.addObject("checkSum", checkSum);
								model.addObject("orderPayVo", orderPayVo);
								model.setViewName("order/front/orderPay/selectpaytype");
								logger.info("=======主订单{}跳转选择支付页面成功=======", order.getOrderId());
							} else {
								logger.info("======跳转到选择支付方式页面失败!主订单{}支付人不是订单创建人", orderId);
								throw new RuntimeException("主订单{}支付人不是订单创建人");
							}
						}
					}else if(null == order && null == commonOrder){//订单不存在
						logger.info("订单{}不存在",orderId);
						model.setViewName("order/front/orderPay/noOrder");
					}
				}else{
					logger.info("订单号为空，跳转至无订单页面==");
					model.setViewName("order/front/orderPay/noOrder");
				}
			}
		}catch (Exception e) {
			logger.info("======跳转到选择支付方式页面失败！=====:", e);
			throw new Exception(e);
		}
		return model;
	}
	
	
	

	private OrderPayBean buildOrderPayBean(OrderPayVo orderPayVo) throws Exception{
		if(PaymentTestArgs.payAmount !=0l){
			orderPayVo.setPayAmount(2l);
		}
		OrderPayBean orderPayBean = new OrderPayBean();
		if(orderPayVo != null){
			orderPayBean = Convert2OrderPayBean.buildOrderPayBeanByOrderPayVo(orderPayVo);
		}
		return orderPayBean;
	}

	@RequestMapping("/jumpToPay")
	@ResponseBody
	public Map<String, Object> jumpToPay(OrderPayFormBean orderPayFormBean, HttpServletRequest request) {
		logger.info("===========跳转第三方支付页面开始==============="+TZBeanUtils.describe(orderPayFormBean));
		Map<String, Object> map = Maps.newHashMap();
		Boolean verfyChecksumResult = true;
		PaymentResponse paymentResponse = new PaymentResponse();
		try {

			OrderPayBean orderPayBean = Convert2OrderPayBean
					.convert2OrderPayBean(orderPayFormBean, request);
			logger.info("========orderPayBean======="+TZBeanUtils.describe(orderPayBean));
			if (null == orderPayBean || null == orderPayFormBean.getCheckSum()) {
				logger.info("======orderPayBean不能为空；checkeSum 不能为空======  :");
				paymentResponse.setErrMsg("orderPayBean不能为空；checkeSum 不能为空");
				paymentResponse.setSuccess(false);
				map.put("paymentResponse", paymentResponse);
				return map;
			}

			if(StringUtils.isNotEmpty(orderPayBean.getMemberId())){
				if(!memberIsActive(orderPayBean.getMemberId())){
					logger.info("========会员已挂起不可支付=======");
					paymentResponse.setErrMsg("会员已挂起");
					paymentResponse.setSuccess(false);
					map.put("paymentResponse", paymentResponse);
					return map;
				}
			}

			// 判断订单在支付系统是否已支付成功
			OrderPayInfoBean orderPayInfoBean = orderPaymentService
					.getOrderPayInfo(orderPayFormBean.getOrderNo());
			logger.info("========orderPayInfoBean======="+TZBeanUtils.describe(orderPayInfoBean));
			Order order = orderReuseService.getOrderById(orderPayFormBean.getOrderId());

			if(order != null){
				if(!order.getFrontState().equals(ZtOrderStatus.UNPAY.getCode())){
					logger.info("=======订单{}已支付或已取消======  :",
							orderPayFormBean.getOrderId());
					paymentResponse.setErrMsg("订单已支付或已取消!");
					paymentResponse.setSuccess(false);
					map.put("paymentResponse", paymentResponse);
					return map;
				}
			}else{
				CommonOrder commonOrder =  commonOrderReuseService.selectByOrderId(orderPayFormBean.getOrderId());
				if(commonOrder != null){
					logger.info("补款订单跳转支付页面", TZBeanUtils.describe(commonOrder));
					if(!commonOrder.getStatus().equals(CommonOrderStatus.UNPAY)){
						logger.info("=======补款订单{}已支付或已取消======  :",
								orderPayFormBean.getOrderId());
						paymentResponse.setErrMsg("补款订单已支付或已取消!");
						paymentResponse.setSuccess(false);
						map.put("paymentResponse", paymentResponse);
						return map;
					}
				}
			}

			if (null != orderPayInfoBean ) {
				OrderPayStatus orderPayStatus = orderPayInfoBean
						.getOrderPayStatus();
				if (!orderPayStatus.equals(OrderPayStatus.AWAIT)) {
					logger.info("=======订单{}在payment已支付或已取消======  :",
							orderPayFormBean.getOrderId());
					paymentResponse.setErrMsg("订单在支付系统中已支付或已取消!");
					paymentResponse.setSuccess(false);
					map.put("paymentResponse", paymentResponse);
					return map;
				}
			}
			// 校验checkSum
			logger.info("orderPay verfyChecksumResult start .."+TZBeanUtils.describe(orderPayBean));
			verfyChecksumResult = orderPaymentService.verifyChecksum(
					orderPayBean, orderPayFormBean.getCheckSum());

			if (verfyChecksumResult) {
				logger.info("==verfyChecksumResult is true");
				logger.info("orderPay placeOrder start.. "+TZBeanUtils.describe(orderPayBean));
				orderPayBean.setProductType(orderPayFormBean.getProductType());
				paymentResponse = orderPaymentService.placeOrder(orderPayBean);
				logger.info("orderPay placeOrder end .. "+TZBeanUtils.describe(paymentResponse));
				if(null != paymentResponse){
					if(paymentResponse.isSuccess() && StringUtils.isNotEmpty(paymentResponse.getPaymentUrl())){
						String orderId = orderPayFormBean.getOrderId();
						if(StringUtils.isNotEmpty(orderId)){
							setOrderIsToPayTrue(orderId);
						}
					}
				}
			}else{
				logger.info("=======订单跳转支付页面失败======  :校验verifyCheckSum 不匹配");
				paymentResponse.setErrMsg("订单跳转支付页面失败");
				paymentResponse.setSuccess(false);
				map.put("paymentResponse", paymentResponse);
			}
		} catch (Exception e) {
			logger.info("=======订单跳转支付页面失败======  :" + e);
			paymentResponse.setErrMsg("订单跳转支付页面失败!");
			paymentResponse.setSuccess(false);
			map.put("paymentResponse", paymentResponse);
		}
		map.put("paymentResponse", paymentResponse);
		logger.info("===========跳转第三方支付页面结束===============");
		return map;
	}

	public void setOrderIsToPayTrue(String orderId) {
		try{
			if(orderId.indexOf("-") == -1){
				Order order = orderReuseService.getOrderById(orderId);
				if(null != order){
					order.setIsToPay(true);
					orderReuseService.updateOrder(order);
				}
			}
		}catch(Exception e){
			logger.error("设置{}订单isToPay失败",e);
		}
	}

	private Boolean memberIsActive(String memberId) throws Exception{
		boolean result = true;
		String memberInfo = memberClientService.getMinMemberById(memberId);
		if(StringUtils.startsWith(memberInfo, "{")) {
			JSONObject json = JSONObject.parseObject(memberInfo);
			if(json.containsKey("isActive")) {
				String isActive = json.getString("isActive");
				if(isActive.equals("false")){
					result = false;
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/payResult", method = RequestMethod.POST)
	public ModelAndView payResult(@ModelAttribute OrderPayResultFormBean orderPayResultFormBean) throws Exception {
		logger.info("==订单在第三方支付成功返回=="+TZBeanUtils.describe(orderPayResultFormBean));
		ModelAndView model = new ModelAndView();
		OrderPayResult orderPayResult = new OrderPayResult();
		CommonOrder commonOrder = null;
		String payAmount="";
		String orderId="";
		String orderStatus="";
		String realOrderId="";
		String memberServer = WebEnv.get("server.path.memberServer", "");
		orderId = orderReuseService.getOrderIdByOrderNo(orderPayResultFormBean.getOrderNum());

		//从补款单表中查询订单id
		String orderNoOrigin = "";
		if(StringUtils.isEmpty(orderId)){
			commonOrder =  commonOrderReuseService.selectByOrderId(orderPayResultFormBean.getOrderNum());
			if(commonOrder != null){
				orderNoOrigin = commonOrder.getOrderNoOrigin();
				orderId = orderReuseService.getOrderIdByOrderNo(orderNoOrigin);
			}
		}

		Order order = orderReuseService.getOrderById(orderId);
		
		
		realOrderId = order.getOrderId();
		String orderCreator = memberClientService.getMemberIdByMid(order.getCreator());
		if (StringUtils.isEmpty(SSOUtil.getMemberId())) {
			logger.info("====跳转到首页==");
			return new ModelAndView("redirect:"+memberServer+"/home");
		}else if(StringUtils.isNotEmpty(SSOUtil.getMemberId()) && !SSOUtil.getMemberId().equals(orderCreator)){
			throw new Exception();
		}

		if(commonOrder != null){
			if(commonOrder.getStatus() != null){
				orderStatus = commonOrder.getStatus().toString();
			}
			if(commonOrder.getPrice() != null){
				payAmount = MoneyUtil.cent2Yuan(commonOrder.getPrice());
			}
		}else if(order != null){
			orderPayResult.setProductNature(order.getProductNature());
			orderStatus = order.getFrontState();
			if(null != order.getPayAmount()){
					payAmount = MoneyUtil.cent2Yuan(order.getPayAmount());
			}

			orderId = orderPayResultFormBean.getOrderNum();
		}

		orderPayResult.setOrderId( orderPayResultFormBean.getOrderNum());
		orderPayResult.setOrderStatus(orderStatus);
		orderPayResult.setRealOrderId(realOrderId);
		orderPayResult.setPayAmount(payAmount);
		model.addObject("orderPayResult", orderPayResult);
		
		model.setViewName("order/front/orderPay/paysuccessed");
		return model;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping("/jumpToWechatPay")
	@ResponseBody
	public Map<String, Object> jumpToWechatPay(OrderPayFormBean orderPayFormBean,
			HttpServletRequest request) {
		logger.info("===========web微信支付跳转第三方支付页面开始==============="+TZBeanUtils.describe(orderPayFormBean));
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
			verfyChecksumResult = orderPaymentService.verifyChecksum(
					orderPayBean, orderPayFormBean.getCheckSum());
			if (verfyChecksumResult) {
				logger.info("==verfyChecksumResult is true");
				logger.info("orderPay placeOrder start.. "+TZBeanUtils.describe(orderPayBean));
				orderPayBean.setTradeType("NATIVE");
				paymentResponse = orderPaymentService.placeWxOrder(orderPayBean);
				logger.info("orderPay placeOrder end .. "+TZBeanUtils.describe(paymentResponse));
				if(null != paymentResponse && paymentResponse.isSuccess() && StringUtils.isNotEmpty(paymentResponse.getValue())){
					String orderId = orderPayFormBean.getOrderId();
					if(StringUtils.isNotEmpty(orderId)){
						setOrderIsToPayTrue(orderId);
					}
					paymentResponse.setValue(URLEncoder.encode(paymentResponse.getValue()));
					map.put("paymentResponse", paymentResponse) ;
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
			CommonOrder commonOrder =  commonOrderReuseService.selectByOrderId(orderPayFormBean.getOrderId());
			if(commonOrder != null){
				if(!commonOrder.getStatus().equals(CommonOrderStatus.UNPAY)){
					logger.info("=======订单{}已支付或已取消======  :",
							orderPayFormBean.getOrderId());
					paymentResponse.setErrMsg("订单已支付或已取消!");
					paymentResponse.setSuccess(false);
					return paymentResponse;
				}
			}
		}

		if (null != orderPayInfoBean ) {
			OrderPayStatus orderPayStatus = orderPayInfoBean
					.getOrderPayStatus();
			if (!orderPayStatus.equals(OrderPayStatus.AWAIT)) {
				logger.info("=======订单{}在payment已支付或已取消======  :", orderPayFormBean.getOrderId());
				paymentResponse.setErrMsg("订单在支付系统中已支付或已取消!");
				paymentResponse.setSuccess(false);
				return paymentResponse ;
			}
		}
		return paymentResponse ;
	}


	@RequestMapping(value = "/toWechatScan")
	public String toWechatScan(String codeUrl, String payAmount, String orderNo, Model model) throws Exception{
		logger.info("toWechatScan codeUrl: {}, payAmount :{}, orderNo:{} ", codeUrl, payAmount, orderNo);

		if (StringUtils.isEmpty(SSOUtil.getMemberId())) {
			logger.info("====跳转到首页==");
			return "redirect:"+memberServer+"/home";
		}


		Order order = null;
		String tradeState = "";
		String orderId = orderReuseService.getOrderIdByOrderNo(orderNo);

		CommonOrder commonOrder = null;
		if(StringUtils.isEmpty(orderId)){
			commonOrder =  commonOrderReuseService.selectByOrderId(orderNo);
			if(commonOrder != null){
				model.addAttribute("productType", ProductType.OPCONFIRM);
			}
		}

		if(StringUtils.isNotEmpty(orderId)){
			order = orderReuseService.getOrderById(orderId);
		}

		if(null != payAmount){
			payAmount = MoneyUtil.cent2Yuan(payAmount);
		}

		tradeState = orderPaymentService.queryWxOrderTradeState(orderNo);
		if(tradeState.equals("SUCCESS")){
			logger.info("订单{}已支付成功,跳转到支付成功页面:",orderNo);
			//如果是补款单
			if(commonOrder != null){
				orderId = commonOrder.getOrderIdOrigin();
				order = orderReuseService.getOrderById(orderId);
				if(order != null){
					OrderPayResult orderPayResult = new OrderPayResult();
					orderPayResult.setRealOrderId(orderId);
					orderPayResult.setOrderStatus(commonOrder.getStatus().toString());
					orderPayResult.setPayAmount(MoneyUtil.cent2Yuan(commonOrder.getPrice()));
					orderPayResult.setOrderId(commonOrder.getOrderId());
					model.addAttribute("orderPayResult", orderPayResult);
				}
				
			}else{
				OrderPayResult orderPayResult = new OrderPayResult();
				String orderStatus = order.getFrontState();
				orderPayResult.setRealOrderId(order.getOrderId());
				orderPayResult.setOrderStatus(orderStatus);
				orderPayResult.setPayAmount(payAmount);
				orderPayResult.setOrderId(order.getOrderNo());
				model.addAttribute("orderPayResult", orderPayResult);
			}
			
			return "order/front/orderPay/paysuccessed";
		}
		if(order != null){
			long countDown = getCountDown(order);
			model.addAttribute("countDown", countDown);
		}


		model.addAttribute("codeUrl", codeUrl);
		model.addAttribute("payAmount", payAmount);
		model.addAttribute("orderNo", orderNo);
		return "order/front/orderPay/wechatScan";
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

	@RequestMapping("/queryWxOrderTradeState")
	@ResponseBody
	public Map<String,Object> queryWxOrderTradeState(String orderId) {
		logger.info("query wx order trade_state start:{}", orderId);
		Map<String, Object> map = Maps.newHashMap();
		String trade_state = "";
		try{
			trade_state = orderPaymentService.queryWxOrderTradeState(orderId);
		}catch(Exception e){
			logger.info(e.toString());
		}
		logger.info("query wx order trade_state end:{}", orderId);
		map.put("trade_state", trade_state);
		return map;
	}

}
