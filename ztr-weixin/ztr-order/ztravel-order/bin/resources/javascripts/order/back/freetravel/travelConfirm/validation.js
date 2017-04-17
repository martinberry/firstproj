
var contactorReg = /^([a-zA-Z0-9|\u4e00-\u9fa5]){1,40}$/;
var phoneReg = /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
var emailReg = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
var addressReg = /^([(-|\_|\.)|a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|;|：|:|；|“|”|’]){1,100}$/;
var passengerNameReg = /^([a-zA-Z|\u4e00-\u9fa5|\.|\/]){1,40}$/;
var pyNameReg = /^[a-zA-Z]{1,19}[\/]{1}[a-zA-Z]{1,20}$/;//长度为40个字符内的中文名拼音
var idCardReg = /(^\d{15}$)|(^\d{17}(\d|X|x)$)/;
var passPortReg = /^[A-Za-z0-9]{1,20}$/;
var hkPassReg = /^[a-zA-Z]{1}[0-9]{8}$/;//港澳通行证，字母开头，后面接8位数字
var enNameReg = /^[a-zA-z]{1,30}$/;//30个字符以内的英文名
var chNameReg = /^[\u4E00-\u9FA5]{1,20}$/;

$(function(){

    $('input[name="contactor"]').bind("change", function(){
    	var val = $.trim($(this).val());
    	if (!contactorReg.test(val)) {
    		$(".contactor-error").show();
    	}else{
    		$(".contactor-error").hide();
    	}
    });

    $('input[name="phone"]').bind("change", function(){
    	var val = $.trim($(this).val());
    	if (!phoneReg.test(val)) {
    		$(".phone-error").show();
    	}else{
    		$(".phone-error").hide();
    	}
    });

    $('input[name="email"]').bind("change", function(){
    	var val = $.trim($(this).val());
    	if (!emailReg.test(val) || val.length>50) {
    		$(".email-error").show();
    	}else{
    		$(".email-error").hide();
    	}
    });

    $('input[name="address"]').bind("change", function(){
    	var val = $.trim($(this).val());
    	if (!addressReg.test(val)) {
    		$(".address-error").show();
    	}else{
    		$(".address-error").hide();
    	}
    });

    $(".editTouristInfoContent").delegate('input[name="passengerName"]', "change", function(){
    	$(this).siblings(".name-error").hide();
    	var val = $.trim($(this).val());
    	if (val != '' && escape(val).indexOf("%u")<0) {
    		if (!pyNameReg.test(val)) {
    			$(this).siblings(".name-error").html('填写英文姓名请在姓与名之间用"/"分隔，例如：zhang/san');
        		$(this).siblings(".name-error").show();
    		}
    	}else if (!passengerNameReg.test(val)) {
    		$(this).siblings(".name-error").html("请按照所持有效证件上的姓名填写");
    		$(this).siblings(".name-error").show();
    	}
    });

    $(".editTouristInfoContent").delegate('input[name="credentialNum"]', "blur", function(){
    	$(this).siblings(".credentialNum-error").hide();
//    	var val = $.trim($(this).siblings('.dropdown').children('ul').children('.active').attr('data-val'));
    	var type = $.trim($(this).siblings('.dropdown').find('.credentialType').attr('data-val'));
    	var val = $.trim($(this).val());
    	if (type=="IDCARD") {
    		if (!idCardReg.test(val)) {
        		$(this).siblings(".credentialNum-error").show();
    		}
    	}else if (type=="PASSPORT") {
    		if (!passPortReg.test(val)) {
        		$(this).siblings(".credentialNum-error").show();
    		}
    	}else if (type=="GANGAOPASS") {
    		if (!hkPassReg.test(val)) {
        		$(this).siblings(".credentialNum-error").show();
    		}
    	}
    });

    $(".editTouristInfoContent").delegate('input[name="firstName"]', "change", function(){
    	var firstName = $.trim($(this).val());
    	var lastName = $.trim($(this).siblings('input[name="lastName"]').val());
    	if (chNameReg.test(firstName) && chNameReg.test(lastName)) {
    		$(this).siblings(".name-error").hide();
    	}else if(enNameReg.test(firstName) && enNameReg.test(lastName)){
    		$(this).siblings(".name-error").hide();
    	}else{
    		$(this).siblings(".name-error").show();
    		}
    });

    $(".editTouristInfoContent").delegate('input[name="lastName"]', "change", function(){
    	var firstName = $.trim($(this).siblings('input[name="firstName"]').val());
    	var lastName = $.trim($(this).val());
    	if (chNameReg.test(firstName) && chNameReg.test(lastName)) {
    		$(this).siblings(".name-error").hide();
    	}else if(enNameReg.test(firstName) && enNameReg.test(lastName)){
    		$(this).siblings(".name-error").hide();
    	}else{
    		$(this).siblings(".name-error").show();
    		}
    });

    $(".editTouristInfoContent").delegate('input[name="firstNameEn"]', "change", function(){
    	var firstNameEn = $.trim($(this).val());
    	var lastNameEn = $.trim($(this).siblings('input[name="lastNameEn"]').val());
    	if(enNameReg.test(firstNameEn) && enNameReg.test(lastNameEn)){
    		$(this).siblings(".nameEn-error").hide();
    	}else{
    		$(this).siblings(".nameEn-error").show();
    		}
    });

    $(".editTouristInfoContent").delegate('input[name="lastNameEn"]', "change", function(){
    	var firstNameEn = $.trim($(this).siblings('input[name="firstNameEn"]').val());
    	var lastNameEn = $.trim($(this).val());
    	if(enNameReg.test(firstNameEn) && enNameReg.test(lastNameEn)){
    		$(this).siblings(".nameEn-error").hide();
    	}else{
    		$(this).siblings(".nameEn-error").show();
    		}
    });

    $(".editTouristInfoContent").delegate('input[name="birthday"]', "change", function(){
    	var val = $.trim($(this).val());
    	if (val=='') {
    		$(this).siblings(".birthday-error").show();
    	}else{
    		$(this).siblings(".birthday-error").hide();
    	}
    });

    $(".editTouristInfoContent").delegate('input[name="deadLine"]', "change", function(){
    	var val = $.trim($(this).val());
    	if (val=='') {
    		$(this).siblings(".deadLine-error").show();
    	}else{
    		$(this).siblings(".deadLine-error").hide();
    	}
    });

});

