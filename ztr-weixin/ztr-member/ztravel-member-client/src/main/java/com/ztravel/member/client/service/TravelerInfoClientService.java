/**
 *
 */
package com.ztravel.member.client.service;

import java.util.List;

import com.ztravel.member.po.TravelerEntity;
import com.ztravel.reuse.member.entity.SimpleTravelerEntity;

/**
 * @author zuoning.shen
 *
 */
public interface TravelerInfoClientService {

    void save(TravelerEntity traveler) throws Exception;

    void saveTravelerForBookOrder(String destinationType,TravelerEntity  traveler) throws Exception;

    void deleteById(String id) throws Exception;

    TravelerEntity getById(String id) throws Exception;

    List<SimpleTravelerEntity> getTravelerInfo(String memberId) ;

    List<TravelerEntity> getTravelerEntityListByMemberIdAndTravelType(String memberId,String travelType) throws Exception;


}
