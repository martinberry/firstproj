//var allCredentialTypes = ["IDCARD", "PASSPORT", "GANGAOPASS"];
var allCredentialTypes = ["身份证", "护照", "港澳通行证"];

var addCreIndex=0;
var nationality_hint_error='对不起，找不到：';

Date.prototype.yyyymmdd = function() {
	   var yyyy = this.getFullYear().toString();
	   var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
	   var dd  = this.getDate().toString();
	   return yyyy +"-"+ (mm[1]?mm:"0"+mm[0]) +"-"+ (dd[1]?dd:"0"+dd[0]); // padding
	  };

	  var address_1;
function initAddress(){
		$("#address").click(function(){

			if(null == address_1 || "" ==address_1){
				console.log(1);
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
$(function(){
	autoCompleteNationality();
	nationalityBlurIfNotFoundSetEmpty();



     $("#birthdate").click(function(){
    		var defaultDate =new Date( $("#birthdate").html() );
    		if(defaultDate == null || defaultDate == ""){
    			defaultDate = new Date(1980,0,1);
    		}
    		 var dp = new DatepickerScroll("#birthdate", {
    	     	    startYear: 1920,
    				defaultDate: defaultDate,
    				cancelCallback: function(){
    					dp.setDate(defaultDate);
    				},
    				confirmCallback: function() {
    					$("#birthdate").html(dp.getDate().yyyymmdd());
    				}
    	     }).init();
    	 $("#birthdateDatepicker").popup({
				afterclose: function() {
					if(dp.getDate() > new Date()){
						$("#errHint").html("出生日期应小于当前日期");
						$("#alertErrHint-dialog").popup();
						$("#alertErrHint-dialog").popup("open");
					}
				}
			});
     	$("#birthdateDatepicker").popup("open");
     });

     initAddress();
})

$(function(){
	//添加证件button
	$("#addCredentialBtn").click(function(){
		addCreIndex = addCreIndex+1;
		console.log("addCreIndex: "+addCreIndex )

		var credNum = $("#credentialList").children(".revise-contain01").size();    //已有证件个数
		if( !(credNum < 3) ){  //最多3个证件
			return false;
		}
		var credTypeHad = getAlreadyHaveCredTypes();  //已有证件类型
		var blankCredential = $("#blankCredential").html();

		console.log("blankCredential"+blankCredential);

		$("#credentialList").append(blankCredential);

		var availableTypes = getAvailableCredTypes(credTypeHad);
		//新加证件默认类型
		var defaultType = {};
		defaultType = nextDefaultCredentialType(credTypeHad);
		var justAddedCred = $("#credentialList .revise-contain01").last();

		var credential = $(justAddedCred).find("input[name='credentialType']");
		var scrollId =  $(credential).attr("id");

		scrollId = scrollId+addCreIndex;
		scrollId = scrollId +"Wrapper";
		$(justAddedCred).find("input[name='credentialType']").val(defaultType.name);

		var deadLineDay = $(justAddedCred).find("#deadLineDates")
		deadLineDayId = "deadLineDates"+addCreIndex;
		$(deadLineDay).attr("id",deadLineDayId);
		$(deadLineDay).html("2020-01-01")

		$(credential).val(defaultType.name);
	});

	//删除证件button
	$("#credentialList").delegate(".rev-del", "click", function(){

		var credNum = $("#credentialList").children(".revise-contain01").size();    //已有证件个数
		if( credNum > 1 ){
			$(this).parent().siblings().last().parent(".revise-contain01").remove();
			$(this).parent().remove();
		}else{
			alert("请至少填写一个证件信息");
		}
	});

	//保存信息
	$("#saveBtn").click(function(){
		var param = buildTravellerInfoParam();
		if( !isTravellerInfoParamValid(param) ){
			return false;
		}

		$.ajax({
			type: "POST",
			url: basepath + "/usercenter/weixin/saveTravellerInfo",
			data: JSON.stringify(param),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success: function(resp){
				if( resp.res_code == "SW_MEMB_0011" ){
					window.location.href = basepath + "/usercenter/weixin/index";
				}else if( resp.res_code == "FW_MEMB_1012" || resp.res_code == "FW_MEMB_1013" ||  resp.res_code == "EF_PDBK_1010" ){
					$("#errHint").html(resp.res_msg);
					$("#alertErrHint-dialog").popup();
					$("#alertErrHint-dialog").popup("open");
				}
			}
		});
	});

});

function credentialAddOnClick(credential, availableTypes, defaultType, scrollId){
	console.log("scrollId"+scrollId);
	     	$(credential).cusListPop({
	     		title: "请选择证件类型",
	     		scrollId: scrollId,
	     		data: availableTypes,
	     		currentData:defaultType,
	              reCreate: true,
	     		callback: function() {
	     			$("#"+scrollId+"Wrapper").find("li").click(function(){
	     			   setTimeout(function(){
                           $("#" + scrollId + "PopContainer").popup("close");
                       }, 300);

                       if ($(this).hasClass("current")) {
                    	  $( credential).val($(this).html());
                       }

	     			})
	     		}
	     	});
}

function buildTravellerInfoParam(){
	var param = {};
	param.id = $("#travellerId").text();
	param.firstNameCn = $("#firstName").val();
	param.lastNameCn = $("#lastName").val();
	param.firstEnName = $("#firstNameEn").val();
	param.lastEnName = $("#lastNameEn").val();
	param.email = $("#email").val();
	param.phoneNum = $("#mobile").val();
	param.gender = $("#gender").find("input:checked").val();
	param.travelType = $("#travellerType").find("input:checked").val();
	param.nationality = $("#nationality").val();
	param.birthday = $("#birthday").html();
	param.province =$("#province").html();
	param.city = $("#city").html();
	param.district = $("#country").html();

	param.detailAddress = $("#detailAddress").val();

	var credentialList = [];
	$("#credentialList").find(".revise-contain01").each(function(){
		var credential = {};
		credential.type = $(this).find("input[name='credentialType']").val();
		if(credential.type == "身份证"){
			credential.type="IDCARD";
		}
		if(credential.type == "护照"){
			credential.type="PASSPORT";
			}
		if(credential.type == "港澳通行证"){
			credential.type="GANGAOPASS";
		}
		credential.number = $(this).find("input[name='credentialNumber']").val();
		credential.deadLineDay=$(this).find("span[name='deadLineDate']").html();


		credentialList.push(credential);
	});
	param.credentials = credentialList;

	return param;
}

//新加证件默认证件类型
function nextDefaultCredentialType(credTypesHad){
	var nextDefaultType;
	var availableTypes=  _.difference(allCredentialTypes , credTypesHad);
	console.log("availableTypes"+availableTypes);
	if(null!=availableTypes){
		nextDefaultType = availableTypes[0];
	}
	var defaultType={};
	defaultType.name= nextDefaultType;
	if(nextDefaultType == '身份证'){
		defaultType.id= "IDCARD";
	}

	if(nextDefaultType == '护照'){
		defaultType.id= "PASSPORT";
	}

	if(nextDefaultType == '港澳通行证'){
		defaultType.id= "GANGAOPASS";
	}
	return defaultType;
}
//get已有证件类型
function getAlreadyHaveCredTypes(){
	var credTypesHad = [];   //已有证件类型
	$("#credentialList").children(".revise-contain01").each(function(){
		console.log("into getAlreadyHaveCredTypes()");
		var type = $(this).find("input[name='credentialType']").val();
		console.log("type: "+type);
		credTypesHad.push(type);
	});
	return credTypesHad;
}
//证件类型是否已有
function isCredTypeAlreadyHave(type, typesHad){
	var result = false;
	for( var i = 0; i < typesHad.length; i++ ){
		if( typesHad[i] == type )
			result = true;
	}
	return result;
}
//可选证件类型
function getAvailableCredTypes(credTypeHad,currentCredentialType){
	var avaTypes = [];
	var availableArr = _.difference(allCredentialTypes , credTypeHad);
    console.log("availableArr: "+availableArr);
	for( var i = 0; i < availableArr.length; i++ ){
		var typeObject={}
		var type = availableArr[i];
		typeObject.name= type;
		if(type == '身份证'){
			typeObject.id= "IDCARD";
		}
		if(type == '护照'){
			typeObject.id= "PASSPORT";
		}
		if(type == '港澳通行证'){
			typeObject.id= "GANGAOPASS";
		}
		avaTypes.push(typeObject);
	}
	if(null != currentCredentialType){
		avaTypes.push(currentCredentialType);
	}
	return avaTypes;
}

function isExistNationalityEdit(data){
	var isExist= false;
	var nationality = $("#nationality").val();
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

//点击证件类型
$(function(){
	$("body").delegate("input[name='credentialType']","click",function(){
		console.log("------------------点击证件类型----------------");
		//获取当前的证件类型
        var currentCredentialType = getCurrentCredentialType($(this));
        console.log("currentCredentialType:"+currentCredentialType);
		//获取已有证件类型
        var credTypeHad = getAlreadyHaveCredTypes();  //已有证件类型
        console.log("credTypeHad:"+credTypeHad)
		//获取可选证件类型
        var availableTypes = getAvailableCredTypes(credTypeHad,currentCredentialType);
        console.log("availableTypes:"+availableTypes);
		//获取当前scrollId
        var scrollId = $(this).attr("id");
        console.log("scrollId: "+scrollId);
        credentialAddOnClick(this, availableTypes, currentCredentialType , scrollId);
	})

	$("body").delegate("div[name='deadLineDateDiv']","click",function(){
		var deadLineDayId = $(this).find("span[name='deadLineDate']").attr("id");
		var deadLineDateVal = $("#"+deadLineDayId+"").html();
		var defaultDate;
		if(deadLineDateVal != null  && deadLineDateVal != ""){
			defaultDate = new Date(deadLineDateVal);
		}else{
			defaultDate= new Date("2020-01-01");
		}
		 var deadLineDay = new DatepickerScroll("#"+deadLineDayId+"", {
	      		startYear: new Date().getFullYear().toString(),
	 			endYear: 2035,
	 			defaultDate: defaultDate,
	 			cancelCallback: function(){
	 				deadLineDay.setDate(defaultDate);
	 			},
	 			confirmCallback: function() {
	 				$("#"+deadLineDayId+"").html(deadLineDay.getDate().yyyymmdd());
	 			}
	      }).init();

			$("#"+deadLineDayId+"Datepicker").popup({
					afterclose: function() {
						if(deadLineDay.getDate()<new Date()){
							$("#errHint").html("有效期应大于当前日期");
							$("#alertErrHint-dialog").popup();
							$("#alertErrHint-dialog").popup("open");
						}
					}
				});

			$("#"+deadLineDayId+"Datepicker").popup("open");
	})


})

function getCurrentCredentialType(credential){
	var currentTypeValue = $(credential).val();
	var currentCredential = {};
	currentCredential.name = currentTypeValue;
	if(currentTypeValue == '身份证'){
		currentCredential.id="IDCARD"
	}
	if(currentTypeValue == '护照'){
		currentCredential.id="PASSPORT"
	}
	if(currentTypeValue == '港澳通行证'){
		currentCredential.id="GANGAOPASS"
	}
	return currentCredential;
}

/**
 *  国籍blur
 */
function nationalityBlurIfNotFoundSetEmpty(){
	$("#nationality").blur(function(){
//		toggleErrorHint(disappear,'#nationality_errorHintAdd') ;
		var nationalityDropList = $("#nationalityDropList").val();
		if(nationalityDropList.indexOf(nationality_hint_error) != -1 ){
			 $("#nationality").val('');
		}
		if(null!=nationalityDropList && ""!= nationalityDropList && $.inArray( $("#nationality").val(), nationalityDropList.split(',')) <0){
			 $("#nationality").val('');
		}

	})
}
//
function isExistNationality(data){
	var isExist= false;
	var nationality = $("#nationality").val();
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

//国籍自动补全
function autoCompleteNationality(){
	$("#nationality").typeahead({
		source: function (query, process) {
			$((($(this)[0]).$element)[0]).val($((($(this)[0]).$element)[0]).val().replace(/</g,"&lt").replace(/>/g,""));
	        var parameter = {query: query};
	        $.post(basepath + "/usercenter/weixin/countryAutoComplete", parameter, function(data){
	        	if( data.length == 0 || !isExistNationalityEdit(data) ){
	        		notFindHint =nationality_hint_error+$("#nationality").val();
	        		$("#nationalityDropList").val(notFindHint);
	        		data=[notFindHint];
	        	}else{
	        		$("#nationalityDropList").val(data);
	        	}
	            process(data);
	        });
		}
	});
}

