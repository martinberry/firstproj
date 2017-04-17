/**
 *
 */

$(function(){

	$("#travelerNameCn").bind("change", function(){
        $("#error_name").hide();
    });

	$("#travelerNameEn").bind("change", function(){
        $("#error_nameEn").hide();
    });

	$("#phoneNum").bind("change", function(){
        $("#error_phone").hide();
    });

	$('.lightBlueBtn').click(function(){
		var data = {};
		data.travelerNameCn = $('#travelerNameCn').val().replace(/\s+/g, "");
		data.travelerNameEn=$('#travelerNameEn').val();
		data.phoneNum = $('#phoneNum').val();
		data.memberId = $('#memberId').val();

		if(data.travelerNameCn != '' && !testNameCn(data.travelerNameCn)){
			$("#verify_name").html("姓名格式输入错误!");
			$("#error_name").show();
			$('#travelerNameCn').focus();
			return;
		}
		if(data.travelerNameEn != '' && !testNameEn(data.travelerNameEn)){
			$("#verify_nameEn").html("拼音名格式输入错误!");
			$("#error_nameEn").show();
			$('#travelerNameEn').focus();
			return;
		}
		if(data.phoneNum != '' && !testPhone(data.phoneNum)){
			$("#verify_phone").html("手机号格式输入错误!");
			$("#error_phone").show();
			$('#phoneNum').focus();
			return ;
		  }
			$.ajax({
				type: "POST",
				url: basepath + '/member/opera/traveller/search',
				data: JSON.stringify(data),
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				success: function (json) {
					$("#traveller_list tbody").html(json);
	    			}
		});
    });

});

function getDetail(section){
	var travelerId = $(section).parent().children('input').val();
	$.ajax({
		type: "POST",
		url: basepath + '/member/opera/traveller/detail',
		data: {"travelerId":travelerId},
		dataType : "html",
		success: function (json) {
			$("#traveller").html(json);
			$(".titleContent a").attr("href",$("#traveller_list").attr("href"));
		}
	});
}


//校验中文名
function testNameCn(travelerName){
	var result =true;
	travelerName=travelerName.replace(/\s+/g, "");
	if(null!=travelerName && ""!=travelerName){
		var r=/^[\u4e00-\u9fa5|a-zA-Z|·|.|/]{1,20}$/;
		if(!r.test(travelerName)){
			result=false;
		}else{
			result=true;
		}
	}
	return result;

}

//校验英文名
function testNameEn(travelerNameEn){
	var result=true;
	travelerNameEn=travelerNameEn.replace(/\s+/g, "");
	if(null!=travelerNameEn && ""!=travelerNameEn){
		var r=/^[a-zA-Z|·|.]{1,40}$/;
		if(!r.test(travelerNameEn)){
			result= false;
		}
	}
	return result;
}

function testPhone(phoneNum){
	var r=/^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
	if(r.test(phoneNum)){
		return true;
	}else{
		return false;
	}
}


