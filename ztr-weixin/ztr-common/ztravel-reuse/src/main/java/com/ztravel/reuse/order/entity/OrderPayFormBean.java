package com.ztravel.reuse.order.entity;
import com.ztravel.common.payment.OrderPayBean;

/**
 * @author bzhou
 *
 */
public class OrderPayFormBean extends OrderPayBean{

  private String checkSum;

  private String orderNo;


	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
