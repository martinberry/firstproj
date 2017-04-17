package com.ztravel.member.dao;

import java.util.List;

import com.github.jmkgreen.morphia.query.Query;
import com.mongodb.DBCursor;
import com.ztravel.member.po.WishEntity;

public interface IWishListDao {

	/**
	 * 根据产品id查询对应的所有心愿清单
	 * @param productId
	 * @return
	 */
	Query<WishEntity> queryWishsByProductId(String productId);

	/**
	 * 根据会员id查询对应的所有心愿清单
	 * @param memberId
	 * @return
	 */
	List<WishEntity> queryWishsByMemberId(String memberId);

	/**
	 * 根据产品id和会员id查询某条心愿清单
	 * @param memberId
	 * @param productId
	 * @return
	 */
	List<WishEntity> queryWishs(String memberId, String productId);

	/**
	 * 将某条心愿清单插入数据库
	 * @param memberId
	 * @param product
	 * @param hasGo
	 * @return
	 */
	String insertWish(String memberId, WishEntity wish);

	/**
	 * 删除数据库中某条心愿清单
	 * @param wishId
	 * @throws Exception
	 */
	void deleteWish(String wishId);

	/**
	 * 删除数据库中某个产品id的所有心愿清单
	 * @param productId
	 */
	void deleteWishByProductId(String productId);
	
	/**
	 * 根据产品名称查询对应的所有心愿清单及相关信息
	 * @param oriPName
	 * @return
	 */
	DBCursor queryWishMembers(String oriPName);


}
