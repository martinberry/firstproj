package com.ztravel.product.back.freetravel.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import com.ztravel.operator.financeMantain.po.Supplier;
import com.ztravel.product.back.freetravel.entity.Cost;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Flight;
import com.ztravel.product.back.freetravel.entity.FlightInfo;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.enums.SaleStatus;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.freetravel.vo.CalendarAbstractSaleBean;
import com.ztravel.product.back.freetravel.vo.CostDayData;
import com.ztravel.product.back.freetravel.vo.CostFlightVo;
import com.ztravel.product.back.freetravel.vo.CostHotelDayData;
import com.ztravel.product.back.freetravel.vo.CostHotelMonthData;
import com.ztravel.product.back.freetravel.vo.CostHotelVo;
import com.ztravel.product.back.freetravel.vo.CostMonthData;
import com.ztravel.product.back.freetravel.vo.CostSupplierVo;
import com.ztravel.product.back.freetravel.vo.ProductVo;

/**
 * @author wanhaofan
 * */
public class Converter {
	
	public static Sale convertVo2Sale(CalendarAbstractSaleBean bean){
		Sale sale = new Sale() ;
		sale.setAdultPrice(bean.getAdultPrice());		
		sale.setAdultPriceTax(bean.getIsAdultPriceHasTax());		
		sale.setHasChildPrice(bean.getHasChildPrice()) ;		
		sale.setChildPrice(bean.getChildPrice());		
		sale.setChildPriceTax(bean.getIsChildPriceHasTax());
		sale.setSaleStatus(SaleStatus.RELEASED);
		sale.setInAdvanceDays(bean.getInAdvanceDays());
		sale.setInAdvanceHours(bean.getInAdvanceHours());
		sale.setMarketPrice(bean.getMarketPrice());
		sale.setSaleUnit(bean.getSaleUnit());		
		sale.setSingleRoomPrice(bean.getSingleRoomPrice());
		sale.setStock(bean.getStock());
		sale.setSalesPackages(bean.getSalesPackages());
		return sale ;
	}
	
	public static CostSupplierVo convertSupplier2Vo(Supplier supplier){
		CostSupplierVo svo = new CostSupplierVo() ;
		svo.setSupplierName(supplier.getSupplierName()) ;
		svo.setSupplierNameTransfer(supplier.getSupplierName().replaceAll(" ", "&nbsp;"));
		svo.setSupplierId(supplier.getSupplierId());
		return svo ;
	}

	public static List<ProductVo> convert2ProductVoList(List<Product> productList){
		List<ProductVo> productVoList = new ArrayList<ProductVo>();
		ProductVo productVo = new ProductVo();
		for(Product product:productList){
			productVo = convert2ProductVo(product);
			productVoList.add(productVo);
		}
		return productVoList;
	}

	private static ProductVo convert2ProductVo(Product p){
		ProductVo pv = new ProductVo();
		pv.setId(p.getId());
		if(null!=p.getCreatedTime()){
			pv.setCreatedTime(dateTime2String(p.getCreatedTime()));
		}
//
		pv.setFrom(p.getFrom());
		pv.setPid(p.getPid());
		pv.setpName(p.getpName());

		if(null!=p.getStatus()){
			if(Status.RELEASED.equals(p.getStatus())){
				pv.setStatus(Status.RELEASED.getDesc());
			}else if(Status.NEW.equals(p.getStatus())){
				pv.setStatus(Status.NEW.getDesc());
			}else if(Status.OFFLINE.equals(p.getStatus())){
				pv.setStatus(Status.OFFLINE.getDesc());
			}
			pv.setStatus(p.getStatus().toString());
		}
		pv.setTheme(p.getTheme());
		String to="";
		List<String> toStrList=p.getTo();
		if(null!=toStrList){
			to=listToString(toStrList);
		}
		pv.setTo(to);
		if(null!=p.getUpdatedTime()){
			pv.setUpdatedTime(dateTime2String(p.getUpdatedTime()));
		}

		return pv;
	}


	private static String dateTime2String(DateTime dateTime){
		return	dateTime.toString("yyyy-MM-dd HH:mm:ss") ;
	}



