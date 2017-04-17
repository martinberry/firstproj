package com.ztravel.product.timming.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.dao.ProductDao;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.product.timming.service.IDestinationTimmingService;

@Service
public class DestinationTimmingServiceImpl implements IDestinationTimmingService {

	private static final Logger LOGGER  = RequestIdentityLogger.getLogger(DestinationTimmingServiceImpl.class);

	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private  ProductDao productDao;
	
	@Resource
	private IVisaProductDao visaProductDaoImpl;
	
	@Resource
	private IUnVisaProductDao unVisaProductDaoImpl;

	@Override
	public void updateAvailableDestination() throws Exception {
		List<JSONObject> destList = new LinkedList<JSONObject>();
		Set<JSONObject> destSet = new HashSet<JSONObject>();

		List<Product> products = productDao.getAllReleasedProducts();
		for(Product prod : products){
			List<String> areaList = prod.getToContinent();  //目的地第一级
			List<String> countryList = prod.getToCountry();   //目的地第二级
			List<String> regionList = prod.getTo();    //目的地第三级

			if( (areaList.size() == countryList.size()) && (countryList.size() == regionList.size()) ){
				for( int i = 0; i < areaList.size(); i++ ){
					JSONObject dest = new JSONObject();
					dest.put("area", areaList.get(i));
					dest.put("country", countryList.get(i));
					dest.put("region", regionList.get(i));
					destSet.add(dest);
				}
			}else{
				LOGGER.debug("产品目的地数据异常");
			}
		}
		destList.addAll(destSet);
		JSONObject destinationsJson = buildDestinationJson(destList);

		redisClient.set(RedisKeyConst.AVAILABLE_DESTINATION_KEY, destinationsJson);
		redisClient.persist(RedisKeyConst.AVAILABLE_DESTINATION_KEY);
		LOGGER.info("DestinationTimer刷新的可用目的地: " + destinationsJson.toString());
	}
	
	@Override
	public void updateVisaAvailableDestination() throws Exception {
		List<JSONObject> destList = new LinkedList<JSONObject>();
		Set<JSONObject> destSet = new HashSet<JSONObject>();
		Map<String,String> map = new HashMap<>();
		map.put("status", ProductStatus.RELEASED.name());
		List<VisaProduct> products = visaProductDaoImpl.selectByMap(map);
		for(VisaProduct prod : products){
			List<String> areaList = prod.getBasicInfo().getToContinent();  //目的地第一级
			List<String> countryList = prod.getBasicInfo().getToCountry();   //目的地第二级
			List<String> regionList = prod.getBasicInfo().getToCity();    //目的地第三级
			if( (areaList.size() == countryList.size()) && (countryList.size() == regionList.size()) ){
				for( int i = 0; i < areaList.size(); i++ ){
					JSONObject dest = new JSONObject();
					dest.put("area", areaList.get(i));
					dest.put("country", countryList.get(i));
					dest.put("region", regionList.get(i));
					destSet.add(dest);
				}
			}else{
				LOGGER.debug("签证产品目的地数据异常");
			}
		}
		destList.addAll(destSet);
		JSONObject destinationsJson = buildDestinationJson(destList);

		redisClient.set(RedisKeyConst.AVAILABLE_VISA_DESTINATION_KEY, destinationsJson);
		redisClient.persist(RedisKeyConst.AVAILABLE_VISA_DESTINATION_KEY);
		LOGGER.info("DestinationTimer刷新签证产品的可用目的地: " + destinationsJson.toString());
	}
	
	@Override
	public void updateLocalAvailableDestination() throws Exception {
		List<JSONObject> destList = new LinkedList<JSONObject>();
		Set<JSONObject> destSet = new HashSet<JSONObject>();
		Map<String,String> map = new HashMap<>();
		map.put("status", ProductStatus.RELEASED.name());
		List<UnVisaProduct> products = unVisaProductDaoImpl.selectByMap(map);
		for(UnVisaProduct prod : products){
			List<String> areaList = prod.getBasicInfo().getToContinent();  //目的地第一级
			List<String> countryList = prod.getBasicInfo().getToCountry();   //目的地第二级
			List<String> regionList = prod.getBasicInfo().getToCity();    //目的地第三级
			if( (areaList.size() == countryList.size()) && (countryList.size() == regionList.size()) ){
				for( int i = 0; i < areaList.size(); i++ ){
					JSONObject dest = new JSONObject();
					dest.put("area", areaList.get(i));
					dest.put("country", countryList.get(i));
					dest.put("region", regionList.get(i));
					destSet.add(dest);
				}
			}else{
				LOGGER.debug("当地游产品目的地数据异常");
			}
		}
		destList.addAll(destSet);
		JSONObject destinationsJson = buildDestinationJson(destList);

		redisClient.set(RedisKeyConst.AVAILABLE_LOCAL_DESTINATION_KEY, destinationsJson);
		redisClient.persist(RedisKeyConst.AVAILABLE_LOCAL_DESTINATION_KEY);
		LOGGER.info("DestinationTimer刷新当地游产品的可用目的地: " + destinationsJson.toString());
	}


	private JSONObject buildDestinationJson(List<JSONObject> destinationList) {
		JSONObject json = new JSONObject();

		JSONObject countryJSon = new JSONObject();
		JSONObject regionJSon = new JSONObject();
		String currentArea = "";
		String currentCountry = "";
		String currentRegion = "";
		for(JSONObject temp : destinationList) {
			currentArea = temp.getString("area");
			currentCountry = temp.getString("country");
			currentRegion = temp.getString("region");

			LinkedList<String> countryList = new LinkedList<String>();
			if(countryJSon.containsKey(currentArea)) {
				countryList = (LinkedList<String>) countryJSon.get(currentArea);
				if(!countryList.contains(currentCountry)) {
					countryList.add(currentCountry);
				}
			}else{
				countryList.add(currentCountry);
			}
			countryJSon.put(currentArea, countryList);

			LinkedList<String> regionList = new LinkedList<String>();
			if(regionJSon.containsKey(currentCountry)) {
				regionList = (LinkedList<String>) regionJSon.get(currentCountry);

				if(!regionList.contains(currentRegion)) {
					regionList.add(currentRegion);
				}
			}else {
				regionList.add(currentRegion);
			}
			regionJSon.put(currentCountry, regionList);
		}
		json.put("country", countryJSon);
		json.put("region", regionJSon);

		return  json;
	}

}
