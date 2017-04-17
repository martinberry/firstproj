package com.ztravel.front.member.test;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;


public class RequestBodyTest {

	@Test
	public void test() {
		final ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.canDeserialize(mapper.constructType(SubClass.class)));
	}

}
