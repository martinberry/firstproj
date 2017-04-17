package com.ztravel.reuse.member.converter;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ztravel.reuse.member.entity.PrivateLetterVo;
import com.ztravel.member.po.Member;
import com.ztravel.member.po.PrivateLetter;

public class PrivateLetterConvert {
	public static List<PrivateLetterVo> Entitys2Vos(List<PrivateLetter> letters, List<Member> minMembers){
		List<PrivateLetterVo> vos = new LinkedList<PrivateLetterVo>();
		if(CollectionUtils.isNotEmpty(letters)){
			for(int i =0; i < letters.size(); i++){
				PrivateLetter le = letters.get(i);
				if(CollectionUtils.isEmpty(le.getMsgs())){
					continue;
				}
				Member mem = minMembers.get(i);
				vos.add(entity2Vo(le,mem));
			}
		}
		return vos;
	}

	public static PrivateLetterVo entity2Vo(PrivateLetter letter, Member minMember){
		PrivateLetterVo vo = new PrivateLetterVo();
		vo.setId(letter.getId().toString());
		vo.setAuthorId(letter.getAuthorId());
		vo.setAnotherId(letter.getAnotherId());
		vo.setHasRead(letter.isHasRead());
		vo.setUpdateTime(letter.getUpdateTime().toString("yyyy-MM-dd HH:mm"));
		vo.setAnotherNickname(minMember == null? "": minMember.getNickName());
		vo.setAnotherHead(minMember==null? "": minMember.getHeadImageId());
		vo.setMsgContent(letter.getMsgs().get(letter.getMsgs().size() -1).getContent());
		return vo;
	}

}
