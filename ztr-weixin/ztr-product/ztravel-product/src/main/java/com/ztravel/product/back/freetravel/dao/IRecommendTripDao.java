package com.ztravel.product.back.freetravel.dao;

import com.ztravel.product.back.freetravel.entity.Product;

public interface IRecommendTripDao {
	Product queryRTById(String id);
	int updateRT(Product product);
}
