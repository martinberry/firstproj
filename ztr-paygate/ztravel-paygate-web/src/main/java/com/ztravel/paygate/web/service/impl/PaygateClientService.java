package com.ztravel.paygate.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.framework.dao.rdbms.SequenceGenerator;
import com.ztravel.paygate.core.dao.IPaygateClientDao;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.po.gen.PaygateClientExample;
import com.ztravel.paygate.web.service.IPaygateClientService;

/**
 * 网关客户端
 * 
 * @author dingguangxian
 * 
 */
@Service("paygate_client_service")
public class PaygateClientService implements IPaygateClientService {

	private static Logger logger = LoggerFactory.getLogger(PaygateClientService.class);
	private static final String SEQUENCE_NAME = "paygate_client_seq";

	@Resource
	private SequenceGenerator sequenceGenerator;
	@Resource
	private IPaygateClientDao entityDao;

	public PaygateClient findByClientId(String clientId) throws Exception {
		PaygateClientExample example = new PaygateClientExample();
		example.createCriteria().andClientIdEqualTo(clientId);
		List<PaygateClient> list = entityDao.selectByExample(example);
		if (list != null && list.size() > 1) {
			throw new RuntimeException("too many entity found with clientId(" + clientId + ")");
		}
		return list == null || list.size() == 0 ? null : list.get(0);
	}
}
