package com.ztravel.weixin.back.service;

import java.util.List;

import com.ztravel.weixin.back.entity.WeixinTopicSearchCriteria;
import com.ztravel.weixin.back.vo.CommentSearchCriteria;
import com.ztravel.weixin.back.vo.CommentVo;
import com.ztravel.weixin.back.vo.WeixinTopicVo;
import com.ztravel.weixin.po.WeixinTopic;


public interface IWeixinTopicService {
	public List<WeixinTopicVo> searchWT(WeixinTopicSearchCriteria criteria)throws Exception;
	public int countWTs(WeixinTopicSearchCriteria criteria)throws Exception;
	public String save(WeixinTopic savecriteria)throws Exception;
	public WeixinTopic released(WeixinTopic releasedcriteria)throws Exception;
	public WeixinTopic topicDetail(String topicId)throws Exception;
	public void offline(String topicId)throws Exception;
	public void delete(String topicId)throws Exception;
	public List<CommentVo> searchComment(CommentSearchCriteria criteria)throws Exception;
	public int countComments(CommentSearchCriteria criteria)throws Exception;
	public void deleteComment(String commentId)throws Exception;
}
