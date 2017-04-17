package com.ztravel.paygate.wx.dao;

import java.util.List;

import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;

public interface IWxPayNotifyDao {
	
	/**
	 * 保存微信支付记录
	 * @param unifieldOrderNotifyEntity
	 * @return
	 * @throws Exception
	 */
	public void insert(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity);
	
	/**
	 * lock
	 * @param unifieldOrderNotifyEntity
	 * @return
	 * @throws Exception
	 */
	public void lock(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity);
	
	/**
	 * count
	 * @param unifieldOrderNotifyEntity
	 * @return
	 * @throws Exception
	 */
	public Integer count(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity);
	
	int updateConfirmResult(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity);
	
	int searchCountByTidCfs(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) ;
	
	List<UnifieldOrderNotifyEntity> searchUnProceedRecords() ;
}
