package com.ztravel.order.front.vo;

import com.ztravel.reuse.order.entity.OrderProductWo;
import com.ztravel.reuse.order.entity.OrderWo;

public class OrderListWo {

	private OrderWo order;

    private OrderProductWo product;

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

}
