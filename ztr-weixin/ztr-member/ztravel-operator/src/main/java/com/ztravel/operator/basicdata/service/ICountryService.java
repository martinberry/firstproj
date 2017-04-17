package com.ztravel.operator.basicdata.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 国家数据(国籍)
 * @author MH
 */
public interface ICountryService {
	/**
	 * 解析国家数据excel,保存国家数据到mongo
	 * @param file
	 * @throws Exception
	 */
	public void saveCountryData(MultipartFile file) throws Exception;
	/**
	 * 删除国家数据库
	 * @throws Exception
	 */
	public void deleteCountryData() throws Exception;

}
