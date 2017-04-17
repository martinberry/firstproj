package com.ztravel.member.weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class VoucherCodePreSellController {
	@RequestMapping("/activity/coupon/weixin/couponList")
	public String couponList(){
		return "member/weixin/voucher_order_pre_sell" ;
	}
}
