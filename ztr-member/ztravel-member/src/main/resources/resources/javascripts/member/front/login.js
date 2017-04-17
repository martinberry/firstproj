var disappear = '' ;
var login_hint_fail = '输入的用户名或密码有错' ;
var user_is_not_active = '咦？账号异常，请与客服联系~' ;
var login_hint_verifycode_error = '输入的验证码有错' ;
var LOGINED_ACCOUNT = '';
$(function(){

	document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			$(".commonBtn.blueBtn.loginBtn").click() ;
		}
	}

	if($("#loginFailCount").val() >= 3){
		$(".componentInput.verificationCode").css("display", "") ;
		produceKaptchaImage() ;
	}

	//免费注册
	$("body").delegate(".register-link","click",function(){
		window.location.href = ssoServer + '/sso/register' ;
	}) ;

	//记住我
	$("body").delegate(".checkboxIcon","click",function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active") ;
		}else{
			$(this).addClass("active") ;
		}
	}) ;

	//忘记密码
	$("body").delegate(".forgetPWFonts","click",function(){
		window.location.href = ssoServer + '/sso/findPassword' ;
	}) ;

	$(".workplabtn").click(function(){
		$('.work-platform').height($(window).height());
        TabcontControl();
	}) ;

	//登陆
	$("body").delegate(".commonBtn.blueBtn.loginBtn","click",function(){
		var account = trim("#accountInputer") ;
		var password = trim("#passwordInputer") ;
		password = escape(password) ;
		var verifyCode = trim("#verifyCodeInputer") ;
		var rememberMe = false ;
		if($(".checkbox").hasClass("active")) rememberMe = true ;
		$.ajax({
		    url: ssoServer + '/sso/login' ,
		    data: 'account=' + account + "&password=" + password + "&verifyCode=" + verifyCode + "&rememberMe=" + rememberMe ,
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
		    	}else if(data.res_code == 'EF_MEMB_1026' || data.res_code == 'EF_MEMB_1024'){
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
		    		loginSuccessPostProcessor(data.res_msg) ;
		    		loginedLong() ;
		    		window.clearInterval(keepLongPollTimer) ;
		            if( typeof login_callback === 'function' ){
		                //存在且是function
		            	login_callback() ;
		            }
		    	}
		    }
		});
	}) ;

	//生成验证码
	$("body").delegate("#kaptchaImage","click",function () {
		produceKaptchaImage() ;
    });

	//用户中心
	$("body").delegate(".userInfo","click",function(){
		window.location.href = memberServer + '/member/info' ;
	})

	//退出登录
	$("body").delegate(".exitbtn","click",function(){
		$.ajax({
		    url: ssoServer + '/sso/logout' ,
		    dataType:'jsonp',
		    jsonp:'callback',
		    success: function(data){
		    	if(data.res_code == 'success'){
		    		$(".after-login").hide();
		    	    $(".login-entrance").show();
		    	    $(".workpla-login").show();
		    	    $(".workpla-afterlogin").hide();
		    	    isLogin = 'false' ;
		    	    LOGINED_ACCOUNT = '';
		    	    clearPlAndSn() ;
		    	    displayQrcode();
		    	    keepLongPoll() ;
		    	    if($("#home_member_pic").length == 1){
		    	    	$("#home_member_pic").removeAttr("src") ;
		    	    }
		    	    $($(".leftImg").find("img")[0]).removeAttr("src") ;
		    	    if( typeof logout_callback === 'function' ){
		                //存在且是function
		    	    	logout_callback() ;
		            }
		    	}
		    }
		});
		_paq.push(['trackEvent', 'exit', 'ztrexit',LOGINED_ACCOUNT]);
	});

	//阅读某条提醒
	$("body").delegate("div.remind-tab-cont li.unread", "click", function(){
		var noticeId = $(this).attr('value');
		if(noticeId){
			readNotice([noticeId],$(this));
		}
	});

//	阅读某条私信
	$("body").delegate("div.privletter-tab-cont li.unread", "click", function(){
		$(this).removeClass('unread');
		var noticeAllRead = isNoticeAllRead();
		var plAllRead = isPrivateLetterAllRead();
		if(plAllRead){
			$("#pl_span").find("span").remove();
		}
		if(noticeAllRead && plAllRead){
			$("#nm_span").find("span").remove();
		}
	});

});

function jumpToSn(){
	window.open(memberServer + "/systemnotice/list") ;
}

function jumpToPl(){
	window.open(memberServer + "/privateletter/list") ;
}

