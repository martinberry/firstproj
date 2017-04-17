var mobile_hint_formaterror = '您输入的手机号有误' ;
var mobile_hint_isalreadyexists = '您的手机号已被注册' ;
var verifycode_hint_wrongcode = '您输入的验证码有误' ;
var verifycode_hint_retry = '验证码发送失败请重试' ;
var login_hint_fail = '用户名或密码错误' ;
var user_is_not_active = '咦？账号异常，请与客服联系~' ;
var register_hint_programerror = '请再点一下～～～' ;
var checkout_login = '您的手机号已注册，请输入密码登录' ;
var account_password_is_null = '输入的账号或密码不能为空' ;

$(function(){

	setBodyMinHeight();

	if($("#toLogin").val() == 'true'){
		showLogin() ;
	}

	//生成验证码
	$("body").delegate("#kaptchaImage","click",function () {
		produceKaptchaImage() ;
    });

	$(".mregisterBtn").click(function(){
		showRegister() ;
	});

	$(".mloginBtn").click(function(){
		showLogin() ;
	});

	//produce verify code
	var verifycode_lock = false ;
	$(".checkCodeBtn").click(function(){
		var mobile = trim(".registerFrame .mobileInputer") ;
		if(!isMobile.test(mobile) && mobile != ''){
			tipHintMsg(mobile_hint_formaterror);
			return ;
		}
		if(!$(this).hasClass("retry") && !verifycode_lock) {
			verifycode_lock = true ;
			$.ajax({
			    type: 'POST',
			    url: wxServer + '/rl/sendRegisterSms' ,
			    data: 'mobile=' + mobile ,
			    dataType: 'json' ,
			    success : function(data){
			    	if(data.res_code == 'EF_MEMB_1002'){
			    		showLogin() ;
			    		$(".loginFrame .mobileInputer").val(mobile) ;
			    		tipHintMsg(checkout_login);
			    	}else if(data.res_code == 'EF_MEMB_1010'){
						tipHintMsg(verifycode_hint_retry);
			    	}else if(data.res_code == 'EF_MEMB_1017'){
						tipHintMsg(mobile_hint_formaterror);
			    	}else if(data.res_code == 'SF_MEMB_1010'){
			    		$(".checkCodeBtn").addClass("retry");
			    		$(".checkCodeBtn").html("<span>60</span>s后重新尝试")
						timer(59) ;
			    	}
			    	verifycode_lock = false ;
			    },
			    error : function(data){
			    	verifycode_lock = false ;
			    }
			});
		}
	});

	//register
	var submit_lock = false ;
//	$(".registerFrame .bottom-btn").click(function(){
	$("body").delegate("#bottom-regist-btn","click",function(){
		var mobile = trim(".registerFrame .mobileInputer") ;
		var verifyCode = trim(".registerFrame .verifyCodeInputer") ;
		var password = trim($(".registerFrame .passwordInputer")) ;
		var backUrl =$("#backUrl").val();
		if(!isMobile.test(mobile)){
			tipHintMsg(mobile_hint_formaterror);
			return ;
		}
		if(verifyCode.length != 6){
			tipHintMsg(verifycode_hint_wrongcode);
			return ;
		}
		if(!isPassword.test(password)){
			tipHintMsg(password_hint_error);
			return ;
		}

		if(!submit_lock){
			submit_lock = true ;
			$.ajax({
			    url: ssoServer + '/sso/wx/doRegister' ,
			    data: 'mobile=' + mobile + "&verifyCode=" + verifyCode + "&password=" + password ,
			    dataType:'jsonp',
			    jsonp:'callback',
			    success: function(data){
			    	submit_lock = false ;
			    	if(data.res_code == 'EF_MEMB_1002'){
						tipHintMsg(mobile_hint_isalreadyexists);
			    	}else if(data.res_code == 'EF_MEMB_1011'){
			    		tipHintMsg(verifycode_hint_wrongcode);
			    	}else if(data.res_code == 'EF_MEMB_1012' || data.res_code == 'EF_MEMB_1000'){
			    		tipHintMsg(register_hint_programerror);
			    	}else if(data.res_code == 'EF_MEMB_1024'){
			    		tipHintMsg(password_hint_error);
			    	}else if(data.res_code == 'NOT_IN_WHITE_LIST'){
			    		tipHintMsg("非认证内测人员不能注册");
			    	}else if(data.res_code == 'SF_MEMB_1000'){
			    		window.location.href = wxServer + '/rl/improvePersonalData' ;
			    	}
			    },
			    error : function(data){
			    	submit_lock = false ;
			    }
			});
		}
	});

	//登陆
	$("body").delegate("#bottom-login-btn","click",function(){
		var account = trim(".loginFrame .mobileInputer") ;
		var password = trim(".loginFrame .passwordInputer") ;
		password = escape(password) ;
		var verifyCode = trim(".loginVerifyCodeInputer") ;
		var rememberMe = $("input[type='checkbox']").prop("checked") ;
		var backUrl =$("#backUrl").val();
		$.ajax({
		    url: ssoServer + '/sso/wx/login' ,
		    data: 'account=' + account + "&password=" + password + "&rememberMe=" + rememberMe + "&verifyCode=" + verifyCode ,
		    dataType:'jsonp',
		    jsonp:'callback',
		    success: function(data){
		    	if(data.res_code == 'SF_MEMB_1005'){
		    		_paq.push(['trackEvent', 'login', 'ztrlogin',account,'SUCCESS']);
		    	}else{
		    		_paq.push(['trackEvent', 'login', 'ztrlogin',account,'FAIL']);
		    	}
		    	if(data.res_code == 'EF_MEMB_1027'){
		    		produceKaptchaImage() ;
	    			$(".loginVerify").css("display", "") ;
		    		tipHintMsg(verifycode_hint_wrongcode);
		    	}else if(data.res_code == 'EF_MEMB_1024'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".loginVerify").css("display", "") ;
		    		}
		    		tipHintMsg(login_hint_fail);
		    	}else if(data.res_code == 'SF_MEMB_1053'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".loginVerify").css("display", "") ;
		    		}
		    		tipHintMsg(account_password_is_null);
		    	}else if(data.res_code == 'EF_MEMB_1026'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".loginVerify").css("display", "") ;
		    		}
		    		tipHintMsg(login_hint_fail);
		    	}else if(data.res_code == 'EF_MEMB_10261'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".loginVerify").css("display", "") ;
		    		}
		    		tipHintMsg(user_is_not_active);
		    	}else if(data.res_code == 'EF_MEMB_1028'){
		    		tipHintMsg(login_hint_fail);
		    	}else if(data.res_code == 'SF_MEMB_1005'){
		    		if (backUrl != undefined && backUrl != '') {
		    			window.location.href = backUrl;
		    		}else {
		    			window.location.href = wxServer + "/weixin/product/list" ;
		    		}
		    	}
		    }
		});
	}) ;

	//微信登陆
	$("body").delegate("#bottom-wxlogin-btn","click",function(){
		var backUrl =$("#backUrl").val();
		$.ajax({
		    url: ssoServer + '/sso/wx/loginByWx' ,
		    data: '' ,
		    dataType:'jsonp',
		    jsonp:'callback',
		    success: function(data){
		    	if(data.res_code == 'EF_MEMB_1026'){
		    		tipHintMsg(login_hint_fail);
		    	}else if(data.res_code == 'EF_MEMB_10261'){
		    		tipHintMsg(user_is_not_active);
		    	}else if(data.res_code == 'EF_MEMB_1028'){
		    		tipHintMsg(login_hint_fail);
		    	}else if(data.res_code == 'SF_MEMB_1060'){
		    		tipHintMsg(data.res_msg);
		    	}else if(data.res_code == 'SF_MEMB_1062'){
		    		if (backUrl != undefined && backUrl != '') {
			    		window.location.href = wxServer + "/rl/toBind?backUrl=" + backUrl ;
		    		}else {
		    			window.location.href = wxServer + "/rl/toBind" ;
		    		}
		    	}else if(data.res_code == 'SF_MEMB_1005'){
		    		if (backUrl != undefined && backUrl != '') {
		    			window.location.href = backUrl;
		    		}else {
		    			window.location.href = wxServer + "/weixin/product/list" ;
		    		}
		    	}
		    }
		});
	}) ;

});

