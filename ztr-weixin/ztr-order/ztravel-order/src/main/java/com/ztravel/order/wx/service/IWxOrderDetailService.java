package com.ztravel.order.wx.service;

import java.util.Date;
import java.util.List;

import com.ztravel.order.po.Order;
import com.ztravel.order.wx.vo.ContactorInfoVo;
import com.ztravel.order.wx.vo.FlightInfoVo;
import com.ztravel.order.wx.vo.OrderPriceDetailVo;
import com.ztravel.order.wx.vo.PassengerInfoVo;
import com.ztravel.order.wx.vo.ProductHotelInfoListVo;
import com.ztravel.reuse.order.entity.AdditionalProductWo;

public interface IWxOrderDetailService {

	ProductHotelInfoListVo getProductHotelInfoListVoByOrder(String productSnapshot);

	FlightInfoVo getFlightInfoVoByOrder(String productSnapshot,Date bookDate);

	OrderPriceDetailVo getPriceDetailVoBySnapshot(String snapshot,Order order) throws Exception;

	List<PassengerInfoVo> getPassengerInfoVoListByOrder(Order order,Boolean isDomestic);

	ContactorInfoVo getContactorInfoByOrder(Order order);

	List<AdditionalProductWo> getAdditionalProductsBySnapshot(String productSnapshot) throws Exception;

}
