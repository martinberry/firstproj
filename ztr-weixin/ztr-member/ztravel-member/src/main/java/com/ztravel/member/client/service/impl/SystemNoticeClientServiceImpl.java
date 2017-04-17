package com.ztravel.member.client.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.common.enums.NoticeType;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.member.dao.ISystemNoticeDao;
import com.ztravel.member.po.SystemNotice;
import com.ztravel.reuse.member.service.ISystemNoticeReuseService;
@Service
public class SystemNoticeClientServiceImpl implements
		ISystemNoticeClientService {

	@Resource
	private ISystemNoticeDao systemNoticeDaoImpl;
	
	@Resource
	ISystemNoticeReuseService systemNoticeReuseService ;
	
	@Override
	public boolean add(String memberId, NoticeType noticeType, String content){
		return systemNoticeReuseService.add(memberId, noticeType, content) ;
	}

	@Override
	public List<SystemNotice> list(String memberId, Integer size)throws Exception {
		return systemNoticeReuseService.list(memberId, size) ;
	}

}
