package com.ztravel.product.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.ActivityType;
import com.ztravel.common.enums.CouponStatus;
import com.ztravel.common.enums.GrantType;
import com.ztravel.common.enums.UserRangeType;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.back.activity.entity.Coupon;
import com.ztravel.product.dao.IActivityDao;

@Repository
public class ActivityDaoImpl implements IActivityDao {

	@Resource
	private Datastore datastore ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public Activity getActivityById(String id) {
		return datastore.createQuery(Activity.class).filter("_id",new ObjectId(id)).get();
	}

	@Override
	public Boolean updateActivityStatus(String id, ActivityStatus newStatus) {
		Query<Activity> query = datastore.createQuery(Activity.class).filter("_id", new ObjectId(id));
		UpdateOperations<Activity> ops = datastore.createUpdateOperations(Activity.class);
		ops.set("status", newStatus);
		ops.set("operator", redisClient.get(OperatorSidHolder.get()));
		ops.set("updateTime", new DateTime());
		return datastore.findAndModify(query, ops) != null;
	}

	@Override
	public Boolean updateActivityWithOutCoupons(Activity activity) {
		Query<Activity> query = datastore.createQuery(Activity.class).filter("_id", activity.getId());
		UpdateOperations<Activity> ops = datastore.createUpdateOperations(Activity.class);
		ops.set("name", activity.getName());
		ops.set("remark", activity.getRemark());
		ops.set("startTime", activity.getStartTime());
		ops.set("endTime", activity.getEndTime());
		ops.set("grantType", activity.getGrantType());
		ops.set("userRangType", activity.getUserRangType());
		ops.set("userRange", activity.getUserRange());
		ops.set("status", activity.getStatus());
		ops.set("operator", redisClient.get(OperatorSidHolder.get()));
		ops.set("updateTime", new DateTime());
		return datastore.findAndModify(query, ops) != null;
	}

	@Override
	public Boolean updateCoupons(String id, Map<String,Coupon> couponsMap,String couponNameString) {
		Query<Activity> query = datastore.createQuery(Activity.class).filter("_id", new ObjectId(id));
		UpdateOperations<Activity> ops = datastore.createUpdateOperations(Activity.class);
		ops.set("couponNameString", couponNameString);
		ops.set("coupons", couponsMap);
		ops.set("startGrantTime", new DateTime());
		ops.set("operator", redisClient.get(OperatorSidHolder.get()));
		ops.set("updateTime", new DateTime());
		return datastore.findAndModify(query, ops) != null;
	}

	@Override
	public String updateActivity(Activity activity) {
		Query<Activity> query = datastore.createQuery(Activity.class).filter("code", activity.getCode());
		String id = null;
		if(null == activity.getId()){
			id = datastore.updateFirst(query, activity, true).getNewId().toString();
		}else{
			id=activity.getId().toString();
			datastore.updateFirst(query, activity, true);
		}
		return id;
	}

	@Override
	public List<Activity> findByConditions(Map<String, String> pConditions,Integer pPage, Integer pPageSize) {
		pPage = pPage < 1 ? 1 : pPage;
		pPageSize = pPageSize < 1 ? 1 : pPageSize;
		Query<Activity> lvQuery = datastore.createQuery(Activity.class).order("-createTime");
		if(!CollectionUtils.isEmpty(pConditions)){
			for (Entry<String, String> entry : pConditions.entrySet()) {
				if(StringUtils.isNotBlank(entry.getValue())){
					if(entry.getKey().equals("startTime")){
						lvQuery.filter("startTime >= ", DateTimeUtil.convertStringToDate(entry.getValue()).toDate());
					}else if(entry.getKey().equals("endTime")){
						lvQuery.filter("endTime <= ", DateTimeUtil.convertStringToDate(entry.getValue()).plusDays(1).toDate());
					}else{
						lvQuery.field(entry.getKey()).contains(entry.getValue());
					}
					if(entry.getKey().equals("grantType")){
						lvQuery.field(entry.getKey()).equal(GrantType.valueOf(entry.getValue()));
					}
					if(entry.getKey().equals("userRangType")){
						lvQuery.field(entry.getKey()).equal(UserRangeType.valueOf(entry.getValue()));
					}
				}
			}
		}
		int offset = (pPage - 1) * pPageSize;
		lvQuery.offset(offset);
		lvQuery.limit(pPageSize);
		List<Activity> lvResult = lvQuery.asList();
		return lvResult;
	}

	@Override
	public long getCount() {
		return datastore.getCount(Activity.class);
	}

	@Override
	public Long getCountByConditions(Map<String, String> pConditions) {
		Query<Activity> lvQuery = datastore.createQuery(Activity.class).order("-updateTime");
		if(!CollectionUtils.isEmpty(pConditions)){
			for (Entry<String, String> entry : pConditions.entrySet()) {
				if(StringUtils.isNotBlank(entry.getValue())){
					if(entry.getKey().equals("startTime")){
						lvQuery.field(entry.getKey()).greaterThanOrEq(entry.getValue());
					}else if(entry.getKey().equals("endTime")){
						lvQuery.field(entry.getKey()).lessThanOrEq(entry.getValue());
					}else{
						lvQuery.field(entry.getKey()).contains(entry.getValue());
					}
				}
			}
		}
		return datastore.getCount(lvQuery);
	}

	@Override
	public Boolean deleteActivity(String id) {
		Query<Activity> query = datastore.createQuery(Activity.class).filter("_id", new ObjectId(id));
		return datastore.delete(query).getN() == 1;
	}

	@Override
	public Activity getActivityByCode(String code) {
		return datastore.createQuery(Activity.class).filter("code",code).get();
	}

