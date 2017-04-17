package com.ztravel.product.client.entity.vo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;


public class RecievedCouponVo {
	private String voucherCode;
	private String voucherOrderId;
	private String mid;
	private PaymentType payType ;
	private Long payAmount ;
	private DateTime payTime;
	private VoucherStatus status ;
	private String orderNo;
	private String orderMid;
	private VoucherType voucherType ;

	//private DateTime exchangeTime;

	private String payTimeStr ;
	//private String exchangeTimeStr ;
	private String mobile;
    private DateTime orderCreateTime;


	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public String getVoucherOrderId() {
		return voucherOrderId;
	}
	public void setVoucherOrderId(String voucherOrderId) {
		this.voucherOrderId = voucherOrderId;
	}
	public PaymentType getPayType() {
		return payType;
	}
	public void setPayType(PaymentType payType) {
		this.payType = payType;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public VoucherStatus getStatus() {
		return status;
	}
	public void setStatus(VoucherStatus status) {
		this.status = status;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getOrderMid() {
		return orderMid;
	}
	public void setOrderMid(String orderMid) {
		this.orderMid = orderMid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public DateTime getPayTime() {
		return payTime;
	}
	public void setPayTime(DateTime payTime) {
		this.payTime = payTime;
	}
/*	public DateTime getExchangeTime() {
		return exchangeTime;
	}
	public void setExchangeTime(DateTime exchangeTime) {
		this.exchangeTime = exchangeTime;
	}*/
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPayTimeStr() {
		return payTimeStr;
	}
	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}
/*	public String getExchangeTimeStr() {
		return exchangeTimeStr;
	}
	public void setExchangeTimeStr(String exchangeTimeStr) {
		this.exchangeTimeStr = exchangeTimeStr;
	}*/
	public DateTime getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(DateTime orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

}
