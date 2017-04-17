package com.ztravel.product.back.freetravel.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.product.back.freetravel.service.CostManageService;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.vo.CostPriceBean;
import com.ztravel.product.back.freetravel.vo.FlightSaveReqBean;
import com.ztravel.product.back.freetravel.vo.FlightSaveRespBean;
import com.ztravel.product.back.freetravel.vo.HotelSaveReqBean;
import com.ztravel.product.back.freetravel.vo.HotelSaveRespBean;
import com.ztravel.product.back.freetravel.vo.PackageCostPageVo;
import com.ztravel.product.back.freetravel.vo.SupplierCostAdditionSaveBean;
import com.ztravel.product.back.freetravel.vo.SupplierSaveBean;
import com.ztravel.product.back.hotel.convertor.HotelEntityConvertor;
import com.ztravel.product.back.hotel.entity.searchcriteria.HotelSearchCriteria;
import com.ztravel.product.back.hotel.service.IHotelService;
import com.ztravel.product.back.hotel.vo.HotelVO;


/**
 * @author wanhaofan
 * 底价维护
 * */
@Controller
@RequestMapping("/product/cost")
public class CostManagePackageController {

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

	    PackageCostPageVo vo = costManageService.initPackageView(id) ;

	    model.addAttribute("cost", vo);
	    model.addAttribute("id", id);
	    model.addAttribute("mode", "view") ;
	    model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;

		return "product/back/cost_manage_package" ;
	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, HttpServletRequest request,@PathVariable String id){
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));

	    PackageCostPageVo vo = costManageService.initPackageView(id) ;

	    model.addAttribute("cost", vo);
	    model.addAttribute("id", id);
	    model.addAttribute("mode", "edit") ;
	    model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;

		return "product/back/cost_manage_package" ;
	}

	@RequestMapping("/saveFlight")
	@ResponseBody
	public FlightSaveRespBean saveFlight(@RequestBody FlightSaveReqBean bean){
		return costManageService.saveFlight(bean) ;
	}

	@RequestMapping("/editFlight")
	@ResponseBody
	public FlightSaveRespBean editFlight(String id){
		return costManageService.getFlight(id) ;
	}

	@RequestMapping("/deleteFlight")
	@ResponseBody
	public Boolean deleteFlight(String id){
		return costManageService.deleteFlight(id) ;
	}

	@RequestMapping(value="/searchHotel",method=RequestMethod.POST)
	public String searchHotelResource(@RequestBody HotelSearchCriteria searchCriteria, Model model) throws Exception{
		//搜索条件格式校验，若校验失败，返回空
		searchCriteria.setStatus("complete");
		ValidResult validResult = BasicValidator.valid(searchCriteria);
		if( !validResult.isSuccess() ){
			return "product/back/hotel_table";
		}
		//查询数据库
		int totalNum = hotelService.countHotels(searchCriteria);
		List<HotelVO> hotelList = hotelService.searchHotels(searchCriteria);

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
		return "product/back/hotel_table";
	}

	@RequestMapping("/addHotel")
	@ResponseBody
	public HotelVO addHotel(String hotelId){
		return HotelEntityConvertor.convertEntityToVO(hotelService.getHotelById(hotelId)) ;
	}

	@RequestMapping("/deleteHotel")
	@ResponseBody
	public Boolean deleteHotel(String id){
		return costManageService.deleteHotel(id) ;
	}
	
	@RequestMapping("/deleteSingleHotel")
	@ResponseBody
	public Boolean deleteSingleHotel(String id, String hotelId){
		return costManageService.deleteSingleHotel(id, hotelId) ;
	}

	@RequestMapping("/saveHotel")
	@ResponseBody
	public HotelSaveRespBean hotel(@RequestBody HotelSaveReqBean bean){
		return costManageService.saveHotel(bean) ;
	}

	@RequestMapping("/editHotel")
	@ResponseBody
	public HotelSaveRespBean editHotel(String id){
		return costManageService.getHotel(id) ;
	}

	@RequestMapping("/removeCost")
	@ResponseBody
	public String removeCost(@RequestBody CostPriceBean bean){
		return costManageService.removeCost(bean) ;
	}

	@RequestMapping("/addCost")
	@ResponseBody
	public String addCost(@RequestBody CostPriceBean bean){
		return costManageService.addCost(bean) ;
	}

	@RequestMapping("/saveCheck")
	@ResponseBody
	public Boolean saveCheck(@RequestBody SupplierSaveBean bean){
		return costManageService.saveCheck(bean) ;
	}
	
	@RequestMapping("/saveAdditionalCostSupplier")
	@ResponseBody
	public Boolean saveAdditionalCostSupplier(@RequestBody SupplierCostAdditionSaveBean bean){
		return costManageService.saveAdditionCostSupplier(bean) ;
	}

}
