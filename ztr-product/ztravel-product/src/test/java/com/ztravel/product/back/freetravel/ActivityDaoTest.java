package com.ztravel.product.back.freetravel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.jmkgreen.morphia.Datastore;
import com.ztravel.common.enums.CouponType;
import com.ztravel.common.enums.GrantType;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.back.activity.entity.Coupon;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/ztravel-datastore.xml"})
public class ActivityDaoTest {
	@Resource
	private Datastore datastore;

	@Test
	public void testAdd(){
		datastore.save(getActivity());
		System.out.println(datastore.getCount(Activity.class));
	}

	public Activity getActivity(){
		Activity activity = new Activity();
		activity.setCode("HD000001");
		activity.setCreateTime(new DateTime());
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setAmount(10l);
//		coupon.setCouponId("CP00001");
		coupon.setCouponType(CouponType.DEFAULT);
		coupon.setCreated(new DateTime());
		coupon.setCreatedBy("zhaopengfei");
		coupon.setName("黄金券");
		List<String> products = new ArrayList<String>();
		products.add("Z0001");
		coupon.setSupportProducts(products);
		coupon.setUpdated(new DateTime());
		coupon.setUpdatedby("zhaopengfei");
		coupon.setValidTimeFrom(new DateTime());
		coupon.setValidTimeTo(new DateTime());
		coupons.add(coupon);
//		activity.setCoupons(coupons);
		activity.setCreator("ceshi");
		activity.setEndTime(new DateTime());
		activity.setGrantType(GrantType.BATCHCONFIG);
		activity.setName("cece");
		activity.setOperator("ce");
		return activity;
	}
}
