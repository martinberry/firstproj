var giftSendMsgErrorHint = "礼盒发放须知限制在100个字符以内";
var outingNoticeMsgErrorHint = "出行通知须知限制在100个字符以内";
var ENABLE_OPCONFIRM = false;

$(function(){
	//操作日志伸缩
    $(".titleRight .unfold").click(function(){
    	$(this).hide();
    	$(this).siblings(".packUp").show();
    	$(".flexibleContent").show();
    });
    $(".titleRight .packUp").click(function(){
    	$(this).hide();
    	$(this).siblings(".unfold").show();
    	$(".flexibleContent").hide();
    });

    //弹窗初始化
    $(".commonInitialize").modal({
    	backdrop:"static",
    	keyboard: false,
    	show: false
    });

//    //取消订单弹窗
//    $(".ac-cancelOrder").click(function(){
//    	var orderId = $(this).attr("value");
//    	$("#cancelOrderDlg").modal("show");
//    	$("#cancelOrderBtn").val(orderId);
//    });
    //确认取消订单
    $("#cancelOrderBtn").click(function(){
    	var orderId = $(this).val();
    	$.ajax({
			type: "POST",
			url: basepath + "/order/freetravel/cancelOrder",
			data: orderId,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success: function(resp){
				if( resp.res_code == "FO_ORDR_1008" ){
					alert("取消订单失败");
				}else if( resp.res_code == "SO_ORDR_1009" ){
					alert("取消订单成功");
					location.reload(true);
				}
				$(".ac-cancelOrder").attr("onclick", "popupCancelOrderDialog(this)")   //ajax请求发出后，enable"取消订单"
			}
		});
    	$(".ac-cancelOrder").removeAttr("onclick")   //ajax请求发出后，disable"取消订单"
    });

    //op确认弹窗
    $("#opConfirm").click(function(){
    	if(ENABLE_OPCONFIRM){
    		var orderId = $(this).val();
    		$("#opConfirmDlg").modal("show");
    		$("#opConfirmBtn").val(orderId);
    	}
    });
  //op确认
    $("#opConfirmBtn").click(function(){
    	if(!ENABLE_OPCONFIRM)return;
    	var orderId = $(this).val();
    	$.ajax({
			type: "POST",
			url: basepath + "/order/freetravel/opConfirm",
			data: orderId,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success: function(resp){
				if( resp.res_code == "FO_ORDR_1004" ){
					alert("OP确认失败");
				}else if( resp.res_code == "SO_ORDR_1005" ){
					$("#ac-OPSure").modal("show");
				}
				$("#opConfirm").removeAttr("disabled");  //ajax请求成功返回后，enable OP确认button
			}
		});
    	$("#opConfirm").attr("disabled", true);  //ajax请求发出后，disable OP确认button
    });
    $("#ac-OPSure").on("hide.bs.modal", function(e) {
    	location.reload(true);
    });

    //礼盒发放弹窗
    $("#sendGift").click(function(){
    	var orderId = $(this).val();
    	$("#sendGiftDlg").modal("show");
    	$("#sendGiftBtn").val(orderId);
    });
    //礼盒发放
    $("#sendGiftBtn").click(function(){
    	var orderId = $(this).val();
    	var giftSendMessage = $("#sendGiftInputer").val();
    	var inputer = $("#sendGiftInputer");
    	if( giftSendMessage.length > 100 ){
    		$(inputer).siblings(".verifyStyle").find(".verifyFonts").text(giftSendMsgErrorHint);
    		showErrorHint(inputer);
    	}else{
    		hideErrorHint(inputer);
    		$.ajax({
    			type: "POST",
    			url: basepath + "/order/freetravel/sendGiftBox",
    			data: 'orderId=' + orderId + '&giftSendMessage=' + giftSendMessage,
    			dataType : "json",
    			success: function(resp){
    				if( resp.res_code == "FO_ORDR_1002" ){
    					alert("礼盒发放失败");
    				}else if( resp.res_code == "SO_ORDR_1003" ){
    					$("#ac-distributionBox").modal("show");
    				}
    				$("#sendGift").removeAttr("disabled");  //ajax请求成功返回后，enable 礼盒发放button
    			}
    		});
    		$("#sendGift").attr("disabled", true);  //ajax请求发出后，disable 礼盒发放button
    	}
    });
    $("#ac-distributionBox").on("hide.bs.modal", function(e) {
    	location.reload(true);
    });

    //出行通知弹窗
    $("#sendOutingNotice").click(function(){
    	var orderId = $(this).val();
    	$("#sendOutingNoticeDlg").modal("show");
    	$("#sendOutingNoticeBtn").val(orderId);
    });
    //出行通知
    $("#sendOutingNoticeBtn").click(function(){
    	var orderId = $(this).val();
    	var outNoticeMessage = $("#outingNoticeInputer").val();
    	var inputer = $("#outingNoticeInputer");

    	if( outNoticeMessage.length > 100 ){
    		$(inputer).siblings(".verifyStyle").find(".verifyFonts").text(outingNoticeMsgErrorHint);
    		showErrorHint(inputer);
    	}else{
    		hideErrorHint(inputer);
    		$.ajax({
    			type: "POST",
    			url: basepath + "/order/freetravel/sendOutingNotice",
    			data: 'orderId=' + orderId + '&outNoticeMessage=' + outNoticeMessage,
    			dataType : "json",
    			success: function(resp){
    				if( resp.res_code == "FO_ORDR_1006" ){
    					alert("发送出行通知失败");
    				}else if( resp.res_code == "SO_ORDR_1007" ){
    					$("#ac-travelToInform").modal("show");
    				}
    				$("#sendOutingNotice").removeAttr("disabled");  //ajax请求成功返回后，enable 出行通知button
    			}
    		});
    		$("#sendOutingNotice").attr("disabled", true);  //ajax请求发出后，disable 出行通知button
    	}
    });
    $("#ac-travelToInform").on("hide.bs.modal", function(e) {
    	location.reload(true);
    });

    //保存按钮
    $("#saveBtn").click(function(){
    	var orderId = $(this).val();
    	var remarks = $("#remarkInputer").val();
    	if( remarks.length > 200 ){
    		alert("备注限制在200个字符以内");
    		return;
    	}
    	$.ajax({
			type: "POST",
			url: basepath + "/order/freetravel/save",
			data: 'orderId=' + orderId + '&remarks=' + remarks,
			dataType : "json",
			success: function(resp){
				if( resp.res_code == "FO_ORDR_1010" ){
					alert(resp.res_msg);
				}else if( resp.res_code == "SO_ORDR_1011" ){
					location.reload(true);
				}
			}
		});
    });

    setOperationBlock();
    setTravellerTable();
//    hideZeroTd($("#feeDetailTable"));
//    hideZeroTd($("#payinfoTable"));

});

