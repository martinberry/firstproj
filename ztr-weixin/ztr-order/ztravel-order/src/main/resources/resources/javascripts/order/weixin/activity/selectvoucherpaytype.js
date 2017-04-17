$(function(){
	$("#wechatPayType").click(function(){
		$('#aliRadio').prop('checked', false);
		$('#weChatRadio').prop('checked',true);
	})
		$("#aliPayType").click(function(){
			$('#weChatRadio').prop('checked',false);
			$('#aliRadio').prop('checked', true);
	})

	$(".choice-bed").click(function(){
	 $(this).find("input[type='radio']").prop("checked", "checked");
	})

	var openId = $("#openId").val();
	if(openId == null || openId ==""){
		$('#aliRadio').prop('checked', true);
	}

})
var lock = false ;
$(function(){
	setInterval(GetRTime,1000);

	//余额兑换按钮初始化
	$("#confirmBtn").click(function(e){
		e.preventDefault();
		var leftAmount = new Number($("#accountVo").val()).divide(100);
		if(leftAmount < 10){
			$("#commonErrorCont").html("10块钱以上才能兑换电话卡,攒攒呗");
			$("#commonError").popup("open");
		}else{
			window.location.href = wxServer + "/account/weixin/toconvert";
		}
	});

	//点击支付按钮
	$(".payment-foot").delegate(".pay-link","click",function(){
		var orderId=$("#orderId").val();
		var orderNo = $("#orderNo").val();

		var paymentType=$('input[name="type"]:checked ').val();
		var payAmount=$("#payAmount").val();
		var checkSum = $("#checkSum").val();

		var useCoupon =$("#discountCoupon").val();
		var orderAmount = $("#totalPrice").val();
		var couponItemId = $("#couponItemId").val();
		var useRewardPoint = $("#integral").val();
		var productType = $("#productType").val();
		if(!lock){
			lock = true ;
			if(paymentType == 'WeChatpay'){
				$.ajax({
					type : "POST",
					url : wxServer+"/weixin/voucherOrderPay/jumpToWechatPay",
					data :{
						paymentType:paymentType,
						payAmount:payAmount,
						orderId:orderId,
						orderNo:orderNo,
						checkSum:checkSum,
						useRewardPoint:useRewardPoint,
						useCoupon:useCoupon,
						couponItemId:couponItemId,
						orderAmount:orderAmount,
						productType:productType
					},
					dataType : "json",
					success : function(result) {
						var paymentResponse = result.paymentResponse;
						lock = false ;
						if(paymentResponse.success){
							//appId,timeStamp,nonceStr,pkg,signType,paySign ;
							$("#appId").val(result.appId) ;
							$("#timeStamp").val(result.timeStamp) ;
							$("#nonceStr").val(result.nonceStr) ;
							$("#signType").val(result.signType) ;
							$("#package").val(result.pkg) ;
							$("#paySign").val(result.paySign) ;
							$("#confirm-dialog").popup("open");
							wechatpay() ;
						}else{
							window.location.href = wxServer + "/weixin/voucherOrderPay/payfailed?voucherOrderId=" + orderId ;
						}
					},error:function(result){
						lock = false ;
					}
				});
			}else if(paymentType == 'Alipay'){
				var hwnd ;
				$.ajax({
					type : "POST",
					url : wxServer+"/weixin/voucherOrderPay/jumpToAliPay",
					data :{
						paymentType:paymentType,
						payAmount:payAmount,
						orderId:orderId,
						orderNo:orderNo,
						checkSum:checkSum,
						useRewardPoint:useRewardPoint,
						useCoupon:useCoupon,
						couponItemId:couponItemId,
						orderAmount:orderAmount,
						productType:productType
					},
					dataType : "json",
					success : function(result) {
						lock = false ;
						var paymentResponse = result.paymentResponse;
						if(paymentResponse.success && (paymentResponse.paymentUrl != null && paymentResponse.paymentUrl!='')){
							_paq.push(['trackEvent', 'paypage', 'ztrselectpayorder', orderId, 'SUCCESS']);
							$("#confirm-dialog").popup("open");
							if(is_weixin()){
								var payUrl = escape(paymentResponse.paymentUrl) ;
								window.location.href = wxServer + "/weixin/voucherOrderPay/blank?url=" + payUrl ;
							}else{
								window.location.href = paymentResponse.paymentUrl ;
							}
						}else{
							_paq.push(['trackEvent', 'paypage', 'ztrselectpayorder', orderId, 'FAIL']);
							window.location.href = wxServer + "/weixin/voucherOrderPay/payfailed?voucherOrderId=" + orderId ;
						}
					},error : function(result){
						lock = false ;
					}
				});
			}
		}
	})
})

function GetRTime(){
	var orderId=$("#orderId").val();
	t= $("#countDown").val();
	if(t > 0){
		t = t - 1000 ;
		$("#countDown").val(t);
	    var m=Math.floor(t/1000/60%60);
	    var s=Math.floor(t/1000%60);
	    if(m < 10){
	      m = '0' + m ;
	    }
	    if(s < 10){
		  s = '0' + s ;
	    }
	    $(".prg-price").html(m + ":" + s) ;
	}else{
	//	window.location.href= wxServer+"weixin/product/list";
	}

}

function onBridgeReady(){
    WeixinJSBridge.invoke(
       'getBrandWCPayRequest', {
           "appId":$("#appId").val(),     //公众号名称，由商户传入
           "timeStamp":$("#timeStamp").val(),         //时间戳，自1970年以来的秒数
           "nonceStr":$("#nonceStr").val(), //随机串
           "package":$("#package").val(),
           "signType":$("#signType").val(),         //微信签名方式：
           "paySign":$("#paySign").val() //微信签名
       },
       function(res){
           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
        	   window.location.href = window.location.href;
           }else{
        	   window.location.href = wxServer + "/weixin/voucherOrderPay/payfailed?voucherOrderId=" + $("#orderId").val() ;
           }
       }
   );
}

function wechatpay(){
	if (typeof WeixinJSBridge == "undefined"){
	    if( document.addEventListener ){
	        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	    }else if (document.attachEvent){
	        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
	        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    }
	}else{
		onBridgeReady() ;
	}
}

function is_weixin(){
	var ua = navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i)=="micromessenger") {
		return true;
 	} else {
		return false;
	}
}

function paysuccess(){
	  window.location.href=window.location.href;
};

function payfail(){
	//window.location.href = wxServer + "/order/weixin/list" ;
};
