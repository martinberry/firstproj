/**
 * 
 */
package com.ztravel.payment.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.payment.po.AccountSummary;

/**
 * @author zuoning.shen
 *
 */
public interface AccountSummaryDao extends BaseDao<AccountSummary> {
    int countAccountSummary(Map params);

    List<AccountSummary> selectAccountSummary(Map params);
}
