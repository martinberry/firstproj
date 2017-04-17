package com.ztravel.order.back.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.areasearch.entity.ProvCityArea;
import com.ztravel.common.areasearch.service.IAddressService;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.util.PriceUtil;
import com.ztravel.order.back.service.IOrderService;
import com.ztravel.order.back.vo.ConfirmOrderWo;
import com.ztravel.order.back.vo.FlightSaveWo;
import com.ztravel.order.back.vo.FlightWo;
import com.ztravel.order.back.vo.HotelSaveWo;
import com.ztravel.order.back.vo.HotelWo;
import com.ztravel.order.back.vo.OrderDetailVO;
import com.ztravel.order.front.validate.OrderValidation;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderPassenger;
import com.ztravel.reuse.order.entity.AdditionalProductWo;
import com.ztravel.reuse.order.entity.AttachmentWo;
import com.ztravel.reuse.order.entity.BookPriceWo;
import com.ztravel.reuse.order.entity.OrderDetailWo;
import com.ztravel.reuse.order.entity.OrderProductWo;
import com.ztravel.reuse.order.entity.OrderWo;
import com.ztravel.reuse.product.entity.ProductBookVo;
import com.ztravel.reuse.product.entity.ProductFlightInfo;
import com.ztravel.reuse.product.entity.ProductHotelInfo;
import com.ztravel.sraech.client.service.IAutoComplete;

/**
 * @author xutian
 */
@Controller
@RequestMapping("/order/travelConfirm")
public class TravelConfirmController {

    private static Logger logger = LoggerFactory.getLogger(TravelConfirmController.class);

    @Resource(name="tThriftAutoComplete")
    IAutoComplete autoComplete;

    @Resource
    IAddressService addressServiceImpl;

    @Resource
    IOrderService  orderServiceImpl;

