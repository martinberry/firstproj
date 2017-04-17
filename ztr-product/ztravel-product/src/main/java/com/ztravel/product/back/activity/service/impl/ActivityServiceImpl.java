package com.ztravel.product.back.activity.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.ActivityType;
import com.ztravel.common.enums.CouponStatus;
import com.ztravel.common.enums.GrantType;
import com.ztravel.common.enums.ProductRangeType;
import com.ztravel.common.enums.UserRangeType;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.util.GenerateActivityCode;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.back.activity.entity.Coupon;
import com.ztravel.product.back.activity.service.IActivityService;
import com.ztravel.product.back.activity.service.IVoucherService;
import com.ztravel.product.back.activity.vo.ActivityVo;
import com.ztravel.product.back.activity.vo.CouponVo;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.dao.IActivityDao;

@Service
public class ActivityServiceImpl implements IActivityService {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(ActivityServiceImpl.class);
	private static final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	IActivityDao activityDaoImpl;

	@Resource
	ProductService productServiceImpl;

	@Resource
	IMemberClientService memberClientServiceImpl;

	@Resource
	IVoucherService voucherServiceImpl;

	@Override
	public AjaxResponse toEffect(ActivityVo activityVo) throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.ACTIVITY_EFFEC_FAILED, ActivityConstants.ACTIVITY_EFFEC_FAILED_MSG);
		LOGGER.debug("开始对活动{}进行生效操作",activityVo.getId());
		Activity activity = activityDaoImpl.getActivityById(activityVo.getId());
		if(null != activity){
			activity = reSetActivityByVo(activityVo, activity);
			if(DateTimeUtil.dayBefore(new DateTime().toDate(),DateTimeUtil.addDay(activity.getEndTime(), 1).toDate())){
				activity.setStatus(ActivityStatus.EFFECTIVE);
				if(null == activity.getStartTime()){
					activity.setStartTime(new DateTime());
				}
				String effectResult = activityDaoImpl.updateActivity(activity);
				if(null != effectResult){
					response.setRes_code(ActivityConstants.ACTIVITY_EFFEC_SUCCESS);
					response.setRes_msg(activityVo.getId());
					LOGGER.debug("活动{}生效操作成功",activityVo.getId());
				}
			}else{
				LOGGER.debug("生效操作失败,系统当前时间已超过活动{}设置的截止时间",activityVo.getId());
				response.setRes_msg("代金券有效期错误");
			}
		}
		return response;
	}

	@Override
	public AjaxResponse toTerminate(String id) throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.ACTIVITY_TERMINATE_FAILED, ActivityConstants.ACTIVITY_TERMINATE_FAILED_MSG);
		LOGGER.debug("开始对活动{}进行终止操作",id);
		Activity activity = activityDaoImpl.getActivityById(id);
		if(null != activity){
			activityDaoImpl.updateActivityStatus(id, ActivityStatus.TERMINATED);
			LOGGER.debug("活动{}终止操作成功",id);
			response.setRes_code(ActivityConstants.ACTIVITY_TERMINATE_SUCCESS);
			response.setRes_msg(null);
		}else{
			LOGGER.debug("活动{}终止操作失败",id);
			response.setRes_msg("活动已被删除");
		}
		return response;
	}

	@Override
	public AjaxResponse checkMobiles(String mobiles) throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.ACTIVITY_CONFIGURE_USER_FAILED, ActivityConstants.ACTIVITY_CONFIGURE_USER_FAILED_MSG);
		LOGGER.debug("手动添加用户{}",mobiles);
		if(StringUtils.isNotBlank(mobiles)){
			Pattern pattern = Pattern.compile("^(((?:13\\d|14\\d|15\\d|17\\d|18\\d)\\d{8},)*)((?:13\\d|14\\d|15\\d|17\\d|18\\d)\\d{8})$");
			Matcher matach = pattern.matcher(mobiles);
			if(!matach.matches()){
				response.setRes_msg("用户信息错误");
				return response;
			}
			List<String> userList = Arrays.asList(mobiles.split(","));
			if(!CollectionUtils.isEmpty(userList)){
				if(userList.size() > 100){
					response.setRes_msg("最多添加100个用户");
					return response;
				}
				for(String user : userList){
					Boolean exist = memberClientServiceImpl.isMobileAlreadyExists(user);
					if(!exist){
						response.setRes_msg("用户"+user+"不存在");
						return response;
					}
					if(userList.indexOf(user) != userList.lastIndexOf(user)){//检验是否重复输入
						response.setRes_msg("重复输入"+user+"");
						return response;
					}
				}
				response.setRes_code(ActivityConstants.ACTIVITY_CONFIGURE_USER_SUCCESS);
			}
		}else{
			response.setRes_msg("用户信息必填");
		}
		return response;
	}

	@Override
	public String saveActivity(ActivityVo activityVo) throws Exception {
		String code = activityVo.getCode();
		Activity activity = null;
		if(StringUtils.isBlank(activityVo.getCode())){//新增活动
			activity = buildActivityByVo(activityVo);
		}else{//编辑活动
			activity = activityDaoImpl.getActivityByCode(code);
			activity = reSetActivityByVo(activityVo, activity);
		}
		String activityId  = activityDaoImpl.updateActivity(activity);
		return activityId;
	}

	/**
	 * 编辑活动时,将查询到的ACtivity转换为Vo对象
	 * */
	@Override
	public ActivityVo convert2Vo(Activity activity) throws Exception{
		ActivityVo activityVo = new ActivityVo();
		if(null != activity){
			activityVo.setCode(activity.getCode());
			activityVo.setGrantType(activity.getGrantType().name());
			activityVo.setName(activity.getName());
			activityVo.setRemark(activity.getRemark());
			activityVo.setStatus(activity.getStatus().name());
			activityVo.setStatusDesc(activity.getStatus().getDesc());
			activityVo.setType(activity.getType().name());
			activityVo.setId(activity.getId().toString());
			activityVo.setOperator(activity.getOperator());

			if(null != activity.getStartTime()){
				activityVo.setStartTime(DateTimeUtil.convertDateToString("yyyy-MM-dd", activity.getStartTime()));
				activityVo.setStartHourFrom(String.format("%02d", activity.getStartTime().getHourOfDay()));
				activityVo.setStartMinFrom(String.format("%02d", activity.getStartTime().getMinuteOfHour()));
			}
			if(null != activity.getEndTime()){
				activityVo.setEndTime(DateTimeUtil.convertDateToString("yyyy-MM-dd", activity.getEndTime()));
				activityVo.setEndHourTo(String.format("%02d", activity.getEndTime().getHourOfDay()));
				activityVo.setEndMinTo(String.format("%02d", activity.getEndTime().getMinuteOfHour()));
			}
			if(!CollectionUtils.isEmpty(activity.getCoupons())){
				activityVo.setCoupons(activity.getCoupons());
			}
			if(StringUtils.isNotBlank(activity.getCouponNameString())){
				StringBuffer couponNames = new StringBuffer();
				List<String> couponNameList = Arrays.asList(activity.getCouponNameString().split(","));
				for(int i = 0 ; i < couponNameList.size();i++){
					if(i < 2){
						couponNames.append(couponNameList.get(i) + ",");
					}else{
						couponNames.append("...");
						break;
					}
				}
				activityVo.setCouponNames(couponNames.toString());
			}

			activityVo.setTypeDesc(activity.getType().getDesc());
			if(activity.getGrantType().equals(GrantType.BATCHCONFIG)){
				activityVo.setUserRangType(activity.getUserRangType().name());
				activityVo.setUserRange(activity.getUserRangType().getDesc());
			}else{
				if(!CollectionUtils.isEmpty(activity.getUserRange())){
					StringBuffer userRange = new StringBuffer();
					StringBuffer userRangeComplete = new StringBuffer();
					for(int i = 0 ;i < activity.getUserRange().size();i++){
						if(i < 2){
							userRange.append(activity.getUserRange().get(i) + ",");
						}else{
							userRange.append("...");
							break;
						}
					}
					for(String s : activity.getUserRange()){
						userRangeComplete.append(s + ",");
					}
					activityVo.setUserRangeComplete(userRangeComplete.toString().substring(0, userRangeComplete.lastIndexOf(",")));
					activityVo.setUserRange(userRange.toString());
				}
			}
		}
		return activityVo;
	}

	/**
	 * 编辑活动时,根据页面传入的activityVo对象对activity进行属性重设置
	 * */
	public Activity reSetActivityByVo(ActivityVo activityVo,Activity activity){
		LOGGER.debug("活动编辑页传入的activityVo对象参数{}",JSONObject.toJSONString(activityVo));
		activity.setName(activityVo.getName());
		activity.setRemark(activityVo.getRemark());
		if(StringUtils.isNotBlank(activityVo.getEndTime()) && StringUtils.isNotBlank(activityVo.getEndHourTo()) && StringUtils.isNoneBlank(activityVo.getEndMinTo())){
			activity.setEndTime(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, activityVo.getEndTime() + " " + activityVo.getEndHourTo() + ":" + activityVo.getEndMinTo() + ":00"));
		}
		if(StringUtils.isNotBlank(activityVo.getStartTime())){
			if(StringUtils.isBlank(activityVo.getStartHourFrom()) || StringUtils.isBlank(activityVo.getStartMinFrom())){
				activity.setStartTime(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, activityVo.getStartTime() + " " + "00:00:00"));
			}else{
				activity.setStartTime(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, activityVo.getStartTime() + " " + activityVo.getStartHourFrom() + ":" + activityVo.getStartMinFrom() + ":00"));
			}
		}else{
			activity.setStartTime(null);
		}
		if(activityVo.getGrantType().equals(GrantType.BATCHCONFIG.name())){//批量设置
			activity.setGrantType(GrantType.BATCHCONFIG);
			activity.setUserRangType(UserRangeType.valueOf(activityVo.getUserRangType()));
		}else{
			activity.setGrantType(GrantType.MANUALADD);
			if(StringUtils.isNoneBlank(activityVo.getUserRange())){
				activity.setUserRange(Arrays.asList(activityVo.getUserRange().split(",")));
			}
		}
		activity.setUpdateTime(new DateTime());
		activity.setOperator(redisClient.get(OperatorSidHolder.get()));
		activity.setStatus(ActivityStatus.DRAFT);
		LOGGER.debug("编辑后的activity对象{}",JSONObject.toJSONString(activityVo));
		return activity;
	}

	/**
	 * 新建Activity时,将页面传入的activityVo对象转换为Activity
	 * */
	public Activity buildActivityByVo(ActivityVo activityVo) throws Exception{
		LOGGER.debug("开始activityVo转换为Activity,传入的activityVo对象参数{}",JSONObject.toJSONString(activityVo));
		Activity activity = new Activity();
		activity.setName(activityVo.getName());
		String code = GenerateActivityCode.generateActivityCode();//自动生成HD+6位阿拉伯数字,递增
		activity.setCode(code);
		if(activityVo.getGrantType().equals(GrantType.BATCHCONFIG.name())){//批量设置
			activity.setGrantType(GrantType.BATCHCONFIG);
			activity.setUserRangType(UserRangeType.valueOf(activityVo.getUserRangType()));
		}else{
			activity.setGrantType(GrantType.MANUALADD);
			if(StringUtils.isNotBlank(activityVo.getUserRange())){
				activity.setUserRange(Arrays.asList(activityVo.getUserRange().split(",")));
			}
		}
		activity.setCreateTime(new DateTime());
		if(StringUtils.isNotBlank(activityVo.getEndTime()) && StringUtils.isNotBlank(activityVo.getEndHourTo()) && StringUtils.isNoneBlank(activityVo.getEndMinTo())){
			activity.setEndTime(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, activityVo.getEndTime() + " " + activityVo.getEndHourTo() + ":" + activityVo.getEndMinTo() + ":00"));
		}
		if(StringUtils.isNotBlank(activityVo.getStartTime())){
			if(StringUtils.isBlank(activityVo.getStartHourFrom()) || StringUtils.isBlank(activityVo.getStartMinFrom())){
				activity.setStartTime(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, activityVo.getStartTime() + " " + "00:00:00"));
			}else{
				activity.setStartTime(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, activityVo.getStartTime() + " " + activityVo.getStartHourFrom() + ":" + activityVo.getStartMinFrom() + ":00"));
			}
		}else{
			activity.setStartTime(null);
		}
		activity.setCreator(redisClient.get(OperatorSidHolder.get()));
		activity.setOperator(redisClient.get(OperatorSidHolder.get()));
		activity.setRemark(activityVo.getRemark());
		activity.setStatus(ActivityStatus.DRAFT);
		activity.setType(ActivityType.valueOf(activityVo.getType()));
		activity.setUpdateTime(new DateTime());
		LOGGER.debug("转换后的Activity对象{}",JSONObject.toJSONString(activity));
		return activity;
	}

	@Override
	public Map<String,Object> searchActivities(Map<String,String> params,Integer pageNo,Integer pageSize) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		List<Activity> list = activityDaoImpl.findByConditions(params,pageNo,pageSize);
		List<ActivityVo> voList = new ArrayList<ActivityVo>();
		if(!CollectionUtils.isEmpty(list)){
			for(Activity activity : list){
				ActivityVo activityVo = convert2Vo(activity);
				voList.add(activityVo);
			}
		}
		Long totalNum = activityDaoImpl.getCountByConditions(params);
		result.put("totalItemCount", totalNum == null ? 0 : totalNum.intValue());
		int totalPageCount = totalNum.intValue()%pageSize == 0 ? totalNum.intValue()/pageSize : totalNum.intValue()/pageSize + 1 ;
		totalPageCount = totalPageCount == 0 ? 1 : totalPageCount ;
		result.put("totalPageCount",totalPageCount);
		result.put("pageNo", pageNo);
		result.put("pageSize",pageSize);
		result.put("activities", voList);
		return result;
	}

	@Override
	public Activity getActivityById(String activityId) throws Exception {
		Assert.hasText(activityId, "查询Activity时传入ID不能为空");
		Activity activity = activityDaoImpl.getActivityById(activityId);
		return activity;
	}

	@Override
	public AjaxResponse addCoupons(String activityId,List<Coupon> coupons)throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.ACTIVITY_EDITCOUPON_FAILED, ActivityConstants.ACTIVITY_EDITCOUPON_FAILED_MSG);
		Activity activity  = getActivityById(activityId);
		Integer totalCount = 0;
		if(null != activity && !CollectionUtils.isEmpty(coupons)){
			Map<String,Coupon> couponMap = activity.getCoupons();
			if(CollectionUtils.isEmpty(couponMap)){
				couponMap = new HashMap<String,Coupon>();
			}
			StringBuffer couponNameString = new StringBuffer();
			if(StringUtils.isNotBlank(activity.getCouponNameString())){
				couponNameString.append(activity.getCouponNameString());
			}
			if(activity.getType().name().equals(ActivityType.CUSTOMERGET.name())){
				totalCount = memberClientServiceImpl.countAll();
			}
			for(Coupon coupon : coupons){
				if(StringUtils.isBlank(couponNameString)){//冗余字段信息同步
					couponNameString.append(coupon.getName());
				}else{
					couponNameString.append(","+coupon.getName());
				}
				if(coupon.getProductRangeType().equals(ProductRangeType.ALLPRODUCTS.name())){
					coupon.setSupportProducts(productServiceImpl.getAllProductIds());
				}
				if(activity.getType().name().equals(ActivityType.CUSTOMERGET.name())){
					if(coupon.getPrice()!=null){
                       coupon.setTotalNum(100);
					}else{
						coupon.setTotalNum(totalCount * coupon.getUnit());
					}

				}else{
					coupon.setTotalNum(null);
				}
				couponMap.put(coupon.getCouponId().toString(), coupon);
			}
			Boolean addResult = activityDaoImpl.updateCoupons(activityId, couponMap,couponNameString.toString());
			if(addResult){
				response.setRes_code(ActivityConstants.ACTIVITY_ADDCOUPON_SUCCESS);
				response.setRes_msg(null);
			}
		}
		return response;
	}

	@Override
	public AjaxResponse removeCoupon(String couponItemId, String activityId)throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.ACTIVITY_DELETECOUPON_FAILED, ActivityConstants.ACTIVITY_DELETECOUPON_FAILED_MSG);
		Boolean deleteResult = activityDaoImpl.deleteCouponById(activityId, couponItemId);
		if(deleteResult){
			response.setRes_code(ActivityConstants.ACTIVITY_DELETECOUPON_SUCCESS);
			response.setRes_msg(null);
		}
		return response;
	}

	@Override
	public Coupon getCoupon(String activityId, String couponItemId)throws Exception {
		Assert.hasText(activityId, "查询代金券传入的活动ID为空");
		Assert.hasText(couponItemId, "查询的代金券ID为空");
		return activityDaoImpl.getCouponById(activityId, couponItemId);
	}

	@Override
	public AjaxResponse updateCoupon(String activityId, Coupon coupon)throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.ACTIVITY_EDITCOUPON_FAILED, ActivityConstants.ACTIVITY_EDITCOUPON_FAILED_MSG);
		Integer updateCount = activityDaoImpl.updateCoupon(activityId, coupon);
		if(updateCount == 1){
			response.setRes_code(ActivityConstants.ACTIVITY_EDITCOUPON_SUCCESS);
			response.setRes_msg(null);
		}
		return response;
	}

	@Override
	public Map<String, Coupon> getCouponsByActivityId(String activityId) throws Exception {
		Assert.hasText(activityId, "查询活动所有代金券传入的activityID为空");
		return activityDaoImpl.getCoupons(activityId);
	}

	@Override
	public AjaxResponse updateCouponNum(String activityId,String couponItemId,Integer totalNum,Integer unit) throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.ACTIVITY_EDITCOUPON_FAILED, ActivityConstants.ACTIVITY_EDITCOUPON_FAILED_MSG);
		Integer updateCount = activityDaoImpl.updateCouponNum(activityId, couponItemId, totalNum, unit);
		if(updateCount == 1){
			response.setRes_code(ActivityConstants.ACTIVITY_EDITCOUPON_SUCCESS);
			response.setRes_msg(null);
		}
		return response;
	}

	@Override
	public AjaxResponse teminateCoupon(String activityId, String couponItemId) throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.COUPON_TERMINATE_FAILED, ActivityConstants.COUPON_TERMINATE_FAILED_MSG);
		Integer updateCount = activityDaoImpl.updateCouponStauts(activityId, couponItemId, CouponStatus.TERMINATED);
		if(updateCount == 1){
			response.setRes_code(ActivityConstants.COUPON_TERMINATE_SUCCESS);
			response.setRes_msg(null);
		}
		return response;
	}

	@Override
	public AjaxResponse sendCoupon(String activityId) throws Exception {
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.COUPON_SENDDING_FAILED, ActivityConstants.COUPON_SENDDING_FAILED_MSG);
		Activity activity = activityDaoImpl.getActivityById(activityId);
		if(null != activity){
			String activityType = activity.getType().name();
			Map<String ,Coupon> coupons = activity.getCoupons();
			Map<String,Coupon> newCoupons = new HashMap<String,Coupon>();
			Map<String,Coupon> addCoupons=new HashMap<String,Coupon>();
			if(!CollectionUtils.isEmpty(coupons)){
				long nowTime = new Date().getTime();
				Integer sendNum = 0;
				for(Entry<String, Coupon> entry : coupons.entrySet()){
					Coupon coupon = entry.getValue();
					if(activityType.equals(ActivityType.CUSTOMERGET.name()) && (coupon.getTotalNum() - coupon.getUsedNum()) <= 0){//用户领券类型的活动,券已发完
						LOGGER.info("活动[{}]为[{}],券[{}]预发数量为[{}],已发送数量为[{}]",activity.getName(),activity.getType().getDesc(),coupon.getCouponCode(),coupon.getTotalNum(),coupon.getUsedNum());
						continue;
					}


					if(entry.getValue().getStatus() == CouponStatus.NOSENDDING && coupon.getValidTimeTo().getMillis() >= new Date().getTime() && !coupon.getIsDelete()){//未过期的未发放代金券

						addCoupons.put(coupon.getCouponId(), coupon);

						if(coupon.getValidTimeFrom() == null || StringUtils.isBlank(coupon.getValidTimeFrom().toString())){
							coupon.setValidTimeFrom(DateTimeUtil.getDate(nowTime,DateTimeUtil.DATE_TIME_PATTERN ));
						}else{
							if(coupon.getValidTimeFrom().getMillis() < new Date().getTime()){
								coupon.setValidTimeFrom(DateTimeUtil.getDate(nowTime,DateTimeUtil.DATE_TIME_PATTERN ));
							}
						}
						if(coupon.getValidTimeFrom().getMillis() > coupon.getValidTimeTo().getMillis()){
							LOGGER.info("券[{}]有效日期的起始时间超过截止时间",coupon.getCouponCode());
							response.setRes_msg(ActivityConstants.COUPON_VALIADTIME_ERROR_MSG);
							break;
						}
						if(activity.getGrantType().name().equals(GrantType.BATCHCONFIG.name())){
							if(activity.getUserRangType().name().equals(UserRangeType.CURRENTALL.name())){
								coupon.setStatus(CouponStatus.FINISHED);
							}else if(activity.getUserRangType().name().equals(UserRangeType.DN.name())){
								coupon.setStatus(CouponStatus.FINISHED);
							}
							else{
								coupon.setStatus(CouponStatus.SENDDING);
							}
						}else{
							coupon.setStatus(CouponStatus.FINISHED);
						}
						coupon.setUpdatedby(redisClient.get(OperatorSidHolder.get()));
						coupon.setUpdated(new DateTime());
						coupon.setGrantTime(new DateTime());
						sendNum ++ ;
					}
					newCoupons.put(coupon.getCouponId(), coupon);
				}
				Boolean sendResult = activityDaoImpl.updateCoupons(activityId, newCoupons, activity.getCouponNameString());

				UserRangeType userRangeType=activity.getUserRangType();
				if(userRangeType!=null){
					if(userRangeType.equals(UserRangeType.DN)&&sendNum>0){
						Coupon coupontmp=new Coupon();
						CouponVo couponVo=new CouponVo();
						for(String key:addCoupons.keySet()){
							coupontmp=addCoupons.get(key);
							couponVo.setCouponId(coupontmp.getCouponId());
							couponVo.setCouponCode(coupontmp.getCouponCode());
							couponVo.setPrice(coupontmp.getPrice());
							couponVo.setTotalNum(coupontmp.getTotalNum());
							couponVo.setActivityId(activityId);
							response=voucherServiceImpl.updateVoucher(couponVo);
						}
					}
				}
				if(sendNum > 0){
					if(sendResult){
						response.setRes_code(ActivityConstants.COUPON_SENDDING_SUCCESS);
						response.setRes_msg(null);
					}else{
						response.setRes_code(ActivityConstants.COUPON_SENDDING_FAILED);
						response.setRes_msg(ActivityConstants.COUPON_SENDDING_FAILED_MSG);
					}
				}else{
					response.setRes_msg("无券可送领");
				}

			}
		}
		return response;
	}
}
