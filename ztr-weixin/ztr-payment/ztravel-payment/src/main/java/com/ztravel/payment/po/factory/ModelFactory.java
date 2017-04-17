/**
 * 
 */
package com.ztravel.payment.po.factory;

import org.joda.time.DateTime;

import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.enums.OrderPayStatus;
import com.ztravel.common.enums.PaymentStatus;
import com.ztravel.common.enums.TradeStatus;
import com.ztravel.payment.po.Account;
import com.ztravel.payment.po.AccountHistory;
import com.ztravel.payment.po.CouponAccount;
import com.ztravel.payment.po.CouponItem;
import com.ztravel.payment.po.OrderPay;
import com.ztravel.payment.po.Payment;
import com.ztravel.payment.po.Trade;

/**
 * @author zuoning.shen
 *
 */
public class ModelFactory {
	
    public static OrderPay createOrderPay(String id) throws Exception{
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderPayId(id);
        orderPay.setPaidAmount(0l);
        orderPay.setOrderPayStatus(OrderPayStatus.AWAIT.name());
        DateTime now = DateTime.now();
        orderPay.setCreatedby(OperatorConstants.AUTO_USER);
        orderPay.setCreated(now);
        orderPay.setUpdatedby(OperatorConstants.AUTO_USER);
        orderPay.setUpdated(now);
        return orderPay;
    }
    
    public static Payment createPayment(String id) throws Exception{
        Payment payment = new Payment();
        payment.setPaymentId(id);
        payment.setPaymentStatus(PaymentStatus.AWAIT.name());
        DateTime now = DateTime.now();
        payment.setCreatedby(OperatorConstants.AUTO_USER);
        payment.setCreated(now);
        payment.setUpdatedby(OperatorConstants.AUTO_USER);
        payment.setUpdated(now);
        return payment;
    }
    
    public static Account createAccount(String id) throws Exception{
        Account account = new Account();
        account.setAccountId(id);
        account.setAmount(0);
        account.setAvailableAmount(0);
        account.setFrozenAmount(0);
        account.setActive(true);
        DateTime now = DateTime.now();
        account.setCreatedby(OperatorConstants.AUTO_USER);
        account.setCreated(now);
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        account.setUpdated(now);
        return account;
    }
    
    public static CouponAccount createCouponAccount(String id) throws Exception {
        CouponAccount account = new CouponAccount();
        account.setCouponAccountId(id);
        account.setAmount(0);
        account.setAvailableAmount(0);
        account.setFrozenAmount(0);
        account.setActive(true);
        DateTime now = DateTime.now();
        account.setCreatedby(OperatorConstants.AUTO_USER);
        account.setCreated(now);
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        account.setUpdated(now);
        return account;
    }

    public static CouponItem createCouponItem(String id) throws Exception{
        CouponItem item = new CouponItem();
        item.setCouponItemId(id);
        item.setStatus(CouponItemStatus.AVAILABLE.name());
        DateTime now = DateTime.now();
        item.setGrantDate(now);
        item.setCreatedby(OperatorConstants.AUTO_USER);
        item.setCreated(now);
        item.setUpdatedby(OperatorConstants.AUTO_USER);
        item.setUpdated(now);
        return item;
    }
    
    public static Trade createTrade(String id) throws Exception{
        Trade trade = new Trade();
        trade.setTradeId(id);
        trade.setTradeStatus(TradeStatus.REQUEST.name());
        DateTime now = DateTime.now();
        trade.setCreatedby(OperatorConstants.AUTO_USER);
        trade.setCreated(now);
        trade.setUpdatedby(OperatorConstants.AUTO_USER);
        trade.setUpdated(now);
        return trade;
    }
    
    public static AccountHistory createAccountHistory(String id) throws Exception{
        AccountHistory accountHistory = new AccountHistory();
        accountHistory.setAccountHistoryId(id);
        DateTime now = DateTime.now();
        accountHistory.setOperateDate(now);
        accountHistory.setCreatedby(OperatorConstants.AUTO_USER);
        accountHistory.setCreated(now);
        accountHistory.setUpdatedby(OperatorConstants.AUTO_USER);
        accountHistory.setUpdated(now);
        return accountHistory;
    }
}
