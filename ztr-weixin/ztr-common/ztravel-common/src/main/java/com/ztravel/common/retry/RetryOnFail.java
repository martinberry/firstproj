package com.ztravel.common.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zuoning.shen
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryOnFail {
	int retryTimes() default 0;

	long timeout() default 0;

	Class<? extends Throwable>[] retryFor() default {};

}
