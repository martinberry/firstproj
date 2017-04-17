package com.ztravel.paygate.wx.client.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 微信统一支付下单接口参数(只有必填参数)
 * @author liuzhuo
 *
 */
@XStreamAlias("xml")
public class UnifieldOrderRequest {
	
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
	 * 商品或支付单简要描述
	 */
	String body;
	
	/**
	 * 商户订单号
	 */
	String out_trade_no;
	
	/**
	 * 总金额
	 */
	Integer total_fee;
	
	/**
	 * 终端IP
	 * APP和网页支付提交用户端ip,Native支付填调用微信支付API的机器ip
	 */
	String spbill_create_ip;
	
	/**
	 * 通知地址
	 * 接受微信支付异步通知回调地址
	 */
	String notify_url;
	
	/**
	 * openid
	 */
	String openid;
	
	/**
	 * 交易类型
	 */
	String trade_type;
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	@Override
	public String toString() {
		return "UnifieldOrderRequest [appid=" + appid + ", mch_id=" + mch_id
				+ ", nonce_str=" + nonce_str + ", sign=" + sign + ", body="
				+ body + ", out_trade_no=" + out_trade_no + ", total_fee="
				+ total_fee + ", spbill_create_ip=" + spbill_create_ip
				+ ", notify_url=" + notify_url + ", trade_type=" + trade_type
				+ "]";
	}
	
}
