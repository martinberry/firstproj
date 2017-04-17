package com.ztravel.weixin.activity.entity;

/**
 * 元旦活动用户
 * @author xutian
 *
 */
public class NewYearActivityUser {

    /**
     * 用户openId
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户名称
     */
    private String realName;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 国家
     */
    private String country;

    /**
     * 省区
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 性别
     */
    private String gender;

    /**
     * 头像
     */
    private String headImageUrl;

    private Integer gameCount;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public Integer getGameCount() {
        return gameCount;
    }

    public void setGameCount(Integer gameCount) {
        this.gameCount = gameCount;
    }

}
