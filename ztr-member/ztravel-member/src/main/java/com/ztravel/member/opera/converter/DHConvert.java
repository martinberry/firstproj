package com.ztravel.member.opera.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;




import com.ztravel.common.enums.AccountConvertContent;
import com.ztravel.common.enums.AccountCovertStatus;
//import org.apache.commons.lang.enums.EnumUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.travelzen.framework.core.util.MoneyUtil;
//import com.ztravel.common.enums.OrderFrom;
//import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.member.po.Dhpo ;
import com.ztravel.member.opera.vo.*;


public class DHConvert  {

	//private static final Logger LOGGER = LoggerFactory.getLogger(DHConvert.class);

	public static List<DHOrderListVO> convertPOList2VOList(List<Dhpo> DHList){
		List<DHOrderListVO> DHorderVoList = new ArrayList<DHOrderListVO>();
		for(Dhpo dh :  DHList){
			DHOrderListVO dhorderVo = convertPO2VO(dh);
			DHorderVoList.add(dhorderVo );
		}
		return DHorderVoList ;
	}

	private static DHOrderListVO convertPO2VO(Dhpo dh){
		DHOrderListVO dhorderVO = new DHOrderListVO();
		dhorderVO.setDhId(dh.getDhId());//po中id传递到vo列表
		dhorderVO.setMemberId(dh.getMemberId());     //memberid传递

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != dh.getPledhTime()){
			dhorderVO.setPledhTime(format.format(dh.getPledhTime()));//申兑时间传递
		}
		dhorderVO.setStatusDesc(AccountCovertStatus.valueOf(dh.getDhStatus()).getDescription());
		dhorderVO.setDhContent(dh.getDhContent());
		dhorderVO.setDhStatus(dh.getDhStatus());
		dhorderVO.setDhMoney(dh.getDhMoney());     //兑换金额传递  格式问题
		dhorderVO.setContentDesc(AccountConvertContent.valueOf(dh.getDhContent()).getDescription());
		dhorderVO.setDhContent(dh.getDhContent());     //兑换内容
		dhorderVO.setMemberId(dh.getMemberId());     //操作
		dhorderVO.setLastOperator(dh.getLastOperator());     //最后操作人
		return dhorderVO;
	}
}
