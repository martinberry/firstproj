package com.ztravel.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PriceUtil {


	public static String cent2yuan(Long cent) {

		if(cent == null){
			return null;
		}

		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String yuan = decimalFormat.format(cent.longValue() / 100.0);
		return yuan;

	}

	public static Long yuan2cent(String yuan) {

		if(yuan == null){
			return null;
		}
		BigDecimal bd = new BigDecimal(yuan);
		Long cent = bd.longValue() * 100;
		return cent;

	}

}
