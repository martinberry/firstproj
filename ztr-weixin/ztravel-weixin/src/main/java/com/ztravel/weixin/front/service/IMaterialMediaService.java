package com.ztravel.weixin.front.service;

import com.ztravel.weixin.bean.BaseMessageBean;

public interface IMaterialMediaService {

	public BaseMessageBean buildMessageByKey(String eventKey);

}