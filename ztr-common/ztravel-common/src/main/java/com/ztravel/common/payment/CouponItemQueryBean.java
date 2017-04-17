/**
 * 
 */
package com.ztravel.common.payment;

import java.util.List;

import org.joda.time.DateTime;

import com.ztravel.common.enums.CouponItemStatus;

/**
 * @author zuoning.shen
 *
 */
public class CouponItemQueryBean {
    private DateTime conditionUseDateFrom;
    private DateTime conditionUseDateTo;
    private String couponCode;
    private String memberId;
    private List<CouponItemStatus> statusList;
    private int pageNo = -1;
    private int pageSize = -1;
    public DateTime getConditionUseDateFrom() {
        return conditionUseDateFrom;
    }
    public void setConditionUseDateFrom(DateTime conditionUseDateFrom) {
        this.conditionUseDateFrom = conditionUseDateFrom;
    }
    public DateTime getConditionUseDateTo() {
        return conditionUseDateTo;
    }
    public void setConditionUseDateTo(DateTime conditionUseDateTo) {
        this.conditionUseDateTo = conditionUseDateTo;
    }
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public List<CouponItemStatus> getStatusList() {
        return statusList;
    }
    public void setStatusList(List<CouponItemStatus> statusList) {
        this.statusList = statusList;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
