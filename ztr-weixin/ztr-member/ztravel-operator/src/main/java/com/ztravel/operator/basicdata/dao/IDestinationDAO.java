package com.ztravel.operator.basicdata.dao;

import com.ztravel.operator.basicdata.entity.DestinationEntity;

/**
 * MongoDB目的地信息
 * @author MH
 */
public interface IDestinationDAO {
	/**
	 * 保存目的地到mongo
	 * @param destinations
	 */
	public void saveDestinations(DestinationEntity destinations);
	/**
	 * 从mongo中取出目的地
	 * @return
	 */
	public DestinationEntity getDestinations();
	/**
	 * 删除目的地库
	 */
	public void deleteDestinationCollection();
	/**
	 * 更新默认目的地
	 * @param defaultDest
	 * @return
	 */
	public int updateDefaultDestination(String defaultDest);

}
