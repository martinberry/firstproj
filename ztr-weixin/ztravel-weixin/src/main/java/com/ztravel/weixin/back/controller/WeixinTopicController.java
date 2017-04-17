package com.ztravel.weixin.back.controller;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.product.client.entity.TopicRelatedProductEntity;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.weixin.back.entity.WeixinTopicSearchCriteria;
import com.ztravel.weixin.back.service.IWeixinTopicService;
import com.ztravel.weixin.back.vo.CommentSearchCriteria;
import com.ztravel.weixin.back.vo.CommentVo;
import com.ztravel.weixin.back.vo.WeixinTopicVo;
import com.ztravel.weixin.po.WeixinTopic;
@Controller
@RequestMapping("/weixinTopic")
public class WeixinTopicController {
	private static final Logger LOGGER=LoggerFactory.getLogger(WeixinTopicController.class);
    
	@Resource
	private IWeixinTopicService weixinTopicServiceImpl;
	@Resource
	private IProductClientService productclientServiceImpl;
	
	@RequestMapping("/index")
	public String index(Model model){
	 return "/back/topic/topic_index";
	}
	
	@RequestMapping("/newTopic")
	public String newTopic(Model model){
		List<TopicRelatedProductEntity> relatedProductList=new ArrayList<TopicRelatedProductEntity>();
		try{
			relatedProductList=productclientServiceImpl.getReleasedProduct();
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("relatedProductList",relatedProductList);
	 return "/back/topic/newTopicDetail";
	}


	@RequestMapping("/topicDetail/{topicId}")
	public String topicDetail(@PathVariable String topicId,Model model){
		WeixinTopic topicDetail=new WeixinTopic();
		String selectedRelatedProduct=null;
		List<TopicRelatedProductEntity> relatedProductList=new ArrayList<TopicRelatedProductEntity>();
		try{
			topicDetail=weixinTopicServiceImpl.topicDetail(topicId);
			if(topicDetail.getPid()!=null){
				selectedRelatedProduct=topicDetail.getPid()+","+topicDetail.getProductTitle();
			}						
			relatedProductList=productclientServiceImpl.getReleasedProduct();
			
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("selectProduct", selectedRelatedProduct);
		model.addAttribute("topicDetail",topicDetail);
		model.addAttribute("relatedProductList",relatedProductList);
		return "/back/topic/newTopicDetail";
	}
	
	
	@RequestMapping("/topicComment")
	public String topicComment(@RequestBody CommentSearchCriteria criteria,Model model){
		List<CommentVo> commentVoList = null;
		int totalNum = 0;
		try{
			if(criteria.getTopicId()!=null && criteria.getTopicId()!=""){
				commentVoList = weixinTopicServiceImpl.searchComment(criteria);
				totalNum = weixinTopicServiceImpl.countComments(criteria);
			}
		
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("commentVoList", commentVoList);
		model.addAttribute("totalItemCount", totalNum);
		if( totalNum != 0 ){
			model.addAttribute("pageNo", criteria.getPageNo());
		}else{
			model.addAttribute("pageNo", 1);
		}
		model.addAttribute("pageSize",criteria.getPageSize());
		int totalPageCount=0;
		if (totalNum ==0){
			totalPageCount=1;
		}
		else{
		totalPageCount = (int) Math.ceil( (double)totalNum/criteria.getPageSize());
		}
		model.addAttribute("totalPageCount", totalPageCount);
		return "back/topic/topicComment"; 
	}
		
	@RequestMapping("/listReleased")
	@ResponseBody
	public AjaxResponse listReleased(@RequestBody WeixinTopic criteria){
		String topicId=criteria.getTopicId();
		WeixinTopic releasedcriteria=new WeixinTopic();
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		releasedcriteria.setTopicId(topicId);
		String releasedtime="";
		try{
			WeixinTopic weixinTopic=weixinTopicServiceImpl.released(releasedcriteria);	
			
			if(weixinTopic.getReleasedTime()!=null){
			releasedtime=weixinTopic.getReleasedTime().toDateTime(DateTimeZone.forOffsetHours(8)).toString("yyyy-MM-dd HH:mm:ss");
			}
		    ajaxResponse.setRes_code("releasedSuccess");
		    ajaxResponse.setRes_msg(releasedtime);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code("releasedFailed");  
		}
		return ajaxResponse;
	}
	
	
	@RequestMapping("/listOffline")
	@ResponseBody
	public AjaxResponse listOffline(@RequestBody WeixinTopic criteria){
		String topicId=criteria.getTopicId();
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			weixinTopicServiceImpl.offline(topicId);
			ajaxResponse.setRes_code("offlineSuccess");
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code("offlineFailed");
		}
		return ajaxResponse;
		
	}
	
	
	@RequestMapping("/listDelete")
	@ResponseBody
	public AjaxResponse delete(@RequestBody WeixinTopic criteria){
		String topicId=criteria.getTopicId();
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			weixinTopicServiceImpl.delete(topicId);
			ajaxResponse.setRes_code("deleteSuccess");
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code("deleteFailed");
		}
		return ajaxResponse;
	}
	
	
	@RequestMapping("/deleteComment")
	@ResponseBody
	public AjaxResponse deleteComment(@RequestBody CommentSearchCriteria criteria){
		String commentId=criteria.getCommentId();
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			weixinTopicServiceImpl.deleteComment(commentId);
			ajaxResponse.setRes_code("deleteSuccess");
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code("deleteFailed");
		}
		return ajaxResponse;
	}
	
	
	
	@RequestMapping("/search")
	public String search(@RequestBody WeixinTopicSearchCriteria criteria, Model model){
		//搜索条件格式校验，若校验失败，返回空列表
				ValidResult result = BasicValidator.valid(criteria);
				if( !result.isSuccess() ){
					return "order/back/commonorder/ListData"; 
				}
				List<WeixinTopicVo> wtVoList = null;
				int totalNum = 0;
				try{
					wtVoList = weixinTopicServiceImpl.searchWT(criteria);
					totalNum = weixinTopicServiceImpl.countWTs(criteria);
				} catch(Exception e){
					LOGGER.error(e.getMessage(), e);
				}
				model.addAttribute("wtVoList", wtVoList);
				model.addAttribute("totalItemCount", totalNum);
				if( totalNum != 0 ){
					model.addAttribute("pageNo", criteria.getPageNo());
				}else{
					model.addAttribute("pageNo", 1);
				}
				model.addAttribute("pageSize",criteria.getPageSize());
				int totalPageCount=0;
				if (totalNum ==0){
					totalPageCount=1;
				}
				else{
				totalPageCount = (int) Math.ceil( (double)totalNum/criteria.getPageSize());
				}
				model.addAttribute("totalPageCount", totalPageCount);
				return "/back/topic/topicdata"; 

	}
	
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResponse save(@RequestBody WeixinTopic savecriteria){
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
		    String topicId=weixinTopicServiceImpl.save(savecriteria);
		    ajaxResponse.setRes_code("saveSuccess");
		    ajaxResponse.setRes_msg(topicId);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code("saveFailed"); 
		}
		return ajaxResponse;
		
	}
	
	
	@RequestMapping("/released")
	@ResponseBody
	public AjaxResponse released(@RequestBody WeixinTopic releasedcriteria){
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			WeixinTopic weixintopic=weixinTopicServiceImpl.released(releasedcriteria);
			String topicId=weixintopic.getTopicId();
		    ajaxResponse.setRes_code("releasedSuccess");
		    ajaxResponse.setRes_msg(topicId);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code("releasedFailed");  
		}
		return ajaxResponse;
		
	}
	
	


}
