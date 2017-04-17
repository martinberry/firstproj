package com.ztravel.product.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/ztravel-datastore.xml","classpath:spring/ztravel-common-all.xml","classpath:spring/ztravel-datasource.xml"})
public class MongoSequenceUtilTest{

//	static {
//		ClassPathXmlApplicationContext cpx
//			= new ClassPathXmlApplicationContext (new String[]{"spring/ztravel-datasource.xml","spring/ztravel-common-all.xml"});
//	}

	@Test
	public void test() throws Exception{
//		String ret = SequenceGenerator.generateId(SequenceGenerator.ZTRAVEL_MEMBER_SEQUENCE_PREFIX, SequenceGenerator.ZTRAVEL_SEQUENCE_NAME) ;
		String ret = MongoSequenceUtil.generateHotelEntityId() ;
		System.out.println(ret) ;
	}

////	@Override
////	public void run(){
////		try {
////			test() ;
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////
////	public static void main(String args[]){
////		System.out.println("start time :" + System.currentTimeMillis()) ;
////
////		for(int i=0 ; i<1000 ; i++){
////			Thread thread = new SequenceGeneratorTest() ;
////			thread.run();
////		}
//
//
//		System.out.println("end time :" + System.currentTimeMillis()) ;
//	}
}