function loadPrivateLetter(){
	$.ajax({
	    type: 'POST',
	    url: memberServer + '/privateletter/load' ,
	    data: 'size=15' ,
	    dataType: 'json' ,
	    success : function(data){
	    	var plHtml = $(".private-letter") ;
	    	if(data != null && data.length>0){
	    		var html = '<div class="privletter-tab-cont tab-cont-height"><ul>' ;
	    		var allRead = true ;
	      		for(var i=0;i<data.length;i++){
	      			var pl = data[i] ;
	      			var isReadClass = "" ;
	      			if(!pl.hasRead){
	      				isReadClass="unread" ;
	      				allRead = false ;
	      			}
	      			html += '<li class="'+isReadClass+'">' ;
	      			html += '<a href="'+memberServer+'/privateletter/talk/'+pl.anotherId+'" target="_blank">' ;
	      			html += '<div class="privaletter-left">' ;
	      			html += '<img src="'+mediaServer+'imageservice?mediaImageId='+pl.anotherHead+'" style="width:42px;height:42px;" class="round_photo">' ;
	      			html += '</div>' ;
	      			html += '<div class="privaletter-right">' ;
	      			html += '<div class="priletter-top">' ;
	      			html += '<span class="username">'+pl.anotherNickname+'</span>' ;
	      			html += '<span class="priletter-data">'+pl.updateTime+'</span>' ;
	      			html += '</div>' ;
	      			html += '<div class="priletter-bottom secondEllipsis"><p>' ;
	      			html += pl.msgContent.replace(/\</ig, '&lt;').replace(/\>/ig, '&gt;'); ;
	      			html += '</p></div></div></a></li>' ;
	      		}
	      		if(allRead){
	      			$("#nm_span").html("") ;
		    		$("#pl_span").html("私信") ;
	      		}else{
	      			$("#nm_span").html("<span></span>") ;
		    		$("#pl_span").html("私信<span></span>") ;
	      		}
	      		html += "</ul></div><a class='more' href='javascript:jumpToPl();'>更多</a>" ;
	      		plHtml.html(html) ;
	      		TabcontControl() ;
	    	}else{
	    		clearPl() ;
	    		$(".private-letter").html('<div class="privletter-tab-cont tab-cont-height"><div class="privletter-empty-block pl-empty" style="margin-top: 79.5px;"><span class="letter-empty-img"></span><p>您暂时还没有私信哦！</p></div></div>') ;
	    	}
	    },
	    error : function(ex){
	    	$(".private-letter").html('<div class="privletter-tab-cont tab-cont-height"><div class="privletter-empty-block pl-empty" style="margin-top: 79.5px;"><span class="letter-empty-img"></span><p>您暂时还没有私信哦！</p></div></div>') ;
	    }
	});
}

function clearPlAndSn(){
	clearPl() ;
	clearSn() ;
}

function clearPl(){
	$("#nm_span").html("") ;
	$("#pl_span").html("私信") ;
	$(".private-letter").html('') ;
}

function clearSn(){
	$("#nm_span").html("") ;
	$("#sn_span").html("提醒") ;
	$(".remind").html('') ;
}

function loadSystemNotice(){
	$.ajax({
		type: 'POST',
	    url: memberServer + '/systemnotice/load' ,
	    data: 'size=15' ,
	    dataType: 'json' ,
	    success: function(data){
	    	var snHtml = $(".remind") ;
	    	if(data != null && data.length>0){
	    		var allRead = true ;
	    		var html = '<div class="remind-tab-cont tab-cont-height"><ul>' ;
	      		for(var i=0;i<data.length;i++){
	      			var sn = data[i] ;
	      			var isReadClass = "" ;
	      			if(!sn.hasRead){
	      				isReadClass="unread" ;
	      				allRead = false ;
	      			}
	      			var typeClass = "" ;
	      			if(sn.type == 'ORDERTYPE'){
	      				typeClass = "ordericon" ;
	      			}else if(sn.type == 'COUPONTYPE'){
	      				typeClass = "vouchericon" ;
	      			}else if(sn.type == 'REMAINTYPE'){
	      				typeClass = "balanceicon" ;
	      			}
	      			var combinationClass = '' ;
	      			if(isReadClass != ''){
	      				combinationClass = isReadClass+"-"+typeClass ;
	      			}else{
	      				combinationClass=typeClass ;
	      			}
	      			html += '<li class="'+isReadClass+'" value="'+sn.id+'">' ;
	      			html += '<span class="remind-conmenicon '+combinationClass+'"></span>' + sn.content ;
	      			html += '</li>' ;
	      		}
	      		if(allRead){
	      			$("#nm_span").html("") ;
		    		$("#sn_span").html("提醒") ;
	      		}else{
		    		$("#nm_span").html("<span></span>") ;
		    		$("#sn_span").html("提醒<span></span>") ;
	      		}
	      		html += "</ul></div><a class='more' href='javascript:jumpToSn();'>更多</a>" ;
	      		snHtml.html(html) ;
	      		TabcontControl() ;
	    	}else{
	    		clearSn() ;
	    		$(".remind").html('<div class="remind-tab-cont tab-cont-height"><div class="privletter-empty-block sns-empty" style="margin-top: 79.5px;"><span class="letter-empty-img"></span><p>您暂时还没有提醒哦！</p></div></div>') ;
	    	}
	    },
	    error: function(data){
	    	$(".remind").html('<div class="remind-tab-cont tab-cont-height"><div class="privletter-empty-block sns-empty" style="margin-top: 79.5px;"><span class="letter-empty-img"></span><p>您暂时还没有提醒哦！</p></div></div>') ;
	    }
	});
}

