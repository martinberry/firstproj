package com.ztravel.product.back.freetravel.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.service.PriceCalendarService;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.vo.CalendarBatchAreaBean;
import com.ztravel.product.back.freetravel.vo.CalendarBatchSaveBean;
import com.ztravel.product.back.freetravel.vo.CalendarCostData;
import com.ztravel.product.back.freetravel.vo.CalendarMonthData;
import com.ztravel.product.back.freetravel.vo.CalendarSingleSaveBean;


/**
 * @author wanhaofan
 * 价格日历
 * */
@Controller
@RequestMapping("/product/calendar")
public class PriceCalendarController {
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(PriceCalendarController.class) ;

	@Resource
	private PriceCalendarService priceCalendarService ;

	@Resource
	private ProductService productService ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping("/view/{id}")
	public String view(Model model, HttpServletRequest request,@PathVariable String id){
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));

	    DateTime now = new DateTime() ;

	    CalendarMonthData data = priceCalendarService.getCalendarMonthData(id, now) ;

	    model.addAttribute("data", JSONObject.toJSONString(data)) ;
	    model.addAttribute("id", id) ;
	    model.addAttribute("now", now.toString("yyyy-MM-dd")) ;
	    model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;
	    model.addAttribute("mode", "view") ;

		return "product/back/price_calendar" ;
	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, HttpServletRequest request,@PathVariable String id){
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));

	    DateTime now = new DateTime() ;

	    CalendarMonthData data = priceCalendarService.getCalendarMonthData(id, now) ;

	    model.addAttribute("data", JSONObject.toJSONString(data)) ;
	    model.addAttribute("id", id) ;
	    model.addAttribute("now", now.toString("yyyy-MM-dd")) ;
	    model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;
	    model.addAttribute("mode", "edit") ;

		return "product/back/price_calendar" ;
	}

	@RequestMapping("/getMonthData")
	@ResponseBody
	public String getMonthData(String id, String now){

	    CalendarMonthData data = priceCalendarService.getCalendarMonthData(id, new DateTime(now)) ;

		return JSONObject.toJSONString(data) ;
	}

	@RequestMapping("/getCost")
	@ResponseBody
	public CalendarCostData getCost(String id, String now){

		CalendarCostData data = priceCalendarService.getCalendarCostData(id, new DateTime(now)) ;

		return data ;
	}

	@RequestMapping("/getSale")
	@ResponseBody
	public Sale getSale(String id, String now){

		Sale sale = priceCalendarService.getSale(id, new DateTime(now)) ;

		return sale ;
	}

	@RequestMapping("/singleSave")
	@ResponseBody
	public Boolean singleSave(@RequestBody CalendarSingleSaveBean calendarSingleSaveBean){
		return priceCalendarService.singleSave(calendarSingleSaveBean) ;
	}

	@RequestMapping("/batchSave")
	@ResponseBody
	public CommonResponse batchSave(@RequestBody CalendarBatchSaveBean calendarBatchSaveBean){
		return priceCalendarService.batchSave(calendarBatchSaveBean) ;
	}
	
	@RequestMapping("/deletePkg")
	@ResponseBody
	public boolean deletePkg(String id, String effectDay, String pkgId){
		SalesPackage pkg = priceCalendarService.getPkg(id, effectDay, pkgId) ;
		if(pkg != null){
			return priceCalendarService.deletePkg(id, effectDay, pkgId) ;
		}else{
			return true ;
		}
	}
	
	@RequestMapping("/acquirePkgId")
	@ResponseBody
	public CommonResponse acquirePkgId(){
		CommonResponse response = new CommonResponse() ;
		String pkgId = "" ;
		boolean success = true ;
		try {
			pkgId = priceCalendarService.acquirePkgId() ;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			success = false ;
		}
		response.setSuccess(success);
		response.setErrMsg(pkgId);
		return response ;
	}
	
	@RequestMapping("/batchClose")
	@ResponseBody
	public String batchClose(@RequestBody CalendarBatchAreaBean bean){
		return JSONObject.toJSONString(priceCalendarService.batchClose(bean)) ;
	}
	
	@RequestMapping("/batchDelete")
	@ResponseBody
	public String batchDelete(@RequestBody CalendarBatchAreaBean bean){
		return JSONObject.toJSONString(priceCalendarService.batchDelete(bean)) ;
	}
	
	@RequestMapping("/batchCantOrder")
	@ResponseBody
	public String batchCantOrder(@RequestBody CalendarBatchAreaBean bean){
		return JSONObject.toJSONString(priceCalendarService.batchCantOrder(bean)) ;
	}

	@RequestMapping("/toggleClose")
	@ResponseBody
	public Boolean toggleClose(Boolean close, String now, String id){
		return priceCalendarService.toggleClose(id, new DateTime(now), close) ;
	}

	@RequestMapping("/deleteSale")
	@ResponseBody
	public Boolean deleteSale(String now, String id){
		return priceCalendarService.deleteSale(id, new DateTime(now)) ;
	}
	
	@RequestMapping("/toggleCanOrder")
	@ResponseBody
	public Boolean toggleCanOrder(Boolean canOrder, String now, String id){
		return priceCalendarService.toggleCanOrder(id, new DateTime(now), canOrder) ;
	}

	@RequestMapping("/saveCheck")
	@ResponseBody
	public Boolean saveCheck(String now, String id){
		return priceCalendarService.saveCheck(id) ;
	}


}