	@Override
	public Coupon getCouponById(String activityId, String couponItemId)throws Exception {
		Activity activity = getActivityById(activityId);
		if(null != activity){
			return activity.getCoupons().get(couponItemId.trim());
		}
		return null;
	}

	@Override
	public Boolean deleteCouponById(String activityId, String couponItemId)throws Exception {
		Activity activity  = getActivityById(activityId);
		if(null != activity){
			Query<Activity> query = datastore.createQuery(Activity.class) ;
			query.field("_id").equal(new ObjectId(activityId.trim())) ;
			UpdateOperations<Activity> uOps =  datastore.createUpdateOperations(Activity.class);
			Coupon coupon = getCouponById(activityId, couponItemId);
			if(StringUtils.isNotBlank(activity.getCouponNameString())){
				String couponName = activity.getCouponNameString().replaceFirst(coupon.getName(), "").replaceAll("[,]+", ",");
				if(couponName.endsWith(",")){
					couponName = couponName.substring(0, couponName.length() - 1);
				}
				if(couponName.startsWith(",")){
					couponName = couponName.substring(1, couponName.length());
				}
				uOps.set("couponNameString", couponName);
			}
			uOps.set("operator", redisClient.get(OperatorSidHolder.get()));
			uOps.set("updateTime", new DateTime());
			uOps.set("coupons."+ couponItemId.trim()+".isDelete",true);//逻辑删除
			return datastore.findAndModify(query, uOps) != null;
		}
		return false;
	}

	@Override
	public Integer updateCoupon(String activityId, Coupon coupon)throws Exception {
		Query<Activity> query = datastore.createQuery(Activity.class) ;
		query.field("_id").equal(new ObjectId(activityId)) ;
		UpdateOperations<Activity> uOps =  datastore.createUpdateOperations(Activity.class);
		uOps.set("operator", redisClient.get(OperatorSidHolder.get()));
		uOps.set("updateTime", new DateTime());
		uOps.set("coupons."+coupon.getCouponId(),coupon);
		return datastore.update(query, uOps).getUpdatedCount();
	}


	@Override
	public Integer updateCouponNum(String activityId, String couponId,Integer totalNum,Integer unit)throws Exception {
		Query<Activity> query = datastore.createQuery(Activity.class) ;
		query.field("_id").equal(new ObjectId(activityId)) ;
		UpdateOperations<Activity> uOps =  datastore.createUpdateOperations(Activity.class);
		uOps.set("operator", redisClient.get(OperatorSidHolder.get()));
		uOps.set("updateTime", new DateTime());
		if(null != totalNum){
			uOps.set("coupons."+couponId.trim()+".totalNum",totalNum);
		}
		uOps.set("coupons."+couponId.trim()+".unit",unit);
		return datastore.update(query, uOps).getUpdatedCount();
	}

	@Override
	public Map<String, Coupon> getCoupons(String activityId) throws Exception {
		Activity activity = getActivityById(activityId);
		Map<String,Coupon> coupons = null;
		if(null != activity){
			coupons = activity.getCoupons();
		}
		return coupons;
	}

	@Override
	public Integer updateCouponStauts(String activityId, String couponId,
			CouponStatus status) throws Exception {
		Query<Activity> query = datastore.createQuery(Activity.class) ;
		query.field("_id").equal(new ObjectId(activityId)) ;
		UpdateOperations<Activity> uOps =  datastore.createUpdateOperations(Activity.class);
		uOps.set("operator", redisClient.get(OperatorSidHolder.get()));
		uOps.set("updateTime", new DateTime());
		uOps.set("coupons."+couponId.trim()+".status",status);
		return datastore.update(query, uOps).getUpdatedCount();
	}

	@Override
	public List<Activity> getAllEffecSysActivity(String memberId)throws Exception {
		Query<Activity> query = datastore.createQuery(Activity.class) ;
		query.field("status").equal(ActivityStatus.EFFECTIVE);
		query.field("type").equal(ActivityType.SYSTEMSEND);
		return query.asList();
	}

	@Override
	public Boolean updateCouponUsedNum(String activityId, String couponId,Integer newSendNum) throws Exception {
		Query<Activity> query = datastore.createQuery(Activity.class) ;
		query.field("_id").equal(new ObjectId(activityId)) ;
		UpdateOperations<Activity> uOps =  datastore.createUpdateOperations(Activity.class);
		uOps.set("coupons."+couponId.trim()+".usedNum",newSendNum);
		uOps.set("coupons."+couponId.trim()+".updated",new DateTime());
		return datastore.findAndModify(query, uOps) != null;
	}

    @Override
    public List<Activity> findByConditions(Map<String, String> pConditions) {
        Query<Activity> lvQuery = datastore.createQuery(Activity.class).order("-createTime");
        if(!CollectionUtils.isEmpty(pConditions)){
            for (Entry<String, String> entry : pConditions.entrySet()) {
                if(StringUtils.isNotBlank(entry.getValue())){
                    if(entry.getKey().equals("startTime")){
                        lvQuery.filter("startTime >= ", DateTimeUtil.convertStringToDate(entry.getValue()).toDate());
                    }else if(entry.getKey().equals("endTime")){
                        lvQuery.filter("endTime <= ", DateTimeUtil.convertStringToDate(entry.getValue()).plusDays(1).toDate());
                    }else{
                        lvQuery.field(entry.getKey()).contains(entry.getValue());
                    }
                    if(entry.getKey().equals("grantType")){
                        lvQuery.field(entry.getKey()).equal(GrantType.valueOf(entry.getValue()));
                    }
                    if(entry.getKey().equals("userRangType")){
                        lvQuery.field(entry.getKey()).equal(UserRangeType.valueOf(entry.getValue()));
                    }
                }
            }
        }
        List<Activity> lvResult = lvQuery.asList();
        return lvResult;
    }

}
