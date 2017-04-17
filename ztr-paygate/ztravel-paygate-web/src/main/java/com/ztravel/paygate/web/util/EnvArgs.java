package com.ztravel.paygate.web.util;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.config.tops.TopsConfEnum;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.ztravel.paygate.api.Env;

/**
 * 环境变量配置
 *
 * @author dingguangxian
 */
public class EnvArgs {
	protected static Logger logger = LoggerFactory.getLogger(EnvArgs.class);

	private static Properties properties = null;

	/**
	 * 参数及常量等
	 *
	 * @author dingguangxian
	 */
	public static interface Const {

		public static final String CONFIG_FILE = "properties/paygate-web.properties";
		public static final String GLOBAL_PAYGATE_CONFIG_FILE = "properties/paygate-server.properties";
		/** thrift服务主机 */
		// public static final String THRIFT_SERVICE_HOST_SUFFIX = ".service.host";
		/** thrift服务端口号 */
		// public static final String THRIFT_SERVICE_PORT_SUFFIX = ".service.port";
		/**
		 * zk 注册路径
		 */
		public static final String ZK_ZKNS = Env.Constants.PARAM_ZKNS;
		/**
		 * ZK服务名
		 */
		public static final String ZK_SERVICE_NAME_SUFFIX = ".serverName";
		/**
		 * thrift服务地址
		 */
		public static final String THRIFT_SERVICE_ADDRESS_SUFFIX = ".service.address";
		/**
		 * 前台通知地址
		 */
		public static final String FG_NOTIFY_URL_SUFFIX = ".fgNotifyUrl";
		/**
		 * 后台通知地址
		 */
		public static final String BG_NOTIFY_URL_SUFFIX = ".bgNotifyUrl";
		/**
		 * 后台通知地址
		 */
		public static final String MOBILE_NOTIFY_URL_SUFFIX = ".mobileNotifyUrl";
		/**
		 * 退款通知地址
		 */
		public static final String REFUND_NOTIFY_URL_SUFFIX = ".refund.notifyUrl";
		/**
		 * 支付宝手机支付商户号
		 */
		public static final String PARTNER_MOBILE_PAY = "alipay.mobile.partner";

		/**
		 * 冻结通知地址
		 */
		public static final String FREEZE_NOTIFY_URL_SUFFIX = ".freeze.notifyUrl";
		/**
		 * 解冻通知地址
		 */
		public static final String UNFREEZE_NOTIFY_URL_SUFFIX = ".unfreeze.notifyUrl";

		/**
		 * 转账结果通知
		 */
		public static final String TRANSFER_ACCOUNT_NOTIFY_URL_SUFFIX = ".taNotifyUrl";

		/**
		 * 客户端确认成功的标识
		 */
		public static final String CLIENT_CONFIRM_SUCCESS_FLAG = "T";
	}

	/**
	 * 加载配置信息
	 */
	protected synchronized static void init() {
		if (properties == null) {
			properties = TopsConfReader.getConfProperties(Const.GLOBAL_PAYGATE_CONFIG_FILE, TopsConfEnum.ConfScope.R);
			properties.putAll(TopsConfReader.getConfProperties(Const.CONFIG_FILE, TopsConfEnum.ConfScope.G));
		}
	}

	/**
	 * 获取全局配置参数
	 *
	 * @param argsName 参数名称
	 * @return
	 */
	public static String getArgs(String argsName) {
		init();
		if (properties != null) {
			return StringUtils.trimToEmpty(properties.getProperty(argsName));
		}
		return null;
	}
}
