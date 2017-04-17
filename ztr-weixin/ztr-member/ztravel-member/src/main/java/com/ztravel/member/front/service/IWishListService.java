package com.ztravel.member.front.service;

import java.util.List;

import com.ztravel.member.front.vo.AddToWishRequest;
import com.ztravel.member.front.vo.WishDetailResponse;

public interface IWishListService {

	/**
	 * 会员查询心愿清单
	 * @param memberId
	 * @param mid
	 * @return
	 * @throws Exception
	 */
	List<WishDetailResponse> searchWishsDetailByMemberId(String memberId, String mid) throws Exception;

	/**
	 * 删除心愿清单中的产品
	 * @param wishId
	 * @throws Exception
	 */
	void deleteWish(String wishId) throws Exception;

	/**
	 * 添加心愿清单
	 * @param memberId
	 * @param mid
	 * @param product
	 * @throws Exception
	 */
	void add2WishList(String memberId, String mid, AddToWishRequest product) throws Exception;

	String getProductIdsByMemberId(String memberId) throws Exception;

}
