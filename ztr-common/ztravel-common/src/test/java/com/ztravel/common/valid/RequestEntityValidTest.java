package com.ztravel.common.valid;

import org.junit.Test;

public class RequestEntityValidTest {
	
	@Test
	public void test1(){
		
		try {
			ValidEntity validEntity = new ValidEntity();
			validEntity.setAge(12);
			validEntity.setName("aaa");
			validEntity.setPasssword("111111");
			validEntity.setConfirmPassword("123456");
			
			ValidSubClass validSubClass = new ValidSubClass();
			validEntity.setValidSubClass(validSubClass);
			
			ValidResult result = BasicValidator.valid(validEntity);
			System.out.println(result.getValidMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
