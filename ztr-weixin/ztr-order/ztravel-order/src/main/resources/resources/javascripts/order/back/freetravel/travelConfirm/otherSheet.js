$(function(){

    $('body').delegate('.deleteFileName', 'click', function(event) {
    	var mediaId = $(this).parents('.fileName').attr('value');
    	var orderId = $('#orderId').val();
    	deleteAttachment(orderId, mediaId, this);
    });

});

/*上传图片*/
function uploadFile(file){
	var varAllImgMaxSize = 2 * 1024 * 1024 ;
	if (file.files && file.files[0]){
		if(file.files[0].size > varAllImgMaxSize){
			alert('文件大小限制2M') ;
			$(file).val("") ;
			return ;
		}
	}
	var fileName = file.files[0].name;
	var orderId = $('#orderId').val();

	$(file).parents('form').ajaxSubmit({
		success: function(data){
	    	if(data.res_code == 'success'){
	    		addAttachment(orderId, fileName, data.res_msg);
	    	}else{
	    		alert(data.res_msg);
	    		$(file).val("") ;
	    	}
	    }
	});
}

function addAttachment(orderId, fileName, mediaId){

	var attachment = {};
	attachment.name = fileName;
	attachment.mediaId = mediaId;

	var data = {
			"orderId":orderId,
			"attachment": attachment
	}
	$.ajax({
	    type: 'POST',
	    url: basepath + '/order/travelConfirm/addAttachment' ,
	    data: JSON.stringify(data),
	    contentType : 'application/json',
	    dataType: 'json' ,
	    success: function(result){
	    	save_lock = false ;
	    	if(result.res_code == 'SUCCESS'){
	    		$('.fileNameList').append('<label class="fileName" value="' + mediaId +'">'
	    	    		+'<span class="fileNameFonts">'+ fileName + '</span>'
	    	    		+'<i class="deleteFileName"></i>'
	    	    		+'</label>');
	    		alert("文件上传成功");
	    	}else if(result.res_code == 'FAILURE'){
	    		var msg = result.res_msg;
	    		if (msg == ''){
	    			msg = "文件上传失败";
	    		}
	    		alert(msg);
	    	}
	    }
	});
}

function deleteAttachment(orderId, mediaId, file){

	var data = {
			"orderId":orderId,
			"mediaId":mediaId
	}
	$.ajax({
	    type: 'POST',
	    url: basepath + '/order/travelConfirm/deleteAttachment' ,
	    data: data,
	    dataType: 'json' ,
	    success: function(result){
	    	save_lock = false ;
	    	if(result.res_code == 'SUCCESS'){
	            $(file).parents('.fileName').eq(0).remove();
	    		alert("文件删除成功");
	    	}else if(result.res_code == 'FAILURE'){
	    		alert("文件删除失败");
	    	}
	    }
	});
}