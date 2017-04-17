package com.ztravel.product.client.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.site.lookup.util.StringUtils;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.entity.CouponBookVo;
import com.ztravel.common.entity.CouponSnapshot;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.ActivityType;
import com.ztravel.common.enums.CouponBookStatus;
import com.ztravel.common.enums.CouponStatus;
import com.ztravel.common.enums.GrantType;
import com.ztravel.common.enums.ProductRangeType;
import com.ztravel.common.enums.UserRangeType;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.back.activity.entity.Coupon;
import com.ztravel.product.client.entity.ActivityClientEntity;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.client.service.IActivityClientService;
import com.ztravel.product.client.service.IVoucherClientService;
import com.ztravel.product.dao.IActivityDao;

@Service
public class ActivityClientServiceImpl implements IActivityClientService {

	@Resource
	IActivityDao activityDao;

	@Resource
	IVoucherClientService voucherClientService;

	@Resource
	IMemberClientService memberClientServiceImpl;

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(ActivityClientServiceImpl.class);

@Override
public DateTime getActivityEndTimeById(String activityId){
	Activity activity=activityDao.getActivityById(activityId);
	DateTime endtime=activity.getEndTime();
	return endtime;
}


	@Override
	public CouponClientEntity getCouponById(String activityId, String couponId){
		LOGGER.info("调用ACtivityClientService 查询活动{}的代金券{}信息",activityId,couponId);
		CouponClientEntity couponClientEntity = null;
		try {
			Coupon coupon = activityDao.getCouponById(activityId, couponId);
			couponClientEntity = coupon2ClientEntity(coupon);
		} catch (Exception e) {

		}
		LOGGER.info("活动{}的代金券{}详细信息为[{}]",activityId,couponId,TZBeanUtils.describe(couponClientEntity));
		return couponClientEntity;
	}

	@Override
	public CouponClientEntity getCouponByIdWithProductName(String activityId, String couponId){
		LOGGER.info("调用ACtivityClientService 查询活动{}的代金券{}信息",activityId,couponId);
		CouponClientEntity couponClientEntity = null;
		try {
			Coupon coupon = activityDao.getCouponById(activityId, couponId);
			couponClientEntity = coupon2ClientEntityWithProductName(coupon);
		} catch (Exception e) {

		}
		LOGGER.info("活动{}的代金券{}详细信息为[{}]",activityId,couponId,TZBeanUtils.describe(couponClientEntity));
		return couponClientEntity;
	}

	/**
	 * 获取某个用户的所有可参与的活动信息
	 * @param memberId : 用户ID
	 * @param registerTime 用户注册时间
	 * */
	@Override
	public List<ActivityClientEntity> getAllCouponsByMemberId(String memberId,DateTime registerTime){
		List<ActivityClientEntity> activityClientList = null;
		try {
			List<Activity> activityList = activityDao.getAllEffecSysActivity(memberId);
			activityList = eliminateDNActivity(activityList) ;
			activityClientList = convert2ClientEntity(activityList, memberId,registerTime);
		} catch (Exception e) {
			LOGGER.info("查询用户{}的代金券出现异常",memberId,e);
		}
		LOGGER.info("ActivityClientService查询的代金券信息[{}]",TZBeanUtils.describe(activityClientList));
		return activityClientList;
	}

	private List<Activity> eliminateDNActivity(List<Activity> activityList){
		List<Activity> eliminateDN = Lists.newArrayList() ;
		if(!CollectionUtils.isEmpty(activityList)){
			for(Activity acivity : activityList){
				if(acivity.getUserRangType() != UserRangeType.DN){
					eliminateDN.add(acivity) ;
				}
			}
		}
		return eliminateDN ;
	}

