package com.ztravel.operator.financeMantain.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.WebRequest;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.payment.AccountSummaryQueryBean;
import com.ztravel.operator.financeMantain.vo.AccountSummaryVo;

public interface IAccountSummaryService {

	void exportExcel(WebRequest request, HttpServletResponse response) throws Exception;

	Pagination<AccountSummaryVo> search(AccountSummaryQueryBean queryBean) throws Exception;
}
