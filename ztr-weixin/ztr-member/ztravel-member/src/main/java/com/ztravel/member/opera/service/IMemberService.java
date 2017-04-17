package com.ztravel.member.opera.service;

import java.sql.SQLException;
import java.util.List;

import com.ztravel.member.opera.entity.MemberSearchCriteria;
import com.ztravel.member.opera.vo.MemberVO;

/**
 * @author
 *
 */
public interface IMemberService {
	public List<MemberVO> searchMembers(MemberSearchCriteria criteria) throws Exception;
	public Integer countMembers(MemberSearchCriteria criteria) throws Exception;
	public MemberVO searchMemberDetailById(String id) throws Exception;
	public void modifyMemberStatus(List<String> idList, boolean isActive) throws SQLException;
	public String getNickNameByMid(String mid);
	public String getWxNickNameById(String id) ;

}
