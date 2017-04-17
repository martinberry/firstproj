package com.ztravel.product.back.hotel.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.hotel.convert.HotelConvert;
import com.ztravel.product.back.hotel.service.ContiNatCityService;
import com.ztravel.product.back.hotel.service.HotelEditService;
import com.ztravel.product.back.hotel.validation.HotelEditValidate;
import com.ztravel.product.back.hotel.wo.HotelEntityWo;

/**
 * 酒店添加与详情
 * @author tengmeilin
 *
 */
@Controller
@RequestMapping("/product/back/hotel")
public class HotelEditController {

	private static Logger logger = RequestIdentityLogger.getLogger(HotelEditController.class);

	@Resource
	private HotelEditService hotelEditServiceImpl ;

	@Resource
	private ContiNatCityService contiNatCityServiceImpl ;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addHotel(Model model) throws Exception {

		model.addAttribute("hotel", new HotelEntityWo());

		Map<String, String> rateMap = HotelConvert.convertHotelRateToMap();
	    Map<String, String> typeMap = HotelConvert.convertHotelTypeToMap();
	    model.addAttribute("rateMap", rateMap);
	    model.addAttribute("typeMap", typeMap);

//	    获取一级大洲
	    List<String> continentList = contiNatCityServiceImpl.getContinentList();
	    model.addAttribute("continentList", continentList);

		return new ModelAndView("product/back/hotel/edithotel") ;
	}

	@RequestMapping(value = "/edit/{hotelId}", method = RequestMethod.GET)
	public ModelAndView editHotel(@PathVariable("hotelId") String id, Model model) throws Exception {

	    HotelEntityWo hotel = new HotelEntityWo();
	    if(StringUtils.isBlank(id)){
	    	logger.error("failed to get hotelId from list");
	    	throw ZtrBizException.instance(Const.FO_PROD_CODE_1001, Const.FO_PROD_REASON_1001) ;
	    }

	    try {
	    	hotel = hotelEditServiceImpl.getHotelById(id);
	    }catch (Exception e) {
	    	logger.error("failed to get hotel", e);
	    	throw ZtrBizException.instance(Const.FO_PROD_CODE_1002, Const.FO_PROD_REASON_1002) ;
		}

	    model.addAttribute("hotel", hotel);

	    Map<String, String> rateMap = HotelConvert.convertHotelRateToMap();
	    Map<String, String> typeMap = HotelConvert.convertHotelTypeToMap();
	    model.addAttribute("rateMap", rateMap);
	    model.addAttribute("typeMap", typeMap);

//	    获取一级大洲
	    List<String> continentList = contiNatCityServiceImpl.getContinentList();
	    model.addAttribute("continentList", continentList);
//	    获取国家
	    if(StringUtils.isNotBlank(hotel.getContinent())){
	    	List<String> nationList = contiNatCityServiceImpl.getNationList(hotel.getContinent());
			model.addAttribute("nationList", nationList);
	    }
//	    获取城市
	    if(StringUtils.isNotBlank(hotel.getNation())){
	    	List<String> cityList = contiNatCityServiceImpl.getCityList(hotel.getNation());
			model.addAttribute("cityList", cityList);
	    }

		return new ModelAndView("product/back/hotel/edithotel") ;
	}

	@RequestMapping(value = "/loadNation", method = RequestMethod.POST)
	public String loadCountry(@RequestBody String continentName, Model model){

		if(StringUtils.isNotBlank(continentName)){
			List<String> nationList = contiNatCityServiceImpl.getNationList(continentName);
			model.addAttribute("nationList", nationList);
		}
		return "product/back/hotel/nationMenu";
	}

	@RequestMapping(value = "/loadCity", method = RequestMethod.POST)
	public String loadCity(@RequestBody String nationName, Model model){

		if(StringUtils.isNotBlank(nationName)){
			List<String> cityList = contiNatCityServiceImpl.getCityList(nationName);
			model.addAttribute("cityList", cityList);
		}
		return "product/back/hotel/cityMenu";
	}

	@RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse uploadPicture(@RequestParam(value = "picture", required = true) MultipartFile file,
	        HttpServletRequest request) throws Exception {
		// 判断图片大小是否大于2M
	    if (file.getSize() > 2 * 1024 * 1024 || file.getSize() == 0) {
	        throw ZtrBizException.instance(Const.FO_PROD_CODE_1003, Const.FO_PROD_REASON_1003) ;
	    }
	    // 获取图片的文件名
	    String fileName = file.getOriginalFilename();
	    // 获取图片的扩展名
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);

		if("jpg,png,jpeg".indexOf(extensionName.toLowerCase()) == -1){
	        throw ZtrBizException.instance(Const.FO_PROD_CODE_1004, Const.FO_PROD_REASON_1004) ;
		}

		String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName ;
		String imageId;
		try{
			imageId = hotelEditServiceImpl.saveHotelPicture(newFileName, file.getBytes());
		}catch (IOException ex) {
			throw ex ;
		}catch (Exception e) {
			logger.error("failed to save hotel picture", e);
			return AjaxResponse.instance(Const.FO_PROD_CODE_1005, Const.FO_PROD_REASON_1005 ) ;
		}

		return AjaxResponse.instance(Const.SO_PROD_CODE_1001, imageId) ;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse updateHotel(@RequestBody HotelEntityWo hotelEntityWo, HttpServletRequest request) throws Exception {

		if( !HotelEditValidate.validateHotelEntity(hotelEntityWo)){
			return AjaxResponse.instance(Const.FO_PROD_CODE_1006, Const.FO_PROD_REASON_1006) ;
		}

		if(hotelEntityWo.getIsComplete()){
			if( !HotelEditValidate.validateIsComplete(hotelEntityWo)){
				return AjaxResponse.instance(Const.FO_PROD_CODE_1007, Const.FO_PROD_REASON_1007) ;
			}
		}

		try{
			if(StringUtils.isBlank(hotelEntityWo.getId())){
				hotelEditServiceImpl.addHotel(hotelEntityWo);
			}else{
				hotelEditServiceImpl.updateHotel(hotelEntityWo);
			}
		}catch (Exception e) {
			logger.error("failed to save hotel", e);
			return AjaxResponse.instance(Const.FO_PROD_CODE_1008, Const.FO_PROD_REASON_1008) ;
		}

		return AjaxResponse.instance(Const.SO_PROD_CODE_1002, Const.SUCCESS) ;
	}

}
