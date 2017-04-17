package com.ztravel.paygate.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.processor.RequestProcessorFactory;
import com.ztravel.paygate.web.service.IPaygateClientService;
import com.ztravel.paygate.web.util.PaygateUtil;
import com.ztravel.paygate.web.validator.RequestBeanValidator;

public abstract class PaygateController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	protected RequestBeanValidator validator;
	@Resource
	protected IPaygateClientService paygateClientService;
	@Resource
	protected RequestProcessorFactory processorFactory;
	@Resource
	protected PaygateUtil paygateUtil;
	/**
	 * 根据对象生成签名串
	 * @param clientId
	 * @param data
	 * @return
	 * @throws PaygateException
	 */
	protected String getDataSign(String clientId, Object data) throws PaygateException {
		PaygateClient client;
		try {
			client = paygateClientService.findByClientId(clientId);
		} catch (Exception e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		Map<String,String> mapData = BeanMapUtil.mapBean(data);
		return PaygateEncryptUtil.getSignStr(mapData, client.getSignKey());
	}
}
