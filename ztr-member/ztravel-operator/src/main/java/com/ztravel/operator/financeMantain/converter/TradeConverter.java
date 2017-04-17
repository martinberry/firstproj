package com.ztravel.operator.financeMantain.converter;

import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.util.MoneyCalculator;
import com.ztravel.operator.financeMantain.util.DateTime2StrUtil;
import com.ztravel.operator.financeMantain.vo.TradeVo;

public class TradeConverter {

	public static TradeVo tradeBeanConverter2TradeVo(TradeBean s,	TradeVo t) throws Exception{
		t.setCouponItemId(s.getCouponItemId());
		t.setOrderAmount(new MoneyCalculator(s.getOrderAmount()).fenToYuan().toString());
		t.setOrderNo(s.getOrderId());
		t.setOrderType(s.getOrderType().getDescription());
		t.setPaymentType(s.getPaymentType().getDescription());
		t.setProductType(s.getProductType().getDescription());
		t.setTraceNum(s.getTraceNum());
		t.setTradeAmount(new MoneyCalculator(s.getTradeAmount()).fenToYuan().toString());
		t.setTradeDate(DateTime2StrUtil.dateTime2Str(s.getTradeDate()));
		t.setTradeId(s.getTradeId());
		t.setTradeStatus(s.getTradeStatus().getDescription());
		t.setTradeType(s.getTradeType().getDescription());
		return t;
	}

}
