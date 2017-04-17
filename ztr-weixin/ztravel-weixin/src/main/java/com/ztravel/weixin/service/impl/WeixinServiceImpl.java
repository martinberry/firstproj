package com.ztravel.weixin.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.sso.client.service.OpenidMemberClientService;
import com.ztravel.sso.client.service.SSOClientService;
import com.ztravel.weixin.entity.WxUserEntity;
import com.ztravel.sso.po.OpenidMemberEntity;
import com.ztravel.sso.po.SSOBasicEntity;
import com.ztravel.weixin.operate.OpenIdOperator;
import com.ztravel.weixin.service.IWeixinService;
import com.ztravel.weixin.servlet.AccessTokenThread;
import com.ztravel.weixin.user.service.IWxUserService;

@Service
public class WeixinServiceImpl implements IWeixinService {

    private static Logger logger = RequestIdentityLogger.getLogger(WeixinServiceImpl.class);

    @Resource
    private IdGeneratorUtil idGeneratorUtil ;

    @Resource
    SSOClientService ssoClientServiceImpl;

    @Resource
    OpenidMemberClientService openidMemberClientServiceImpl;

    @Resource
    IWxUserService wxUserServiceImpl;

    private static RedisClient redisClient = RedisClient.getInstance();

    private String getSceneId(Map<String, String> requestMap) {
        String sceneId = null;
        String msgType = requestMap.get("MsgType");
        String openId = requestMap.get("FromUserName");
        if ("event".equals(msgType)) {
            String event = requestMap.get("Event");
            String eventKey = requestMap.get("EventKey");
            if ("subscribe".equals(event) && eventKey != null && eventKey.startsWith("qrscene_")) {
                sceneId = eventKey.replace("qrscene_", "");
            } else if ("subscribe".equals(event) && StringUtil.isEmpty(eventKey)) {
              //对于关注公众号的用户，默认注册成为真旅行用户。场景：扫描或者搜索关注真旅行公众号二维码（不包括扫描系统生成的带参二维码）
                dealSubscribeWithoutScene(openId);
            } else if ("unsubscribe".equals(event)) {
              //用户取消关注公众号时，释放掉微信号所绑定的用户信息（如果没有则不操作）
                dealUnsubscribe(openId);
            } else if (event.equals("SCAN") && eventKey != null) {
                sceneId = eventKey;
            }
        }
        return sceneId;
    }

    /**
     * 处理不带参数的关注推送事件
     * @param openId
     */
    private void dealSubscribeWithoutScene(String openId) {
        //对于关注公众号的用户，默认注册成为真旅行用户。场景：扫描或者搜索关注真旅行公众号二维码（不包括扫描系统生成的带参二维码）
        //1、注册生成真旅行用户(已注册或绑定过的微信号此处不处理)
        SSOBasicEntity entity = ssoClientServiceImpl.selectMemberByOpenId(openId);
        if (entity == null || entity.getId() == null) {
            try {
                JSONObject jsonObject = recordWxUserByOpenId(openId);
                String nickName = jsonObject.getString("nickname");
                String headImgUrl = jsonObject.getString("headimgurl");
                entity = ssoClientServiceImpl.doRegisterByWx(openId, nickName, headImgUrl);
            } catch (Exception e) {
                logger.error("注册生成真旅行用户出现异常！");
                e.printStackTrace();
            }
        }
        //2、微信号绑定刚生成的用户信息
        sceneTypeIsWxLogin(openId, entity.getId(), "subscribe");
    }

    /**
     * 处理取消关注事件
     * @param openId
     */
    private void dealUnsubscribe(String openId) {
        //用户取消关注公众号时，释放掉微信号所绑定的用户信息（如果没有则不操作）
        OpenidMemberEntity openidMember = openidMemberClientServiceImpl.selectOpenidMemberByOpenId(openId);
        if (openidMember != null) {
            openidMember = new OpenidMemberEntity();
            openidMember.setOpenId(openId);
            openidMember.setLoginTime(new DateTime());
            openidMemberClientServiceImpl.updateOpenidMemberToUnBind(openidMember);
        }
    }

