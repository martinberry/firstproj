package com.ztravel.weixin.activity.vo;

/**
 * 活动奖品参数Vo
 * @author xutian
 *
 */
public class ActivityAwardOptionsVo {

    /**
     * 奖品CODE
     */
    private String awardCode;

    /**
     * 奖品总数
     */
    private long totalNum;

    /**
     * 奖品剩余数量
     */
    private long leftNum;

    /**
     * 奖品权重
     */
    private Integer weight;

    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public long getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(long leftNum) {
        this.leftNum = leftNum;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
