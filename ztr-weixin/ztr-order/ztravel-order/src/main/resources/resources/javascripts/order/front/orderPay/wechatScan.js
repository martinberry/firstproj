var timer;
var expireTimer;
$(function(){
	generateQrcode();
	timer = window.setInterval("queryOrderTradeState()", 3000);
	var countDown = $("#countDown").val();
	//如果不是补款单
	var productType = $("#productType").val();
	if(productType == null || productType != "OPCONFIRM"){
		expireTimer = window.setInterval("toHomePage()",countDown);
	}
})

function generateQrcode(){
	var codeUrl = $("#codeUrl").val();
	$("#qrCode div").qrcode({
	    render: "table", //table方式
	    width: 256, //宽度
	    height:256, //高度
	    text: codeUrl//任意内容
	});
	window.onresize();
}


function queryOrderTradeState(){
	var url = memberServer+ "/orderPay/queryWxOrderTradeState";
	var orderNo = $("#orderNo").val();
	var amount = $("#payAmount").val();
	amount = parseFloat(amount)*100;
	$.ajax({
		type : "GET",
		url : url,
		data :{
			orderId: orderNo
		},
		dataType : "json",
		success:function(result) {
			if(result.trade_state == "SUCCESS"){
				var payResultUrl = memberServer + "/orderPay/payResult";
				var $form = $('<form action="'+ payResultUrl +'" method="post" style="display:none;"></form>');
				$form.append('<input name="orderNum" value="'+orderNo+'"/>');
				$form.append('<input name="amount" value="'+amount+'"/>');
				$form.appendTo('body');
				$form.submit();
				window.clearInterval(timer);
			}
		},error:function(){

		}
	})
}

function toHomePage(){

	window.location.href = memberServer+"/home";
}