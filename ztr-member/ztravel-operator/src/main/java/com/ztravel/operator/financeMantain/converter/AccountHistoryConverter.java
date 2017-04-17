package com.ztravel.operator.financeMantain.converter;

import com.ztravel.common.enums.InoutType;
import com.ztravel.common.payment.AccountHistoryBean;
import com.ztravel.common.util.MoneyCalculator;
import com.ztravel.operator.financeMantain.util.DateTime2StrUtil;
import com.ztravel.operator.financeMantain.vo.AccountHistoryVo;

public class AccountHistoryConverter {

	public static AccountHistoryVo accountHistoryBeanConverter2AccountHistoryVo(AccountHistoryBean s,	AccountHistoryVo t) throws Exception{
		t.setAccountType(s.getAccountType().getDescription());
		t.setAmount(s.getAmount());
		if(null != s.getInoutDetailType()){
			t.setInoutDetailType(s.getInoutDetailType().getDescription());
		}
	    if(null != s.getInoutType()){
	    	t.setInoutType(s.getInoutType().getDescription());

	       long amount = s.getAmount();
	    	if(s.getInoutType().equals(InoutType.INCOME)){
				t.setInCome(new MoneyCalculator(amount).fenToYuan().toString());
			}else if(s.getInoutType().equals(InoutType.OUTGO)){
				t.setOutGo(new MoneyCalculator(s.getAmount()).fenToYuan().toString());
			}
	    }
		t.setOperateDate(DateTime2StrUtil.dateTime2Str(s.getOperateDate()));
		t.setOrderNo(s.getOrderId());
		if(null != s.getProductType()){
			t.setProductType(s.getProductType().getDescription());
		}
		return t;
	}

}
