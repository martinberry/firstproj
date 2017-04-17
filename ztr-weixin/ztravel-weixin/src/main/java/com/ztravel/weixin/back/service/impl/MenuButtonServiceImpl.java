package com.ztravel.weixin.back.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.site.lookup.util.StringUtils;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.enums.ButtonLevel;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.weixin.back.bean.ComplexMenuButton;
import com.ztravel.weixin.back.bean.MenuButtonConvert;
import com.ztravel.weixin.back.bean.MenuButtonVo;
import com.ztravel.weixin.back.bean.MenuVo;
import com.ztravel.weixin.back.constant.WxMenuConst;
import com.ztravel.weixin.back.entity.MaterialMediaEntity;
import com.ztravel.weixin.back.entity.MaterialNewsItemEntity;
import com.ztravel.weixin.back.entity.MenuButton;
import com.ztravel.weixin.back.entity.ViewState;
import com.ztravel.weixin.back.service.IMenuButtonService;
import com.ztravel.weixin.dao.IMaterialMediaDao;
import com.ztravel.weixin.dao.IMaterialNewsItemDao;
import com.ztravel.weixin.dao.IMenuButtonDao;
import com.ztravel.weixin.dao.IViewStateDao;
import com.ztravel.weixin.menu.pojo.Button;
import com.ztravel.weixin.menu.pojo.CommonButton;
import com.ztravel.weixin.menu.pojo.ComplexButton;
import com.ztravel.weixin.menu.pojo.Menu;
import com.ztravel.weixin.operate.MenuOperator;
import com.ztravel.weixin.servlet.AccessTokenThread;
@Service
public class MenuButtonServiceImpl implements IMenuButtonService {

	private final static Logger logger = LoggerFactory.getLogger(MenuButtonServiceImpl.class);

	@Resource
	IMenuButtonDao menuButtonDaoImpl;

	@Resource
	IMaterialMediaDao materialMediaDaoImpl;

	@Resource
	IMaterialNewsItemDao materialNewsItemDaoImpl;

	@Resource
	IViewStateDao viewStateDaoImpl;

	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Override
	@Transactional
	public void clearAndSaveMenu(MenuVo menuVo) throws Exception {
		menuButtonDaoImpl.deleteAll();
		viewStateDaoImpl.deleteAll();
		ComplexMenuButton[] complexMenuButtons = menuVo.getFirstLevelButtons();
		for(int i=0; i<complexMenuButtons.length; i++){
			ComplexMenuButton complexMenuButton = complexMenuButtons[i];
			MenuButton firstLevelButton = new MenuButton();
			String buttonId = idGeneratorUtil.getMenuButtonId();
			firstLevelButton.setButtonId(buttonId);
			MenuButtonVo[] subMenuButtonVos = complexMenuButton.getSubMenuButtonVos();
			if(subMenuButtonVos != null){
				for(int j=0 ; j<subMenuButtonVos.length; j++){
					MenuButton secondLevelButton = new MenuButton();
					secondLevelButton.setButtonId(idGeneratorUtil.getMenuButtonId());
					secondLevelButton.setParentButtonId(buttonId);
					secondLevelButton.setButtonLevel(new Integer(ButtonLevel.CHILDLEVEL.getLevel()));
					secondLevelButton = MenuButtonConvert.buildMenuButtonByMenuButtonVo(secondLevelButton, subMenuButtonVos[j]);
					saveViewStateByMenButtonVo(subMenuButtonVos[j]);
					menuButtonDaoImpl.insert(secondLevelButton);
				}
			}
			firstLevelButton.setButtonLevel(new Integer(ButtonLevel.PARENTLEVEL.getLevel()));
			firstLevelButton = MenuButtonConvert.buildMenuButtonByMenuButtonVo(firstLevelButton, complexMenuButton);
			saveViewStateByMenButtonVo(complexMenuButton);
			menuButtonDaoImpl.insert(firstLevelButton);
		}

	}

	private void saveViewStateByMenButtonVo(MenuButtonVo menuButtonVo) throws Exception{
		ViewState viewState = new ViewState();
		viewState.setId(menuButtonVo.getViewState());
		String url = menuButtonVo.getUrl();
		if(StringUtils.isNotEmpty(url)){
			viewState.setUrl(url.split(WxMenuConst.SPLIT_STR)[0].toString());
		}
		viewStateDaoImpl.insert(viewState);
	}

