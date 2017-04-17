package com.ztravel.member.opera.service;


import java.util.List;

import com.ztravel.member.opera.wo.TravellerResponseInfo;
import com.ztravel.member.opera.wo.TravellerResponseInfoDetail;
import com.ztravel.member.opera.wo.TravellerSearchCriteria;

public interface TravellerService {

	/**
	 * 获取某个会员的所有常旅客
	 * @param searchCriteria
	 * @param criteria
	 * @param responseWapper
	 */
	public List<TravellerResponseInfo> getTravellersOneMember(TravellerSearchCriteria criteria);

	/**
	 * 获取常旅客详情
	 * @param travelerId
	 * @return
	 */
	public TravellerResponseInfoDetail getTravellersDetail(String travelerId);


}
