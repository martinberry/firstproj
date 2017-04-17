
$(function(){
	$(".travelerInfoTab").delegate("#firstNameEdit","blur",function(){
		var firstName = $(this).val();
		toggleErrorHint(disappear,'#firstName_errorHintEdit') ;
		toggleErrorHint(disappear,'#firstNameEn_errorHintEdit');
		toggleErrorHint(disappear,'#repeateName_errorHintEdit') ;
		if(null == firstName || "" == firstName){
			toggleErrorHint("姓"+empty_hint_error,'#firstName_errorHintEdit') ;
		}
		if(cnNameReg.test(firstName)){
			var firstNamePinyin = Pinyin.GetQP($.trim(firstName)).toUpperCase();
			if(firstNamePinyin.length>30){
				toggleErrorHint(" 姓:"+nameErrorHint,'#firstName_errorHintEdit') ;
			}else{
				toggleErrorHint(disappear,'#firstNameEn_errorHintEdit');
				toggleErrorHint(disappear,'#repeateName_errorHintEdit') ;
				$("#firstNameEnEdit").val(firstNamePinyin);   //英文名输入框自动填拼音
			}
		}else if(enNameReg.test(firstName)){
			toggleErrorHint(disappear,'#firstNameEn_errorHintEdit');
			toggleErrorHint(disappear,'#repeateName_errorHintEdit') ;
			$("#firstNameEnEdit").val(firstName);
		}else{
			toggleErrorHint(" 姓:"+nameErrorHint,'#firstName_errorHintEdit') ;
		}

		checkRepeatName();
	})

	$(".travelerInfoTab").delegate("#lastNameEdit","blur",function(){
		var lastName = $(this).val();
		toggleErrorHint(disappear,'#lastName_errorHintEdit') ;
		toggleErrorHint(disappear,'#lastNameEn_errorHintEdit') ;
		toggleErrorHint(disappear,'#repeateName_errorHintEdit') ;
		if(null == lastName || "" == lastName){
			$("#lastNameEnEdit").val('');
    		toggleErrorHint("名"+empty_hint_error,'#lastName_errorHintEdit') ;
//    		toggleErrorHint("lastName"+empty_hint_error,'#lastNameEn_errorHintEdit') ;
		}
		if(cnNameReg.test(lastName)){
			var lastNamePinyin = Pinyin.GetQP($.trim(lastName)).toUpperCase();
			if(lastNamePinyin.length>30){
				toggleErrorHint(" 名:"+nameErrorHint,'#lastName_errorHintEdit') ;
			}else{
				toggleErrorHint(disappear,'#firstNameEn_errorHintEdit');
				toggleErrorHint(disappear,'#repeateName_errorHintEdit') ;
				$("#lastNameEnEdit").val(lastNamePinyin);   //英文名输入框自动填拼音
			}
		}else if(enNameReg.test(lastName)){
				toggleErrorHint(disappear,'#firstNameEn_errorHintEdit');
				toggleErrorHint(disappear,'#repeateName_errorHintEdit') ;
				$("#lastNameEnEdit").val(lastName);
		}else{
			toggleErrorHint(" 名:"+nameErrorHint,'#lastName_errorHintEdit') ;
		}
		checkRepeatName();
	})

$(".travelerInfoTab").delegate("#firstNameEnEdit","blur",function(){
		var firstName = $(this).val();
		toggleErrorHint(disappear,'#firstNameEn_errorHintEdit') ;
		if(null == firstName || "" == firstName){
			toggleErrorHint("firstName "+empty_hint_error,'#firstNameEn_errorHintEdit') ;
		}else if(!enNameReg.test(firstName)){
			toggleErrorHint("firstName"+enname_hint_error,'#firstNameEn_errorHintEdit') ;
		}


	})

	$(".travelerInfoTab").delegate("#lastNameEnEdit","blur",function(){
		var lastName = $(this).val();
		toggleErrorHint(disappear,'#lastNameEn_errorHintEdit') ;
		if(null == lastName || "" == lastName){
    		toggleErrorHint("lastName"+empty_hint_error,'#lastNameEn_errorHintEdit') ;
		}else if(!enNameReg.test(lastName)){
			toggleErrorHint("lastName"+enname_hint_error,'#lastNameEn_errorHintEdit') ;
		}
	})

	$(".travelerInfoTab").delegate("#phoneNumEdit","blur",function(){
		validatePhoneNumEdit($(this).val());
	});
	$(".idTypeInfoEdit").delegate(".number","blur",function(){
		validateBlurEditCredit($(this));
	});

	$(".travelerInfoTab").delegate("#emailEdit","blur",function(){
		validateEmailEdit($(this).val());
	});

	$("#address_detailAddress_edit").blur(function(){
		validateDetailAddressEdit();
	});




	var firstName = $("#firstEnNameHideEdit").val();
	var lastName = $("#lastEnNameHideEdit").val();
	if(cnNameReg.test(firstName)){
		firstName = Pinyin.GetQP($.trim(firstName)).toUpperCase();
	}
	if(cnNameReg.test(lastName)){
		lastName = Pinyin.GetQP($.trim(lastName)).toUpperCase();
	}
	$("#firstNameEnEdit").html(firstName);
	$("#lastNameEnEdit").html(lastName);

})

