/**
 * 
 */
package com.ztravel.payment.po;

import org.joda.time.DateTime;

import com.ztravel.common.entity.AbstractBase;

/**
 * @author zuoning.shen
 *
 */
public class AccountHistory extends AbstractBase {
    private String accountHistoryId;
    private String memberId;
    private String orderId;
    private String productType;
    private String accountType;
    private long amount;
    private String inoutType;
    private String inoutDetailType;
    private DateTime operateDate;
    public String getAccountHistoryId() {
        return accountHistoryId;
    }
    public void setAccountHistoryId(String accountHistoryId) {
        this.accountHistoryId = accountHistoryId;
    }
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
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }
    public String getInoutType() {
        return inoutType;
    }
    public void setInoutType(String inoutType) {
        this.inoutType = inoutType;
    }
    public String getInoutDetailType() {
        return inoutDetailType;
    }
    public void setInoutDetailType(String inoutDetailType) {
        this.inoutDetailType = inoutDetailType;
    }
    public DateTime getOperateDate() {
        return operateDate;
    }
    public void setOperateDate(DateTime operateDate) {
        this.operateDate = operateDate;
    }
}
