package com.ztravel.sso.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 白名单注册
 * @author liuzhuo
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD })
public @interface RegWhilteListFilter {
	
	String desc() default "白名单注册";

}
