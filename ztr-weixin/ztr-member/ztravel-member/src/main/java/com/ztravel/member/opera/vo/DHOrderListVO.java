package com.ztravel.member.opera.vo;


public class DHOrderListVO {

	//兑换单ID
	private String dhId;
	//会员ID
	private String memberId;
	//申兑时间
	private String pledhTime;
	//兑换状态
	private String dhStatus;
	//兑换金额
	private Long dhMoney;
	//兑换内容
	private String dhContent;
	//兑换操作
	private int dhOperate;
	//最后操作人
	private String lastOperator;

	private String statusDesc;

	private String contentDesc;





	public String getContentDesc() {
		return contentDesc;
	}
	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getDhId() {
		return dhId;
	}
	public void setDhId(String dhId) {
		this.dhId = dhId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String  getPledhTime() {
		return pledhTime;
	}
	public void setPledhTime(String  pledhTime) {
		this.pledhTime = pledhTime;
	}

	public String getDhStatus() {
		return dhStatus;
	}
	public void setDhStatus(String dhStatus) {
		this.dhStatus = dhStatus;
	}
	public Long getDhMoney() {
		return dhMoney;
	}
	public void setDhMoney(Long dhMoney) {
		this.dhMoney = dhMoney;
	}
	public String getDhContent() {
		return dhContent;
	}
	public void setDhContent(String dhContent) {
		this.dhContent = dhContent;
	}
	public int getDhOperate() {
		return dhOperate;
	}
	public void setDhOperate(int dhOperate) {
		this.dhOperate = dhOperate;
	}
	public String getLastOperator() {
		return lastOperator;
	}
	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}






}

