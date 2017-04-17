/**
 *
 */
package com.ztravel.member.opera.vo;

/**
 * @author
 * @description 用于后台会员管理模块 会员列表展示
 */
public class MemberVO {
	private String id;    //会员表主键
	private String memberId;    //会员ID
	private String nickName;    //昵称
	private String realName;    //真实姓名
	private String gender;
	private String mobile;    //手机号
	private String email;
	//private String region;     //地区 province+city
	private String province;
	private String city;
	private String area;
	private String detailAddress;
	private int purchaseAmount;   //消费金额
	private String registerTime;
	private String latestLoginDate;    //最近登录日期
	private String latestLoginTime;    //最近登录时间
	private boolean isActive;    //会员状态 是否挂起
	private String headImgId;
    private String openId;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
//	public String getRegion() {
//		return region;
//	}
//	public void setRegion(String region) {
//		this.region = region;
//	}
	public int getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public String getLatestLoginDate() {
		return latestLoginDate;
	}
	public void setLatestLoginDate(String latestLoginDate) {
		this.latestLoginDate = latestLoginDate;
	}
	public String getLatestLoginTime() {
		return latestLoginTime;
	}
	public void setLatestLoginTime(String latestLoginTime) {
		this.latestLoginTime = latestLoginTime;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getHeadImgId() {
		return headImgId;
	}
	public void setHeadImgId(String headImgId) {
		this.headImgId = headImgId;
	}
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
