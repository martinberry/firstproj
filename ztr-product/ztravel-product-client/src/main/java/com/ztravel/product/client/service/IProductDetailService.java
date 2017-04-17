package com.ztravel.product.client.service;

import com.ztravel.product.client.entity.ProductBasicDetail;
import com.ztravel.product.client.wo.OrderProductDetailWo;

public interface IProductDetailService {

	/**
	 * 根据产品id和预定日前获取产品全部详情
	 * @param productId
	 * @param bookDate
	 * @return
	 * @throws Exception
	 */
	public OrderProductDetailWo getProductById(String productId, String bookDate) throws Exception;

	/**
	 * 根据产品id获取产品基本详情
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public ProductBasicDetail getProductById(String productId) throws Exception;

}