$(function(){
	$(".travelerInfoTab.idTypeInfoEdit").delegate(".number","blur",function(){
		var number = $(this).val();
		var creditType=$(this).siblings("div").find("a span ").html();
		var checkResult = isCredit(creditType,number);
		if(""==number){
			if("身份证"==creditType){
				checkResult="creditError";
			}
			if("护照"==creditType){
				checkResult="passportError";
			}
			if("港澳通行证"==creditType){
				checkResult="gangaoError";
			}
		}
		var creditHintEdit=$(this).parents("td").siblings().find(".errorHint");
		showCreditValidateMsg(checkResult,creditHintEdit);

		if(checkResult==""){
			$(this).parent().siblings("td").find(".datepicker").attr("disabled",false);
		}else{
			$(this).parent().siblings("td").find(".datepicker").attr("disabled",true);
		}
	});
})

function changeIdType() {
			    var result = ["身份证", "护照", "港澳通行证"];
			    $(".dropdown.id-type:visible").each(function(){
			        var curType = $(this).find("li.active a").html();
			        result = _.without(result, curType);
			    });
			    return result;
			}

			function geneIdTypeList(types,activeTypeHtml) {
			    var result = "";
			    for (var i = 0; i < types.length; i++) {
			    	var val = "";
			    	if(types[i] == '港澳通行证'){
			    		val = "GANGAOPASS";
			    	}else if(types[i] == '护照'){
			    		val = "PASSPORT";
			    	}else{
			    		val = "IDCARD";
			    	}
			        result += "<li data-val='"+val+"' ";
			        if (activeTypeHtml == undefined) {
	                    if (i == 0) {
	                        result += " class='active'";
	                    }
	                } else {
	                    if (types[i] == activeTypeHtml) {
	                        result += " class='active'";
	                    }
	                }
			        result += "><a href='javascript:;'>" + types[i] + "</a></li>";
			    }
			    return result;
			}

			function changeDropdown() {
			    $(".dropdown.id-type").each(function(){
			        $(this).find("span.activeFonts").html($(this).find("li.active a").html());
			    });
			}

			function addLiClickEvent() {
			    $(".dropdown-menu li a").click(function(){
			        var thisHtml = $(this).html();
			        var $thisParents = $(this).parents(".dropdown-menu li");
			        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
			        $thisParents.addClass("active");
			        $thisParents.siblings().removeClass("active");
			    });
			}