	/**
	 * 返回用户满足条件的券信息
	 * @param activityList : 已生效的系统送券类型活动
	 * @param memberId : 用户ID
	 * @param registerTime :用户注册时间,用于筛选券
	 * @return 用户可以获得的券信息,在用户登陆时,进行发放
	 * */
	List<ActivityClientEntity> convert2ClientEntity(List<Activity> activityList,String memberId,DateTime registerTime){
		List<ActivityClientEntity> activityClientList = new ArrayList<ActivityClientEntity>();
		if(!CollectionUtils.isEmpty(activityList)){
			for(Activity activity : activityList){//批量发的活动
				ActivityClientEntity activityClientEntity = new ActivityClientEntity();
				List<CouponClientEntity> couponClientEntityList = new ArrayList<>();
				activityClientEntity.setActivityId(activity.getId().toString());
				Map<String,Coupon> couponsMap = activity.getCoupons();
				if(CollectionUtils.isEmpty(couponsMap)){//活动没有券
					continue;
				}
				if(activity.getGrantType().equals(GrantType.BATCHCONFIG)){
					if(activity.getUserRangType().equals(UserRangeType.NEWUSER)){
						if(registerTime.getMillis() < activity.getStartGrantTime().getMillis()){//用户在活动发券时间之前注册
							continue;
						}
					}else if(activity.getUserRangType().equals(UserRangeType.NEWANDSHARED)){//新增且参与真旅行分享计划
						String recommender= memberClientServiceImpl.getRecommender(memberId);
						if(registerTime.getMillis() < activity.getStartGrantTime().getMillis() || StringUtils.isEmpty(recommender)){//新注册且推荐用户id不为空
							continue;
						}
					}else{
						if(registerTime.getMillis() > activity.getStartGrantTime().getMillis()){
							continue;
						}
					}
				}else{//手动添加用户的活动
					String memberPhone = SSOUtil.getMemberSessionBean().getMobile();
					List<String> userRange = activity.getUserRange();
					if(!CollectionUtils.isEmpty(userRange) && userRange.indexOf(memberPhone) < 0){//手动添加的用户不包含该用户
						continue;
					}
				}
				for(Entry<String, Coupon> entry : couponsMap.entrySet()){
					Coupon coupon = entry.getValue();
					if(coupon.getIsDelete()){
						continue;
					}
					CouponClientEntity couponClientEntity = null ;
					if(activity.getGrantType().equals(GrantType.BATCHCONFIG) && (activity.getUserRangType().equals(UserRangeType.NEWUSER) || activity.getUserRangType().equals(UserRangeType.NEWANDSHARED))){
						if(coupon.getStatus().equals(CouponStatus.SENDDING)){//对新增用户,选择发送中状态的代金券
							if(null != coupon.getGrantTime()){//此处为了兼容线上部分没有发放时间的数据
								if(registerTime.getMillis() < coupon.getGrantTime().getMillis()){
									continue;
								}
							}
							couponClientEntity = coupon2ClientEntity(entry.getValue());
							couponClientEntityList.add(couponClientEntity);
						}
					}else{//对手动添加的用户或者批量设置的系统已有用户,选择已发送的代金券
						if(coupon.getStatus().equals(CouponStatus.FINISHED)){
							if(null != coupon.getGrantTime()){//此处为了兼容线上部分没有发放时间的数据
								if(registerTime.getMillis() > coupon.getGrantTime().getMillis()){
									continue;
								}
							}
							couponClientEntity = coupon2ClientEntity(entry.getValue());
							couponClientEntityList.add(couponClientEntity);
						}
					}
				}
				activityClientEntity.setCoupons(couponClientEntityList);
				activityClientList.add(activityClientEntity);
			}
		}
		return activityClientList;
	}

	/**
     * 返回用户满足条件的券信息
     * @param activityList : 已生效的领券类型活动
     * @return 用户可以获得的券信息,在用户登陆时,进行发放
     * */
	List<CouponBookVo> convert2CouponBookVo(List<Activity> activityList){
        List<CouponBookVo> couponBookVoList = new ArrayList<CouponBookVo>();
        Long currentTime = DateTime.now().getMillis();
        if (!CollectionUtils.isEmpty(activityList)) {
            for (Activity activity : activityList) {//批量发的活动
                LOGGER.info("查询的活动id为：{}",activity.getId());
                if (activity.getEndTime().getMillis() < currentTime) {
                    continue;
                }
                Map<String, Coupon> couponsMap = activity.getCoupons();
                if (CollectionUtils.isEmpty(couponsMap)) {//活动没有券
                    continue;
                }
                for(Entry<String, Coupon> entry : couponsMap.entrySet()){
                    Coupon coupon = entry.getValue();
                    if(coupon.getIsDelete()){
                        continue;
                    }
                    CouponBookVo couponBookVo = coupon2CouponBookVo(coupon);
                    if (couponBookVo.getPrice() > 0 && couponBookVo.getLeftAmount() > 0) {
                        if (activity.getStartTime().getMillis() > currentTime) {
                            couponBookVo.setStatus(CouponBookStatus.READY);
                        } else {
                            couponBookVo.setStatus(CouponBookStatus.SELLING);
                        }
                        int availableNum = voucherClientService.countBuyAvailableByCouponId(coupon.getCouponId());
                        if (availableNum > 0) {
                            couponBookVo.setActivityId(activity.getId().toString());
                            couponBookVoList.add(couponBookVo);
                        }
                    }
                }
            }
        }
        return couponBookVoList;
    }

