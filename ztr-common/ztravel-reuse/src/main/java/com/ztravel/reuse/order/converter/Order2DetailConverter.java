package com.ztravel.reuse.order.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.travelzen.framework.core.util.MoneyUtil;
import com.ztravel.common.entity.AdditionalProduct;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.reuse.order.entity.AdditionalProductWo;
import com.ztravel.reuse.order.entity.BookPriceWo;
import com.ztravel.reuse.order.entity.OrderDetailWo;
import com.ztravel.reuse.order.entity.OrderProductWo;
import com.ztravel.reuse.order.entity.OrderWo;
import com.ztravel.reuse.product.entity.ProductBookVo;

public class Order2DetailConverter {
	
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
    
    
    public static void convertOrderDetailWo(Order order, OrderProduct product, OrderDetailWo orderDetail){

		Map<String, String> orderOperate = new HashMap<>();
		OrderWo orderWo = new OrderWo();
		BookPriceWo priceWo = new BookPriceWo();
		OrderProductWo productWo = new OrderProductWo();

		ProductBookVo productBook = JSON.parseObject(product.getProductSnapshot(), ProductBookVo.class);

		orderDetail.setIsDomestic("domestic".equalsIgnoreCase(productBook.getIsDomestic()) ? "1": "0");
		if(order.getBedPrefer()!=null)
		orderDetail.setBedPrefer(order.getBedPrefer());

		List<AdditionalProduct> additionalProducts = productBook.getAdditionalProducts();

		if(additionalProducts != null && !additionalProducts.isEmpty()){
			List<AdditionalProductWo> additionalProductWos = convertAdditionProducts2AdditionalProductWos(additionalProducts);
			orderDetail.setAdditionalProducts(additionalProductWos);
		}


		if(ZtOrderStatus.getByCode(order.getFrontState()) == ZtOrderStatus.CANCELED || ZtOrderStatus.getByCode(order.getFrontState()) == ZtOrderStatus.REFUNDING){
			//已取消订单不可更改订单信息
			orderDetail.setCanChange(false);
			//已取消订单不显示订单操作
			orderDetail.setOrderOperate(orderOperate);
		}else{
			if(null != product.getBookDate()){
				orderDetail.setCanChange(isCanChange(product.getBookDate()));
			}

			buildOrderOperate(orderOperate, order.getOperateRecord());
			orderDetail.setOrderOperate(orderOperate);
		}

		OrderReuseConverter.buildproductWo(productWo, product);
		buildproductWoDetail(productWo, productBook);
		orderDetail.setProduct(productWo);

		OrderReuseConverter.buildOrderWo(orderWo, order);
		orderDetail.setOrder(orderWo);

		buildBookPriceWo(priceWo, productBook, order.getDiscountCouponId(),product.getPieceType());
		orderDetail.setPrice(priceWo);
	}
    private static List<AdditionalProductWo> convertAdditionProducts2AdditionalProductWos(List<AdditionalProduct> additionalProducts) {
		List<AdditionalProductWo> additonalProductWos = Lists.newArrayList();

		for(AdditionalProduct additionalProduct :additionalProducts){
			AdditionalProductWo additionalProductWo = new AdditionalProductWo();
			additionalProductWo.setName(additionalProduct.getName());
			additionalProductWo.setNum(additionalProduct.getNum());
			additionalProductWo.setPrice(MoneyUtil.cent2Yuan(additionalProduct.getPrice()));
			additionalProductWo.setTotalPrice(MoneyUtil.cent2Yuan(additionalProduct.getTotalPrice()));
			additonalProductWos.add(additionalProductWo);
		}
		return additonalProductWos;
	}
    
    private static Boolean isCanChange(Date departDay) {

		DateTime now = new DateTime() ;

//		DateTime date = DateTime.parse(departDay) ;

		long interval = (departDay.getTime() - now.getMillis())/(3600 * 24 * 1000);

		return (interval >= 15) ;
	}
    
    private static void buildOrderOperate(Map<String, String> orderOperate, String operateRecord){

		if(StringUtils.isNotBlank(operateRecord)){
			JSONObject record = (JSONObject) JSONObject.parse(operateRecord);
			for(String key : record.keySet()){
				orderOperate.put(key, record.get(key).toString());
			}
		}

	}
    
    private static void buildproductWoDetail(OrderProductWo productWo, ProductBookVo productBook){
       if(productBook.getGoFlightList()!=null)
		productWo.setGoFlightList(productBook.getGoFlightList());
       if(productBook.getMidlFlightList()!=null)
		productWo.setMidlFlightList(productBook.getMidlFlightList());
       if(productBook.getBackFlightList()!=null)
		productWo.setBackFlightList(productBook.getBackFlightList());
       if(productBook.getHotelList()!=null)
		productWo.setHotelList(productBook.getHotelList());
       if(productBook.getCostPriceName()!=null)
    	productWo.setCostPriceName(productBook.getCostPriceName());   
		if(productBook.getAdditionalInfos() != null && ! productBook.getAdditionalInfos().isEmpty()){
			//转换附加信息, 包含费用说明
			Map<AdditionalRule, String> additionalInfos = productBook.getAdditionalInfos();
			Map<String, String> infos = new HashMap<>();
			if(additionalInfos != null && additionalInfos.keySet() != null){
				Iterator<AdditionalRule> it = additionalInfos.keySet().iterator();
				while(it.hasNext()){
					AdditionalRule rule = it.next();
					infos.put(rule.toString(), additionalInfos.get(rule));
					}
			}
			productWo.setAdditionalInfos(infos);
		}
	}
    
    private static void buildBookPriceWo(BookPriceWo priceWo, ProductBookVo productBook, String couponId,String productNature){

		priceWo.setAdultPrice(productBook.getAdultPrice());
		priceWo.setAdultNum(productBook.getAdultNum());
		priceWo.setTotalAdultPrice(productBook.getTotalAdultPrice());
		priceWo.setChildrenPrice(productBook.getChildrenPrice());
		priceWo.setChildNum(productBook.getChildNum());
		priceWo.setTotalChildPrice(productBook.getTotalChildPrice());		
		if(StringUtils.isEmpty(productNature)||productNature=="PACKAGE"||productNature=="COMBINATION"){
			if(productBook.getSingleRoomDiff()!=null)
				priceWo.setSingleRoomDiff(productBook.getSingleRoomDiff());
				if(productBook.getSingleNum()!=null)
				priceWo.setSingleNum(productBook.getSingleNum());
				if(productBook.getTotalSingleDiff()!=null)
				priceWo.setTotalSingleDiff(productBook.getTotalSingleDiff());
		        if(productBook.getPackageId()!=null)
				priceWo.setPackageId(productBook.getPackageId());
		        if(productBook.getPackageName()!=null)
				priceWo.setPackageName(productBook.getPackageName());
		        if(productBook.getTotalPackagePrice()!=null)
				priceWo.setTotalPackagePrice(productBook.getTotalPackagePrice());
		}

		priceWo.setCouponId(couponId);


	}
    
    
    
}
