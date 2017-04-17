package com.ztravel.member.client.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.util.MoneyCalculator;
import com.ztravel.member.client.service.IBalanceDetailClientService;
import com.ztravel.member.dao.BalanceDetailDao;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.enums.BonusStatus;
import com.ztravel.member.enums.TravelStatus;
import com.ztravel.member.po.BalanceDetailEntity;
import com.ztravel.member.po.Member;

@Service(value="balanceDetailClientService")
public class BalanceDetailClientServiceImpl implements IBalanceDetailClientService{


	private static final Logger LOGGER = LoggerFactory.getLogger(BalanceDetailClientServiceImpl.class);


	@Resource
	private BalanceDetailDao balanceDetailDao ;

	@Resource
	private MemberDao memberDao ;

	@Override
	public void insert(String friendId, String memberId, Long bonus) {
		try{
			if(StringUtil.isEmpty(friendId)){
				return ;
			}
			BalanceDetailEntity entity = new BalanceDetailEntity() ;
			entity.setCreateTime(DateTime.now());
			entity.setUpdateTime(DateTime.now());
			entity.setbStatus(BonusStatus.RESERVE);
			String mobile = memberDao.selectById(memberId).getMobile() ;
			entity.setfMobile(mobile);
			entity.setFriend(memberId);
			entity.setBonus(bonus);
			entity.setfStatus(TravelStatus.REGISTER);
			entity.setMemberId(friendId);
			LOGGER.debug("memberId:{},friendId:{},bonus:{},mobile:{}",friendId,memberId,bonus,mobile);
			balanceDetailDao.insert(entity);
		}catch(Exception e){
			LOGGER.error("insert balance detail error..", e);
		}

	}

	@Override
	public Pagination<BalanceDetailEntity> getBalanceDetailsByMemberid(String memberId, Pagination<BalanceDetailEntity> pagination) throws Exception{
		Map <String, Object> searchParams = Maps.newHashMap();
		if(StringUtils.isNotEmpty(memberId))
		searchParams.put("memberId", memberId);
		List<BalanceDetailEntity> balanceDetails = Lists.newArrayList();

		   if(pagination != null){
			   searchParams.put("offset", (pagination.getPageNo()-1)*pagination.getPageSize());
			   searchParams.put("limit", pagination.getPageSize());
	        }
		   balanceDetails =	balanceDetailDao.select(searchParams);
		   if(balanceDetails != null ){
			   for(BalanceDetailEntity balanceDetail :	balanceDetails){
				   setFriendMobileByBalance(balanceDetail);
			   }
		   }
		   pagination.setData(balanceDetails);
		return pagination;
	}


	private void setFriendMobileByBalance(BalanceDetailEntity balanceDetail) throws Exception{
		   String friendId = balanceDetail.getFriend();
		   if(StringUtils.isNotEmpty(friendId)){
			   Member member = memberDao.selectById(friendId);
			   if(member != null){
				   String mobile = member.getMobile();
				   if(StringUtils.isNotEmpty(mobile)){
					   balanceDetail.setfMobile(mobile);
				   }
			   }
		   }
	}
	
	private void setFriendNameByBalanceDetail(BalanceDetailEntity balanceDetail) throws Exception{
		String friendId = balanceDetail.getFriend();
		if(StringUtils.isNotEmpty(friendId)){
			   Member member = memberDao.selectById(friendId);
			   if(member != null){
				   String nickName = member.getNickName();
				   if(StringUtils.isNotEmpty(nickName)){
					   balanceDetail.setFriend(nickName);
				   }
			   }
		   }
	}
	

	@Override
	public int countByMemberId(String memberId) throws Exception {
		Map <String, Object> searchParams = Maps.newHashMap();
		searchParams.put("memberId", memberId);
		return balanceDetailDao.count(searchParams);
	}

	@Override
	public void setBonusIssueAndFriendTravel(String memberId,	String friendId) throws Exception {
		Map<String,	String> params = Maps.newHashMap();
		if(StringUtils.isNotEmpty(memberId)){
			params.put("memberId", memberId);
		}
		if(StringUtils.isNotEmpty(friendId)){
			params.put("friend", friendId);
		}
		List<BalanceDetailEntity> balanceDetails = balanceDetailDao.select(params);
		if(null != balanceDetails && balanceDetails.size()>0){
			BalanceDetailEntity balanceDetailEntity = balanceDetails.get(0);
			balanceDetailEntity.setbStatus(BonusStatus.ISSUED);
			balanceDetailEntity.setfStatus(TravelStatus.TRAVEL);
			balanceDetailEntity.setUpdateTime(DateTime.now());
			balanceDetailDao.update(balanceDetailEntity);
		}
	}

	@Override
	public long getBonusByRecommanderAndFriend(String memberId, String friendId)throws Exception {
		long bonus = 0l;
		Map<String,	String> params = Maps.newHashMap();
		if(StringUtils.isNotEmpty(memberId)){
			params.put("memberId", memberId);
		}
		if(StringUtils.isNotEmpty(friendId)){
			params.put("friend", friendId);
		}
		List<BalanceDetailEntity> balanceDetails = balanceDetailDao.select(params);
		if(null != balanceDetails && balanceDetails.size()>0){
			BalanceDetailEntity balanceDetailEntity = balanceDetails.get(0);
			if(null != balanceDetailEntity){
				bonus = balanceDetailEntity.getBonus();
			}
		}
		return bonus;
	}

	@Override
	public List<BalanceDetailEntity> getBalanceDetailsByMemberId(String memberId) throws Exception {
		Map map = Maps.newHashMap();
		map.put("memberId", memberId);
		List<BalanceDetailEntity> list = balanceDetailDao.select(map);
		for(BalanceDetailEntity balanceDetail : list){
			setFriendNameByBalanceDetail(balanceDetail);
			balanceDetail.setfStatus(balanceDetail.getfStatus());
			if(null != balanceDetail.getBonus() && 0!=balanceDetail.getBonus()){
				MoneyCalculator money = new MoneyCalculator(balanceDetail.getBonus());
				long bonus = money.fenToYuan().toLong();
				balanceDetail.setBonus(bonus);
			}
			
		}
		return list;
	}

	


}
