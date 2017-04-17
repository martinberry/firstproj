package com.ztravel.weixin.activity.vo;

/**
 * 活动用户VO
 * @author xutian
 *
 */
public class ActivityUserVo {

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
     * 详细地址
     */
    private String detailAddress;

    /**
     * 头像
     */
    private String headImageUrl;

    /**
     * 记录Id
     */

    private String recordId;

    /**
     * 奖品CODE
     */
    private String awardCode;

    /**
     * 游戏点击次数
     */
    private Integer clickNum;

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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

}
