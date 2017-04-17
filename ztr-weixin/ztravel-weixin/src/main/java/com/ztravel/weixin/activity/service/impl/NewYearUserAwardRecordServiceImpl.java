package com.ztravel.weixin.activity.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityAwardConstants;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.common.entity.AwardWeight;
import com.ztravel.common.enums.ActivityAwardType;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.service.IActivityClientService;
import com.ztravel.product.client.service.IVoucherClientService;
import com.ztravel.weixin.activity.dao.INewYearAwardStockDao;
import com.ztravel.weixin.activity.dao.INewYearUserAwardRecordDao;
import com.ztravel.weixin.activity.entity.GameRecordQueryBean;
import com.ztravel.weixin.activity.entity.NewYearUserAwardRecord;
import com.ztravel.weixin.activity.enums.ActivityRecordStatus;
import com.ztravel.weixin.activity.service.INewYearAwardStockService;
import com.ztravel.weixin.activity.service.INewYearUserAwardRecordService;
import com.ztravel.weixin.activity.vo.ActivityUserVo;
import com.ztravel.weixin.activity.vo.AwardRecordVo;
import com.ztravel.weixin.activity.vo.GameRecordVo;
import com.ztravel.weixin.user.service.IWxUserService;

@Service
public class NewYearUserAwardRecordServiceImpl implements INewYearUserAwardRecordService {

    private static ActivityAwardType defaultActivityAwardType = null;

    private static Logger logger = RequestIdentityLogger.getLogger(NewYearUserAwardRecordServiceImpl.class);

    private static final RedisClient redisClient = RedisClient.getInstance();

    @Resource
    private INewYearUserAwardRecordDao newYearUserAwardRecordDao;

    @Resource
    private INewYearAwardStockDao newYearAwardStockDao;

    @Resource
    private INewYearAwardStockService newYearAwardStockService;

    @Resource
    private IVoucherClientService voucherClientService;

    @Resource
    private IActivityClientService activityClientService;

    @Resource
    private IdGeneratorUtil idGeneratorUtil;
    
    @Resource
    private IWxUserService wxUserService ;

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    public AjaxResponse randomCreateAwardRecordByOpenId(String openId) {
    	wxUserService.lock(openId);
//      1、查询该用户是否领取过奖励（包括领取成功和占用中的）；
        Integer achieveAwardCount = newYearUserAwardRecordDao.countAchieveAwardRecordByOpenId(openId);
        if (achieveAwardCount > 0) {
            //该用户已获得过奖品
            return AjaxResponse.instance(ActivityAwardConstants.AWARD_ACHIEVED_BY_USER, "");
        }

        AjaxResponse response = new AjaxResponse();

//        2、根据随机数生成奖励得到奖品Code；
        ActivityAwardType awardCode = randomCreateAwardCode();

        boolean achieveSuccess = false;
        String voucherCode = null;
//      3、根据奖品Id查询奖品奖品库存，如库存大于0，占用该奖品，库存减1；否则，获取判断代金券库存，如库存大于0，占用该奖品，库存减1，否则提示奖品已发完
        try {
            if (ActivityAwardType.BAG.equals(awardCode) || ActivityAwardType.CALENDARY.equals(awardCode)) {
                Integer leftAmount = newYearAwardStockDao.selectLeftAmountByAwardCode(awardCode.name());
                if (leftAmount > 0) {
                    //该非代金券奖品库存占用
                    achieveSuccess = achieveOtherAward(awardCode.name());
                } else {
                  //该非代金券奖品库存为空
                    voucherCode = achieveCouponAward(defaultActivityAwardType);
                }
            } else {
                voucherCode = achieveCouponAward(awardCode);
            }
        } catch (Exception e) {
            response.setRes_code(ActivityAwardConstants.AWARD_ACHIEVE_FAIL);
        }

        if (achieveSuccess || !StringUtil.isEmpty(voucherCode)) {
            try {
                NewYearUserAwardRecord userAwardRecord = buildUserAwardRecord(openId, awardCode);
                createAwardRecordByOpenIdAndAwardCode(userAwardRecord);
                response.setRes_code(ActivityAwardConstants.AWARD_ACHIEVE_SUCCESS);

                ActivityUserVo activityUserVo = new ActivityUserVo();
                activityUserVo.setRecordId(userAwardRecord.getRecordId());
                activityUserVo.setOpenId(openId);
                activityUserVo.setAwardCode(awardCode.name());
                if (!StringUtil.isEmpty(voucherCode)) {
                    activityUserVo.setRecordId(voucherCode); //对于代金券奖品，不需要传递recordId信息，将voucherCode传递过去即可
                }
                response.setRes_msg(JSONObject.toJSONString(activityUserVo));
            } catch (Exception e) {
              //捕获占用奖励时的异常
                logger.error(TZMarkers.p2, "生成用户获奖记录数据时出现异常", e);
                response.setRes_code(ActivityAwardConstants.AWARD_ACHIEVE_FAIL);
            }
        } else {
            //库存不足
            response.setRes_code(ActivityAwardConstants.AWARD_STOCK_NOT_ENOUGH);
        }

        return response;
    }

