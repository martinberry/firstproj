package com.ztravel.product.client.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.DateUtils;
import com.ztravel.common.constants.Const;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.freetravel.entity.Flight;
import com.ztravel.product.back.freetravel.entity.FlightInfo;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.utils.Sorter;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.client.entity.ProductBasicDetail;
import com.ztravel.product.client.service.IProductDetailService;
import com.ztravel.product.client.wo.FlightInfoWo;
import com.ztravel.product.client.wo.OrderFlightWo;
import com.ztravel.product.client.wo.OrderHotelWo;
import com.ztravel.product.client.wo.OrderProductDetailWo;
import com.ztravel.product.common.service.impl.ProductDetailServiceImpl;
import com.ztravel.product.dao.IHotelDao;
import com.ztravel.product.dao.ProductDao;

@Service
public class ProductClientDetailServiceImpl implements IProductDetailService {

	private static Logger logger = RequestIdentityLogger.getLogger(ProductDetailServiceImpl.class);

	@Resource
	private ProductDao productDao;

	@Resource
	private IHotelDao hotelDao ;

	@Override
	public OrderProductDetailWo getProductById(String productId, String bookDate) throws Exception {
		Product product = productDao.getProductById(productId) ;
		OrderProductDetailWo productWo = new OrderProductDetailWo();
		if(product != null){
			productWo = convertFromProduct(product, bookDate) ;
		}
		return productWo ;
	}

	@Override
	public ProductBasicDetail getProductById(String productId) throws Exception {

		Product product = productDao.getProductById(productId) ;
		if(product == null){
			return null;
		}
		ProductBasicDetail productWo = new ProductBasicDetail();
		productWo.setProductId(productId);
		productWo.setPid(product.getPid());
		productWo.setpName(product.getpName());
		productWo.setImage(product.getImages().get(0));
		List<String> str = product.getToContinent();
		productWo.setToContinent(str == null ? "" : str.toString().replace("[", "").replace("]", ""));
		productWo.setStatus(product.getStatus().getDesc());
		return productWo ;

	}

	private OrderProductDetailWo convertFromProduct(Product product, String bookDate) throws Exception {

		OrderProductDetailWo productWo = new OrderProductDetailWo();

		if(product.getId() != null){
			productWo.setId(product.getId().toString());
		}
		productWo.setPid(product.getPid());
		productWo.setpName(product.getpName());
		productWo.setImages(product.getImages());
		productWo.setBookDate(bookDate);

		if(product.getFlight() != null){
			//转换航班信息
			setFlightForProduct(productWo, product.getFlight());
		}

		if(CollectionUtils.isNotEmpty(product.getHotels())){
			//转换酒店信息
			setHotelsForProduct(productWo, product.getHotels());
		}

		if(product.getAdditionalInfos() != null && ! product.getAdditionalInfos().isEmpty()){
			//转换附加信息, 包含费用说明
			setAdditionalInfosForProduct(productWo, product.getAdditionalInfos());
		}

		return productWo ;

	}

	private void setAdditionalInfosForProduct(OrderProductDetailWo productWo, Map<AdditionalRule, String> additionalInfos) {
		Map<String, String> infos = new HashMap<>();
		if(additionalInfos != null && additionalInfos.keySet() != null){
			Iterator<AdditionalRule> it = additionalInfos.keySet().iterator();
			while(it.hasNext()){
				AdditionalRule rule = it.next();
				infos.put(rule.toString(), additionalInfos.get(rule));
				}
		}
		productWo.setAdditionalInfos(infos);
	}

