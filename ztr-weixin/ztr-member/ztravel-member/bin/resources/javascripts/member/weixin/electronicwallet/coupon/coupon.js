$(function(){
	$(".accountBtn").click(function(){
		$(this).addClass("current").siblings("span").removeClass("current");
	window.location.href=basepath+"/accountBalance/index"
});
	$(".btn-confirm").click(function(){
		$("#alert-errorhint-dialog").popup("close");
	})

})

$(function(){
	FastClick.attach(document.body);

	$("#cancelExchangeBtn").click(function(){
 		$("#alert-dialog-exchange").popup({
			afterclose: function() {
			}
		});
		$("#alert-dialog-exchange").popup("close");
	})

	$("#foot-exchangeCodeBtn").click(function(){
		$("#exchangeCode").val("");
	})


});



$(function(){
    $(".voucher-switch > li").each(function(index){
        $(this).click(function(){
            $(".voucher-switch > li").removeClass("current");
            $(this).addClass("current");
            var couponItemType =  $(this).html();
            changeStatusSearchFunc();
        });
    });
    submitFunc();
});

//提交查询请求
function submitFunc() {
var criteria = {};
var status = $(".voucher-switch .current").html().substring(0,3);
if(null!=status){
	if(status=='未使用'){
		criteria.status = "AVAILABLE";
	}
	if(status=='已使用'){
		criteria.status = "USED";
	}
	if(status=='已过期'){
		criteria.status = "EXPIRED"
	}
	if(status=='已赠送'){
		criteria.status = "SHARED"
	}
}

$.ajax({
	type : "POST",
	url : wxServer+"/coupon/search",
	data : JSON.stringify(criteria),
	headers : {
		'Accept' : 'application/json',
		'Content-Type' : 'application/json'
	},
	dataType : "html",
	success : function(result) {
		$("#theDataList").html(result);
	},
})
}

//提交查询请求
function changeStatusSearchFunc() {
var criteria = {};
var status = $(".voucher-switch .current").html().substring(0,3);
if(null!=status){
	if(status=='未使用'){
		criteria.status = "AVAILABLE";
	}
	if(status=='已使用'){
		criteria.status = "USED";
	}
	if(status=='已失效'){
		criteria.status = "INVALID"
	}
	if(status=='已赠送'){
		criteria.status = "SHARED"
	}
}

$.ajax({
	type : "POST",
	url : wxServer+"/coupon/search",
	data : JSON.stringify(criteria),
	headers : {
		'Accept' : 'application/json',
		'Content-Type' : 'application/json'
	},
	dataType : "html",
	success : function(result) {
		$("#theDataList").html(result);
	},
})
}

function isPhone(phoneNum){
	 var r= /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
	if(r.test(phoneNum)){
		return true;
	}else{
		return false;
	}
}


function validateSharer(){
	var result = "yes";
	var selfMobile = $("#selfMobile").val();
	var sharePhone = $("#sharer_phone").val();

	if(selfMobile == null || selfMobile == ""){
	    	$("#errorHintMsg").html("尚未绑定手机号，请到个人中心进行绑定");
			result = "no";
			return  result;
		 }

	if(!isPhone(sharePhone)){
    	$("#errorHintMsg").html("请输入正确的手机号");
		result = "no";
		return  result;
	 }

if(!isSelfPhone(sharePhone ,selfMobile)){
	$("#errorHintMsg").html("不能分享给自己");
	result = "no";
	return result;
}
var isRegistPhoneResult = isRegistPhone(sharePhone);
if(isRegistPhoneResult=="no"){
	$("#errorHintMsg").html("用户未注册");
	result = "no";
	return result;
}
if(isRegistPhoneResult=="error"){
	$("#errorHintMsg").html("");
	result = "error";
	return result;
}

if(result == "yes"){
	   	$("#errorHintMsg").html("");
}
	return result;
}

function isSelfPhone(sharePhone ,selfMobile){
var result = true;
if(sharePhone == selfMobile){
	result = false;
}
return result;
}

function isRegistPhone(sharePhone){
			var result ="yes";
		//手机号码对应的用户存在，不存在，提示用户未注册,
		 $.ajax({
				type : "get",
				url : basepath + "/coupon/checkMobile",
				data : {
					mobile:sharePhone
				},
				async:false,
				success : function(data) {
					if(data.res_code !='EF_MEMB_1002'){
						result = "no";
					}
				},
				error:function(data){
					result = "error";
					$("#alert-dialog").popup({
						afterclose: function() {
						}
					});
					$("#alert-dialog").popup("close");
				}
			});
		 return result;
}


