package com.ztravel.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderComContactorDao;
import com.ztravel.order.po.OrderComContactor;

@Repository
public class OrderComContactorDaoImpl extends BaseDaoImpl<OrderComContactor> implements IOrderComContactorDao{

	private static final String SELECT_BY_MEMBER_ID = ".selectByMemberId" ;
	private static final String SELECT_BY_MEMBER_ID_NAME = ".selectByMemberIdAndName" ;
	private static final String SELECT_BY_MEMBER_IDS = ".selectByMemberIds" ;
	@Override
	public OrderComContactor selectByMemberId(String memberId) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("memberId", memberId);
		return session.selectOne(nameSpace + SELECT_BY_MEMBER_ID, params) ;
	}
	
	@Override
	public List<OrderComContactor> selectByMemberIds(String memberId) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("memberId", memberId);
		return session.selectList(nameSpace + SELECT_BY_MEMBER_IDS, params) ;
	}
	
	
	
	@Override
	public OrderComContactor selectByMemberIdAndName(String memberId,String contactorName) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("memberId", memberId);
		params.put("contactorName", contactorName);
		return session.selectOne(nameSpace + SELECT_BY_MEMBER_ID_NAME, params) ;
	}
	
	@Override
	public List<OrderComContactor> selectContactorsByMemberId(String memberId) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("memberId", memberId);
		return session.selectList(nameSpace + SELECT_BY_MEMBER_ID, params) ;
	}

	@Override
	public Boolean isContactorExist(String memberId) throws Exception {
		return selectByMemberId(memberId) == null;
	}

}
