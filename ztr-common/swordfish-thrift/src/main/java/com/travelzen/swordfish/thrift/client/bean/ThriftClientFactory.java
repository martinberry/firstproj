package com.travelzen.swordfish.thrift.client.bean;

import java.util.List;
import com.incubate.framework.thrift.ThriftService;
import com.incubate.framework.thrift.ThriftService.Iface;
import com.travelzen.framework.thrift.client.balancing.Client;
import com.travelzen.framework.thrift.client.balancing.LoadBalancingChannelNoProperty;
import com.travelzen.framework.thrift.client.balancing.WjThriftClient;

public class ThriftClientFactory extends LoadBalancingChannelNoProperty<ThriftService.Iface>{
	
	/**
	 * LoadBalancingChannelNoProperty 加了一个新参数， 是否缓存， 如果给false， 就是每次从zk上取新的server列表
	 */
	private static final boolean needCache = false;

	public ThriftClientFactory(List<String> list, String prefix, String serviceName) {
		super(list, prefix, serviceName, needCache);
	}

	@Override
	public Client<Iface> createThriftCient(String ip, int port) {
		return new WjThriftClient<ThriftService.Iface, ThriftService.Client>(ip, port, true, ThriftService.Client.class);
	}



}
