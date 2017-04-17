package com.ztravel.paygate.wx.reslover;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztravel.paygate.wx.client.entity.RefundOrderResponseEntity;
import com.ztravel.paygate.wx.client.entity.WxPayResponse;
import com.ztravel.paygate.wx.converter.WxPayParmConvert;

public class RefundOrderResponseResolver {

	private static final Logger logger = LoggerFactory.getLogger(RefundOrderResponseResolver.class);

	public static WxPayResponse proceed(String response) {

		logger.info("微信申请退款http报文:  "+ response);

		WxPayResponse refundResponse = null ;

		if (StringUtils.isBlank(response)) {
			refundResponse =  WxPayResponse.instance("FAIL", "http请求无结果");
		} else {
			RefundOrderResponseEntity refundOrderResponse = WxPayParmConvert.getEntityByXml(response, RefundOrderResponseEntity.class);

			if (null != refundOrderResponse && refundOrderResponse.getReturn_code().equals("SUCCESS")) {
				if (refundOrderResponse.getResult_code().equals("SUCCESS")) {
				    refundResponse =  WxPayResponse.instance("SUCCESS", "SUCCESS");
				} else {
					refundResponse = WxPayResponse.instance("FAIL", "微信申请退款业务失败,检查通信报文");
				}
			} else {
				refundResponse = WxPayResponse.instance("FAIL", "微信申请退款通信失败,检查通信报文");
			}

		}
		logger.info("报文解析结果:  "+ refundResponse.toString());
		return refundResponse ;
	}

}
