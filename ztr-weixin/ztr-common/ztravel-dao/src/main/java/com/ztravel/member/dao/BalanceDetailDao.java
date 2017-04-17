package com.ztravel.member.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.member.po.BalanceDetailEntity;

/**
 * @author wanhaofan
 * */
public interface BalanceDetailDao extends BaseDao<BalanceDetailEntity> {

	void insert(BalanceDetailEntity entity) ;

}
