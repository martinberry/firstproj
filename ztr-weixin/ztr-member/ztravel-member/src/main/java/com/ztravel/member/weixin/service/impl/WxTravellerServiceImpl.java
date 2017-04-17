package com.ztravel.member.weixin.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.WxMembConst;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.member.dao.TravellerDao;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.weixin.service.IWxTravellerService;

/**
 * @author MH
 */
@Service
public class WxTravellerServiceImpl implements IWxTravellerService {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(WxTravellerServiceImpl.class);

	@Resource
    private TravellerDao travellerDao;

	@Override
	public void deleteTravellerById(String id) throws Exception {
		travellerDao.deleteById(id);
	}

	@Override
	public TravelerEntity getTravellerById(String id) throws Exception {
		TravelerEntity traveller = travellerDao.getById(id);
		if( traveller == null ){
			throw ZtrBizException.instance(WxMembConst.MEMB_GET_TRAVELLER_BY_ID_ERROR_CODE, WxMembConst.MEMB_GET_TRAVELLER_BY_ID_ERROR_MSG);
		}
		return traveller;
	}

	@Override
	public void updateTraveller(TravelerEntity traveller) throws Exception {
		if( StringUtils.isNotBlank(traveller.getFirstNameCn()) && StringUtils.isNotBlank(traveller.getLastNameCn()) ){
			if( isContainChineseWord(traveller.getFirstNameCn()) || isContainChineseWord(traveller.getLastNameCn()) ){
				traveller.setTravelerNameCn(traveller.getFirstNameCn() + traveller.getLastNameCn());
			}else{
				traveller.setTravelerNameCn(traveller.getFirstNameCn() + "/" + traveller.getLastNameCn());
			}
		}
		if( StringUtils.isNotBlank(traveller.getFirstEnName()) && StringUtils.isNotBlank(traveller.getLastEnName()) ){
			traveller.setTravelerNameEn(traveller.getFirstEnName() + "/" + traveller.getLastEnName());
		}
		int num = travellerDao.updateTraveller(traveller);
		if( num == 1 ){
			LOGGER.info("更新常旅客信息成功,常旅客id：" + traveller.getId().toString());
		}else if( num > 1 ){
			LOGGER.debug("更新常旅客信息异常，更新了" + num + "条记录，常旅客id：" + traveller.getId().toString());
		}else{
			LOGGER.error("更新常旅客信息失败");
		}

	}

	private boolean isContainChineseWord(String str){
		Matcher matcher = Pattern.compile("[\u4E00-\u9FA5]").matcher(str);
		if( matcher.find() )
			return true;
		else
			return false;
	}

//	private WTravellerVO convertTravellerEntity2TravellerVO(TravelerEntity traveller){
//		WTravellerVO travellerVo = new WTravellerVO();
//		travellerVo.setFirstNameCn(traveller.getFirstNameCn());
//		travellerVo.setLastNameCn(traveller.getLastNameCn());
//		travellerVo.setFirstNameEn(traveller.getFirstEnName());
//		travellerVo.setFirstNameEn(traveller.getFirstEnName());
//		travellerVo.setEmail(traveller.getEmail());
//		travellerVo.setMobile(traveller.getPhoneNum());
//		travellerVo.setGender(traveller.getGender());
//		travellerVo.setTravellerType(traveller.getTravelType());
//		travellerVo.setNationality(traveller.getNationality());
//		travellerVo.setBirthday(traveller.getBirthday());
//		travellerVo.setProvince(traveller.getProvince());
//		travellerVo.setCity(traveller.getCity());
//		travellerVo.setDistrict(traveller.getDistrict());
//		travellerVo.setDetailAddress(travellerVo.getDetailAddress());
//		return travellerVo;
//	}

}
