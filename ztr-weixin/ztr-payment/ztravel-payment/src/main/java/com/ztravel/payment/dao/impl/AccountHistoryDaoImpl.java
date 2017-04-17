/**
 * 
 */
package com.ztravel.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.payment.dao.AccountHistoryDao;
import com.ztravel.payment.po.AccountHistory;

/**
 * @author zuoning.shen
 *
 */
@Repository
public class AccountHistoryDaoImpl extends BaseDaoImpl<AccountHistory> implements AccountHistoryDao {

    @Override
    public int countAccountHistory(Map params) {
        return session.selectOne(nameSpace + ".countAccountHistory", params);
    }

    @Override
    public List<AccountHistory> selectAccountHistory(Map params) {
        return session.selectList(nameSpace + ".selectAccountHistory", params);
    }

}
