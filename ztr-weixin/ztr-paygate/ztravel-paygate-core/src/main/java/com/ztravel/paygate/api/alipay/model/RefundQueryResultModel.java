package com.ztravel.paygate.api.alipay.model;

/**
 * 退款返回结果信息,格式为：原付款支付宝交易号^退款金额^处理结果码$被收费人Email（也就是在交易的时候支付宝收取服务费的账户）^被收费人userId^退款金额^处理结果码
 * 
 * @author dingguangxian
 * 
 */
public class RefundQueryResultModel extends RefundResultModel {
	private String refundNum;

	public String getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}
}
