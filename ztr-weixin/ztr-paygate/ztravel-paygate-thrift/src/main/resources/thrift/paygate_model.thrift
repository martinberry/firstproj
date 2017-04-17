namespace java com.ztravel.paygate.thrift.model
/**
 * 单笔支付请求(签名)
 */
struct PaySignRequest{
	/** partner */
	1: optional string partner,
	/** 订单号 */
	2: required string orderNum,
	/** 订单金额 */
	3: required i64 amount,
	/** 商品详情 */
	4: optional string comment,
	/** 银行前台通知url */
	5: optional string fgNotifyUrl,
	/** 银行后台通知url */
	6: required string bgNotifyUrl,
	/** 手机号 */
	7: optional string mobileNo,
	/** 是否手机支付 */
	8: optional bool isMobilePay = false;
	/** 其他的参数信息 */
	9: optional map<string,string> extraInfos,
}
/**
 * 单笔支付请求的签名返回
 */
struct PaySignResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/**  对应银行支付url,返回码为0000时，该字段不能为空 */
	3: optional string bankPayUrl,
	/**  签名时使用的参数信息等 */
	4: optional map<string,string> extraInfos,
}
/**
 * 验签请求
 */
struct ValSignRequest{
	/** partner */
	1: optional string partner,
    /**  是否前台返回结果（前后台验签方式有所不同） */
    2: required bool fgNotify;
	/** 是否手机支付 */
	3: optional bool isMobilePay = false;
    /**  银行的返回结果 */
    4: required map<string,string> bankResponseData,
}
/**
 * 验签结果
 */
struct ValSignResponse{
	/** 返回码, 0000 代表验证成功 */
	1: required string retCode,
	/** 订单号 */
	2: optional string orderNum,
	/** 返回信息，是对返回码的详细描述 */
	3: optional string retMsg,
	/** 支付平台生成的流水号,验证成功时，该字符不能为空 */
	4: optional string traceNum,
	/** 扣款金额。返回码为0000时，该字段不能为空 */
	5: optional i64 amount,
}
/**
 * 交易查询请求
 */
struct QueryRequest{
	/** partner */
	1: optional string partner,
	/** 支付订单号 */
	2: required string orderNum,
	/** 交易流水号 */
	3: optional string traceNum,
}
/**
 * 交易查询结果
 */
struct QueryResponse{
	/** 返回码, 0000 代表处理成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/** 订单号。返回码为0000时，该字段不能为空 */
	3: optional string orderNum,
	/** 支付平台生成的流水号,返回码为0000时,该字段不为空 */
	4: optional string traceNum,
	/** 支付金额 */
	5: optional i64 amount,
	/** 支付状态,SUCCESS,FAIL,HANDING */
	6: optional string payState,
	/** 额外的返回信息 */
	7: optional map<string,string> extraInfos,
}
/**
 * 退款时的退分润请求
 */
struct RefundShareProfitModel{
	/** 原分润方账户 */
	1: required string refundAccount,
	/** 退分润金额 */
	2: required i64 amount,
	/** 退分润备注 */
	3: optional string comment,
}
/**
 * 退款申请
 */
struct RefundRequest{
	/** partner */
	1: optional string partner,
	/** 退款标识 */
	2: required string refundNum,
	/** 原支付交易的订单号 */
	3: required string orderNum,
    /** 原支付交易流水号 */
	4: required string traceNum,
	/** 本次退款金额 */
	5: required i64 refundAmount,
	/** 原始交易金额 */
	6: required i64 transAmount,
	/** 备注信息 */
	7: optional string comment,
	/** 交易结果通知地址 */
	8: optional string notifyUrl,
	/** 退款时间(yyyy-MM-dd HH:mm:ss) */
	9: optional string refundTime,
	/** 退分润信息 */
	10: optional list<RefundShareProfitModel> refundProfitDetails;
}
/**
 * 退款响应结果
 */
struct RefundResponse{
	/** 返回码, 0000 代表处理成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/** 退款请求时使用的参数等信息 */
	3: optional map<string,string> extraInfos,
}

/**
 * 退款验签请求
 */
