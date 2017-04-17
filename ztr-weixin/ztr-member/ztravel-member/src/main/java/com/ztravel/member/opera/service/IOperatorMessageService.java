package com.ztravel.member.opera.service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.opera.vo.OperatorMessageVo;

public interface IOperatorMessageService {
	Pagination<OperatorMessageVo> getPage(int pageNo, int pageSize)throws Exception;
	boolean readOne(String id)throws Exception;
	boolean deleteOne(String id)throws Exception;
	int countUnread()throws Exception;
}
