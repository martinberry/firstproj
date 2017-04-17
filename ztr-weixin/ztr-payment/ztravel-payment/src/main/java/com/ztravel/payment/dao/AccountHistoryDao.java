/**
 * 
 */
package com.ztravel.payment.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.payment.po.AccountHistory;

/**
 * @author zuoning.shen
 *
 */
public interface AccountHistoryDao extends BaseDao<AccountHistory> {
    int countAccountHistory(Map params);

    List<AccountHistory> selectAccountHistory(Map params);
}
