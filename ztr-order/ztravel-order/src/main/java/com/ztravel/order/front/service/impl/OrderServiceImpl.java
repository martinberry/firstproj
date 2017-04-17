package com.ztravel.order.front.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.travelzen.framework.core.util.MoneyUtil;
import com.ztravel.common.constants.Const;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.dao.IOrderComContactorDao;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderPassengerDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.front.convert.OrderFrontConvert;
import com.ztravel.order.front.service.IOrderService;
import com.ztravel.order.front.vo.OrderListWo;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.product.client.entity.ProductClientEntity;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.reuse.order.converter.Order2DetailConverter;
import com.ztravel.reuse.order.entity.OrderDetailWo;
import com.ztravel.reuse.order.entity.OrderWo;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Resource
	IOrderDao orderDaoImpl;

	@Resource
	ICommonOrderDao commonOrderDaoImpl;

	@Resource
	IOrderProductDao orderProductDaoImpl;

	@Resource
	IOrderContactorDao orderContactorDaoImpl;

	@Resource
	IOrderPassengerDao orderPassengerDaoImpl;

	@Resource
	IOrderComContactorDao orderComContactorDaoImpl;
	
	@Resource
	IProductClientService productClientServiceImpl;

	@Resource
	IOrderReuseService orderReuseService;
	
	@Resource
	ICommonOrderReuseService commonOrderReuseService;

	private final String BED_TIP_COUNTRY = "日本"; 

	@Override
	public OrderDetailWo selectOrderById(String orderId, String mid) throws Exception{
		OrderDetailWo orderDetail = new OrderDetailWo() ;

		if(StringUtils.isNotBlank(orderId)){
			Order order = orderDaoImpl.selectById(orderId);

			if( ! mid.equals(order.getCreator())){
				throw ZtrBizException.instance(Const.FF_ORDE_CODE_1018, Const.FF_ORDE_REASON_1018) ;
			}

			OrderProduct product = orderProductDaoImpl.selectByOrderId(orderId);
			ProductClientEntity entity = new ProductClientEntity();
			if(StringUtils.isBlank(order.getProductNature())||order.getProductNature().equals(Nature.PACKAGE.toString()) || order.getProductNature().equals(Nature.COMBINATION.toString())){
				entity = productClientServiceImpl.getProductById(product.getProductId());
			}
			if(!CollectionUtils.isEmpty(entity.getToCountry()) && entity.getToCountry().contains(BED_TIP_COUNTRY)){
				orderDetail.setIsBedTips(true);
			}else{
				orderDetail.setIsBedTips(false);
			}
			Order2DetailConverter.convertOrderDetailWo(order, product, orderDetail);
//			OrderContactor contactor = orderContactorDaoImpl.selectByOrderId(orderId);
//			List<OrderPassenger> passengers = orderPassengerDaoImpl.selectByOrderId(orderId);
//			orderDetail.setOrderContactor(contactor);
//			orderDetail.setPassengers(passengers);
		}

		return orderDetail;
	}

	@Override
	public List<OrderListWo> getProductByOrderId(List<Order> orders) throws SQLException{

		List<OrderListWo> products = new ArrayList<>();

		for(Order order : orders){
			OrderListWo productWo = new OrderListWo();
			OrderProduct product = new OrderProduct();
			if(StringUtils.isNotBlank(order.getOrderId())){
				product = orderProductDaoImpl.selectByOrderId(order.getOrderId());
			}

			if(product != null){
				productWo = OrderFrontConvert.convertProductWo(product, order);
				products.add(productWo);
			}else{
				throw ZtrBizException.instance(Const.FF_ORDE_CODE_1002, Const.FF_ORDE_REASON_1002) ;
			}
		}

		return products;

	}

	public void setCommonOrderInfo(OrderListWo order) throws Exception {
		OrderWo orderWo = order.getOrder();
		if(orderWo != null){
			String orderId = orderWo.getOrderId();
			if(StringUtils.isNotEmpty(orderId)){
				CommonOrder commonOrder = commonOrderReuseService.getRepairCommonOrderByOrderId(orderId);
				if(commonOrder != null){
					orderWo.setCommonOrderId(commonOrder.getOrderId());
					orderWo.setCommonOrderPrice(MoneyUtil.cent2Yuan(commonOrder.getPrice()));
					if(commonOrder.getStatus() != null){
						orderWo.setCommonOrderStatus(commonOrder.getStatus().toString());
					}
				}
			}
		}
	}

}
