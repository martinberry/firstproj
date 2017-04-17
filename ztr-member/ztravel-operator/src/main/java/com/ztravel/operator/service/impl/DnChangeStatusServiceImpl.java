package com.ztravel.operator.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.operator.service.IDnChangeStatusService;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.service.IActivityClientService;
import com.ztravel.product.dao.IVoucherDao;

@Service
public class DnChangeStatusServiceImpl implements IDnChangeStatusService{
	@Resource
	private IVoucherDao voucherdao;
	@Resource
	private IActivityClientService ActivityClientServiceImpl;

@Override
public AjaxResponse changeVoucherTypeInit(String voucherCode,String activityId) throws Exception{
	AjaxResponse ajaxResponse =  AjaxResponse.instance("","");

	DateTime endtime=ActivityClientServiceImpl.getActivityEndTimeById(activityId);
	if(endtime.getMillis() < new Date().getTime()){
		ajaxResponse.setRes_code(ActivityConstants.DN_ACTIVITY_END);
		ajaxResponse.setRes_msg(ActivityConstants.DN_ACTIVITY_END_SUCCESS_MSG);
		return ajaxResponse;
	}

	ajaxResponse.setRes_code(ActivityConstants.CHANEGE_VOUCHERTYPE_FAILED);
	ajaxResponse.setRes_msg(ActivityConstants.CHANEGE_VOUCHERTYPE_FAILED_MSG);
	Map<String,String> map=new HashMap<String,String>();
	map.put("voucherCode", voucherCode);
   Map<String, Object> changeVoucherTypeMap = Maps.newHashMap();
	   List<Voucher> voucherlist=voucherdao.select(map);
		Voucher vouchertmp=voucherlist.get(0);
		VoucherType currentVoucherType =vouchertmp.getVoucherType();
	if(currentVoucherType.equals(VoucherType.NORMAL)){
		changeVoucherTypeMap.put("voucherType",VoucherType.SYSTEM);
		changeVoucherTypeMap.put("voucherCode",voucherCode );
		changeVoucherTypeMap.put("updated", DateTime.now());

		int flag=voucherdao.updateByMap(changeVoucherTypeMap);
		if(flag==1){
			ajaxResponse.setRes_code(ActivityConstants.CHANEGE_VOUCHERTYPE_SUCCESS);
			ajaxResponse.setRes_msg(ActivityConstants.CHANEGE_VOUCHERTYPE_SUCCESS_MSG);
		}
	}
	else{
		changeVoucherTypeMap.put("voucherType",VoucherType.NORMAL);
		changeVoucherTypeMap.put("voucherCode", voucherCode);
		changeVoucherTypeMap.put("updated", DateTime.now());
	   ImmutableList<VoucherStatus> statusList = ImmutableList.of(VoucherStatus.INIT);
	   changeVoucherTypeMap.put("statusList", statusList);
		int flag=voucherdao.updateByMap(changeVoucherTypeMap);
		if(flag==1){
			ajaxResponse.setRes_code(ActivityConstants.CHANEGE_VOUCHERTYPE_SUCCESS);
			ajaxResponse.setRes_msg(ActivityConstants.CHANEGE_VOUCHERTYPE_SUCCESS_MSG);
		}
	}
return ajaxResponse;
}



@Override
public AjaxResponse changeVoucherTypeAll(String activityId, String couponId, VoucherType voucherType) throws Exception{
	AjaxResponse ajaxResponse =  AjaxResponse.instance("","");
	DateTime endtime=ActivityClientServiceImpl.getActivityEndTimeById(activityId);
	if(endtime.getMillis() < new Date().getTime()){
		ajaxResponse.setRes_code(ActivityConstants.DN_ACTIVITY_END);
		ajaxResponse.setRes_msg(ActivityConstants.DN_ACTIVITY_END_SUCCESS_MSG);
		return ajaxResponse;
	}
	ajaxResponse.setRes_code(ActivityConstants.CHANEGE_VOUCHERTYPE_FAILED);
	ajaxResponse.setRes_msg(ActivityConstants.CHANEGE_VOUCHERTYPE_FAILED_MSG);
   Map<String,Object> lockAllMap = Maps.newHashMap();
   lockAllMap.put("voucherType",voucherType.toString());
   ImmutableList<VoucherStatus> statusList = ImmutableList.of(VoucherStatus.INIT);
   lockAllMap.put("statusList", statusList);
   lockAllMap.put("couponId", couponId);
   lockAllMap.put("updated", DateTime.now());


   voucherdao.updateByMap(lockAllMap);
	ajaxResponse.setRes_code(ActivityConstants.CHANEGE_VOUCHERTYPE_SUCCESS);
	ajaxResponse.setRes_msg(ActivityConstants.CHANEGE_VOUCHERTYPE_SUCCESS_MSG);
	return ajaxResponse;
}


}
