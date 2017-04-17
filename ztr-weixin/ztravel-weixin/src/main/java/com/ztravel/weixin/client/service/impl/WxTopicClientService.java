package com.ztravel.weixin.client.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.weixin.client.service.IWxTopicClientService;
import com.ztravel.weixin.dao.IWeixinTopicDao;
import com.ztravel.weixin.po.WeixinTopic;

@Service
public class WxTopicClientService implements IWxTopicClientService{
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(WxTopicClientService.class);
	
	@Resource
	private IWeixinTopicDao weixinTopicDaoImpl;
	
	@Override
	public List<WeixinTopic> getByProductId(String productId) throws Exception {
		Assert.hasText(productId, "查询产品相关话题时，产品ID为空");
		Map<String,String> params = new HashMap<String,String>();
		LOGGER.info("开始产品[{}]相关话题查询",productId);
		params.put("pid", productId);
		params.put("status", "RELEASED");
		List<WeixinTopic> topics = weixinTopicDaoImpl.selectByNumAndDate(params);
		LOGGER.info("完成产品[{}]相关话题查询,查询结果[{}]",productId,TZBeanUtils.describe(topics));
		return topics;
	}

}
