package com.ztravel.common.bean;

/**
 * 
 * @author wanhaofan
 * wechat access token
 */
public class AccessToken {
	private String value ;
	
	private int expire ;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}
	
	
}
