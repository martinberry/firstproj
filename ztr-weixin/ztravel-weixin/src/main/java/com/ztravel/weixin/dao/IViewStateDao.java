package com.ztravel.weixin.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.back.entity.ViewState;

public interface IViewStateDao extends BaseDao<ViewState>{
	public void deleteAll() throws Exception;
}
