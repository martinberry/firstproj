package com.ztravel.product.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.product.client.entity.VoucherLock;
import com.ztravel.product.dao.IVoucherLockDao;
@Repository
public class VoucherLockDaoImpl extends BaseDaoImpl<VoucherLock> implements IVoucherLockDao{

    private static final String FOR_UPDATE_BY_COUPONID = ".forUpdateByCouponId";

    @Override
    public Integer forUpdateByCouponId(String couponId) {
        return session.selectOne(nameSpace + FOR_UPDATE_BY_COUPONID, couponId) == null ? 0 : 1;
    }
}







