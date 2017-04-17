package com.ztravel.operator.financeMantain.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.poi.util.ExcelHelper;
import com.ztravel.common.enums.AccountType;
import com.ztravel.common.enums.InoutDetailType;
import com.ztravel.common.enums.InoutType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.payment.AccountHistoryBean;
import com.ztravel.common.payment.AccountHistoryQueryBean;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.operator.financeMantain.converter.AccountHistoryConverter;
import com.ztravel.operator.financeMantain.service.IAccountHistoryService;
import com.ztravel.operator.financeMantain.util.ExcelHeadFactoryUtil;
import com.ztravel.operator.financeMantain.util.FileNameUtil;
import com.ztravel.operator.financeMantain.vo.AccountHistoryVo;
import com.ztravel.payment.service.IFinanceService;
import com.ztravel.reuse.order.service.IOrderReuseService;
@Service("AccountHistoryService")
public class AccountHistoryService implements IAccountHistoryService{
	@Resource
	IFinanceService financeService;

	@Resource
	IMemberClientService  memberClientService;

	@Resource
	IOrderReuseService orderReuseService;

	@Override
	public void exportExcel(WebRequest request, HttpServletResponse response)
			throws Exception {

		AccountHistoryQueryBean queryBean = this.buildQueryBeanByRequest(request);
		//强制设置
		StringBuffer fileName = new StringBuffer();
		fileName.append("票券收支明细");
		fileName.append("_");
		fileName.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		fileName.append(".xls");
		OutputStream os = response.getOutputStream();
		String returnFileName = FileNameUtil.converterFileName(fileName.toString(), request);
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment;filename=" + returnFileName);
		List<AccountHistoryVo> accountHistoryVos = Lists.newArrayList();
		Pagination<AccountHistoryBean>  pagination = financeService.queryAccountHistoryInfo(queryBean);
		if(null!= pagination.getData()) {
			List<AccountHistoryBean> accountHistoryBeans = (List<AccountHistoryBean>) pagination.getData();
			accountHistoryVos = getAccountHistoryBeanVosByAccountHistoryBeans(accountHistoryBeans);
		}
		Class<AccountHistoryVo> clazz = AccountHistoryVo.class;
		ExcelHelper.getInstanse().exportToOS(os, ExcelHeadFactoryUtil.getAccountHistoryHead(), accountHistoryVos, clazz);

	}

	@Override
	public Pagination<AccountHistoryVo> search(AccountHistoryQueryBean queryBean)
			throws Exception {
		Pagination<AccountHistoryVo> searchResult = new Pagination<AccountHistoryVo>();
		int pageNo =0;
		int totalItemCount=0;
		int totalPageCount=0;
		 List<AccountHistoryVo> searchList = Lists.newArrayList();

		setMemberIdOfQueryBean(queryBean);

		Pagination<AccountHistoryBean>  pagination = financeService.queryAccountHistoryInfo(queryBean);

		List<AccountHistoryBean> accountHistoryBeans = (List<AccountHistoryBean>) pagination.getData();

		  totalItemCount =  (int) pagination.getTotalItemCount();
		  totalPageCount = (int) Math.ceil(new Double(totalItemCount)/queryBean.getPageSize() );
		 pageNo = queryBean.getPageNo();

		 if( accountHistoryBeans.size() == 0){
		 		pageNo = 1;
				totalItemCount = 0;
				totalPageCount=1;
		 }

		searchList = getAccountHistoryBeanVosByAccountHistoryBeans(accountHistoryBeans);

		searchResult.setPageNo(pageNo);
		searchResult.setPageSize(queryBean.getPageSize());
		searchResult.setTotalItemCount(totalItemCount);
		searchResult.setTotalPageCount(totalPageCount);
		searchResult.setData(searchList);
		return searchResult;
	}

	private void setMemberIdOfQueryBean(AccountHistoryQueryBean queryBean){
		if(StringUtils.isNotEmpty(queryBean.getMemberId())){
			String memberId = memberClientService.getMemberIdByMid(queryBean.getMemberId());
			if(StringUtils.isNotEmpty(memberId)){
				queryBean.setMemberId(memberId);
			}
		}
	}

	private List<AccountHistoryVo> getAccountHistoryBeanVosByAccountHistoryBeans(List<AccountHistoryBean> accountHistoryBeans) throws Exception{
		List<AccountHistoryVo> accountHistoryVos = Lists.newArrayList();
		if(null != accountHistoryBeans && accountHistoryBeans.size()	>	0){
			AccountHistoryVo accountHistoryVo = new AccountHistoryVo();
			String orderId = "";
			for(AccountHistoryBean accountHistoryBean :	accountHistoryBeans){
				accountHistoryVo = getAccountHistoryVoByAccountHistoryBean(accountHistoryBean);
				orderId = orderReuseService.getOrderIdByOrderNo(accountHistoryBean.getOrderId());
				accountHistoryVo.setOrderId(orderId);
				accountHistoryVos.add(accountHistoryVo);
			}
		}
		return accountHistoryVos;
	}

