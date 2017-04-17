package com.ztravel.member.weixin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SMSType;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.dao.TravellerDao;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.member.po.Member;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;
import com.ztravel.member.weixin.service.IWxMemberService;
import com.ztravel.member.weixin.vo.MemberInfoParam;
import com.ztravel.member.weixin.vo.WMemberVO;
import com.ztravel.member.weixin.vo.WTravellerVO;

/**
 * @author MH
 */
@Service
public class WxMemberServiceImpl implements IWxMemberService {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(WxMemberServiceImpl.class);

	@Resource
	private MemberDao memberDao;

	@Resource
    private TravellerDao travellerDao;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public MemberSessionBean getMemberSessionBean() throws Exception {
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		if( memberSessionBean == null || memberSessionBean.getMemberId() == null ){
			ZtrBizException exception = ZtrBizException.instance(ResponseConstants.MEMB_NOTFOUND_ERROR_CODE, ResponseConstants.MEMB_NOTFOUND_ERROR_MSG);
			LOGGER.error(ResponseConstants.MEMB_NOTFOUND_ERROR_CODE, exception);
	        throw exception ;
		}
		return memberSessionBean;
	}

	@Override
	public WMemberVO getMemberInfoWithTravellers(String id) throws Exception {
		WMemberVO memVo = new WMemberVO();

		Member member = memberDao.selectById(id);

		memVo.setHeadImgId(member.getHeadImageId());
		memVo.setNickName(member.getNickName());
		memVo.setMobile(member.getMobile());
		memVo.setEmail(member.getEmail());
		memVo.setProvince(member.getProvince());
		memVo.setCity(member.getCity());
		memVo.setArea(member.getArea());
		memVo.setDetailAddress(member.getDetailAddress());

		String address = "";
		if( StringUtils.isNotBlank(member.getProvince()) ){
			address += member.getProvince();
		}
		if( StringUtils.isNotBlank(member.getCity()) ){
			address += member.getCity();
		}
		if( StringUtils.isNotBlank(member.getArea()) ){
			address += member.getArea();
		}
		if( StringUtils.isNotBlank(member.getDetailAddress()) ){
			address += member.getDetailAddress();
		}
		memVo.setAddress(address);

		//常旅客
		List<TravelerEntity> travellers = travellerDao.findByMemberId(id);
		List<WTravellerVO> travellerVoList = new ArrayList<WTravellerVO>();
		for(TravelerEntity traveller : travellers){
			WTravellerVO travellerVo = new WTravellerVO();
			travellerVo.setId(traveller.getId().toString());
			travellerVo.setTravelerNameCn(traveller.getTravelerNameCn());
			travellerVo.setTravelerNameEn(traveller.getTravelerNameEn());
			travellerVo.setMobile(traveller.getPhoneNum());
			travellerVoList.add(travellerVo);
		}
		memVo.setTravellers(travellerVoList);

		return memVo;
	}

	@Override
	public WMemberVO getMemberInfoWithoutTravellers(String id) throws Exception {
		WMemberVO memVo = new WMemberVO();

		Member member = memberDao.selectById(id);

		memVo.setHeadImgId(member.getHeadImageId());
		memVo.setNickName(member.getNickName());
		memVo.setRealName(member.getRealName());
		memVo.setGender(member.getGender());
		memVo.setEmail(member.getEmail());
		memVo.setMobile(member.getMobile());
		memVo.setProvince(member.getProvince());
		memVo.setCity(member.getCity());
		memVo.setArea(member.getArea());
		memVo.setDetailAddress(member.getDetailAddress());

		String address = "";
		if( StringUtils.isNotBlank(member.getProvince()) ){
			address += member.getProvince();
		}
		if( StringUtils.isNotBlank(member.getCity()) ){
			address += member.getCity();
		}
		if( StringUtils.isNotBlank(member.getArea()) ){
			address += member.getArea();
		}
		if( StringUtils.isNotBlank(member.getDetailAddress()) ){
			address += member.getDetailAddress();
		}
		memVo.setAddress(address);

		return memVo;
	}

	@Override
	public Boolean isMobileAlreadyExists(String mobile) {
		Map<String,String> params = new HashMap<String,String>() ;
		params.put("mobile", mobile) ;
		return memberDao.count(params) >= 1;
	}

	@Override
	public void sendChangeMobileSms(String mobile, String verificationCode) throws Exception {
		LOGGER.debug(String.format("发送短信{手机号码: %s, 验证码: %s}", mobile, verificationCode));
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity() ;
		mobileCaptchaEntity.setContent(String.format("真旅行修改手机号,验证码:%s", verificationCode));
		mobileCaptchaEntity.setMobileNum(mobile);
		mobileCaptchaEntity.setMsgType("修改手机号");
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}

	@Override
	public void updateMemberInfo(String memberId, MemberInfoParam memInfoParam) throws Exception {
		Member member = new Member();
		member.setId(memberId);
		buildMember(member, memInfoParam);
		Integer nRows = memberDao.updateByExample(member);
		if( nRows == 1 ){
			LOGGER.info("更新会员信息成功：" + member.toString());
		}else if( nRows > 1 ){
			LOGGER.debug("更新了多条数据记录");
		}else{
			LOGGER.debug("更新会员信息失败，会员id：" + memberId);
		}
	}

