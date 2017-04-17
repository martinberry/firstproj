package com.ztravel.operator.financeMantain.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.payment.AccountHistoryQueryBean;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.operator.financeMantain.service.IAccountHistoryService;
import com.ztravel.operator.financeMantain.vo.AccountHistoryVo;
import com.ztravel.payment.service.IFinanceService;

@Controller
@RequestMapping("/financeMantain/opera/accountHistory")
public class AccountHistoryController {
	private static Logger logger = RequestIdentityLogger.getLogger(AccountHistoryController.class);

	@Resource
	IMemberClientService  memberClientService;

	@Resource
	IFinanceService financeService;

	@Resource
	IAccountHistoryService accountHistoryService;

	@RequestMapping("/index")
	public String showList(Model model, HttpServletRequest request){
		return "operator/financeMantain/accountHistory_index";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView getSearchList(@RequestBody AccountHistoryQueryBean queryBean ,HttpServletRequest request, Model model) throws Exception {
		Pagination<AccountHistoryVo> searchResult = new Pagination<AccountHistoryVo>();
		try{
			DateTime conditionOperateTo = queryBean.getConditionOperateTo();
			if(null != conditionOperateTo){
				String conditionOperateToStr = conditionOperateTo.toString("yyyy-MM-dd ")+"23:59:59";
				conditionOperateTo	=	DateTime.parse(conditionOperateToStr,  DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss"));
				queryBean.setConditionOperateTo(conditionOperateTo);
			}
			logger.info("==query params:="+TZBeanUtils.describe(queryBean));
			searchResult = accountHistoryService.search(queryBean);
			logger.info("==search result :"+TZBeanUtils.describe(searchResult));
		}catch(Exception e){
			e.printStackTrace();
			logger.error("=====查询列表失败 :",e);
		}
		model.addAttribute("searchList", searchResult.getData());
		model.addAttribute("pageNo", searchResult.getPageNo());
		model.addAttribute("pageSize", queryBean.getPageSize());
		model.addAttribute("totalItemCount", searchResult.getTotalItemCount());
		model.addAttribute("totalPageCount", searchResult.getTotalPageCount());
		return new ModelAndView("operator/financeMantain/accountHistoryTable");
	}


	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	public void exportExcel(WebRequest request,  HttpServletResponse response) {
		try {
			accountHistoryService.exportExcel(request, response);
		} catch (Exception e) {
			logger.error("=====导出票券收支明细excel失败 :",e);
		}
	}




}
