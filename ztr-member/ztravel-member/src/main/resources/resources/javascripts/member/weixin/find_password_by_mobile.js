var disappear = '' ;
var mobile_hint_formaterror = '您输入的手机号有误' ;
var verifycode_hint_wrongcode = '您输入的验证码有误' ;
var verifycode_hint_retry = '验证码发送失败请重试' ;
var register_hint_programerror = '请再点一下～～～' ;

$(function(){

	//清空
	$(".row-close").click(function(){
		$("#mobileInputer").val("") ;
	});

	//produce verify code
	var verifycode_lock = false ;
	$(".checkCodeBtn").click(function(){
		var mobile = trim("#mobileInputer") ;
		if(!isMobile.test(mobile) && mobile != ''){
			$("#errHintMsgMobile").html(mobile_hint_formaterror);
    		$("#alert-dialog-mobile").popup();
    		$("#alert-dialog-mobile").popup("open");
			return ;
		}
		if(!$(this).hasClass("retry") && !verifycode_lock) {
			verifycode_lock = true ;
			$.ajax({
			    type: 'POST',
			    url: wxServer + '/rl/sendFindPasswordSms' ,
			    data: 'mobile=' + mobile ,
			    dataType: 'json' ,
			    success: function(data){
			    	verifycode_lock = false ;
			    	if(data.res_code == 'EF_MEMB_1036'){
			    		$("#errHintMsgMobile").html(mobile_hint_formaterror);
			    		$("#alert-dialog-mobile").popup();
			    		$("#alert-dialog-mobile").popup("open");
			    	}else if(data.res_code == 'EF_MEMB_1010'){
			    		$("#errHintMsgMobile").html(mobile_hint_formaterror);
			    		$("#alert-dialog-mobile").popup();
			    		$("#alert-dialog-mobile").popup("open");
			    	}else if(data.res_code == 'SF_MEMB_1010'){
			    		$(".checkCodeBtn").addClass("retry");
			    		$(".checkCodeBtn").html("<span>60</span>s后重新尝试")
						timer(59) ;
			    	}
			    },
			    error : function(data){
			    	verifycode_lock = false ;
			    }
			});
		}
	});

	//register
	var submit_lock = false ;
	$(".link-fin").click(function(){
		var mobile = trim("#mobileInputer") ;
		var verifyCode = trim("#verifyCodeInputer") ;
		if(!isMobile.test(mobile)){
			$("#errHintMsgMobile").html(mobile_hint_formaterror);
    		$("#alert-dialog-mobile").popup();
    		$("#alert-dialog-mobile").popup("open");
			return ;
		}
		if(verifyCode.length != 6){
			$("#errHintMsgMobile").html(verifycode_hint_wrongcode);
    		$("#alert-dialog-mobile").popup();
    		$("#alert-dialog-mobile").popup("open");
			return ;
		}
		if(!submit_lock){
			submit_lock = true ;
			$.ajax({
				type: 'POST',
				url: wxServer + '/rl/checkFindPasswordVerifyCode' ,
				data: 'mobile=' + mobile + "&verifyCode=" + verifyCode ,
				dataType: 'json' ,
				success: function(data){
					submit_lock = false ;
					if(data.res_code == 'SF_MEMB_1006'){
						window.location.href = wxServer + '/rl/resetPasswordByMobile' ;
					}else if(data.res_code == 'EF_MEMB_1011'){
						$("#errHintMsgMobile").html(verifycode_hint_wrongcode);
			    		$("#alert-dialog-mobile").popup();
			    		$("#alert-dialog-mobile").popup("open");
					}else if(data.res_code == 'EF_MEMB_1017'){
						$("#errHintMsgMobile").html(mobile_hint_formaterror);
			    		$("#alert-dialog-mobile").popup();
			    		$("#alert-dialog-mobile").popup("open");
					}
				},
				error:function(data){
					submit_lock = false ;
				}
			});
		}
	});

});

function timer(intDiff){
	var timer = window.setInterval(function(){
		var second = 0;//时间默认值
		if(intDiff > 0){
			second = Math.floor(intDiff) ;
		}
		if (second <= 9) second = '0' + second;
		$(".checkCodeBtn.retry").find("span").html(second) ;
		destoryTimer(timer,intDiff) ;
		intDiff--;
	}, 1000);
}

function destoryTimer(timer,intDiff){
	if(intDiff <= 0){
		clearInterval(timer) ;
		$(".checkCodeBtn.retry").removeClass("retry");
		$(".checkCodeBtn").html("获取验证码") ;
	}
}

