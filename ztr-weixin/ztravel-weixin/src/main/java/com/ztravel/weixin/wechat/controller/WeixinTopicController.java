package com.ztravel.weixin.wechat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.weixin.front.service.IWeixinTopicService;
import com.ztravel.weixin.front.vo.FrontCommentVo;
import com.ztravel.weixin.front.vo.FrontTopicVo;
import com.ztravel.weixin.po.TopicComment;
import com.ztravel.weixin.po.WeixinTopic;



@Controller
@RequestMapping("/topic")
public class WeixinTopicController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinTopicController.class);
	
	@Resource
	private IWeixinTopicService weixinTopicService;
	
	@RequestMapping("/topicDetail/{topicId}")
	public String showTopicList(HttpServletRequest request,@PathVariable String topicId,String scrollType, Model model,String state,String code){
	try{
		weixinTopicService.dealWxUserInfo (code, state, model);		
		String openId = OpenIdUtil.getOpenId();
		//openId = "oSyr1we5si0PaiIyn0G3nmWhCytI" ;
        if (openId != null) {    	        
                model.addAttribute("openId", openId);                  	
        		
        }
        WeixinTopic topic=new WeixinTopic();
		List<TopicComment> commentList=new ArrayList<TopicComment>();
		FrontTopicVo weixintopic=new FrontTopicVo();
		List<FrontCommentVo> commentVoList=new ArrayList<FrontCommentVo>();
	    topic=weixinTopicService.getTopicDetailById(topicId);		    
	    weixintopic=weixinTopicService.convert2TopicVo(topic);
	    commentList=weixinTopicService.getTopicCommentById(topicId);
	    commentVoList=weixinTopicService.convert2CommentListVo(commentList);		    
		model.addAttribute("weixinTopic",weixintopic);
		model.addAttribute("commentList",commentVoList);
        model.addAttribute("appId", WechatAccountHolder.APP_ID);	
		if(scrollType != null){
			model.addAttribute("scrollType", scrollType);
		}
	}catch(Exception e){
		LOGGER.error(e.getMessage(), e);
	}
			
		return "/weixin/topic/topicDetail";
}
	
   @RequestMapping("/praise")
   @ResponseBody
   public AjaxResponse praiseComment(@RequestParam String openId,@RequestParam String commentId,Model model){   
	   AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
    	try{   		
    		ajaxResponse=weixinTopicService.praiseAction(openId, commentId);     
    	}catch(Exception e){  
    		ajaxResponse.setRes_code("praiseFail");
    		LOGGER.error(e.getMessage(), e);   		
    	}
    	return ajaxResponse;    	
    }
	

}
