package com.ztravel.paygate.web.controller;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.thrift.model.RefundValSignResponse;
import com.ztravel.paygate.web.dto.middlebean.RefundResponseConfirmBean;
import com.ztravel.paygate.web.dto.result.RefundResult;
import com.ztravel.paygate.web.processor.RequestProcessor;
import com.ztravel.paygate.web.service.IPaygateClientService;
import com.ztravel.paygate.web.util.EnvArgs.Const;
import com.ztravel.paygate.web.util.HttpUtil;

/**
 * 退款结果通知的回调(支付宝、汇付天下、兴业银行)
 *
 * @see AlipayRefundNotifyController
 * @see ChinapnrRefundNotifyController
 * @see CibpayRefundNotifyController
 * @author dingguangxian
 *
 */
public abstract class RefundNotifyController {
	@Resource
	protected IPaygateClientService paygate_client_service;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected GateType gateType;
	protected String charset = "UTF-8";

	public RefundNotifyController(GateType gateType) {
		this.gateType = gateType;
	}

	@RequestMapping("/refundResult/{refundEntityId}")
	@ResponseBody
	public String refundResult(@PathVariable("refundEntityId") String refundEntityId, HttpServletRequest req) {
		try {
			req.setCharacterEncoding(charset);
			// 从银行返回的信息记录进map
			Map<String, String> responseData = extractResponseData(req);
			logger.info("退款::支付平台返回的退款结果" + responseData);
			// 处理退款结果
			return processRefundResponse(refundEntityId, responseData);
		} catch (Throwable thr) {
			logger.error("", thr);
			return "";
		}
	}

