package com.ztravel.common.enums;

/**
 * 订单评价来源
 * @author liuzhuo
 *
 */
public enum OrderCommentSource {

	MEMCOMIT("memCommit","会员提交"),
	OPERAADD("operaAdd","后台添加");

	private final String value;

	private final String desc;

	OrderCommentSource(String value,String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}



