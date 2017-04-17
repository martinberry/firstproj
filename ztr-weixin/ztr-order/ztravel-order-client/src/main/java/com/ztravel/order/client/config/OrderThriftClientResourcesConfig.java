package com.ztravel.order.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.swordfish.thrift.client.bean.ThriftClient;
import com.travelzen.swordfish.thrift.common.ThriftClientPro;


/**
 * thrift client资源配置
 * @author liuzhuo
 *
 */

@Configuration
public class OrderThriftClientResourcesConfig {

	private static final String CLIENT_PATH = "ztr-order/order-server.properties";

	private static final ConfScope CLIENT_SCOPE = ConfScope.R;

	@Bean
	ThriftClientPro orderthriftClientConfig() {
		ThriftClientPro clientConfig = new ThriftClientPro();

		clientConfig.setClient_ip(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.order.ip", CLIENT_SCOPE));
		clientConfig.setClient_port(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.order.port", CLIENT_SCOPE));
		clientConfig.setClient_zookeeper_servicename(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.order.zookeeper.servicename", CLIENT_SCOPE));
		clientConfig.setClient_zookeeper_prefix(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.order.zookeeper.prefix", CLIENT_SCOPE));

		return clientConfig;
	}

	@Bean(name="orderThriftClient")
	ThriftClient thriftClient() {
		ThriftClient thriftClient = new ThriftClient(orderthriftClientConfig());
		return thriftClient;
	}



}