	private AccountHistoryVo getAccountHistoryVoByAccountHistoryBean(AccountHistoryBean accountHistoryBean) throws Exception{
		AccountHistoryVo accountHistoryVo = new AccountHistoryVo();
		String memberName ="";
		String mid = "";
		String memberId = "";
		memberId = accountHistoryBean.getMemberId();
		if(StringUtils.isNotEmpty(memberId)){
			String  memInfo = memberClientService.getMinMemberById(memberId);
			if(StringUtils.isNotEmpty(memInfo) && StringUtils.startsWith(memInfo, "{")) {
					JSONObject json = JSONObject.parseObject(memInfo);
					if(json.containsKey("nickName")) {
						memberName = json.getString("nickName");
						accountHistoryVo.setMemberName(memberName);
					}
					if(json.containsKey("mid")) {
						mid = json.getString("mid");
						accountHistoryVo.setMid(mid);
					}
			  }
		   }
		AccountHistoryConverter.accountHistoryBeanConverter2AccountHistoryVo(accountHistoryBean, accountHistoryVo);
		return accountHistoryVo;
	}

	private AccountHistoryQueryBean buildQueryBeanByRequest(WebRequest request) {
		AccountHistoryQueryBean queryBean = new AccountHistoryQueryBean();
		String mid = request.getParameter("mid");
		String accountType = request.getParameter("accountType");
		String productType = request.getParameter("productType");
		String inoutType = request.getParameter("inoutType");
		String inoutDetailType = request.getParameter("inoutDetailType");
		String orderId = request.getParameter("orderId");
		String conditionOperateFrom = request.getParameter("conditionOperateFrom");
		String conditionOperateTo = request.getParameter("conditionOperateTo");
		if(StringUtils.isNotEmpty(conditionOperateFrom)){
			queryBean.setConditionOperateFrom(DateTime.parse(conditionOperateFrom));
		}
		if(StringUtils.isNotEmpty(conditionOperateTo)){
			queryBean.setConditionOperateTo(DateTime.parse(conditionOperateTo));
		}
		if(StringUtils.isNotEmpty(orderId)){
			queryBean.setOrderId(orderId);
		}

		if(StringUtils.isNotEmpty(mid)){
			queryBean.setMemberId(mid);
			setMemberIdOfQueryBean(queryBean);
		}

		if(StringUtils.isNotEmpty(productType)){
			if(accountType.equals(ProductType.TRAVEL.getDescription())){
				queryBean.setProductType(ProductType.TRAVEL);
			}
		}

		if(StringUtils.isNotEmpty(accountType)){
			if(accountType.equals(AccountType.COUPON.getDescription())){
				queryBean.setAccountType(AccountType.COUPON);
			}else if(accountType.equals(AccountType.REWARD_POINT.getDescription())){
				queryBean.setAccountType(AccountType.REWARD_POINT);
			}
		}

		if(StringUtils.isNotEmpty(inoutType)){
			if(inoutType.equals(InoutType.INCOME.getDescription())){
				queryBean.setInoutType(InoutType.INCOME);
			}else if(inoutType.equals(InoutType.OUTGO.getDescription())){
				queryBean.setInoutType(InoutType.OUTGO);
			}
		}
		if(StringUtils.isNotEmpty(inoutDetailType)){
			if(inoutDetailType.equals(InoutDetailType.COUPON_EXPIRED.getDescription())){
				queryBean.setInoutDetailType(InoutDetailType.COUPON_EXPIRED);
			}else if(inoutDetailType.equals(InoutDetailType.GRANTED_COUPON.getDescription())){
				queryBean.setInoutDetailType(InoutDetailType.GRANTED_COUPON);
			}else if(inoutDetailType.equals(InoutDetailType.GRANTED_REWARD_POINT.getDescription())){
				queryBean.setInoutDetailType(InoutDetailType.GRANTED_REWARD_POINT);
			}else if(inoutDetailType.equals(InoutDetailType.PAY_ORDER.getDescription())){
				queryBean.setInoutDetailType(InoutDetailType.PAY_ORDER);
			}else if(inoutDetailType.equals(InoutDetailType.REFUND_ORDER.getDescription())){
				queryBean.setInoutDetailType(InoutDetailType.REFUND_ORDER);
			}else if(inoutDetailType.equals(InoutDetailType.SHARED_COUPON.getDescription())){
				queryBean.setInoutDetailType(InoutDetailType.SHARED_COUPON);
			}else if(inoutDetailType.equals(InoutDetailType.REWARD_POINT_EXPIRED.getDescription())){
				queryBean.setInoutDetailType(InoutDetailType.REWARD_POINT_EXPIRED);
			}
		}

		return queryBean;
	}

}
