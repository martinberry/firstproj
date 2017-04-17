var disappear = '' ;
var jumpTo = "" ;

$(function(){
	
	$("#ac-reset-ok").modal({
        backdrop:"static",
        keyboard: false,
        show: false
    });
	
	//修改密码强弱图标
	$("#againPasswordInputer").bind('input propertychange', function() {
		var password = trim($("#againPasswordInputer")) ;
		var lv = checkPassword(password) ;
		
		if(lv == -1){
			toggleErrorHint(disappear) ;
			toggleErrorHint(password_hint_error) ;
			$(".strength").attr('class', 'strength weak') ;
			$(".strength.weak").html("弱") ;
			return ;
		}
		
		if(lv == 0){
			$(".strength").attr('class', 'strength weak') ;
			$(".strength.weak").html("弱") ;
		}
		
		if(lv == 1){
			$(".strength").attr('class', 'strength centre') ;
			$(".strength.centre").html("中") ;
		}

		if(lv == 2){
			$(".strength").attr('class', 'strength strong') ;
			$(".strength.strong").html("强") ;
		}
		toggleErrorHint(disappear) ;
	});
	
	
	var submit_lock = false ;
	$(".bigBlue11b9b7Btn").click(function(){
		var password = $("#newPasswordInputer").val() ;
		if(!isPassword.test(password)){
			toggleErrorHint(disappear) ;
			toggleErrorHint(password_hint_error) ;
			return ;
		}else{
			toggleErrorHint(disappear) ;
		}
		var password2 = $("#againPasswordInputer").val() ;
		if(!isPassword.test(password2)){
			toggleErrorHint(disappear) ;
			toggleErrorHint(password_hint_error) ;
			return ;
		}else{
			toggleErrorHint(disappear) ;
		}
		if(password != password2){
			toggleErrorHint(disappear) ;
			toggleErrorHint(password_hint_error2) ;
			return ;
		}else{
			toggleErrorHint(disappear) ;
		}
		password = escape(password) ;
		password2 = escape(password2) ;
		if(!submit_lock){
			submit_lock = true ;
			$.ajax({
				type: 'POST',
				url: ssoServer + '/sso/resetPassword' ,
				data: "password=" + password + "&password2=" + password2 ,
				dataType: 'json' ,
				success: function(data){
					submit_lock = false ;
					if(data.res_code == 'EF_MEMB_1032'){
						toggleErrorHint(disappear) ;
						toggleErrorHint(password_hint_error) ;
					}else if(data.res_code == 'EF_MEMB_1031'){
						toggleErrorHint(disappear) ;
						toggleErrorHint(password_hint_error2) ;
					}else if(data.res_code == 'EF_MEMB_1030'){
						toggleErrorHint(disappear) ;
						toggleErrorHint("用户手机重置密码失败") ;
					}else if(data.res_code == 'SF_MEMB_1007'){
						toggleErrorHint(disappear) ;
						jumpTo = data.res_msg ;
						$("#ac-reset-ok").modal("show");
					}
				},
				error:function(data){
					submit_lock = false ;
				}
			});
		}
	});
})

function popCheck(){
	$('#ac-reset-ok').modal('hide');
	window.location.href = jumpTo ;
}

