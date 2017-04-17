package com.ztravel.paygate.wx.client.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信支付跳转支付页面返回结果
 * @author liuzhuo
 *
 */
public class WxPayResponse {
	
	/**
	 * 结果状态码(SUCCESS/FAIL)
	 */
	String result_code;
	
	/**
	 * 返回结果(成功二维码链接，失败空)
	 */
	String result_msg;

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	
	
	public WxPayResponse(){
		
	}
	
	private WxPayResponse(String result_code, String result_msg) {
		this.result_code = result_code;
		this.result_msg = result_msg;
	}
	
	public static WxPayResponse instance(String result_code, String result_msg) {
		return new WxPayResponse(result_code, result_msg);
	}
	
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
