package com.ztravel.common.util;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/ztravel-datasource.xml","classpath:spring/ztravel-common-all.xml"})
public class IdGeneratorTest{
	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Test
	public void test() throws Exception{
//		System.out.println(idGeneratorUtil.getMemberId()) ;
//		System.out.println(idGeneratorUtil.getMId()) ;
//		System.out.println(idGeneratorUtil.getOrderId()) ;
//		System.out.println(idGeneratorUtil.getOrderNo()) ;
//		System.out.println(idGeneratorUtil.getCouponId()) ;
//		System.out.println(idGeneratorUtil.getCouponCode()) ;
//		System.out.println(idGeneratorUtil.getRoleId()) ;
		System.out.println(idGeneratorUtil.getSenceId()) ;
	}
}
