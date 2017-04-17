package com.ztravel.product.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.dao.ProductDao;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Resource
	private Datastore datastore ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public Product getProductById(String id) {
		return datastore.createQuery(Product.class).filter("_id", new ObjectId(id)).get();
	}

	@Override
	public Product getProductByPid(String pid){
		return datastore.createQuery(Product.class).filter("pid", pid).get();
	}

	@Override
	public Boolean updateProgress(String id, Integer progress) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
				.set("progress", progress) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public List<Product> findByConditions(Map<String, String> pConditions,
			int pPage, int pPageSize) {
		if (pPage < 1) {
			pPage = 1;
		}

		if (pPageSize < 1) {
			pPageSize = 1;
		}

		Query<Product> lvQuery = datastore.createQuery(Product.class).order("-updatedTime");
		if(null!=pConditions){
			for (Entry<String, String> entry : pConditions.entrySet()) {
				if(StringUtils.isNotBlank(entry.getValue())){
					lvQuery.field(entry.getKey()).contains(entry.getValue());
				}
			}
		}
		int offset = (pPage - 1) * pPageSize;
		lvQuery.offset(offset);
		lvQuery.limit(pPageSize);
		List<Product> lvResult = lvQuery.asList();
		return lvResult;
	}

	@Override
	public long getCount() {
		return datastore.getCount(Product.class);
	}

	@Override
	public Long getCountByConditions(Map<String, String> map) {
		Query<Product> lvQuery = datastore.createQuery(Product.class);
		if (null != map) {
			for (Entry<String, String> entry : map.entrySet()) {
				if(StringUtils.isNotBlank(entry.getValue())){
					lvQuery.field(entry.getKey()).contains(entry.getValue());
				}
			}
		}
		return datastore.getCount(lvQuery);
	}

	@Override
	public Boolean deleteProduct(String id) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		return datastore.delete(q).getN() == 1;
	}

	@Override
	public Boolean changeProductStatus(String id, Status status) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
				.set("status", status) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public Boolean updateLowestPrice(String id, Double adultPrice,
			Double oldMinAdultPrice) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id))
			.filter("lowestPrice", oldMinAdultPrice) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		if(adultPrice != null){
			ops.set("lowestPrice", adultPrice) ;
		}else{
			ops.unset("lowestPrice") ;
		}
		datastore.findAndModify(q, ops) ;
		return true ;
	}

	@Override
	public Boolean updateAndModifyStock(String productId, String bookDate,
			Integer usedStock, Integer stock) {
		Query<Product> query = datastore.createQuery(Product.class).filter("_id", new ObjectId(productId)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class).set("calendar."+bookDate+".sale.stock",stock);
		ops.set("calendar."+bookDate+".sale.usedStock",usedStock);
		Boolean updateResult = datastore.findAndModify(query, ops) != null ;
		return updateResult;
	}

	@Override
	public List<Product> getAllProducts() {
		Query<Product> query = datastore.createQuery(Product.class);
		return query.asList();
	}

	@Override
	public List<Product> getAllReleasedProducts() {
		Query<Product> query = datastore.createQuery(Product.class);
		query.field("status").equal(Status.RELEASED);
		query.order("-updatedTime");
		return query.asList();
	}


}
