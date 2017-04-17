package com.ztravel.member.client.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.swordfish.thrift.util.JacksonUtil;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.HttpUtil;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.front.service.impl.MemberServiceImpl;
import com.ztravel.member.po.Member;
import com.ztravel.member.po.MemberTimeEntity;
import com.ztravel.member.po.WxUserEntity;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.reuse.member.service.IMemberReuseService;

@Service(value="memberClientService")
public class MemberClientServiceImpl implements IMemberClientService{

	@Resource
	private MemberDao memberDaoImpl ;
	@Resource
	private IdGeneratorUtil idGeneratorUtil;
	
	@Resource
	private IMemberReuseService memberReuseService ;

    private static final String USER_INFO_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "USER_INFO_URL", ConfScope.R) ;

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public MemberTimeEntity getMemberRegisterTimeById(String id) throws Exception {
		return memberDaoImpl.getMemberRegisterTime(id);
	}

	/**
	 * 手机号码是否注册
	 * */
	@Override
	public Boolean isMobileAlreadyExists(String mobile) {
		return memberReuseService.isMobileAlreadyExists(mobile) ;
	}

    @Override
    public void updatePasswordById(String memberId, String newPassword) throws Exception {
        Member member = new Member();
        member.setId(memberId);
        newPassword = SignUtil.signPassword(newPassword, SignUtil.FRONT_SIGN_KEY);
        member.setPassword(newPassword);
        member.setUpdateTime(DateTime.now());
        memberDaoImpl.updateByExample(member);
    }

	//随机取昵称
	@Override
	public String getRandomNickName() {
		return memberDaoImpl.getRandomNickName() ;
	}

	//获取用户默认头像mediaId
	@Override
	public String getRandomDefaultImageId() {
		String imageStr = redisClient.get(MemberConstants.DEAFULT_HEADER_IMG_KEY) ;
		String[] images = imageStr.split(",") ;
		Random random = new Random();
		return images[random.nextInt(images.length)] ;
	}

	/**
	 * 邮箱是否注册
	 * */
	@Override
	public Boolean isEmailAlreadyExists(String email) {
		return memberReuseService.isEmailAlreadyExists(email) ;
	}

	/**
	 * 统计会员数
	 * */
	@Override
	public Integer countAll() {
		return memberDaoImpl.countAll() ;
	}

	@Override
	public MemberSessionBean getMemberFromRedisBySID() throws Exception {
		return memberReuseService.getMemberFromRedisBySID() ;
	}

	//根据手机修改密码
	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void updateMemberPasswordByMobile(String mobile, String password) throws Exception{
		Integer records = memberDaoImpl.updateMemberPasswordByMobile(mobile, password) ;
		if(records > 1){
			Exception exception = new RuntimeException(ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
			LOGGER.error(String.format("错误代码[%s]:用户重置密码{mobile: %s}错误,影响记录数%s"
					,ResponseConstants.MEMB_UPDATE_LAST_LOGIN_DATE_ERROR_CODE, mobile, records), exception);
			throw exception ;
		}else if((records == 0)){
			LOGGER.error(String.format("用户重置密码{mobile: %s}失败,影响记录数【0】",mobile));
		}
	}

	//根据邮箱修改密码
	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void updateMemberPasswordByEmail(String email, String password)
			throws Exception {
		Integer records = memberDaoImpl.updateMemberPasswordByEmail(email, password) ;
		if(records > 1){
			Exception exception = new RuntimeException(ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
			LOGGER.error(String.format("错误代码[%s]:用户重置密码{email: %s}错误,影响记录数%s"
					,ResponseConstants.MEMB_UPDATE_LAST_LOGIN_DATE_ERROR_CODE, email, records), exception);
			throw exception ;
		}else if((records == 0)){
			LOGGER.error(String.format("用户重置密码{email: %s}失败,影响记录数【0】",email));
		}
	}

	/**
	 * 保存用户 昵称,email,recommender
	 * */
	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void saveMemberImproveData(String id, String nickName, String email,
			String recommender, String imageId) throws Exception {
		LOGGER.debug(String.format("用户完善资料{id: %s,nickName: %s,email: %s}", id, nickName, email));
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("id", id) ;
		params.put("nickName", nickName) ;
		params.put("email", email) ;
		params.put("recommender", recommender) ;
		params.put("imageId", imageId) ;
		Integer records = memberDaoImpl.updateMemberImproveDataById(params) ;
		if(records > 1){
			Exception exception = new RuntimeException(ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
			LOGGER.error(String.format("错误代码[%s]:用户完善资料{id: %s,nickName: %s,email: %s}错误,影响记录数%s"
					,ResponseConstants.MEMB_IMPROVEDATA_ERROR_CODE, id, nickName, email, records), exception);
			throw exception ;
		}else if((records == 0)){
			LOGGER.error(String.format("用户完善资料{id: %s,nickName: %s,email: %s}失败,影响记录数【0】",id, nickName, email));
		}
	}

	/**
	 * 向指定手机号码发送短信---注册
	 * */
	@Override
	public void sendRegisterSms(String mobile, String verifyCode) {
		LOGGER.debug(String.format("发送短信{手机号码: %s, 验证码: %s}", mobile, verifyCode));
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity() ;
		mobileCaptchaEntity.setContent(String.format("欢迎注册真旅行,验证码:%s", verifyCode));
		mobileCaptchaEntity.setMobileNum(mobile);
		mobileCaptchaEntity.setMsgType("注册");
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}

	/**
	 * 向指定手机号码发送短信---找回密码
	 * */
	@Override
	public void sendFindPasswordSms(String mobile, String verifyCode) {
		memberReuseService.sendFindPasswordSms(mobile, verifyCode);
	}

	//换一批头像
	@Override
	public List<String> getHeadImages(int count) {
		return memberReuseService.getHeadImages(count) ;
	}

	/**
	 * 保存用户头像 type : 1 上传形式 type : 0 默认形式
	 * */
	@Override
	public String saveMemberHeadImage(String id, String imageNameOrId, byte[] bytes, int type) throws Exception {
		return memberReuseService.saveMemberHeadImage(id, imageNameOrId, bytes, type) ;
	}

	@Override
	public String selectMemberByMobile(String mobile) {
		return memberReuseService.selectMemberByMobile(mobile) ;
	}

	@Override
	public String getMinMemberById(String id){
		Member member = memberDaoImpl.selectMinById(id);
		try {
			return JacksonUtil.obj2json(member);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public String getMemberIdByMid(String mid) {
		String memberId = "";
		try{
			Member member = memberDaoImpl.selectMemberByMid(mid);
			if(null != member){
				memberId = member.getId();
			}
		}catch(Exception e){
			LOGGER.error("根据mid:{} 查询member失败: "+e,	mid);
		}
		return memberId;
	}
	@Override
	public String getMemberByMid(String mid) {
		Member member = memberDaoImpl.selectMemberByMid(mid);
		try {
			return JacksonUtil.obj2json(member);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public String getNickNameByMid(String mid) {
		return memberReuseService.getNickNameByMid(mid) ;
	}

	@Override
	public String getRecommender(String id) {
		return memberDaoImpl.selectRecommender(id) ;
	}

	@Override
	public String getPassword(String id) {
		return memberDaoImpl.selectById(id).getPassword() ;
	}

	@Override
	public boolean checkMobileOnly(String mobile) throws Exception {
		return memberReuseService.checkMobileOnly(mobile) ;
	}

	@Override
	public void updateMemberBasicParam(String paramName, String paramValue, String id)throws Exception {
		memberReuseService.updateMemberBasicParam(paramName, paramValue, id);
	}

	@Override
    public Boolean isWxLogined(String memberId){
        Boolean wxLogind = false;
        int count = memberDaoImpl.countOpenidMemberByMemberId(memberId);
        if (count > 0) {
            wxLogind = true;
        }
        return wxLogind;
    }

    @Override
    public WxUserEntity selectWxUserByOpenId(String openId) {
        return memberDaoImpl.selectWxUserByOpenId(openId);
    }

    @Override
    public WxUserEntity insertWxUserByOpenId(String openId) throws Exception {
        String accessToken = redisClient.get("WECHAT_ACCESS_TOKEN");
        String url = USER_INFO_URL.replace("#ACCESS_TOKEN#", accessToken).replace("#OPEN_ID#", openId);
        LOGGER.info("获取用户基本信息(UnionID机制)时请求调用的URL为：" + url);
        String wxUserStr = HttpUtil.httpGet(url, "utf-8");
        JSONObject wxUserJson = JSONObject.parseObject(wxUserStr);
        LOGGER.info("获取用户基本信息(UnionID机制)时得到微信的返回JSON为：" + wxUserJson);
        if (wxUserJson == null || wxUserJson.containsKey("errcode")) {
            LOGGER.info("调用微信获取用户基本信息（包括UnionID机制）接口未得到用户信息，openid为：{}", openId);
            return null;
        }
        if (wxUserJson.getInteger("subscribe") != 1) {
            LOGGER.info("该微信用户尚未关注公众号，openid为：{}", openId);
            return null;
        }

        WxUserEntity wxUser = JSONObject.toJavaObject(wxUserJson, WxUserEntity.class);
        wxUser.setLastModifyTime(new DateTime());
        wxUser.setMpId(WechatAccountHolder.APP_ID);

        //插入微信用户表（公众号关注者）
        wxUser.setId(idGeneratorUtil.getWxUserId());
        memberDaoImpl.insertWxUser(wxUser);
        return wxUser;
    }

	@Override
	public String getMidById(String id) throws Exception {
		String mid ="";
		Member member = memberDaoImpl.selectById(id);
		if(member != null){
			mid = member.getMid();
		}
		return mid;
	}

    @Override
    public void bindMobileToMember(String id, String mobile, String password)
            throws Exception {
        Integer records = memberDaoImpl.updateMobileAndPasswordById(id, mobile, password);
        if((records == 0)){
            LOGGER.error(String.format("用户绑定手机号{id: %s}失败,影响记录数【0】",id));
        }
    }

    @Override
    public String getMobileById(String id) {
        Member member = memberDaoImpl.selectById(id);
        return member == null ? null : member.getMobile();
    }

    @Override
    public  String selectMemberIdByOpenId(String openId) {
        return memberDaoImpl.selectMemberIdByOpenId(openId);
    }

}
