package com.ztravel.product.timming.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.CouponStatus;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.timming.dao.IActivityTimmingDao;

@Repository
public class ActivityTimmingDaoImpl implements IActivityTimmingDao {
	@Resource
	private Datastore datastore;


	@Override
	public List<Activity> getAllActivity() throws Exception {
		Query<Activity> query = datastore.createQuery(Activity.class);
		query.field("status").equal(ActivityStatus.EFFECTIVE);
		return query.asList();
	}

	@Override
	public Boolean updateActivityStatus(String id, ActivityStatus newStatus) {
		Query<Activity> query = datastore.createQuery(Activity.class).filter("_id", new ObjectId(id));
		UpdateOperations<Activity> ops = datastore.createUpdateOperations(Activity.class);
		ops.set("status", newStatus);
		ops.set("operator", "定时器");
		ops.set("updateTime", new DateTime());
		return datastore.findAndModify(query, ops) != null;
	}

	@Override
	public Boolean updateCouponStauts(String activityId, String couponId,
			CouponStatus status) throws Exception {
		Query<Activity> query = datastore.createQuery(Activity.class) ;
		query.field("_id").equal(new ObjectId(activityId)) ;
		UpdateOperations<Activity> uOps =  datastore.createUpdateOperations(Activity.class);
		uOps.set("coupons."+couponId.trim()+".updated", new DateTime());
		uOps.set("coupons."+couponId.trim()+".updatedby", "定时器");
		uOps.set("coupons."+couponId.trim()+".status",status);
		return datastore.findAndModify(query, uOps) != null;
	}

	@Override
	public List<Activity> getAllEndActivity() throws Exception {
		Query<Activity> query = datastore.createQuery(Activity.class);
		query.field("status").equal(ActivityStatus.EDN);
		return query.asList();
	}
}
