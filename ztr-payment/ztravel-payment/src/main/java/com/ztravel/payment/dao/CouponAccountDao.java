/**
 * 
 */
package com.ztravel.payment.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.payment.po.CouponAccount;

/**
 * @author zuoning.shen
 *
 */
public interface CouponAccountDao extends BaseDao<CouponAccount> {
    CouponAccount selectByMemberId(String memberId);
}