$(function(){

			//详细地址
			//地址联动
	    PROVINCEDD = new ZtrDropDown('#address_province_edit',
			{
	        	getData:function(request){return getAddress(request);},
	        	textName:'areaName',
	        	valueName:'no',
	    	}
	    );
	    CITYDD = new ZtrDropDown('#address_city_edit',
			{
	        	getData:function(request){return getAddress(request);},
			}
	    );
	    AREADD = new ZtrDropDown('#address_area_edit',
	    	{
	    		getData:function(request){return getAddress(request);},
			}
	    );
	    PROVINCEDD.casCading(CITYDD);
	    CITYDD.casCading(AREADD);
	    PROVINCEDD.init('');
	    initAddressDropDown();

		})

		//初始化地址地址下拉框
		function initAddressDropDown(){
			var defaultProvince = $('#defaultProvince').val();
			var defaultCity = $('#defaultCity').val();
			var defaultArea = $('#defaultArea').val();
			PROVINCEDD.select(defaultProvince);
			CITYDD.select(defaultCity);
			AREADD.select(defaultArea);
		}


    $(function(){
		  var idTypeAll = ["身份证", "护照", "港澳通行证"];
		    //添加证件类型
		    $(".addTravelerInfoModel").delegate(".travelerInfoTab .addRoundnessIcon","click",function(){
		        var addTr = $(this).parents(".travelerInfoTab").find(".addTrContent:last").html();
		        $(this).parents(".defaultTr").siblings(".addTrContent:last").before("<tr class='addTrContent'>" + addTr + "</tr>");

				var result = ["身份证", "护照", "港澳通行证"];
		        if ($(".idTypeInfoEdit .addTrContent:visible").length == 2) {
		            $(".travelerInfoTab .addRoundnessIcon").hide();
		        } else {
		            $(".travelerInfoTab .addRoundnessIcon").show();
		        }
				$(".idTypeInfoEdit").find("tr:visible").each(function(){
			            var curType = $(this).find(".dropdown").find(".activeFonts").html();
			            result = _.without(result, curType);
			         });
		        $(this).closest(".idTypeInfoEdit").find(".dropdown:visible:last").find("ul.dropdown-menu").html(geneIdTypeList(result));
		        addLiClickEvent();
		        changeDropdown();

		   	 $(" input.datepicker.default").datepicker({
		         format: "yyyy-mm-dd",
		         language: "zh-CN",
		         autoclose: true,
		         todayHighlight: true,
		         startDate:new Date(),
		         endDate:new Date("2035-12-31"),
		         weekStart: 0
		       }).on("show", function(){
		           $("div.datepicker table thead .prev").html("");
		           $("div.datepicker table thead .next").html("");
		       });
		    });

		   // 删除证件类型
		    $(".addTravelerInfoModel").delegate(".addTrContent .delRoundnessIcon","click",function(){
		        $(this).parents(".addTrContent").remove();

		        if ($(".idTypeInfoEdit .addTrContent:visible").length == 2) {
		            $(".travelerInfoTab .addRoundnessIcon").hide();
		        } else {
		            $(".travelerInfoTab .addRoundnessIcon").show();
		        }

		    $(this).closest(".idTypeInfoEdit").find(".dropdown:visible:last").find("ul.dropdown-menu").html(geneIdTypeList(changeIdType()));
		        addLiClickEvent();
		        changeDropdown();
		    });

		       //  点击 dropdown 时，判断并生成对应的 类型结构
		    $(".idTypeInfoEdit").delegate('.dropdown.id-type .dropdownBtn', 'click', function(event) {
		        var $that = $(this),activeTypeHtml = $that.find(".activeFonts").html();
		        var result = ["身份证", "护照", "港澳通行证"];
		        $that.parents("tr").siblings("tr:visible").each(function(){
		            var curType = $(this).find("li.active a").html();
		            result = _.without(result, curType);
		        });
		        $that.closest(".dropdown").find("ul.dropdown-menu").html(geneIdTypeList(result,activeTypeHtml));
		        addLiClickEvent();
		    });

		    $(".travelerInfoTab.idTypeInfoEdit").delegate("li.active","click",function(){
				var creditType =$(this).find("a").html();
				var creditNum = $(this).parents("tr").find(".number").val();
				var result=isCredit(creditType,creditNum);

				var creditHintEdit=$(this).parents("td").siblings().find(".errorHint");
				showCreditValidateMsg(result,creditHintEdit);
				  if(null!=result && ""!=result){
					  $(this).parents("td").siblings().find(".datepicker").attr("disabled",true);
				  }
				  if(""==result){
					  $(this).parents("td").siblings().find(".datepicker").attr("disabled",false);
				  }

			})

		    changeDropdown();

     });

	        $(function(){


				 $("input.datepicker.default").datepicker({
			         format: "yyyy-mm-dd",
			         language: "zh-CN",
			         autoclose: true,
			         todayHighlight: true,
			         startDate:new Date(),
			         endDate:new Date("2035-12-31"),
			         weekStart: 0
			       }).on("show", function(){
			           $("div.datepicker table thead .prev").html("");
			           $("div.datepicker table thead .next").html("");
			       });
			       $("#birthdayEdit").datepicker("setStartDate", "1920-01-01");
 				   $("#birthdayEdit").datepicker("setEndDate", new Date());

	        	$(".commonIcon.radioIcon").click(function(){
	        		$(this).parent().siblings(".radio").removeClass("active") ;
	        		$(this).parent().addClass("active") ;
	        	}) ;
	        	$(".commonIcon.checkboxIcon").click(function(){
	        		if($(this).parent().hasClass("active")){
	        			$(this).parent().removeClass("active") ;
	        		}else{
	        			$(this).parent().addClass("active") ;
	        		}
	        	}) ;

	        	var getParams = function(selector) {
					var formParams = selector.serializeObject();
					/**
					 * 证件信息，删除数组最后一项(因为默认是空的)
					 */
					var addcredentialList = [];

					/**
					 * 第一行
					 */
					var defaultCredential = {};

					defaultCredential.type = selector.find(".defaultTr").find("ul li.active").data("val");
					defaultCredential.number = selector.find(".defaultTr").find(".number")
							.val();
					defaultCredential.deadLineDay = selector.find(".defaultTr").find(
							".datepicker").val();

					addcredentialList.push(defaultCredential);
					selector.find(".addTrContent").each(function() {
						var tempCredential = {};
						tempCredential.type = $(this).find('ul li.active').data("val");
						tempCredential.number = $(this).find(".number").val();
						tempCredential.deadLineDay = $(this).find(".datepicker").val();
						addcredentialList.push(tempCredential);
					});
					/**
					 * 删除默认空的哪一行
					 */
					addcredentialList.pop();
					formParams.credentials = addcredentialList;

					/**
					 * 性别，旅客类型。是否默认
					 */
					formParams.gender = selector.find(".radioContent.gender label.active").data("val");

					formParams.travelType = selector.find(".radioContent.travelType label.active").data("val");
					formParams.isDefault = selector.find("label[name='isDefault']")
							.hasClass("active") ? "true" : "false";

					formParams.id = selector.find("#travelerId").val() ;

			//地址
				formParams.province=$("#address_province_edit .active a").html();
				formParams.city=$("#address_city_edit .active a").html();
				formParams.district=$("#address_area_edit .active a").html();
				formParams.detailAddress=$("#address_detailAddress_edit").val();

				var firstName = $("#firstNameEdit").val();
				var lastName = $("#lastNameEdit").val();
				formParams.firstEnName = firstName;
				formParams.lastEnName = lastName;
				if(null!=firstName &&""!=firstName && null!=lastName && ""!=lastName ){

					if(cnNameReg.test(firstName) && cnNameReg.test(lastName)){
						formParams.travelerNameCn=firstName+lastName;
					}else{
						formParams.travelerNameCn = firstName+"/"+lastName;
					}
				}

				formParams.firstEnName =  $("#firstNameEnEdit").val();
				formParams.lastEnName = $("#lastNameEnEdit").val();

				formParams.travelerNameEn=formParams.firstEnName+"/"+formParams.lastEnName;

					return formParams;
				};

	        	$(".commonBtn.blueBtn.edit").click(function() {
		        	//校验表单
					if( !formEditValidator()  || $(".errorHint:visible").parent().length > 0){
						return false;
					}
					memberIsActive();
					$.ajax({
						type : "POST",
						url : basepath + "/travelerInfo/save",
						data : JSON.stringify(getParams($("#editTravelerForm"))),

						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						dataType : "json",
						success : function(data) {
						if(data.res_code == '0'){
								toggleErrorHint(disappear,'#firstName_errorHintEdit') ;
					    		toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
								window.location.href = basepath + "/travelerInfo/list"
							}else if(data.res_code == 'FF_TRAVLERINFO_1001'){
								toggleErrorHint(disappear,'#phoneNum_errorHintEdit') ;
								toggleErrorHint(mobile_hint_formaterror,'#phoneNum_errorHintEdit') ;
						 		return;
					    	}else if(data.res_code == 'FF_TRAVLERINFO_1002'){
					    		toggleErrorHint(disappear,'#email_errorHintEdit') ;
								toggleErrorHint(email_hint_formaterror,'#email_errorHintEdit') ;
					    		return;
					    	}else if(data.res_code == 'FF_TRAVLERINFO_1003'){
					    		toggleErrorHint(disappear,'#firstName_errorHintEdit') ;
								toggleErrorHint("姓:请输入0-20个中英文",'#firstName_errorHintEdit') ;
					    		return;
					    	}else if(data.res_code == 'FF_TRAVLERINFO_1004'){
					    		toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
								toggleErrorHint("名:请输入0-20个中英文",'#lastName_errorHintAdd') ;
					    		return;
					    	}else if(data.res_code == 'FF_TRAVLERINFO_1005'){
					    		alert(creditCard_hint_emptyerror);
					    		return;
					    	}else if(data.res_code == 'FF_TRAVLERINFO_1006'){
					    		alert(passport_hint_emptyerror);
					    		return;
					    	}else if(data.res_code == 'FF_TRAVLERINFO_1007'){
					    		alert( gangaopass_hint_emptyerror);
					    		return;
					    	}else if(data.res_code == 'FF_TRAVLERINFO_1010'){
					    		alert(credit_hint_sizeerror);
					    		return;
					    	}else if(data.res_code=='FF_TRAVLERINFO_1013'){
					    		toggleErrorHint(disappear,'#nationality_errorHintEdit') ;
					    		toggleErrorHint(nationality_hint_error+$("#nationality").val(),'#nationality_errorHintEdit') ;
					    		return;
					       }else if(data.res_code=='FF_TRAVLERINFO_1015'){
					    		toggleErrorHint(disappear,'#repeateName_errorHintEdit') ;
					    		toggleErrorHint(repeatename_hint_error,'#repeateName_errorHintEdit') ;
					    		return;
					    	}else if(data.res_code=='MEMBERINFOERROR'){
					    		alert(data.res_msg);
					    		return;
					    	}else if(data.res_code=='EF_PDBK_1010'){
					    		toggleErrorHint(disappear,'#repeateName_errorHintEdit') ;
					    		toggleErrorHint(repeatename_hint_error,'#repeateName_errorHintEdit') ;
					    	}
						}
					});
	        	});
    })


