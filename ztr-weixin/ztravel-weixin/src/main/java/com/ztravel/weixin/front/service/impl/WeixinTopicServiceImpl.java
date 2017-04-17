package com.ztravel.weixin.front.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.weixin.client.service.IWxUserClientService;
import com.ztravel.weixin.dao.ICommentPraiseDao;
import com.ztravel.weixin.dao.ITopicCommentDao;
import com.ztravel.weixin.dao.IWeixinTopicDao;
import com.ztravel.weixin.entity.WxUserEntity;
import com.ztravel.weixin.front.service.IWeixinTopicService;
import com.ztravel.weixin.front.vo.FrontCommentVo;
import com.ztravel.weixin.front.vo.FrontTopicVo;
import com.ztravel.weixin.operate.OpenIdOperator;
import com.ztravel.weixin.po.CommentPraise;
import com.ztravel.weixin.po.TopicComment;
import com.ztravel.weixin.po.WeixinTopic;


@Service
public class WeixinTopicServiceImpl implements IWeixinTopicService{
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(WeixinTopicServiceImpl.class);
	
	@Resource
	private IWeixinTopicDao weixinTopicDaoImpl;
	
	@Resource
	private ITopicCommentDao topicCommentDaoImpl;
	
	@Resource
	private ICommentPraiseDao commentPraiseDaoImpl;
	
	@Resource
	private IdGeneratorUtil idGeneratorUtil ;
	
    @Resource
    private IWxUserClientService wxUserService;
	
	@Override
	public WeixinTopic getTopicDetailById(String topicId)throws Exception{
		WeixinTopic topicDetail=weixinTopicDaoImpl.selectById(topicId);
		return topicDetail;
	}
	
	
	@Override
	@SuppressWarnings({ "rawtypes","unchecked"})	
	public List<TopicComment> getTopicCommentById(String topicId)throws Exception{
		Map map=new HashMap();
		map.put("topicId", topicId);
		List<TopicComment> topicCommentList=topicCommentDaoImpl.select(map);
		DateTime currentTime=new DateTime();
		long hourunit=(long)(60*60*1000);
		long dayunit=(long)(24*60*60*1000);
		for(TopicComment commenttmp:topicCommentList){
			long minusTime=currentTime.getMillis()-commenttmp.getCommentTime().getMillis();
			if(minusTime<=60*60*1000){
				commenttmp.setTimeStatus("刚刚");
			}else if(minusTime<=24*60*60*1000 && minusTime>60*60*1000){				
				 long hour=minusTime /hourunit;
				 int minushour=(int)hour;
				commenttmp.setTimeStatus(minushour+"小时前");
			}else{
				long day=minusTime/dayunit;
				int minusday=(int)day;
				commenttmp.setTimeStatus(minusday+"天前");
			}
		}
		return topicCommentList;
		
	}
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void submitComment(TopicComment entity) {
		TopicComment topicComment = new TopicComment() ;
		topicComment.setAnonymous(entity.getAnonymous());
		topicComment.setCommentDetail(entity.getCommentDetail());
		topicComment.setCommentId(entity.getCommentId());
		topicComment.setCommentTime(entity.getCommentTime());
		topicComment.setNickName(entity.getNickName());
		topicComment.setPraiseNum(entity.getPraiseNum());
		topicComment.setSubmiterId(entity.getSubmiterId());
		topicComment.setTimeStatus(entity.getTimeStatus());
		topicComment.setTopicId(entity.getTopicId());
		topicComment.setHeadImage(entity.getHeadImage());
		topicCommentDaoImpl.insert(topicComment);
		weixinTopicDaoImpl.discussNumAddOne(entity.getTopicId());
	}
	
	@Override
	@Transactional(rollbackFor=Throwable.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public AjaxResponse praiseAction(String openId,String commentId){
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			Map params=new HashMap();
			params.put("openId", openId);
			params.put("commentId",commentId);
			CommentPraise commentpraise=commentPraiseDaoImpl.selectByOpenIdAndCommentId(params);
			if(commentpraise==null){
				CommentPraise tmp=new CommentPraise();
				tmp.setCommentId(commentId);
				tmp.setFlag(1);
				tmp.setOpenId(openId);
				tmp.setPraiseId(idGeneratorUtil.getPraiseId());
				commentPraiseDaoImpl.insert(tmp);
				TopicComment topicComment=topicCommentDaoImpl.selectById(commentId);
			    topicComment.setPraiseNum(topicComment.getPraiseNum()+1);			
				topicCommentDaoImpl.update(topicComment);
				ajaxResponse.setRes_code("praiseAdd");
			}else if(commentpraise.getFlag()==0){
				commentpraise.setFlag(1);
				commentPraiseDaoImpl.update(commentpraise);
				TopicComment topicComment=topicCommentDaoImpl.selectById(commentId);
			    topicComment.setPraiseNum(topicComment.getPraiseNum()+1);			
				topicCommentDaoImpl.update(topicComment);	
				ajaxResponse.setRes_code("praiseAdd");
			}else if(commentpraise.getFlag()==1){
				commentpraise.setFlag(0);
				commentPraiseDaoImpl.update(commentpraise);
				TopicComment topicComment=topicCommentDaoImpl.selectById(commentId);
			    topicComment.setPraiseNum(topicComment.getPraiseNum()-1);			
				topicCommentDaoImpl.update(topicComment);
				ajaxResponse.setRes_code("praiseMinus");
			}
			
		}catch(Exception e){
			ajaxResponse.setRes_code("praiseFail");
			LOGGER.error(e.getMessage(), e);
			
		}
		return ajaxResponse;
		
	}
	
