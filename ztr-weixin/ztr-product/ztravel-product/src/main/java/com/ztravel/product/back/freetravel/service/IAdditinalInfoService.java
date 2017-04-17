package com.ztravel.product.back.freetravel.service;

import com.ztravel.product.back.freetravel.vo.AdditionalInfoVo;

public interface IAdditinalInfoService {
	AdditionalInfoVo queryAIById(String id)throws Exception;
	void save(AdditionalInfoVo addInfo)throws Exception;
	void release(String id)throws Exception;

}
