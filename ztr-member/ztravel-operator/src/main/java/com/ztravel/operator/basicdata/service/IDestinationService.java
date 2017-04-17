package com.ztravel.operator.basicdata.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ztravel.operator.basicdata.entity.Destination;
import com.ztravel.operator.basicdata.vo.DestinationVO;

/**
 * @author MH
 */
public interface IDestinationService {
	/**
	 * 保存目的地到Redis
	 * @param destinationJsons
	 * @throws Exception
	 */
	public void saveDestinationsToRedis(List<JSONObject> destinationJsons) throws Exception;
	/**
	 * 保存目的地到Mongo
	 * @param destinationJsons
	 * @throws Exception
	 */
	public void saveDestinationsToMongo(List<JSONObject> destinationJsons) throws Exception;
	/**
	 * 从mongo中取全部目的地
	 * @return
	 * @throws Exception
	 */
	public DestinationVO getDestinationInfoFromMongo() throws Exception;
	/**
	 * 删除目的地库
	 * @throws Exception
	 */
	public void deleteDestinationsFromMongo() throws Exception;
	/**
	 * 设置默认目的地(mongo)
	 * @param defaultDest
	 * @throws Exception
	 */
	public void setDefaultDestinationMongo(String defaultDest) throws Exception;
//	/**
//	 * 设置默认目的地(redis)
//	 * @param defaultDest
//	 * @throws Exception
//	 */
//	public void setDefaultDestinationRedis(String defaultDest) throws Exception;

}
