package com.ztravel.paygate.web.modelbuilder;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.ztravel.paygate.core.po.gen.Alipay;
import com.ztravel.paygate.core.po.gen.AlipayRefund;
import com.ztravel.paygate.thrift.model.PaySignResponse;
import com.ztravel.paygate.thrift.model.QueryResponse;
import com.ztravel.paygate.thrift.model.RefundResponse;
import com.ztravel.paygate.thrift.model.ValSignResponse;
import com.ztravel.paygate.web.dto.request.RequestPayBean;
import com.ztravel.paygate.web.dto.request.RequestRefundBean;
import com.ztravel.paygate.web.dto.response.BankPayResponse;

/**
 * 支付宝
 * 
 * @author dingguangxian
 * 
 */
public class AlipayModelBuilder{
	private static Logger logger = LoggerFactory.getLogger(AlipayModelBuilder.class);
	/**
	 * 支付请求
	 * 
	 * @param alipayId
	 * @param payNotifyResponse
	 * @return
	 */
	public static Alipay buildFromPayRequest(String alipayId, RequestPayBean payRequest) {
		Alipay entity = new Alipay();
		entity.setAlipayId(alipayId);
		entity.setClientId(payRequest.getClientId());
		entity.setOrderNum(payRequest.getOrderNum());
		entity.setPayType(payRequest.getPayType());
		entity.setTransComment(payRequest.getComment());
		entity.setTransAmount(payRequest.getAmount());
		entity.setFgNotifyUrl(payRequest.getFgNotifyUrl());
		entity.setTransDate(DateTimeUtil.date8());
		entity.setTransTime(DateTimeUtil.time6());
		entity.setCreateTime(new Date());
		entity.setPartner(payRequest.getPartner());
		return entity;
	}

	/**
	 * 支付签名请求结果
	 * 
	 * @param alipayId
	 * @param payNotifyResponse
	 * @return
	 */
	public static Alipay buildFromPaySignResponse(String alipayId, PaySignResponse response) {
		Alipay record = new Alipay();
		record.setAlipayId(alipayId);
		record.setSignRetCode(response.getRetCode());
		record.setSignRetMsg(response.getRetMsg());
		Map<String, String> infos = response.getExtraInfos();
		if (infos != null) {
			record.setPartner(infos.get("partner"));
			record.setSellerEmail(infos.get("seller_email"));
			// record.setSellerId(infos.get("seller_id"));
		}
		return record;
	}

	/**
	 * 支付签名请求结果
	 * 
	 * @param alipayId
	 * @param payNotifyResponse
	 * @return
	 */
	public static Alipay buildFromValSignResponse(String alipayId, ValSignResponse response) {
		Alipay record = new Alipay();
		record.setAlipayId(alipayId);
		record.setValsignRetCode(response.getRetCode());
		record.setValsignRetMsg(response.getRetMsg());
		return record;
	}

	/**
	 * 支付结果通知信息
	 * 
	 * @param alipayId
	 * @param payNotifyResponse
	 * @return
	 */
	public static Alipay buildFromPayNotifyResponse(String alipayId, BankPayResponse payNotifyResponse) {
		Map<String, String> responseData = payNotifyResponse.getFormData();
		Alipay record = new Alipay();
		record.setAlipayId(alipayId);
		record.setTraceNum(responseData.get("trade_no"));
		record.setBuyerEmail(responseData.get("buyer_email"));
		record.setBuyerId(responseData.get("buyer_id"));
		record.setTradeStatus(responseData.get("trade_status"));
		record.setGmtCreate(responseData.get("gmt_create"));
		record.setGmtPayment(responseData.get("gmt_payment"));
		record.setNotifyTime(responseData.get("notify_time"));
		record.setRespTime(new Date());
		JsonObject extraInfos = new JsonObject();
		String[] infos = new String[] { "notify_type", "price", "quantity", "refund_status", "seller_email", "seller_id",
				"is_total_fee_adjust", "gmt_close", "gmt_refund", "use_coupon" };
		for (String info : infos) {
			String val = responseData.get(info);
			if (StringUtils.isNotBlank(val)) {
				extraInfos.addProperty(info, val);
			}
		}
		record.setExtraInfos(extraInfos.toString());
		return record;
	}

