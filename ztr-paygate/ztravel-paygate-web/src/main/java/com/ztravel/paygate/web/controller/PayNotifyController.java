package com.ztravel.paygate.web.controller;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.thrift.model.ValSignResponse;
import com.ztravel.paygate.web.dto.middlebean.ProcessResponseResult;
import com.ztravel.paygate.web.dto.middlebean.ResponseConfirmBean;
import com.ztravel.paygate.web.dto.response.BankPayResponse;
import com.ztravel.paygate.web.dto.result.PayResult;
import com.ztravel.paygate.web.processor.RequestProcessor;
import com.ztravel.paygate.web.service.IPaygateClientService;
import com.ztravel.paygate.web.util.EnvArgs.Const;
import com.ztravel.paygate.web.util.HttpUtil;

/**
 * 支付结果通知的回调
 * 
 * @see AlipayPayNotifyController
 * @see TenpayPayNotifyController
 * @see ChinapnrPayNotifyController
 * @author dingguangxian
 * 
 */
public abstract class PayNotifyController {

	@Resource
	protected IPaygateClientService paygate_client_service;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected GateType gateType;

	public PayNotifyController(GateType gateType) {
		this.gateType = gateType;
	}

	@RequestMapping("/payResult/{fg}/{entityId}")
	public String payResult(@PathVariable("fg") String fgFlag, @PathVariable("entityId") String entityId, HttpServletRequest req,
			HttpServletResponse res, Model model) {
		logger.info("fgFlag is {}", fgFlag);
		BankPayResponse bankPayResponse = new BankPayResponse();
		if("m".equals(fgFlag)){
			bankPayResponse.setMobilePay(true);
			bankPayResponse.setFgNotify(false);
		} else if("0".equals(fgFlag)){
			bankPayResponse.setFgNotify(false);
		}
		bankPayResponse.setRequest(req);
		boolean isFgNotify = bankPayResponse.isFgNotify();
		try {
			req.setCharacterEncoding("UTF-8");
			// 从银行返回的信息记录进map
			Map<String, String> formData = extractResponseData(isFgNotify, req);
			bankPayResponse.setFormData(formData);
			logger.info("银行返回的支付结果" + formData);
			// 请求处理银行支付结果
			ProcessResponseResult processResult = new ProcessResponseResult();
			processBankResponse(processResult, entityId, bankPayResponse);
			// if(StringUtils.isBlank(processResult.getFgNotifyUrl())){
			// processResult.setFgNotifyUrl(fgUrl);
			// }
			logger.info("支付结果的处理结果:" + TZBeanUtils.describe(processResult.getPayResult()));
			String responseToBank = processResult.getAckContent();
			if (!isFgNotify && StringUtils.isNotBlank(responseToBank)) {
				// 需要给银行应答
				res.setHeader("HTTP/1.1", "200 OK");
				res.setHeader("Server", "Apache/1.39");
				res.setContentLength(responseToBank.length());
				PrintWriter out = res.getWriter();
				out.write(responseToBank);
				out.close();
			}
			if (isFgNotify) {// 前台通知
				PayResult result = processResult.getPayResult();
				model.addAttribute("fgNotifyUrl", processResult.getFgNotifyUrl());
				model.addAttribute("resultBean", result);
				return "/payment/jumpToFgNotify";
			} else {
				return null;
			}
		} catch (Throwable thr) {
			logger.error("", thr);
			if (isFgNotify) {
				// 前台通知
				PayResult result = new PayResult();
				processException(result, thr);
				model.addAttribute("fgNotifyUrl", null);
				model.addAttribute("resultBean", result);
				return "/payment/jumpToFgNotify";
			}
			// throw BizException.instance(ReturnCode.ERROR, "未知错误", thr);
			return null;
		}
	}

