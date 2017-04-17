package com.ztravel.product.back.freetravel.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.service.IBasicInfoService;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.utils.ProductValidator;
import com.ztravel.product.back.freetravel.vo.BasicInfoVo;
import com.ztravel.product.back.freetravel.vo.ProductMenuVo;

@Controller
@RequestMapping("/product/basicInfo")
public class BasicInfoController {
	@Resource
	private IBasicInfoService basicInfoServiceImpl;

	@Resource
	private ProductService productService ;

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(BasicInfoController.class) ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping("/add")
	public String add(Model model){
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
		model.addAttribute("mode", "edit");
		ProductMenuVo productMenuVo = new ProductMenuVo();
		productMenuVo.setPid("");
		productMenuVo.setProgress(0);
		model.addAttribute("productMenuVo", productMenuVo) ;
		model.addAttribute("ADDRESS", redisClient.get(Const.CONTINENT_NATION_CITY_KEY).replace("\"", "'"));
		//从redis中取可选主题
		List<String> themeList = redisClient.get(RedisKeyConst.PRODUCT_THEME_KEY, List.class);
		model.addAttribute("themes", themeList);
		//从redis中取可选标签
		List<String> tagList = redisClient.get(RedisKeyConst.PRODUCT_TAG_KEY, List.class);
		model.addAttribute("hotTags", tagList);
		//从redis中取可选出发地
		List<String> departPlaceList = redisClient.get(RedisKeyConst.DEPARTURE_PLACE_KEY, List.class);
		model.addAttribute("departurePlaces", departPlaceList);
		return "product/back/basic_info_edit";
	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable String id)throws Exception{
		BasicInfoVo basicInfo = null;
		String url = "product/back/basic_info_edit";
		try {
			basicInfo = basicInfoServiceImpl.queryById(id);
		} catch (ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
			throw e;
		}catch(Exception e){
			LOGGER.error("查询基本信息出错"+e.getMessage(), e);
		}
		model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
		model.addAttribute("basicInfo", basicInfo);
		model.addAttribute("mode", "edit");
		model.addAttribute("ADDRESS", redisClient.get(Const.CONTINENT_NATION_CITY_KEY).replace("\"", "'"));
		//从redis中取可选主题
		List<String> themeList = redisClient.get(RedisKeyConst.PRODUCT_THEME_KEY, List.class);
		model.addAttribute("themes", themeList);
		//从redis中取可选标签
		List<String> tagList = redisClient.get(RedisKeyConst.PRODUCT_TAG_KEY, List.class);
		model.addAttribute("hotTags", tagList);
		//从redis中取可选出发地
		List<String> departPlaceList = redisClient.get(RedisKeyConst.DEPARTURE_PLACE_KEY, List.class);
		model.addAttribute("departurePlaces", departPlaceList);
		return url;
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResponse save(@RequestBody BasicInfoVo basicInfo){
		String id = basicInfo.getId();
		try {
			if(basicInfo.getWithNext()){
				ProductValidator.AssertBasicInfo(basicInfo);
			}
			id = basicInfoServiceImpl.save(basicInfo);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
		}catch(ZtrBizException e){
			LOGGER.error(e.getRetMsg(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
		}catch(Exception e){
			LOGGER.error("保存基本信息失败:"+e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存基本信息出错");
		}
		return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, id);
	}

}
