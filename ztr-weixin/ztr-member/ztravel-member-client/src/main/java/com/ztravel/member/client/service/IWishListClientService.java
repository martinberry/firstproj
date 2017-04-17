package com.ztravel.member.client.service;

public interface IWishListClientService {

	/**
	 * 根据会员id和产品id查询是否已加入心愿清单
	 * @param memberId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	boolean isWishorNot(String memberId, String productId) throws Exception;

	/**
	 * 根据产品id删除数据库中心愿清单
	 * @param productId
	 * @throws Exception
	 */
	void deleteWishByProductId(String productId) throws Exception;

}
