package com.ztravel.operator.basicdata.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author MH
 */
public interface INicknameLibService {
	/**
	 * 删除昵称库
	 * @throws Exception
	 */
	public void deleteNicknameLib() throws Exception;
	/**
	 * 解析昵称AB库excel,生成昵称库保存到mongo
	 * @param file
	 * @throws Exception
	 */
	public void saveNicknameLib(MultipartFile file) throws Exception;

}
