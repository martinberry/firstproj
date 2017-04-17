/**
 * 
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum InoutType {
    INCOME("收入"),OUTGO("支出");

    private final String description;

    InoutType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
