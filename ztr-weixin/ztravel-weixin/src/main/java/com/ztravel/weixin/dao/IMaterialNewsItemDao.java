package com.ztravel.weixin.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.back.entity.MaterialNewsItemEntity;


public interface IMaterialNewsItemDao extends BaseDao<MaterialNewsItemEntity> {
	//public List<MaterialNewsItemEntity> selectRelated(String mediaId);
	public void deleteAll();
}
