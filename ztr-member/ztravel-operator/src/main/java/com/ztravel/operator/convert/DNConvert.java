package com.ztravel.operator.convert;


import java.util.ArrayList;
import java.util.List;

import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.entity.vo.SystemLockCouponVo;

public class DNConvert {
	public static List<SystemLockCouponVo> convertFirstList2VO(List<Voucher> voucherlist){
	List<SystemLockCouponVo> dnFirstListVoList=new ArrayList<SystemLockCouponVo>();
	for(Voucher vouchertemp:voucherlist){
		SystemLockCouponVo dnfirstVo=convertFirst2VO(vouchertemp);
		dnFirstListVoList.add(dnfirstVo);
	}
	return dnFirstListVoList;


	}


	private static SystemLockCouponVo convertFirst2VO(Voucher  voucher){
		SystemLockCouponVo firstlistvo=new SystemLockCouponVo();
		firstlistvo.setVoucherCode(voucher.getVoucherCode());
		firstlistvo.setVoucherType(voucher.getVoucherType());
        return firstlistvo;
	}
}