	@Override
	public MenuVo getWeixinMenu() throws Exception {
		MenuVo menuVo = new MenuVo();
		//查出所有的button
		Map<String, Object> params = Maps.newHashMap();
		params.put("buttonLevel", new Integer(ButtonLevel.PARENTLEVEL.getLevel()));
		List<MenuButton> parentButtons = menuButtonDaoImpl.select(params);
		ComplexMenuButton[] firstLevelButtons = new ComplexMenuButton[parentButtons.size()];
		int i = 0;
		for(MenuButton parentButton : parentButtons){
			ComplexMenuButton parentButtonVo = new ComplexMenuButton();
			if(StringUtils.isNotEmpty(parentButton.getWxUrlFlag()) && parentButton.getWxUrlFlag().equals("1")){
				setButtonUrl(parentButton);
			}
			parentButtonVo =  MenuButtonConvert.menuButton2ComplexMenuButton(parentButton);
			Map<String, Object> map = Maps.newHashMap();
			map.put("parentButtonId", parentButton.getButtonId());
			List<MenuButton> childButtons = menuButtonDaoImpl.select(map);
			if(childButtons != null && childButtons.size()>0){
				MenuButtonVo[] secondLevelButtons = new MenuButtonVo[childButtons.size()];
				int j = 0;
				for(MenuButton childButton : childButtons){
					MenuButtonVo childButtonVo = new MenuButtonVo();
					if(StringUtils.isNotEmpty(childButton.getWxUrlFlag()) && childButton.getWxUrlFlag().trim().equals(WxMenuConst.ONE_STR)){
						setButtonUrl(childButton);
					}
					childButtonVo = MenuButtonConvert.menuButton2MenuButtonVo(childButton);
					String mediaId = childButtonVo.getMediaId();
					setButtonKey(childButtonVo);
					childButtonVo.setMediaId(mediaId);
					secondLevelButtons[j] = childButtonVo;
					j++;
				}
				parentButtonVo.setSubMenuButtonVos(secondLevelButtons);
			}else{
				String type = parentButtonVo.getType();
				if(StringUtils.isNotEmpty(type)){
					if(type.equals(WxMenuConst.CLICK)){
						String mediaId = parentButton.getMediaId();
						parentButtonVo.setMediaId(mediaId);
						setButtonKey(parentButtonVo);
					}
				}
			}

			firstLevelButtons[i] = parentButtonVo;
			i++;
		}
		menuVo.setFirstLevelButtons(firstLevelButtons);
		return menuVo;
	}

	private void setButtonUrl(MenuButton menuButton) {
		String viewStateId = menuButton.getViewStateId();
		if(StringUtils.isNotEmpty(viewStateId)){
			Map<String, Object> params = Maps.newHashMap();
			params.put("id", viewStateId);
			List<ViewState> viewStates = viewStateDaoImpl.select(params);
			if(viewStates != null && viewStates.size()>0){
					String url = viewStates.get(0).getUrl();
					url =url.trim()+WxMenuConst.ZHENLX_SUFFIX;
					menuButton.setUrl(url);
			}
		}
	}

	private MenuButtonVo setButtonKey(MenuButtonVo childButtonVo) throws Exception{
		String mediaId = childButtonVo.getMediaId();
		if(StringUtils.isNotEmpty(mediaId)){
			Map<String, Object> materialMediaParams = Maps.newHashMap();
			materialMediaParams.put("mediaId", mediaId);
			List<MaterialMediaEntity> materialMedias = materialMediaDaoImpl.select(materialMediaParams);
			if(materialMedias != null && materialMedias.size()>0){
				MaterialMediaEntity materialMedia = materialMedias.get(0);
				String type = materialMedia.getType();
				if(StringUtils.isNotEmpty(type)){
					if(type.equals("image") || type.equals("text")){
						setImageMaterialKey(childButtonVo);
					}else if(type.equals("news") ){
						setNewsMaterialKey(childButtonVo);
					}
				}
			}else{
				Map<String, Object> materialNewsItemParam = Maps.newHashMap();
				materialNewsItemParam.put("relatedMediaId", mediaId);
				List<MaterialNewsItemEntity>  materialNewsItems = materialNewsItemDaoImpl.select(materialNewsItemParam);
				if(materialNewsItems != null && materialNewsItems.size()>0){
					String title = materialNewsItems.get(0).getTitle();
					childButtonVo.setMediaId(mediaId);
					childButtonVo.setKey(title);
				}
			}

		}
		return childButtonVo;
	}



