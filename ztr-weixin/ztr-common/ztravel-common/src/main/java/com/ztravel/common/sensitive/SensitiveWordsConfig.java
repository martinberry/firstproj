package com.ztravel.common.sensitive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;



/**
 * 
 * @author wanhaofan
 * 敏感词汇
 */
@Configuration
@ComponentScan(basePackages={"com.ztravel.common.sensitive"},
	excludeFilters={
		@Filter(type=FilterType.ANNOTATION, value=Configuration.class)
})
public class SensitiveWordsConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordsConfig.class);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public Map<String, String> sensitiveWordsMap(){
		Set<String> sensitiveWordSet = new HashSet<String>() ;
		FileReader fr = null ;
		BufferedReader br = null ;
		try {
			fr = new FileReader("/data/sensitive/keyWords.txt");
			br = new BufferedReader(fr);
			String str = null ;
			while((str=br.readLine())!=null){
				sensitiveWordSet.add(str) ;
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		
		
		// 初始化敏感词容器，减少扩容操作  
        Map wordMap = new HashMap(sensitiveWordSet.size());  
        for (String word : sensitiveWordSet) {  
            Map nowMap = wordMap;  
            for (int i = 0; i < word.length(); i++) {  
                // 转换成char型  
                char keyChar = word.charAt(i);  
                // 获取  
                Object tempMap = nowMap.get(keyChar);  
                // 如果存在该key，直接赋值  
                if (tempMap != null) {  
                    nowMap = (Map) tempMap;  
                }  
                // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个  
                else {  
                    // 设置标志位  
                    Map<String, String> newMap = new HashMap<String, String>();  
                    newMap.put("isEnd", "0");  
                    // 添加到集合  
                    nowMap.put(keyChar, newMap);  
                    nowMap = newMap;  
                }  
                // 最后一个  
                if (i == word.length() - 1) {  
                    nowMap.put("isEnd", "1");  
                }  
            }  
        }  
        return wordMap;  
	}
	
}
