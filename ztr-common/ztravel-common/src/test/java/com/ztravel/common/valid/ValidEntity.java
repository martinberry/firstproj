package com.ztravel.common.valid;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.ztravel.common.valid.constraints.FieldMatch;


@FieldMatch(first="passsword", second="confirmPassword", message="两次密码不一致")
public class ValidEntity {
	
	
	@NotEmpty(message="姓名不能为空")
	private String name;
	
	@Max(value=100l)
	@Min(value=10l)
	private Integer age;
	
	
	@NotEmpty(message="密码不能那个为空")
	private String passsword;
	
	@NotEmpty(message="确认密码不能为空")
	private String confirmPassword;
	
	
	@Valid
	private ValidSubClass validSubClass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public ValidSubClass getValidSubClass() {
		return validSubClass;
	}

	public void setValidSubClass(ValidSubClass validSubClass) {
		this.validSubClass = validSubClass;
	}

	public String getPasssword() {
		return passsword;
	}

	public void setPasssword(String passsword) {
		this.passsword = passsword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	

}
