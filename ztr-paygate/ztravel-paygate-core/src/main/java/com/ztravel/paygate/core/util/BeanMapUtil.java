package com.ztravel.paygate.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.beans.BeanMap;

/**
 * 
 * @author dingguangxian
 * 
 */
public class BeanMapUtil {
	/**
	 * 将bean中的属性转为map形式
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, String> mapBean(Object object) {
		BeanMap beanMap = BeanMap.create(object);
		Map<String, String> map = new HashMap<String, String>();
		for (Object entry : beanMap.entrySet()) {
			Entry e = (Entry) entry;
			String key = (String) e.getKey();
			Object value = e.getValue();
			map.put(key, value == null ? "" : value.toString());
		}
		return map;
	}
}
