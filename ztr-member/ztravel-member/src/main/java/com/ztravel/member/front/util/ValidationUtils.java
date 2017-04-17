package com.ztravel.member.front.util;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.travelzen.framework.core.exception.BizException;

public class ValidationUtils  {

	/**
	 * person ID pattern(apply for new 18-bit ID and old 15-bit ID)
	 */
	public static final String ID_PATTERN = "^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[10-12])(0[1-9]|[10-12]\\d|3[01])\\d{3}(\\d|X)$";

	public static final String MOBILE_PATTERN = "^1[35678]\\d{9}$";

	public static final String NAME_PATTERN = "[\u4E00-\u9FA5]{2,40}";

	public static SimpleDateFormat FORMAT_YYMMDD = new SimpleDateFormat("yyMMdd");

	public static SimpleDateFormat FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	public static final Map<Integer, String> PROVINCE_MAP = new HashMap<Integer, String>();
	/**
	 * 11 : "北京", 12 : "天津", 13 : "河北", 14 : "山西", 15 : "内蒙古", 21 : "辽宁", 22 :
	 * "吉林", 23 : "黑龙江 ", 31 : "上海", 32 : "江苏", 33 : "浙江", 34 : "安徽", 35 : "福建",
	 * 36 : "江西", 37 : "山东", 41 : "河南", 42 : "湖北 ", 43 : "湖南", 44 : "广东", 45 :
	 * "广西", 46 : "海南", 50 : "重庆", 51 : "四川", 52 : "贵州", 53 : "云南", 54 : "西藏 ",
	 * 61 : "陕西", 62 : "甘肃", 63 : "青海", 64 : "宁夏", 65 : "新疆", 71 : "台湾", 81 :
	 * "香港", 82 : "澳门", 91 : "国外 "
	 */
	static {
		PROVINCE_MAP.put(11, "北京");
		PROVINCE_MAP.put(12, "天津");
		PROVINCE_MAP.put(13, "河北");
		PROVINCE_MAP.put(14, "山西");
		PROVINCE_MAP.put(15, "内蒙古");
		PROVINCE_MAP.put(21, "辽宁");
		PROVINCE_MAP.put(22, "吉林");
		PROVINCE_MAP.put(23, "黑龙江");
		PROVINCE_MAP.put(31, "上海");
		PROVINCE_MAP.put(32, "江苏");
		PROVINCE_MAP.put(33, "浙江");
		PROVINCE_MAP.put(34, "安徽");
		PROVINCE_MAP.put(35, "福建");
		PROVINCE_MAP.put(36, "江西");
		PROVINCE_MAP.put(37, "山东");
		PROVINCE_MAP.put(41, "河南");
		PROVINCE_MAP.put(42, "湖北");
		PROVINCE_MAP.put(43, "湖南");
		PROVINCE_MAP.put(44, "广东");
		PROVINCE_MAP.put(45, "广西");
		PROVINCE_MAP.put(46, "海南");
		PROVINCE_MAP.put(50, "重庆");
		PROVINCE_MAP.put(51, "四川");
		PROVINCE_MAP.put(52, "贵州");
		PROVINCE_MAP.put(53, "云南");
		PROVINCE_MAP.put(54, "西藏");
		PROVINCE_MAP.put(61, "陕西");
		PROVINCE_MAP.put(62, "甘肃");
		PROVINCE_MAP.put(63, "青海");
		PROVINCE_MAP.put(64, "宁夏");
		PROVINCE_MAP.put(65, "新疆");
		PROVINCE_MAP.put(71, "台湾");
		PROVINCE_MAP.put(81, "香港");
		PROVINCE_MAP.put(82, "澳门");
		PROVINCE_MAP.put(91, "国外 ");
	}



	/**
	 * avoid user to create new instance
	 */
	private ValidationUtils() {

	}




	/**
	 * validate person ID, if validate fails, throw BizException
	 */
	public static boolean validateID(String identity) {
		if (StringUtils.isBlank(identity)) {
			return false;
		}
		identity = identity.toUpperCase();
		if (!identity.matches(ID_PATTERN)) {
			return false;
		}
		int province = Integer.parseInt(identity.substring(0, 2));
		if (PROVINCE_MAP.get(province) == null) {
			return false;
		}
		// 18位身份证需要验证最后一位校验位
		if (identity.length() == 18) {
			// ∑(ai×Wi)(mod 11)
			// 加权因子
			int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
			// 校验位
			char[] parity = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
			int sum = 0;
			int ai = 0;
			int wi = 0;
			for (int i = 0; i < 17; i++) {
				ai = Integer.valueOf(identity.substring(i, i + 1));
				wi = factor[i];
				sum += ai * wi;
			}
			char last = parity[sum % 11];
			char code17 = identity.charAt(17);
			if (last != code17) {
				return false;
			}
		}
		return true;
	}

	public static void validateMobile(String mobile) {
		if (!mobile.matches(MOBILE_PATTERN)) {
			throw BizException.instance("手机号不合法:" + mobile);
		}
	}


}
