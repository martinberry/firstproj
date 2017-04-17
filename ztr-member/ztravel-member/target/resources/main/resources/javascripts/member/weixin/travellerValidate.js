//var nameReg = /^([a-zA-Z\u4E00-\u9FA5]{1,20})$/;
var nameErrorHint = "填写不正确";
var enNameReg = /^([a-zA-Z]{1,30})$/;
var enNameErrorHint = "请输入拼音或英文名";
var mobileReg = /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
var mobileErrorHint = "请输入正确的手机号";
var emailReg = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
var emailErrorHint = "请输入正确的邮箱";
var idCardReg = /(^\d{15}$)|(^\d{17}(\d|X|x)$)/;
var idCardErrorHint = "请输入正确的身份证号";
var passportReg=/^[A-Za-z0-9]{1,20}$/;
var passportErrorHint = "请输入正确的护照号";
var gangaoPassReg=/^[a-zA-Z][0-9]{8}$/;
var gangaoPassErrorHint = "请输入正确的港澳通行证号";
var cnNameReg = /^[\u4E00-\u9FA5]{1,15}$/;

$(function(){
	//中文名
	$("#firstName").blur(function(){
		var firstName = trim(this);
		if(cnNameReg.test(firstName)){
			var firstNamePinyin = Pinyin.GetQP(firstName).toUpperCase();
			if(firstNamePinyin.length>30){
				$("#errHint").html("姓："+nameErrorHint);
				$("#alertErrHint-dialog").popup("open");
			}else{
				$("#firstNameEn").val(firstNamePinyin);   //英文名输入框自动填拼音
			}
		}else if(enNameReg.test(firstName)){
			$("#firstNameEn").val(firstName);
		}else{
			$("#errHint").html("姓："+nameErrorHint);
			$("#alertErrHint-dialog").popup("open");
		}
	});
	$("#lastName").blur(function(){
		var lastName = trim(this);
		if(cnNameReg.test(lastName)){
			var lastNamePinyin =Pinyin.GetQP(lastName).toUpperCase();
			if(lastNamePinyin.length>30){
				$("#errHint").html("名："+nameErrorHint);
				$("#alertErrHint-dialog").popup("open");
			}else{
				$("#lastNameEn").val(lastNamePinyin);  //英文名输入框自动填拼音
			}
		}else  if(enNameReg.test(lastName)){
			$("#lastNameEn").val(lastName);
		}else{
			$("#errHint").html("名："+nameErrorHint);
			$("#alertErrHint-dialog").popup("open");
		}
	});

	//拼音/英文名
	$("#firstNameEn").blur(function(){
		var firstNameEn = trim(this);
		if( !isEnNameValid(firstNameEn) ){
			$("#errHint").html("拼音/英文名："+nameErrorHint);
			$("#alertErrHint-dialog").popup();
			$("#alertErrHint-dialog").popup("open");
		}
	});
	$("#lastNameEn").blur(function(){
		var lastNameEn = trim(this);
		if( !isEnNameValid(lastNameEn) ){
			$("#errHint").html("拼音/英文名："+nameErrorHint);
			$("#alertErrHint-dialog").popup();
			$("#alertErrHint-dialog").popup("open");
		}
	});

	//邮箱
	$("#email").blur(function(){
		var email = trim(this);
		if( !isEmailValid(email) ){
			$("#errHint").html(emailErrorHint);
			$("#alertErrHint-dialog").popup();
			$("#alertErrHint-dialog").popup("open");
		}
	});

	//手机号
	$("#mobile").blur(function(){
		var mobile = trim(this);
		if( !isMobileValid(mobile) ){
			$("#errHint").html(mobileErrorHint);
			$("#alertErrHint-dialog").popup();
			$("#alertErrHint-dialog").popup("open");
		}
	});

	//证件号
	$("#credentialList").delegate("input[name='credentialNumber']", "blur", function(){
		var credNo = trim(this);
		var credType = $(this).parents(".revise-contain01").find("input[name='credentialType']").val();
//		var validateDate = $(this).parents(".revise-contain01").find().

		switch(credType){
		case "身份证":
			if( !isIDCardNoValid(credNo) ){
				$("#errHint").html(idCardErrorHint);
				$("#alertErrHint-dialog").popup();
				$("#alertErrHint-dialog").popup("open");
			}
			break;
		case "护照":
			if( !isPassportNoValid(credNo) ){
				$("#errHint").html(passportErrorHint);
				$("#alertErrHint-dialog").popup();
				$("#alertErrHint-dialog").popup("open");
			}
			break;
		case "港澳通行证":
			if( !isGangaoPassNoValid(credNo) ){
				$("#errHint").html(gangaoPassErrorHint);
				$("#alertErrHint-dialog").popup();
				$("#alertErrHint-dialog").popup("open");
			}
			break;
		}
		var deadLineDate = $(this).parents(".revise-contain01").find("input[name='deadLineDate']").val();
		if(new Date(deadLineDate) < new Date()){
			$("#errHint").html("有效期应大于当前日期");
			$("#alertErrHint-dialog").popup();
			$("#alertErrHint-dialog").popup("open");
		}
	});

	$("body").delegate("#detailAddress","blur",function(){
		validateDetailAddress();
	})

});

