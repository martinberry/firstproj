$(function(){
	submitFunc();

	initExportExcel("#search-form", "#exportExcel", basepath + "/financeMantain/opera/trade/exportExcel");

	$(".dropdown").delegate(".dropdown-menu","click",function(){
		$(this).parents(".dropdown").find(".searchFormField").val($(this).find(".active a").html());
	})

	$(".searchTab").delegate(".lightBlueBtn","click",function(){
		searchByParams();
	})


});




function searchByParams(){

	var criteria = buildSearchCriteria();
	criteria.pageNo = 1;
	criteria.pageSize=$("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../trade/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			var dataArray = result.split("<-split->");
			var tableData = dataArray[0];
			var paginationData = dataArray[1];
			$(".commonTab  tbody").html(tableData);
			$("#searchField").html(paginationData);
		},
	})
}

//提交查询请求
function submitFunc() {
	var criteria = buildSearchCriteria();
	criteria.pageNo = $("input[name=pageNo]").val();
	criteria.pageSize=$("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../trade/search",
//		data: {},
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			var dataArray = result.split("<-split->");
			var tableData = dataArray[0];
			var paginationData = dataArray[1];
			$(".commonTab  tbody").html(tableData);
			$("#searchField").html(paginationData);
		},
	})
}


function buildSearchCriteria(){
	var criteria = {};
	var mid = $("#mid").val();
	if(null!=mid && ""!=mid){
		criteria.memberId =  mid;
	}
	var orderId = $("#orderId").val();
	if(null!= orderId && ""!=orderId){
		criteria.orderId = orderId;
	}
	
	var orderType=$("#orderType").html();
	if(null!= orderType && "全部" !=orderType){
		if(orderType == "支付单"){
			orderType = "PAY_ORDER";
		}
		if(orderType == "退款单"){
			orderType = "REFUND_ORDER";
		}
		if(orderType=="取消单"){
			orderType="CANCEL_ORDER";
		}
		if(orderType=="OP确认补款单"){
			orderType="OP_REPAIR_ORDER";
		}
		if(orderType=="OP确认退款单"){
			orderType="OP_REFUND_ORDER";
		}
		criteria.orderType = orderType;
	}
	

	var tradeType = $("#tradeType").html();
	if(null!= tradeType && "全部" !=tradeType){
		if(tradeType == "支付"){
			tradeType = "PAYMENT";
		}
		if(tradeType == "退款"){
			tradeType = "REFUND";
		}
		criteria.tradeType = tradeType;
	}


	var productType = $("#productType").html();
	if(null!= productType && "全部" !=productType){
		if("自由行" == productType){
			productType = "TRAVEL";
		}else if("代金券" == productType){
			productType = "VOUCHER";
		}else if("OP确认差额单"==productType){
			productType="OPCONFIRM";
		}
		criteria.productType = productType;
	}

	var tradeStatus = $("#tradeStatus").html();
	if(null!=tradeStatus && "全部" != tradeStatus){
		if("成功"== tradeStatus){
			tradeStatus = "SUCCESS";
		}
		if("失败"== tradeStatus){
			tradeStatus = "FAIL";
		}
		if("请求"== tradeStatus){
			tradeStatus = "REQUEST";
		}
		criteria.tradeStatus = tradeStatus;
	}
	var  paymentType = $("#paymentType").html();
	if(null!=paymentType && "全部" !=paymentType){
		if(paymentType == '支付宝支付'){
			paymentType = "Alipay";
		}
		if(paymentType == '微信支付'){
			paymentType = "WeChatpay";
		}
		if(paymentType == '积分支付'){
			paymentType = "RewardPoint";
		}
		if(paymentType == '代金券支付'){
			paymentType = "Coupon";
		}
		criteria.paymentType = paymentType;
	}

	var conditionTradeFrom = $("#conditionTradeFrom").val();
	var conditionTradeTo = $("#conditionTradeTo").val();
	if(null!=conditionTradeFrom && "" !=conditionTradeFrom){
		criteria.conditionTradeFrom = new Date(conditionTradeFrom).getTime();
	}
	if(null!=conditionTradeTo && ""!=conditionTradeTo){
		criteria.conditionTradeTo = new Date(conditionTradeTo).getTime();
	}
	var traceNum=$("#traceNum").val();
	if(null!=traceNum && ""!=traceNum){
		criteria.traceNum = traceNum;
	}
	return criteria;
}

function initExportExcel(formSelector, buttonSelector, url) {
	$('body').delegate(buttonSelector, "click", function() {
		$(formSelector).attr("method", "post");
		var oldUrl = $(formSelector).attr("action");
		$(formSelector).attr("action", url);
		$(formSelector).submit();
		$(formSelector).attr("action", oldUrl);
	});
}


