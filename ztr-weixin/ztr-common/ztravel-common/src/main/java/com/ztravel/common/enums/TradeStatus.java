package com.ztravel.common.enums;

public enum TradeStatus {
	SUCCESS("成功"), FAIL("失败"), REQUEST("请求"), UNKNOWN("未知");

	private String description;

	private TradeStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
