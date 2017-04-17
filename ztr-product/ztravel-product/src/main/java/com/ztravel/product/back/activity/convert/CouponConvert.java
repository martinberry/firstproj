package com.ztravel.product.back.activity.convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.ztravel.common.enums.CouponStatus;
import com.ztravel.common.enums.CouponType;
import com.ztravel.common.enums.ProductRangeType;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.activity.entity.Coupon;
import com.ztravel.product.back.activity.vo.CouponVo;

public class CouponConvert {

	public static Coupon vo2Coupon(CouponVo couponVo) throws Exception{
		Coupon coupon = new Coupon();
		if(couponVo.getPrice()!=null){
		coupon.setPrice(couponVo.getPrice());
		}
		coupon.setCouponId(couponVo.getCouponId());
		coupon.setCouponCode(couponVo.getCouponCode());
		coupon.setName(couponVo.getName());
		coupon.setOrderLeast(couponVo.getOrderLeast() == null ? Long.MAX_VALUE : couponVo.getOrderLeast());
		coupon.setCouponType(CouponType.DEFAULT);
		coupon.setCreated(new DateTime());
		coupon.setCreatedBy(OperatorSidHolder.get());
		coupon.setUpdated(new DateTime());
		coupon.setUnit(couponVo.getUnit() == null ? 1 : couponVo.getUnit());
		coupon.setUsedNum(couponVo.getUsedNum() == null ? 0 : couponVo.getUsedNum());
		coupon.setUpdatedby(OperatorSidHolder.get());
		coupon.setAmount(couponVo.getAmount() == null ? 0l : couponVo.getAmount());
		coupon.setDescription(couponVo.getDescription());
		coupon.setStatus(CouponStatus.NOSENDDING);
		coupon.setIsDelete(false);
		if(couponVo.getProductRangeType().equals(ProductRangeType.MANUALADD.name())){
			if(StringUtils.isNotBlank(couponVo.getProductRange())){
				coupon.setSupportProducts(Arrays.asList(couponVo.getProductRange().split(",")));
			}
		}
		coupon.setProductRangeType(ProductRangeType.valueOf(couponVo.getProductRangeType()));
		coupon.setTotalNum(couponVo.getTotalNum());
		if(StringUtils.isBlank(couponVo.getValidTimeFrom())){
			coupon.setValidTimeFrom(null);
		}else{
			if(StringUtils.isBlank(couponVo.getValidHourFrom()) || StringUtils.isBlank(couponVo.getValidMinFrom())){
				coupon.setValidTimeFrom(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, couponVo.getValidTimeFrom() + " " + "00:00:00"));
			}else{
				coupon.setValidTimeFrom(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, couponVo.getValidTimeFrom() + " " + couponVo.getValidHourFrom() + ":" + couponVo.getValidMinFrom() + ":00"));
			}
		}
		coupon.setValidTimeTo(DateTimeUtil.parseDate(DateTimeUtil.DATE_TIME_PATTERN, couponVo.getValidTimeTo() + " " + couponVo.getValidHourTo() + ":" + couponVo.getValidMinTo() + ":00"));
		return coupon;
	}

	public static CouponVo coupon2Vo(Coupon coupon){
		CouponVo couponVo = new CouponVo();
		if(null != coupon){
			if((coupon.getPrice())!=null){
			couponVo.setPrice(coupon.getPrice()/100);
			}
			couponVo.setCouponCode(coupon.getCouponCode());
			couponVo.setAmount(coupon.getAmount()/100);
			couponVo.setCouponId(coupon.getCouponId().toString());
			couponVo.setCouponType(coupon.getCouponType());
			couponVo.setCouponTypeDesc("测试");//
			couponVo.setDescription(coupon.getDescription());
			couponVo.setName(coupon.getName());
			couponVo.setOrderLeast(coupon.getOrderLeast()/100);
			couponVo.setProductRangeType(coupon.getProductRangeType().name());
			couponVo.setStatus(coupon.getStatus().name());
			couponVo.setStatusDesc(coupon.getStatus().getDesc());
			if(coupon.getProductRangeType().name().equals(ProductRangeType.MANUALADD.name())){
				if(!CollectionUtils.isEmpty(coupon.getSupportProducts())){
					StringBuffer productRange = new StringBuffer();
					for(String s : coupon.getSupportProducts()){
						productRange.append(s + ",");
					}
					couponVo.setProductRange(productRange.toString().substring(0, productRange.lastIndexOf(",")));
				}
			}else{
				couponVo.setProductRange("所有产品");
			}
			if(coupon.getTotalNum() == null){//系统送券活动
				couponVo.setTotalNum((coupon.getUsedNum() == 0 || coupon.getUsedNum() == null) ? null : coupon.getUsedNum());
			}else{
				couponVo.setTotalNum(coupon.getTotalNum());
			}
			couponVo.setUnit(coupon.getUnit() == null ? 0 : coupon.getUnit());
			couponVo.setUsedNum(coupon.getUsedNum() == null ? 0 : coupon.getUsedNum());
			if(null != coupon.getValidTimeFrom()){
				couponVo.setValidTimeFrom(coupon.getValidTimeFrom().toString(DateTimeUtil.DATE_TIME_PATTERN));
			}
			couponVo.setValidTimeTo(coupon.getValidTimeTo().toString(DateTimeUtil.DATE_TIME_PATTERN));
		}
		return couponVo;
	}

	public static List<CouponVo> coupons2Vo(Map<String,Coupon> coupons){
		List<CouponVo> couponVoList = new ArrayList<CouponVo>();
		if(!CollectionUtils.isEmpty(coupons)){
			for(Entry<String, Coupon> entry : coupons.entrySet()){
				if(!entry.getValue().getIsDelete()){//显示未逻辑删除的代金券
					CouponVo couponVo = coupon2Vo(entry.getValue());
					couponVoList.add(couponVo);
				}
			}
		}
		return couponVoList;
	}
}
