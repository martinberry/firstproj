package com.ztravel.common.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.site.lookup.util.StringUtils;

/**
 * 
 * @author wanhaofan
 *
 */
public class HTMLUtil {
	private static final String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	private static final String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	private static final String regEx_li="<li[^>]+>"; //li 
	private static final String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
	private static final String regEx_space="&nbsp;"; //定义HTML&nbsp;
	private static final String regEx_enter="\n"; //定义HTML换行
	private static final String regEx_tab="\t"; //定义HTML制表
	
	private static final String regEx_gt=">"; //定义>
	
	private static final String regEx_lt="<"; //定义<
	
	private static final String lt="&lt;"; //定义<
	
	private static final String gt="&gt;"; //定义<
	
	public static String removeHtmlTag(String htmlStr){
		if(StringUtils.isEmpty(htmlStr)){
			return "" ;
		}
		
		Pattern p_enter=Pattern.compile(regEx_enter,Pattern.CASE_INSENSITIVE); 
        Matcher m_enter=p_enter.matcher(htmlStr); 
        htmlStr=m_enter.replaceAll(""); //过滤enter标签 
        
        Pattern p_tab=Pattern.compile(regEx_tab,Pattern.CASE_INSENSITIVE); 
        Matcher m_tab=p_tab.matcher(htmlStr); 
        htmlStr=m_tab.replaceAll(""); //过滤tab标签 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
        
        Pattern p_li=Pattern.compile(regEx_li,Pattern.CASE_INSENSITIVE); 
        Matcher m_li=p_li.matcher(htmlStr); 
        htmlStr=m_li.replaceAll("\n"); //过滤html标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
        
        Pattern p_space=Pattern.compile(regEx_space,Pattern.CASE_INSENSITIVE); 
        Matcher m_space=p_space.matcher(htmlStr); 
        htmlStr=m_space.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
	}
	
	public static String replaceScriptAndCssTag(String htmlStr){
		if(StringUtils.isEmpty(htmlStr)){
			return "" ;
		}
		
        Pattern p_script=Pattern.compile(regEx_gt,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(gt); //过滤>标签 
         
        Pattern p_style=Pattern.compile(regEx_lt,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(lt); //过滤<标签 
        
        return htmlStr.trim(); //返回文本字符串 
	}
	
}
