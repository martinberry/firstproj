package com.ztravel.paygate.server.alipay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.api.alipay.AlipayParser;
import com.ztravel.paygate.api.alipay.model.QueryResultModel;
import com.ztravel.paygate.api.alipay.model.RefundResultModel;
import com.ztravel.paygate.core.enums.PayState;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.server.alipay.AlipayEnv.Args;
import com.ztravel.paygate.server.alipay.api.AlipayCore;
import com.ztravel.paygate.server.alipay.api.AlipaySignMD5;
import com.ztravel.paygate.thrift.model.PaySignRequest;
import com.ztravel.paygate.thrift.model.PaySignResponse;
import com.ztravel.paygate.thrift.model.QueryRequest;
import com.ztravel.paygate.thrift.model.QueryResponse;
import com.ztravel.paygate.thrift.model.RefundQueryRequest;
import com.ztravel.paygate.thrift.model.RefundQueryResponse;
import com.ztravel.paygate.thrift.model.RefundRequest;
import com.ztravel.paygate.thrift.model.RefundResponse;
import com.ztravel.paygate.thrift.model.RefundShareProfitModel;
import com.ztravel.paygate.thrift.model.RefundValSignRequest;
import com.ztravel.paygate.thrift.model.RefundValSignResponse;
import com.ztravel.paygate.thrift.model.ValSignRequest;
import com.ztravel.paygate.thrift.model.ValSignResponse;
/**
 * 基本支付/退款相关的服务组件
 * @author dingguangxian
 *
 */
@Component("alipay_pay_service_handler")
public class PayServiceHandler extends AbstractServiceHandler{

