package com.ztravel.member.dao;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.member.po.PrivateMsg;

public interface IPrivateLetterDao {
	/**
	 * 用户未读会话数量
	 * @param authorId
	 * @return
	 */
	long count(String authorId);
	/**
	 * 分页获取用户会话列表
	 * @param authorId
	 * @param offset
	 * @param limit
	 * @return
	 */
	Pagination<PrivateLetter> list(String authorId, int offset, int limit);
	/**
	 * 获取某一会话的全部（暂时不分批）聊天记录
	 * @param letterId
	 * @return
	 */
	PrivateLetter detail(String authorId, String anotherId);
	/**
	 * 添加一条聊天记录，会同时修改两个PrivateLetter
	 * @param authorId
	 * @param anotherId
	 * @param content
	 */
	int addMsg(String authorId, String anotherId, PrivateMsg msg);
	/**
	 * 单方面清空一个会话，对方仍然保有聊天记录
	 * @param letterId
	 */
	int deleteAll(String letterId);
	/**
	 * 单方面删除一条对话记录，不会删除对方的该条记录
	 * @param letterId
	 * @param msgId
	 */
	int deleteOne(String letterId, String msgId);

	int read(String authorId, String anotherId);

	int countAll(String authorId);


}