//取消订单弹窗
function popupCancelOrderDialog(selector){
	var orderId = $(selector).attr("value");
	$("#cancelOrderDlg").modal("show");
	$("#cancelOrderBtn").val(orderId);
}

//根据订单状态设置操作栏
function setOperationBlock(){
	var status = $("#orderStatus").attr("value");
	var commonOrderType = $("#commonOrderType").val();
	switch(status){
	case "REFUNDFAILED":
	case "UNPAY":
		$("#cancelOrderOperaBlock").show();
		$("#cancelOrderOperaBlock").siblings(".orderSpan").hide();
		break;
	case "PAYED":
		//根据退/补款单设置操作
		var $showBlock = $("#OPconfirmOperaBlock");
		$showBlock.show();
		$showBlock.siblings(".orderSpan").hide();
		var commonOrderStatus = $("#commonOrderStatus").val();
		if(commonOrderType && commonOrderType === 'OP_CONFIRM_EQUAL'){
			//OP确认
			ENABLE_OPCONFIRM = true;
			$("#opConfirm").show();
		}else{
			if(commonOrderStatus){
				switch(commonOrderStatus){
				case "INIT":
				case "UNPAY":
					//OP确认(灰)
					ENABLE_OPCONFIRM = false;
					var $opConfirm = $("#opConfirm");
					$opConfirm.removeClass("blue-45c8dcButton");
					$opConfirm.show();
					break;
				case "PAID":
				case "REFUNDING":
				case "REFUNDED":
				case "REFUNDFAIL":
					//OP确认
					ENABLE_OPCONFIRM = true;
					$("#opConfirm").show();
					break;
				case "AUDIT_UNPASS":
					//OP受理
					$showBlock.find("a.travelConfirm").hide();
					$("#opAccept").show();
					break;
				default:
					//CANCELED
				}
			}else{
				//无退/补款单
				//OP受理
				$showBlock.find("a.travelConfirm").hide();
				$("#opAccept").show();
			}
		}
		break;
	case "CONFIRM":
		$("#giftSendOperaBlock").show();
		$("#giftSendOperaBlock").siblings(".orderSpan").hide();
		break;
	case "GIFTSEND":
		$("#outNoticeOperaBlock").show();
		$("#outNoticeOperaBlock").siblings(".orderSpan").hide();
		break;
	case "OUTNOTICE":
	case "OUTTING":
	case "COMPLETED":
	case "CANCELED":
	case "REFUNDING":
		$("#canceledOperaBlock").show();
		$("#canceledOperaBlock").siblings(".orderSpan").hide();
		break;
	default:
		$(".orderSpan").hide();
		break;
	}
	if(commonOrderType === 'OP_CONFIRM_REPAIR'){
		$("#minusedTotalPrice").addClass("fill");
	}else if(commonOrderType === 'OP_CONFIRM_REFUND'){
		$("#minusedTotalPrice").addClass("refund");
	}
}