    /**
     * 微信二维码扫描事件处理
     * @param requestMap
     * @throws Exception
     * 
     */
    @Override
    public void wxScan(Map<String, String> requestMap) throws Exception {

        String scenId = getSceneId(requestMap);
        if (StringUtil.isEmpty(scenId)) {
            return ;
        }
        logger.info("扫描带参数二维码事件所推送的二维码参数值为：{}", scenId);

        JSONObject sceneIdJson = redisClient.get(scenId, JSONObject.class);
        logger.info("根据二维码参数值 [{}] 在redis中获取到信息为：{}", scenId, sceneIdJson);
        if (sceneIdJson == null || sceneIdJson.getString("cookies") == null || sceneIdJson.getString("type") == null) {
            return ;
        }
        String openId = requestMap.get("FromUserName");
        String cookie = sceneIdJson.getString("cookies");
        String sceneType = sceneIdJson.getString("type");
        String memberId =  sceneIdJson.getString("memberId");

        if ("wxLogin".equals(sceneType) || "unbind".equals(sceneType) || "bind".equals(sceneType)) {
            if (memberId == null) {
                logger.info("当扫码场景为：1、扫码登陆微信端；2、扫码解绑微信；3、扫码绑定微信 之一时memberId不得为空");
                return ;
            }
        }

        if ("login".equals(sceneType)) { //场景一：扫码登陆网页
            sceneTypeIsLogin(openId, cookie);
        } else if ("wxLogin".equals(sceneType)) { //场景二：扫码登陆微信端
            sceneTypeIsWxLogin(openId, memberId, "scan");
            logger.info("用户个人信息二维码扫描成功。");
        }  else if ("unbind".equals(sceneType)) { //场景三：扫码解绑微信
            sceneTypeIsUnbind(openId, memberId, cookie);
        }  else if ("bind".equals(sceneType)) { //场景四：扫码绑定微信
            sceneTypeIsBind(openId, memberId, cookie);
        }

    }

    private void sceneTypeIsBind(String openId, String memberId, String cookie) throws Exception {
        SSOBasicEntity entity = ssoClientServiceImpl.selectMemberByOpenId(openId);
        JSONObject json = new JSONObject();
        if (entity == null) {
            recordWxUserByOpenId(openId);

            boolean flag = ssoClientServiceImpl.bindOpenIdToMember(memberId, openId);
            if (flag) {
                json.put("result_code", true);
                json.put("result_type", "bind");
            } else {
                json.put("result_code", false);
                json.put("result_type", "bind");
               // json.put("result_msg", "咦?账号异常,请与客服联系~");
            }
        } else {
            json.put("result_code", false);
            json.put("result_type", "bind");
            json.put("result_msg", "扫码的微信账号已经注册或绑定用户，不可重复绑定");
        }

        redisClient.set("scan_memberInfo:" + memberId + "&" + cookie, json);
        logger.info("用户绑定微信二维码扫描成功。redis保存键值为[{}, {}]", "scan_memberInfo:" + memberId + "&" + cookie, json);
    }

    private void sceneTypeIsUnbind(String openId, String memberId, String cookie) throws Exception {
        SSOBasicEntity entity = ssoClientServiceImpl.selectMemberByMemberId(memberId);
        String bindedOpenId = entity.getOpenId();
        JSONObject json = new JSONObject();
        if (!StringUtil.isEmpty(bindedOpenId) && bindedOpenId.equals(openId)) {
            boolean flag = ssoClientServiceImpl.unbindOpenIdFromMember(memberId);
            if (flag) {
                json.put("result_code", true);
                json.put("result_type", "unbind");
            } else {
                json.put("result_code", false);
                json.put("result_type", "unbind");
                //json.put("result_msg", "咦?账号异常,请与客服联系~");
            }
        } else {
            json.put("result_code", false);
            json.put("result_type", "unbind");
            json.put("result_msg", "解绑微信必须使用已绑定的微信账号进行扫码");
        }
        redisClient.set("scan_memberInfo:" + memberId + "&" + cookie, json);
        logger.info("用户解绑微信二维码扫描成功。redis保存键值为[{}, {}]", "scan_memberInfo:" + memberId + "&" + cookie, json);
    }