	CouponBookVo coupon2CouponBookVo(Coupon coupon){
	    CouponBookVo couponBookVo = new CouponBookVo();
        if(null != coupon){
            LOGGER.info("查询的代金券id为：{}", coupon.getCouponId());
            couponBookVo.setCouponCode(coupon.getCouponCode());
            couponBookVo.setCouponId(coupon.getCouponId().toString());
            couponBookVo.setAmount(coupon.getAmount());
            couponBookVo.setPrice(coupon.getPrice());
            couponBookVo.setCouponName(coupon.getName());
            couponBookVo.setUnit(coupon.getUnit());
            if (coupon.getTotalNum() != null && coupon.getUsedNum() != null) {
                if (coupon.getTotalNum() > 0 && coupon.getTotalNum() > coupon.getUsedNum()) {
                    couponBookVo.setLeftAmount(coupon.getTotalNum() - coupon.getUsedNum());
                } else {
                    couponBookVo.setLeftAmount(0);
                }
            }
        }
        return couponBookVo;
    }


	CouponClientEntity coupon2ClientEntity(Coupon coupon){
		CouponClientEntity couponClientEntity = new CouponClientEntity();
		if(null != coupon){
			couponClientEntity.setAmount(coupon.getAmount());
			couponClientEntity.setCouponCode(coupon.getCouponCode());
			couponClientEntity.setCouponId(coupon.getCouponId().toString());
			couponClientEntity.setDescription(coupon.getDescription());
			couponClientEntity.setName(coupon.getName());
			couponClientEntity.setOrderLeast(coupon.getOrderLeast());
			couponClientEntity.setValidTimeFrom(coupon.getValidTimeFrom().toString(DateTimeUtil.DATE_TIME_PATTERN));
			couponClientEntity.setValidTimeTo(coupon.getValidTimeTo().toString(DateTimeUtil.DATE_TIME_PATTERN));
			couponClientEntity.setProductRangeType(coupon.getProductRangeType().name());
			couponClientEntity.setUnit(coupon.getUnit());
			couponClientEntity.setIsDelete(coupon.getIsDelete());
			couponClientEntity.setProductRange(coupon.getDescription());
			if(coupon.getPrice()!=null){
				couponClientEntity.setPrice(coupon.getPrice());
			}
			if(coupon.getTotalNum() == null){//系统送券活动
				couponClientEntity.setTotalNum((coupon.getUsedNum() == 0 || coupon.getUsedNum() == null) ? null : coupon.getUsedNum());
			}else{
				couponClientEntity.setTotalNum(coupon.getTotalNum());
			}
//			if(coupon.getProductRangeType().equals(ProductRangeType.ALLPRODUCTS)){
//				couponClientEntity.setProductRange(ProductRangeType.ALLPRODUCTS.getDesc());
//			}else{
//				couponClientEntity.setProductRange("部分产品");
////				StringBuffer productRange = new StringBuffer();
////				if(!CollectionUtils.isEmpty(coupon.getSupportProducts())){
////					for(String s : coupon.getSupportProducts()){
////						productRange.append(s + ",");
////					}
////					couponClientEntity.setProductRange(productRange.toString().substring(0, productRange.lastIndexOf(",")));
////				}
//			}
		}
		return couponClientEntity;
	}

