package com.ztravel.sso.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.eventbus.EventBus;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.TokenUtil;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.reuse.member.event.MemberEvent;
import com.ztravel.reuse.member.event.MemberEventType;
import com.ztravel.reuse.sso.service.ISSOReuseService;
import com.ztravel.sso.dao.SSODao;
import com.ztravel.sso.po.SSOBasicEntity;
import com.ztravel.sso.service.SSOService;

/**
 * @author wanhaofan
 * */
@Service
public class SSOServiceImpl implements SSOService{

	private static final Logger logger = RequestIdentityLogger.getLogger(SSOServiceImpl.class);

	@Resource
	private SSODao ssoDao ;
	
	@Resource
	private ISSOReuseService ssoReuseService ;

	@Resource
	private IMemberClientService memberClientService ;

	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Resource(name="memberEventBus")
	private EventBus memberEventBus;

	@Override
	public SSOBasicEntity login(String account, String password) {
		return ssoReuseService.login(account, password) ;
	}

	private void saveSSOBasicEntity(SSOBasicEntity entity){
		try{
			MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
			memberSessionBean.setIsActive(entity.getIsActive());
			memberSessionBean.setIsLogin(entity.getIsLogin());
			memberSessionBean.setMid(entity.getMid());
			memberSessionBean.setMemberId(entity.getId());
			memberSessionBean.setMobile(entity.getMobile());
			memberSessionBean.setNickName(entity.getNickName());
			memberSessionBean.setImageId(entity.getHeadImageId());
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			TokenUtil.setTokens(entity.getId());
		}catch(Exception e){
			logger.error("refresh redis member data fail...", e);
		}
	}

	/**
	 * 保存用户 最近登录时间
	 * */
	@Override
	public void updateLastLoginDate(String id) throws Exception {
		ssoReuseService.updateLastLoginDate(id);
	}

	@Override
	public Cookie buildRememberMeCookie(String account, String signPassword){
		return ssoReuseService.buildRememberMeCookie(account, signPassword) ;
	}

	@Override
	public void doRegister(String account, String password) throws Exception {
		logger.debug("member register: account{}",account);
		password = SignUtil.signPassword(password , SignUtil.FRONT_SIGN_KEY) ;
		SSOBasicEntity entity = buildSSOBasicEntity(account, password) ;
		ssoDao.insert(entity);
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		memberSessionBean.setIsLogin(true);
		memberSessionBean.setIsActive(true);
		memberSessionBean.setMemberId(entity.getId());
		memberSessionBean.setMid(entity.getMid());
		memberSessionBean.setMobile(entity.getMobile());
		memberSessionBean.setImageId(entity.getHeadImageId());
		memberSessionBean.setNickName(entity.getNickName());
		SSOUtil.refreshMemberSessionBean(memberSessionBean);
		TokenUtil.setTokens(entity.getId());
		memberEventBus.post(new MemberEvent(entity.getId(), MemberEventType.RANDL, TokenHolder.get()));
	}

	private SSOBasicEntity buildSSOBasicEntity(String mobile, String password) throws Exception {
		SSOBasicEntity member = new SSOBasicEntity() ;
		DateTime now = new DateTime() ;
		member.setCreateTime(now);
		member.setUpdateTime(now);
		member.setLastLoginDate(now);
		member.setMobile(mobile);
		member.setHeadImageId(memberClientService.getRandomDefaultImageId());
		member.setNickName(memberClientService.getRandomNickName());
		if(password != null){
			member.setPassword(password);
		}
		member.setIsActive(true);
		member.setId(idGeneratorUtil.getMemberId()) ;
		member.setMid(idGeneratorUtil.getMId());
		return member ;
	}

	/**
	 * 使用微信用户信息构造会员对象
	 * @param openid
	 * @param nickname
	 * @param province
	 * @param city
	 * @param country
	 * @param headimgurl
	 * @return
	 * @throws Exception
	 */
	private SSOBasicEntity buildSSOBasicEntity(String openId, String nickName, String headImgUrl) throws Exception {
        SSOBasicEntity member = new SSOBasicEntity() ;
        DateTime now = new DateTime() ;
        member.setOpenId(openId);
        member.setCreateTime(now);
        member.setUpdateTime(now);
        member.setLastLoginDate(now);
        member.setHeadImageId(headImgUrl);
        member.setNickName(nickName);
        member.setIsActive(true);
        member.setId(idGeneratorUtil.getMemberId()) ;
        member.setMid(idGeneratorUtil.getMId());
        return member ;
    }

