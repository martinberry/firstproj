package com.ztravel.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.entity.MemberSessionBean;

@Controller
@RequestMapping("/redis")
public class RedisController {
	
	private static final RedisClient client = RedisClient.getInstance();
	
	@RequestMapping("/search/{key}")
	@ResponseBody
	public String getValueFromRedis(@PathVariable String key) {
		return JSON.toJSONString(client.get(key));
	}
	
	@RequestMapping("/mem/{originToken}/{currentToken}")
	@ResponseBody
	public String copyMemSession(@PathVariable String originKey, @PathVariable String currentKey) {
		
		MemberSessionBean msb = client.get(originKey, MemberSessionBean.class);
		client.set(currentKey, msb, 30*60);
		return "copy success";
	}
	
	
	
	/**
	 * redis操作key-value，expire设置为-1，则永不超时 单位 second
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	@RequestMapping("/save/{key}/{value}/{expire}")
	@ResponseBody
	public String saveKeyToRedis(@PathVariable String key,@PathVariable String value,@PathVariable Integer expire) {
		try{
			if(expire == -1) {
				client.set(key, value);
				client.persist(key);
			}else{
				client.set(key, value, expire);			
			}
		}catch(Exception e){
			return "save error";
		}
		return " save succcess";
	}
	
	@RequestMapping("/delete/{key}")
	@ResponseBody
	public String deleteKeyFromRedis(@PathVariable String key) {
		try{
			client.delete(key);
		}catch(Exception e){
			return "delete error";
		}
		return " delete succcess";
	}

}
