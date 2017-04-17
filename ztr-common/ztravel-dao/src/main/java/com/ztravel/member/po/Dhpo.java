package com.ztravel.member.po;

import java.util.Date;

public class Dhpo {
	// 兑换单ID
	private String dhId;
	// 会员ID
	private String memberId;
	// 申兑时间
	private Date pledhTime;
	// 兑换状态
	private String dhStatus;
	// 兑换金额
	private Long dhMoney;
	// 兑换内容
	private String dhContent;
	// 最后操作人
	private String lastOperator;
	// 兑换手机号
	private String dhPhonenum;
	// 确认兑换时间
	private Date condhTime;
	// 会员昵称xinxi
	private String memberName;

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

	public Date getPledhTime() {
		return pledhTime;
	}

	public void setPledhTime(Date pledhTime) {
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

	public String getLastOperator() {
		return lastOperator;
	}

	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}

	public String getDhPhonenum() {
		return dhPhonenum;
	}

	public void setDhPhonenum(String dhPhonenum) {
		this.dhPhonenum = dhPhonenum;
	}

	public Date getCondhTime() {
		return condhTime;
	}

	public void setCondhTime(Date condhTime) {
		this.condhTime = condhTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
