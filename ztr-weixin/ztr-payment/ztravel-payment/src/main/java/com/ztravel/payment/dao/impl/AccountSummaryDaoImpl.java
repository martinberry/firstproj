/**
 * 
 */
package com.ztravel.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.payment.dao.AccountSummaryDao;
import com.ztravel.payment.po.AccountSummary;

/**
 * @author zuoning.shen
 *
 */
@Repository
public class AccountSummaryDaoImpl extends BaseDaoImpl<AccountSummary> implements AccountSummaryDao {

    @Override
    public int countAccountSummary(Map params) {
        return session.selectOne(nameSpace + ".countAccountSummary", params);
    }

    @Override
    public List<AccountSummary> selectAccountSummary(Map params) {
        return session.selectList(nameSpace + ".selectAccountSummary", params);
    }

}
