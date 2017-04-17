package com.ztravel.order.client.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.entity.OrderComment;
import com.ztravel.common.entity.OrderCommentSearchEntity;
import com.ztravel.common.enums.OrderCommentStatus;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.order.client.service.IOrderCommentClientService;
import com.ztravel.order.client.vo.OrderCommentClientVO;
import com.ztravel.order.dao.IOrderCommentDao;

/**
 * @author MH
 */
@Service
public class OrderCommentClientServiceImpl implements IOrderCommentClientService {

	@Resource
	IOrderCommentDao orderCommentDao;

	@Resource
	IMemberClientService memberClientService;
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(OrderCommentClientServiceImpl.class);

	@Override
	public List<OrderCommentClientVO> searchOrderComment(OrderCommentSearchEntity searchEntity) throws Exception {
		List<OrderComment> comments = orderCommentDao.searchComment(searchEntity);
		return buildCommentClientVOList(comments);
	}
	
	@Override
	public List<OrderCommentClientVO> searchOrderCommentByPid(String pid) throws Exception {
		LOGGER.info("开始查询产品:[]评论",pid);
		Assert.hasText(pid, "查询产品评论时，产品pid为空");
		OrderCommentSearchEntity searchEntity = new OrderCommentSearchEntity();
		searchEntity.setPid(pid);
		searchEntity.setStatus(OrderCommentStatus.PUBLISHED);
		List<OrderComment> comments = orderCommentDao.searchComment(searchEntity);
		LOGGER.info("产品:[]评论查询结束",pid);
		return buildCommentClientVOList(comments);
	}
	
	@Override
	public Long countOrderCommentByPid(String pid) throws Exception {
		LOGGER.info("开始统计产品:[]评论数",pid);
		Assert.hasText(pid, "统计产品评论数时，产品pid为空");
		OrderCommentSearchEntity searchEntity = new OrderCommentSearchEntity();
		searchEntity.setPid(pid);
		searchEntity.setStatus(OrderCommentStatus.PUBLISHED);
		return orderCommentDao.countComment(searchEntity);
	}

	@Override
	public Long countOrderComment(OrderCommentSearchEntity searchEntity) throws Exception {
		return orderCommentDao.countComment(searchEntity);
	}

	private List<OrderCommentClientVO> buildCommentClientVOList(List<OrderComment> commentList) {
		List<OrderCommentClientVO> result = new LinkedList<OrderCommentClientVO>();

		for(OrderComment orderComment : commentList) {
			OrderCommentClientVO ocvo = new OrderCommentClientVO();
			ocvo.setId(orderComment.getId().toString());
			ocvo.setStars(orderComment.getStars());
			ocvo.setComment(orderComment.getComment());
			ocvo.setDate(DateTimeUtil.convertDateToString("yyyy-MM-dd", orderComment.getDate()));

			String memInfo = memberClientService.getMinMemberById(orderComment.getMemberId());

			if(StringUtils.startsWith(memInfo, "{")) {
				JSONObject json = JSONObject.parseObject(memInfo);
				if(json.containsKey("nickName")) {
					ocvo.setMemNickName(json.getString("nickName"));
				}

				if(json.containsKey("id")) {
					ocvo.setMemid(json.getString("id"));
				}

				if(json.containsKey("headImageId")) {
					ocvo.setHeadImgId(json.getString("headImageId"));
				}
			}

			result.add(ocvo);
		}
		return result;
	}

}