	private void processException(PayResult model, Throwable ex) {
		if (ex instanceof PaygateException) {
			model.setRetCode(((PaygateException) ex).getError().code());
			model.setRetMsg(((PaygateException) ex).getError().desc());
		} else {
			model.setRetCode(PaygateError.E100_ERROR.code());
			model.setRetMsg(PaygateError.E100_ERROR.desc());
		}
	}

	protected Map<String, String> extractResponseData(boolean isFgNotify, HttpServletRequest req) {
		Map<String, String> formData = new HashMap<String, String>();
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			if ("fg".equals(name)) {// 用于标识是前台通知
				continue;
			}
			formData.put(name, req.getParameter(name));
		}
		return formData;
	}

	/**
	 * 
	 * @return
	 */
	public abstract RequestProcessor getRequestProcessor();

	/**
	 * 处理银行反馈的结果
	 * 
	 * @param result
	 * @param entityId
	 * @param response
	 * @return
	 */
	public void processBankResponse(ProcessResponseResult result, String entityId, BankPayResponse response) {
		logger.info("验证该支付结果是否有效");
		// 1. 从数据库中获取该笔交易信息
		ResponseConfirmBean bean = buildConfirmBeanFromResponse(entityId, response);
		PayResult payResult = buildPayResult(response, bean);
		result.setPayResult(payResult);
		if(bean == null){
			return;
		}
		result.setFgNotifyUrl(bean.getFgNotifyUrl());
		result.setAckContent(bean.getAckContent());
		if(bean.isProcessed()){
			return;
		}
		// 3. 将支付结果反馈给调用端
		if (!response.isFgNotify() && PaygateError.SUCCESS.code().equals(payResult.getRetCode())) {
			// 后台,
			// 3.1 把结果反馈给终端
			String clientConfirmResult = sendResponseToClient(bean, payResult);
			if (clientConfirmResult != null) {
				clientConfirmResult = clientConfirmResult.trim();
			}
			logger.info("终端返回信息:{}", clientConfirmResult);
			if (!Const.CLIENT_CONFIRM_SUCCESS_FLAG.equalsIgnoreCase(clientConfirmResult)) {
				logger.info("终端确认信息有异常，暂不发送成功标记.");
				// 客户端确认失败，先不发送信息给支付平台
				processClientConfirmFail(result, bean);
			}
			if (clientConfirmResult.length() > 6) {
				logger.warn("终端确认信息过长，截断...");
				clientConfirmResult = clientConfirmResult.substring(0, 4) + "..";
			}
			recordConfirmResult(bean, clientConfirmResult);
		}
	}

	protected void processClientConfirmFail(ProcessResponseResult result, ResponseConfirmBean bean) {
		result.setAckContent("");
		bean.setAckContent("");
	}

	protected PayResult buildPayResult(BankPayResponse response, ResponseConfirmBean bean) {
		PayResult payResult = new PayResult();
		if(response.isMobilePay()){
			payResult.setService(PayResult.SERVICE_MOBILE);
		}
		if (bean == null) {
			logger.warn("接收到的结果无效，数据库中找不到对应的记录, 丢弃.{}", response.getFormData());
			// 前台跳转时，将会输出这些信息
			payResult.setRetCode(PaygateError.E209_INVALID_RESPONSE.code());
			payResult.setRetMsg(PaygateError.E209_INVALID_RESPONSE.desc());
			return payResult;
		}
		payResult.setPayState(bean.getPayState());
		payResult.setClientId(bean.getClientId());
		payResult.setOrderNum(bean.getOrderNum());
		payResult.setAmount(bean.getAmount());
		Date paymentTime = bean.getBankPaymentTime();
		if (paymentTime != null) {
			payResult.setBankPaymentTime(new DateTime(paymentTime).toString("yyyy-MM-dd HH:mm:ss"));
		}
		payResult.setGateType(gateType.code);
		if (bean.isProcessed()) {// 结果已经被正确处理过
			logger.warn("接收到已经被正确处理过的结果.{}", response.getFormData());
			// 前台跳转时，将会输出这些信息
			payResult.setRetCode(PaygateError.SUCCESS.code());
			payResult.setRetMsg(PaygateError.SUCCESS.desc());
			return payResult;
		}
		// 2. 验证是否为有效的返回
		ValSignResponse valSignResponse = getRequestProcessor().payValSign(bean, response);
		validateAndprocessValSignResult(valSignResponse, bean);

		payResult.setRetCode(valSignResponse.getRetCode());
		payResult.setRetMsg(valSignResponse.getRetMsg());
		if (!PaygateError.SUCCESS.code().equals(valSignResponse.getRetCode())) {
			logger.warn("验签结果无效，暂不发送成功标记.");
			bean.setAckContent("");
			// 验签失败，无效请求.
			return payResult;
		}
		payResult.setTraceNum(valSignResponse.getTraceNum());
		payResult.setPayState(bean.getPayState());
		return payResult;
	}
	
	/**
	 * 通过银行的返回结果信息，检索数据库，获取原始的请求信息
	 * 
	 * @param entityId
	 * @param response
	 * @return
	 */
	protected abstract ResponseConfirmBean buildConfirmBeanFromResponse(String entityId, BankPayResponse response);

	/**
	 * 验签结果与原始请求信息进行校验，并将结果记录在数据库中
	 * 
	 * @param valSignResponse
	 */
	protected void validateAndprocessValSignResult(ValSignResponse valSignResponse, ResponseConfirmBean confirmBean) {
		//logger.info("valSignResponse: {}", TZBeanUtils.describe(valSignResponse));
		//logger.info("confirmBean: {}", TZBeanUtils.describe(confirmBean));
		if (PaygateError.SUCCESS.code().equals(valSignResponse.getRetCode()) && valSignResponse.getAmount() != confirmBean.getAmount()) {
			valSignResponse.setRetCode(PaygateError.E210_AMOUNT_NOT_MATCHED.code());
			valSignResponse.setRetMsg(PaygateError.E210_AMOUNT_NOT_MATCHED.desc());
		}
		if (!PaygateError.SUCCESS.code().equals(valSignResponse.getRetCode())) {
			// 验签失败
			confirmBean.setPayState(null);
		}
		processValSignResult(valSignResponse, confirmBean);
	}

	protected abstract void processValSignResult(ValSignResponse valSignResponse, ResponseConfirmBean confirmBean);

	/**
	 * 
	 * @param confirmBean
	 * @param payResult
	 * @return
	 */
	private String sendResponseToClient(ResponseConfirmBean confirmBean, PayResult payResult) {
		PaygateClient client = null;
		try {
			client = paygate_client_service.findByClientId(confirmBean.getClientId());
		} catch (Exception e1) {
			logger.error("", e1);
			throw new RuntimeException(e1);
		}
		if (client == null) {
			throw new PaygateException(PaygateError.E204_NULL_CLIENTID);
		}
		String notifyUrl = client.getPaymentNotifyUrl();
		logger.info("后台通知地址:" + notifyUrl);
		if (StringUtils.isBlank(notifyUrl)) {
			logger.error("后台通知地址不正确.{}.{}", confirmBean.getClientId(), confirmBean.getPayType());
			throw new PaygateException(PaygateError.E311_INVALID_NOTIFY_URL);
		}
		Map<String, String> params = BeanMapUtil.mapBean(payResult);
		params.put("sign", PaygateEncryptUtil.getSignStr(params, client.getSignKey()));
		logger.info("通知给调用端的信息:{}", params);
		try {
			return HttpUtil.doFormPost(new URL(notifyUrl), params);
		} catch (Exception e) {
			logger.error("支付结果通知给调用端出现异常.", e);
			return "E";
		}
	}

	protected abstract void recordConfirmResult(ResponseConfirmBean confirmBean, String clientConfirmResult);
}
