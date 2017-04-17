var imgMaxSize = 2 * 1024 * 1024;  //上传图片最大2M
var exceedMaxSizeErrorHint = "图片文件大小不能超过2M";
var validFileTypeArray = new Array("image/jpg", "image/jpeg", "image/png");
var invalidFileTypeErrorHint = "只能上传jpg，jpeg，png格式图片";
var maxImgNum = 50;
var maxImgNumErrorHint = "最多只能上传50个头像";

$(function(){

	//上传头像
	$("#headImgInputer").change(function(){
		var imageNum = $(".upload-image-list").children(".upload-image-item").size();
		if( imageNum >= maxImgNum ){
			showFailMessage(maxImgNumErrorHint);
			return;
		}
		if( !isFileTypeValid(this) ){
			showFailMessage(invalidFileTypeErrorHint);
			$(this).val("");
			return;
		}
		//检查图片大小
		var fileSize = this.files[0].size;
		if( fileSize > imgMaxSize ){
			showFailMessage(exceedMaxSizeErrorHint);
			return;
		}
		$("#headImgForm").ajaxSubmit({
			success: function(resp){
				if( resp.res_code == "SO_OPER_1015" ){
					location.reload(true);
				}else if(resp.res_code == "FO_OPER_1016" ){
					showFailMessage(resp.res_msg);
				}
			}
		});
	});

	//删除头像
	$(".delHeadImgBtn").click(function(){
		var delHeadImgId = $(this).attr("value");
		$.ajax({
			type : "POST",
			url : basepath + "/operation/basicData/deleteDefaultHeadImage",
			data : delHeadImgId,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success : function(resp) {
				if( resp.res_code == "SO_OPER_1017" ){
					location.reload(true);
				}else if( resp.res_code == "FO_OPER_1018" ){
					showFailMessage(resp.res_msg);
				}
			}
		});
	});

});

//检查图片格式
function isFileTypeValid(fileInputer){
	var fileType = fileInputer.files[0].type;
	for(var i in validFileTypeArray){
		if( fileType == validFileTypeArray[i] ){
			return true;
		}else{
			continue;
		}
	}
	return false;
}

function showFailMessage(message){
	$(".popupContainer").children("i").removeClass("passIcon");
	$(".popupContainer").children("i").addClass("warnIcon");
	$(".popupContainer-fonts").text(message);
	$("#messageDlg").modal("show");
}
