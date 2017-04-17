var departurePlaceReg = /^[a-zA-Z\d\u4E00-\u9FA5\s]{1,10}$/;
var departurePlaceErrorHint = "出发地每行限制10个字符以内中文、字母、数字";
var validFileExtArray = new Array("xls", "xlsx");
var invalidFileTypeErrorHint = "只能上传Excel文件";
var fileMaxSize = 2 * 1024 * 1024;
var exceedMaxSizeErrorHint = "上传的文件不能超过2M";

$(function(){
	$(".leftClassifyBtn .modifiedPlaceDeparture").click(function(){
		$(this).hide();
		$(this).siblings(".saveCancelBtn").show();
		$(this).parents(".leftClassifyBtn").siblings(".rightClassifyInfo").find(".commonTextarea").removeAttr("readonly","readonly");
	});

	$(".saveCancelBtn .gray-bbbButton").click(function(){
		$(this).parents(".saveCancelBtn").hide();
		$(this).parents(".saveCancelBtn").siblings(".modifiedPlaceDeparture").show();
		$(this).parents(".leftClassifyBtn").siblings(".rightClassifyInfo").find(".commonTextarea").attr("readonly","readonly");
		location.reload(true);
	});

	$(".placeList .placeTab th").click(function(){
		$(this).addClass("current");
		$(this).siblings("th").removeClass("current");
		$(this).parents("tr").siblings("tr").find("th").removeClass("current");
	});

	//保存出发地
	$("#saveDeparturePlaceBtn").click(function(){
		var departurePlaces = $("#departureTextarea").val();
		departurePlaces = $.trim(departurePlaces);
		var departPlaceArray = departurePlaces.split("\n");
		for(var i in departPlaceArray){
			if( !isDeparturePlaceValid(departPlaceArray[i]) ){
				alert(departurePlaceErrorHint);
				return;
			}
		}
		$.ajax({
			type : "POST",
			url : basepath + "/operation/basicData/saveDeparturePlace",
			data : departurePlaces,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success : function(resp) {
				if( resp.res_code == "SO_OPER_1001" ){
					showSuccessMessage(resp.res_msg);
				}else if( resp.res_code == "FO_OPER_1002" ){
					showFailMessage(resp.res_msg);
				}
			}
		});
	});

	//上传目的地
	$("#destInputer").change(function(){
		//检查文件类型
		if( !isFileTypeValid(this) ){
			showFailMessage(invalidFileTypeErrorHint);
			return;
		}
		//检查文件大小
		var fileSize = this.files[0].size;
		if( fileSize > fileMaxSize ){
			showFailMessage(exceedMaxSizeErrorHint);
			return;
		}
		$("#destForm").ajaxSubmit({
			success: function(resp){
				if( resp.res_code == "SO_OPER_1003" ){
					showSuccessMessage(resp.res_msg);
				}else if( resp.res_code == "FO_OPER_1004" ){
					showFailMessage(resp.res_msg);
				}
				$("#destInputer").val("");
			}
		});
	});

	//设置默认目的地
	$(".setDefault").click(function(){
		var defaultDest = $(".placeTab tr .current").text();
		if( defaultDest != "" ){
			$.ajax({
				type : "POST",
				url : basepath + "/operation/basicData/setDefaultDestination",
				data : defaultDest,
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "json",
				success : function(resp) {
					if( resp.res_code == "SO_OPER_1005" ){
						$("#defaultDest").text(defaultDest);
						$(".defaultVal").show();
					}else if( resp.res_code == "FO_OPER_1006" ){
						showFailMessage(resp.res_msg);
					}
				}
			});
		}
//		if($(".placeTab tr .current").html() != ""){
//			$(this).parents("div").siblings(".defaultVal").find(".fontsWidth").text(defaultDest);
//			$(".defaultVal").show();
//		}
	});

	//删除默认目的地
	$(".closeIcon").click(function(){
		$.ajax({
			type : "POST",
			url : basepath + "/operation/basicData/setDefaultDestination",
			data : "",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success : function(resp) {
				if( resp.res_code == "SO_OPER_1005" ){
					$(".defaultVal").hide();
				}else if( resp.res_code == "FO_OPER_1006" ){
					showFailMessage(resp.res_msg);
				}
			}
		});
	});

	$("#messageDlg").on("hide.bs.modal", function(e){
		location.reload(true);
	});

	setDefaultDestination();

});

//设置默认目的地标签是否显示
function setDefaultDestination(){
	if( $("#defaultDest").text() != "" ){
		$(".defaultVal").show();
	}else{
		$(".defaultVal").hide();
	}
}

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

function isDeparturePlaceValid(departurePlace){
	if( departurePlace != "" && !departurePlaceReg.test(departurePlace) )
		return false;
	else
		return true;
}