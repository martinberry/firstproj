package com.ztravel.paygate.wx.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyResponse;
import com.ztravel.paygate.wx.converter.WxPayParmConvert;
import com.ztravel.paygate.wx.service.IWxPayNotifyService;
import com.ztravel.paygate.wx.service.IWxRefundService;


/**
 * 微信支付结果通知
 * @author haofan.wan
 *
 */
@Controller
@RequestMapping("/notify")
public class PayResultNotifyController {
	private static final Logger logger = LoggerFactory.getLogger(PayResultNotifyController.class);
	@Resource
	IWxPayNotifyService wxPayNotifyService;

	@Resource
	IWxRefundService wxRefundService;

	@RequestMapping(value="/pay", method = {RequestMethod.POST})
	public void proceed(HttpServletRequest req, HttpServletResponse response) throws IOException {
		logger.info("notify pay result start..");
		ServletInputStream in = req.getInputStream() ;
		StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n, "UTF-8"));
        }
        logger.info("notify pay result toString..{}", out.toString());

        UnifieldOrderNotifyEntity unifieldOrderNotifyEntity = WxPayParmConvert.getEntityByXml(out.toString(), UnifieldOrderNotifyEntity.class) ;
		logger.info("notify pay result convert..{}", TZBeanUtils.describe(unifieldOrderNotifyEntity));
		UnifieldOrderNotifyResponse unifieldOrderNotifyResponse = wxPayNotifyService.proceed(unifieldOrderNotifyEntity) ;
		String responseToBank = WxPayParmConvert.buildXMlParamByEntity(unifieldOrderNotifyResponse, UnifieldOrderNotifyResponse.class, "xml") ;
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		bos.write(responseToBank.getBytes());
		bos.flush();
		bos.close();
	}

    @ResponseBody
	@RequestMapping(value="/refundQuery", method = {RequestMethod.GET})
	public CommonResponse proceedForRefund(HttpServletRequest req) throws Exception {
		logger.info("notify refund query result start..");
		String orderId = req.getParameter("orderId");

        logger.info("orderId:::{}", orderId);
        CommonResponse commonResponse = wxRefundService.proceedForRefund(orderId);
        return commonResponse;
	}

}
