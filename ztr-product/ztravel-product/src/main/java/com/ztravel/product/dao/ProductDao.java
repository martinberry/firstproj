package com.ztravel.product.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Status;

public interface ProductDao {

	/**
	 * 根据产品id查询产品信息
	 * @param id
	 * @return
	 */
	Product getProductById(String id) ;
	
	Product getProductByPid(String pid) ;

	Boolean updateProgress(String id, Integer progress) ;

	public List<Product> findByConditions(Map<String, String> pConditions, int pPage, int pPageSize);

	public long getCount();

	Long getCountByConditions(Map<String, String> map);

	Boolean changeProductStatus(String id, Status status) ;

	Boolean deleteProduct(String id) ;

	Boolean updateLowestPrice(String id, Double adultPrice, Double oldMinAdultPrice) ;

	Boolean updateAndModifyStock(String productId, String bookDate, Integer usedStock, Integer stock) ;

	public List<Product> getAllProducts();

	public List<Product> getAllReleasedProducts();

}
