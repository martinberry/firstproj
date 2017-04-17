package com.ztravel.product.back.activity.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.product.back.activity.service.IVoucherService;
import com.ztravel.product.back.activity.vo.CouponVo;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.entity.VoucherLock;
import com.ztravel.product.dao.IVoucherDao;
import com.ztravel.product.dao.IVoucherLockDao;
import com.ztravel.product.utils.VoucherCodeGenerator;

@Service
public class VoucherServiceImpl implements IVoucherService {
	private final RedisClient redisClient = RedisClient.getInstance();
	@Resource
    private IVoucherDao voucherdao;
	@Resource
	private IVoucherLockDao voucherlockdao;

	@Resource
	private IdGeneratorUtil idGeneratorUtil ;
	@Override
	public AjaxResponse updateVoucher(CouponVo couponvo){
		AjaxResponse response =AjaxResponse.instance(ActivityConstants.COUPON_SENDDING_FAILED, ActivityConstants.COUPON_SENDDING_FAILED_MSG);
		try{
			int num=couponvo.getTotalNum();
			if(num!=0){
				for(int i=0;i<num;i++){
					Voucher voucher=new Voucher();
					String voucherId=idGeneratorUtil.getVoucherId();
					voucher.setVoucherId(voucherId);
					VoucherCodeGenerator.generator(voucher);
					voucher.setPrice(couponvo.getPrice());
					VoucherStatus status=VoucherStatus.INIT;
					voucher.setStatus(status);
					VoucherType vouchertype=VoucherType.NORMAL;
					voucher.setVoucherType(vouchertype);
					voucher.setCreated(DateTime.now());
					voucher.setUpdated(DateTime.now());
					String username=redisClient.get(OperatorSidHolder.get());
					voucher.setCreatedBy(username);
					voucher.setUpdatedBy(username);
					voucher.setActivityId(couponvo.getActivityId());
					voucher.setCouponId(couponvo.getCouponId());
					voucher.setCouponCode(couponvo.getCouponCode());
					voucherdao.insert(voucher);
			}
				VoucherLock lockvoucher=new VoucherLock();
				lockvoucher.setCouponId(couponvo.getCouponId());
				voucherlockdao.insert(lockvoucher);
		}
			response.setRes_code(ActivityConstants.COUPON_SENDDING_SUCCESS);
			response.setRes_msg(null);
			return response;
		}catch(Exception e){
			return response;
		}
	}


	@Override
	public List<Voucher> selectVoucher() throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		List<Voucher> voucherlist=voucherdao.select(map);
		return voucherlist;
	}



}
