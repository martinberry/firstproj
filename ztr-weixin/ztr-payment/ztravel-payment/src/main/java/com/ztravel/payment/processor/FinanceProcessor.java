/**
 *
 */
package com.ztravel.payment.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.enums.AccountStatus;
import com.ztravel.common.enums.AccountType;
import com.ztravel.common.enums.InoutDetailType;
import com.ztravel.common.enums.InoutType;
import com.ztravel.common.enums.OrderType;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.TradeStatus;
import com.ztravel.common.enums.TradeType;
import com.ztravel.common.payment.AccountHistoryBean;
import com.ztravel.common.payment.AccountHistoryQueryBean;
import com.ztravel.common.payment.AccountSummaryBean;
import com.ztravel.common.payment.AccountSummaryQueryBean;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.payment.TradeQueryBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.payment.dao.AccountDao;
import com.ztravel.payment.dao.AccountHistoryDao;
import com.ztravel.payment.dao.AccountSummaryDao;
import com.ztravel.payment.dao.CouponAccountDao;
import com.ztravel.payment.dao.TradeDao;
import com.ztravel.payment.po.Account;
import com.ztravel.payment.po.AccountHistory;
import com.ztravel.payment.po.AccountSummary;
import com.ztravel.payment.po.CouponAccount;
import com.ztravel.payment.po.Trade;

/**
 * @author zuoning.shen
 *
 */
@Component
public class FinanceProcessor {
    @Resource
    private TradeDao tradeDao;
    @Resource
    private AccountHistoryDao accountHistoryDao;
    @Resource
    private AccountSummaryDao accountSummaryDao;
    @Resource
    private AccountDao accountDao;
    @Resource
    private CouponAccountDao couponAccountDao;

    @Profiled
    public Pagination<TradeBean> queryTradeInfo(TradeQueryBean bean) {
        Pagination<TradeBean> pagination = new Pagination<TradeBean>();
        Map params = new HashMap();
        if (bean.getConditionTradeFrom() != null) {
            params.put("conditionTradeFrom", bean.getConditionTradeFrom());
        }
        if (bean.getConditionTradeTo() != null) {
            params.put("conditionTradeTo", bean.getConditionTradeTo());
        }
        if (bean.getMemberId() != null) {
            params.put("memberId", bean.getMemberId());
        }
        if (bean.getOrderId() != null) {
            params.put("orderId", bean.getOrderId());
        }
        if (bean.getTraceNum() != null) {
            params.put("traceNum", bean.getTraceNum());
        }
        if (bean.getOrderType() != null) {
            params.put("orderType", bean.getOrderType().name());
        }
        if (bean.getTradeType() != null) {
            params.put("tradeType", bean.getTradeType().name());
        }
        if (bean.getProductType() != null) {
            params.put("productType", bean.getProductType().name());
        }
        if (bean.getPaymentType() != null) {
            params.put("paymentType", bean.getPaymentType().name());
        }
        if (bean.getTradeStatus() != null) {
            params.put("tradeStatus", bean.getTradeStatus().name());
        }
        if (bean.getPageNo() != -1 && bean.getPageSize() != -1) {
            params.put("offset", bean.getPageSize() * (bean.getPageNo() - 1));
            params.put("limit", bean.getPageSize());
            int totalCount = tradeDao.countTrades(params);
            pagination.setTotalItemCount(totalCount);
        }
        List<TradeBean> resultList = new ArrayList<TradeBean>();
        List<Trade> tradeList = tradeDao.selectTrades(params);
        if(tradeList != null){
            for(Trade trade: tradeList){
                TradeBean tradeBean = coventFromTrade(trade);
                resultList.add(tradeBean);
            }
        }
        pagination.setData(resultList);
        return pagination;
    }

