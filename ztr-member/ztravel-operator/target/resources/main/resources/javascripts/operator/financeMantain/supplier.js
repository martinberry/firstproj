var empty_hint_Illegal="不能为空";
var disappear ="";
$(function(){
	submitFunc();

	$(".searchTab").delegate(".lightBlueBtn","click",function(){
		searchByParams();
	})
	

	$("#addSupply").delegate("#addConfirm","click",function(){
		if(!validateAddForm() || $(".errorHint:visible").parent().length > 0){
			return;
		}
		$("#addForm").submit();
	})

	$("#edtSupply").delegate("#editConfirm","click",function(){
		if(validateEditForm()  && !($(".errorHint:visible").parent().length > 0)){
			$("#editForm").submit();
		}

	})

	initExportExcel("#search-form", "#exportExcel", basepath + "/financeMantain/opera/supplier/exportExcel");

	blurValidateAdd();

	blurValidateEdit();

	autoCompleteSupplierName();
	
    $(".operationDiv").click(function(){
    	clearAddForm();
        $("#addSupply").modal("show");
    });
    
})

function blurSupplierName(){
	$("#supplierName").blur(function(){
		if($("#supplierName").val() != null && $("#supplierName").val() !=""){
			$("#supplierName").val($("#supplierName").val().replace(/</g,"&lt").replace(/>/g,""));
		}
	})
}

function clearAddForm(){
	$("#supplierNameAdd").val("");
	$("#bussinessContacts").val("");
	$("#accountBank").val("");
	$("#phone").val("");
	$("#accountName").val("");
	$("#fax").val("");
	$("#account").val("");
	$("#email").val("");
	$("#secMenuAdd li").first().find("a").click();
	$("#innerContacts").val("");
	
	toggleErrorHint(disappear,'.errorHint') ;
	
}

//提交查询请求
function submitFunc() {
	var criteria = buildSearchCriteria();
	criteria.pageNo = $("input[name=pageNo]").val();
	criteria.pageSize=$("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../supplier/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			var dataArray = result.split("<-split->");
			var tableData = dataArray[0];
			var paginationData = dataArray[1];
			$(".commonTab  tbody").html(tableData);
			$("#searchField").html(paginationData);
		},
	})
}

function searchByParams(){

	var criteria = buildSearchCriteria();
	criteria.pageNo = 1;
	criteria.pageSize=$("input[name=pageSize]").val();

	$.ajax({
		type : "POST",
		url : "../supplier/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			var dataArray = result.split("<-split->");
			var tableData = dataArray[0];
			var paginationData = dataArray[1];
			$(".commonTab  tbody").html(tableData);
			$("#searchField").html(paginationData);
		},
	})
}

function buildSearchCriteria(){
	var criteria = {};
	var supplierName = $("#supplierName").val();
	if(null!=supplierName && ""!=supplierName){
		criteria.supplierName =  supplierName;
	}

	return criteria;
}
function initExportExcel(formSelector, buttonSelector, url) {
	$('body').delegate(buttonSelector, "click", function() {
		$(formSelector).attr("method", "post");
		var oldUrl = $(formSelector).attr("action");
		$(formSelector).attr("action", url);
		$(formSelector).submit();
		$(formSelector).attr("action", oldUrl);
	});
}

function validateAddForm(){
	var result = true;
	var supplierName =$("#supplierNameAdd").val();
	var bussinessContacts = $("#bussinessContacts").val();
	var accountBank = $("#accountBank").val();
	var phone = $("#phone").val();
	var accountName = $("#accountName").val();
	var account =$("#account").val();
	var fax = $("#fax").val();
	var email =$("#email").val();

	if(!validateSupplierName(supplierName)){
		result=false;
	}
	if(!validatebussinessContacts(bussinessContacts)){
		result=false;
	}
	if(!validateaccountBank(accountBank)){
		result=false;
	}
	if(!validateaccount(account)){
		result=false;
	}
	if(!validateaccountName(accountName)){
		result=false;
	}
	if(!validatephone(phone)){
		result=false;
	}
	if(!validateemail(email)){
		result=false;
	}
	if(!validatefax(fax)){
		result=false;
	}
	return result;
}

