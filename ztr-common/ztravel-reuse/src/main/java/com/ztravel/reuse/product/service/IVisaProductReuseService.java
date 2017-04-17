package com.ztravel.reuse.product.service;

import java.util.List;
import java.util.Map;

import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public interface IVisaProductReuseService {
	
	List<VisaProduct> selectByMap(Map<String,String> params) throws Exception;
	
	List<VisaProduct> selectByMapAndPage(Map<String,String> params,Integer pageNum,Integer pageSize) throws Exception;
	
	VisaProduct selectByPid(String pid) throws Exception;
	
	VisaProduct selectById(String id) throws Exception;
	
	PriceInfo getPriceByPidAndPriceId(String pid,String priceId) throws Exception;
	
}
