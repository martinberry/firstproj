/**
 *
 */
var nationality_hint_error='对不起，找不到：';
var verifycode_lock = false ;
var t;
var bookInit = {
		initSubmit : function(){
			 var clicked = false;
			    $(".checkUser").click(function(){
			    	if(clicked || !$(this).hasClass("active")) {
			    		return ;
			    	}else {
			    		clicked = true;
			    	}
			    	//提交订单
			    	if(applyOrder.submitValidate()){
			    		if(!applyOrder.checkAgree()){
			    			clicked = false;
			    			return;
			    		}
			    		applyOrder.submit();
			    	}else{
			    		if($(".errorBox").length > 0){//跳转到错误提示框位置
			    			var firstErrorBox = $(".errorBox").eq(0);
			    			var topSet = $(firstErrorBox).offset().top;
			    			window.scrollTo(0,topSet - 10);
			    		}
			    		clicked = false;
			    		return ;
			    	}
			    	clicked = false;
			     });
		},
		initValidate : function(){
			$('input[data-cv]:not(".datepicker")').each(function(){
		  		$(this).blur(function(){
		  			var validateResult = $(this).bookValidate({
		                validateAll: false
		            });
		  		});
		  	});
			$('input.datepicker[data-cv]').each(function(){
				$(this).on('changeDate', function(){
					var validateResult = $(this).bookValidate({
		                validateAll: false
		            });
				});
			});
			$(".order-linkManTable").find(".dropdown-menu li a").click(function(){
		    	var thisHtml = $(this).html();
		    	var $thisParents = $(this).parents(".dropdown-menu li");
		        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
		        var checkResult = $(this).parents(".dropdown-menu").bookValidate({
	                validateAll: false
	            });
		    });
			$("textarea.remark").each(function(){
				$(this).blur(function(){
					var validateResult = $(this).bookValidate({
		                validateAll: false
		            });
				});
			});
		},
		initTipsBtn : function(){
			$("#priceChangeTip .blueBtn").click(function(){
				$("#priceChangeTip").modal("hide");
			});
			$("#noAgreeTip .blueBtn").click(function(){
				$("#noAgreeTip").modal("hide");
			});
			$("#couponUsed .blueBtn").click(function(){
				$("#couponUsed").modal("hide");
				coupon.refresh();
			});
			$("#customError .blueBtn").click(function(){
				$("#customError").modal("hide");
			});
			$("#orderFailed .blueBtn").click(function(){
				$("#orderFailed").modal("hide");
				bookLogin.refreshPage();
			});
			$("#backToEditBtn").click(function(){
				$("#checkUserRegister").modal("hide");
				manulDestoryTimer(t) ;
				toggleErrorHint(disappear) ;
			});
			$("#orderContinue").click(function(){
				bookLogin.registerAndLogin();
			});
		},
		initPassenger : function(){
			//动态生成乘客数量
			var productType = $("#nature").val();
			if(productType != "VISA"){
				passenger.create($("#orderAdultNum").html(),$("#orderChildNum").html(),$("#isDomestic").val(),$("#login").val());
				passenger.initTranselate();
				//国籍自动补全
				$("#order-conBlock input.country").each(function(i,item){
					$(this).typeahead({
						source: function (query, process) {
							$((($(this)[0]).$element)[0]).val($((($(this)[0]).$element)[0]).val().replace(/</g,"&lt").replace(/>/g,"&gt"));
							var parameter = {query: query};
							$.post(basepath + "/travelerInfo/countryAutoComplete", parameter, function (data) {
								if(data.length==0 || !isExistNationality(data,i)){
									notFindHint =nationality_hint_error+$("#nationality_"+i).val();
									data=[notFindHint];
								}else{
									nationalityDropListValues=data;
									$("#nationalityDropList_"+i).val(data);
								}
								process(data);
							});
						}
					});
				});
				nationalityBlurIfNotFoundSetEmpty();
				//常旅客姓名提示
				$(".guestNameIcon").click(function(){
					var travellerType = $(this).parents("div.guestInfo-item").find("span.passengerType").data("val");
					passenger.getCommonPassenger($(this),travellerType);
					$(this).next(".guestNameList").slideToggle("fast",function(obj){
						if($(this).css('display') !== 'none'){
							_paq.push(['trackEvent', 'orderpage', 'ztrtraveller']);
						}
					});
				});
			}
		},
		initBookLogin : function(){
			$("#checkUser").find('a.blueBtn').click(function(){
				_paq.push(['trackEvent', 'login', 'ztrorderlogin', $("#userName").val()]);
				if(applyOrder.submitValidate()){
		    		applyOrder.submit();
		    	}
			});
			bookLogin.changeUser();
			$("span.unlogin-tip").click(function(){
				workplatformChange();
			});
			//发送短信验证码
			$(".getVerificationCode").click(function(){
				var mobile = trim("#newMobilePhone") ;
				if(!isMobile.test(mobile) && mobile != ''){
					toggleErrorHint(disappear) ;
					toggleErrorHint(mobile_hint_formaterror) ;
					return ;
				}
				if(!$(this).hasClass("retry") && !verifycode_lock) {
					verifycode_lock = true ;
					$(".getVerificationCode").addClass("retry");
		    		$(".getVerificationCode").html("<span>60</span>s后重新尝试")
					t = timer(59) ;
					$.ajax({
					    url: ssoServer + '/sso/sendRegisterSmsByGet' ,
					    data: 'mobile=' + trim("#newMobilePhone") ,
					    dataType:'jsonp',
					    jsonp:'callback',
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
			//初始化查看密码
			$(".view-pwd").click(function(){
		        $(this).toggleClass("active");

		        if ($(this).hasClass("active")) {
		            $(this).parent(".componentInput").find("input").attr("type", "text");
		        } else {
		            $(this).parent(".componentInput").find("input").attr("type", "password");
		        }
		    });
			//修改密码强弱图标
			$("#newPassWord").bind('input propertychange', function() {
				var password = trim($("#newPassWord")) ;

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
			//校验手机号
			$("#newMobilePhone").blur(function(){
				var mobile = trim("#newMobilePhone") ;
				if(!isMobile.test(mobile) && mobile != ''){
					toggleErrorHintNew(disappear) ;
					toggleErrorHintNew(mobile_hint_formaterror) ;
				}else{
					if(mobile != ''){
						$.ajax({
						    type: 'POST',
						    url: ssoServer + '/sso/isMobileAlreadyExistsByGet' ,
						    data: 'mobile=' + mobile ,
						    dataType:'jsonp',
						    jsonp:'callback',
						    success: function(data){
						    	if(data.res_code == 'EF_MEMB_1002'){
						    		toggleErrorHintNew(disappear) ;
						    		toggleErrorHintNew(mobile_hint_isalreadyexists) ;
						    	}else if(data.res_code == 'EF_MEMB_1001'){
						    		toggleErrorHintNew(disappear) ;
						    		toggleErrorHintNew(mobile_hint_formaterror) ;
						    	}else if(data.res_code == 'EF_MEMB_1003'){
						    		toggleErrorHintNew(disappear) ;
						    	}else{
						    		toggleErrorHintNew(disappear) ;
						    	}
						    }
						});
					}else{
						toggleErrorHintNew(disappear) ;
						toggleErrorHintNew("手机号不能为空") ;
					}
				}
			});
			//校验验证码
			$('#newVerifyCode').blur(function() {
				if(trim("#newVerifyCode").length > 0){
					$.ajax({
						url: ssoServer + '/sso/checkVerifyCodeByGet' ,
					    data: 'mobile=' + trim("#newMobilePhone") + "&verifyCode=" + escape(trim("#newVerifyCode")) ,
					    dataType:'jsonp',
					    jsonp:'callback',
					    success : function(data){
					    	if(data.res_code == 'EF_MEMB_1011'){
					    		toggleErrorHintNew(disappear) ;
					    		toggleErrorHintNew(verifycode_hint_wrongcode) ;
					    	}else{
					    		toggleErrorHintNew(disappear) ;
					    	}
					    }
					});
				}
			});
		},
		initBackArrow : function(){
			$("a.back-lineArrow").click(function(){
				var productNo = $("#productNo").val();
				var bookDate = $("#bookDate").val();
				var adultNum = $("#orderAdultNum").html();
				var childNum = $("#orderChildNum").html();
				var packageId = $("#packageId").val();
				var nature = $("#nature").val();
				var priceId = $("#costPriceId").val();
				if(typeof(nature) != undefined && nature != ''){
					if(nature == 'VISA'){
						window.location.href = basepath + "/product/visa/detail/" + productNo + "?selectedDay=" + bookDate + "&adultNum=" + adultNum + "&childNum=" + childNum +"&costPriceId=" +priceId;
					}else if(nature == 'PACKAGE' || nature=='COMBINATION'){
						if (packageId != '') {
							window.location.href = basepath + "/product/front/detail/" + productNo + "?selectedDay=" + bookDate + "&packageId=" + packageId;
						} else {
							window.location.href = basepath + "/product/front/detail/" + productNo + "?selectedDay=" + bookDate + "&adultNum=" + adultNum + "&childNum=" + childNum;//等会确认
						}
					}else{
						window.location.href = basepath + "/localproduct/front/detail/" + productNo + "?selectedDay=" + bookDate + "&adultNum=" + adultNum + "&childNum=" + childNum +"&costPriceId=" +priceId;
					}
				}
				_paq.push(['trackEvent', 'orderpage', 'ztrreselect', productNo]);
			});
		},
		//禁止回退到此页面
		initForbiddenBack : function(){
			javascript:window.history.forward(1);
		},
		//设置有效日期与出生日期日历时间范围
		initDatePickerRange: function(){
			$("input.datepicker.credentialsDeadLine").each(function(){
				var today = new Date();
				$(this).datepicker('setStartDate', today.getFullYear() + '-'+(today.getMonth()+1)+'-'+(today.getDate() + 1));
				$(this).datepicker('setDate', '2020-01-01');
				$(this).datepicker('setEndDate', '2035-12-31');
			});
			$("input.datepicker.birthday").each(function(){
				$(this).datepicker('setStartDate', '1920-01-01');
				$(this).datepicker('setDate', '1980-01-01');
				$(this).datepicker('setEndDate', new Date());
			});
		},
		initAreaCount : function(){//文本框字数限制提示
				var contentLength = $("textarea.remark").val().length;
				var leftWordsNum = 200 - contentLength;
				$("#leftWords").html(leftWordsNum);
		},
		initProtocol : function(){
				$("#readProBtn").click(function(){
	                $("#orderProtocol").modal("show");
	            });
	            $("#agree").click(function(){
	                $("#orderProtocol").modal("hide");
	                $("#agreeProtocol").addClass("active");
	                $(".checkUser").addClass("active");
	            });
		},
		initSubmitBtn : function(){
			$("div.submitOrderBlock label").click(function(){
				if($("#agreeProtocol").hasClass("active")){
					$(".checkUser").addClass("active");
				}else{
					$(".checkUser").removeClass("active");
				}
			});
		},
		detailAddressCascade : function(){
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
		    PROVINCEDD.select('');
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

function toggleErrorHintNew(hint){
	if(hint == '') {
		$(".newErrorBoxContent").css("display","none") ;
	}else {
		$("div.newErrorBoxContent span.errorHint").html(hint) ;
		$(".newErrorBoxContent").css("display","") ;
	}
}

function initHotelTips(){
	$("div.order-conBlock table.order-hotelTable").find("label.radio").click(function(){
		if($(this).closest("td").find("div.hotel-tip").length > 0){
			if($(this).attr("val") == "double"){
				$(this).closest("td").find("div.hotel-tip").attr("style","display;");
			}else{
				$(this).closest("td").find("div.hotel-tip").attr("style","display:none;");
			}
		}
	});
}
