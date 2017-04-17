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
import com.ztravel.common.payment.TradeQueryBean;
import com.ztravel.operator.financeMantain.service.ITradeService;
import com.ztravel.operator.financeMantain.vo.TradeVo;

@Controller
@RequestMapping("/financeMantain/opera/trade")
public class TradeController {
	private static Logger logger = RequestIdentityLogger.getLogger(TradeController.class);

	@Resource
	ITradeService tradeService;

	@RequestMapping("/index")
	public String showList(Model model, HttpServletRequest request){
		return "operator/financeMantain/trade_index";
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView getSearchList(@RequestBody TradeQueryBean tradeQuery ,HttpServletRequest request, Model model) throws Exception {
		 Pagination<TradeVo> searchResult = new Pagination<TradeVo>();
		try{
			DateTime conditionTradeTo = tradeQuery.getConditionTradeTo();
			if(null != conditionTradeTo){
				String conditionTradeToStr = conditionTradeTo.toString("yyyy-MM-dd ")+"23:59:59";
				conditionTradeTo	=	DateTime.parse(conditionTradeToStr,  DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss"));
				tradeQuery.setConditionTradeTo(conditionTradeTo);
			}
			logger.info("==query params:="+TZBeanUtils.describe(tradeQuery));
			searchResult = tradeService.search(tradeQuery);
			logger.info("==search result :"+TZBeanUtils.describe(searchResult));
		}catch(Exception e){
			e.printStackTrace();
			logger.error("=====查询列表失败 :",e);
		}
		model.addAttribute("searchList", searchResult.getData());
		model.addAttribute("pageNo", searchResult.getPageNo());
		model.addAttribute("pageSize", tradeQuery.getPageSize());
		model.addAttribute("totalItemCount", searchResult.getTotalItemCount());
		model.addAttribute("totalPageCount", searchResult.getTotalPageCount());

		return new ModelAndView("operator/financeMantain/tradeTable");
	}



	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	public void exportExcel(WebRequest request,  HttpServletResponse response) {
		try {

			tradeService.exportExcel(request,response);


		} catch (Exception e) {
			logger.error("=====导出交易明细excel失败 :",e);
		}
	}





}
