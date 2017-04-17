package com.ztravel.reuse.member.service;

import java.util.List;

import com.ztravel.common.enums.NoticeType;
import com.ztravel.member.po.SystemNotice;

public interface ISystemNoticeReuseService {
	boolean add(String memberId, NoticeType noticeType, String content);
	
	List<SystemNotice> list(String memberId, Integer size)throws Exception ;
}
