package com.ztravel.product.weixin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.Const;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.weixin.convertor.LocalListConvertor;
import com.ztravel.product.weixin.entity.ProductSearchCriteria;
import com.ztravel.product.weixin.service.ILocalListService;
import com.ztravel.product.weixin.vo.LocaltListVo;
import com.ztravel.reuse.member.entity.SearchModuleVo;
import com.ztravel.reuse.product.service.IUnvisaProductReuseService;

@Controller
@RequestMapping("/weixin/product/local")
public class LocalListController {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(LocalListController.class);
	@Resource
	private FrontCommonService frontCommonService;
	
	@Resource
	private IUnvisaProductReuseService unvisaProductReuseService;
	
	@Resource
	private ILocalListService localListServiceImpl;
	
	@RequestMapping("/list")
	public String list(Model model){
		SearchModuleVo smv = frontCommonService.getSearchModuleVo(RedisKeyConst.AVAILABLE_LOCAL_DESTINATION_KEY) ;
		smv.setDepartPlace("上海");
		smv.setDefaultDestination(Const.DESTINATION_PLACEHOLDER);
		smv.setDestinationLevel(1);
		model.addAttribute("smv", smv);
		return "product/weixin/list/local/list";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String search(@RequestBody ProductSearchCriteria criteria, Model model){
		Map<String,String> map = LocalListConvertor.paramsConvert(criteria);
		List<UnVisaProduct> unVisaProducts = localListServiceImpl.search(map);
		List<LocaltListVo> vos = null;
		try { 
			vos = LocalListConvertor.listConvert(unVisaProducts);
			model.addAttribute("success", true);
			model.addAttribute("productList", vos);
		} catch (Exception e) {
			model.addAttribute("success", false);
			LOGGER.error("移动端当地游产品列表查询异常:[{}]", e);
		}
		return "product/weixin/list/local/data";
	}
}
