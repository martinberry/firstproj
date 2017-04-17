package com.ztravel.member.opera.converter;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.po.TravelerEntity.Credentials;
import com.ztravel.member.dto.TravellerDto;
import com.ztravel.member.opera.wo.TravellerResponseInfo;
import com.ztravel.member.opera.wo.TravellerResponseInfoDetail;
import com.ztravel.member.opera.wo.TravellerSearchCriteria;

public class TravellerConvert {

	public static List<TravellerResponseInfo> convertTravellerResponse(List<TravelerEntity> entityList){

		List<TravellerResponseInfo> infoList = new ArrayList<>();

		if(entityList != null){
			for(TravelerEntity entity : entityList){
				TravellerResponseInfo info = new TravellerResponseInfo();
				info.setTravelerId(entity.getId().toString());
				info.setMemberId(entity.getMemberId());
				info.setTravelerNameCn(entity.getTravelerNameCn());

				if(StringUtils.isNotBlank(entity.getFirstEnName()) && StringUtils.isNotBlank(entity.getLastEnName())){
					info.setTravelerNameEn(entity.getFirstEnName() + " " + entity.getLastEnName());
				}

				info.setTravelType(entity.getTravelType());
				info.setPhoneNum(entity.getPhoneNum());
				info.setGender(entity.getGender());

				List<Credentials> credentials = entity.getCredentials();
				info.setCredentials(sort(credentials));
				infoList.add(info);
			}
		}

		return infoList;

	}

	private static List<Credentials> sort(List<Credentials> credentials){

		List<Credentials> credentialsNew = new ArrayList<>();
		if(credentials != null && credentials.size() >= 2){
			for(Credentials credential : credentials){
				if(credential.getType().equals("IDCARD")){
					credentialsNew.add(credential);
				}
			}
			for(Credentials credential : credentials){
				if(credential.getType().equals("PASSPORT")){
					credentialsNew.add(credential);
				}
			}
			for(Credentials credential : credentials){
				if(credential.getType().equals("GANGAOPASS")){
					credentialsNew.add(credential);
				}
			}
		}else{
			credentialsNew = credentials;
		}
		return credentialsNew;
	}

	public static TravellerResponseInfoDetail convertTravellerDetail(TravelerEntity entity){

		TravellerResponseInfoDetail detail = new TravellerResponseInfoDetail();

		if(entity != null){

			detail.setTravelerId(entity.getId().toString());
			detail.setMemberId(entity.getMemberId());

			if(StringUtils.isNotBlank(entity.getFirstEnName()) && StringUtils.isNotBlank(entity.getLastEnName())){
				detail.setTravelerNameEn(entity.getFirstEnName() + " " + entity.getLastEnName());
			}

			detail.setTravelerNameCn(entity.getTravelerNameCn());
			detail.setTravelType(entity.getTravelType());
			detail.setPhoneNum(entity.getPhoneNum());
			detail.setGender(entity.getGender());
			detail.setCredentials(entity.getCredentials());
			detail.setBirthday(entity.getBirthday());


			detail.setAddress(getAddressByTraveler(entity));
			detail.setEmail(entity.getEmail());
			detail.setNationality(entity.getNationality());
		}

		return detail;

	}

	private  static String  getAddressByTraveler(TravelerEntity traveler){
		String address="";

		if(StringUtils.isNotBlank(traveler.getProvince())){
			address +=	traveler.getProvince()+" ";
		}
		if(StringUtils.isNotBlank(traveler.getCity())){
			address +=	traveler.getCity()+" ";
		}
		if(StringUtils.isNotBlank(traveler.getDistrict())){
			address +=	traveler.getDistrict()+" ";
		}
		if(StringUtils.isNotBlank(traveler.getDetailAddress())){
			address +=	traveler.getDetailAddress();
		}

		return address.trim();
	}
	
	public static TravellerDto convertTravellerSearchCriteria2TravellerDto(TravellerSearchCriteria criteria){
		TravellerDto dto = new TravellerDto() ;
		dto.setMemberId(criteria.getMemberId());
		dto.setPhoneNum(criteria.getPhoneNum());
		dto.setTravelerNameCn(criteria.getTravelerNameCn());
		dto.setTravelerNameEn(criteria.getTravelerNameEn());
		return dto ;
	}

}
