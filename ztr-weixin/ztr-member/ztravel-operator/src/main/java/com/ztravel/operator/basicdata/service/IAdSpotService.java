package com.ztravel.operator.basicdata.service;

import java.util.List;

import com.ztravel.operator.basicdata.entity.AdSpotEntity;

/**
 *广告位
 * @author zhoubo
 */
public interface IAdSpotService {

	/**
	 * 保存广告位集合
	 * @throws Exception
	 */
	public void saveAdSpotList(List<AdSpotEntity> adSpotList) throws Exception;
	/**
	 * 获取广告位集合
	 * @throws Exception
	 */
	public List<AdSpotEntity> getAdSpotList() throws Exception;

}
