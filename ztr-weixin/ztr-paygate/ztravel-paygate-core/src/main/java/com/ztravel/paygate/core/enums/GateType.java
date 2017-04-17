package com.ztravel.paygate.core.enums;

/**
 * 支付平台类型
 *
 * @author dingguangxian
 *
 */
public enum GateType {
	AliPay("0001", "alipay")/*, Tenpay("0002", "tenpay"), Chinapnr("0003", "chinapnr"), Dovepay("0004", "dovepay"), Yeepay("0006", "yeepay"), Cibpay("0007", "cibpay")*/;
	public final String code;
	public final String name;

	private GateType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static GateType getByCode(String code){
		for (GateType t : GateType.values()) {
			if (t.code.equals(code)) {
				return t;
			}
		}
		return null;
	}
}
