var disappear = '' ;
var jumpTo = "" ;

$(function(){
	
	//修改密码强弱图标
	$("#newPasswordInputer").bind('input propertychange', function() {
		var password = trim($("#newPasswordInputer")) ;
		var lv = checkPassword(password) ;
		
		if(lv == -1){
			$("#stronger").removeClass().addClass('type03') ;
			$("#stronger").html("弱") ;
			return ;
		}
		
		if(lv == 0){
			$("#stronger").removeClass().addClass('type03') ;
			$("#stronger").html("弱") ;
		}
		
		if(lv == 1){
			$("#stronger").removeClass().addClass('type02') ;
			$("#stronger").html("中") ;
		}

		if(lv == 2){
			$("#stronger").removeClass().addClass('type01') ;
			$("#stronger").html("强") ;
		}
	});
	
	//修改密码强弱图标
	$("#newPasswordInputer").bind('blur', function() {
		var password = trim($("#newPasswordInputer")) ;
		var lv = checkPassword(password) ;
		if(lv == -1){
			toggleErrorHint(password_hint_error) ;
		}
	});
	
	
	var submit_lock = false ;
	$(".link-fin").click(function(){
		var password = $("#newPasswordInputer").val() ;
		if(!isPassword.test(password)){
			toggleErrorHint(password_hint_error) ;
			return ;
		}
		var password2 = $("#againPasswordInputer").val() ;
		if(!isPassword.test(password2)){
			toggleErrorHint(password_hint_error) ;
			return ;
		}
		if(password != password2){
			toggleErrorHint(password_hint_error2) ;
			return ;
		}
		password = escape(password) ;
		password2 = escape(password2) ;
		if(!submit_lock){
			submit_lock = true ;
			$.ajax({
				url: ssoServer + '/sso/wx/resetPasswordByMail' ,
				data: "password=" + password + "&password2=" + password2 + "&sid=" + $("#sid").val() ,
				dataType: 'jsonp' ,
				jsonp: 'callback' ,
				success: function(data){
					submit_lock = false ;
					if(data.res_code == 'SF_MEMB_1008'){
						$("#alert-dialog a").attr("onclick", "forward()");
						toggleErrorHint("密码重置成功");
					}else if(data.res_code == 'EF_MEMB_1032'){
						toggleErrorHint(password_hint_error) ;
					}else if(data.res_code == 'EF_MEMB_1031'){
						toggleErrorHint(password_hint_error2) ;
					}else if(data.res_code == 'EF_MEMB_1030'){
						toggleErrorHint("用户手机重置密码失败") ;
					}else if(data.res_code == 'SF_MEMB_1007'){
						jumpTo = data.res_msg ;
						$("#alert-dialog a").attr("onclick", "forward()");
						toggleErrorHint("密码重置成功");
					}
				},
				error:function(data){
					submit_lock = false ;
				}
			});
		}
	});
})
function forward(){
	$("#alert-dialog").popup("close");
	setTimeout(function(){
		window.location.href = wxServer + "/rl/torl";
	}, 500);
	
}