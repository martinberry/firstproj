package com.ztravel.product.client.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;
import com.ztravel.common.payment.DNCouponGrantBean;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.service.IVoucherClientService;
import com.ztravel.product.dao.IVoucherDao;
import com.ztravel.product.dao.IVoucherLockDao;

@Service
public class VoucherClientServiceImpl implements IVoucherClientService {

	private static Logger LOGGER = RequestIdentityLogger.getLogger(VoucherClientServiceImpl.class);

	@Resource
	IVoucherDao voucherDao;

    @Resource
    IVoucherLockDao voucherLockDao;

	@Override
	public Voucher getVoucherById(String voucherId) throws Exception {
		return voucherDao.selectById(voucherId);
	}

	@Override
	public Voucher getVoucherByCode(String voucherCode) throws Exception {
		return voucherDao.selectByCode(voucherCode) ;
	}

	@Override
	public Voucher getVoucherByCouponItemId(String couponItemId) {
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("couponItemId", couponItemId) ;
		List<Voucher> vouchers = null ;
		try {
			vouchers = this.getVoucherListByParams(params);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return vouchers == null ? null : vouchers.get(0) ;
	}

	@Override
	public int countByCouponItemId(String couponItemId) {
		return voucherDao.countByCouponItemId(couponItemId) ;
	}

	/**
	 * 兑换
	 * */
	@Override
	public boolean updateVoucher4Grant(String couponItemId, DNCouponGrantBean bean) {
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("couponItemId", couponItemId) ;
    	params.put("voucherCode", bean.getVoucherCode()) ;
    	params.put("status", VoucherStatus.RECIEVED) ;
    	if(bean.getVoucherType() == VoucherType.NORMAL){
    		params.put("oldStatus", VoucherStatus.LOCK) ;
    	}else if(bean.getVoucherType() == VoucherType.SYSTEM){
    		params.put("oldStatus", VoucherStatus.INIT) ;
    	}
		int count = voucherDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateVoucher4Grant params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true ;
	}

	@Override
	public boolean updateVoucher4Share(String newCouponItemId, String couponItemId) {
		Map<String, Object> params = Maps.newHashMap() ;
    	params.put("couponItemId", newCouponItemId) ;
    	params.put("oldCouponItemId", couponItemId) ;
    	params.put("oldStatus", VoucherStatus.RECIEVED) ;
		int count = voucherDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateVoucher4Share params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true ;
	}

	//取消使用代金券的产品订单
	@Override
	public boolean updateVoucher4Cancel(String couponItemId) {
		Map<String, Object> params = Maps.newHashMap() ;
    	params.put("oldCouponItemId", couponItemId) ;
    	params.put("status", VoucherStatus.RECIEVED) ;
    	params.put("oldStatus", VoucherStatus.USED) ;
		int count = voucherDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateVoucher4Refund params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true ;
	}

	/**
	 * voucherOrder timming auto expired
	 * voucher --> lock --> invalid
	 * */
	@Override
	public boolean updateVoucher4VoucherOrderExpired(String voucherCode) {
		Map<String, Object> params = Maps.newHashMap() ;
    	params.put("voucherCode", voucherCode) ;
    	params.put("status", VoucherStatus.INIT) ;
    	params.put("oldStatus", VoucherStatus.LOCK) ;
		int count = voucherDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateVoucher4VoucherOrderExpired params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true ;
	}

	@Override
	public boolean updateVoucher4ApplyRefund(String couponItemId) {
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("oldCouponItemId", couponItemId) ;
		params.put("oldStatus", VoucherStatus.RECIEVED) ;
		params.put("status", VoucherStatus.INVALID) ;
		int count = voucherDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateVoucher4ApplyRefund params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true ;
	}

	/**
	 * 购买
	 * */
	@Override
	public boolean updateVoucher4Paid(String voucherCode) {
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("voucherCode", voucherCode) ;
    	params.put("status", VoucherStatus.RECIEVED) ;
		params.put("oldStatus", VoucherStatus.LOCK) ;
		int count = voucherDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateVoucher4Paid params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true ;
	}

	@Override
	public List<Voucher> getVoucherListByParams(Map<String, Object> params) throws Exception {
		List<Voucher> vouchers = voucherDao.select(params) ;
		if(CollectionUtils.isEmpty(vouchers)){
			Exception ex
				= new Exception("getVoucherListByParams params:::{" + params + "} can't find any one") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return vouchers;
	}

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	public boolean updateVoucher4BackDoor(List<String> newCodes, String couponId) throws Exception {
		Map<String, Object> ps = Maps.newHashMap() ;
		ps.put("couponId", couponId) ;
		List<Voucher> vouchers = getVoucherListByParams(ps) ;
		for(int i=0; i<newCodes.size(); i++){
			Map<String, Object> params = Maps.newHashMap() ;
			params.put("oldVoucherCode", vouchers.get(i).getVoucherCode()) ;
			params.put("newVoucherCode", newCodes.get(i)) ;
			int count = voucherDao.updateByMap(params) ;
			if(count != 1){
				RuntimeException ex
					= new RuntimeException("updateVoucher4BackDoor params:::{" + params + "} effect records:::{" + count + "}") ;
				LOGGER.error(ex.getMessage());
				throw ex ;
			}
		}
		return true ;
	}

	/**
	 * 代金券参与产品购买
	 * */
	@Override
	public boolean updateVoucher4TravelPaid(String couponItemId) {
		Map<String, Object> params = Maps.newHashMap() ;
    	params.put("oldCouponItemId", couponItemId) ;
    	params.put("status", VoucherStatus.USED) ;
    	params.put("oldStatus", VoucherStatus.RECIEVED) ;
		int count = voucherDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateVoucher4Refund params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true ;
	}

	@Override
	public int countByMap(Map<String, Object> map) throws Exception {
		return voucherDao.count(map);
	}

    @Override
    public Integer countBuyAvailableByCouponId(String couponId) {
        return voucherDao.countBuyAvailableByCouponId(couponId);
    }

    @Override
    public List<Voucher> selectBuyAvailableByCouponId(String couponId) {
        return voucherDao.selectBuyAvailableByCouponId(couponId);
    }

    @Override
    public List<Voucher> selectBuyAvailableByCouponIdAndNum(String couponId, Integer num) {
        return voucherDao.selectBuyAvailableByCouponIdAndNum(couponId, num);
    }

    @Override
    public void selectVoucherLockForUpdateByCouponId(String couponId) {
        voucherLockDao.forUpdateByCouponId(couponId);
    }

    @Override
    public boolean updateVoucher4ApplyLock(List<String> voucherIdList) throws Exception {
        Map<String, Object> params = Maps.newHashMap() ;
        params.put("voucherIdList", voucherIdList) ;
        params.put("status", VoucherStatus.LOCK) ;
        params.put("oldStatus", VoucherStatus.INIT) ;
        int count = voucherDao.updateByMap(params) ;
        if(count != voucherIdList.size()){
            RuntimeException ex
                = new RuntimeException("updateVoucher4ApplyLock params:::{" + params + "} effect records:::{" + count + "}") ;
            LOGGER.error(ex.getMessage());
            throw ex ;
        }
        return true ;
    }

    @Override
    public boolean updateVoucherStatus(String voucherCode, VoucherStatus voucherStatus) throws Exception {

        if (StringUtil.isEmpty(voucherCode) || voucherStatus == null) {
            return false;
        }
        Map params = Maps.newHashMap();
        params.put("voucherCode", voucherCode);
        params.put("status", voucherStatus.toString());
        int count = voucherDao.updateByMap(params);
        if(count != 1){
            RuntimeException ex
                = new RuntimeException("updateVoucherType params:::{" + params + "} effect records:::{" + count + "}") ;
            LOGGER.error(ex.getMessage());
            throw ex ;
        }
        return true;
    }

    @Override
    public List<Map> selectVoucherStockByCouponIds(List<String> couponIds) {
        return voucherDao.selectVoucherStockByCouponIds(couponIds);
    }

    @Override
    public List<Voucher> selectActivityAvailableByCouponId(String couponId) {
        return voucherDao.selectActivityAvailableByCouponId(couponId);
    }

}
