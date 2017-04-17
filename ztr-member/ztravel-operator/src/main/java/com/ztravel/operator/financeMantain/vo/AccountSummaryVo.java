package com.ztravel.operator.financeMantain.vo;
public class AccountSummaryVo {
	private String memberId;
	private String memberName;
	private String mid;
	private String accountType;
	private String amount;
	private String availableAmount;
	private String frozenAmount;
	private String accountStatus;
	private String isAccountFrozen;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getIsAccountFrozen() {
		return isAccountFrozen;
	}
	public void setIsAccountFrozen(String isAccountFrozen) {
		this.isAccountFrozen = isAccountFrozen;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}
	public String getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
}
