/**
 *
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum AccountType {
    REWARD_POINT("积分"), COUPON("代金券");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
