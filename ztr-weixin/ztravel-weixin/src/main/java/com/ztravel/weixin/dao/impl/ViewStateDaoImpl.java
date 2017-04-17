package com.ztravel.weixin.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.back.entity.ViewState;
import com.ztravel.weixin.dao.IViewStateDao;
@Repository
public class ViewStateDaoImpl extends BaseDaoImpl<ViewState> implements IViewStateDao{

	private static String DeleteAll = ".deleteAll";
	@Override
	public void deleteAll() throws Exception{
		session.delete(nameSpace+ DeleteAll);
	}
}
