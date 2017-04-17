/**
 * 
 */
package com.ztravel.payment.service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.enums.AccountType;
import com.ztravel.common.payment.AccountHistoryBean;
import com.ztravel.common.payment.AccountHistoryQueryBean;
import com.ztravel.common.payment.AccountSummaryBean;
import com.ztravel.common.payment.AccountSummaryQueryBean;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.payment.TradeQueryBean;
import com.ztravel.common.rpc.CommonResponse;

/**
 * @author zuoning.shen
 *
 */
public interface IFinanceService {
    Pagination<TradeBean> queryTradeInfo(TradeQueryBean bean);
    
    Pagination<AccountHistoryBean> queryAccountHistoryInfo(AccountHistoryQueryBean bean);
    
    Pagination<AccountSummaryBean> queryAccountSummaryInfo(AccountSummaryQueryBean bean);
    
    CommonResponse freezeAccount(String memberId, AccountType accountType);
    
    CommonResponse unfreezeAccount(String memberId, AccountType accountType);
}