	private static String listToString(List<String> a){
	    String result="";
	    for(int i=0;i<a.size();i++){
	        if(i+1==a.size()){
	            result+=a.get(i);
	        }else{
	            result+=a.get(i)+" ";
	        }
	    }
        return result;
    }

	public static CostFlightVo convertFlight2Vo(Flight flight, String from, Sorter sorter){
		CostFlightVo flightVo = new CostFlightVo() ;
		List<FlightInfo> infos = flight.getInfos() ;
		Iterator<FlightInfo> iter = infos.iterator() ;
		List<FlightInfo> go = new ArrayList<FlightInfo>() ;
		List<FlightInfo> middle = new ArrayList<FlightInfo>() ;
		List<FlightInfo> back = new ArrayList<FlightInfo>() ;
		while(iter.hasNext()){
			FlightInfo info = iter.next() ;
			switch (info.getAirRange()) {
			case GO:
				go.add(info) ;
				break;
			case BACK:
				back.add(info) ;
				break;
			case MIDDLE:
				middle.add(info) ;
				break;
			default:
				break;
			}
		}
		Collections.sort(go, sorter);
		Collections.sort(back, sorter);
		Collections.sort(middle, sorter);
		Map<String, List<FlightInfo>> mrange = new LinkedHashMap<String,List<FlightInfo>>() ;
		for(FlightInfo f:middle){
			String index = String.valueOf(f.getAirRangeIndex() / 1000 + 1) ;
			if(mrange.get(index) != null){
				mrange.get(index).add(f) ;
			}else{
				List<FlightInfo> tmp = new ArrayList<FlightInfo>() ;
				tmp.add(f) ;
				mrange.put(index, tmp) ;
			}
		}

		flightVo.setMiddle(mrange);
		flightVo.setBack(back);
		flightVo.setGo(go);
		flightVo.setGoNum(go.size());
		flightVo.setBackNum(back.size());
		flightVo.setMiddleNum(middle.size());
		flightVo.setFrom(from);
		flightVo.setInnerRemark(flight.getInnerRemark());
		flightVo.setAirRangeRemark(flight.getAirRangeRemark());

		return flightVo ;
	}

	public static List<CostHotelVo> convertHotel2Vo(List<Hotel> hotels, Sorter sorter){
		List<CostHotelVo> hotelVos = new ArrayList<CostHotelVo>() ;
		for(Hotel hotel : hotels){
			CostHotelVo hotelVo = new CostHotelVo() ;
			hotelVo.setBedType(hotel.getBedType());
			hotelVo.setBedTypeCN(hotel.getBedType() == null ? "" : hotel.getBedType().getDesc());
			hotelVo.setBreakFestType(hotel.getBreakFestType());
			hotelVo.setBreakFestTypeCN(hotel.getBreakFestType() == null ? "" : hotel.getBreakFestType().getDesc());
			List<Integer> checkinDays = hotel.getCheckinDays() == null ? new ArrayList<Integer>() : hotel.getCheckinDays() ;
			Collections.sort(checkinDays, sorter);
			hotelVo.setCheckinDays(checkinDays);
			hotelVo.setCheckinDaysStr(checkinDays == null ? "" : checkinDays.toString().replace("[", "").replace("]", ""));
			hotelVo.setHighLights(hotel.getHighLights());
			hotelVo.setHotelRemark(hotel.getHotelRemark());
			hotelVo.setId(hotel.getId());
			hotelVo.setInnerRemark(hotel.getInnerRemark());
			hotelVo.setRoomType(hotel.getRoomType());
			CostSupplierVo svo = new CostSupplierVo() ;
			if(hotel.getSupplier() != null && !hotel.getSupplier().equals("")){
				svo.setSupplierId(Integer.valueOf(hotel.getSupplier()));
			}
			hotelVo.setSupplier(svo);
			hotelVos.add(hotelVo) ;
		}
		return hotelVos ;
	}

