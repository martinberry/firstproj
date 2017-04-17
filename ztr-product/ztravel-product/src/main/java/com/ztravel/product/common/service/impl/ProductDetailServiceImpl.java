package com.ztravel.product.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.Const;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.LanguageType;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Flight;
import com.ztravel.product.back.freetravel.entity.FlightInfo;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.entity.Trip;
import com.ztravel.product.back.freetravel.enums.TravelTipEnum;
import com.ztravel.product.back.freetravel.utils.Sorter;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.enums.HotelRate;
import com.ztravel.product.client.wo.FlightInfoWo;
import com.ztravel.product.common.dao.IProductDetailDao;
import com.ztravel.product.common.service.ProductDetailService;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.front.wo.CalendarDataWo;
import com.ztravel.product.front.wo.DayWo;
import com.ztravel.product.front.wo.FHTipWo;
import com.ztravel.product.front.wo.FlightInfoCalWo;
import com.ztravel.product.front.wo.FlightWo;
import com.ztravel.product.front.wo.HotelInfo;
import com.ztravel.product.front.wo.HotelWo;
import com.ztravel.product.front.wo.ProductWo;
import com.ztravel.product.front.wo.TripWo;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.weixin.wo.PriceTabDataWo;
import com.ztravel.product.weixin.wo.WxDayWo;

/**
 *
 * @author tengmeilin
 *
 */
