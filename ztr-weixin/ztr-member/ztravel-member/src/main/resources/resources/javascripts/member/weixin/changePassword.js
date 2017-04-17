var oldPwdEmptyErrorHint = "原密码不能为空";
var newPwdEmptyErrorHint = "新密码不能为空";
var newPwdRetypeEmptyErrorHint = "确认密码不能为空";
var newPwdNotEqualErrorHint = "新密码两次输入不一致";
var invalidPwdErrorHint = "密码为8-28位数字、字母或常用字符组合";
var weakPwdHint = "<span class=\"type02\">弱</span>";
var normalPwdHint = "<span class=\"type03\">中</span>";
var strongPwdHint = "<span class=\"type01\">强</span>";

$(function(){
	//新密码强弱校验
	$("#newPassword").keyup(function(){
		var newPwd = $(this).val();
		var pwdLevel = checkPassword(newPwd);
		switch(pwdLevel){
		case -1:    //格式不合法
			//alert(invalidPwdErrorHint);
			$(".flag-box").html(weakPwdHint);
			break;
		case 0:
			$(".flag-box").html(weakPwdHint);
			break;
		case 1:
			$(".flag-box").html(normalPwdHint);
			break;
		case 2:
			$(".flag-box").html(strongPwdHint);
			break;
		}
	});

	//提交
	$("#submitPwdChange").click(function(){
		var oldPwd = $("#oldPassword").val();
		var newPwd = $("#newPassword").val();
		var newPwdRetype = $("#newPasswordRetype").val();
		//原密码
		if( oldPwd == "" ){
			errorTipsWindow(oldPwdEmptyErrorHint);
			return;
		}else{
			if( checkPassword(oldPwd) == -1 ){
				errorTipsWindow(invalidPwdErrorHint);
				return;
			}
		}
		//新密码
		if( newPwd == "" ){
			errorTipsWindow(newPwdEmptyErrorHint);
			return;
		}else{
			if( checkPassword(newPwd) == -1 ){
				errorTipsWindow(invalidPwdErrorHint);
				return;
			}
		}
		//确认密码
		if( newPwdRetype == "" ){
			errorTipsWindow(newPwdRetypeEmptyErrorHint);
			return;
		}else{
			if( checkPassword(newPwdRetype) == -1 ){
				errorTipsWindow(invalidPwdErrorHint);
				return;
			}
		}
		if( newPwd != newPwdRetype ){
			errorTipsWindow(newPwdNotEqualErrorHint);
			return;
		}

		$.ajax({
			type: "POST",
			url: basepath + "/usercenter/weixin/submitPwdChange?oldPwd=" + oldPwd + "&newPwd=" + newPwd + "&newPwdRetype=" + newPwdRetype,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success: function(resp){
				if( resp.res_code == "SW_MEMB_1018" ){   //成功
					$("#alert-dialog a").attr("onclick", "forward()");
					errorTipsWindow(resp.res_msg);
				}else if( resp.res_code == "FW_MEMB_1014" || resp.res_code == "FW_MEMB_1016" || resp.res_code == "FW_MEMB_1017" ){  //错误提示
					errorTipsWindow(resp.res_msg);
				}else if( resp.res_code == "FW_MEMB_1015" || resp.res_code == "FW_MEMB_1019" ){   //异常
					errorTipsWindow(resp.res_msg);

				}
			}
		});

	});

});

//错误弹框
function errorTipsWindow(errorText){
	$("#alert-dialog > p").html(errorText);
	$("#alert-dialog").popup();
	$("#alert-dialog").popup("open");
	
}
function forward(){
	$("#alert-dialog").popup("close");
	setTimeout(function(){
		window.location.href = basepath + "/usercenter/weixin/index";
	}, 500);
	
}