function blurValidateAdd(){
	$("#supplierNameAdd").blur(function() {
		if($("#supplierNameAdd").val() != null && $("#supplierNameAdd").val() !=""){
			$("#supplierNameAdd").val($("#supplierNameAdd").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var supplierName=$("#supplierNameAdd").val();
		validateSupplierName(supplierName);
	});
	$("#bussinessContacts").blur(function() {
		if($("#bussinessContacts").val() != null && $("#bussinessContacts").val() !=""){
			$("#bussinessContacts").val($("#bussinessContacts").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var bussinessContacts=$("#bussinessContacts").val();
		validatebussinessContacts(bussinessContacts);
	});
	$("#accountBank").blur(function() {
		if($("#accountBank").val() != null && $("#accountBank").val() !=""){
			$("#accountBank").val($("#accountBank").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var accountBank=$("#accountBank").val();
		validateaccountBank(accountBank);
	});
	$("#accountName").blur(function() {
		if($("#accountName").val() != null && $("#accountName").val() !=""){
			$("#accountName").val($("#accountName").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var accountName=$("#accountName").val();
		validateaccountName(accountName);
	});
	$("#phone").blur(function() {
		if($("#phone").val() != null && $("#phone").val() !=""){
			$("#phone").val($("#phone").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var phone=$("#phone").val();
		validatephone(phone);
	});
	$("#account").blur(function() {
		if($("#account").val() != null && $("#account").val() !=""){
			$("#account").val($("#account").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var account=$("#account").val();
		validateaccount(account);
	});
	$("#fax").blur(function() {
		if($("#fax").val() != null && $("#fax").val() !=""){
			$("#fax").val($("#fax").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var fax=$("#fax").val();
		validatefax(fax);
	});
	$("#email").blur(function() {
		if($("#email").val() != null && $("#email").val() !=""){
			$("#email").val($("#email").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var email=$("#email").val();
		validateemail(email);
	});
	$("#innerContacts").blur(function() {
		if($("#innerContacts").val() != null && $("#innerContacts").val() !=""){
			$("#innerContacts").val($("#innerContacts").val().replace(/</g,"&lt").replace(/>/g,""));
		}
	});
}


function toggleErrorHint(hint,$trigger){
	if(typeof($trigger) == undefined || $trigger == null){
		$trigger = ".errorHint";
	}
	if(hint == ''){
		$($trigger).parent().css("display","none") ;
	}else{
		$($trigger).html(hint) ;
		$($trigger).parent().css("display","") ;
	}
}

function validateSupplierName(supplierName){
	var result = true;
	toggleErrorHint(disappear,'#supplierName_errorHintAdd') ;
	if(null==supplierName || ''==supplierName){
		toggleErrorHint(empty_hint_Illegal,'#supplierName_errorHintAdd') ;
		result = false;
	}
	if(null != supplierName && "" !=supplierName){
		if(isSupplierNameExisted(supplierName)){
			result = false;
		}
	}
	return result;
}
function isSupplierNameExisted(supplierName){
	var result = false;
	toggleErrorHint(disappear,'#supplierName_errorHintAdd') ;
	$.ajax({
		type : "GET",
		url : "../supplier/isSupplierNameExisted",
		data :{
			supplierName:supplierName
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.result='successed'){
				if(data.msg=='existed'){

					toggleErrorHint("该供应商已存在",'#supplierName_errorHintAdd') ;
					result = true;
				}
			}

		}
	   })

	   return result;
}

function isSupplierNameExistedEdit(supplierName){
	var result = false;
	toggleErrorHint(disappear,'#supplierName_errorHintEdit') ;
	$.ajax({
		type : "GET",
		url : "../supplier/isSupplierNameExisted",
		data :{
			supplierName:supplierName
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.result='successed'){
				if(data.msg=='existed'){

					toggleErrorHint("该供应商已存在",'#supplierName_errorHintEdit') ;
					result = true;
				}
			}

		}
	   })

	   return result;
}

function validatebussinessContacts(bussinessContacts){
	var result=true;
	toggleErrorHint(disappear,'#bussinessContacts_errorHintAdd') ;
	if(null==bussinessContacts || ''==bussinessContacts){
		toggleErrorHint(empty_hint_Illegal,'#bussinessContacts_errorHintAdd') ;
		result=false;
	}
	return result;
}
function validateaccountBank(accountBank){
	var result=true;
	toggleErrorHint(disappear,'#accountBank_errorHintAdd') ;
	if(null==accountBank || ''==accountBank){
		toggleErrorHint(empty_hint_Illegal,'#accountBank_errorHintAdd') ;
		result=false;
	}
	return result;
}
function validateaccountName(accountName){
	var result=true;
	toggleErrorHint(disappear,'#accountName_errorHintAdd') ;
	if(null==accountName || ''==accountName){
		toggleErrorHint(empty_hint_Illegal,'#accountName_errorHintAdd') ;
		result=false;
	}
	return result;
}
function validateaccount(account){
	var result=true;
	toggleErrorHint(disappear,'#account_errorHintAdd') ;
	if(null==account || ''==account){
		toggleErrorHint(empty_hint_Illegal,'#account_errorHintAdd') ;
		result=false;
	}
	return result;
}
function validatephone(phone){
	var result=true;
	toggleErrorHint(disappear,'#phone_errorHintAdd') ;
	if(null==phone || ''==phone){
		toggleErrorHint(empty_hint_Illegal,'#phone_errorHintAdd') ;
		result=false;
	}else{
		if(!isPhone(phone)){
			toggleErrorHint("电话格式不正确",'#phone_errorHintAdd') ;
			result=false;
		}
	}
	return result;
}
function validatefax(fax){
	var result=true;
	toggleErrorHint(disappear,'#fax_errorHintAdd') ;
	if(null!=fax && ''!=fax && !isFax(fax)){
		toggleErrorHint("传真格式不正确",'#fax_errorHintAdd') ;
		result=false;
	}
	return result;
}
function validateemail(email){
	var result=true;
	toggleErrorHint(disappear,'#email_errorHintAdd') ;
	if(null!=email && ''!=email){
		if(!isEmail(email)){
			toggleErrorHint("电邮格式不正确",'#email_errorHintAdd') ;
			result=false;
		}

	}
	return result;
}

function isPhone(phoneNum){
//	 var r= /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
	var r =/^[0-9]{1,20}$/
	if(r.test(phoneNum)){
		return true;
	}else{
		return false;
	}
}

function isEmail(email){
	var isEmail = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
	return isEmail.test(email) ;
}

function isFax(fax){
	var reg=/^(\d{3,4}-)?\d{7,8}$/;
	return fax.match(reg) !=null;
}

function validateEditForm(){
	var result = true;
	var supplierName =$("#supplierNameEdit").val();
	var bussinessContacts = $("#bussinessContactsEdit").val();
	var accountBank = $("#accountBankEdit").val();
	var phone = $("#phoneEdit").val();
	var accountName = $("#accountNameEdit").val();
	var account =$("#accountEdit").val();
	var fax = $("#faxEdit").val();
	var email =$("#emailEdit").val();

	if(!validateSupplierNameEdit(supplierName)){
		result=false;
	}
	if(!validatebussinessContactsEdit(bussinessContacts)){
		result=false;
	}
	if(!validateaccountBankEdit(accountBank)){
		result=false;
	}
	if(!validateaccountEdit(account)){
		result=false;
	}
	if(!validateaccountNameEdit(accountName)){
		result=false;
	}
	if(!validatephoneEdit(phone)){
		result=false;
	}
	if(!validateemailEdit(email)){
		result=false;
	}
	if(!validatefaxEdit(fax)){
		result=false;
	}
	return result;
}

function blurValidateEdit(){
	$("#supplierNameEdit").blur(function() {
		if($("#supplierNameEdit").val() != null && $("#supplierNameEdit").val() !=""){
			$("#supplierNameEdit").val($("#supplierNameEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var supplierName=$("#supplierNameEdit").val();
		validateSupplierNameEdit(supplierName);
	});
	$("#bussinessContactsEdit").blur(function() {
		if($("#bussinessContactsEdit").val() != null && $("#bussinessContactsEdit").val() !=""){
			$("#bussinessContactsEdit").val($("#bussinessContactsEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var bussinessContacts=$("#bussinessContactsEdit").val();
		validatebussinessContactsEdit(bussinessContacts);
	});
	$("#accountBankEdit").blur(function() {
		if($("#accountBankEdit").val() != null && $("#accountBankEdit").val() !=""){
			$("#accountBankEdit").val($("#accountBankEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var accountBank=$("#accountBankEdit").val();
		validateaccountBankEdit(accountBank);
	});
	$("#accountNameEdit").blur(function() {
		if($("#accountNameEdit").val() != null && $("#accountNameEdit").val() !=""){
			$("#accountNameEdit").val($("#accountNameEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var accountName=$("#accountNameEdit").val();
		validateaccountNameEdit(accountName);
	});
	$("#phoneEdit").blur(function() {
		if($("#phoneEdit").val() != null && $("#phoneEdit").val() !=""){
			$("#phoneEdit").val($("#phoneEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var phone=$("#phoneEdit").val();
		validatephoneEdit(phone);
	});
	$("#accountEdit").blur(function() {
		if($("#accountEdit").val() != null && $("#accountEdit").val() !=""){
			$("#accountEdit").val($("#accountEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var account=$("#accountEdit").val();
		validateaccountEdit(account);
	});
	$("#faxEdit").blur(function() {
		if($("#faxEdit").val() != null && $("#faxEdit").val() !=""){
			$("#faxEdit").val($("#faxEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var fax=$("#faxEdit").val();
		validatefaxEdit(fax);
	});
	$("#emailEdit").blur(function() {
		if($("#emailEdit").val() != null && $("#emailEdit").val() !=""){
			$("#emailEdit").val($("#emailEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
		var email=$("#emailEdit").val();
		validateemailEdit(email);
	});
	$("#innerContactsEdit").blur(function() {
		if($("#innerContactsEdit").val() != null && $("#innerContactsEdit").val() !=""){
			$("#innerContactsEdit").val($("#innerContactsEdit").val().replace(/</g,"&lt").replace(/>/g,""));
		}
	});
	
	
}

function validateSupplierNameEdit(supplierName){
	var result=true;
	var supplierNameOrigin = $("#supplierNameOrigin").val();
	toggleErrorHint(disappear,'#supplierName_errorHintEdit') ;
	if(null==supplierName || ''==supplierName){
		toggleErrorHint(empty_hint_Illegal,'#supplierName_errorHintEdit') ;
		result=false;
	}
	if(null != supplierName && ""!=supplierName && supplierName != supplierNameOrigin){
		if(isSupplierNameExistedEdit(supplierName)){
			result = false;
			toggleErrorHint("该供应商已存在",'#supplierName_errorHintEdit') ;
		}
	}
	return result;
}
function validatebussinessContactsEdit(bussinessContacts){
	var result=true;
	toggleErrorHint(disappear,'#bussinessContacts_errorHintEdit') ;
	if(null==bussinessContacts || ''==bussinessContacts){
		toggleErrorHint(empty_hint_Illegal,'#bussinessContacts_errorHintEdit') ;
		result=false;
	}
	return result;
}
function validateaccountBankEdit(accountBank){
	var result=true;
	toggleErrorHint(disappear,'#accountBank_errorHintEdit') ;
	if(null==accountBank || ''==accountBank){
		toggleErrorHint(empty_hint_Illegal,'#accountBank_errorHintEdit') ;
		result=false;
	}
	return result;
}
function validateaccountNameEdit(accountName){
	var result=true;
	toggleErrorHint(disappear,'#accountName_errorHintEdit') ;
	if(null==accountName || ''==accountName){
		toggleErrorHint(empty_hint_Illegal,'#accountName_errorHintEdit') ;
		result=false;
	}
	return result;
}
function validateaccountEdit(account){
	var result=true;
	toggleErrorHint(disappear,'#account_errorHintEdit') ;
	if(null==account || ''==account){
		toggleErrorHint(empty_hint_Illegal,'#account_errorHintEdit') ;
		result=false;
	}
	return result;
}
function validatephoneEdit(phone){
	var result=true;
	toggleErrorHint(disappear,'#phone_errorHintEdit') ;
	if(null==phone || ''==phone){
		toggleErrorHint(empty_hint_Illegal,'#phone_errorHintEdit') ;
		result=false;
	}else{
		if(!isPhone(phone)){
			toggleErrorHint("电话格式不正确",'#phone_errorHintEdit') ;
			result=false;
		}
	}
	return result;
}
function validatefaxEdit(fax){
	var result=true;
	toggleErrorHint(disappear,'#fax_errorHintEdit') ;
	if(null!=fax && ''!=fax && !isFax(fax)){
		toggleErrorHint("传真格式不正确",'#fax_errorHintEdit') ;
		result=false;
	}
	return result;
}
function validateemailEdit(email){
	var result=true;
	toggleErrorHint(disappear,'#email_errorHintEdit') ;
	if(null!=email && ''!=email){
		if(!isEmail(email)){
			toggleErrorHint("电邮格式不正确",'#email_errorHintEdit') ;
			result=false;
		}

	}
	return result;
}

/**
 * 供应商姓名自动补全
 */
function autoCompleteSupplierName(){
	$("#supplierName").typeahead({
	    source: function (query, process) {
	    	$((($(this)[0]).$element)[0]).val($((($(this)[0]).$element)[0]).val().replace(/</g,"&lt").replace(/>/g,""));
	        var parameter = {query: query};
	        $.post(basepath + "/financeMantain/opera/supplier/supplierNameAutoComplete", parameter, function (data) {
	            process(data);
	        });
	    }
	});
}