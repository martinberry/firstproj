package com.ztravel.product.back.activity.convert;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztravel.common.enums.GrantType;
import com.ztravel.product.back.activity.vo.ActivityCriteria;

public class ActivityConvert {
	public static Map<String,String> criteriaToParams(ActivityCriteria criteria){
		Map<String,String> params = new HashMap<String,String>();
		if(StringUtils.isNoneBlank(criteria.getActivityName())){
			params.put("name", criteria.getActivityName());
		}
		if(StringUtils.isNoneBlank(criteria.getCouponName())){
			params.put("couponNameString", criteria.getCouponName());
		}
		if(StringUtils.isNoneBlank(criteria.getStartTime())){
			params.put("startTime", criteria.getStartTime());
		}
		if(StringUtils.isNoneBlank(criteria.getEndTime())){
			params.put("endTime", criteria.getEndTime());
		}
		if(StringUtils.isNoneBlank(criteria.getStatus())){
			params.put("status", criteria.getStatus());
		}
		if(StringUtils.isNoneBlank(criteria.getType())){
			params.put("type", criteria.getType());
		}
		if(StringUtils.isNotBlank(criteria.getGrantType())){
			if(!criteria.getGrantType().equals(GrantType.MANUALADD.name())){//批量设置
				params.put("grantType", GrantType.BATCHCONFIG.name());
				params.put("userRangType", criteria.getGrantType().trim());
			}else{
				params.put("grantType", GrantType.MANUALADD.name());
			}
//			if(criteria.getGrantType().equals(UserRangeType.CURRENTALL.name())){
//				params.put("grantType", GrantType.BATCHCONFIG.name());
//				params.put("userRangType", UserRangeType.CURRENTALL.name());
//			}else if(criteria.getGrantType().equals(UserRangeType.NEWUSER.name())){
//				params.put("grantType", GrantType.BATCHCONFIG.name());
//				params.put("userRangType", UserRangeType.NEWUSER.name());
//			}else if(criteria.getGrantType().equals(UserRangeType.NEWANDSHARED.name())){
//				params.put("grantType", GrantType.BATCHCONFIG.name());
//				params.put("userRangType", UserRangeType.NEWUSER.name());
//			}else if(criteria.getGrantType().equals(GrantType.MANUALADD.name())){
//				params.put("grantType", GrantType.MANUALADD.name());
//			}
		}
		return params;
	}
}
