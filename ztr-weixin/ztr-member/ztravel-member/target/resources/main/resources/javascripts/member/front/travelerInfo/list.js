var disappear = '' ;

var mobile_hint_formaterror = '请输入正确的手机号' ;

var email_hint_formaterror = '邮箱格式有误' ;
var email_hint_isalreadyexists = '邮箱已经被注册' ;

var creditCard_hint_emptyerror="请输入正确的身份证";
var passport_hint_emptyerror="请输入正确的护照";
var gangaopass_hint_emptyerror="请输入正确的港澳通行证";
var credit_hint_sizeerror="证件数量不能大于３";

var nationality_hint_error='对不起，找不到：';
var detailAddress_hint_error="地址格式不正确";

var repeatename_hint_error="该旅客已存在";
var enname_hint_error = "请输入拼音或英文"
var cnname_hint_error="请输入1-20个中英文字符"
var empty_hint_error="不能为空"

var nameErrorHint = "填写不正确";

var cnNameReg = /^[\u4E00-\u9FA5]{1,20}$/;
var enNameReg = /^([a-zA-Z]{1,30})$/;

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



$(function(){

	initBirthDayDatePickers();

	autoCompleteNationality();

	nationalityBlurIfNotFoundSetEmpty();

	detailAddressCascade();


})



/**
 *  初始化时间控件
 */
function initBirthDayDatePickers(){
	$(".idTypeInfo .datepicker").datepicker("setStartDate",new Date());
	$(".idTypeInfo .datepicker").datepicker("setEndDate",new Date("2035-12-31"));
	$("#birthday").datepicker("setStartDate", "1920-01-01");
	$("#birthday").datepicker("setEndDate", new Date());
}

/**
 * 国籍自动补全
 */
function autoCompleteNationality(){
	$("#nationality").typeahead({
	    source: function (query, process) {
	    	 $((($(this)[0]).$element)[0]).val($((($(this)[0]).$element)[0]).val().replace(/</g,"&lt").replace(/>/g,""));
	        var parameter = {query: query};
	        $.post(basepath + "/travelerInfo/countryAutoComplete", parameter, function (data) {
	        	if(data.length==0 || !isExistNationality(data)){
	        		notFindHint =nationality_hint_error+$("#nationality").val();
	        		data=[notFindHint];
	        	}else{
	        		$("#nationalityDropList").val(data);
	        	}
	            process(data);
	        });
	    }
	});
}

/**
 *  国籍blur
 */
function nationalityBlurIfNotFoundSetEmpty(){
	$("#nationality").blur(function(){
		toggleErrorHint(disappear,'#nationality_errorHintAdd') ;
		var nationalityDropList = $("#nationalityDropList").val();

		if(nationalityDropList.indexOf(nationality_hint_error) != -1 ){
			 $("#nationality").val('');
		}
		if(null!=nationalityDropList && ""!= nationalityDropList && $.inArray( $("#nationality").val(), nationalityDropList.split(',')) <0){
			 $("#nationality").val('');
		}

	})
}

/**
 * 详细地址联动
 */
function detailAddressCascade(){
	   //地址联动
    PROVINCEDD = new ZtrDropDown('#address_province',
		{
        	getData:function(request){return getAddress(request);},
        	textName:'areaName',
        	valueName:'no',
    	}
    );
    CITYDD = new ZtrDropDown('#address_city',
		{
        	getData:function(request){return getAddress(request);},
		}
    );
    AREADD = new ZtrDropDown('#address_area',
    	{
    		getData:function(request){return getAddress(request);},
		}
    );
    PROVINCEDD.casCading(CITYDD);
    CITYDD.casCading(AREADD);
    PROVINCEDD.init('');
    PROVINCEDD.select('');
}

//证件操作
$(function(){

	addCredential();

	delCredential();

	credentialTypeDropDownClick();

    changeDropdown();
});

