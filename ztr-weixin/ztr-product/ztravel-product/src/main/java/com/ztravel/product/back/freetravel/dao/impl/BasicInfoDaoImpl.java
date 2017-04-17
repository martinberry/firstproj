package com.ztravel.product.back.freetravel.dao.impl;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Key;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.dao.IBasicInfoDao;
import com.ztravel.product.back.freetravel.entity.Product;

@Repository
public class BasicInfoDaoImpl implements IBasicInfoDao {

	@Resource
	private Datastore datastore ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public String insertProduct(Product product) {
		product.setCreatedTime(DateTime.now());
		product.setCreatedBy(redisClient.get(OperatorSidHolder.get()));
		product.setUpdatedTime(product.getCreatedTime());
		product.setUpdateBy(product.getCreatedBy());
		Key<Product> result = datastore.save(product);
		if(result.getId() != null){
			return result.getId().toString();
		}else{
			return null;
		}
	}

	@Override
	public int updateBasicInfo(Product product) {
		Query<Product> query = datastore.createQuery(Product.class);
		query.field("id").equal(product.getId());
		UpdateOperations<Product> ups = datastore.createUpdateOperations(Product.class);
		if(product.getPid() != null)ups.set("pid", product.getPid());
		if(product.getpName() != null)ups.set("pName", product.getpName());
		if(product.getSubName() != null)ups.set("subName", product.getSubName());
		if(product.getRecommendWords() != null)ups.set("recommendWords", product.getRecommendWords());
		if(product.getTheme() != null)ups.set("theme", product.getTheme());
		if(product.getTripDays() != null)ups.set("tripDays", product.getTripDays());
		if(product.getTripNights() != null)ups.set("tripNights", product.getTripNights());
		if(product.getTags() != null)ups.set("tags", product.getTags());
		if(product.getFrom() != null)ups.set("from", product.getFrom());
		if(product.getTo() != null)ups.set("to", product.getTo());
		if(product.getToContinent() != null)ups.set("toContinent", product.getToContinent());
		if(product.getToCountry() != null)ups.set("toCountry", product.getToCountry());
		if(product.getDestinationType() != null)ups.set("destinationType", product.getDestinationType());
		if(product.getNature() != null)ups.set("nature", product.getNature());
		if(product.getContents() != null)ups.set("contents", product.getContents());
		if(product.getHighLights() != null)ups.set("highLights", product.getHighLights());
		if(product.getHighLightTitles() != null)ups.set("highLightTitles", product.getHighLightTitles());
		if(product.getLightColor() != null)ups.set("lightColor", product.getLightColor());
		if(product.getImages() != null)ups.set("images", product.getImages());
		if(product.getTitleImages() != null)ups.set("titleImages", product.getTitleImages());
		if(product.getDetailTitleImages() != null)ups.set("detailTitleImages", product.getDetailTitleImages());
		if(product.getStatus() != null)ups.set("status", product.getStatus());
		if(product.getProgress() != null)ups.set("progress", product.getProgress());
		ups.set("updatedTime", DateTime.now()) ;
		ups.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		UpdateResults<Product> results = datastore.update(query, ups);
		return results.getUpdatedCount();
	}

	@Override
	public Product queryBasicInfoById(String id) {
		Query<Product> query = datastore.createQuery(Product.class);
		query.field("id").equal(new ObjectId(id));
		query.retrievedFields(true, "id","pid","pName","subName","recommendWords","theme","tripDays","tripNights","tags","from","to","toContinent", "toCountry",
				"nature", "destinationType", "contents","highLights", "highLightTitles", "lightColor", "images","titleImages","detailTitleImages","status","progress");
		return query.get();
	}

}
