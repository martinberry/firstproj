/**
 * 
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum OrderType {
    PAY_ORDER("支付单"), CANCEL_ORDER("取消单"), REFUND_ORDER("退款单"), OP_REPAIR_ORDER("OP确认补款单"), OP_REFUND_ORDER("OP确认退款单");
    
    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
