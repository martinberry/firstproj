package com.ztravel.product.back.activity.validate;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.product.back.activity.vo.ActivityVo;

public class ActivityValidate {

	public static AjaxResponse activityValidate(ActivityVo activityVo,String operation){
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.ACTIVITY_VALIDATE_FAILED,"");
		if(operation.equals(ActivityStatus.EFFECTIVE.name())){
			if(StringUtils.isNotBlank(activityVo.getEndTime()) && StringUtils.isNotBlank(activityVo.getEndHourTo()) && StringUtils.isNoneBlank(activityVo.getEndMinTo())){
				long endTime = DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, activityVo.getEndTime() + " " + activityVo.getEndHourTo() + ":" + activityVo.getEndMinTo() + ":00").getMillis();
				long timenow = new DateTime().getMillis();
				if(endTime < timenow){
					response.setRes_msg("活动结束时间小于当前时间");
					return response;
				}
			}else{
				response.setRes_msg("活动结束时间不完整");
				return response;
			}
		}
		response.setRes_code(ActivityConstants.ACTIVITY_VALIDATE_SUCCESS);
		return response;
	}
}
