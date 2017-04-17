$(function(){

	var ENABLE_OPACCEPT = true;

	//查看行程确认单发送邮件
    $("#sendEmailHeaderBtn").click(function(){
    	if(ENABLE_OPACCEPT){
    		ENABLE_OPACCEPT = false;
    		$("#sendEmailDlg").modal("show");
    	}
    });

  //编辑行程确认单确认完成
    $("#confirmHeaderBtn").click(function(){
    	if(isContactorEdit || isTravellerEdit || isAdditionalEdit || isCostDescriptionEdit || isFeesDetailEdit){
    		alert("尚有模块仍在编辑状态,请完成编辑!");
    		return;
    	}
    	if(ENABLE_OPACCEPT){
    		ENABLE_OPACCEPT = false;
    		$("#opAcceptDlg").modal("show");
    	}
    });

    //关闭op受理完成
    $("#closeOpAcceptBtn").click(function(){
    	ENABLE_OPACCEPT = true;
    });

    //关闭邮件发送
    $("#closeSendEmailBtn").click(function(){
    	ENABLE_OPACCEPT = true;
    });

    //op受理完成
    $("#opAcceptBtn").click(function(){
    	if(ENABLE_OPACCEPT) return;
    	var orderId = $('#orderId').val();
    	$.ajax({
			type: "POST",
			url: basepath + "/order/travelConfirm/confirm",
			data: orderId,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success: function(resp){
				ENABLE_OPACCEPT = true;
				if( resp.res_code == "SUCCESS" ){
					window.location.href = basepath + "/order/freetravel/detail/" + orderId;
				}else{
					alert("确认完成失败");
				}
			},
			error:function(result){
				ENABLE_OPACCEPT = true;
				alert("确认完成异常");
			}
		});
    });

    //取消op受理
    $("#cancelOpAcceptBtn").click(function(){
    	ENABLE_OPACCEPT = true;
    });

    //确认发送邮件
    $("#confirmSendBtn").click(function(){
    	if(ENABLE_OPACCEPT) return;
    	var orderId = $('#orderId').val();
    	var email = $("input[name='contactorEmail']").val();
    	if(!email){
    		alert("请填写邮箱");
		ENABLE_OPACCEPT = true;
    		return;
    	}
    	$.ajax({
			type: "POST",
			url: basepath + "/order/travelConfirm/sendEmail",
			data: {orderId:orderId, email:email},
			headers : {
				'Accept' : 'application/json',
			},
			dataType : "json",
			success: function(resp){
				ENABLE_OPACCEPT = true;
				if( resp.res_code == "SUCCESS" ){
					alert("发送邮件成功");
					$("input[name='contactorEmail']").val("") ;
				}else if (resp.res_msg != ""){
					alert("发送邮件失败[" + resp.res_msg + "]");
				}else{
					alert("发送邮件失败");
				}
			},
			error:function(result){
				ENABLE_OPACCEPT = true;
				alert("发送邮件异常");
			}
		});
    });

  //取消op受理
    $("#cancelSendBtn").click(function(){
    	ENABLE_OPACCEPT = true;
    });

    //取消行程确认单按钮
    $("#cancelHeaderBtn").click(function(){
      	if(ENABLE_OPACCEPT){
      		ENABLE_OPACCEPT = false;
      		var orderId = $('#orderId').val();
      		window.location.href = basepath + "/order/freetravel/detail/" + orderId;
      		ENABLE_OPACCEPT = true;
      	}
    });

});
