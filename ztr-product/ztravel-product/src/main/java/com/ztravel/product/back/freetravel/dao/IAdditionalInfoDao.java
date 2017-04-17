package com.ztravel.product.back.freetravel.dao;

import com.ztravel.product.back.freetravel.entity.Product;

public interface IAdditionalInfoDao {
	Product queryAIById(String id);
	int updateAI(Product product);
	int release(String id);
}
