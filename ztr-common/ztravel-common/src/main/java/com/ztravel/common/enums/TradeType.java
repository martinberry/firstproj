/**
 * 
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum TradeType {
    REFUND("退款"), PAYMENT("支付");
    
    private final String description;

    TradeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
