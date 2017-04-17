
var failureCount = 0;

$(function(){

	$(window).keydown(function(event){
		//回车键的键值为13
		if (event.keyCode==13) {
			//调用登录按钮的登录事件
			if (loginValiation()) {
	    		userSignin();
	    		}
			}
		});

    if (errorMsg && errorMsg.length > 0) {
    	$(".errorBox span").html(errorMsg);
        $(".errorBox-field").show();
    }

    //初始化计数
//    getLoginFailCount();
    $('.authCode-field').hide();

//    $(".userName").bind("focusout", function(){
//    	getLoginFailCount();
//    });

    $(".userName").bind("change", function(){
    	getLoginFailCount();
        $(".errorBox-field").hide();
    });

    $(".userPwd").bind("change", function(){
        $(".errorBox-field").hide();
    });

    $(".authCode").bind("change", function(){
        $(".errorBox-field").hide();
    });

    $('.delIcon').click(function(){
    	$(".userName").val('');
    	$('.authCode-field').hide();
    });

    $(".autoLogin .checkedBox").click(function(){
        $(this).toggleClass("noCheckedBox");
    });

    $('.loginBtn').click(function(){
    	if (loginValiation()) {
    		userSignin();
	}
    });

});

//获取登录失败次数
function getLoginFailCount() {
	var input_username = $('.userName').val();
	var timestamp = new Date().getTime();
	$.ajax({
		  url: basepath + '/user/getLoginFailureCount',
		  dataType: 'json',
		  type : 'POST',
		  data : {'userName':input_username, 'timeStamp':timestamp},
		  success:  function(text) {
				var loginFailureCount = parseInt(text);
				failureCount = loginFailureCount;
				if (loginFailureCount >= 3) {
					//show authCode
					$('.authCode-field').show();
					$('#imgCaptcha').attr('src', basepath + '/captcha/' + Math.floor(Math.random()*100) ).fadeIn();
				}else {
					//hide authCode
					$('.authCode-field').hide();
				}
		  }
	});
}

function loginValiation() {
	var $username = $('.userName');
	if ( !testUsername($username.val())) {
		error( '输入格式有误，请重新输入');
		$username.focus();
		return false;
	} else {
		$(".errorBox-field").hide();
	}
	var $password = $('.userPwd');
	if ( !testPassword($password.val())) {
		error('输入格式有误，请重新输入');
		$password.focus();
		return false;
	} else {
		$(".errorBox-field").hide();
	}
	if (failureCount >= 3) {
		var $authCode = $('.authCode');
		if ($authCode.val() == '') {
			error( '验证码不能为空');
			$authCode.focus();
			return false;
		} else if ($authCode.val().length != 4) {
			error('验证码为4位');
			$authCode.focus();
			return false;
		} else {
			$(".errorBox-field").hide();
		}
	}
	return true;
}

//员工登录
function userSignin() {
	var data = {};
	data.userName = $('.userName').val();
	data.password = $('.userPwd').val();
	data.authCode = $('.authCode').val();
	data.rememberMe = $('.autoLogin .checkedBox').hasClass("noCheckedBox") ? false : true;
	$.ajax({
		  url: basepath + '/user/signIn',
		  dataType: 'json',
		  type : 'POST',
		  headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		  data : JSON.stringify(data),
		  success:  function(resp) {
			  if (resp.res_code == 'SO_OPER_1001') {
					window.location.href = basepath+'/user/main';
				}else if(resp.res_code == 'FO_OPER_1002'){
					error('验证码错误');
					$('#imgCaptcha').attr('src', basepath + '/captcha/' + Math.floor(Math.random()*100) ).fadeIn();
				}else if(resp.res_code == 'FO_OPER_1003'){
					error('用户名或密码错误');
					getLoginFailCount();
				}else{
					error(resp.res_msg);
				}
		  }
	});
}

function error(message){
    $(".errorBox span").html(message);
    $(".errorBox-field").show();
}

function testUsername(username){
	var r=/^([a-zA-Z0-9|\u4e00-\u9fa5|.]){1,20}$/;
	if(r.test(username)){
		return true;
	}else{
		return false;
	}
}

function testPassword(password){
	var r=/^[A-Za-z0-9]{6,28}$/;
	if(r.test(password)){
		return true;
	}else{
		return false;
	}
}

