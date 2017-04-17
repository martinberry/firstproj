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
public class AccountHistoryQueryBean {
    private String memberId;
    private String orderId;
    private ProductType productType;
    private AccountType accountType;
    private InoutType inoutType;
    private InoutDetailType inoutDetailType;
    private DateTime conditionOperateFrom;
    private DateTime conditionOperateTo;
    private int pageNo = -1;
    private int pageSize = -1;
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
    public DateTime getConditionOperateFrom() {
        return conditionOperateFrom;
    }
    public void setConditionOperateFrom(DateTime conditionOperateFrom) {
        this.conditionOperateFrom = conditionOperateFrom;
    }
    public DateTime getConditionOperateTo() {
        return conditionOperateTo;
    }
    public void setConditionOperateTo(DateTime conditionOperateTo) {
        this.conditionOperateTo = conditionOperateTo;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
