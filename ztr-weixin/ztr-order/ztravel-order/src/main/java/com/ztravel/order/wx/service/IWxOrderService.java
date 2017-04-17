package com.ztravel.order.wx.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.order.wx.vo.OrderVo;

public interface IWxOrderService {

	Boolean isDomestic(String snapshot,String orderId) throws Exception;

	List<Order> getOrdersByMid(Map<String,Object> params) throws Exception;

	OrderVo order2Vo (Order order,OrderProduct orderProduct);

	Integer getOrderCount(Map<String,String> params)throws Exception;

	Boolean isOrderCanComment(String orderId, String strBackDate)throws Exception;

	Boolean isOrderCanEdit(Date departDay,String orderStatus)throws Exception;

	Integer getUnCommentOrderCount(Map<String,String> params) throws Exception;

	Boolean isBedTip(String productId);

	void updateOrderIsToPay(boolean isToPay, String orderId);

}
