package com.ztravel.weixin.timming.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.weixin.activity.entity.NewYearUserAwardRecord;
import com.ztravel.weixin.activity.enums.ActivityRecordStatus;
import com.ztravel.weixin.activity.vo.AwardRecordVo;
import com.ztravel.weixin.timming.dao.NewYearTimmingStockDao;
import com.ztravel.weixin.timming.dao.NewYearTimmingUserAwardRecordDao;
import com.ztravel.weixin.timming.service.NewYearTimmingService;

@Service
public class NewYearTimmingServiceImpl implements NewYearTimmingService{
	
	@Resource
	private NewYearTimmingUserAwardRecordDao newYearTimmingUserAwardRecordDao;
	
	@Resource
	private NewYearTimmingStockDao newYearTimmingStockDaoImpl;
	
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(NewYearTimmingServiceImpl.class);
	
	private static final RedisClient redisClient = RedisClient.getInstance() ;

	@Override
	@SuppressWarnings("rawtypes")
	public void updateGiftStatus() throws Exception{
		Map map=new HashMap();
		List<NewYearUserAwardRecord> giftRecordList=newYearTimmingUserAwardRecordDao.select(map);
		for(NewYearUserAwardRecord giftRecord:giftRecordList){
			ActivityRecordStatus status=giftRecord.getStatus();
			LOGGER.info("giftRecord:::{}", TZBeanUtils.describe(giftRecord));
			if(status.equals(ActivityRecordStatus.INIT)){
				long now = new Date().getTime();
				long createDate	= giftRecord.getCreateTime().getMillis();
				if((now-createDate)/1000>30*60){
					giftRecord.setStatus(ActivityRecordStatus.RELEASED);
					try{
						updateStatusAndStock(giftRecord) ;
					}catch(Exception e){
						LOGGER.error(TZBeanUtils.describe(giftRecord),e);
					}
				}
			}
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,rollbackFor=Throwable.class)
	public void updateStatusAndStock(NewYearUserAwardRecord giftRecord) throws Exception{
		String awardCode=giftRecord.getAwardCode();
		newYearTimmingUserAwardRecordDao.update(giftRecord);//奖品记录表
		newYearTimmingStockDaoImpl.updateAwardStockPlusOne(awardCode);//奖品库存表
	}
	
	@Override
	public void setCarousel(){
		try{
			LOGGER.info("persit award record list in redis");
			List<AwardRecordVo> arvs = newYearTimmingUserAwardRecordDao.searchAwardRecordList() ;
			redisClient.set(RedisKeyConst.NEWYEAR_ACTIVITY_AWARD_CAROUSEL, arvs, RedisKeyConst.ONE_HOUR);
			LOGGER.info("persit award record list in redis end");
		}catch(Exception e){
			LOGGER.error("persit award record list in redis failed", e);
		}
	
	}
	
	
//	public static void main(String args[]){
//		List<AwardRecordVo> arvs = new ArrayList<AwardRecordVo>(200) ;
//		int i = 0 ;
//		for(;i<200;){
//			AwardRecordVo arv = new AwardRecordVo() ;
//			arv.setAwardCode("awardCode" + i);
//			arv.setHeadImageUrl("http://www.baidu.com/url?i="+i);
//			arv.setNickName("nickName"+i);
//			arvs.add(arv) ;
//			i++ ;
//		}
//		redisClient.set(RedisKeyConst.NEWYEAR_ACTIVITY_AWARD_CAROUSEL, arvs, RedisKeyConst.ONE_HOUR);
//		
//		List<AwardRecordVo> lss = redisClient.get(RedisKeyConst.NEWYEAR_ACTIVITY_AWARD_CAROUSEL, java.util.ArrayList.class) ;
//		for(Object ls : lss){
//			System.out.println(ls) ;
//		}
//	}
}
