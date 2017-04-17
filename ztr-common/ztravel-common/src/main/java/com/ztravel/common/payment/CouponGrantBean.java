/**
 * 
 */
package com.ztravel.common.payment;

import org.joda.time.DateTime;




/**
 * @author zuoning.shen
 *
 */
public class CouponGrantBean {
    private String memberId;
    private String activityId;
    private String couponCode;
    private long amount;
    private String name;
    private String description;
    /**
     * Format: yyyyMMdd
     */
    private DateTime validDateFrom;
    private DateTime validDateTo;
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getActivityId() {
        return activityId;
    }
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public DateTime getValidDateFrom() {
        return validDateFrom;
    }
    public void setValidDateFrom(DateTime validDateFrom) {
        this.validDateFrom = validDateFrom;
    }
    public DateTime getValidDateTo() {
        return validDateTo;
    }
    public void setValidDateTo(DateTime validDateTo) {
        this.validDateTo = validDateTo;
    }
}
