package com.ztravel.weixin.wechat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.sensitive.SensitiveValidator;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.weixin.entity.WxUserEntity;
import com.ztravel.weixin.front.service.IWeixinTopicService;
import com.ztravel.weixin.operate.OpenIdOperator;
import com.ztravel.weixin.po.TopicComment;
import com.ztravel.weixin.po.WeixinTopic;
import com.ztravel.weixin.topicdiscuss.vo.TopicCommentSubmitVo;
import com.ztravel.weixin.user.service.IWxUserService;


@Controller
@RequestMapping("/topicdiscuss")
public class TopicDiscussController {

    private static Logger logger = RequestIdentityLogger.getLogger(TopicDiscussController.class);

    @Resource
    private IdGeneratorUtil idGeneratorUtil ;

    @Resource
    private IWxUserService wxUserService;
    
    @Resource
    private IWeixinTopicService weixinTopicService ;
    
    @Resource
    private SensitiveValidator sensitiveValidator;

    @ResponseBody
    @RequestMapping(value = "/toSubmitDiscuss", method = RequestMethod.GET)
    public ModelAndView toSubmitDiscuss(HttpServletRequest request, Model model, String code, String state, String topicId) throws Exception {
        dealWxUserInfo (code, state, model);
        String openId = OpenIdUtil.getOpenId();
        //openId = "oSyr1we5si0PaiIyn0G3nmWhCytI" ;
        if (openId != null) {
            WxUserEntity wxUser = wxUserService.selectWxUserInfoByOpenId(openId);
            if (wxUser != null) {
                model.addAttribute("wxUser", wxUser);
            }
            WeixinTopic topic = weixinTopicService.getTopicDetailById(topicId) ;
            if(topic != null){
            	model.addAttribute("topic", topic);
            }
        }
        model.addAttribute("appId", WechatAccountHolder.APP_ID);
        return new ModelAndView("/weixin/topic/submit_topic_discuss");
    }
    
    @RequestMapping(value = "/submitDiscuss")
    @ResponseBody
    public AjaxResponse submitDiscuss(@RequestBody TopicCommentSubmitVo topicCommentSubmitVo){
    	AjaxResponse response = new AjaxResponse() ;
    	response.setRes_code("SUCCESS");
    	boolean hasSenstiveWord = sensitiveValidator.hasSensitiveWord(topicCommentSubmitVo.getComment()) ;
		if(hasSenstiveWord){
			return AjaxResponse.instance(ResponseConstants.SENSITIVE_WORD, "输入内容涉嫌违法信息，请重新输入");
		}
    	try{
    		TopicComment entity = new TopicComment() ;
        	entity.setAnonymous(topicCommentSubmitVo.getAnonymous());
        	if(topicCommentSubmitVo.getComment()!=null){
        		String commentDetail=topicCommentSubmitVo.getComment();
        		commentDetail=commentDetail.replace("&lt;", "<");
        		commentDetail=commentDetail.replace("&gt;", ">");
        		entity.setCommentDetail(commentDetail);
        	}
        	
    		entity.setCommentId(idGeneratorUtil.getTopicCommentId());
        	entity.setCommentTime(DateTime.now());
        	entity.setNickName(topicCommentSubmitVo.getNickName());
        	entity.setPraiseNum(0);
        	entity.setSubmiterId(topicCommentSubmitVo.getSubmiterId());
        	entity.setTimeStatus("刚刚");
        	entity.setTopicId(topicCommentSubmitVo.getTopicId());
        	entity.setHeadImage(topicCommentSubmitVo.getHeadImage());
        	weixinTopicService.submitComment(entity);
    	}catch(Exception e){
    		response.setRes_code("FAIL");
    		response.setRes_msg("提交评论失败,请重试");
    		logger.error(e.getMessage(), e);
    	}
    	return response ;
    }

    private void dealWxUserInfo (String code, String state, Model model) {
        logger.info("toSubmitDiscuss请求的参数code为：" + code + ",state为：" + state + ".");
        if (!StringUtil.isEmpty(code) && !StringUtil.isEmpty(state) && "topicDiscuss".equals(state)) {
            try {
                JSONObject oAuthAccessToken = OpenIdOperator.oAuthAccessToken(code);
                if (oAuthAccessToken.getString("errcode") == null) {
                    //获取得到openid
                    String openid = oAuthAccessToken.getString("openid");
                    String access_token = oAuthAccessToken.getString("access_token");
                    JSONObject userBasicInfo = OpenIdOperator.getUserBasicInfo(access_token, openid);
                    if (userBasicInfo.getString("errcode") == null) {
                        String nickName = userBasicInfo.getString("nickname");
                        String headimgurl = userBasicInfo.getString("headimgurl");
                        WxUserEntity wxUser = new WxUserEntity() ;
                        wxUser.setOpenid(openid);
                        wxUser.setNickname(nickName);
                        wxUser.setHeadimgurl(headimgurl);
                        model.addAttribute("wxUser", wxUser);

                        WxUserEntity entity = wxUserService.selectWxUserInfoByOpenId(openid);
                        if (entity == null) {
                            WxUserEntity wxUser2 = JSONObject.toJavaObject(userBasicInfo, WxUserEntity.class);
                            wxUser2.setLastModifyTime(new DateTime());
                            wxUser2.setMpId(WechatAccountHolder.APP_ID);
                            //插入微信用户表
                            wxUser2.setId(idGeneratorUtil.getWxUserId());
                            wxUserService.insertWxUserInfo(wxUser2);
                        }
                    }
                    logger.info("获得的openid为：" + openid);

                    OpenIdUtil.setOpenId(openid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
