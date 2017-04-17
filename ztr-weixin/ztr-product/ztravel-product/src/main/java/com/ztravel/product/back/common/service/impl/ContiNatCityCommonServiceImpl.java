package com.ztravel.product.back.common.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.product.back.common.service.ContiNatCityCommonService;

@Service
public class ContiNatCityCommonServiceImpl implements ContiNatCityCommonService {

	private final static RedisClient redisClient = RedisClient.getInstance();

	Map<String, Object> map = null;
	Map<String, List<String>> cityMap = null;
	Map<String, List<String>> countryMap = null;

	@Override
	public List<String> getContinentList() {
		map = redisClient.get(Const.CONTINENT_NATION_CITY_KEY, Map.class);
		countryMap = (Map<String, List<String>>)map.get("country");
		List<String> continentList = new ArrayList<>();
		Set<String> continentSet = countryMap.keySet();
		Iterator<String> it = continentSet.iterator();
		while(it.hasNext()){
		continentList.add(it.next());
		}
		return continentList;
	}

	@Override
	public List<String> getNationList( String continentName) {
		map = redisClient.get(Const.CONTINENT_NATION_CITY_KEY, Map.class);
		countryMap = (Map<String, List<String>>)map.get("country");
		List<String> nationList = new ArrayList<>();
		nationList = countryMap.get(continentName);
		return nationList;
	}

	public List<String> getCityList( String nationName){
		map = redisClient.get(Const.CONTINENT_NATION_CITY_KEY, Map.class);
		cityMap = (Map<String, List<String>>)map.get("region");
		List<String> cityList = new ArrayList<>();
		cityList = cityMap.get(nationName);
		return cityList;
	}

}
