package com.ztravel.paygate.web.dto;

public interface PaygateErrorSupport {
	public void setRetCode(String retCode);

	public void setRetMsg(String retMsg);

	public String getRetCode();

	public String getRetMsg();
}
