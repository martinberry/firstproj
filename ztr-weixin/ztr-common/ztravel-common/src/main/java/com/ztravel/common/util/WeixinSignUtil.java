package com.ztravel.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.site.lookup.util.StringUtils;
import com.travelzen.framework.security.MD5;
import com.ztravel.common.holder.WechatAccountHolder;

public class WeixinSignUtil {
	
	private static Logger logger = LoggerFactory.getLogger(WeixinSignUtil.class);
	
	public static String sign(Map<String, String> params, String signType){
		String retstr = null ;
		if(params != null && !params.isEmpty()){
			StringBuffer sb = new StringBuffer() ;
			List<String> keys = new ArrayList<String>() ;
			Iterator<Entry<String, String>> iter = params.entrySet().iterator() ;
			while(iter.hasNext()){
				Entry<String, String> entry = iter.next() ;
				if(StringUtils.isNotEmpty(entry.getValue()) && StringUtils.isNotEmpty(entry.getKey())){
					keys.add(entry.getKey()) ;
				}
			}
			Object[] array = keys.toArray() ;
			Arrays.sort(array);
			for(int i=0;i<array.length;i++){
				String key = array[i].toString() ;
				String value = params.get(key) ;
				sb.append(key + "=" + value + "&") ;
			}
			sb.append("key=" + WechatAccountHolder.API_KEY) ;
			logger.info("sign source :{}", sb);
			retstr = MD5.MD5Encode(sb.toString()).toUpperCase();
			logger.info("sign result :{}", retstr);
		}
		
		return retstr ;
	}
	
	public static String sign(Map<String, String> params){
		return sign(params, "MD5") ;
	}
	
	public static boolean verify(Map<String, String> params, String checkSum){
		return sign(params, "MD5").equals(checkSum) ;
	}
	
	
	public static void main(String args[]){
		Integer timstamp = Integer.parseInt((new Date().getTime()/1000) + "") ;
		System.out.println(timstamp) ;
		timstamp = 1439837680 ;
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "") ;
		nonceStr = "2ed8aaede22e448d8a461a43aed5dd32" ;
		String pkg = "prepay_id=wx201508180254378d930f12390109530624" ;
		String appId = WechatAccountHolder.APP_ID ;
		String signType = "MD5" ;
		System.out.println(nonceStr) ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("appId", "wxe9ed2ca46ee5bd4d") ;
		params.put("timeStamp", "1439837680") ;
		params.put("nonceStr", "2ed8aaede22e448d8a461a43aed5dd32") ;
		params.put("signType", "MD5") ;
		params.put("package", "prepay_id=wx201508180254378d930f12390109530624") ;
		String paySign = sign(params) ;
		System.out.println(paySign) ;
	}
}
