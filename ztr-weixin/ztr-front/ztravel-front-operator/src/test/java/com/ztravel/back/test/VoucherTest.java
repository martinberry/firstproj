package com.ztravel.back.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;
import com.ztravel.common.test.SpringJUnit4ClassRunnerWithLog;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.dao.IVoucherDao;
import com.ztravel.product.utils.VoucherCodeGenerator;

@RunWith(SpringJUnit4ClassRunnerWithLog.class)
@ContextConfiguration(classes = VoucherTestConfig.class)
public class VoucherTest {

	@Resource
	private IVoucherDao voucherDao ;


	@Test
	public void insert(){
		Voucher voucher = new Voucher() ;
		voucher.setVoucherId("99121223999999999999236t");
		VoucherCodeGenerator.generator(voucher);
		voucher.setCouponId("couponIdt");
		voucher.setActivityId("activityIdt");
		voucher.setCouponCode("couponCode");
		voucher.setCouponItemId("couponItemIdssst");
		voucher.setCreated(DateTime.now());
		voucher.setCreatedBy("wanhf");
		voucher.setPrice(10000);
		voucher.setStatus(VoucherStatus.INIT);
		voucher.setUpdated(DateTime.now().plusDays(1));
		voucher.setUpdatedBy("wanhaofan");
		voucher.setVoucherType(VoucherType.NORMAL);
		voucherDao.insert(voucher);
	}

	@Test
	public void select(){
		System.out.println(voucherDao.selectById("99121223999999999999")) ;
	}

	@Test
	public void selectByParams(){
		Map<String, Object> params = Maps.newHashMap();
		params.put("voucherCode", "0A63DF2417DF728C");
		params.put("couponItemId", "citm000000000000000000011701t");
		params.put("price", 10000);
		List<VoucherStatus> statusList = Lists.newArrayList();
		statusList.add(VoucherStatus.RECIEVED);
		params.put("statusList", statusList);
		params.put("couponCode", "couponCode");
//		params.put("activityId", "");
		params.put("createdby", "wanhf");
		params.put("updatedby", "wanhaofan");
		params.put("createdFrom", DateTime.now().minusDays(2));
		params.put("createdTo",DateTime.now());
		params.put("updatedFrom", DateTime.now().minusDays(2));
		params.put("updatedTo", DateTime.now());
		List<Voucher> voucherList = voucherDao.select(params);

		if(voucherList != null && voucherList.size()>0){
			System.out.println(TZBeanUtils.describe(voucherList.get(0))) ;
		}

	}

	@Test
	public void update(){
		Voucher voucher = new Voucher() ;
		voucher.setVoucherCode("0A63DF2417DF728C");
		voucher.setCouponItemId("11111111134t");
		//voucherDao.updateCouponItemId(voucher);
	}

	//@Test
	public void countByCid(){
		System.out.println(voucherDao.countByCouponItemId("couponItemId")) ;
	}

	@Test
	public void grant(){
		Map<String, String> params = new HashMap<String, String>() ;
    	params.put("couponItemId", "11111111111") ;
    	params.put("voucherCode", "AEE01824786B6567") ;
    	params.put("status", VoucherStatus.RECIEVED.toString()) ;
    	params.put("oldStatus", VoucherStatus.INIT.toString()) ;
    	voucherDao.updateByMap(params) ;
	}

	@Test
	public void share(){
    	Map<String, String> params = new HashMap<String, String>() ;
    	params.put("couponItemId", "222222222") ;
    	params.put("oldCouponItemId", "11111111111") ;
    	params.put("oldStatus", VoucherStatus.RECIEVED.toString()) ;
    	voucherDao.updateByMap(params) ;
	}

	//@Test
	public void updateVoucher(){
		Voucher voucher = voucherDao.selectById("99121223999999999999");
		voucher.setStatus(VoucherStatus.EXPIRED);
		voucher.setCouponId("couponId");
		voucher.setActivityId("activityId");
		voucher.setCouponCode("couponCode");
		voucher.setCouponItemId("couponItemIdssssssst");
		voucher.setCreated(DateTime.now());
		voucher.setCreatedBy("wanhf");
		voucher.setPrice(10000);
		voucher.setUpdated(DateTime.now().plusDays(1));
		voucher.setUpdatedBy("wanhaofan");
		voucher.setVoucherType(VoucherType.SYSTEM);
		voucherDao.update(voucher);
	}
	
	
	@Test
	public void autoExpired(){
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("couponId", "couponId") ;
		List<VoucherStatus> vss = Lists.newArrayList() ;
		vss.add(VoucherStatus.INIT) ;
		vss.add(VoucherStatus.LOCK) ;
		vss.add(VoucherStatus.RECIEVED) ;
		params.put("status", VoucherStatus.EXPIRED) ;
		params.put("statusList", vss) ;
		int count = voucherDao.updateByMap(params) ;
		System.out.println(count) ;
	}
}
