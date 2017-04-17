package com.ztravel.product.back.freetravel.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.product.back.freetravel.dao.PriceCalendarDao;
import com.ztravel.product.back.freetravel.entity.Cost;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.enums.SaleStatus;
import com.ztravel.product.back.freetravel.service.PriceCalendarService;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.utils.CommonUtils;
import com.ztravel.product.back.freetravel.utils.Converter;
import com.ztravel.product.back.freetravel.vo.CalendarBatchAreaBean;
import com.ztravel.product.back.freetravel.vo.CalendarBatchSaveBean;
import com.ztravel.product.back.freetravel.vo.CalendarCostData;
import com.ztravel.product.back.freetravel.vo.CalendarDayData;
import com.ztravel.product.back.freetravel.vo.CalendarMonthData;
import com.ztravel.product.back.freetravel.vo.CalendarSingleSaveBean;
import com.ztravel.product.dao.ProductDao;

/**
 * @author wanhaofan
 * */
@Service
public class PriceCalendarServiceImpl implements PriceCalendarService{

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(PriceCalendarServiceImpl.class) ;

	@Resource
	private PriceCalendarDao priceCalendarDao ;

	@Resource
	private ProductService productService ;

	@Resource
	private ProductDao productDao ;
	
	@Resource
	private IdGeneratorUtil idGeneratorUtil ;
	
	/**
	 * 取得DateTime对应Month的Calendar数据
	 * */
	@Override
	public CalendarMonthData getCalendarMonthData(String id, DateTime dateTime) {
		Integer month = dateTime.getMonthOfYear() ;
		Integer year = dateTime.getYear() ;
		CalendarMonthData monthData = new CalendarMonthData() ;
		List<CalendarDayData> dayDatas = new ArrayList<CalendarDayData>() ;
		Product product = productDao.getProductById(id) ;
		Map<String, Day> calendar = product.getCalendar() ;
		if(calendar != null && !calendar.isEmpty()){
			Iterator<Entry<String, Day>> iter = calendar.entrySet().iterator() ;
			while(iter.hasNext()){
				Entry<String, Day> entry = iter.next() ;
				DateTime current = DateTime.parse(entry.getKey()) ;
				if(current.getMonthOfYear() == month && year == current.getYear()){
					CalendarDayData dayData = new CalendarDayData() ;
					Day day = entry.getValue() ;
					if(day.getSale() != null){
						dayData.setAdultPrice(day.getSale().getAdultPrice());
						dayData.setChildPrice(day.getSale().getChildPrice());
						dayData.setSaleStatus(day.getSale().getSaleStatus());
						dayData.setSoldNum(day.getSale().getUsedStock());
						dayData.setTotalNum(day.getSale().getStock());
						dayData.setHasSale(true);
					}else{
						dayData.setHasSale(false);
					}
					dayData.setDay(current.getDayOfMonth());
					if(day.getCost() != null){
						dayData.setFlightFlag(day.getCost().isContainFlightCost(product));
						dayData.setHotelFlag(day.getCost().isContainHotelCost(product));
					}
					dayDatas.add(dayData) ;
				}
			}
		}
		monthData.setMonthData(dayDatas);
		monthData.setMonth(dateTime.toString("yyyy-MM"));
		return monthData;
	}

