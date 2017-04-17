package com.ztravel.product.weixin.service;

import java.util.List;
import java.util.Map;

import com.ztravel.product.po.pieces.visa.VisaProduct;

public interface IVisaProductListService {
	
	List<VisaProduct> search(Map<String,String> map);
	
}
