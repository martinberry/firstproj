package com.ztravel.order.po;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.VoucherOrderStatus;

/**
 * 代金券联合支付订单
 * @author haofan.wan
 *
 */

public class VoucherCombineOrder {
	private String combineOrderId ;
	private long payAmount ;
	private PaymentType payType ;
	private String paySerialNum ;
	private long refundedAmount ;
	private VoucherOrderStatus status ;
	private String createdBy ;
	private String updatedBy ;
	private DateTime created ;
	private DateTime updated ;
    private long itemPrice ;
    private int num ;
    private String mobile;

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public DateTime getCreated() {
		return created == null ? null : created.toDateTime(DateTimeZone.forOffsetHours(8)) ;
	}
	public void setCreated(DateTime created) {
		this.created = created;
	}
	public DateTime getUpdated() {
		return updated == null ? null : updated.toDateTime(DateTimeZone.forOffsetHours(8)) ;
	}
	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}
	public String getCombineOrderId() {
		return combineOrderId;
	}
	public void setCombineOrderId(String combineOrderId) {
		this.combineOrderId = combineOrderId;
	}
	public PaymentType getPayType() {
		return payType;
	}
	public void setPayType(PaymentType payType) {
		this.payType = payType;
	}
	public String getPaySerialNum() {
		return paySerialNum;
	}
	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}
	public VoucherOrderStatus getStatus() {
		return status;
	}
	public void setStatus(VoucherOrderStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "VoucherCombineOrder [combineOrderId=" + combineOrderId
				+ ", payType=" + payType + ", paySerialNum=" + paySerialNum + ", payAmount=" + payAmount
				+ ", status=" + status + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", created=" + created == null ? null : created.toDateTime(DateTimeZone.forOffsetHours(8))
				+ ", updated=" + updated == null ? null : updated.toDateTime(DateTimeZone.forOffsetHours(8)) + "]";
	}
	public long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}
    public long getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

	public long getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(long refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	
}