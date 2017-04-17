package com.ztravel.member.client.service;

import java.util.List;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.po.BalanceDetailEntity;





/**
 * @author wanhaofan
 * */
public interface IBalanceDetailClientService {

	void insert(String friendId, String memberId, Long bonus) ;

	/**
	 * 根据memberId查询帮忙赚钱好友列表
	 * @param memberId
	 * @return  Pagination<BalanceDetailEntity> pagination
	 */
	Pagination<BalanceDetailEntity> getBalanceDetailsByMemberid(String memberId, Pagination<BalanceDetailEntity> pagination) throws Exception;

	int countByMemberId(String memberId) throws Exception;

	void setBonusIssueAndFriendTravel(String memberId,	String friendId) throws Exception;

	long getBonusByRecommanderAndFriend(String memberId,	String friendId) throws Exception;
	
	List<BalanceDetailEntity> getBalanceDetailsByMemberId(String memberId) throws Exception;
}
