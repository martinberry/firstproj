package com.ztravel.order.client.convert;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.entity.ContactorInfo;
import com.ztravel.common.entity.PassengerInfo;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.OrderOperate;
import com.ztravel.common.enums.PieceType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderComContactor;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderPassenger;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.product.client.entity.ProductClientEntity;
import com.ztravel.reuse.order.entity.OrderPayVo;

public class ProductBookItemConvert {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(ProductBookItemConvert.class);
	
	/**
	 * 根据预订信息构建订单
	 * @param productBookItem
	 * @return
	 * @throws Exception
	 */
	public static Order buildOrderByBookitem(ProductBookItem productBookItem,String orderId,String orderNum) throws Exception {
		Order order = new Order();
		JSONObject operateRecord = new JSONObject();
		//产品要求建单与去支付操作记录同时加 也是醉了
		operateRecord.put(OrderOperate.CREATE.getCode(), DateTimeUtil.date10());
		operateRecord.put(OrderOperate.PAY.getCode(), DateTimeUtil.date10());
		order.setOperateRecord(operateRecord.toJSONString());
		order.setOrderId(orderId);
		order.setOrderNo(orderNum.substring(2));
		order.setOrderType(productBookItem.getIsDomestic());

		order.setOrderFrom(productBookItem.getOrderFrom());
		order.setCreateDate(new Date());
		order.setBedPrefer(productBookItem.getBedPrefer());
		order.setRemark(productBookItem.getRemark());
		order.setCreator(SSOUtil.getMemberSessionBean().getMid());
		order.setOperator(SSOUtil.getMemberSessionBean().getMid());
		order.setDiscountCouponId(productBookItem.getDiscountcouponId());
		order.setProgress(1);
		order.setFrontState(ZtOrderStatus.UNPAY.getCode());
		order.setBackState(ZtOrderStatus.UNPAY.getCode());
		order.setStateChangeHistory("created");
		order.setPayAmount(productBookItem.getPayAmount());
		order.setTotalPrice(productBookItem.getTotalPrice());
		order.setCouponSub(productBookItem.getCouponSub());
		order.setIntegralSub(productBookItem.getIntegralSub());
		order.setUpdateDate(new Date());
		order.setIsToPay(false);
		order.setProductNature(productBookItem.getNature());
		LOGGER.info(TZMarkers.p4,"提交的订单信息：[{}]", JSONObject.toJSONString(order));
		return order;
	}
	
	public static OrderComContactor buildComContactorByBookitem(ProductBookItem productBookItem ,String comContactorId) {
		OrderComContactor comContactor = new OrderComContactor();
		ContactorInfo bookContactorInfo = productBookItem.getContactorInfo();
		comContactor.setMemberId(SSOUtil.getMemberSessionBean().getMemberId());
		comContactor.setAddress(bookContactorInfo.getAddressDetail());
		comContactor.setCity(bookContactorInfo.getCity());
		comContactor.setCounty(bookContactorInfo.getCounty());
		comContactor.setContactor(bookContactorInfo.getName());
		comContactor.setEmail(bookContactorInfo.getEmail());
		comContactor.setPhone(bookContactorInfo.getPhone());
		comContactor.setProvince(bookContactorInfo.getProvince());
		comContactor.setId(comContactorId == null ? UUID.randomUUID().toString() : comContactorId);
		LOGGER.info(TZMarkers.p4,"用户[{}]常用联系人信息联系人信息：[{}]", JSONObject.toJSONString(comContactor));
		return comContactor;
	}
	
	/**
	 * 根据预订信息构建订单联系人
	 * @param productBookItem
	 * @return
	 */
	public static OrderContactor buildOrderContactorByBookitem(ProductBookItem productBookItem ,String orderId) {
		OrderContactor orderContactor = new OrderContactor();
		ContactorInfo bookContactorInfo = productBookItem.getContactorInfo();

		orderContactor.setAddress(bookContactorInfo.getAddressDetail());
		orderContactor.setCity(bookContactorInfo.getCity());
		orderContactor.setCounty(bookContactorInfo.getCounty());
		orderContactor.setContactor(bookContactorInfo.getName());
		orderContactor.setEmail(bookContactorInfo.getEmail());
		orderContactor.setOrderId(orderId);
		orderContactor.setPhone(bookContactorInfo.getPhone());
		orderContactor.setProvince(bookContactorInfo.getProvince());
		orderContactor.setId(UUID.randomUUID().toString());
		LOGGER.info(TZMarkers.p4,"订单[{}]联系人信息：[{}]", orderId,JSONObject.toJSONString(orderContactor));
		return orderContactor;
	}
	
