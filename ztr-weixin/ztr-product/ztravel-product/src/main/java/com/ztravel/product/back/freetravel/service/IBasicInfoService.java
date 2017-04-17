package com.ztravel.product.back.freetravel.service;

import com.ztravel.product.back.freetravel.vo.BasicInfoVo;

public interface IBasicInfoService {
	/**
	 * 按id查询基本信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	BasicInfoVo queryById(String id)throws Exception;
	/**
	 * 保存基本信息，可以是新增或者修改
	 * @param basicInfo
	 * @return
	 * @throws Exception
	 */
	String save(BasicInfoVo basicInfo)throws Exception;
}
