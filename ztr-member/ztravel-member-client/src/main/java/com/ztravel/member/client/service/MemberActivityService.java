package com.ztravel.member.client.service;

import com.ztravel.member.po.MemberActivityEntity;



public interface MemberActivityService {
	void insert(MemberActivityEntity entity) ;

	int count(String memberId,String activityId,String couponId) ;
}
