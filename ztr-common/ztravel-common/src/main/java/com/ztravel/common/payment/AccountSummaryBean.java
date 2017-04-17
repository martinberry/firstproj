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
public class AccountSummaryBean {
    private String memberId;
    private AccountType accountType;
    private long amount;
    private long availableAmount;
    private long frozenAmount;
    private AccountStatus accountStatus;
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
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }
    public long getAvailableAmount() {
        return availableAmount;
    }
    public void setAvailableAmount(long availableAmount) {
        this.availableAmount = availableAmount;
    }
    public long getFrozenAmount() {
        return frozenAmount;
    }
    public void setFrozenAmount(long frozenAmount) {
        this.frozenAmount = frozenAmount;
    }
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
