package com.ztravel.common.payment;

import org.codehaus.plexus.util.StringUtils;

import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;

public class PaymentTestArgs {
	public static final String CLIENT_PATH = "ztr-payment/payment-server.properties";
	
	public static long payAmount = 0l  ;
	
	static {
		String payAmountConfig = TopsConfReader.getConfContent(CLIENT_PATH, "ztr.payment.payAmount", ConfScope.R) ;
		if(StringUtils.isNotEmpty(payAmountConfig)){
			payAmount = Long.parseLong(payAmountConfig) ;
		}
	}
	
}