    /**
     * 占用非代金券奖品（现指：包和台历）
     *
     * @param awardCode
     * @return
     * @throws Exception
     */
    private boolean achieveOtherAward(String awardCode) throws Exception {
        return newYearAwardStockService.achieveAwardByAwardCode(awardCode);
    }

    /**
     * 按权重随机生成奖品类型，并返回得到的AwardCode
     *
     * @return
     */
    private ActivityAwardType randomCreateAwardCode() {

        ActivityAwardType awardCode = ActivityAwardType.COUPON1;
        int num = (int) (Math.random() * 100);
        AwardWeight awardWeight = redisClient.get("newYearAwardWight", AwardWeight.class);
        Integer[] awardWeights = new Integer[5];
        awardWeights[0] = awardWeight.getCoupon1();
        awardWeights[1] = awardWeights[0] + awardWeight.getCoupon2();
        awardWeights[2] = awardWeights[1] + awardWeight.getCoupon3();
        awardWeights[3] = awardWeights[2] + awardWeight.getBag();
        awardWeights[4] = awardWeights[3] + awardWeight.getCalendary();
        if (num < awardWeights[0]) {
            awardCode = ActivityAwardType.COUPON1;
        } else if (num < awardWeights[1]) {
            awardCode = ActivityAwardType.COUPON2;
        } else if (num < awardWeights[2]) {
            awardCode = ActivityAwardType.COUPON3;
        } else if (num < awardWeights[3]) {
            awardCode = ActivityAwardType.BAG;
        } else if (num < awardWeights[4]) {
            awardCode = ActivityAwardType.CALENDARY;
        }

        if (num >= awardWeights[2]) {
            if (awardWeight.getCoupon1() > 0) {
                defaultActivityAwardType = ActivityAwardType.COUPON1;
            } else if (awardWeight.getCoupon2() > 0) {
                defaultActivityAwardType = ActivityAwardType.COUPON2;
            } else if (awardWeight.getCoupon3() > 0) {
                defaultActivityAwardType = ActivityAwardType.COUPON3;
            } else {
                defaultActivityAwardType = null;
            }
        }
        return awardCode;
    }

    /**
     * 占用代金券奖品
     * @param awardCode
     * @return
     * @throws Exception
     */
    private String achieveCouponAward(ActivityAwardType activityAwardType) throws Exception {

        if (activityAwardType == null ||
                (!ActivityAwardType.COUPON1.equals(activityAwardType) && !ActivityAwardType.COUPON2.equals(activityAwardType) && !ActivityAwardType.COUPON3.equals(activityAwardType))) {
            return null;
        }

        voucherClientService.selectVoucherLockForUpdateByCouponId(activityAwardType.getDescription());
        List<Voucher> voucherList = voucherClientService.selectActivityAvailableByCouponId(activityAwardType.getDescription());
        if (voucherList != null && voucherList.size() > 0) {
            String voucherCode = voucherList.get(0).getVoucherCode();
            try {
                boolean flag = voucherClientService.updateVoucherStatus(voucherCode, VoucherStatus.LOCK);
                if (flag) {
                    return voucherCode;
                }
            } catch (Exception e) {
                logger.error(TZMarkers.p2, "占用奖品(代金券)库存时出现异常", e);
                throw e ;
            }
        }
        return null;
    }