struct RefundValSignRequest{
	/** partner */
	1: optional string partner,
	/** 支付订单号 */
	2: map<string,string> refundResponse,
}
/**
 * 退款验签结果
 */
struct RefundValSignResponse{
	/** 返回码, 0000 代表验证成功 */
	1: required string retCode,
	/** 退款标识 */
	2: optional string refundNum,
	/** 返回信息，是对返回码的详细描述 */
	3: optional string retMsg,
	/** 扣款金额。返回码为0000时，该字段不能为空 */
	4: optional i64 refundAmount,
}

/**
 * 交易查询请求
 */
struct RefundQueryRequest{
	/** partner */
	1: optional string partner,
	/** 支付订单号 */
	2: required string refundNum,
}
/**
 * 交易查询结果
 */
struct RefundQueryResponse{
	/** 退单号 */
	1: required string refundNum,
	/** 返回码, 0000 代表处理成功 */
	2: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	3: optional string retMsg,
	/** 额外的返回信息 */
	4: optional map<string,string> extraInfos,
}
/**
 * 批量下载交易记录请求
 */
struct TradeBatchDownloadRequest{
	/** 商户号 */
	1: required string partner,
	/** 交易日期(yyyyMMdd) */
	2: required string transDate,
	/** 页号,支付宝使用 */
	3: optional i16 pageNo=1,
}

/**
 * 批量下载交易记录返回结果
 */
struct TradeBatchDownloadResponse{
	/** 返回码, 0000 代表验证成功 */
	1: required string retCode,
	/** 返回信息 */
	2: required string retMsg,
	/** 商户号 */
	3: required string partner,
	/** 下载是否成功(无账单记录也认为是下载成功) */
	4: optional bool success=false,
	/** 是否有下一页数据 */
	5: optional bool hasNextPage=true,
	/** 附加的参数信息 */
	6: optional map<string,string> extraInfos,
	/** 下载的结果内容 */
	7: optional string content,
}

/////PartnerSign:合作商户签约/////
/**
 * 合作商户签约
 */
struct PartnerSignRequest{
	/** partner */
	1: optional string partner,
	/** 合作商户partner */
	2: optional string signPartner,
	/** email */
	3: optional string signEmail,
	/** 账户名称 */
	4: optional string signAccountName,
	/** 其他的参数信息 */
	5: optional map<string,string> extraInfos,
}
/**
 * 合作商户签约请求返回
 */
struct PartnerSignResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/**  商户签约url,返回码为0000时，该字段不能为空 */
	3: optional string signUrl,
	/**  额外的返回信息 */
	4: optional map<string,string> extraInfos,
}

/**
 * 合作商户签约查询
 */
struct PartnerSignQueryRequest{
	/** partner */
	1: optional string partner,
	/** 合作商户partner */
	2: optional string signPartner,
	/** email */
	3: optional string signEmail,
	/** 账户名称 */
	4: optional string signAccountName,
	/** 其他的参数信息 */
	5: optional map<string,string> extraInfos,
}
/**
 * 合作商户签约查询返回
 */
struct PartnerSignQueryResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/** 是否签约支付 */
	3: required bool signPayment,
	/** 是否签约退款 */
	4: required bool signRefund,
	/**  额外的返回信息 */
	5: optional map<string,string> extraInfos,
}

/**
 * 合作商户解约请求
 */
struct PartnerUnsignRequest{
	/** partner */
	1: optional string partner,
	/** 合作商户partner */
	2: optional string signPartner,
	/** email */
	3: optional string signEmail,
	/** 账户名称 */
	4: optional string signAccountName,
	/** 其他的参数信息 */
	5: optional map<string,string> extraInfos,
}
/**
 * 合作商户解约请求返回结果
 */
struct PartnerUnsignResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/**  额外的返回信息 */
	3: optional map<string,string> extraInfos,
}
/////ShareProfit:商户分润/////
/**
 * 分润请求
 */
struct ShareProfitRequest{
	/** partner */
	1: optional string partner,
	/** 分润标识号 */
	2: required string shareNum,
	/** email */
	3: required string orderNum,
	/** 账户名称 */
	4: required string traceNum,
	/** 分润账户 */
	5: required string sharePartner,
	/** 分润金额 */
	6: required i64 shareAmount,
	/** 分润备注 */
	7: optional string comment,
	/** 其他的参数信息 */
	8: optional map<string,string> extraInfos,
}
/**
 * 分润请求返回结果
 */
