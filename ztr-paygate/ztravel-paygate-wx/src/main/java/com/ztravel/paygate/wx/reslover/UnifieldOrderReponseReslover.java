package com.ztravel.paygate.wx.reslover;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztravel.paygate.wx.client.entity.UnifieldOrderResponse;
import com.ztravel.paygate.wx.client.entity.WxPayResponse;
import com.ztravel.paygate.wx.converter.WxPayParmConvert;

/**
 * 统一下单支付结果解析
 * @author liuzhuo
 *
 */
public class UnifieldOrderReponseReslover {
	private static final Logger LOG = LoggerFactory.getLogger(UnifieldOrderReponseReslover.class);
	
	public static WxPayResponse proceed(String response, String tradeType) {
		
		LOG.info("微信支付统一下单http200报文:  "+ response);
		
		WxPayResponse wxPayResponse = null ;
		
		if(StringUtils.isBlank(response)) {
			wxPayResponse =  WxPayResponse.instance("FAIL", "http请求无结果");
		}else {
			UnifieldOrderResponse unifieldOrderResponse = WxPayParmConvert.getEntityByXml(response,UnifieldOrderResponse.class);
			
			if(null != unifieldOrderResponse && 
					unifieldOrderResponse.getReturn_code().equals("SUCCESS")) {
				if(unifieldOrderResponse.getResult_code().equals("SUCCESS")) {
					if("JSAPI".equals(tradeType)){
						wxPayResponse =  WxPayResponse.instance("SUCCESS", unifieldOrderResponse.getPrepay_id());
					}else if("NATIVE".equals(tradeType)){
						wxPayResponse =  WxPayResponse.instance("SUCCESS", unifieldOrderResponse.getCode_url());
					}
				}else {
					wxPayResponse = WxPayResponse.instance("FAIL", "微信支付统一下单业务失败,检查通信报文");
				}
			}else {
				wxPayResponse = WxPayResponse.instance("FAIL", "微信支付统一下单通信失败,检查通信报文");
			}
			
		}
		LOG.info("报文解析结果:  "+ wxPayResponse.toString());
		return wxPayResponse ;
	}

}
