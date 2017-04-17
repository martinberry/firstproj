package com.ztravel.paygate.web.thrift;

import java.util.List;

import com.travelzen.framework.thrift.client.balancing.ThriftBalancingChannel;
import com.ztravel.paygate.thrift.service.PaygateService;

/**
 *
 */
public class PaygateThriftBalancingClientFactory implements PaygateThriftClientFactory {

	private ThriftBalancingChannel<PaygateService.Iface, PaygateService.Client> channel;

	public PaygateThriftBalancingClientFactory(List<String> hosts, String prefix, String serviceName) {
		this.channel = new ThriftBalancingChannel<PaygateService.Iface, PaygateService.Client>(hosts, prefix, serviceName, false){};
	}

	@Override
	public PaygateService.Iface getClient() throws Exception {
		return channel.proxy();
	}

	@Override
	public void releaseClient(PaygateService.Client client) throws Exception {

	}

}
