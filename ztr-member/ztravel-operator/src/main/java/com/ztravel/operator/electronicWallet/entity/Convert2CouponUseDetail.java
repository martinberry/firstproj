package com.ztravel.operator.electronicWallet.entity;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.ztravel.common.payment.CouponItemBean;

public class Convert2CouponUseDetail {


	public static CouponUseDetail  couponItemBean2CouponUseDetail(CouponItemBean s){
		CouponUseDetail  t = new CouponUseDetail();
		t.setGrantDate(convertDateTimeToStr(s.getGrantDate()));
		t.setUseDate( convertDateTimeToStr(s.getUseDate()));
		return t;
	}

	private static String convertDateTimeToStr(DateTime dateTime ){
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != dateTime){
			try{
			str =  sdf.format(dateTime.toDate());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return str;
	}
}
