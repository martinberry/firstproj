/**
 *
 */
package com.ztravel.product.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.mongodb.WriteResult;
import com.ztravel.product.dao.IHotelDao;
import com.ztravel.product.back.hotel.entity.HotelEntity;

/**
 * @author
 * 酒店列表查询
 */
@Repository
public class HotelDaoImpl implements IHotelDao {
	@Resource
	private Datastore datastore;

	@SuppressWarnings("rawtypes")
	@Override
	public List<HotelEntity> searchHotelsWithPaging(Map params) {
		Query<HotelEntity> query = datastore.createQuery(HotelEntity.class);
		if( params.containsKey("hotelNameCn") ){
			query.field("hotelNameCn").contains((String)params.get("hotelNameCn"));
		}
		if( params.containsKey("nation") ){
			query.field("nation").equal(params.get("nation"));
		}
		if( params.containsKey("city") ){
			query.field("city").equal(params.get("city"));
		}
		if( params.containsKey("isComplete") ){
			query.field("isComplete").equal(params.get("isComplete"));
		}
		query.order("-createTime");
		query.offset((int)params.get("offset"));
		query.limit((int)params.get("limit"));

		List<HotelEntity> result = query.asList();
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countHotels(Map params) {
		Query<HotelEntity> query = datastore.createQuery(HotelEntity.class);
		if( params.containsKey("hotelNameCn") ){
			query.field("hotelNameCn").contains((String)params.get("hotelNameCn"));
		}
		if( params.containsKey("nation") ){
			query.field("nation").equal(params.get("nation"));
		}
		if( params.containsKey("city") ){
			query.field("city").equal(params.get("city"));
		}
		if( params.containsKey("isComplete") ){
			query.field("isComplete").equal(params.get("isComplete"));
		}
		return (int)query.countAll();
	}

	@Override
	public int deleteById(String id) {
		WriteResult writeResult = datastore.delete(HotelEntity.class, new ObjectId(id));
		return writeResult.getN();
	}

	@Override
	public HotelEntity getHotelById(String id) {
		Query<HotelEntity> q = datastore.createQuery(HotelEntity.class).filter("_id", new ObjectId(id)) ;
		return q.get();
	}

}
