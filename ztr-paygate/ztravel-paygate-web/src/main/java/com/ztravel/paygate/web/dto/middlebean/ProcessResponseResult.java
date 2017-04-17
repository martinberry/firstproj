package com.ztravel.paygate.web.dto.middlebean;

import com.ztravel.paygate.web.dto.result.PayResult;


/**
 * 处理银行返回结果
 * 
 * @author dingguangxian
 * 
 */
public class ProcessResponseResult {
	private String ackContent;// 返回给银行的信息
	private String fgNotifyUrl;
	private PayResult payResult;// 支付结果

	public String getAckContent() {
		return ackContent;
	}

	public void setAckContent(String ackContent) {
		this.ackContent = ackContent;
	}

	public PayResult getPayResult() {
		return payResult;
	}

	public void setPayResult(PayResult payResult) {
		this.payResult = payResult;
	}

	public String getFgNotifyUrl() {
		return fgNotifyUrl;
	}

	public void setFgNotifyUrl(String fgNotifyUrl) {
		this.fgNotifyUrl = fgNotifyUrl;
	}

}
