package com.ztravel.sso.client.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

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
import com.ztravel.member.client.utils.MemberUtils;
import com.ztravel.reuse.member.event.MemberEvent;
import com.ztravel.reuse.member.event.MemberEventType;
import com.ztravel.reuse.sso.service.ISSOReuseService;
import com.ztravel.sso.client.entity.SSOClientEntity;
import com.ztravel.sso.client.service.SSOClientService;
import com.ztravel.sso.dao.SSODao;
import com.ztravel.sso.po.SSOBasicEntity;

/**
 * @author wanhaofan
 * */
@Service
public class SSOServiceClientImpl implements SSOClientService{

	private static final Logger logger = RequestIdentityLogger.getLogger(SSOServiceClientImpl.class);

	@Resource
	private SSODao ssoDao ;
	
	@Resource
	private ISSOReuseService ssoReuseService ;

	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Resource
	private IMemberClientService memberClientService ;

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

	/**
	 * 注册  生成密码并自动发送
	 * @throws Exception
	 * ，则点击预定系统默认该用户注册，默认联系人手机号为用户名，随机分配八位查询密码（zlx+随机5位数字）
	 * ，随机分配昵称头像。并同步将用户名、密码、昵称将此信息以短信形式发送至该用户手机；页面跳转支付页面，浏览器返回为已登录状态
	 * */
	@Override
	public void doRegisterAndSendPassword(SSOClientEntity entity, boolean autoLogin) throws Exception {
		logger.debug(String.format("用户注册{手机号码: %s}", entity.getMobile()));
		String password = MemberUtils.randomPassword() ;
		entity.setPassword(SignUtil.signPassword(password , SignUtil.FRONT_SIGN_KEY));
		SSOBasicEntity basic = buildBasicEntity(entity) ;
		ssoDao.insert(basic) ;
		if(autoLogin){
			saveSSOBasicEntity(basic) ;
		}
		memberEventBus.post(new MemberEvent(basic.getId(), MemberEventType.RANDL, TokenHolder.get()));
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity() ;
		mobileCaptchaEntity.setContent(String.format("真旅行注册成功,用户名:%s,密码:%s,昵称:%s", basic.getMobile(), password, basic.getNickName()));
		mobileCaptchaEntity.setMobileNum(entity.getMobile());
		mobileCaptchaEntity.setMsgType("密码找回");
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}

	private SSOBasicEntity buildBasicEntity(SSOClientEntity entity) throws Exception {
		SSOBasicEntity basic = new SSOBasicEntity() ;
		DateTime now = new DateTime() ;
		basic.setCreateTime(now);
		basic.setUpdateTime(now);
		basic.setLastLoginDate(now);
		basic.setMobile(entity.getMobile());
		basic.setPassword(entity.getPassword());
		basic.setProvince(entity.getProvince());
		basic.setCity(entity.getCity());
		basic.setArea(entity.getArea());
		basic.setDetailAddress(entity.getDetailAddress());
		basic.setNickName(memberClientService.getRandomNickName());
		basic.setEmail(entity.getEmail());
		basic.setRealName(entity.getRealName());
		basic.setHeadImageId(memberClientService.getRandomDefaultImageId());
		basic.setIsActive(true);
		basic.setIsLogin(true);
		basic.setId(idGeneratorUtil.getMemberId()) ;
		basic.setMid(idGeneratorUtil.getMId());
		return basic ;
	}

    @Override
    public SSOBasicEntity selectMemberByOpenId(String openId) {
        return ssoDao.selectByOpenId(openId);
    }

    @Override
    public SSOBasicEntity doLoginByMemberId(String memberId) {

    	return ssoReuseService.doLoginByMemberId(memberId) ;
    }

    @Override
    public SSOBasicEntity selectMemberByMemberId(String memberId) {
        return ssoDao.selectById(memberId);
    }

    @Override
    public boolean unbindOpenIdFromMember(String memberId) throws Exception {
        boolean flag = false;
        logger.debug("unbind openId from member--  memberId :{}", memberId);
        Integer records = ssoDao.updateMemberOpenIdById(memberId, null);
        if(records > 1){
            Exception exception = new RuntimeException(ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
            logger.error(String.format("错误代码[%s]:网站用户{id: %s}j解绑微信用户错误,影响记录数%s"
                    ,ResponseConstants.MEMB_UPDATE_LAST_LOGIN_DATE_ERROR_CODE, memberId, records), exception);
            throw exception ;
        }else if((records == 0)){
            logger.error(String.format("网站用户{id: %s}解绑微信用户失败,影响记录数【0】",memberId));
        }else{
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean bindOpenIdToMember(String memberId, String openId) throws Exception {
        boolean flag = false;
        logger.debug("bind openId to member-- openid :{}; memberId :{}", openId, memberId);
        Integer records = ssoDao.updateMemberOpenIdById(memberId, openId);
        if(records > 1){
            Exception exception = new RuntimeException(ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
            logger.error(String.format("错误代码[%s]:网站用户{id: %s}绑定微信用户{id: %s}错误,影响记录数%s"
                    ,ResponseConstants.MEMB_UPDATE_LAST_LOGIN_DATE_ERROR_CODE, memberId, openId, records), exception);
            throw exception ;
        }else{
            flag = true;
        }
        return flag;
    }

    @Override
    public SSOBasicEntity doRegisterByWx(String openId, String nickName, String headImgUrl) throws Exception {
        logger.debug("register by wx-- openid:{}", openId);
        SSOBasicEntity entity = buildSSOBasicEntity(openId, nickName, headImgUrl) ;
        ssoDao.insert(entity);
        memberEventBus.post(new MemberEvent(entity.getId(), MemberEventType.REGISTER, null));
        return entity;
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

}
