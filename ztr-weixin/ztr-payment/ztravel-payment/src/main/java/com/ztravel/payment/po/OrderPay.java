/**
 * 
 */
package com.ztravel.payment.po;

import com.ztravel.common.entity.AbstractBase;

/**
 * 订单提交支付请求记录表
 * @author zuoning.shen
 *
 */
public class OrderPay extends AbstractBase{
    private String orderPayId;
    private String memberId;
    private String orderId;
    private String traceNum ;
    private String productType;
    private long orderAmount;
    /**
     * 应支付金额，单位：分
     */
    private long payAmount;
    private long useRewardPoint;
    private long useCoupon;
    private String couponItemId;
    private boolean isMobile;
    private String fgNotifyUrl;
    private String comment;
    /**
     * 已支付金额，单位：分
     */
    private long paidAmount;
    private long refundedAmount;
    private String orderPayStatus;
    public String getOrderPayId() {
        return orderPayId;
    }
    public void setOrderPayId(String orderPayId) {
        this.orderPayId = orderPayId;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public long getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }
    public long getPayAmount() {
        return payAmount;
    }
    public void setPayAmount(long payAmount) {
        this.payAmount = payAmount;
    }
    public long getUseRewardPoint() {
        return useRewardPoint;
    }
    public void setUseRewardPoint(long useRewardPoint) {
        this.useRewardPoint = useRewardPoint;
    }
    public long getUseCoupon() {
        return useCoupon;
    }
    public void setUseCoupon(long useCoupon) {
        this.useCoupon = useCoupon;
    }
    public String getCouponItemId() {
        return couponItemId;
    }
    public void setCouponItemId(String couponItemId) {
        this.couponItemId = couponItemId;
    }
    public boolean isMobile() {
        return isMobile;
    }
    public void setMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }
    public String getFgNotifyUrl() {
        return fgNotifyUrl;
    }
    public void setFgNotifyUrl(String fgNotifyUrl) {
        this.fgNotifyUrl = fgNotifyUrl;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public long getPaidAmount() {
        return paidAmount;
    }
    public void setPaidAmount(long paidAmount) {
        this.paidAmount = paidAmount;
    }
    public String getOrderPayStatus() {
        return orderPayStatus;
    }
    public void setOrderPayStatus(String orderPayStatus) {
        this.orderPayStatus = orderPayStatus;
    }
	public long getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(long refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	public String getTraceNum() {
		return traceNum;
	}
	public void setTraceNum(String traceNum) {
		this.traceNum = traceNum;
	}
}
