package com.ztravel.common.enums;

public enum OrderPayStatus {
    AWAIT("等待支付"), PAID("已支付"), CANCELLED("已取消"), PART_CANCELLED("部分取消");

	private String description;

	private OrderPayStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
