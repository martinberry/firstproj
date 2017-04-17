package com.ztravel.product.common.dao;

import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.hotel.entity.HotelEntity;

public interface IProductDetailDao {

	/**
	 * 根据产品PID查询产品信息
	 * @param pid
	 * @return
	 */
	public Product getProductByPid(String pid);

	/**
	 * 根据产品id查询产品信息
	 * @param productId
	 * @return
	 */
	public Product getProductById(String productId);

	/**
	 * 根据酒店ID查询酒店资源
	 * @param id
	 * @return
	 */
	public HotelEntity getHotelById(String id);

}
