package com.ztravel.member.weixin.service;

import com.ztravel.common.adapter.sms.SMSType;
import com.ztravel.common.bean.AjaxResponse;
import java.util.Map;

import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.member.po.Member;
import com.ztravel.member.weixin.vo.MemberInfoParam;
import com.ztravel.member.weixin.vo.WMemberVO;

/**
 * @author MH
 */
public interface IWxMemberService {

	public MemberSessionBean getMemberSessionBean() throws Exception;
	/**
	 * 获取会员概要信息,包含常旅客
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WMemberVO getMemberInfoWithTravellers(String id) throws Exception;
	/**
	 * 获取会员详情信息,不包含常旅客
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WMemberVO getMemberInfoWithoutTravellers(String id) throws Exception;
	/**
	 * 保存会员信息
	 * @param memInfoParam
	 * @throws Exception
	 */
	public void updateMemberInfo(String memberId, MemberInfoParam memInfoParam) throws Exception;
	/**
	 * 手机号是否已注册
	 * @param mobile
	 * @return
	 */
	public Boolean isMobileAlreadyExists(String mobile);
	/**
	 * 发送修改手机号验证短信
	 * @param mobile
	 * @param verificationCode
	 */
	public void sendChangeMobileSms(String mobile, String verificationCode) throws Exception;
	/**
	 * 检查密码输入是否正确
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Boolean checkPasswordByMemberId(String memberId, String password) throws Exception;
	/**
	 * 修改用户密码
	 * @param memberId
	 * @param newPassword
	 * @throws Exception
	 */
	public void updatePasswordById(String memberId, String newPassword) throws Exception;


	AjaxResponse checkMobile(String mobile) ;

	void sendSMS(String mobile, String content, SMSType type) ;

	String getRandomNickName() ;

	String getRandomDefaultImageId() ;

	/**
	 * 邮箱是否注册
	 * */
	public Boolean isEmailAlreadyExists(String email) ;


	public String getRecommender(String id) ;

	public String selectMemberByMobile(String mobile) ;

	public void saveMemberImproveData(String id, String nickName, String email,
			String recommender) throws Exception ;

	public Member getMemberById(String id) throws Exception ;

	Map<String, Object> getNickNameByPhone(String phone);

    public String getWxNickNameById(String id) throws Exception ;

}
