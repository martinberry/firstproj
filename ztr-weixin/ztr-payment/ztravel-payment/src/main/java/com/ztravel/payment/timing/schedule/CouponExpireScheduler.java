/**
 *
 */
package com.ztravel.payment.timing.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.ztravel.payment.bo.CouponBo;
import com.ztravel.payment.timing.service.CouponExpireService;

/**
 * @author zuoning.shen
 *
 */
@Component
public class CouponExpireScheduler {
	private static Logger logger = LoggerFactory
			.getLogger(CouponExpireScheduler.class);

	@Resource
	private CouponExpireService couponExpireService;
	@Resource
	private CouponBo couponBo;

	@Scheduled(cron = "0 */1 * * * ?")
	public void expire() {
		logger.info("Expire couponItems start====>");
		List<String> couponItemList = Lists.newArrayList();
		try {
			couponItemList = couponBo.getItemsForExpire();
		} catch (Exception e) {
			logger.info("get expired couponItems error: [{}]", e);
		}
		logger.info("Expire couponItems : [{}]", couponItemList.toString());
		if (!couponItemList.isEmpty()) {
			for (String couponItemId : couponItemList) {
				couponExpireService.expire(couponItemId);
			}
		}
		logger.info("====>Expire couponItems end");
	}
}
