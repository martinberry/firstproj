package com.ztravel.member.validation;

import org.springframework.util.Assert;

import com.ztravel.common.entity.CouponBookVo;

public class VoucherBuyValidate {

    public static void validate(CouponBookVo couponBookVo) throws Exception {
        Assert.hasText(couponBookVo.getCouponId(), "代金券ID为空");
        Assert.isTrue(couponBookVo.getBookAmount() > 0 && couponBookVo.getBookAmount() <= 12,"购买数量须在1到12之间");
        Assert.hasText(couponBookVo.getMobile(), "输入手机号为空");
        Assert.hasText(couponBookVo.getVerifyCode(), "输入验证码为空");
    }

}
