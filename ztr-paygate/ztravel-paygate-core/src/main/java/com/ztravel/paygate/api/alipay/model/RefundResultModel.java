package com.ztravel.paygate.api.alipay.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 退款返回结果信息,格式为：原付款支付宝交易号^退款金额^处理结果码$被收费人Email（也就是在交易的时候支付宝收取服务费的账户）^被收费人userId^退款金额^处理结果码
 * 
 * @author dingguangxian
 * 
 */
public class RefundResultModel {
	private String traceNum;
	/** 请求退款金额 **/
	private long amount;
	private String transRetMsg;

	private String sellerEmail;
	private String sellerId;
	/** 实际退款金额 **/
	private long refundAmount;
	private String refundRetMsg;
	
	private List<RefundProfitResultModel> refundProfits = new ArrayList<RefundProfitResultModel>();

	public String getTraceNum() {
		return traceNum;
	}

	public void setTraceNum(String traceNum) {
		this.traceNum = traceNum;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getTransRetMsg() {
		return transRetMsg;
	}

	public void setTransRetMsg(String transRetMsg) {
		this.transRetMsg = transRetMsg;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundRetMsg() {
		return refundRetMsg;
	}

	public void setRefundRetMsg(String refundRetMsg) {
		this.refundRetMsg = refundRetMsg;
	}

	public List<RefundProfitResultModel> getRefundProfits() {
		return refundProfits;
	}

	public void setRefundProfits(List<RefundProfitResultModel> refundProfits) {
		this.refundProfits = refundProfits;
	}
	
}
