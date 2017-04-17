package com.ztravel.member.dao.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.dao.ISystemNoticeDao;
import com.ztravel.member.po.SystemNotice;

@Repository
public class SystemNoticeDaoImpl implements ISystemNoticeDao {

	@Resource
	private Datastore datastore ;

	@Override
	public long countUnread(String memberId) {
		Query<SystemNotice> query = datastore.createQuery(SystemNotice.class);
		query.field("memberId").equal(memberId);
		query.field("hasRead").equal(false);
		return query.countAll();
	}

	@Override
	public Pagination<SystemNotice> list(String memberId, int offSet, int limit) {
		Pagination<SystemNotice> page = new Pagination<SystemNotice>();
		Query<SystemNotice> query = datastore.createQuery(SystemNotice.class);
		query.field("memberId").equal(memberId);
		query.offset(offSet).limit(limit);
		query.order("-dateTime");
		page.setPageSize(limit);
		page.setPageNo((offSet/limit) +1);
		page.setTotalItemCount((int)query.countAll());
		page.setData(query.asList());
		return page;
	}

	@Override
	public List<SystemNotice> list(String memberId,int limit) {
		Query<SystemNotice> query = datastore.createQuery(SystemNotice.class);
		query.field("memberId").equal(memberId);
		query.limit(limit);
		query.order("-dateTime");
		return query.asList();
	}

	@Override
	public long read(List<String> ids) {
		List<ObjectId> noticeIds = new LinkedList<ObjectId>();
		if(CollectionUtils.isEmpty(ids)){
			return 0l;
		}else{
			for(String id: ids){
				if(!ObjectId.isValid(id))continue;
				noticeIds.add(new ObjectId(id));
			}
		}
		Query<SystemNotice> query = datastore.createQuery(SystemNotice.class);
		UpdateOperations<SystemNotice> ups = datastore.createUpdateOperations(SystemNotice.class);
		query.field("_id").in(noticeIds);
		ups.set("hasRead", true);
		return datastore.update(query, ups).getUpdatedCount();
	}

	@Override
	public boolean add(SystemNotice entity) {
		return datastore.save(entity).getId() == null? false: true;
	}
	
}
