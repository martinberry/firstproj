package com.ztravel.paygate.wx.client.entity;



/**
 * 微信统一关闭订单
 * @author haofan.wan
 *
 */
public class CloseOrderResponse {

	private String return_code ;
	
	private String return_msg ;
	
	private String appid ;
	
	private String mch_id ;
	
	private String sub_mch_id ;
	
	private String nonce_str ;
	
	private String sign ;
	
	private String result_code ;

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

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	@Override
	public String toString() {
		return "CloseOrderResponse [return_code=" + return_code
				+ ", return_msg=" + return_msg + ", appid=" + appid
				+ ", mch_id=" + mch_id + ", sub_mch_id=" + sub_mch_id
				+ ", nonce_str=" + nonce_str + ", sign=" + sign
				+ ", result_code=" + result_code + "]";
	}
	

	
}
