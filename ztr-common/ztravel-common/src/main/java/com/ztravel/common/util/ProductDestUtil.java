package com.ztravel.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.common.entity.ProductDestination;

/**
 * 从redis中取产品目的地工具类
 * @author MH
 */
public class ProductDestUtil {
	private static RedisClient redisClient = RedisClient.getInstance();
	private static Map<String, Object> destinationMap;

	/**
	 * 从redis中取出可用目的地(有产品上线的)
	 * 用于前台产品搜索框 目的地展示
	 * @return
	 */
	public static List<ProductDestination> getAvailableDestinations(String key){
		List<ProductDestination> destinations = new ArrayList<ProductDestination>();

		destinationMap = redisClient.get(key, Map.class);
		Map<String, List<String>> map = (Map<String, List<String>>) destinationMap.get("country");

		for(String firstLevelDest : map.keySet()){
			ProductDestination dest = new ProductDestination();
			dest.setDestName(firstLevelDest);
			dest.setSubDestinations(map.get(firstLevelDest));
			destinations.add(dest);
		}

		return destinations;
	}
	/**
	 * 取默认目的地
	 * @return
	 */
	public static String getDefaultDestination(){
		return redisClient.get(RedisKeyConst.DEFAULT_DESTINATION_KEY);
	}

}