function addCredential(){
	//添加证件类型
    $(".addTravelerInfoModel").delegate(".travelerInfoTab .addRoundnessIcon","click",function(){

    	var addTr = $(this).parents(".travelerInfoTab").find(".addTrContent").html();
        $(this).parents(".defaultTr").siblings(".addTrContent:last").before("<tr class='addTrContent'>" + addTr + "</tr>");

        if ($(".idTypeInfo .addTrContent:visible").length == 2) {
            $(".travelerInfoTab .addRoundnessIcon").hide();
        } else {
            $(".travelerInfoTab .addRoundnessIcon").show();
        }

        $(this).closest(".idTypeInfo").find(".dropdown:visible:last").find("ul.dropdown-menu").html(geneIdTypeList(changeIdType()));
        addLiClickEvent();
        changeDropdown();
        initValidateDate();
    });
}

function delCredential(){
    $(".addTravelerInfoModel").delegate(".addTrContent .delRoundnessIcon","click",function(){
        $(this).parents(".addTrContent").remove();

        if ($(".idTypeInfo .addTrContent:visible").length == 2) {
            $(".travelerInfoTab .addRoundnessIcon").hide();
        } else {
            $(".travelerInfoTab .addRoundnessIcon").show();
        }

        $(this).closest(".idTypeInfo").find(".dropdown:visible:last").find("ul.dropdown-menu").html(geneIdTypeList(changeIdType()));
        addLiClickEvent();
        changeDropdown();
    });
}

function credentialTypeDropDownClick(){
	   $(".idTypeInfo").delegate('.dropdown.id-type .dropdownBtn', 'click', function(event) {
	        var $that = $(this);
	        var activeTypeHtml = $that.find(".activeFonts").html();
	        var result = ["身份证", "护照", "港澳通行证"];
	        $that.parents("tr").siblings("tr:visible").each(function(){
	            var curType = $(this).find("li.active a").html();
	            result = _.without(result, curType);
	        });
	        $that.closest(".dropdown").find("ul.dropdown-menu").html(geneIdTypeList(result,activeTypeHtml));

	        addLiClickEvent();
//	        changeDropdown();
	    });
}

function initValidateDate(){
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
}

//  添加 删除 选择 操作后需要重新过滤证件类型的值
function changeIdType() {
    var result = ["身份证","护照","港澳通行证"];
    $(".dropdown.id-type:visible").each(function(){
        var curType = $(this).find("li.active a").html();
        result = _.without(result, curType);
    });
    return result;
}


