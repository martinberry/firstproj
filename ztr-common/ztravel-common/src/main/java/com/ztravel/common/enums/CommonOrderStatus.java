package com.ztravel.common.enums;

public enum CommonOrderStatus {
	
	INIT("初始化"),AUDIT_UNPASS("审核未通过"),UNPAY("待支付"),PAID("已支付"),REFUNDING("待退款"),REFUNDED("已退款"),REFUNDFAIL("退款失败"),CANCELED("已取消");
	//REFUND_PASS("已通过"), REFUND_UNPASS("未通过"), REFUND_AUDIT("待审核"),REFUND_CANCELED("已取消"),UNREPAIR("待补款"),REPAIRED("已补款");
	private final String description;

	CommonOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}




