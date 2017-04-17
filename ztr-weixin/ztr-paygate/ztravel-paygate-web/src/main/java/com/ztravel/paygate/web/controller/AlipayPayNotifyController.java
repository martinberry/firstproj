package com.ztravel.paygate.web.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PayState;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.Alipay;
import com.ztravel.paygate.thrift.model.ValSignResponse;
import com.ztravel.paygate.web.dto.middlebean.ProcessResponseResult;
import com.ztravel.paygate.web.dto.middlebean.ResponseConfirmBean;
import com.ztravel.paygate.web.dto.response.BankPayResponse;
import com.ztravel.paygate.web.dto.result.PayResult;
import com.ztravel.paygate.web.modelbuilder.AlipayModelBuilder;
import com.ztravel.paygate.web.processor.AlipayRequestProcessor;
import com.ztravel.paygate.web.processor.RequestProcessor;
import com.ztravel.paygate.web.service.IAlipayService;
import com.ztravel.paygate.web.util.EnvArgs.Const;

/**
 * 支付宝通知回调接口
 * 
 * @author dingguangxian
 * 
 */
@Controller
@RequestMapping("/paygate/alipay")
public class AlipayPayNotifyController extends PayNotifyController {

	public static final String RESPONSE_MSG_SUCCESS = "success";
	@Resource
	IAlipayService alipayService;
	@Resource
	AlipayRequestProcessor requestProcessor;

	public AlipayPayNotifyController() {
		super(GateType.AliPay);
	}

	@Override
	protected Map<String, String> extractResponseData(boolean isFgNotify, HttpServletRequest req) {
		if (!isFgNotify) {
			logger.info("extractResponseData::后台通知，无需转码.");
			return super.extractResponseData(isFgNotify, req);
		}
		Map<String, String> formData = new HashMap<String, String>();
		Enumeration<String> paramNames = req.getParameterNames();
		String subject = req.getParameter("subject");
		logger.info("subject ISO8859-1:::" + subject);
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			if ("fg".equals(name)) {
				continue;
			}
			String value = req.getParameter(name);
			if (value != null) {
				try {
					// 对接收到的参数进行转码
					value = new String(value.getBytes("ISO8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.warn("字符集不支持，转码失败.", e);
				}
			}
			formData.put(name, value);
		}
		return formData;
	}

	@Override
	public RequestProcessor getRequestProcessor() {
		return requestProcessor;
	}
	@Override
	public void processBankResponse(ProcessResponseResult result, String entityId, BankPayResponse response) {
		Map<String,String> responseData = response.getFormData();
		if(responseData != null &&!response.isFgNotify() && "WAIT_BUYER_PAY".equals(responseData.get("trade_status"))){
			//手机支付，等待买家付款状态,直接跳过
			logger.info("交易状态为WAIT_BUYER_PAY,直接跳过");
			result.setAckContent(RESPONSE_MSG_SUCCESS);
			return ;
		}
		super.processBankResponse(result, entityId, response);
	}

	@Override
	protected ResponseConfirmBean buildConfirmBeanFromResponse(String entityId, BankPayResponse response) {
		Map<String, String> responseData = response.getFormData();
		// String orderNum = responseData.get("out_trade_no");
		Alipay entity;
		try {
			entity = alipayService.findById(entityId);
		} catch (SQLException e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		if (entity == null || !PaygateError.SUCCESS.code().equals(entity.getSignRetCode())) {
			// 保证是签名成功的记录.
			logger.warn("接收到的信息(id={})在数据库中未找到对应的记录,丢弃.", entityId);
			return null;
		}

		ResponseConfirmBean bean = new ResponseConfirmBean();
		bean.setPartner(entity.getPartner());
		bean.setFgNotify(response.isFgNotify());
		bean.setEntityId(entity.getAlipayId());
		bean.setClientId(entity.getClientId());
		bean.setOrderNum(entity.getOrderNum());
		bean.setAmount(entity.getTransAmount());
		bean.setComment(entity.getTransComment());
		bean.setFgNotifyUrl(entity.getFgNotifyUrl());
		bean.setPayType(entity.getPayType());
		if (StringUtils.isNotBlank(responseData.get("gmt_payment"))) {
			// 前台不输出gmt_payment
			bean.setBankPaymentTime(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(responseData.get("gmt_payment"))
					.toDate());
		}

		bean.setAckContent(RESPONSE_MSG_SUCCESS);
		String tradeStatus = responseData.get("trade_status");
		bean.setPayState("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus) || "TRADE_FINISHED".equalsIgnoreCase(tradeStatus) ? PayState.SUCCESS
				: PayState.FAIL);

		if (!response.isFgNotify() && PayState.SUCCESS.name().equalsIgnoreCase(entity.getPayState())
				&& Const.CLIENT_CONFIRM_SUCCESS_FLAG.equalsIgnoreCase(entity.getConfirmResult())) {
			// 客户端已经确认成功，可能是重复返回的信息
			logger.warn("接收到的信息(id={})已经处理过,丢弃.", entityId);
			bean.setProcessed(true);
		} else if (!response.isFgNotify()) {
			// 在客户端未确认成功的情况下，无论是否已经接收过该消息，都需要进行根据返回信息更新数据库
			Alipay record = AlipayModelBuilder.buildFromPayNotifyResponse(entity.getAlipayId(), response);
			try {
				alipayService.updateSelectiveById(record);
			} catch (SQLException e) {
				logger.error("", e);
				throw new PaygateException(PaygateError.E101_DB_ERROR);
			}
		}
		return bean;
	}

	@Override
	protected void processValSignResult(ValSignResponse valSignResponse, ResponseConfirmBean confirmBean) {
		if (!confirmBean.isFgNotify()) {
			Alipay record = AlipayModelBuilder.buildFromValSignResponse(confirmBean.getEntityId(), valSignResponse);
			if (confirmBean.getPayState() != null) {
				record.setPayState(confirmBean.getPayState().name());
			}
			try {
				alipayService.updateSelectiveById(record);
			} catch (SQLException e) {
				logger.error("", e);
				throw new PaygateException(PaygateError.E101_DB_ERROR);
			}
		}
	}

	@Override
	protected void recordConfirmResult(ResponseConfirmBean confirmBean, String clientConfirmResult) {
		if (!confirmBean.isFgNotify()) {
			Alipay record = new Alipay();
			record.setAlipayId(confirmBean.getEntityId());
			record.setAckContent(confirmBean.getAckContent());
			record.setConfirmResult(clientConfirmResult);
			record.setCompleteTime(new Date());
			try {
				alipayService.updateSelectiveById(record);
			} catch (SQLException e) {
				logger.error("", e);
				throw new PaygateException(PaygateError.E101_DB_ERROR);
			}
		}
	}
	@Override
	protected PayResult buildPayResult(BankPayResponse response,
			ResponseConfirmBean bean) {
		Map<String,String> responseData = response.getFormData();
		if(responseData != null && "air_cae_charge_agent".equals(responseData.get("notify_type"))){
			//代扣的返回结果
			PayResult payResult = super.buildPayResult(response, bean);
			payResult.setService(PayResult.SERVICE_AGENT);
			return payResult;
		} else {
			return super.buildPayResult(response, bean);
		}
	}
}
