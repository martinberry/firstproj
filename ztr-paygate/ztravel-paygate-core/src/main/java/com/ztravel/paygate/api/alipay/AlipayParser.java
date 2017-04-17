package com.ztravel.paygate.api.alipay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.travelzen.framework.core.util.MoneyUtil;
import com.ztravel.paygate.api.alipay.model.CommonResponse;
import com.ztravel.paygate.api.alipay.model.QueryResultModel;
import com.ztravel.paygate.api.alipay.model.RefundQueryResultModel;
import com.ztravel.paygate.api.alipay.model.RefundResultModel;
import com.ztravel.paygate.api.alipay.model.RefundUnfreezedModel;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;

public class AlipayParser {
	private static final Logger logger = LoggerFactory.getLogger(AlipayParser.class);

	/**
	 * 解析退款结果信息(result_details),格式为:原付款支付宝交易号^退款金额^处理结果码$被收费人Email（ 也就是在交易的时候支付宝收取服务费的账户）^被收费人userId^退款金额^处理结果码
	 * 
	 * @param details
	 * @return
	 * @throws PaygateException
	 */
	public static List<RefundResultModel> parserRefundResultDetails(String details) throws PaygateException {
		logger.info("解析退单结果:{}", details);
		List<RefundResultModel> list = new ArrayList<>();
		if (StringUtils.isBlank(details)) {
			return list;
		}
		for (String detail : details.split("#")) {
			int i = detail.indexOf("|");
			String refundProfitDetails = null;
			if(i > 0){
				refundProfitDetails = detail.substring(i+1);
				detail = detail.substring(0, i);
			}
			String[] strArr = detail.split("\\$");
			String[] strArr1 = StringUtils.splitPreserveAllTokens(strArr[0], "\\^");//strArr[0].split("\\^");
			if (strArr1.length < 3) {
				throw new PaygateException(PaygateError.E312_INVALID_REFUND_RESULT_DETAILS);
			}
			String[] strArr2 = null;
			if (strArr.length > 1) {
				strArr2 = StringUtils.splitPreserveAllTokens(strArr[1], "\\^");//strArr[1].split("\\^");
				if (strArr2.length < 4) {
					throw new PaygateException(PaygateError.E312_INVALID_REFUND_RESULT_DETAILS);
				}
//				throw new PaygateException(PaygateError.E312_INVALID_REFUND_RESULT_DETAILS);
			}
//			String[] strArr2 = StringUtils.splitPreserveAllTokens(strArr[1], "\\^");//strArr[1].split("\\^");
			RefundResultModel model = new RefundResultModel();
			model.setTraceNum(strArr1[0]);
			model.setAmount(Long.parseLong(MoneyUtil.yuan2Cent(strArr1[1])));
			model.setTransRetMsg(strArr1[2]);
			model.setSellerEmail(strArr2 == null ? null : strArr2[0]);
			model.setSellerId(strArr2 == null ? null : strArr2[1]);
			model.setRefundAmount(strArr2 == null ? 0L : Long.parseLong(MoneyUtil.yuan2Cent(strArr2[2])));
			model.setRefundRetMsg(strArr2 == null ? null : strArr2[3]);
			list.add(model);
		}
		return list;
	}


	/**
	 * 解析退款结果信息(result_details),格式为:原付款支付宝交易号^退款金额^处理结果码$被收费人Email（ 也就是在交易的时候支付宝收取服务费的账户）^被收费人userId^退款金额^处理结果码
	 * 
	 * @param details
	 * @return
	 * @throws PaygateException
	 */
	public static List<RefundQueryResultModel> parseRefundQueryResultDetails(String details) throws PaygateException {
		logger.info("解析退单查询结果:{}", details);
		List<RefundQueryResultModel> list = new ArrayList<>();
		if (StringUtils.isBlank(details)) {
			return list;
		}
		for (String detail : details.split("#")) {
			int strIndex = detail.indexOf("^");
			if (strIndex < 1) {
				throw new PaygateException(PaygateError.E312_INVALID_REFUND_RESULT_DETAILS);
			}
			RefundQueryResultModel model = new RefundQueryResultModel();
			model.setRefundNum(detail.substring(0, strIndex));
			List<RefundResultModel> listRefund = parserRefundResultDetails(details.substring(strIndex + 1));
			if (listRefund.size() != 1) {
				throw new PaygateException(PaygateError.E312_INVALID_REFUND_RESULT_DETAILS);
			}
			BeanUtils.copyProperties(listRefund.get(0), model);
			list.add(model);
		}
		return list;
	}

