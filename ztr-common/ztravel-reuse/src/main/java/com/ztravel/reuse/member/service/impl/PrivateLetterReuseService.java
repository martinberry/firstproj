package com.ztravel.reuse.member.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.member.dao.IPrivateLetterDao;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.reuse.member.converter.PrivateLetterConvert;
import com.ztravel.member.po.Member;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.member.po.PrivateMsg;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.reuse.member.entity.PrivateLetterVo;
import com.ztravel.reuse.member.service.IPrivateLetterReuseService;


@Service
public class PrivateLetterReuseService implements IPrivateLetterReuseService{
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(IPrivateLetterReuseService.class);
	
	@Resource
	private IPrivateLetterDao privateLetterDaoImpl;
	
	@Resource
	private MemberDao memberDaoImpl;

	@Override
	public Pagination<PrivateLetterVo> list(String authorId, int pageNo,
			int pageSize) {
		Pagination<PrivateLetterVo> voPage = new Pagination<PrivateLetterVo>();
		try {
			Pagination<PrivateLetter> page = privateLetterDaoImpl.list(authorId, (pageNo-1)*pageSize, pageSize);
			List<PrivateLetter> letters = (List<PrivateLetter>)page.getData();
			List<Member> minMembers = new LinkedList<Member>();
			if(CollectionUtils.isNotEmpty(letters)){
				for(PrivateLetter le: letters){
					minMembers.add(memberDaoImpl.selectMinById(le.getAnotherId()));
				}
				voPage.setPageNo(pageNo);
				voPage.setPageSize(pageSize);
				voPage.setTotalItemCount((int)privateLetterDaoImpl.countAll(authorId));
				voPage.setData(PrivateLetterConvert.Entitys2Vos(letters, minMembers));
			}
		} catch (Exception e) {
			LOGGER.error("获取私信列表出错", e);
		}
		return voPage ;
	}

	@Override
	public boolean addMsg(String authorId, String anotherId, String content) {
		PrivateMsg newMsg = new PrivateMsg();
		newMsg.setDateTime(DateTime.now());
		newMsg.setId(MemberConstants.PRIVATEMSG_ID_PREFIX+ newMsg.getDateTime().getMillis());
		newMsg.setMemberId(authorId);
		newMsg.setContent(content);
		int result = privateLetterDaoImpl.addMsg(authorId, anotherId, newMsg);
		return result == 2? true: false;
	}

	@Override
	public long count(String authorId) {
		return privateLetterDaoImpl.count(authorId);
	}

	@Override
	public PrivateLetter detail(String authorId, String anotherId) {
		privateLetterDaoImpl.read(authorId, anotherId);
		PrivateLetter pl = privateLetterDaoImpl.detail(authorId, anotherId) ;
		PrivateLetter entity = new PrivateLetter() ;
		if(pl != null){
			entity.setAnotherId(pl.getAnotherId());
			entity.setAuthorId(pl.getAuthorId());
			entity.setHasRead(pl.isHasRead());
			entity.setId(pl.getId());
			entity.setUpdateTime(pl.getUpdateTime());
			List<PrivateMsg> clientEntities = new ArrayList<PrivateMsg>() ;
			if(pl.getMsgs() != null){
				for(PrivateMsg msg : pl.getMsgs()){
					PrivateMsg msgEntity = new PrivateMsg() ;
					msgEntity.setContent(msg.getContent());
					msgEntity.setDateTime(msg.getDateTime());
					msgEntity.setId(msg.getId());
					msgEntity.setMemberId(msg.getMemberId());
					clientEntities.add(msgEntity) ;
				}
			}
			entity.setMsgs(clientEntities);
		}
		return entity ;
	}

	@Override
	public boolean deleteAll(String letterId) {
		int result = privateLetterDaoImpl.deleteAll(letterId);
		return result >=1? true: false;
	}

	@Override
	public boolean deleteOne(String letterId, String msgId) {
		int result = privateLetterDaoImpl.deleteOne(letterId, msgId);
		return result >=1? true: false;
	}

}
