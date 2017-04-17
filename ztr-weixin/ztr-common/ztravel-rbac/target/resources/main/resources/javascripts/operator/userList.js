
var passwordPlaceholder = "********";
var passwordEditted = false;


$(function () {
        $(".modal").modal({
            backdrop: "static",
            keyboard: false,
            show: false
        });

        $("#edtUser input:password[name='password'], #edtUser input:password[name='confirmPassword']").keypress(function(event){
    		var which = event.which || event.keyCode;
    		if(which == 8 || which == 46
    				|| (which >=48 && which <=90)
    				|| (which >= 96 && which <= 111)
    				|| (which >= 186 && which <=192)
    				|| (which >= 219 && which <= 222)){
    			passwordEditted = true;
    		}
    	});
    	$("#edtUser input:password[name='password'], #edtUser input:password[name='confirmPassword']").change(function(){
    		passwordEditted = true;
    	});

    	$("#edtUser input:password[name='password'], #edtUser input:password[name='confirmPassword']").focusout(function(){
    		if($.trim($(this).val()) == '' && $.trim(getBrotherInput($(this)).val()) == ''){
    			$("#edtUser input:password[name='password'], #edtUser input:password[name='confirmPassword']").val(passwordPlaceholder);
    			passwordEditted = false;
    		}
    	});

    	$("#edtUser input:password[name='password'], #edtUser input:password[name='confirmPassword']").focusin(function(){
    		if(!passwordEditted){
    			$("#edtUser input:password[name='password'], #edtUser input:password[name='confirmPassword']").val('');
    		}
    	});

        // 下拉列表(事件需要动态绑定)
        $("body").delegate(".dropdown li a", "click", function(){
            var thisHtml = $(this).html();
            var $thisParents = $(this).parents(".dropdown-menu li");
            $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".role").html(thisHtml);
            $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".role").attr("roleId",$thisParents.attr("roleId"));
            $thisParents.addClass("active");
            $thisParents.siblings().removeClass("active");
        });

        submitFunc();

        $(".lightBlueBtn").click(function () {
        	$("input[name='pageNo']").val("1");
    		submitFunc();
        });

    	$("body").delegate(".deleUser", "click", function(){
    		var userId= $.trim($(this).parents("tr").find("#userId").val());
         	$("#delUserId").val(userId);
    		$("#deleModal").modal("show");
    	});

        $("body").delegate(".offline", "click", function(){
        	var str = $.trim($(this).text());
            if (str == "挂起") {
            	lockUser($(this));
            } else if (str = "解挂") {
            	unlockUser($(this));
            }
        });

        $(".operationDiv").click(function () {
        	$("#addUser input").val('');
//        	$("#addUser .role").html("角色选择");
//            $("#addUser .role").attr("roleId","none");
            $("#addUser .verifyStyle").hide();
        	getAllRoles($("#addUser"));
            $("#addUser").modal("show");
            $("#addUser .role").html("角色");
        });

        //编辑员工信息
        $("body").delegate(".edtLink", "click", function(){

//        	getAllRoles($("#edtUser"));

        	var userId =  $.trim($(this).parents("tr").find("#userId").val());
            var realName =  $.trim($(this).parents("tr").find("td").first().text());
            var employeeCode =  $.trim($(this).parents("tr").find("td").eq(1).text());
            var userName =  $.trim($(this).parents("tr").find("td").eq(2).text());
            var mobile =  $.trim($(this).parents("tr").find("#mobile").val());
            var email =  $.trim($(this).parents("tr").find("#email").val());
            var roleName =  $.trim($(this).parents("tr").find("td").eq(3).text());
			var roleId =  $.trim($(this).parents("tr").find("#roleId").val());

            $("#edtUser").find("input[name='userId']").val(userId);
            $("#edtUser").find("input[name='realName']").val(realName);
            $("#edtUser").find("input[name='mobile']").val(mobile);
            $("#edtUser").find("input[name='employeeCode']").val(employeeCode);
            $("#edtUser").find("input[name='userName']").val(userName);
            $("#edtUser").find("input[name='password']").val(passwordPlaceholder);
            $("#edtUser").find("input[name='confirmPassword']").val(passwordPlaceholder);
            $("#edtUser").find("input[name='email']").val(email);

            $.ajax({
        		type: "POST",
        		url: basepath + '/rbac/user/role/get',
        		headers : {
    				'Accept' : 'application/json',
    				'Content-Type' : 'application/json'
    			},
        		dataType : "html",
    			success : function(result) {
    				$("#edtUser").find(".dropdown").html(result);
    				if(roleName !='' && roleId !=''){
    					$("#edtUser").find(".role").html(roleName);
        	            $("#edtUser").find(".role").attr("roleId",roleId);
    				}
    			},
        	});

            $("#edtUser").modal("show");
            passwordEditted = false;
        });

        $('.save').click(function(){
        	var data = $(this).parents("form").serializeObject();
        	if(data.userId != ''){
        		if(data.password == passwordPlaceholder){
        			data.password = '';
        		}
        		if(data.confirmPassword == passwordPlaceholder){
        			data.confirmPassword = '';
        		}
        	}
        	if( $(this).parents("form").find(".role").attr("roleId") == "none" ){
        		data.roleId = "";
        	}else{
        		data.roleId = $.trim($(this).parents("form").find(".role").attr("roleId"));
        	}
        	if (validate(data,$(this).parents("form"))) {
        		if(data.userId == '' && data.password == ''){
        			error( '登录密码必填',$(this).parents("form"));
        		}else{
        			saveUser(data);
        		}
        	}
        });

    });

