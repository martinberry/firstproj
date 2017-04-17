var mobile_hint_formaterror = '您输入的手机号有误' ;
var mobile_hint_isalreadyexists = '您的手机号已被注册' ;
var verifycode_hint_wrongcode = '您输入的验证码有误' ;
var verifycode_hint_retry = '验证码发送失败请重试' ;
var login_hint_fail = '用户名或密码错误' ;
var user_is_not_active = '咦？账号异常，请与客服联系~' ;
var register_hint_programerror = '请再点一下～～～' ;
var checkout_login = '您的手机号已注册，请输入密码登录' ;

var bookLogin = {
		show : function(isRegistered) {
			if(!isRegistered) {
				$("#loginMsg").text("已用联系人手机为您注册账号,请查收");
			}
	        $("#checkUser").modal("show");
		},

		hide : function() {
			 $("#checkUser").modal("hide");
		},
		login : function() {
		},
		changeUser: function(){
			$("div.text-center").click(function(){
				bookLogin.hide();
			});
		},
		refreshPage : function(){
			var anStr = $("#orderAdultNum").html();
			var cnStr = $("#orderChildNum").html();
			var selectedDay = $("#bookDate").val();
			var url = memberServer +'/product/book/tobookpage';
			var $form = $('<form action="'+ url +'" method="post" style="display:none;"></form>');
			$form.append('<input name="productId" value="'+ $("#productId").val() +'"/>');
			$form.append('<input name="bookDate" value="'+ selectedDay.replace(/\//ig,'-') +'"/>');
			$form.append('<input name="adultNum" value="'+ anStr +'"/>');
			$form.append('<input name="childNum" value="'+ cnStr +'"/>');
			$form.append('<input name="packageId" value="'+ $("#packageId").val() +'"/>');
			$form.appendTo('body');
			$form.submit();
		},
		initRegisterWin : function(){
			  $("#newVerifyCode").val("") ;
			  $("#newPassWord").val("") ;
			  $(".view-pwd").removeClass("active")
			  $(".view-pwd").parent(".componentInput").find("input").attr("type", "password");
		},
		registerAndLogin : function(){
			var userName = $("#newMobilePhone").val();
			var passWord = $("#newPassWord").val();
			var verifyCode = $("#newVerifyCode").val();
			var contactorName = $("#contactorName").val();
			var contactorEmail = $("#contactorEmail").val();
			var province = $(".order-linkManTable div.province a.dropdownBtn span.activeFonts").html();
			var city = $(".order-linkManTable div.city a.dropdownBtn span.activeFonts").html();
			var county  = $(".order-linkManTable div.county a.dropdownBtn span.activeFonts").html();
			var area = $("#contactorAddress").val();
			passWord = escape(passWord) ;
			_paq.push(['trackEvent', 'orderpage', 'ztrRegisterApply', userName]);
			$.ajax({
			    url: ssoServer + '/sso/wx/doRegisterByGet' ,
			    data: 'mobile=' + userName + '&password=' + passWord + '&email=' + contactorEmail + '&realName=' + contactorName +'&province=' + province +'&city=' + city + "&county=" + county + "&area=" + area + "&rememberMe=false&verifyCode=" + verifyCode ,
			    dataType:'jsonp',
			    jsonp:'callback',
			    success: function(data){
			    	if(data.res_code == 'EF_MEMB_1002'){
			    		manulDestoryTimer(t) ;
			    		toggleErrorHintNew(disappear) ;
			    		toggleErrorHintNew(mobile_hint_isalreadyexists) ;
			    	}else if(data.res_code == 'EF_MEMB_1011'){
			    		manulDestoryTimer(t) ;
			    		toggleErrorHintNew(disappear) ;
			    		toggleErrorHintNew(verifycode_hint_wrongcode) ;
			    	}else if(data.res_code == 'EF_MEMB_1012' || data.res_code == 'EF_MEMB_1000'){
			    		manulDestoryTimer(t) ;
			    		toggleErrorHintNew(disappear) ;
			    		toggleErrorHintNew(register_hint_programerror) ;
			    	}else if(data.res_code == 'EF_MEMB_1024'){
			    		manulDestoryTimer(t) ;
			    		toggleErrorHintNew(disappear) ;
			    		toggleErrorHintNew(password_hint_error) ;
			    	}else if(data.res_code == 'NOT_IN_WHITE_LIST'){
			    		manulDestoryTimer(t) ;
			    		toggleErrorHintNew(disappear) ;
			    		toggleErrorHintNew("非认证内测人员不能注册") ;
			    	}else if(data.res_code == 'SF_MEMB_1000'){
			    		applyOrder.submit();
			    	}else if(data.res_code == 'EF_MEMB_1017'){
			    		manulDestoryTimer(t) ;
			    		toggleErrorHintNew(disappear) ;
			    		toggleErrorHintNew(mobile_hint_formaterror) ;
			    	}
			    }
			});
		}
}

	function refreshCallBack(){
		coupon.refresh();
//		passenger.cleanInfo();
		$("div.errorBox").remove();
}

	function login_callback(){
		refreshCallBack();
		$("span.unlogin-tip").attr("style","display:none;");
		$("span.guestNameIcon").attr("style","display;");
	}

	function logout_callback(){
		refreshCallBack();
		contactor.clean();
		passenger.cleanInfo();
		$("span.unlogin-tip").attr("style","display;");
		$("span.guestNameIcon").attr("style","display:none;");
	}

	function manulDestoryTimer(timer){
		clearInterval(timer) ;
		$(".getVerificationCode.retry").removeClass("retry");
		$(".getVerificationCode").html("获取短信验证码") ;
	}