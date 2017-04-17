package com.ztravel.paygate.wx.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.util.WeixinSignUtil;
import com.ztravel.paygate.wx.client.entity.CloseOrderRequest;
import com.ztravel.paygate.wx.client.entity.QueryOrderRequest;
import com.ztravel.paygate.wx.client.entity.RefundOrderRequestEntity;
import com.ztravel.paygate.wx.client.entity.RefundQueryRequestEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderRequest;

public class WxPayParmConvert {

	public static final String NOTIFY_URL = TopsConfReader.getConfContent("properties/wx-pay.properties", "pay_notify_url", ConfScope.R) ;
	/**
	 * 构造修改订单状态的bean
	 * @param unifieldOrderNotifyEntity
	 * @return
	 */
	public static OrderPaidBean buildOrderPaidBean(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		OrderPaidBean orderPaidBean = new OrderPaidBean();

		orderPaidBean.setPaymentType(PaymentType.WeChatpay);
		orderPaidBean.setOrderId(unifieldOrderNotifyEntity.getOut_trade_no());
		orderPaidBean.setBankPaymentTime(unifieldOrderNotifyEntity.getTime_end());
		orderPaidBean.setTraceNum(unifieldOrderNotifyEntity.getTransaction_id());

		return orderPaidBean;
	}



	/**
	 * 获取xml类型参数
	 * @param wxPayEntity
	 * @return
	 */
	public static String buildXMlParamByEntity(Object entity,Class<?> clazz,String rootElementName) {

		XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		xstream.alias(rootElementName, clazz);
		String wxPayEntityXml = xstream.toXML(entity);

		return wxPayEntityXml;
	}

	/**
	 * 根据xml获取对象
	 * @param response
	 * @return
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEntityByXml(String response,Class<T> clazz ) {
		XStream xstream = new XStream();
		xstream.alias("xml", clazz);
		xstream.ignoreUnknownElements();
		return  (T) xstream.fromXML(response);

	}

	/**
	 * 构造微信统一下单接口参数
	 * @param oriParam
	 * @return
	 */
	public static UnifieldOrderRequest buildWxUnifiedlOrderParam(String ori) {
		UnifieldOrderRequest wxPayEntity = new UnifieldOrderRequest();
		JSONObject json = JSONObject.parseObject(ori) ;
		String appid = WechatAccountHolder.APP_ID;
		String mch_id = WechatAccountHolder.MCH_ID;
		String body = "微信支付";
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		String notify_url = NOTIFY_URL ;
		String out_trade_no = json.containsKey("orderId")?json.getString("orderId"):"";
		String spbill_create_ip = json.containsKey("requestIp")?json.getString("requestIp"):"";
		Integer total_fee = json.containsKey("orderAmount")?json.getInteger("orderAmount"):0;
		String trade_type = json.containsKey("tradeType")?json.getString("tradeType"):"NATIVE";
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("appid", appid) ;
		params.put("mch_id", mch_id) ;
		params.put("body", body) ;
		params.put("nonce_str", nonce_str) ;
		params.put("notify_url", notify_url) ;
		params.put("out_trade_no", out_trade_no) ;
		params.put("spbill_create_ip", spbill_create_ip) ;
		params.put("total_fee", total_fee.toString()) ;
		params.put("trade_type", trade_type) ;
		String open_id = "" ;
		if("JSAPI".equals(trade_type)){
			open_id = json.containsKey("openId")?json.getString("openId"):"";
			wxPayEntity.setOpenid(open_id);
			params.put("openid", open_id) ;
		}
		String sign = WeixinSignUtil.sign(params) ;
		wxPayEntity.setAppid(appid);
		wxPayEntity.setBody(body);
		wxPayEntity.setMch_id(mch_id);
		wxPayEntity.setNonce_str(nonce_str);
		wxPayEntity.setNotify_url(notify_url);
		wxPayEntity.setOut_trade_no(out_trade_no);
		wxPayEntity.setSign(sign);
		wxPayEntity.setSpbill_create_ip(spbill_create_ip);
		wxPayEntity.setTotal_fee(total_fee);
		wxPayEntity.setTrade_type(trade_type);

		return wxPayEntity;
	}

	/**
	 * 构造微信统一关闭定单接口参数
	 * @param oriParam
	 * @return
	 */
	public static CloseOrderRequest buildWxCloseOrderParam(String orderId) {
		CloseOrderRequest request = new CloseOrderRequest();
		String appid = WechatAccountHolder.APP_ID;
		String mch_id = WechatAccountHolder.MCH_ID;
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		String out_trade_no = orderId ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("appid", appid) ;
		params.put("mch_id", mch_id) ;
		params.put("nonce_str", nonce_str) ;
		params.put("out_trade_no", out_trade_no) ;
		String sign = WeixinSignUtil.sign(params) ;
		request.setAppid(appid);
		request.setMch_id(mch_id);
		request.setNonce_str(nonce_str);
		request.setOut_trade_no(out_trade_no);
		request.setSign(sign);
		return request;
	}

