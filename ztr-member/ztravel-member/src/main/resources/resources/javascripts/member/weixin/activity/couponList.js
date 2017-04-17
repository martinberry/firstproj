var max_num_msg = "亲～您买的太多，考虑下别人！";
var mobile_hint_formaterror = '您输入的手机号有误' ;
var mobile_is_empty = '您输入的手机号为空' ;
var verifycode_hint_wrongcode = '您输入的验证码有误' ;
var verifycode_hint_retry = '验证码发送失败请重试' ;
$(function(){

	 var least = 1;
	 var most = 10;
     $(".counter > .counter-minus").click(function(){
         var minus_num = parseInt($(this).next().text());
         minus_num--;
         if(minus_num <= least){
             $(this).addClass("counter-disable");
             $(this).next().text(least);
             return false;
         }else{
             if($(this).hasClass("counter-disable")){
                 $(this).removeClass("counter-disable");
             }
         }
         if(minus_num < most){
             var obj = $(this).parent().find(".counter-add");
             if(obj.hasClass("counter-disable")){
                 obj.removeClass("counter-disable");
             }
         }
         $(this).next().text(minus_num);
     });
     $(".counter > .counter-add").click(function(){
         var add_num = parseInt($(this).prev().text());
         add_num++;
         if(add_num >= most){
             $(this).addClass("counter-disable");
             $(this).prev().text(most);
             return false;
         }else{
             if($(this).hasClass("counter-disable")){
                 $(this).removeClass("counter-disable");
             }
         }
         if(add_num > least){
             var obj = $(this).parent().find(".counter-minus");
             if(obj.hasClass("counter-disable")){
                 obj.removeClass("counter-disable");
             }
         }
         $(this).prev().text(add_num);
     });

     var buyLock = false ;
     $(".orange_buy").click(function(){
         var couponId = $(this.parentNode).attr("id");
         var activityId = this.parentNode.childNodes.item(3).value;
         var amount = $(this.parentNode.childNodes.item(5).childNodes.item(3)).text();
         if(!buyLock){
        	 buyLock = true ;
        	 $.ajax({
          		type : "POST",
          		url : wxServer + "/activity/coupon/weixin/buy",
          		data : "activityId="+activityId+"&couponId="+couponId+"&amount="+amount,
          		dataType: 'json',
          		success : function(result) {
          			if (result.res_code == "EF_CPBK_1011") {
          				showTip(result.res_msg);
          			} else if (result.res_code == "EF_CPBK_1002") {
          				window.location.href = result.res_msg;
          			} else if (result.res_code == "EF_CPBK_1006") {
           				window.location.href = wxServer + "/weixin/voucherOrderPay/selectPayType?combineOrderId=" + result.res_msg;
           			} else if (result.res_code == "EF_CPBK_1015") {
          				$("#chooseCouponId").val(couponId);
          				$("#chooseNum").val(amount);
          				$("#chooseActivityId").val(activityId);
          				$("#verifyCodeInputer").val("");
          				$("#submit-dlg").popup("open");
          			} else if (result.res_msg != "") {
          				showTip(result.res_msg);
          			} else {
          				showTip("抢购失败，请稍后重试～");
          			}
          			buyLock = false ;
          		},
     		    error : function(result){
     		    	buyLock = false ;
      				showTip("抢购失败，请稍后重试～");
     		    }
          	});
         }
     });

     var submitLock = false ;
     $("#submit-dlg .submit-dlg-confirm").click(function(){
 		var mobile = $("#mobileInputer").val();
    	 var verifyCode = $("#verifyCodeInputer").val();
  		var couponId = $("#chooseCouponId").val();
  		var amount = $("#chooseNum").val();
  		var activityId = $("#chooseActivityId").val();
  		var params = getParams(activityId, couponId, amount, mobile, verifyCode);
  		if(!submitLock){
  			submitLock = true ;
  			 $.ajax({
  	      		type : "POST",
  	      		url : wxServer + "/activity/coupon/weixin/apply",
  	      		data : JSON.stringify(params),
  	      		dataType: 'json',
  				  headers : {
  					  'Accept' : 'application/json',
  					  'Content-Type' : 'application/json'
  				  },
  	      		success : function(result) {
  	      			if (result.res_code == "EF_CPBK_1002") {
  	      				window.location.href = result.res_msg;
  	      			} else if (result.res_code == "EF_CPBK_1006") {
  	      				window.location.href = wxServer + "/weixin/voucherOrderPay/selectPayType?combineOrderId=" + result.res_msg;
  	      			} else if (result.res_msg != "") {
  	      				showErrMsg(result.res_msg);
  	      			} else {
  	      				showErrMsg("抢购失败，请稍后重试～");
  	      			}
  	      			submitLock = false ;
  	      		},error:function(){
  	      			submitLock = false ;
  	      		}
  	      	});
  		}
	});

     var verifycode_lock = false ;
 	$(".btn-vld").click(function(){
 		//var mobile = $("#mobileInputer").val() ;
 		var mobile = trim("#mobileInputer") ;
 		if (mobile == '') {
 			showErrMsg(mobile_is_empty);
 			return ;
 		}else if(!isMobile.test(mobile)){
 			showErrMsg(mobile_hint_formaterror);
 			return ;
 		}
 		if(!$(this).hasClass("retry") && !verifycode_lock) {
 			verifycode_lock = true ;
 			$.ajax({
 			    type: 'POST',
 			    url: wxServer + '/rl/sendCommonSms' ,
 			    data: 'mobile=' + mobile ,
 			    dataType: 'json' ,
 			    success : function(data){
 			    	if(data.res_code == 'EF_MEMB_1002'){
 			    		$("#mobileInputer").val(mobile) ;
 			    		showErrMsg(checkout_login);
 			    	}else if(data.res_code == 'EF_MEMB_1010'){
 			    		showErrMsg(verifycode_hint_retry);
 			    	}else if(data.res_code == 'EF_MEMB_1017'){
 			    		showErrMsg(mobile_hint_formaterror);
 			    	}else if(data.res_code == 'SF_MEMB_1010'){
 			    		$(".btn-vld").addClass("retry");
 			    		$(".btn-vld").html("<span>60</span>s后获取")
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
});

function getParams(activityId, couponId, amount, mobile, verifyCode){
	var params  = {};
	params.activityId = activityId;
	params.couponId = couponId;
	params.bookAmount = amount;
	params.mobile = mobile;
	params.verifyCode = verifyCode;
	return params;
}

function timer(intDiff){
	var timer = window.setInterval(function(){
		var second = 0;//时间默认值
		if(intDiff > 0){
			second = Math.floor(intDiff) ;
		}
		if (second <= 9) second = '0' + second;
		$(".btn-vld.retry").find("span").html(second) ;
		destoryTimer(timer,intDiff) ;
		intDiff--;
	}, 1000);
}

function destoryTimer(timer,intDiff){
	if(intDiff <= 0){
		clearInterval(timer) ;
		$(".btn-vld.retry").removeClass("retry");
		$(".btn-vld").html("获取验证码") ;
	}
}

function showTip(msg){
	$("#errHint").html(msg);
	$("#alert-buy-msg-dialog").popup();
	$("#alert-buy-msg-dialog").popup("open");
}

function validateMobile(){
	 var result = true;
	  var mobileRegx = /^[0-9]{11}$/;
	  var mobile = $("#mobileInputer").val();
	  if(!mobileRegx.test(mobile)){
		  	result= false;
		  	showErrMsg("验证码输入有误，请重新输入");
    }
	  return result;
}

function validateVerifyCode(){
	 var result = true;
	  var verifyCodeRegx = /^[0-9]{6}$/;
	  var verifyCode = $("#verifyCodeInputer").val();
	  if(!verifyCodeRegx.test(verifyCode)){
		  	result= false;
		  	showErrMsg("验证码输入有误，请重新输入");
     }
	  return result;
}

function showErrMsg (msg) {
	$(".check-error-tip").html(msg);
	$(".check-error-tip").addClass("show");
	setTimeout(function(){
		$(".check-error-tip").removeClass("show");
	},2000);
}