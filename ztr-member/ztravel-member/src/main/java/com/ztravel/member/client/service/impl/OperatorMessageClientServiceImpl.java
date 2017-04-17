package com.ztravel.member.client.service.impl;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.entity.OperatorMessage;
import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.MessageType;
import com.ztravel.common.util.OperatorMessageContentUtil;
import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.member.dao.IOperatorMessageDao;
@Service
public class OperatorMessageClientServiceImpl implements IOperatorMessageClientService {
	private static Logger logger = RequestIdentityLogger.getLogger(OperatorMessageClientServiceImpl.class);
	@Resource
	IOperatorMessageDao operatorMessageDaoImpl;
	@Override
	public void add(MessageTitleType title, String mid, String productName,	String link) {
		try {
			OperatorMessage om = new OperatorMessage();
			om.setHasRead(false);
			om.setTime(DateTime.now());
			om.setTitle(title);
			om.setType(MessageType.valueOfIndex(title.getFindex()));
			om.setContent(OperatorMessageContentUtil.getContent(title, mid, productName));
			om.setLink(link);
			operatorMessageDaoImpl.add(om);
		} catch (Exception e) {
			logger.error("插入后台消息出错：", e);
		}
	}

}
