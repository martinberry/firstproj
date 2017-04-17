package com.ztravel.operator.financeMantain.vo;


public class AccountHistoryVo {

	   private String memberId;
	   private String mid;
	   private String memberName;
	    private String orderId;
	    private String productType;
	    private String accountType;
	    private long amount;
	    private String inoutType;
	    private String inoutDetailType;
	    private String inCome;
	    private  String outGo;
	    private String operateDate;
	    private String orderNo;
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
		public long getAmount() {
			return amount;
		}
		public void setAmount(long amount) {
			this.amount = amount;
		}
		public String getMid() {
			return mid;
		}
		public void setMid(String mid) {
			this.mid = mid;
		}
		public String getMemberName() {
			return memberName;
		}
		public void setMemberName(String memberName) {
			this.memberName = memberName;
		}
		public String getOperateDate() {
			return operateDate;
		}
		public void setOperateDate(String operateDate) {
			this.operateDate = operateDate;
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
		public String getInCome() {
			return inCome;
		}
		public void setInCome(String inCome) {
			this.inCome = inCome;
		}
		public String getOutGo() {
			return outGo;
		}
		public void setOutGo(String outGo) {
			this.outGo = outGo;
		}
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
}
