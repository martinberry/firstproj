package com.ztravel.operator.util;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;

public class util {

	public static String getIpAddrByRequest(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();

        }

        return ip;

    }

	public static boolean validateUsername(String str) {

		if(StringUtils.isNotBlank(str)){
			return str.matches("^([a-zA-Z0-9|\u4e00-\u9fa5|.]){1,20}$");
		}

		return false;

		 }

	public static boolean validatePassword(String str) {

		if(StringUtils.isNotBlank(str)){
			return str.matches("^[A-Za-z0-9]{6,28}$");
		}

		return false;

		 }


	public static void main(String[] args){
		boolean boo = validateUsername(null);
        System.out.println(boo);
    }


}