//  根据证件类型 生成对应的li结构
function geneIdTypeList(types,activeTypeHtml) {
    var result = "";

    for (var i = 0; i < types.length; i++) {
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


//  dropdown 默认显示的是 active li中的值
function changeDropdown() {
    $(".dropdown.id-type").each(function(){
        $(this).find("span.activeFonts").html($(this).find("li.active a").html());
    });
}

//  li 被替换掉之后没有点击事件，需重新绑定一次
function addLiClickEvent() {
    $(".dropdown-menu li a").click(function(){
        var thisHtml = $(this).html();
        var $thisParents = $(this).parents(".dropdown-menu li");
        $(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
        $thisParents.addClass("active");
        $thisParents.siblings().removeClass("active");
    });
}

//常旅客操作
$(function() {
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	addTraveler();

	editTraveler();

	delTraveler();

	saveTraveler();

	blurValidateAdd();

});

/**
 * 添加常旅客
 */
function addTraveler(){


	$(".titleContent .addTravelerBtn").click(function() {
		memberIsActive();

		$(this).parents(".travelerInfoListModel").hide();
		$(".addTravelerInfoModel.add").show();
	});
}
/**
 * 编辑常旅客
 */
function editTraveler(){
	$(".bigEditIcon").click(function() {

		memberIsActive();

		$(".travelerInfoListModel").hide();
		var travelId = $(this).parent(".hoverBtn").attr("id");
		$.ajax({
			type : "POST",
			url : basepath + "/travelerInfo/edit",
			data : "id=" + travelId,
			dataType : "html",
			success : function(result) {
				$("#travelerInfoSync").html(result);
				$(".addTravelerInfoModel.edit").show();
			}
		});
	});
}

/**
 * 删除常旅客
 */
function delTraveler(){
	$(".bigDeleteIcon").click(function() {
		memberIsActive();
		var travelId = $(this).parent(".hoverBtn").attr("id");
		$("#delGuestWindow").modal("show");
		$(".commonBtn.blueBtn.delete").click(function(){
			$.ajax({
				type : "POST",
				url : basepath + "/travelerInfo/delete",
				data : "id=" + travelId,
				dataType : "json",
				success : function(result) {
					window.location.href = basepath + "/travelerInfo/list"
				}
			});
		});
	});
}


/**
 * 保存常旅客
 */
function saveTraveler(){
	$(".commonBtn.blueBtn.add").click(function() {
		//校验表单
		if(!formValidator() || $(".errorHint:visible").parent().length > 0){
			return false;
		}

		//判断分享者与被分享者是否挂起，如果有挂起的跳转到首页，如果木有继续分享
		memberIsActive();

		//保存
		$.ajax({
			type : "POST",
			url : basepath + "/travelerInfo/save",
			data : JSON.stringify(getParams($("#addTravelerForm"))),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			async:false,
			dataType : "json",
			success : function(data) {
				if(data.res_code == '0'){
					toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
		    		toggleErrorHint(disappear,'#firstName_errorHintAdd') ;
					window.location.href = basepath + "/travelerInfo/list"
				}else if(data.res_code == 'FF_TRAVLERINFO_1001'){
					toggleErrorHint(disappear,'#phoneNum_errorHintAdd') ;
					toggleErrorHint(mobile_hint_formaterror,'#phoneNum_errorHintAdd') ;
			 		return;
		    	}else if(data.res_code == 'FF_TRAVLERINFO_1002'){
		    		toggleErrorHint(disappear,'#email_errorHintAdd') ;
					toggleErrorHint(email_hint_formaterror,'#email_errorHintAdd') ;
		    		return;
		    	}else if(data.res_code == 'FF_TRAVLERINFO_1003'){
		    		toggleErrorHint(disappear,'#firstName_errorHintAdd') ;
					toggleErrorHint("姓:请输入0-20个中英文",'#firstName_errorHintAdd') ;
		    		return;
		    	}else if(data.res_code == 'FF_TRAVLERINFO_1004'){
		    		toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
					toggleErrorHint("名:请输入0-20个中英文",'#lastName_errorHintAdd') ;
		    		return;
		    	}
		    	else if(data.res_code == 'FF_TRAVLERINFO_1005'){
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
		    	}else if(data.res_code=='100003'){
		    		toggleErrorHint(disappear,'#travelerNameCn_errorHintAdd') ;
		    		toggleErrorHint(disappear,'#travelerNameEn_errorHintAdd') ;
		    		alert(data.res_msg);
		    		return;
		    	}else if(data.res_code=='FF_TRAVLERINFO_1013'){
		    		toggleErrorHint(disappear,'#nationality_errorHintAdd') ;
		    		toggleErrorHint(nationality_hint_error+$("#nationality").val(),'#nationality_errorHintAdd') ;
		    		return;
		    	}else if(data.res_code=='FF_TRAVLERINFO_1015'){
		    		toggleErrorHint(disappear,'#repeateName_errorHintAdd') ;
		    		toggleErrorHint(repeatename_hint_error,'#repeateName_errorHintAdd') ;
		    		return;
		    	}else if(data.res_code=='MEMBERINFOERROR'){
		    		alert(data.res_msg);
		    		return;
		    	}else if(data.res_code=='EF_PDBK_1010'){
		    		toggleErrorHint(disappear,'#repeateName_errorHintAdd') ;
		    		toggleErrorHint(data.res_msg, '#repeateName_errorHintAdd') ;
		    	}
			}
		});
	});
}

function memberIsActive(){
	 $.ajax({
			type : "get",
			url : basepath + "/travelerInfo/membersIsActive",
			data : {
			},
			async:false,
			dataType : "json",
			success : function(data) {
				if(data.isActive=='no'){
					window.location.href= memberServer+"/home"
					return;
				}
			}
		});
}

function formValidator(){
	var result=false;
	if($(".errorHint:visible").parent().length > 0){
		return result;
	}

	var phoneNum=$("#phoneNumAdd").val();
	validatePhoneNum(phoneNum);
	var email=$("#emailAdd").val();
	validateEmail(email);
	//证件
	validateCredits();

	validateAddressAdd();

	validateDetailAddressAdd();

	validateNames();

	checkRepeatName();

	if(validateEmail(email)  && validateAddressAdd()){
		result=true;
	}
	return result;
}

function validateFirstNameCn(){
	var firstName = $("#firstNameCnAdd").val();
	if(null == firstName || "" == firstName){
		toggleErrorHint("姓"+empty_hint_error,'#firstName_errorHintAdd') ;
	}
	if(cnNameReg.test(firstName)){
		var firstNamePinyin = Pinyin.GetQP($.trim(firstName)).toUpperCase();
		if(firstNamePinyin.length<=30){
			toggleErrorHint(disappear,'#firstName_errorHintAdd') ;
		}else{
			toggleErrorHint(" 姓:"+nameErrorHint,'#firstName_errorHintAdd') ;
			return false;
		}
	}else if(enNameReg.test(firstName)){
			toggleErrorHint(disappear,'#firstName_errorHintAdd') ;
	}else{
		toggleErrorHint(" 姓:"+nameErrorHint,'#firstName_errorHintAdd') ;
		return false;
	}
}

function validateLastNameCn(){
	var lastName = $("#lastNameCnAdd").val()
	if(null == lastName || "" == lastName){
		toggleErrorHint("名"+empty_hint_error,'#lastName_errorHintAdd') ;
	}
	if(cnNameReg.test(lastName)){
		var lastNamePinyin = Pinyin.GetQP($.trim(lastName)).toUpperCase();
		if(lastNamePinyin.length<=30){
			toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
		}else{
			toggleErrorHint(" 名:"+nameErrorHint,'#lastName_errorHintAdd') ;
			return false;
		}
	}else if(enNameReg.test(lastName)){
		toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
	}else{
		toggleErrorHint(" 名:"+nameErrorHint,'#lastName_errorHintAdd') ;
		return false;
	}

}

function validateLastNameEn(){

	var lastName =$("#lastNameEnAdd").val();
	if(null == lastName || "" == lastName){
		toggleErrorHint("lastName"+empty_hint_error,'#lastNameEn_errorHintAdd') ;
		return false;
	}else if(enNameReg.test(lastName)){
		toggleErrorHint(disappear,'#lastNameEn_errorHintAdd') ;
	}else{
		toggleErrorHint("lastName"+nameErrorHint,'#lastNameEn_errorHintAdd') ;
		return false;
	}
}

function validateFirstNameEn(){
	var firstName = $("#firstNameEnAdd").val();
	if(null == firstName || "" == firstName){
		toggleErrorHint("firstName "+empty_hint_error,'#firstNameEn_errorHintAdd') ;
		return false;
	}else if(enNameReg.test(firstName)){
		toggleErrorHint(disappear,'#firstNameEn_errorHintAdd') ;
	}else{
		toggleErrorHint("firstName"+enname_hint_error,'#firstNameEn_errorHintAdd') ;
		return false;
	}
}

function validateFirstNameCnEdit(){
	var firstName = $("#firstNameEdit").val();
	if(null == firstName || "" == firstName){
		toggleErrorHint("姓"+empty_hint_error,'#firstName_errorHintEdit') ;
	}
	if(cnNameReg.test(firstName)){
		var firstNamePinyin = Pinyin.GetQP($.trim(firstName)).toUpperCase();
		if(firstNamePinyin.length<=30){
			toggleErrorHint(disappear,'#firstName_errorHintEdit') ;
		}else{
			toggleErrorHint(" 姓:"+nameErrorHint,'#firstName_errorHintEdit') ;
			return false;
		}
	}else if(enNameReg.test(firstName)){
		toggleErrorHint(disappear,'#firstName_errorHintEdit') ;
	}else{
		toggleErrorHint(" 姓:"+nameErrorHint,'#firstName_errorHintEdit') ;
		return false;
	}
}

function validateLastNameCnEdit(){
	var lastName = $("#lastNameEdit").val()
	if(null == lastName || "" == lastName){
		toggleErrorHint("名"+empty_hint_error,'#lastName_errorHintEdit') ;
	}
	if(cnNameReg.test(lastName)){
		var lastNamePinyin = Pinyin.GetQP($.trim(lastName)).toUpperCase();
		if(lastNamePinyin.length<=30){
			toggleErrorHint(disappear,'#lastName_errorHintEdit') ;
		}else{
			toggleErrorHint(" 名:"+nameErrorHint,'#lastName_errorHintEdit') ;
			return false;
		}
	}else if(enNameReg.test(lastName)){
		toggleErrorHint(disappear,'#lastName_errorHintEdit') ;
	}else{
		toggleErrorHint(" 名:"+nameErrorHint,'#lastName_errorHintEdit') ;
		return false;
	}
}

function validateLastNameEnEdit(){
	var lastName =$("#lastNameEnEdit").val();
	toggleErrorHint(disappear,'#lastNameEn_errorHintEdit') ;
	if(null == lastName || "" == lastName){
		toggleErrorHint("lastName"+empty_hint_error,'#lastNameEn_errorHintEdit') ;
	}else if(!enNameReg.test(lastName)){
		toggleErrorHint("lastName"+enname_hint_error,'#lastNameEn_errorHintEdit') ;
	}
}

function validateFirstNameEnEdit(){
	var firstName = $("#firstNameEnEdit").val();
	toggleErrorHint(disappear,'#firstNameEn_errorHintEdit') ;
	if(null == firstName || "" == firstName){
		toggleErrorHint("firstName "+empty_hint_error,'#firstNameEn_errorHintEdit') ;
	}else if(!enNameReg.test(firstName)){
		toggleErrorHint("firstName"+enname_hint_error,'#firstNameEn_errorHintEdit') ;
	}
}

	function validateNames(){
		validateFirstNameCn();
		validateLastNameCn();
		validateLastNameEn();
		validateFirstNameEn();
	}

	function validateNamesEdit(){
		validateFirstNameCnEdit();
		validateLastNameCnEdit();
		validateLastNameEnEdit();
		validateFirstNameEnEdit();
	}


/**
 * 获取保存参数
 * @param selector
 * @returns params
 */
//获取保存参数
function  getParams (selector) {
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

	//地址
	formParams.province=$("#address_province  .active a").html();
	formParams.city=$("#address_city  .active a").html();
	formParams.district=$("#address_area  .active a").html();
	formParams.detailAddress=$("#address_detailAddressAdd").val();

	var firstName = $("#firstNameCnAdd").val();
	var lastName = $("#lastNameCnAdd").val();
	if(null!=firstName &&""!=firstName && null!=lastName && ""!=lastName ){
		if(cnNameReg.test(firstName) && cnNameReg.test(lastName)){
			formParams.travelerNameCn=firstName+lastName;
		}else{
			formParams.travelerNameCn = firstName+"/"+lastName;
		}

		formParams.travelerNameEn = $("#firstNameEnAdd").val()+"/"+$("#lastNameEnAdd").val();
	}

	return formParams;
};


/**
 * 新增页面blur校验
 */
function blurValidateAdd(){
	//手机号
	$("#phoneNumAdd").blur(function() {
		var phoneNum=$("#phoneNumAdd").val();
		validatePhoneNum(phoneNum);
	});

	//邮箱
	$("#emailAdd").blur(function() {
		var email=$("#emailAdd").val();
		if(email != ''){
			if(!isEmail(email)){
				toggleErrorHint(disappear,'#email_errorHintAdd') ;
				toggleErrorHint(email_hint_formaterror,'#email_errorHintAdd') ;
			}else{
				toggleErrorHint(disappear,'#email_errorHintAdd') ;
			}
		}else{
			toggleErrorHint(disappear,'#email_errorHintAdd') ;
		}

		validateEmail(email);
	});

	$(".travelerInfoTab").delegate("#firstNameCnAdd","blur",function(){
		checkRepeatName();
		var firstName = $(this).val();
		toggleErrorHint(disappear,'#firstName_errorHintAdd') ;
		toggleErrorHint(disappear,'#firstNameEn_errorHintAdd') ;
		toggleErrorHint(disappear,'#repeateName_errorHintAdd') ;
		if(null == firstName || "" == firstName){
			toggleErrorHint("姓"+empty_hint_error,'#firstName_errorHintAdd') ;
		}
		if(cnNameReg.test(firstName)){
			var firstNamePinyin = Pinyin.GetQP($.trim(firstName)).toUpperCase();
			if(firstNamePinyin.length>30){
				toggleErrorHint(" 姓:"+nameErrorHint,'#firstName_errorHintAdd') ;
			}else{
				$("#firstNameEnAdd").val(firstNamePinyin);   //英文名输入框自动填拼音
				toggleErrorHint(disappear,'#firstName_errorHintAdd') ;
				toggleErrorHint(disappear,'#repeateName_errorHintAdd') ;
			}
		}else if(enNameReg.test(firstName)){
			$("#firstNameEnAdd").val(firstName);
			toggleErrorHint(disappear,'#firstName_errorHintAdd') ;
			toggleErrorHint(disappear,'#repeateName_errorHintAdd') ;
		}else{
			toggleErrorHint(" 姓:"+nameErrorHint,'#firstName_errorHintAdd') ;
		}
	})

	$(".travelerInfoTab").delegate("#lastNameCnAdd","blur",function(){
		var lastName = $(this).val();
		toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
		toggleErrorHint(disappear,'#lastNameEn_errorHintAdd') ;
		toggleErrorHint(disappear,'#repeateName_errorHintAdd') ;
		if(null == lastName || "" == lastName){
    		toggleErrorHint("名"+empty_hint_error,'#lastName_errorHintAdd') ;
		}
		if(cnNameReg.test(lastName)){
			var lastNamePinyin = Pinyin.GetQP($.trim(lastName)).toUpperCase();
			if(lastNamePinyin.length>30){
				toggleErrorHint(" 名:"+nameErrorHint,'#lastName_errorHintAdd') ;
			}else{
				toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
				toggleErrorHint(disappear,'#repeateName_errorHintAdd') ;
				$("#lastNameEnAdd").val(lastNamePinyin);   //英文名输入框自动填拼音
			}
		}else if(enNameReg.test(lastName)){
				toggleErrorHint(disappear,'#lastName_errorHintAdd') ;
				toggleErrorHint(disappear,'#repeateName_errorHintAdd') ;
				$("#lastNameEnAdd").val(lastName);
		}else{
			toggleErrorHint(" 名:"+nameErrorHint,'#lastName_errorHintAdd') ;
		}
		checkRepeatName();
	})

$(".travelerInfoTab").delegate("#firstNameEnAdd","blur",function(){
		var firstName = $(this).val();
		toggleErrorHint(disappear,'#firstNameEn_errorHintAdd') ;
		if(null == firstName || "" == firstName){
			toggleErrorHint("firstName "+empty_hint_error,'#firstNameEn_errorHintAdd') ;
		}else if(!enNameReg.test(firstName)){
			toggleErrorHint("firstName"+enname_hint_error,'#firstNameEn_errorHintAdd') ;
		}


	})

	$(".travelerInfoTab").delegate("#lastNameEnAdd","blur",function(){
		var lastName = $(this).val();
		toggleErrorHint(disappear,'#lastNameEn_errorHintAdd') ;
		if(null == lastName || "" == lastName){
    		toggleErrorHint("lastName"+empty_hint_error,'#lastNameEn_errorHintAdd') ;
		}else if(!enNameReg.test(lastName)){
			toggleErrorHint("lastName"+enname_hint_error,'#lastNameEn_errorHintAdd') ;
		}
	})


	$(".travelerInfoTab.idTypeInfo").delegate(".number","blur",function(){
		    var creditNum = $(this).val();
		    var creditType = $(this).parent().find("span.activeFonts").html();
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
			var creditHintAdd=$(this).parents("td").siblings().find(".errorHint");
			showCreditValidateMsg(result,creditHintAdd);

			setValidateDateDisabledOrNotDisabled($(this),result);
		});

	$(".travelerInfoTab.idTypeInfo").delegate("li.active","click",function(){
		var creditType =$(this).find("a").html();
		var creditNum = $(this).parents("td").find(".number").val();
		var result=isCredit(creditType,creditNum);
		var creditHintAdd=$(this).parents("td").siblings().find(".errorHint");
		showCreditValidateMsg(result,creditHintAdd);
		setValidateDateDisabledOrNotDisabled($(this),result);

	})

    //  点击 dropdown 时，判断并生成对应的 类型结构
    $(".idTypeInfoEdit").delegate('.dropdown.id-type .dropdownBtn', 'click', function(event) {
        var $that = $(this);
        var result = ["身份证", "护照", "港澳通行证"];
        $that.parents("tr").siblings("tr:visible").each(function(){
            var curType = $(this).find("li.active a").html();
            result = _.without(result, curType);
        });
        $that.closest(".dropdown").find("ul.dropdown-menu").html(geneIdTypeList(result));
        addLiClickEvent();
    });

	$("#address_detailAddressAdd").blur(function(){
		validateDetailAddressAdd();
	});

}

function checkRepeatName(){

	var firstName=$("#firstNameCnAdd").val();
	var lastName = $("#lastNameCnAdd").val();
	var traveleNameCn = "";

	if(null != firstName && firstName != "" && null!=lastName && lastName!=""){
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
						toggleErrorHint(repeatename_hint_error,'#repeateName_errorHintAdd') ;
				}
			}
		});
	}

}

