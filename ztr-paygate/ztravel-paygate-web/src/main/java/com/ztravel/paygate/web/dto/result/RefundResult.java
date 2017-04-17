package com.ztravel.paygate.web.dto.result;

import com.ztravel.paygate.core.enums.RefundState;

/**
 * 退款结果
 * 
 * @author dingguangxian
 * 
 */
public class RefundResult extends AbstractResultBean {
	private static final long serialVersionUID = -7696590115234646024L;
	private String clientId;
	private String retCode;
	private String retMsg;
	private String gateType;
	// 标识当前退款业务
	private String refundNum;
	private String orderNum;
	private String traceNum;
	private long refundAmount;
	private RefundState refundState;

	private String refundShareDetails;
	private String unfreezeDetails;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getGateType() {
		return gateType;
	}

	public void setGateType(String gateType) {
		this.gateType = gateType;
	}

	public String getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getTraceNum() {
		return traceNum;
	}

	public void setTraceNum(String traceNum) {
		this.traceNum = traceNum;
	}

	public long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public RefundState getRefundState() {
		return refundState;
	}

	public void setRefundState(RefundState refundState) {
		this.refundState = refundState;
	}

	public String getRefundShareDetails() {
		return refundShareDetails;
	}

	public void setRefundShareDetails(String refundShareDetails) {
		this.refundShareDetails = refundShareDetails;
	}

	public String getUnfreezeDetails() {
		return unfreezeDetails;
	}

	public void setUnfreezeDetails(String unfreezeDetails) {
		this.unfreezeDetails = unfreezeDetails;
	}

}
