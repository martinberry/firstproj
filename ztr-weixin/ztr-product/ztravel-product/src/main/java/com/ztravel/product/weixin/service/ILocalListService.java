package com.ztravel.product.weixin.service;

import java.util.List;
import java.util.Map;

import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;

public interface ILocalListService {
	List<UnVisaProduct> search(Map<String,String> map);
}
