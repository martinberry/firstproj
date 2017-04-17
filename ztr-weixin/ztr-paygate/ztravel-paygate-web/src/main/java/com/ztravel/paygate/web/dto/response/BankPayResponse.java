package com.ztravel.paygate.web.dto.response;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 银行支付结果反馈
 * 
 * @author dingguangxian
 * 
 */
public class BankPayResponse {
	private HttpServletRequest request;
	private boolean fgNotify = true;// 是否前台通知
	private boolean mobilePay;//是否手机支付
	// 返回的信息
	private Map<String, String> formData;

	/**
	 * @return the fgNotify
	 */
	public boolean isFgNotify() {
		return fgNotify;
	}

	/**
	 * @param fgNotify
	 *            the fgNotify to set
	 */
	public void setFgNotify(boolean fgNotify) {
		this.fgNotify = fgNotify;
	}

	/**
	 * @return the formData
	 */
	public Map<String, String> getFormData() {
		return formData;
	}

	/**
	 * @param formData
	 *            the formData to set
	 */
	public void setFormData(Map<String, String> formData) {
		this.formData = formData;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public boolean isMobilePay() {
		return mobilePay;
	}

	public void setMobilePay(boolean mobilePay) {
		this.mobilePay = mobilePay;
	}

}
