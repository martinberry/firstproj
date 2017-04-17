package com.ztravel.common.valid;

import java.util.Set;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;

/**
 * 后台校验
 * 
 * <p>JSR-303 validator
 * <p>http://blog.trifork.com/2009/08/04/bean-validation-integrating-jsr-303-with-spring/
 * <p>http://stackoverflow.com/questions/1972933/cross-field-validation-with-hibernate-validator-jsr-303
 * @author liuzhuo
 */
public class BasicValidator {
	
	private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	private static final Validator validator = validatorFactory.getValidator();
	
	public static <T> ValidResult valid(T clazz) {
		Set<ConstraintViolation<T>> violations = validator.validate(clazz);
		for(ConstraintViolation<T> violation : violations) {
			return ValidResult.instance(false, violation.getMessage());
		}
		return ValidResult.instance(true, "");

	}

}
