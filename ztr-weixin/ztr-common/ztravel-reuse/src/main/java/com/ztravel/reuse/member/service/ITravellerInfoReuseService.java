package com.ztravel.reuse.member.service;

import java.util.List;

import com.ztravel.member.po.TravelerEntity;
import com.ztravel.reuse.member.entity.SimpleTravelerEntity;

public interface ITravellerInfoReuseService {
	
	void deleteById(String id) throws Exception;
	
	TravelerEntity getById(String id) throws Exception;
	
	List<SimpleTravelerEntity> getTravelerInfo(String memberId) ;
}
