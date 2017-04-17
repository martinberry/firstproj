/**
 *
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum InoutDetailType {
    GRANTED_COUPON("获得代金券"), GRANTED_REWARD_POINT("获得积分"), PAY_ORDER("支付票款"), REFUND_ORDER("退款票款"), SHARED_COUPON("分享代金券"), COUPON_EXPIRED("代金券过期")
    , REWARD_POINT_EXPIRED("积分过期"), REFUND_VOUCHER("代金券退款");

    private final String description;

    InoutDetailType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
