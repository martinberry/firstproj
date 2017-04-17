package com.ztravel.paygate.core.util;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ztravel.paygate.core.dao.Dao;
@Component("paygate_dao_initiator")
public class DaoInitiator implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger logger = LoggerFactory.getLogger(DaoInitiator.class);

	@Resource(name="paygate-sqlMapClient")
	SqlMapClient sqlMapClient;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext context = event.getApplicationContext();
		Map<String,Dao> daoMap = context.getBeansOfType(Dao.class);
		for(Entry<String,Dao> entry : daoMap.entrySet()){
			logger.info("初始化DAO \"{}\",class:{}", entry.getKey(), entry.getValue().getClass().getName());
			entry.getValue().setSqlMapClient(sqlMapClient);
		}
	}

}
