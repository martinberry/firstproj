var realNameReg1 = /^[a-z\.A-Z]{1,40}$/;   //真实姓名英文40个字母
var realNameReg2 = /^[\u4E00-\u9FA5]{1,10}$/;   //真实姓名中文10个汉字
var mobileReg = /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
var memIdReg = /^\d{8}$/;
var nickNameReg = /^([0-9a-zA-Z\*\u4E00-\u9FA5]+)$/;
var emailReg = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
var purAmountReg = /^\d+\.{0,1}\d{0,2}$/;

$(function(){
	submitFunc();
	//搜索框 输入框格式校验
	//真实姓名格式校验
	$("#realNameInput").focusout(function(){
		var realName = trim(this);
		if( (realName != "") && !realName.match(realNameReg1) && !realName.match(realNameReg2) ){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	//手机号格式校验
	$("#mobileInput").focusout(function(){
		var mobile = trim(this);
		if( (mobile != "") && (!mobile.match(mobileReg)) ){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	//会员ID格式校验
	$("#memberIdInput").focusout(function(){
		var memId = trim(this);
		if( (memId != "") && (!memId.match(memIdReg)) ){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	//昵称格式校验
	$("#nickNameInput").focusout(function(){
		var nickName = trim(this);
		if( (nickName != "") && ( !nickName.match(nickNameReg) || nickName.length > 20 ) ){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	//邮箱格式校验
	$("#emailInput").focusout(function(){
		var email = trim(this);
		if( (email != "") && (!email.match(emailReg)) ){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	//消费金额格式校验
	$(".purchaseAmount").focusout(function(){
		var purAmountFrom = trim("#purAmountFrom");
		var purAmountTo = trim("#purAmountTo");
		if( ((purAmountFrom != "") && (!purAmountFrom.match(purAmountReg))) || ((purAmountTo != "") && (!purAmountTo.match(purAmountReg)))
				|| parseFloat(purAmountFrom) > parseFloat(purAmountTo) ){
			$(this).siblings(".verifyStyle").show();
		}else{
			$(this).siblings(".verifyStyle").hide();
		}
	});

	$("#searchBtn").click(function(){
		$("input[name='pageNo']").val(1);
		submitFunc();
	});

	//动态绑定事件
	$("#province").on("click",".active",function() {
		var regionNo = $("#province .active").attr("name");
		$.ajax({
		    type: "POST",
		    url: "../memberMaintain/loadCity",
		    data: regionNo,
		    headers : {
			    'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    success: function(cityList){
		    	$("#city").html(cityList);
		    }
	    });
	});
    // 下拉列表(事件需要动态绑定)
    $("#city").on("click","li a",function() {
        var thisHtml = $(this).html();
        var $thisParents = $(this).parents(".dropdown-menu li");
        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
        $thisParents.addClass("active");
        $thisParents.siblings().removeClass("active");
    });

    $("#searchField").on("click", ".checkbox", function(){
    	$(this).toggleClass("active");
        var $that = $(this);
        if ($(".commonTab tbody .checkbox").not(".active").length > 0) {
            $that.parents("table").find(".allCheckbox").removeClass("active");
        } else {
            $that.parents("table").find(".allCheckbox").addClass("active");
        }
    });

    $(".ac-hangUp").click(function(){
    	var checkboxList = $(".commonTab tbody").find(".checkbox.active");
    	if( checkboxList.length == 0 ){
    		$("#noSelect").modal();
    	}else{
    		$("#ac-hangUp").modal();
    	}
    });

    $(".ac-unlock").click(function(){
    	var checkboxList = $(".commonTab tbody").find(".checkbox.active");
    	if( checkboxList.length == 0 ){
    		$("#noSelect").modal();
    	}else{
    		$("#ac-unlock").modal();
    	}
    });

    $("#suspendBatch").click(function(){
    	var checkboxList = $(".commonTab tbody").find(".checkbox.active");
    	var idList = "";
    	checkboxList.each(function(){
    		idList += $(this).attr("name") + " ";
    	});
		$.ajax({
			type: "POST",
		    url: "../memberMaintain/suspend/lock",
		    data: idList,
		    headers : {
		    	'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    dataType : "json",
		    success: function(resp){
		    	if( resp.res_code == "SO_MEMB_1001" ){
		    		submitFunc();
		    	}else if( resp.res_code == "FO_MEMB_1001" ){
		    		$("#suspendError").find(".popupContainer-fonts").text(resp.res_msg);
		    		$("#suspendError").modal();
		    	}
		    }
	    });
    });

    $("#unlockBatch").click(function(){
    	var checkboxList = $(".commonTab tbody").find(".checkbox.active");
    	var idList = "";
    	checkboxList.each(function(){
    		idList += $(this).attr("name") + " ";
    	});
    	$.ajax({
			type: "POST",
		    url: "../memberMaintain/suspend/unlock",
		    data: idList,
		    headers : {
		    	'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    dataType : "json",
		    success: function(resp){
		    	if( resp.res_code == "SO_MEMB_1001" ){
		    		submitFunc();
		    	}else if( resp.res_code == "FO_MEMB_1001" ){
		    		$("#suspendError").find(".popupContainer-fonts").text(resp.res_msg);
		    		$("#suspendError").modal();
		    	}
		    }
	    });
    });

    $("#searchField").on("click", "#suspendOne", function(){
    	var id = $(this).parent().siblings().first().children(".checkbox").attr("name");
    	$.ajax({
			type: "POST",
		    url: "../memberMaintain/suspend/lock",
		    data: id,
		    headers : {
		    	'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    dataType : "json",
		    success: function(resp){
		    	if( resp.res_code == "SO_MEMB_1001" ){
		    		submitFunc();
		    	}else if( resp.res_code == "FO_MEMB_1001" ){
		    		$("#suspendError").find(".popupContainer-fonts").text(resp.res_msg);
		    		$("#suspendError").modal();
		    	}
		    }
	    });
    });

    $("#searchField").on("click", "#unlockOne", function(){
    	var id = $(this).parent().siblings().first().children(".checkbox").attr("name");
    	$.ajax({
			type: "POST",
		    url: "../memberMaintain/suspend/unlock",
		    data: id,
		    headers : {
		    	'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    dataType : "json",
		    success: function(resp){
		    	if( resp.res_code == "SO_MEMB_1001" ){
		    		submitFunc();
		    	}else if( resp.res_code == "FO_MEMB_1001" ){
		    		$("#suspendError").find(".popupContainer-fonts").text(resp.res_msg);
		    		$("#suspendError").modal();
		    	}
		    }
	    });
    });

});

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

//提交查询请求
function submitFunc() {
	var criteria = $("form").serializeObject();
	criteria.status = $("#status .active").attr("id");
	if( $("#province .active").text() == "全部" ){
		criteria.province = "";
	}else{
		criteria.province = $("#province .active").text();
	}
	if( $("#city .active").text() == "全部" ){
		criteria.city = "";
	}else{
		criteria.city = $("#city .active").text();
	}
	criteria.latestLoginPeriod = $("#latestLoginPeriod .active").attr("name");

	if( isRealNameValid(criteria.realName) && isNickNameValid(criteria.nickName)
			&& isMobileValid(criteria.mobile) && isMemberIdValid(criteria.memberId)
			&& isEmailValid(criteria.email) && isPurAmountValid(criteria.amountFrom)
			&& isPurAmountValid(criteria.amountTo) ){
		$.ajax({
			type : "POST",
			url : "../memberMaintain/searchMember",
			data : JSON.stringify(criteria),
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

function isRealNameValid(realName){
	if( (realName != "") && !realName.match(realNameReg1) && !realName.match(realNameReg2) )
		return false;
	else
		return true;
}

function isNickNameValid(nickName){
	if( (nickName != "") && ( !nickName.match(nickNameReg) || nickName.length > 20 ) )
		return false;
	else
		return true;
}

function isMobileValid(mobile){
	if( (mobile != "") && (!mobile.match(mobileReg)) )
		return false;
	else
		return true;
}

function isMemberIdValid(memberId){
	if( (memberId != "") && (!memberId.match(memIdReg)) )
		return false;
	else
		return true;
}

function isEmailValid(email){
	if( (email != "") && (!email.match(emailReg)) )
		return false;
	else
		return true;
}

function isPurAmountValid(purAmount){
	if( (purAmount != "") && (!purAmount.match(purAmountReg)) )
		return false;
	else
		return true;
}

//function isRegisterDateValid(from, to){
//	if( (from != "") && (to != "") ){
//		var fromDate = Date.parse(from);
//		var toDate = Date.parse(to);
//		if( fromDate > toDate )
//			return false;
//		else
//			return true;
//	}else{
//		return true;
//	}
//}