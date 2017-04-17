/**
 *
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum PassengerType {
    ADULT("成人"), CHILD("儿童"), BABY("婴儿");

    private final String desc;

    PassengerType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
