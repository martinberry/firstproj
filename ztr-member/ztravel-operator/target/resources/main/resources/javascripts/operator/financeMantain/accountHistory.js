$(function(){
	submitFunc();

	$(".dropdown").delegate(".dropdown-menu","click",function(){
		$(this).parents(".dropdown").find(".searchFormField").val($(this).find(".active a").html());
	})

	$(".searchTab").delegate(".lightBlueBtn","click",function(){
		searchByParams();
	})

		initExportExcel("#search-form", "#exportExcel", basepath + "/financeMantain/opera/accountHistory/exportExcel");


	$("#inoutDropdown").delegate(".dropdown-menu li a","click",function(){
		var inoutType = $(this).html();
		if(inoutType == '全部'){
			$("#sonMenu").html("<li class='active' role='presentation'><a href='javascript:void(0);'>全部</a></li><li class='income-subType'><a href='javascript:void(0);'>获得代金券</a></li><li class='income-subType'><a href='javascript:void(0);'>获得积分</a></li><li class='outcome-subType'><a href='javascript:void(0);'>支付票款</a></li><li class='income-subType'><a href='javascript:void(0);'>退款票款</a></li><li class='income-subType'><a href='javascript:void(0);'>分享代金券</a></li><li class='outcome-subType'><a href='javascript:void(0);'>代金券过期</a></li><li class='outcome-subType'><a href='javascript:void(0);'>积分过期</a></li><li class='outcome-subType'><a href='javascript:void(0);'>代金券退款</a></li>");
		}
		if(inoutType=='收入'){
			$("#sonMenu").html( "<li class='active' role='presentation'><a href='javascript:void(0);'>全部</a></li><li class='income-subType'><a href='javascript:void(0);'>获得代金券</a></li><li class='income-subType'><a href='javascript:void(0);'>获得积分</a></li><li class='income-subType'><a href='javascript:void(0);'>退款票款</a></li>");
		}
		if(inoutType=='支出'){
			$("#sonMenu").html("<li class='active' role='presentation'><a href='javascript:void(0);'>全部</a></li><li class='outcome-subType'><a href='javascript:void(0);'>支付票款</a></li><li class='income-subType'><a href='javascript:void(0);'>分享代金券</a></li><li class='outcome-subType'><a href='javascript:void(0);'>代金券过期</a></li><li class='outcome-subType'><a href='javascript:void(0);'>积分过期</a></li><li class='outcome-subType'><a href='javascript:void(0);'>代金券退款</a></li>");
		}
	})

	$("#sonMenu").delegate("li a","click",function(){
		$("#inoutDetailType").html($(this).html());
	})

});


function searchByParams(){
	var criteria =buildSearchCriteria();
	criteria.pageNo = 1;
	criteria.pageSize=$("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../accountHistory/search",
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
	var criteria =buildSearchCriteria();
	criteria.pageNo = $("input[name=pageNo]").val();
	criteria.pageSize = $("input[name=pageSize]").val();


	$.ajax({
		type : "POST",
		url : "../accountHistory/search",
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

	var accountType = $("#accountType").html();
	if(null!= accountType && "全部" !=accountType){
		if(accountType == "积分"){
			accountType = "REWARD_POINT";
		}
		if(accountType == "代金券"){
			accountType = "COUPON";
		}
		criteria.accountType = accountType;
	}

	var productType = $("#productType").html();
	if(null!= productType && "全部" !=productType){
		if(productType == "自由行"){
			productType = "TRAVEL";
		}
		if(productType == "代金券"){
			productType = "VOUCHER";
		}
		criteria.productType = productType;
	}

	var inoutType = $("#inoutType").html();
	if(null!= inoutType && "全部" !=inoutType){
		if(inoutType == "收入"){
			inoutType = "INCOME";
		}
		if(inoutType == "支出"){
			inoutType = "OUTGO";
		}
		criteria.inoutType = inoutType;
	}

	var inoutDetailType = $("#inoutDetailType").html();
	if(null!= inoutDetailType && "全部" !=inoutDetailType){
		if(inoutDetailType == "获得代金券"){
			inoutDetailType = "GRANTED_COUPON";
		}
		if(inoutDetailType == "获得积分"){
			inoutDetailType = "GRANTED_REWARD_POINT";
		}
		if(inoutDetailType == "支付票款"){
			inoutDetailType = "PAY_ORDER";
		}
		if(inoutDetailType == "退款票款"){
			inoutDetailType = "REFUND_ORDER";
		}
		if(inoutDetailType == "代金券过期"){
			inoutDetailType = "COUPON_EXPIRED";
		}
		if(inoutDetailType == "分享代金券"){
			inoutDetailType = "SHARED_COUPON";
		}
		if(inoutDetailType == "积分过期"){
			inoutDetailType = "REWARD_POINT_EXPIRED";
		}
		if(inoutDetailType == "代金券退款"){
			inoutDetailType = "REFUND_VOUCHER";
		}
		criteria.inoutDetailType = inoutDetailType;
	}

	var conditionOperateFrom = $("#conditionOperateFrom").val();
	if(null!=conditionOperateFrom && ""!=conditionOperateFrom){
		criteria.conditionOperateFrom =  conditionOperateFrom;
	}

	var conditionOperateTo = $("#conditionOperateTo").val();
	if(null!=conditionOperateTo && ""!=conditionOperateTo){
		criteria.conditionOperateTo =  conditionOperateTo;
	}

	var orderId = $("#orderId").val();
	if(null!=orderId && ""!=orderId){
		criteria.orderId =  orderId;
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



