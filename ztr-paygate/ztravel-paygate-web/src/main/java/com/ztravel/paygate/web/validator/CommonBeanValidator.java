package com.ztravel.paygate.web.validator;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.request.AbstractRequestBean;
import com.ztravel.paygate.web.service.IPaygateClientService;
import com.ztravel.paygate.web.util.ValidationUtil;
/**
 * 基本数据校验器，对请求参数进行初步校验
 * @author dingguangxian
 *
 */
@BeanValidator
@Component("paygate_common_bean_validator")
public class CommonBeanValidator {
	@Resource
	private IPaygateClientService paygateClientService;
	
	@BeanValidator(order = -1)
	public void validateCommonRequestBean(AbstractRequestBean bean){
		ValidationUtil.validateClientId(bean.getClientId());
		PaygateClient client;
		try {
			client = paygateClientService.findByClientId(bean.getClientId());
		} catch (Exception e) {
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		if (client == null) {
			throw new PaygateException(PaygateError.E205_INVALID_CLIENTID);
		}
//		if (!client.getSupportsPayment()) {
//			throw new PaygateException(PaygateError.E230_INVALD_REQUEST);
//		}
		GateType gateType = bean.gateType();
		if(gateType == null){
			throw new PaygateException(PaygateError.E203_GATETYPE_NOT_SUPPORT);
		}
		
		// 验证客户端的签名信息
		if (client.getSupportsEncrypt()) {
			Map<String, String> params = BeanMapUtil.mapBean(bean);
			if (!PaygateEncryptUtil.verifySignStr(params, bean.getSign(), client.getSignKey())) {
				throw new PaygateException(PaygateError.E302_VAL_SIGN_FAIL);
			}
		}
	}
}
