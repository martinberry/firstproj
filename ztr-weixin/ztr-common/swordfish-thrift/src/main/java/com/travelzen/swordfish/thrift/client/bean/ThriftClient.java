package com.travelzen.swordfish.thrift.client.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.travelzen.swordfish.thrift.common.ThriftClientPro;


public class ThriftClient {

	private  ThriftClientPro thriftClientConfig;

	private ThriftClientFactory thriftClientFactory = null;

	public ThriftClient(ThriftClientPro thriftClientConfig) {
		this.thriftClientConfig = thriftClientConfig;
	}

	public ThriftClientPro getThriftClientConfig() {
		return thriftClientConfig;
	}

	public void setThriftClientConfig(ThriftClientPro thriftClientConfig) {
		this.thriftClientConfig = thriftClientConfig;
	}



	public ThriftClientFactory getObject(){

		List<String> ips = new ArrayList<String>();

		ips.add(thriftClientConfig.getClient_ip() + ":" + thriftClientConfig.getClient_port());
		String prefix = thriftClientConfig.getClient_zookeeper_prefix();
		String serviceName = thriftClientConfig.getClient_zookeeper_servicename();
		thriftClientFactory = new ThriftClientFactory(ips, prefix, serviceName);

		return thriftClientFactory;

	}


}
