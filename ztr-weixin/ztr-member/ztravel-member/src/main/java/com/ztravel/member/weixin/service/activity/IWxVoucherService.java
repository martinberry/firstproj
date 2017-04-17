package com.ztravel.member.weixin.service.activity;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.CouponBookVo;

public interface IWxVoucherService {

    public AjaxResponse applyOrder(CouponBookVo couponBookVo) throws Exception;

    public AjaxResponse applyOrderWithoutValidation(CouponBookVo couponBookVo) throws Exception;

    public AjaxResponse validate(CouponBookVo couponBookVo) throws Exception;

}
