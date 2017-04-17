package com.ztravel.weixin.client.service;

import java.util.List;

import com.ztravel.weixin.po.WeixinTopic;

public interface IWxTopicClientService {
		
	List<WeixinTopic> getByProductId(String productId)throws Exception;

}
