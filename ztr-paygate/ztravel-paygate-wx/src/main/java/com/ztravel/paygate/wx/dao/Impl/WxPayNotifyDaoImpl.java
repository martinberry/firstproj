package com.ztravel.paygate.wx.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;
import com.ztravel.paygate.wx.dao.IWxPayNotifyDao;
@Repository
public class WxPayNotifyDaoImpl extends BaseDaoImpl<UnifieldOrderNotifyEntity> implements IWxPayNotifyDao{
	
	@Override
	public void lock(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		session.selectOne(nameSpace + ".lock", unifieldOrderNotifyEntity);
	}

	@Override
	public Integer count(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		return session.selectOne(nameSpace + ".count", unifieldOrderNotifyEntity);
	}
	
	@Override
	public int updateConfirmResult(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		return session.update(nameSpace + ".updateConfirmResult", unifieldOrderNotifyEntity);
	}
	
	@Override
	public int searchCountByTidCfs(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		return session.selectOne(nameSpace + ".searchRecordByTidCfs", unifieldOrderNotifyEntity);
	}
	
	@Override
	public List<UnifieldOrderNotifyEntity> searchUnProceedRecords() {
		return session.selectList(nameSpace + ".searchUnProceedRecords");
	}
	
}
