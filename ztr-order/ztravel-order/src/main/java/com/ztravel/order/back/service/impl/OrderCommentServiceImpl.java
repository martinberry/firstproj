package com.ztravel.order.back.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.swordfish.thrift.util.JacksonUtil;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.entity.OrderComment;
import com.ztravel.common.entity.OrderCommentSearchEntity;
import com.ztravel.common.enums.OrderCommentStatus;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.order.back.convertor.CommentConvertor;
import com.ztravel.order.back.service.IOrderCommentService;
import com.ztravel.order.back.vo.OrderCommentVO;
import com.ztravel.order.dao.IOrderCommentDao;

@Service("commentService")
public class OrderCommentServiceImpl implements IOrderCommentService{

	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	IOrderCommentDao orderCommentDao;

	@Resource
	IMemberClientService memberClientService;

	@Override
	public AjaxResponse audit(String commentId, Integer auditResult) throws Exception {
		OrderComment comment = new OrderComment();
		comment.setId(new ObjectId(commentId));
		comment.setOperator(redisClient.get(OperatorSidHolder.get()));
		if( auditResult == 1 ){
			comment.setStatus(OrderCommentStatus.PUBLISHED);
		}else if( auditResult == 0 ){
			comment.setStatus(OrderCommentStatus.RETURN);
		}

		orderCommentDao.update(comment);

		return AjaxResponse.instance(OrderConstans.ORDER_COMMENT_AUDIT_SUCCESS_CODE, "");
	}

	@Override
	public List<OrderCommentVO> searchOrderComment(OrderCommentSearchEntity searchEntity) throws Exception {
		List<OrderComment> commentList = orderCommentDao.searchComment(searchEntity);
		List<OrderCommentVO> comments = CommentConvertor.convertEntityList2VoList(commentList);
		return comments;
	}

	@Override
	public Long countComments(OrderCommentSearchEntity searchEntity) throws Exception {
		return orderCommentDao.countComment(searchEntity);
	}

	@Override
	public OrderCommentVO getCommentDetail(String commentId) throws Exception {

		OrderComment comment = orderCommentDao.getCommentDetail(commentId);
		OrderCommentVO commentVo = CommentConvertor.convertEntity2Vo(comment);

		String memJson = memberClientService.getMinMemberById(comment.getMemberId());
		Map<String, Object> map = JacksonUtil.json2map(memJson);

		commentVo.setNickName(map.get("nickName").toString());
		return commentVo;
	}


}
