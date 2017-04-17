package com.ztravel.reuse.order.entity;

/**
 * 附加产品
 * @author xutian
 *
 */
public class AdditionalProductWo {

    /**
     * 附加产品名称
     */
    private String name;

    /**
     * 附加产品价格
     */
    private String price;

    /**
     * 附件产品数量
     */
    private String num;

    /**
     * 附加产品总价
     */
    private String totalPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
