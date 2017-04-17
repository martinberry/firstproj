package com.ztravel.member.dao.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Key;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.dao.IPrivateLetterDao;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.member.po.PrivateMsg;
@Repository
public class PrivateLetterDaoImpl implements IPrivateLetterDao {

	@Resource
	private Datastore datastore ;

	@Override
	public long count(String authorId) {
		Query<PrivateLetter> query = datastore.createQuery(PrivateLetter.class);
		query.field("authorId").equal(authorId);
		query.field("hasRead").equal(false);
		return query.countAll();
	}

	@Override
	public Pagination<PrivateLetter> list(String authorId, int offset, int limit) {
		Pagination<PrivateLetter> page = new Pagination<PrivateLetter>();
		BasicDBObject querys = new BasicDBObject();
		querys.put("authorId", authorId);
		BasicDBObject keys = new BasicDBObject();
		keys.put("authorId", true);
		keys.put("anotherId", true);
		keys.put("updateTime", true);
		keys.put("hasRead",true);
		keys.put("msgs", new BasicDBObject("$slice", -1));
		BasicDBObject orderBy = new BasicDBObject("updateTime",-1);
		DBCursor cursor = datastore.getCollection(PrivateLetter.class).find(querys, keys).skip(offset).limit(limit).sort(orderBy);
		page.setPageSize(limit);
		page.setPageNo(offset/limit + 1);
		page.setTotalItemCount(cursor.count());
		page.setData(dbos2Objs(cursor.toArray()));
		return page;
	}

	private List<PrivateLetter> dbos2Objs(List<DBObject> dbos){
		List<PrivateLetter> objs = new LinkedList<PrivateLetter>();
		if(CollectionUtils.isNotEmpty(dbos)){
			for(DBObject dbo: dbos){
				PrivateLetter pl = JSONObject.parseObject(dbo.toString(), PrivateLetter.class);
				pl.setId(new ObjectId(dbo.get("_id").toString()));
				pl.setUpdateTime(new DateTime(((Date)dbo.get("updateTime")).getTime()));
				objs.add(pl);
			}
		}
		return objs;
	}

	@Override
	public PrivateLetter detail(String authorId, String anotherId) {
		Query<PrivateLetter> query = datastore.createQuery(PrivateLetter.class);
		query.field("authorId").equal(authorId);
		query.field("anotherId").equal(anotherId);
		return query.get();
	}

	@Override
	public int addMsg(String authorId, String anotherId, PrivateMsg msg) {
		int num = 0;
		//更新自己对话
		num += addOneMsg(authorId, anotherId, msg,false);//更新自己的会话不需要设置未读
		//更新对方会话
		num += addOneMsg(anotherId, authorId, msg,true);
		return num;
	}
	private int addOneMsg(String authorId, String anotherId, PrivateMsg msg,boolean setUnread) {
		int num = 0;
		//更新自己会话
		Query<PrivateLetter> query = datastore.createQuery(PrivateLetter.class);
		query.field("authorId").equal(authorId);
		query.field("anotherId").equal(anotherId);
		if(query.countAll() == 0){
			PrivateLetter newLetter = new PrivateLetter();
			newLetter.setAuthorId(authorId);
			newLetter.setAnotherId(anotherId);
			newLetter.setUpdateTime(DateTime.now());
			if(setUnread){
				newLetter.setHasRead(false);
			}
			LinkedList<PrivateMsg> msgs = new LinkedList<PrivateMsg>();
			msgs.add(msg);
			newLetter.setMsgs(msgs);
			Key<PrivateLetter> key = datastore.save(newLetter);
			if(key.getId() != null){
				num++;
			}
		}else{
			UpdateOperations<PrivateLetter> ups = datastore.createUpdateOperations(PrivateLetter.class);
			ups.set("updateTime", msg.getDateTime());
			if(setUnread){
				ups.set("hasRead", false);
			}
			ups.add("msgs", msg, true);
			UpdateResults<PrivateLetter> result = datastore.update(query, ups);
			if(result.getUpdatedCount() >= 1){
				num++;
			}
		}
		return num;
	}

	@Override
	public int deleteAll(String letterId) {
		Query<PrivateLetter> query = datastore.createQuery(PrivateLetter.class);
		query.field("_id").equal(new ObjectId(letterId));
		return datastore.delete(query).getN();
	}

	@Override
	public int deleteOne(String letterId, String msgId) {
		int resultCount = 0;
		Query<PrivateLetter> query = datastore.createQuery(PrivateLetter.class);
		UpdateOperations<PrivateLetter> ups = datastore.createUpdateOperations(PrivateLetter.class);
		query.field("_id").equal(new ObjectId(letterId));
		PrivateMsg tempMsg = new PrivateMsg();
		tempMsg.setId(msgId);
		Query<PrivateLetter> queryEmpty = datastore.createQuery(PrivateLetter.class);
		queryEmpty.field("_id").equal(new ObjectId(letterId));
		queryEmpty.filter("msgs elem", tempMsg);
		queryEmpty.field("msgs").sizeEq(1);
		if(queryEmpty.countAll() > 0){
			resultCount = datastore.delete(query).getN();
		}else{
			query.filter("msgs elem", tempMsg);
			ups.removeAll("msgs", tempMsg);
			UpdateResults<PrivateLetter>  result = datastore.update(query, ups);
			resultCount = result.getUpdatedCount();
		}

		return resultCount;
	}

	@Override
	public int read(String authorId, String anotherId) {
		Query<PrivateLetter> query = datastore.createQuery(PrivateLetter.class);
		UpdateOperations<PrivateLetter> ups = datastore.createUpdateOperations(PrivateLetter.class);
		query.field("authorId").equal(authorId);
		query.field("anotherId").equal(anotherId);
		ups.set("hasRead", true);
		return datastore.update(query, ups).getUpdatedCount();
	}

	@Override
	public int countAll(String authorId) {
		return  (int) datastore.createQuery(PrivateLetter.class).field("authorId").equal(authorId).countAll();
	}

}