@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	private static Logger logger = RequestIdentityLogger.getLogger(ProductDetailServiceImpl.class);

	@Resource
	private IProductDetailDao productDetailDaoImpl;

	@Resource
	private IUnVisaProductDao UnvisaProductDaoImpl;
	

	@Override
	public ProductWo getProductByPid(String productPid) throws Exception {
		Product product = productDetailDaoImpl.getProductByPid(productPid);
		ProductWo productWo = convertFromProduct(product);
		return productWo;
	}
	
	@Override
	public ProductWo getUnvisaProductByPid(String productPid)throws Exception{
		UnVisaProduct pieceproduct=UnvisaProductDaoImpl.getUnvisaProductByPid(productPid);
		ProductWo productWo=convertFromPieceProduct(pieceproduct); 
		return productWo;
	}
	
	
	

	@Override
	public ProductWo getProductById(String productId) throws Exception {
		Product product = productDetailDaoImpl.getProductById(productId);
		ProductWo productWo = convertFromProduct(product);
		return productWo;
	}

	private ProductWo convertFromProduct(Product product) throws Exception {

		ProductWo productWo = new ProductWo();

		if (product.getId() != null) {
			productWo.setId(product.getId().toString());
		}
		productWo.setNature(product.getNature().name());
		productWo.setPid(product.getPid());
		productWo.setpName(product.getpName());
		productWo.setSubName(product.getSubName());
		productWo.setTheme(product.getTheme());
		productWo.setTripDays(product.getTripDays());
		productWo.setTripNights(product.getTripNights());
		productWo.setTags(product.getTags());
		productWo.setFrom(product.getFrom());
		productWo.setTo(product.getTo());
		productWo.setToCountry(product.getToCountry());
		productWo.setHighLights(product.getHighLights());
		productWo.setHighLightTitles(product.getHighLightTitles());
		productWo.setLightColor(product.getLightColor());
		productWo.setImages(product.getImages());
		productWo.setTitleImages(product.getDetailTitleImages());

		setLowestPriceAndMarketPrice(product, productWo);

		if (product.getFlight() != null) {
			// 转换航班信息
			setFlightForProduct(productWo, product.getFlight());
		}

		if (CollectionUtils.isNotEmpty(product.getHotels())) {
			// 转换酒店信息
			setHotelsForProduct(productWo, product.getHotels(), product.getTripNights());
		}

		if (CollectionUtils.isNotEmpty(product.getRecommendTrips())) {
			// 转换行程推荐
			setTripsForProduct(productWo, product.getRecommendTrips());
		}

		if (product.getAdditionalInfos() != null && !product.getAdditionalInfos().isEmpty()) {
			// 转换附加信息, 包含费用说明和预订须知
			setAdditionalInfosForProduct(productWo, product.getAdditionalInfos());
		}
		if (product.getTravelTips() != null && !product.getTravelTips().isEmpty()) {
			Map<String, String> tips = new HashMap<String, String>();
			for (TravelTipEnum key : product.getTravelTips().keySet()) {
				tips.put(key.toString(), product.getTravelTips().get(key));
			}
			productWo.setTravelTips(tips);
		}
		return productWo;

	}

	private ProductWo convertFromPieceProduct(UnVisaProduct product){
		ProductWo productWo = new ProductWo();
		if (product.getId() != null) {
			productWo.setId(product.getId().toString());
		}
		productWo.setPid(product.getPid());
		productWo.setpName(product.getBasicInfo().getPname());
		productWo.setTo(product.getBasicInfo().getToCity());
		productWo.setToCountry(product.getBasicInfo().getToCountry());
		productWo.setImages(product.getDetailInfo().getImages());
		setCostPriceForPieceProduct(productWo,product.getPriceInfos());	 
		productWo.setLanguage(LanguageType.valueOf(product.getDetailInfo().getLanguageType()).getDescription());
        productWo.setServiceTime(product.getDetailInfo().getServiceTime());
        productWo.setPieceType(product.getBasicInfo().getType().getDesc());
        setPrice(productWo,product.getPriceInfos());
        productWo.setProductNature(product.getNature().getName());
		if (product.getAdditionalInfos() != null && !product.getAdditionalInfos().isEmpty()) {
			// 转换附加信息, 包含费用说明和预订须知
			setAdditionalInfosForProduct(productWo, product.getAdditionalInfos());
		}
		return productWo;
		
		
	}
	
	
	private void setLowestPriceAndMarketPrice(Product product, ProductWo productWo) {
		Map<String, Day> calMap = product.getCalendar();
		List<DayWo> dayWoList = Lists.newArrayList();
		Double lowestPrice = Double.MAX_VALUE;
		DayWo lowestPriceDayWo = new DayWo();
		if(calMap != null && calMap.size() >0){
			dayWoList = getSortedDayWoListByCalendarMap(calMap);
		}
		Double adultPrice = 0D;
		for (DayWo dayWo : dayWoList) {
			adultPrice = dayWo.getAdultPrice();
			if (adultPrice != null) {
				if(adultPrice < lowestPrice){
					lowestPrice = adultPrice;
					lowestPriceDayWo = dayWo;
				}
			}
		}
		if(lowestPriceDayWo != null){
			lowestPrice = lowestPriceDayWo.getAdultPrice();
			if( lowestPrice !=null){
				productWo.setLowestPrice(lowestPrice);
			}
			Double marketPrice = lowestPriceDayWo.getMarketPrice();
			if(marketPrice !=null){
				productWo.setMarketPrice(marketPrice);
			}
		}
	}

	private List<DayWo> getSortedDayWoListByCalendarMap(Map<String, Day> calMap){
		List<DayWo> dayWoList = Lists.newArrayList();
		for (String key : calMap.keySet()) {
			Day dbDay = calMap.get(key);
			Integer addD = 0;
			String addH = "00:00";
			if (dbDay.getSale() != null && dbDay.getSale().getInAdvanceDays() != null) {
				addD = dbDay.getSale().getInAdvanceDays();
			}
			if (dbDay.getSale() != null && dbDay.getSale().getInAdvanceHours() != null) {
				addH = dbDay.getSale().getInAdvanceHours();
			}

            DateTime now = DateTime.parse(key);
			DateTime timeLine = DateTime.parse(key + "T" + addH);
			timeLine = timeLine.minusDays(addD);
			DayWo dayWo = new DayWo();

			if (DateTime.now().isBefore(now)) {
                if (DateTime.now().isBefore(timeLine.getMillis() + 1)) {
                    dayWo = buildDayWo(dbDay, true);
                    if (dayWo != null && StringUtils.isNotEmpty(dayWo.getDay())) {
                        dayWoList.add(dayWo);
                    }
                }
            }
		}
		if(!dayWoList.isEmpty()){
			Collections.sort(dayWoList);
		}
		return dayWoList;
	}


	private void setAdditionalInfosForProduct(ProductWo productWo, Map<AdditionalRule, String> additionalInfos) {
		Map<String, String> infos = new HashMap<>();
		if (additionalInfos != null && additionalInfos.keySet() != null) {
			Iterator<AdditionalRule> it = additionalInfos.keySet().iterator();
			while (it.hasNext()) {
				AdditionalRule rule = it.next();
				infos.put(rule.toString(), additionalInfos.get(rule));
			}
		}
		productWo.setAdditionalInfos(infos);
	}
	
	
	private void setCostPriceForPieceProduct(ProductWo productWo,List<PriceInfo> priceInfo){
		List<String> costPriceId=new ArrayList<String>();
		List<String> costPrice=new ArrayList<String>();
		for(PriceInfo tmp:priceInfo){
			if(tmp!=null){
				costPrice.add(tmp.getName());
				costPriceId.add(tmp.getId());
			}	
		}
		productWo.setCostPrice(costPrice);
		productWo.setCostPriceId(costPriceId);
	}
	
	
	private void setPrice(ProductWo productWo,List<PriceInfo> priceInfo){
		List<Double> adultPrice=new ArrayList<Double>();
		List<Double> childPrice=new ArrayList<Double>();
		for(PriceInfo tmp:priceInfo){
			if(tmp!=null){
				if(tmp.getAdultPrice()!=null&&tmp.getAdultPrice()>0){
					adultPrice.add(tmp.getAdultPrice());
					childPrice.add(tmp.getChildPrice());
				}else{
					int exludeindex=adultPrice.size();
					productWo.getCostPrice().remove(exludeindex);
					productWo.getCostPriceId().remove(exludeindex);
				}				
			}		
		}
		productWo.setAdultPrice(adultPrice);
		productWo.setChildPrice(childPrice);
		
	}
	

	private void setTripsForProduct(ProductWo productWo, List<Trip> trips) {

		List<TripWo> tripsWo = new ArrayList<TripWo>();
		for (Trip trip : trips) {
			TripWo tripWo = new TripWo();
			tripWo.setTitle(trip.getTitle());
			tripWo.setDesItems(trip.getDesItems());
			tripWo.setContent(trip.getContent());
			tripWo.setBreakfest(trip.getBreakfest());
			tripWo.setLunch(trip.getLunch());
			tripWo.setDinner(trip.getDinner());
			tripWo.setIndex(trip.getIndex());
			if (productWo != null && productWo.getHotels() != null) {
				for (HotelWo hotel : productWo.getHotels()) {
					for (Integer i : hotel.getCheckinDays()) {
						if (trip.getIndex().equals(i)) {
							HotelInfo hotelInfo = new HotelInfo();
							hotelInfo.setHotelNameCn(hotel.getHotelNameCn());
							hotelInfo.setHotelNameEn(hotel.getHotelNameEn());
							hotelInfo.setName(hotel.getName());
							hotelInfo.setRate(hotel.getRate());
							tripWo.setHotelInfo(hotelInfo);
						}
					}
				}
			}
			tripsWo.add(tripWo);
		}
		productWo.setRecommendTrips(tripsWo);

	}

	private void setHotelsForProduct(ProductWo productWo, List<Hotel> hotels, Integer tripNights) throws Exception {

		List<HotelWo> hotelWos = new ArrayList<HotelWo>();
		for (Hotel hotel : hotels) {
			HotelWo hotelWo = new HotelWo();

			if (StringUtils.isNotBlank(hotel.getId())) {
				HotelEntity hotelEntity = new HotelEntity();
				try {
					hotelEntity = productDetailDaoImpl.getHotelById(hotel.getId());
				} catch (Exception e) {
					logger.error("failed to get hotel", e);
					throw ZtrBizException.instance(Const.FO_PROD_CODE_1002, Const.FO_PROD_REASON_1002);
				}
				hotelWo.setId(hotel.getId());
				hotelWo.setHotelNameCn(hotelEntity.getHotelNameCn());
				hotelWo.setHotelNameEn(hotelEntity.getHotelNameEn());
				hotelWo.setName(hotelEntity.getHotelNameCn() + ' ' + hotelEntity.getHotelNameEn());
				hotelWo.setPhone(hotelEntity.getPhone());
				hotelWo.setPictureIds(hotelEntity.getPictureIds());
				hotelWo.setAddress(hotelEntity.getAddress());
				if (hotelEntity.getRating() != null) {
					hotelWo.setRate(hotelEntity.getRating().getNum());
				}
				if (hotelEntity.getType() != null) {
					hotelWo.setHoteltype(hotelEntity.getType().getDesc());
				}
				hotelWo.setActivityFacilities(hotelEntity.getActivityFacilities());
				hotelWo.setCateringFacilities(hotelEntity.getCateringFacilities());
				hotelWo.setCompFacilities(hotelEntity.getCompFacilities());
				hotelWo.setNetworkFacilities(hotelEntity.getNetworkFacilities());
				hotelWo.setServiceFacilities(hotelEntity.getServiceFacilities());
				hotelWo.setDescribe(hotelEntity.getDescribe());
				hotelWo.setNotes(hotelEntity.getNotes());
			}

			if (hotel.getBedType() != null) {
				hotelWo.setBedType(hotel.getBedType().getDesc());
			}
			if (hotel.getBreakFestType() != null) {
				hotelWo.setBreakFestType(hotel.getBreakFestType().getDesc());
			}
			List<Integer> checkinDays = hotel.getCheckinDays() == null ? new ArrayList<Integer>() : hotel.getCheckinDays();
			hotelWo.setCheckinDays(checkinDays);
			if (tripNights.intValue() == checkinDays.size()) {
				hotelWo.setCheckinDaysStr("全程");
			} else {
				hotelWo.setCheckinDaysStr(checkinDays == null ? "" : checkinDays.toString().replace("[", "").replace("]", "").replace(",", "·")
						.replace(" ", ""));
			}
			hotelWo.setHighLights(hotel.getHighLights());
			hotelWo.setRoomType(hotel.getRoomType());

			hotelWos.add(hotelWo);
		}
		productWo.setHotels(hotelWos);

	}

	private static void setFlightForProduct(ProductWo productWo, Flight flight) {

		List<FlightInfoWo> go = new ArrayList<FlightInfoWo>();
		List<FlightInfoWo> middle = new ArrayList<FlightInfoWo>();
		List<FlightInfoWo> back = new ArrayList<FlightInfoWo>();
		Sorter sorter = new Sorter();

		FlightWo flightWo = new FlightWo();
		List<FlightInfoWo> infos = convertFromFlightInfo(flight);
		Iterator<FlightInfoWo> iter = infos.iterator();
		while (iter.hasNext()) {
			FlightInfoWo info = iter.next();
			switch (info.getAirRange()) {
			case "去程":
				go.add(info);
				break;
			case "返程":
				back.add(info);
				break;
			case "中间程":
				middle.add(info);
				break;
			default:
				break;
			}
		}
		Collections.sort(go, sorter);
		Collections.sort(back, sorter);
		Collections.sort(middle, sorter);

		List<FlightInfoWo> main = new ArrayList<FlightInfoWo>();
		// 将去程, 中间程, 返程结合到main中, 并将中间程按程分开放到map中
		Map<String, List<FlightInfoWo>> mrange = combineFlightInfo(go, back, middle, main);

		flightWo.setMiddle(mrange);
		flightWo.setBack(back);
		flightWo.setGo(go);
		flightWo.setGoNum(go.size());
		flightWo.setBackNum(back.size());
		flightWo.setMiddleNum(middle.size());
		flightWo.setMain(main);
		flightWo.setAirRangeRemark(flight.getAirRangeRemark());

		productWo.setFlight(flightWo);

	}

	private static Map<String, List<FlightInfoWo>> combineFlightInfo(List<FlightInfoWo> go, List<FlightInfoWo> back, List<FlightInfoWo> middle,
			List<FlightInfoWo> main) {

		Map<String, List<FlightInfoWo>> mrange = new LinkedHashMap<String, List<FlightInfoWo>>();

		if (go != null && go.size() > 0) {
			for (FlightInfoWo f : go) {
				if (f.getAirRangeIndex() % 1000 == 1) {
					main.add(f);
				} else {
					FlightInfoWo fi = f;
					fi.setAirRange(f.getAirRange() + "-中转");
					main.add(fi);
				}
			}
		}

		for (FlightInfoWo f : middle) {
			String index = String.valueOf(f.getAirRangeIndex() / 1000 + 1);
			if (mrange.get(index) != null) {
				mrange.get(index).add(f);
				FlightInfoWo fi = f;
				fi.setAirRange("第" + index + "程" + "-中转");
				main.add(fi);
			} else {
				List<FlightInfoWo> tmp = new ArrayList<FlightInfoWo>();
				tmp.add(f);
				mrange.put(index, tmp);
				FlightInfoWo fi = f;
				fi.setAirRange("第" + index + "程");
				main.add(fi);
			}
		}

		if (back != null && back.size() > 0) {
			for (FlightInfoWo f : back) {
				if (f.getAirRangeIndex() % 1000 == 1) {
					main.add(f);
				} else {
					FlightInfoWo fi = f;
					fi.setAirRange(f.getAirRange() + "-中转");
					main.add(fi);
				}
			}
		}

		return mrange;

	}

	private static List<FlightInfoWo> convertFromFlightInfo(Flight flight) {

		List<FlightInfoWo> infos = new ArrayList<>();

		if (flight != null && flight.getInfos() != null) {
			for (FlightInfo info : flight.getInfos()) {
				FlightInfoWo infoWo = new FlightInfoWo();
				infoWo.setOffsetDays(info.getOffsetDays());
				infoWo.setAirRange(info.getAirRange().getDesc());
				infoWo.setFlightNo(info.getFlightNo());
				infoWo.setCabin(info.getCabin());
				infoWo.setFlightModel(info.getFlightModel());
				infoWo.setAirLine(info.getAirLine());
				infoWo.setFromCity(info.getFromCity());
				infoWo.setFromAirPort(info.getFromAirPort());
				infoWo.setDepartureTime(info.getDepartureTime());
				infoWo.setToCity(info.getToCity());
				infoWo.setToAirPort(info.getToAirPort());
				infoWo.setArrivalTime(info.getArrivalTime());
				infoWo.setStop(info.getStop());
				infoWo.setAirRangeIndex(info.getAirRangeIndex());
				infoWo.setAddDays(info.getAddDays());
				infos.add(infoWo);
			}
		}

		return infos;

	}

	@Override
	public CalendarDataWo getCalDataById(String id) throws Exception {
		Product product = productDetailDaoImpl.getProductById(id);
		return getCalData(product);
	}

	@Override
	public CalendarDataWo getCalDataByPid(String pid) throws Exception {
		Product product = productDetailDaoImpl.getProductByPid(pid);
		return getCalData(product);
	}

	@Override
	public PriceTabDataWo getTabDataByPid(String pid) throws Exception {
		Product product = productDetailDaoImpl.getProductByPid(pid);

		if (product == null)
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002);
		PriceTabDataWo data = new PriceTabDataWo();
		if (product.getCalendar() != null && !product.getCalendar().isEmpty()) {
			Map<String, DayWo> orderedMap = new TreeMap<String, DayWo>();
			Map<String, Day> calMap = product.getCalendar();

			for (String key : calMap.keySet()) {
				Day dbDay = calMap.get(key);
				Integer addD = 0;
				String addH = "00:00";
				if (dbDay.getSale() != null && dbDay.getSale().getInAdvanceDays() != null) {
					addD = dbDay.getSale().getInAdvanceDays();
				}
				if (dbDay.getSale() != null && dbDay.getSale().getInAdvanceHours() != null) {
					addH = dbDay.getSale().getInAdvanceHours();
				}
				DateTime now = DateTime.parse(key);
				DateTime timeLine = DateTime.parse(key + "T" + addH);
				timeLine = timeLine.minusDays(addD);
				if (DateTime.now().isBefore(now)) {
				    if (DateTime.now().isBefore(timeLine.getMillis() + 1)) {
	                    orderedMap.put(key.replace("-", "/"), buildDayWo(dbDay, true));
	                }
				}
			}
			data.setCalendar(orderedMap);

			// 设置最低成人价格 最高市场价格
			Double highestMarketPrice = new Double(0);
			for (DayWo value : orderedMap.values()) {
				if (value.getMarketPrice() != null) {
					if (value.getMarketPrice().doubleValue() > highestMarketPrice.doubleValue()) {
						highestMarketPrice = value.getMarketPrice();
					}
				}
			}
			data.setLowestAdultPrice(product.getLowestPrice());
			data.setHighestMarketPrice(highestMarketPrice);
		}
		return data;
	}

	private CalendarDataWo getCalData(Product product) throws Exception {
		if (product == null)
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002);
		CalendarDataWo data = new CalendarDataWo();
		if (product.getCalendar() != null && !product.getCalendar().isEmpty()) {
			Map<String, DayWo> orderedMap = new TreeMap<String, DayWo>();
			Map<String, Day> calMap = product.getCalendar();
			DateTime firstDayOfNow = DateTime.now().minusDays(DateTime.now().getDayOfMonth() - 1);
			DateTime lastMonth = firstDayOfNow.minusMonths(1);
			DateTime next11Month = firstDayOfNow.plusMonths(12).minusDays(1);
			for (String key : calMap.keySet()) {
				DateTime keyDt = DateTime.parse(key);
				if (keyDt.isAfter(next11Month) || keyDt.isBefore(lastMonth)) {
					continue;
				}
				Day dbDay = calMap.get(key);
				Integer addD = 0;
				String addH = "00:00";
				int stock = -1;
				if (dbDay.getSale() != null) {
					if (dbDay.getSale().getInAdvanceDays() != null) {
						addD = dbDay.getSale().getInAdvanceDays();
					}
					if (dbDay.getSale().getInAdvanceHours() != null) {
						addH = dbDay.getSale().getInAdvanceHours();
					}

					if (dbDay.getSale().getStock() == 0) {
						stock = 0;
					}
				}
				DateTime now = DateTime.parse(key);
				DateTime timeLine = DateTime.parse(key + "T" + addH);
				timeLine = timeLine.minusDays(addD);
				if (DateTime.now().isBefore(now)) {
					if (DateTime.now().isBefore(timeLine.getMillis() + 1)) {
						orderedMap.put(key.replace("-", "/"), buildDayWo(dbDay, true));
					} else {
						orderedMap.put(key.replace("-", "/"), buildDayWo(dbDay, false));
					}
				} else {
					if (stock == 0) {
						orderedMap.put(key.replace("-", "/"), buildDayWo(dbDay, false));
					}
				}

			}
			data.setCalendar(orderedMap);

			// 设置最高市场价格(取最低成人价当天最近的市场价) modify by xutian 2016/01/13
			data.setLowestAdultPrice(product.getLowestPrice());
			setPrice(orderedMap, data);

		}
		data.setFhTips(buildFHTips(product));
		return data;
	}

	private void setPrice(Map<String, DayWo> orderedMap, CalendarDataWo data) {
		Double highestMarketPrice = new Double(0);
        for(DayWo value: orderedMap.values()){
            if (data.getLowestAdultPrice().equals(value.getAdultPrice()) && value.getMarketPrice() != null) {
                highestMarketPrice = value.getMarketPrice();
                break ;
            }
		}
		data.setHighestMarketPrice(highestMarketPrice);
	}

	private DayWo buildDayWo(Day day, boolean isBefore) {
		DayWo wo = new DayWo();
		if (day.getSale() != null && !(day.getSale().isClosed())) {
			wo.setAdultPrice(day.getSale().getAdultPrice());
			wo.setChildPrice(day.getSale().getChildPrice());
			wo.setInAdvanceDays(day.getSale().getInAdvanceDays());
			wo.setInAdvanceHours(day.getSale().getInAdvanceHours());
			wo.setSaleStatus(day.getSale().getSaleStatus());
			wo.setMarketPrice(day.getSale().getMarketPrice());
			// wo.setSaleUnit(day.getSale().getSaleUnit().toString());
			wo.setSingleRoomPrice(day.getSale().getSingleRoomPrice());
			wo.setStock(day.getSale().getStock());
			wo.setUsedStock(day.getSale().getUsedStock());
			wo.setHasChildPrice(day.getSale().getHasChildPrice());
			wo.setBefore(isBefore);
			wo.setSalesPackages(day.getSale().getSalesPackages());
			wo.setDay(day.getDay());
		}

		return wo;
	}

	private List<FHTipWo> buildFHTips(Product product) {
		List<FHTipWo> calTips = new LinkedList<FHTipWo>();

		int tripDays = product.getTripDays() == null ? 0 : product.getTripDays();
		int tripNights = product.getTripNights() == null ? 0 : product.getTripNights();
		int tripNums = tripDays > tripNights ? tripDays : tripNights;

		for (int i = 0; i < tripNums; i++) {
			FHTipWo wo = new FHTipWo();
			wo.setFlightInfos(new LinkedList<FlightInfoCalWo>());
			calTips.add(wo);
		}

		if (CollectionUtils.isNotEmpty(product.getHotels())) {
			for (Hotel h : product.getHotels()) {
				HotelEntity hotelEntity = new HotelEntity();
				try {
					hotelEntity = productDetailDaoImpl.getHotelById(h.getId());
				} catch (Exception e) {
					logger.error("failed to get hotel", e);
					throw ZtrBizException.instance(Const.FO_PROD_CODE_1002, Const.FO_PROD_REASON_1002);
				}
				if (CollectionUtils.isEmpty(h.getCheckinDays()))
					continue;
				for (Integer night : h.getCheckinDays()) {
					if (night == null || night == 0)
						continue;
					calTips.get(night - 1).setHotelInfo(buildHotelInfo(hotelEntity));
				}
			}
		}

		if (product.getFlight() != null && CollectionUtils.isNotEmpty(product.getFlight().getInfos())) {
			for (FlightInfo fi : product.getFlight().getInfos()) {
				int offsetDay = fi.getOffsetDays() == null ? 0 : fi.getOffsetDays();
				if (offsetDay <= calTips.size()) {
					calTips.get(offsetDay - 1).getFlightInfos().add(buildFlightInfo(fi));
				}
			}
		}

		return calTips;
	}

	private HotelInfo buildHotelInfo(HotelEntity he) {
		HotelInfo hi = new HotelInfo();
		hi.setHotelNameCn(StringUtils.isNotBlank(he.getHotelNameCn()) ? he.getHotelNameCn() : "未知");
		hi.setHotelNameEn(StringUtils.isNotBlank(he.getHotelNameEn()) ? he.getHotelNameEn() : "unknow");
		hi.setRate(he.getRating() == null ? HotelRate.TWO_STAR.getNum() : he.getRating().getNum());
		return hi;
	}

	private FlightInfoCalWo buildFlightInfo(FlightInfo fi) {
		FlightInfoCalWo wo = new FlightInfoCalWo();
		wo.setArrivalTime(StringUtils.isNotBlank(fi.getArrivalTime()) ? fi.getArrivalTime() : "未知");
		wo.setDepartureTime(StringUtils.isNotBlank(fi.getDepartureTime()) ? fi.getDepartureTime() : "未知");
		wo.setFlightNo(StringUtils.isNotBlank(fi.getFlightNo()) ? fi.getFlightNo() : "未知");
		wo.setFromCity(StringUtils.isNotBlank(fi.getFromCity()) ? fi.getFromCity() : "未知");
		wo.setToCity(StringUtils.isNotBlank(fi.getToCity()) ? fi.getToCity() : "未知");
		return wo;
	}

	public List<WxDayWo> getWxDayListByPid(String pid) throws Exception {
		List<WxDayWo> wxDayWoList = Lists.newArrayList();
		Map<String, Day> calendarMap = getCalendarMap(pid);
		WxDayWo wxDayWo = new WxDayWo();
		DateTime firstDayOfNow = DateTime.now().minusDays(DateTime.now().getDayOfMonth() - 1);
        DateTime lastMonth = firstDayOfNow.minusMonths(1);
        DateTime next11Month = firstDayOfNow.plusMonths(12).minusDays(1);
		for (String key : calendarMap.keySet()) {
            DateTime keyDt = DateTime.parse(key);
			if (keyDt.isAfter(next11Month) || keyDt.isBefore(lastMonth)) {
                continue;
            }
            Day dbDay = calendarMap.get(key);
			Integer addD = 0;
			String addH = "00:00";
			if (dbDay.getSale() != null && dbDay.getSale().getInAdvanceDays() != null) {
				addD = dbDay.getSale().getInAdvanceDays();
			}
			if (dbDay.getSale() != null && dbDay.getSale().getInAdvanceHours() != null) {
				addH = dbDay.getSale().getInAdvanceHours();
			}
			DateTime now = DateTime.parse(key);
			DateTime timeLine = DateTime.parse(key + "T" + addH);
			timeLine = timeLine.minusDays(addD);
			 if (DateTime.now().isBefore(now)) {
			     if (DateTime.now().isBefore(timeLine.getMillis() + 1)) {
			         wxDayWo = buildWxDayWo(dbDay, true);
			         if (wxDayWo != null && StringUtils.isNotEmpty(wxDayWo.getDateWithYear())) {
			             wxDayWoList.add(wxDayWo);
			         }
			     } else if (dbDay.getSale() != null && dbDay.getSale().getStock() == 0) {
	                 wxDayWo = buildWxDayWo(dbDay, false);
	                 if (wxDayWo != null && StringUtils.isNotEmpty(wxDayWo.getDateWithYear())) {
	                     wxDayWoList.add(wxDayWo);
	                 }
	             }
			 } else if (dbDay.getSale() != null && dbDay.getSale().getStock() == 0) {
		         wxDayWo = buildWxDayWo(dbDay, false);
                 if (wxDayWo != null && StringUtils.isNotEmpty(wxDayWo.getDateWithYear())) {
                     wxDayWoList.add(wxDayWo);
                 }
		     }

		}

		Collections.sort(wxDayWoList);
		return wxDayWoList;
	}

	private WxDayWo buildWxDayWo(Day day, boolean isBefore) throws Exception {
		WxDayWo wo = new WxDayWo();
		if (day.getSale() != null && !(day.getSale().isClosed())) {
			Sale sale = day.getSale();
			wo.setMarketPrice(sale.getMarketPrice());
			if (sale.getStock() != null) {
				wo.setAvailableStock(sale.getStock());
			} else {
				wo.setAvailableStock(0);
			}

			if (sale.getSalesPackages() != null && sale.getSalesPackages().size() > 0) {
			    wo.setContainsPackage(true);
			} else {
			    wo.setContainsPackage(false);
			}

			wo.setAdultPrice(sale.getAdultPrice());
			wo.setChildPrice(sale.getChildPrice());
			wo.setSaleStatus(sale.getSaleStatus());
			wo.setSingleRoomPrice(sale.getSingleRoomPrice());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
			SimpleDateFormat sdfMonthAndDay = new SimpleDateFormat("M月dd日");
			Date date = sdf.parse(day.getDay());
			SimpleDateFormat dateFm = new SimpleDateFormat("EEEE",Locale.CHINESE);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			wo.setDateStr(sdfMonthAndDay.format(date));
            wo.setYear(sdfYear.format(date));
			wo.setDay(Integer.parseInt(sdfDay.format(date)));
			wo.setMonth(cal.get(Calendar.MONTH) + 1);
			wo.setWeek(dateFm.format(date));
			wo.setDateWithYear(day.getDay());
		}
		return wo;
	}

	@Override
	public Map<String, List<WxDayWo>> getWxDayMapByPid(String pid) throws Exception {
		Map<String, List<WxDayWo>> wxDayMap = Maps.newHashMap();
		List<WxDayWo> wxDayWoList = getWxDayListByPid(pid);
		if (!wxDayWoList.isEmpty()) {
			wxDayMap = convertWxDayWoList2Map(wxDayWoList);
		}
		return wxDayMap;
	}

	private Map<String, List<WxDayWo>> convertWxDayWoList2Map(List<WxDayWo> wxDayWoList) throws Exception {
		Map<String, List<WxDayWo>> wxDayWoMap = Maps.newTreeMap();
		for (int i = 0; i < wxDayWoList.size(); i++) {
			WxDayWo wxDayWo = wxDayWoList.get(i);
			Integer month = wxDayWo.getMonth();
			String year = wxDayWo.getYear();
			if (null != month) {
				String monthKey = getMonthKeyByMonth(month);
				String yearMonth = year + monthKey;
				if (StringUtils.isNotEmpty(yearMonth)) {
					if (!wxDayWoMap.containsKey(yearMonth)) {
						List<WxDayWo> wxDayWoTmpList = Lists.newArrayList();
						wxDayWoTmpList.add(wxDayWo);
						wxDayWoMap.put(yearMonth, wxDayWoTmpList);
					} else {
						List<WxDayWo> wxDayWoTmpList = wxDayWoMap.get(yearMonth);
						wxDayWoTmpList.add(wxDayWo);
					}
				}
			}
		}
		return wxDayWoMap;
	}

	private String getMonthKeyByMonth(Integer month) throws Exception {
		String monthKey = "";
		if (month >= 1 && month < 10) {
			monthKey = "0" + month;
		} else if (month >= 10 && month <= 12) {
			monthKey = "" + month;
		}
		return monthKey;
	}

	@Override
	public List<SalesPackage> getPackageListByPidAndDate(String pid, String chooseDay) throws Exception {
		List<SalesPackage> salePackageList = Lists.newArrayList();
		Sale sale = getSaleByPidAndDate(pid, chooseDay);
		if(sale != null){
			salePackageList = sale.getSalesPackages();
			for(SalesPackage salePackage: salePackageList){
				salePackage.setSumNum(salePackage.getAdultNum()+salePackage.getChildrenNum());
			}
		}
		return salePackageList;
	}

	@Override
	public int getStockByPidAndDate(String pid, String chooseDay) throws Exception {
		int stock = 0;
		Sale sale = getSaleByPidAndDate(pid, chooseDay);
		if(sale != null){
			stock = sale.getStock();
		}
		return stock;
	}

	private Sale getSaleByPidAndDate(String pid, String date) throws Exception{
		Sale sale = null;
		Map<String, Day> calendarMap = getCalendarMap(pid);
		Day day = calendarMap.get(date);
		if (day != null) {
			 sale = day.getSale();
		}
		return sale;
	}

	private Map<String,Day> getCalendarMap(String pid) throws Exception{
		Map<String, Day> calendarMap = Maps.newHashMap();
		Product product = productDetailDaoImpl.getProductByPid(pid);
		if(product != null){
			calendarMap = product.getCalendar();
		}
		return calendarMap;
	}
}
