package com.ztravel.paygate.web.dto.middlebean;

import com.ztravel.paygate.web.dto.PaygateErrorSupport;

/**
 * 退款请求结果
 * 
 * @author dingguangxian
 * 
 */
public class RefundConfirmBean implements PaygateErrorSupport {
	private String entityId;// 原支付订单entity ID
	private String refundEntityId;// 退单entity ID
	private String partner;// 原支付订单的partner
	private String retCode;
	private String retMsg;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getRefundEntityId() {
		return refundEntityId;
	}

	public void setRefundEntityId(String refundEntityId) {
		this.refundEntityId = refundEntityId;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
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

}