struct ShareProfitResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/**  额外的返回信息 */
	3: optional map<string,string> extraInfos,
}

/////RefundFreeze:退单冻结/解冻/////
/**
 * 冻结请求
 */
struct RefundFreezeRequest{
	/** partner */
	1: optional string partner,
	/** 冻结标识号 */
	2: required string freezeNum,
	/** 订单号 */
	3: required string orderNum,
	/** 交易流水号 */
	4: required string traceNum,
	/** 冻结账户 */
	5: required string freezePartner,
	/** 冻结金额 */
	6: required i64 freezeAmount,
	/** 冻结备注 */
	7: optional string comment,
	/** 冻结结果通知地址 */
	8: optional string freezeNotifyUrl,
	/** 其他的参数信息 */
	9: optional map<string,string> extraInfos,
}
/**
 * 冻结请求返回结果
 */
struct RefundFreezeResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/**  额外的返回信息 */
	3: optional map<string,string> extraInfos,
}
/**
 * 退单解冻请求
 */
struct RefundUnfreezeRequest{
	/** partner */
	1: optional string partner,
	/** 解冻标识号 */
	2: required string unfreezeNum,
	/** 冻结标识号 */
	3: required string freezeNum,
	/** 冻结金额 */
	4: required i64 unfreezeAmount,
	/** 解冻备注 */
	5: optional string comment,
	/** 解冻结果通知地址 */
	6: optional string notifyUrl,
	/** 其他的参数信息 */
	7: optional map<string,string> extraInfos,
}
/**
 * 退单解冻请求返回结果
 */
struct RefundUnfreezeResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/**  额外的返回信息 */
	3: optional map<string,string> extraInfos,
}

/////AgentPay:代扣/////
/**
 * CAE代扣请求
 */
struct AgentPayRequest{
	/** partner */
	1: optional string partner,
	/** 订单号 */
	2: required string orderNum,
	/** 订单金额 */
	3: required i64 amount,
	/** 商品详情 */
	4: optional string comment,
	/** 银行后台通知url */
	5: required string notifyUrl,
	/** 支出的账户 */
	6: required string payAccount,
	/** 入账的账户 */
	7: optional string inAccount,
	/** 其他的参数信息 */
	8: optional map<string,string> extraInfos,
}
/**
 * CAE代扣请求返回结果
 */
struct AgentPayResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/**  额外的请求返回信息 */
	3: optional map<string,string> requestInfos,
	/**  额外的请求返回信息 */
	4: optional map<string,string> responseInfos,
}

/////TransferAccount:转账到对方账户/////
/**
 * 转账到对账账户请求
 */
struct TransferAccountRequest{
	/** partner */
	1: optional string partner,
	/** 转账标识号 */
	2: required string transferNum,
	/** 入账账户 */
	3: required string inAccount,
	/** 入账账户名称 */
	4: required string inAccountName,
	/** 转账金额 */
	5: required i64 amount,
	/** 转账备注 */
	6: optional string comment,
	/** 转账结果通知地址 */
	7: optional string notifyUrl,
	/** 转账时间(yyyy-MM-dd HH:mm:ss) */
	8: optional string transferTime,
	/** 其他的参数信息 */
	9: optional map<string,string> extraInfos,
}
/**
 * 转账到对账账户请求返回结果
 */
struct TransferAccountResponse{
	/** 返回码, 0000 代表签名成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/**  额外的返回信息 */
	3: optional map<string,string> extraInfos,
}
/**
 * 转账验签请求
 */
struct TransferAccountValsignRequest{
	/** partner */
	1: optional string partner,
    /**  银行的返回结果 */
    2: required map<string,string> bankResponseData,
}
/**
 * 转账验签结果
 */
struct TransferAccountValsignResponse{
	/** 返回码, 0000 代表验证成功 */
	1: required string retCode,
	/** 返回信息，是对返回码的详细描述 */
	2: optional string retMsg,
	/** 返回的其他信息 */
	3: optional map<string,string> extraInfos,
}