    private TradeBean coventFromTrade(Trade trade) {
        TradeBean tradeBean = new TradeBean();
        tradeBean.setTradeId(trade.getTradeId());
        tradeBean.setMemberId(trade.getMemberId());
        tradeBean.setOrderId(trade.getOrderId());
        tradeBean.setOrderType( StringUtils.isBlank(trade.getOrderType()) ? null : OrderType.valueOf(trade.getOrderType()));
        tradeBean.setTradeType(StringUtils.isBlank(trade.getTradeType()) ? null : TradeType.valueOf(trade.getTradeType()));
        tradeBean.setProductType(StringUtils.isBlank(trade.getProductType()) ? null : ProductType.valueOf(trade.getProductType()));
        tradeBean.setOrderAmount(trade.getOrderAmount());
        tradeBean.setTradeAmount(trade.getTradeAmount());
        tradeBean.setPaymentType(StringUtils.isBlank(trade.getPaymentType()) ? null : PaymentType.valueOf(trade.getPaymentType()));
        tradeBean.setComment(trade.getComment());
        tradeBean.setTradeDate(trade.getTradeDate());
        tradeBean.setTradeStatus(StringUtils.isBlank(trade.getTradeStatus()) ? null : TradeStatus.valueOf(trade.getTradeStatus()));
        tradeBean.setTraceNum(trade.getTraceNum());
        tradeBean.setBankPaymentTime(trade.getBankPaymentTime());
        tradeBean.setCouponItemId(trade.getCouponItemId());
        return tradeBean;
    }

    @Profiled
    public Pagination<AccountHistoryBean> queryAccountHistoryInfo(AccountHistoryQueryBean bean) {
        Pagination<AccountHistoryBean> pagination = new Pagination<AccountHistoryBean>();
        Map params = new HashMap();
        if (bean.getMemberId() != null) {
            params.put("memberId", bean.getMemberId());
        }
        if (bean.getOrderId() != null) {
            params.put("orderId", bean.getOrderId());
        }
        if (bean.getProductType() != null) {
            params.put("productType", bean.getProductType().name());
        }
        if (bean.getAccountType() != null) {
            params.put("accountType", bean.getAccountType().name());
        }
        if (bean.getInoutType() != null) {
            params.put("inoutType", bean.getInoutType().name());
        }
        if (bean.getInoutDetailType() != null) {
            params.put("inoutDetailType", bean.getInoutDetailType().name());
        }
        if (bean.getConditionOperateFrom() != null) {
            params.put("conditionOperateFrom", bean.getConditionOperateFrom());
        }
        if (bean.getConditionOperateTo() != null) {
            params.put("conditionOperateTo", bean.getConditionOperateTo());
        }
        if (bean.getPageNo() != -1 && bean.getPageSize() != -1) {
            params.put("offset", bean.getPageSize() * (bean.getPageNo() - 1));
            params.put("limit", bean.getPageSize());
            int totalCount = accountHistoryDao.countAccountHistory(params);
            pagination.setTotalItemCount(totalCount);
        }
        List<AccountHistoryBean> resultList = new ArrayList<AccountHistoryBean>();
        List<AccountHistory> accountHistoryList = accountHistoryDao.selectAccountHistory(params);
        if(accountHistoryList != null){
            for(AccountHistory accountHistory: accountHistoryList){
                AccountHistoryBean accountHistoryBean = coventFromAccountHistory(accountHistory);
                resultList.add(accountHistoryBean);
            }
        }
        pagination.setData(resultList);
        return pagination;
    }

    private AccountHistoryBean coventFromAccountHistory(AccountHistory accountHistory) {
        AccountHistoryBean accountHistoryBean = new AccountHistoryBean();
        accountHistoryBean.setMemberId(accountHistory.getMemberId());
        accountHistoryBean.setOrderId(accountHistory.getOrderId());
        accountHistoryBean.setProductType(StringUtils.isBlank(accountHistory.getProductType()) ? null : ProductType.valueOf(accountHistory.getProductType()));
        accountHistoryBean.setAccountType(StringUtils.isBlank(accountHistory.getAccountType()) ? null : AccountType.valueOf(accountHistory.getAccountType()));
        accountHistoryBean.setAmount(accountHistory.getAmount());
        accountHistoryBean.setInoutType(StringUtils.isBlank(accountHistory.getInoutType()) ? null : InoutType.valueOf(accountHistory.getInoutType()));
        accountHistoryBean.setInoutDetailType(StringUtils.isBlank(accountHistory.getInoutDetailType()) ? null : InoutDetailType.valueOf(accountHistory.getInoutDetailType()));
        accountHistoryBean.setOperateDate(accountHistory.getOperateDate());
        return accountHistoryBean;
    }

