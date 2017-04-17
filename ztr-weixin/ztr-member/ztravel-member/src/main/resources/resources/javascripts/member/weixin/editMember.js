var nickNameReg = /^([0-9a-zA-Z\u4E00-\u9FA5\*]{1,11})$/;
var passwordReg = /^([a-zA-Z0-9~!@#$%^&*]{8,28})$/ ;
var mobileReg = /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;

var realNameReg1 = /^([\u4E00-\u9FA5]{0,10})$/;
var realNameReg2 = /^([a-zA-Z]{0,20})$/;
var verifycode_lock = false ;
var canEditEmail = false;
var isEmail = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
$(function(){
	//昵称格式校验
	$("#nickName").blur(function(){
		var nickName = trim(this);
		if( !isNickNameValid(nickName) ){
			$("#errHintMember").html("昵称输入不正确");
			$("#alert-dialog-member").popup();
			$("#alert-dialog-member").popup("open");
		}
	});

	$("#detailAddress").blur(function(){
		validateDetailAddress();
	})

	//获取验证码
	$("#getVerificationCodeBtn").click(function(){

		var bindMobile = $("#bindMobile").val();
		if( typeof(bindMobile) != "undefined" && bindMobile != null && bindMobile != ""){
			if( !mobileReg.test(bindMobile) ){
				$("#errHintMember").html("手机号格式不正确");
				$("#alert-dialog-member").popup();
				$("#alert-dialog-member").popup("open");
				return;
			}

			if( !verifycode_lock && !$("#getVerificationCodeBtn").hasClass("retry") ){
				verifycode_lock = true;
				$("#getVerificationCodeBtn").html("<span>60</span>s后重新尝试");
				var t = timer(59) ;
				$("#getVerificationCodeBtn").addClass("retry");
				sendGetVerificationCodeRequest(bindMobile, t);  //发送获取验证码请求
			}
			return ;
		}

		var oldMobile = $("#mobile").val();
		var password = $("#password").val();
		var newMobile = $("#newMobile").val();
		if( typeof(password) == "undefined" || password == null || password == ""){
			$("#errHintMember").html("登录密码不能为空");
			$("#alert-dialog-member").popup();
			$("#alert-dialog-member").popup("open");
			return;
		}
		if( !passwordReg.test(password) ){
			$("#errHintMember").html("密码格式不正确");
			$("#alert-dialog-member").popup();
			$("#alert-dialog-member").popup("open");
			return;
		}
		if( !mobileReg.test(newMobile) ){
			$("#errHintMember").html("手机号格式不正确");
			$("#alert-dialog-member").popup();
			$("#alert-dialog-member").popup("open");
			return;
		}


		if( !verifycode_lock && !$("#getVerificationCodeBtn").hasClass("retry") ){
			verifycode_lock = true;
			//验证输入的登录密码是否正确
			$.ajax({
				type: "POST",
				url: ssoServer + "/sso/isPasswordSame",
				data: "account=" + oldMobile + "&password=" + password,
				dataType: "jsonp",
			    jsonp: "callback",
				success: function(resp){
					switch(resp.res_code){
					case "success":
						$("#getVerificationCodeBtn").html("<span>60</span>s后重新尝试");
						var t = timer(59) ;
						$("#getVerificationCodeBtn").addClass("retry");
						sendGetVerificationCodeRequest(newMobile, t);  //发送获取验证码请求
						break;
					case "error":
						$("#errHintMember").html("登录密码不正确！");
						$("#alert-dialog-member").popup();
						$("#alert-dialog-member").popup("open");
						break;
					}
				},error:function(){
					verifycode_lock = false;
				}
			}
			);
		}

	});

	//保存信息
	$("#saveBtn").click(function(){
		var param = $("#memberInfoForm").serializeObject();
		param.province = $("#province").html();
		param.city = $("#city").html();
		param.area=$("#country").html();

		if( !isMemberInfoParamValid(param) ){
			return;
		}

		var bindMobile = param.bindMobile;
		if (typeof(bindMobile) != "undefined" && bindMobile != null && bindMobile != "") {
			var password = $("#password").val();
			if( typeof(password) == "undefined" || password == null || password == ""){
				$("#errHintMember").html("登录密码不能为空");
				$("#alert-dialog-member").popup();
				$("#alert-dialog-member").popup("open");
				return;
			}
			if(!isPassword.test(password)){
				$("#errHintMember").html("登录密码不合法");
				$("#alert-dialog-member").popup();
				$("#alert-dialog-member").popup("open");
    			return;
    		}
			$.ajax({
				type: "POST",
				url: ssoServer + "/sso/wx/setPasswordToWxUser" ,
				data: "newPwd=" + password,
				dataType: "jsonp",
			    jsonp: "callback",
				success: function(resp){
					if( resp.res_code == "SW_MEMB_1018" ){   //成功

					}else if( resp.res_code == "FW_MEMB_1014" || resp.res_code == "FW_MEMB_1016" || resp.res_code == "FW_MEMB_1017" ){  //错误提示
						errorTipsWindow(resp.res_msg);
						return ;
					}else if( resp.res_code == "FW_MEMB_1015" || resp.res_code == "FW_MEMB_1019" ){   //异常
						errorTipsWindow(resp.res_msg);
						return ;
					}
				}
			});
			param.newMobile = bindMobile;
			delete param.bindMobile;
			postParams(param);
			return ;
		}

		var newEmail = param.newEmail;
		if(typeof(newEmail) != "undefined" && newEmail != null && newEmail != ""){
			if(!isEmail.test(newEmail)){
				$("#errHintMember").html("新邮箱格式不正确");
				$("#alert-dialog-member").popup();
				$("#alert-dialog-member").popup("open");
				return;
			}
			if(newEmail == param.email){
				$("#errHintMember").html("新,旧邮箱不能相同");
				$("#alert-dialog-member").popup();
				$("#alert-dialog-member").popup("open");
    			return;
    		}
			//验证输入的登录密码是否正确
			var oldEmail = $("#email").val();
			var password = $("#password").val();
			var passWordRight = false;
			if( typeof(password) == "undefined" || password == null || password == ""){
				$("#errHintMember").html("登录密码不能为空");
				$("#alert-dialog-member").popup();
				$("#alert-dialog-member").popup("open");
				return;
			}
			if(typeof(oldEmail) == "undefined" || oldEmail == null || oldEmail == ""){
				oldEmail = $("#mobile").val();
			}
			$.ajax({
				type: "POST",
				url: ssoServer + "/sso/isPasswordSame",
				data: "account=" + oldEmail + "&password=" + password,
				dataType: "jsonp",
			    jsonp: "callback",
				success: function(resp){
					switch(resp.res_code){
					case "success":
						postParams(param);
						return;
					case "error":
						canEditEmail = false;
						$("#errHintMember").html("登录密码不正确！");
						$("#alert-dialog-member").popup();
						$("#alert-dialog-member").popup("open");
						return;
					}
				},error:function(){
					canEditEmail = false;
				}
			}
			);
		}else{
			postParams(param);
		}
	});

});

function postParams(param){
	$.ajax({
		type: "POST",
		url: basepath + "/usercenter/weixin/saveMemberInfo",
		data: JSON.stringify(param),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "json",
		success: function(resp){
			if( resp.res_code == "SW_MEMB_0009" ){
//			$("#successDlg").modal();
				window.location.href = wxServer + "/usercenter/weixin/index";
			}else if( resp.res_code == "FW_MEMB_0007" || resp.res_code == "FW_MEMB_0005" ||
					resp.res_code == "FW_MEMB_0008" || resp.res_code == "FW_MEMB_0010" || resp.res_code == "FW_MEMB_1020"){
				$("#errHintMember").html(resp.res_msg);
				$("#alert-dialog-member").popup();
				$("#alert-dialog-member").popup("open");
			}
		}
	});
}

function sendGetVerificationCodeRequest(mobile,t){
	$.ajax({
		type: "POST",
		url: basepath + "/usercenter/weixin/getVerificationCode",
		data: mobile,
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "json",
		success: function(resp){
			verifycode_lock = false ;
			if( resp.res_code == "FW_MEMB_0004" || resp.res_code == "FW_MEMB_0005" || resp.res_code == "FW_MEMB_0006" ){
				manulDestoryTimer(t) ;
				$("#getVerificationCodeBtn").removeClass("retry");
				$("#errHintMember").html(resp.res_msg);
				$("#alert-dialog-member").popup();
				$("#alert-dialog-member").popup("open");
			}else if( resp.res_code == "SW_MEMB_0006" ){

			}
		},
		error:function(resp){
			manulDestoryTimer(t) ;
			verifycode_lock = false ;
		}
	});
}

/*验证码等待计时*/
function timer(intDiff){
	var timerObj = window.setInterval(function(){
		var second = 0;//时间默认值
		if(intDiff > 0){
			second = Math.floor(intDiff) ;
		}
		if (second <= 9) second = '0' + second;
		$("#getVerificationCodeBtn").find("span").html(second) ;
		destoryTimer(timerObj,intDiff) ;
		intDiff--;
	}, 1000);
}


function destoryTimer(timer,intDiff){
	if(intDiff <= 0){
		$("#getVerificationCodeBtn").removeClass("retry");
		manulDestoryTimer(timer) ;
	}
}

function manulDestoryTimer(timer){
		clearInterval(timer) ;
		$("#getVerificationCodeBtn").html("获取短信验证码") ;
}


function isMemberInfoParamValid(param){
	if( isNickNameValid(param.nickName)  && validateDetailAddress()  && isRealNameValid(param.realName))
		return true;
	else
		return false;
}

function isNickNameValid(nickName){
	if( !nickNameReg.test(nickName) ){
		$("#errHintMember").html("昵称输入不正确");
		$("#alert-dialog-member").popup();
		$("#alert-dialog-member").popup("open");
		return false;
	}
	else
		return true;
}

function isRealNameValid(realName){
	if(realNameReg1.test(realName) || realNameReg2.test(realName) )
		return true;
	else{
		$("#errHintMember").html("真实姓名输入不正确");
		$("#alert-dialog-member").popup();
		$("#alert-dialog-member").popup("open");
		return false;
	}
}

function trim(selector){
	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
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

$(function(){
	initAddress();
})
var address_1;
function initAddress(){
		$("#address").click(function(){
			if(null == address_1 || "" ==address_1){
				sessionStorage.clear();
		        sessionStorage.setItem("province", JSON.stringify(province));
		        sessionStorage.setItem("city", JSON.stringify(city));
		        sessionStorage.setItem("county", JSON.stringify(county));
		        var defaultProvince;
		        var defaultCity;
		        var defaultCounty;
		        if($("#hideProvince").val()!=""){
		        	defaultProvince = $("#hideProvince").val();
		        }else{
		        	defaultProvince = "北京市";
//					$("#province").html(defaultProvince);
		        }
				if($("#hideCity").val()!=""){
					defaultCity = $("#hideCity").val();
		        }else{
		        	defaultCity = "北京市";
//					$("#city").html(defaultCity);
		        }
				if($("#hideCounty").val()!=""){
					defaultCounty = $("#hideCounty").val();
				}else{
					defaultCounty = "东城区";
//					$("#country").html(defaultCounty);
				}
				address_1=  new AddressPicker("#addressWrapperContainer", {
		        	currentAddress: [defaultProvince, defaultCity, defaultCounty]
		        }).init();


			}
		   	$("#addressWrapperContainer").closest("div[data-role='popup']").find(".dp-sure-btn").click(function(){
				var address = address_1.getAddress();
				var addressHtml = address[0]+"  "+address[1]+"   "+address[2];
				var province = address[0];
				var city = address[1];
				var country = address[2];
				$("#hideProvince").val(province);
				$("#hideCity").val(city);
				$("#hideCounty").val(country);

				$("#province").html(province);
				$("#city").html(city);
				$("#country").html(country);
			});

			$("#addressWrapperContainer").closest("div[data-role='popup']").find(".dp-cancel-btn").click(function(){
				var currentProvince = $("#province").html()
				var currentCity = $("#city").html();
				var currentCountry = $("#country").html();
				if(currentProvince != null && currentProvince!="" && currentCity != null && currentCity !="" &&  currentCountry != null && currentCountry !=""){
					address_1.setAddress([currentProvince,currentCity,currentCountry]);
				}
				if(currentProvince == "" && currentCity == "" &&  currentCountry == ""){
					address_1.setAddress(["北京市","北京市","东城区"]);
				}
			});
        	$("#addressWrapperContainer").closest("div[data-role='popup']").popup("open");
        });

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
		$("#errHintMember").html("通讯地址格式错误!");
		$("#alert-dialog-member").popup();
		$("#alert-dialog-member").popup("open");
		return false;
	}
}


