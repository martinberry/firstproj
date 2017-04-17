package com.ztravel.reuse.member.service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.reuse.member.entity.PrivateLetterVo;

public interface IPrivateLetterReuseService {
	Pagination<PrivateLetterVo> list(String authorId, int pageNo, int pageSize);
	
	boolean addMsg(String authorId, String anotherId, String content) ;
	
	long count(String authorId) ;
	
	PrivateLetter detail(String authorId, String anotherId) ;
	
	boolean deleteAll(String letterId) ;
	
	boolean deleteOne(String letterId, String msgId) ;
}
