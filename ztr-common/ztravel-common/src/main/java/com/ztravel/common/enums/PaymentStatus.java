package com.ztravel.common.enums;

public enum PaymentStatus {
    AWAIT("等待支付"), PAID("已支付") ,REFUNDED("REFUNDED");

	private String description;

	private PaymentStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