function readNotice(noticeIds,trigger){
	$.ajax({
		type: "POST",
		url: memberServer+"/systemnotice/read",
		data: JSON.stringify(noticeIds),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType: "json",
		success: function(response){
			if(response.res_code == 'success'){
				trigger.removeClass('unread');
				//判断是代金券，还是订单，分享好友
				var remindIcon = trigger.find(".remind-conmenicon");
				if(remindIcon.hasClass("unread-vouchericon")){
					remindIcon.removeClass("unread-vouchericon").addClass("vouchericon");
				}
				if(remindIcon.hasClass("unread-ordericon")){
					remindIcon.removeClass("unread-ordericon").addClass("ordericon");
				}
				if(remindIcon.hasClass("unread-balanceicon")){
					remindIcon.removeClass("unread-balanceicon").addClass("balanceicon");
				}
				var noticeAllRead = isNoticeAllRead();
				var plAllRead = isPrivateLetterAllRead();
				if(noticeAllRead){
					$("#sn_span").find("span").remove();
				}
				if(noticeAllRead &&plAllRead){
					$("#nm_span").find("span").remove();
				}
			}else{
				console.log(response);
			}
		},
		error: function(e){
			console.log(e);
		},
	});
}

function isNoticeAllRead(){
	var isAllRead = false;
	var unReadSize = $(".remind-cont-block").find("li.unread").length;
	if(unReadSize==0){
		isAllRead = true;
	}
	return isAllRead;
}

function isPrivateLetterAllRead(){
	var isAllRead = false;
	var unReadSize = $(".privletter-tab-cont").find("li.unread").length;
	if(unReadSize==0){
		isAllRead = true;
	}
	return isAllRead;
}

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

function resetLoginHint(){
	$(".errorBoxContent").hide() ;
	$("#accountInputer").val("") ;
	$("#passwordInputer").val("") ;
	$(".verificationCode").hide() ;
	$("#verifyCodeInputer").val("") ;
}


/**
 * 微信二维码展示
 * */
function displayQrcode(){
	$.ajax({
		url : memberServer + "/member/getTicket",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.success){
				if(data.isLogin){
					if (typeof(data.wxLogined) == "undefined" || !data.wxLogined) {
						$(".maskingContent").find("img").attr("src","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+data.ticket+"");
						$(".workTwoDimension").attr("style","display:none;");
						$(".maskingContent").animate({'top':'122px','height':'100%','opacity':"show"},1000);
						$(".maskingContent").attr("style","display;");
					}
				}else{
					$(".workTwoDimension").find("img").attr("src","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+data.ticket+"");
					$(".maskingContent").attr("style","display:none;");
					$(".workTwoDimension").attr("style","display;");
				}
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
        url: memberServer + "/member/unloginLongPoll?auto=true",
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
            			window.clearInterval(keepLongPollTimer) ;
            			popNotActive() ;
            		} else {
            			if(typeof(callback) == "function"){
                    		callback();
                    	}
                		loginSuccessPostProcessor(data.member) ;
                		window.clearInterval(keepLongPollTimer) ;
                		loginedLongTimer = setInterval("loginedLongProxy()", 1000) ;
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


var loginedLongTimer ;
function loginedLong(){
	loginedLongTimer = setInterval("loginedLongProxy()", 1000) ;
}

function loginedLongProxy(){
	if(isLogin == 'true'){
		$.ajax({
	        url: memberServer + "/member/isWxLogined?auto=true",
	        type:"GET",
	        dataType: "json",
	        success: function (data, textStatus) {
	            if (textStatus == "success") {// 请求成功
	            	if(data.wxLogined){
	            		$(".closeIcon").click();//关闭二维码
	            		//window.clearInterval(loginedLongTimer) ;
	            	} else if (typeof(data.errorMsg) != "undefined" && data.errorMsg != null && data.errorMsg != '') {
	            		$(".closeIcon").click();//关闭二维码
	            		//window.clearInterval(loginedLongTimer) ;
	            		popNotActive() ;
	            	}
	            }
	        }
	    });
	}
}

function loginSuccessPostProcessor(member){
	member = $.parseJSON(member) ;
	$(".login-entrance").hide();
    $(".after-login").show();
    $(".workpla-login").hide();
    $(".workpla-afterlogin").show();
    $(".workpla-afterlogin").css("opacity","1");
    var headImgUrl = mediaServer + "imageservice?mediaImageId=" + member.headImageId;
    $($(".leftImg").find("img")[0]).attr("src", headImgUrl) ;
    $(".rightUsername").html(member.nickName) ;
    $(".userName").find("em").html(member.nickName) ;
    isLogin = 'true' ;
    LOGINED_ACCOUNT = member.mobile;
    resetLoginHint() ;
    clearPlAndSn() ;
    $("#accountInputer").val("") ;
	$("#passwordInputer").val("") ;
	$("#verifyCodeInputer").val("") ;
	displayQrcode();
    loadPrivateLetter() ;
    loadSystemNotice() ;
    if($("#home_member_pic").length == 1){
    	$("#home_member_pic").attr("src", headImgUrl) ;
    }
    TabcontControl();
}