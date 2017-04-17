package com.ztravel.member.client.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.client.service.IPrivateLetterClientService;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.reuse.member.entity.PrivateLetterVo;
import com.ztravel.reuse.member.service.IPrivateLetterReuseService;
@Service
public class PrivateLetterClientServiceImpl implements IPrivateLetterClientService {

	@Resource
	private IPrivateLetterReuseService privateLetterReuseService;
	

	@Override
	public long count(String authorId) {
		return privateLetterReuseService.count(authorId) ;
	}

	@Override
	public Pagination<PrivateLetterVo> list(String authorId, int pageNo, int pageSize) {
		return privateLetterReuseService.list(authorId, pageNo, pageSize) ;
	}

	@Override
	public PrivateLetter detail(String authorId, String anotherId) {
		return privateLetterReuseService.detail(authorId, anotherId) ;
	}

	@Override
	public boolean addMsg(String authorId, String anotherId, String content) {
		return privateLetterReuseService.addMsg(authorId, anotherId, content) ;
	}

	@Override
	public boolean deleteAll(String letterId) {
		return privateLetterReuseService.deleteAll(letterId) ;
	}

	@Override
	public boolean deleteOne(String letterId, String msgId) {
		return privateLetterReuseService.deleteOne(letterId, msgId) ;
	}

}
