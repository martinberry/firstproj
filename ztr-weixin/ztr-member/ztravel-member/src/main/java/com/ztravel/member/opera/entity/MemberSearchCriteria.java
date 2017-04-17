/**
 *
 */
package com.ztravel.member.opera.entity;

import com.ztravel.common.entity.PaginationEntity;

/**
 * @author
 *
 */
public class MemberSearchCriteria extends PaginationEntity {
	private String realName;
	private String nickName;
	private String mobile;
	private String memberId;
	private String email;
	private String status;
	private String registerFromDate;
	private String registerToDate;
	private String latestLoginPeriod;
	private String amountFrom;   //消费金额下限
	private String amountTo;       //消费金额上限
	private String province;
	private String city;
	//private int pageIndex;
	//private int pageSize;

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRegisterFromDate() {
		return registerFromDate;
	}
	public void setRegisterFromDate(String registerFromDate) {
		this.registerFromDate = registerFromDate;
	}
	public String getRegisterToDate() {
		return registerToDate;
	}
	public void setRegisterToDate(String registerToDate) {
		this.registerToDate = registerToDate;
	}
	public String getAmountFrom() {
		return amountFrom;
	}
	public void setAmountFrom(String amountFrom) {
		this.amountFrom = amountFrom;
	}
	public String getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(String amountTo) {
		this.amountTo = amountTo;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
//	public int getPageIndex() {
//		return pageIndex;
//	}
//	public void setPageIndex(int pageIndex) {
//		this.pageIndex = pageIndex;
//	}
//	public int getPageSize() {
//		return pageSize;
//	}
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
	public String getLatestLoginPeriod() {
		return latestLoginPeriod;
	}
	public void setLatestLoginPeriod(String latestLoginPeriod) {
		this.latestLoginPeriod = latestLoginPeriod;
	}


}
