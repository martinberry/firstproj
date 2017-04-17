var orderNoReg = /^\d{0,12}$/;
var orderNoErrorHint = "订单号应为12位数字";
var productIdReg = /^[a-zA-Z\d]{0,6}$/;
var productIdErrorHint = "产品ID应为Z+5位数字";
var productTitleReg = /^\S{1,30}$/;
var productTitleErrorHint = "产品标题请输入30个以内字符";
var orderPriceReg = /^\d{1,7}\.\d{2}$/;
var integerOrderPriceReg = /^\d{1,7}$/;
var incompleteOrderPriceReg = /^\d{1,7}\.\d$/;
var priceFormatErrorHint = "订单金额格式错误";
var priceLowerUpperErrorHint = "后框金额应大于等于前框金额";
var memberIdReg = /^\d{0,8}$/;
var memberIdErrorHint = "会员ID应为8位数字";

$(function(){
	submitFunc();
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

	//产品ID校验
	$("#productId").blur(function(){
		var productId = trim(this);
		if( !isProductIdValid(productId) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(productIdErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});

	//产品标题校验
	$("#productTitle").blur(function(){
		var productTitle = trim(this);
		if( !isProductTitleValid(productTitle) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(productTitleErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});

	//建单会员ID校验
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
		orderPrice = orderPriceAutoComplete(orderPrice);
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
		$("input[name='pageNo']").val(1);
		submitFunc();
	});

});

function submitFunc(){
	var criteria = $("form").serializeObject();
	criteria.status = $("#status").find(".active").find("a").attr("name");
	criteria.source = $("#source").find(".active").find("a").attr("name");

	if( isSearchParamsValid(criteria) ){
		$.ajax({
			type : "POST",
			url : basepath + "/order/freetravel/search",
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
		});
	}

}

function isOrderNoValid(orderNo){
	if( orderNo != "" && !orderNoReg.test(orderNo) )
		return false;
	else
		return true;
}

function isProductIdValid(productId){
	if( productId != "" && !productIdReg.test(productId) )
		return false;
	else
		return true;
}

function isProductTitleValid(productTitle){
	if( productTitle != "" && !productTitleReg.test(productTitle) )
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
	if( isOrderNoValid(searchParams.orderNo) && isProductIdValid(searchParams.productId) && isProductTitleValid(searchParams.productTitle)
		 && isOrderPriceValid(searchParams.orderPriceLowerBound) && isOrderPriceValid(searchParams.orderPriceUpperBound))
		return true;
	else
		return false;
}

function orderPriceAutoComplete(orderPrice){
	if( integerOrderPriceReg.test(orderPrice) ){
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
