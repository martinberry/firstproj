package com.ztravel.member.weixin.service;

import com.ztravel.member.po.TravelerEntity;

/**
 * @author MH
 */
public interface IWxTravellerService {
	/**
	 * 通过id获取常旅客信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TravelerEntity getTravellerById(String id) throws Exception;
	/**
	 * 删除常旅客
	 * @param id
	 * @throws Exception
	 */
	public void deleteTravellerById(String id) throws Exception;
	/**
	 * 更新常旅客信息
	 * @param traveller
	 * @throws Exception
	 */
	public void updateTraveller(TravelerEntity traveller) throws Exception;

}
