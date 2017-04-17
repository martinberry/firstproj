/**
 * 
 */
package com.ztravel.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.payment.dao.AccountDao;
import com.ztravel.payment.po.Account;

/**
 * @author zuoning.shen
 *
 */
@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {

    @Override
    public Account selectForUpdate(String memberId) {
        return session.selectOne(nameSpace + ".selectForUpdate", memberId);
    }

    @Override
    public Account selectByMemberId(String memberId) {
        return session.selectOne(nameSpace + ".selectByMemberId", memberId);
    }

}
