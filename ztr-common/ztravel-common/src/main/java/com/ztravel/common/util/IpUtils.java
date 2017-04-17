package com.ztravel.common.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.text.StrTokenizer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztravel.common.entity.IpResloverEntity;

/**
 * 获取client ip
 * @author liuzhuo
 *<p>Look for “x-forwarded-for” header.
 *<p>If header exists, get the first IP.
 *<p>Check that:
 *<p>IP is valid.
 *<p>IP is not a private IP.
 *<p>If IP passes these 2 tests. Return this IP. If not move to the next IP and do the same test and so on.
 *<p>If header doesn’t exist. Return the IP from calling “request.getRemoteAddr”.
 */
public class IpUtils {
	

	public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	  
	public static final Pattern pattern = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");

	public static String longToIpV4(long longIp) {
		
		int octet3 = (int) ((longIp >> 24) % 256);
		int octet2 = (int) ((longIp >> 16) % 256);
		int octet1 = (int) ((longIp >> 8) % 256);
		int octet0 = (int) ((longIp) % 256);
		return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
	}
	
	public static long ipV4ToLong(String ip) {
		String[] octets = ip.split("\\.");
		return (Long.parseLong(octets[0]) << 24)
				+ (Integer.parseInt(octets[1]) << 16)
				+ (Integer.parseInt(octets[2]) << 8)
				+ Integer.parseInt(octets[3]);
	}

	public static boolean isIPv4Private(String ip) {
		long longIp = ipV4ToLong(ip);
		return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255"))
				|| (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255"))
				|| longIp >= ipV4ToLong("192.168.0.0")
				&& longIp <= ipV4ToLong("192.168.255.255");
	}
	
	public static boolean isIPv4Valid(String ip) {
		return pattern.matcher(ip).matches();
	}
	
	public static String getIpFromRequest(HttpServletRequest request) {
		String ip;
		boolean found = false;
		if ((ip = request.getHeader("x-forwarded-for")) != null) {
			StrTokenizer tokenizer = new StrTokenizer(ip, ",");
			while (tokenizer.hasNext()) {
				ip = tokenizer.nextToken().trim();
				if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
					found = true;
					break;
				}
			}
		}
		if (!found) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 简单的分割文本,得到解析信息
	 * @param html
	 * @return
	 */
	public static IpResloverEntity buildIpResloverEntity(String html) {
		
		IpResloverEntity ipResloverEntity = new IpResloverEntity();
		String description = html.split("meta")[3].split("link")[0];
		String dimension = description.split("Dimension:")[1].split("Longitude")[0].toString();
		String longitude = description.split("Longitude:")[1].split(".\"")[0].toString();
	    String address = description.split("所属地区:")[1].split(",ip")[0].toString();
	    
	    ipResloverEntity.setAddress(address);
	    ipResloverEntity.setDimension(dimension);
	    ipResloverEntity.setLongitude(longitude);
	    
	    return ipResloverEntity;
	    
	}
	
	/**
	 * 根据ip解析区域信息(882667网站)
	 * @param ip
	 * @return
	 */
	public static IpResloverEntity getAreaByIP(String ip) {
		String html = HttpUtil.httpGet("http://www.882667.com/ip_" + ip + ".html", null);
		
		if(org.apache.commons.lang.StringUtils.isEmpty(html)) {
			return null;
		}
		
		return buildIpResloverEntity(html);
	}
	
	/**
	 * 根据淘宝api解析地址  精确到市
	 * @param ip
	 * @return
	 * {"area":"华东",
	 * 	"area_id":"300000",
	 * 	"city":"上海市",
	 * 	"city_id":"310000",
	 * 	"country":"中国",
	 * 	"country_id":"CN",
	 * 	"county":"",
	 * 	"county_id":"-1",
	 * 	"ip":"116.228.99.242",
	 * 	"isp":"电信",
	 * 	"isp_id":"100017",
	 * 	"region":"上海市",
	 * 	"region_id":"310000"
	 * }
	 */
	public static JSONObject getAreaByIp(String ip){
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
		String result = HttpUtil.httpGet(url, "utf-8");
		JSONObject json = JSON.parseObject(result);
		if(json.get("code").toString().equals("0")) {
			return json.getJSONObject("data");
		}else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getAreaByIp("116.228.99.242"));
	}
}