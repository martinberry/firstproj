package com.ztravel.reuse.order.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.order.dao.IOrderOpenIdDao;
import com.ztravel.order.po.OrderOpenId;
import com.ztravel.reuse.order.service.IOrderOpenIdReuseService;

/**
 * @author zhaopengfei
 * */

@Service
public class OrderOpenIdReuseService implements IOrderOpenIdReuseService {

	@Resource
	IOrderOpenIdDao orderOpenIdDaoImpl;

	private static  Logger LOGGER = RequestIdentityLogger.getLogger(OrderOpenIdReuseService.class);
	@Override
	public String getOpenIdByOrderId(String orderId) {
		LOGGER.info("开始查询微信端订单[{}]建单人openId信息",orderId);
		String openId = null;
		try {
			if(StringUtils.isNoneBlank(orderId)){
				OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
				if(null != orderOpenId){
					openId = orderOpenId.getOpenId();
					LOGGER.info("微信端订单[{}]建单人openId为[{}]",orderId,openId);
				}else{
					LOGGER.info("微信端订单[{}]没有openId信息",orderId);
				}
			}else{
				throw new IllegalArgumentException("查询订单openId时,订单ID为空");
			}
		} catch (Exception e) {
			LOGGER.error("查询微信端订单openId信息错误", e);
		}
		return openId;
	}

}
