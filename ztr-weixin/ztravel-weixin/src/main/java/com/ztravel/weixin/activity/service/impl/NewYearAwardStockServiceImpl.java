package com.ztravel.weixin.activity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.entity.AwardWeight;
import com.ztravel.common.enums.ActivityAwardType;
import com.ztravel.product.client.service.IVoucherClientService;
import com.ztravel.weixin.activity.dao.INewYearAwardStockDao;
import com.ztravel.weixin.activity.entity.NewYearAwardStock;
import com.ztravel.weixin.activity.service.INewYearAwardStockService;
import com.ztravel.weixin.activity.vo.ActivityAwardOptionsVo;

@Service
public class NewYearAwardStockServiceImpl implements INewYearAwardStockService {

    private static Logger logger = RequestIdentityLogger.getLogger(NewYearAwardStockServiceImpl.class);

    private static final RedisClient redisClient = RedisClient.getInstance();

    @Resource
    private IVoucherClientService voucherClientService;

    @Resource
    private INewYearAwardStockDao newYearAwardStockDao;

    @Override
    public boolean achieveAwardByAwardCode(String awardCode) throws Exception {
        try {
            Integer count = newYearAwardStockDao.updateAwardStockMinusOne(awardCode);
            if (count != 1) {
                RuntimeException e = new RuntimeException("updateAwardStockMinusOne awardCode:::{" + awardCode + "} effect records:::{" + count + "}") ;
                logger.error(TZMarkers.p2, "占用非代金券奖品时修改记录数不是1条记录", e);
                throw e;
            }
        } catch (Exception e) {
            logger.error(TZMarkers.p2, "占用奖品(非代金券)库存时出现异常", e);
            throw e ;
        }
        return true;
    }

    @Override
    public Map<String, ActivityAwardOptionsVo> getActivityAwardOptions() {

        Map<String, ActivityAwardOptionsVo> awardOptionsVoMap = new HashMap<String, ActivityAwardOptionsVo>();
        AwardWeight awardWeight = redisClient.get("newYearAwardWight", AwardWeight.class);

        addCouponAwardOptions(awardOptionsVoMap, awardWeight);
        addOtherAwardOptions(awardOptionsVoMap, awardWeight);

        return awardOptionsVoMap;
    }

    private void addCouponAwardOptions(Map<String, ActivityAwardOptionsVo> awardOptionsVoMap, AwardWeight awardWeight) {
        List<String> couponIds = new ArrayList<String>();
        couponIds.add(ActivityAwardType.COUPON1.getDescription());
        couponIds.add(ActivityAwardType.COUPON2.getDescription());
        couponIds.add(ActivityAwardType.COUPON3.getDescription());
        List<Map> couponStockList = voucherClientService.selectVoucherStockByCouponIds(couponIds);
        if (couponStockList != null) {
            for (Map map : couponStockList) {
                ActivityAwardOptionsVo awardOptionsVo = new ActivityAwardOptionsVo();
                String couponId = map.get("coupon_id").toString();
                if (ActivityAwardType.COUPON1.getDescription().equals(couponId)) {
                    awardOptionsVo.setAwardCode(ActivityAwardType.COUPON1.name());
                    awardOptionsVo.setWeight((awardWeight == null || awardWeight.getCoupon1() == null) ? 0 : awardWeight.getCoupon1());
                } else if (ActivityAwardType.COUPON2.getDescription().equals(couponId)) {
                    awardOptionsVo.setAwardCode(ActivityAwardType.COUPON2.name());
                    awardOptionsVo.setWeight((awardWeight == null || awardWeight.getCoupon2() == null) ? 0 : awardWeight.getCoupon2());
                } else if (ActivityAwardType.COUPON3.getDescription().equals(couponId)) {
                    awardOptionsVo.setAwardCode(ActivityAwardType.COUPON3.name());
                    awardOptionsVo.setWeight((awardWeight == null || awardWeight.getCoupon3() == null) ? 0 : awardWeight.getCoupon3());
                }

                awardOptionsVo.setTotalNum((Long)(map.get("total_num")));
                awardOptionsVo.setLeftNum((map.get("left_num") == null ? 0L : (Long)map.get("left_num")));

                awardOptionsVoMap.put(awardOptionsVo.getAwardCode(), awardOptionsVo);
            }
        }

    }

    public void addOtherAwardOptions(Map<String, ActivityAwardOptionsVo> awardOptionsVoMap, AwardWeight awardWeight) {
        List<NewYearAwardStock> awardStockList = newYearAwardStockDao.select(Maps.newHashMap());
        for (NewYearAwardStock awardStock : awardStockList) {
            if (!ActivityAwardType.BAG.name().equals(awardStock.getAwardCode()) && !ActivityAwardType.CALENDARY.name().equals(awardStock.getAwardCode())) {
                continue ;
            }
            ActivityAwardOptionsVo awardOptionsVo = new ActivityAwardOptionsVo();
            awardOptionsVo.setAwardCode(awardStock.getAwardCode());
            awardOptionsVo.setTotalNum(awardStock.getTotalAmount());
            awardOptionsVo.setLeftNum(awardStock.getLeftAmount());
            if (ActivityAwardType.BAG.name().equals(awardStock.getAwardCode())) {
                awardOptionsVo.setWeight((awardWeight == null || awardWeight.getBag() == null) ? 0 : awardWeight.getBag());
            } else {
                awardOptionsVo.setWeight((awardWeight == null || awardWeight.getCalendary() == null) ? 0 : awardWeight.getCalendary());
            }
            awardOptionsVoMap.put(awardStock.getAwardCode(), awardOptionsVo);
        }
    }

    @Override
    public void buildAwardWeight(AwardWeight awardWeight) {
        redisClient.set("newYearAwardWight", JSONObject.toJSON(awardWeight),  24 * 30 * 60 * 60);
    }

    @Override
    public boolean updateStockByAwardCodeAndChangeNum(String awardCode, Integer changeNum) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("awardCode", awardCode);
        params.put("changeNum", changeNum);
        String remark = DateTimeUtil.formatDate(new DateTime(), "yy-MM-dd HH:mm") + "【" + awardCode + "】库存修改数量为：" + changeNum + "；";
        params.put("remark", remark);
        Integer count = newYearAwardStockDao.updateStockByChangeNum(params);
        if (count == 1) {
            return true;
        }
        return false;
    }

}
