package com.ztravel.weixin.client.service;

import java.util.List;

import com.ztravel.weixin.entity.WxUserEntity;



public interface IWxUserClientService {
	
	public void batchInsertWxUserInfo(List<WxUserEntity> wxUserList); 

	    
	    public void batchUpdateWxUserInfo(List<WxUserEntity> wxUserList);

	    public void insertWxUserInfo(WxUserEntity wxUser);

	    public void updateWxUserInfo(WxUserEntity wxUser);

	    public WxUserEntity selectWxUserInfoByOpenId(String openId);

		void lock(String openId);
		
		

}
