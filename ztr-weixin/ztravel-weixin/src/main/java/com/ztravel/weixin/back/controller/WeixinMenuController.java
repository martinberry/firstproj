package com.ztravel.weixin.back.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.weixin.back.bean.MenuVo;
import com.ztravel.weixin.back.service.IMenuButtonService;
import com.ztravel.weixin.back.service.IViewStateService;
@Controller
@RequestMapping("/weixinMenu")
public class WeixinMenuController {
	private final static Logger logger = LoggerFactory.getLogger(WeixinMenuController.class);
	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Resource
	 IMenuButtonService menuButtonServiceImpl;

	@Resource
	 IViewStateService viewStateServiceImpl;

	@RequestMapping("/index")
	public ModelAndView index() throws Exception{
		MenuVo menuVo = new MenuVo();
		try{
			menuVo = menuButtonServiceImpl.getWeixinMenu();
		}catch(Exception e){
			logger.info(e.toString());
		}
		ModelAndView model = new ModelAndView();
		model.addObject("menuVo", menuVo);
		model.setViewName("back/weixinMenu/menu_index");
		return model ;
	}

	@RequestMapping("/createMenu")
	@ResponseBody
	public AjaxResponse createMenu() {
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			ajaxResponse = menuButtonServiceImpl.createMenu();
		}catch(Exception e){
			ajaxResponse.setRes_code("error");
			ajaxResponse.setRes_msg("生成自定义菜单失败");
			logger.error(e.toString());
		}
		return ajaxResponse;
	}

	@RequestMapping("/cancelMenu")
	@ResponseBody
	public AjaxResponse cancelMenu(){
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			ajaxResponse = menuButtonServiceImpl.cancelMenu();
		}catch(Exception e){
			ajaxResponse.setRes_code("error");
			ajaxResponse.setRes_msg("撤销自定义菜单失败");
			logger.error(e.toString());
		}
		return ajaxResponse;
	}


	@RequestMapping("/saveMenu")
	@ResponseBody
	public AjaxResponse saveMenu(@RequestBody MenuVo menuVo) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			menuButtonServiceImpl.clearAndSaveMenu(menuVo);
			ajaxResponse.setRes_code("success");
			ajaxResponse.setRes_msg("保存菜单成功");
		}catch(Exception e){
			ajaxResponse.setRes_code("error");
			ajaxResponse.setRes_msg("保存菜单失败");
			logger.error(e.toString());
		}
		return ajaxResponse;
	}
}
