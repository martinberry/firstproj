package com.ztravel.paygate.web.thrift;

import com.ztravel.paygate.thrift.service.PaygateService;

public interface PaygateThriftClientFactory {

	PaygateService.Iface getClient() throws Exception;

	void releaseClient(PaygateService.Client client) throws Exception;

}
