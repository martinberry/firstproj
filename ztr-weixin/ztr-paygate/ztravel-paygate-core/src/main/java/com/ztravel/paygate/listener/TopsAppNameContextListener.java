package com.ztravel.paygate.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;

import com.travelzen.framework.config.tops.util.TopsConfigReaderUtil;
import com.travelzen.framework.core.util.StringUtil;

/**
 * Created by dylan on 14-6-9.
 */
public class TopsAppNameContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		String appName = event.getServletContext().getInitParameter("appName");
		if (StringUtils.isNotBlank(appName)) {
			TopsConfigReaderUtil.setApplicationName(StringUtil.trim(appName));
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
