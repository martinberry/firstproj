/**
 *
 */
package com.ztravel.member.front.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ztravel.member.dao.TravellerDao;
import com.ztravel.member.front.service.TravelerInfoService;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.reuse.member.entity.SimpleTravelerEntity;
import com.ztravel.reuse.member.service.ITravellerInfoReuseService;

/**
 * @author zuoning.shen
 *
 */
@Service
public class TravelerInfoServiceImpl implements TravelerInfoService{

    @Resource
    private TravellerDao travellerDao;
    
    @Resource
    private ITravellerInfoReuseService travellerInfoReuseService ;

    @Override
	public List<SimpleTravelerEntity> getTravelerInfo(String memberId) {
    	return travellerInfoReuseService.getTravelerInfo(memberId) ;
	}

    @Override
    public void save(TravelerEntity traveler) throws Exception {
        if(traveler.getIsDefault()){
            travellerDao.cancelDeaultTraveler(traveler.getMemberId());
        }

        travellerDao.save(traveler);
    }

    @Override
    public void deleteById(String id) throws Exception {
    	travellerInfoReuseService.deleteById(id);
    }

    @Override
    public TravelerEntity getById(String id) throws Exception {
    	return travellerInfoReuseService.getById(id) ;
    }

	@Override
	public Map<String, Object> checkRepeateName(String travelerNameCn,String memberId) throws Exception{
		Map<String,	Object> repeateMap = Maps.newHashMap();
		Map<String,	Object> searchParams = Maps.newHashMap();
		searchParams.put("travelerNameCn", travelerNameCn);
		searchParams.put("memberId", memberId);
//		searchParams.put("isActive", true);
		String result="successed";
		String repeate="notRepeated";
		try{
			List<TravelerEntity> travelerList = travellerDao.getTravelersByParams(searchParams);
			if(travelerList.size()>0){
				repeate="repeated";
			}
		}catch(Exception e){
			result	=	"failed";
		}
		repeateMap.put("result", result);
		repeateMap.put("repeate", repeate);
		return repeateMap;
	}

}
