/**
 *
 */
var mobile_hint_formaterror = '您输入的手机号有误' ;
var mobile_hint_isalreadyexists = '您的手机号已被注册' ;
var verifycode_hint_wrongcode = '您输入的验证码有误' ;
var verifycode_hint_retry = '验证码发送失败请重试' ;
var login_hint_fail = '用户名或密码错误' ;
var user_is_not_active = '咦？账号异常，请与客服联系~' ;
var register_hint_programerror = '请再点一下～～～' ;
var checkout_login = '您的手机号已注册，请输入密码登录' ;
var t;
var init = {
		tipBtnInit : function(){
			$("#priceChangeTip .btn-confirm").click(function(){
				$("#priceChangeTip").popup("close");
			});
			$("#noAgreeTip .btn-confirm").click(function(){
				$("#noAgreeTip").modal("hide");
			});
			$("#couponUsed .btn-confirm").click(function(){
				$("#couponUsed").popup("close");
				coupon.refresh();
			});
			$("#accountError .btn-confirm").click(function(){
				$("#accountError").popup("close");
				//logout()
				 logout();
//				init.refreshPage();
			});
			$("#commonError .btn-confirm").click(function(){
				$("#commonError").popup("close");
			});
			$("#orderFailed .btn-confirm").click(function(){
				$("#commorderFailed").popup("close");

			});
			$("#productOver .btn-confirm").click(function(){
				$("#productOver").popup("close");
				var pid = $("#productNo").val();
				var url = wxServer +'/product/weixin/detail/chooseTrip/'+pid;
				console.log("url: "+url);
				window.location.href=url;
			});
			$("#submit-dlg").popup({
				afterclose: function() {
					return;
				}
			});
			$("#regisError .btn-confirm").click(function(){
				$("#regisError").popup("close");
			});
			$("#submit-dlg .submit-dlg-confirm").click(function(){
				applyOrder.userLogin();
			});
		},
		initProtocol : function(){
			$(".agr-txt").click(function(){
	            $("#agr-dialog").popup("open");
	            $("#agr-dialog-screen").touchmove(function(){
	                return false;
	            });

	        });
			$("#agr-dialog").find("a.btn-confirm").click(function(){
				if(!($("div.agreement").find("input[type='checkbox']").prop("checked"))){
					$("div.agreement").find("input[type='checkbox']").prop("checked",true);
					$("a.submenu").removeClass("sub-disable");
					$("a.submenu").addClass("sub-link");
				}
				 $("#agr-dialog").popup("close");
			});
			$("#agr-dialog").find("a.btn-cancel").click(function(){
				 $("#agr-dialog").popup("close");
			});
		},
		initAgree : function(){
			$("div.agreement").find("label.agreelabel").find("input[type='checkbox']").click(function(){
				return false;
			});
			$("div.agreement").find("label.agreelabel").find("input[type='checkbox']").prop("checked",false);
			$("div.agreement").find("label.agreelabel,span.agreespan").click(function(){
				$(this).closest("div").find("input[type='checkbox']").prop("checked", !$(this).closest("div").find("input[type='checkbox']").prop("checked"));
				if($(this).closest("div").find("input[type='checkbox']").prop("checked")){
					$("a.submenu").removeClass("sub-disable");
					$("a.submenu").addClass("sub-link");
				}else{
					$("a.submenu").removeClass("sub-link");
					$("a.submenu").addClass("sub-disable");
				}
			});
		},
		initUserRegister : function(){
			//继续预订,返回修改按钮初始化
			$("#orderContinue").click(function(e){
				e.preventDefault();
				init.registerAndApply();
			});
			$("#backToEditBtn").click(function(e){
				e.preventDefault();
				$("#newMobilePhone").val($('#contactorDiv').find('input.mobile').val());
				$("#newVerifyCode").val("");
				$("#newPassWord").val();
				manulDestoryTimer(t);
				$("#regist-dlg").popup({
					afterclose : function(){
						return;
					}
				});
				$("#regist-dlg").popup("close");
			});
			//注册提示错误弹窗关闭后弹出原有注册框
			$("#regisError").popup({
				afterclose: function(){
					$("#newVerifyCode").val("");
					$("#newPassWord").val("");
					$("#regist-dlg").popup("open");
					manulDestoryTimer(t);
					return;
				}
			});
			//注册码
			var verifycode_lock = false ;
			$("a.btn-vld").click(function(){
				var mobile = trim("#newMobilePhone") ;
				if(!isMobile.test(mobile) && mobile != ''){
					$("#regist-dlg").popup({
						afterclose : function(){
							$("#regisErrorCont").html(mobile_hint_formaterror);
							$("#regisError").popup("open");
							return ;
						}
					});
					$("#regist-dlg").popup("close");
				}
				if(!$(this).hasClass("retry") && !verifycode_lock) {
					verifycode_lock = true ;
					$.ajax({
						url: ssoServer + '/sso/sendRegisterSmsByGet' ,
					    data: 'mobile=' + trim("#newMobilePhone") ,
					    dataType:'jsonp',
					    jsonp:'callback',
					    success : function(data){
					    	if(data.res_code == 'SF_MEMB_1010'){
					    		$("a.btn-vld").addClass("retry");
		    					$("a.btn-vld").html("<span>60</span>s后重新尝试")
		    					t = timer(59) ;
					    	}else{
					    		$("#regist-dlg").popup({
					    			afterclose : function(){
					    				if(data.res_code == 'EF_MEMB_1010'){
					    					$("#regisErrorCont").html(verifycode_hint_retry);
					    					$("#regisError").popup("open");
					    				}else if(data.res_code == 'EF_MEMB_1012' || data.res_code == 'EF_MEMB_1000'){
					    					$("#regisErrorCont").html(register_hint_programerror);
					    					$("#regisError").popup("open");
					    				}else if(data.res_code == 'EF_MEMB_1011'){
					    					$("#regisErrorCont").html(verifycode_hint_wrongcode);
					    					$("#regisError").popup("open");
					    				}else if(data.res_code == 'EF_MEMB_1024'){
					    					$("#regisErrorCont").html(password_hint_error);
					    					$("#regisError").popup("open");
					    				}else if(data.res_code == 'EF_MEMB_1017'){
					    					$("#regisErrorCont").html(mobile_hint_formaterror);
					    					$("#regisError").popup("open");
					    				}else if(data.res_code == 'EF_MEMB_1002'){
					    					$("#regisErrorCont").html(mobile_hint_isalreadyexists);
					    					$("#regisError").popup("open");
					    				}
					    			}
					    		});
					    		$("#regist-dlg").popup("close");
					    	}
					    	verifycode_lock = false ;
					    },
					    error : function(data){
					    	verifycode_lock = false ;
					    }
					});
				}
			});
		},
		refreshPage : function(){
			var selectedDay = $("#bookDate").val();
			var productId = $("#productId").val();
			var packageId = $("#packageId").val();
			var adultNum =$("#adultNum").val();
			var childNum =$("#childNum").val();
			var url = wxServer +'/book/weixin/tobook';
			if(packageId != null && packageId !=""){
				window.location.href = url + "/" + packageId + "/" + selectedDay + "/" + productId;
			}else{
				url = wxServer+'/book/weixin/tobook';
				window.location.href = url + "/" + adultNum + "/" +childNum+"/"+ selectedDay + "/" + productId;
			}

		},
		registerAndApply : function(){
			var contactorPhone = $('#contactorDiv').find('input.mobile').val();
			var userName = $("#newMobilePhone").val();
			var passWord = $("#newPassWord").val();
			var verifyCode = escape($("#newVerifyCode").val());
			var contactorName = $("#contactorDiv input.name").val();
			var contactorEmail = $("#contactorDiv input.email").val();
			var province = $("#hideProvince").val();
			var city = $("#hideCity").val();
			var county  = $("#hideCounty").val();
			var area = $("#detail").val();
			passWord = escape(passWord) ;
			_paq.push(['trackEvent', 'orderpage', 'ztrRegisterApply', userName]);
			$.ajax({
			    url: ssoServer + '/sso/wx/doRegisterByGet' ,
			    data: 'mobile=' + userName + '&password=' + passWord + '&email=' + contactorEmail + '&realName=' + contactorName +'&province=' + province +'&city=' + city + "&county=" + county + "&area=" + area + "&rememberMe=false&verifyCode=" + verifyCode ,
			    dataType:'jsonp',
			    jsonp:'callback',
			    success: function(data){
			    	$("#regist-dlg").popup({
			    		afterclose : function(){
			    			if(data.res_code == 'EF_MEMB_1002'){
			    				manulDestoryTimer(t);
			    				$("#newMobilePhone").val(contactorPhone);
					    		$("#regisErrorCont").html(mobile_hint_isalreadyexists);
								$("#regisError").popup("open");
					    	}else if(data.res_code == 'EF_MEMB_1011'){
					    		manulDestoryTimer(t);
					    		$("#newMobilePhone").val(contactorPhone);
					    		$("#regisErrorCont").html(verifycode_hint_wrongcode);
								$("#regisError").popup("open");
					    	}else if(data.res_code == 'EF_MEMB_1012' || data.res_code == 'EF_MEMB_1000'){
					    		manulDestoryTimer(t);
					    		$("#newMobilePhone").val(contactorPhone);
					    		$("#regisErrorCont").html(register_hint_programerror);
								$("#regisError").popup("open");
					    	}else if(data.res_code == 'EF_MEMB_1024'){
					    		manulDestoryTimer(t);
					    		$("#newMobilePhone").val(contactorPhone);
					    		$("#regisErrorCont").html(password_hint_error);
								$("#regisError").popup("open");
					    	}else if(data.res_code == 'EF_MEMB_1017'){
					    		manulDestoryTimer(t);
					    		$("#newMobilePhone").val(contactorPhone);
					    		$("#regisErrorCont").html(mobile_hint_formaterror);
								$("#regisError").popup("open");
					    	}else if(data.res_code == 'SF_MEMB_1000'){
					    		applyOrder.submit();
					    	}
			    		}
			    	});
			    	$("#regist-dlg").popup("close");
			    }
			});
		}
}

function timer(intDiff){
	var timer = window.setInterval(function(){
		var second = 0;//时间默认值
		if(intDiff > 0){
			second = Math.floor(intDiff) ;
		}
		if (second <= 9) second = '0' + second;
		$("a.btn-vld.retry").find("span").html(second) ;
		destoryTimer(timer,intDiff) ;
		intDiff--;
	}, 1000);
	return timer;
}

function destoryTimer(timer,intDiff){
	if(intDiff <= 0){
		clearInterval(timer) ;
		$("a.btn-vld.retry").removeClass("retry");
		$("a.btn-vld").html("获取验证码") ;
	}
}

function manulDestoryTimer(timer){
	clearInterval(timer) ;
	$("a.btn-vld.retry").removeClass("retry");
	$("a.btn-vld").html("获取验证码") ;
}
