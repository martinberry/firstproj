package com.ztravel.order.entity;

public class OrderCancelNeedToDoEntity {
	private Long alreadyRefundPrice ;
	
	private String needRefundTraceNum ;
	
	private String paymentType ;
	
	private boolean needTodo ;

	public String getNeedRefundTraceNum() {
		return needRefundTraceNum;
	}

	public void setNeedRefundTraceNum(String needRefundTraceNum) {
		this.needRefundTraceNum = needRefundTraceNum;
	}

	public Long getAlreadyRefundPrice() {
		return alreadyRefundPrice;
	}

	public void setAlreadyRefundPrice(Long alreadyRefundPrice) {
		this.alreadyRefundPrice = alreadyRefundPrice;
	}

	public boolean isNeedTodo() {
		return needTodo;
	}

	public void setNeedTodo(boolean needTodo) {
		this.needTodo = needTodo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
}
