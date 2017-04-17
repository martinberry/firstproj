package com.ztravel.paygate.web.dto.result;

import java.io.Serializable;

import com.ztravel.paygate.web.dto.PaygateErrorSupport;

/**
 * 客户端请求的返回信息
 * @author dingguangxian
 * 
 */
public abstract class AbstractResultBean implements Serializable,
		PaygateErrorSupport {
	private static final long serialVersionUID = -2585605129045842553L;
	private String sign;
	private String retCode;
	private String retMsg;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

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

}
