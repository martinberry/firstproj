package com.ztravel.operator.financeMantain.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.springframework.web.context.request.WebRequest;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;

public class FileNameUtil {
	private static Logger logger = RequestIdentityLogger.getLogger(FileNameUtil.class);
	public static String converterFileName(String fileName, WebRequest request) {
		try {
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("文件名转换失败：",e);
		}
		return fileName;
	}
}
