//金额精确小数
function moneyPrescision(prescision){
	var paymentAmount = $("#payAmount").val();
	if(null!=paymentAmount){
		paymentAmount=new Number(paymentAmount/100).toFixed(prescision);
		$("#payAmountPrecision").html(paymentAmount);
	}

	var totalPrice = $("#totalPrice").val();
	if(null!=totalPrice){
		totalPrice=new Number(totalPrice/100).toFixed(prescision);
		$("#totalPricePrescision").html("￥"+totalPrice);
	}


	var discountCoupon = $("#discountCoupon").val();
	if(null!=discountCoupon){
		discountCoupon=new Number(discountCoupon/100).toFixed(prescision);
		$("#discountCouponPrescision").html("-￥"+discountCoupon);
	}


	var integral = $("#integral").val();
	if(null!=integral){
		integral=new Number(integral/100).toFixed(prescision);
		$("#integralPrecision").html("-￥"+integral);
	}


}
$(function(){
	moneyPrescision(2);

	//点击支付按钮
	$(".btnCenter").delegate("#jumpToPay","click",function(){
		var orderId=$("#orderId").val();
		var orderNo = $("#orderNo").html();
		var paymentType=$(".radioContent .active").find(".radioIcon").attr("name");
		var payAmount=$("#payAmountPrecision").html()*100;
		var checkSum = $("#checkSum").val();

		var useCoupon =$("#discountCoupon").val();
		var orderAmount = $("#totalPrice").val();
		var couponItemId = $("#couponItemId").val();
		var useRewardPoint = $("#integral").val();
		var productType = $("#productType").val();

	    var hwnd = window.open("", "_blank");

	    var url="";
	    if(paymentType != null && paymentType == "Alipay"){
	    	url = memberServer + "/orderPay/jumpToPay";
	    }else if(paymentType != null && paymentType == "WeChatpay"){
	    	url = memberServer + "/orderPay/jumpToWechatPay"
	    }
		$.ajax({
			type : "POST",
			url : url,
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
				if(paymentResponse.success){
					 _paq.push(['trackEvent', 'paypage', 'ztrselectpayorder', orderId, 'SUCCESS']);
					 if(paymentType != null && paymentType == "Alipay" &&  (paymentResponse.paymentUrl != null && paymentResponse.paymentUrl!='')){
						    hwnd.location=paymentResponse.paymentUrl;
					 } else if(paymentType != null && paymentType == "WeChatpay" &&  (paymentResponse.value != null && paymentResponse.value!='')){
							//跳转到二维码页面,传递code_url
					    	hwnd.location.href=memberServer+"/orderPay/toWechatScan?codeUrl="+paymentResponse.value+"&payAmount="+payAmount+"&orderNo="+orderNo;
					    }
					 $("#ac-payHintWindow").modal("show");
				}else{
					hwnd.close();
					_paq.push(['trackEvent', 'paypage', 'ztrselectpayorder', orderId, 'FAIL']);
					if(paymentResponse.errMsg!=null && paymentResponse.errMsg !=''){
						if("会员已挂起"==paymentResponse.errMsg){
							$("#ac-payErrorHintWindow-suspend").modal("show");
							return;
						}
						$("#orderPayErrorMsg").html(paymentResponse.errMsg);
					}else{
						$("#orderPayErrorMsg").html('账号异常，请与客服联系');
						$("#jumpTips").html("回到首页");
					}
					$("#ac-payErrorHintWindow").modal("show");
					return;
				}
			}
		});
	})
})



  $(function(){
    $(".modal").modal({
        backdrop:"static",
        keyboard: false,
        show: false
    });

//订单支付失败
    $("#ac-payHintWindow").delegate(".orangeBtn","click",function(){
    	_paq.push(['trackEvent', 'paypage', 'ztrpayfail', orderId, 'fail']);
    	//跳转到订单详情页面
    	jumpToDetail();
    })

    //订单支付成功
    $("#ac-payHintWindow").delegate(".blueBtn","click",function(){
    	jumpToDetail();
    })

    //订单已取消
    $("#ac-payErrorHintWindow").delegate(".blueBtn","click",function(){
    	jumpToDetail();
    })

});

	function jumpToDetail(){
		var orderId=$("#orderId").val();
		var orderIdOrigin = $("#orderIdOrigin").val();
		if(orderIdOrigin != null && orderIdOrigin != ''){
			orderId = orderIdOrigin;
		}
		var productType = $("#productType").val();
    	//跳转到订单详情页面
    	if(typeof(productType) != undefined && productType != ''){
    		if(productType == 'VISA' || productType == 'visa'){
     			window.location.href = memberServer + "/visaorder/front/detail/" + orderId ;
    		}else if(productType == 'UNVISA' || productType== 'TICKET' || productType== 'LOCAL' || productType== 'TRAFFIC' || productType== 'WIFI' || productType== 'HOTELUP' || productType== 'CHARTER' || productType== 'INTELTAXI'){
     			window.location.href = memberServer + "/localorder/front/detail/" + orderId ;
     		}else{
     			window.location.href = memberServer + "/order/front/detail/" + orderId ;
     		}
    	}else{
    		window.location.href= memberServer+"/order/front/detail/"+orderId;
    	}
	}