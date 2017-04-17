/**
 * 
 */
package com.ztravel.payment.po;

import com.ztravel.common.entity.AbstractBase;

/**
 * @author zuoning.shen
 *
 */
public class Account extends AbstractBase{
    private String accountId;
    private String memberId;
    private long amount;
    private long availableAmount;
    private long frozenAmount;
    private boolean isActive;
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
