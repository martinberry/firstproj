package com.ztravel.common.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 模拟随机选取IP地址
 * @author MH
 */
public class IPAddressMock {
	private static List<String> ipList = new ArrayList<String>();;
	static{
		ipList.add("125.113.14.172");  //浙江省
		ipList.add("49.52.64.16");  //上海市
		ipList.add("58.242.30.29");  //安徽省
		ipList.add("27.25.64.39");  //湖北省
		ipList.add("49.73.72.11");  //江苏省
		ipList.add("27.98.209.206");  //北京市
		ipList.add("59.67.168.193");  //天津市
		ipList.add("27.129.65.122");  //河北省
		ipList.add("1.196.82.237");  //河南省
		ipList.add("59.44.98.74");  //辽宁省
		ipList.add("222.172.32.218");  //黑龙江省
		ipList.add("223.78.15.22");  //山东省
		ipList.add("60.13.141.142");  //新疆维吾尔自治区
		ipList.add("60.164.184.90");  //甘肃省
		ipList.add("59.48.158.70");  //山西省
		ipList.add("58.18.102.33");  //内蒙古自治区
		ipList.add("1.86.193.157");  //陕西省
		ipList.add("58.244.70.191");  //吉林省
		ipList.add("61.133.192.50");  //宁夏回族自治区
		ipList.add("58.20.16.130");  //湖南省
		ipList.add("58.59.133.168");  //广西壮族自治区
		ipList.add("59.56.124.123");  //福建省
		ipList.add("58.17.80.246");  //江西省
		ipList.add("58.60.3.208");  //广东省
		ipList.add("59.49.129.58");  //海南省
		ipList.add("14.104.188.59");  //重庆市
		ipList.add("60.160.0.144");  //云南省
		ipList.add("61.133.231.162");  //青海省
		ipList.add("124.31.222.3");  //西藏
		ipList.add("61.139.79.204");  //四川省
		ipList.add("1.205.26.177");  //贵州省
	}

	public static String getRandomIP(){
		Random ra = new Random();
		return ipList.get(ra.nextInt(ipList.size()));
	}
}
