var disappear = '' ;
var mobile_hint_formaterror = '您输入的手机号有误' ;
var mobile_hint_isalreadyexists = '您的手机号已经注册' ;
var verifycode_hint_wrongcode = '您输入的验证码有误' ;
var verifycode_hint_retry = '验证码发送失败请重试' ;
var register_hint_programerror = '请再点一下～～～' ;



$(function(){

	$("#mobileInputer").val("") ;
	$("#verificationCodeInputer").val("") ;
	toggleErrorHint(disappear) ;
	keepLongPoll();

	$("#mobileInputer").blur(function(){
		var mobile = trim("#mobileInputer") ;
		if(!isMobile.test(mobile) && mobile != ''){
			toggleErrorHint(disappear) ;
			toggleErrorHint(mobile_hint_formaterror) ;
		}else{
			if(mobile != ''){
				$.ajax({
				    type: 'POST',
				    url: ssoServer + '/sso/isMobileAlreadyExists' ,
				    data: 'mobile=' + mobile ,
				    dataType: 'json' ,
				    success: function(data){
				    	if(data.res_code == 'EF_MEMB_1002'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint(mobile_hint_isalreadyexists) ;
				    	}else if(data.res_code == 'EF_MEMB_1001'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint(mobile_hint_formaterror) ;
				    	}else if(data.res_code == 'EF_MEMB_1003'){
				    		toggleErrorHint(disappear) ;
				    	}else{
				    		toggleErrorHint(disappear) ;
				    	}
				    }
				});
			}else{
				if(isErrorHintDisplay()){
					toggleErrorHint(disappear) ;
				}
			}
		}
	});

	//修改密码强弱图标
	$("#passwordInputer").bind('input propertychange', function() {
		var password = trim($("#passwordInputer")) ;

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

	$('#verificationCodeInputer').bind('input propertychange', function() {
		toggleErrorHint(disappear) ;
		toggleRegisterClass() ;
	});

	$(".view-pwd").click(function(){
        $(this).toggleClass("active");

        if ($(this).hasClass("active")) {
            $(this).parent(".componentInput").find("input").attr("type", "text");
        } else {
            $(this).parent(".componentInput").find("input").attr("type", "password");
        }
    });

	$('#verificationCodeInputer').blur(function() {
		if(trim("#verificationCodeInputer").length > 0){
			$.ajax({
			    type: 'POST',
			    url: ssoServer + '/sso/checkVerifyCode' ,
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
			return ;
		}
		if(!$(this).hasClass("retry") && !verifycode_lock) {
			verifycode_lock = true ;
			$(".getVerificationCode").addClass("retry");
    		$(".getVerificationCode").html("<span>60</span>s后重新尝试")
			var t = timer(59) ;
			$.ajax({
			    type: 'POST',
			    url: ssoServer + '/sso/sendRegisterSms' ,
			    data: 'mobile=' + trim("#mobileInputer") ,
			    dataType: 'json' ,
			    success : function(data){
			    	if(data.res_code == 'EF_MEMB_1002'){
			    		toggleErrorHint(disappear) ;
			    		toggleErrorHint(mobile_hint_isalreadyexists) ;
			    		manulDestoryTimer(t) ;
			    	}else if(data.res_code == 'EF_MEMB_1010'){
			    		toggleErrorHint(disappear) ;
			    		toggleErrorHint(verifycode_hint_retry) ;
			    		manulDestoryTimer(t) ;
			    	}else if(data.res_code == 'EF_MEMB_1017'){
			    		toggleErrorHint(disappear) ;
			    		toggleErrorHint(mobile_hint_formaterror) ;
			    		manulDestoryTimer(t) ;
			    	}else if(data.res_code == 'SF_MEMB_1010'){
			    	}
			    	verifycode_lock = false ;
			    },
			    error : function(data){
			    	verifycode_lock = false ;
			    	manulDestoryTimer(t) ;
			    }
			});
		}
	});

	var submit_lock = false ;
	$(".bigOrangeBtn").click(function(){
		var mobile = trim("#mobileInputer") ;
		var verifyCode = trim("#verificationCodeInputer") ;
		var password = trim($("#passwordInputer")) ;
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
			if(!isPassword.test(password)){
				toggleErrorHint(disappear) ;
				toggleErrorHint(password_hint_error) ;
				return ;
			}else{
				toggleErrorHint(disappear) ;
			}

			if(!submit_lock){
				submit_lock = true ;
				$.ajax({
				    type: 'POST',
				    url: ssoServer + '/sso/doRegister' ,
				    data: 'mobile=' + mobile + "&verifyCode=" + verifyCode + "&password=" + password ,
				    dataType: 'json' ,
				    success: function(data){
				    	submit_lock = false ;
				    	if(data.res_code == 'EF_MEMB_1002'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint(mobile_hint_isalreadyexists) ;
				    	}else if(data.res_code == 'EF_MEMB_1011'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint(verifycode_hint_wrongcode) ;
				    	}else if(data.res_code == 'EF_MEMB_1012' || data.res_code == 'EF_MEMB_1000'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint(register_hint_programerror) ;
				    	}else if(data.res_code == 'EF_MEMB_1024'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint(password_hint_error) ;
				    	}else if(data.res_code == 'NOT_IN_WHITE_LIST'){
				    		toggleErrorHint(disappear) ;
				    		toggleErrorHint("非认证内测人员不能注册") ;
				    	}else if(data.res_code == 'SF_MEMB_1000'){
				    		toggleErrorHint(disappear) ;
				    		window.location.href = ssoServer + '/sso/improvePersonalData' ;
				    	}
				    },
				    error : function(data){
				    	submit_lock = false ;
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
	return timer ;
}

function destoryTimer(timer,intDiff){
	if(intDiff <= 0){
		manulDestoryTimer(timer) ;
	}
}

function manulDestoryTimer(timer){
		clearInterval(timer) ;
		$(".getVerificationCode.retry").removeClass("retry");
		$(".getVerificationCode").html("获取短信验证码") ;
}

var keepLongPollTimer ;
function keepLongPoll(requestUrl,callback){
	keepLongPollTimer = setInterval("keepLongPollProxy()", 1000) ;
}

function keepLongPollProxy(){
	$.ajax({
        url: ssoServer + "/sso/unloginLongPoll?auto=true",
        type:"GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data, textStatus) {
            if (textStatus == "success") {// 请求成功
            	if(data.res_code == "SF_LONG_POLL_USER_LOGINED_001"){//用户已登陆
            		if(typeof(callback) == "function"){
                		callback();
                	}
            	}else if(data.res_code == "SF_LONG_POLL_OLD_USER_LOGIN_001"){//老用户扫码
            		var member = $.parseJSON(data.member) ;
            		if (!member.isActive) {
            			popNotActive() ;
            		} else {
            			var url = memberServer + "/home";
                		var $form = $('<form action="'+ url +'" method="get" style="display:none;"></form>');
            			$form.appendTo('body');
            			$form.submit();
            		}
            	}else if(data.res_code == "SF_LONG_POLL_NEW_USER_LOGIN_001"){
            		var url = memberServer + "/member/tobind";
            		var $form = $('<form action="'+ url +'" method="post" style="display:none;"></form>');
        			$form.append('<input name="openId" value="'+ data.openId +'"/>');
        			$form.append('<input name="headImgUrl" value="'+ data.headImgUrl +'"/>');
        			$form.append('<input name="nickName" value="'+ data.nickName +'"/>');
        			$form.appendTo('body');
        			$form.submit();
            	}
            }
        }
    }) ;
}
