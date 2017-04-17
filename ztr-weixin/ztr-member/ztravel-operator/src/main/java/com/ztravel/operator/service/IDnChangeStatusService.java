package com.ztravel.operator.service;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.enums.VoucherType;

public interface IDnChangeStatusService {
	public AjaxResponse changeVoucherTypeInit(String vouchercode,String activityId) throws Exception;
	public AjaxResponse changeVoucherTypeAll(String activityId, String couponId, VoucherType voucherType) throws Exception;
}
