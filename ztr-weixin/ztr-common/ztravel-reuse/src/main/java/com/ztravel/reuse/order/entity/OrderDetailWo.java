package com.ztravel.reuse.order.entity;

import java.util.List;
import java.util.Map;

import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderPassenger;


public class OrderDetailWo {

	private Map<String, String> orderOperate;

	private OrderWo order;

    private OrderProductWo product;

    private BookPriceWo price;

    private OrderContactor orderContactor;

    private List<OrderPassenger> passengers;

    private List<AdditionalProductWo> additionalProducts;

    private List<AttachmentWo> attachments;

    /**
     * 1 国内 0 国际
     */
	private String isDomestic;

	/**
	 * 是否可编辑
	 */
    private boolean canChange;

	/**
	 * 床型偏好
	 * */
	private String bedPrefer;
	
	private Boolean isBedTips;

	public Map<String, String> getOrderOperate() {
		return orderOperate;
	}

	public void setOrderOperate(Map<String, String> orderOperate) {
		this.orderOperate = orderOperate;
	}

	public OrderWo getOrder() {
		return order;
	}

	public void setOrder(OrderWo order) {
		this.order = order;
	}

	public OrderProductWo getProduct() {
		return product;
	}

	public void setProduct(OrderProductWo product) {
		this.product = product;
	}

	public BookPriceWo getPrice() {
		return price;
	}

	public void setPrice(BookPriceWo price) {
		this.price = price;
	}

	public String getIsDomestic() {
		return isDomestic;
	}

	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	public boolean isCanChange() {
		return canChange;
	}

	public void setCanChange(boolean canChange) {
		this.canChange = canChange;
	}

	public String getBedPrefer() {
		return bedPrefer;
	}

	public void setBedPrefer(String bedPrefer) {
		this.bedPrefer = bedPrefer;
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


    public List<AttachmentWo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentWo> attachments) {
        this.attachments = attachments;
    }

	public List<AdditionalProductWo> getAdditionalProducts() {
		return additionalProducts;
	}

	public void setAdditionalProducts(List<AdditionalProductWo> additionalProducts) {
		this.additionalProducts = additionalProducts;
	}

	public Boolean getIsBedTips() {
		return isBedTips;
	}

	public void setIsBedTips(Boolean isBedTips) {
		this.isBedTips = isBedTips;
	}


}
