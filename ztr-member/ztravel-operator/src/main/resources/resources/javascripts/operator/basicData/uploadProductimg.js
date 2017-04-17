$(function(){
	initPictureUpload();
})

function initPictureUpload() {
	// 自动上传图片
	$(".main-container").delegate("[name='pictureform'] .againUpload,.instantUploadFile", "change", function(){
		//检查图片格式
		var $this = $(this);
		var filePath = $(this).val();
		var pos = filePath.lastIndexOf(".");
		var fileName = filePath.substring(pos+1,filePath.length).toLowerCase();
		var fileSize = this.files[0].size;

		if((fileName == '')||(fileName != "jpg" && fileName != "png" && fileName != "jpeg")||( fileSize >  2 * 1024 * 1024 )){
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
						$($this).parents("form").find("[name-value='pictureId']").val(pictureId);
						if($($this).hasClass("againUpload")){
							$($this).parents(".row05").find("img").attr("src",mediaserver+"imageservice?mediaImageId="+pictureId);
						}else if($($this).hasClass("instantUploadFile")){
							var imageHtml = '<div class="commonStyle row05 upLoadProductImg"> '
							+ '<img src="'+mediaserver+'imageservice?mediaImageId='+pictureId+'" class="uploadImg" />'
							+'<form name="pictureform" action="'+basepath+'/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">'
							+'<div class="showUpload">'
							+'<input type="file" class="againUpload"  name="picture">'
							+'<input type ="hidden" name-value="pictureId" value="'+pictureId+'"/>'
							+' <a href="javascript:void(0);" class="againUploadFonts">重新上传</a>'
							+"</div>"
							+'</form>'
							+'</div>';
							$($this).parents(".row05").replaceWith(imageHtml);
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

