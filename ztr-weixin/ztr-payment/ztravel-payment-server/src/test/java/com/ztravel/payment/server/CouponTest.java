/**
 * 
 */
package com.ztravel.payment.server;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.payment.CouponAccountInfoBean;
import com.ztravel.common.payment.CouponGrantBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.payment.CouponItemQueryBean;
import com.ztravel.common.payment.DNCouponGrantBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.test.SpringJUnit4ClassRunnerWithLog;
import com.ztravel.payment.server.config.AppConfig;
import com.ztravel.payment.service.IAccountService;
import com.ztravel.payment.service.ICouponService;

/**
 * @author zuoning.shen
 *
 */
@RunWith(SpringJUnit4ClassRunnerWithLog.class)
@ContextConfiguration(classes = AppConfig.class)
public class CouponTest {
	
	@Resource
	private ICouponService couponService ;
	
	@Test
	public void testGrantCoupon2(){
		DNCouponGrantBean bean = new DNCouponGrantBean() ;
		String memberId = "MEMB1508311023239601";
        bean.setMemberId(memberId);
        bean.setActivityId("test0618");
        bean.setAmount(10000l);
        bean.setCouponCode("J0618");
        bean.setName("0618");
        bean.setDescription("test0618");
        bean.setValidDateFrom(DateTime.parse("2015-06-18 00:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        bean.setValidDateTo(DateTime.parse("2015-06-30 23:59:59", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        bean.setVoucherCode("0A63DF2417DF728C");
        couponService.grant(bean) ;
	}
	
	
    //@Test
    public void testGrantCoupon() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ICouponService couponService = (ICouponService)context.getBean("CouponService");
        IAccountService accountService = (IAccountService)context.getBean("AccountService");
        String memberId = "test0618";
        CouponGrantBean bean = new CouponGrantBean();
        bean.setMemberId(memberId);
        bean.setActivityId("test0618");
        bean.setAmount(10000l);
        bean.setCouponCode("J0618");
        bean.setName("0618");
        bean.setDescription("test0618");
        bean.setValidDateFrom(DateTime.parse("2015-06-18 00:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        bean.setValidDateTo(DateTime.parse("2015-06-30 23:59:59", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        CommonResponse response = couponService.grant(bean);
        System.out.println("Response: " + TZBeanUtils.describe(response));
        CouponAccountInfoBean couponAccountInfo = accountService.getCouponAccountInfo(memberId);
        System.out.println("CouponAccountInfo: " + TZBeanUtils.describe(couponAccountInfo));
        CouponItemQueryBean queryBean = new CouponItemQueryBean();
        queryBean.setMemberId(memberId);
        queryBean.setStatusList(Lists.newArrayList(CouponItemStatus.AVAILABLE));
        Pagination<CouponItemBean> items = couponService.getCouponItems(queryBean);
        System.out.println("Item: " + TZBeanUtils.describe(items));
        for(CouponItemBean item: items.getData()){
            System.out.println("Item: " + TZBeanUtils.describe(item));
        }
    }
    
    //@Test
    public void testTransfer(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ICouponService couponService = (ICouponService)context.getBean("CouponService");
        IAccountService accountService = (IAccountService)context.getBean("AccountService");
        String memberId = "test0605";
        String destMemberId = "MEMB1506020911416601";
        String couponItemId = "citm000000000000000000000107";
        CouponAccountInfoBean account = accountService.getCouponAccountInfo(memberId);
        System.out.println("Account: " + TZBeanUtils.describe(account));
        CouponAccountInfoBean destAccount = accountService.getCouponAccountInfo(destMemberId);
        System.out.println("Dest Account: " + TZBeanUtils.describe(destAccount));
        CommonResponse res = couponService.transfer(couponItemId, destMemberId);
        System.out.println("Response: " + TZBeanUtils.describe(res));
        CouponAccountInfoBean afterAccount = accountService.getCouponAccountInfo(memberId);
        System.out.println("Account: " + TZBeanUtils.describe(afterAccount));
        CouponAccountInfoBean afterDestAccount = accountService.getCouponAccountInfo(destMemberId);
        System.out.println("Dest Account: " + TZBeanUtils.describe(afterDestAccount));
    }
}
