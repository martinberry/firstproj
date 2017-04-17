package com.ztravel.paygate.core.exception;

import com.ztravel.paygate.core.enums.PaygateError;

/**
 * 
 * @author dingguangxian
 * 
 */
public class PaygateException extends RuntimeException {

	private static final long serialVersionUID = -6735194878166253700L;
	private PaygateError error;

	public PaygateException(PaygateError paygateError) {
		super("PaygateException:" + paygateError.code() + "," + paygateError.desc());
		this.error = paygateError;
	}

	public PaygateException(PaygateError paygateError, Throwable cause) {
		super("PaygateException:" + paygateError.code() + "," + paygateError.desc(), cause);
		this.error = paygateError;
	}

	public PaygateError getError() {
		return error;
	};
}
