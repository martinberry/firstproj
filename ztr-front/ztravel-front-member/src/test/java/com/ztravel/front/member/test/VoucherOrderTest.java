package com.ztravel.front.member.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Maps;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.VoucherOrderStatus;
import com.ztravel.common.test.SpringJUnit4ClassRunnerWithLog;
import com.ztravel.order.dao.IVoucherOrderDao;
import com.ztravel.order.po.VoucherOrder;

@RunWith(SpringJUnit4ClassRunnerWithLog.class)
@ContextConfiguration(classes = VoucherTestConfig.class)
public class VoucherOrderTest {
	
	@Resource
	private IVoucherOrderDao voucherOrderDao ;
	
	@Test
	public void insert(){
		VoucherOrder order = new VoucherOrder() ;
		order.setCouponCode("couponCode");
		order.setCouponId("couponId");
		order.setCouponSnapshot("couponSnapshot");
		order.setCreated(DateTime.now());
		order.setCreatedBy("wanhf");
		order.setOrderFrom("orderFrom");
		order.setPayAmount(10000);
		order.setPaySerialNum("paySerialNum");
		order.setPayType(PaymentType.Alipay);
		order.setStatus(VoucherOrderStatus.UNPAY);
		order.setTotalPrice(10001);
		order.setUpdated(DateTime.now().plusDays(1));
		order.setUpdatedBy("wanhaofan");
		order.setVoucherCode("voucherCode");
		order.setVoucherOrderId("voucherOrderId1");
		voucherOrderDao.insert(order);
		
	}
	
	@Test
	public void select4Cancel(){
		Map<String,Object> params = Maps.newHashMap();
		params.put("createdTo", DateTime.now().minusMinutes(30));
		List<VoucherOrderStatus> statusList = new ArrayList<VoucherOrderStatus>() ;
		statusList.add(VoucherOrderStatus.UNPAY) ;
		params.put("statusList", statusList) ;
		List<VoucherOrder> orders = voucherOrderDao.select(params) ;
		VoucherOrder order = orders.get(0) ;
		System.out.println(order.getCreated().toDateTime(DateTimeZone.forOffsetHours(8))) ;
	}
	
	@Test
	public void cancel(){
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("voucherOrderId", "voucherOrderId1") ;
		params.put("status", VoucherOrderStatus.CANCELED.toString()) ;
		int count = voucherOrderDao.updateByMap(params) ;
		System.out.println(count);
	}
	
	@Test
	public void select(){
		System.out.println(voucherOrderDao.selectById("voucherOrderId1")) ;
	}
	
}
