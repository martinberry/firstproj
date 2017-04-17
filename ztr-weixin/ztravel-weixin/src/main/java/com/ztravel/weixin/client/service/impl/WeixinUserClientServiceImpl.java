package com.ztravel.weixin.client.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.weixin.client.service.IWxUserClientService;
import com.ztravel.weixin.entity.WxUserEntity;
import com.ztravel.weixin.user.dao.IWxUserDao;


@Service
public class WeixinUserClientServiceImpl implements IWxUserClientService{
	 @Resource
	    private IWxUserDao wxUserDaoImpl;

	    @Override
	    public void batchInsertWxUserInfo(List<WxUserEntity> wxUserList) {
	        for (WxUserEntity wxUser : wxUserList) {
	            if (wxUser != null) {
	                wxUserDaoImpl.insert(wxUser);
	            }
	        }

	    }

	    @Override
	    public void batchUpdateWxUserInfo(List<WxUserEntity> wxUserList) {
	        // TODO Auto-generated method stub

	    }

	    @Override
	    public void insertWxUserInfo(WxUserEntity wxUser) {
	        if (wxUser != null && wxUser.getId() != null) {
	            wxUserDaoImpl.insert(wxUser);
	        }
	    }

	   
	    @Override
	    public void updateWxUserInfo(WxUserEntity wxUser) {
	        if (wxUser != null && wxUser.getOpenid() != null) {
	            wxUserDaoImpl.update(wxUser);
	        }
	    }
	    

	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    @Override
	    public WxUserEntity selectWxUserInfoByOpenId(String openId) {
	        Map params = new HashMap();
	        params.put("openid", openId);
	        List<WxUserEntity> list =wxUserDaoImpl.select(params);
	        if(list == null || list.isEmpty()) {
	            return null;
	        }
	        return list.get(0);
	    }
	    
	    @Override
	    public void lock(String openId) {
	    	wxUserDaoImpl.lock(openId);
	    }
	
	

}



