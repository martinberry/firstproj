/**
 *
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum PaymentType {
    Alipay("支付宝支付"), WeChatpay("微信支付"), RewardPoint("积分支付"), Coupon("代金券支付");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentType fromGateType(GateType gateType){
        return PaymentType.valueOf(gateType.name());
    }

    public boolean isNetPaymentType(){
        return Alipay.equals(this) || WeChatpay.equals(this);
    }
}