function setValidateDateDisabledOrNotDisabled(that,result){
	  if(null!=result && ""!=result){
		  that.parents("td").siblings().find(".datepicker").attr("disabled",true);
	  }
	  if(""==result){
		  that.parents("td").siblings().find(".datepicker").attr("disabled",false);
	  }
}



function validatePhoneNum(phoneNum){
	var result=true;
	if(null==phoneNum || ''==phoneNum){
		toggleErrorHint(disappear,'#phoneNum_errorHintAdd') ;
	}else{
		if( !isPhone(phoneNum)){
			toggleErrorHint(disappear,'#phoneNum_errorHintAdd') ;
			toggleErrorHint(mobile_hint_formaterror,'#phoneNum_errorHintAdd') ;
			result=false;
		}else{
			toggleErrorHint(disappear,'#phoneNum_errorHintAdd') ;
		}
	}
	return result;
}

function validateEmail(email){
	var result=false;
	if(null==email || ''==email ){
		toggleErrorHint(disappear,'#email_errorHintAdd') ;
		result = true;
	}else{
		if( !isEmail(email)){
			toggleErrorHint(disappear,'#email_errorHintAdd') ;
			toggleErrorHint(email_hint_formaterror,'#email_errorHintAdd') ;
		}else{
			toggleErrorHint(disappear,'#email_errorHintAdd') ;
			result = true;
		}
	}
	return result;
}


