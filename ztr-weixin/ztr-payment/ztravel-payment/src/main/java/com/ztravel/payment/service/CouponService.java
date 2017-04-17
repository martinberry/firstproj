/**
 *
 */
package com.ztravel.payment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.util.TZBeanUtils;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.payment.CouponCountBean;
import com.ztravel.common.payment.CouponGrantBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.payment.CouponItemQueryBean;
import com.ztravel.common.payment.CouponSumBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.payment.dao.CouponItemDao;
import com.ztravel.payment.po.CouponItem;
import com.ztravel.payment.processor.CouponProcessor;

/**
 * @author zuoning.shen
 *
 */
@Service("CouponService")
@ThriftServiceEndpoint
public class CouponService implements ICouponService {
    private static Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Resource
    private CouponProcessor couponProcessor;

    @Resource
    private  CouponItemDao couponitemdao;


    @Override
    public List<String> selectCouponByMap(Map map){
    	List<CouponItem> couponitemlist= couponitemdao.select(map);
    	List<String> couponitemIdlist=new ArrayList<String>();
    	for(CouponItem couponitemtemp:couponitemlist){
    		couponitemIdlist.add(couponitemtemp.getCouponItemId());
    	}
    	return couponitemIdlist;
    }




    @Override
    public CommonResponse grant(CouponGrantBean bean) {
        logger.info("Coupon grant start, bean: {}", TZBeanUtils.describe(bean));
        CommonResponse res = null;
        try {
            res = couponProcessor.grant(bean);
        } catch (Exception e) {
            logger.error("Coupon grant failed", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("Coupon grant end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

    @Override
    public CommonResponse invalid(String couponItemId) {
        logger.info("Coupon invalid start, bean: {}", TZBeanUtils.describe(couponItemId));
        CommonResponse res = null;
        try {
            res = couponProcessor.invalid(couponItemId);
        } catch (Exception e) {
            logger.error("Coupon invalid failed", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("Coupon invalid end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

    @Override
    public int countCouponNumWithoutShared(String memberId, String couponId) {
        logger.info("countCouponNumWithoutShared start, memberId: {},couponId: {}", memberId, couponId);
        List<CouponItemStatus> statusList = Lists.newArrayList(
    			CouponItemStatus.AVAILABLE,CouponItemStatus.EXPIRED,CouponItemStatus.FROZEN,CouponItemStatus.INVALID,
    			CouponItemStatus.USED) ;
    	Map<String, Object> params = Maps.newHashMap() ;
    	params.put("couponCode", couponId);
    	params.put("memberId", memberId);
    	params.put("statusList", statusList);
        int result = 0 ;
        try {
        	result = couponProcessor.getCouponItemNumByMap(params) ;
        	return result ;
        } catch (Exception e) {
            logger.error("countCouponNumWithoutShared failed", e);
        }
        logger.info("countCouponNumWithoutShared end, response: {}", result);
        return result ;
    }

    @Override
    public CommonResponse transfer(String couponItemId, String destMemberId) {
        logger.info("transfer coupon start, couponItemId: {}, destMemberId: {}", couponItemId, destMemberId);
        CommonResponse res = null;
        try {
            res = couponProcessor.transfer(couponItemId, destMemberId);
        } catch (Exception e) {
            logger.error("transfer coupon failed", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("transfer coupon end, couponItemId: {}, destMemberId: {}", couponItemId, destMemberId);
        return res;
    }

    @Override
    public CommonResponse freeze(String couponItemId) {
        logger.info("freeze coupon start, couponItemId: {}", couponItemId);
        CommonResponse res = null;
        try {
            res = couponProcessor.freeze(couponItemId);
        } catch (Exception e) {
            logger.error("freeze coupon failed", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("freeze coupon end, couponItemId: {}", couponItemId);
        return res;
    }

    @Override
    public CommonResponse unfreeze(String couponItemId) {
        logger.info("unfreeze coupon start, couponItemId: {}", couponItemId);
        CommonResponse res = null;
        try {
            res = couponProcessor.unfreeze(couponItemId);
        } catch (Exception e) {
            logger.error("unfreeze coupon failed", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("unfreeze coupon end, couponItemId: {}", couponItemId);
        return res;
    }

    @Override
    public CouponItemBean getCouponItem(String couponItemId) {
        logger.info("getCouponItem start, couponItemId: {}", couponItemId);
        CouponItemBean item = null;
        try {
            item = couponProcessor.getCouponItem(couponItemId);
        } catch (Exception e) {
            logger.error("getCouponItem error", e);
        }
        logger.info("getCouponItem end, item: {}", TZBeanUtils.describe(item));
        return item;
    }

    @Override
    public List<CouponItemBean> getAvailableCouponItems(String memberId) {
        logger.info("getAvailableCouponItems start, memberId: {}", memberId);
        List<CouponItemBean> itemList = null;
        try {
            itemList = couponProcessor.getAvailableCouponItems(memberId);
        } catch (Exception e) {
            logger.error("getAvailableCouponItems error.", e);
        }
        logger.info("getAvailableCouponItems end, memberId: {}", memberId);
        return itemList;
    }

    @Override
    public Pagination<CouponItemBean> getCouponItems(CouponItemQueryBean bean) {
        logger.info("getCouponItems start, CouponItemQueryBean: {}", TZBeanUtils.describe(bean));
        Pagination<CouponItemBean> result = null;
        try {
            result = couponProcessor.getCouponItems(bean);
        } catch (Exception e) {
            logger.error("getCouponItems error.", e);
        }
        logger.info("getCouponItems end, CouponItemQueryBean: {}", TZBeanUtils.describe(bean));
        return result;
    }

    @Override
    public CouponSumBean sumAmount(String couponCode) {
        logger.info("sumAmount start, couponCode: {}", couponCode);
        CouponSumBean bean = null;
        try {
            bean = couponProcessor.sumAmount(couponCode);
        } catch (Exception e) {
            logger.error("sumAmount error.", e);
        }
        logger.info("sumAmount end, couponCode: {}", couponCode);
        return bean;
    }

    @Override
    public CouponCountBean countByCode(String couponCode) {
        logger.info("countByCode start, couponCode: {}", couponCode);
        CouponCountBean bean = null;
        try {
            bean = couponProcessor.countByCode(couponCode);
        } catch (Exception e) {
            logger.error("countByCode error.", e);
        }
        logger.info("countByCode end, couponCode: {}", couponCode);
        return bean;
    }

	@Override
	public int countByStatusListAndMemberId(List<CouponItemStatus> statusList, String memberId) {
		int count = 0;
		logger.info("countByStatusAndMemberId start, status : {}, memberId: {} ", statusList.toString() ,memberId);
		try{
			count = couponProcessor.countByStatusListAndMemberId(statusList, memberId);
		}catch(Exception e){
			logger.error("countByStatusAndMemberId error.", e);
		}
		logger.info("countByStatusAndMemberId end , count : {}", count);
		return count;
	}

	@Override
	public Pagination<CouponItemBean> getCouponItemsNew(CouponItemQueryBean couponItemQuery) {
	     logger.info("getCouponItemsNew start, CouponItemQueryBean: {}", TZBeanUtils.describe(couponItemQuery));
        Pagination<CouponItemBean> result = null;
        try {
            result = couponProcessor.getCouponItemsNew(couponItemQuery);
        } catch (Exception e) {
            logger.error("getCouponItems error.", e);
        }
        logger.info("getCouponItemsNew end, CouponItemQueryBean: {}", TZBeanUtils.describe(couponItemQuery));
        return result;
	}

}
