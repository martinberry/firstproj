package com.ztravel.paygate.api.alipay.model;
/**
 * 
 * @author dingguangxian
 *
 */
public class CommonResponse {
	private boolean success;
	private String errMsg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
