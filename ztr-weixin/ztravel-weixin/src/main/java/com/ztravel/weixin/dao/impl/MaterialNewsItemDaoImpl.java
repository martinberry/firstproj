package com.ztravel.weixin.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.back.entity.MaterialNewsItemEntity;
import com.ztravel.weixin.dao.IMaterialNewsItemDao;

@Repository
public class MaterialNewsItemDaoImpl extends BaseDaoImpl<MaterialNewsItemEntity> implements IMaterialNewsItemDao {
	/*private static final String SELECTRELATED=".selectrelatedid";
	public List<MaterialNewsItemEntity> selectRelated(String mediaId){
		return session.selectList(nameSpace+SELECTRELATED, mediaId);
	}
*/



	@Override
    public void deleteAll(){
    	session.delete(nameSpace+".deleteAll");
    }
}
