package com.ztravel.member.weixin.service;

import com.ztravel.common.bean.AjaxResponse;

public interface IWxAccountConvertService {

	//微信端兑换余额
	AjaxResponse convert(String mobile,Long amount);
}
