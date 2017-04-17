package com.ztravel.product.back.freetravel.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.product.back.freetravel.entity.Cost;
import com.ztravel.product.back.freetravel.entity.Flight;
import com.ztravel.product.back.freetravel.entity.Hotel;

/**
 * @author wanhaofan
 * */
public interface CostManageDao {
	Boolean saveFlight(String id, Flight flight) ;

	Flight getFlight(String id) ;

	Boolean deleteFlight(String id) ;

	Boolean deleteHotel(String id) ;

	Boolean saveHotel(String id, List<Hotel> hotels) ;


	Boolean removeCost(String id,Map<String, Cost> costs) ;

	Boolean addCost(String id,Map<String, Cost> costs) throws Exception ;

	Boolean removeFlightCost(String id,Map<String, Cost> costs) ;

	Boolean addFlightCost(String id,Map<String, Cost> costs) throws Exception ;

	Boolean addHotelCost(String id,Map<String, Cost> costs) throws Exception ;

	Boolean removeHotelCost(String id,Map<String, Cost> costs) ;


	Boolean saveCheck(String id,String supplier) ;

	Boolean saveCombinationCheck(String id,String flightSupplier,String hotelSupplier) ;
	
	Boolean saveAdditionCostSupplier(String id, String sid, Double cost, String type) ;
	
	Boolean deleteSingleHotel(String id, String hotelId);
	
}