	/**
	 * 取得DateTime对应Date的成本数据
	 * */
	@Override
	public CalendarCostData getCalendarCostData(String id, DateTime dateTime) {
		Product product = productDao.getProductById(id) ;
		CalendarCostData calendarCostData = new CalendarCostData() ;
		calendarCostData.setIsCombination(product.isCombination());
		calendarCostData.setIsPackage(product.isPackage());

		Day day = product.getCalendar().get(dateTime.toString("yyyy-MM-dd")) ;
		Cost cost = day.getCost() ;

		if(product.isPackage()){
			calendarCostData.setPackageAdultCost(cost.getPackageAdultCost());
			calendarCostData.setPackageChildCost(cost.getPackageChildCost());
		}

		if(product.isCombination()){
			if(cost.isContainFlightCost(product)){
				calendarCostData.setFlightAdultCost(cost.getFlightAdultCost());
				calendarCostData.setFlightChildCost(cost.getFlightChildCost());
				calendarCostData.setIsContainFlightCost(true);
			}else{
				calendarCostData.setIsContainFlightCost(false);
			}
			if(cost.isContainHotelCost(product)){
				Double totalHotelCost = 0d ;
				if(cost.getHotelRoomCost() != null){
					for(Double hotelCost : cost.getHotelRoomCost()){
						totalHotelCost += hotelCost == null ? 0 : hotelCost ;
					}
				}
				calendarCostData.setHotelRoomCost(totalHotelCost);
				calendarCostData.setIsContainHotelCost(true);
			}else{
				calendarCostData.setIsContainHotelCost(false);
			}
		}
		
		calendarCostData.setIsContainOtherCost(product.isContainOther());
		calendarCostData.setOtherCost(product.getOtherCost());
		calendarCostData.setIsContainWifiCost(product.isContainWifi());
		calendarCostData.setWifiCost(product.getWifiCost());
		calendarCostData.setIsContainShuttleCost(product.isContainShuttle());
		calendarCostData.setShuttleCost(product.getShuttleCost());
		calendarCostData.setIsContainZenbookCost(product.isContainZenbook());
		calendarCostData.setZenbookCost(product.getZenbookCost());
		
		calendarCostData.setTeamNum(day.getTeamNum());
		
		return calendarCostData;
	}

	/**
	 * 单独保存或修改某一个产品某一天的数据
	 * */
	@Override
	public Boolean singleSave(CalendarSingleSaveBean bean) {
		DateTime effectDay = DateTime.parse(bean.getEffectDay()) ;
		DateTime now = DateTime.parse(DateTime.now().toString("yyyy-MM-dd")) ;
		if(now.isBefore(effectDay) || now.isEqual(effectDay)){
			String effectDayStr = effectDay.toString("yyyy-MM-dd") ;
			Sale sale = Converter.convertVo2Sale(bean) ;
			return priceCalendarDao.updateSale(bean.getId(), effectDayStr, sale)
					&& productService.updateLowestPrice(bean.getId()) ;
		}else{
			return false ;
		}
	}

	/**
	 * 取得DateTime对应Date的销售情况
	 * */
	@Override
	public Sale getSale(String id, DateTime dateTime) {
		return priceCalendarDao.getSale(id, dateTime.toString("yyyy-MM-dd")) ;
	}

	/**
	 * 修改取得DateTime对应Date的销售关闭/开启状态
	 * */
	@Override
	public Boolean toggleClose(String id, DateTime dateTime, Boolean close) {
		String effectDay = dateTime.toString("yyyy-MM-dd") ;
		if(close){
			return priceCalendarDao.updateSaleStatus(id, effectDay, SaleStatus.CLOSED) && productService.updateLowestPrice(id) ;
		}else{
			return priceCalendarDao.updateSaleStatus(id, effectDay, SaleStatus.RELEASED) && productService.updateLowestPrice(id) ;
		}
	}
	
	/**
	 * 修改取得DateTime对应Date的销售关闭/开启状态
	 * */
	@Override
	public Boolean toggleCanOrder(String id, DateTime dateTime, Boolean canOrder) {
		String effectDay = dateTime.toString("yyyy-MM-dd") ;
		return priceCalendarDao.updateSaleStatus(id, effectDay, SaleStatus.NOT_SCHEDULED) ;
	}

	/**
	 * 删除id的dateTime下的Sale
	 * */
	@Override
	public Boolean deleteSale(String id, DateTime dateTime) {
		String effectDay = dateTime.toString("yyyy-MM-dd") ;
		return priceCalendarDao.deleteSale(id, effectDay) && productService.updateLowestPrice(id);
	}

