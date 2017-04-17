$(function(){
	initPictureUpload();
})

function initPictureUpload() {
	// 自动上传图片
	$(".main-container").delegate("[name='imageform'] .againUpload,.instantUploadFile", "change", function(){
		//检查图片格式
		var $this = $(this);
		var filePath = $(this).val();
		var pos = filePath.lastIndexOf(".");
		var fileName = filePath.substring(pos+1,filePath.length).toLowerCase();
		if(fileName == ''){
			return;
		}
	    if(fileName != "jpg" && fileName != "png" && fileName != "jpeg"){
	    	$(this).find(".againUpload").val("");
	    	  clickActionPopup("imageSizeTip");
             return;
	    }
	    //检查图片大小
	    var fileSize = this.files[0].size;
	    if( fileSize >  2 * 1024 * 1024 ){
	    	$(this).find(".againUpload").val("");
	  	    clickActionPopup("imageSizeTip");
	    	return;
	    }

	    $(this).parents("form").ajaxSubmit({
			dataType : "json",
			success : function(response) {
				if(response.res_code == 'SO_PROD_1001'){
					var pictureId = response.res_msg;
					if(pictureId){
						$($this).parents("form").find("[name-value='imageId']").val(pictureId);
						if($($this).hasClass("againUpload")){
							$($this).parents(".row04").find("img").attr("src",mediaserver+"imageservice?mediaImageId="+pictureId);
						}else if($($this).hasClass("instantUploadFile")){
							var imageHtml = '<div class="commonStyle row04 instantUpload"> '
							+ '<img src="'+mediaserver+'imageservice?mediaImageId='+pictureId+'" class="uploadImg" />'
							+'<form name="imageform" action="'+basepath+'/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">'
							+'<div class="showUpload">'
							+'<input type="file" class="againUpload"  name="picture">'
							+'<input type ="hidden" name-value="imageId" value="'+pictureId+'"/>'
							+' <a href="javascript:void(0);" class="againUploadFonts">重新上传</a>'
							+"</div>"
							+"</form>"
							+"</div>";
							$($this).parents(".row04").replaceWith(imageHtml);
						}
						  clickActionPopup("imageUploadSuccessTip");
					}else{
						clickActionPopup("imageUploadFailedTip");
					}
		    	}else{
		    		clickActionPopup("imageUploadFailedTip");
		    	}
			}
		});
	});
}