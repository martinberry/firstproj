package com.ztravel.operator.basicdata.dao;

import java.util.LinkedList;

import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.entity.NickNameEntity;

/**
 * MongoDB昵称库
 * @author MH
 */
public interface INicknameLibDAO {
	/**
	 * 插入昵称(单个)
	 * @param nickName
	 * @return
	 */
	public String insertNickname(NickNameEntity nickName);
	/**
	 * 插入昵称(批量)
	 * @param nickNameList
	 */
	public void batchInsertNickname(LinkedList<NickNameEntity> nickNameList);
	/**
	 * 删除昵称库
	 * @throws MongoException
	 */
	public void deleteNicknameLibCollection() throws MongoException;
//	/**
//	 * 清空昵称
//	 */
//	public void clearNicknameLib();
}