    @RequestMapping("/show/{orderId}")
    public String showTravelConfirmSheet(@PathVariable String orderId, Model model){

        try{
            OrderDetailWo orderDetail = orderServiceImpl.selectConfirmOrderById(orderId);
            OrderDetailWo originalOrderDetail = orderServiceImpl.selectOriginalOrderById(orderId);
            if (originalOrderDetail != null) {
                String originalPayAmount = originalOrderDetail.getOrder().getPayAmount();
                model.addAttribute("originalPayAmount", originalPayAmount);
            }
            model.addAttribute("orderDetail", orderDetail);

            OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(orderId);
            if (orderDetailVO == null ||orderDetailVO.getStatus() == null || ZtOrderStatus.CANCELED.getDesc().equals(orderDetailVO.getStatus()) || ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
                model.addAttribute("orderNotCancelled", false);
            } else {
                model.addAttribute("orderNotCancelled", true);
            }

        }catch(Exception e){
            logger.error("failed to get order detail by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        model.addAttribute("readOnly", true);
        return "order/back/freetravel/travelConfirm/mainSheet";
    }

    @RequestMapping("/edit/{orderId}")
    public String editTravelConfirmSheet(@PathVariable String orderId, Model model){

        try{
            OrderDetailWo orderDetail =  orderServiceImpl.selectConfirmOrderById(orderId);
            OrderDetailWo originalOrderDetail = orderServiceImpl.selectOriginalOrderById(orderId);
            if (originalOrderDetail != null) {
                String originalPayAmount = originalOrderDetail.getOrder().getPayAmount();
                model.addAttribute("originalPayAmount", originalPayAmount);
            }
            model.addAttribute("orderDetail", orderDetail);

        }catch(Exception e){
            logger.error("failed to get order detail by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        model.addAttribute("readOnly", false);
        return "order/back/freetravel/travelConfirm/mainSheet";
    }

    @RequestMapping(value = "/loadProvince", method = RequestMethod.POST)
    public ModelAndView loadProvince(Model model){

        List<ProvCityArea> provinceList = addressServiceImpl.getAddressByNameAndLevel(null, "1");
        model.addAttribute("provinceList", provinceList);
        return new ModelAndView("/order/front/orderDetail/provinceDropDownMenu") ;
    }

    @RequestMapping(value = "/loadCity", method = RequestMethod.POST)
    public ModelAndView loadCity(@RequestBody String provinceName, Model model){

        if(StringUtils.isNotBlank(provinceName)){
            List<ProvCityArea> cityList = addressServiceImpl.getAddressByNameAndLevel(provinceName, "2");
            model.addAttribute("cityList", cityList);
        }
        return new ModelAndView("/order/front/orderDetail/cityDropDownMenu") ;
    }

    @RequestMapping(value = "/loadCounty", method = RequestMethod.POST)
    public ModelAndView loadCounty(@RequestBody String cityName, Model model){

        if(StringUtils.isNotBlank(cityName)){
            List<ProvCityArea> countyList = addressServiceImpl.getAddressByNameAndLevel(cityName, "3");
            model.addAttribute("countyList", countyList);
        }
        return new ModelAndView("/order/front/orderDetail/countyDropDownMenu") ;
    }

    @RequestMapping(value = "/addAttachment", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addAttachment(@RequestBody ConfirmOrderWo confirmOrderWo, Model model) throws Exception {

        OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(confirmOrderWo.getOrderId());
        if (orderDetailVO == null ||orderDetailVO.getStatus() == null || !ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
            return AjaxResponse.instance("FAILURE","订单不存在或订单不是已支付状态");
        }

        if(confirmOrderWo == null || confirmOrderWo.getAttachment() == null
                || StringUtils.isBlank(confirmOrderWo.getAttachment().getName()) || StringUtils.isBlank(confirmOrderWo.getAttachment().getMediaId())
                || StringUtils.isBlank(confirmOrderWo.getOrderId())) {
            logger.info("addAttachment args -- confirmOrderWo:::[{}]", TZBeanUtils.describe(confirmOrderWo));
            return AjaxResponse.instance("FAILURE","缺少文件参数信息");
        }

        String orderId = confirmOrderWo.getOrderId();
        AttachmentWo attachmentWo = confirmOrderWo.getAttachment();

        AjaxResponse ajaxResponse =  AjaxResponse.instance("FAILURE","");
        try {
            OrderDetailWo orderDetailWo = orderServiceImpl.selectConfirmOrderById(orderId);
            if (orderDetailWo != null) {
                List<AttachmentWo> attachments = orderDetailWo.getAttachments();
                if (attachments == null) {
                    attachments = new ArrayList<AttachmentWo>();
                }
                attachments.add(attachmentWo);
                orderDetailWo.setAttachments(attachments);
                Boolean flag = orderServiceImpl.updateTempOrder(orderDetailWo, orderId);
                if (flag) {
                    ajaxResponse = AjaxResponse.instance("SUCCESS", "");
                }
            }
        } catch (Exception e) {
            logger.error("failed to addAttachment by orderId", e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "/deleteAttachment", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse deleteAttachment(String orderId, String mediaId, Model model) throws Exception {

        if(StringUtils.isBlank(orderId) || StringUtils.isBlank(mediaId)) {
            logger.info("deleteAttachment args -- orderId:::[{}],mediaId:::[{}]", orderId, mediaId);
            return AjaxResponse.instance("FAILURE","缺少文件参数信息");
        }

        OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(orderId);
        if (orderDetailVO == null ||orderDetailVO.getStatus() == null || !ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
            return AjaxResponse.instance("FAILURE","订单不存在或订单不是已支付状态");
        }

        AjaxResponse ajaxResponse =  AjaxResponse.instance("FAILURE","");
        try {
            OrderDetailWo orderDetailWo = orderServiceImpl.selectConfirmOrderById(orderId);
            if (orderDetailWo != null) {
                List<AttachmentWo> attachments = orderDetailWo.getAttachments();
                boolean flag = false;
                for(AttachmentWo attachment : attachments) {
                    if (mediaId.equals(attachment.getMediaId())) {
                        attachments.remove(attachment);
                        flag = true;
                        break ;
                    }
                }
                if (flag) {
                    flag = orderServiceImpl.updateTempOrder(orderDetailWo, orderId);
                    if (flag) {
                        ajaxResponse = AjaxResponse.instance("SUCCESS", "");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("failed to addAttachment by orderId", e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "/updateContactor", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse updateContactor(@RequestBody ConfirmOrderWo confirmOrderWo, Model model) throws Exception {

        OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(confirmOrderWo.getOrderId());
        if (orderDetailVO == null ||orderDetailVO.getStatus() == null || !ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
            return AjaxResponse.instance("FAILURE","订单不存在或订单不是已支付状态");
        }

        try{
            logger.info("updateContactor args -- confirmOrderWo:::[{}]", TZBeanUtils.describe(confirmOrderWo));

            if(confirmOrderWo == null || confirmOrderWo.getOrderContactor() == null || !OrderValidation.validateOrderContactor(confirmOrderWo.getOrderContactor())){
                return AjaxResponse.instance(Const.FF_ORDE_CODE_1005, Const.FF_ORDE_REASON_1005);
            }

            OrderContactor newOrderContactor = confirmOrderWo.getOrderContactor();
            String orderId = confirmOrderWo.getOrderId();

            OrderDetailWo orderDetailWo = orderServiceImpl.selectConfirmOrderById(orderId);
            if (orderDetailWo != null) {

                if(orderDetailWo.getOrderContactor() != null && newOrderContactor.getId().equals(newOrderContactor.getId())){
                    orderDetailWo.setOrderContactor(newOrderContactor);
                    Boolean flag = orderServiceImpl.updateTempOrder(orderDetailWo, orderId);
                    if (!flag) {
                        return AjaxResponse.instance(Const.FF_ORDE_CODE_1006, Const.FF_ORDE_REASON_1006);
                    }
                }
            }
        }catch(SQLException ex){
            logger.error("failed to update orderContactor from DB", ex);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1006, Const.FF_ORDE_REASON_1006);
        }catch(Exception e){
            logger.error("failed to update orderContactor", e);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1007, Const.FF_ORDE_REASON_1007);
        }

        return AjaxResponse.instance(Const.SF_ORDE_CODE_1001, Const.SUCCESS);
    }

    @RequestMapping("/contactor/refresh/{orderId}")
    public String refreshContactorTab(@PathVariable String orderId, Model model){

        try{
            OrderDetailWo orderDetail =  orderServiceImpl.selectConfirmOrderById(orderId);
            model.addAttribute("contactor", orderDetail.getOrderContactor());

        }catch(Exception e){
            logger.error("failed to get order detail by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        model.addAttribute("readOnly", false);
        return "order/back/freetravel/travelConfirm/contactorTab";
    }

    @RequestMapping(value = "/updatePassengers", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse updatePassengers(@RequestBody ConfirmOrderWo confirmOrderWo, Model model) throws Exception {

        OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(confirmOrderWo.getOrderId());
        if (orderDetailVO == null ||orderDetailVO.getStatus() == null || !ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
            return AjaxResponse.instance("FAILURE","订单不存在或订单不是已支付状态");
        }

        try{
            logger.info("updatePassengers args -- confirmOrderWo:::[{}]", TZBeanUtils.describe(confirmOrderWo));

            List<OrderPassenger> passengers = new ArrayList<OrderPassenger>();
            if (confirmOrderWo == null || confirmOrderWo.getPassengers() == null) {
                return AjaxResponse.instance(Const.FF_ORDE_CODE_1019, Const.FF_ORDE_REASON_1019);
            } else {
                passengers = confirmOrderWo.getPassengers();
                for(OrderPassenger passenger : passengers){
                    if(!OrderValidation.validateOrderPassenger(passenger)
                            ||(StringUtils.isNotBlank(passenger.getCountry()) && !isExistNationalName(passenger.getCountry()))){
                        return AjaxResponse.instance(Const.FF_ORDE_CODE_1010, Const.FF_ORDE_REASON_1010);
                    }
                }
            }

            String orderId = confirmOrderWo.getOrderId();

            OrderDetailWo orderDetailWo = orderServiceImpl.selectConfirmOrderById(orderId);
            if (orderDetailWo != null) {

                if(orderDetailWo.getPassengers() != null && passengers.size() > 0){
                    orderDetailWo.setPassengers(passengers);
                    Boolean flag = orderServiceImpl.updateTempOrder(orderDetailWo, orderId);
                    if (!flag) {
                        return AjaxResponse.instance(Const.FF_ORDE_CODE_1011, Const.FF_ORDE_REASON_1011);
                    }
                }
            }
        }catch(SQLException ex){
            logger.error("failed to update orderPassenger from DB", ex);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1011, Const.FF_ORDE_REASON_1011);
        }catch(Exception e){
            logger.error("failed to update orderPassenger", e);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1012, Const.FF_ORDE_REASON_1012);
        }

        return AjaxResponse.instance(Const.SF_ORDE_CODE_1002, Const.SUCCESS);
    }

    @RequestMapping("/passengers/refresh/{orderId}")
    public String refreshPassengersTab(@PathVariable String orderId, Model model){

        try{
            OrderDetailWo orderDetail =  orderServiceImpl.selectConfirmOrderById(orderId);
            model.addAttribute("passengers", orderDetail.getPassengers());

        }catch(Exception e){
            logger.error("failed to get order Passengers by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        model.addAttribute("readOnly", false);
        return "order/back/freetravel/travelConfirm/travellerTab";
    }

    private boolean  isExistNationalName(String nationalName) throws Exception {
        boolean isExist = false;
        List<String> result = autoComplete.countryAutoComplete(nationalName, 5);
        @SuppressWarnings("rawtypes")
		Map nationalMap = Maps.newHashMap();
        for(int i = 0; i < result.size(); i++){
            nationalMap =  JSON.parseObject(result.get(i));
            String searchName=(String) nationalMap.get("nameCn");
            if(StringUtils.isNotBlank(searchName)){
                if(searchName.equals(nationalName)){
                    isExist = true;
                    break;
                }
            }
        }
        return isExist;
    }



    @RequestMapping("/selectOrderFlight")
    @ResponseBody
    public FlightWo selectOrderFlight(String orderId) {

        FlightWo flightWo = new FlightWo();

        try{
            OrderDetailWo orderDetail = orderServiceImpl.selectConfirmOrderById(orderId);
            flightWo.setGo(orderDetail.getProduct().getGoFlightList());
            if (orderDetail.getProduct().getMidlFlightList() != null) {

                Map<String, List<ProductFlightInfo>> midlFlightMap = new HashMap<String, List<ProductFlightInfo>>();
                for (ProductFlightInfo productFlightInfo : orderDetail.getProduct().getMidlFlightList()) {

                    String index = String.valueOf(Integer.parseInt(productFlightInfo.getAirRangeIndex()) / 1000 + 1) ;
                    if(midlFlightMap.get(index) != null){
                        midlFlightMap.get(index).add(productFlightInfo) ;
                    }else{
                        List<ProductFlightInfo> tmp = new ArrayList<ProductFlightInfo>() ;
                        tmp.add(productFlightInfo) ;
                        midlFlightMap.put(index, tmp) ;
                    }
                }

                flightWo.setMiddle(midlFlightMap);
            }

            flightWo.setBack(orderDetail.getProduct().getBackFlightList());

        }catch(Exception e){
            logger.error("failed to select OrderFlight by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        return flightWo;
    }

    @RequestMapping(value = "/saveOrderFlight", method = RequestMethod.POST)
    @ResponseBody
    public FlightWo saveOrderFlight(@RequestBody FlightSaveWo flightSaveWo) throws Exception {

        FlightWo flightWo = new FlightWo();
        if (flightSaveWo == null || flightSaveWo.getOrderId() == null) {
            return flightWo;
        }

        String orderId = flightSaveWo.getOrderId();

        try {
            OrderDetailWo orderDetail = orderServiceImpl.selectConfirmOrderById(orderId);
            if (flightSaveWo.getGos() != null || flightSaveWo.getBacks() != null) {
                orderDetail.getProduct().setGoFlightList(flightSaveWo.getGos());
                orderDetail.getProduct().setMidlFlightList(flightSaveWo.getMiddles());
                orderDetail.getProduct().setBackFlightList(flightSaveWo.getBacks());
            }

            Boolean flag = orderServiceImpl.updateTempOrder(orderDetail, orderId);
            if (flag) {
                flightWo = selectOrderFlight(orderId);
            }
        } catch(Exception e) {
            logger.error("failed to save OrderFlight by flightWo", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        return flightWo;
    }


    @RequestMapping("/selectOrderHotel")
    @ResponseBody
    public List<HotelWo> selectOrderHotel(String orderId) {

        List<HotelWo> hotelWos = new ArrayList<HotelWo>();

        try{
            OrderDetailWo orderDetail = orderServiceImpl.selectConfirmOrderById(orderId);
            List<ProductHotelInfo> hotels = orderDetail.getProduct().getHotelList();
            DateTime minDate = null;
            for(ProductHotelInfo hotelInfo : hotels) {
                DateTime dateTime = DateTimeUtil.convertStringToDate(hotelInfo.getCheckInDate());
                if (minDate == null || dateTime.isBefore(minDate)) {
                    minDate = dateTime;
                }
            }
            //Integer day = 0;
            for (ProductHotelInfo hotel : hotels) {
                HotelWo hotelWo = new HotelWo();
                hotelWo.setCheckInDate(hotel.getCheckInDate());
                hotelWo.setCheckOutDate(hotel.getCheckOutDate());
                hotelWo.setHotelName(hotel.getHotelName());
                hotelWo.setHotelId(hotel.getHotelName());
                hotelWo.setHotelType(hotel.getHotelType());
                hotelWo.setRoomType(hotel.getRoomType());
                hotelWo.setTripNights(DateTimeUtil.diffInDay(DateTimeUtil.convertStringToDate(hotel.getCheckOutDate()),
                        DateTimeUtil.convertStringToDate(hotel.getCheckInDate())));
                Integer night = hotelWo.getTripNights();
                if (night > 0) {
                    DateTime checkInDate = DateTimeUtil.convertStringToDate(hotelWo.getCheckInDate());
                    int diffInday = DateTimeUtil.diffInDay(checkInDate, minDate);
                    List<Integer> days = new ArrayList<Integer>();
                    for (int i = 0; i < night; i++) {
                        days.add(diffInday + i + 1);
                    }
                    hotelWo.setCheckinDays(days);
                    hotelWo.setCheckinDaysStr(days == null ? "" : days.toString().replace("[", "").replace("]", ""));
                }
                hotelWos.add(hotelWo);
            }

        }catch(Exception e){
            logger.error("failed to select OrderHotel by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        return hotelWos;
    }

    @RequestMapping(value = "/saveOrderHotel", method = RequestMethod.POST)
    @ResponseBody
    public List<HotelWo> saveOrderHotel(@RequestBody HotelSaveWo hotelSaveWo) throws Exception {

        List<HotelWo> hotelWos = new ArrayList<HotelWo>();
        if (hotelSaveWo == null || hotelSaveWo.getOrderId() == null) {
            return hotelWos;
        }

        String orderId = hotelSaveWo.getOrderId();

        try {
            OrderDetailWo orderDetail = orderServiceImpl.selectConfirmOrderById(orderId);
            List<ProductHotelInfo> hotelInfoList = orderDetail.getProduct().getHotelList();
            DateTime minDateTime = null;
            for(ProductHotelInfo hotelInfo : hotelInfoList) {
                DateTime dateTime = DateTimeUtil.convertStringToDate(hotelInfo.getCheckInDate());
                if (minDateTime == null || dateTime.isBefore(minDateTime)) {
                    minDateTime = dateTime;
                }
            }
            if (hotelSaveWo.getHotels() != null) {
                List<ProductHotelInfo> hotels = new ArrayList<ProductHotelInfo>();
                for (HotelWo hotelWo : hotelSaveWo.getHotels()) {
                    ProductHotelInfo hotel = new ProductHotelInfo();
                    hotel.setHotelName(hotelWo.getHotelName());
                    hotel.setHotelType(hotelWo.getHotelType());
                    hotel.setRoomType(hotelWo.getRoomType());

                    List<Integer> days = hotelWo.getCheckinDays();
                    String checkInDate = DateTimeUtil.date10(minDateTime.plusDays(days.get(0) - 1));
                    String checkOutDate = DateTimeUtil.date10(minDateTime.plusDays(days.get(days.size() - 1)));
                    hotel.setCheckInDate(checkInDate);
                    hotel.setCheckOutDate(checkOutDate);
                    hotel.setTripNights(days.size());
                    hotels.add(hotel);
                }
                orderDetail.getProduct().setHotelList(hotels);
            }

            Boolean flag = orderServiceImpl.updateTempOrder(orderDetail, orderId);
            if (flag) {
                hotelWos = selectOrderHotel(orderId);
            }
        } catch(Exception e) {
            logger.error("failed to save OrderFlight by flightWo", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        return hotelWos;
    }



    @RequestMapping(value = "/updateCostDescription", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse updateCostDescription(@RequestBody ConfirmOrderWo confirmOrderWo, Model model) throws Exception {

        OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(confirmOrderWo.getOrderId());
        if (orderDetailVO == null ||orderDetailVO.getStatus() == null || !ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
            return AjaxResponse.instance("FAILURE","订单不存在或订单不是已支付状态");
        }

        try{
            logger.info("updateCostDescription args -- confirmOrderWo:::[{}]", TZBeanUtils.describe(confirmOrderWo));

            if (confirmOrderWo == null || confirmOrderWo.getAdditionalInfos() == null
                    || confirmOrderWo.getAdditionalInfos().get(AdditionalRule.FEE_INCLUDE.name()) == null
                    ||confirmOrderWo.getAdditionalInfos().get(AdditionalRule.FEE_NOT_INCLUDE.name()) == null
                    || confirmOrderWo.getAdditionalInfos().get(AdditionalRule.GIFT_ITEMS.name()) == null
                    || confirmOrderWo.getAdditionalInfos().get(AdditionalRule.REFUND_POLICY.name()) == null) {
                return AjaxResponse.instance(Const.FF_ORDE_CODE_1019, Const.FF_ORDE_REASON_1019);
            }

            String orderId = confirmOrderWo.getOrderId();
            Map<String, String> additionalInfos = confirmOrderWo.getAdditionalInfos();

            OrderDetailWo orderDetailWo = orderServiceImpl.selectConfirmOrderById(orderId);
            if (orderDetailWo != null) {
                OrderProductWo orderProductWo = orderDetailWo.getProduct();
                orderProductWo.setAdditionalInfos(additionalInfos);
                Boolean flag = orderServiceImpl.updateTempOrder(orderDetailWo, orderId);
                if (!flag) {
                    return AjaxResponse.instance(Const.FF_ORDE_CODE_1011, Const.FF_ORDE_REASON_1011);
                }
            }
        }catch(SQLException ex){
            logger.error("failed to update costDescription from DB", ex);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1011, Const.FF_ORDE_REASON_1011);
        }catch(Exception e){
            logger.error("failed to update costDescription", e);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1012, Const.FF_ORDE_REASON_1012);
        }

        return AjaxResponse.instance(Const.SF_ORDE_CODE_1002, Const.SUCCESS);
    }

    @RequestMapping("/costDescription/refresh/{orderId}")
    public String refreshCostDescriptionTab(@PathVariable String orderId, Model model){

        try{
            OrderDetailWo orderDetail =  orderServiceImpl.selectConfirmOrderById(orderId);
            model.addAttribute("additionalInfos", orderDetail.getProduct().getAdditionalInfos());

        }catch(Exception e){
            logger.error("failed to get costDescription by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        model.addAttribute("readOnly", false);
        return "order/back/freetravel/travelConfirm/costDescriptionTab";
    }

    @RequestMapping(value = "/updateFeesDetail", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse updateFeesDetail(@RequestBody ConfirmOrderWo confirmOrderWo, Model model) throws Exception {

        OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(confirmOrderWo.getOrderId());
        if (orderDetailVO == null ||orderDetailVO.getStatus() == null || !ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
            return AjaxResponse.instance("FAILURE","订单不存在或订单不是已支付状态");
        }

        try{
            logger.info("updateFeesDetail args -- confirmOrderWo:::[{}]", TZBeanUtils.describe(confirmOrderWo));

            if (confirmOrderWo == null || confirmOrderWo.getPrice() == null
                    || (confirmOrderWo.getPrice().getAdultPrice() == null
                    && confirmOrderWo.getPrice().getPackageId() == null)) {
                return AjaxResponse.instance(Const.FF_ORDE_CODE_1019, Const.FF_ORDE_REASON_1019);
            }

            String orderId = confirmOrderWo.getOrderId();
            BookPriceWo price = confirmOrderWo.getPrice();

            OrderDetailWo orderDetailWo = orderServiceImpl.selectConfirmOrderById(orderId);
            if (orderDetailWo != null) {

                OrderWo order = orderDetailWo.getOrder();
                BookPriceWo origPrice = orderDetailWo.getPrice();
                OrderProductWo product = orderDetailWo.getProduct();
                ProductBookVo productSnapshot = JSON.parseObject(product.getProductSnapshot(), ProductBookVo.class);
                Long totalPrice = 0l;

                if (!StringUtil.isBlank(price.getPackageId()) && !StringUtil.isBlank(price.getTotalPackagePrice())) {
                    totalPrice += PriceUtil.yuan2cent(price.getTotalPackagePrice());
                    origPrice.setTotalPackagePrice(price.getTotalPackagePrice());

                    productSnapshot.setPackagePrice(price.getTotalPackagePrice());
                    productSnapshot.setTotalPackagePrice(price.getTotalPackagePrice());

                } else if (!StringUtil.isBlank(price.getTotalAdultPrice())){
                    totalPrice += PriceUtil.yuan2cent(price.getTotalAdultPrice());
                    origPrice.setAdultPrice(price.getAdultPrice());
                    origPrice.setTotalAdultPrice(price.getTotalAdultPrice());

                    productSnapshot.setAdultPrice(price.getAdultPrice());
                    productSnapshot.setTotalAdultPrice(price.getTotalAdultPrice());

                    if (!StringUtil.isBlank(price.getTotalChildPrice())) {
                        totalPrice += PriceUtil.yuan2cent(price.getTotalChildPrice());
                        origPrice.setChildrenPrice(price.getChildrenPrice());
                        origPrice.setTotalChildPrice(price.getTotalChildPrice());

                        productSnapshot.setChildrenPrice(price.getChildrenPrice());
                        productSnapshot.setTotalChildPrice(price.getTotalChildPrice());
                    }
                    if (!StringUtil.isBlank(price.getTotalSingleDiff())) {
                        totalPrice += PriceUtil.yuan2cent(price.getTotalSingleDiff());
                        origPrice.setSingleRoomDiff(price.getSingleRoomDiff());
                        origPrice.setTotalSingleDiff(price.getTotalSingleDiff());

                        productSnapshot.setSingleRoomDiff(price.getSingleRoomDiff());
                        productSnapshot.setTotalSingleDiff(price.getTotalSingleDiff());
                    }
                }

                product.setProductSnapshot(JSONObject.toJSONString(productSnapshot));

                List<AdditionalProductWo> additionalProducts = orderDetailWo.getAdditionalProducts();
                if (additionalProducts != null) {
                    for (AdditionalProductWo additionalProduct : additionalProducts) {
                        totalPrice += PriceUtil.yuan2cent(additionalProduct.getTotalPrice());
                    }
                }
                order.setTotalPrice(PriceUtil.cent2yuan(totalPrice));
                Long payAmount = totalPrice;
                if (!StringUtil.isBlank(order.getDiscountTotalSub())) {
                    payAmount = payAmount - PriceUtil.yuan2cent(order.getDiscountTotalSub());
                }
                order.setPayAmount(PriceUtil.cent2yuan(payAmount));
                Boolean flag = orderServiceImpl.updateTempOrder(orderDetailWo, orderId);
                if (!flag) {
                    return AjaxResponse.instance(Const.FF_ORDE_CODE_1011, Const.FF_ORDE_REASON_1011);
                }
            }
        }catch(SQLException ex){
            logger.error("failed to update feesDetail from DB", ex);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1011, Const.FF_ORDE_REASON_1011);
        }catch(Exception e){
            logger.error("failed to update feesDetail", e);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1012, Const.FF_ORDE_REASON_1012);
        }

        return AjaxResponse.instance(Const.SF_ORDE_CODE_1002, Const.SUCCESS);
    }

    @RequestMapping("/feesDetail/refresh/{orderId}")
    public String refreshFeesDetailTab(@PathVariable String orderId, Model model){

        try{
            OrderDetailWo orderDetail =  orderServiceImpl.selectConfirmOrderById(orderId);
            model.addAttribute("orderDetail", orderDetail);
            OrderDetailWo originalOrderDetail = orderServiceImpl.selectOriginalOrderById(orderId);
            if (originalOrderDetail != null) {
                String originalPayAmount = originalOrderDetail.getOrder().getPayAmount();
                model.addAttribute("originalPayAmount", originalPayAmount);
            }

        }catch(Exception e){
            logger.error("failed to get order feesDetail by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        model.addAttribute("readOnly", false);
        return "order/back/freetravel/travelConfirm/feesDetailTab";
    }

    @RequestMapping(value = "/updateAdditionalProduct", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse updateAdditionalProduct(@RequestBody ConfirmOrderWo confirmOrderWo, Model model) throws Exception {

        OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(confirmOrderWo.getOrderId());
        if (orderDetailVO == null ||orderDetailVO.getStatus() == null || !ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
            return AjaxResponse.instance("FAILURE","订单不存在或订单不是已支付状态");
        }

        try{
            logger.info("updateAdditionalProduct args -- confirmOrderWo:::[{}]", TZBeanUtils.describe(confirmOrderWo));

            if (confirmOrderWo == null || confirmOrderWo.getAdditionalProducts() == null) {
                return AjaxResponse.instance(Const.FF_ORDE_CODE_1019, Const.FF_ORDE_REASON_1019);
            }

            String orderId = confirmOrderWo.getOrderId();
            List<AdditionalProductWo> additionalProducts = confirmOrderWo.getAdditionalProducts();

            OrderDetailWo orderDetailWo = orderServiceImpl.selectConfirmOrderById(orderId);
            if (orderDetailWo != null) {

                orderDetailWo.setAdditionalProducts(additionalProducts);

                Long totalPrice = 0l;
                BookPriceWo price = orderDetailWo.getPrice();
                if (!StringUtil.isBlank(price.getPackageId()) && !StringUtil.isBlank(price.getTotalPackagePrice())) {
                    totalPrice += PriceUtil.yuan2cent(price.getTotalPackagePrice());

                } else if (!StringUtil.isBlank(price.getTotalAdultPrice())){
                    totalPrice += PriceUtil.yuan2cent(price.getTotalAdultPrice());

                    if (!StringUtil.isBlank(price.getTotalChildPrice())) {
                        totalPrice += PriceUtil.yuan2cent(price.getTotalChildPrice());
                    }
                    if (!StringUtil.isBlank(price.getTotalSingleDiff())) {
                        totalPrice += PriceUtil.yuan2cent(price.getTotalSingleDiff());
                    }
                }

                for (AdditionalProductWo additionalProduct : additionalProducts) {
                    totalPrice += PriceUtil.yuan2cent(additionalProduct.getTotalPrice());
                }

                OrderWo order = orderDetailWo.getOrder();
                order.setTotalPrice(PriceUtil.cent2yuan(totalPrice));
                Long payAmount = totalPrice;
                if (!StringUtil.isBlank(order.getDiscountTotalSub())) {
                    payAmount = payAmount - PriceUtil.yuan2cent(order.getDiscountTotalSub());
                }
                order.setPayAmount(PriceUtil.cent2yuan(payAmount));

                Boolean flag = orderServiceImpl.updateTempOrder(orderDetailWo, orderId);
                if (!flag) {
                    return AjaxResponse.instance(Const.FF_ORDE_CODE_1011, Const.FF_ORDE_REASON_1011);
                }
            }
        }catch(SQLException ex){
            logger.error("failed to update additionalProduct from DB", ex);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1011, Const.FF_ORDE_REASON_1011);
        }catch(Exception e){
            logger.error("failed to update additionalProduct", e);
            return AjaxResponse.instance(Const.FF_ORDE_CODE_1012, Const.FF_ORDE_REASON_1012);
        }

        return AjaxResponse.instance(Const.SF_ORDE_CODE_1002, Const.SUCCESS);
    }

    @RequestMapping("/additionalProduct/refresh/{orderId}")
    public String refreshAdditionalProductTab(@PathVariable String orderId, Model model){

        try{
            OrderDetailWo orderDetail =  orderServiceImpl.selectConfirmOrderById(orderId);
            model.addAttribute("orderDetail", orderDetail);

            OrderDetailWo originalOrderDetail = orderServiceImpl.selectOriginalOrderById(orderId);
            if (originalOrderDetail != null) {
                String originalPayAmount = originalOrderDetail.getOrder().getPayAmount();
                model.addAttribute("originalPayAmount", originalPayAmount);
            }
        }catch(Exception e){
            logger.error("failed to get order additionalProduct by orderId", e);
            throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
        }

        model.addAttribute("readOnly", false);
        return "order/back/freetravel/travelConfirm/additionalTab";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse confirm(@RequestBody String orderId, Model model) throws Exception {

        OrderDetailVO orderDetailVO = orderServiceImpl.getOrderDetailByOrderId(orderId);
        if (orderDetailVO == null ||orderDetailVO.getStatus() == null || !ZtOrderStatus.PAYED.getDesc().equals(orderDetailVO.getStatus())) {
            return AjaxResponse.instance("FAILURE","订单不存在或订单不是已支付状态");
        }

        logger.info("confirm args -- orderId:::[{}]", TZBeanUtils.describe(orderId));
        if(StringUtil.isBlank(orderId)) {
            return AjaxResponse.instance("FAILURE","缺少文件参数信息");
        }

        AjaxResponse ajaxResponse =  AjaxResponse.instance("FAILURE","");
        try {
            ajaxResponse = orderServiceImpl.confirm(orderId);
        } catch (Exception e) {
            logger.error("failed to confirm by orderId", e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse sendEmail(String orderId, String email) throws Exception {
    	AjaxResponse ajaxResponse =  AjaxResponse.instance("FAILURE","");
        logger.info("sendEmail args -- orderId:::[{}],email:::[{}]", orderId, email);
        if(StringUtil.isBlank(orderId)) {
            return AjaxResponse.instance("FAILURE","缺少文件参数信息");
        }
    	if(StringUtils.isBlank(email) || !Pattern.matches("^((\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+){0,50}$", email)){
    		return AjaxResponse.instance("FAILURE", "邮箱地址不合法");
    	}
        try {
            ajaxResponse = orderServiceImpl.sendEmailByTravelConfirm(orderId, email);
        } catch (Exception e) {
            logger.error("failed to sendEmail by orderId", e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value="/countryAutoComplete", method = RequestMethod.POST)
    @ResponseBody
    public String[] countryAutoComplete(String query){
         try {
                List<String> result = autoComplete.countryAutoComplete(query, 5);
                @SuppressWarnings("rawtypes")
				Map map = new HashMap<String,String>();
                int size = result.size();
                String[] countryArr =new String[size];
                for(int i=0;i<result.size();i++){
                    map =  JSON.parseObject(result.get(i));
                    countryArr[i]=(String) map.get("nameCn");
                }
                return countryArr;
            }catch (Exception e) {
                logger.error("Failed to country autoComplete", e);
            }
        return null;
    }

}