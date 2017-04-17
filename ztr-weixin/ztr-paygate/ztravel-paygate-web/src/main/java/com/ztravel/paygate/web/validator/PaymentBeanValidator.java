package com.ztravel.paygate.web.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.web.dto.request.RequestPayBean;
import com.ztravel.paygate.web.dto.request.RequestRefundBean;
import com.ztravel.paygate.web.util.ValidationUtil;

@BeanValidator
@Component("paygate_payment_bean_validator")
public class PaymentBeanValidator {
	/**
	 * 对支付请求进行数据有效性校验，校验失败直接抛出{@link PaygateException}
	 * 
	 * @param requestPayBean
	 * @param req
	 * @throws PaygateException
	 */
	@BeanValidator
	public void validateRequestPayBean(RequestPayBean requestPayBean){
		ValidationUtil.validateOrderNum(requestPayBean.getOrderNum());
		ValidationUtil.validateAmount(requestPayBean.getAmount());
		// ValidationUtil.validateFgNotifyUrl(requestPayBean.getFgNotifyUrl());
	}
	
	/**
	 * 退单请求校验
	 * @param refundBean
	 */
	@BeanValidator
	public void validateRefundBean(RequestRefundBean refundBean) {
		ValidationUtil.validateOrderNum(refundBean.getOrderNum());
		ValidationUtil.validateAmount(refundBean.getTransAmount());
		ValidationUtil.validateAmount(refundBean.getRefundAmount());
		ValidationUtil.validateGateType(refundBean.getGateType());
		//退分润校验,退分润格式:分润方账号|退分润金额|备注
		String refundProfitDetails = refundBean.getRefundProfitDetails();
		if(StringUtils.isNotBlank(refundProfitDetails)){
			String[] str = StringUtils.splitPreserveAllTokens(refundProfitDetails, "\\|");//refundProfitDetails.split("\\|");
			if(str.length != 3) {
				throw new PaygateException(PaygateError.E201_INVALID_PARAM);
			}
			ValidationUtil.validateRequired(str[0]);
			ValidationUtil.validateAmount(Long.valueOf(str[1]));
		}
	}
}
