/**
 * 
 */
package com.ztravel.payment.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.payment.po.Account;

/**
 * @author zuoning.shen
 *
 */
public interface AccountDao extends BaseDao<Account> {
    Account selectForUpdate(String memberId);
    
    Account selectByMemberId(String memberId);
}
