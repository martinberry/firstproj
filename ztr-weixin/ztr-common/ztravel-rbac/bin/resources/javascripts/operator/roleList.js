$(function(){

	$(".modal").modal({
        backdrop: "static",
        keyboard: false,
        show: false
    });

	$("body").delegate(".deleRole", "click", function(){
		var roleId= $.trim($(this).parents("tr").find("#roleId").val());
     	$("#delRoleId").val(roleId);
		$("#deleModal").modal("show");
	});

	submitFunc();

});

function deleteRole(){
	var roleId = $.trim($("#delRoleId").val());
	$.ajax({
		type: "POST",
		url: basepath + '/rbac/role/delete',
		data: {"roleId":roleId},
		dataType : "json",
		success: function (resp) {
			$("#deleModal").modal("hide");
			if( resp.res_code == "SO_RBAC_1001" ){
				submitFunc();
	    	}else{
	    		alert("网络异常，请稍后重试");
	    	}
		}
	});

  }


function submitFunc(){
	var data = {};

	data.pageNo =  $.trim($("input[name=pageNo]").val());
	data.pageSize =  $.trim($("input[name=pageSize]").val());

		$.ajax({
			type: "POST",
			url: basepath + '/rbac/role/list/page',
			data: JSON.stringify(data),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
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

