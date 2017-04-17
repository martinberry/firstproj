/**
 *
 */
package com.ztravel.common.enums;

/**
 * @author bzhou
 *
 */
public enum SettlementPeriod {
    SINGLE("一单一结"),WEEK("周结"), HALFMONTH("半月结"),MONTH("月结");

    private final String description;

    SettlementPeriod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