function validateBlurEditCredit(credit){

	var creditNum = $(credit).val();
	var creditType = $(credit).prev().find("a span").html();
	var result=isCredit(creditType,creditNum);
	if(""==creditNum){
		if("身份证"==creditType){
				result="creditError";
		}
		if("护照"==creditType){
				result="passportError";
		}
		if("港澳通行证"==creditType){
				result="gangaoError";
		}
	}
	var creditHintEdit=$(credit).parent().parent().find(".errorHint");
	toggleErrorHint(disappear,creditHintEdit) ;
	if("creditError"==result){
		toggleErrorHint(disappear,creditHintEdit) ;
		toggleErrorHint(creditCard_hint_emptyerror,creditHintEdit) ;
		return false;
	}else if("passportError"==result){
		toggleErrorHint(disappear,creditHintEdit) ;
		toggleErrorHint(passport_hint_emptyerror,creditHintEdit) ;
		return false;
	}else if("gangaoError"==result){
		toggleErrorHint(disappear,creditHintEdit) ;
		toggleErrorHint(gangaopass_hint_emptyerror,creditHintEdit) ;
		return false;
	}
	return true;
}

function validatePhoneNumEdit(phoneNum){
	if(null==phoneNum || ''==phoneNum){
		toggleErrorHint(disappear,'#phoneNum_errorHintEdit') ;
	}else{
		if( !isPhone(phoneNum)){
			toggleErrorHint(disappear,'#phoneNum_errorHintEdit') ;
			toggleErrorHint(mobile_hint_formaterror,'#phoneNum_errorHintEdit') ;
		}else{
			toggleErrorHint(disappear,'#phoneNum_errorHintEdit') ;
		}
	}
}