	/**
	 * 解析查询结果
	 * 
	 * @param queryResult
	 * @return
	 * @throws PaygateException
	 */
	public static QueryResultModel parseQueryResult(String queryResult) throws PaygateException {
		QueryResultModel model = new QueryResultModel();
		try {
			Document doc = DocumentHelper.parseText(queryResult);
			Element element = doc.getRootElement();
			// 查询结果数据
			model.is_success = element.elementTextTrim("is_success");
			model.error = element.elementTextTrim("error");
			model.sign = element.elementTextTrim("sign");
			model.sign_type = element.elementTextTrim("sign_type");
			Element requestNode = element.element("request");
			if (requestNode != null) {
				for (Element ele : (List<Element>) requestNode.elements()) {
					model.addRequestData(ele.attributeValue("name"), ele.getTextTrim());
				}
			}
			Element responseNode = element.element("response");
			Element tradeNode;
			if (responseNode != null && (tradeNode = responseNode.element("trade")) != null) {
				for (Element ele : (List<Element>) tradeNode.elements()) {
					model.addTradeData(ele.getName(), ele.getTextTrim());
				}
			}
		} catch (Exception e) {
			//
			logger.error("解析结果失败:{}", queryResult);
			logger.error("解析返回结果失败.", e);
			throw new PaygateException(PaygateError.E100_ERROR);
		}
		return model;
	}

	public static CommonResponse parseCommonResponse(String responseText) {

		CommonResponse model = new CommonResponse();
		try {
			Document doc = DocumentHelper.parseText(responseText);
			Element rootElement = doc.getRootElement();
			model.setSuccess("T".equals(rootElement.elementTextTrim("is_success")));
			model.setErrMsg(rootElement.elementTextTrim("error"));
		} catch (Exception e) {
			logger.error("解析结果失败:{}", responseText);
			logger.error("解析返回结果失败.", e);
			throw new PaygateException(PaygateError.E100_ERROR);
		}
		return model;
	}
	
	/**
     * 退款解冻明细信息解析
     * 解冻结订单号^冻结订单号^解冻结金额^交易号^处理时间^状 态^描述码
     * @param freezedDetails
     * @return
     */
    public static RefundUnfreezedModel parseRefundUnfreezedModel(String freezedDetails){
        if(StringUtils.isBlank(freezedDetails)){
            throw new PaygateException(PaygateError.E100_ERROR);
        }
        String[] arr = StringUtils.splitPreserveAllTokens(freezedDetails, "\\^");//freezedDetails.split("\\^");
        if(arr.length!=7){
            logger.error("解冻结果信息有误:{}", freezedDetails);
            throw new PaygateException(PaygateError.E100_ERROR);
        }
        RefundUnfreezedModel model = new RefundUnfreezedModel();
        model.setUnfreezeNum(arr[0]);
        model.setFreezeNum(arr[1]);
        model.setUnfreezeAmount(yuan2Cent(arr[2]));
        model.setTraceNum(arr[3]);
        model.setTransTime(arr[4]);
        model.setState(arr[5]);
        model.setMsg(arr[6]);
        return model;
    }

	private static long yuan2Cent(String amount) {
		if (StringUtils.isBlank(amount)) {
			return 0;
		}
		return MoneyUtil.yuan2Cent(new BigDecimal(amount));
	}

	private static double parseDouble(String amount) {
		if (StringUtils.isBlank(amount)) {
			return 0;
		}
		return new BigDecimal(amount).doubleValue();
	}

}
