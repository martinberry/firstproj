package com.ztravel.weixin.activity.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.weixin.activity.entity.GameRecordQueryBean;
import com.ztravel.weixin.activity.service.INewYearActivityUserService;
import com.ztravel.weixin.activity.service.INewYearAwardStockService;
import com.ztravel.weixin.activity.service.INewYearUserAwardRecordService;
import com.ztravel.weixin.activity.vo.GameRecordVo;

@Controller
@RequestMapping("/happy2016")
public class NewYearGameRecordController {

	private static Logger logger = RequestIdentityLogger.getLogger(NewYearGameRecordController.class);

	@Resource
	private INewYearActivityUserService newYearActivityUserService;

	@Resource
	private INewYearAwardStockService newYearAwardStockService;

	@Resource
	private INewYearUserAwardRecordService newYearUserAwardRecordService;

	@RequestMapping("/gameRecord/index")
	public String showList(Model model, HttpServletRequest request) {
		return "/activity/newYear/gameRecord_index";
	}

	@RequestMapping(value = "/gameRecord/search", method = RequestMethod.POST)
	public ModelAndView getSearchList(@RequestBody GameRecordQueryBean queryBean, HttpServletRequest request, Model model) throws Exception {
		Pagination<GameRecordVo> searchResult = new Pagination<GameRecordVo>();
		try {
			searchResult = newYearUserAwardRecordService.searchAwardRecordListByMap(queryBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====查询列表失败 :", e);
		}

		model.addAttribute("searchList", searchResult.getData());
		model.addAttribute("pageNo", searchResult.getPageNo());
		model.addAttribute("pageSize", queryBean.getPageSize());
		model.addAttribute("totalItemCount", searchResult.getTotalItemCount());
		model.addAttribute("totalPageCount", searchResult.getTotalPageCount());
		return new ModelAndView("/activity/newYear/gameRecordTable");
	}

}
