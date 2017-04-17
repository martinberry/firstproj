package com.ztravel.weixin.event;

import java.util.Map;


/**
 * @author haofan.wan
 *
 */
public class WechatEvent {

	private Map<String, String> requestMap ;

    public WechatEvent(Map<String, String> requestMap2) {
        this.setRequestMap(requestMap2);
    }

	public Map<String, String> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, String> requestMap) {
		this.requestMap = requestMap;
	}

}
