package com.ztravel.operator.financeMantain.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.FinanceMantainCons;
import com.ztravel.common.enums.AccountType;
import com.ztravel.common.payment.AccountSummaryQueryBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.operator.financeMantain.service.IAccountSummaryService;
import com.ztravel.operator.financeMantain.vo.AccountSummaryVo;
import com.ztravel.payment.service.IFinanceService;

@Controller
@RequestMapping("/financeMantain/opera/accountSummary")
public class AccountSummaryController {
	private static Logger logger = RequestIdentityLogger.getLogger(AccountSummaryController.class);

	@Resource
	IMemberClientService  memberClientService;

	@Resource
	IFinanceService financeService;
	@Resource
	IAccountSummaryService accountSummaryService;

	@RequestMapping("/index")
	public String showList(Model model, HttpServletRequest request){
		return "operator/financeMantain/accountSummary_index";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView getSearchList(@RequestBody AccountSummaryQueryBean queryBean ,HttpServletRequest request, Model model) throws Exception {
		Pagination< AccountSummaryVo> searchResult = new Pagination< AccountSummaryVo>();
		try{
			searchResult = accountSummaryService.search(queryBean);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("=====查询列表失败 :",e);
		}
		model.addAttribute("searchList", searchResult.getData());
		model.addAttribute("pageNo", searchResult.getPageNo());
		model.addAttribute("pageSize", queryBean.getPageSize());
		model.addAttribute("totalItemCount", searchResult.getTotalItemCount());
		model.addAttribute("totalPageCount", searchResult.getTotalPageCount());
		return new ModelAndView("operator/financeMantain/accountSummaryTable");
	}



	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	public void exportExcel(WebRequest request,  HttpServletResponse response) {
		try {
			accountSummaryService.exportExcel(request, response);
		} catch (Exception e) {
			logger.error("=====导出票券账户明细excel失败 :",e);
		}
	}

	@RequestMapping(value = "/frozenAccount")
	@ResponseBody
	public AjaxResponse frozenAccount(String memberId ,String accountType){
		try{
			CommonResponse commonResponse = financeService. freezeAccount(memberId, AccountType.valueOf(accountType));
			if(!commonResponse.isSuccess()){
				return AjaxResponse.instance(FinanceMantainCons.COUPONACCOUNT_FROZEN_ERROR_CODE, FinanceMantainCons.COUPONACCOUNT_FROZEN_ERROR_MSG);
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(FinanceMantainCons.COUPONACCOUNT_FROZEN_ERROR_CODE, FinanceMantainCons.COUPONACCOUNT_FROZEN_ERROR_MSG);
		}

		return AjaxResponse.instance(FinanceMantainCons.COUPONACCOUNT_FROZEN_SUCESS_CODE, FinanceMantainCons.COUPONACCOUNT_FROZEN_SUCESS_MSG);
	}

	@RequestMapping(value = "/unFrozenAccount")
	@ResponseBody
	public AjaxResponse unFrozenAccount(String memberId,	String accountType){
		try{
			CommonResponse commonResponse = financeService.unfreezeAccount(memberId, AccountType.valueOf(accountType));
			if(!commonResponse.isSuccess()){
				return AjaxResponse.instance(FinanceMantainCons.COUPONACCOUNT_UNFROZEN_ERROR_CODE, FinanceMantainCons.COUPONACCOUNT_UNFROZEN_ERROR_MSG);
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(FinanceMantainCons.COUPONACCOUNT_UNFROZEN_ERROR_CODE, FinanceMantainCons.COUPONACCOUNT_UNFROZEN_ERROR_MSG);
		}
		return AjaxResponse.instance(FinanceMantainCons.COUPONACCOUNT_UNFROZEN_SUCESS_CODE, FinanceMantainCons.COUPONACCOUNT_UNFROZEN_SUCESS_MSG);
	}

}
