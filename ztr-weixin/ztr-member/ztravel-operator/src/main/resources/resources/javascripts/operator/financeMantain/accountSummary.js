$(function(){
	submitFunc();


	$(".searchTab").delegate(".lightBlueBtn","click",function(){
		searchByParams();
	})

	$(".dropdown").delegate(".dropdown-menu","click",function(){
		$(this).parents(".dropdown").find(".searchFormField").val($(this).find(".active a").html());
	})
	initExportExcel("#search-form", "#exportExcel", basepath + "/financeMantain/opera/accountSummary/exportExcel");

	$(".productTab").delegate(".greenFontsLink","click",function(){
		$("#frozenMemberId").val('');
		$("#unFrozenMemberId").val('');
		$("#frozenAccountType").val('');
		$("#unFrozenAccountType").val('');
		var accountType = $(this).parents("tr").find(".accountType").html();
		var memberId = $(this).parents("tr").find("[name='memberId']").val();
		var operate = $(this).html();
		if(null != operate && null != memberId && null !=accountType){
			if(operate == '冻结'){
				$("#frozenMemberId").val(memberId);
				$("#ac-frozen").modal();
				$("#frozenAccountType").val(accountType);
			}
			if(operate == '解冻'){
				$("#unFrozenMemberId").val(memberId);
				$("#ac-unfrozen").modal();
				$("#unFrozenAccountType").val(accountType);
			}
		}
	})

	$(".modal.fade").delegate(".blue-45c8dcButton","click",function(){
		$(this).parents(".modal.fade").modal("hide");
	})
});


function searchByParams(){

	var criteria = buildSearchCriteria();
	criteria.pageNo = 1;
	criteria.pageSize=$("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../accountSummary/search",
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
	criteria.pageSize = $("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../accountSummary/search",
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

	var accountStatus = $("#accountStatus").html();
	if(null!= accountStatus && "全部" !=accountStatus){
		if(accountStatus == "已冻结"){
			accountStatus = "FROZEN";
		}
		if(accountStatus == "未冻结"){
			accountStatus = "AVAILABLE";
		}
		criteria.accountStatus = accountStatus;
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

function frozenAccount(){
   	var memberId = $("#frozenMemberId").val();
   	var accountType = $("#frozenAccountType").val();
   	if(null != accountType){
   		if(accountType== '代金券'){
   			accountType = "COUPON";
   		}
   		if(accountType== '积分'){
   			accountType = "REWARD_POINT";
   		}
   	}

   	$.ajax({
			type: "POST",
			url: "../accountSummary/frozenAccount",
			data: {
				memberId:memberId,
				accountType:accountType
			} ,
			dataType : "json",
			success: function(resp){
				if( resp.res_code == "SO_FROZEN_1001" ){
					$("#ac-frozen").modal("hide");
					submitFunc();
				}else if(resp.res_code == "FO_FROZEN_1001" ){
				   alert(resp.res_msg);
				}
			}
		});
  }

function unFrozenAccount(){
   	var memberId =$("#unFrozenMemberId").val();
   	var accountType = $("#unFrozenAccountType").val();
  	if(null != accountType){
   		if(accountType== '代金券'){
   			accountType = "COUPON";
   		}
   		if(accountType== '积分'){
   			accountType = "REWARD_POINT";
   		}
   	}
   	$.ajax({
			type: "POST",
			url: "../accountSummary/ unFrozenAccount",
			data: {
				memberId:memberId,
				accountType:accountType
			} ,
			dataType : "json",
			success: function(resp){
				if( resp.res_code == "SO_UNFROZEN_1002" ){
					$("#ac-unfrozen").modal("hide");
					submitFunc();
				}else if(resp.res_code == "FO_UNFROZEN_1002" ){
				   alert(resp.res_msg);
				}
			}
		});
  }

$("#ac-frozen").find(".blue-45c8dcButton").click(function(){
	$("#ac-frozen").modal("hide");
});

$("#ac-unfrozen").find(".blue-45c8dcButton").click(function(){
	$("#ac-unfrozen").modal("hide");
});


