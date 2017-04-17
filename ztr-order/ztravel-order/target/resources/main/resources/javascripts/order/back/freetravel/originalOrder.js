var giftSendMsgErrorHint = "礼盒发放须知限制在100个字符以内";
var outingNoticeMsgErrorHint = "出行通知须知限制在100个字符以内";

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

    setTravellerTable();

});

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