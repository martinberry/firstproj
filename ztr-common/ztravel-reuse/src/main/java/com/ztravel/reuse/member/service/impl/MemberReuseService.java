package com.ztravel.reuse.member.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.mediaserver.client.CompressType;
import com.ztravel.mediaserver.client.MediaClientUtil;
import com.ztravel.mediaserver.client.MediaClientUtil.MediaType;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.po.Member;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.reuse.member.service.IMemberReuseService;


@Service
public class MemberReuseService implements IMemberReuseService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberReuseService.class);

	@Resource
	private MemberDao memberDaoImpl ;
	
	private static final RedisClient redisClient = RedisClient.getInstance();
	
	@Override
	public Boolean isEmailAlreadyExists(String email) {
		LOGGER.debug(String.format("校验邮箱是否已经存在{邮箱: %s}", email));
		Map<String,String> params = new HashMap<String,String>() ;
		params.put("email", email) ;
		return memberDaoImpl.count(params) >= 1;
	}

	@Override
	public Boolean isMobileAlreadyExists(String mobile) {
		LOGGER.debug(String.format("校验手机号码是否已经存在{手机号码: %s}", mobile));
		Map<String,String> params = new HashMap<String,String>() ;
		params.put("mobile", mobile) ;
		return memberDaoImpl.count(params) >= 1;
	}

	@Override
	public MemberSessionBean getMemberFromRedisBySID() throws Exception {
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		if(memberSessionBean == null || memberSessionBean.getMemberId() == null){
			ZtrBizException exception = ZtrBizException.instance(ResponseConstants.MEMB_NOTFOUND_ERROR_CODE, ResponseConstants.MEMB_NOTFOUND_ERROR_MSG);
			LOGGER.error(ResponseConstants.MEMB_NOTFOUND_ERROR_CODE, exception);
	        throw exception ;
		}
		return memberSessionBean ;
	}

	@Override
	public void sendFindPasswordSms(String mobile, String verifyCode) {
		LOGGER.debug(String.format("发送短信{手机号码: %s, 验证码: %s}", mobile, verifyCode));
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity() ;
		mobileCaptchaEntity.setContent(String.format("真旅行找回密码,验证码:%s", verifyCode));
		mobileCaptchaEntity.setMobileNum(mobile);
		mobileCaptchaEntity.setMsgType("密码找回");
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}

	@Override
	public List<String> getHeadImages(int count) {
		String imageStr = redisClient.get(MemberConstants.DEAFULT_HEADER_IMG_KEY) ;
		String[] images = imageStr.split(",") ;
		List<String> imageList = new LinkedList<String>() ;
		int maxCount = images.length;
		boolean noRepeat = maxCount>count;
		for(int i=0; i<count; i++){
			int index = (int)(Math.random()*maxCount);
			if(noRepeat){
				imageList.add(images[index]) ;
				images[index] = images[maxCount-1];
				maxCount--;
			}else{
				imageList.add(images[index]) ;
			}
		}
		return imageList;
	}
	
	/**
	 * 保存用户头像 type : 1 上传形式 type : 0 默认形式
	 * */
	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public String saveMemberHeadImage(String id, String imageNameOrId, byte[] bytes, int type) throws Exception {
		String mediaId = "" ;
		if(type == 1){
			mediaId = MediaClientUtil.uploadAndCompress(bytes, MediaType.IMAGE,imageNameOrId,CompressType.Normal);
		}else{
			mediaId = imageNameOrId ;
		}
		LOGGER.debug(String.format("用户更新头像{id: %s,mediaId: %s}",id , mediaId));
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("id", id) ;
		params.put("headImageId", mediaId) ;
		Integer records = memberDaoImpl.updateMemberHeadImageById(params) ;
		if(records > 1){
			Exception exception = new RuntimeException(ResponseConstants.MEMB_IMAGE_UPDATE_ERROR_MSG) ;
			LOGGER.error(String.format("错误代码[%s]:用户更新头像{id: %s,mediaId: %s}错误,影响记录数%s"
					,ResponseConstants.MEMB_IMAGE_UPDATE_ERROR_CODE, id , mediaId, records), exception);
			throw exception ;
		}else if((records == 0)){
			LOGGER.error(String.format("用户更新头像{id: %s,mediaId: %s}失败,影响记录数【0】",id, mediaId));
		}
		return mediaId ;
	}

	@Override
	public String selectMemberByMobile(String mobile) {
		return memberDaoImpl.selectByMobile(mobile) ;
	}

	@Override
	public boolean checkMobileOnly(String mobile) throws Exception {
		return memberDaoImpl.countMobile(mobile) >0?false:true;
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void updateMemberBasicParam(String paramName, String paramValue, String id)throws Exception {
		Member member = new Member();
		JSONObject json = new JSONObject();
		member.setId(id);
		Integer records = 0;
		switch (paramName){
		case "nickName":
			member.setNickName(paramValue);
			break;
		case "realName":
			member.setRealName(paramValue);
			break;
		case "gender":
			member.setGender(paramValue);
			break;
		case "mobile":
			json = JSONObject.parseObject(paramValue);
			String oldMobile = json.getString("oldMobile");
			String newMobile = json.getString("newMobile");
			String password = json.getString("password");
			password = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY) ;
			Member dbMember = memberDaoImpl.selectById(id);
			if(password == null || !password.equals(dbMember.getPassword())){
				throw new Exception("密码验证失败");
			}
			if(oldMobile == null || !oldMobile.equals(dbMember.getMobile())){
				throw new Exception("原号码验证失败");
			}
			member.setMobile(newMobile);
			break;
		case "email":
			json = JSONObject.parseObject(paramValue);
			String oldEmail = json.getString("oldEmail");
			String newEmail = json.getString("newEmail");
			String emailPassword = json.getString("password");
			password = SignUtil.signPassword(emailPassword, SignUtil.FRONT_SIGN_KEY) ;
			Member dmember = memberDaoImpl.selectById(id);
			if(password == null || !password.equals(dmember.getPassword())){
				throw new Exception("密码验证失败");
			}
			if(StringUtils.isNoneBlank(dmember.getEmail())){
				if(!oldEmail.equals(dmember.getEmail())){
					throw new Exception("原邮箱验证失败");
				}
			}else{
				if(StringUtils.isNoneBlank(oldEmail)){
					throw new Exception("原邮箱验证失败");
				}
			}
			member.setEmail(newEmail);
			break;
		case "password":
			json = JSONObject.parseObject(paramValue);
			String oldPassword = json.getString("oldPassword");
			String newPassword = json.getString("newPassword");
			String newPasswordRe = json.getString("newPasswordRe");
			Map<String, String> checkParam = new HashMap<String, String>();
			checkParam.put("id", id);
			oldPassword = SignUtil.signPassword(oldPassword, SignUtil.FRONT_SIGN_KEY) ;
			checkParam.put("password", oldPassword);
			Integer count =memberDaoImpl.countByIdPassword(checkParam);
			if(count == null || count <= 0){
				throw new Exception("原密码验证失败");
			}
			if(StringUtils.isBlank(newPassword)){
				throw new Exception("新密码不能为空");
			}
			if(!newPassword.equals(newPasswordRe)){
				throw new Exception("两次输入密码不一致");
			}
			newPassword = SignUtil.signPassword(newPassword, SignUtil.FRONT_SIGN_KEY) ;
			member.setPassword(newPassword);
			break;
		case "address":
			json = JSONObject.parseObject(paramValue);
			member.setProvince(json.getString("province"));
			member.setCity(json.getString("city"));
			member.setArea(json.getString("area"));
			member.setDetailAddress(json.getString("detailAddress"));
			break;
        case "bindMobile":
            json = JSONObject.parseObject(paramValue);
            String bindMobile = json.getString("bindMobile");
            String bindPassword = json.getString("bindPassword");
            bindPassword = SignUtil.signPassword(bindPassword, SignUtil.FRONT_SIGN_KEY) ;
            member.setMobile(bindMobile);
            member.setPassword(bindPassword);
            break;
		default :
		}
		member.setUpdateTime(DateTime.now());
		records = memberDaoImpl.updateByExample(member);
		if(records > 1){
			Exception exception = new RuntimeException("更新数据库出错") ;
			LOGGER.error(exception.getMessage(), exception);
			throw exception ;
		}else if(records == 1){
			LOGGER.info("更新个人资料成功:"+paramName+paramValue);
		}else if(records == 0){
			LOGGER.error("影响记录数【0】");
		}
	}

	@Override
	public String getNickNameByMid(String mid) {
		String nickName = "";
		try{
			Member member = memberDaoImpl.selectMemberByMid(mid);
			if(null != member){
				nickName = member.getNickName();
			}
		}catch(Exception e){
			LOGGER.error("根据mid:{} 查询nickName失败: "+e,	mid);
		}
		return nickName;
	}

}
