/**
 * 
 */
package com.ztravel.payment.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.framework.util.TZBeanUtils;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.common.payment.AccountCreateBean;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.payment.CouponAccountInfoBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.payment.processor.AccountProcessor;

/**
 * @author zuoning.shen
 *
 */
@Service("AccountService")
@ThriftServiceEndpoint
public class AccountService implements IAccountService {
    private static Logger logger = LoggerFactory.getLogger(AccountService.class);
    
    @Resource
    private AccountProcessor accountProcessor;

    @Override
    public CommonResponse createAccount(AccountCreateBean bean) {
        logger.info("createAccount start, bean: {}", TZBeanUtils.describe(bean));
        CommonResponse res = null;
        try{
            res = accountProcessor.createAccount(bean);
        }catch(Exception e){
            logger.error("createAccount error", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("createAccount end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

    @Override
    public AccountInfoBean getAccountInfo(String memberId) {
        logger.info("getAccountInfo start, memberId: {}", memberId);
        AccountInfoBean accountInfo = null;
        try {
            accountInfo = accountProcessor.getAccountInfo(memberId);
        } catch (Exception e) {
            logger.error("getAccountInfo error", e);
        }
        logger.info("getAccountInfo end, accountInfo: {}", TZBeanUtils.describe(accountInfo));
        return accountInfo;
    }

    @Override
    public CouponAccountInfoBean getCouponAccountInfo(String memberId) {
        logger.info("getCouponAccountInfo start, memberId: {}", memberId);
        CouponAccountInfoBean couponAccountInfo = null;
        try {
            couponAccountInfo = accountProcessor.getCouponAccountInfo(memberId);
        } catch (Exception e) {
            logger.error("getCouponAccountInfo error", e);
        }
        logger.info("getCouponAccountInfo end, couponAccountInfo: {}", TZBeanUtils.describe(couponAccountInfo));
        return couponAccountInfo;
    }
    
    @Override
    public CommonResponse addAmount(String memberId, long amount) {
        logger.info("addAmount start, memberId: {}, amount: {}", memberId, amount);
        CommonResponse res = null;
        try{
            res = accountProcessor.addAmount(memberId, amount);
        }catch(Exception e){
            logger.error("addAmount error", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("addAmount end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

}
