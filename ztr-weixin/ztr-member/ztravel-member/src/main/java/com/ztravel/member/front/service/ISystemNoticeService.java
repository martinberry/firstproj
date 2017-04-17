package com.ztravel.member.front.service;

import java.util.List;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.po.SystemNotice;
import com.ztravel.member.front.vo.MsgRequest;

public interface ISystemNoticeService {
	long countUnread(String memberId)throws Exception;
	Pagination<SystemNotice> list(MsgRequest request)throws Exception;
	boolean batchRead(List<String> noticeIds)throws Exception;
	boolean add(String memberId, String noticeType, String content)throws Exception;
	boolean add(SystemNotice entity)throws Exception;
	List<SystemNotice> list(String memberId, Integer size)throws Exception;
}