function validateEmailEdit(email){
	var originEmail = $("#originEmail").val();
	if(null!=email && email != ''){
		if(!isEmail(email)){
			toggleErrorHint(disappear,'#email_errorHintEdit') ;
			toggleErrorHint(email_hint_formaterror,'#email_errorHintEdit') ;
			}else{
				toggleErrorHint(disappear,'#email_errorHintEdit') ;
			}
		}else{
			toggleErrorHint(disappear,'#email_errorHintEdit') ;
		}
		validateEmail(email);
}



function formEditValidator(){
	var result=false;
	if($(".errorHint:visible").parent().length > 0){
		return result;
	}
	//手机号码
	var phoneNum=$("#phoneNumEdit").val();
	validatePhoneNum(phoneNum);
	//邮箱
	var email=$("#emailEdit").val();
	validateEmailEdit(email);
	//校验证件
	validateCredits();

	validateDetailAddressEdit();

	validateAddressEdit();

	validateNamesEdit();
	checkRepeatNameEdit();

	return true;
}

$(function(){
	$("#nationalityEdit").typeahead({
	    source: function (query, process) {
	    	 $((($(this)[0]).$element)[0]).val($((($(this)[0]).$element)[0]).val().replace(/</g,"&lt").replace(/>/g,""));
	        var parameter = {query: query};
	        $.post(basepath + "/travelerInfo/countryAutoComplete", parameter, function (data) {
	        	if(data.length==0 || !isExistNationalityEdit(data)){
	        		notFindHint =nationality_hint_error+$("#nationalityEdit").val();
	        		$("#nationalityDropListEdit").val(notFindHint);
	        		data=[notFindHint];
	        	}else{
	        		$("#nationalityDropListEdit").val(data);
	        	}
	            process(data);
	        });
	    }
	});

	$("#nationalityEdit").blur(function(){
		var nationalityDropList = $("#nationalityDropListEdit").val();
		if(nationalityDropList.indexOf(nationality_hint_error) != -1){
			 $("#nationalityEdit").val('');
		}
		if(null!=nationalityDropList && ""!= nationalityDropList && $.inArray( $("#nationalityEdit").val(), nationalityDropList.split(',')) <0){
			 $("#nationalityEdit").val('');
		}
	})
})

