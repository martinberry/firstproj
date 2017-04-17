package com.ztravel.order.back.vo;

import java.util.List;

import com.ztravel.reuse.product.entity.ProductFlightInfo;


public class FlightSaveWo {

    private String orderId;

    private List<ProductFlightInfo> gos ;

    private List<ProductFlightInfo> middles ;

    private List<ProductFlightInfo> backs ;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<ProductFlightInfo> getGos() {
        return gos;
    }

    public void setGos(List<ProductFlightInfo> gos) {
        this.gos = gos;
    }

    public List<ProductFlightInfo> getMiddles() {
        return middles;
    }

    public void setMiddles(List<ProductFlightInfo> middles) {
        this.middles = middles;
    }

    public List<ProductFlightInfo> getBacks() {
        return backs;
    }

    public void setBacks(List<ProductFlightInfo> backs) {
        this.backs = backs;
    }

}
