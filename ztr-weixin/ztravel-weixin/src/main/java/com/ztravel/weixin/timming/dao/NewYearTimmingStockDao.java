package com.ztravel.weixin.timming.dao;
import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.activity.entity.NewYearAwardStock;

public interface NewYearTimmingStockDao extends BaseDao<NewYearAwardStock>{
	  public Integer updateAwardStockPlusOne(String awardCode);
}
