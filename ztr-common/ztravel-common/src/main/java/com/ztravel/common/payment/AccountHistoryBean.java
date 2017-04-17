/**
 *
 */
package com.ztravel.common.payment;

import org.joda.time.DateTime;

import com.ztravel.common.enums.AccountType;
import com.ztravel.common.enums.InoutDetailType;
import com.ztravel.common.enums.InoutType;
import com.ztravel.common.enums.ProductType;

/**
 * @author zuoning.shen
 *
 */
public class AccountHistoryBean {
    private String memberId;
    private String orderId;
    private ProductType productType;
    private AccountType accountType;
    private long amount;
    private InoutType inoutType;
    private InoutDetailType inoutDetailType;
    private DateTime operateDate;
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public ProductType getProductType() {
        return productType;
    }
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }
    public InoutType getInoutType() {
        return inoutType;
    }
    public void setInoutType(InoutType inoutType) {
        this.inoutType = inoutType;
    }
    public InoutDetailType getInoutDetailType() {
        return inoutDetailType;
    }
    public void setInoutDetailType(InoutDetailType inoutDetailType) {
        this.inoutDetailType = inoutDetailType;
    }
    public DateTime getOperateDate() {
        return operateDate;
    }
    public void setOperateDate(DateTime operateDate) {
        this.operateDate = operateDate;
    }
}