function validateCredits(){
	$(".addTrContent").parent().find("tr").each(
			function(i){
				if(i<$(".addTrContent").parent().find("tr").length-1){
					var creditType = $(this).find("a span ").html();
					var creditNum =  $(this).find(".number").val();
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
					var creditHintAdd=$(this).find(".errorHint");
					showCreditValidateMsg(result,creditHintAdd);
				}
			}
	);
}

function validateDetailAddressAdd(){
	var result = true;
	var detailAddressAdd = $("#address_detailAddressAdd").val();
	toggleErrorHint(disappear,'#detailAddress_errorHintAdd') ;
	if(null != detailAddressAdd && ""!=detailAddressAdd){
		if(!(isDetailAddress(detailAddressAdd))){
			toggleErrorHint(detailAddress_hint_error,'#detailAddress_errorHintAdd') ;
			result=false;
		}
	}
}

function validateDetailAddressEdit(){
	var result = true;
	var detailAddressEdit = $("#address_detailAddress_edit").val();
	toggleErrorHint(disappear,'#detailAddress_errorHint_edit') ;
	if(null != detailAddressEdit && ""!=detailAddressEdit){
		if(!(isDetailAddress(detailAddressEdit))){
			toggleErrorHint(detailAddress_hint_error,'#detailAddress_errorHint_edit') ;
			result=false;
		}
	}
}

