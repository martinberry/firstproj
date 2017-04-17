package com.ztravel.operator.basicdata.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.redis.client.RedisClient;

@Controller
@RequestMapping("/regWhiteList")
public class RegWhiltwListController {
	
private static final String REG_WHITELIST_KEY = "regWhiteListKey";
	
	private static final RedisClient redisClient = RedisClient.getInstance();

	
	@RequestMapping("/view")
	public String view(){
		return "operator/regWhiteList/view";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(String mobiles){
		
		String[] mobileArray = mobiles.split(",");
		
		Set<String> oriMobileSet = redisClient.get(REG_WHITELIST_KEY, Set.class);
		
		if(null == oriMobileSet) {
			oriMobileSet = new HashSet<>();
		}
		
		for(String mob : mobileArray){
			oriMobileSet.add(mob);
		}
		
		//有效期30天
		redisClient.set(REG_WHITELIST_KEY, oriMobileSet, 60 * 60 * 24 * 30);
		
		return "添加成功";
	}
	
}
