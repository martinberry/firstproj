package com.ztravel.common.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * C端出发地映射关系
 * @author MH
 */
public class DepartureMapperUtil {
	private static Map<String, Set<String>> mapper = new HashMap<String, Set<String>>();
	static{
		Set<String> regionSet1 = new HashSet<String>();
		regionSet1.add("上海市");
		regionSet1.add("安徽省");
		regionSet1.add("江苏省");
		regionSet1.add("浙江省");
		regionSet1.add("湖北省");
		mapper.put("上海", regionSet1);

		Set<String> regionSet2 = new HashSet<String>();
		regionSet2.add("北京市");
		regionSet2.add("天津市");
		regionSet2.add("河北省");
		regionSet2.add("河南省");
		regionSet2.add("辽宁省");
		regionSet2.add("黑龙江省");
		regionSet2.add("山东省");
		regionSet2.add("新疆维吾尔自治区");
		regionSet2.add("甘肃省");
		regionSet2.add("山西省");
		regionSet2.add("内蒙古自治区");
		regionSet2.add("陕西省");
		regionSet2.add("吉林省");
		regionSet2.add("宁夏回族自治区");
		mapper.put("北京", regionSet2);

		Set<String> regionSet3 = new HashSet<String>();
		regionSet3.add("湖南省");
		regionSet3.add("广西壮族自治区");
		regionSet3.add("福建省");
		regionSet3.add("江西省");
		regionSet3.add("广东省");
		regionSet3.add("海南省");
		regionSet3.add("台湾省");
		regionSet3.add("香港特别行政区");
		regionSet3.add("澳门特别行政区");
		mapper.put("广州", regionSet3);

		Set<String> regionSet4 = new HashSet<String>();
		regionSet4.add("重庆市");
		regionSet4.add("云南省");
		regionSet4.add("青海省");
		regionSet4.add("西藏自治区");
		regionSet4.add("四川省");
		regionSet4.add("贵州省");
		mapper.put("成都", regionSet4);
	}

	public static String getDeparturePlace(String region){
		String place = "上海";   //默认设置“上海”
		for(String key : mapper.keySet()){
			if( mapper.get(key).contains(region) ){
				place = key;
				break;
			}
		}
		return place;
	}

}
