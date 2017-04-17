package com.ztravel.weixin.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.back.entity.MaterialMediaEntity;

public interface IMaterialMediaDao extends BaseDao<MaterialMediaEntity> {

    public List<String> selectMediaIdsByMediaIds(List<String> list);
    public void deleteAll();

}
