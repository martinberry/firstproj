package com.ztravel.paygate.web.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;

/**
 * 
 */
public class ValidationUtil {
	/**
	 * 验证value不为空
	 * 
	 * @param value
	 */
	public static void validateRequired(String value) throws PaygateException {
		if (StringUtils.isBlank(value))
			throw new PaygateException(PaygateError.E206_VALIDATE_FAIL);
	}

	/**
	 * 验证订单来源
	 * 
	 * @param clientId
	 */
	public static void validateClientId(String clientId) throws PaygateException {
		clientId = StringUtils.trim(clientId);
		if (StringUtils.isEmpty(clientId))
			throw new PaygateException(PaygateError.E204_NULL_CLIENTID);
	}

	/**
	 * 验证订单来源
	 * 
	 * @param clientId
	 */
	public static void validatePartner(String partner) throws PaygateException {
		partner = StringUtils.trim(partner);
		if (StringUtils.isEmpty(partner))
			throw new PaygateException(PaygateError.E205_NULL_PARTNER);
	}

	/**
	 * 验证支付订单号
	 * 
	 * @param orderNum
	 */
	public static void validateOrderNum(String orderNum) throws PaygateException {
		orderNum = StringUtils.trim(orderNum);
		if (StringUtils.isEmpty(orderNum) || orderNum.length() > 30)
			throw new PaygateException(PaygateError.E201_INVALID_PARAM);
	}

	/**
	 * 验证退单标识号
	 * 
	 * @param orderNum
	 */
	public static void validateRefundNum(String refundNum) throws PaygateException {
		refundNum = StringUtils.trim(refundNum);
		if (StringUtils.isEmpty(refundNum) || refundNum.length() > 36)
			throw new PaygateException(PaygateError.E201_INVALID_PARAM);
	}

	/**
	 * 验证金额
	 * 
	 * @param amount
	 */
	public static void validateAmount(long amount) throws PaygateException {
		if (amount <= 0 || amount > 100000000000L)
			throw new PaygateException(PaygateError.E207_INVALID_AMOUNT);
	}

	/**
	 * 验证加密串
	 * 
	 * @param encryMsg
	 */
	public static void validateEncryMsg(String encryMsg) throws PaygateException {
		encryMsg = StringUtils.trim(encryMsg);
		if (StringUtils.isEmpty(encryMsg))
			throw new PaygateException(PaygateError.E206_VALIDATE_FAIL);
	}

	/**
	 * 验证前台通知地址
	 * 
	 * @param fgNotifyUrl
	 */
	public static void validateFgNotifyUrl(String fgNotifyUrl) throws PaygateException {
		fgNotifyUrl = StringUtils.trim(fgNotifyUrl);
		if (StringUtils.isEmpty(fgNotifyUrl) || fgNotifyUrl.length() > 250)
			throw new PaygateException(PaygateError.E208_INVALID_FG_NOTIFY_URL);
	}

	/**
	 * 验证支付方式
	 * 
	 * @param payType
	 */
	public static void validateGateType(String gateType) throws PaygateException {
		gateType = StringUtils.trim(gateType);
		for (GateType type : GateType.values())
			if (type.code.equals(gateType))
				return;
		throw new PaygateException(PaygateError.E203_GATETYPE_NOT_SUPPORT);
	}

	/**
	 * 验证日期数据
	 * 
	 * @param date
	 * @param pattern
	 * @throws PaygateException
	 */
	public static void validateDate(String date, String pattern) throws PaygateException {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		try {
			format.parseDateTime(date);
		} catch (Exception e) {
			throw new PaygateException(PaygateError.E201_INVALID_PARAM);
		}
	}
	/**
	 * 校验交易流水号
	 * @param traceNum
	 */
	public static void validateTraceNum(String traceNum) {
		traceNum = StringUtils.trim(traceNum);
		if (StringUtils.isEmpty(traceNum) || traceNum.length() > 64)
			throw new PaygateException(PaygateError.E201_INVALID_PARAM);
	}

}
