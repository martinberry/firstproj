package com.ztravel.common.valid;

import org.hibernate.validator.constraints.NotEmpty;

public class ValidSubClass {
	
	@NotEmpty(message="subName不能为空")
	private String subName;

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}
	
	

}
