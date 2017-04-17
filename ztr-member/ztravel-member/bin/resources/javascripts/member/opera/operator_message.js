$(function(){
	submitFunc();

	$("#searchField").delegate(".commonTab tbody tr", "click", function(){
		var $unread = $(this).find("td i.hot-icon");
		if($unread && $unread.length >0){
			var id = $(this).attr('value');
			if(id)readOne(id,$unread);
		}
	});


});

function submitFunc(){
	var pageNo = $("input[name=pageNo]").val();
	var pageSize = $("input[name=pageSize]").val();

	$.ajax({
		type: "POST",
		url: basepath + '/operation/message/load',
		data: 'pageNo='+pageNo+'&pageSize='+pageSize,
		dataType : "html",
		success: function (result) {
			var resultArray = result.split("<-split->");
			var tbody = resultArray[0];
			var pagination = resultArray[1];
			$(".commonTab tbody").html(tbody);
			$("#pagination").html(pagination);
			}
	});
}

function readOne(id, $unread){
	$.ajax({
		type: "POST",
		url: basepath + '/operation/message/read/'+id,
		dataType : "json",
		success: function (result) {
			if(result.res_code == 'success'){
				$unread.remove();
			}else{
				console.log(result);
			}
		}
	});
}
function deleteOne(id){
	$.ajax({
		type: "POST",
		url: basepath + '/operation/message/delete/'+id,
		dataType : "json",
		success: function (result) {
			if(result.res_code == 'success'){
				submitFunc();
			}else{
				console.log(result);
			}
		}
	});
}