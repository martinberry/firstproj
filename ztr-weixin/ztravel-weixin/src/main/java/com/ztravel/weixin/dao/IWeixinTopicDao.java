package com.ztravel.weixin.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.po.WeixinTopic;

public interface IWeixinTopicDao extends BaseDao<WeixinTopic>{
	public List<WeixinTopic> selectIndiscuss(Map params);
	
	public void discussNumAddOne(String topicId) ;
	
	List<WeixinTopic> selectByNumAndDate(Map params);
}
