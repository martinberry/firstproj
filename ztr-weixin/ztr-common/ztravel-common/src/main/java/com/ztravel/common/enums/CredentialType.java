/**
 *
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum CredentialType {
    IDCARD("身份证"), PASSPORT("护照"),GANGAOPASS("港澳通行证");

    private final String desc;

    CredentialType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
