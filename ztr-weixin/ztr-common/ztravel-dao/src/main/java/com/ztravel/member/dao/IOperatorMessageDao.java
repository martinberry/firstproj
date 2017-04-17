package com.ztravel.member.dao;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.entity.OperatorMessage;

public interface IOperatorMessageDao {
	void add(OperatorMessage entity);
	Pagination<OperatorMessage> getPage(Pagination<OperatorMessage> page);
	int readOne(String id);
	int deleteOne(String id);
	int countUnread();
}
