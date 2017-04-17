package com.ztravel.order.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.swordfish.thrift.common.ThriftProtoPro;
import com.travelzen.swordfish.thrift.common.ThriftServerPro;


/**
 * thrift server 配置
 * @author pengfei.zhao
 *
 */

@Configuration
public class ThriftServerResourcesConfig {


	private static final String SERVER_PATH ="ztr-order-server.properties";

	private static final ConfScope SERVER_SCOPE = ConfScope.M;

	private static final Integer THREAD_COUNT_DEFAULT = 24;

	private static final Long MAX_READBUFFER_BYTES_DEAUFT = (long) (512 * 1024);

	@Bean
	ThriftServerPro thriftServerConfig() {
		ThriftServerPro serverConfig = new ThriftServerPro();

		serverConfig.setServer_ip(TopsConfReader.getConfContent(SERVER_PATH, "ztr.order.ip", SERVER_SCOPE));
		serverConfig.setServer_port(TopsConfReader.getConfContent(SERVER_PATH, "ztr.order.port", SERVER_SCOPE));
		serverConfig.setServer_zookeeper_shardid(TopsConfReader.getConfContent(SERVER_PATH, "ztr.order.zookeeper.shardid", SERVER_SCOPE));
		serverConfig.setServer_zookeeper_replicaid(TopsConfReader.getConfContent(SERVER_PATH, "ztr.order.zookeeper.replicaid", SERVER_SCOPE));

		serverConfig.setClient_zookeeper_servicename(TopsConfReader.getConfContent(SERVER_PATH, "ztr.order.zookeeper.servicename", SERVER_SCOPE));
		serverConfig.setClient_zookeeper_prefix(TopsConfReader.getConfContent(SERVER_PATH, "ztr.order.zookeeper.prefix", SERVER_SCOPE));

		return serverConfig;
	}

	@Bean
	ThriftProtoPro thriftProtoConfig() {
		ThriftProtoPro thriftProtoConfig = new ThriftProtoPro();
		thriftProtoConfig.setMaxReadBufferBytes(MAX_READBUFFER_BYTES_DEAUFT);
		thriftProtoConfig.setThreadCount(THREAD_COUNT_DEFAULT);
		return thriftProtoConfig;
	}

}