	private void setImageMaterialKey(MenuButtonVo childButtonVo){
		String mediaId = childButtonVo.getMediaId();
		Map<String, Object> materialNewsItemParams = Maps.newHashMap();
		materialNewsItemParams.put("mediaId", mediaId);
		List<MaterialMediaEntity>  imageMaterialMedias = materialMediaDaoImpl.select(materialNewsItemParams);
		if(imageMaterialMedias != null && imageMaterialMedias.size()>0){
			String title = imageMaterialMedias.get(0).getName();
			childButtonVo.setKey(title);
		}

	}

	private void setNewsMaterialKey(MenuButtonVo childButtonVo){
		String mediaId = childButtonVo.getMediaId();
		Map<String, Object> materialNewsItemParams = Maps.newHashMap();
		materialNewsItemParams.put("relatedMediaId", mediaId);
		List<MaterialNewsItemEntity> materialNewsMedias =  materialNewsItemDaoImpl.select(materialNewsItemParams);
		if(materialNewsMedias != null && materialNewsMedias.size()>0){
			MaterialNewsItemEntity  materiaNewsMedia = materialNewsMedias.get(0);
			childButtonVo.setKey(materiaNewsMedia.getTitle());
		}
	}


	@Override
	public AjaxResponse createMenu() throws Exception {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String accessToken =  AccessTokenThread.getAccessToken();
    	Menu menu = getMenu();
    	 String jsonStr = JSONObject.toJSONString(menu).toString();
    	 logger.info("weixin menu jsonStr: " + jsonStr);
    	 JSONObject jsonObject  = MenuOperator.createMenu(jsonStr, accessToken);
    	 logger.info("生成自定义菜单返回: "+jsonObject.toString());
    	 if(jsonObject.get("errcode").toString().equals(WxMenuConst.ZERO_STR)){
    		 ajaxResponse.setRes_code("success");
    		 ajaxResponse.setRes_msg("");
    	 }else{
    		 ajaxResponse.setRes_code("failed");
    		 ajaxResponse.setRes_msg("");
    	 }

    	 return ajaxResponse;
	}

	public Menu getMenu() throws Exception{
		Menu menu = new Menu();
		Map<String, Object> params = Maps.newHashMap();
    	params.put("buttonLevel", new Integer(ButtonLevel.PARENTLEVEL.getLevel()));
    	params.put("state", new Integer(1));
    	params.put("orderBy", "asc");
    	List<MenuButton> parentButtons = menuButtonDaoImpl.select(params);
    	Button[] firstLevelButtons = new Button[parentButtons.size()];
    	int i= 0 ;

    	for(MenuButton parentButton : parentButtons){
    		ComplexButton firstLevelButton = new ComplexButton();
    		firstLevelButton.setName(parentButton.getName());
    		Map<String, Object> map = Maps.newHashMap();
    		map.put("parentButtonId", parentButton.getButtonId());
    		map.put("state", new Integer(1));
    		map.put("orderBy", "desc");
    		List<MenuButton> childButtons = menuButtonDaoImpl.select(map);
    		if(childButtons ==null || childButtons.size() == 0){
    			String type = parentButton.getType();
    			if(type != null){
    				firstLevelButton.setType(type);
    				if(type.equals("click") ){
    					firstLevelButton.setType(parentButton.getType());
    					firstLevelButton.setKey(parentButton.getMediaId());
    				}else if(type.equals("view")){
    					firstLevelButton.setUrl(parentButton.getUrl());
    				}
    			}
    		}else{
	    		CommonButton[] secondLevelButtons = new CommonButton[childButtons.size()];
	    		int j = 0;
	    		for(MenuButton childButton : childButtons){
	    			CommonButton commonButton =  MenuButtonConvert.menuButton2Button(childButton);
	    			secondLevelButtons[j] = commonButton;
	    			j++;
	    		}
	    		firstLevelButton.setSub_button(secondLevelButtons);
    		}
    		firstLevelButtons[i] = firstLevelButton;
    		i++;

    	}
    	menu.setButton(firstLevelButtons);
		return menu;
	}


	@Override
	public AjaxResponse cancelMenu() throws Exception {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String accessToken =  AccessTokenThread.getAccessToken();
		 JSONObject jsonObject  = MenuOperator.deleteMenuInfo(accessToken);
    	 logger.info("撤销自定义菜单返回: "+ jsonObject.toString());
    	 if(jsonObject.get("errcode").toString().equals(WxMenuConst.ZERO_STR)){
    		 ajaxResponse.setRes_code("success");
    		 ajaxResponse.setRes_msg("撤销自定义菜单成功");
    	 }else{
    		 ajaxResponse.setRes_code("failed");
    		 ajaxResponse.setRes_msg("撤销自定义菜单失败");
    	 }
    	 return ajaxResponse;
	}

}
