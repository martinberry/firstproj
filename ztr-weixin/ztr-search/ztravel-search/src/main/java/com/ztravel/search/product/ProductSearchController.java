package com.ztravel.search.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/index")
public class ProductSearchController {
	
	
	@Resource
	IndexService indexService;
	
	@RequestMapping("/view")
	public String view() {
		return "productSearhView";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String write(JSONObject product) {
		
		try {
			indexService.writeIndex(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping("/search")
	@ResponseBody	
	public String search(JSONObject searchParams) {
		
		
		List<JSONObject> result = null;
		
		try {
			result = indexService.readIndex(searchParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