function setBodyMinHeight() {
	var topValue = window.innerHeight - $(".headerBar").height() - $(".wrapper").height();

	$(".cont-block .bottom-btn").css({
		"margin-top": (topValue < 0 ? 0 : topValue) + "px"
	});
}

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

function showRegister(){
	$(".mregisterBtn").addClass("current").siblings("span").removeClass("current");
	$('.registerFrame').show();
	$('.loginFrame').hide();
	$(".cont-block .bottom-btn").css({
		"margin-top": 0
	});
	setBodyMinHeight();
}

function showLogin(){
	$(".mloginBtn").addClass("current").siblings("span").removeClass("current");
	$('.registerFrame').hide();
	$('.loginFrame').show();
	$(".cont-block .bottom-btn").css({
		"margin-top": 0
	});
	setBodyMinHeight();
}

function findPassword(){
	window.location.href=wxServer + "/rl/findPassword" ;
}

function produceKaptchaImage(){
	$('#kaptchaImage').hide().attr('src', wxServer + '/rl/captcha-image/' + Math.floor(Math.random()*100) ).fadeIn();
}

$(function(){

	FastClick.attach(document.body);

	setBodyMinHeight();

	$(".mregisterBtn").click(function(){
		$(this).addClass("current").siblings("span").removeClass("current");
		$('.registerFrame').show();
		$('.loginFrame').hide();
	});

	$(".mloginBtn").click(function(){
		$(this).addClass("current").siblings("span").removeClass("current");
		$('.registerFrame').hide();
		$('.loginFrame').show();
	});

	window.resize = function() {
		setBodyMinHeight();
	}
});

function setBodyMinHeight() {
	var topValue = window.innerHeight - $(".headerBar").outerHeight() - $(".mloginTab").outerHeight();
	$(".cont-block").css({
		"height": topValue + "px"
	});
}

function tipHintMsg(errHintMsg){
	$("#errHintregisterlogin").html(errHintMsg);
	$("#alert-dialog-registerlogin").popup();
	$("#alert-dialog-registerlogin").popup("open");
}
