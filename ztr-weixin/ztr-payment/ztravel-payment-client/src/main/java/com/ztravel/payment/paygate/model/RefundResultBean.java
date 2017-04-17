package com.ztravel.payment.paygate.model;

/**
 * @author zuoning.shen
 *
 */
public class RefundResultBean extends ResponseBean {
	private static final long serialVersionUID = -7696590115234646024L;
	private String clientId;// 客户端ID
	private String gateType;// 支付平台,0001-支付宝,0002-财付通,0003-汇付
	private String refundNum;// 标识当前退款业务
	private String orderNum;// 原订单号
	private String traceNum;// 原交易号
	private long refundAmount;// 本次退款金额
	private String refundState;// 退款状态,SUCCESS-成功,FAIL-失败
	/** 退分润信息,格式:商户账户|分润账户|分润成功金额|状态 */
	private String refundShareDetails;
	/** 解冻信息,格式:解冻结订单号^冻结订单号^解冻结金额^交易号^处理时间^状 态^描述码 */
	private String unfreezeDetails;
	/**
	 * 退分润信息,格式:商户账户|分润账户|分润成功金额|状态
	 * @author dingguangxian
	 *
	 */
	public static class RefundShareDetail{
		public final String shareAccount;
		public final String partnerAccount;
		public final long amount;
		public final String status;
		public RefundShareDetail(String detail){
			String[] arr = detail.split("\\|");
			shareAccount = arr[0];
			partnerAccount = arr[1];
			amount = Long.valueOf(arr[2]);
			status = arr[3];
		}
	}
	/**
	 * 退款解冻信息,格式:解冻结订单号^冻结订单号^解冻结金额^交易号^处理时间^状 态^描述码
	 * @author dingguangxian
	 *
	 */
	public static class UnfreezeDetail {
		public final String orderNum;
		public final String freezeNum;
		public final long amount;
		public final String traceNum;
		public final String unfreezeTime;
		public final String status;
		public final String msg;
		public UnfreezeDetail(String detail){
			String[] arr = detail.split("\\|");
			orderNum = arr[0];
			freezeNum = arr[1];
			amount = Long.valueOf(arr[2]);
			traceNum = arr[3];
			unfreezeTime = arr[4];
			status = arr[5];
			msg = arr[6];
		}
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
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
