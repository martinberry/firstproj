package com.ztravel.paygate.server.alipay;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.server.alipay.AlipayEnv.Args;
import com.ztravel.paygate.server.alipay.api.Rsa;
import com.ztravel.paygate.thrift.model.PaySignRequest;
import com.ztravel.paygate.thrift.model.PaySignResponse;
import com.ztravel.paygate.thrift.model.ValSignRequest;
import com.ztravel.paygate.thrift.model.ValSignResponse;
/**
 * 支付宝手机支付签名与验签
 * @author dingguangxian
 *
 */
@Service("paygate_alipay_mobile_service_handler")
public class AlipayMobileServiceHandler extends PayServiceHandler{
	private static Logger logger = LoggerFactory.getLogger(AlipayMobileServiceHandler.class);
	/**
	 * 单笔支付签名
	 */
	@Override
	public PaySignResponse paySign(PaySignRequest signRequest) throws TException {
		if(!signRequest.isMobilePay){
			return super.paySign(signRequest);
		}
		logger.info("单笔支付(mobile)签名请求:{}", TZBeanUtils.describe(signRequest));
		// 支付接口地址
		PaySignResponse signResponse = new PaySignResponse();
		Map<String, String> paramsEncode = new LinkedHashMap<String, String>();
		signResponse.setExtraInfos(paramsEncode);

		try {
			String partner = signRequest.getPartner();
			String rsaPrivateKey = alipayEnv.getPartnerArgs(partner, Args.RSA_PRIVATE_KEY);
			paramsEncode.put("partner", partner);
			paramsEncode.put("seller_id", partner);
			// 外部交易号（流水号）
			paramsEncode.put("out_trade_no", signRequest.getOrderNum());
			// 商品名称
			paramsEncode.put("subject", signRequest.getComment());
			// 金额
			paramsEncode.put("total_fee", MoneyUtil.cent2Yuan(signRequest.getAmount()));
			// 银行后台通知url(回调地址)
			paramsEncode.put("notify_url", signRequest.getBgNotifyUrl());
			// 接口名称
			paramsEncode.put("service", Args.TT_MOBILE_PAY);
			// 支付类型,强制为"1"
			paramsEncode.put("payment_type", "1");
			paramsEncode.put("_input_charset", inputCharset);
//			paramsEncode.put("it_b_pay", "120m");
			
			// 签名
			StringBuilder url = createLinkStringWithEncode(paramsEncode, partner);
			String sign = Rsa.sign(url.toString(), rsaPrivateKey);
			sign = URLEncoder.encode(sign,inputCharset);
			url.append("&sign=\"").append(sign).append("\"");
			// 签名方式
			url.append("&sign_type=\"RSA\"");

			signResponse.setBankPayUrl(StringUtils.trim(url.toString()));
			logger.info("bankPayUrl : {}", signResponse.getBankPayUrl());
			signResponse.setRetCode(PaygateError.SUCCESS.code());
			signResponse.setRetMsg(PaygateError.SUCCESS.desc());
		} catch (Throwable thr) {
			logger.error("", thr);
			signResponse.setRetCode(PaygateError.E301_SIGN_FAIL.code());
			signResponse.setRetMsg(PaygateError.E301_SIGN_FAIL.desc());
		}
		return signResponse;
	}
	
	protected StringBuilder createLinkStringWithEncode(Map<String, String> paramsEncode, String inputCharset){
		StringBuilder str = new StringBuilder();
		for(Entry<String,String> param : paramsEncode.entrySet()){
			str.append(param.getKey()).append("=\"").append(param.getValue()).append("\"&");
		}
		str.deleteCharAt(str.length()-1);
		return str;
	}
	
	@Override
	public ValSignResponse payValSign(ValSignRequest request) throws TException {
		return super.payValSign(request);
	}
	
}