function isTravellerInfoParamValid(param){

	if( !isNameValid(param.firstNameCn)){
		$("#errHint").html("姓："+nameErrorHint);
		$("#alertErrHint-dialog").popup();
		$("#alertErrHint-dialog").popup("open");
		return false;
	}
	if(!isNameValid(param.lastNameCn)){
		$("#errHint").html("名："+nameErrorHint);
		$("#alertErrHint-dialog").popup();
		$("#alertErrHint-dialog").popup("open");
		return false;
	}
	if(!isEnNameValid(param.firstEnName) || !isEnNameValid(param.lastEnName)){
		$("#errHint").html("拼音/英文名："+nameErrorHint);
		$("#alertErrHint-dialog").popup();
		$("#alertErrHint-dialog").popup("open");
		return false;
	}

	if( !isMobileValid(param.phoneNum) ){
		$("#errHint").html(mobileErrorHint);
		$("#alertErrHint-dialog").popup();
		$("#alertErrHint-dialog").popup("open");
		return false;
	}
	if( !isEmailValid(param.email) ){
		$("#errHint").html(emailErrorHint);
		$("#alertErrHint-dialog").popup();
		$("#alertErrHint-dialog").popup("open");
		return false;
	}

	var birthDate = $("#birthdate").html();
	if(new Date(birthDate)> new Date()){
		$("#errHint").html("出生日期应小于当前日期");
		$("#alertErrHint-dialog").popup();
		$("#alertErrHint-dialog").popup("open");
		return false;
	}

	var deadLineDateResult = true;
	$("span[name='deadLineDate']").each(function(){
		var deadLineDate = $(this).html();
		if(deadLineDate != null && "" !=deadLineDate && new Date(deadLineDate) <  new Date()){
			$("#errHint").html("有效期应大于当前日期");
			$("#alertErrHint-dialog").popup();
			$("#alertErrHint-dialog").popup("open");
			deadLineDateResult =  false;
		}
	})
	if(!deadLineDateResult){
		return false;
	}

	if( isNameValid(param.firstNameCn) && isNameValid(param.lastNameCn)
		  && isEnNameValid(param.firstEnName) && isEnNameValid(param.lastEnName)
		  && isEmailValid(param.email) && isMobileValid(param.phoneNum) && validateDetailAddress()&& validateCredential(param.credentials) ){
		return true;
	}else{
		return false;
	}
}

function isNameValid(name){
	var result = false;
	if(cnNameReg.test(name)){
		var namePinyin = Pinyin.GetQP(name).toUpperCase();
		if(namePinyin.length<=30){
			result = true;
		}else{
			result = false;
		}
	}else if(enNameReg.test(name)){
		result = true;
	}else{
		result = false;
	}
	return result;
}

function isEnNameValid(enName){
	if( !enNameReg.test(enName) )
		return false;
	else
		return true;
}

function isEmailValid(email){
	if( email != "" && !emailReg.test(email) )
		return false;
	else
		return true;
}

function isMobileValid(mobile){
	if( mobile != "" && !mobileReg.test(mobile) )
		return false;
	else
		return true;
}

function isIDCardNoValid(idCardNo){
	var result =true;
	if( !idCardReg.test(idCardNo) ){
		result = false;
	}
	return result;
}

function isPassportNoValid(passportNo){
	if( !passportReg.test(passportNo) )
		return false;
	else
		return true;
}

function isGangaoPassNoValid(gangaoPassNo){
	var result =true;
	if( !gangaoPassReg.test(gangaoPassNo) ){
		result=  false;
	}
	return result;
}

function trim(selector){
	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
}


function validateDetailAddress(){
	var result = true;
	var detailAddress = $("#detailAddress").val();
	if(null != detailAddress && ""!=detailAddress){
		result = isDetailAddress(detailAddress);
	}
	return result;
}

function isDetailAddress(detailAddress){
	var streetReg = /^([(-|\_|\.)|a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|;|：|:|；|“|”|’]){1,50}$/;
	if(streetReg.test(detailAddress)){
		return true;
	}else{
		$("#errHint").html("通讯地址格式错误!");
		$("#alertErrHint-dialog").popup();
		$("#alertErrHint-dialog").popup("open");
		return false;
	}
}

function validateCredential(credentials){
	var result =true;
	 for(var i=0;i<credentials.length;i++){
		 	var credNo = credentials[i].number;
		 	if(credentials[i].type == 'IDCARD'){
		 		if( !isIDCardNoValid(credNo) ){
					$("#errHint").html(idCardErrorHint);
					$("#alertErrHint-dialog").popup();
					$("#alertErrHint-dialog").popup("open");
					result = false;
				}
		 	}

		 	if(credentials[i].type == 'PASSPORT'){
		 		if( !isPassportNoValid(credNo) ){
		 			$("#errHint").html(passportErrorHint);
		 			$("#alertErrHint-dialog").popup();
					$("#alertErrHint-dialog").popup("open");
					result = false;
				}
		 	}

		 	if(credentials[i].type == 'GANGAOPASS'){
		 		if( !isGangaoPassNoValid(credNo) ){
					$("#errHint").html(gangaoPassErrorHint);
					$("#alertErrHint-dialog").popup();
					$("#alertErrHint-dialog").popup("open");
					result = false;
				}
		 	}

     }
		return result;
}