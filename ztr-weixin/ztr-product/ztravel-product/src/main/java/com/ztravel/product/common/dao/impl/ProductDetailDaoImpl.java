package com.ztravel.product.common.dao.impl;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.common.dao.IProductDetailDao;

/**
 *
 * @author tengmeilin
 *
 */
@Repository
public class ProductDetailDaoImpl implements IProductDetailDao {

	@Resource
	private Datastore datastore ;

	@Override
	public Product getProductByPid(String pid) {
		Query<Product> query = datastore.createQuery(Product.class)
				.filter("pid", pid)
				.filter("status", Status.RELEASED) ;
		return query.get() ;
	}

	@Override
	public HotelEntity getHotelById(String id) {
		return datastore.get(HotelEntity.class, new ObjectId(id));
	}

	@Override
	public Product getProductById(String productId) {
		Query<Product> query = datastore.createQuery(Product.class)
				.filter("_id", new ObjectId(productId));
//				.filter("status", Status.RELEASED) ;
		return query.get() ;
	}

}
