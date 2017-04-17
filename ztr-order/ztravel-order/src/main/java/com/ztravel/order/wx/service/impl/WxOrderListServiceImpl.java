package com.ztravel.order.wx.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.order.wx.service.IWxOrderListService;
import com.ztravel.order.wx.service.IWxOrderService;
import com.ztravel.order.wx.vo.OrderListVo;
import com.ztravel.order.wx.vo.OrderVo;
import com.ztravel.reuse.order.service.IOrderProductReuseService;


@Service
public class WxOrderListServiceImpl implements IWxOrderListService {

	@Resource
	IWxOrderService wxOrderServiceImpl;
	
	@Resource
	IOrderProductReuseService orderProductReuseService;


	private static Logger LOGGER = RequestIdentityLogger.getLogger(WxOrderListServiceImpl.class);

	public OrderListVo orderList2Vo(List<Order> orderList){
		OrderListVo orderListVo = new OrderListVo();
		if(!CollectionUtils.isEmpty(orderList)){
			List<OrderVo> orderVoList = new ArrayList<OrderVo>();
			for(Order order : orderList){
				try {
					OrderProduct orderProduct = orderProductReuseService.getOrderProductByOrderId(order.getOrderId());
					OrderVo orderVo = wxOrderServiceImpl.order2Vo(order,orderProduct);
					if(null != orderVo){
						orderVoList.add(orderVo);
					}
				} catch (Exception e) {
					LOGGER.error("查询订单[{}]产品信息异常",order.getOrderId(),e);
				}
			}
			orderListVo.setOrderVoList(orderVoList);
		}
		return orderListVo;
	}



}
