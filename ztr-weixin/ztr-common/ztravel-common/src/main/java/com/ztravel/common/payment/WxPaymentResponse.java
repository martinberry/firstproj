/**
 * 
 */
package com.ztravel.common.payment;

import com.ztravel.common.rpc.CommonResponse;


/**
 * @author haofan.wan
 *
 */
public class WxPaymentResponse extends CommonResponse{
    private String value ;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
