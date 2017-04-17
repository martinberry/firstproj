package com.ztravel.product.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.dao.IProductListDao;

/**
 * 前台产品列表Dao
 * @author MH
 */
@Repository
public class ProductListDaoImpl implements IProductListDao {

	@Resource
	private Datastore datastore;

	/**
	 * 搜索产品列表(只搜索已上线产品)
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Product> searchProducts(Map params) throws Exception {
		Query<Product> query = datastore.createQuery(Product.class);
		if( params.containsKey("from") ){
			query.field("from").equal(params.get("from"));
		}
		if( params.containsKey("toContinent") ){
			query.field("toContinent").hasThisOne(params.get("toContinent"));
		}
		if( params.containsKey("toCountry") ){
			query.field("toCountry").hasThisOne(params.get("toCountry"));
		}
		query.field("status").equal(Status.RELEASED);
		query.order("-updatedTime");
		return query.asList();
	}

}
