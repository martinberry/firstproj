package com.ztravel.paygate.wx.client.entity;

import org.joda.time.DateTime;

/**
 * 微信查询退款请求返回结果
 * @author tian1.xu
 *
 */
public class RefundQueryResponseEntity {

    /**
     * 返回状态码
     */
    private String return_code;

    /**
     * 返回信息
     */
    private String return_msg;

    /**
     * 业务结果
     */
    private String result_code;

    /**
     * 错误代码
     */
    private String err_code;

    /**
     * 错误代码描述
     */
    private String err_code_des;

	/**
	 * 公众账号ID
	 */
	private String appid;

    /**
     * 商户号
     */
    private String mch_id;

    /**
     * 设备号
     */
    private String device_info;

    /**
     * 随机字符串
     */
    private String nonce_str;

    /**
     * 签名
     */
    private String sign;

    /**
     * 微信订单号
     */
    private String transaction_id;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 订单总金额
     */
    private int total_fee;

    /**
     * 订单金额货币种类
     */
    private String fee_type;

	/**
	 * 现金支付金额
	 */
	private int cash_fee;

    /**
     * 退款笔数
     */
    private int refund_count;

	/**
	 * 商户退款单号
	 */
	private String out_refund_no;

    /**
     * 微信退款单号
     */
    private String refund_id;

	/**
	 * 退款渠道
	 */
	private String refund_channel;

	/**
	 * 退款金额
	 */
	private int refund_fee;

	/**
	 * 代金券或立减优惠退款金额
	 */
	private int coupon_refund_fee;

	/**
	 * 代金券或立减优惠使用数量
	 */
    private int coupon_refund_count;

    /**
     * 代金券或立减优惠批次ID
     */
    private String coupon_refund_batch_id;

    /**代金券或立减优惠ID
     *
     */
    private String coupon_refund_id;

    /**
     * 退款状态
     */
    private String refund_status;

    /**
     * 记录状态
     */
    private String record_status;

    /**
     * 初次记录时间
     */
    private DateTime create_time;

    /**
     * 最后修改时间
     */
    private DateTime last_modify_time;

    public DateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(DateTime create_time) {
        this.create_time = create_time;
    }

    public DateTime getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(DateTime last_modify_time) {
        this.last_modify_time = last_modify_time;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

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

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public int getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(int cash_fee) {
        this.cash_fee = cash_fee;
    }

    public int getRefund_count() {
        return refund_count;
    }

    public void setRefund_count(int refund_count) {
        this.refund_count = refund_count;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getRefund_channel() {
        return refund_channel;
    }

    public void setRefund_channel(String refund_channel) {
        this.refund_channel = refund_channel;
    }

    public int getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(int refund_fee) {
        this.refund_fee = refund_fee;
    }

    public int getCoupon_refund_fee() {
        return coupon_refund_fee;
    }

    public void setCoupon_refund_fee(int coupon_refund_fee) {
        this.coupon_refund_fee = coupon_refund_fee;
    }

    public int getCoupon_refund_count() {
        return coupon_refund_count;
    }

    public void setCoupon_refund_count(int coupon_refund_count) {
        this.coupon_refund_count = coupon_refund_count;
    }

    public String getCoupon_refund_batch_id() {
        return coupon_refund_batch_id;
    }

    public void setCoupon_refund_batch_id(String coupon_refund_batch_id) {
        this.coupon_refund_batch_id = coupon_refund_batch_id;
    }

    public String getCoupon_refund_id() {
        return coupon_refund_id;
    }

    public void setCoupon_refund_id(String coupon_refund_id) {
        this.coupon_refund_id = coupon_refund_id;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getRecord_status() {
        return record_status;
    }

    public void setRecord_status(String record_status) {
        this.record_status = record_status;
    }

	@Override
	public String toString() {
		return "RefundQueryResponseEntity [transaction_id=" + transaction_id
				+ ", out_trade_no=" + out_trade_no + ", out_refund_no="
				+ out_refund_no + ", refund_status=" + refund_status
				+ ", record_status=" + record_status + ", create_time="
				+ create_time + ", last_modify_time=" + last_modify_time + "]";
	}
    
    

}
