package com.ztravel.member.client.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ztravel.member.po.MemberActivityEntity;
import com.ztravel.member.client.service.MemberActivityService;
import com.ztravel.member.dao.MemberActivityDao;

@Service
public class MemberActivityServiceImpl implements MemberActivityService{

	@Resource
	private MemberActivityDao memberActivityDao ;

	@Override
	public void insert(MemberActivityEntity entity) {
		memberActivityDao.insert(entity);
	}

	@Override
	public int count(String memberId, String activityId, String couponId) {
		Map<String, String> params = Maps.newHashMap() ;
		params.put("memberId", memberId) ;
		params.put("activityId", activityId) ;
		params.put("couponId", couponId) ;
		return memberActivityDao.count(params);
	}

}
