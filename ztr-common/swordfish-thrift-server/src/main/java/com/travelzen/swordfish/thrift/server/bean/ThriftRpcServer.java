package com.travelzen.swordfish.thrift.server.bean;

import javax.annotation.Resource;

import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.incubate.framework.thrift.ThriftService;
import com.incubate.framework.thrift.ThriftService.Iface;
import com.travelzen.framework.config.tops.util.TopsAppRegistry;
import com.travelzen.framework.config.tops.zk.TopsZookeeperBalancer;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.thrift.protocol.RIThriftProtocolFactory;
import com.travelzen.swordfish.thrift.common.ThriftClientPro;
import com.travelzen.swordfish.thrift.common.ThriftProtoPro;
import com.travelzen.swordfish.thrift.common.ThriftServerPro;
import com.travelzen.swordfish.thrift.rpc.AbstractThriftRpcServer;



/**
 *
 * @author liuzhuo
 *
 */


@Component
public class ThriftRpcServer extends AbstractThriftRpcServer{

	public static final Logger logger = RequestIdentityLogger.getLogger(ThriftRpcServer.class);

	private static final Integer THREAD_COUNT_DEFAULT = 24;
	
	private static final Long MAX_READBUFFER_BYTES_DEAUFT = (long) (512 * 1024);


	@Resource
	private Iface RpcHandler;
	
	@Resource
	private ThriftServerPro thriftServerConfig;
	
	@Resource
	private ThriftProtoPro thriftProtoConfig;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start() {
		try {

			logger.info("开始启动thrift服务*****************************************************");
			
			Integer server_port = Integer.valueOf(thriftServerConfig.getServer_port());

			ThriftService.Processor processor = new ThriftService.Processor(RpcHandler);
			TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(server_port);
			TThreadedSelectorServer.Args targs = new TThreadedSelectorServer.Args(serverTransport).processor(processor);
			if(null == thriftProtoConfig) {
				targs.maxReadBufferBytes = MAX_READBUFFER_BYTES_DEAUFT;
				targs.workerThreads(THREAD_COUNT_DEFAULT);			
			}else {
				targs.workerThreads(thriftProtoConfig.getThreadCount());
				targs.maxReadBufferBytes = thriftProtoConfig.getMaxReadBufferBytes();
			}

			targs.transportFactory(new TFramedTransport.Factory());
			targs.outputProtocolFactory(new RIThriftProtocolFactory());
			targs.inputProtocolFactory(new RIThriftProtocolFactory());

			TThreadedSelectorServer server = new TThreadedSelectorServer(targs);


			String rpcUrl = TopsAppRegistry.getLocalIP()+":"+server_port;
			String YRNS_PR = thriftServerConfig.getClient_zookeeper_prefix();
			String serviceName = thriftServerConfig.getClient_zookeeper_servicename();
			String shardId = thriftServerConfig.getServer_zookeeper_shardid();
			String replicaId = thriftServerConfig.getServer_zookeeper_replicaid();


			TopsZookeeperBalancer.registerRpc(rpcUrl, YRNS_PR, serviceName, shardId, replicaId);

			server.serve();

		} catch (Exception e) {
			logger.error(TZMarkers.p2, "启动thrift服务失败", e);
			System.exit(0);
		}


	}



}
