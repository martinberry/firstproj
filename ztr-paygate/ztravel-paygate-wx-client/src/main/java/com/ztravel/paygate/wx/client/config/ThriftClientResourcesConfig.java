package com.ztravel.paygate.wx.client.config;

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

	private static final String CLIENT_PATH = "ztr-paygate-wx/paygate-wx-client.properties";
	
	private static final ConfScope CLIENT_SCOPE = ConfScope.R; 
	
	@Bean 
	ThriftClientPro thriftClientConfig() {
		ThriftClientPro clientConfig = new ThriftClientPro();
		
		clientConfig.setClient_ip(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.paygate.wx.ip", CLIENT_SCOPE));
		clientConfig.setClient_port(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.paygate.wx.port", CLIENT_SCOPE));
		clientConfig.setClient_zookeeper_servicename(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.paygate.wx.zookeeper.servicename", CLIENT_SCOPE));
		clientConfig.setClient_zookeeper_prefix(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.paygate.wx.zookeeper.prefix", CLIENT_SCOPE));
		
		return clientConfig;
	}
	
	
	@Bean(name="paygateWxThriftClient")
	ThriftClient thriftClient() {
		ThriftClient thriftClient = new ThriftClient(thriftClientConfig());
		return thriftClient;
	}
	

}
