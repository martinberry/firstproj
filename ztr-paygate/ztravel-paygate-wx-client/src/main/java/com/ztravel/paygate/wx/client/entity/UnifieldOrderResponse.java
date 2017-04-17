package com.ztravel.paygate.wx.client.entity;

/**
 * 统一下单结果
 * @author liuzhuo
 *
 */
public class UnifieldOrderResponse {
	
	/**
	 * 返回状态码(SUCCESS/FAIL)
	 * 通信标示而非业务表示
	 */
	String return_code;
	
	/**
	 * 返回信息
	 */
	String return_msg;
	
	/**
	 * 公共账号id
	 */
	String appid;
	
	/**
	 * 商户号
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
	 * 业务结果(SUCCESS/FAIL)
	 */
	String result_code;
	
	/**
	 * 错误代码
	 */
	String err_code;
	
	/**
	 * 错误代码描述
	 */
	String err_code_des;
	
	/**
	 * 交易类型
	 */
	String trade_type;
	
	/**
	 * 预支付会话标示
	 */
	String prepay_id;
	
	/**
	 * 二维码链接
	 */
	String code_url;

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

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

}
