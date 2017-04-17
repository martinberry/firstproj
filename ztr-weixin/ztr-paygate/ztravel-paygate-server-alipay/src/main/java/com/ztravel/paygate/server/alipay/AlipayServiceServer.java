package com.ztravel.paygate.server.alipay;

import javax.annotation.Resource;

import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.travelzen.framework.config.tops.util.TopsAppRegistry;
import com.travelzen.framework.config.tops.zk.TopsZookeeperBalancer;
import com.ztravel.paygate.server.alipay.AlipayEnv.Args;
import com.ztravel.paygate.thrift.service.PaygateService;
import com.ztravel.paygate.thrift.service.PaygateService.Iface;

/**
 * 支付宝服务端
 * 
 * @author dingguangxian
 * 
 */
@Component("paygate_alipay_service_server")
public class AlipayServiceServer {
	private static AlipayEnv alipayEnv = AlipayEnv.instance();
	private static String SHARD_ID = "alipay";
	private static Logger log = LoggerFactory.getLogger(AlipayServiceServer.class);

	@Resource
	private PaygateService.Iface handler;

	private PaygateService.Processor<Iface> processor;

	private TThreadedSelectorServer server;

	Thread runnerThread;

	/**
	 * 服务启动
	 */
	public void start() {
		try {
			final int port = Integer.parseInt(alipayEnv.getArgs(Args.SERVICE_PORT));
			// handler = new TelpayServiceHandler();
			processor = new PaygateService.Processor<Iface>(handler);
			Runnable simple = new Runnable() {
				public void run() {
					simple(processor, port);
				}
			};
			runnerThread = new Thread(simple);
			// runnerThread.setDaemon(true);
			runnerThread.start();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 服务停止
	 */
	public void stop() {
		log.info("停止[支付宝]服务...");
		if (server != null) {
			server.stop();
		}
		// if(runnerThread!=null) {
		// runnerThread.interrupt();
		// }
	}

	/**
	 * 启动服务
	 * 
	 * @param processor
	 * @param port
	 */
	private static void simple(PaygateService.Processor<Iface> processor, int port) {
		try {
			log.info("start server.");
			TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
			// Use this for a multithreaded server
			TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport).processor(processor);
			args.workerThreads(Integer.parseInt(alipayEnv.getArgs(Args.SERVICE_WORKTHREADS)));
			args.transportFactory(new TFramedTransport.Factory());
			// args.protocolFactory(new TBinaryProtocol.Factory());
			TThreadedSelectorServer server = new TThreadedSelectorServer(args);
			System.out.println("Starting the [支付宝] simple server at port[" + port + "]...");

			/**
			 * 注册zookeeper
			 */
			if (alipayEnv.isZkEnabled()) {
				String content = TopsAppRegistry.getLocalIP() + ":" + port;
				log.info("注册支付服务到zookeeper:{}, {}, {}, {}, ", content, alipayEnv.getTznsPath(), alipayEnv.getServerName(), alipayEnv.getShardId());
				TopsZookeeperBalancer.registerRpc(content,
						alipayEnv.getTznsPath(), alipayEnv.getServerName(), alipayEnv.getShardId(), content);
			}
			// TZCuratorFramework.registerRpc(PropertiesUtil.getPropertyFromResource(SERVER_CONFIG, SERVER_IP) + ":" + port);
			server.serve();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
