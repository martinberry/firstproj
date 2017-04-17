var disappear = '' ;
var login_hint_fail = '输入的用户名或密码有错' ;
var user_is_not_active = '咦？账号异常，请与客服联系~' ;
var login_hint_verifycode_error = '输入的验证码有错' ;
var user_has_binded_error = '用户已绑定微信账号，请重新绑定' ;
var openid_has_registered_error = '该微信号已经注册啦' ;
var openid_has_binded_error = '该微信号已经绑定手机号啦' ;
var user_password_error = '输入手机或密码不合法' ;
$(function(){

	$("#mobileInputer").val("") ;
	$("#passwordInputer").val("") ;

	//忘记密码
	$("body").delegate(".forgetPWFonts","click",function(){
		window.open(ssoServer + '/sso/findPassword');
	}) ;

	$(".bigBlue11b9b7Btn").click(function(){
		var account = trim("#mobileInputer") ;
		var password = trim("#passwordInputer") ;
		var openId = $("#openId").text();
		password = escape(password) ;
		$.ajax({
		    url: ssoServer + '/sso/bindAndLogin' ,
		    data: 'account=' + account + "&password=" + password + "&openId=" + openId,
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
	    			$(".componentInput.verificationCode").css("display", "") ;
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(login_hint_verifycode_error) ;
		    	}else if(data.res_code == 'EF_MEMB_1026'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".componentInput.verificationCode").css("display", "") ;
		    		}
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(login_hint_fail) ;
		    	}else if(data.res_code == 'EF_MEMB_10261'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".componentInput.verificationCode").css("display", "") ;
		    		}
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(user_is_not_active) ;
		    	}else if(data.res_code == 'EF_MEMB_1024'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".componentInput.verificationCode").css("display", "") ;
		    		}
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(user_password_error) ;
		    	}else if(data.res_code == 'EF_MEMB_1028'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(login_hint_fail) ;
		    	}else if(data.res_code == 'SF_MEMB_1050'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(user_has_binded_error) ;
		    	}else if(data.res_code == 'SF_MEMB_1051'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(openid_has_registered_error) ;
		    	}else if(data.res_code == 'SF_MEMB_1052'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(openid_has_binded_error) ;
		    	}else if(data.res_code == 'SF_MEMB_1005'){
		    		window.location.reload();
		    	}
		    },
		    error : function(data){
		    	alert("请重新扫码登陆～");
		    }
		});
	});

	$(".bigYellowBtn").click(function(){
		var openId = $("#openId").text();
		var nickName = $("#nickName").text();
		var headImgUrl = $("#headImgUrl img").attr("src");
		$.ajax({
		    url: ssoServer + '/sso/registerAndLogin' ,
		    data: 'openId=' + openId + "&nickName=" + nickName + "&headImgUrl=" + headImgUrl,
		    dataType:'jsonp',
		    jsonp:'callback',
		    success: function(data){
	    		var member = $.parseJSON(data.res_msg) ;
		    	if(data.res_code == 'SF_MEMB_1005'){
		    		_paq.push(['trackEvent', 'login', 'ztrlogin',member.mobile,'SUCCESS']);
		    	}else{
		    		_paq.push(['trackEvent', 'login', 'ztrlogin',member.mobile,'FAIL']);
		    	}
		    	if(data.res_code == 'EF_MEMB_1027'){
		    		produceKaptchaImage() ;
	    			$(".componentInput.verificationCode").css("display", "") ;
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(login_hint_verifycode_error) ;
		    	}else if(data.res_code == 'EF_MEMB_1026'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".componentInput.verificationCode").css("display", "") ;
		    		}
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(login_hint_fail) ;
		    	}else if(data.res_code == 'EF_MEMB_10261'){
		    		if(data.res_msg >= 3){
		    			produceKaptchaImage() ;
		    			$(".componentInput.verificationCode").css("display", "") ;
		    		}
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(user_is_not_active) ;
		    	}else if(data.res_code == 'EF_MEMB_1028'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(login_hint_fail) ;
		    	}else if(data.res_code == 'SF_MEMB_1005'){
		    		window.location.reload();
		    	}
		    },
		    error : function(data){
		    	alert("请重新扫码登陆～");
		    }
		});
	});

});

function produceKaptchaImage(){
	$('#kaptchaImage').hide().attr('src', ssoServer + '/sso/captcha-image/' + Math.floor(Math.random()*100) ).fadeIn();
}

function toggleErrorHint(hint){
	if(hint == '') {
		$(".errorBoxContent").css("display","none") ;
	}else {
		$(".errorHint").html(hint) ;
		$(".errorBoxContent").css("display","") ;
	}
}

function trim(selector){
	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
}