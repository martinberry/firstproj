package com.ztravel.product.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.product.client.entity.VoucherLock;

public interface IVoucherLockDao extends BaseDao<VoucherLock>{

    public Integer forUpdateByCouponId(String couponId);

}
