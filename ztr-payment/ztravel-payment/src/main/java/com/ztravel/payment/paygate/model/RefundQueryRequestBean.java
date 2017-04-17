package com.ztravel.payment.paygate.model;
/**
 * @author zuoning.shen
 *
 */
public class RefundQueryRequestBean extends RequestBean {
	/**
	 * 退款标识号,required
	 */
	private String refundNum;

	public String getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}
}
