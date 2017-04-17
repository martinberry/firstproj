var memberIdReg = /^\d{0,8}$/;
var memberIdErrorHint = "会员ID应为8位数字";
var productTitleMaxLength = 30;
var productTitleErrorHint = "产品标题限输入30个以内字符";

$(function(){
	submitFunc();

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

	//评论用户ID校验
	$("#memberId").blur(function(){
		var mid = trim(this);
		if( !isMemberIdValid(mid) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(memberIdErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});

	$("#searchBtn").click(function(){
		$("input[name='pageNo']").val(1);
		submitFunc();
	});

});

function submitFunc(){
	var searchEntity = $("form").serializeObject();
	searchEntity.status = $("#status").find(".active").find("a").attr("name");

	if( isSearchParamsValid(searchEntity) ){
		$.ajax({
			type : "POST",
			url : basepath + "/comment/search",
			data : JSON.stringify(searchEntity),
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
			}
		});
	}

}

function isProductTitleValid(productTitle){
	if( productTitle.length > productTitleMaxLength )
		return false;
	else
		return true;
}

function isMemberIdValid(mid){
	if( mid != "" && !memberIdReg.test(mid) )
		return false;
	else
		return true;
}

function isSearchParamsValid(searchParams){
	if( isProductTitleValid(searchParams.productTitle) && isMemberIdValid(searchParams.mid) )
		return true;
	else
		return false;
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