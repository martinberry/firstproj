package com.ztravel.paygate.wx.client.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信订单申请退款请求
 * @author tian1.xu
 *
 */
@XStreamAlias("xml")
public class RefundOrderRequestEntity {

	/**
	 * 公众账号ID
	 */
	private String appid;

	/**
	 * 商户号
	 */
	private String mch_id;

	/**
	 * 随机字符串
	 */
	private String nonce_str;

	/**
	 * 操作员
	 */
	private String op_user_id;

	/**
	 * 商户退款单号
	 */
	private String out_refund_no;

	/**
	 * 商户订单号
	 */
	private String out_trade_no;

	/**
	 * 退款金额
	 */
	private Integer refund_fee;

	/**
	 * 总金额
	 */
	private Integer total_fee;

	/**
	 * 微信订单号
	 */
	private String transaction_id;

	/**
	 * 签名
	 */
	private String sign;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getOp_user_id() {
		return op_user_id;
	}

	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Integer getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "RefundOrderRequest [appid=" + appid + ", mch_id=" + mch_id
				+ ", nonce_str=" + nonce_str + ", op_user_id=" + op_user_id
				+ ", out_refund_no=" + out_refund_no + ", out_trade_no="
				+ out_trade_no + ", refund_fee=" + refund_fee + ", total_fee="
				+ total_fee + ", transaction_id=" + transaction_id + ", sign="
				+ sign + "]";
	}

}
