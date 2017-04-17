/**
 *
 */
package com.ztravel.member.front.service;

import java.util.List;
import java.util.Map;

import com.ztravel.reuse.member.entity.SimpleTravelerEntity;
import com.ztravel.member.po.TravelerEntity;


/**
 * @author zuoning.shen
 *
 */
public interface TravelerInfoService {

    void save(TravelerEntity traveler) throws Exception;

    void deleteById(String id) throws Exception;

    TravelerEntity getById(String id) throws Exception;

    List<SimpleTravelerEntity> getTravelerInfo(String memberId) ;

	Map<String, Object> checkRepeateName(String travelerNameCn,String memberId)	throws Exception;
}
