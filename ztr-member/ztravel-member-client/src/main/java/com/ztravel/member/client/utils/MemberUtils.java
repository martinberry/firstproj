package com.ztravel.member.client.utils;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;


/**
 * @author wanhaofan
 * */
public class MemberUtils {

	private static final Random random = new Random() ;

	private static final String prefix = "zlx" ;

	public static String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}

	public static String randomPassword(){
		int rand = random.nextInt(99999) ;
		if(rand < 10000){
			rand += 10000 ;
		}
		return prefix + rand ;
	}

}
