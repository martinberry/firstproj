package com.ztravel.member.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.member.dto.TravellerDto;
import com.ztravel.member.po.TravelerEntity;

/**
 *
 * @author zuoning.shen
 */
public interface TravellerDao{
	public String save(TravelerEntity traveler);

	public void deleteById(String id);

	public TravelerEntity getById(String id);

	public TravelerEntity getDeaultTraveler(String memberId);

	public void cancelDeaultTraveler(String memberId);

	public List<TravelerEntity> findByMemberId(String memberId, int page,int pageSize);

	public List<TravelerEntity> findByMemberId(String memberId);

	public int countByMemberId(String memberId) ;

	public List<TravelerEntity> findByMemberIdAndtravelType(String memberId,String travelType);

	@SuppressWarnings("rawtypes")
	public List<TravelerEntity> getTravelersByParams(Map searchParams);

	/**
	 * 修改常旅客信息
	 * @param traveller
	 */
	public int updateTraveller(TravelerEntity traveller);
	
	/**
	 * 获取某个会员的所有常旅客
	 * @param travellerRequest
	 * @return
	 */
	public List<TravelerEntity> queryTravellersOneMember(TravellerDto travellerDto);

	/**
	 *获取常旅客详情
	 * @param travelerId
	 * @return
	 */
	public List<TravelerEntity> queryTravellersDetail(String travelerId);
}
