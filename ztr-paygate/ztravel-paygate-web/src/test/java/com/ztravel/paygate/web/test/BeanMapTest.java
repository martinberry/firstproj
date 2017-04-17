package com.ztravel.paygate.web.test;

import net.sf.cglib.beans.BeanMap;

import com.ztravel.paygate.web.dto.result.PayResult;

public class BeanMapTest {
	public static void main(String[] args) {
		PayResult result = new PayResult();
		result.setAmount(1000);
		result.setClientId("100000");
		result.setGateType("0001");
		result.setOrderNum("12346");
		BeanMap map = BeanMap.create(result);
		System.out.println(map);
	}
}
