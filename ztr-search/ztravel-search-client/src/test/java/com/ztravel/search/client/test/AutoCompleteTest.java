package com.ztravel.search.client.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ztravel.sraech.client.config.SearchClientConfig;
import com.ztravel.sraech.client.service.IAutoComplete;


/**
 * test case
 * 1:本地调试
 *   global/ztr-search/search-server.properties
 *   ztr.search.ip=127.0.0.1  --> ztr.search.ip=192.168.164.135
 *
 *
 * @author liuzhuo
 *
 */

@ContextConfiguration(classes={
		SearchClientConfig.class
})
public class AutoCompleteTest extends AbstractJUnit4SpringContextTests{

	@Resource(name="tThriftAutoComplete")
	IAutoComplete autoComplete;

	@Test
	public void test(){

		try {
			List<String> result = autoComplete.countryAutoComplete("阿富", 5);
			for(String json : result) {
				System.out.println(json.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
