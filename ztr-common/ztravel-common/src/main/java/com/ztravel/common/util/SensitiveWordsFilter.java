package com.ztravel.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 敏感词汇过滤
 * @see http://www.tuicool.com/articles/BNjemmR
 * @see https://github.com/observerss/textfilter
 * @author liuzhuo
 *
 */
public class SensitiveWordsFilter {
	
	  /**
	   * 日志
	   */
	  private static final Logger LOG = LoggerFactory.getLogger(SensitiveWordsFilter.class);
	  
	  /**
	   * 默认编码格式
	   */
	  private static final String ENCODING = "utf-8";
	  
	  /**
	   * 敏感词库的路径
	   */
	  private static final InputStream in = SensitiveWordsFilter.class.getClassLoader().getResourceAsStream("sensitive/keyWords.txt");
	 
	  /**
	   * 脏字库
	   */
	  private static Set<Character> sensitiveCharSet = null;
	 
	  /**
	   * 敏感词库
	   */
	  private static Set<String> sensitiveWordSet = null;
	  
	  /**
	   * 是否初始化
	   */
	  private static volatile boolean isInited = false;
	  
	  /**
	   * 初始化敏感词库
	   */
	  private static void init() {
		  
		  if(!isInited) {
		    //初始化容器
		    sensitiveCharSet = new HashSet<>();
		    sensitiveWordSet = new HashSet<>();
		    //读取文件 创建敏感词库
		    readSensitiveWords();		
		    isInited = true;
		  }
		
	  }
	  /**
	   * 读取本地的敏感词文件
	   * @return
	   */
	  private static void readSensitiveWords() {
	    BufferedReader reader = null;
	    try {
	      reader = new BufferedReader(new InputStreamReader(in, ENCODING));
	      String line;
	      while ((line = reader.readLine()) != null) {
	        String word = line.trim();
	        sensitiveWordSet.add(word);
	        for (Character c : word.toCharArray()) {
	          sensitiveCharSet.add(c);
	        }
	      }
	    } catch (UnsupportedEncodingException e) {
	      LOG.error("敏感词库文件转码失败!");
	    } catch (FileNotFoundException e) {
	      LOG.error("敏感词库文件不存在!");
	    } catch (IOException e) {
	      LOG.error("敏感词库文件读取失败!");
	    } finally {
	      if (reader != null) {
	        try {
	          reader.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	        reader = null;
	      }
	    }
	    return;
	  }
	  /**
	   * 检查敏感词
	   * @return
	   */
	  private static List<String> checkSensitiveWord(String text) {
	    if (sensitiveWordSet == null || sensitiveCharSet == null) {
	      init();
	    }
	    List<String> sensitiveWords = new ArrayList<>();
	    for (int i = 0; i < text.length(); i++) {
	      Character word = text.charAt(i);
	      if (!sensitiveCharSet.contains(word)) {
	        continue;
	      }
	      int j = i;
	      while (j < text.length()) {
	        if (!sensitiveCharSet.contains(word)) {
	          break;
	        }
	        String key = text.substring(i, j + 1);
	        if (sensitiveWordSet.contains(key)) {
	          sensitiveWords.add(key);
	        }
	        j++;
	      }
	    }
	    return sensitiveWords;
	  }
	  
	  public static void main(String[] args) {
		  System.out.println("start check" + System.currentTimeMillis());
		  String text = "万恶的资本主义，毛主席万岁,回民吃猪肉";
		  List<String> result = checkSensitiveWord(text);
		  for(String str : result ) {
			  System.out.println(str);
		  }
		  System.out.println("end check" + System.currentTimeMillis());
	  }

}
