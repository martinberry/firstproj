package com.ztravel.product.back.hotel.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.product.back.hotel.service.ContiNatCityService;

@Service
public class ContiNatCityServiceImpl implements ContiNatCityService {

	private final static RedisClient redisClient = RedisClient.getInstance();

	Map<String, Object> map = null;
	Map<String, List<String>> cityMap = null;
	Map<String, List<String>> countryMap = null;

	@Override
	public List<String> getContinentList() {
		List<String> continentList = new ArrayList<>();
		Map<String, List<String>> countryMap = getCountryMap();
		Set<String> continentSet = countryMap.keySet();
		Iterator<String> it = continentSet.iterator();
		while(it.hasNext()){
		continentList.add(it.next());
		}
		return continentList;
	}

	@Override
	public List<String> getNationList( String continentName) {

		List<String> nationList = new ArrayList<>();
		Map<String, List<String>> countryMap = getCountryMap();
		nationList = countryMap.get(continentName);
		return nationList;
	}

	public List<String> getCityList( String nationName){

		List<String> cityList = new ArrayList<>();
		Map<String, List<String>> cityMap = getCityMap();
		cityList = cityMap.get(nationName);
		return cityList;
	}

	private Map<String, List<String>> getCityMap(){
		map = redisClient.get(Const.CONTINENT_NATION_CITY_KEY, Map.class);
		cityMap =( Map<String, List<String>>)map.get("region");
		return cityMap;
	}

	private Map<String, List<String>> getCountryMap(){
		map = redisClient.get(Const.CONTINENT_NATION_CITY_KEY, Map.class);
		countryMap =( Map<String, List<String>>)map.get("country");
		return countryMap;
	}

}
