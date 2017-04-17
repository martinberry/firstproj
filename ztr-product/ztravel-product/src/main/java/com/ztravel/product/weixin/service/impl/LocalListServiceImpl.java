package com.ztravel.product.weixin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.weixin.service.ILocalListService;

@Service
public class LocalListServiceImpl implements ILocalListService {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(LocalListServiceImpl.class);
	
	@Resource
	IUnVisaProductDao unVisaProductDaoImpl;
	

	@Override
	public List<UnVisaProduct> search(Map<String, String> map) {
		List<UnVisaProduct> locals = null;
		try {
			LOGGER.info("移动端查询签证产品列表，参数:[{}]",JSONObject.toJSONString(map));
			locals = unVisaProductDaoImpl.selectByMap(map);
		} catch (Exception e) {
			locals = null;
			LOGGER.error("查询移动端非签证产品列表错误[{}]", e);
		}
		return locals;
	}
}