function saveUser(data){

	$.ajax({
		type : "POST",
		url : basepath + "/rbac/user/save",
		data : JSON.stringify(data),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "json",
		success : function(resp) {
			if( resp.res_code == "SO_RBAC_1005" ){
				window.location.href=basepath + "/rbac/user/list";
	    	}else if( resp.res_code == "FO_RBAC_1018" ){
	    		alert(resp.res_msg);
	    	}else if( resp.res_code == "FO_RBAC_1019" ){
	    		window.location.href = basepath + '/user/login';
	    	}else{
	    		alert("网络异常，请稍后重试");
	    	}
		}
	});
}

  //提交查询请求
    function submitFunc() {
    	var data = {};
    	data.realName = $.trim($("#realName").val());
    	data.employeeCode = $.trim($("#employeeCode").val());
    	if( $(".searchTab .role").attr("roleId") == "all" ){
    		data.roleId = "";
    	}else{
    		data.roleId = $.trim($(".searchTab .role").attr("roleId"));
    	}

    	data.pageNo = $("input[name=pageNo]").val();
    	data.pageSize = $("input[name=pageSize]").val();

    	if( data.realName.length <=10 &&  data.employeeCode.length <=10){
    		$.ajax({
    			type : "POST",
    			url : basepath+"/rbac/user/search",
    			data : JSON.stringify(data),
    			headers : {
    				'Accept' : 'application/json',
    				'Content-Type' : 'application/json'
    			},
    			dataType : "html",
    			success : function(result) {
    				var resultArray = result.split("<-split->");
    				var data = resultArray[0];
    				var pagination = resultArray[1];
    				$(".commonTab tbody").html(data);
    				$("#pagination").html(pagination);
    			},
    		})
    	}

    }

    function deleteUser(){
    	var userId =$.trim($("#delUserId").val());
    	$.ajax({
    		type: "POST",
    		url: basepath + '/rbac/user/delete',
    		data: {"userId":userId},
    		dataType : "json",
    		success: function (resp) {
    			$("#deleModal").modal("hide");
    			if( resp.res_code == "SO_RBAC_1003" ){
    				submitFunc();
    	    	}else{
    	    		alert("网络异常，请稍后重试");
    	    	}
    		}
    	});

      }

    function lockUser(section) {
    	var userId=$.trim(section.parents("tr").find("#userId").val());
    	$.ajax({
    		type: "POST",
    		url: basepath + '/rbac/user/suspend/lock',
    		data: {"userId":userId},
    		dataType : "json",
    		success: function (resp) {
    			if( resp.res_code == "SO_RBAC_1004" ){
    				section.text("解挂");
    	    	}else{
    	    		alert("网络异常，请稍后重试");
    	    	}
    		}
    	});
    }

    function unlockUser(section) {
    	var userId=$.trim(section.parents("tr").find("#userId").val());
    	$.ajax({
    		type: "POST",
    		url: basepath + '/rbac/user/suspend/unlock',
    		data: {"userId":userId},
    		dataType : "json",
    		success: function (resp) {
    			if( resp.res_code == "SO_RBAC_1004" ){
    				section.text("挂起");
    	    	}else{
    	    		alert("网络异常，请稍后重试");
    	    	}
    		}
    	});
    }

    function getAllRoles(section,isEdit) {
    	$.ajax({
    		type: "POST",
    		url: basepath + '/rbac/user/role/get',
    		headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
    		dataType : "html",
			success : function(result) {
				section.find(".dropdown").html(result);
			},
    	});
    }


    $.fn.serializeObject = function(){
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

