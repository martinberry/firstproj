package com.ztravel.common.util;

import java.util.Properties;

import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.ztravel.common.enums.SEOEnums;

/**
 * 
 * @author wanhaofan
 * @return title, desc, keywords
 */
public class SEOUtil {

	private static Properties props = TopsConfReader.getConfProperties("properties/seo.properties", ConfScope.R) ;
	
	class SEOGetter implements Runnable{

		@Override
		public void run() {
			try{
				Thread.sleep(7200 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(true){
				props = TopsConfReader.getConfProperties("properties/seo.properties", ConfScope.R) ;
				try{
					Thread.sleep(7200 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	static {
		SEOUtil util = new SEOUtil() ;
		Thread thread = new Thread(util.new SEOGetter(), "Thread-SEO") ;
		thread.start();
	}
	
	public static String getProductListSEO(String from, String to, SEOEnums type){
		String key = props.getProperty(from) + "2" + props.getProperty(to) + type.getValue() ;
		return props.getProperty(key) ;
	}
	
}
