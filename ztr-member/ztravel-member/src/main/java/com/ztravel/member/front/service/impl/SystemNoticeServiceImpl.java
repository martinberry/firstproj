package com.ztravel.member.front.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.member.dao.ISystemNoticeDao;
import com.ztravel.member.po.SystemNotice;
import com.ztravel.member.front.vo.MsgRequest;
import com.ztravel.member.front.service.ISystemNoticeService;
import com.ztravel.reuse.member.service.ISystemNoticeReuseService;

@Service
public class SystemNoticeServiceImpl implements ISystemNoticeService {

	@Resource
	ISystemNoticeDao systemNoticeDaoImpl;
	
	@Resource
	ISystemNoticeReuseService systemNoticeReuseService ;

	@Override
	public long countUnread(String memberId)throws Exception  {
		Assert.hasText(memberId);
		return systemNoticeDaoImpl.countUnread(memberId);
	}

	@Override
	public Pagination<SystemNotice> list(MsgRequest request)throws Exception  {
		Assert.hasText(request.getMemberId());
		return systemNoticeDaoImpl.list(request.getMemberId(), (request.getPageNo() -1)*request.getPageSize(), request.getPageSize());
	}

	@Override
	public List<SystemNotice> list(String memberId, Integer size)throws Exception {
		return systemNoticeReuseService.list(memberId, size) ;
	}

	@Override
	public boolean batchRead(List<String> noticeIds) throws Exception {
		long resultNum = systemNoticeDaoImpl.read(noticeIds);
		return resultNum == noticeIds.size()? true: false;
	}

	@Override
	public boolean add(String memberId, String noticeType, String content) throws Exception{
		return systemNoticeReuseService.add(memberId, NoticeType.valueOf(noticeType), content) ;
	}

	@Override
	public boolean add(SystemNotice entity) throws Exception{
		if(StringUtils.isBlank(entity.getMemberId())){
			throw new IllegalArgumentException("memberId is blank");
		}
		if(StringUtils.isBlank(entity.getContent())){
			throw new IllegalArgumentException("content is blank");
		}
		if(entity.getType() == null){
			throw new IllegalArgumentException("noticeType is null");
		}
		if(entity.getDateTime() == null){
			entity.setDateTime(DateTime.now());
		}
		return systemNoticeDaoImpl.add(entity);
	}

}
