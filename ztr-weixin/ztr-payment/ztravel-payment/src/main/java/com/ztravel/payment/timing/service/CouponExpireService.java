/**
 * 
 */
package com.ztravel.payment.timing.service;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.payment.dao.CouponItemDao;
import com.ztravel.payment.po.CouponItem;
import com.ztravel.payment.timing.processor.CouponExpireProcessor;

/**
 * @author zuoning.shen
 *
 */
@Service
public class CouponExpireService {
private static Logger logger = LoggerFactory.getLogger(CouponExpireService.class);
    
    @Resource
    private CouponExpireProcessor couponExpireProcessor;
    
    @Resource
    private CouponItemDao couponItemDao;
    
    @Profiled
    public void expire(String couponItemId) {
        try{
            logger.info("Expire coupon start, couponItemId: {}", couponItemId);
            CouponItem item = couponItemDao.selectById(couponItemId);
            //Double check the status of the given coupon item 
            if(CouponItemStatus.AVAILABLE.name().equals(item.getStatus())){
                couponExpireProcessor.expire(item);
            }
            logger.info("Expire coupon end, couponItemId: {}", couponItemId);
        }catch(Exception e){
            logger.error("Expire coupon error, couponItemId: {}", couponItemId, e);
        }
    }

}
