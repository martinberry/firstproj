var disappear = '' ;
var mobile_hint_formaterror = '您输入的手机号有误' ;
var email_hint_formaterror = '您输入的邮箱号有误' ;
var mobile_hint_isalreadyexists = '您的手机号已被注册' ;
var verifycode_hint_wrongcode = '您输入的验证码有误' ;
var verifycode_hint_retry = '验证码发送失败请重试' ;
var register_hint_programerror = '请再点一下～_～' ;
var nickNameReg = /^([0-9a-zA-Z\u4E00-\u9FA5\*]+)$/;
var verificationCodeReg = /^[0-9]{6}$/;
var timerObj;
var verifycode_lock = false ;
var isEmail = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;


$(function(){
	initPage();
	keepLongPoll();

	$(".modal.fade").modal({
        backdrop:"static",
        keyboard: false,
        show: false
    });
});

/*初始化页面*/
function initPage(){

        //-----编辑头像
        $(".headPortraitImg .editHeadPortraitIcon").click(function(){
            $(this).parents(".editHeadPortrait").hide();
            $(this).parents(".editHeadPortrait").siblings(".addHeadPortrait").show();
        });
    	//取消返回页面
    	$("#headImg_grayBtn").click(function(){
    		$(this).parents("section.addHeadPortrait").hide();
    		$(this).parents("section.addHeadPortrait").siblings(".editHeadPortrait").show();
    		$('#imagePreviewer').attr('src', host + '/images/client/review-avatar-blank.jpg');
    		clearFile('#imageInputer');
    		toggleErrorHint('','#img_errorHint') ;
    	});
    	//提交图片
    	$("#headImg_blueBtn").click(function(){
    		if($(this).attr('flag') == 'upload'){
    			upload();
    		}else if($(this).attr('flag') == 'pickup'){
    			pickup();
    		}
    	});
        //选择头像
        $(".sec-tab span").click(function(){
            $(".sec-tab span").removeClass("current");
            $(this).addClass("current");
            if ($(this).hasClass("uploadBtn")) {
                $(".pickup-default-avatar").hide();
                $(".upload-avatar").show();
            } else if ($(this).hasClass("pickupBtn")) {
                $(".upload-avatar").hide();
                $(".pickup-default-avatar").show();
            }
        });

        //-----编辑昵称 a
        $(".nicknameContent .editIcon").click(function(){
            $(this).parents(".nicknameContent").hide();
            $(this).parents(".nicknameContent").siblings(".editContent").show();
            $('#nickName_input').val($('#nickName_content').html());
        });
        //  编辑邮箱
        $(".mailContent .editIcon").click(function(){
            $(this).hide();
            $(this).parents(".mailContent").siblings(".editMail").find(".editModelTabContainer").slideDown(function(){
                $(window).trigger("resize");
            });
        });
        $(".editMail .cancelOper").click(function(){
            $(this).parents(".editMail").find(".editModelTabContainer").slideUp(function(){
                $(window).trigger("resize");
                $(this).parents(".editMail").siblings(".mailContent").find(".editIcon").show();
                toggleErrorHint('','#newEmail_errorHint');
                toggleErrorHint('','#emailPassword_errorHint');
                $("#newEmail").val("");
                $("#emailPassword").val("");

            });
        });
        //取消昵称编辑
        $("#nickName_cancelBtn").click(function(){
            $(this).parents(".editContent").hide();
            $(this).parents(".editContent").siblings(".nicknameContent").show();
            toggleErrorHint('','#nickName_errorHint') ;
        });
        //提交昵称编辑
        $("#nickName_sureBtn").click(function(){
        	toggleErrorHint('','#nickName_errorHint') ;
            var $inputVal = $(this).siblings(".valContent").val();
            var $parents = $(this).parents(".editContent");
            if(getStrLength($inputVal) <= 0){
            	toggleErrorHint('昵称不能为空','#nickName_errorHint') ;
    			return;
            }else if($inputVal.length > 11 ){
            	toggleErrorHint('昵称长度限制：11个中英文字符','#nickName_errorHint') ;
    			return;
            }
            if(!(nickNameReg.test($inputVal)) ){
            	toggleErrorHint('您的昵称格式不对哦','#nickName_errorHint') ;
    			return;
            }
//            var result = changeParam('nickName', $inputVal);
            if($inputVal == null || $inputVal == ''){
        		return ;
        	}
        	var params = {};
        	params.paramName = 'nickName';
        	params.paramValue = $inputVal;
        	$.ajax({
        	    type: 'GET',
        	    dataType:'jsonp',
        	    jsonp:'callback',
        	    url:ssoServer + '/sso/changeInfoParam' ,
        	    data: params ,
        	    async: false,
        	    success: function(data){
        	    	if(data.res_code == 'error'){
        	    		popAlert(data.res_msg);
        	    		return;
        	    	}else{
        	    		$('#nickName_content').html($inputVal);
        	            $parents.hide();
        	            $parents.siblings(".nicknameContent").show();
        	    	}
        	    },
        		error: function(e){
//        			console.log(e.responseText);
        		}
        	});
        });

        //-----编辑性别
        $(".genderContent .editIcon").click(function(){
            $(this).parents(".genderContent").hide();
            $(this).parents(".genderContent").siblings(".editContent").show();
        });
        $(".editContent.gender .cancelBtn").click(function(){
            $(this).parents(".editContent").hide();
            $(this).parents(".editContent").siblings(".genderContent").show();
        });
        $(".editContent.gender .sureBtn").click(function(){
            var $gender = $(this).siblings(".radioContent").find(".active .genderSelect");
            var $parents = $(this).parents(".editContent");
            if($gender.attr('value') == null || $gender.attr('value') == ''){
        		return ;
        	}
        	var params = {};
        	params.paramName = 'gender';
        	params.paramValue = $gender.attr('value');
        	$.ajax({
        	    type: 'GET',
        	    dataType:'jsonp',
        	    jsonp:'callback',
        	    url:ssoServer + '/sso/changeInfoParam' ,
        	    data: params ,
        	    async: false,
        	    success: function(data){
        	    	if(data.res_code == 'error'){
        	    		popAlert(data.res_msg);
        	    		return;
        	    	}else{
        	    		$parents.siblings(".genderContent").find(".genderFonts").html($gender.html());
        	            $parents.hide();
        	            $parents.siblings(".genderContent").show();
        	    	}
        	    },
        		error: function(e){
//        			console.log(e.responseText);
        		}
        	});
        });

        //-----编辑手机号码
        $(".phoneNumberContent .editIcon").click(function(){
            $(this).hide();
            $(this).parents(".phoneNumberContent").siblings(".editPhoneNumber").show();
//            setWrapperHeight();
//            setMainBoxPosition();
        });
        $("#mobile_grayBtn").click(function(){
            $(this).parents(".editPhoneNumber").hide();
            $(this).parents(".editPhoneNumber").siblings(".phoneNumberContent").find(".editIcon").show();
//            setWrapperHeight();
//            setMainBoxPosition();
            $('#oldMobile').val('');
            $('#mobilePassword').val('');
            $('#newMobile').val('');
            $('#verificationCode').val('');
            destoryTimer(timerObj,0);
            toggleErrorHint(disappear,'#oldMobile_errorHint') ;
        	toggleErrorHint(disappear,'#mobilePassword_errorHint') ;
        	toggleErrorHint(disappear,'#newMobile_errorHint') ;
        	toggleErrorHint(disappear,'#verificationCode_errorHint') ;
        });
        //验证新号码是否被注册
        function checkMobileExist(mobile){
        	var result = false;
        	$.ajax({
			    type: 'POST',
			    url: basepath + '/member/checkMobile' ,
			    data: 'mobile=' + mobile ,
			    dataType: 'json' ,
			    async: false,
			    success : function(data){
			    	if(data.res_code == 'success'){
			    		result = true;
			    	}else if(data.res_code == 'fail'){
			    		popAlert(data.res_msg);
			    	}else{
			    		popAlert("验证出错！");
			    	}
			    }
			});
        	return result;
        }
        //获取验证码
        $(".getVerificationCode").click(function(){
    		var mobile = trim("#newMobile") ;
    		if(!isMobile.test(mobile) && mobile != ''){
    			toggleErrorHint(disappear,'#newMobile_errorHint') ;
    			toggleErrorHint(mobile_hint_formaterror,'#newMobile_errorHint') ;
    		}
    		if(!checkMobileExist(mobile))return;
    		if( isMobile.test(mobile) && !verifycode_lock && !$(".getVerificationCode").hasClass("retry")) {
    			verifycode_lock = true;
    			$(".getVerificationCode").html("<span>60</span>s后重新尝试")
				var t = timer(59) ;
    			$.ajax({
    			    type: 'POST',
    			    url: basepath + '/member/sendResetPasswordSms' ,
    			    data: 'mobile=' + mobile ,
    			    dataType: 'json' ,
    			    success : function(data){
    			    	verifycode_lock = false ;
    			    	if(data.res_code == 'EF_MEMB_1002'){
    			    		toggleErrorHint(disappear) ;
    			    		toggleErrorHint(mobile_hint_isalreadyexists) ;
    			    		manulDestoryTimer(t);
    			    	}else if(data.res_code == 'EF_MEMB_1010'){
    			    		toggleErrorHint(disappear) ;
    			    		toggleErrorHint(verifycode_hint_retry) ;
    			    		manulDestoryTimer(t);
    			    	}else if(data.res_code == 'SF_MEMB_1010'){
    			    		$(".getVerificationCode").addClass("retry") ;
    			    	}
    			    },
    			    error:function(){
    			    	manulDestoryTimer(t);
    			    	verifycode_lock = false ;
    			    }
    			});
    		}
    	});
        //提交修改电话
        $('#mobile_blueBtn').click(function(){
        	var mobileParams = {};
        	mobileParams.oldMobile = $('#oldMobile').val();
        	mobileParams.newMobile = $('#newMobile').val();
        	mobileParams.password = $('#mobilePassword').val();
        	mobileParams.verificationCode = $('#verificationCode').val();
        	toggleErrorHint(disappear,'#oldMobile_errorHint') ;
        	toggleErrorHint(disappear,'#mobilePassword_errorHint') ;
        	toggleErrorHint(disappear,'#newMobile_errorHint') ;
        	toggleErrorHint(disappear,'#verificationCode_errorHint') ;
        	if(mobileParams.oldMobile == null || mobileParams.oldMobile == ''){
        		toggleErrorHint("原号码不能为空",'#oldMobile_errorHint') ;
        		return;
        	}
        	if(!isMobile.test(mobileParams.oldMobile)){
    			toggleErrorHint(mobile_hint_formaterror,'#oldMobile_errorHint') ;
    			return;
    		}
        	if(mobileParams.password == ''){
    			toggleErrorHint('密码不能为空','#mobilePassword_errorHint') ;
    			return;
    		}
    		if(!isPassword.test(mobileParams.password)){
    			toggleErrorHint('密码不合法','#mobilePassword_errorHint') ;
    			return;
    		}
    		if( mobileParams.newMobile == null ||  mobileParams.newMobile == ''){
    			toggleErrorHint('新号码不能为空','#newMobile_errorHint') ;
    			return;
    		}
    		if(mobileParams.newMobile == mobileParams.oldMobile){
    			toggleErrorHint('新，旧号码不能相同','#newMobile_errorHint') ;
    			return;
    		}
    		if($('#mobile_content').html() != mobileParams.oldMobile){
    			toggleErrorHint('原手机号不一致','#oldMobile_errorHint') ;
    			return;
    		}
    		if(!isMobile.test(mobileParams.newMobile)){
    			toggleErrorHint(mobile_hint_formaterror,'#newMobile_errorHint') ;
    			return;
    		}
    		if(mobileParams.verificationCode == null || mobileParams.verificationCode == ''){
    			toggleErrorHint('验证码不能为空','#verificationCode_errorHint') ;
    			return;
    		}
    		if(!(verificationCodeReg.test(mobileParams.verificationCode))){
    			toggleErrorHint('验证码是6位数字','#verificationCode_errorHint') ;
    			return;
    		}
    		if(JSON.stringify(mobileParams) == null || JSON.stringify(mobileParams) == ''){
        		return ;
        	}
        	var params = {};
        	params.paramName = 'mobile';
        	params.paramValue = JSON.stringify(mobileParams);
        	$.ajax({
        	    type: 'GET',
        	    dataType:'jsonp',
        	    jsonp:'callback',
        	    url:ssoServer + '/sso/changeInfoParam' ,
        	    data: params ,
        	    async: false,
        	    success: function(data){
        	    	if(data.res_code == 'error'){
        	    		popAlert(data.res_msg);
        	    		return;
        	    	}else{
        	    		$('#mobile_content').html(mobileParams.newMobile);
        	    		$("#mobile_grayBtn").click();
        	    		logout("绑定号码已修改，请重新登录，系统将在３秒后跳回主页");
        	    	}
        	    },
        		error: function(e){
//        			console.log(e.responseText);
        		}
        	});
        });

      //提交修改邮件
        $('#email_blueBtn').click(function(){
        	var emailParams = {};
        	emailParams.oldEmail = $('#email_content').html();
        	emailParams.newEmail = $('#newEmail').val();
        	emailParams.password = $('#emailPassword').val();
        	toggleErrorHint(disappear,'#emailPassword_errorHint') ;
        	toggleErrorHint(disappear,'#newEmail_errorHint') ;
        	if( emailParams.oldEmail != null && emailParams.oldEmail != ""){
	        	if(!isEmail.test(emailParams.oldEmail)){
	        		toggleErrorHint(email_hint_formaterror,'#oldEmail_errorHint') ;
	        		return;
	        	}
        	}
        	if(emailParams.password == ''){
    			toggleErrorHint('密码不能为空','#emailPassword_errorHint') ;
    			return;
    		}
    		if(!isPassword.test(emailParams.password)){
    			toggleErrorHint('密码不合法','#emailPassword_errorHint') ;
    			return;
    		}
    		if( emailParams.newEmail == null ||  emailParams.newEmail == ''){
    			toggleErrorHint('新邮箱不能为空','#newEmail_errorHint') ;
    			return;
    		}
    		if(emailParams.newEmail == emailParams.oldEmail){
    			toggleErrorHint('新，旧邮箱不能相同','#newEmail_errorHint') ;
    			return;
    		}
    		if($('#email_content').html() != emailParams.oldEmail){
    			toggleErrorHint('原邮箱不一致','#oldEmail_errorHint') ;
    			return;
    		}
    		if(!isEmail.test(emailParams.newEmail)){
    			toggleErrorHint(email_hint_formaterror,'#newEmail_errorHint') ;
    			return;
    		}
    		if(JSON.stringify(emailParams) == null || JSON.stringify(emailParams) == ''){
        		return ;
        	}
        	var params = {};
        	params.paramName = 'email';
        	params.paramValue = JSON.stringify(emailParams);
        	$.ajax({
        	    type: 'GET',
        	    dataType:'jsonp',
        	    jsonp:'callback',
        	    url:ssoServer + '/sso/changeInfoParam' ,
        	    data: params ,
        	    async: false,
        	    success: function(data){
        	    	if(data.res_code == 'error'){
        	    		popAlert(data.res_msg);
        	    		return;
        	    	}else{
        	    		$('#email_content').html(emailParams.newEmail);
        	    		$("#email_grayBtn").click();
        	    		logout("邮箱已修改，请重新登录，系统将在３秒后跳回主页");
        	    	}
        	    },
        		error: function(e){
//        			console.log(e.responseText);
        		}
        	});
        });

        //-----编辑密码
        $(".PWContent .editIcon").click(function(){
            $(this).hide();
            $(this).parents(".PWContent").siblings(".editPw").show();
//            setWrapperHeight();
//            setMainBoxPosition();
        });
        $("#password_grayBtn").click(function(){
            $(this).parents(".editPw").hide();
            $(this).parents(".editPw").siblings(".PWContent").find(".editIcon").show();
//            setWrapperHeight();
//            setMainBoxPosition();
            $('#oldPassword').val('');
            $('#newPassword').val('');
            $('#newPasswordRe').val('');
            toggleErrorHint(disappear,'#oldPassword_errorHint') ;
        	toggleErrorHint(disappear,'#newPassword_errorHint') ;
        	toggleErrorHint(disappear,'#newPasswordRe_errorHint') ;
        	$('#newPassword').siblings('.strength').css('display', 'none');
        	$('#newPassword').siblings('.strength.weak').css('display', '');
        });
        //密码强度变换
        $('#newPassword').on('input',function(e){
        	var password = $(this).val();
        	var lv = checkPassword(password) ;

    		if(lv == -1){
    			$(this).siblings('.strength').css('display', 'none');
        		$(this).siblings('.strength.weak').css('display', '');
        		return;
    		}

    		if(lv == 0){
    			$(this).siblings('.strength').css('display', 'none');
        		$(this).siblings('.strength.weak').css('display', '');
    		}

    		if(lv == 1){
    			$(this).siblings('.strength').css('display', 'none');
        		$(this).siblings('.strength.centre').css('display', '');
    		}

    		if(lv == 2){
    			$(this).siblings('.strength').css('display', 'none');
        		$(this).siblings('.strength.strong').css('display', '');
    		}
        });
        //提交密码修改
        $('#password_blueBtn').click(function(){
        	var passwordParams = {};
        	passwordParams.oldPassword = $('#oldPassword').val();
        	passwordParams.newPassword = $('#newPassword').val();
        	passwordParams.newPasswordRe = $('#newPasswordRe').val();
        	toggleErrorHint(disappear,'#oldPassword_errorHint') ;
        	toggleErrorHint(disappear,'#newPassword_errorHint') ;
        	toggleErrorHint(disappear,'#newPasswordRe_errorHint') ;
        	if(passwordParams.oldPassword == ''){
    			toggleErrorHint('原密码不能为空','#oldPassword_errorHint') ;
    			return;
    		}
        	if(!isPassword.test(passwordParams.oldPassword)){
    			toggleErrorHint('原密码不合法','#oldPassword_errorHint') ;
    			return;
    		}
        	if(passwordParams.newPassword == ''){
    			toggleErrorHint('新密码不能为空','#newPassword_errorHint') ;
    			return;
    		}
        	if(!isPassword.test(passwordParams.newPassword)){
    			toggleErrorHint('新密码不合法','#newPassword_errorHint') ;
    			return;
    		}
        	if(passwordParams.newPasswordRe == ''){
    			toggleErrorHint('确认密码不能为空','#newPasswordRe_errorHint') ;
    			return;
    		}
        	if(!isPassword.test(passwordParams.newPasswordRe)){
    			toggleErrorHint('确认密码不合法','#newPasswordRe_errorHint') ;
    			return;
    		}
        	if(passwordParams.newPassword != passwordParams.newPasswordRe){
        		toggleErrorHint('两次输入密码不一致','#newPasswordRe_errorHint') ;
    			return;
        	}
    		if(JSON.stringify(passwordParams) == null || JSON.stringify(passwordParams) == ''){
        		return ;
        	}
        	var params = {};
        	params.paramName = 'password';
        	params.paramValue = JSON.stringify(passwordParams);
        	$.ajax({
        	    type: 'GET',
        	    dataType:'jsonp',
        	    jsonp:'callback',
        	    url:ssoServer + '/sso/changeInfoParam' ,
        	    data: params ,
        	    async: false,
        	    success: function(data){
        	    	if(data.res_code == 'error'){
        	    		popAlert(data.res_msg);
        	    		return;
        	    	}else{
        	    		$(".editPw .grayBtn").click();
                		logout("密码已修改，请重新登录，系统将在３秒后跳回主页");
        	    	}
        	    },
        		error: function(e){
//            			console.log(e.responseText);
        		}
        	});
        });

        //-----编辑真实姓名
        $(".realNameContent .editIcon").click(function(){
            $(this).parents(".realNameContent").hide();
            $(this).parents(".realNameContent").siblings(".editContent").show();
        });
        $("#realName_cancelBtn").click(function(){
            $(this).parents(".editContent").hide();
            $(this).parents(".editContent").siblings(".realNameContent").show();
            $('#realName_input').val($('#realName_content').html());
            toggleErrorHint('','#realName_errorHint');
        });
        //提交真实姓名
        $("#realName_sureBtn").click(function(){
        	toggleErrorHint('','#realName_errorHint');
            var $realNameVal = $(this).siblings(".editRealName").val();
            var $parents = $(this).parents(".editContent");
            if(getStrLength($realNameVal) <= 0){
            	toggleErrorHint('姓名不能为空','#realName_errorHint') ;
    			return;
            }else if(getStrLength($realNameVal) > 20 ){
            	toggleErrorHint('姓名长度限制：中文10,英文20','#realName_errorHint') ;
    			return;
            }
            if(!(/^([a-zA-Z\.]+)$/.test($realNameVal)) && !(/^([\u4E00-\u9FA5]+)$/.test($realNameVal))){
            	toggleErrorHint('姓名不合法','#realName_errorHint') ;
    			return;
            }
            if($realNameVal == null || $realNameVal == ''){
        		return ;
        	}
        	var params = {};
        	params.paramName = 'realName';
        	params.paramValue = $realNameVal;
        	$.ajax({
        	    type: 'GET',
        	    dataType:'jsonp',
        	    jsonp:'callback',
        	    url:ssoServer + '/sso/changeInfoParam' ,
        	    data: params ,
        	    async: false,
        	    success: function(data){
        	    	if(data.res_code == 'error'){
        	    		popAlert(data.res_msg);
        	    		return;
        	    	}else{
        	    		$('#realName_content').html($realNameVal);
                        $parents.hide();
                        $parents.siblings(".realNameContent").show();
        	    	}
        	    },
        		error: function(e){
//                			console.log(e.responseText);
        		}
        	});
        });

        //地址联动
        PROVINCEDD = new ZtrDropDown('#address_province',
    		{
	        	getData:function(request){return getAddress(request);},
	        	textName:'areaName',
	        	valueName:'no',
        	}
        );
        CITYDD = new ZtrDropDown('#address_city',
    		{
	        	getData:function(request){return getAddress(request);},
    		}
        );
        AREADD = new ZtrDropDown('#address_area',
        	{
        		getData:function(request){return getAddress(request);},
			}
        );
        PROVINCEDD.casCading(CITYDD);
        CITYDD.casCading(AREADD);
        PROVINCEDD.init('');
        initAddressDropDown();
        //-----编辑地址
        $(".addressContent .editIcon").click(function(){
            $(this).parents(".addressContent").hide();
            $(this).parents(".addressContent").siblings(".editContent").show();
        });
        $("#address_cancelBtn").click(function(){
        	$(this).parents(".editContent").hide();
        	$(this).parents(".editContent").siblings(".addressContent").show();
        	toggleErrorHint(disappear,'#detailAddress_errorHint') ;
        	initAddressDropDown();
        	$('#address_detailAddress').val($('#defaultDetailAddress').val());
        	//要将省市地区等地址复原到未修改前的
        });
        $("#address_sureBtn").click(function(){
        	toggleErrorHint(disappear,'#detailAddress_errorHint') ;
        	var addressSum = {};
        	addressSum.province = $('#address_province').find('ul li.active a').html()
        	addressSum.city = $('#address_city').find('ul li.active a').html()
        	addressSum.area = $('#address_area').find('ul li.active a').html()
        	addressSum.detailAddress = $('#address_detailAddress').val();
            if(addressSum.detailAddress == null || addressSum.detailAddress == ''){
    			toggleErrorHint('详细地址不能为空','#detailAddress_errorHint') ;
    			return;
            }
            if(addressSum.detailAddress.length < 1 || addressSum.detailAddress.length > 50){
            	toggleErrorHint('详细地址长度应为：1~50','#detailAddress_errorHint') ;
            	return;
            }
        	if(JSON.stringify(addressSum) == null || JSON.stringify(addressSum) == ''){
        		return ;
        	}
        	var params = {};
        	params.paramName = 'address';
        	params.paramValue = JSON.stringify(addressSum);
        	$.ajax({
        	    type: 'GET',
        	    dataType:'jsonp',
        	    jsonp:'callback',
        	    url:ssoServer + '/sso/changeInfoParam' ,
        	    data: params ,
        	    async: false,
        	    success: function(data){
        	    	if(data.res_code == 'error'){
        	    		popAlert(data.res_msg);
        	    		return;
        	    	}else{
        	    		window.location.reload();
        	    	}
        	    },
        		error: function(e){
//                    			console.log(e.responseText);
        		}
        	});
        });

    //绑定微信号
    $(".bindWechat").attr("title", $("#bindwechat_template").html());
    $(".bindWechat-tooltip").tooltip({
        html: true,
        placement: 'top',
        template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
    });
    //解除绑定微信
    $(".unbindWechat").attr("title", $("#unbindwechat_template").html());
    $(".unbindWechat-tooltip").tooltip({
        html: true,
        placement: 'top',
        template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
    });

  //提交修改电话
    $('#bindmobile_blueBtn').click(function(){
    	var bindMobileParams = {};
    	bindMobileParams.bindMobile = $('#newMobile').val();
    	bindMobileParams.bindPassword = $('#bindPassword').val();
    	bindMobileParams.verificationCode = $('#verificationCode').val();
    	toggleErrorHint(disappear,'#newMobile_errorHint') ;
    	toggleErrorHint(disappear,'#bindPassword_errorHint') ;
    	toggleErrorHint(disappear,'#verificationCode_errorHint') ;
		if( bindMobileParams.bindMobile == null ||  bindMobileParams.bindMobile == ''){
			toggleErrorHint('号码不能为空','#newMobile_errorHint') ;
			return;
		}
		if(!isMobile.test(bindMobileParams.bindMobile)){
			toggleErrorHint(mobile_hint_formaterror,'#newMobile_errorHint') ;
			return;
		}
		if(bindMobileParams.verificationCode == null || bindMobileParams.verificationCode == ''){
			toggleErrorHint('验证码不能为空','#verificationCode_errorHint') ;
			return;
		}
		if(!(verificationCodeReg.test(bindMobileParams.verificationCode))){
			toggleErrorHint('验证码是6位数字','#verificationCode_errorHint') ;
			return;
		}
    	if(bindMobileParams.bindPassword == ''){
			toggleErrorHint('密码不能为空','#bindPassword_errorHint') ;
			return;
		}
		if(!isPassword.test(bindMobileParams.bindPassword)){
			toggleErrorHint('密码不合法','#bindPassword_errorHint') ;
			return;
		}
		if(JSON.stringify(bindMobileParams) == null || JSON.stringify(bindMobileParams) == ''){
    		return ;
    	}

    	var params = {};
    	params.paramName = 'bindMobile';
    	params.paramValue = JSON.stringify(bindMobileParams);
    	$.ajax({
    	    type: 'GET',
    	    dataType:'jsonp',
    	    jsonp:'callback',
    	    url:ssoServer + '/sso/changeInfoParam' ,
    	    data: params ,
    	    async: false,
    	    success: function(data){
    	    	if(data.res_code == 'error'){
    	    		popAlert(data.res_msg);
    	    		return;
    	    	}else{
    	    		$('#mobile_content').html(bindMobileParams.bindMobile);
    	    		$("#mobile_grayBtn").click();
    	    		logout("绑定号码已修改，请重新登录，系统将在３秒后跳回主页");
    	    	}
    	    },
    		error: function(e){
//    			console.log(e.responseText);
    		}
    	});
    });

	  //绑定手机号
	$(".phoneNumberContent .bindphone").click(function(){
	    $(this).hide();
	    $(this).parents(".phoneNumberContent").siblings(".bindPhoneNumber").find(".editModelTabContainer").slideDown(function(){
	        $(window).trigger("resize");
	    });
	});
	$(".bindPhoneNumber .cancelOper").click(function(){
	    $(this).parents(".bindPhoneNumber").find(".editModelTabContainer").slideUp(function(){
	        $(window).trigger("resize");
	        $(this).parents(".bindPhoneNumber").siblings(".phoneNumberContent").find(".bindphone").show();
            $('#newMobile').val('');
            $('#verificationCode').val('');
            $('#bindPassword').val('');
            destoryTimer(timerObj,0);
            toggleErrorHint(disappear,'#newMobile_errorHint');
        	toggleErrorHint(disappear,'#bindPassword_errorHint');
        	toggleErrorHint(disappear,'#verificationCode_errorHint') ;
	    });
	});

}
//initPage end