	/**
	 * 根据预订信息构建订单乘客
	 * @param productBookItem
	 * @return
	 */
	public static List<OrderPassenger> buildOrderPassengerByBookitem(ProductBookItem productBookItem, String orderId) {
		List<OrderPassenger> passengerList = new LinkedList<OrderPassenger>();
		for(PassengerInfo passenger : productBookItem.getPassengerList()) {
			OrderPassenger orderPassenger = new OrderPassenger();
			orderPassenger.setId(UUID.randomUUID().toString());
			orderPassenger.setBirthday(passenger.getBirthday());
			orderPassenger.setCountry(passenger.getCountry());
			orderPassenger.setCredentialDeadLine(passenger.getCredentialsDeadLine());
			orderPassenger.setCredentialNum(passenger.getCredentialNum());
			orderPassenger.setCredentialType(passenger.getCredentialType());
			orderPassenger.setFirstName(passenger.getFirstName());
			orderPassenger.setGender(passenger.getGender());
			orderPassenger.setLastName(passenger.getLastName());
			orderPassenger.setName(passenger.getPassengerName());
			orderPassenger.setOrderId(orderId);
			orderPassenger.setPassengerType(passenger.getType());
			if(StringUtils.isNotBlank(passenger.getFirstNameEn())){
				orderPassenger.setFirstEnName(passenger.getFirstNameEn());
			}
			if(StringUtils.isNotBlank(passenger.getLastNameEn())){
				orderPassenger.setLastEnName(passenger.getLastNameEn());
			}
			if(StringUtils.isNotBlank(passenger.getPassengerEnName())){
				orderPassenger.setEnName(passenger.getPassengerEnName());
			}
			passengerList.add(orderPassenger);
		}
		LOGGER.info(TZMarkers.p4,"订单[{}]乘客信息：[{}]", orderId,JSONObject.toJSONString(passengerList));
		return passengerList;
	}
	
	public static OrderPayVo buildOrderPayVo(ProductBookItem item,String orderId,String orderNo){
		OrderPayVo payVo = new OrderPayVo();
		if(item.getProductType().equals(Nature.PACKAGE.name()) || item.getProductType().equals(Nature.COMBINATION.name())){
			payVo.setProductType(ProductType.TRAVEL.name());
		}else{
			if(item.getNature().equals(PieceType.VISA.name())){
				payVo.setProductType(ProductType.VISA.name());
			}else{
				payVo.setProductType(ProductType.UNVISA.name());
			}
		}
		payVo.setMemberId(SSOUtil.getMemberSessionBean().getMemberId());
		payVo.setPayAmount(item.getPayAmount());
		payVo.setDiscountCoupon(item.getCouponSub());
		payVo.setUseRewardPoint(item.getIntegralSub() == null ? 0 : item.getIntegralSub());
		payVo.setTitle(item.getProductTitle());
		payVo.setOrderId(orderId);
		payVo.setOrderCode(orderNo);
		payVo.setDiscountCoupon(item.getCouponSub());
		payVo.setCouponItemId(StringUtils.isBlank(item.getDiscountcouponId()) ? "" : item.getDiscountcouponId());//支付时,如果代金券未选或者为null直接设置为空字符串
		payVo.setTotalPrice(item.getTotalPrice());
		payVo.setUseRewardPoint(item.getIntegralSub());
		return payVo;
	}
	
	/**
	 * 根据预订信息构建订单产品
	 * @param productBookItem
	 * @return
	 * @throws Exception
	 */
	public static OrderProduct buildOrderProductByBookitem(ProductBookItem productBookItem, String orderId,ProductClientEntity productClientEntity) throws Exception {
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setSnapshotId(productBookItem.getProductShapshotId());
		orderProduct.setBookDate(DateTimeUtil.convertStringToDate(productBookItem.getBookDate()).toDate());
		orderProduct.setOrderId(orderId);
		orderProduct.setBackDate(DateTime.parse(productBookItem.getBookDate()).plusDays(productBookItem.getTripDays() - 1).toDate());
		orderProduct.setProductId(productBookItem.getProductId());
		orderProduct.setProductSnapshot(productBookItem.getShapShot());
		orderProduct.setProductTitle(productBookItem.getProductTitle());
		orderProduct.setProductType(productBookItem.getProductType());
		orderProduct.setProductNo(productBookItem.getProductNo());
		orderProduct.setImageId(productBookItem.getFirstImageId());
		orderProduct.setProviderInfo(productClientEntity.getProviderInfo());
		orderProduct.setTripDays(productBookItem.getTripDays());
		orderProduct.setPackageId(productBookItem.getPackageId());
		orderProduct.setPackageName(productBookItem.getPackageName());
		LOGGER.info(TZMarkers.p4,"订单[{}]的产品信息：[{}]", orderId,JSONObject.toJSONString(orderProduct));
		return orderProduct;
	}
}
