/**
 * 
 */
package com.ztravel.payment.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.util.TZBeanUtils;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.common.enums.AccountType;
import com.ztravel.common.payment.AccountHistoryBean;
import com.ztravel.common.payment.AccountHistoryQueryBean;
import com.ztravel.common.payment.AccountSummaryBean;
import com.ztravel.common.payment.AccountSummaryQueryBean;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.payment.TradeQueryBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.payment.processor.FinanceProcessor;

/**
 * @author zuoning.shen
 *
 */
@Service("FinanceService")
@ThriftServiceEndpoint
public class FinanceService implements IFinanceService {
    private static Logger logger = LoggerFactory.getLogger(FinanceService.class);
    
    @Resource
    private FinanceProcessor financeProcessor;

    @Override
    public Pagination<TradeBean> queryTradeInfo(TradeQueryBean bean) {
        logger.info("queryTradeInfo start, TradeQueryBean: {}", TZBeanUtils.describe(bean));
        Pagination<TradeBean> result = null; 
        try {
            result = financeProcessor.queryTradeInfo(bean);
        } catch (Exception e) {
            logger.error("queryTradeInfo failed.", e);
        }
        logger.info("queryTradeInfo end, TradeQueryBean: {}", TZBeanUtils.describe(bean));
        return result;
    }

    @Override
    public Pagination<AccountHistoryBean> queryAccountHistoryInfo(AccountHistoryQueryBean bean) {
        logger.info("queryAccountHistoryInfo start, AccountHistoryQueryBean: {}", TZBeanUtils.describe(bean));
        Pagination<AccountHistoryBean> result = null; 
        try {
            result = financeProcessor.queryAccountHistoryInfo(bean);
        } catch (Exception e) {
            logger.error("queryAccountHistoryInfo failed.", e);
        }
        logger.info("queryAccountHistoryInfo end, AccountHistoryQueryBean: {}", TZBeanUtils.describe(bean));
        return result;
    }

    @Override
    public Pagination<AccountSummaryBean> queryAccountSummaryInfo(AccountSummaryQueryBean bean) {
        logger.info("queryAccountSummaryInfo start, AccountSummaryQueryBean: {}", TZBeanUtils.describe(bean));
        Pagination<AccountSummaryBean> result = null; 
        try {
            result = financeProcessor.queryAccountSummaryInfo(bean);
        } catch (Exception e) {
            logger.error("queryAccountSummaryInfo failed.", e);
        }
        logger.info("queryAccountSummaryInfo end, AccountSummaryQueryBean: {}", TZBeanUtils.describe(bean));
        return result;
    }

    @Override
    public CommonResponse freezeAccount(String memberId, AccountType accountType) {
        logger.info("freezeAccount start, memberId: {}, accountType: {}", memberId, accountType.name());
        CommonResponse res = null;
        try {
            res = financeProcessor.freezeAccount(memberId, accountType);
        } catch (Exception e) {
            logger.error("freezeAccount failed", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("freezeAccount end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

    @Override
    public CommonResponse unfreezeAccount(String memberId, AccountType accountType) {
        logger.info("unfreezeAccount start, memberId: {}, accountType: {}", memberId, accountType.name());
        CommonResponse res = null;
        try {
            res = financeProcessor.unfreezeAccount(memberId, accountType);
        } catch (Exception e) {
            logger.error("unfreezeAccount failed", e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("unfreezeAccount end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

}