	/**
	 * 单笔支付签名
	 */
	public PaySignResponse paySign(PaySignRequest signRequest) throws TException {
		logger.info("单笔支付签名请求:{}", TZBeanUtils.describe(signRequest));
		String partner = getPartner(signRequest.getPartner());
		// 支付接口地址
		String paymentURL = alipayEnv.getArgs(Args.URL_SINGLE_PAY);

		PaySignResponse signResponse = new PaySignResponse();
		Map<String, String> paramsEncode = new HashMap<String, String>();
		signResponse.setExtraInfos(paramsEncode);

		try {
			paramsEncode.put("_input_charset", inputCharset);
			// 银行后台通知url(回调地址)
			paramsEncode.put("notify_url", signRequest.getBgNotifyUrl());
			// 外部交易号（流水号）
			paramsEncode.put("out_trade_no", signRequest.getOrderNum());
			// 合作伙伴
			paramsEncode.put("partner", partner);
			// 支付类型,强制为"1"
			paramsEncode.put("payment_type", "1");
			// 银行前台通知url
			paramsEncode.put("return_url", signRequest.getFgNotifyUrl());

			// 卖家Email
			paramsEncode.put("seller_email", alipayEnv.getPartnerArgs(partner, Args.SELLER_EMAIL));
			// url.append("&").append("seller_id").append("=").append(encodeParams(alipayEnv.getArgs(Args.SELLER_EMAIL)));
			// urlcode.append("&").append("seller_id").append("=").append(alipayEnv.getArgs(Args.SELLER_EMAIL));

			// 接口名称
			paramsEncode.put("service", Args.TT_SINGLE_PAYMENT);
			// 商品名称
			paramsEncode.put("subject", signRequest.getComment());
			// 金额
			paramsEncode.put("total_fee", MoneyUtil.cent2Yuan(signRequest.getAmount()));
			// 签名
			StringBuilder url = new StringBuilder(AlipayCore.createLinkStringWithEncode(paramsEncode, inputCharset));
			String encodeParams = AlipayCore.createLinkString(paramsEncode);
			String sign = AlipaySignMD5.sign(encodeParams, alipayEnv.getPartnerArgs(partner, Args.SIGN_KEY), inputCharset);
			url.append("&sign=").append(sign);
			// 签名方式
			url.append("&sign_type=").append(alipayEnv.getPartnerArgs(partner, Args.SIGN_TYPE));

			signResponse.setBankPayUrl(StringUtils.trim(paymentURL + "?" + url.toString()));
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

	/**
	 * 单笔支付结果验签
	 */
	public ValSignResponse payValSign(ValSignRequest request) throws TException {
		logger.info("单笔支付结果验签:{}", TZBeanUtils.describe(request));
		String partner = getPartner(request.getPartner());
		ValSignResponse response = new ValSignResponse();
		boolean fgNotify = request.isFgNotify();
		Map<String, String> bankResponseData = request.getBankResponseData();
		try {
			// 过滤空值、sign与sign_type参数
			Map<String, String> sParaNew = AlipayCore.paraFilter(bankResponseData);
			// 获取待签名字符串
			String preSignStr = AlipayCore.createLinkString(sParaNew);
			// 进行签名验证
			String sign = "";
			if (bankResponseData.get("sign") != null) {
				sign = bankResponseData.get("sign");
			}
			// 对前台请求，不进行远程校验
			String notifyId = fgNotify ? null : bankResponseData.get("notify_id");
			// 进行签名验证(前台不验签)
			boolean verify = fgNotify ? true : verifyResponseData(preSignStr,partner, sign, bankResponseData.get("sign_type"), notifyId);
			logger.info("response data ::: {}", preSignStr);
			logger.info("verify result ::: {}", verify);
			if (verify) {
				response.setRetCode(PaygateError.SUCCESS.code());
				response.setRetMsg(PaygateError.SUCCESS.desc());
				response.setOrderNum(StringUtils.trim(bankResponseData.get("out_trade_no")));
				response.setTraceNum(StringUtils.trim(bankResponseData.get("trade_no")));
				response.setAmount(Long.parseLong(MoneyUtil.yuan2Cent(StringUtils.trim(bankResponseData.get("total_fee")))));
			} else {
				response.setRetCode(PaygateError.E302_VAL_SIGN_FAIL.code());
				response.setRetMsg(PaygateError.E302_VAL_SIGN_FAIL.desc());
			}
		} catch (Throwable thr) {
			logger.error("", thr);
			response.setRetCode(PaygateError.E302_VAL_SIGN_FAIL.code());
			response.setRetMsg(PaygateError.E302_VAL_SIGN_FAIL.desc());
		}
		return response;
	}

	public QueryResponse query(QueryRequest queryRequest) throws TException {
		logger.info("查询请求,{}", TZBeanUtils.describe(queryRequest));
		String partner = getPartner(queryRequest.getPartner());
		String signKey = alipayEnv.getPartnerArgs(partner, Args.SIGN_KEY);
		QueryResponse response = new QueryResponse();
		Map<String, String> paramsEncode = new HashMap<String, String>();
		// 支付宝接口地址
		String httpURL = alipayEnv.getArgs(Args.URL_QUERY) + "?_input_charset=" + Args.CHARSET_UTF8;

		try {
			paramsEncode.put("_input_charset", inputCharset);
			// 订单号
			paramsEncode.put("out_trade_no", queryRequest.getOrderNum());
			// 订单号
			if (queryRequest.getTraceNum() != null && !queryRequest.getTraceNum().equals("")) {
				paramsEncode.put("trade_no", queryRequest.getTraceNum());
			}

			// 合作伙伴
			paramsEncode.put("partner", partner);
			// 接口名称
			paramsEncode.put("service", Args.TT_QUERY);
			/*
			 * 向支付宝发送https请求
			 */
			String alipayResponse = AlipayCore.requestAlipay(httpURL, signKey, paramsEncode);
			logger.info("查询返回结果:{}", alipayResponse);
			Map<String, String> responseData = new HashMap<String, String>();
			response.setExtraInfos(responseData);
			QueryResultModel model = AlipayParser.parseQueryResult(alipayResponse);

			if ("T".equals(model.is_success)) {// 查询成功
				logger.info("查询结果正确返回..");
				responseData.putAll(model.tradeDatas());
				logger.info("预验签串:{}", AlipayCore.createLinkString(AlipayCore.paraFilter(model.tradeDatas())));
				boolean verify = AlipaySignMD5.verify(AlipayCore.createLinkString(AlipayCore.paraFilter(model.tradeDatas())), model.sign,
						signKey, inputCharset);
				if (verify) {// 验签成功
					logger.info("验签成功..{}", model.tradeDatas());
					response.setRetCode(PaygateError.SUCCESS.code());
					response.setRetMsg(PaygateError.SUCCESS.desc());
				} else {
					logger.info("验签失败..");
					// 验签失败
					response.setRetCode(PaygateError.E302_VAL_SIGN_FAIL.code());
					response.setRetMsg(PaygateError.E302_VAL_SIGN_FAIL.desc());
				}
				String tradeStatus = model.tradeDatas().get("trade_status");
				if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
					// 交易成功
					response.setPayState(PayState.SUCCESS.name());
				} else {
					response.setPayState(PayState.FAIL.name());
				}
				response.setTraceNum(responseData.get("trade_no"));
				response.setOrderNum(responseData.get("out_trade_no"));
				response.setAmount(MoneyUtil.yuan2Cent(new BigDecimal(responseData.get("total_fee"))));
			} else {
				logger.info("查询结果返回错误信息:{}", model.error);
				response.setRetCode(PaygateError.E310_QUERY_FAIL.code());
				response.setRetMsg(PaygateError.E310_QUERY_FAIL.desc() + ":" + model.error);
			}
		} catch (Throwable thr) {
			logger.error("", thr);
			response.setRetCode(PaygateError.E310_QUERY_FAIL.code());
			response.setRetMsg(PaygateError.E310_QUERY_FAIL.desc());
		}
		return response;
	}

	public RefundResponse refund(RefundRequest refundRequest) throws TException {
		logger.info("支付宝退款请求:{}", TZBeanUtils.describe(refundRequest));
		String partner = getPartner(refundRequest.getPartner());
		String email = alipayEnv.getPartnerArgs(partner, Args.SELLER_EMAIL);
		String signKey = alipayEnv.getPartnerArgs(partner, Args.SIGN_KEY);
		RefundResponse response = new RefundResponse();
		// 接口地址
		String refundUrl = alipayEnv.getArgs(Args.URL_REFUND);

		Map<String, String> paramsEncode = new HashMap<String, String>();
		response.setExtraInfos(paramsEncode);

		try {
			paramsEncode.put("_input_charset", inputCharset);
			// 接口名称
			paramsEncode.put("service", Args.TT_REFUND);
			// 合作伙伴
			paramsEncode.put("partner", partner);
			// 银行后台通知url(回调地址)
			paramsEncode.put("notify_url", refundRequest.getNotifyUrl());

			// 卖家Email
			paramsEncode.put("seller_email", email);
			// url.append("&").append("seller_id").append("=").append(encodeParams(alipayEnv.getArgs(Args.SELLER_EMAIL)));
			// urlcode.append("&").append("seller_id").append("=").append(alipayEnv.getArgs(Args.SELLER_EMAIL));

			// 商品名称
			paramsEncode.put("refund_date", refundRequest.getRefundTime());
			// 退款标识
			paramsEncode.put("batch_no", refundRequest.getRefundNum());
			// 退款笔数
			paramsEncode.put("batch_num", "1");
			// 退款数据集, 退单号^退交易金额^退交易备注|转出人 Email_1(原收到分润金额的账户)^转出人userId_1^转入人 Email_1(原付出分润金额的账户)^转入人 userId_1^退款金额^退款理由
			StringBuilder data = new StringBuilder(refundRequest.getTraceNum() + "^" + MoneyUtil.cent2Yuan(refundRequest.getRefundAmount()) + "^"
					+ refundRequest.getComment());
			List<RefundShareProfitModel> refundProfitDetails = refundRequest.getRefundProfitDetails();
			if(refundProfitDetails != null && refundProfitDetails.size() > 0){
				for(RefundShareProfitModel model : refundProfitDetails){
					data.append("|").append(model.refundAccount).append("^^")
						.append(email).append("^^").append(MoneyUtil.cent2Yuan(model.amount))
						.append("^").append(StringUtils.trimToEmpty(model.comment));
				}
			}
			paramsEncode.put("use_freeze_amount", "Y");
			paramsEncode.put("detail_data", data.toString());
			/*
			 * 向支付宝发送退款请求
			 */
			String alipayRespone = AlipayCore.requestAlipay(refundUrl + "?_input_charset=" + inputCharset, signKey, paramsEncode);
			logger.info("alipay response: " + alipayRespone);
			Document doc = DocumentHelper.parseText(alipayRespone);
			Element elemen = doc.getRootElement();

			String success = elemen.elementTextTrim("is_success");
			if ("T".equalsIgnoreCase(success)) {
				response.setRetCode(PaygateError.SUCCESS.code());
				response.setRetMsg(PaygateError.SUCCESS.desc());
			} else {
				response.setRetCode(PaygateError.E224_REFUND_REQ_FAIL.code());
				response.setRetMsg(PaygateError.E224_REFUND_REQ_FAIL.desc() + "[" + elemen.elementTextTrim("error") + "]");
			}
		} catch (Throwable thr) {
			logger.error("", thr);
			response.setRetCode(PaygateError.E224_REFUND_REQ_FAIL.code());
			response.setRetMsg(PaygateError.E224_REFUND_REQ_FAIL.desc());
		}
		return response;
	}

	public RefundQueryResponse refundQuery(RefundQueryRequest refundQueryRequest) throws TException {
		String refundNum = refundQueryRequest.getRefundNum();
		logger.info("查询退款状态:{}", refundNum);
		String partner = getPartner(refundQueryRequest.getPartner());
		String signKey = alipayEnv.getPartnerArgs(partner, Args.SIGN_KEY);
		RefundQueryResponse response = new RefundQueryResponse();
		response.setRefundNum(refundNum);
		Map<String, String> paramsEncode = new HashMap<String, String>();
		// 支付宝接口地址
		String httpURL = alipayEnv.getArgs(Args.URL_REFUND_QUERY) + "?_input_charset=" + Args.CHARSET_UTF8;

		try {
			paramsEncode.put("_input_charset", inputCharset);
			// 合作伙伴
			paramsEncode.put("partner", partner);
			// 接口名称
			paramsEncode.put("service", Args.TT_REFUND_QUERY);
			// 退单标识号
			paramsEncode.put("batch_no", refundNum);

			// 签名
			String alipayResponse = AlipayCore.requestAlipay(httpURL, signKey, paramsEncode);
			logger.info("查询返回结果:{}", alipayResponse);
			Map<String, String> responseData = new HashMap<String, String>();
			response.setExtraInfos(responseData);
			String[] newxml = alipayResponse.split("&");
			for (int i = 0; i < newxml.length; i++) {
				String[] name = newxml[i].split("=");
				if (name.length == 2) {
					responseData.put(name[0], name[1]);
				}
			}

			if ("T".equals(responseData.get("is_success"))) {// 查询成功
				logger.info("查询结果正确返回..");
				response.setRetCode(PaygateError.SUCCESS.code());
				response.setRetMsg(PaygateError.SUCCESS.desc());
			} else {
				logger.info("查询结果返回错误信息:{}", responseData.get("error_code"));
				response.setRetCode(PaygateError.E310_QUERY_FAIL.code());
				response.setRetMsg(PaygateError.E310_QUERY_FAIL.desc() + ":" + responseData.get("error_code"));
			}
		} catch (Throwable thr) {
			logger.error("", thr);
			response.setRetCode(PaygateError.E310_QUERY_FAIL.code());
			response.setRetMsg(PaygateError.E310_QUERY_FAIL.desc());
		}
		return response;
	}

	/**
	 * 退款验签，验签结果中的金额指的是原始申请的金额，并不是退款成功的金额
	 */
	public RefundValSignResponse refundValSign(RefundValSignRequest valSignRequest) throws TException {
		Map<String, String> refundResponse = valSignRequest.getRefundResponse();
		logger.info("支付宝退款验签:{}", TZBeanUtils.describe(refundResponse));
		String partner = getPartner(valSignRequest.getPartner());
		RefundValSignResponse response = new RefundValSignResponse();
		try {
			// 过滤空值、sign与sign_type参数
			Map<String, String> sParaNew = AlipayCore.paraFilter(refundResponse);
			// 获取待签名字符串
			String preSignStr = AlipayCore.createLinkString(sParaNew);
			// 进行签名验证
			String sign = "";
			if (refundResponse.get("sign") != null) {
				sign = refundResponse.get("sign");
			}
			// 远程数据校验
			String notifyId = refundResponse.get("notify_id");
			// 进行签名验证
			boolean verify = verifyResponseData(preSignStr,partner, sign, refundResponse.get("sign_type"), notifyId);
			logger.info("response data ::: {}", preSignStr);
			logger.info("verify result ::: {}", verify);
			if (verify) {
				response.setRetCode(PaygateError.SUCCESS.code());
				response.setRetMsg(PaygateError.SUCCESS.desc());
				response.setRefundNum(StringUtils.trim(refundResponse.get("batch_no")));
				String resultDetail = refundResponse.get("result_details");
				List<RefundResultModel> list = AlipayParser.parserRefundResultDetails(resultDetail);
				long amount = 0;
				for (RefundResultModel model : list) {
					amount += model.getAmount();
				}
				response.setRefundAmount(amount);
			} else {
				response.setRetCode(PaygateError.E302_VAL_SIGN_FAIL.code());
				response.setRetMsg(PaygateError.E302_VAL_SIGN_FAIL.desc());
			}
		} catch (Throwable thr) {
			logger.error("", thr);
			response.setRetCode(PaygateError.E302_VAL_SIGN_FAIL.code());
			response.setRetMsg(PaygateError.E302_VAL_SIGN_FAIL.desc());
		}
		return response;
	}

}
