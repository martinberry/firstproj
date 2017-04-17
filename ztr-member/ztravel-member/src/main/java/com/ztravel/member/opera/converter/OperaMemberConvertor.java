/**
 *
 */
package com.ztravel.member.opera.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztravel.member.opera.vo.MemberVO;
import com.ztravel.member.po.Member;

/**
 * @author
 *
 */
public class OperaMemberConvertor {
	public static MemberVO convertPOtoVO(Member member) throws Exception{
		MemberVO memVO = new MemberVO();
		if( StringUtils.isNotBlank(member.getId()) )
			memVO.setId(member.getId());
		else
			memVO.setId("");

        if( StringUtils.isNotBlank(member.getMid()) )
        	memVO.setMemberId(member.getMid());
        else
        	memVO.setMemberId("");

		if( StringUtils.isNotBlank(member.getRealName()) )
			memVO.setRealName(member.getRealName());
		else
			memVO.setRealName("");

		if( StringUtils.isNotBlank(member.getNickName()) )
			memVO.setNickName(member.getNickName());
		else
			memVO.setNickName("");

		if( StringUtils.isNotBlank(member.getGender()) )
			memVO.setGender(member.getGender());
		else
			memVO.setGender("");

		if( StringUtils.isNotBlank(member.getMobile()) )
			memVO.setMobile(member.getMobile());
		else
			memVO.setMobile("");

		if( StringUtils.isNotBlank(member.getEmail()) )
			memVO.setEmail(member.getEmail());
		else
			memVO.setEmail("");

		memVO.setPurchaseAmount(0);
		if( member.getIsActive() != null ){
			memVO.setIsActive(member.getIsActive());
		}

		if( StringUtils.isNotBlank(member.getProvince()) )
			memVO.setProvince(member.getProvince());
		else
			memVO.setProvince("");

		if( StringUtils.isNotBlank(member.getCity()) )
			memVO.setCity(member.getCity());
		else
			memVO.setCity("");

		if( StringUtils.isNotBlank(member.getArea()) )
			memVO.setArea(member.getArea());
		else
			memVO.setArea("");

		if( StringUtils.isNotBlank(member.getDetailAddress()) )
			memVO.setDetailAddress(member.getDetailAddress());
		else
			memVO.setDetailAddress("");

        if( StringUtils.isNotBlank(member.getOpenId()) )
            memVO.setOpenId(member.getOpenId());
        else
            memVO.setOpenId("");

//		String province;
//		if( StringUtils.isNotBlank(member.getProvince()) ){
//			province = member.getProvince();
//		}else{
//			province = "";
//		}
//		String city;
//		if( StringUtils.isNotBlank(member.getCity()) ){
//			city = member.getCity();
//		}else{
//			city = "";
//		}
//		memVO.setRegion(province+city);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if( member.getLastLoginDate() !=null ){
			memVO.setLatestLoginDate(dateFormat.format(new Date(member.getLastLoginDate().getMillis())));
			memVO.setLatestLoginTime(timeFormat.format(new Date(member.getLastLoginDate().getMillis())));
		}else{
			memVO.setLatestLoginDate("");
			memVO.setLatestLoginTime("");
		}
		if( member.getCreateTime() != null ){
			memVO.setRegisterTime(dateTimeFormat.format(new Date(member.getCreateTime().getMillis())));
		}else{
			memVO.setRegisterTime("");
		}
		memVO.setHeadImgId(member.getHeadImageId());
		return memVO;
	}
	
	public static List<MemberVO> convertListPOtoListVO(List<Member> memList){//
		List<MemberVO> memVOList = new ArrayList<MemberVO>();
		for(Member mem : memList){
			MemberVO memVO = new MemberVO();
			if( StringUtils.isNotBlank(mem.getId()) )
				memVO.setId(mem.getId());
			else
				memVO.setId("");

            if( StringUtils.isNotBlank(mem.getMid()) )
            	memVO.setMemberId(mem.getMid());
            else
            	memVO.setMemberId("");

			if( StringUtils.isNotBlank(mem.getRealName()) )
				memVO.setRealName(mem.getRealName());
			else
				memVO.setRealName("");

			if( StringUtils.isNotBlank(mem.getNickName()) )
				memVO.setNickName(mem.getNickName());
			else
				memVO.setNickName("");

			if( StringUtils.isNotBlank(mem.getGender()) )
				memVO.setGender(mem.getGender());
			else
				memVO.setGender("");

			if( StringUtils.isNotBlank(mem.getMobile()) )
				memVO.setMobile(mem.getMobile());
			else
				memVO.setMobile("");

			if( StringUtils.isNotBlank(mem.getEmail()) )
				memVO.setEmail(mem.getEmail());
			else
				memVO.setEmail("");

			memVO.setPurchaseAmount(0);
			if( mem.getIsActive() != null ){
				memVO.setIsActive(mem.getIsActive());
			}

			if( StringUtils.isNotBlank(mem.getProvince()) )
				memVO.setProvince(mem.getProvince());
			else
				memVO.setProvince("");

			if( StringUtils.isNotBlank(mem.getCity()) )
				memVO.setCity(mem.getCity());
			else
				memVO.setCity("");

			if( StringUtils.isNotBlank(mem.getArea()) )
				memVO.setArea(mem.getArea());
			else
				memVO.setArea("");

			if( StringUtils.isNotBlank(mem.getDetailAddress()) )
				memVO.setDetailAddress(mem.getDetailAddress());
			else
				memVO.setDetailAddress("");

			if( StringUtils.isNotBlank(mem.getOpenId()) )
                memVO.setOpenId(mem.getOpenId());
            else
                memVO.setOpenId("");

//			String province;
//			if( StringUtils.isNotBlank(mem.getProvince()) ){
//				province = mem.getProvince();
//			}else{
//				province = "";
//			}
//			String city;
//			if( StringUtils.isNotBlank(mem.getCity()) ){
//				city = mem.getCity();
//			}else{
//				city = "";
//			}
//			memVO.setRegion(province+city);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if( mem.getLastLoginDate() !=null ){
				memVO.setLatestLoginDate(dateFormat.format(new Date(mem.getLastLoginDate().getMillis())));
				memVO.setLatestLoginTime(timeFormat.format(new Date(mem.getLastLoginDate().getMillis())));
			}else{
				memVO.setLatestLoginDate("");
				memVO.setLatestLoginTime("");
			}
			if( mem.getCreateTime() != null ){
				memVO.setRegisterTime(dateTimeFormat.format(new Date(mem.getCreateTime().getMillis())));
			}else{
				memVO.setRegisterTime("");
			}

			memVOList.add(memVO);
		}
		return memVOList;
	}

}