function isExistNationalityEdit(data){
	var isExist= false;
	var nationality = $("#nationalityEdit").val();
	if(null!=nationality  ){
		for(var i=0;i<data.length;i++){
			if(data[i].indexOf(nationality) != -1){
				isExist=true;
				break;
			}
		}
	}
	return isExist;
}

function checkRepeatNameEdit(){

	var firstName=$("#firstNameEdit").val();
	var lastName = $("#lastNameEdit").val();
	var traveleNameCn = "";
	if(null != firstName && firstName != "" && null!=lastName && lastName!=""){
		if(!(firstName == $("#firstEnNameHideEdit").val() && lastName==$("#lastEnNameHideEdit").val())){
			toggleErrorHint(disappear,'#firstName_errorHintEdit') ;

			if(cnNameReg.test(firstName) && cnNameReg.test(lastName)){
				travelerNameCn=firstName+lastName;
			}else{
				travelerNameCn = firstName+"/"+lastName;
			}

			$.ajax({
				type : "GET",
				url : basepath + "/travelerInfo/checkRepeateName",
				data : {
					travelerNameCn:travelerNameCn
				},
				dataType : "json",
				success : function(data) {
					if(data.result=="successed"){
						if(data.repeate=="repeated")
							toggleErrorHint("重复姓名",'#firstName_errorHintEdit') ;
					}
				}
			});
		}

	}

}

$(function(){
	$("#address_area_edit").delegate("ul.dropdown-menu li a","click",function(){
		toggleErrorHint(disappear,'#address_errorHint_edit') ;
	})
})


