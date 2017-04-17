package com.ztravel.product.back.freetravel.service;

import com.ztravel.product.back.freetravel.vo.CombinationCostPageVo;
import com.ztravel.product.back.freetravel.vo.CostPriceBean;
import com.ztravel.product.back.freetravel.vo.FlightSaveReqBean;
import com.ztravel.product.back.freetravel.vo.FlightSaveRespBean;
import com.ztravel.product.back.freetravel.vo.HotelSaveReqBean;
import com.ztravel.product.back.freetravel.vo.HotelSaveRespBean;
import com.ztravel.product.back.freetravel.vo.PackageCostPageVo;
import com.ztravel.product.back.freetravel.vo.SupplierCostAdditionSaveBean;
import com.ztravel.product.back.freetravel.vo.SupplierSaveBean;

/**
 * @author wanhaofan
 * */
public interface CostManageService {

	PackageCostPageVo initPackageView(String id) ;

	FlightSaveRespBean saveFlight(FlightSaveReqBean bean) ;

	FlightSaveRespBean getFlight(String id) ;

	Boolean deleteFlight(String id) ;

	Boolean deleteHotel(String id) ;

	HotelSaveRespBean saveHotel(HotelSaveReqBean bean) ;

	HotelSaveRespBean getHotel(String id) ;

	String removeCost(CostPriceBean bean) ;

	String addCost(CostPriceBean bean);

	String removeHotelCost(CostPriceBean bean) ;

	String addHotelCost(CostPriceBean bean);

	String removeFlightCost(CostPriceBean bean) ;

	String addFlightCost(CostPriceBean bean);

	Boolean saveCheck(SupplierSaveBean bean) ;

	Boolean saveCombinationCheck(SupplierSaveBean bean) ;

	CombinationCostPageVo initCombinationView(String id) ;
	
	Boolean saveAdditionCostSupplier(SupplierCostAdditionSaveBean bean) ;
	
	Boolean deleteSingleHotel(String id, String hotelId);

}
