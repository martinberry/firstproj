
var realNameReg=/^([a-zA-Z0-9|\u4e00-\u9fa5|.]){1,10}$/;
var userNameReg=/^([a-zA-Z0-9|\u4e00-\u9fa5|.]){1,20}$/;
var mobileReg=/^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
var employeeCodeReg=/^([a-zA-Z0-9|\u4e00-\u9fa5|.]){1,10}$/;
var passwordReg=/^[A-Za-z0-9]{6,28}$/;
var emailReg = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;

$(function () {

	$(".modal").delegate("form", "focusin", function(){
		$(this).find(".verifyStyle").hide();
	});

});

    function validate(data,section){

    	if (!realNameReg.test(data.realName)) {
    		error( '员工姓名限制1-10个中英文字符或数字',section);
    		return false;
    	} else {
    		section.find(".verifyStyle").hide();
    	}

    	if (!userNameReg.test(data.userName)) {
    		error( '用户名限制1-20个中英文字符或数字',section);
    		return false;
    	} else {
    		section.find(".verifyStyle").hide();
    	}

    	if (data.mobile !='' && !mobileReg.test(data.mobile)) {
    		error( '手机号输入格式有误，请重新输入',section);
    		return false;
    	} else {
    		section.find(".verifyStyle").hide();
    	}

    	if (data.employeeCode !='' && !employeeCodeReg.test(data.employeeCode)) {
    		error( '工号限制1-10个中英文字符或数字',section);
    		return false;
    	} else {
    		section.find(".verifyStyle").hide();
    	}

    if (data.password !='' && !passwordReg.test(data.password)) {
		error( '登录密码输入格式有误，请重新输入',section);
		return false;
	} else {
		section.find(".verifyStyle").hide();
	}

    if (data.password != data.confirmPassword) {
    	error('两次输入的密码不匹配',section);
		return false;
	}else {
		section.find(".verifyStyle").hide();
	}



    if (data.email !='' && !emailReg.test(data.email)) {
    	error('邮箱输入格式有误，请重新输入',section);
		return false;
	}else {
		section.find(".verifyStyle").hide();
	}

    if (data.roleId=="") {
		error( '请选择角色',section);
		return false;
	} else {
		section.find(".verifyStyle").hide();
	}

	return true;
}

    function error(message,section){
    	section.find(".verifyStyle").children("span").html(message);
        section.find(".verifyStyle").show();
    }