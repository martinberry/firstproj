package com.ztravel.weixin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.dao.ITopicCommentDao;
import com.ztravel.weixin.po.TopicComment;

@Repository
public class TopicCommentDaoImpl extends BaseDaoImpl<TopicComment> implements ITopicCommentDao{

	
	
	private static final String SELECT_BY_SORT=".selectBysort";
	
	
	@Override
	public List<TopicComment> selectInPraiseNum(Map params){
		return session.selectList(nameSpace + SELECT_BY_SORT, params);
	}
}
