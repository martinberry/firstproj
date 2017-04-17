package com.ztravel.order.back.vo;

import java.util.List;

public class HotelSaveWo {

    private String orderId;

    private List<HotelWo> hotels;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<HotelWo> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelWo> hotels) {
        this.hotels = hotels;
    }

}