	/**
	 * 查询返回结果
	 * 
	 * @param alipayId
	 * @param payNotifyResponse
	 * @return
	 */
	public static Alipay buildFromQueryResponse(String alipayId, QueryResponse response) {
		Map<String, String> responseData = response.getExtraInfos();
		Alipay record = new Alipay();
		record.setAlipayId(alipayId);
		record.setValsignRetCode(response.getRetCode());
		record.setValsignRetMsg(response.getRetMsg());
		record.setTraceNum(response.getTraceNum());
		record.setBuyerEmail(responseData.get("buyer_email"));
		record.setBuyerId(responseData.get("buyer_id"));
		record.setTradeStatus(responseData.get("trade_status"));
		record.setGmtCreate(responseData.get("gmt_create"));
		record.setGmtPayment(responseData.get("gmt_payment"));
		record.setNotifyTime(responseData.get("notify_time"));
		record.setPayState(response.getPayState());
		record.setRespTime(new Date());
		JsonObject extraInfos = new JsonObject();
		String[] infos = new String[] { "notify_type", "price", "quantity", "refund_status", "seller_email", "seller_id",
				"is_total_fee_adjust", "gmt_close", "gmt_refund", "use_coupon" };
		for (String info : infos) {
			String val = responseData.get(info);
			if (StringUtils.isNotBlank(val)) {
				extraInfos.addProperty(info, val);
			}
		}
		record.setExtraInfos(extraInfos.toString());
		return record;
	}

	/**
	 * 退单请求
	 * 
	 * @param entityId
	 * @param alipayId
	 * @param refundRequest
	 * @return
	 */
	public static AlipayRefund buildRefundModelFromRefundRequest(String entityId, String alipayId, RequestRefundBean refundRequest) {
		AlipayRefund entity = new AlipayRefund();
		entity.setAlipayRefundId(entityId);
		entity.setAlipayId(alipayId);
		entity.setClientId(refundRequest.getClientId());
		entity.setRefundNum(refundRequest.getRefundNum());
		entity.setOrderNum(refundRequest.getOrderNum());
		entity.setTraceNum(refundRequest.getTraceNum());
		entity.setTransAmount(refundRequest.getTransAmount());
		entity.setRefundAmount(refundRequest.getRefundAmount());
		entity.setRefundComment(refundRequest.getComment());
		entity.setRefundProfitDetails(refundRequest.getRefundProfitDetails());
		return entity;
	}

	/**
	 * 退单请求返回结果
	 * 
	 * @param entityId
	 * @param response
	 * @return
	 */
	public static AlipayRefund buildRefundModelFromRefundResponse(String entityId, RefundResponse response) {
		AlipayRefund record = new AlipayRefund();
		record.setAlipayRefundId(entityId);
		record.setReqRetCode(response.getRetCode());
		record.setReqRetMsg(response.getRetMsg());
		Map<String, String> infos = response.getExtraInfos();
		if (infos != null) {
			record.setPartner(infos.get("partner"));
			record.setSellerEmail(infos.get("seller_email"));
			// record.setSellerId(infos.get("seller_id"));
			record.setRefundDate(infos.get("refund_date"));
			record.setDetailData(infos.get("detail_data"));
		}
		return record;
	}

	/**
	 * 退单请求返回结果
	 * 
	 * @param entityId
	 * @param response
	 * @return
	 */
	public static AlipayRefund buildFromRefundQueryResponse(String entityId, RefundResponse response) {
		AlipayRefund record = new AlipayRefund();
		record.setAlipayRefundId(entityId);
		record.setReqRetCode(response.getRetCode());
		record.setReqRetMsg(response.getRetMsg());
		Map<String, String> infos = response.getExtraInfos();
		if (infos != null) {
			record.setPartner(infos.get("partner"));
			record.setSellerEmail(infos.get("seller_email"));
			// record.setSellerId(infos.get("seller_id"));
			record.setRefundDate(infos.get("refund_date"));
			record.setDetailData(infos.get("detail_data"));
		}
		return record;
	}
}
