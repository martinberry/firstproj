package com.ztravel.reuse.order.converter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.util.PriceUtil;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.reuse.order.entity.BookPriceWo;
import com.ztravel.reuse.order.entity.OrderDetailWo;
import com.ztravel.reuse.order.entity.OrderProductWo;
import com.ztravel.reuse.order.entity.OrderWo;
import com.ztravel.reuse.product.entity.ProductBookVo;

public class OrderReuseConverter {
	
	private static Map<ZtOrderStatus, String> nextStepConvertor;
    static {
    	nextStepConvertor = new HashMap<ZtOrderStatus, String>();
        // 新建,等待支付 -- 支付
    	nextStepConvertor.put(ZtOrderStatus.UNPAY, "去支付");
    	// -- 已确认
    	nextStepConvertor.put(ZtOrderStatus.OPCONFIRM, "已确认");
    	// 已完成 -- 评价
    	nextStepConvertor.put(ZtOrderStatus.COMPLETED, "评价");

    }
	
	public static void buildproductWo(OrderProductWo productWo, OrderProduct product){

		productWo.setProductId(product.getProductId());
		productWo.setProductTitle(product.getProductTitle());
//		productWo.setProductType(product.getProductType());
		productWo.setImageId(product.getImageId());
		productWo.setBookDate(DateTimeUtil.date10(product.getBookDate()));
		productWo.setBackDate(DateTimeUtil.date10(product.getBackDate()));

		if(product.getProductSnapshot() != null){
			ProductBookVo productBook = JSON.parseObject(product.getProductSnapshot(), ProductBookVo.class);
			Preconditions.checkNotNull(productBook);
			productWo.setPackageId(productBook.getPackageId());
			productWo.setPackageName(productBook.getPackageName());
		}
		productWo.setProductNo(product.getProductNo());
		productWo.setProductSnapshot(product.getProductSnapshot());
		if(product.getTripDays()!=null)
		productWo.setTripDays(product.getTripDays());
		if(product.getProviderInfo()!=null)
		productWo.setSupplier(buildSupplier(product.getProviderInfo()));
		
	}
	
		
	public static String buildSupplier(String providerInfo){
		String supplier = "";
		if(StringUtils.isNotBlank(providerInfo)){
			JSONObject json = JSON.parseObject(providerInfo);
			//产品性质决定供应商类型的逻辑在IProductClientService.getProductById中已处理，此处不需再判断
			JSONObject packageSupplier = json.getJSONObject("packageSupplier");
			if( packageSupplier != null ){
				String packSupName = packageSupplier.getString("name");
				if(  StringUtils.isNotBlank(packSupName) ){
					supplier += "打包产品供应商: " + packSupName;
				}
			}
			JSONObject flightSupplier = json.getJSONObject("flightSupplier");
			if( flightSupplier != null ){
				String flightSupName = flightSupplier.getString("name");
				if( StringUtils.isNotBlank(flightSupName) ){
					supplier += "机票供应商: " + flightSupName + "  ";
				}
			}
			String hotelSupplier = json.getString("hotelSupplier");
			if( hotelSupplier != null ){
				String hotelSupName = hotelSupplier ;
				if( StringUtils.isNotBlank(hotelSupName) ){
					supplier += "酒店供应商: " + hotelSupName;
				}
			}
		}
		return supplier;
	}
	
	public static void buildOrderWo(OrderWo orderWo, Order order){

		orderWo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(order.getCreateDate()));
		orderWo.setCreator(order.getCreator());
		orderWo.setOrderId(order.getOrderId());
		orderWo.setOrderNo(order.getOrderNo());
		orderWo.setOrderType(order.getOrderType());
		orderWo.setTotalPrice(PriceUtil.cent2yuan(order.getTotalPrice()));
		orderWo.setPayAmount(PriceUtil.cent2yuan(order.getPayAmount()));
		orderWo.setCouponSub(PriceUtil.cent2yuan(order.getCouponSub()));
		orderWo.setIntegralSub(PriceUtil.cent2yuan(order.getIntegralSub()));
		orderWo.setBackState(order.getBackState());
		orderWo.setProductNature(order.getProductNature());
		if(order.getCouponSub() != 0 && order.getCouponSub() != null && order.getIntegralSub() != null){
			orderWo.setDiscountTotalSub(PriceUtil.cent2yuan(order.getCouponSub() + order.getIntegralSub()));
		}
		if(StringUtils.isNotBlank(order.getFrontState())){
			if(order.getFrontState().equals(ZtOrderStatus.OPCONFIRM.getCode())){
				orderWo.setFrontState("已确认");
			}else{
				orderWo.setFrontState(ZtOrderStatus.getByCode(order.getFrontState()).getDesc());
			}
			orderWo.setNextStep(nextStepConvertor.get(ZtOrderStatus.getByCode(order.getFrontState())));
		}

		//如果有补款单id存在，并且补款单status 为unpay
		//设置补款金额，以及nextStep为去补款，以及补款订单id

	}
	
	public static void setOrderProductAndCoupon(CouponItemBean couponItem, OrderDetailWo orderDetail){

		buildCoupon(couponItem, orderDetail);

	}
	
	public static void buildCoupon(CouponItemBean couponItem, OrderDetailWo orderDetail){

		BookPriceWo priceWo = orderDetail.getPrice();
		priceWo.setCouponName(couponItem.getName());
		orderDetail.setPrice(priceWo);

	}

}
