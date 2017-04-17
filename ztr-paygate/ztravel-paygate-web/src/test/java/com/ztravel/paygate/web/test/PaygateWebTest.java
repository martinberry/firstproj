package com.ztravel.paygate.web.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ztravel.paygate.thrift.model.ValSignRequest;

@ContextConfiguration(locations = { "classpath*:/spring/paygate-*.xml", "classpath*:/spring/servlet-context-paygate.xml" })
public abstract class PaygateWebTest extends AbstractJUnit4SpringContextTests {

	public Map<String, String> bean2Map(Object bean) {
		BeanMap map = BeanMap.create(bean);
		Map<String, String> result = new HashMap<String, String>();
		for (Entry entry : (Set<Entry>) map.entrySet()) {
			result.put(entry.getKey().toString(), entry.getValue() == null ? "" : entry.getValue().toString());
		}
		return result;
	}

	protected Map<String, String> convertStr2Map(String str) {
		String values[] = str.split(",");
		ValSignRequest request = new ValSignRequest();
		Map<String, String> map = new HashMap<>();
		request.setBankResponseData(map);
		for (String value : values) {
			String[] pair = value.split("=");
			String k = pair[0].trim();
			String v = "";
			if (pair.length > 1) {
				v = pair[1].trim();
			}
			map.put(k, v);
		}
		return map;
	}
}
