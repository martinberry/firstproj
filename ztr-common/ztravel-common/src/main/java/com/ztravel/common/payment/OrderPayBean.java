/**
 *
 */
package com.ztravel.common.payment;

import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;

/**
 * @author zuoning.shen
 *
 */
public class OrderPayBean {
    private String memberId;
    private String orderId;
    private ProductType productType;
    private PaymentType paymentType;
    /**
     * 订单金额，单位：分
     */
    private long orderAmount;
    /**
     * 应支付金额，单位：分
     */
    private long payAmount;
    /**
     * 积分抵扣金额，单位：分
     */
    private long useRewardPoint;
    /**
     * 代金券抵扣金额，单位：分
     */
    private long useCoupon;
    /**
     * 代金券券码
     */
    private String couponItemId;
    private boolean isMobile;
    /**
     * 前端跳转地址
     */
    private String fgNotifyUrl;
    private String comment;

    /**
     * 微信支付使用：trade_type
     */
    private String tradeType;

    /**
     * 微信支付使用：openId
     */
    private String openId;

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
    public ProductType getProductType() {
        return productType;
    }
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
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
    public boolean needPay(){
        return payAmount != 0;
    }
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
