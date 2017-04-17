$(function(){
	submitFunc();
	countUnread();
	/*阅读某一条提醒*/
	$('#noticelist').delegate('div.remind-column','click',function(){
		var noticeId = $(this).find('div.remind-left input').val();
		readNotice([noticeId]);
	});
	/*阅读所有提醒*/
	$('#readAllBtn').click(function(){
		var allIds = [];
		$('#noticelist div.remind-column.remind-column-bg div.remind-left input').each(function(){
			allIds.push($(this).val());
		});
		readNotice(allIds);
		_paq.push(['trackEvent', 'infopage', 'ztrmarkread']);
	});
});

function submitFunc(){
	var request = {};
	request.pageNo = $("input[name=pageNo]").val();
	request.pageSize = $("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : basepath + '/systemnotice/search',
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
			$("#noticelist").html(tableData);
			$("#pagination").html(paginationData);
		},
	});
}

function countUnread(){
	$.ajax({
		type : "POST",
		url: basepath+"/systemnotice/countunread",
		dataType: "json",
		success: function(response){
			if(response.res_code == 'success'){
				$('#unreadNum').html(response.res_msg);
			}
		},
	});
}
function readNotice(noticeIds){
	$.ajax({
		type: "POST",
		url: basepath+"/systemnotice/read",
		data: JSON.stringify(noticeIds),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType: "json",
		success: function(response){
			if(response.res_code == 'success'){
				$('#noticelist div.remind-column.remind-column-bg div.remind-left input').each(function(){
					for(index in noticeIds){
						if(noticeIds[index] == $(this).val()){
							$(this).parent().parent().removeClass('remind-column-bg');
							var icon = $(this).prev();
							var classStr = icon.attr('class');
							icon.attr('class', classStr.replace('pressed','formal'));
						}
					}
				});
				countUnread();
			}
		},
	});
}