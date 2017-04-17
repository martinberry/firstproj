/**
 *
 */
package com.ztravel.member.opera.enums;

/**
 * @author
 *
 */
public enum LatestLoginPeriodEnum {
	ONE_WEEK("one_week"),
	ONE_MONTH("one_month"),
	THREE_MONTH("three_month"),
	SIX_MONTH("six_month"),
	ONE_YEAR("one_year");

	private String value;

	private LatestLoginPeriodEnum(String val){
		this.value = val;
	}

	public String value(){
		return this.value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
