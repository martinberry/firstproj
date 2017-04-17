var disappear = '' ;
var hint_link_expire = '链接已失效，请重新获取' ;
var email_hint_Illegal = '邮箱格式有误' ;
var email_hint_not_found = '邮箱没有注册诶' ;
var email_hint_alreadysend = '邮件已经发送,请一小时后重试' ;

var email_hint_not_send = '邮件发送失败' ;

$(function(){

	//提交
	$(".bigBlue11b9b7Btn").click(function(){
		var email = trim("#emailInputer") ;
		if(!checkEmail(trim("#emailInputer"))){
			toggleErrorHint(disappear) ;
			toggleErrorHint(email_hint_Illegal) ;
			return ;
		}else{
			toggleErrorHint(disappear) ;
		}
		$.ajax({
		    type: 'POST',
		    url: ssoServer + '/sso/findPasswordEmail' ,
		    data: 'email=' + email ,
		    dataType: 'json' ,
		    success: function(data){
		    	if(data.res_code == 'SF_MEMB_1008'){
		    		window.location.href = ssoServer + '/sso/findPasswordEmailSuccess' ;
		    	}else if(data.res_code == 'EF_MEMB_1033'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(email_hint_not_found) ;
		    	}else if(data.res_code == 'EF_MEMB_1023'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(email_hint_not_send) ;
		    	}else if(data.res_code == 'EF_MEMB_1036'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(email_hint_alreadysend) ;
		    	}
		    }
		});
	});

});

function checkEmail(email){
	var reg = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
	return email.match(reg) != null;
}