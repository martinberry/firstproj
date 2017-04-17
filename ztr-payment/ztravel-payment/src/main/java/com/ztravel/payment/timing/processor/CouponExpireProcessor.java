/**
 * 
 */
package com.ztravel.payment.timing.processor;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ztravel.common.enums.AccountType;
import com.ztravel.common.enums.InoutDetailType;
import com.ztravel.common.enums.InoutType;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.payment.bo.CouponBo;
import com.ztravel.payment.dao.AccountDao;
import com.ztravel.payment.dao.AccountHistoryDao;
import com.ztravel.payment.po.Account;
import com.ztravel.payment.po.AccountHistory;
import com.ztravel.payment.po.CouponItem;
import com.ztravel.payment.po.factory.ModelFactory;

/**
 * @author zuoning.shen
 *
 */
@Component
public class CouponExpireProcessor {
    
    @Resource
    private CouponBo couponBo;
    @Resource
    private AccountDao accountDao;
    @Resource
    private AccountHistoryDao accountHistoryDao;
    @Resource
	private IdGeneratorUtil idGeneratorUtil ;

    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public void expire(CouponItem item) throws Exception{
        Account account = accountDao.selectForUpdate(item.getMemberId());
        couponBo.expire(item.getCouponItemId());
        createAccountHistory(item);
    }

    private void createAccountHistory(CouponItem item) throws Exception{
        AccountHistory accountHistory = ModelFactory.createAccountHistory(idGeneratorUtil.getAccountHistoryId());
        accountHistory.setMemberId(item.getMemberId());
        accountHistory.setAccountType(AccountType.COUPON.name());
        accountHistory.setAmount(item.getAmount());
        accountHistory.setInoutType(InoutType.OUTGO.name());
        accountHistory.setInoutDetailType(InoutDetailType.COUPON_EXPIRED.name());
        accountHistoryDao.insert(accountHistory);
    }

}
