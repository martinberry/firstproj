package com.ztravel.paygate.server.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztravel.paygate.server.alipay.AlipayEnv.Args;
import com.ztravel.paygate.server.alipay.api.AlipaySignMD5;
import com.ztravel.paygate.server.alipay.api.Rsa;
/**
 * 服务组件基类
 * @author dingguangxian
 *
 */
public abstract class AbstractServiceHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final AlipayEnv alipayEnv = AlipayEnv.instance();
	protected final String inputCharset = Args.CHARSET_UTF8;
	protected final String ALIPAY_URL="https://mapi.alipay.com/gateway.do";
	protected final String ALIPAY_URL_CHARSET="https://mapi.alipay.com/gateway.do?_input_charset="+inputCharset;

	protected String getPartner(String defaultPartner) {
		String partner = defaultPartner;
		if(StringUtils.isBlank(partner)){
			partner = alipayEnv.getArgs(Args.PARTNER);
		}
		logger.info("处理中的partner:{}", partner);
		return partner;
	}
	
	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * 
	 * @param params
	 *            通知返回来的参数数组
	 * @return 验证结果
	 */
	protected boolean verifyResponseData(String preSignStr, String partner, String sign,String signType, String notifyId) {
		boolean isVerify = false;
		if(Args.SIGN_TYPE_RSA.equals(signType)){
			isVerify = Rsa.doCheck(preSignStr, sign, alipayEnv.getArgs(Args.RSA_PUBLIC_KEY));
		} else {
			isVerify = AlipaySignMD5.verify(preSignStr, sign, alipayEnv.getPartnerArgs(partner, Args.SIGN_KEY), inputCharset);
		}
		// 验证
		// responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// mysign与sign不等，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		if (isVerify) {
			logger.info("本地验签成功.preSignStr={}", preSignStr);
			String responseTxt = "true";
			if (notifyId != null) {
				responseTxt = verifyResponse(notifyId,partner);
				logger.info("远程验签返回结果.preSignStr={},notifyId={}", preSignStr, notifyId);
			}
			return "true".equals(responseTxt);
		} else {
			return false;
		}
	}
	

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notifyId
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true 返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	protected String verifyResponse(String notifyId,String partner) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

//		String partner = alipayEnv.getArgs(Args.PATNER);
		String veryfy_url = alipayEnv.getArgs(Args.URL_VERIFY) + "partner=" + partner + "&notify_id=" + notifyId;

		return checkUrl(veryfy_url);
	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true 返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private String checkUrl(String urlvalue) {
		String inputLine = "";

		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			inputLine = "";
		}

		return inputLine;
	}
}
