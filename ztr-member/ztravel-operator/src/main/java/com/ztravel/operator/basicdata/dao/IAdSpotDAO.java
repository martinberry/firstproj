package com.ztravel.operator.basicdata.dao;

import java.util.List;

import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.entity.AdSpotEntity;

/**
 * MongoDb 基础数据 -->广告位
 * @author zhoubo
 *
 */
public interface IAdSpotDAO {
	/**
	 * 保存广告位List
	 * @param adSpotList
	 */
	public void insertAdSpotList(List<AdSpotEntity> adSpotList) throws MongoException;
	/**
	 * 删除广告位
	 * @throws MongoException
	 */
	public void deleteAll() throws MongoException;

	/**
	 * 获取广告位集合
	 * @throws MongoException
	 */
	public List<AdSpotEntity> getAdSpotList() throws MongoException;;

}