	CouponClientEntity coupon2ClientEntityWithProductName(Coupon coupon){
		CouponClientEntity couponClientEntity = new CouponClientEntity();
		long timeNow = new DateTime().getMillis();
		if(null != coupon && coupon.getValidTimeFrom().getMillis() <= timeNow && coupon.getValidTimeTo().getMillis() >= timeNow ){
			couponClientEntity.setAmount(coupon.getAmount());
			couponClientEntity.setCouponCode(coupon.getCouponCode());
			couponClientEntity.setCouponId(coupon.getCouponId().toString());
			couponClientEntity.setDescription(coupon.getDescription());
			couponClientEntity.setName(coupon.getName());
			couponClientEntity.setOrderLeast(coupon.getOrderLeast());
			couponClientEntity.setValidTimeFrom(coupon.getValidTimeFrom().toString(DateTimeUtil.DATE_TIME_PATTERN));
			couponClientEntity.setValidTimeTo(coupon.getValidTimeTo().toString(DateTimeUtil.DATE_TIME_PATTERN));
			couponClientEntity.setProductRangeType(coupon.getProductRangeType().name());
			couponClientEntity.setUnit(coupon.getUnit());
			couponClientEntity.setIsDelete(coupon.getIsDelete());
			if(coupon.getProductRangeType().equals(ProductRangeType.ALLPRODUCTS)){
				couponClientEntity.setProductRange(ProductRangeType.ALLPRODUCTS.getDesc());
			}else{
				StringBuffer productRange = new StringBuffer();
				if(!CollectionUtils.isEmpty(coupon.getSupportProducts())){
					for(String s : coupon.getSupportProducts()){
						productRange.append(s + ",");
					}
					couponClientEntity.setProductRange(productRange.toString().substring(0, productRange.lastIndexOf(",")));
				}
			}
		}
		return couponClientEntity;
	}

	@Override
	public Boolean updateCouponSendNum(String activityId, String couponId) {
		Boolean updateUsedNumResult = false;
		LOGGER.info("更新活动[{}]的代金券[{}]的发送数[{}]参数为:",activityId,couponId);
		try {
			Coupon coupon  = activityDao.getCouponById(activityId, couponId);
			Integer usedNum = coupon.getUsedNum() == null ? 0 : coupon.getUsedNum();
			Integer newUsedNum = usedNum + 1;
			updateUsedNumResult = activityDao.updateCouponUsedNum(activityId, couponId, newUsedNum);
			LOGGER.info("活动[{}]的代金券[{}]的发送数更新为[{}]:",activityId,couponId,newUsedNum);
		} catch (Exception e) {
			LOGGER.info("修改活[{}]的代金券[{}]的使用数出现异常",e);
		}
		return updateUsedNumResult;
	}

	@Override
	public String getActivityStatusById(String activityId) throws Exception {
		Activity activity = activityDao.getActivityById(activityId);
		String status = "";
		if(activity != null){
			status = activity.getStatus().toString();
		}
		return status;
	}

    @Override
    public Boolean updateCouponNum(String activityId, String couponId, Integer num) {
        Boolean updateUsedNumResult = false;
        LOGGER.info("更新活动[{}]的代金券[{}]的发送数[{}]参数为:",activityId,couponId);
        try {
            Coupon coupon  = activityDao.getCouponById(activityId, couponId);
            Integer usedNum = coupon.getUsedNum() == null ? 0 : coupon.getUsedNum();
            Integer newUsedNum = usedNum + num;
            updateUsedNumResult = activityDao.updateCouponUsedNum(activityId, couponId, newUsedNum);
            LOGGER.info("活动[{}]的代金券[{}]的发送数更新为[{}]:",activityId,couponId,newUsedNum);
        } catch (Exception e) {
            LOGGER.info("修改活[{}]的代金券[{}]的使用数出现异常",e);
        }
        return updateUsedNumResult;
    }

    @Override
    public List<CouponBookVo> getDNActivityList(Map<String, String> params) {
        List<CouponBookVo> couponBookVoList = null;
        try {
            List<Activity> activityList = activityDao.findByConditions(params);
            couponBookVoList = convert2CouponBookVo(activityList);
        } catch (Exception e) {
            LOGGER.info("查询购买活动代金券出现异常",e);
        }
        LOGGER.info("ActivityClientService查询的代金券信息[{}]",TZBeanUtils.describe(couponBookVoList));
        return couponBookVoList;
    }

