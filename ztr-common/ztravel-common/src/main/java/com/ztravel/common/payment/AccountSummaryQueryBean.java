/**
 * 
 */
package com.ztravel.common.payment;

import com.ztravel.common.enums.AccountStatus;
import com.ztravel.common.enums.AccountType;

/**
 * @author zuoning.shen
 *
 */
public class AccountSummaryQueryBean {
    private String memberId;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private int pageNo = -1;
    private int pageSize = -1;
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
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
