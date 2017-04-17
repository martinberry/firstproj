/**
 * 
 */
package com.ztravel.common.payment;

/**
 * @author zuoning.shen
 *
 */
public class CouponCountBean {
    private String couponCode;
    private int grantedCount;
    private int usedCount;
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public int getGrantedCount() {
        return grantedCount;
    }
    public void setGrantedCount(int grantedCount) {
        this.grantedCount = grantedCount;
    }
    public int getUsedCount() {
        return usedCount;
    }
    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }
}
