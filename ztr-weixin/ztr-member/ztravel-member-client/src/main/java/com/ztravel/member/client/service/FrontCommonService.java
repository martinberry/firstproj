package com.ztravel.member.client.service;

import javax.servlet.http.HttpServletRequest;

import com.ztravel.reuse.member.entity.SearchModuleVo;
import com.ztravel.reuse.member.entity.WorkPlatFormVo;

/**
 * @author wanhaofan
 * */
public interface FrontCommonService {

	SearchModuleVo getSearchModuleVo(String key) ;

	WorkPlatFormVo getWorkPlatFormVo(HttpServletRequest request) ;

	String getTicket() ;

    String getUnbindTicket() ;

    String getBindTicket() ;

	Boolean isWxLogined(String memberId);

}
