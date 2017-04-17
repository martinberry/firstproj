package com.ztravel.common.areasearch.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztravel.common.areasearch.entity.ProvCityArea;
import com.ztravel.common.areasearch.service.IAddressService;



@Controller
@RequestMapping("/autoAddress")
public class AutoAddressController {
	@Resource
	private IAddressService addressServiceImpl;

	@RequestMapping("/load")
	@ResponseBody
	public List<ProvCityArea> load(String topNo/*可为空*/){
		List<ProvCityArea> subAddress = addressServiceImpl.getSubAddress(topNo);
		return subAddress;
	}
	/**
	 * 非member项目不可调用该方法
	 * @param topNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadTemp")
	public String loadTemp(@RequestBody String topNo, Model model){
		List<ProvCityArea> secLevelRegion = addressServiceImpl.getSubAddress(topNo);
		model.addAttribute("cityList", secLevelRegion);
		return "member/opera/memberManage/memberMaintain/cityDropDownMenu";
	}

	@RequestMapping("/loadByNL")
	@ResponseBody
	public List<ProvCityArea> loadByNL(String parentAreaName,String areaLevel){
		List<ProvCityArea> subAddress = addressServiceImpl.getAddressByNameAndLevel(parentAreaName, areaLevel);
		return subAddress;
	}
}
