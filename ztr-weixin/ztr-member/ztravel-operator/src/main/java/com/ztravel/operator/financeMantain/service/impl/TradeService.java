package com.ztravel.operator.financeMantain.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.poi.util.ExcelHelper;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.TradeStatus;
import com.ztravel.common.enums.TradeType;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.payment.TradeQueryBean;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.operator.financeMantain.converter.TradeConverter;
import com.ztravel.operator.financeMantain.service.ITradeService;
import com.ztravel.operator.financeMantain.util.ExcelHeadFactoryUtil;
import com.ztravel.operator.financeMantain.util.FileNameUtil;
import com.ztravel.operator.financeMantain.vo.TradeVo;
import com.ztravel.payment.service.IFinanceService;
import com.ztravel.reuse.order.service.IOrderReuseService;
@Service("TradeService")
public class TradeService implements ITradeService{
	private static Logger logger = RequestIdentityLogger.getLogger(TradeService.class);
	@Resource
	IFinanceService financeService;

	@Resource
	IMemberClientService  memberClientService;

	@Resource
	IOrderReuseService orderReuseService;

	@Override
	public void exportExcel(WebRequest request, HttpServletResponse response) throws Exception {
		TradeQueryBean tradeQuery = this.buildQueryBeanByRequest(request);
		//强制设置
		StringBuffer fileName = new StringBuffer();
		fileName.append("交易明细");
		fileName.append("_");
		fileName.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		fileName.append(".xls");
		OutputStream os = response.getOutputStream();
		String returnFileName = FileNameUtil.converterFileName(fileName.toString(), request);
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment;filename=" + returnFileName);
		List<TradeVo> tradeVos = Lists.newArrayList();
		logger.info("excel调用财务结构查询交易信息开始:params:"+TZBeanUtils.describe(tradeQuery));
		Pagination<TradeBean>  pagination = financeService.queryTradeInfo(tradeQuery);
		logger.info("excel调用财务结构查询交易信息结束;pagination:"+TZBeanUtils.describe(pagination));
		if(null!= pagination.getData()) {
			List<TradeBean> tradeBeans = (List<TradeBean>) pagination.getData();
			tradeVos = getTradeVosByTradeBeans(tradeBeans);
		}
		Class<TradeVo> clazz = TradeVo.class;
		ExcelHelper.getInstanse().exportToOS(os, ExcelHeadFactoryUtil.getTradeReportHead(), tradeVos, clazz);
	}

	private TradeQueryBean buildQueryBeanByRequest(WebRequest request) {
		TradeQueryBean tradeQuery = new TradeQueryBean();
		String mid = request.getParameter("mid");
		String productType = request.getParameter("productType");
		String orderId = request.getParameter("orderId");
		String paymentType = request.getParameter("paymentType");
		String tradeType = request.getParameter("tradeType");
		String tradeStatus = request.getParameter("tradeStatus");
		String conditionTradeFrom = request.getParameter("conditionTradeFrom");
		String conditionTradeTo = request.getParameter("conditionTradeTo");
		String traceNum = request.getParameter("traceNum");
		if(StringUtils.isNotEmpty(traceNum)){
			tradeQuery.setTraceNum(traceNum);
		}
		if(StringUtils.isNotEmpty(conditionTradeFrom)){
			tradeQuery.setConditionTradeFrom(DateTime.parse(conditionTradeFrom));
		}
		if(StringUtils.isNotEmpty(conditionTradeTo)){
			tradeQuery.setConditionTradeTo(DateTime.parse(conditionTradeTo));
		}
		if(StringUtils.isNotEmpty(mid)){
			tradeQuery.setMemberId(mid);
			setMemberIdOfQueryBean(tradeQuery);
		}
		if(StringUtils.isNotEmpty(orderId)){
			tradeQuery.setOrderId(orderId);
		}

		if(StringUtils.isNotEmpty(tradeType)){
			if(tradeType.equals(TradeType.PAYMENT.getDescription())){
				tradeQuery.setTradeType(TradeType.PAYMENT);
			}else if(tradeType.equals(TradeType.REFUND.getDescription())){
				tradeQuery.setTradeType(TradeType.REFUND);
			}
		}

		if(StringUtils.isNotEmpty(productType)){
			if(productType.equals(ProductType.TRAVEL.getDescription())){
				tradeQuery.setProductType(ProductType.TRAVEL);
			}
		}

		if(StringUtils.isNotEmpty(tradeStatus)){
			if(tradeStatus.equals(TradeStatus.FAIL.getDescription())){
				tradeQuery.setTradeStatus(TradeStatus.FAIL);
			}else if(tradeStatus.equals(TradeStatus.REQUEST.getDescription())){
				tradeQuery.setTradeStatus(TradeStatus.REQUEST);
			}else if(tradeStatus.equals(TradeStatus.SUCCESS.getDescription())){
				tradeQuery.setTradeStatus(TradeStatus.SUCCESS);
			}else if(tradeStatus.equals(TradeStatus.UNKNOWN.getDescription())){
				tradeQuery.setTradeStatus(TradeStatus.UNKNOWN);
			}
		}
		if(StringUtils.isNotEmpty(paymentType)){
			if(paymentType.equals(PaymentType.Alipay.getDescription())){
				tradeQuery.setPaymentType(PaymentType.Alipay);
			}else if(paymentType.equals(PaymentType.WeChatpay.getDescription())){
				tradeQuery.setPaymentType(PaymentType.WeChatpay);
			}else if(paymentType.equals(PaymentType.Coupon.getDescription())){
				tradeQuery.setPaymentType(PaymentType.Coupon);
			}else if(paymentType.equals(PaymentType.RewardPoint.getDescription())){
				tradeQuery.setPaymentType(PaymentType.RewardPoint);
			}
		}

		return tradeQuery;
	}

