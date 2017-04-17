package com.ztravel.common.exception;

/**
 * @author zuoning.shen
 *
 */
public class ShouldRetryException extends RuntimeException {
	public ShouldRetryException() {
	}

	public ShouldRetryException(String message) {
		super(message);
	}

	public ShouldRetryException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShouldRetryException(Throwable cause) {
		super(cause);
	}

	public ShouldRetryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
