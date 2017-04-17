package com.ztravel.product.back.freetravel.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.service.CostManageService;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.vo.CombinationCostPageVo;
import com.ztravel.product.back.freetravel.vo.CostPriceBean;
import com.ztravel.product.back.freetravel.vo.SupplierSaveBean;
import com.ztravel.product.back.hotel.service.IHotelService;


/**
 * @author wanhaofan
 * 底价维护
 * */
@Controller
@RequestMapping("/product/cost2")
public class CostManageCombinationController {

	@Resource
	private IHotelService hotelService;

	@Resource
	private CostManageService costManageService ;

	@Resource
	private ProductService productService ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping("/view/{id}")
	public String view(Model model, HttpServletRequest request,@PathVariable String id){
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));

	    CombinationCostPageVo vo = costManageService.initCombinationView(id) ;

	    model.addAttribute("cost", vo);
	    model.addAttribute("id", id);
	    model.addAttribute("mode", "view") ;
	    model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;

		return "product/back/cost_manage_combination" ;
	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, HttpServletRequest request,@PathVariable String id){
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
	    CombinationCostPageVo vo = costManageService.initCombinationView(id) ;

	    model.addAttribute("cost", vo);
	    model.addAttribute("id", id);
	    model.addAttribute("mode", "edit") ;
	    model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;

		return "product/back/cost_manage_combination" ;
	}

	@RequestMapping("/removeHotelCost")
	@ResponseBody
	public String removeHotelCost(@RequestBody CostPriceBean bean){
		return costManageService.removeHotelCost(bean) ;
	}

	@RequestMapping("/addHotelCost")
	@ResponseBody
	public String addCost(@RequestBody CostPriceBean bean){
		return costManageService.addHotelCost(bean) ;
	}

	@RequestMapping("/removeFlightCost")
	@ResponseBody
	public String removeFlightCost(@RequestBody CostPriceBean bean){
		return costManageService.removeFlightCost(bean) ;
	}

	@RequestMapping("/addFlightCost")
	@ResponseBody
	public String addFlightCost(@RequestBody CostPriceBean bean){
		return costManageService.addFlightCost(bean) ;
	}

	@RequestMapping("/saveCombinationCheck")
	@ResponseBody
	public Boolean saveCombinationCheck(@RequestBody SupplierSaveBean bean){
		return costManageService.saveCombinationCheck(bean) ;
	}

}