	@Override
	public void dealWxUserInfo (String code, String state,Model model) throws Exception{
		LOGGER.info("praiseAction请求的参数code为：" + code + ",state为：" + state + ".");
        if (!StringUtil.isEmpty(code) && !StringUtil.isEmpty(state) && "topic".equals(state)) {           
                JSONObject oAuthAccessToken = OpenIdOperator.oAuthAccessToken(code);
                if (oAuthAccessToken.getString("errcode") == null) {
                    //获取得到openid
                    String openid = oAuthAccessToken.getString("openid");
                    String access_token = oAuthAccessToken.getString("access_token");
                    JSONObject userBasicInfo = OpenIdOperator.getUserBasicInfo(access_token, openid);
                    if (userBasicInfo.getString("errcode") == null) {
                        WxUserEntity entity = wxUserService.selectWxUserInfoByOpenId(openid);
                        if (entity == null) {
                        	model.addAttribute("openId", openid);
                            WxUserEntity wxUser = JSONObject.toJavaObject(userBasicInfo, WxUserEntity.class);
                            wxUser.setLastModifyTime(new DateTime());
                            wxUser.setMpId(WechatAccountHolder.APP_ID);
                            //插入微信用户表
                            wxUser.setId(idGeneratorUtil.getWxUserId());
                            wxUserService.insertWxUserInfo(wxUser);
                        }
                    }
                    LOGGER.info("获得的openid为：" + openid);
                    OpenIdUtil.setOpenId(openid);
                }
            
        }
    }
	
	@Override
	public List<FrontCommentVo> convert2CommentListVo(List<TopicComment> topicCommentList){
		List<FrontCommentVo> VoList=new ArrayList<FrontCommentVo>();
		for(TopicComment temp:topicCommentList){
			FrontCommentVo votmp=new FrontCommentVo();
			if(temp.getCommentId()!=null)
			votmp.setCommentId(temp.getCommentId());
			if(temp.getNickName()!=null)
			votmp.setNickName(temp.getNickName());
			if(temp.getTimeStatus()!=null)
			votmp.setTimeStatus(temp.getTimeStatus());
			if(temp.getPraiseNum()!=null)
			votmp.setPraiseNum(temp.getPraiseNum().toString());
			if(temp.getCommentDetail()!=null)
			votmp.setCommentDetail(temp.getCommentDetail());
			if(temp.getHeadImage()!=null){
				votmp.setHeadImage(temp.getHeadImage());
			}
			votmp.setAnonymous(String.valueOf(temp.getAnonymous()));
			VoList.add(votmp);
		}
		return VoList;
	}
	
	@Override
	public FrontTopicVo convert2TopicVo(WeixinTopic weixinTopic){
		FrontTopicVo topicVo=new FrontTopicVo();
		if(weixinTopic.getDiscussNum()!=null){
			topicVo.setDiscussNum(weixinTopic.getDiscussNum().toString());
		}
		if(weixinTopic.getGiftContent()!=null){
			topicVo.setGiftContent(weixinTopic.getGiftContent());
		}
		if(weixinTopic.getGiftImage()!=null){
			topicVo.setGiftImage(weixinTopic.getGiftImage());
		}
		if(weixinTopic.getGiftTitle()!=null){
			topicVo.setGiftTitle(weixinTopic.getGiftTitle());
		}
		if(weixinTopic.getTopicContent()!=null){
			topicVo.setTopicContent(weixinTopic.getTopicContent());
		}
		if(weixinTopic.getTopicId()!=null){
			topicVo.setTopicId(weixinTopic.getTopicId());
		}
		if(weixinTopic.getTopicTitle()!=null){
			topicVo.setTopicTitle(weixinTopic.getTopicTitle());
		}
		
        return topicVo;
		
	}
	
	
}
