package com.ztravel.common.weixin.notice;

public class GiftBoxSendNotice {

	private String userName;

	private String logisticsType;//物流名称

	private String logisticsCode;//快递单号

	private String  receiver;//收货人

	private String phone;

	private String receiveAddress;//收货地址

	private String openId;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogisticsType() {
		return logisticsType;
	}
	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}
	public String getLogisticsCode() {
		return logisticsCode;
	}
	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
