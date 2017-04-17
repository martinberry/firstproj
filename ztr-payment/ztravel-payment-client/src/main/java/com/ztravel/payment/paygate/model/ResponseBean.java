package com.ztravel.payment.paygate.model;

import java.io.Serializable;

/**
 * @author zuoning.shen
 *
 */
public class ResponseBean implements Serializable{
	private static final long serialVersionUID = -2341719237933643776L;
	public static String SUCCESS_CODE = "0000";
	private String retCode;// 返回码,只有“0000”是有效的返回
	private String retMsg;// 返回详细信息
	private String sign;// 签名串,验签使用

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
