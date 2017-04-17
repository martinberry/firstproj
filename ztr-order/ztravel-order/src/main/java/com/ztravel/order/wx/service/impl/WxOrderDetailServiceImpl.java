package com.ztravel.order.wx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.entity.AdditionalProduct;
import com.ztravel.common.enums.CredentialType;
import com.ztravel.common.enums.Gender;
import com.ztravel.common.enums.PassengerType;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.order.dao.IOrderComContactorDao;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderPassengerDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderPassenger;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.order.wx.service.IWxOrderDetailService;
import com.ztravel.order.wx.service.IWxOrderService;
import com.ztravel.order.wx.vo.ContactorInfoVo;
import com.ztravel.order.wx.vo.FlightInfoVo;
import com.ztravel.order.wx.vo.OrderPriceDetailVo;
import com.ztravel.order.wx.vo.PassengerInfoVo;
import com.ztravel.order.wx.vo.ProductHotelInfoListVo;
import com.ztravel.order.wx.vo.ProductHotelInfoVo;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.reuse.order.entity.AdditionalProductWo;
import com.ztravel.reuse.product.entity.ProductBookVo;
import com.ztravel.reuse.product.entity.ProductFlightInfo;
import com.ztravel.reuse.product.entity.ProductHotelInfo;

@Service
public class WxOrderDetailServiceImpl implements IWxOrderDetailService {

	private static Logger LOGGER = RequestIdentityLogger.getLogger(WxOrderDetailServiceImpl.class);

	@Resource
	IOrderContactorDao orderContactorDaoImpl;

	@Resource
	IOrderPassengerDao orderPassengerDaoImpl;

	@Resource
	IOrderComContactorDao orderComContactorDaoImpl;

	@Resource
	IWxOrderService wxOrderServiceImpl;

	@Resource
	ICouponService couponService;

	@Resource
	IOrderContactorDao orderContactorDao;

	@Resource
	IOrderPassengerDao orderPassengerDao;
	
	@Resource 
	IOrderProductDao orderProductDao;
	
	@Resource
	IOrderDao orderDao;


	@Override
	public ProductHotelInfoListVo getProductHotelInfoListVoByOrder(String productSnapshot) {
		ProductHotelInfoListVo hotelInfoListVo = new ProductHotelInfoListVo();
		List<ProductHotelInfoVo> productHotelInfoVoList = new ArrayList<ProductHotelInfoVo>();
		if(StringUtils.isNotBlank(productSnapshot)){
			ProductBookVo bookVo = JSON.parseObject(productSnapshot, ProductBookVo.class);
			List<ProductHotelInfo> productHotelInfoList = bookVo.getHotelList();
			if(!CollectionUtils.isEmpty(productHotelInfoList)){
				for(ProductHotelInfo hotelInfo : productHotelInfoList){
					ProductHotelInfoVo hotelInfoVo = new ProductHotelInfoVo();
					hotelInfoVo.setCheckInDate(hotelInfo.getCheckInDate());
					hotelInfoVo.setCheckOutDate(hotelInfo.getCheckOutDate());
					hotelInfoVo.setHotelName(hotelInfo.getHotelName());
					hotelInfoVo.setHotelType(hotelInfo.getHotelType());
					hotelInfoVo.setRoomType(hotelInfo.getRoomType());
					hotelInfoVo.setTripNights(DateTimeUtil.diffInDay(DateTimeUtil.convertStringToDate(hotelInfo.getCheckOutDate()), DateTimeUtil.convertStringToDate(hotelInfo.getCheckInDate())));
					productHotelInfoVoList.add(hotelInfoVo);
				}
			}
			hotelInfoListVo.setBedPrefer(bookVo.getBedPrefer());
			hotelInfoListVo.setProductHotelInfoVoLsit(productHotelInfoVoList);
			return hotelInfoListVo;
		}
		return null;
	}

