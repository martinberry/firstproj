package com.ztravel.member.opera.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.entity.OperatorMessage;
import com.ztravel.member.dao.IOperatorMessageDao;
import com.ztravel.member.opera.service.IOperatorMessageService;
import com.ztravel.member.opera.vo.OperatorMessageVo;
@Service
public class OperatorMessageServiceImpl implements IOperatorMessageService {
	private static Logger logger = RequestIdentityLogger.getLogger(OperatorMessageServiceImpl.class);
	@Resource
	IOperatorMessageDao operatorMessageDaoImpl;
	@Override
	public Pagination<OperatorMessageVo> getPage(int pageNo, int pageSize)throws Exception {
		Pagination<OperatorMessage> page= new Pagination<OperatorMessage>();
		Pagination<OperatorMessageVo> voPage = new Pagination<OperatorMessageVo>();
		page.setPageNo(pageNo);
		voPage.setPageNo(pageNo);
		page.setPageSize(pageSize);
		voPage.setPageSize(pageSize);
		try {
			page = operatorMessageDaoImpl.getPage(page);
			if(CollectionUtils.isNotEmpty(page.getData())){
				List<OperatorMessageVo> list = new ArrayList<OperatorMessageVo>();
				for(OperatorMessage o: page.getData()){
					list.add(convert2Vo(o));
				}
				voPage.setData(list);
			}
			voPage.setTotalItemCount((int)page.getTotalItemCount());
		} catch (Exception e) {
			logger.error("获取后台消息列表出错", e);
		}
		return voPage;
	}

	@Override
	public boolean readOne(String id) throws Exception {
		int updateCount = 0;
		try {
			updateCount = operatorMessageDaoImpl.readOne(id);
		} catch (Exception e) {
			logger.error("标记["+id+"]为已读出错:", e);
		}
		return updateCount == 1?true: false;
	}

	private OperatorMessageVo convert2Vo(OperatorMessage entity){
		OperatorMessageVo vo = new OperatorMessageVo();
		vo.setId(entity.getId() == null ? "": entity.getId().toString());
		vo.setType(entity.getType() == null? "": entity.getType().getDesc());
		vo.setTitle(entity.getTitle() == null? "": entity.getTitle().getDesc());
		vo.setTime(entity.getTime() == null? "": entity.getTime().toString("yyyy-MM-dd HH:mm:ss"));
		vo.setHasRead(entity.isHasRead());
		vo.setContent(entity.getContent());
		vo.setLink(entity.getLink());
		return vo;
	}

	@Override
	public boolean deleteOne(String id) throws Exception {
		int updateCount = 0;
		try {
			updateCount = operatorMessageDaoImpl.deleteOne(id);
		} catch (Exception e) {
			logger.error("删除["+id+"]出错:", e);
		}
		return updateCount == 1?true: false;
	}

	@Override
	public int countUnread() throws Exception {
		int updateCount = 0;
		try {
			updateCount = operatorMessageDaoImpl.countUnread();
		} catch (Exception e) {
			logger.error("查询后台消息未读数出错:", e);
		}
		return updateCount;
	}

}
