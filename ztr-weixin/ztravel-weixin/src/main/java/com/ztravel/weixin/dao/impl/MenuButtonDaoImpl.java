package com.ztravel.weixin.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.back.entity.MenuButton;
import com.ztravel.weixin.dao.IMenuButtonDao;
@Repository
public class MenuButtonDaoImpl extends BaseDaoImpl<MenuButton> implements IMenuButtonDao{

	private static String DeleteAll = ".deleteAll";
	@Override
	public void deleteAll() throws Exception{
		session.delete(nameSpace+ DeleteAll);
	}

}
