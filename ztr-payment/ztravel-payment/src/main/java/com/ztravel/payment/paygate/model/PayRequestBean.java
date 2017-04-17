package com.ztravel.payment.paygate.model;

/**
 * @author zuoning.shen
 *
 */
public class PayRequestBean extends RequestBean {
	private String orderNum;// 订单号,required
	private String comment;// 商品描述,required
	private long amount;// 订单金额,required
	private String fgNotifyUrl;// 前台通知地址,required
	private String userIP;// 用户IP，required for tenpay
	private String partner;// 商户号，optional

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getFgNotifyUrl() {
		return fgNotifyUrl;
	}

	public void setFgNotifyUrl(String fgNotifyUrl) {
		this.fgNotifyUrl = fgNotifyUrl;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

}