	@Override
	public boolean isPasswordSame(String account, String password){
		String signPassword = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY) ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("password", signPassword) ;
		params.put("account", account) ;
		SSOBasicEntity entity = null ;
		if(account.indexOf("@") != -1){
			//邮箱
			entity = ssoDao.selectByEmailPassword(params) ;
		}else {
			//电话
			entity = ssoDao.selectByMobilePassword(params) ;
		}
		return entity != null ;
	}

	/**
	 * 未登录注册的用户进行注册,添加发送短信,用户昵称生成规则不一致
	 * @author zhaopengfei
	 * */
	@Override
	public void doRegisterByGet(String account, String password,String email,String realName,String province,String city,String county,String area) throws Exception {
		logger.debug("member register: account{}",account);
		password = SignUtil.signPassword(password , SignUtil.FRONT_SIGN_KEY) ;
		SSOBasicEntity entity = buildSSOBasicEntity(account, password) ;
		entity.setNickName(account.substring(0, 3)+account.substring(3, 7).replaceAll("[0-9]", "*")+account.substring(7,11));
		entity.setRealName(realName);
		entity.setEmail(email);
		entity.setProvince(province);
		entity.setCity(city);
		entity.setArea(county);
        entity.setDetailAddress(area);
		ssoDao.insert(entity);
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		memberSessionBean.setIsLogin(true);
		memberSessionBean.setIsActive(true);
		memberSessionBean.setMemberId(entity.getId());
		memberSessionBean.setMid(entity.getMid());
		memberSessionBean.setMobile(entity.getMobile());
		memberSessionBean.setImageId(entity.getHeadImageId());
		memberSessionBean.setNickName(entity.getNickName());
		SSOUtil.refreshMemberSessionBean(memberSessionBean);
		TokenUtil.setTokens(entity.getId());
		memberEventBus.post(new MemberEvent(entity.getId(), MemberEventType.RANDL, TokenHolder.get()));
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity() ;
		mobileCaptchaEntity.setContent("感谢您预定真旅行，您的网站用户名为本手机号，您可登录真旅行网站个人中心查询订单、修改个人信息。");
		mobileCaptchaEntity.setMobileNum(entity.getMobile());
		mobileCaptchaEntity.setMsgType("密码找回");
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    public SSOBasicEntity doRegisterAndLoginByWx(String openId, String nickname, String headimgurl) throws Exception {
        logger.debug("register and login by wx-- openid:{}", openId);
        SSOBasicEntity entity = null;
        try {
            entity = ssoDao.selectByOpenId(openId);
            if (entity == null) {
                entity = buildSSOBasicEntity(openId, nickname, headimgurl);
                ssoDao.insert(entity);
            }
            entity.setIsLogin(entity.getIsActive());
            if(entity.getIsActive()){
                saveSSOBasicEntity(entity) ;
            }
            memberEventBus.post(new MemberEvent(entity.getId(), MemberEventType.RANDL, TokenHolder.get()));
        } catch(Exception e) {
            entity = new SSOBasicEntity() ;
            entity.setIsLogin(false);
            logger.error("register and login by wx fail...", e);
        }

        return entity;
    }

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    public void doBindOpenIdToMember(String openId, String memberId) throws Exception {
        logger.debug("bind openId to member-- openid :{}; memberId :{}", openId, memberId);
        Integer records = ssoDao.updateMemberOpenIdById(memberId, openId);
        if(records > 1){
            Exception exception = new RuntimeException(ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
            logger.error(String.format("错误代码[%s]:网站用户{id: %s}绑定微信用户{id: %s}错误,影响记录数%s"
                    ,ResponseConstants.MEMB_UPDATE_LAST_LOGIN_DATE_ERROR_CODE, memberId, openId, records), exception);
            throw exception ;
        }else if((records == 0)){
            logger.error(String.format("网站用户{id: %s}绑定微信用户{id: %s}失败,影响记录数【0】",memberId, openId));
        }

    }

    @Override
    public SSOBasicEntity doLoginByMemberId(String memberId) {

        return ssoReuseService.doLoginByMemberId(memberId) ;
    }

    @Override
    public SSOBasicEntity selectMemberByAccountAndPassword(String account, String password) {
        SSOBasicEntity entity = null ;

        Map<String, String> params = new HashMap<String, String>() ;
        params.put("password", password) ;
        params.put("account", account) ;
        if(account.indexOf("@") != -1){
            //邮箱
            entity = ssoDao.selectByEmailPassword(params) ;
        }else {
            //电话
            entity = ssoDao.selectByMobilePassword(params) ;
        }

        return entity;
    }

    @Override
    public SSOBasicEntity selectMemberByOpenId(String openId) {
        return ssoDao.selectByOpenId(openId);
    }

}
