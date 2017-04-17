package com.ztravel.paygate.web.dto.request;


/**
 * 请求支付
 *
 * @author dingguangxian
 *
 */
public class RequestPayBean extends AbstractRequestBean {
	private static final long serialVersionUID = 6076860006418570341L;
	private String orderNum;
	private String payType;// 支付类型,如机票\酒店等,由调用方决定其值
	private String comment;// 商品描述
	private long amount;
	private String fgNotifyUrl;
	private String userIP;
	private Boolean mobilePay;
	private String partner;

	//扩展字段, 目前仅在德付通支付时有效
	private String bankId;


	public String getFgNotifyUrl() {
		return fgNotifyUrl;
	}

	public void setFgNotifyUrl(String fgNotifyUrl) {
		this.fgNotifyUrl = fgNotifyUrl;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public Boolean getMobilePay() {
		return mobilePay;
	}

	public void setMobilePay(Boolean mobilePay) {
		this.mobilePay = mobilePay;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

}
