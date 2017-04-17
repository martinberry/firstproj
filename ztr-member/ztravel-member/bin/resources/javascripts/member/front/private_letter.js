$(function(){
	submitFunc();
	countUnread();

});

function submitFunc(){
	var request = {};
	request.pageNo = $("input[name=pageNo]").val();
	request.pageSize = $("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : basepath + '/privateletter/search',
		data : JSON.stringify(request),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			var dataArray = result.split("<-split->");
			var tableData = dataArray[0];
			var paginationData = dataArray[1];
			$("#msglist").html(tableData);
			$("#pagination").html(paginationData);
		},
	});
}

function countUnread(){
	$.ajax({
		type : "POST",
		url: basepath+"/privateletter/countunread/",
		dataType: "json",
		success: function(response){
			if(response.res_code == 'success'){
				$('#unreadNum').html(response.res_msg);
			}
		},

	});
}
function deleteLetter(letterId){
$("#delletterWindow").modal("show");
$(".commonBtn.blueBtn.delete").click(function(){
	if(letterId){
		$.ajax({
			type : "POST",
			url: basepath+"/privateletter/deleteletter/"+letterId,
			dataType: "json",
			success: function(response){
				if(response.res_code == 'success'){
					window.location.reload();
				}
			},
		});
	}
});
}