    @Override
    public CommonResponse validateDNActivityAvailable(CouponBookVo couponBookVo) {

        String validationMsg = "";
        String activityId = couponBookVo.getActivityId();
        String couponId = couponBookVo.getCouponId();
        Integer amount = couponBookVo.getBookAmount();
        LOGGER.info("调用ACtivityClientService 查询活动{}的信息", activityId);
        CommonResponse response = new CommonResponse();
        response.setSuccess(true);
        Long currentTime = DateTime.now().getMillis();
        try {
            Activity activity = activityDao.getActivityById(activityId);
            if (activity == null ) {
                response.setSuccess(false);
                validationMsg = "按活动id[{" + activityId + "}]查询不到活动信息";
                response.setErrMsg("亲~活动结束了哦！下次再来~");
            } if (activity.getStatus() == null || !activity.getStatus().equals(ActivityStatus.EFFECTIVE)) {
                response.setSuccess(false);
                validationMsg = "按活动id[{" + activityId + "}]查询活动为非有效状态";
                response.setErrMsg("亲~活动结束了哦！下次再来~");
            } else if (activity.getType() == null || !activity.getType().equals(ActivityType.CUSTOMERGET)) {
                response.setSuccess(false);
                validationMsg = "按活动id[{" + activityId + "}]查询活动不属于用户领券活动";
                response.setErrMsg("亲~活动结束了哦！下次再来~");
            } else if (activity.getUserRangType() == null || !activity.getUserRangType().equals(UserRangeType.DN)) {
                response.setSuccess(false);
                validationMsg = "按活动id[{" + activityId + "}]查询活动不属于大宁活动";
                response.setErrMsg("亲~活动结束了哦！下次再来~");
            } else if (activity.getStartTime().getMillis() > currentTime) {
                response.setSuccess(false);
                validationMsg = "按活动id[{" + activityId + "}]查询活动不在有效期内";
                response.setErrMsg("亲~活动即将开始！");
            } else if (activity.getEndTime().getMillis() < currentTime) {
                response.setSuccess(false);
                validationMsg = "按活动id[{" + activityId + "}]查询活动不在有效期内";
                response.setErrMsg("亲~活动结束了哦！下次再来~");
            } else if (activity.getCoupons() == null || activity.getCoupons().get(couponId) == null) {
                response.setSuccess(false);
                validationMsg = "按活动id[{" + activityId + "}]查询活动不存在代金券信息或找不到代金券id[{" + couponId + "}]";
                response.setErrMsg("活动信息获取异常");
            } else {
                Coupon coupon = activity.getCoupons().get(couponId);
                Integer leftNum = coupon.getTotalNum() - coupon.getUsedNum();
                if (leftNum <= 0 || leftNum < amount) {
                    response.setSuccess(false);
                    validationMsg = "按活动id[{" + activityId + "}]查询活动的代金券id[{" + couponId + "}]库存为[{" + leftNum + "}]不足，用户购买数量为[{" + amount + "}]";
                    response.setErrMsg("仓库断货，筹货中……");
                } else {
                    couponBookVo.setUnit(coupon.getUnit());
                    couponBookVo.setPrice(coupon.getPrice());
                    couponBookVo.setAmount(coupon.getAmount());
                    couponBookVo.setCouponName(coupon.getName());
                    couponBookVo.setValidTimeFrom(coupon.getValidTimeFrom());
                    couponBookVo.setValidTimeTo(coupon.getValidTimeTo());
                    couponBookVo.setDescription(coupon.getDescription());
                    CouponSnapshot couponSnapshot = buidCouponSnapshot(couponBookVo, coupon);
                    couponBookVo.setCouponSnapshot(JSONObject.toJSONString(couponSnapshot));
                }
            }
        } catch (Exception e) {
            response.setSuccess(false);
            validationMsg = "按活动id[{" + activityId + "}]查询不到活动信息";
            response.setErrMsg("查询不到活动信息");
        }
        LOGGER.info("校验大宁活动信息为：[{}]",validationMsg);
        return response;
    }

    private CouponSnapshot buidCouponSnapshot(CouponBookVo couponBookVo, Coupon coupon) {
        com.ztravel.common.entity.CouponSnapshot css = new CouponSnapshot() ;
        css.setActivityId(couponBookVo.getActivityId());
        css.setAmount(coupon.getAmount());
        css.setCouponId(coupon.getCouponId());
        css.setName(coupon.getName());
        css.setValidDateFrom(coupon.getValidTimeFrom().toString("yyyy-MM-dd HH:mm:ss"));
        css.setValidDateTo(coupon.getValidTimeTo().toString("yyyy-MM-dd HH:mm:ss"));
        css.setDescription(coupon.getDescription());
        css.setProductRangeType(coupon.getProductRangeType());
        css.setOrderLeast(coupon.getOrderLeast());
        return css;
    }

}
