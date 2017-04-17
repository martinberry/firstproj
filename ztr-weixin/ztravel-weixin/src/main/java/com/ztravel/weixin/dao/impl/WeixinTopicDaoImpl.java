package com.ztravel.weixin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.dao.IWeixinTopicDao;
import com.ztravel.weixin.po.WeixinTopic;

@Repository
public class WeixinTopicDaoImpl extends BaseDaoImpl<WeixinTopic> implements IWeixinTopicDao{
	private static final String SELECT_BY_SORT=".selectBysort";
	
	private static final String DISCUSSNUM_ADD_ONE = ".discussNumAddOne" ;
	
	private static final String SORT_BY_DISCUSSNUM_AND_DATE = ".sortByNumAndDate" ;
	
	@Override
	public List<WeixinTopic> selectIndiscuss(Map params){
		return session.selectList(nameSpace + SELECT_BY_SORT, params);
	}

	@Override
	public void discussNumAddOne(String topicId) {
		session.update(nameSpace + DISCUSSNUM_ADD_ONE, topicId) ;
	}
	
	@Override
	public List<WeixinTopic> selectByNumAndDate(Map params){
		return session.selectList(nameSpace + SORT_BY_DISCUSSNUM_AND_DATE, params);
	}
	
}
