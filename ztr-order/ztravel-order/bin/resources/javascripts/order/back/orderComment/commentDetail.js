$(function(){
	$(".modal").modal({
		backdrop:"static",
		keyboard: false,
		show: false
	});

	//通过
	$("#passBtn").click(function(){
		$("#successAndPublish").modal("show");
	});
	$("#passCommentBtn").click(function(){
		var commentId = $(this).attr("value");
		sendAuditRequest(commentId, 1);
	});

	//不通过
	$("#rejectBtn").click(function(){
		$("#failModal").modal("show");
	});
	$("#rejectCommentBtn").click(function(){
		var commentId = $(this).attr("value");
		sendAuditRequest(commentId, 0);
	});

});

function sendAuditRequest(commentId, auditResult){
	$.ajax({
		type: "POST",
		url: basepath + "/comment/audit",
		data: "commentId=" + commentId + "&auditResult=" + auditResult,
		dataType : "json",
		success: function(resp){
			if( resp.res_code == "SO_ORDR_2003" ){
				location.reload(true);
			}else if( resp.res_code == "FO_ORDR_2004" ){
				alert("评价审核异常");
			}
		}
	});
}
