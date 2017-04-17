package com.ztravel.paygate.core.enums;

public enum PaygateError {

	SUCCESS("0000","成功"),
	
	E001_ENV_FAIL("E001","服务器环境初始化失败"),
	E002_SEVER_ERR("E002","服务器错误"),
	
	E100_ERROR("E100","未知异常"),
	E101_DB_ERROR("E101","数据库异常"),
	E103_PF_SIGN_FAIL("E103","平台签名失败"),
	E104_PF_VAL_SIGN_FAIL("E104","平台验签失败"),
	
	E201_INVALID_PARAM("E201","参数不正确."),
	E202_ORDER_INVALID("E202","订单不存在"),
	
	E203_GATETYPE_NOT_SUPPORT("E203","不支持该支付类型"),

	E204_NULL_CLIENTID("E204","ClientId不能为空"),
	E205_NULL_PARTNER("E205","partner不能为空"),
	
	E205_INVALID_CLIENTID("E205","ClientId无效"),
	E206_VALIDATE_FAIL("E206","信息校验失败"),
	E207_INVALID_AMOUNT("E207","金额不正确"),
	E208_INVALID_FG_NOTIFY_URL("E208","前台通知地址不正确"),
	E209_INVALID_RESPONSE("E209","返回的结果无效或已经处理"),
	E210_AMOUNT_NOT_MATCHED("E210","实际支付金额与原始金额不匹配"),
	E221_CLIENTID_NOT_MATCHED("E221","ClientId与原支付信息不匹配"),
	E222_PAY_STATE_NOT_SUPPORT_REFUND("E222","订单支付状态异常，不能退款"),
	E223_CLIENT_COFNIRM_NOT_SUPPORT_REFUND("E223","订单尚未确认，不能退款"),
	
	E224_REFUND_REQ_FAIL("E224","退款请求失败"),
	E225_REFUND_NUM_NOT_MATCHED("E225","退款返回单号与请求单号不一致"),
	E226_DATA_NOT_FOUND("E226","支付平台不存在该数据"),
	E227_REFUND_PROC_FAIL("E227","退款处理失败"),
	E228_PAY_FAIL("E228","订单未支付或支付失败"),
	E230_INVALD_REQUEST("E230","请求不受支持"),
	E231_PARTNER_UNMATCHED("E231","partner无效."),
	
	E240_TRADE_BATCH_DOWNLOAD_REQ_FAIL("E240","批量交易记录下载请求失败."),
	E241_INVALID_TRADE_RECORD_FORMAT("E241","交易记录格式不正确."),
	
	E301_SIGN_FAIL("E301","签名失败"),
	E302_VAL_SIGN_FAIL("E302","验签失败"),
	E310_QUERY_FAIL("E310","查询失败"),
	E311_INVALID_NOTIFY_URL("E311","客户端通知地址不正确"),
	E312_INVALID_REFUND_RESULT_DETAILS("E312","退款结果明细不正确"),
	
	E313_PATCH_NOT_FIND_PAYGATE_DATA("E313","没有查询到网关数据"),
	E314_PATCH_NOT_FIND_PF_DATA("E314","平台中没有查询到订单数据"),
	E315_PATCH_FAIL_PF_DATA("E315","订单在平台支付失败"),
	

	E320_SHARE_PROFIT_FAIL("E320","分润请求失败"),
	E321_SHARE_PROFIT_ERROR("E321","分润请求出现异常"),

	E330_REFUND_FREEZE_FAIL("E330","退款冻结请求失败"),
	E331_REFUND_FREEZE_ERROR("E331","退款冻结请求出现异常"),
	E332_REFUND_UNFREEZE_FAIL("E332","退款冻结请求失败"),
	E333_REFUND_UNFREEZE_ERROR("E333","退款冻结请求出现异常"),
	E334_FREEZE_NOT_EXISTS("E334","冻结记录不存在"),
	E335_FREEZE_STATE_INVALID("E335","冻结状态异常"),

	E340_AGENT_PAY_FAIL("E340","请求代扣失败"),
	E341_AGENT_PAY_ERROR("E341","请求代扣出现异常"),

	E350_TRANSFER_ACCOUNT_FAIL("E350","请求转账失败"),
	E351_TRANSFER_ACCOUNT_ERROR("E351","请求转账出现异常"),
	
	E360_PARTNER_UNSIGN_FAIL("E360","解除签约失败"),
	E361_PARTNER_UNSIGN_ERROR("E361","解除签约出现异常"),

	E901_INVALID_INTERFACE("E901","不支持该接口"),
	;
	private String code;
	private String desc;

	private PaygateError(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String code() {
		return this.code;
	}

	public String desc() {
		return this.desc;
	}
}
