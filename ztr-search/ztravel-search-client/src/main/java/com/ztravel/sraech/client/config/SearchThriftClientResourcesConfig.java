package com.ztravel.sraech.client.config;

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
public class SearchThriftClientResourcesConfig {

	private static final String CLIENT_PATH = "ztr-search/search-server.properties";
	
	private static final ConfScope CLIENT_SCOPE = ConfScope.R; 
	
	@Bean 
	ThriftClientPro searchthriftClientConfig() {
		ThriftClientPro clientConfig = new ThriftClientPro();
		
		clientConfig.setClient_ip(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.search.ip", CLIENT_SCOPE));
		clientConfig.setClient_port(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.search.port", CLIENT_SCOPE));
		clientConfig.setClient_zookeeper_servicename(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.search.zookeeper.servicename", CLIENT_SCOPE));
		clientConfig.setClient_zookeeper_prefix(TopsConfReader.getConfContent(CLIENT_PATH, "ztr.search.zookeeper.prefix", CLIENT_SCOPE));
		
		return clientConfig;
	}
	
	@Bean(name="searchThriftClient")
	ThriftClient thriftClient() {
		ThriftClient thriftClient = new ThriftClient(searchthriftClientConfig());
		return thriftClient;
	}
	
	

}
