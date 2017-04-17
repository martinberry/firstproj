package com.ztravel.product.weixin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.product.weixin.service.IVisaProductListService;

@Service
public class VisaProductListServiceImpl implements IVisaProductListService {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(VisaProductListServiceImpl.class);
	
	@Resource
	IVisaProductDao visaProductDaoImpl;
	
	
	@Override
	public List<VisaProduct> search(Map<String, String> map) {
		List<VisaProduct> visaProducts = null;
		try {
			LOGGER.info("移动端查询签证产品列表，参数:[{}]",JSONObject.toJSONString(map));
			visaProducts = visaProductDaoImpl.selectByMap(map);
		} catch (Exception e) {
			visaProducts = null;
			e.printStackTrace();
		}
		return visaProducts;
	}

}
