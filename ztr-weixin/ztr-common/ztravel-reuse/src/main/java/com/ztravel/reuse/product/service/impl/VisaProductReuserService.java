package com.ztravel.reuse.product.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.reuse.product.service.IVisaProductReuseService;

@Service
public class VisaProductReuserService implements IVisaProductReuseService {
	
	@Resource
	private IVisaProductDao visaProductDaoImpl;
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(VisaProductReuserService.class);
	
	
	@Override
	public List<VisaProduct> selectByMap(Map<String, String> params)throws Exception {
		List<VisaProduct> products = null;
		products = selectByMapAndPage(params, null, null);
		return products;
	}

	@Override
	public List<VisaProduct> selectByMapAndPage(Map<String, String> params,Integer pageNum, Integer pageSize) throws Exception {
		List<VisaProduct> products = null;
		products = visaProductDaoImpl.selectByMapAndPage(params, pageNum, pageSize);
		return products;
	}

	@Override
	public VisaProduct selectByPid(String pid) throws Exception {
		LOGGER.info("开始查询签证产品，pid:[{}]",pid);
		Assert.hasText(pid, "签证产品查询的产品编号为空");
		VisaProduct product = visaProductDaoImpl.getProductByPid(pid);
		LOGGER.info("签证产品:[{}]查询结束,查询结果:[{}]",pid,TZBeanUtils.describe(product));
		return product;
	}
	
	@Override
	public VisaProduct selectById(String id) throws Exception {
		LOGGER.info("开始查询签证产品，id:[{}]",id);
		Assert.hasText(id, "签证产品查询的产品ID为空");
		VisaProduct product = visaProductDaoImpl.getProductById(id);
		LOGGER.info("签证产品:[{}]查询结束,查询结果:[{}]",id,TZBeanUtils.describe(product));
		return product;
	}

	@Override
	public PriceInfo getPriceByPidAndPriceId(String pid, String priceId)throws Exception {
		LOGGER.info("开始查询产品:[{}],价格信息:[{}]",pid,priceId);
		Assert.hasText(priceId, "查询产品[{}]价格类型信息的价格ID为空");
		VisaProduct product = selectByPid(pid);
		Assert.isTrue(product!= null, "产品["+pid+"]不存在");
		PriceInfo price = null;
		if(CollectionUtils.isNotEmpty(product.getPriceInfos())){
			for(PriceInfo priceInfo : product.getPriceInfos()){
				if(priceInfo.getId().equals(priceId.trim())){
					price = priceInfo;
					break;
				}
			}
		}else{
			LOGGER.info("产品:[{}]销售价格信息为空",pid);
			throw new RuntimeException("产品["+pid+"]销售价格信息为空");
		}
		Assert.isTrue(null != price, "产品["+pid+"]ID为["+priceId+"]的价格信息不存在");
		return price;
	}

}
