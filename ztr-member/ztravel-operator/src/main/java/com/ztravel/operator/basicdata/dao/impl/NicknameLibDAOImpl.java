package com.ztravel.operator.basicdata.dao.impl;

import java.util.LinkedList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Key;
import com.github.jmkgreen.morphia.query.Query;
import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.dao.INicknameLibDAO;
import com.ztravel.operator.basicdata.entity.NickNameEntity;

/**
 * MongoDB昵称库
 * @author MH
 */
@Repository
public class NicknameLibDAOImpl implements INicknameLibDAO {

	@Resource
	private Datastore datastore;

	@Override
	public String insertNickname(NickNameEntity nickName) {
		Key<NickNameEntity> result = datastore.save(nickName);
		if( result.getId() != null )
			return result.getId().toString();
		else
			return null;
	}

	@Override
	public void batchInsertNickname(LinkedList<NickNameEntity> nickNameList) {
		datastore.save(nickNameList);
	}

	@Override
	public void deleteNicknameLibCollection() throws MongoException {
		datastore.getDB().getCollection("nickNameLib").drop();
//		Query<NickNameEntity> q = datastore.createQuery(NickNameEntity.class);
//		datastore.delete(q);
	}

}
