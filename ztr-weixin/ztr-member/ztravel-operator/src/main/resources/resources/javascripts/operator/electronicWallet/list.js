$(function(){
	submitFunc();

	$("#couponUser").focusout(function(){
		var couponUser = $("#couponUser").val();
		var reg=/^[0-9]{8}$/;
		if( (couponUser != "") && !couponUser.match(reg)){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	$(".searchTab").delegate(".lightBlueBtn","click",function(){
		
		
		searchByParams();
	})


});


function searchByParams(){

	var criteria = {};
	criteria.pageNo = 1;
	criteria.pageSize=$("input[name=pageSize]").val();
	criteria.mid = $("#couponUser").val();
	criteria.useTimeFrom = $("#useTimeFrom").val();
	criteria.useTimeTo = $("#useTimeTo").val();
	criteria.couponCode = $("#couponCode").val();
	
    if($(".verifyFonts:visible").parent().length > 0){
    	return false;
    }
    
	$.ajax({
		type : "POST",
		url : basepath+"/operator/couponUserDetail/search",
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
	var criteria = {};
	criteria.pageNo = $("input[name=pageNo]").val();
	criteria.pageSize = $("input[name=pageSize]").val();
	criteria.mid = $("#couponUser").val();
	criteria.useTimeFrom = $("#useTimeFrom").val();
	criteria.useTimeTo = $("#useTimeTo").val();
	criteria.couponCode = $("#couponCode").val();


	$.ajax({
		type : "POST",
		url : basepath+"/operator/couponUserDetail/search",
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