function getNickNameByPhone(phone){
 $.ajax({
type : "get",
url : basepath + "/coupon/getNickNameByPhone",
data : {
	phone:phone
},
dataType : "json",
async:false,
success : function(data) {
	if(data.result=='success'){
		if(null!=data.destMember){
			$("#destMemberId").val(data.destMember.id);
		}
	}
	if(data.result=='fail'){
		$("#errorHintMsg").html("用户未注册");
		$("#alert-dialog").popup({
			afterclose: function() {
					$("#alert-errorhint-dialog").popup("open");
			}
		});
		$("#alert-dialog").popup("close");
	}
},
error:function(data){
	result = false;
	$("#alert-dialog").popup({
		afterclose: function() {
		}
	});
	$("#alert-dialog").popup("close");
}
});
}

function initShareVoucher(){
	$("#sharer_phone").val('');
	$("#share_couponItemId").val('');
	$("#shareAmount").val('');
}

$(function(){
	$(".btn-cancel").click(function(){
	   	$("#alert-dialog").popup({
			afterclose: function() {
			}
		});
		$("#alert-dialog").popup("close");
	})
})

$(function(){

$("#btn-share").unbind("click");
$("#btn-share").bind("click",function(e){
	  e.preventDefault();
       //获取分享者，被分享者id,代金券id
	   var phone = $("#sharer_phone").val();
       if($("#btn-share").hasClass("hasShared")){
       			return false;
       }
        var varlidateSharerResult = validateSharer();
    	if(varlidateSharerResult == "yes"){
    		getNickNameByPhone(phone);
    	}else if(varlidateSharerResult == "no"){
    		$("#alert-dialog").popup({
				afterclose: function() {
						$("#alert-errorhint-dialog").popup("open");
					}
				});
			$("#alert-dialog").popup("close");
    	}else if(varlidateSharerResult == "error"){
    		return false;
    	}

       var sharerId = $("#currentMemberId").val();
       var destMemberId = $("#destMemberId").val();
       var couponItemId = $("#couponItemId").val();
       var shareAmount = $("#shareAmount").val();


    if(null!=couponItemId && "" !=couponItemId && null !=destMemberId && "" !=destMemberId && varlidateSharerResult =="yes"){
		//判断分享者与被分享者是否挂起，如果有挂起的跳转到首页，如果木有继续分享
		 $.ajax({
				type : "get",
				url : basepath + "/coupon/transferCouponItem",
				data : {
					couponItemId: couponItemId,
					destMemberId: destMemberId,
					shareAmount: shareAmount
				},
				dataType : "json",
				async:false,
				success : function(data) {
					if(data.success){
						$("#shared-tip").show();
						$("#btn-share").hide();
						$("#btn-cancel").hide();
						$("#btn-know").removeClass("hiddenBtn");
						$("#btn-share").addClass("hasShared");
						  $("#sharer_phone").attr("readonly",true);
						  $("#confirmShare").addClass("grayBtn");
						  _paq.push(['trackEvent', 'ewalletpage', 'ztrcouponshare', couponItemId, 'success']);
					}else{
				    	$("#errorHintMsg").html(data.errMsg);
				    		$("#alert-dialog").popup({
							afterclose: function() {
									$("#alert-errorhint-dialog").popup("open");
							}
						});
			    		_paq.push(['trackEvent', 'ewalletpage', 'ztrcouponshare', couponItemId, 'fail']);
						$("#alert-dialog").popup("close");
					}
				}
			});
	}

    })

    $("#btn-know").unbind("click");
    $("#btn-know").bind("click",function(){
		$("#alert-dialog").popup({
			afterclose: function() {
			}
		});
		$("#destMemberId").val('');
		  $("#sharer_phone").attr("readonly",false);
		$("#alert-dialog").popup("close");
		$("#shared-tip").hide();
		$("#btn-share").show();
		$("#btn-cancel").show();
		$("#btn-know").addClass("hiddenBtn");
		  $("#confirmShare").removeClass("grayBtn");
        submitFunc();
    })
})


