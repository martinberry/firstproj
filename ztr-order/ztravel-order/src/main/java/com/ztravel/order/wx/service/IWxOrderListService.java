package com.ztravel.order.wx.service;

import java.util.List;

import com.ztravel.order.po.Order;
import com.ztravel.order.wx.vo.OrderListVo;

public interface IWxOrderListService {

	OrderListVo orderList2Vo(List<Order> orderList);

}
