/**
 * 
 */
package com.ztravel.payment.server;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.payment.TradeQueryBean;
import com.ztravel.payment.server.config.AppConfig;
import com.ztravel.payment.service.IFinanceService;

/**
 * @author zuoning.shen
 *
 */
public class FinanceTest {
    @Test
    public void testQueryTrade() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        IFinanceService financeService = (IFinanceService)context.getBean("FinanceService");
        String memberId = "test0618";
        TradeQueryBean bean = new TradeQueryBean();
        bean.setMemberId(memberId);
        bean.setPageNo(1);
        bean.setPageSize(10);
        Pagination<TradeBean> pagination = financeService.queryTradeInfo(bean);
        System.out.println("Result: " + TZBeanUtils.describe(pagination));
    }
}
