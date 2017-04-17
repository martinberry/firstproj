/**
 *
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum ProductType {
    TRAVEL("自由行"),
    VISA("签证产品"),
    UNVISA("当地游产品"),//
    OPCONFIRM("OP确认差额单"),
    VOUCHER("代金券");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
