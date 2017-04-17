var orderNoReg =    /^[0-9a-zA-Z]{0,18}$/;
var orderNoErrorHint = "兑换单ID应为0-18位数字或字母";

var memberIdReg =/^[0-9a-zA-Z]{0,8}$/;
var memberIdErrorHint = "会员ID应为0-8位数字或字母";

var orderPriceReg = /^\d{0,7}\.\d{2}$/;//.xx
var integerOrderPriceReg = /^\d{0,7}$/;//1-7位数
var incompleteOrderPriceReg = /^\d{0,7}\.\d$/;//.d
var priceFormatErrorHint = "订单金额格式错误";
var priceLowerUpperErrorHint = "后框金额应大于等于前框金额";

//
$(function(){
	submitFunc();
	confrimToConvert();
	//订单号校验
	$("#orderNo").blur(function(){
		var orderNo = trim(this);
		if( !isOrderNoValid(orderNo) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(orderNoErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});
	//会员ID校验
	$("#memberId").blur(function(){
		var memberId = trim(this);
		if( !isMemberIdValid(memberId) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(memberIdErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});

	//订单金额校验
	$(".orderPrice").blur(function(){
		var orderPrice = trim(this);
		var orderPrice = orderPriceAutoComplete(orderPrice);
		$(this).val(orderPrice);
		if( !isOrderPriceValid(orderPrice) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(priceFormatErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
		var orderPriceLowerBound = $("#orderPriceLowerBound").val();
		var orderPriceUpperBound = $("#orderPriceUpperBound").val();
		if( orderPriceLowerBound != "" && isOrderPriceValid(orderPriceLowerBound) && orderPriceUpperBound != "" && isOrderPriceValid(orderPriceUpperBound) ){
			if( parseFloat(orderPriceLowerBound) > parseFloat(orderPriceUpperBound) ){
				$(this).siblings(".verifyStyle").find(".verifyFonts").text(priceLowerUpperErrorHint);
				showErrorHint(this);
			}else{
				hideErrorHint(this);
			}
		}
	});

	$("#searchBtn").click(function(){
		submitFunc();
	});



});

////////////////////////
function submitFunc(){
	var criteria = $("form").serializeObject();
	criteria.dhStatus = $("div.dropdown").find("li.active").attr("data-val");
	if( isSearchParamsValid(criteria) ){
		$.ajax({
			type : "POST",
			url : basepath + "/order/convert/search",
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
				initConfirmBtn();
				initLookBtn();
			},
		});
	}

}

function isOrderNoValid(orderNo){
	if( orderNo != "" && !orderNoReg.test(orderNo) )
		return false;
	else
		return true;
}


function isOrderPriceValid(orderPrice){
	if( orderPrice != "" && !orderPriceReg.test(orderPrice) )
		return false;
	else
		return true;
}


function isMemberIdValid(memberId){
	if( memberId != "" && !memberIdReg.test(memberId) )
		return false;
	else
		return true;
}

function isSearchParamsValid(searchParams){
	if( isOrderNoValid(searchParams.dhId) &&  isOrderPriceValid(searchParams.dhPriceLowerBound)
			&& isOrderPriceValid(searchParams.dhPriceUpperBound)
		 && isMemberIdValid(searchParams.memberId) )
		return true;
	else
		return false;
}

function orderPriceAutoComplete(orderPrice){
	if(orderPrice==""){
	}
	else if( integerOrderPriceReg.test(orderPrice) ){
		orderPrice += ".00";
	}else if( incompleteOrderPriceReg.test(orderPrice) ){
		orderPrice += "0";
	}
	return orderPrice;
}

function showErrorHint(inputer){
	$(inputer).addClass("verifyBox");
	$(inputer).siblings(".verifyStyle").show();
}

function hideErrorHint(inputer){
	$(inputer).removeClass("verifyBox");
	$(inputer).siblings(".verifyStyle").hide();
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

function trim(selector){
	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
}

function initConfirmBtn(){
	//点击确认兑换改变兑换状态
	$("a.confirmConvert").click(function(){
		confirmdh($(this));
		});
}

function initLookBtn(){
	//点击跳出查看模态框
	$("a.look").click(function(){
		var dhId=$(this).attr("data-id");
		$.ajax({
			type : "POST",
			url : basepath+"/order/convert/detail/"+dhId,
			data : {"dhId":dhId},
			dataType : "json",
			success : function(data) {
				if(data.res_code == 'SO_DHVIEW_1001'){
					$("#haveconverted").find("table td.confirmDate").html("");
					$("#haveconverted").find("h4.modal-title div").html(data.res_msg.dhId);
					$("#haveconverted").find("table td.nickName").html(data.res_msg.membername);
					$("#haveconverted").find("table td.mobile").html(data.res_msg.dhphonenumber);
					 var dhmoney=data.res_msg.dhmoney/100;
					$("#haveconverted").find("table td.orangefa7f1f").html(dhmoney);
					$("#haveconverted").find("table td.content").html(data.res_msg.contentDesc);
					$("#haveconverted").find("table td.pleDate").html(data.res_msg.pledhtime);
					$("#haveconverted").find("table td.confirmDate").html(data.res_msg.confdhtime);
					$("#haveconverted").modal("show");
					$("#confirmConvert").attr("data-id",data.res_msg.dhId);
				}else if(data.res_code == 'FO_MEMB_1003'){

				}
		}
		});
});
}

function confrimToConvert(){
	$("#confirmConvert").click(function(){
		var dhId=$(this).attr("data-id");
		$.ajax({
			type : "POST",
			url : basepath+"/order/convert/changestatus/"+dhId,
			data : {"dhId":dhId},
			//dataType : "html",
			success : function(data) {
				if(data.res_code=='SO_MEMBDH_1003'){
					$("#converting").modal("hide");
					$("div."+dhId+"").html("已兑换");
					$("td."+dhId+"_operator").html(data.res_msg);
					$("a."+dhId+"_confirm").attr("style","display:none;");
					$("a."+dhId+"_view").attr("style","display;");
					}
			}
		});
	});
}

//点击确认兑换跳出确认兑换模态框
function confirmdh(obj){
	var dhId=$(obj).attr("data-id");            //qqq
	$.ajax({
		type : "POST",
		url : basepath+"/order/convert/detail/"+dhId,
		data : {"dhId":dhId},
		dataType : "json",
		success : function(data) {
			if(data.res_code='SO_MEMBDH_1001') {
				$("#converting").find("table td.confirmDate").html("");
				$("#converting").find("h4.modal-title div").html(data.res_msg.dhId);
				$("#converting").find("table td.nickName").html(data.res_msg.membername);
				$("#converting").find("table td.mobile").html(data.res_msg.dhphonenumber);
				 var dhmoney=data.res_msg.dhmoney/100;
				$("#converting").find("table td.orangefa7f1f").html(dhmoney);
				$("#converting").find("table td.content").html(data.res_msg.contentDesc);
				$("#converting").find("table td.pleDate").html(data.res_msg.pledhtime);
				$("#converting").find("table td.confirmDate").html(data.res_msg.confdhtime);
				$("#confirmConvert").attr("data-id",data.res_msg.dhId);
				$("#converting").modal("show");
			}
		}
	});
}

function initModal(){

}
