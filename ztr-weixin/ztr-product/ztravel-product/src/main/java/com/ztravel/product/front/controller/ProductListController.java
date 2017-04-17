package com.ztravel.product.front.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.common.enums.SEOEnums;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.util.SEOUtil;
import com.ztravel.reuse.member.entity.SearchModuleVo;
import com.ztravel.reuse.member.entity.WorkPlatFormVo;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.product.front.entity.ProductSearchCriteria;
import com.ztravel.product.front.service.IProductListService;
import com.ztravel.product.front.vo.ProductVO;

/**
 * 前台产品列表页
 * @author MH
 */
@Controller
@RequestMapping("/product")
public class ProductListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductListController.class);

	@Resource
	private IProductListService productService;

	@Resource
	private FrontCommonService frontCommonService;

	private final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping("/list")
	public String showProductListPage(@RequestParam(required=false) String departure,
			                                                     @RequestParam(required=false) String destination,
			                                                     @RequestParam(required=false) Integer destLevel, Model model, HttpServletRequest request){

		SearchModuleVo smv = frontCommonService.getSearchModuleVo(RedisKeyConst.AVAILABLE_DESTINATION_KEY) ;
		//产品搜索条件(不传参数,默认搜索上海到世界)
		if( StringUtils.isNotBlank(departure) ){
			smv.setDepartPlace(departure);
		}else{
			smv.setDepartPlace("上海");
		}

		if( StringUtils.isNotBlank(destination) ){
			smv.setDefaultDestination(destination);
		}else{
			smv.setDefaultDestination(Const.DESTINATION_PLACEHOLDER);
		}

		if( destLevel != null ){
			smv.setDestinationLevel(destLevel);
		}else{
			smv.setDestinationLevel(1);
		}
		model.addAttribute("smv", smv);

		model.addAttribute("title", SEOUtil.getProductListSEO(smv.getDepartPlace(), smv.getDefaultDestination(), SEOEnums.LIST_TITLE));
		model.addAttribute("description", SEOUtil.getProductListSEO(smv.getDepartPlace(), smv.getDefaultDestination(), SEOEnums.LIST_DESC));
		model.addAttribute("keywords", SEOUtil.getProductListSEO(smv.getDepartPlace(), smv.getDefaultDestination(), SEOEnums.LIST_KEYWORDS));

		WorkPlatFormVo wpfv = frontCommonService.getWorkPlatFormVo(request);
		model.addAttribute("wpfv", wpfv) ;
		return "product/front/list/productList";
	}

	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String searchProductList(@RequestBody ProductSearchCriteria criteria, Model model){
		List<ProductVO> prodList;
		Boolean hasProduct = false;
		Boolean hasRecommendProd = false;
		try{
			if( criteria.getDestination().equals("全部") ){
				criteria.setDestination(Const.DESTINATION_PLACEHOLDER);
			}
			prodList = productService.searchProducts(criteria);
			if( prodList != null && prodList.size() != 0 ){  //有产品
				hasProduct = true;
			}else{   //无指定产品,推荐该出发地所有产品
				hasProduct = false;
				model.addAttribute("searchCriteria", criteria);
				ProductSearchCriteria newCriteria = new ProductSearchCriteria();
				newCriteria.setDeparture(criteria.getDeparture());
				prodList = productService.searchProducts(newCriteria);
				if( prodList != null && prodList.size() != 0 )
					hasRecommendProd = true;
				else
					hasRecommendProd = false;
				model.addAttribute("hasRecomProd", hasRecommendProd);
			}
			model.addAttribute("productList", prodList);
			//搜索到的所有产品pid需传给详情页
			List<String> pids = new ArrayList<String>();
			for(ProductVO product : prodList){
				String pid = product.getPid();
				pids.add(pid);
			}
			redisClient.set(TokenHolder.get() + ":pids", pids, 60 * 60);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		if( hasProduct )
			return "product/front/list/productData";
		else
			return "product/front/list/noProduct";
	}


}
