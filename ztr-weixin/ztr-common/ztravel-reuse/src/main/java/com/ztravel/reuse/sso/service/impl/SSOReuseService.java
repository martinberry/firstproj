package com.ztravel.reuse.sso.service.impl;

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
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.CookieFactory;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.TokenUtil;
import com.ztravel.reuse.member.event.MemberEvent;
import com.ztravel.reuse.member.event.MemberEventType;
import com.ztravel.reuse.sso.service.ISSOReuseService;
import com.ztravel.sso.dao.SSODao;
import com.ztravel.sso.po.SSOBasicEntity;


@Service
public class SSOReuseService implements ISSOReuseService{
	
	private static final Logger logger = RequestIdentityLogger.getLogger(SSOReuseService.class);
	private static final RedisClient redisClient = RedisClient.getInstance();
	
	@Resource
	private SSODao ssoDao ;
	
	@Resource(name="memberEventBus")
	private EventBus memberEventBus;
	
	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Override
	public SSOBasicEntity login(String account, String password) {
		SSOBasicEntity entity = null ;
		try{
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
			if(entity == null){
				entity = new SSOBasicEntity() ;
				entity.setIsLogin(false);
			}else{
				entity.setIsLogin(entity.getIsActive());
				if(entity.getIsActive()){
					saveSSOBasicEntity(entity) ;
				}
				memberEventBus.post(new MemberEvent(entity.getId(), MemberEventType.LOGIN, TokenHolder.get()));
			}
		}catch(Exception e){
			entity = new SSOBasicEntity() ;
			entity.setIsLogin(false);
			logger.error("login fail...", e);
		}

		return entity;
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

	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void updateLastLoginDate(String id) throws Exception {
		Integer records = ssoDao.updateMemberLastLoginDate(id, DateTime.now()) ;
		if(records > 1){
			Exception exception = new RuntimeException(ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
			logger.error(String.format("错误代码[%s]:用户更新最后登录时间{id: %s}错误,影响记录数%s"
					,ResponseConstants.MEMB_UPDATE_LAST_LOGIN_DATE_ERROR_CODE, id, records), exception);
			throw exception ;
		}else if((records == 0)){
			logger.error(String.format("用户更新最后登录时间{id: %s}失败,影响记录数【0】",id));
		}
	}

	@Override
	public Cookie buildRememberMeCookie(String account, String signPassword){
		String signAccount = SignUtil.signSid(account, SignUtil.THREE_MONTH_MILLIS, SignUtil.FRONT_SIGN_KEY) ;
		Cookie accountCookie = CookieFactory.buildRememberMe(signAccount, SignUtil.THREE_MONTH_MILLIS) ;
		redisClient.set(signAccount,account + ":" + signPassword, SignUtil.THREE_MONTH_MILLIS) ;
		return accountCookie ;
	}

	@Override
    public SSOBasicEntity doLoginByMemberId(String memberId) {

        logger.debug("login by memberId: memberId{}", memberId);

        SSOBasicEntity entity = null;
        try {
            entity = ssoDao.selectById(memberId);
            if (entity == null) {
                entity = new SSOBasicEntity() ;
                entity.setIsLogin(false);
            } else {
                entity.setIsLogin(entity.getIsActive());
                if (entity.getIsActive()) {
                    saveSSOBasicEntity(entity) ;
                }
                memberEventBus.post(new MemberEvent(entity.getId(), MemberEventType.LOGIN, TokenHolder.get()));
            }
        } catch(Exception e) {
            entity = new SSOBasicEntity() ;
            entity.setIsLogin(false);
            logger.error("login by memberId fail...", e);
        }
        return entity;
    }

}
