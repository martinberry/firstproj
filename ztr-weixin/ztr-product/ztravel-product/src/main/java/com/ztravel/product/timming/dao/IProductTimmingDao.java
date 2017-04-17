package com.ztravel.product.timming.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.ztravel.product.back.freetravel.entity.Product;

public interface IProductTimmingDao {

	/**
	 * 获取上线的产品
	 * @return
	 * @throws Exception
	 */
	public List<Product> getAllProduct() throws Exception;

	/**
	 * 更新产品状态为过期
	 * @param ids
	 * @throws Exception
	 */
	public void updateProductStatus(List<ObjectId> ids) throws Exception;

}
