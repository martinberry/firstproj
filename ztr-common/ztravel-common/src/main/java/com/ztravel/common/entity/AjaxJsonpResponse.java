package com.ztravel.common.entity;

import com.alibaba.fastjson.JSON;
import com.ztravel.common.bean.AjaxResponse;

public class AjaxJsonpResponse {
	
	private String callback;
	
	private AjaxResponse ajaxRes;
	
	private AjaxJsonpResponse(String callback, AjaxResponse ajaxRes) {
		this.callback = callback;
		this.ajaxRes = ajaxRes;
	}
	
	public static AjaxJsonpResponse getInstance(String callback, AjaxResponse ajaxRes) {
		return new AjaxJsonpResponse(callback, ajaxRes);
	} 
	

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public AjaxResponse getAjaxRes() {
		return ajaxRes;
	}

	public void setAjaxRes(AjaxResponse ajaxRes) {
		this.ajaxRes = ajaxRes;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.callback);
		sb.append("(");
		sb.append(JSON.toJSONString(this.ajaxRes));
		sb.append(")");
		return sb.toString();
	}
	
	
	
	

}