//初始化地址地址下拉框
function initAddressDropDown(){
	var defaultProvince = $('#defaultProvince').val();
	var defaultCity = $('#defaultCity').val();
	var defaultArea = $('#defaultArea').val();
	PROVINCEDD.select(defaultProvince);
	CITYDD.select(defaultCity);
	AREADD.select(defaultArea);
}

/*验证码等待计时*/
function timer(intDiff){
	timerObj = window.setInterval(function(){
		var second = 0;//时间默认值
		if(intDiff > 0){
			second = Math.floor(intDiff) ;
		}
		if (second <= 9) second = '0' + second;
		$(".getVerificationCode").find("span").html(second) ;
		destoryTimer(timerObj,intDiff) ;
		intDiff--;
	}, 1000);
}
function destoryTimer(timerObj,intDiff){
	if(intDiff <= 0){
		$(".getVerificationCode").removeClass("retry");
		manulDestoryTimer(timerObj);
	}
}

function manulDestoryTimer(timer){
	clearInterval(timer) ;
	$(".getVerificationCode").html("获取短信验证码") ;
}
/*验证消息处理函数*/
function geneErrorEle($this, msg) {
	$this.addClass("verifyBox");
	if ($this.siblings("div.verifyStyle").length == 0) {
		$this.after($("<div class='verifyStyle' style='width: " + $this.outerWidth() + "px;'>"
					+"<i class='verifyIcon'></i><span class='verifyFonts'>"
					+msg+"</span></div>"));
		//"<div class='verifyStyle'><i class='commonIcon errorIcon'></i><span id='newPassword_errorHint' class='errorHint'><span></div>";
	} else {
		$this.siblings("div.verifyStyle").find(".verifyFonts").html(msg);
	}
}
function cleanErrorEle($this) {
	if ($this.siblings("div.verifyStyle").length != 0) {
		$this.removeClass('verifyBox');
		$this.siblings('div.verifyStyle').remove();
	}
}
/*获取字符串长度（中文一个字是两个字符）*/
function getStrLength(str) { 
    var cArr = str.match(/[^\x00-\xff]/ig); 
    return str.length + (cArr == null ? 0 : cArr.length); 
}
function popAlert(msg){
	if(msg === undefined)msg = "验证失败！";
	$("#ac-HintWindow").find("div.modal-body p").html(msg)
	$("#ac-HintWindow").modal("show");
}

