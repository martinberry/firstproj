package com.ztravel.product.back.hotel.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.hotel.entity.searchcriteria.HotelSearchCriteria;
import com.ztravel.product.back.hotel.service.ContiNatCityService;
import com.ztravel.product.back.hotel.service.IHotelService;
import com.ztravel.product.back.hotel.vo.HotelVO;

/**
 * @author
 *
 */
@Controller
@RequestMapping("/product/hotelResource")
public class HotelListController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(HotelListController.class);

	@Resource
	private IHotelService hotelService;

	@Resource
	private ProductService productService;

	@Resource
	private ContiNatCityService contiNatCityService;

	@RequestMapping("/list")
	public String showHotelList(Model model){
		List<String> continentList = contiNatCityService.getContinentList();
		model.addAttribute("continentList", continentList);
		return "product/back/hotel/hotelList";
	}

	@RequestMapping("/loadCountry")
	public String loadCountry(@RequestBody String continentName, Model model){
		if( !continentName.equals("不限") ){
			List<String> countryList = contiNatCityService.getNationList(continentName);
			model.addAttribute("countryList", countryList);
		}
		return "product/back/hotel/countryDropDownMenu";
	}

	@RequestMapping("/loadCity")
	public String loadCity(@RequestBody String countryName, Model model){
		if( !countryName.equals("不限") ){
			List<String> cityList = contiNatCityService.getCityList(countryName);
			model.addAttribute("cityList", cityList);
		}
		return "product/back/hotel/cityDropDownMenu";
	}

	@RequestMapping(value="/searchHotel",method=RequestMethod.POST)
	public String searchHotelResource(@RequestBody HotelSearchCriteria searchCriteria, Model model){
		//搜索条件格式校验，若校验失败，返回空
		ValidResult validResult = BasicValidator.valid(searchCriteria);
		if( !validResult.isSuccess() ){
			return "product/back/hotel/hotelTable";
		}

		int totalNum = 0;
		List<HotelVO> hotelList = null;
		try{
			totalNum = hotelService.countHotels(searchCriteria);
			hotelList = hotelService.searchHotels(searchCriteria);
		} catch(ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		model.addAttribute("hotelList", hotelList);
		model.addAttribute("totalItemCount", totalNum);
		if( totalNum != 0 ){
			model.addAttribute("pageNo", searchCriteria.getPageNo());
		}else{
			model.addAttribute("pageNo", 1);
		}
		model.addAttribute("pageSize", searchCriteria.getPageSize());
		Integer totalPageCount = (int) Math.ceil( (double)totalNum/searchCriteria.getPageSize() );
		totalPageCount = totalPageCount == 0 ? 1 : totalPageCount ;
		model.addAttribute("totalPageCount", totalPageCount);
		return "product/back/hotel/hotelTable";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public AjaxResponse deleteHotel(@RequestBody String id){
		try{
			//若该酒店已被添加至产品中,不可删除
			Boolean isHotelUsed = productService.isHotelUsedByProduct(id);
			if( isHotelUsed ){
				return AjaxResponse.instance(ProductCons.PROD_HOTEL_CONTAINED_BY_PRODUCT_DELETE_ERROR_CODE, ProductCons.PROD_HOTEL_CONTAINED_BY_PRODUCT_DELETE_ERROR_MSG);
			}
			hotelService.deleteHotelById(id);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_HOTEL_DELETE_ERROR_CODE, ProductCons.PROD_HOTEL_DELETE_ERROR_MSG);
		}
		return AjaxResponse.instance(ProductCons.PROD_HOTEL_DELETE_SUCCESS_CODE, ProductCons.PROD_HOTEL_DELETE_SUCCESS_MSG);
	}

}