$(function(){

	var exchange_lock = false;
	 $("#confirmExchangeBtn").click(function(){
		 var exchangeCode = $("#exchangeCode").val();
		 if(exchangeCode == null || exchangeCode =="" || !validateExchangeCode()){
			 $("#errorHintMsg").html("兑换码输入有误，请重新输入");
	    		$("#alert-dialog-exchange").popup({
				afterclose: function() {
						$("#alert-errorhint-dialog").popup("open");
				}
			});
	    		$("#alert-dialog-exchange").popup("close");
			 return false;
		 }

		 if(!exchange_lock){
			 exchange_lock = true;
			 $.ajax({
				 type:"GET",
				 url:basepath + "/coupon/exchange",
				 data:{
					 exchangeCode : exchangeCode
				 },
				 success:function(data){
					 	exchange_lock = false;
						if(data.res_code=='success'){
									$("#exchanged-tip").show();
									$("#cancelExchangeBtn").hide();
									$("#confirmExchangeBtn").hide();
									$("#btn-exchanged-know").removeClass("hiddenBtn");
									$("#confirmExchangeBtn").addClass("hasShared");
									  $("#exchangeCode").attr("readonly",true);
									  $("#confirmExchangeBtn").addClass("grayBtn");

						}else{
						 	$("#errorHintMsg").html("兑换码输入有误，请重新输入");
				    		$("#alert-dialog-exchange").popup({
								afterclose: function() {
										$("#alert-errorhint-dialog").popup("open");
								}
							 });
				    		$("#alert-dialog-exchange").popup("close");
						}
				 },
				 error:function(data){
						exchange_lock = false;
				 }
			 })
		 }


	 })

	 $("#btn-exchanged-know").unbind("click");
   　 $("#btn-exchanged-know").bind("click",function(){
		$("#alert-dialog").popup({
			afterclose: function() {
			}
		});
		$("#exchangeCode").val("");
		$("#exchangeCode").attr("readonly",false);
		$("#alert-dialog").popup("close");
		$("#exchanged-tip").hide();
		$("#cancelExchangeBtn").show();
		$("#confirmExchangeBtn").show();
		$("#btn-exchanged-know").addClass("hiddenBtn");
		$("#confirmExchangeBtn").removeClass("grayBtn");

		$("#alert-dialog-exchange").popup({
			afterclose: function() {
			}
		});
		$("#alert-dialog-exchange").popup("close");
        submitFunc();
    })

    $("#exchange-success-ok").click(function(e){
		 submitFunc();
	 })

	 $("#refund-success-ok").click(function(e){
		 submitFunc();
	 });

   　 var refund_lock = false;
     $("#confirm-refund-btn").click(function(e){
	   	  var couponItemId = $("#refund-couponItemId").val();
	   	  if(!refund_lock){
	   		refund_lock = true;
	   		 $.ajax({
	      		  type:"GET",
	      		  url:"../coupon/refundVoucher",
	      		  data:{
	      			  couponItemId : couponItemId
	      		  },
	      		  success: function(data){
	      			refund_lock = false;
	      			  if(data.res_code=="success"){
	      				$("#alert-dialog-refund").popup({
	      					afterclose: function() {
	      						$("#alert-refundSuccess-dialog").popup("open");
	      					}
	      				});
	      				$("#alert-dialog-refund").popup("close");
	      				  submitFunc();
	      			  }else{
	      				$("#alert-dialog-refund").popup("close");
	      			  }
	      		  },
	      		  error: function(data){
	      			refund_lock = false;
	      			  $("#alert-dialog-refund").popup("close");
	      		  }
	      	  })
	   	  }

     });

     $("body").delegate("a[name='refundCoupon']","click", function(){
    	 var couponItemId = $(this).parents(".bottom").find("[name='couponItemId']").val();
         if(null!=couponItemId){
          	$("#refund-couponItemId").val(couponItemId);
         }
         $("#alert-dialog-refund").show();
     });


})


 function validateExchangeCode(){
	 var result = true;
 	  var exchangeCodeRegx = /^[A-Za-z0-9]{6}$/;
 	  var exchangeCode = $("#exchangeCode").val();
	  if(!exchangeCodeRegx.test(exchangeCode)){
		  	result= false;
			 $("#errorHintMsg").html("兑换码输入有误，请重新输入");
	    		$("#alert-dialog-exchange").popup({
				afterclose: function() {
						$("#alert-errorhint-dialog").popup("open");
				}
			});
    		$("#alert-dialog-exchange").popup("close");
      }
	  return result;
 }

$(function(){

	$("#refundSuccessOk").click(function(){
		$("#alert-refundSuccess-dialog").popup("close");
	})
})

