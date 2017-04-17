/**
 *
 */
package com.ztravel.payment.po;

import org.joda.time.DateTime;

import com.ztravel.common.entity.AbstractBase;

/**
 * @author zuoning.shen
 *
 */
public class CouponItem extends AbstractBase {
    private String couponItemId;
    private String memberId;
    private String couponAccountId;
    private String activityId;
    private String couponCode;
    private String name;
    private String description;
    private long amount;
    private String status;
    private DateTime validDateFrom;
    private DateTime validDateTo;
    private String relatedOrderId;
    private DateTime grantDate;
    private DateTime useDate;
    
    public String getCouponItemId() {
        return couponItemId;
    }
    public void setCouponItemId(String couponItemId) {
        this.couponItemId = couponItemId;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getCouponAccountId() {
        return couponAccountId;
    }
    public void setCouponAccountId(String couponAccountId) {
        this.couponAccountId = couponAccountId;
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
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
    public String getRelatedOrderId() {
        return relatedOrderId;
    }
    public void setRelatedOrderId(String relatedOrderId) {
        this.relatedOrderId = relatedOrderId;
    }
    public DateTime getGrantDate() {
        return grantDate;
    }
    public void setGrantDate(DateTime grantDate) {
        this.grantDate = grantDate;
    }
    public DateTime getUseDate() {
        return useDate;
    }
    public void setUseDate(DateTime useDate) {
        this.useDate = useDate;
    }
}
