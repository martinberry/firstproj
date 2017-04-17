package com.ztravel.member.front.service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.reuse.member.entity.PrivateLetterVo;

public interface IPrivateLetterService {
	long count(String authorId);
	Pagination<PrivateLetterVo> list(String authorId, int pageNo, int pageSize);
	PrivateLetter detail(String authorId, String anotherId);
	boolean addMsg(String authorId, String anotherId, String content);
	boolean deleteAll(String letterId);
	boolean deleteOne(String letterId, String msgId);
}
