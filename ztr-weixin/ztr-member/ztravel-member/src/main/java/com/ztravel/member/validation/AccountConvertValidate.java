package com.ztravel.member.validation;

import com.travelzen.framework.core.util.MoneyUtil;
import com.ztravel.common.constants.ResponseConstants;

public class AccountConvertValidate {
	public static Long validateAmount(String amount) throws Exception {
		if(amount == null || amount.trim().length() == 0){
			Exception exception = new Exception(ResponseConstants.WX_CONVERT_AMOUNT_EMPTY_CODE) ;
        	throw exception ;
		};
		Long convertAmount = Long.parseLong(MoneyUtil.yuan2Cent(amount));
		if(convertAmount < 0){
			Exception exception = new Exception(ResponseConstants.WX_CONVERT_AMOUNT_LESS_ZERO_CODE) ;
        	throw exception ;
		}
		return convertAmount;
	}

	public static Long validateAmount(Long amount) throws Exception {
		if(amount == null){
			Exception exception = new Exception(ResponseConstants.WEB_CONVERT_AMOUNT_EMPTY_CODE) ;
        	throw exception ;
		};
		Long convertAmount = amount*100;
		if(convertAmount < 0){
			Exception exception = new Exception(ResponseConstants.WEB_CONVERT_AMOUNT_LESS_ZERO_CODE) ;
        	throw exception ;
		}
		return convertAmount;
	}
}
