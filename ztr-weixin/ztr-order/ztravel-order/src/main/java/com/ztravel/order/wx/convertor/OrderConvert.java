package com.ztravel.order.wx.convertor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class OrderConvert {

	public static Map<String,Object> getParams(String mid,Integer startNum,Integer pageSize){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("creator", mid);
		params.put("limit", pageSize);//pageSize暂定为10
		params.put("offset", getOffset(startNum,pageSize));
		return params;
	}

	public Map<String,String> getCountParams(String mid,String backState){
		Map<String,String> params = new HashMap<String,String>();
		params.put("creator", mid);
		params.put("backState", backState);
		return params;
	}

	public static Map<String,String> getDetailParams(String mid,String orderId){
		Map<String,String> params = new HashMap<String,String>();
		params.put("creator", mid);
		Assert.hasText(orderId, "订单Id为空");
		params.put("orderId", orderId);
		return params;
	}

	public static int getOffset(Integer pageNum,Integer pageSize){
		return (pageNum - 1)* pageSize > 0 ? (pageNum-1)*pageSize : 0;
	}

}
