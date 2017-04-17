package com.ztravel.product.back.freetravel.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.common.service.ContiNatCityCommonService;
import com.ztravel.product.back.freetravel.entity.ProductSearchCriteria;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.vo.ProductCheckRespBean;
import com.ztravel.product.back.freetravel.vo.ProductVo;

/**
 * @author
 * @description 后台系统 会员维护Controller
 */

@Controller
@RequestMapping("/product/productMaintain")
public class ProductMaintainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMaintainController.class);

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private ProductService productService ;
	
	

	@Resource
	private ContiNatCityCommonService contiNatCityService;

	@RequestMapping("/index")
	public String showProductList(Model model, HttpServletRequest request) throws Exception{
		List<String> continentList = contiNatCityService.getContinentList();
		model.addAttribute("continentList", continentList);
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
		//redis中取出发地
		List<String> departPlaces = redisClient.get(RedisKeyConst.DEPARTURE_PLACE_KEY, List.class);
		model.addAttribute("departurePlaces", departPlaces);
		//redis中取产品主题
		List<String> themes = redisClient.get(RedisKeyConst.PRODUCT_THEME_KEY, List.class);
		model.addAttribute("themes", themes);

		return "product/back/product_list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String  search(@RequestBody ProductSearchCriteria searchCriteria, Model model){

		Map<String, Object> searchResultMap = Maps.newHashMap();
		int pageNo=0;
		int pageSize=0;
		Long totalItemCount=0L;
		Integer totalPageCount=0;
		List<ProductVo> productVoList = Lists.newArrayList();

		try{
			searchResultMap =  productService.search(searchCriteria);
		}catch(Exception e){
			LOGGER.info("产品列表查询失败："+e.getMessage());
		}
		if(null!=searchResultMap){
			productVoList=(List<ProductVo>)searchResultMap.get("productVoList");
			pageNo=(int)searchResultMap.get("pageNo");
			pageSize=(int)searchResultMap.get("pageSize");
			totalItemCount=(Long)searchResultMap.get("totalItemCount");
			totalPageCount=(Integer)searchResultMap.get("totalPageCount");
		}

		model.addAttribute("productVoList", productVoList);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);

		model.addAttribute("totalItemCount", totalItemCount);
		model.addAttribute("totalPageCount", totalPageCount);

		return "product/back/product_table";
	}

	@RequestMapping(value = "/online")
	@ResponseBody
	public AjaxResponse online(String id){
		try{
			ProductCheckRespBean bean = productService.changeProductStatus(id, Status.RELEASED) ;
			if(!bean.getFlag()){
				return AjaxResponse.instance(ProductCons.PROD_PRODUCT_ONLINE_ERROR_CODE, bean.getMsg());
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_PRODUCT_ONLINE_ERROR_CODE, ProductCons.PROD_PRODUCT_ONLINE_ERROR_MSG);
		}
		return AjaxResponse.instance(ProductCons.PROD_PRODUCT_ONLINE_SUCCESS_CODE, ProductCons.PROD_PRODUCT_ONLINE_SUCCESS_MSG);
	}

	@RequestMapping(value = "/close")
	@ResponseBody
	public AjaxResponse close(String id){
		try{
			ProductCheckRespBean bean = productService.changeProductStatus(id, Status.OFFLINE) ;
			if(!bean.getFlag()){
				return AjaxResponse.instance(ProductCons.PROD_PRODUCT_CLOSE_ERROR_CODE, ProductCons.PROD_PRODUCT_CLOSE_ERROR_MSG);
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_PRODUCT_CLOSE_ERROR_CODE, ProductCons.PROD_PRODUCT_CLOSE_ERROR_MSG);
		}
		return AjaxResponse.instance(ProductCons.PROD_PRODUCT_CLOSE_SUCCESS_CODE, ProductCons.PROD_PRODUCT_CLOSE_SUCCESS_MSG);
	}
	@RequestMapping(value = "/del")
	@ResponseBody
	public AjaxResponse del(String id){
		try{
		
			if(!productService.deleteProductAndWishList(id)){
				return AjaxResponse.instance(ProductCons.PROD_PRODUCT_DEL_ERROR_CODE, ProductCons.PROD_PRODUCT_DEL_ERROR_MSG);
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_PRODUCT_DEL_ERROR_CODE, ProductCons.PROD_PRODUCT_DEL_ERROR_MSG);
		}
		return AjaxResponse.instance(ProductCons.PROD_PRODUCT_DEL_SUCCESS_CODE, ProductCons.PROD_PRODUCT_DEL_SUCCESS_MSG);
	}

	@RequestMapping("/loadCountry")
	public String loadCountry(@RequestBody String continentName, Model model){
		if( !continentName.equals("不限") ){
			List<String> countryList = contiNatCityService.getNationList(continentName);
			model.addAttribute("countryList", countryList);
		}
		return "product/common/countryDropDownMenu";
	}

	@RequestMapping("/loadCity")
	public String loadCity(@RequestBody String countryName, Model model){
		if( !countryName.equals("不限") ){
			List<String> cityList = contiNatCityService.getCityList(countryName);
			model.addAttribute("cityList", cityList);
		}
		return "product/common/cityDropDownMenu";
	}

}