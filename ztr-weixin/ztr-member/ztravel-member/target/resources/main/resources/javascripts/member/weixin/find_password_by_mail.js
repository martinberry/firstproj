var disappear = '' ;
var email_hint_Illegal = '邮箱格式有误' ;
var email_hint_not_found = '邮箱没有注册诶' ;
var email_hint_alreadysend = '邮件已经发送,请一小时后重试' ;

var email_hint_not_send = '邮件发送失败' ;
var lock = false ;

$(function(){


	//清空
	$(".row-close").click(function(){
		$("#emailInputer").val("") ;
	});

	//提交
	$(".link-fin").click(function(){
		var email = trim("#emailInputer") ;
		if(!checkEmail(trim("#emailInputer"))){
			$("#errHintMsgEmail").html(email_hint_Illegal);
    		$("#alert-dialog-email").popup();
    		$("#alert-dialog-email").popup("open");
			return ;
		}
		if(!lock){
			lock = true ;
			$.ajax({
			    type: 'POST',
			    url: wxServer + '/rl/findPasswordMail' ,
			    data: 'email=' + email ,
			    dataType: 'json' ,
			    success: function(data){
			    	lock = false ;
			    	if(data.res_code == 'SF_MEMB_1008'){
			    		$("#errHintMsgEmail").html("发送成功");
			    		$("#alert-dialog-email").popup();
			    		$("#alert-dialog-email").popup("open");
			    	}else if(data.res_code == 'EF_MEMB_1033'){
			    		$("#errHintMsgEmail").html(email_hint_not_found);
			    		$("#alert-dialog-email").popup();
			    		$("#alert-dialog-email").popup("open");
			    	}else if(data.res_code == 'EF_MEMB_1023'){
			    		$("#errHintMsgEmail").html(email_hint_not_send);
			    		$("#alert-dialog-email").popup();
			    		$("#alert-dialog-email").popup("open");
			    	}else if(data.res_code == 'EF_MEMB_1036'){
			    		$("#errHintMsgEmail").html(email_hint_alreadysend);
			    		$("#alert-dialog-email").popup();
			    		$("#alert-dialog-email").popup("open");
			    	}
			    },error: function(data){
			    	lock = false ;
			    }
			});
		}
	});

});

function checkEmail(email){
	var reg = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
	return email.match(reg) != null;
}