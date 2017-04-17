package com.ztravel.weixin.activity.entity;

import org.joda.time.DateTime;

import com.ztravel.weixin.activity.enums.ActivityRecordStatus;

public class NewYearUserAwardRecord {

    /**
     * 记录Id
     */
    private String recordId;

    /**
     * openId
     */
    private String openId;

    /**
     * 奖品Id
     */
    private String awardCode;

    /**
     * 生成时间
     */
    private DateTime createTime;

    /**
     * 获得奖品数量，默认为1
     */
    private Integer amount = 1;

    /**
     * 记录状态
     */
    private ActivityRecordStatus status;

    /**
     * 记录结果
     */
    private String recordResult;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }



    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public ActivityRecordStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityRecordStatus status) {
        this.status = status;
    }

    public String getRecordResult() {
        return recordResult;
    }

    public void setRecordResult(String recordResult) {
        this.recordResult = recordResult;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

	public String getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}

}
