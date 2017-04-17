var disappear = '' ;
var hint_verify_code = '您输入的验证码有误' ;
var mobile_hint_formaterror = '您输入的手机号有误' ;

var mobile_hint_error = '请再点一下～～～' ;
var mobile_hint_notfound = '手机号码未注册' ;

$(function(){

	//提交
	var submit_lock = false ;
	$(".bigBlue11b9b7Btn").click(function(){
		var mobile = trim("#mobileInputer") ;
		var verifyCode = trim("#verificationCodeInputer") ;
		if(!submit_lock){
			submit_lock = true ;
			$.ajax({
				type: 'POST',
				url: ssoServer + '/sso/findPasswordVerifyMobileCode' ,
				data: 'mobile=' + mobile + "&verifyCode=" + verifyCode ,
				dataType: 'json' ,
				success: function(data){
					submit_lock = false ;
					if(data.res_code == 'SF_MEMB_1006'){
						window.location.href = ssoServer + '/sso/findPasswordNewPassword' ;
					}else if(data.res_code == 'EF_MEMB_1011'){
						toggleErrorHint(disappear) ;
						toggleErrorHint(hint_verify_code) ;
					}else if(data.res_code == 'EF_MEMB_1017'){
						toggleErrorHint(disappear) ;
						toggleErrorHint(mobile_hint_formaterror) ;
					}
				},
				error:function(data){
					submit_lock = false ;
				}
			});
		}
	});

	$("#mobileInputer").blur(function(){
		var mobile = trim("#mobileInputer") ;
		if(!isMobile.test(mobile) && mobile != ''){
			toggleErrorHint(disappear) ;
			toggleErrorHint(mobile_hint_formaterror) ;
		}else{
			if(isErrorHintDisplay()){
				toggleErrorHint(disappear) ;
			}
		}
	});

	//获取短信验证码
	var verifycode_lock = false ;
	$(".getVerificationCode").click(function(){
		var mobile = trim("#mobileInputer") ;
		if(!isMobile.test(mobile)){
			toggleErrorHint(disappear) ;
    		toggleErrorHint(mobile_hint_formaterror) ;
			return ;
		}
		if(!$(this).hasClass("retry") && !verifycode_lock) {
			verifycode_lock = true ;
			$.ajax({
			    type: 'POST',
			    url: ssoServer + '/sso/sendFindPasswordSms' ,
			    data: 'mobile=' + trim("#mobileInputer") ,
			    dataType: 'json' ,
			    success: function(data){
			    	verifycode_lock = false ;
			    	if(data.res_code == 'EF_MEMB_1036'){
			    		toggleErrorHint(disappear) ;
			    		toggleErrorHint(mobile_hint_notfound) ;
			    	}else if(data.res_code == 'EF_MEMB_1010'){
			    		toggleErrorHint(disappear) ;
			    		toggleErrorHint(mobile_hint_error) ;
			    	}else if(data.res_code == 'SF_MEMB_1010'){
			    		toggleErrorHint(disappear) ;
			    		$(".getVerificationCode").addClass("retry");
			    		$(".getVerificationCode").html("<span>60</span>s后重新尝试")
						timer(59) ;
			    	}
			    },
			    error : function(data){
			    	verifycode_lock = false ;
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
		$(".getVerificationCode.retry").find("span").html(second) ;
		destoryTimer(timer,intDiff) ;
		intDiff--;
	}, 1000);
}

function destoryTimer(timer,intDiff){
	if(intDiff <= 0){
		clearInterval(timer) ;
		$(".getVerificationCode.retry").removeClass("retry");
		$(".getVerificationCode").html("获取短信验证码") ;
	}
}