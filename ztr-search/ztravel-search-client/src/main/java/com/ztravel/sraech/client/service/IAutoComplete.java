package com.ztravel.sraech.client.service;

import java.util.List;


public interface IAutoComplete {
	
	/**
	 * 国家自动补全，支持 汉字 英文 三字码
	 * @param searchKey
	 * @param limit
	 * @return
	 */
	public List<String> countryAutoComplete(String searchKey, int limit); 

}
