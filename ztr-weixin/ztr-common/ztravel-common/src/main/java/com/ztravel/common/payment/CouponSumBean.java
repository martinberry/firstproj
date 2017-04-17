/**
 * 
 */
package com.ztravel.common.payment;

/**
 * @author zuoning.shen
 *
 */
public class CouponSumBean {
    private String couponCode;
    private long grantedAmount;
    private long usedAmount;
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public long getGrantedAmount() {
        return grantedAmount;
    }
    public void setGrantedAmount(long grantedAmount) {
        this.grantedAmount = grantedAmount;
    }
    public long getUsedAmount() {
        return usedAmount;
    }
    public void setUsedAmount(long usedAmount) {
        this.usedAmount = usedAmount;
    }
}
