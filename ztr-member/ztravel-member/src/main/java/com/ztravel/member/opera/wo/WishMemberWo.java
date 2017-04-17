package com.ztravel.member.opera.wo;

public class WishMemberWo {

	private String memberId;    //member主键id

	private String mId;

	private String nickName;

	private String realName;

	private String mobile;

	private String province;

	private String city;

	private String recentTime ;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getRecentTime() {
		return recentTime;
	}

	public void setRecentTime(String recentTime) {
		this.recentTime = recentTime;
	}

}
