package com.ztravel.common.sensitive;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


/**
 * 
 * @author wanhaofan
 * 敏感词校验器
 */
@Component
public class SensitiveValidator {
	
	@Resource
	private Map<String, String> sensitiveWordsMap ;
	
	private int checkSensitiveWord(String txt, int beginIndex) {  
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况  
        int matchFlag = 0;     //匹配标识数默认为0  
        char word = 0;  
        Map nowMap = sensitiveWordsMap;  
        for(int i = beginIndex; i < txt.length() ; i++){  
            word = txt.charAt(i);  
            nowMap = (Map)nowMap.get(word);     //获取指定key  
            if(nowMap != null){     //存在，则判断是否为最后一个  
                matchFlag++;     //找到相应key，匹配标识+1   
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数  
                    flag = true;       //结束标志位为true    
                    break ;
                }  
            }else{     //不存在，直接返回
            	matchFlag = 0 ;
                break;  
            }  
        }  
        if(!flag){       
            matchFlag = 0;  
        }  
        
        return matchFlag;  
    }
	
	public boolean hasSensitiveWord(String txt) {  
        boolean flag = false;  
        for (int i = 0; i < txt.length(); i++) {  
  
            // 判断是否包含敏感字符  
            int matchFlag = this.checkSensitiveWord(txt, i);  
  
            // 大于0存在，返回true  
            if (matchFlag > 0) {  
                flag = true;  
            }  
        }  
        return flag;  
    }  
	
	public String replaceSensitiveWord(String txt, String replaceChar) {  
		  
        String resultTxt = txt;  
  
        // 获取所有的敏感词  
        Set<String> set = getSensitiveWord(txt);  
        Iterator<String> iterator = set.iterator();  
        String word = null;  
        String replaceString = null;  
        while (iterator.hasNext()) {  
            word = iterator.next();  
            replaceString = getReplaceChars(replaceChar, word.length());  
            resultTxt = resultTxt.replaceAll(word, replaceString);  
        }  
  
        return resultTxt;  
    }  
	
	private String getReplaceChars(String replaceChar, int length) {  
        String resultReplace = replaceChar;  
        for (int i = 1; i < length; i++) {  
            resultReplace += replaceChar;  
        }  
  
        return resultReplace;  
    }  
	
	private Set<String> getSensitiveWord(String txt) {  
        Set<String> sensitiveWordList = new HashSet<String>();  
  
        for (int i = 0; i < txt.length(); i++) {  
  
            // 判断是否包含敏感字符  
            int length = checkSensitiveWord(txt, i);  
  
            // 存在,加入list中  
            if (length > 0) {  
                sensitiveWordList.add(txt.substring(i, i + length));  
  
                // 减1的原因，是因为for会自增  
                i = i + length - 1;  
            }  
        }  
  
        return sensitiveWordList;  
    }  
	
//	public static void main(String args[]){
//		SensitiveWordsConfig config = new SensitiveWordsConfig() ;
//		sensitiveWordsMap = config.sensitiveWordsMap() ;
//		String txt = "adult video" ;
//		SensitiveValidator valid = new SensitiveValidator() ;
//		System.out.println(valid.hasSensitiveWord(txt)) ;
//	}
	
}  

