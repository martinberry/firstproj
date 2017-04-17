package com.ztravel.operator.basicdata.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.operator.basicdata.entity.AdSpotEntity;
import com.ztravel.operator.basicdata.entity.RecProductHomePageEntity;
import com.ztravel.operator.basicdata.service.IAdSpotClientService;
import com.ztravel.operator.basicdata.service.IAdSpotService;
import com.ztravel.operator.basicdata.service.IRecProductClientService;
import com.ztravel.operator.basicdata.service.IRecProductService;

/**
 * 基础数据 -->广告位
 * @author zhoubo
 *
 */
@Controller
@RequestMapping("/adSpots")
public class AdSpotsController {
	private static final Logger logger = LoggerFactory.getLogger(AdSpotsController.class);

	@Resource
	IAdSpotService adSpotServiceImpl;

	@Resource
	IRecProductService recProductServiceImpl;

	@Resource
	private FrontCommonService frontCommonService;

	@Resource
	private IAdSpotClientService adSpotService ;

	@Resource
	private IRecProductClientService recProductService ;

	@RequestMapping("/index")
	public String index(Model model){
		List<AdSpotEntity> adSpotList = Lists.newArrayList();
		try{
			 adSpotList = adSpotServiceImpl.getAdSpotList();
		}catch(Exception e){
			logger.error("获取广告位集合失败: "+e.toString());
		}
		model.addAttribute("adSpotList", adSpotList);
		return "operator/adSpots/adSpots_index";
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResponse save(@RequestBody List<AdSpotEntity> adSpotList){
		AjaxResponse response = new AjaxResponse();
		response.setRes_code("success");
		try{
			adSpotServiceImpl.saveAdSpotList(adSpotList);
		}catch(Exception e){
			logger.error("保存广告位失败:"+e.toString());
			response.setRes_code("failed");
			response.setRes_msg("保存广告位失败:"+e.toString());
		}
		return response;
	}

	@RequestMapping(value="/preView", method=RequestMethod.POST)
	public ModelAndView preView(HttpServletRequest request, Model model){
		String imageIdsStr = request.getParameter("imageIds");
		List<String> imageIds = getImageIdsByStr(imageIdsStr);
		model.addAttribute("imageIds", imageIds);
		try{
			List<RecProductHomePageEntity> recProducts = recProductService.searchRecProduct() ;
			model.addAttribute("recProducts", recProducts) ;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("operator/adSpots/adSpots_preview") ;
	}

	private List<String> getImageIdsByStr(String imageIdsStr) {
		logger.info("getImageIdsByStr : "+imageIdsStr);
		List<String> imageIds = Lists.newArrayList();
		try{
			String[] imageIdArray = imageIdsStr.split(",");
			imageIds = Arrays.asList(imageIdArray);
		}catch(Exception e){
			logger.error("获取imageIds 失败:"+e.toString());
		}
		return imageIds;
	}

}
