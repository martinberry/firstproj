package com.ztravel.order.common;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ztravel.reuse.order.service.IOrderLogReuseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/ztravel-datasource.xml"})
public class InsertOrderLog {

	@Resource
	private IOrderLogReuseService orderLogReuseService;

	@Test
	public void insert(){
		try {
			orderLogReuseService.save("1505261950560901", "hua", "创建", "测试");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
