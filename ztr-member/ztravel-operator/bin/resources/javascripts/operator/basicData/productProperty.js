var productThemeReg = /^[a-zA-Z\d\u4E00-\u9FA5\s]{1,10}$/;
var productThemeErrorHint = "产品主题限制每行10个以内中文、字母、数字";
var productTagReg = /^[a-zA-Z\d\u4E00-\u9FA5\s]{1,10}$/;
var productTagErrorHint = "产品标签限制每行10个以内中文、字母、数字";

$(function(){
	$(".leftClassifyBtn .modifiedTheme,.leftClassifyBtn .modifiedLabel").click(function(){
		$(this).hide();
		$(this).siblings(".saveCancelBtn").show();
		$(this).parents(".leftClassifyBtn").siblings(".rightClassifyInfo").find(".commonTextarea").removeAttr("readonly","readonly");
	});

	$(".saveCancelBtn .gray-bbbButton").click(function(){
		$(this).parents(".saveCancelBtn").hide();
		$(this).parents(".saveCancelBtn").siblings(".modifiedLabel,.modifiedTheme").show();
		$(this).parents(".leftClassifyBtn").siblings(".rightClassifyInfo").find(".commonTextarea").attr("readonly","readonly");
		location.reload(true);
	});

	//保存产品主题
	$("#saveThemesBtn").click(function(){
		var themes = $("#themeTextarea").val();
		themes = $.trim(themes);
		var themesArray = themes.split("\n");
		for(var i in themesArray){
			if( !isThemeValid(themesArray[i]) ){
				alert(productThemeErrorHint);
				return;
			}
		}
		$.ajax({
			type : "POST",
			url : basepath + "/operation/basicData/saveProductTheme",
			data : themes,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success : function(resp) {
				if( resp.res_code == "SO_OPER_1007" ){
					showSuccessMessage(resp.res_msg);
				}else if( resp.res_code == "FO_OPER_1008" ){
					showFailMessage(resp.res_msg);
				}
			}
		});
	});

	//保存产品标签
	$("#saveTagsBtn").click(function(){
		var tags = $("#tagTextarea").val();
		tags = $.trim(tags);
		var tagsArray = tags.split("\n");
		for(var i in tagsArray){
			if( !isTagValid(tagsArray[i]) ){
				alert(productTagErrorHint);
				return;
			}
		}
		$.ajax({
			type : "POST",
			url : basepath + "/operation/basicData/saveProductTag",
			data : tags,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success : function(resp) {
				if( resp.res_code == "SO_OPER_1009" ){
					showSuccessMessage(resp.res_msg);
				}else if( resp.res_code == "FO_OPER_1010" ){
					showFailMessage(resp.res_msg);
				}
			}
		});
	});

	$("#messageDlg").on("hide.bs.modal", function(e){
		location.reload(true);
	});

});

function showSuccessMessage(message){
	$(".popupContainer").children("i").removeClass("warnIcon");
	$(".popupContainer").children("i").addClass("passIcon");
	$(".popupContainer-fonts").text(message);
	$("#messageDlg").modal("show");
}

function showFailMessage(message){
	$(".popupContainer").children("i").removeClass("passIcon");
	$(".popupContainer").children("i").addClass("warnIcon");
	$(".popupContainer-fonts").text(message);
	$("#messageDlg").modal("show");
}

function isThemeValid(theme){
	if( theme != "" && !productThemeReg.test(theme) )
		return false;
	else
		return true;
}

function isTagValid(tag){
	if( tag != "" && !productTagReg.test(tag) )
		return false;
	else
		return true;
}