	/**
	 * 构造微信查询订单接口参数
	 * @param oriParam
	 * @return
	 */
	public static QueryOrderRequest buildWxOrderQueryParam(String orderId) {
		QueryOrderRequest request = new QueryOrderRequest();
		String appid = WechatAccountHolder.APP_ID;
		String mch_id = WechatAccountHolder.MCH_ID;
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		String out_trade_no = orderId ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("appid", appid) ;
		params.put("mch_id", mch_id) ;
		params.put("nonce_str", nonce_str) ;
		params.put("out_trade_no", out_trade_no) ;
		String sign = WeixinSignUtil.sign(params) ;
		request.setAppid(appid);
		request.setMch_id(mch_id);
		request.setNonce_str(nonce_str);
		request.setOut_trade_no(out_trade_no);
		request.setSign(sign);
		return request;
	}

	/**
	 *构造微信申请退款接口参数
	 * @param oriParam
	 * @return
	 */
	public static RefundOrderRequestEntity buildWxRefundOrderParam(String requestParams) {

		RefundOrderRequestEntity refundOrder = new RefundOrderRequestEntity();

		JSONObject json = JSONObject.parseObject(requestParams) ;
		String appid = WechatAccountHolder.APP_ID;
		String mch_id = WechatAccountHolder.MCH_ID;
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		String op_user_id = json.containsKey("memberId")?json.getString("memberId"):"";
		String out_trade_no = json.containsKey("orderId")?json.getString("orderId"):"";
		String transaction_id = json.containsKey("transactionId")?json.getString("transactionId"):"";
		String out_refund_no = json.containsKey("outRefundNo")?json.getString("outRefundNo"):"";
		Integer refund_fee = json.containsKey("refundFee")?json.getInteger("refundFee"):0;
		Integer total_fee = json.containsKey("orderAmount")?json.getInteger("orderAmount"):0;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("appid", appid) ;
		params.put("op_user_id", op_user_id) ;
		params.put("mch_id", mch_id) ;
		params.put("nonce_str", nonce_str) ;
		if(StringUtils.isNotEmpty(out_trade_no)){
			params.put("out_trade_no", out_trade_no) ;
		}
		if(StringUtils.isNotEmpty(out_refund_no)){
			params.put("out_refund_no", out_refund_no) ;//商户退款单号设置为与商户订单号一样
		}else{
			if(StringUtils.isNotEmpty(out_trade_no)){
				params.put("out_refund_no", out_trade_no) ;
			}
		}
		params.put("refund_fee", refund_fee.toString()) ;
		params.put("total_fee", total_fee.toString()) ;
		if(StringUtils.isNotEmpty(transaction_id)){
			params.put("transaction_id", transaction_id) ;
		}
		String sign = WeixinSignUtil.sign(params) ;

		refundOrder.setAppid(appid);
		refundOrder.setMch_id(mch_id);
		refundOrder.setNonce_str(nonce_str);
		refundOrder.setOp_user_id(op_user_id);
		if(StringUtils.isNotEmpty(out_trade_no)){
			refundOrder.setOut_trade_no(out_trade_no);
		}
		if(StringUtils.isNotEmpty(out_refund_no)){
			refundOrder.setOut_refund_no(out_refund_no);
		}else{
			if(StringUtils.isNotEmpty(out_trade_no)){
				refundOrder.setOut_refund_no(out_trade_no);
			}
		}
		refundOrder.setRefund_fee(refund_fee);
		refundOrder.setTotal_fee(total_fee);
		if(StringUtils.isNotEmpty(transaction_id)){
			refundOrder.setTransaction_id(transaction_id);
		}
		refundOrder.setSign(sign);

		return refundOrder;
	}

	/**
	 *构造微信查询退款接口参数
	 * @param oriParam
	 * @return
	 */
	public static RefundQueryRequestEntity buildWxRefundQueryParam(String refundId) {

		RefundQueryRequestEntity refundQuery = new RefundQueryRequestEntity();

		String appid = WechatAccountHolder.APP_ID;
		String mch_id = WechatAccountHolder.MCH_ID;
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("appid", appid) ;
		params.put("mch_id", mch_id) ;
		params.put("nonce_str", nonce_str) ;
		params.put("refund_id", refundId) ;
		String sign = WeixinSignUtil.sign(params) ;

		refundQuery.setAppid(appid);
		refundQuery.setMch_id(mch_id);
		refundQuery.setNonce_str(nonce_str);
		refundQuery.setRefund_id(refundId);
		refundQuery.setSign(sign);

		return refundQuery;
	}

}
