package com.ztravel.paygate.server.alipay.test;

import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztravel.paygate.server.alipay.AlipayEnv;
import com.ztravel.paygate.server.alipay.AlipayEnv.Args;
import com.ztravel.paygate.server.alipay.AlipayServiceHandler;
import com.ztravel.paygate.thrift.service.PaygateService;
import com.ztravel.paygate.thrift.service.PaygateService.Iface;

/**
 * 服务端
 * 
 * @author dingguangxian
 * 
 */
public class AlipayServiceServerStartup {
	private static Logger log = LoggerFactory.getLogger(AlipayServiceServerStartup.class);
	private static AlipayEnv alipayEnv = AlipayEnv.instance();

	private AlipayServiceHandler handler = new AlipayServiceHandler();

	private PaygateService.Processor<Iface> processor;

	private TThreadedSelectorServer server;

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
			new Thread(simple).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	/**
	 * 服务停止
	 */
	public void stop() {
		if (server != null && server.isServing()) {
			server.stop();
		}
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
			System.out.println("Starting the [支付宝] simple server...");

			/**
			 * 注册zookeeper
			 */
			// TZCuratorFramework.registerRpc(PropertiesUtil.getPropertyFromResource(SERVER_CONFIG, SERVER_IP) + ":" + port);
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		AlipayServiceServerStartup server = new AlipayServiceServerStartup();
		server.start();
	}
}
