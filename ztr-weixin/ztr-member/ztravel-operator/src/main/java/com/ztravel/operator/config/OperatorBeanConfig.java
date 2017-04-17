package com.ztravel.operator.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;

@Configuration
@ComponentScan({"com.ztravel.operator.controller",
	                             "com.ztravel.operator.filter",
	                             "com.ztravel.operator.service",
	                             "com.ztravel.operator.dao",
	                             "com.ztravel.operator.basicdata.controller",
	                             "com.ztravel.operator.basicdata.service",
	                             "com.ztravel.operator.basicdata.dao",
	                             "com.ztravel.operator.electronicWallet",
	                             "com.ztravel.operator.financeMantain"
	                             })
public class OperatorBeanConfig {
	/**
	 * 胡志明	美奈				
福冈	别府	熊本			
曼谷	大城				
普吉	皮皮岛				
洛杉矶	圣芭芭拉	旧金山	弗雷斯诺	拉斯维加斯	
巴塞罗那	塞维利亚	格拉纳达	昆卡	托雷多	马德里
里斯本	拉各斯				
雷克雅未克	  雷克霍特	阿克雷利	胡萨维克	赫本	维克
迈阿密	奥乔里奥斯 	开曼群岛	科苏梅尔	拿骚	新加坡
	 */
	@Bean
	public Map<String, String> cityPySx(){
		Map<String, String> map = Maps.newHashMap() ;
		map.put("胡志明", "hzm") ;map.put("雷克雅未克", "lkywk") ;
		map.put("美奈", "mn") ;map.put("雷克霍特", "lkht") ;
		map.put("福冈", "fg") ;map.put("阿克雷利", "akll") ;
		map.put("别府", "bf") ;map.put("胡萨维克", "hswk") ;
		map.put("熊本", "xb") ;map.put("赫本", "hb") ;
		map.put("曼谷", "mg") ;map.put("维克", "wk") ;
		map.put("大城", "dc") ;map.put("迈阿密", "mam") ;
		map.put("普吉", "pj") ;map.put("奥乔里奥斯", "aqlas") ;
		map.put("皮皮岛", "ppd") ;map.put("开曼群岛", "kmqd") ;
		map.put("洛杉矶", "lsj") ;map.put("科苏梅尔", "ksme") ;
		map.put("圣芭芭拉", "sbbl") ;map.put("拿骚", "ns") ;
		map.put("旧金山", "jjs") ;
		map.put("弗雷斯诺", "flsn") ;
		map.put("拉斯维加斯", "lswjs") ;
		map.put("巴塞罗那", "bsln") ;
		map.put("塞维利亚", "swly") ;
		map.put("格拉纳达", "glnd") ;
		map.put("新加坡", "xjp") ;
		map.put("昆卡", "kk") ;map.put("托雷多", "tld") ;map.put("马德里", "mdl") ;map.put("里斯本", "lsb") ;map.put("拉各斯", "lgs") ;
		
		return map ;
	}

}
