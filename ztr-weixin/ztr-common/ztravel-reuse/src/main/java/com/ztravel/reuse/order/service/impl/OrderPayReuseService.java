package com.ztravel.reuse.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductType;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.reuse.order.entity.OrderPayVo;
import com.ztravel.reuse.order.service.IOrderPayReuseService;

@Service
public class OrderPayReuseService implements IOrderPayReuseService {

	@Resource
	IOrderDao orderDao;

	@Resource
	IOrderProductDao orderProductDao;

	@Override
	public OrderPayVo buildOrderPayVoByOrderId(Order order,String memberId) throws Exception{
			OrderProduct orderProduct = new OrderProduct();
			OrderPayVo orderPayVo = new OrderPayVo();
			String orderId = order.getOrderId();

			orderProduct=orderProductDao.selectByOrderId(orderId);
			if(orderProduct == null){
				return null;
			}
			
			setOrderPayVo(orderPayVo, order, orderProduct,memberId);

			orderPayVo.setOrderId(orderId);
		    orderPayVo.setOrderCode(order.getOrderNo());
			orderPayVo.setCouponItemId(order.getDiscountCouponId());
			orderPayVo.setUseRewardPoint(order.getIntegralSub());
			orderPayVo.setDiscountCoupon(order.getCouponSub());
			orderPayVo.setTotalPrice(order.getTotalPrice());
			if(StringUtils.isNotBlank(order.getProductNature())){
				if(order.getProductNature().equals(Nature.PACKAGE.name()) || order.getProductNature().equals(Nature.COMBINATION.name())){
					orderPayVo.setProductType(ProductType.TRAVEL.name());
				}else if(order.getProductNature().equals(Nature.VISA.name())){
					orderPayVo.setProductType(ProductType.VISA.name());
				}else{
					orderPayVo.setProductType(ProductType.UNVISA.name());
				}
			}else{
				orderPayVo.setProductType(ProductType.TRAVEL.name());
			}
		    orderPayVo.setPayAmount(order.getPayAmount());
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    orderPayVo.setCreateDate(sdf.format(order.getCreateDate()));
		return orderPayVo;
	}
	
	@Override
	public OrderPayVo buildOrderPayVoByOrdeAndCommonOrder(Order order, CommonOrder commonOrder,String memberId) throws Exception {
		OrderProduct orderProduct = new OrderProduct();
		OrderPayVo orderPayVo = new OrderPayVo();
		String orderId = order.getOrderId();

		orderProduct=orderProductDao.selectByOrderId(orderId);
		if(null==orderProduct){
			return null;
		}

		setOrderPayVo(orderPayVo, order, orderProduct,memberId);
		
		orderPayVo.setOrderId(commonOrder.getOrderId());
	    orderPayVo.setOrderCode(commonOrder.getOrderId());
		orderPayVo.setProductType(ProductType.OPCONFIRM.name());
	    orderPayVo.setPayAmount(commonOrder.getPrice());
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    if(commonOrder.getCreateDate() != null){
	    	orderPayVo.setCreateDate(sdf.format(new Date(commonOrder.getCreateDate().getMillis())));
	    }
	    orderPayVo.setOrderIdOrigin(order.getOrderId());
		orderPayVo.setCouponItemId("");
		orderPayVo.setUseRewardPoint(0l);
		orderPayVo.setDiscountCoupon(0l);
		orderPayVo.setTotalPrice(commonOrder.getPrice());
	    return orderPayVo;
	}
	
	
	private void setOrderPayVo(OrderPayVo orderPayVo, Order order, OrderProduct orderProduct,String memberId){
		orderPayVo.setMemberId(memberId);
		orderPayVo.setTitle(orderProduct.getProductTitle());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    orderPayVo.setDepartDate(sdf.format(orderProduct.getBookDate()));
	}


}
