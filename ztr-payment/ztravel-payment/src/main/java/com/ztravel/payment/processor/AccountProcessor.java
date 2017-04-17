/**
 * 
 */
package com.ztravel.payment.processor;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.payment.AccountCreateBean;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.payment.CouponAccountInfoBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.payment.bo.CouponBo;
import com.ztravel.payment.dao.AccountDao;
import com.ztravel.payment.dao.CouponAccountDao;
import com.ztravel.payment.dao.CouponItemDao;
import com.ztravel.payment.po.Account;
import com.ztravel.payment.po.CouponAccount;
import com.ztravel.payment.po.factory.ModelFactory;

/**
 * @author zuoning.shen
 *
 */
@Component
public class AccountProcessor {
    @Resource
    private AccountDao accountDao;
    @Resource
    private CouponAccountDao couponAccountDao;
    @Resource
    private CouponItemDao couponItemDao;
    @Resource
    private CouponBo couponBo;
    @Resource
	private IdGeneratorUtil idGeneratorUtil ;
    
    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse createAccount(AccountCreateBean bean) throws Exception{
        CommonResponse res = new CommonResponse();
        String memberId = bean.getMemberId();
        createAccount(memberId);
        createCouponAccount(memberId);
        res.setSuccess(true);
        return res;
    }

    private void createAccount(String memberId) throws Exception{
        Account account = accountDao.selectByMemberId(memberId);
        if(account != null) throw new Exception("账户已存在");
        account = ModelFactory.createAccount(idGeneratorUtil.getAccountId());
        account.setMemberId(memberId);
        accountDao.insert(account);
    }

    private void createCouponAccount(String memberId) throws Exception{
        CouponAccount account = couponAccountDao.selectByMemberId(memberId);
        if(account != null) throw new Exception("账户已存在");
        account = ModelFactory.createCouponAccount(idGeneratorUtil.getCouponAccountId());
        account.setMemberId(memberId);
        couponAccountDao.insert(account);
    }

    public AccountInfoBean getAccountInfo(String memberId) throws Exception{
        AccountInfoBean accountInfo = new AccountInfoBean();
        accountInfo.setMemberId(memberId);
        Account account = accountDao.selectByMemberId(memberId);
        if(account == null){
            throw new Exception("账户不存在");
        }
        accountInfo.setAmount(account.getAmount());
        accountInfo.setAvailableAmount(account.getAvailableAmount());
        accountInfo.setFrozenAmount(account.getFrozenAmount());
        accountInfo.setActive(account.isActive());
        return accountInfo;
    }

    public CouponAccountInfoBean getCouponAccountInfo(String memberId) throws Exception {
        CouponAccountInfoBean bean = new CouponAccountInfoBean();
        CouponAccount account = couponAccountDao.selectByMemberId(memberId);
        bean.setMemberId(memberId);
        if (account == null) {
            throw new Exception("账户不存在");
        }
        bean.setAmount(account.getAmount());
        bean.setAvailableAmount(account.getAvailableAmount());
        bean.setFrozenAmount(account.getFrozenAmount());
        bean.setActive(account.isActive());
        List<CouponItemBean> itemList = couponBo.getAvailableCouponItems(memberId);
        bean.setAvailableCouponItems(itemList);
        return bean;
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse addAmount(String memberId, long amount) throws Exception{
        CommonResponse res = new CommonResponse();
        //Lock account
        Account account = accountDao.selectForUpdate(memberId);
        if (account == null) {
            throw new Exception("账户不存在");
        }
        addAmount(account, amount);
        res.setSuccess(true);
        return res;
    }

    private void addAmount(Account account, long amount) throws Exception{
        long originAmount = account.getAmount();
        long originAvailableAmount = account.getAvailableAmount();
        account.setAmount(originAmount + amount);
        account.setAvailableAmount(originAvailableAmount + amount);
        account.setUpdated(DateTime.now());
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        checkAmount(account);
        accountDao.update(account);
    }
    
    private void checkAmount(Account account) throws Exception{
        long amount = account.getAmount();
        long availableAmount = account.getAvailableAmount();
        long frozenAmount = account.getFrozenAmount();
        if(amount <0 || availableAmount < 0 || frozenAmount < 0){
            throw new Exception("金额计算错误");
        }
        if(amount != availableAmount + frozenAmount){
            throw new Exception("金额计算错误");
        }
    }
    
}