    @Profiled
    public Pagination<AccountSummaryBean> queryAccountSummaryInfo(AccountSummaryQueryBean bean) {
        Pagination<AccountSummaryBean> pagination = new Pagination<AccountSummaryBean>();
        Map params = new HashMap();
        if (bean.getMemberId() != null) {
            params.put("memberId", bean.getMemberId());
        }
        if (bean.getAccountType() != null) {
            params.put("accountType", bean.getAccountType().name());
        }
        if(bean.getAccountStatus() != null){
            if(AccountStatus.AVAILABLE.equals(bean.getAccountStatus())) params.put("isActive", true);
            if(AccountStatus.FROZEN.equals(bean.getAccountStatus())) params.put("isActive", false);
        }
        if (bean.getPageNo() != -1 && bean.getPageSize() != -1) {
            params.put("offset", bean.getPageSize() * (bean.getPageNo() - 1));
            params.put("limit", bean.getPageSize());
            int totalCount = accountSummaryDao.countAccountSummary(params);
            pagination.setTotalItemCount(totalCount);
        }
        List<AccountSummaryBean> resultList = new ArrayList<AccountSummaryBean>();
        List<AccountSummary> accountSummaryList = accountSummaryDao.selectAccountSummary(params);
        if(accountSummaryList != null){
            for(AccountSummary accountSummary: accountSummaryList){
                AccountSummaryBean accountSummaryBean = conventFromAccountSummary(accountSummary);
                resultList.add(accountSummaryBean);
            }
        }
        pagination.setData(resultList);
        return pagination;
    }

    private AccountSummaryBean conventFromAccountSummary(AccountSummary accountSummary) {
        AccountSummaryBean accountSummaryBean = new AccountSummaryBean();
        accountSummaryBean.setMemberId(accountSummary.getMemberId());
        accountSummaryBean.setAccountType(StringUtils.isBlank(accountSummary.getAccountType()) ? null : AccountType.valueOf(accountSummary.getAccountType()));
        accountSummaryBean.setAmount(accountSummary.getAmount());
        accountSummaryBean.setAvailableAmount(accountSummary.getAvailableAmount());
        accountSummaryBean.setFrozenAmount(accountSummary.getFrozenAmount());
        accountSummaryBean.setAccountStatus(accountSummary.isActive() ? AccountStatus.AVAILABLE : AccountStatus.FROZEN);
        return accountSummaryBean;
    }

    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse freezeAccount(String memberId, AccountType accountType) throws Exception{
        CommonResponse res = new CommonResponse();
        Account account = accountDao.selectForUpdate(memberId);
        if(account == null){
            throw new Exception("账户不存在");
        }
        if(AccountType.COUPON.equals(accountType)){
            freeze(memberId);
        }
        res.setSuccess(true);
        return res;
    }

    private void freeze(String memberId) throws Exception{
        CouponAccount account = couponAccountDao.selectByMemberId(memberId);
        if (account == null) {
            throw new Exception("账户不存在");
        }
        account.setActive(false);
        account.setUpdated(DateTime.now());
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        couponAccountDao.update(account);
    }

    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse unfreezeAccount(String memberId, AccountType accountType) throws Exception{
        CommonResponse res = new CommonResponse();
        Account account = accountDao.selectForUpdate(memberId);
        if(account == null){
            throw new Exception("账户不存在");
        }
        if(AccountType.COUPON.equals(accountType)){
            unfreeze(memberId);
        }
        res.setSuccess(true);
        return res;
    }

    private void unfreeze(String memberId) throws Exception{
        CouponAccount account = couponAccountDao.selectByMemberId(memberId);
        if (account == null) {
            throw new Exception("账户不存在");
        }
        account.setActive(true);
        account.setUpdated(DateTime.now());
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        couponAccountDao.update(account);
    }

}