    private NewYearUserAwardRecord buildUserAwardRecord(String openId, ActivityAwardType awardCode) throws Exception {
        NewYearUserAwardRecord newYearUserAwardRecord = new NewYearUserAwardRecord();
        newYearUserAwardRecord.setRecordId(idGeneratorUtil.getUserAwardRecordId());
        newYearUserAwardRecord.setOpenId(openId);
        newYearUserAwardRecord.setAwardCode(awardCode.name());
        newYearUserAwardRecord.setCreateTime(new DateTime());
        if (ActivityAwardType.BAG.equals(awardCode) || ActivityAwardType.CALENDARY.equals(awardCode)) {
            newYearUserAwardRecord.setStatus(ActivityRecordStatus.INIT);
        } else {
            newYearUserAwardRecord.setStatus(ActivityRecordStatus.FINISHED);
        }
        return newYearUserAwardRecord;
    }

    @Override
    public ActivityRecordStatus selectAwardStatus(ActivityUserVo activityuservo){
    	String recordId=activityuservo.getRecordId();
    	NewYearUserAwardRecord awardrecord=newYearUserAwardRecordDao.selectById(recordId);
        return awardrecord.getStatus();
    }

    @Override
    public List<AwardRecordVo> searchAwardRecordList() {
        @SuppressWarnings("unchecked")
        List<AwardRecordVo> arvs = redisClient.get(RedisKeyConst.NEWYEAR_ACTIVITY_AWARD_CAROUSEL, java.util.ArrayList.class) ;
        return arvs;

    }

    @Override
    public NewYearUserAwardRecord selectNewYearUserAwardRecordByRecordId(String recordId) {
        return newYearUserAwardRecordDao.selectById(recordId);
    }

    @Override
    public void createAwardRecordByOpenIdAndAwardCode(NewYearUserAwardRecord userAwardRecord) throws Exception {
        newYearUserAwardRecordDao.insert(userAwardRecord);
    }

    @Override
    public Pagination<GameRecordVo> searchAwardRecordListByMap(GameRecordQueryBean queryBean) throws Exception {
        Pagination<GameRecordVo> searchResult = new Pagination<GameRecordVo>();
        int pageNo = 0;
        int totalItemCount = 0;
        int totalPageCount = 0;
        List<GameRecordVo> searchList = Lists.newArrayList();
        Map<String, Object> searchParams = Maps.newHashMap();
        try {
            searchParams = getSearchParamsByQuery(queryBean);
            searchList = newYearUserAwardRecordDao.searchAwardRecordListByMap(searchParams);
            for (GameRecordVo gameRecordVo : searchList) {
                String awardCode = gameRecordVo.getAwardCode();
                Preconditions.checkNotNull(awardCode);
                switch (ActivityAwardType.valueOf(awardCode)) {
                case COUPON1:
                    gameRecordVo.setAwardCode("50代金券");
                    break;
                case COUPON2:
                    gameRecordVo.setAwardCode("100代金券");
                    break;
                case COUPON3:
                    gameRecordVo.setAwardCode("800代金券");
                    break;
                case BAG:
                    gameRecordVo.setAwardCode("包");
                    break;
                case CALENDARY:
                    gameRecordVo.setAwardCode("台历");
                    break;
                default:
                    break;
                }
            }
            totalItemCount = newYearUserAwardRecordDao.countAwardRecordListByMap(searchParams);
            totalPageCount = (int) Math.ceil(new Double(totalItemCount) / queryBean.getPageSize());

            if (searchList.size() == 0) {
                pageNo = 1;
                totalItemCount = 0;
                totalPageCount = 1;
            }else{
            	pageNo = queryBean.getPageNo();
            }
            searchResult.setData(searchList);
            searchResult.setTotalItemCount(totalItemCount);
            searchResult.setPageSize(queryBean.getPageSize());
            searchResult.setTotalPageCount(totalPageCount);
            searchResult.setPageNo(pageNo);
        } catch (Exception e) {
            logger.error("查询失败", e);
        }
        return searchResult;

    }

    private Map<String, Object> getSearchParamsByQuery(GameRecordQueryBean queryBean) {
        Map<String, Object> searchParams = Maps.newHashMap();
        if (queryBean.getPageNo() != -1 && queryBean.getPageSize() != -1) {
            searchParams.put("offset", queryBean.getPageSize() * (queryBean.getPageNo() - 1));
            searchParams.put("limit", queryBean.getPageSize());
        }
        return searchParams;
    }

}
