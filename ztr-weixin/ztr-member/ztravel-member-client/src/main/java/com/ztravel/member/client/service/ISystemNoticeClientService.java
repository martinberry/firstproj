package com.ztravel.member.client.service;

import java.util.List;

import com.ztravel.common.enums.NoticeType;
import com.ztravel.member.po.SystemNotice;

public interface ISystemNoticeClientService {
	/**
	 * 插入一条提醒到数据库，返回生成记录ID
	 * @param memberId
	 * @param noticeType {@link NoticeType}
	 * @param content
	 * @return new id
	 * @throws Exception
	 */
	boolean add(String memberId, NoticeType noticeType, String content);

	List<SystemNotice> list(String memberId, Integer size)throws Exception ;
}
