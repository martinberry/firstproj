package com.ztravel.common.bean;

public class AjaxResponse {
	
	public String res_code;
	
	public String res_msg;

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_msg() {
		return res_msg;
	}

	public void setRes_msg(String res_msg) {
		this.res_msg = res_msg;
	}
	
	public AjaxResponse() {
		
	}
	
	public AjaxResponse(String res_code, String res_msg) {
		this.res_code = res_code;
		this.res_msg = res_msg;
	}

	
	public static AjaxResponse instance(String res_code, String res_msg) {
		return new AjaxResponse(res_code, res_msg);
	}
}
