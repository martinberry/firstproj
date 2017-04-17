/**
 * 
 */
package com.ztravel.payment.service;

import com.ztravel.common.payment.AccountCreateBean;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.payment.CouponAccountInfoBean;
import com.ztravel.common.rpc.CommonResponse;

/**
 * @author zuoning.shen
 *
 */
public interface IAccountService {
    CommonResponse createAccount(AccountCreateBean bean);
    
    AccountInfoBean getAccountInfo(String memberId);
    
    CouponAccountInfoBean getCouponAccountInfo(String memberId);
    
    CommonResponse addAmount(String memberId, long amount);
}
