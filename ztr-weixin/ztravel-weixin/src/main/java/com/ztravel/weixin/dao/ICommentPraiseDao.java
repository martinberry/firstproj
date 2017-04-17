package com.ztravel.weixin.dao;

import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.po.CommentPraise;

public interface ICommentPraiseDao extends BaseDao<CommentPraise> {
	public CommentPraise selectByOpenIdAndCommentId(Map params);
}
