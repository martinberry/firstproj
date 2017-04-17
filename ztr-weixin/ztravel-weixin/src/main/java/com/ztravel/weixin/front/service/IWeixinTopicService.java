package com.ztravel.weixin.front.service;

import java.util.List;

import org.springframework.ui.Model;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.weixin.front.vo.FrontCommentVo;
import com.ztravel.weixin.front.vo.FrontTopicVo;
import com.ztravel.weixin.po.TopicComment;
import com.ztravel.weixin.po.WeixinTopic;



public interface IWeixinTopicService {
	
	public WeixinTopic getTopicDetailById(String topicId)throws Exception;
	public List<TopicComment> getTopicCommentById(String topicId)throws Exception;
	public AjaxResponse praiseAction(String openId,String commentId);
	public void dealWxUserInfo (String code, String state,Model model) throws Exception;
	public List<FrontCommentVo> convert2CommentListVo(List<TopicComment> topicCommentList);
	public FrontTopicVo convert2TopicVo(WeixinTopic weixinTopic);
	void submitComment(TopicComment entity);
	
}