	private void buildMember(Member member, MemberInfoParam memInfoParam){
		member.setNickName(memInfoParam.getNickName());
		member.setRealName(memInfoParam.getRealName());
		member.setGender(memInfoParam.getGender());
		if( StringUtils.isNotBlank(memInfoParam.getNewMobile()) ){
			member.setMobile(memInfoParam.getNewMobile());
		}
		if(StringUtils.isNotBlank(memInfoParam.getNewEmail())){
			member.setEmail(memInfoParam.getNewEmail());
		}
		member.setProvince(memInfoParam.getProvince());
		member.setCity(memInfoParam.getCity());
		member.setArea(memInfoParam.getArea());
		member.setDetailAddress(memInfoParam.getDetailAddress());
		member.setUpdateTime(DateTime.now());
	}

	@Override
	public Boolean checkPasswordByMemberId(String memberId, String password) throws Exception {
		String signedPwd = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY);
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", memberId);
		params.put("password", signedPwd);
		Integer num = memberDao.countByIdPassword(params);
		if( num == null || num <= 0 ){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void updatePasswordById(String memberId, String newPassword) throws Exception {
		Member member = new Member();
		member.setId(memberId);
		newPassword = SignUtil.signPassword(newPassword, SignUtil.FRONT_SIGN_KEY);
		member.setPassword(newPassword);
		member.setUpdateTime(DateTime.now());
		memberDao.updateByExample(member);
	}

	@Override
	public AjaxResponse checkMobile(String mobile) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		if(isMobileAlreadyExists(mobile)){
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG) ;
		}
		return null;
	}

	@Override
	public void sendSMS(String mobile, String content, SMSType type) {
		LOGGER.debug(String.format("发送短信{手机号码: %s, 验证码: %s}", mobile, content));
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity() ;
		switch(type){
		case REGISTER:
			mobileCaptchaEntity.setContent(String.format("欢迎注册真旅行,验证码:%s", content));
			break ;
		case FIND_PASSWORD:
			mobileCaptchaEntity.setContent(String.format("真旅行找回密码,验证码:%s", content));
			break ;
        case COMMON:
            mobileCaptchaEntity.setContent(String.format("验证码:%s", content));
            break;
        case VOUCHERBINDMOBILE:
            mobileCaptchaEntity.setContent(String.format("【真旅行】购买成功，预售款已发送到个人电子钱包。请通过微信登陆或手机号" + mobile + "+初始密码%s登录真旅行www.zhenlx.com查收。", content));
            break ;
		default:break;
		}
		mobileCaptchaEntity.setMobileNum(mobile);
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}

	//随机取昵称
	@Override
	public String getRandomNickName() {
		return memberDao.getRandomNickName() ;
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
		LOGGER.debug(String.format("校验邮箱是否已经存在{邮箱: %s}", email));
		Map<String,String> params = new HashMap<String,String>() ;
		params.put("email", email) ;
		return memberDao.count(params) >= 1;
	}

	@Override
	public String getRecommender(String id) {
		return memberDao.selectRecommender(id) ;
	}

	@Override
	public String selectMemberByMobile(String mobile) {
		return memberDao.selectByMobile(mobile) ;
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void saveMemberImproveData(String id, String nickName, String email,
			String recommender) throws Exception {
		LOGGER.debug(String.format("用户完善资料{id: %s,nickName: %s,email: %s}", id, nickName, email));
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("id", id) ;
		params.put("nickName", nickName) ;
		params.put("email", email) ;
		params.put("recommender", recommender) ;
		Integer records = memberDao.updateMemberImproveDataById(params) ;
		if(records > 1){
			Exception exception = new RuntimeException(ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
			LOGGER.error(String.format("错误代码[%s]:用户完善资料{id: %s,nickName: %s,email: %s}错误,影响记录数%s"
					,ResponseConstants.MEMB_IMPROVEDATA_ERROR_CODE, id, nickName, email, records), exception);
			throw exception ;
		}else if((records == 0)){
			LOGGER.error(String.format("用户完善资料{id: %s,nickName: %s,email: %s}失败,影响记录数【0】",id, nickName, email));
		}
	}

	@Override
	public Member getMemberById(String id) throws Exception {
		return memberDao.selectById(id);
	}

	public Map<String, Object> getNickNameByPhone(String phone) {
		Map<String, Object> resultMap = Maps.newHashMap();
		String result = "success";
		String msg = "";
		String memberId = "";
		String idAndNickName = "";
		@SuppressWarnings("unused")
		String destMembeId = "" ;
		String id = "";

		Member destMember = new Member();
		try{
			idAndNickName =	memberDao.selectByMobile(phone);
		}catch(Exception e){
			result = "fail";
			msg="根据手机号查询用户失败";
			LOGGER.info("根据手机号查询用户失败",e);
		}

	    if(StringUtils.isNotEmpty(idAndNickName)){
	    	 id = idAndNickName.split(":")[0];
	    	 if(StringUtils.isNotEmpty(id)){
	    		 destMembeId = id;
	    		 destMember = memberDao.selectById(id);
	    	 }
	    }else{
	    	 result="fail";
	    	 msg="该好友未注册 ";
	    }
		if(StringUtils.isNotEmpty(memberId) && null!= destMember){
		      if(memberId.equals(id)){
		    	  result="fail";
		    	  msg="不能分享给自己";
		      }
		      if(StringUtils.isNotEmpty(idAndNickName)){
		    	  msg = idAndNickName.split(":")[1];
		      }

		}
		resultMap.put("msg", msg);
		resultMap.put("result", result);
		resultMap.put("destMember", destMember);


		return resultMap;
	}

    @Override
    public String getWxNickNameById(String id) throws Exception {
        Member member = memberDao.selectById(id);
        if (member != null && !StringUtil.isEmpty(member.getOpenId())) {
            String nickName = memberDao.selectWxNickNameByOpenId(member.getOpenId());
            return nickName;
        }
        return null;
    }
}
