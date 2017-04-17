package com.ztravel.product.client.service;

import java.util.List;

import com.ztravel.product.client.entity.ProductClientEntity;
import com.ztravel.product.client.entity.ProductHomePageEntity;
import com.ztravel.product.client.entity.TopicRelatedProductEntity;

public interface IProductClientService {


	Boolean updateAndModifyStock(String productId , String bookDate , Integer usedStock) throws Exception;

	Integer getStock(String productId,String bookDate)   throws Exception;

	/**
	 * 根据id查询产品信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ProductClientEntity getProductById(String id) throws Exception;

	ProductHomePageEntity getProductHPById(String id) throws Exception ;
	String getProductstatusbyid(String id) throws Exception;
	public List<TopicRelatedProductEntity> getReleasedProduct() throws Exception;
}
