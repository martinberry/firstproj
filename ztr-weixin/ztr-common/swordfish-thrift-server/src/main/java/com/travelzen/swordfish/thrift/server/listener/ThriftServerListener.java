package com.travelzen.swordfish.thrift.server.listener;

import javax.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.swordfish.thrift.rpc.AbstractThriftRpcServer;
import org.slf4j.Logger;


/**
 * 
 * @author liuzhuo1
 *
 */

@Component
public class ThriftServerListener implements ApplicationListener<ContextRefreshedEvent>{

	private static final Logger LOG = RequestIdentityLogger.getLogger(ThriftServerListener.class);
	
	@Resource
	private AbstractThriftRpcServer thriftRpcServer;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		startRpcMoudle();		
	}
	
	/**
	 * 启动rpc模块，目前设定一个module只启动一个rpc服务
	 */
	public void startRpcMoudle() {
		
		if(thriftRpcServer == null) {
			LOG.debug("没有检测到thriftServer模块");
			return ;
		}else {
			Thread thread = new Thread(new Runnable(){
				@Override
				public void run() {
					thriftRpcServer.start();
				}}, "Thread-ThriftServer");
			thread.setDaemon(true);
			thread.start();
		}
		
   }
}

