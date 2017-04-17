package com.ztravel.product.back.freetravel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.operator.client.finance.service.ISupplierClientService;
import com.ztravel.operator.financeMantain.po.Supplier;
import com.ztravel.product.back.freetravel.dao.CostManageDao;
import com.ztravel.product.back.freetravel.entity.Cost;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Flight;
import com.ztravel.product.back.freetravel.entity.FlightInfo;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.service.CostManageService;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.utils.CommonUtils;
import com.ztravel.product.back.freetravel.utils.Converter;
import com.ztravel.product.back.freetravel.utils.Sorter;
import com.ztravel.product.back.freetravel.vo.CombinationCostPageVo;
import com.ztravel.product.back.freetravel.vo.CostHotelMonthData;
import com.ztravel.product.back.freetravel.vo.CostHotelVo;
import com.ztravel.product.back.freetravel.vo.CostMonthData;
import com.ztravel.product.back.freetravel.vo.CostPriceBean;
import com.ztravel.product.back.freetravel.vo.CostSupplierVo;
import com.ztravel.product.back.freetravel.vo.FlightSaveReqBean;
import com.ztravel.product.back.freetravel.vo.FlightSaveRespBean;
import com.ztravel.product.back.freetravel.vo.HotelSaveReqBean;
import com.ztravel.product.back.freetravel.vo.HotelSaveRespBean;
import com.ztravel.product.back.freetravel.vo.PackageCostPageVo;
import com.ztravel.product.back.freetravel.vo.SupplierCostAdditionSaveBean;
import com.ztravel.product.back.freetravel.vo.SupplierSaveBean;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.service.IHotelService;

/**
 * @author wanhaofan
 * 低价维护
 * */
@Service
public class CostManageServiceImpl implements CostManageService{
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(CostManageServiceImpl.class);

	@Resource
	private ProductService productService ;

	@Resource
	private CostManageDao costManageDao ;

	@Resource
	private IHotelService hotelService;
	
	@Resource
	private ISupplierClientService supplierClientService ;

