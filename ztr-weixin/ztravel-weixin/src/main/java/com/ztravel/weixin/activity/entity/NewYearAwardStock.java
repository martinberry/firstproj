package com.ztravel.weixin.activity.entity;

/**
 * 元旦奖品库存
 * @author xutian
 *
 */
public class NewYearAwardStock {

    /**
     * 奖品CODE
     */
    private String awardCode;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品总数
     */
    private long totalAmount;

    /**
     * 剩余奖品数
     */
    private long leftAmount;

    /**
     * 备注
     */
    private String remark;

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(long leftAmount) {
        this.leftAmount = leftAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode;
    }

}
