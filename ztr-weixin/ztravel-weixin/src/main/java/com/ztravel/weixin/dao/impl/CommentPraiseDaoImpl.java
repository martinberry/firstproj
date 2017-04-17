package com.ztravel.weixin.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.dao.ICommentPraiseDao;
import com.ztravel.weixin.po.CommentPraise;

@Repository
public class CommentPraiseDaoImpl extends BaseDaoImpl<CommentPraise> implements ICommentPraiseDao{
	private static final String SELECT_BY_OPENID_COMMENTID_COMMENTID =".selectByOpenIdAndCommentId";

	
	@Override
	public CommentPraise selectByOpenIdAndCommentId(Map params){
		return session.selectOne(nameSpace + SELECT_BY_OPENID_COMMENTID_COMMENTID, params);
	}

}
