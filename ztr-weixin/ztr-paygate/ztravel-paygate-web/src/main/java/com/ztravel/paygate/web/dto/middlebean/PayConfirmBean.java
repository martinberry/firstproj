package com.ztravel.paygate.web.dto.middlebean;

import com.ztravel.paygate.web.dto.PaygateErrorSupport;

/**
 * 跳转到支付页面之前的中间Bean,用于判断是否签名成功,能否进行页面跳转
 * 
 * @author dingguangxian
 * 
 */
public class PayConfirmBean implements PaygateErrorSupport {
	private String orderNum;
	private String retCode;
	private String retMsg;
	private String payURL;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
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

	public String getPayURL() {
		return payURL;
	}

	public void setPayURL(String payURL) {
		this.payURL = payURL;
	}

}