//根据订单类型设置旅客表格(中/外)
function setTravellerTable(){
	var orderType = $("#orderType").attr("value");
	if( orderType == "INTERNATIONAL" ){
		$("#internationalTravellerTable").show();
		$("#internationalTravellerLable").addClass("current");
		$("#domesticTravellerTable").hide();
		$("#domesticTravellerLable").removeClass("current");
	}else if( orderType == "DOMESTIC" ){
		$("#domesticTravellerTable").show();
		$("#domesticTravellerLable").addClass("current");
		$("#internationalTravellerTable").hide();
		$("#internationalTravellerLable").removeClass("current");
	}
}

function showErrorHint(inputer){
	$(inputer).addClass("verifyBox");
	$(inputer).siblings(".verifyStyle").show();
}

function hideErrorHint(inputer){
	$(inputer).removeClass("verifyBox");
	$(inputer).siblings(".verifyStyle").hide();
}
function hideZeroTd($table){
	$table.find("tbody tr").each(function(){
		var $tr = $(this);
		var price = $tr.find("td span").html();
		var value = parseFloat(price);
		if(value === 0){
			var $th = $tr, rowspan;
			var $trWithTh = recur($tr);
			if($trWithTh){
				$th = $trWithTh.find("th");
				rowspan = $th.attr("rowspan");
			}else{
				$trWithTh = $tr;
			}
			if(rowspan){
				rowspan = parseInt(rowspan);
				rowspan--;
				if(rowspan === 0){
					$trWithTh.remove();
				}else{
					$th.attr("rowspan", rowspan);
					$tr.remove();
				}
			}else{
				$trWithTh.remove();
			}
		}
	});
}
function recur($tr){
	if(!$tr || $tr.length === 0)return;
	if($tr.find("th").length === 1)return $tr;
	return recur($tr.prev());
}