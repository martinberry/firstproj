package com.ztravel.weixin.activity.vo;

public class AwardRecordVo {

    private String nickName;

    private String headImageUrl;

    private String awardCode;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((awardCode == null) ? 0 : awardCode.hashCode());
		result = prime * result
				+ ((headImageUrl == null) ? 0 : headImageUrl.hashCode());
		result = prime * result
				+ ((nickName == null) ? 0 : nickName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AwardRecordVo other = (AwardRecordVo) obj;
		if (awardCode == null) {
			if (other.awardCode != null)
				return false;
		} else if (!awardCode.equals(other.awardCode))
			return false;
		if (headImageUrl == null) {
			if (other.headImageUrl != null)
				return false;
		} else if (!headImageUrl.equals(other.headImageUrl))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AwardRecordVo [nickName=" + nickName + ", headImageUrl="
				+ headImageUrl + ", awardCode=" + awardCode + "]";
	}
    
}
