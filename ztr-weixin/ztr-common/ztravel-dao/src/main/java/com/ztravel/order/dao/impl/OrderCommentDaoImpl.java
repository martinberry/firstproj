package com.ztravel.order.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.ztravel.common.entity.OrderComment;
import com.ztravel.common.entity.OrderCommentSearchEntity;
import com.ztravel.common.enums.OrderCommentStatus;
import com.ztravel.order.dao.IOrderCommentDao;


/**
 *
 * @author liuzhuo
 *
 */
@Repository
public class OrderCommentDaoImpl implements IOrderCommentDao{

	@Resource
	Datastore datastore;

	@Override
	public void insert(OrderComment orderComment) {
		datastore.save(orderComment);
	}

	@Override
	public void update(OrderComment orderComment) {

		Query<OrderComment> query = datastore.createQuery(OrderComment.class);
		query.field("_id").equal(orderComment.getId());

		UpdateOperations<OrderComment> ops = datastore.createUpdateOperations(OrderComment.class);
		ops.set("status", orderComment.getStatus());
		ops.set("operator", orderComment.getOperator());
		datastore.findAndModify(query, ops);
	}

	@Override
	public List<OrderComment> searchComment(OrderCommentSearchEntity searchEntity) {

		Query<OrderComment> query = buildCommonQuery(searchEntity);

		if( searchEntity.getPageNo() > 0 && searchEntity.getPageSize() > 0 ){
			int offset = (searchEntity.getPageNo()-1)*searchEntity.getPageSize();
			if(offset >= 0) {
				query.offset(offset);
			}
			query.limit(searchEntity.getPageSize());
		}

		query.order("-date");
		return query.asList();
	}

	@Override
	public Long countComment(OrderCommentSearchEntity searchEntity) {

		Query<OrderComment> query = buildCommonQuery(searchEntity);
		return query.countAll();
	}

	@Override
	public OrderComment getCommentDetail(String commentId) {
		Query<OrderComment> query = datastore.createQuery(OrderComment.class);

		query.field("_id").equal(new ObjectId(commentId));
		return query.get();
	}


	/**
	 * 构建查询器
	 * @param searchEntity
	 * @return
	 */
	public Query<OrderComment> buildCommonQuery(OrderCommentSearchEntity searchEntity) {
		Query<OrderComment> query = datastore.createQuery(OrderComment.class);

		if(StringUtils.isNotBlank(searchEntity.getMemberId())) {
			query.field("memberId").equal(searchEntity.getMemberId());
		}

		if( StringUtils.isNotBlank(searchEntity.getMid()) ){
			query.field("mid").equal(searchEntity.getMid());
		}

		if(StringUtils.isNotBlank(searchEntity.getProductId())) {
			query.field("productId").equal(searchEntity.getProductId());
		}

		if( StringUtils.isNotBlank(searchEntity.getPid()) ){
			query.field("pid").equal(searchEntity.getPid());
		}

		if(StringUtils.isNotBlank(searchEntity.getProductTitle())) {
			query.field("productTitle").contains(searchEntity.getProductTitle());
		}

		if( StringUtils.isNotBlank(searchEntity.getOrderId()) ){
			query.field("orderId").equal(searchEntity.getOrderId());
		}

		if( searchEntity.getDateBegin() != null ){
			query.filter("date > ", searchEntity.getDateBegin());
		}
		if( searchEntity.getDateEnd() != null ){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(searchEntity.getDateEnd());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dateEnd = calendar.getTime();
			query.filter("date < ", dateEnd);
		}

		if(null != searchEntity.getStatus()) {
			if(OrderCommentStatus.ALL != searchEntity.getStatus()) {
				query.field("status").equal(searchEntity.getStatus());
			}
		}
		return query;
	}

}
