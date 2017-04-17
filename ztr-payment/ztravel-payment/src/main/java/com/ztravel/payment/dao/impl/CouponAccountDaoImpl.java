/**
 * 
 */
package com.ztravel.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.payment.dao.CouponAccountDao;
import com.ztravel.payment.po.CouponAccount;

/**
 * @author zuoning.shen
 *
 */
@Repository
public class CouponAccountDaoImpl extends BaseDaoImpl<CouponAccount> implements CouponAccountDao {

    @Override
    public CouponAccount selectByMemberId(String memberId) {
        return session.selectOne(nameSpace + ".selectByMemberId", memberId);
    }

}