	@Override
	public FlightInfoVo getFlightInfoVoByOrder(String productSnapshot,Date bookDate) {
		FlightInfoVo flightInfoVo = new FlightInfoVo();
		if(StringUtils.isNotBlank(productSnapshot)){
			ProductBookVo bookVo = JSON.parseObject(productSnapshot, ProductBookVo.class);
			List<ProductFlightInfo> goFlightList = bookVo.getGoFlightList();
			List<ProductFlightInfo> backFlightList = bookVo.getBackFlightList();
			List<ProductFlightInfo> midlFlightList = bookVo.getMidlFlightList();
			flightInfoVo.setGoFlightList(goFlightList);
			flightInfoVo.setBackFlightList(backFlightList);
			flightInfoVo.setMidlFlightList(midlFlightList);
			return flightInfoVo;
		}
		return null;
	}

	@Override
	public OrderPriceDetailVo getPriceDetailVoBySnapshot(String snapshot,Order order) throws Exception {
		OrderPriceDetailVo priceDetailVo = new OrderPriceDetailVo();
		if(StringUtils.isNotBlank(snapshot)){
			ProductBookVo bookVo = JSON.parseObject(snapshot, ProductBookVo.class);
			priceDetailVo.setAdultNum(bookVo.getAdultNum());
			priceDetailVo.setAdultPrice(bookVo.getAdultPrice());
			priceDetailVo.setChildNum(bookVo.getChildNum());
			priceDetailVo.setChildrenPrice(bookVo.getChildrenPrice());			
			priceDetailVo.setTotalAdultPrice(bookVo.getTotalAdultPrice());
			priceDetailVo.setTotalChildPrice(bookVo.getTotalChildPrice());
			Order orderpo=orderDao.selectById(order.getOrderId());
			String pieceType=orderpo.getProductNature();
			if(pieceType==null||pieceType.equals("PACKAGE")||pieceType.equals("COMBINATION")){
				priceDetailVo.setTotalSingleDiff(bookVo.getTotalSingleDiff());
				priceDetailVo.setSingleRoomDiff(bookVo.getSingleRoomDiff());
				priceDetailVo.setSingleNum(bookVo.getSingleNum());
				priceDetailVo.setPackageId(bookVo.getPackageId());
				priceDetailVo.setPackageName(bookVo.getPackageName());
				priceDetailVo.setTotalPackagePrice(bookVo.getTotalPackagePrice());
			}
			if(StringUtils.isNotBlank(order.getDiscountCouponId())){
				CouponItemBean couponItem = couponService.getCouponItem(order.getDiscountCouponId());
				priceDetailVo.setCouponSub(MoneyUtil.cent2Yuan(couponItem.getAmount()));
				priceDetailVo.setCouponName(couponItem.getName());
				priceDetailVo.setCouponId(couponItem.getCouponItemId());
			}
			return priceDetailVo;
		}
		return null;
	}

	@Override
	public ContactorInfoVo getContactorInfoByOrder(Order order) {
		ContactorInfoVo contactorInfoVo = new ContactorInfoVo();
		LOGGER.error("微信端开始查询订单[{}]联系人信息", order.getOrderId());
		if(null != order){
			try {
				OrderContactor orderContactor = orderContactorDao.selectContactorByOrderId(order.getOrderId());	
				if(null != orderContactor){
					LOGGER.info("订单[{}]联系人信息:[{}]", order.getOrderId(),TZBeanUtils.describe(orderContactor));
					contactorInfoVo.setName(orderContactor.getContactor());
					contactorInfoVo.setMobile(orderContactor.getPhone());
					contactorInfoVo.setProvince(orderContactor.getProvince());
					contactorInfoVo.setCity(orderContactor.getCity());
					contactorInfoVo.setCounty(orderContactor.getCounty());
					contactorInfoVo.setStreet(orderContactor.getAddress());
					contactorInfoVo.setComContactorId(orderContactor.getId());
					contactorInfoVo.setMail(orderContactor.getEmail());
					return contactorInfoVo;
				}
			} catch (Exception e) {
				LOGGER.error("查询订单[{}]联系人信息出错:{}", order.getOrderId(),e);
				return null;
			}
		}
		return null;
	}