//图片上传预览
function clearErrorMsg(param){
	var error_id = '#' + param.id + '_errorHint';
	toggleErrorHint(disappear,error_id) ;
}

function logout(msg){
	$.ajax({
	    url: ssoServer + '/sso/logout' ,
	    dataType:'jsonp',
	    jsonp:'callback',
	    success: function(data){
	    	if(data.res_code == 'success'){
	    	    if( typeof logout_callback === 'function' ){
	    	    	logout_callback() ;
	            }
	    	    popAlert(msg);
	    	    setTimeout("window.location.href = memberServer+'/home'",3000);
	    	}
	    }
	});
}

var keepLongPollTimer ;
function keepLongPoll(requestUrl,callback){
	keepLongPollTimer = setInterval("keepLongPollProxy()", 1000) ;
}

function keepLongPollProxy(){
	$.ajax({
        url: memberServer + "/member/memberInfoLongPoll?auto=true",
        type:"GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data, textStatus) {
            if (textStatus == "success") {// 请求成功
            	if (data.closeLongPoll) {
            		window.clearInterval(keepLongPollTimer) ;
            		if (typeof(data.resultType) != "undefined" && data.resultType != "") {
            			window.location.href = memberServer + "/member/info";
            		}
            	} else if (typeof(data.resultMsg) != "undefined" && data.resultMsg != null && data.resultMsg != "") {
            		popAlert(data.resultMsg);
            	}
            }
        }
    }) ;
}