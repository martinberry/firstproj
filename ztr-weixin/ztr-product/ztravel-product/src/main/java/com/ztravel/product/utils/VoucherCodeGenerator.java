package com.ztravel.product.utils;

import com.ztravel.common.util.SerialNumberUtil;
import com.ztravel.product.client.entity.Voucher;

public class VoucherCodeGenerator {
	
	public static void generator(Voucher voucher){
		long orgin = Long.valueOf(voucher.getVoucherId().substring(15,24)) ;
		String voucherCode = SerialNumberUtil.toSerialNumber(orgin) ;
		voucher.setVoucherCode(voucherCode.toUpperCase());
	}
	
}
