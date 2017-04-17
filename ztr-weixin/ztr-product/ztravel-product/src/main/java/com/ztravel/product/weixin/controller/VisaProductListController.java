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
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.product.weixin.convertor.VisaProductListConvertor;
import com.ztravel.product.weixin.entity.ProductSearchCriteria;
import com.ztravel.product.weixin.service.IVisaProductListService;
import com.ztravel.product.weixin.vo.VisaProductListVo;
import com.ztravel.reuse.member.entity.SearchModuleVo;
import com.ztravel.reuse.product.service.IVisaProductReuseService;


@Controller
@RequestMapping("/weixin/product/visa")
public class VisaProductListController {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(VisaProductListController.class);
	@Resource
	private FrontCommonService frontCommonService;
	
	@Resource
	private IVisaProductReuseService visaProductReuseService;
	
	@Resource
	private IVisaProductListService visaProductListServiceImpl;
	
	@RequestMapping("/list")
	public String list(Model model){
		SearchModuleVo smv = frontCommonService.getSearchModuleVo(RedisKeyConst.AVAILABLE_VISA_DESTINATION_KEY) ;
		smv.setDepartPlace("上海");
		smv.setDefaultDestination(Const.DESTINATION_PLACEHOLDER);
		smv.setDestinationLevel(1);
		model.addAttribute("smv", smv);
		return "product/weixin/list/visa/list";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String search(@RequestBody ProductSearchCriteria criteria, Model model){
		Map<String,String> map = VisaProductListConvertor.paramsConvert(criteria);
		List<VisaProduct> visaProducts = visaProductListServiceImpl.search(map);
		List<VisaProductListVo> vos = null;
		try { 
			vos = VisaProductListConvertor.listConvert(visaProducts);
			model.addAttribute("success", true);
			model.addAttribute("productList", vos);
		} catch (Exception e) {
			model.addAttribute("success", false);
			LOGGER.error("移动端签证产品列表查询异常:[{}]", e);
		}
		return "product/weixin/list/visa/data";
	}
}
