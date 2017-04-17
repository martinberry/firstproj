package com.ztravel.payment.paygate;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.ztravel.common.security.Md5Util;

/**
 * @author zuoning.shen
 *
 */
public class PaygateEncryptUtil {
	private static final String charset = "UTF-8";

	/**
	 * 生成预签名字符串
	 * 
	 * @param params
	 * @param signKey
	 */
	private static String buildPreSignStr(Map<String, String> params, String signKey) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				String value = entry.getValue();
				String key = entry.getKey();
				if (value != null && !"".equals(value.trim()) && !"sign".equals(key)) {
					map.put(key, value);
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		sb.append("key=").append(signKey);
//		if (sb.length() > 0) {
//			sb.deleteCharAt(sb.length() - 1);
//		}
		return sb.toString();
	}

	/**
	 * 获取签名字符串
	 * 
	 * @param params
	 * @param signKey
	 * @return
	 */
	public static String getSignStr(Map<String, String> params, String signKey) {
		String preSignStr = buildPreSignStr(params, signKey);
		return Md5Util.encode(preSignStr, charset);
	}

	/**
	 * 验证签名信息
	 * 
	 * @param params
	 * @param signStr
	 * @param signKey
	 * @return
	 */
	public static boolean verifySignStr(Map<String, String> params, String signStr, String signKey) {
		return getSignStr(params, signKey).equals(signStr);
	}
}