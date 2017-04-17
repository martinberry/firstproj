package com.ztravel.common.valid;

/**
 * 验证结果封装
 * @author liuzhuo
 *
 */
public class ValidResult {
	
	/**
	 * 是否验证通过
	 */
	private boolean isSuccess;
	
	/**
	 * 验证结果信息
	 */
	private String validMsg;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getValidMsg() {
		return validMsg;
	}

	public void setValidMsg(String validMsg) {
		this.validMsg = validMsg;
	}
	
	private ValidResult(boolean isSuccess, String validMsg) {
		this.isSuccess = isSuccess;
		this.validMsg = validMsg;
	}
	
	public static ValidResult instance(boolean isSuccess, String validMsg){
		return new ValidResult(isSuccess, validMsg);
	}
	

}
