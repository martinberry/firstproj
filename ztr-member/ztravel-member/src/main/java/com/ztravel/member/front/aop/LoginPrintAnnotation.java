package com.ztravel.member.front.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 通过注解来区分不同的切面业务,需要aop的方法上 加上注解即可
 * @author liuzhuo
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD })
public @interface LoginPrintAnnotation {
	
	String desc() default "登录打印aop";

}
