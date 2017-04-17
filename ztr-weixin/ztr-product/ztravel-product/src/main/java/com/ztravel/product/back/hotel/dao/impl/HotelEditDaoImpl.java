package com.ztravel.product.back.hotel.dao.impl;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.ztravel.product.back.hotel.convert.HotelConvert;
import com.ztravel.product.back.hotel.dao.HotelEditDao;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.wo.HotelEntityWo;

@Repository
public class HotelEditDaoImpl implements HotelEditDao {

	@Resource
	private Datastore datastore ;

	@Override
	public HotelEntity getHotelById(String id) {
		return datastore.get(HotelEntity.class, new ObjectId(id));
	}

	@Override
	public String addHotel(HotelEntity hotelEntity) {
		return datastore.save(hotelEntity).getId().toString();
	}

	@Override
	public void updateHotel(HotelEntityWo hotelEntityWo) {
		Query<HotelEntity> query = datastore.createQuery(HotelEntity.class);
		query.field("id").equal(new ObjectId(hotelEntityWo.getId()));

		UpdateOperations<HotelEntity> ops = datastore.createUpdateOperations(HotelEntity.class);
		HotelConvert.convertToUpdateOperations(hotelEntityWo, ops);

		DateTime now = new DateTime() ;
		ops.set("updateTime",now);

		datastore.findAndModify(query, ops);
	}

}
