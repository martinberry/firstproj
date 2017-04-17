package com.ztravel.order.back.vo;

import com.ztravel.common.entity.PayDetailInfo;

public class VisaOrderConfirmPDFEntity {
	//联系人姓名
    private String contactorName ;

    //订单号
	private String orderNo ;

	//产品名称
	private String productName ;

	//产品类型
	private String visaPriceType;

	//费用说明
	private String feeDetail;

	//退改政策
	private String refundPolicy ;

	//应付总价
	private PayDetailInfo payDetail ;

	public String getContactorName() {
		return contactorName;
	}

	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVisaPriceType() {
		return visaPriceType;
	}

	public void setVisaPriceType(String visaPriceType) {
		this.visaPriceType = visaPriceType;
	}

	public String getFeeDetail() {
		return feeDetail;
	}

	public void setFeeDetail(String feeDetail) {
		this.feeDetail = feeDetail;
	}

	public String getRefundPolicy() {
		return refundPolicy;
	}

	public void setRefundPolicy(String refundPolicy) {
		this.refundPolicy = refundPolicy;
	}

	public PayDetailInfo getPayDetail() {
		return payDetail;
	}

	public void setPayDetail(PayDetailInfo payDetail) {
		this.payDetail = payDetail;
	}





}
