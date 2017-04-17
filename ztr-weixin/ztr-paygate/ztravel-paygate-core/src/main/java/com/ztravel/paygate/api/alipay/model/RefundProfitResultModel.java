package com.ztravel.paygate.api.alipay.model;
/**
 * 退分润结果
 * @author dingguangxian
 *
 */
public class RefundProfitResultModel {
	private String partner;//商户号
	private String partnerAccount;//商户账户名
	private String sharePartner;//分润方商户号
	private String shareAccount;//分润方账户名
	private long amount;//退分润金额
	private String status;//退分润处理结果

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(String partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public String getSharePartner() {
		return sharePartner;
	}

	public void setSharePartner(String sharePartner) {
		this.sharePartner = sharePartner;
	}

	public String getShareAccount() {
		return shareAccount;
	}

	public void setShareAccount(String shareAccount) {
		this.shareAccount = shareAccount;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
