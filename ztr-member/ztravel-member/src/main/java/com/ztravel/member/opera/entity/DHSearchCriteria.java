package com.ztravel.member.opera.entity;

import javax.validation.constraints.Pattern;
import com.ztravel.common.entity.PaginationEntity;

public class DHSearchCriteria extends PaginationEntity {

	/**
	 * 兑换单ID
	 */
	@Pattern(regexp = "^[0-9a-zA-Z]{0,18}$")
	private String dhId;
	/**
	 * 会员ID
	 */
	@Pattern(regexp = "^[0-9a-zA-Z]{0,8}$")
	private String memberId;

	/**
	 * 兑换状态
	 */
	private String dhStatus;
	/**
	 * 建单日期(from, to)
	 */
	private String pledhTimeFrom;

	private String pledhTimeTo;
	/**
	 * 订单金额下限
	 */
	@Pattern(regexp = "^\\d{0,7}\\.{0,1}\\d{0,2}$")
	private String dhPriceLowerBound;
	/**
	 * 订单金额上限
	 */
	@Pattern(regexp = "^\\d{0,7}\\.{0,1}\\d{0,2}$")
	private String dhPriceUpperBound;//"^\\d{0,7}\\.{0,1}\\d{0,2}$"




	public String getDhId() {
		return dhId;
	}
	public void setDhId(String dhId) {
		this.dhId = dhId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getDhStatus() {
		return dhStatus;
	}
	public void setDhStatus(String dhStatus) {
		this.dhStatus = dhStatus;
	}
	public String getPledhTimeFrom() {
		return pledhTimeFrom;
	}
	public void setPledhTimeFrom(String pledhTimeFrom) {
		this.pledhTimeFrom = pledhTimeFrom;
	}
	public String getPledhTimeTo() {
		return pledhTimeTo;
	}
	public void setPledhTimeTo(String pledhTimeTo) {
		this.pledhTimeTo = pledhTimeTo;
	}
	public String getDhPriceLowerBound() {
		return dhPriceLowerBound;
	}
	public void setDhPriceLowerBound(String dhPriceLowerBound) {
		this.dhPriceLowerBound = dhPriceLowerBound;
	}
	public String getDhPriceUpperBound() {
		return dhPriceUpperBound;
	}
	public void setDhPriceUpperBound(String dhPriceUpperBound) {
		this.dhPriceUpperBound = dhPriceUpperBound;
	}





}




