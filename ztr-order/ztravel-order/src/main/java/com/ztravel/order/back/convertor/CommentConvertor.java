package com.ztravel.order.back.convertor;

import java.util.ArrayList;
import java.util.List;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.ztravel.common.entity.OrderComment;
import com.ztravel.order.back.vo.OrderCommentVO;

public class CommentConvertor {

	public static List<OrderCommentVO> convertEntityList2VoList(List<OrderComment> comments){
		List<OrderCommentVO> commentVoList = new ArrayList<OrderCommentVO>();
		for(OrderComment comment : comments){
			OrderCommentVO commentVo = convertEntity2Vo(comment);
			commentVoList.add(commentVo);
		}
		return commentVoList;
	}


	public static OrderCommentVO convertEntity2Vo(OrderComment comment){
		OrderCommentVO commentVo = new OrderCommentVO();

		commentVo.setId(comment.getId().toString());
		commentVo.setPid(comment.getPid());
		commentVo.setProductTitle(comment.getProductTitle());
		commentVo.setMid(comment.getMid());
		commentVo.setContent(comment.getComment());
		commentVo.setStars(comment.getStars());
		commentVo.setStatus(comment.getStatus().getDesc());
		commentVo.setStatusEnum(comment.getStatus());
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		commentVo.setDate(format.format(comment.getDate()));
		commentVo.setDate(DateTimeUtil.convertDateToString("yyyy-MM-dd HH:mm", comment.getDate()));

		return commentVo;
	}

}