	private void setMemberIdOfQueryBean(TradeQueryBean tradeQuery){
		if(StringUtils.isNotEmpty(tradeQuery.getMemberId())){
			String memberId = memberClientService.getMemberIdByMid(tradeQuery.getMemberId());
			if(StringUtils.isNotEmpty(memberId)){
				tradeQuery.setMemberId(memberId);
			}
		}
	}

	private List<TradeVo> getTradeVosByTradeBeans(List<TradeBean> tradeBeans) throws Exception{
		List<TradeVo> tradeVos = Lists.newArrayList();
		if(null != tradeBeans && tradeBeans.size()	>	0){
			TradeVo tradeVo = new TradeVo();
			String orderId = "";
			for(TradeBean tradeBean :	tradeBeans){
				tradeVo = getTradeVoByTradeBean(tradeBean);
				orderId = orderReuseService.getOrderIdByOrderNo(tradeBean.getOrderId());
				tradeVo.setOrderId(orderId);
				tradeVos.add(tradeVo);
			}
		}
		return tradeVos;
	}

	private TradeVo getTradeVoByTradeBean(TradeBean tradeBean) throws Exception{
		TradeVo tradeVo = new TradeVo();

		String memberName ="";
		String mid = "";
		String memberId = "";
		memberId = tradeBean.getMemberId();
		if(StringUtils.isNotEmpty(memberId)){
			String  memInfo = memberClientService.getMinMemberById(memberId);
			if(StringUtils.isNotEmpty(memInfo) && StringUtils.startsWith(memInfo, "{")) {
					JSONObject json = JSONObject.parseObject(memInfo);
					if(json.containsKey("nickName")) {
						memberName = json.getString("nickName");
						tradeVo.setMemberName(memberName);
					}
					if(json.containsKey("mid")) {
						mid = json.getString("mid");
						tradeVo.setMid(mid);
					}
			  }
		   }
		TradeConverter.tradeBeanConverter2TradeVo(tradeBean, tradeVo);

		return tradeVo;
	}

	@Override
	public Pagination<TradeVo> search(TradeQueryBean tradeQuery) throws Exception {
		Pagination<TradeVo> searchResult = new Pagination<TradeVo>();
		int pageNo =0;
		int totalItemCount=0;
		int totalPageCount=0;
		 List<TradeVo> searchList = Lists.newArrayList();

		setMemberIdOfQueryBean(tradeQuery);
		logger.info("调用财务结构查询交易信息开始:params:"+TZBeanUtils.describe(tradeQuery));
		Pagination<TradeBean>  pagination = financeService.queryTradeInfo(tradeQuery);
		logger.info("调用财务结构查询交易信息结束;pagination:"+TZBeanUtils.describe(pagination));
		List<TradeBean> tradeBeans = (List<TradeBean>) pagination.getData();

		  totalItemCount =  (int) pagination.getTotalItemCount();

		  totalPageCount = (int) Math.ceil(new Double(totalItemCount)/tradeQuery.getPageSize() );
		 pageNo = tradeQuery.getPageNo();

		 if( tradeBeans.size() == 0){
		 		pageNo = 1;
				totalItemCount = 0;
				totalPageCount=1;
		 }
		searchList = getTradeVosByTradeBeans(tradeBeans);

		searchResult.setPageNo(pageNo);
		searchResult.setPageSize(tradeQuery.getPageSize());
		searchResult.setTotalItemCount(totalItemCount);
		searchResult.setTotalPageCount(totalPageCount);
		searchResult.setData(searchList);
		return searchResult;
	}
}
