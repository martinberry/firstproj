package com.ztravel.member.opera.service;

import java.util.List;

import com.ztravel.member.po.WishEntity;
import com.ztravel.member.opera.wo.WishDetailPageWo;
import com.ztravel.member.opera.wo.WishListSearchWo;
import com.ztravel.member.opera.wo.WishMemberWo;
import com.ztravel.member.opera.wo.WishProductResponseWo;

public interface IWishListService {

	/**
	 * 查询单个会员的所有心愿清单
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	List<WishEntity> searchWishsByMemberId(String memberId) throws Exception;

	/**
	 * 根据产品名称分页查询所有产品的收藏信息
	 * @param searchWo
	 * @param response
	 * @return
	 * @throws Exception
	 */
	long searchWishMembers(WishListSearchWo searchWo, List<WishProductResponseWo> response) throws Exception;

	/**
	 * 分页显示收藏某个产品的所有会员信息
	 * @param pageWo
	 * @param members
	 * @return
	 * @throws Exception
	 */
	long searchWishsByProductId(WishDetailPageWo pageWo, List<WishMemberWo> members) throws Exception;

}
