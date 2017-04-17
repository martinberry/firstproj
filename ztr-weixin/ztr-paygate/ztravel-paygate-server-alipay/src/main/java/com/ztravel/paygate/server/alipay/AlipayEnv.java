package com.ztravel.paygate.server.alipay;

import com.ztravel.paygate.api.Env;
import com.ztravel.paygate.core.enums.GateType;

/**
 * 支付宝配置信息
 * 
 * @author dingguangxian
 */
public class AlipayEnv extends Env {
	private static final String CONFIG_FILE = "alipay_env.properties";
	private static final String PARTNER_CONFIG_FILE = "partner.$$.properties";
	private static AlipayEnv instance = null;

	private AlipayEnv() {
		super(CONFIG_FILE, PARTNER_CONFIG_FILE);
	}

	public static AlipayEnv instance() {
		if (instance == null) {
			instance = new AlipayEnv();
		}
		return instance;
	}
	/**
	 * 支付宝服务接口名称R
	 * @author dingguangxian
	 *
	 */
	public static enum AlipayService{
		/** 交易类型,单笔支付 */
		TT_SINGLE_PAYMENT(Args.TT_SINGLE_PAYMENT),
		/** 结果通过校验 */
		TT_NOTIFY_VERIFY(Args.TT_NOTIFY_VERIFY),
		/** 交易类型,查询订单详情 */
		TT_QUERY(Args.TT_QUERY),
		/** 交易类型,退款 */
		TT_REFUND(Args.TT_REFUND),
		/** 交易类型,退款查询 */
		TT_REFUND_QUERY(Args.TT_REFUND_QUERY),
		/** 交易类型,交易记录批量下载 */
		TT_TRADE_BATCH_DOWNLOAD(Args.TT_TRADE_BATCH_DOWNLOAD),
		/** 手机支付 */
		TT_MOBILE_PAY(Args.TT_MOBILE_PAY),
		
		/** 合作商户签约 */
		TT_PARTNER_SIGN("sign_protocol_with_partner"),
		/** 合作商户签约查询 */
		TT_PARTNER_SIGN_QUERY("query_customer_protocol"),
		/** 合作商户签约查询 */
		TT_PARTNER_UNSIGN("customer_unsign"),
		
		/** 分润 */
		TT_SHARE_PROFIT("distribute_royalty"),
		
		/** 退款冻结 */
		TT_REFUND_FREEZE("air_trade_refund_freeze"),
		/** 退款冻结解冻 */
		TT_REFUND_UNFREEZE("air_trade_refund_unfreeze"),
		

		/** CAE代扣 */
		TT_AGENT_PAY("cae_charge_agent"),
		
		/** 直接转账到支付宝账户 */
		TT_TRANSFER_ACCOUNT("batch_trans_notify_no_pwd"),
		;
		private String name;
		private AlipayService(String name){
			this.name = name;
		}
		public String serviceName(){
			return this.name;
		}
	}

	@Override
	protected GateType gateType() {
		return GateType.AliPay;
	}

	/**
	 * 配置信息对应的参数名称
	 * 
	 * @author dingguangxian
	 * 
	 */
	public static interface Args {
		// 配置信息
		public String SERVICE_PORT = "alipay.service.port";
		public String SERVICE_WORKTHREADS = "alipay.service.workThreads";

		public String RESULT_KEYS = "alipay.resultKeys";

		public String URL_SINGLE_PAY = "alipay.payBuy.url";
		public String URL_QUERY = "alipay.query.url";
		public String URL_REFUND = "alipay.refund.url";
		public String URL_REFUND_QUERY = "alipay.refundQuery.url";
		public String URL_VERIFY = "alipay.verify.url";
		public String URL_TRADE_BATCH_DOWNLOAD = "alipay.tradeBatchDownload.url";
		/** 支付宝公钥 */
		public String RSA_PUBLIC_KEY = "alipay.rsa.publickey";

		// 常量
		public String CHARSET_GBK = "gbk";
		public String CHARSET_UTF8 = "utf-8";

		public String TRADE_STATUS_PAYING = "WAIT_BUYER_PAY";
		public String TRADE_STATUS_SUCCESS = "TRADE_SUCCESS";
		public String TRADE_STATUS_FINISHED = "TRADE_FINISHED";
		public String TRADE_STATUS_CLOSED = "TRADE_CLOSED";
		/** 签名类型 */
		public String SIGN_TYPE_MD5 = "MD5";
		public String SIGN_TYPE_RSA = "RSA";

		// 交易类型,单笔支付
		public String TT_SINGLE_PAYMENT = "create_direct_pay_by_user";
		public String TT_NOTIFY_VERIFY = "notify_verify";
		// 交易类型,查询订单详情
		public String TT_QUERY = "single_trade_query";
		// 交易类型,退款
		public String TT_REFUND = "refund_fastpay_by_platform_nopwd";
		// 交易类型,退款查询
		public String TT_REFUND_QUERY = "refund_fastpay_query";
		// 交易类型,交易记录批量下载
		public String TT_TRADE_BATCH_DOWNLOAD = "account.page.query";
		// 交易类型,交易记录批量下载
		public String TT_MOBILE_PAY = "mobile.securitypay.pay";

		// partner级别的参数
		/** 签名类型 */
		public String SIGN_TYPE = "alipay.signType";
		/** 签名key */
		public String SIGN_KEY = "alipay.signKey";
		/** partner */
		public String PARTNER = "alipay.partner";
		/** 卖方email */
		public String SELLER_EMAIL = "alipay.seller.email";
		/** 卖方email */
		public String ACCOUNT_NAME = "alipay.account.name";
		/** 卖方rsa私钥 */
		public String RSA_PRIVATE_KEY = "alipay.rsa.privatekey";
	}
}