	/**
	 * 打包成本信息
	 * */
	@Override
	public PackageCostPageVo initPackageView(String id) {
		Product product = productService.getProductById(id) ;

		PackageCostPageVo vo = new PackageCostPageVo() ;

		vo.setFrom(product.getFrom());

		vo.setTripNights(product.getTripNights());

		vo.setIsContainFlight(product.isContainFlight()) ;
		
		vo.setIsContainShuttle(product.isContainShuttle()) ;
		
		vo.setIsContainZenbook(product.isContainZenbook()) ;
		
		vo.setIsContainWifi(product.isContainWifi()) ;
		
		vo.setIsContainOther(product.isContainOther()) ;
		
		if(product.getFlight() != null && !CollectionUtils.isEmpty(product.getFlight().getInfos())){
			vo.setIsFlightAlreadyHave(true);
		}else{
			vo.setIsFlightAlreadyHave(false);
		}

		vo.setIsContainHotel(product.isContainHotel()) ;

		if(!CollectionUtils.isEmpty(product.getHotels())){
			vo.setIsHotelAlreadyHave(true);
		}else{
			vo.setIsHotelAlreadyHave(false);
		}

		List<Supplier> allSuppliers = supplierClientService.getSuppliers(Maps.newHashMap()) ;
		List<CostSupplierVo> svos = new ArrayList<CostSupplierVo>() ;
		for(Supplier supplier : allSuppliers){
			svos.add(Converter.convertSupplier2Vo(supplier)) ;
		}
		vo.setAllSuppliers(svos);
		CostSupplierVo pkgSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getPackageSupplier())) ;
		vo.setPackageSupplier(pkgSupplier);
		
		if(vo.getIsContainShuttle()){
			CostSupplierVo shuttleSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getShuttleSupplier())) ;
			shuttleSupplier.setCost(product.getShuttleCost());
			vo.setShuttleSupplier(shuttleSupplier);
		}
		
		if(vo.getIsContainZenbook()){
			CostSupplierVo zenbookSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getZenbookSupplier())) ;
			zenbookSupplier.setCost(product.getZenbookCost());
			vo.setZenbookSupplier(zenbookSupplier);
		}
		
		if(vo.getIsContainWifi()){
			CostSupplierVo wifiSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getWifiSupplier())) ;
			wifiSupplier.setCost(product.getWifiCost());
			vo.setWifiSupplier(wifiSupplier);
		}
		
		if(vo.getIsContainOther()){
			CostSupplierVo otherSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getOtherSupplier())) ;
			otherSupplier.setCost(product.getOtherCost());
			vo.setOtherSupplier(otherSupplier);
		}
		
		Sorter sorter = new Sorter() ;

		/**
		 * 初始化航程信息 start
		 * */
		Flight flight = product.getFlight() ;
		if(flight != null){
			vo.setFlight(Converter.convertFlight2Vo(flight, product.getFrom(), sorter));
		}
		/**
		 * 初始化航程信息 end
		 * */

		/**
		 * 初始化酒店信息 start
		 * */
		List<Hotel> hotels = product.getHotels() ;
		if(hotels != null){
			List<CostHotelVo> hotelVos = Converter.convertHotel2Vo(hotels, sorter) ;
			for(CostHotelVo hotelVo : hotelVos){
				HotelEntity hotelEntity = hotelService.getHotelById(hotelVo.getId()) ;
				if(hotelEntity != null){
					hotelVo.setRate(hotelEntity.getRating() == null ? "" : hotelEntity.getRating().getDesc());
					hotelVo.setDest(hotelEntity.getCity());
					hotelVo.setName(hotelEntity.getHotelNameCn());
				}
			}
			vo.setHotels(hotelVos);
		}
		/**
		 * 初始化酒店信息 end
		 * */

		/**
		 * 初始化打包成本 start
		 * */
		Map<String, Day> calendar = product.getCalendar() ;
		if(calendar != null){
			List<CostMonthData> costs = Converter.convertCalendar2Cost(calendar) ;
			if(!CollectionUtils.isEmpty(costs)){
				vo.setCosts(JSONObject.toJSONString(costs));
			}
		}
		/**
		 * 初始化打包成本 end
		 * */

		return vo;
	}

	/**
	 * 打包成本信息
	 * */
	@Override
	public CombinationCostPageVo initCombinationView(String id) {
		Product product = productService.getProductById(id) ;

		CombinationCostPageVo vo = new CombinationCostPageVo() ;

		vo.setFrom(product.getFrom());

		vo.setTripNights(product.getTripNights());

		vo.setIsContainFlight(product.isContainFlight()) ;

		if(product.getFlight() != null && !CollectionUtils.isEmpty(product.getFlight().getInfos())){
			vo.setIsFlightAlreadyHave(true);
		}else{
			vo.setIsFlightAlreadyHave(false);
		}

		vo.setIsContainHotel(product.isContainHotel()) ;

		if(!CollectionUtils.isEmpty(product.getHotels())){
			vo.setIsHotelAlreadyHave(true);
		}else{
			vo.setIsHotelAlreadyHave(false);
		}
		vo.setIsContainFlight(product.isContainFlight()) ;
		
		vo.setIsContainShuttle(product.isContainShuttle()) ;
		
		vo.setIsContainZenbook(product.isContainZenbook()) ;
		
		vo.setIsContainWifi(product.isContainWifi()) ;
		
		vo.setIsContainOther(product.isContainOther()) ;
		
		List<Supplier> allSuppliers = supplierClientService.getSuppliers(Maps.newHashMap()) ;
		List<CostSupplierVo> svos = new ArrayList<CostSupplierVo>() ;
		for(Supplier supplier : allSuppliers){
			svos.add(Converter.convertSupplier2Vo(supplier)) ;
		}
		vo.setAllSuppliers(svos);
		CostSupplierVo pkgSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getPackageSupplier())) ;
		vo.setPackageSupplier(pkgSupplier);
		
		if(vo.getIsContainShuttle()){
			CostSupplierVo shuttleSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getShuttleSupplier())) ;
			shuttleSupplier.setCost(product.getShuttleCost());
			vo.setShuttleSupplier(shuttleSupplier);
		}
		
		if(vo.getIsContainZenbook()){
			CostSupplierVo zenbookSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getZenbookSupplier())) ;
			zenbookSupplier.setCost(product.getZenbookCost());
			vo.setZenbookSupplier(zenbookSupplier);
		}
		
		if(vo.getIsContainWifi()){
			CostSupplierVo wifiSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getWifiSupplier())) ;
			wifiSupplier.setCost(product.getWifiCost());
			vo.setWifiSupplier(wifiSupplier);
		}
		
		if(vo.getIsContainOther()){
			CostSupplierVo otherSupplier = Converter.convertSupplier2Vo(supplierClientService.getSupplierById(product.getOtherSupplier())) ;
			otherSupplier.setCost(product.getOtherCost());
			vo.setOtherSupplier(otherSupplier);
		}
		Sorter sorter = new Sorter() ;

		/**
		 * 初始化航程信息 start
		 * */
		Flight flight = product.getFlight() ;
		if(flight != null){
			vo.setFlight(Converter.convertFlight2Vo(flight, product.getFrom(), sorter));
		}
		/**
		 * 初始化航程信息 end
		 * */

		/**
		 * 初始化酒店信息 start
		 * */
		List<Hotel> hotels = product.getHotels() ;
		if(hotels != null){
			List<CostHotelVo> hotelVos = Converter.convertHotel2Vo(hotels, sorter) ;
			for(CostHotelVo hotelVo : hotelVos){
				HotelEntity hotelEntity = hotelService.getHotelById(hotelVo.getId()) ;
				if(hotelEntity != null){
					hotelVo.setRate(hotelEntity.getRating() == null ? "" : hotelEntity.getRating().getDesc());
					hotelVo.setDest(hotelEntity.getCity());
					hotelVo.setName(hotelEntity.getHotelNameCn());
				}
			}
			vo.setHotels(hotelVos);
		}
		/**
		 * 初始化酒店信息 end
		 * */

		/**
		 * 初始化酒店成本 start
		 * */
		Map<String, Day> calendar = product.getCalendar() ;
		if(calendar != null){
			List<CostHotelMonthData> costs = Converter.convertCalendar2HotelCost(calendar) ;
			if(!CollectionUtils.isEmpty(costs)){
				vo.setHotelCosts(JSONObject.toJSONString(costs));
			}
		}
		/**
		 * 初始化酒店成本 end
		 * */

		/**
		 * 初始化机票成本 start
		 * */
		if(calendar != null){
			List<CostMonthData> costs = Converter.convertCalendar2FlightCost(calendar) ;
			if(!CollectionUtils.isEmpty(costs)){
				vo.setFlightCosts(JSONObject.toJSONString(costs));
			}
		}
		/**
		 * 初始化机票成本 end
		 * */

		/**
		 * 初始化机票供应商 start
		 * */
		String flightSupplier = product.getFlightSupplier() ;
		if(flightSupplier != null){
			vo.setFlightSupplier(Converter.convertSupplier2Vo(supplierClientService.getSupplierById(flightSupplier)));
		}
		/**
		 * 初始化机票供应商 end
		 * */

		return vo;
	}

	/**
	 * 机票保存
	 * */
	@Override
	public FlightSaveRespBean saveFlight(FlightSaveReqBean bean) {
		List<FlightInfo> gos = bean.getGos() == null ? new ArrayList<FlightInfo>() : bean.getGos() ;
		List<FlightInfo> middles = bean.getMiddles() == null ? new ArrayList<FlightInfo>() : bean.getMiddles() ;
		List<FlightInfo> backs = bean.getBacks() == null ? new ArrayList<FlightInfo>() : bean.getBacks() ;
		gos.addAll(middles) ;
		gos.addAll(backs) ;
		Flight flight = new Flight();
		flight.setAirRangeRemark(bean.getAirRangeRemark());
		flight.setInfos(gos);
		flight.setInnerRemark(bean.getInnerRemark());
		FlightSaveRespBean resp = new FlightSaveRespBean() ;
		String from = productService.getProductById(bean.getId()).getFrom() ;
		if(costManageDao.saveFlight(bean.getId(), flight)){
			Sorter sorter = new Sorter() ;
			resp.setFlag(true);
			resp.setFlight(Converter.convertFlight2Vo(flight, from, sorter));
		}else{
			resp.setFlag(false);
		}
		return resp ;
	}

	/**
	 * 取出机票信息
	 * */
	@Override
	public FlightSaveRespBean getFlight(String id) {
		FlightSaveRespBean bean = new FlightSaveRespBean() ;
		Flight flight = null ;
		try {
			Product product = productService.getProductById(id) ;
			flight = product.getFlight() ;
			bean.setFlag(true);
			Sorter sorter = new Sorter() ;
			bean.setFlight(Converter.convertFlight2Vo(flight, product.getFrom(), sorter)) ;
			bean.getFlight().setFrom(product.getFrom());
		} catch (Exception e) {
			bean.setFlag(false);
		}
		return bean;
	}

	/**
	 * 删除机票信息
	 * */
	@Override
	public Boolean deleteFlight(String id) {
		return costManageDao.deleteFlight(id) ;
	}

	/**
	 * 酒店保存
	 * */
	@Override
	public HotelSaveRespBean saveHotel(HotelSaveReqBean bean) {
		HotelSaveRespBean resp = new HotelSaveRespBean() ;
		resp.setFlag(costManageDao.saveHotel(bean.getId(), bean.getHotels()));
		Sorter sorter = new Sorter() ;
		List<CostHotelVo> hotelVos = Converter.convertHotel2Vo(bean.getHotels(), sorter) ;
		for(CostHotelVo hotelVo : hotelVos){
			HotelEntity hotelEntity = hotelService.getHotelById(hotelVo.getId()) ;
			if(hotelEntity != null){
				hotelVo.setRate(hotelEntity.getRating() == null ? "" : hotelEntity.getRating().getDesc());
				hotelVo.setDest(hotelEntity.getCity());
				hotelVo.setName(hotelEntity.getHotelNameCn());
			}
		}
		resp.setHotels(hotelVos);
		return resp ;
	}

	@Override
	public HotelSaveRespBean getHotel(String id) {
		HotelSaveRespBean bean = new HotelSaveRespBean() ;
		List<Hotel> hotels = null ;
		try {
			Product product = productService.getProductById(id) ;
			hotels = product.getHotels() ;
			for(Hotel hotel : product.getHotels()){
				if(hotel.getSupplier() != null && !hotel.getSupplier().equals("")){
					
				}
			}
			bean.setFlag(true);
			Sorter sorter = new Sorter() ;
			bean.setHotels(Converter.convertHotel2Vo(hotels, sorter)) ;
			for(CostHotelVo hotelVo : bean.getHotels()){
				HotelEntity hotelEntity = hotelService.getHotelById(hotelVo.getId()) ;
				if(hotelEntity != null){
					hotelVo.setRate(hotelEntity.getRating() == null ? "" : hotelEntity.getRating().getDesc());
					hotelVo.setDest(hotelEntity.getCity());
					hotelVo.setName(hotelEntity.getHotelNameCn());
				}
				String tmp = hotelVo.getSupplier().getSupplierId() == null ? "" : String.valueOf(hotelVo.getSupplier().getSupplierId()) ;
				Supplier supplier = supplierClientService.getSupplierById(tmp) ;
				hotelVo.getSupplier().setSupplierName((supplier.getSupplierName())) ;
			}
		} catch (Exception e) {
			bean.setFlag(false);
		}
		return bean;
	}

	@Override
	public String removeCost(CostPriceBean bean) {
		List<DateTime> days = CommonUtils.convertAvaiDays(bean.getStart(), bean.getEnd(), bean.getWeekDays()) ;
		Map<String, Cost> costs = new HashMap<String, Cost>() ;
		for(DateTime day:days){
			Cost cost = new Cost();
			cost.setPackageAdultCost(bean.getAdultPrice());
			cost.setPackageChildCost(bean.getChildPrice());
			costs.put(day.toString("yyyy-MM-dd"), cost) ;
		}
		if(costManageDao.removeCost(bean.getId(), costs)){
			Product product = productService.getProductById(bean.getId()) ;
			if(product != null){
				return JSONObject.toJSONString(Converter.convertCalendar2Cost(product.getCalendar())) ;
			}else{
				return null ;
			}
		}else{
			return null ;
		}
	}

	@Override
	public String addCost(CostPriceBean bean) {
		List<DateTime> days = CommonUtils.convertAvaiDays(bean.getStart(), bean.getEnd(), bean.getWeekDays()) ;
		Map<String, Cost> costs = new HashMap<String, Cost>() ;
		for(DateTime day:days){
			Cost cost = new Cost();
			cost.setPackageAdultCost(bean.getAdultPrice());
			cost.setPackageChildCost(bean.getChildPrice());
			costs.put(day.toString("yyyy-MM-dd"), cost) ;
		}
		try{
			if(costManageDao.addCost(bean.getId(), costs)){
				Product product = productService.getProductById(bean.getId()) ;
				if(product != null){
					return JSONObject.toJSONString(Converter.convertCalendar2Cost(product.getCalendar())) ;
				}else{
					return null ;
				}
			}else{
				return null ;
			}
		}catch(Exception e){
			LOGGER.error("product:::{} add package cost fail",bean.getId(),e);
			return null ;
		}
		
	}

	@Override
	public Boolean saveCheck(SupplierSaveBean bean) {
		if(bean.getIsNextStep()){
			return costManageDao.saveCheck(bean.getId(), bean.getSupplier()) && productService.updateProgress(bean.getId(), 2) ;
		}else{
			return costManageDao.saveCheck(bean.getId(), bean.getSupplier()) ;
		}
	}

	@Override
	public String removeHotelCost(CostPriceBean bean) {
		List<DateTime> days = CommonUtils.convertAvaiDays(bean.getStart(), bean.getEnd(), bean.getWeekDays()) ;
		Map<String, Cost> costs = new HashMap<String, Cost>() ;
		for(DateTime day:days){
			Cost cost = new Cost();
			cost.setHotelRoomCost(bean.getRoomPrice());
			costs.put(day.toString("yyyy-MM-dd"), cost) ;
		}
		if(costManageDao.removeHotelCost(bean.getId(), costs)){
			Product product = productService.getProductById(bean.getId()) ;
			if(product != null){
				return JSONObject.toJSONString(Converter.convertCalendar2HotelCost(product.getCalendar())) ;
			}else{
				return null ;
			}
		}else{
			return null ;
		}
	}

	@Override
	public String addHotelCost(CostPriceBean bean) {
		List<DateTime> days = CommonUtils.convertAvaiDays(bean.getStart(), bean.getEnd(), bean.getWeekDays()) ;
		Map<String, Cost> costs = new HashMap<String, Cost>() ;
		for(DateTime day:days){
			Cost cost = new Cost();
			cost.setHotelRoomCost(bean.getRoomPrice());
			costs.put(day.toString("yyyy-MM-dd"), cost) ;
		}
		try{
			if(costManageDao.addHotelCost(bean.getId(), costs)){
				Product product = productService.getProductById(bean.getId()) ;
				if(product != null){
					return JSONObject.toJSONString(Converter.convertCalendar2HotelCost(product.getCalendar())) ;
				}else{
					return null ;
				}
			}else{
				return null ;
			}
		}catch(Exception e){
			LOGGER.error("product:::{} add hotel cost fail",bean.getId(),e);
			return null ;
		}
	}

	@Override
	public String removeFlightCost(CostPriceBean bean) {
		List<DateTime> days = CommonUtils.convertAvaiDays(bean.getStart(), bean.getEnd(), bean.getWeekDays()) ;
		Map<String, Cost> costs = new HashMap<String, Cost>() ;
		for(DateTime day:days){
			Cost cost = new Cost();
			cost.setFlightAdultCost(bean.getAdultPrice());
			cost.setFlightChildCost(bean.getChildPrice());
			costs.put(day.toString("yyyy-MM-dd"), cost) ;
		}
		if(costManageDao.removeFlightCost(bean.getId(), costs)){
			Product product = productService.getProductById(bean.getId()) ;
			if(product != null){
				return JSONObject.toJSONString(Converter.convertCalendar2FlightCost(product.getCalendar())) ;
			}else{
				return null ;
			}
		}else{
			return null ;
		}
	}

	@Override
	public String addFlightCost(CostPriceBean bean) {
		List<DateTime> days = CommonUtils.convertAvaiDays(bean.getStart(), bean.getEnd(), bean.getWeekDays()) ;
		Map<String, Cost> costs = new HashMap<String, Cost>() ;
		for(DateTime day:days){
			Cost cost = new Cost();
			cost.setFlightAdultCost(bean.getAdultPrice());
			cost.setFlightChildCost(bean.getChildPrice());
			costs.put(day.toString("yyyy-MM-dd"), cost) ;
		}
		try{
			if(costManageDao.addFlightCost(bean.getId(), costs)){
				Product product = productService.getProductById(bean.getId()) ;
				if(product != null){
					return JSONObject.toJSONString(Converter.convertCalendar2FlightCost(product.getCalendar())) ;
				}else{
					return null ;
				}
			}else{
				return null ;
			}
		}catch(Exception e){
			LOGGER.error("product:::{} add flight cost fail",bean.getId(),e);
			return null ;
		}
	}

	@Override
	public Boolean saveCombinationCheck(SupplierSaveBean bean) {
		if(bean.getIsNextStep()){
			return costManageDao.saveCombinationCheck(bean.getId(), bean.getFlightSupplier(), bean.getHotelSupplier())
					&& productService.updateProgress(bean.getId(), 2) ;
		}else{
			return costManageDao.saveCombinationCheck(bean.getId(), bean.getFlightSupplier(), bean.getHotelSupplier()) ;
		}
	}

	@Override
	public Boolean deleteHotel(String id) {
		return costManageDao.deleteHotel(id) ;
	}
	
	@Override
	public Boolean deleteSingleHotel(String id, String hotelId) {
		return costManageDao.deleteSingleHotel(id, hotelId) ;
	}

	@Override
	public Boolean saveAdditionCostSupplier(SupplierCostAdditionSaveBean bean) {
		return costManageDao.saveAdditionCostSupplier(bean.getId(), bean.getSid(), bean.getCost(), bean.getType());
	}

}
