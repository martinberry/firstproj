  var roleNameReg=/^([a-zA-Z0-9|\u4e00-\u9fa5|.]){1,10}$/;

$(function(){

        $(".modal").modal({
            backdrop:"static",
            keyboard: false,
            show: false
        });

        $("#redBtn").click(function(){
        	var roleName =  $.trim($("#roleName").val());
            if(!roleNameReg.test(roleName)){
            	$(".verifyStyle").children("span").html("角色名称为1-10个中英文字符或数字");
            	$(".verifyStyle").show();
            }else if($("tr .active").size() < 1){
            	$(".verifyStyle").children("span").html("至少勾选一个权限");
            	$(".verifyStyle").show();
            }else{
            	$(".verifyStyle").hide();
            	saveRole();
            }
        });

        $("#confirmBtn").click(function(){
        	location.href=basepath + "/rbac/role/list";
        });

    })

    function saveRole(){

	var data = {};
	data.roleId = $.trim($("#roleId").val());
	data.roleName = $.trim($("#roleName").val());
	var permissionIds = [];
	$("tr .active").parents("tr").children("#permissionId").each(function (i) {
		permissionIds[i] = $.trim($(this).val());
		});
	data.permissionIds = permissionIds;

	$.ajax({
		type : "POST",
		url : basepath + "/rbac/role/save",
		data : JSON.stringify(data),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "json",
		success : function(resp) {
			if( resp.res_code == "SO_RBAC_1002" ){
				$("#conserveMaintain").modal("show");
	    	}else if( resp.res_code == "FO_RBAC_1007" ){
	    		alert(resp.res_msg);
	    	}else{
	    		alert("网络异常，请稍后重试");
	    	}
		}
	});
}