package com.ztravel.operator.financeMantain.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.WebRequest;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.payment.TradeQueryBean;
import com.ztravel.operator.financeMantain.vo.TradeVo;

public interface ITradeService {

	void exportExcel(WebRequest request, HttpServletResponse response) throws Exception;

	Pagination<TradeVo> search(TradeQueryBean tradeQuery) throws Exception;

}
