package com.ztravel.paygate.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.core.exception.BizException;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.PaygateErrorSupport;
import com.ztravel.paygate.web.dto.request.RequestQueryBean;
import com.ztravel.paygate.web.dto.request.RequestRefundQueryBean;
import com.ztravel.paygate.web.dto.result.PayResult;
import com.ztravel.paygate.web.dto.result.RefundResult;
import com.ztravel.paygate.web.processor.RequestProcessor;
import com.ztravel.paygate.web.util.ValidationUtil;

/**
 * 请求支付
 * 
 * @author dingguangxian
 * 
 */
@Controller
@RequestMapping("/paygate/query")
public class PayGateQueryController extends PaygateController{
	private static Logger logger = LoggerFactory.getLogger(PayGateQueryController.class);

	/**
	 * 
	 * 支付结果查询
	 * 
	 * @param model
	 * @param req
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("payment")
	@ResponseBody
	public Map<String, String> payment(Model model, HttpServletRequest req) {
		PayResult payResult = new PayResult();
		try {
			// 0. 将客户端的请求进行封装
			req.setCharacterEncoding("UTF-8");
			// QueryProcessBean requestQueryBean = new QueryProcessBean();
			RequestQueryBean requestBean = new RequestQueryBean();
			TZBeanUtils.setProperties(requestBean, req);
			logger.info("查询请求报文:" + TZBeanUtils.describe(requestBean));
			validateRequestPayBean(requestBean, req);
			String gateTypeCode = requestBean.getGateType();
			GateType gateType = null;
			for (GateType t : GateType.values()) {
				if (t.code.equalsIgnoreCase(gateTypeCode)) {
					gateType = t;
					break;
				}
			}

			RequestProcessor processor = null;
			if ((gateType != null) && (processor = processorFactory.getProcessor(gateType)) != null) {
				payResult = processor.paymentQuery(requestBean, req);
			} else {
				// 支付类型不受支持,直接返回
				payResult.setRetCode(PaygateError.E203_GATETYPE_NOT_SUPPORT.code());
				payResult.setRetMsg(PaygateError.E203_GATETYPE_NOT_SUPPORT.desc());
			}
			Map<String, String> result = BeanMapUtil.mapBean(payResult);
			result.put("sign", signMapData(requestBean.getClientId(), result));
			return result;
		} catch (Throwable thr) {
			logger.error("", thr);
			processException(payResult, thr);
			return BeanMapUtil.mapBean(payResult);
		}
	}

	/**
	 * 
	 * 退款结果查询
	 * 
	 * @param model
	 * @param req
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("refund")
	@ResponseBody
	public Map<String, String> refund(Model model, HttpServletRequest req) {
		RefundResult refundResult = new RefundResult();
		try {
			// 0. 将客户端的请求进行封装
			req.setCharacterEncoding("UTF-8");
			RequestRefundQueryBean requestQueryBean = new RequestRefundQueryBean();
			TZBeanUtils.setProperties(requestQueryBean, req);
			logger.info("查询请求报文:" + TZBeanUtils.describe(requestQueryBean));
			validateRequestRefundBean(requestQueryBean, req);
			String gateTypeCode = requestQueryBean.getGateType();
			GateType gateType = null;
			for (GateType t : GateType.values()) {
				if (t.code.equalsIgnoreCase(gateTypeCode)) {
					gateType = t;
					break;
				}
			}

			RequestProcessor processor = null;
			if ((gateType != null) && (processor = processorFactory.getProcessor(gateType)) != null) {
				refundResult = processor.refundQuery(requestQueryBean, req);
			} else {
				// 支付类型不受支持,直接返回
				refundResult.setRetCode(PaygateError.E203_GATETYPE_NOT_SUPPORT.code());
				refundResult.setRetMsg(PaygateError.E203_GATETYPE_NOT_SUPPORT.desc());
			}
			Map<String, String> result = BeanMapUtil.mapBean(refundResult);
			result.put("sign", signMapData(requestQueryBean.getClientId(), result));
			return result;
		} catch (Throwable thr) {
			logger.error("", thr);
			processException(refundResult, thr);
			return BeanMapUtil.mapBean(refundResult);
		}
	}

	private String signMapData(String clientId, Map<String, String> mapData) throws PaygateException {
		PaygateClient client;
		try {
			client = paygateClientService.findByClientId(clientId);
		} catch (Exception e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		return PaygateEncryptUtil.getSignStr(mapData, client.getSignKey());
	}

	private void processException(PaygateErrorSupport model, Throwable ex) {
		if (ex instanceof PaygateException) {
			model.setRetCode(((PaygateException) ex).getError().code());
			model.setRetMsg(((PaygateException) ex).getError().desc());
		} else {
			model.setRetCode(PaygateError.E100_ERROR.code());
			model.setRetMsg(PaygateError.E100_ERROR.desc());
		}
	}

	/**
	 * 对支付请求进行数据有效性校验，校验失败直接抛出{@link PaygateException}
	 * 
	 * @param requestPayBean
	 * @param req
	 * @throws PaygateException
	 */
	protected void validateRequestPayBean(RequestQueryBean requestQueryBean, HttpServletRequest req) throws PaygateException {
		logger.info("验证请求数据合法性开始.{}", TZBeanUtils.describe(requestQueryBean));
		ValidationUtil.validateClientId(requestQueryBean.getClientId());
		PaygateClient client;
		try {
			client = paygateClientService.findByClientId(requestQueryBean.getClientId());
		} catch (Exception e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		if (client == null) {
			throw new PaygateException(PaygateError.E205_INVALID_CLIENTID);
		}
		if (!client.getSupportsPayment()) {
			throw new PaygateException(PaygateError.E230_INVALD_REQUEST);
		}
		ValidationUtil.validateOrderNum(requestQueryBean.getOrderNum());
		// ValidationUtil.validateAmount(requestPayBean.getAmount());
		// ValidationUtil.validateFgNotifyUrl(requestPayBean.getFgNotifyUrl());
		ValidationUtil.validateGateType(requestQueryBean.getGateType());
		// ValidationUtil.validateEncryMsg(requestPayBean.getEncryMsg());

		// 验证客户端的签名信息
		if (client.getSupportsEncrypt()) {
			Map<String, String> params = BeanMapUtil.mapBean(requestQueryBean);
			if (!PaygateEncryptUtil.verifySignStr(params, requestQueryBean.getSign(), client.getSignKey())) {
				throw new PaygateException(PaygateError.E302_VAL_SIGN_FAIL);
			}
		}
		logger.info("验证请求数据合法性成功");
	}

	private void validateRequestRefundBean(RequestRefundQueryBean requestQueryBean, HttpServletRequest req) {

		logger.info("验证请求数据合法性开始.{}", TZBeanUtils.describe(requestQueryBean));
		ValidationUtil.validateClientId(requestQueryBean.getClientId());
		PaygateClient client;
		try {
			client = paygateClientService.findByClientId(requestQueryBean.getClientId());
		} catch (Exception e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		if (client == null) {
			throw new PaygateException(PaygateError.E205_INVALID_CLIENTID);
		}
		if (!client.getSupportsPayment()) {
			throw new PaygateException(PaygateError.E230_INVALD_REQUEST);
		}
		ValidationUtil.validateRefundNum(requestQueryBean.getRefundNum());
		ValidationUtil.validateGateType(requestQueryBean.getGateType());

		// 验证客户端的签名信息
		if (client.getSupportsEncrypt()) {
			Map<String, String> params = BeanMapUtil.mapBean(requestQueryBean);
			if (!PaygateEncryptUtil.verifySignStr(params, requestQueryBean.getSign(), client.getSignKey())) {
				throw new PaygateException(PaygateError.E302_VAL_SIGN_FAIL);
			}
		}
		logger.info("验证请求数据合法性成功");

	}

}
