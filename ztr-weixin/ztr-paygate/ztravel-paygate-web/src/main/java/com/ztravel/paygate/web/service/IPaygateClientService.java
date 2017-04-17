package com.ztravel.paygate.web.service;

import com.ztravel.paygate.core.po.gen.PaygateClient;

/**
 * 网关客户端
 * 
 * @author dingguangxian
 * 
 */
public interface IPaygateClientService {

	public PaygateClient findByClientId(String clientId) throws Exception;

}
