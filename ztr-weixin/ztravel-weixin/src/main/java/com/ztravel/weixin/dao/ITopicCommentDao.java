package com.ztravel.weixin.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.po.TopicComment;

public interface ITopicCommentDao extends BaseDao<TopicComment> {
	public List<TopicComment> selectInPraiseNum(Map params);

}
