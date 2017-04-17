package com.ztravel.paygate.web.util;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.PaygateErrorSupport;
import com.ztravel.paygate.web.dto.result.AbstractResultBean;
import com.ztravel.paygate.web.service.IPaygateClientService;

/**
 * 工具类
 * 
 * @author dingguangxian
 * 
 */
@Component
public class PaygateUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private IPaygateClientService paygateClientService;

	/**
	 * 将异常信息设置给{@link PaygateErrorSupport} bean
	 * 
	 * @param bean
	 * @param ex
	 */
	public static void processException(PaygateErrorSupport bean, Throwable ex) {
		if (ex instanceof PaygateException) {
			bean.setRetCode(((PaygateException) ex).getError().code());
			bean.setRetMsg(((PaygateException) ex).getError().desc());
		} else {
			bean.setRetCode(PaygateError.E100_ERROR.code());
			bean.setRetMsg(PaygateError.E100_ERROR.desc());
		}
	}
	/**
	 * 对返回结果进行签名
	 * @param resultBean
	 */
	public void signResultBean(String clientId, AbstractResultBean resultBean){
		PaygateClient client;
		try {
			client = paygateClientService.findByClientId(clientId);
		} catch (Exception e) {
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		if (client == null) {
			throw new PaygateException(PaygateError.E205_INVALID_CLIENTID);
		}
		Map<String,String> params = BeanMapUtil.mapBean(resultBean);
		String sign = PaygateEncryptUtil.getSignStr(params, client.getSignKey());
		logger.info("对返回结果进行签名结果:{}, sign:{}",params,sign);
		resultBean.setSign(sign);
	}
}
