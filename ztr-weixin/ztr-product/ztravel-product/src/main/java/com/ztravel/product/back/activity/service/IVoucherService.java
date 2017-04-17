package com.ztravel.product.back.activity.service;

import java.util.List;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.product.back.activity.vo.CouponVo;
import com.ztravel.product.client.entity.Voucher;

public interface IVoucherService {
	public AjaxResponse updateVoucher(CouponVo couponvo);
	public List<Voucher> selectVoucher() throws Exception;
}
