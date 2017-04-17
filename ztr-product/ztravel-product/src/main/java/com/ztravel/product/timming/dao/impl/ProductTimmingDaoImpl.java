package com.ztravel.product.timming.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.timming.dao.IProductTimmingDao;

@Repository
public class ProductTimmingDaoImpl implements IProductTimmingDao {

	@Resource
	private Datastore datastore;


	@Override
	public List<Product> getAllProduct() throws Exception {

		Query<Product> query = datastore.createQuery(Product.class);

		query.field("status").equal(Status.RELEASED);

		return query.asList();


	}

	@Override
	public void updateProductStatus(List<ObjectId> ids) throws Exception {

		Query<Product> query = datastore.createQuery(Product.class);
		query.field("_id").in(ids);

		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class);
		ops.set("status", Status.EXPIRED);

		datastore.update(query, ops);

	}

}
