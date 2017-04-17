package com.ztravel.order.back.vo;

import java.util.List;
import java.util.Map;

import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderPassenger;
import com.ztravel.reuse.order.entity.AdditionalProductWo;
import com.ztravel.reuse.order.entity.AttachmentWo;
import com.ztravel.reuse.order.entity.BookPriceWo;


public class ConfirmOrderWo {

    private String orderId;

    private AttachmentWo attachment;

    private List<AdditionalProductWo> additionalProducts;

    private OrderContactor orderContactor;

    private List<OrderPassenger> passengers;

    private Map<String, String> additionalInfos;

    private BookPriceWo price;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public AttachmentWo getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentWo attachment) {
        this.attachment = attachment;
    }

    public List<AdditionalProductWo> getAdditionalProducts() {
        return additionalProducts;
    }

    public void setAdditionalProducts(List<AdditionalProductWo> additionalProducts) {
        this.additionalProducts = additionalProducts;
    }

    public OrderContactor getOrderContactor() {
        return orderContactor;
    }

    public void setOrderContactor(OrderContactor orderContactor) {
        this.orderContactor = orderContactor;
    }

    public List<OrderPassenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<OrderPassenger> passengers) {
        this.passengers = passengers;
    }

    public Map<String, String> getAdditionalInfos() {
        return additionalInfos;
    }

    public void setAdditionalInfos(Map<String, String> additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

    public BookPriceWo getPrice() {
        return price;
    }

    public void setPrice(BookPriceWo price) {
        this.price = price;
    }

}
