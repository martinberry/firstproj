package com.ztravel.common.entity;

/**
 * 附加产品
 * @author xutian
 *
 */
public class AdditionalProduct {

    /**
     * 附加产品名称
     */
    private String name;

    /**
     * 附加产品价格
     */
    private long price;

    /**
     * 附件产品数量
     */
    private String num;

    /**
     * 附加产品总价
     */
    private long totalPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

}
