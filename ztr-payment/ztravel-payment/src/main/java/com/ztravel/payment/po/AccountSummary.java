/**
 * 
 */
package com.ztravel.payment.po;


/**
 * @author zuoning.shen
 *
 */
public class AccountSummary {
    private String memberId;
    private String accountType;
    private long amount;
    private long availableAmount;
    private long frozenAmount;
    private boolean isActive;
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
