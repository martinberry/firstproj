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

	//登陆
	var wxBindLoginLock = false ;
	$("body").delegate("#bottom-wxbindandlogin-btn","click",function(){
		var account = trim(".loginFrame .mobileInputer") ;
		var password = trim(".loginFrame .passwordInputer") ;
		password = escape(password) ;
		var verifyCode = trim(".loginVerifyCodeInputer") ;
		var backOrigUrl =$("#backOrigUrl").val();
		if(!wxBindLoginLock){
			wxBindLoginLock = true ;
			$.ajax({
			    url: ssoServer + '/sso/wx/bindAndLoginByWx' ,
			    data: 'account=' + account + "&password=" + password + "&verifyCode=" + verifyCode ,
			    dataType:'jsonp',
			    jsonp:'callback',
			    success: function(data){
			    	if(data.res_code == 'SF_MEMB_1005'){
			    		_paq.push(['trackEvent', 'login', 'ztrlogin',account,'SUCCESS']);
			    	}else{
			    		_paq.push(['trackEvent', 'login', 'ztrlogin',account,'FAIL']);
			    	}
			    	if(data.res_code == 'SF_MEMB_1060'){
			    		tipHintMsg(data.res_msg);
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
			    	}else if(data.res_code == 'SF_MEMB_1050'){
			    		produceKaptchaImage() ;
		    			$(".loginVerify").css("display", "") ;
		    			tipHintMsg(data.res_msg);
			    	}else if(data.res_code == 'SF_MEMB_1051'){
			    		produceKaptchaImage() ;
		    			$(".loginVerify").css("display", "") ;
		    			tipHintMsg(data.res_msg);
			    	}else if(data.res_code == 'SF_MEMB_1052'){
			    		produceKaptchaImage() ;
		    			$(".loginVerify").css("display", "") ;
		    			tipHintMsg(data.res_msg);
			    	}else if(data.res_code == 'EF_MEMB_1027'){
			    		produceKaptchaImage() ;
		    			$(".loginVerify").css("display", "") ;
			    		tipHintMsg(verifycode_hint_wrongcode);
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
			    		if (backOrigUrl != undefined && backOrigUrl != '') {
			    			window.location.href = backOrigUrl;
			    		}else {
			    			window.location.href = wxServer + "/weixin/product/list" ;
			    		}
			    	}
			    	wxBindLoginLock = false ;
			    },error: function(){
			    	wxBindLoginLock = false ;
			    }
			});
		}

	}) ;


	var wxLoginLock = false ;
	//微信登陆
	$("body").delegate("#bottom-wxregisterandlogin-btn","click",function(){
		var backOrigUrl =$("#backOrigUrl").val();
		if(!wxLoginLock){
			wxLoginLock = true ;
			$.ajax({
			    url: ssoServer + '/sso/wx/registerAndLoginByWx' ,
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
			    	}else if(data.res_code == 'SF_MEMB_1061'){
			    		tipHintMsg(data.res_msg);
			    	}else if(data.res_code == 'SF_MEMB_1005'){
			    		if (backOrigUrl != undefined && backOrigUrl != '') {
			    			window.location.href = backOrigUrl;
			    		}else {
			    			window.location.href = wxServer + "/weixin/product/list" ;
			    		}
			    	}
			    	wxLoginLock = false ;
			    },error: function(data){
			    	wxLoginLock = false ;
			    }
			});
		}
	});
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

function findPassword(){
	window.location.href=wxServer + "/rl/findPassword?url=/rl/toBind";
}

function produceKaptchaImage(){
	$('#kaptchaImage').hide().attr('src', wxServer + '/rl/captcha-image/' + Math.floor(Math.random()*100) ).fadeIn();
}

$(function(){

	FastClick.attach(document.body);

	setBodyMinHeight();

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
