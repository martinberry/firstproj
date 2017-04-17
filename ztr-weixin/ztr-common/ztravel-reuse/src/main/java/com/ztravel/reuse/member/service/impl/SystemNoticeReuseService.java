package com.ztravel.reuse.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.member.dao.ISystemNoticeDao;
import com.ztravel.member.po.SystemNotice;
import com.ztravel.reuse.member.service.ISystemNoticeReuseService;


@Service
public class SystemNoticeReuseService implements ISystemNoticeReuseService{
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(SystemNoticeReuseService.class);
	
	@Resource
	ISystemNoticeDao systemNoticeDaoImpl;
	
	@Override
	public boolean add(String memberId, NoticeType noticeType, String content) {
		try {
			SystemNotice entity = new SystemNotice();
			entity.setMemberId(memberId);
			entity.setType(noticeType);
			entity.setContent(content);
			entity.setDateTime(DateTime.now());
			entity.setHasRead(false);
			return systemNoticeDaoImpl.add(entity);
		} catch (Exception e) {
			LOGGER.error("向用户["+memberId+"]发送["+noticeType.getDesc()+"]类型提醒失败", e);
			return false;
		}
	}

	@Override
	public List<SystemNotice> list(String memberId, Integer size)
			throws Exception {
		if(size == null){
			size = 18;
		}
		Assert.hasText(memberId);
		return systemNoticeDaoImpl.list(memberId, size);
	}

}
