package com.ztravel.front.member.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.member.dao.IPrivateLetterDao;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.member.front.service.IPrivateLetterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class InsertPrivateLetterTest {
	@Configuration
	@Import({
		com.ztravel.front.member.config.AppConfig.class
	})
	static class TempFrontConfig {}
	@Resource
	private IPrivateLetterService privateLetterServiceImpl;
	@Resource
	private IPrivateLetterDao privateLetterDaoImpl;
	@Test
	public void insert(){
		privateLetterServiceImpl.addMsg("MEMB1506161401349009", "MEMB1506161354179007", "I am jun, and you ?");
		privateLetterServiceImpl.addMsg("MEMB1506161354179007", "MEMB1506161401349009", "说人话");

		privateLetterServiceImpl.addMsg("MEMB1506161401349009", "MEMB1506161354179007", "额，好吧．");
		privateLetterServiceImpl.addMsg("MEMB1506161354179007", "MEMB1506161401349009", "I am swimming in thw smog");

		privateLetterServiceImpl.addMsg("MEMB1506161401349009", "MEMB1506161354179007", "so do not apolegize");
		privateLetterServiceImpl.addMsg("MEMB1506161354179007", "MEMB1506161401349009", "cu");
	}
	@Test
	public void deleteOne(){
		privateLetterServiceImpl.deleteOne("557a6018e4b073336ff217af", "PMSG1434083352855");
	}

	@Test
	public void list(){
		Pagination<PrivateLetter> page = privateLetterDaoImpl.list("MEM123456", 0, 5);
		System.out.println(page.getData());
	}

}
