package com.ztravel.reuse.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.member.dao.TravellerDao;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.reuse.member.entity.SimpleTravelerEntity;
import com.ztravel.reuse.member.service.ITravellerInfoReuseService;


@Service
public class TravellerInfoReuseService implements ITravellerInfoReuseService{
	
	@Resource
    private TravellerDao travellerDao;
	
	@Override
	public void deleteById(String id) throws Exception {
		travellerDao.deleteById(id) ;
	}

	@Override
	public TravelerEntity getById(String id) throws Exception {
		return travellerDao.getById(id);
	}

	@Override
	public List<SimpleTravelerEntity> getTravelerInfo(String memberId) {
		List<TravelerEntity> travelerList = travellerDao.findByMemberId(memberId);
        List<SimpleTravelerEntity> simpleTravelerList = new ArrayList<SimpleTravelerEntity>();
        for(TravelerEntity e: travelerList){
            SimpleTravelerEntity simple = new SimpleTravelerEntity();
            simple.setTravelerId(e.getId().toString());
            simple.setTravelerNameCn(e.getTravelerNameCn());
            simple.setTravelerNameEn(e.getTravelerNameEn());
            simple.setIsDefault(String.valueOf(e.getIsDefault()));
            simple.setPhoneNum(e.getPhoneNum());
            simpleTravelerList.add(simple);
        }
		return simpleTravelerList;
	}
	
	

}
