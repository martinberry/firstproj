package com.ztravel.search.product;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface IndexService {
	
	public void writeIndex(JSONObject product) throws Exception;
	
	public List<JSONObject> readIndex(JSONObject searchParams) throws Exception;

}
