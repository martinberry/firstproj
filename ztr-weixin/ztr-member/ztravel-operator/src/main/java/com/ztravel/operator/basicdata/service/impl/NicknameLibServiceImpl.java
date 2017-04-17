package com.ztravel.operator.basicdata.service.impl;

import java.util.LinkedList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ztravel.operator.basicdata.dao.INicknameLibDAO;
import com.ztravel.operator.basicdata.entity.NickNameEntity;
import com.ztravel.operator.basicdata.service.INicknameLibService;
import com.ztravel.operator.basicdata.util.ExcelUtil;

/**
 * @author MH
 */
@Service
public class NicknameLibServiceImpl implements INicknameLibService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NicknameLibServiceImpl.class);

	@Resource
	private INicknameLibDAO nicknameLibDao;

	@Override
	public void saveNicknameLib(MultipartFile file) throws Exception {
		LinkedList<String> ALibList = new LinkedList<String>();
		LinkedList<String> BLibList = new LinkedList<String>();

		ExcelUtil.resolveNicknameABExcel(file.getInputStream(), ALibList, BLibList);

		long startTime = 0l;
		long endTime = 0l;
		startTime = System.currentTimeMillis();
////		int n = 0;
//		for(int i = 0; i < ALibList.size(); i++){
//			for(int j = 0; j < BLibList.size(); j++){
//				StringBuffer strBuffer = new StringBuffer();
//				NickNameEntity nickName = new NickNameEntity();
//				nickName.setNickName(strBuffer.append(ALibList.get(i)).append("的").append(BLibList.get(j)).toString());
////				n++;
//				String objectId = nicknameLibDao.insertNickname(nickName);
////				LOGGER.info("*****插入第" + n + "条昵称*****");
//				if( objectId == null )
//					throw new Exception("插入昵称到数据库失败");
//			}
//		}
		int n = 0;
		LinkedList<NickNameEntity> tempNicknameList = new LinkedList<NickNameEntity>();
		for( int i = 0; i < ALibList.size(); i++ ){
			for( int j = 0; j < BLibList.size(); j++ ){
				StringBuffer strBuffer = new StringBuffer();
				NickNameEntity nickName = new NickNameEntity();
				nickName.setNickName(strBuffer.append(ALibList.get(i)).append("的").append(BLibList.get(j)).toString());
				tempNicknameList.add(nickName);

				if( tempNicknameList.size() == 10000 ){
					n++;
					LOGGER.info("*****第" + n + "次插入昵称到数据库开始*****");
					nicknameLibDao.batchInsertNickname(tempNicknameList);
					LOGGER.info("*****第" + n + "次插入昵称到数据库结束*****");
					tempNicknameList.clear();
				}else{
					
					if(i == ALibList.size() && j == BLibList.size()) {
						nicknameLibDao.batchInsertNickname(tempNicknameList);
					}
					continue;
				}
			}
		}

		endTime = System.currentTimeMillis();
		LOGGER.info("*****导入昵称到mongo用时：" + (endTime-startTime)/1000 + "s *****");
	}

	@Override
	public void deleteNicknameLib() throws Exception {
		nicknameLibDao.deleteNicknameLibCollection();
	}

}
