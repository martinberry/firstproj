package com.ztravel.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.swordfish.thrift.client.bean.ThriftClient;
import com.travelzen.swordfish.thrift.common.ThriftClientPro;

/**
 * @author zuoning.shen
 *
 */

@Configuration
public class ThriftClientResourcesConfig {

	private static final String CLIENT_PATH = "ztr-payment/payment-server.properties";
	
	private static final ConfScope CLIENT_SCOPE = ConfScope.R; 
	
	@Bean 
	ThriftClientPro thriftClientConfig() {
		ThriftClientPro clientConfig = new ThriftClientPro();
		
		clientConfig.setClient_ip(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.payment.ip", CLIENT_SCOPE));
		clientConfig.setClient_port(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.payment.port", CLIENT_SCOPE));
		clientConfig.setClient_zookeeper_servicename(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.payment.zookeeper.servicename", CLIENT_SCOPE));
		clientConfig.setClient_zookeeper_prefix(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.payment.zookeeper.prefix", CLIENT_SCOPE));
		
		return clientConfig;
	}
	
	
	@Bean(name="paymentThriftClient")
	ThriftClient thriftClient() {
		ThriftClient thriftClient = new ThriftClient(thriftClientConfig());
		return thriftClient;
	}
	

}
