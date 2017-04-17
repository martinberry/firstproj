package com.ztravel.front.member.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.ztravel.common.enums.NoticeType;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.member.po.SystemNotice;
import com.ztravel.member.front.vo.MsgRequest;
import com.ztravel.member.front.service.ISystemNoticeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class InsertSystemNoticeTest {
	@Configuration
	@Import({
		com.ztravel.front.member.config.AppConfig.class
	})
//	@ComponentScan({
//		"com.ztravel.order.dao"
//	})
	static class TempFrontConfig {}
	@Resource
	ISystemNoticeService systemNoticeServiceImpl;
	@Resource
	ISystemNoticeClientService systemNoticeServiceClientImpl;
	@Test
	public void insertTest(){
		try {
			int index =0;
			do{
				systemNoticeServiceImpl.add("00010602", NoticeType.ORDERTYPE.toString(), "hello jun" +index+ ", your <a href='https://www.baidu.com'>order</a> is completed!!!");
			}while(index++ <12);
//			systemNoticeServiceClientImpl.add("MEMB1505051054210601", NoticeType.ORDERTYPE.toString(), "hello jun, this message is inserted by client!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void countUnread(){
		try {
			System.out.println(systemNoticeServiceImpl.countUnread("00010602"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void read(){
		try {
			List<String> ids = new ArrayList<String>();
			ids.add("MEMB1505051054210601");
			System.out.println(systemNoticeServiceImpl.batchRead(ids));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void list(){
		MsgRequest request = new MsgRequest();
		request.setMemberId("00010602");
		request.setPageSize(20);
		List<SystemNotice> list = new ArrayList<SystemNotice>();
		try {
			list = (List<SystemNotice>)systemNoticeServiceImpl.list(request).getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
	}
}
