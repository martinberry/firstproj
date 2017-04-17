var disappear = '' ;
var nickname_hint_Illegal = '您的昵称格式不对哦' ;
var email_hint_Illegal = '邮箱格式有误' ;
var email_hint_isalreadyexists = '邮箱已经被注册' ;
var mobile_hint_formaterror = '您输入的手机号有误' ;

var save_lock = false ;
var skip_lock = false ;

$(function(){

    var uName = $("#uName").val();
    var uEmail = $("#uEmail").val();
    var uPhone = $("#uPhone").val();

    if(uName != ""){
        $("#uName").next().css("display", "inline-block");
        $("#uName").next().click(function(){
            iconClick("#uName");
        });
    }
    $("#uName").focus(function(){
        var txt = $(this).val();
        if(txt == ""){
            $(this).next().css("display", "none");
        }else{
            $(this).next().css("display", "inline-block");
            $("#uName").next().click(function(){
                iconClick("#uName");
            });
        }
    });

    //昵称
	$("#uName").blur(function() {
		if(!checkNickName(trim("#uName"))){
			$("#errHintImprove").html(nickname_hint_Illegal);
			$("#alert-dialog-improve").popup();
			$("#alert-dialog-improve").popup("open");
		}
	});

	//email校验
	$("#uEmail").blur(function() {
		if(trim("#uEmail") != ''){
			if(!checkEmail(trim("#uEmail"))){
				$("#errHintImprove").html(email_hint_Illegal);
				$("#alert-dialog-improve").popup();
				$("#alert-dialog-improve").popup("open");
			}else{
				isEmailAlreadyExists(trim("#uEmail")) ;
			}
		}
	});

	skip();

	jumpToSharePlan();
});

function checkNickName(nickName){
	var reg = /^([0-9a-zA-Z\*\u4E00-\u9FA5]{1,11})$/;
	return nickName.match(reg) != null;
}

function checkEmail(email){
	var reg = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
	return email.match(reg) != null;
}

function isEmailAlreadyExists(email){
	$.ajax({
	    type: 'POST',
	    url: wxServer + '/rl/isEmailAlreadyExists' ,
	    data: "email=" + email ,
	    dataType: 'json' ,
	    success: function(data){
	    	if(data.res_code == 'EF_MEMB_1020'){
	    		$("#errHintImprove").html(email_hint_isalreadyexists);
				$("#alert-dialog-improve").popup();
				$("#alert-dialog-improve").popup("open");
	    	}else if(data.res_code == 'EF_MEMB_1023'){

	    	}else if(data.res_code == 'SF_MEMB_1004'){
	    	}
	    }
	});
}

function iconClick(para){
    $(para).val("");
    $(para).next().css("display", "none");
}

function save(){
	var uName = trim("#uName");
    var uEmail = trim("#uEmail");
    var uPhone = trim("#uPhone");

    if(!checkNickName(trim("#uName"))){
		$("#errHintImprove").html(nickname_hint_Illegal);
		$("#alert-dialog-improve").popup();
		$("#alert-dialog-improve").popup("open");
		return ;
	}

    if(!checkEmail(uEmail) && uEmail != ''){
		$("#errHintImprove").html(email_hint_Illegal);
		$("#alert-dialog-improve").popup();
		$("#alert-dialog-improve").popup("open");
		return ;
	}

	if(!isMobile.test(uPhone) && uPhone != ''){
		$("#errHintImprove").html(mobile_hint_formaterror);
		$("#alert-dialog-improve").popup();
		$("#alert-dialog-improve").popup("open");
		return ;
	}

	if(!save_lock){
		save_lock = true ;
		$.ajax({
		    type: 'POST',
		    url: wxServer + '/rl/saveImproveData' ,
		    data: "nickName=" + uName + "&email=" + uEmail + "&recommender=" + uPhone ,
		    dataType: 'json' ,
		    success: function(data){
		    	save_lock = false ;
		    	if(data.res_code == 'EF_MEMB_1020'){
		    		$("#errHintImprove").html(email_hint_isalreadyexists);
		    		$("#alert-dialog-improve").popup();
		    		$("#alert-dialog-improve").popup("open");
		    	}else if(data.res_code == 'EF_MEMB_1018'){
		    		$("#errHintImprove").html(email_hint_Illegal);
		    		$("#alert-dialog-improve").popup();
		    		$("#alert-dialog-improve").popup("open");
		    	}else if(data.res_code == 'EF_MEMB_1019'){
		    		$("#errHintImprove").html(nickname_hint_Illegal);
		    		$("#alert-dialog-improve").popup();
		    		$("#alert-dialog-improve").popup("open");
		    	}else if(data.res_code == 'EF_MEMB_1017'){
		    		$("#errHintImprove").html(mobile_hint_formaterror);
		    		$("#alert-dialog-improve").popup();
		    		$("#alert-dialog-improve").popup("open");
		    	}else if(data.res_code == 'EF_MEMB_1040'){
		    		$("#errHintImprove").html("不能推荐本人");
		    		$("#alert-dialog-improve").popup();
		    		$("#alert-dialog-improve").popup("open");
		    	}else if(data.res_code == 'EF_MEMB_1036'){
		    		$("#errHintImprove").html("推荐人手机号码未注册");
		    		$("#alert-dialog-improve").popup();
		    		$("#alert-dialog-improve").popup("open");
		    	}else if(data.res_code == 'TAKE_ME_BACK'){
		    		window.location.href= wxServer+"/weixin/product/list";
		    	}
		    },
		    error: function(data){
		    	save_lock = false ;
		    }
		});
	}

}

function skip(){
	$("#skip").click(function(){
		window.location.href= wxServer+"/weixin/product/list";
	})
}

function jumpToSharePlan(){
	$("#jumpToSharePlan").click(function(){
		window.location.href=wxServer+"/rl/jumpToSharePlan";
	})
}

