$(function(){
	submitFunc();
})

function searchByParams(){
	var criteria = {};
	criteria.pageNo = 1;
	criteria.pageSize=$("input[name=pageSize]").val();
	ajaxSearch(criteria);
}

//提交查询请求
function submitFunc() {
	var criteria = {};
	criteria.pageNo=$("input[name=pageNo]").val();
	criteria.pageSize=$("input[name=pageSize]").val();
	ajaxSearch(criteria);
}

function ajaxSearch(criteria){
	$.ajax({
		type : "POST",
		url : basepath+"/happy2016/gameRecord/search",
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