	public static List<CostMonthData> convertCalendar2Cost(Map<String, Day> calendar){
		if(calendar == null){
			return new ArrayList<CostMonthData>() ;
		}
		Iterator<Entry<String, Day>> iter = calendar.entrySet().iterator() ;
		List<CostMonthData> costs = new ArrayList<CostMonthData>() ;
		List<CostDayData> days = null ;
		while(iter.hasNext()){
			Entry<String, Day> entry = iter.next() ;
			Cost cost = entry.getValue().getCost() ;
			if(cost != null && cost.getPackageAdultCost() != null){
				List<Double> price = new ArrayList<Double>() ;
				price.add(cost.getPackageAdultCost()) ;
				price.add(cost.getPackageChildCost()) ;

				DateTime now = new DateTime(entry.getKey()) ;

				Integer day = now.getDayOfMonth() ;

				CostDayData dayData = new CostDayData() ;
				dayData.setDay(day);
				dayData.setPrice(price);

				CostMonthData data = new CostMonthData() ;
				data.setMonth(now.toString("yyyy-MM"));
				if(costs.contains(data)){
					CostMonthData old = costs.get(costs.indexOf(data)) ;
					old.getMonthData().add(dayData) ;
				}else{
					days = new ArrayList<CostDayData>() ;
					days.add(dayData) ;
					data.setMonthData(days);
					costs.add(data) ;
				}
			}
		}
		return costs;
	}

	public static List<CostMonthData> convertCalendar2FlightCost(Map<String, Day> calendar){
		if(calendar == null){
			return new ArrayList<CostMonthData>() ;
		}
		Iterator<Entry<String, Day>> iter = calendar.entrySet().iterator() ;
		List<CostMonthData> costs = new ArrayList<CostMonthData>() ;
		List<CostDayData> days = null ;
		while(iter.hasNext()){
			Entry<String, Day> entry = iter.next() ;
			Cost cost = entry.getValue().getCost() ;
			if(cost != null && cost.getFlightAdultCost() != null){
				List<Double> price = new ArrayList<Double>() ;
				price.add(cost.getFlightAdultCost()) ;
				price.add(cost.getFlightChildCost()) ;

				DateTime now = new DateTime(entry.getKey()) ;

				Integer day = now.getDayOfMonth() ;

				CostDayData dayData = new CostDayData() ;
				dayData.setDay(day);
				dayData.setPrice(price);

				CostMonthData data = new CostMonthData() ;
				data.setMonth(now.toString("yyyy-MM"));
				if(costs.contains(data)){
					CostMonthData old = costs.get(costs.indexOf(data)) ;
					old.getMonthData().add(dayData) ;
				}else{
					days = new ArrayList<CostDayData>() ;
					days.add(dayData) ;
					data.setMonthData(days);
					costs.add(data) ;
				}
			}
		}
		return costs;
	}

	public static List<CostHotelMonthData> convertCalendar2HotelCost(Map<String, Day> calendar){
		if(calendar == null){
			return new ArrayList<CostHotelMonthData>() ;
		}
		Iterator<Entry<String, Day>> iter = calendar.entrySet().iterator() ;
		List<CostHotelMonthData> costs = new ArrayList<CostHotelMonthData>() ;
		List<CostHotelDayData> days = null ;
		while(iter.hasNext()){
			Entry<String, Day> entry = iter.next() ;
			Cost cost = entry.getValue().getCost() ;
			String priceInfo = "" ;
			if(cost != null && cost.getHotelRoomCost() != null){
				Double[] prices = cost.getHotelRoomCost() ;
				if(prices != null){
					for(Double price : prices){
						priceInfo += price + "/" ;
					}
					priceInfo = priceInfo.length() > 0 ? priceInfo.substring(0, priceInfo.length() - 1) : priceInfo ;
				}

				DateTime now = new DateTime(entry.getKey()) ;

				Integer day = now.getDayOfMonth() ;

				CostHotelDayData dayData = new CostHotelDayData() ;
				dayData.setDay(day);
				dayData.setPrice(priceInfo);

				CostHotelMonthData data = new CostHotelMonthData() ;
				data.setMonth(now.toString("yyyy-MM"));
				if(costs.contains(data)){
					CostHotelMonthData old = costs.get(costs.indexOf(data)) ;
					old.getMonthData().add(dayData) ;
				}else{
					days = new ArrayList<CostHotelDayData>() ;
					days.add(dayData) ;
					data.setMonthData(days);
					costs.add(data) ;
				}
			}
		}
		return costs;
	}


}
