package com.ztravel.order.front.convert;

import com.ztravel.order.front.vo.OrderListWo;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.reuse.order.converter.OrderReuseConverter;
import com.ztravel.reuse.order.entity.OrderProductWo;
import com.ztravel.reuse.order.entity.OrderWo;

public class OrderFrontConvert {
	
	public static OrderListWo convertProductWo(OrderProduct product, Order order){

		OrderListWo orderList = new OrderListWo();
		OrderWo orderWo = new OrderWo();
		OrderProductWo productWo = new OrderProductWo();

		OrderReuseConverter.buildOrderWo(orderWo, order);
		orderList.setOrder(orderWo);

		OrderReuseConverter.buildproductWo(productWo, product);
		orderList.setProduct(productWo);

		return orderList;

	}
	
	
}
