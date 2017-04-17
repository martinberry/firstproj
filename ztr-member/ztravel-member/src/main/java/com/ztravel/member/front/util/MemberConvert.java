package com.ztravel.member.front.util;

import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.Gender;
import com.ztravel.member.front.bo.MemberBo;
import com.ztravel.member.po.Member;

public class MemberConvert {

	public static Member MemberSessionBean2Po(MemberSessionBean memberSessionBean){
		Member member = new Member() ;
		member.setId(memberSessionBean.getMemberId()) ;
		member.setMid(memberSessionBean.getMid());
		return member ;
	}


	public static MemberBo MemberPo2Bo(Member member){
		MemberBo memberBo = new MemberBo() ;
		memberBo.setId(member.getId()) ;
		memberBo.setMid(member.getMid());
		memberBo.setArea(member.getArea());
		memberBo.setCity(member.getCity());
		memberBo.setProvince(member.getProvince());
		memberBo.setDetailAddress(member.getDetailAddress());
		memberBo.setCreatedBy(member.getCreatedBy());
		memberBo.setCreateTime(member.getCreateTime());
		memberBo.setLastLoginDate(member.getLastLoginDate());
		memberBo.setDescription(member.getDescription());
		memberBo.setEmail(member.getEmail());
		memberBo.setGender(Gender.valueOf(member.getGender()));
		memberBo.setIsActive(member.getIsActive());
		memberBo.setMobile(member.getMobile());
		memberBo.setNickName(member.getNickName());
		memberBo.setPassword(member.getPassword());
		memberBo.setRealName(member.getRealName());
		memberBo.setUpdatedBy(member.getUpdatedBy());
		memberBo.setUpdateTime(member.getUpdateTime());
		return memberBo ;
	}

	public static Member MemberBo2Po(MemberBo memberBo){
		Member member = new Member() ;
		member.setId(memberBo.getId()) ;
		member.setMid(memberBo.getMid());
		member.setArea(memberBo.getArea());
		member.setCity(memberBo.getCity());
		member.setProvince(memberBo.getProvince());
		member.setDetailAddress(memberBo.getDetailAddress());
		member.setCreatedBy(memberBo.getCreatedBy());
		member.setCreateTime(memberBo.getCreateTime());
		member.setLastLoginDate(memberBo.getLastLoginDate());
		member.setDescription(memberBo.getDescription());
		member.setEmail(memberBo.getEmail());
		member.setGender(memberBo.getGender().name());
		member.setIsActive(memberBo.getIsActive());
		member.setMobile(memberBo.getMobile());
		member.setNickName(memberBo.getNickName());
		member.setPassword(memberBo.getPassword());
		member.setRealName(memberBo.getRealName());
		member.setUpdatedBy(memberBo.getUpdatedBy());
		member.setUpdateTime(memberBo.getUpdateTime());
		return member ;
	}
}
