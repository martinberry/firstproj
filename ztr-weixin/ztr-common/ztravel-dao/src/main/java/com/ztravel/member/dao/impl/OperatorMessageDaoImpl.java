package com.ztravel.member.dao.impl;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.entity.OperatorMessage;
import com.ztravel.member.dao.IOperatorMessageDao;
@Repository
public class OperatorMessageDaoImpl implements IOperatorMessageDao {

	@Resource
	private Datastore datastore ;

	@Override
	public void add(OperatorMessage entity) {
		datastore.save(entity);
	}
	
	@Override
	public Pagination<OperatorMessage> getPage(Pagination<OperatorMessage> page) {
		Query<OperatorMessage> query = datastore.createQuery(OperatorMessage.class);
		query.offset(page.getStart());
		query.limit(page.getPageSize());
		query.order("-time");
		page.setTotalItemCount((int)query.countAll());
		page.setData(query.asList());
		return page;
	}

	@Override
	public int readOne(String id) {
		Query<OperatorMessage> query = datastore.createQuery(OperatorMessage.class);
		UpdateOperations<OperatorMessage> ops = datastore.createUpdateOperations(OperatorMessage.class);
		query.field("_id").equal(new ObjectId(id));
		ops.set("hasRead", true);
		return datastore.update(query, ops).getUpdatedCount();
	}

	@Override
	public int deleteOne(String id) {
		return datastore.delete(OperatorMessage.class, new ObjectId(id)).getN();
	}

	@Override
	public int countUnread() {
		Query<OperatorMessage> query = datastore.createQuery(OperatorMessage.class);
		query.field("hasRead").equal(false);
		return (int)query.countAll();
	}

}
