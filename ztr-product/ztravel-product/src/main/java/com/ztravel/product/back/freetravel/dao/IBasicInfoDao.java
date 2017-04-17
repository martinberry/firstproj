package com.ztravel.product.back.freetravel.dao;

import com.ztravel.product.back.freetravel.entity.Product;

public interface IBasicInfoDao {
	String insertProduct(Product product);
	int updateBasicInfo(Product product);
	Product queryBasicInfoById(String id);
}
