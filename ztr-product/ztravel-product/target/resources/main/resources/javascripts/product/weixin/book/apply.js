/**
 *
 */
var cnNameReg = /^[\u4E00-\u9FA5]/;
var enNameReg = /^[a-zA-z|/.]{1,30}$/;

var applyOrder = {
		init : function(){
			$("a.submenu").click(function(){
				if($(this).hasClass("sub-link")){
					var params = applyOrder.getParams();
					console.log(params);
					if(applyOrder.validate()){
						applyOrder.submit();
					}
				}
			});
		},
		validate : function(){
			var lastResult = true;
			/**
			 * 乘客信息校验
			 * */
			$("#passengerContainer input[data-cv]").each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				if(!r1){
					lastResult = false;
					return lastResult;
				};
			});
			/**
			 * 联系人信息
			 * */
			if(lastResult){
				$("#contactorDiv input[data-cv]").each(function(i,ele){
					var r1 = $(ele).bookValidate({
						validateAll: false
					});
					if(!r1){
						lastResult = false;
						return lastResult;
					};
				});
				if(lastResult){
					$("div.order-mark textarea.remark").each(function(i,ele){
						var r1 = $(ele).bookValidate({
							validateAll: false
						});
						if(!r1){
							lastResult = false;
							return lastResult;
						};
					});
				}
			}
			return lastResult;
		},
		getParams : function(){
			var params  = {};
			var isDomestic = $("#isDomestic").val();
			params.isDomestic = isDomestic;
			params.productId = $("#productId").val();
			params.productNo = $("#productNo").val();
			params.packageId = $("#packageId").val();
			params.packageName = $("#packageName").val();
			params.firstImageId = $("#firstImageId").val();
			params.tripDays = $("#tripDays").val();
			params.nature = $("#nature").val();
			params.payAmount = new Number($("#firstPrice").find("span.price-num").html()).multiply(100);
			params.totalPrice = new Number($("#originTotalPrice").val()).multiply(100);
			params.productType = $("#productType").val();
			params.integralSub = new Number(0).multiply(100);//积分抵消金额
			params.integral = 0;
			params.productTitle = $("#productTitle").html();
			params.bookDate = $("#bookDate").val();
			params.adaultNum = $("#adultNum").val();
			params.childrenNum = $("#childNum").val();
			params.orderFrom = "WEIXIN";
			params.bedPrefer="big";
			params.nature = $("#nature").val();
			params.priceId = $("#costPriceId").val();
			if($("#couponContainer").find("input[type='checkbox']").prop("checked")){
				params.couponSub = typeof($("#couponAmount").html()) == 'undefined' ? 0 : new Number($("#couponAmount").html()).multiply(100);
				params.discountcouponId=$("#currentCoupon").attr("data-couponid");
			}else{
				params.discountcouponId="";
				params.couponSub = 0;
			}
			if($("div.order-hotel-bed").find("input.bigBed").prop("checked")){
				params.bedPrefer = "big";
			}else{
				params.bedPrefer = "double";
			}
			//需求备注
			params.remark = $(".remark").val();
			params.contactorInfo = contactor.getParams();
			params.passengerList = passenger.getParams();
			return params;
		},
		submit : function(){
			var params = applyOrder.getParams();
			var productId = params.productId;
			var bookDate = params.bookDate;
			$("a.submenu").removeClass("sub-link");
			$("a.submenu").addClass("sub-disable");
			$.ajax({
				  url: wxServer + '/book/weixin/apply',
				  dataType: 'json',
				  headers : {
					  'Accept' : 'application/json',
					  'Content-Type' : 'application/json'
				  },
				  type : 'POST',
				  data : JSON.stringify(applyOrder.getParams()),
				  success:  function(result) {
					  var tipMsg = '' , tipTitle = '' , confirmBtnTitle = '' , unconfirmBtnTitle = '', confirmBtnUrl = '', unconfrimBtnUrl = '';
					  if(result.res_code == "SF_ORDR_1001"){
						  _paq.push(['trackEvent', 'orderpage', 'ztrsubmitorder','success',result.res_msg]);
					  }else{
						  _paq.push(['trackEvent', 'orderpage', 'ztrsubmitorder','error',result.res_msg]);
					  }
					  switch(result.res_code) {
						  case "EF_PDBK_1001":
							  $("#productOver").popup("open");
							  $("a.submenu").removeClass("sub-disable");
							  $("a.submenu").addClass("sub-link");
							  break;
						  case "EF_PDBK_1002":
							  $("#newPriceInfo").html(new Number(result.res_msg).toFixed(2));
							  price.showPriceInfo();
							  $("#priceChangeTip").popup("open");
							  $("a.submenu").removeClass("sub-disable");
							  $("a.submenu").addClass("sub-link");
							  break;
						  case	"EF_PDBK_1004"://用户已存在,但未登录
							  $("#accountInputer").val(result.res_msg);
							  $("#password").val("");
							  $("#submit-dlg").popup("open");
							  $("a.submenu").removeClass("sub-disable");
							  $("a.submenu").addClass("sub-link");
							  break;
						  case 	"EF_PDBK_1005":
						  		$("#orderFailed").popup("open");
						  		$("a.submenu").removeClass("sub-disable");
								  $("a.submenu").addClass("sub-link");
						  		break;
						  case 	"EF_PDBK_1008":
							  $("#commonErrorCont").html(result.res_msg);
							  $("#commonError").popup("open");
							  $("a.submenu").removeClass("sub-disable");
							  $("a.submenu").addClass("sub-link");
						  		break;
						  case 	"EF_PDBK_1009":
						  		$("#accountError").popup("open");
						  		$("a.submenu").removeClass("sub-disable");
								 $("a.submenu").addClass("sub-link");
						  		break;
						  case "SF_ORDR_1001":
							  var orderId = result.res_msg;
							  if(wxServer.charAt(wxServer.length - 1) == '/'){
								  window.location.href = wxServer + "weixin/orderPay/selectPayType?orderId=" + orderId;
							  }else{
								  window.location.href = wxServer + "/weixin/orderPay/selectPayType?orderId=" + orderId;
							  }
							  break;
						  case "FO_ACTIVITY_1025":
							  $("#couponUsed").popup("open");
							  $("a.submenu").removeClass("sub-disable");
							  $("a.submenu").addClass("sub-link");
							  break;
						  case "FO_ACTIVITY_1026":
							  $("#couponUsed").popup("open");
							  $("a.submenu").removeClass("sub-disable");
							  $("a.submenu").addClass("sub-link");
							  break;
						  case "FO_ACTIVITY_1027":
							  $("#couponUsed").popup("open");
							  $("a.submenu").removeClass("sub-disable");
							  $("a.submenu").addClass("sub-link");
							  break;
						  case "EF_MEMB_1036":
							  $("a.submenu").removeClass("sub-disable");
							  $("a.submenu").addClass("sub-link");
							  $("#newMobilePhone").val(result.res_msg);
							  $("#newVerifyCode").val("") ;
							  $("#newPassWord").val("") ;
							  manulDestoryTimer(t);
							  $("#regist-dlg").popup("open");
							  break;
						  default:
							  $("a.submenu").removeClass("sub-disable");
						  	  $("a.submenu").addClass("sub-link");
							  break;
					  }
				  }
			});
		},
		userLogin : function(){
			var userName = $("#accountInputer").val();
			var passWord = $("#password").val();
			var verifyCode = "";
			passWord = escape(passWord) ;
			_paq.push(['trackEvent', 'login', 'ztrorderlogin', userName]);
			$.ajax({
			    url: ssoServer + '/sso/wx/login' ,
			    data: 'account=' + userName + "&password=" + passWord + "&rememberMe=false&verifyCode=" + verifyCode ,
			    dataType:'jsonp',
			    jsonp:'callback',
			    success: function(data){
			    	 $("#submit-dlg").popup({
							afterclose: function() {
								if(data.res_code == 'EF_MEMB_1027'){
						    		$("#commonErrorCont").html("用户名或密码错误");
									$("#commonError").popup("open");
//						    		alert('用户名或密码错误');
						    	}else if(data.res_code == 'EF_MEMB_1026'){
						    		$("#commonErrorCont").html("用户名或密码错误");
									  $("#commonError").popup("open");
//						    		alert('用户名或密码错误');
						    	}else if(data.res_code == 'EF_MEMB_10261'){
						    		$("#commonErrorCont").html("账号异常,请联系客服");
									  $("#commonError").popup("open");
//						    		alert('账号异常,请联系客服');
						    	}else if(data.res_code == 'EF_MEMB_1028'){
						    		$("#commonErrorCont").html(data.res_msg);
									$("#commonError").popup("open");
//						    		alert('用户名或密码错误');
						    	}else if(data.res_code == 'EF_MEMB_1024'){
						    		$("#commonErrorCont").html(password_hint_error);
									 $("#commonError").popup("open");
//						    		alert('用户名或密码错误');
						    	}else if(data.res_code == 'SF_MEMB_1053'){
                                    $("#commonErrorCont").html("输入的账号或密码不能为空");
                                    $("#commonError").popup("open");
                               }else if(data.res_code == 'SF_MEMB_1005'){
						    		applyOrder.submit();
						    	}
							}
						});
			    	 $("#submit-dlg").popup("close");


			    }
			});

		}
}