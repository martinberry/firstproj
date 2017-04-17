package com.ztravel.searchServer.test;

import org.junit.Test;

import com.ztravel.common.util.WebEnv;

public class Env {
	
	@Test
	public void testNeedBuildCountryIndex() {
		String need = WebEnv.get("needBuildCountryIndex");
		System.out.println(Boolean.FALSE.toString());
		System.out.println(Boolean.TRUE.toString());
		System.out.println(Boolean.parseBoolean(need));
	}

}
