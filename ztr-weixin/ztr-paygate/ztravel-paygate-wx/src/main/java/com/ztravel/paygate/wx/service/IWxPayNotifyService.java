package com.ztravel.paygate.wx.service;

import java.util.List;

import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyResponse;

public interface IWxPayNotifyService {
	
	/**
	 * 处理微信通知
	 * @param unifieldOrderNotifyEntity
	 * @throws Exception
	 */
	public UnifieldOrderNotifyResponse proceed(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity);
	
	/**
	 * 预处理微信通知
	 * @param unifieldOrderNotifyEntity
	 * @throws Exception
	 */
	public boolean preproceed(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity);
	
	boolean updateConfirmResult(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity);
	
	boolean isProceed(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) ;
	
	List<UnifieldOrderNotifyEntity> searchUnProceedRecords() ;
	
}
