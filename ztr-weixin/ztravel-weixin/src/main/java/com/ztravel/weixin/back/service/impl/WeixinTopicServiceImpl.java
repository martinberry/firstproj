package com.ztravel.weixin.back.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztravel.common.enums.TopicStatus;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.weixin.back.entity.WeixinTopicSearchCriteria;
import com.ztravel.weixin.back.service.IWeixinTopicService;
import com.ztravel.weixin.back.vo.CommentSearchCriteria;
import com.ztravel.weixin.back.vo.CommentVo;
import com.ztravel.weixin.back.vo.WeixinTopicVo;
import com.ztravel.weixin.dao.ITopicCommentDao;
import com.ztravel.weixin.dao.IWeixinTopicDao;
import com.ztravel.weixin.po.TopicComment;
import com.ztravel.weixin.po.WeixinTopic;

@Service
public class WeixinTopicServiceImpl implements IWeixinTopicService{
@Resource
private IWeixinTopicDao weixinTopicDao;

@Resource 
private ITopicCommentDao topicCommentDao;

@Resource
private IdGeneratorUtil idGeneratorUtil ;


@Override	
@SuppressWarnings({ "rawtypes","unchecked"})	
public List<WeixinTopicVo> searchWT(WeixinTopicSearchCriteria criteria)throws Exception{
	Map searchmap=convertSearch(criteria);
	searchmap.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
	searchmap.put("limit", criteria.getPageSize());
	List<WeixinTopic> weixinTopicList=new ArrayList<WeixinTopic>();
	List<WeixinTopicVo> weixinTopicVoList=new ArrayList<WeixinTopicVo>();
	if(criteria.getSortType().equals("time")){
		weixinTopicList=weixinTopicDao.select(searchmap);
	}else{
		weixinTopicList=weixinTopicDao.selectIndiscuss(searchmap);
	}
	
    weixinTopicVoList=convertVoList(weixinTopicList);
    return weixinTopicVoList;
	
}

@Override
@SuppressWarnings({ "rawtypes"})
public int countWTs(WeixinTopicSearchCriteria criteria)throws Exception{
   Map countmap=convertSearch(criteria);
   int count=weixinTopicDao.count(countmap);
   return count;
}

@Override	
@SuppressWarnings({ "rawtypes","unchecked"})	
public List<CommentVo> searchComment(CommentSearchCriteria criteria)throws Exception{
   Map map=new HashMap();
   map.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
   map.put("limit", criteria.getPageSize());
   map.put("topicId", criteria.getTopicId());
   List<TopicComment> commentList;
   if(criteria.getSortType().equals("time")){
	   commentList=topicCommentDao.select(map); 
   }else{
	   commentList=topicCommentDao.selectInPraiseNum(map); 
   }
   
   List<CommentVo> commentVoList=convert2CommentListVo(commentList);
   return commentVoList;

}

@Override
@SuppressWarnings({ "rawtypes","unchecked"})	
public int countComments(CommentSearchCriteria criteria)throws Exception{
   Map map=new HashMap();
   map.put("topicId", criteria.getTopicId());
   int count=topicCommentDao.count(map);
   return count;
}



@Override
public String save(WeixinTopic savecriteria)throws Exception{
	if(savecriteria.getTopicContent()=="")
		savecriteria.setTopicContent(null);
	if(savecriteria.getGiftTitle()=="")
		savecriteria.setGiftTitle(null);
	if(savecriteria.getGiftContent()=="")
		savecriteria.setGiftContent(null);
	savecriteria.setDiscussNum(0);
	if(savecriteria.getTopicId()!=null && savecriteria.getTopicId()!=""){
		savecriteria.setUpdatedate(new DateTime());
		weixinTopicDao.update(savecriteria);
	}else{
		savecriteria.setTopicId(idGeneratorUtil.getWeixinTopicId());
		savecriteria.setStatus(TopicStatus.NEW);
		savecriteria.setCreatedate(new DateTime());
		savecriteria.setUpdatedate(new DateTime());
		weixinTopicDao.insert(savecriteria);
	}	
	String topicId=savecriteria.getTopicId();
	return  topicId;
}

@Override
public WeixinTopic released(WeixinTopic releasedcriteria)throws Exception{
	if(releasedcriteria.getTopicContent()=="")
		releasedcriteria.setTopicContent(null);
	if(releasedcriteria.getGiftTitle()=="")
		releasedcriteria.setGiftTitle(null);
	if(releasedcriteria.getGiftContent()=="")
		releasedcriteria.setGiftContent(null);
	releasedcriteria.setStatus(TopicStatus.RELEASED);
	releasedcriteria.setReleasedTime(new DateTime());
	if(releasedcriteria.getTopicId()==""||releasedcriteria.getTopicId()==null){		
		releasedcriteria.setTopicId(idGeneratorUtil.getWeixinTopicId());
		releasedcriteria.setCreatedate(new DateTime());
		releasedcriteria.setUpdatedate(new DateTime());
		releasedcriteria.setDiscussNum(0);
		weixinTopicDao.insert(releasedcriteria);
	}else{
		releasedcriteria.setUpdatedate(new DateTime());		
		weixinTopicDao.update(releasedcriteria);
	}
	return releasedcriteria;
	
}

@Override
public void offline(String topicId)throws Exception{
   WeixinTopic weixinTopic=new WeixinTopic();
   weixinTopic.setStatus(TopicStatus.OFFLINE);
   weixinTopic.setTopicId(topicId);
   weixinTopic.setUpdatedate(new DateTime());
   WeixinTopic topicTmp=weixinTopicDao.selectById(topicId);
   if(topicTmp.getGiftContent()!=null)
   weixinTopic.setGiftContent(topicTmp.getGiftContent());
   if(topicTmp.getGiftTitle()!=null)
   weixinTopic.setGiftTitle(topicTmp.getGiftTitle());	 
   if(topicTmp.getGiftImage()!=null)
   weixinTopic.setGiftImage(topicTmp.getGiftImage());
   if(topicTmp.getTopicTitle()!=null)
   weixinTopic.setTopicTitle(topicTmp.getTopicTitle());	   
   if(topicTmp.getTopicContent()!=null)
   weixinTopic.setTopicContent(topicTmp.getTopicContent());	  
   if(topicTmp.getProductTitle()!=null)
   weixinTopic.setProductTitle(topicTmp.getProductTitle());
   if(topicTmp.getPid()!=null)
   weixinTopic.setPid(topicTmp.getPid());	      
   weixinTopicDao.update(weixinTopic);  
}

@Override
public void delete(String topicId)throws Exception{
	weixinTopicDao.deleteById(topicId);
}


@Override
@Transactional(rollbackFor=Throwable.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
public void deleteComment(String commentId)throws Exception{
	TopicComment topicComment=topicCommentDao.selectById(commentId);
	String topicId=topicComment.getTopicId();
	topicCommentDao.deleteById(commentId);
	WeixinTopic weixinTopic=weixinTopicDao.selectById(topicId);
	weixinTopic.setDiscussNum(weixinTopic.getDiscussNum()-1);
	weixinTopicDao.update(weixinTopic);	
}



@Override
@SuppressWarnings({ "rawtypes", "unchecked" })
public WeixinTopic topicDetail(String topicId)throws Exception{
	Map map=new HashMap();
	map.put("topicId", topicId);
	List<WeixinTopic> weixinlist=weixinTopicDao.select(map);
	WeixinTopic topicDetail=weixinlist.get(0);
	return topicDetail;
}


@SuppressWarnings({ "rawtypes", "unchecked" })
private Map convertSearch(WeixinTopicSearchCriteria criteria){
	Map map = new HashMap();	
	if(StringUtils.isNotBlank(criteria.getTopicTitle())){
		String topicTitle;
		if(criteria.getTopicTitle().contains("%")){
			topicTitle=criteria.getTopicTitle().replace("%","\\%");
		}else{
			topicTitle=criteria.getTopicTitle();
		}
		map.put("topicTitle", "%"+topicTitle+"%");
		
	}
	if( StringUtils.isNotBlank(criteria.getProductTitle())){
		String productTitle;
		if(criteria.getProductTitle().contains("%")){
			productTitle=criteria.getProductTitle().replace("%","\\%");
		}else{
			productTitle=criteria.getProductTitle();
		}
		map.put("productTitle", "%"+productTitle+"%");
	}
	
	if(StringUtils.isNotBlank(criteria.getStatus())){
		if(criteria.getStatus().equals("上线")){
			map.put("status", "RELEASED");
		}else if(criteria.getStatus().equals("关闭")){
			map.put("status", "OFFLINE");
		}else if(criteria.getStatus().equals("草稿")){
			map.put("status", "NEW");
		}
	}	
	return map;
}

private List<WeixinTopicVo> convertVoList(List<WeixinTopic> weixinTopic){
	List<WeixinTopicVo> VoList=new ArrayList<WeixinTopicVo>();
	for(WeixinTopic topictmp:weixinTopic){
		WeixinTopicVo votmp=new WeixinTopicVo();
		if(topictmp.getTopicId()!=null){
			votmp.setTopicId(topictmp.getTopicId());
		}
		if(topictmp.getTopicTitle()!=null){
			votmp.setTopicTitle(topictmp.getTopicTitle());
		}
		if(topictmp.getPid()!=null){
			votmp.setPid(topictmp.getPid());
		}
		if(topictmp.getGiftTitle()!=null){
			votmp.setGiftTitle(topictmp.getGiftTitle());	
		}
		if(topictmp.getReleasedTime()!=null){
			votmp.setReleasedTime(topictmp.getReleasedTime().toDateTime(DateTimeZone.forOffsetHours(8)).toString("yyyy-MM-dd HH:mm:ss"));
		}
		if(topictmp.getDiscussNum()!=null){
			votmp.setDiscussNum(topictmp.getDiscussNum().toString());
		}
		if(topictmp.getStatus()!=null){
			votmp.setStatus(topictmp.getStatus().getDesc());
		}
		if(topictmp.getProductTitle()!=null){
			votmp.setProductTitle(topictmp.getProductTitle());
		}
		
		VoList.add(votmp);
	}
	return VoList;
}

private List<CommentVo> convert2CommentListVo(List<TopicComment> topicCommentList){
	List<CommentVo> VoList=new ArrayList<CommentVo>();
	for(TopicComment temp:topicCommentList){
		CommentVo votmp=new CommentVo();
		if(temp.getCommentId()!=null)
		votmp.setCommentId(temp.getCommentId());
		if(temp.getNickName()!=null)
		votmp.setNickName(temp.getNickName());
		if(temp.getCommentTime()!=null)
		votmp.setCommentTime(temp.getCommentTime().toDateTime(DateTimeZone.forOffsetHours(8)).toString("yyyy-MM-dd HH:mm:ss"));
		if(temp.getPraiseNum()!=null)
		votmp.setPraiseNum(temp.getPraiseNum().toString());
		if(temp.getCommentDetail()!=null)
		votmp.setCommentDetail(temp.getCommentDetail());
		VoList.add(votmp);
	}
	return VoList;
}







}
