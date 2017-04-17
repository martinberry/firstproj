package com.ztravel.operator.financeMantain.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.travelzen.framework.poi.util.ExcelColumn;
import com.travelzen.framework.poi.util.ExcelHead;

public class ExcelHeadFactoryUtil {

	public static ExcelHead getTradeReportHead() {
		int i=0;
		List<ExcelColumn> columns=Arrays.asList(
				new ExcelColumn(i++,"memberName","昵称"),
				new ExcelColumn(i++,"mid","会员ID"),
				new ExcelColumn(i++,"orderNo","订单号"),
				new ExcelColumn(i++,"productType","业务类型"),
				new ExcelColumn(i++,"tradeDate","交易日期","date"),
				new ExcelColumn(i++,"orderAmount","订单金额"),
				new ExcelColumn(i++,"tradeAmount","支付金额"),
				new ExcelColumn(i++,"paymentType","支付方式"),
				new ExcelColumn(i++,"tradeType","支付类型"),
				new ExcelColumn(i++,"tradeStatus","支付状态"),
				new ExcelColumn(i++,"traceNum","银行订单号")
				);
		return generateHead(columns);
	}

	public static ExcelHead getAccountSummaryHead() {
		int i=0;
		List<ExcelColumn> columns=Arrays.asList(
				new ExcelColumn(i++,"memberName","昵称"),
				new ExcelColumn(i++,"mid","会员ID"),
				new ExcelColumn(i++,"accountType","账户类型"),
				new ExcelColumn(i++,"amount","账户余额"),
				new ExcelColumn(i++,"availableAmount","可用余额"),
				new ExcelColumn(i++,"frozenAmount","冻结余额"),
				new ExcelColumn(i++,"isAccountFrozen","账户冻结")
				);
		return generateHead(columns);
	}

	public static ExcelHead getAccountHistoryHead() {
		int i=0;
		List<ExcelColumn> columns=Arrays.asList(
				new ExcelColumn(i++,"memberName","昵称"),
				new ExcelColumn(i++,"mid","会员ID"),
				new ExcelColumn(i++,"orderId","订单号"),
				new ExcelColumn(i++,"productType","业务类型"),
				new ExcelColumn(i++,"accountType","账户类型"),
				new ExcelColumn(i++,"inCome","收入"),
				new ExcelColumn(i++,"outGo","支出"),
				new ExcelColumn(i++,"inoutDetailType","收支分类"),
				new ExcelColumn(i++,"operateDate","操作日期","date")
				);
		return generateHead(columns);
	}

	public static ExcelHead getSupplierHead() {
		int i=0;
		List<ExcelColumn> columns=Arrays.asList(
				new ExcelColumn(i++,"supplierName","供应商名称"),
				new ExcelColumn(i++,"bussinessContacts","业务联系人"),
				new ExcelColumn(i++,"phone","电话"),
				new ExcelColumn(i++,"fax","传真"),
				new ExcelColumn(i++,"email","电邮")
				);
		return generateHead(columns);
	}

	@SuppressWarnings("rawtypes")
	protected static ExcelHead generateHead(List<ExcelColumn> columns) {
		ExcelHead head = new ExcelHead();
		head.setColumnCount(1);
		head.setColumns(columns);
		Map<String, Map> excelColumnsConvertMap = new HashMap<String, Map>();
		head.setColumnsConvertMap(excelColumnsConvertMap);
		return head;
	}
}