	private void setHotelsForProduct(OrderProductDetailWo productWo, List<Hotel> hotels) throws Exception {

		List<OrderHotelWo> hotelWos = new ArrayList<OrderHotelWo>();
		for(Hotel hotel : hotels){
			OrderHotelWo hotelWo = new OrderHotelWo();

			if(StringUtils.isNotBlank(hotel.getId())){
				HotelEntity hotelEntity = new HotelEntity();
				try{
					hotelEntity = hotelDao.getHotelById(hotel.getId());
				}catch(Exception e){
					logger.error("failed to get hotel", e);
			    	throw ZtrBizException.instance(Const.FO_PROD_CODE_1002, Const.FO_PROD_REASON_1002) ;
				}
				hotelWo.setId(hotel.getId());
				hotelWo.setHotelNameCn(hotelEntity.getHotelNameCn());
				hotelWo.setHotelNameEn(hotelEntity.getHotelNameEn());
				hotelWo.setName(hotelEntity.getHotelNameCn() + ' ' + hotelEntity.getHotelNameEn());

			}

			if(hotel.getBedType() != null){
				hotelWo.setBedType(hotel.getBedType().getDesc());
			}

			List<Integer> checkinDays = hotel.getCheckinDays() == null ? new ArrayList<Integer>() : hotel.getCheckinDays() ;
			hotelWo.setCheckinDays(checkinDays);
			hotelWo.setRoomType(hotel.getRoomType());

			String bookDate = productWo.getBookDate();
			if(!CollectionUtils.isEmpty(checkinDays)){
				hotelWo.setCheckInDate(DateUtils.addDate(bookDate, 3, checkinDays.get(0)));
				hotelWo.setCheckOutDate(DateUtils.addDate(bookDate, 3, checkinDays.get(checkinDays.size()-1)));
			}else{
				hotelWo.setCheckInDate(bookDate);
				hotelWo.setCheckOutDate(DateUtils.addDate(bookDate, 3, 1));
			}

			hotelWos.add(hotelWo);
		}
		productWo.setHotels(hotelWos);

	}

	private static void setFlightForProduct(OrderProductDetailWo productWo, Flight flight) {

		List<FlightInfoWo> go = new ArrayList<FlightInfoWo>() ;
		List<FlightInfoWo> middle = new ArrayList<FlightInfoWo>() ;
		List<FlightInfoWo> back = new ArrayList<FlightInfoWo>() ;
		Sorter sorter = new Sorter() ;

		OrderFlightWo flightWo = new OrderFlightWo() ;
		List<FlightInfoWo> infos = convertFromFlightInfo(flight);
		Iterator<FlightInfoWo> iter = infos.iterator() ;
		while(iter.hasNext()){
			FlightInfoWo info = iter.next() ;
			switch (info.getAirRange()) {
			case "去程":
				go.add(info) ;
				break;
			case "返程":
				back.add(info) ;
				break;
			case "中间程":
				middle.add(info) ;
				break;
			default:
				break;
			}
		}
		Collections.sort(go, sorter);
		Collections.sort(back, sorter);
		Collections.sort(middle, sorter);

		List<FlightInfoWo> main = new ArrayList<FlightInfoWo>() ;
		//将去程, 中间程, 返程结合到main中, 并将中间程按程分开放到map中
		Map<String, List<FlightInfoWo>> mrange = combineFlightInfo(go, back, middle, main);

		flightWo.setMiddle(mrange);
		flightWo.setBack(back);
		flightWo.setGo(go);
		flightWo.setMain(main);

		productWo.setFlight(flightWo);

	}

	private static Map<String, List<FlightInfoWo>> combineFlightInfo(List<FlightInfoWo> go, List<FlightInfoWo> back
			, List<FlightInfoWo> middle, List<FlightInfoWo> main) {

		Map<String, List<FlightInfoWo>> mrange = new LinkedHashMap<String,List<FlightInfoWo>>() ;

		if(go != null && go.size() > 0){
			for(FlightInfoWo f : go){
				if(f.getAirRangeIndex() % 1000 == 1){
					main.add(f);
				}else{
					FlightInfoWo fi = f;
					fi.setAirRange(f.getAirRange() + "-中转");
					main.add(fi);
				}
			}
		}

		for(FlightInfoWo f : middle){
			String index = String.valueOf(f.getAirRangeIndex() / 1000 + 1) ;
			if(mrange.get(index) != null){
				mrange.get(index).add(f) ;
				FlightInfoWo fi = f;
				fi.setAirRange("第" + index + "程" + "-中转");
				main.add(fi);
			}else{
				List<FlightInfoWo> tmp = new ArrayList<FlightInfoWo>() ;
				tmp.add(f) ;
				mrange.put(index, tmp) ;
				FlightInfoWo fi = f;
				fi.setAirRange("第" + index + "程");
				main.add(fi);
			}
		}

		if(back != null && back.size() > 0){
			for(FlightInfoWo f : back){
				if(f.getAirRangeIndex() % 1000 == 1){
					main.add(f);
				}else{
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

		if(flight !=null && flight.getInfos() != null){
			for(FlightInfo info : flight.getInfos()){
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
				infos.add(infoWo);
			}
		}

		return infos ;

	}

}
