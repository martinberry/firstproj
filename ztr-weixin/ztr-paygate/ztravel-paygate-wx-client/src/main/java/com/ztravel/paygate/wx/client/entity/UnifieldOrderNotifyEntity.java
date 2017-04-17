package com.ztravel.paygate.wx.client.entity;


/**
 * 统一下单支付结果通知
 * @author liuzhuo
 *
 */
public class UnifieldOrderNotifyEntity {
	
	/**
	 * 返回状态码
	 * 通信标示 非交易标示
	 */
	String return_code;
	
	/**
	 * 返回信息
	 */
	String return_msg;
	
	/**
	 * 公众账号id
	 */
	String appid;
	
	/**
	 * 商户号
	 */
	String mch_id;
	
	/**
	 * 随机字符串
	 */
	String nonce_str;
	
	/**
	 * 签名
	 */
	String sign;
	
	/**
	 * 业务结果(SUCCESS/FAIL)
	 */
	String result_code;
	
    /**
     * 错误代码
     */
	String err_code;
	
	/**
	 * 错误代码描述
	 */
	String err_code_des;
	
	/**
	 * 用户表示
	 */
	String openid;
	
	/**
	 * 交易类型
	 */
	String trade_type;
	
	/**
	 * 付款银行
	 */
	String bank_type;
	
	/**
	 * 总金额（单位分）
	 */
	Integer total_fee;
	
	/**
	 * 微信支付订单号
	 */
	String transaction_id;
	
	/**
	 * 商户订单号
	 */
	String out_trade_no;
	
	/**
	 * 支付完成时间
	 */
	String time_end;
	
	/**
	 * 支付完成时间
	 */
	String cash_fee;
	
	/**
	 * 支付完成时间
	 */
	String fee_type;
	
	/**
	 * 支付完成时间
	 */
	String is_subscribe;
	
	/**
	 * 处理完成标记
	 */
	String confirm_result;
	
	public String getConfirm_result() {
		return confirm_result;
	}

	public void setConfirm_result(String confirm_result) {
		this.confirm_result = confirm_result;
	}
	
	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	
	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	
	public String getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
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

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	@Override
	public String toString() {
		return "UnifieldOrderNotifyEntity [return_code=" + return_code
				+ ", return_msg=" + return_msg + ", openid=" + openid
				+ ", trade_type=" + trade_type + ", total_fee=" + total_fee
				+ ", transaction_id=" + transaction_id + ", out_trade_no="
				+ out_trade_no + ", confirm_result=" + confirm_result + "]";
	}

	
}
