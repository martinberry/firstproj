package com.ztravel.product.weixin.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/weixin/demo")
public class WeiXinDemoController {
	@RequestMapping(value="/test")
	public String test(){
		return "product/weixin/weixintest";
	}
}
