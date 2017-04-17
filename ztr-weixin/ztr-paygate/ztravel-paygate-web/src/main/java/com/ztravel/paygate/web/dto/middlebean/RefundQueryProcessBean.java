package com.ztravel.paygate.web.dto.middlebean;

import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.RefundState;

/**
 * 退款查询
 * 
 * @author dingguangxian
 * 
 */
public class RefundQueryProcessBean {
	private String refundId;// 退单业务表主键
	private String clientId;
	private String partner;
	private String reqRetCode;
	private String reqRetMsg;
	private GateType gateType;
	// 标识当前退款业务
	private String refundNum;
	private String orderNum;
	private String traceNum;
	private long transAmount;
	private long refundAmount;
	private RefundState refundState;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public long getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(long transAmount) {
		this.transAmount = transAmount;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getReqRetCode() {
		return reqRetCode;
	}

	public void setReqRetCode(String reqRetCode) {
		this.reqRetCode = reqRetCode;
	}

	public String getReqRetMsg() {
		return reqRetMsg;
	}

	public void setReqRetMsg(String reqRetMsg) {
		this.reqRetMsg = reqRetMsg;
	}

	public GateType getGateType() {
		return gateType;
	}

	public void setGateType(GateType gateType) {
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

}
