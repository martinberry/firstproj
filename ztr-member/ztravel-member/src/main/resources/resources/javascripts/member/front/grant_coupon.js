var disappear = '' ;
var mobile_hint_formaterror = '您输入的手机号有误' ;
var verifycode_hint_wrongcode = '您输入的验证码有误' ;
var verifycode_hint_retry = '验证码发送失败请重试' ;
var grant_hint_programerror = '领取失败,请再点一下～～～' ;


$(function(){

	$("#mobileInputer").val("") ;
	$("#verificationCodeInputer").val("") ;
	toggleErrorHint(disappear) ;

	$('#verificationCodeInputer').bind('input propertychange', function() {
		toggleErrorHint(disappear) ;
		toggleRegisterClass() ;
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

	$('#verificationCodeInputer').blur(function() {
		if(trim("#verificationCodeInputer").length > 0){
			$.ajax({
			    type: 'POST',
			    url: memberServer + '/grant/checkVerifyCode' ,
			    data: 'mobile=' + trim("#mobileInputer") + "&verifyCode=" + trim("#verificationCodeInputer") ,
			    dataType: 'json' ,
			    success : function(data){
			    	if(data.res_code == 'EF_MEMB_1011'){
			    		toggleErrorHint(disappear) ;
			    		toggleErrorHint(verifycode_hint_wrongcode) ;
			    	}else{
			    		toggleErrorHint(disappear) ;
			    	}
			    }
			});
		}
	});

	$('#mobileInputer').bind('input propertychange', function() {
		toggleRegisterClass() ;
	});

	var verifycode_lock = false ;
	$(".getVerificationCode").click(function(){
		var mobile = trim("#mobileInputer") ;
		if(!isMobile.test(mobile) && mobile != ''){
			toggleErrorHint(disappear) ;
			toggleErrorHint(mobile_hint_formaterror) ;
		}
		if(!$(this).hasClass("retry") && isMobile.test(mobile) && !verifycode_lock) {
			verifycode_lock = true ;
			$.ajax({
			    type: 'POST',
			    url: memberServer + '/grant/sendCouponSms' ,
			    data: 'mobile=' + trim("#mobileInputer") ,
			    dataType: 'json' ,
			    success : function(data){
			    	if(data.res_code == 'EF_MEMB_1010'){
			    		toggleErrorHint(disappear) ;
			    		toggleErrorHint(verifycode_hint_retry) ;
			    	}else if(data.res_code == 'SF_MEMB_1010'){
			    		$(".getVerificationCode").addClass("retry");
			    		$(".getVerificationCode").html("<span>60</span>s后重新尝试")
						timer(59) ;
			    	}
			    	verifycode_lock = false ;
			    }
			});
		}
	});

	var submit_lock = false ;
	$(".bigOrangeBtn").click(function(){
		var mobile = trim("#mobileInputer") ;
		var verifyCode = trim("#verificationCodeInputer") ;
		if($(this).hasClass("active")) {
			if(!isMobile.test(mobile) && mobile != ''){
				toggleErrorHint(disappear) ;
				toggleErrorHint(mobile_hint_formaterror) ;
				return ;
			}
			if(verifyCode.length != 6){
				toggleErrorHint(disappear) ;
				toggleErrorHint(verifycode_hint_wrongcode) ;
				return ;
			}

			if(!submit_lock){
				submit_lock = true ;
				$.ajax({
				    type: 'POST',
				    url: memberServer + '/grant/grantCoupon' ,
				    data: 'mobile=' + mobile + "&verifyCode=" + verifyCode ,
				    dataType: 'json' ,
				    success: function(data){
				    	submit_lock = false ;
				    	if(data.res_code == 'EF_MEMB_1011'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint(verifycode_hint_wrongcode) ;
				    	}else if(data.res_code == 'EF_MEMB_1012' || data.res_code == 'EF_MEMB_1000'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint(grant_hint_programerror) ;
				    	}else if(data.res_code == 'success'){
				    		toggleErrorHint(disappear) ;
				    		window.location.href = memberServer + '/grant/grantCouponSuccess' ;
				    	}
				    }
				});
			}
		}
	});
})

function toggleRegisterClass(){
	var mobile = trim("#mobileInputer") ;
	var verifyCode = trim("#verificationCodeInputer") ;
	if(verifyCode.length == 6 && isMobile.test(mobile)){
		$(".bigOrangeBtn").addClass("active") ;
	}else{
		$(".bigOrangeBtn").removeClass("active") ;
	}
}

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