	@Override
	public List<PassengerInfoVo> getPassengerInfoVoListByOrder(Order order,Boolean isDomestic) {
		if(null != order){
			List<PassengerInfoVo> passengerInfoVoList = new ArrayList<PassengerInfoVo>();
			try {
				LOGGER.error("微信端开始查询订单[{}]乘客信息", order.getOrderId());
				List<OrderPassenger> passengerList = orderPassengerDao.selectByOrderId(order.getOrderId());
				if(!CollectionUtils.isEmpty(passengerList)){
					LOGGER.info("订单[{}]乘客信息:[{}]", order.getOrderId(),TZBeanUtils.describe(passengerList));
					for(OrderPassenger passenger : passengerList){
						PassengerInfoVo passengerInfoVo  = passenger2Vo(passenger,isDomestic);
						passengerInfoVoList.add(passengerInfoVo);
					}
					return passengerInfoVoList;
				}
			} catch (Exception e) {
				LOGGER.error("查询订单[{}]乘客信息出错:{}", order.getOrderId(),e);
				return null;
			}
		}
		return null;
	}

	public PassengerInfoVo passenger2Vo(OrderPassenger passenger,Boolean isDomestic){
		PassengerInfoVo passengerInfoVo = new PassengerInfoVo();
		passengerInfoVo.setPassengerId(passenger.getId());
		passengerInfoVo.setName(passenger.getName());
		passengerInfoVo.setFirstName(passenger.getFirstName());
		passengerInfoVo.setLastName(passenger.getLastName());
		passengerInfoVo.setGender(passenger.getGender());
		passengerInfoVo.setFirstEnName(passenger.getFirstEnName());
		passengerInfoVo.setLastEnName(passenger.getLastEnName());
		passengerInfoVo.setCredentialType(passenger.getCredentialType());
		passengerInfoVo.setCredentialTypeDesc(CredentialType.valueOf(passenger.getCredentialType()).getDesc());
		passengerInfoVo.setCredentialNum(passenger.getCredentialNum());
		passengerInfoVo.setPassengerTypeDesc(PassengerType.valueOf(passenger.getPassengerType()).getDesc());
		passengerInfoVo.setGenderDesc(Gender.valueOf(passenger.getGender()).getDesc());
		passengerInfoVo.setBirthday(passenger.getBirthday());//差乘客中英文姓名,待分支合并后修改
		passengerInfoVo.setEnName(passenger.getEnName());
		passengerInfoVo.setCountry(passenger.getCountry());
		passengerInfoVo.setCredentialDeadLine(passenger.getCredentialDeadLine());
		return passengerInfoVo;
	}

	@Override
	public List<AdditionalProductWo> getAdditionalProductsBySnapshot(String productSnapshot) throws Exception {
		if(StringUtils.isNotBlank(productSnapshot)){
			ProductBookVo bookVo = JSON.parseObject(productSnapshot, ProductBookVo.class);
			if(bookVo != null){
				List<AdditionalProductWo> additionalProductWos = Lists.newArrayList();
				List<AdditionalProduct> additionalProducts = bookVo.getAdditionalProducts();
				if(additionalProducts != null && !additionalProducts.isEmpty()){
					for(AdditionalProduct additionalProduct : additionalProducts){
						AdditionalProductWo additionalProductWo = new AdditionalProductWo();
						additionalProductWo.setPrice(MoneyUtil.cent2Yuan(additionalProduct.getPrice()));
						additionalProductWo.setTotalPrice(MoneyUtil.cent2Yuan(additionalProduct.getTotalPrice()));
						additionalProductWo.setNum(additionalProduct.getNum());
						additionalProductWo.setName(additionalProduct.getName());
						additionalProductWos.add(additionalProductWo);
					}
				}
				return additionalProductWos;
			}
		}
		return null;
	}
}
