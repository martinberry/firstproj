package com.ztravel.product.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.product.back.freetravel.entity.Product;

/**
 * C端产品列表Dao
 * @author MH
 */
public interface IProductListDao {
	/**
	 * 搜索产品列表(只搜索已上线产品)
	 * @return
	 */
	public List<Product> searchProducts(Map params) throws Exception;
}
