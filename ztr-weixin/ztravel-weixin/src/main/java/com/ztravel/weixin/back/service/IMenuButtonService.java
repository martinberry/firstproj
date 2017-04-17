package com.ztravel.weixin.back.service;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.weixin.back.bean.MenuVo;

public interface IMenuButtonService {

	public void clearAndSaveMenu(MenuVo menuVo) throws Exception;

	public MenuVo getWeixinMenu() throws Exception;

	public AjaxResponse createMenu() throws Exception;

	public AjaxResponse cancelMenu() throws Exception;
}