function isDetailAddress(detailAddress){
	var streetReg = /^([(-|\_|\.)|a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|;|：|:|；|“|”|’]){1,50}$/;
	if(streetReg.test(detailAddress)){
		return true;
	}else{
		return false;
	}
}

function isCnName(travelerName){
	var r=/^[\u4e00-\u9fa5\\.\\|a-zA-Z]{2,20}$/;
	if(r.test(travelerName)){
		return true;
	}else{
		return false;
	}
}

function isEnName(travelerName){
	var r=/^[a-zA-Z\\.\\]{0,20}$/;
	if(r.test(travelerName)){
		return true;
	}else{
		return false;
	}
}

function isPhone(phoneNum){
	 var r= /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
	if(r.test(phoneNum)){
		return true;
	}else{
		return false;
	}
}


function isGangAoPass(gangAoPassNo){
		var reg=/^[a-zA-Z][0-9]{8}$/;
		 if(reg.test(gangAoPassNo) === false)
		   {
		       return  false;
		   }
	return true;
}

function isPassport(passport){
		var reg=/^[A-Za-z0-9]{1,20}$/;
		 if(reg.test(passport) === false)
		   {
		       return  false;
		   }
	return true;
}

function isEmail(email){
	var reg =/^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
	return email.match(reg) != null;
}

