package com.ztravel.product.back.freetravel.service;

import java.util.List;

import org.joda.time.DateTime;

import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.vo.CalendarBatchAreaBean;
import com.ztravel.product.back.freetravel.vo.CalendarBatchSaveBean;
import com.ztravel.product.back.freetravel.vo.CalendarCostData;
import com.ztravel.product.back.freetravel.vo.CalendarMonthData;
import com.ztravel.product.back.freetravel.vo.CalendarSingleSaveBean;


/**
 * @author wanhaofan
 * */
public interface PriceCalendarService {

	CalendarMonthData getCalendarMonthData(String id, DateTime dateTime) ;

	CalendarCostData getCalendarCostData(String id, DateTime dateTime) ;

	Boolean singleSave(CalendarSingleSaveBean calendarSingleSaveBean) ;

	CommonResponse batchSave(CalendarBatchSaveBean calendarBatchSaveBean) ;

	Sale getSale(String id, DateTime dateTime) ;

	Boolean toggleClose(String id, DateTime dateTime, Boolean close) ;

	Boolean deleteSale(String id, DateTime dateTime) ;

	Boolean saveCheck(String id) ;

	Boolean toggleCanOrder(String id, DateTime dateTime, Boolean canOrder);

	String batchDelete(CalendarBatchAreaBean bean);

	String batchClose(CalendarBatchAreaBean bean);

	String batchCantOrder(CalendarBatchAreaBean bean);

	List<String> convertAvaiDays(String id, String start, String end,
			String weeks, boolean isChangeStatus);
	
	String acquirePkgId() throws Exception ;

	boolean deletePkg(String id, String effectDay, String pkgId);

	SalesPackage getPkg(String id, String effectDay, String pkgId);
}
