var validFileExtArray = new Array("xls", "xlsx");
var invalidFileTypeErrorHint = "只能上传Excel文件";
var fileMaxSize = 2 * 1024 * 1024;
var exceedMaxSizeErrorHint = "上传的文件不能超过2M";

$(function(){
	//上传昵称AB库
	$("#abLibInputer").change(function(){
		//检查文件类型
		if( !isFileTypeValid(this) ){
			showFailMessage(invalidFileTypeErrorHint);
			$("#abLibInputer").val("");
			return;
		}
		//检查文件大小
		var fileSize = this.files[0].size;
		if( fileSize > fileMaxSize ){
			showFailMessage(exceedMaxSizeErrorHint);
			return;
		}
		$("#abLibForm").ajaxSubmit({
			success: function(resp){
				if( resp.res_code == "SO_OPER_1011" ){
					showSuccessMessage(resp.res_msg);
				}else if( resp.res_code == "FO_OPER_1012" ){
					showFailMessage(resp.res_msg);
				}
				$("#abLibInputer").val("");
			}
		});
	});

	//上传国籍
	$("#countryInputer").change(function(){
		//检查文件类型
		if( !isFileTypeValid(this) ){
			showFailMessage(invalidFileTypeErrorHint);
			$("#countryInputer").val("");
			return;
		}
		//检查文件大小
		var fileSize = this.files[0].size;
		if( fileSize > fileMaxSize ){
			showFailMessage(exceedMaxSizeErrorHint);
			return;
		}
		$("#countryForm").ajaxSubmit({
			success: function(resp){
				if( resp.res_code == "SO_OPER_1013" ){
					showSuccessMessage(resp.res_msg);
				}else if( resp.res_code == "FO_OPER_1014" ){
					showFailMessage(resp.res_msg);
				}
				$("#countryInputer").val("");
			}
		});
	});

});

function isFileTypeValid(fileInputer){
	//var fileType = fileInputer.files[0].type;
	var fileName = fileInputer.files[0].name;
	var fileExt = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length);
	for(var i in validFileExtArray){
		if( fileExt == validFileExtArray[i] ){
			return true;
		}else{
			continue;
		}
	}
	return false;
}

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