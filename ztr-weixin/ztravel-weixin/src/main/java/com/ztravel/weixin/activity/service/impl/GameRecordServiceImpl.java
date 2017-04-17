package com.ztravel.weixin.activity.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztravel.weixin.activity.dao.IGameRecordDao;
import com.ztravel.weixin.activity.entity.GameRecordEntity;
import com.ztravel.weixin.activity.service.IGameRecordService;

@Service
public class GameRecordServiceImpl implements IGameRecordService {

	@Resource
	private IGameRecordDao gameRecordDaoImpl ;

	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void saveGameRecord(GameRecordEntity gameRecordEntity) {

		gameRecordDaoImpl.insert(gameRecordEntity);

	}

}
