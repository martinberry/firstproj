package com.ztravel.member.opera.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.opera.converter.TravellerConvert;
import com.ztravel.member.dao.TravellerDao;
import com.ztravel.member.dto.TravellerDto;
import com.ztravel.member.opera.service.TravellerService;
import com.ztravel.member.opera.wo.TravellerResponseInfo;
import com.ztravel.member.opera.wo.TravellerResponseInfoDetail;
import com.ztravel.member.opera.wo.TravellerSearchCriteria;

@Service
public class TravellerServiceImpl implements TravellerService {

	@Resource
	private TravellerDao travellerDaoImpl;

	public List<TravellerResponseInfo> getTravellersOneMember(TravellerSearchCriteria criteria){
		
		TravellerDto dto = TravellerConvert.convertTravellerSearchCriteria2TravellerDto(criteria) ;
		
		List<TravelerEntity> list = travellerDaoImpl.queryTravellersOneMember(dto);

		List<TravellerResponseInfo> response = TravellerConvert.convertTravellerResponse(list);

		return response;

	}

	public TravellerResponseInfoDetail getTravellersDetail(String travelerId){

		List<TravelerEntity> list = travellerDaoImpl.queryTravellersDetail(travelerId);

		TravellerResponseInfoDetail response = new TravellerResponseInfoDetail();

		if(list != null && list.size() >= 1){
			response = TravellerConvert.convertTravellerDetail(list.get(0));
		}

		return response;

	}


}
