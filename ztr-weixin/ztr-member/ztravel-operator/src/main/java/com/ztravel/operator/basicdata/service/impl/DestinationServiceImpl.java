package com.ztravel.operator.basicdata.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.operator.basicdata.dao.IDestinationDAO;
import com.ztravel.operator.basicdata.entity.Destination;
import com.ztravel.operator.basicdata.entity.DestinationEntity;
import com.ztravel.operator.basicdata.service.IDestinationService;
import com.ztravel.operator.basicdata.vo.DestinationVO;

/**
 * @author MH
 */
@Service
public class DestinationServiceImpl implements IDestinationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DestinationServiceImpl.class);

	@Resource
	private IDestinationDAO destinationDao;

	private static final RedisClient redisClient =  RedisClient.getInstance();

	@Override
	public void saveDestinationsToRedis(List<JSONObject> destJsonList) throws Exception {
		JSONObject desinationJson = buildDestinationJson(destJsonList);
		redisClient.set(RedisKeyConst.DESTINATION_KEY, desinationJson);
		redisClient.persist(RedisKeyConst.DESTINATION_KEY);
	}

	@Override
	public void saveDestinationsToMongo(List<JSONObject> destJsonList) throws Exception {
		DestinationEntity destEntity = buildDestinationEntity(destJsonList);
		destinationDao.saveDestinations(destEntity);
	}

	@Override
	public DestinationVO getDestinationInfoFromMongo() throws Exception {
		String currentContinent = "";
		String currentCountry = "";

		DestinationVO destVo = new DestinationVO();
		List<Destination> destList = new ArrayList<Destination>();

		DestinationEntity destEntity = destinationDao.getDestinations();

		//将mongo中保存的目的地结构转换成页面展示结构
		for(Destination dest : destEntity.getDestinationList()){
			Destination destination = new Destination();

			if( !dest.getContinent().equals(currentContinent) ){
				destination.setContinent(dest.getContinent());
				currentContinent = dest.getContinent();
			}
			if( !dest.getCountry().equals(currentCountry) ){
				destination.setCountry(dest.getCountry());
				currentCountry = dest.getCountry();
			}
			destination.setCity(dest.getCity());

			destList.add(destination);
		}

		destVo.setDestinationList(destList);
		destVo.setDefaultDestination(destEntity.getDefaultDestination());
		return destVo;
	}

	@Override
	public void deleteDestinationsFromMongo() throws Exception {
		destinationDao.deleteDestinationCollection();
	}

	@Override
	public void setDefaultDestinationMongo(String defaultDest) throws Exception {
		int n = destinationDao.updateDefaultDestination(defaultDest);
		if( n != 1 ){
			LOGGER.error("更新数据库错误，更新了"+ n + "条数据");
		}
	}

//	@Override
//	public void setDefaultDestinationRedis(String defaultDest) throws Exception {
//		redisClient.setNoJSON(DEFAULT_DESTINATION_KEY, defaultDest);
//		redisClient.persist(DEFAULT_DESTINATION_KEY);
//	}


	private DestinationEntity buildDestinationEntity(List<JSONObject> destJsonList){
		DestinationEntity destEntity = new DestinationEntity();
		List<Destination> destList = new ArrayList<Destination>();

		for(JSONObject destJson : destJsonList){
			Destination dest = new Destination();
			dest.setContinent(destJson.getString("area"));
			dest.setCountry(destJson.getString("country"));
			dest.setCity(destJson.getString("region"));
			destList.add(dest);
		}

		destEntity.setDestinationList(destList);
		return destEntity;
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
