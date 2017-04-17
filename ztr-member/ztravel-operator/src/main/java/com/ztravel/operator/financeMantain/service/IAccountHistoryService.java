package com.ztravel.operator.financeMantain.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.WebRequest;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.payment.AccountHistoryQueryBean;
import com.ztravel.operator.financeMantain.vo.AccountHistoryVo;

public interface IAccountHistoryService {
	void exportExcel(WebRequest request, HttpServletResponse response) throws Exception;

	Pagination<AccountHistoryVo> search(AccountHistoryQueryBean queryBean) throws Exception;
}
