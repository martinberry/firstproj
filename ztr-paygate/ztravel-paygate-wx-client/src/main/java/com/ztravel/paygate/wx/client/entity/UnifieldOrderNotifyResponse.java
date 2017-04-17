package com.ztravel.paygate.wx.client.entity;


/**
 * 微信扫码支付通知返回参数
 * @author liuzhuo
 *
 */
public class UnifieldOrderNotifyResponse {
	
	/**
	 * 返回状态码
	 */
	String return_code;
	
	/**
	 * 返回信息
	 */
	String return_msg;

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	
	public UnifieldOrderNotifyResponse() {
		
	}
	
	private UnifieldOrderNotifyResponse(String return_code, String return_msg) {
		this.return_code = return_code;
		this.return_msg = return_msg;
	}
	
	public static UnifieldOrderNotifyResponse instance(String return_code, String return_msg) {
		return new UnifieldOrderNotifyResponse(return_code, return_msg);
	}

}
