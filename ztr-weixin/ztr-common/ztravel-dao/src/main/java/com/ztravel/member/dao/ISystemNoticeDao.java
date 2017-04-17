package com.ztravel.member.dao;

import java.util.List;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.po.SystemNotice;

public interface ISystemNoticeDao {
	long countUnread(String memberId);
	Pagination<SystemNotice> list(String memberId, int offSet, int limit);
	long read(List<String> list);
	boolean add(SystemNotice entity);
	List<SystemNotice> list(String memberId,int limit);
}
