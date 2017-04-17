package com.ztravel.operator.financeMantain.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.poi.util.ExcelHelper;
import com.ztravel.common.enums.AccountStatus;
import com.ztravel.common.enums.AccountType;
import com.ztravel.common.payment.AccountSummaryBean;
import com.ztravel.common.payment.AccountSummaryQueryBean;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.operator.financeMantain.converter.AccountSummaryConverter;
import com.ztravel.operator.financeMantain.service.IAccountSummaryService;
import com.ztravel.operator.financeMantain.util.ExcelHeadFactoryUtil;
import com.ztravel.operator.financeMantain.util.FileNameUtil;
import com.ztravel.operator.financeMantain.vo.AccountSummaryVo;
import com.ztravel.payment.service.IFinanceService;
@Service("AccountSummaryService")
public class AccountSummaryService implements IAccountSummaryService{

	@Resource
	IMemberClientService  memberClientService;

	@Resource
	IFinanceService financeService;

	@Override
	public void exportExcel(WebRequest request, HttpServletResponse response)
			throws Exception {
		AccountSummaryQueryBean queryBean = this.buildQueryBeanByRequest(request);
		//强制设置
		StringBuffer fileName = new StringBuffer();
		fileName.append("票券账户明细");
		fileName.append("_");
		fileName.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		fileName.append(".xls");
		OutputStream os = response.getOutputStream();
		String returnFileName = FileNameUtil.converterFileName(fileName.toString(), request);
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment;filename=" + returnFileName);
		List<AccountSummaryVo> accountSummaryVos = Lists.newArrayList();
		Pagination<AccountSummaryBean>  pagination = financeService.queryAccountSummaryInfo(queryBean);
		if(null!= pagination.getData()) {
			List<AccountSummaryBean> accountSummaryBeans = (List<AccountSummaryBean>) pagination.getData();
			accountSummaryVos = getAccountSummaryVosByAccountSummaryBeans(accountSummaryBeans);
		}
		Class<AccountSummaryVo> clazz = AccountSummaryVo.class;
		ExcelHelper.getInstanse().exportToOS(os, ExcelHeadFactoryUtil.getAccountSummaryHead(), accountSummaryVos, clazz);

	}

	@Override
	public Pagination<AccountSummaryVo> search(AccountSummaryQueryBean queryBean)
			throws Exception {
		Pagination<AccountSummaryVo> searchResult = new Pagination<AccountSummaryVo>();
		int pageNo =0;
		int totalItemCount=0;
		int totalPageCount=0;
		 List<AccountSummaryVo> searchList = Lists.newArrayList();
		setMemberIdOfQueryBean(queryBean);
		Pagination<AccountSummaryBean>  pagination = financeService.queryAccountSummaryInfo(queryBean);
		List<AccountSummaryBean> accountSummaryBeans = (List<AccountSummaryBean>) pagination.getData();

		  totalItemCount = (int) pagination.getTotalItemCount();
		  totalPageCount = (int) Math.ceil(new Double(totalItemCount)/queryBean.getPageSize() );
		 pageNo = queryBean.getPageNo();

		 if( accountSummaryBeans.size() == 0){
		 		pageNo = 1;
				totalItemCount = 0;
				totalPageCount=1;
		 }
		searchList = getAccountSummaryVosByAccountSummaryBeans(accountSummaryBeans);
		searchResult.setPageNo(pageNo);
		searchResult.setTotalItemCount(totalItemCount);
		searchResult.setTotalPageCount(totalPageCount);
		searchResult.setData(searchList);

		return searchResult;
	}

	private void setMemberIdOfQueryBean(AccountSummaryQueryBean queryBean){
		if(StringUtils.isNotEmpty(queryBean.getMemberId())){
			String memberId = memberClientService.getMemberIdByMid(queryBean.getMemberId());
			if(StringUtils.isNotEmpty(memberId)){
				queryBean.setMemberId(memberId);
			}
		}
	}

	private List<AccountSummaryVo> getAccountSummaryVosByAccountSummaryBeans(List<AccountSummaryBean> accountSummaryBeans) throws Exception{
		List<AccountSummaryVo> accountSummaryVos = Lists.newArrayList();
		if(null != accountSummaryBeans && accountSummaryBeans.size()	>	0){
			AccountSummaryVo accountSummaryVo = new AccountSummaryVo();
			for(AccountSummaryBean accountSummaryBean :	accountSummaryBeans){
				accountSummaryVo = getAccountSummaryVoByAccountSummaryBean(accountSummaryBean);
				accountSummaryVos.add(accountSummaryVo);
			}
		}
		return accountSummaryVos;
	}

	private AccountSummaryVo getAccountSummaryVoByAccountSummaryBean(AccountSummaryBean accountSummaryBean) throws Exception{
		AccountSummaryVo accountSummaryVo = new AccountSummaryVo();

		String memberName ="";
		String mid = "";
		String memberId = "";
		memberId = accountSummaryBean.getMemberId();
		if(StringUtils.isNotEmpty(memberId)){
			String  memInfo = memberClientService.getMinMemberById(memberId);
			if(StringUtils.isNotEmpty(memInfo) && StringUtils.startsWith(memInfo, "{")) {
					JSONObject json = JSONObject.parseObject(memInfo);
					if(json.containsKey("nickName")) {
						memberName = json.getString("nickName");
						accountSummaryVo.setMemberName(memberName);
					}
					if(json.containsKey("mid")) {
						mid = json.getString("mid");
						accountSummaryVo.setMid(mid);
					}
			  }
		   }
		AccountSummaryConverter.accountSummaryBeanConverter2AccountSummaryVo(accountSummaryBean, accountSummaryVo);

		return accountSummaryVo;
	}

	private AccountSummaryQueryBean buildQueryBeanByRequest(WebRequest request) {
		AccountSummaryQueryBean queryBean = new AccountSummaryQueryBean();
		String mid = request.getParameter("mid");
		String accountType = request.getParameter("accountType");
		String accountStatus = request.getParameter("accountStatus");
		if(StringUtils.isNotEmpty(mid)){
			queryBean.setMemberId(mid);
			setMemberIdOfQueryBean(queryBean);
		}

		if(StringUtils.isNotEmpty(accountStatus)){
			if(accountStatus.equals(AccountStatus.AVAILABLE.getDescription())){
				queryBean.setAccountStatus(AccountStatus.AVAILABLE);
			}else if(accountStatus.equals(AccountStatus.FROZEN.getDescription())){
				queryBean.setAccountStatus(AccountStatus.FROZEN);
			}
		}

		if(StringUtils.isNotEmpty(accountType)){
			if(accountType.equals(AccountType.COUPON.getDescription())){
				queryBean.setAccountType(AccountType.COUPON);
			}else if(accountType.equals(AccountType.REWARD_POINT.getDescription())){
				queryBean.setAccountType(AccountType.REWARD_POINT);
			}
		}
		return queryBean;
	}
}
