package com.ztravel.operator.financeMantain.converter;

import org.apache.commons.lang3.StringUtils;

import com.ztravel.common.enums.AccountStatus;
import com.ztravel.common.payment.AccountSummaryBean;
import com.ztravel.common.util.MoneyCalculator;
import com.ztravel.operator.financeMantain.vo.AccountSummaryVo;

public class AccountSummaryConverter {

	public static void accountSummaryBeanConverter2AccountSummaryVo(AccountSummaryBean s,	AccountSummaryVo t) {
		if(StringUtils.isNotEmpty(s.getMemberId())){
			t.setMemberId(s.getMemberId());
		}
		AccountStatus accountStatus = s.getAccountStatus();
		if(accountStatus != null){
			if(accountStatus.equals(AccountStatus.AVAILABLE)){
				t.setIsAccountFrozen("否");
			}else if(accountStatus.equals(AccountStatus.FROZEN)){
				t.setIsAccountFrozen("是");
			}
		}
		if(s.getAccountType() != null){
			t.setAccountType(s.getAccountType().getDescription());
		}
		t.setAmount(new MoneyCalculator(s.getAmount()).fenToYuan().toString());
		t.setAvailableAmount(new MoneyCalculator(s.getAvailableAmount()).fenToYuan().toString());
		t.setFrozenAmount(new MoneyCalculator(s.getFrozenAmount()).fenToYuan().toString());
	}

}