	/**
	 * 批量修改状态可用日期
	 * */
	@Override
	public List<String> convertAvaiDays(String id, String start, String end, String weeks, boolean isChangeStatus) {
		Product product = productDao.getProductById(id) ;
		List<DateTime> days = CommonUtils.convertAvaiDays(start, end, weeks) ;
		List<String> addDays = new ArrayList<String>() ;
		DateTime now = new DateTime(new DateTime().toString("yyyy-MM-dd")) ;
		for(DateTime day:days){
			if(day.isAfter(now) || day.isEqual(now)){
				Day tmp = product.getCalendar().get(day.toString("yyyy-MM-dd")) ;
				if(tmp != null && tmp.getCost() != null){
					if(isChangeStatus){
						if(tmp.getSale() != null){
							addDays.add(day.toString("yyyy-MM-dd")) ;
						}
					}else{
						addDays.add(day.toString("yyyy-MM-dd")) ;
					}
				}
			}
		}
		return addDays ;
	}

	/**
	 * 批量保存
	 * */
	@Override
	public CommonResponse batchSave(CalendarBatchSaveBean bean) {
		CommonResponse response = new CommonResponse() ;
		List<String> days = convertAvaiDays(bean.getId(), bean.getStart(), bean.getEnd(), bean.getWeekDays(), false) ;
		Sale sale = Converter.convertVo2Sale(bean) ;
		if(CollectionUtils.isEmpty(days)){
			response.setErrMsg("NO_DAYS");
			response.setSuccess(false);
			return response ;
		}
		response.setSuccess(priceCalendarDao.updateSaleBatch(bean.getId(), days, sale, bean.isNeedUpdPrice())
				&& productService.updateLowestPrice(bean.getId())) ;
		return response ;
	}
	
	/**
	 * get pkg
	 * */
	@Override
	public SalesPackage getPkg(String id, String effectDay, String pkgId) {
		return priceCalendarDao.getPkg(id, effectDay, pkgId) ;
	}
	
	/**
	 * 删除pkg
	 * */
	@Override
	public boolean deletePkg(String id, String effectDay, String pkgId) {
		return priceCalendarDao.deletePkg(id, effectDay, pkgId) && productService.updateLowestPrice(id) ;
	}
	
	/**
	 * 批量删除
	 * */
	@Override
	public String batchDelete(CalendarBatchAreaBean bean) {
		String ret = "" ;
		List<String> days = convertAvaiDays(bean.getId(), bean.getStart(), bean.getEnd(), bean.getWeekDays(), false) ;
		for(String day:days){
			if(!deleteSale(bean.getId(), new DateTime(day))){
				ret += day + ";" ;
			}
		}
		return StringUtils.isEmpty(ret) ? "" : ret + "删除失败" ;
	}
	
	/**
	 * 批量关闭
	 * */
	@Override
	public String batchClose(CalendarBatchAreaBean bean) {
		String ret = "" ;
		List<String> days = convertAvaiDays(bean.getId(), bean.getStart(), bean.getEnd(), bean.getWeekDays(), true) ;
		for(String day:days){
			if(!toggleClose(bean.getId(), new DateTime(day), true)){
				ret += day + ";" ;
			}
		}
		return StringUtils.isEmpty(ret) ? "" : ret + "关闭失败" ;
	}
	
	/**
	 * 批量未开放
	 * */
	@Override
	public String batchCantOrder(CalendarBatchAreaBean bean) {
		String ret = "" ;
		List<String> days = convertAvaiDays(bean.getId(), bean.getStart(), bean.getEnd(), bean.getWeekDays(), true) ;
		for(String day:days){
			if(!toggleCanOrder(bean.getId(), new DateTime(day), false)){
				ret += day + ";" ;
			}
		}
		return StringUtils.isEmpty(ret) ? "" : ret + "设置未开放失败" ;
	}

	/**
	 * 时间价格保存
	 * */
	@Override
	public Boolean saveCheck(String id) {
		Boolean isOk = true ;
		try{
			isOk = productService.updateProgress(id, 3) ;
		}catch(Exception e){
			isOk = false ;
			LOGGER.error(e.getMessage(), e);
		}
		return isOk ;
	}

	@Override
	public String acquirePkgId() throws Exception {
		return idGeneratorUtil.getPkgId();
	}

}
