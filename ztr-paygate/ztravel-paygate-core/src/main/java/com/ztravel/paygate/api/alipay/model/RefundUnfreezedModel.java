package com.ztravel.paygate.api.alipay.model;

/**
 * 退款解冻结果:解冻结订单号^冻结订单号^解冻结金额^交易号^处理时间^状 态^描述码
 * 
 * @author dingguangxian
 * 
 */
public class RefundUnfreezedModel {
	private String unfreezeNum;
	private String freezeNum;
	private long unfreezeAmount;
	private String traceNum;
	private String transTime;
	private String state;
	private String msg;

	public String getUnfreezeNum() {
		return unfreezeNum;
	}

	public void setUnfreezeNum(String unfreezeNum) {
		this.unfreezeNum = unfreezeNum;
	}

	public String getFreezeNum() {
		return freezeNum;
	}

	public void setFreezeNum(String freezeNum) {
		this.freezeNum = freezeNum;
	}

	public long getUnfreezeAmount() {
		return unfreezeAmount;
	}

	public void setUnfreezeAmount(long unfreezeAmount) {
		this.unfreezeAmount = unfreezeAmount;
	}

	public String getTraceNum() {
		return traceNum;
	}

	public void setTraceNum(String traceNum) {
		this.traceNum = traceNum;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
