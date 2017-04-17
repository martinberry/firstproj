/**
 * 
 */
package com.ztravel.payment.server;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.payment.AccountCreateBean;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.payment.CouponAccountInfoBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.payment.server.config.AppConfig;
import com.ztravel.payment.service.IAccountService;

/**
 * @author zuoning.shen
 *
 */
public class AccountTest {
    @Test
    public void testCreateAccount() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        IAccountService accountService = (IAccountService)context.getBean("AccountService");
        String memberId = "test0618";
        AccountCreateBean bean = new AccountCreateBean();
        bean.setMemberId(memberId);
        CommonResponse response = accountService.createAccount(bean);
        System.out.println("Response: " + TZBeanUtils.describe(response));
        AccountInfoBean accountInfo = accountService.getAccountInfo(memberId);
        System.out.println("AccountInfo: " + TZBeanUtils.describe(accountInfo));
        CouponAccountInfoBean couponAccountInfo = accountService.getCouponAccountInfo(memberId);
        System.out.println("CouponAccountInfo: " + TZBeanUtils.describe(couponAccountInfo));
    }
}
