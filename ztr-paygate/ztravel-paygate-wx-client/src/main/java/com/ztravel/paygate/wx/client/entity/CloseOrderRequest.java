package com.ztravel.paygate.wx.client.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 微信统一关闭订单请求
 * @author haofan.wan
 *
 */
@XStreamAlias("xml")
public class CloseOrderRequest {
	
	/**
	 * 公共账号ID
	 * 微信分配的公众账号ID 
	 */
	String appid;
	
	/**
	 * 微信支付分配的商户号
	 */
	String mch_id;
	
	/**
	 * 随机字符串
	 */
	String nonce_str;
	
	/**
	 * 签名
	 */
	String sign;
	
	/**
	 * 商户订单号
	 */
	String out_trade_no;
	
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

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	@Override
	public String toString() {
		return "CloseOrderRequest [appid=" + appid + ", mch_id=" + mch_id
				+ ", nonce_str=" + nonce_str + ", sign=" + sign
				+ ", out_trade_no=" + out_trade_no + "]";
	}

	
}