function isCredit(creditType,creditNum){
	var result="";
	if("" != creditNum){
		if("身份证"==creditType){
			if(!idCardValid(creditNum)){
				result="creditError";
			}
		}
		if("护照"==creditType){
			if(!isPassport(creditNum)){
				result="passportError";
			}
		}
		if("港澳通行证"==creditType){
			if(!isGangAoPass(creditNum)){
				result="gangaoError";
			}
		}
	}
	return result;
}

function idCardValid(creditNum){
	var idCardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	if(idCardReg.test(creditNum)==false){
		return false;
	}
	return true;
}

function showCreditValidateMsg(result,creditHintOfAdd){
	toggleErrorHint(disappear,creditHintOfAdd) ;
	if("creditError"==result){
		ret=false;
		toggleErrorHint(disappear,creditHintOfAdd) ;
		toggleErrorHint(creditCard_hint_emptyerror,creditHintOfAdd) ;
	}else if("passportError"==result){
		ret=false;
		toggleErrorHint(disappear,creditHintOfAdd) ;
		toggleErrorHint(passport_hint_emptyerror,creditHintOfAdd) ;
	}else if("gangaoError"==result){
		ret=false;
		toggleErrorHint(disappear,creditHintOfAdd) ;
		toggleErrorHint(gangaopass_hint_emptyerror,creditHintOfAdd) ;
	}
}

