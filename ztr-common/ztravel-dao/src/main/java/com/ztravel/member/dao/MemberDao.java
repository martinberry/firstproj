package com.ztravel.member.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.member.po.MemberTimeEntity;
import com.ztravel.member.po.WxUserEntity;
import com.ztravel.member.po.Member;

/**
 * @author wanhaofan
 * */
public interface MemberDao extends BaseDao<Member> {
	Integer updateMemberHeadImage(Member member) ;

	Integer updateMemberImproveDataById(Map params) ;

	String getRandomNickName() ;

	Integer updateMemberPasswordByMobile(String account, String password) ;

	Integer updateMemberPasswordByEmail(String email, String password) ;

	Integer updateByExample(Member member);

	Integer countByIdPassword(Map params);

	Integer countMobile(String mobile);

	Integer updateMemberHeadImageById(Map params) ;

	String selectByMobile(String mobile) ;

	MemberTimeEntity getMemberRegisterTime(String id) ;

	Integer countAll() ;

	/**
	 * 根据用户id查询用户部分信息
	 * @param id
	 * @return
	 */
	Member selectMinById(String id);

	List<Member> selectMinsByIds(List<String> ids);

	Member selectMemberByMid(String mid);

	String selectRecommender(String id) ;

    void insertWxUser(WxUserEntity wxUserEntity);

    WxUserEntity selectWxUserByOpenId(String openId);

	String selectWxNickNameByOpenId(String openId);

	Integer countOpenidMemberByMemberId(String memberId);

    Integer updateMobileAndPasswordById(String id, String mobile, String password);

    public String selectMemberIdByOpenId(String openId);

	int updateActiveByIds(Map params) throws SQLException;

}