	protected Map<String, String> extractResponseData(HttpServletRequest req) {
		Map<String, String> formData = new HashMap<String, String>();
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
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
	 * @param entityId
	 * @param response
	 * @return
	 */
	protected String processRefundResponse(String entityId, Map<String, String> response) {
		RefundResult refundResult = new RefundResult();
		logger.info("验证该支付结果是否有效");
		// 1. 从数据库中获取该笔交易信息
		RefundResponseConfirmBean bean = buildConfirmBeanFromResponse(entityId, response);
		if (bean == null) {
			logger.warn("接收到的结果无效，数据库中找不到对应的记录, 丢弃.{}", response);
			// 前台跳转时，将会输出这些信息
			refundResult.setRetCode(PaygateError.E209_INVALID_RESPONSE.code());
			refundResult.setRetMsg(PaygateError.E209_INVALID_RESPONSE.desc());
			return "fail";
		}
		refundResult.setClientId(bean.getClientId());
		refundResult.setOrderNum(bean.getOrderNum());
		refundResult.setRefundNum(bean.getRefundNum());
		refundResult.setTraceNum(bean.getTraceNum());
		refundResult.setRefundState(bean.getRefundState());
		refundResult.setRefundAmount(bean.getRefundAmount());
		refundResult.setGateType(gateType.code);
		refundResult.setUnfreezeDetails(bean.getUnfreezeDetails());
		refundResult.setRefundShareDetails(bean.getRefundShareProfits());
		if (bean.isProcessed()) {// 结果已经被正确处理过
			logger.warn("接收到已经被正确处理过的结果.{}", response);
			// 前台跳转时，将会输出这些信息
			refundResult.setRetCode(PaygateError.SUCCESS.code());
			refundResult.setRetMsg(PaygateError.SUCCESS.desc());
			return bean.getAckContent();
		}
		// 2. 验证是否为有效的返回
		RefundValSignResponse valSignResponse = getRequestProcessor().refundValSign(bean, response);
		validateAndProcessValSignResult(valSignResponse, bean);

		refundResult.setRetCode(valSignResponse.getRetCode());
		refundResult.setRetMsg(valSignResponse.getRetMsg());
		if (!PaygateError.SUCCESS.code().equals(valSignResponse.getRetCode())) {
			// 验签失败，无效请求.
			return "fail";
		}
		// 3. 将支付结果反馈给调用端
		String clientConfirmResult = sendResponseToClient(bean, refundResult);
		if (clientConfirmResult != null) {
			clientConfirmResult = clientConfirmResult.trim();
		}
		logger.info("终端返回信息:{}", clientConfirmResult);
		if (!Const.CLIENT_CONFIRM_SUCCESS_FLAG.equalsIgnoreCase(clientConfirmResult)) {
			logger.info("终端确认信息有异常，暂不发送成功标记.");
			// 客户端确认失败，先不发送信息给支付平台
			bean.setAckContent("");
		}
		if (clientConfirmResult.length() > 6) {
			logger.warn("终端确认信息过长，截断...");
			clientConfirmResult = clientConfirmResult.substring(0, 4) + "..";
		}
		recordConfirmResult(bean, clientConfirmResult);
		return bean.getAckContent();
	}

	/**
	 * 通过银行的返回结果信息，检索数据库，获取原始的请求信息
	 *
	 * @param entityId
	 * @param response
	 * @return
	 */
	protected abstract RefundResponseConfirmBean buildConfirmBeanFromResponse(String entityId, Map<String, String> refundResponse);

	/**
	 * 验签结果与原始请求信息进行校验，并将结果记录在数据库中
	 *
	 * @param valSignResponse
	 */
	protected void validateAndProcessValSignResult(RefundValSignResponse valSignResponse, RefundResponseConfirmBean bean) {
		if (PaygateError.SUCCESS.code().equals(valSignResponse.getRetCode())) {
			if (valSignResponse.getRefundAmount() != bean.getRefundAmount()) {
				valSignResponse.setRetCode(PaygateError.E210_AMOUNT_NOT_MATCHED.code());
				valSignResponse.setRetMsg(PaygateError.E210_AMOUNT_NOT_MATCHED.desc());
			}
			if (!valSignResponse.getRefundNum().equalsIgnoreCase(bean.getRefundNum())) {
				valSignResponse.setRetCode(PaygateError.E225_REFUND_NUM_NOT_MATCHED.code());
				valSignResponse.setRetMsg(PaygateError.E225_REFUND_NUM_NOT_MATCHED.desc());
			}
		}
		if (!PaygateError.SUCCESS.code().equals(valSignResponse.getRetCode())) {
			// 验签失败
			bean.setRefundState(null);
		}
		processValSignResult(valSignResponse, bean);
	}

	protected abstract void processValSignResult(RefundValSignResponse valSignResponse, RefundResponseConfirmBean confirmBean);

	/**
	 * 将退款结果通知给终端
	 *
	 * @param bean
	 * @param refundResult
	 * @return
	 */
	protected String sendResponseToClient(RefundResponseConfirmBean bean, RefundResult refundResult) {
		PaygateClient client = null;
		try {
			client = paygate_client_service.findByClientId(bean.getClientId());
		} catch (Exception e1) {
			logger.error("", e1);
			throw new RuntimeException(e1);
		}
		if (client == null) {
			throw new PaygateException(PaygateError.E204_NULL_CLIENTID);
		}
		// String urlKey = Const.CLIENT_REFUND_NOTIFY_URL_PREFIX + bean.getClientId();
		// if(StringUtils.isNotBlank(bean.getPayType())){
		// urlKey+="."+bean.getPayType().trim();
		// }
		// logger.info("退款通知URL配置key:" + urlKey);
		// String notifyUrl = PropertiesUtil.getProperty(Const.CONFIG_FILE, urlKey);
		String notifyUrl = client.getRefundNotifyUrl();
		logger.info("退款通知URL:" + notifyUrl);
		if (StringUtils.isBlank(notifyUrl)) {
			logger.error("退款通知URL不正确.{}.{}", bean.getClientId(), bean.getPayType());
			throw new PaygateException(PaygateError.E311_INVALID_NOTIFY_URL);
		}
		Map<String, String> params = BeanMapUtil.mapBean(refundResult);
		params.put("sign", PaygateEncryptUtil.getSignStr(params, client.getSignKey()));
		logger.info("通知给调用端的信息:{}", params);
		try {
			return HttpUtil.doFormPost(new URL(notifyUrl), params);
		} catch (Exception e) {
			logger.error("", e);
			return "E";
		}
	}

	protected abstract void recordConfirmResult(RefundResponseConfirmBean bean, String clientConfirmResult);
}