function validateAddressAdd(){
	var result = false;
	var address_province = $("#address_province .active a").html();
	var address_city = $("#address_city .active a").html();
	var address_area =$("#address_area .active a").html();

	toggleErrorHint("请选择地址",'#address_errorHintAdd') ;
	if(null != address_province && address_province != "" && address_city != null && address_city != "" && address_area != null && address_area !=""){
		toggleErrorHint(disappear,'#address_errorHintAdd') ;
		result= true;
	}
	if(null == address_province && address_city == null && address_area == null ){
		toggleErrorHint(disappear,'#address_errorHintAdd') ;
		result = true;
	}
	return result;
}

function validateAddressEdit(){
	var result = false;
	var address_province = $("#address_province_edit .active a").html();
	var address_city = $("#address_city_edit .active a").html();
	var address_area =$("#address_area_edit .active a").html();

	toggleErrorHint("请选择地址",'#address_errorHint_edit') ;
	if(null != address_province && address_province != "" && address_city != null && address_city != "" && address_area != null && address_area !=""){
		toggleErrorHint(disappear,'#address_errorHint_edit') ;
		result = true;
	}
	if(null == address_province && address_city == null && address_area == null ){
		toggleErrorHint(disappear,'#address_errorHint_edit') ;
		result = true;
	}
	return result;
}


$(function(){
	$("#address_area").delegate("ul.dropdown-menu li a","click",function(){
		toggleErrorHint(disappear,'#address_errorHintAdd') ;
	})
})
