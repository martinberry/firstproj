package com.ztravel.common.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;

/**
 * 记载web环境配置文件，应用级别下的覆盖全局
 * @author liuzhuo
 *
 */

public class WebEnv {
	
	private static final Logger LOG = LoggerFactory.getLogger(WebEnv.class);

	private static Properties pptConf = null;

	static {
		String path = null;
		try {
			path = "properties/web-env-default.properties";
			pptConf = TopsConfReader.getConfProperties(path, ConfScope.R);
		} catch (Exception e) {
			LOG.error("load properties R:properties/web-env-default.properties failed." ,e);
		}

		try {
			path = "properties/web-env.properties";
			Properties ppts = TopsConfReader.getConfProperties(path, ConfScope.G);
			for (Object oKey : ppts.keySet()) {
				String key = oKey.toString();
				pptConf.setProperty(key, ppts.getProperty(key));
			}
		} catch (Exception e) {
			LOG.warn("load G:properties/web-env-default.properties failed, all default env will be kept.");
		}
	}

	public static String get(String key) {
		return pptConf.getProperty(key);
	}

	public static String get(String key, String defaultValue) {
		return pptConf.getProperty(key, defaultValue);
	}


}
