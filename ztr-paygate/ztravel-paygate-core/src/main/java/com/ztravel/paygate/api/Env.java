package com.ztravel.paygate.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.config.tops.TopsConfEnum;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.ztravel.paygate.core.enums.GateType;

/**
 * 环境参数配置
 *
 * @author dingguangxian
 */
public abstract class Env {
	public static interface Constants{
		String PATH_GLOBAL_PROPERTIES = "properties/paygate-server.properties";
		String PARAM_ZKNS = "zkns";
		String PARAM_SERVER_NAME = "serverName";
		String PARAM_ZK_ENABLED = "zkEnabled";
		String DEFAULT_ZKNS = "/ztr/test";
		String DEFAULT_ZK_ENABLED = "true";
	}
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private String envConfigFile;
	private String partnerConfigFile;

	private Properties properties = null;
	private Map<String, Properties> partnerProperties = new HashMap<>();

	/**
	 * @param envConfigFile     全局变量配置文件
	 * @param partnerConfigFile partner变量配置文件，partner以$$替代.如:partner.$$.properties
	 */
	protected Env(String envConfigFile, String partnerConfigFile) {
		this.envConfigFile = envConfigFile;
		this.partnerConfigFile = partnerConfigFile;
	}

	/**
	 * 加载配置信息
	 */
	protected synchronized void init() {
		if (properties == null) {
			properties = initDefaultConfig();
			Properties property = TopsConfReader.getConfProperties(envConfigFile, TopsConfEnum.ConfScope.G);
			properties.putAll(property);
		}
	}

	private Properties initDefaultConfig() {
		logger.info("load global paygate config properties from : {}", Constants.PATH_GLOBAL_PROPERTIES);
		Properties properties = new Properties();
		properties.put(Constants.PARAM_ZKNS, Constants.DEFAULT_ZKNS);
		properties.put(Constants.PARAM_ZK_ENABLED, Constants.DEFAULT_ZK_ENABLED);
		Properties defaultConfig = TopsConfReader.getConfProperties(Constants.PATH_GLOBAL_PROPERTIES, TopsConfEnum.ConfScope.R);
		if(StringUtils.isNotBlank(defaultConfig.getProperty(Constants.PARAM_ZKNS))){
			properties.put(Constants.PARAM_ZKNS, StringUtils.trim(defaultConfig.getProperty(Constants.PARAM_ZKNS)));
		}
		String gateTypePrefix = gateType().name + ".";
		for(String name : defaultConfig.stringPropertyNames()){
			if(name.startsWith(gateTypePrefix)){
				String val = defaultConfig.getProperty(name);
				properties.put(name.substring(gateTypePrefix.length()), StringUtils.trim(val));
			}
		}
		logger.debug("load global paygate config success.");
		logger.debug(defaultConfig.toString());
		return properties;
	}

	/**
	 * 加载配置信息
	 */
	protected synchronized void initPartnerProperties(String partner) {
		if (!partnerProperties.containsKey(partner)) {
			Properties properties = TopsConfReader.getConfProperties(partnerConfigFile.replace("$$", partner), TopsConfEnum.ConfScope.G);
			partnerProperties.put(partner, properties);
		}
	}

	/**
	 * 获取全局配置参数
	 *
	 * @param argsName 参数名称
	 * @return
	 */
	public String getArgs(String argsName) {
		init();
		if (properties != null) {
			return properties.getProperty(argsName);
		}
		return null;
	}

	/**
	 * 获取partner参数,优先尝试加载partner级别的参数，如果不存在partner的特殊配置，调用{@link Env#getArgs(String)}获取全局定义
	 *
	 * @param partner  商户号
	 * @param argsName 参数名称
	 * @return
	 */
	public String getPartnerArgs(String partner, String argsName) {
		initPartnerProperties(partner);
		Properties properties = partnerProperties.get(partner);
		if (properties != null) {
			return properties.getProperty(argsName);
		}
		return getArgs(argsName);
	}

	protected abstract GateType gateType();

	/**
	 * 获取注册到zookeeper中的前缀地址
	 * @return
	 */
	public String getTznsPath() {
		return getArgs(Constants.PARAM_ZKNS);
	}

	/**
	 * 获取服务名称
	 * @return
	 */
	public String getServerName() {
		return getArgs(Constants.PARAM_SERVER_NAME);
	}

	public String getShardId() {
		return gateType().name;
	}

	/**
	 * 是否支持ZK注册
	 * @return
	 */
	public boolean isZkEnabled() {
		String zkEnabled = getArgs(Constants.PARAM_ZK_ENABLED);
		if(StringUtils.isNotBlank(zkEnabled)){
			return Boolean.valueOf(zkEnabled.trim());
		}
		return true;
	}
}
