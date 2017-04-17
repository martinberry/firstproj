/**
 * 
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum AccountStatus {
    AVAILABLE("未冻结"), FROZEN("已冻结");

    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