    private void sceneTypeIsWxLogin(String openId, String memberId, String type) {
        SSOBasicEntity entity = ssoClientServiceImpl.selectMemberByMemberId(memberId);
        if (entity.getIsActive()) {
            boolean flag = false;
            if ("subscribe".equals(type)) {
                flag = true;
            } else if ("scan".equals(type)) {
                int count = openidMemberClientServiceImpl.countOpenidMemberBymemberId(memberId);
                if (count == 0)  flag = true;
            }
            if (flag) {
                OpenidMemberEntity newOpenidMember = new OpenidMemberEntity();
                newOpenidMember.setOpenId(openId);
                newOpenidMember.setMemberId(memberId);
                OpenidMemberEntity openidMember = openidMemberClientServiceImpl.selectOpenidMemberByOpenId(openId);
                if (openidMember == null) {
                    openidMemberClientServiceImpl.insertOpenidMember(newOpenidMember);
                    logger.info("插入openid_member信息为[open_id:{}, member_id:{}]", openId, memberId);
                } else {
                    if (openidMember.getToken() != null) {
                        newOpenidMember.setPreMemberId(openidMember.getMemberId());
                    }
                    openidMemberClientServiceImpl.updateOpenidMember(newOpenidMember);
                    logger.info("更新openid_member信息为[open_id:{}, member_id:{},pre_member_id:{}]", openId, memberId, openidMember.getMemberId());
                }
            }
        } else {
            logger.info("用户[{}]为挂起状态，不能进行微信登陆操作", memberId);
        }

    }

    private void sceneTypeIsLogin(String openId, String cookie) throws Exception {

        JSONObject wxUserJson = recordWxUserByOpenId(openId);
        if (wxUserJson == null) {
            return ;
        }

        SSOBasicEntity ssoBasicEntity = ssoClientServiceImpl.selectMemberByOpenId(openId);
        JSONObject json = new JSONObject();
        if (ssoBasicEntity != null && ssoBasicEntity.getId() != null) {
            String memberId = ssoBasicEntity.getId();
            json.put("status", "old");
            json.put("memberId", memberId);
        } else {
            String nickName = wxUserJson.getString("nickname");
            String headImgUrl = wxUserJson.getString("headimgurl");
            json.put("status", "new");
            json.put("openId", openId);
            json.put("nickName", nickName);
            json.put("headImgUrl", headImgUrl);
        }
        if (redisClient.get("scan_login:" + cookie) != null) {
            logger.info("网页登陆二维码扫描时检测到redis中已有扫码记录。且redis保存键值为[{}, {}]", "scan_login:" + cookie, redisClient.get("scan_login:" + cookie));
            return ;
        }
        MemberSessionBean memberSessionBean = redisClient.get(cookie, MemberSessionBean.class) ;
        if (memberSessionBean != null && memberSessionBean.getIsLogin()) {
            logger.info("网页登陆二维码扫描时检测到redis中已经记录用户登陆信息记录");
            return ;
        }
        redisClient.set("scan_login:" + cookie, json);
        logger.info("网页登陆二维码扫描成功。redis保存键值为[{}, {}]", "scan_login:" + cookie, json);
    }

    private JSONObject recordWxUserByOpenId(String openId) throws Exception {

        WxUserEntity entity = wxUserServiceImpl.selectWxUserInfoByOpenId(openId);
        if (entity != null) {
            JSONObject wxUserJson = new JSONObject();
            wxUserJson.put("nickname", entity.getNickname());
            wxUserJson.put("headimgurl", entity.getHeadimgurl());
            return wxUserJson;
        }

        String accessToken = AccessTokenThread.getAccessToken();
        JSONObject wxUserJson = OpenIdOperator.getUserInfo(accessToken, openId);
        if (wxUserJson == null || wxUserJson.containsKey("errcode")) {
            logger.info("调用微信获取用户基本信息（包括UnionID机制）接口未得到用户信息，openid为：{}", openId);
            return null;
        }
        if (wxUserJson.getInteger("subscribe") != 1) {
            logger.info("该微信用户尚未关注公众号，openid为：{}", openId);
            return null;
        }

        WxUserEntity wxUser = JSONObject.toJavaObject(wxUserJson, WxUserEntity.class);
        wxUser.setLastModifyTime(new DateTime());
        wxUser.setMpId(WechatAccountHolder.APP_ID);
//        //请求微信返回速度过慢，暂不执行更新操作
//        //更新微信用户表（公众号关注者）
//        wxUserServiceImpl.updateWxUserInfo(wxUser);

            //插入微信用户表（公众号关注者）
            wxUser.setId(idGeneratorUtil.getWxUserId());
            wxUserServiceImpl.insertWxUserInfo(wxUser);

        return wxUserJson;
    }

}
