/**
 *
 */
package com.ztravel.member.client.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.constants.Const;
import com.ztravel.common.enums.CredentialType;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.TravelerInfoClientService;
import com.ztravel.member.dao.TravellerDao;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.po.TravelerEntity.Credentials;
import com.ztravel.reuse.member.entity.SimpleTravelerEntity;
import com.ztravel.reuse.member.service.ITravellerInfoReuseService;


/**
 * @author zuoning.shen
 *
 */
@Service
public class TravelerInfoClientServiceImpl implements TravelerInfoClientService{
	private static final Logger logger = LoggerFactory.getLogger(TravelerInfoClientService.class);

    @Resource
    private TravellerDao travellerDao;

    @Resource
    private IMemberClientService memberClientService;
    
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
        if(StringUtils.isEmpty(traveler.getNationality())){
        	traveler.setNationality(Const.DEFAULT_COUNTRY);
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
	public List<TravelerEntity> getTravelerEntityListByMemberIdAndTravelType(String memberId,String travelType) throws Exception{
		List<TravelerEntity> travelers = travellerDao.findByMemberIdAndtravelType(memberId, travelType);
		logger.info("before putDefaultTraveler travelers size is: "+travelers.size());
		putDefaultTravelerAtFirst(travelers);
		if(travelType.equals("international")){
			removeIdCreditType(travelers);
		}
		logger.info("travelers size: "+travelers.size());
		return travelers;
	}

	private void removeIdCreditType(List<TravelerEntity> travelers) {
		List<Credentials> credentials = Lists.newArrayList();
		for(TravelerEntity traveler: travelers){
			credentials = traveler.getCredentials();
			for(Credentials credential : credentials){
				if(credential.getType().equals(CredentialType.IDCARD)){
					credentials.remove(credential);
				}
			}
			traveler.setCredentials(credentials);
		}

	}

	private void  putDefaultTravelerAtFirst(List<TravelerEntity> travelers){
		if(null != travelers && travelers.size()>1){

			for(int i=0;	i<travelers.size();	i++){
				if(travelers.get(i).getIsDefault()){
					TravelerEntity defaultTraveler = travelers.get(i);
					travelers.set(i, travelers.get(0));
					travelers.set(0, defaultTraveler);
				}
			}
		}
	}

	@Override
	public void saveTravelerForBookOrder(String destinationType,	TravelerEntity travelerOfBookOrder) throws Exception {
		logger.info("订单预定保存常旅客开始==destinationType: "+destinationType+"==travelerOfBookOrder:=="+TZBeanUtils.describe(travelerOfBookOrder));
		String memberId = getMemberIdByTravelerOfBookOrder(travelerOfBookOrder);

		TravelerEntity traveler = getTravelerByDestinationType(destinationType,	travelerOfBookOrder);
		if(null	==	traveler){
			if(StringUtils.isEmpty(travelerOfBookOrder.getMemberId())){
				travelerOfBookOrder.setMemberId(memberId);
			}

	        if(StringUtils.isEmpty(travelerOfBookOrder.getNationality())){
	        	travelerOfBookOrder.setNationality(Const.DEFAULT_COUNTRY);
	        }

			travellerDao.save(travelerOfBookOrder);
		}
		if(null != traveler){
			setTravelerByTravelerOfBookOrder(travelerOfBookOrder,	traveler);
			traveler.setMemberId(memberId);

		    if(StringUtils.isEmpty(traveler.getNationality())){
	        	traveler.setNationality(Const.DEFAULT_COUNTRY);
	        }

			travellerDao.save(traveler);
		}

		logger.info("===订单预定保存常旅客结束===");
	}

	private String getMemberIdByTravelerOfBookOrder(TravelerEntity travelerOfBookOrder) throws Exception{
		String memberId = travelerOfBookOrder.getMemberId();
		if(StringUtils.isEmpty(travelerOfBookOrder.getMemberId())){
			if(StringUtils.isNotEmpty(TokenHolder.get())){
				memberId = SSOUtil.getMemberSessionBean().getMemberId();
				travelerOfBookOrder.setMemberId(memberId);
			}
		}
		return memberId;
	}


	public TravelerEntity setTravelerByTravelerOfBookOrder(TravelerEntity travelerOfBookOrder,	TravelerEntity traveler){

			if(StringUtils.isNotEmpty(travelerOfBookOrder.getFirstEnName())){
				traveler.setFirstEnName(travelerOfBookOrder.getFirstEnName());
			}
			if(StringUtils.isNotEmpty(travelerOfBookOrder.getLastEnName())){
				traveler.setLastEnName(travelerOfBookOrder.getLastEnName());
			}
			if(StringUtils.isNotEmpty(travelerOfBookOrder.getTravelerNameCn())){
				traveler.setTravelerNameCn(travelerOfBookOrder.getTravelerNameCn());
			}
			if(StringUtils.isNotEmpty(travelerOfBookOrder.getTravelType())){
				traveler.setTravelType(travelerOfBookOrder.getTravelType());
			}
			if(StringUtils.isNotEmpty(travelerOfBookOrder.getNationality())){
				traveler.setNationality(travelerOfBookOrder.getNationality());
			}
			if(StringUtils.isNotEmpty(travelerOfBookOrder.getBirthday())){
				traveler.setBirthday(travelerOfBookOrder.getBirthday());
			}
			if(StringUtils.isNotEmpty(travelerOfBookOrder.getGender())){
				traveler.setGender(travelerOfBookOrder.getGender());
			}
			traveler.setIsActive(travelerOfBookOrder.getIsActive());

			setTravelerCredentials(travelerOfBookOrder,traveler);
			return traveler;
	}


	public void setTravelerCredentials(TravelerEntity travelerOfBookOrder,	TravelerEntity traveler){
		List<Credentials> credentials	=	traveler.getCredentials();
		Credentials credentialOfBookOrder =	getCredential(travelerOfBookOrder);
		if(null !=	credentialOfBookOrder){
			String credentialType =	credentialOfBookOrder.getType();
			for(Credentials credential :credentials){
				if(credentialType.equals(credential.getType())){
					if(StringUtils.isEmpty(credentialOfBookOrder.getDeadLineDay())){
						credentialOfBookOrder.setDeadLineDay(credential.getDeadLineDay());
					}
					credentials.remove(credential);
					break;
				}
			}
		}
		credentials.add(credentialOfBookOrder);
	}


	public Credentials getCredential(TravelerEntity traveler){
		Credentials credentials	=	new Credentials();
		List<Credentials> credentialsOfBookOrder =	traveler.getCredentials();
		if(credentialsOfBookOrder.size()>0){
			credentials	=	credentialsOfBookOrder.get(0);
			return credentials;
		}
		return null;
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TravelerEntity 	getTravelerByDestinationType(String destionationType,	TravelerEntity travelerEntity){
		TravelerEntity traveler =  new TravelerEntity();
		Map searchParams = Maps.newHashMap();
		List<TravelerEntity> travelers = Lists.newArrayList();
		searchParams.put("firstNameCn", travelerEntity.getFirstNameCn());
		searchParams.put("lastNameCn", travelerEntity.getLastNameCn());
		if(StringUtils.isNotEmpty(travelerEntity.getMemberId())){
			searchParams.put("memberId", travelerEntity.getMemberId());
		}

		travelers	=	travellerDao.getTravelersByParams(searchParams);
		if(travelers.size()>0){
			traveler	=	travelers.get(0);
			return traveler;
		}
		return null;
	}

}
