package com.ztravel.member.front.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.reuse.member.entity.SearchModuleVo;
import com.ztravel.reuse.member.entity.WorkPlatFormVo;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.operator.basicdata.entity.AdSpotEntity;
import com.ztravel.operator.basicdata.entity.RecProductHomePageEntity;
import com.ztravel.operator.basicdata.service.IAdSpotClientService;
import com.ztravel.operator.basicdata.service.IRecProductClientService;
import com.ztravel.sso.client.service.SSOClientService;

/**
 * C端首页
 * @author haofan.wan
 */

@Controller
public class HomePageController {
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(HomePageController.class);
	@Resource
	private FrontCommonService frontCommonService;

	@Resource
	private SSOClientService ssoClientService ;
	
	@Resource
	private IAdSpotClientService adSpotService ;
	
	@Resource
	private IRecProductClientService recProductService ;

	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request, Model model) throws Exception {

		SearchModuleVo smv = frontCommonService.getSearchModuleVo(RedisKeyConst.AVAILABLE_DESTINATION_KEY) ;
		smv.setDepartPlace("上海");

		WorkPlatFormVo wpfv =  frontCommonService.getWorkPlatFormVo(request) ;

		model.addAttribute("smv", smv) ;
		model.addAttribute("wpfv", wpfv) ;
		try{
			List<AdSpotEntity> adSpots = adSpotService.getAdSpotList() ;
			model.addAttribute("adSpots", adSpots) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		try{
			List<RecProductHomePageEntity> recProducts = recProductService.searchRecProduct() ;
			model.addAttribute("recProducts", recProducts) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView("common/front/home_page_new") ;
	}

}
