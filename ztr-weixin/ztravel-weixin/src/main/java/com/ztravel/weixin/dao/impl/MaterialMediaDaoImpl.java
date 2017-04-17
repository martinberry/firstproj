package com.ztravel.weixin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.back.entity.MaterialMediaEntity;
import com.ztravel.weixin.dao.IMaterialMediaDao;


@Repository
public class MaterialMediaDaoImpl extends BaseDaoImpl<MaterialMediaEntity> implements IMaterialMediaDao {


    @Override
    public List<String> selectMediaIdsByMediaIds(List<String> list) {
        return session.selectList(nameSpace + ".selectMediaIdsByMediaIds", list);
    }


    @Override
    public void deleteAll(){
    	session.delete(nameSpace+".deleteAll");
    }
}
