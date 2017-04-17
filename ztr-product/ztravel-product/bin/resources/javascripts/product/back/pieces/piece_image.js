var imgMaxSize = 2 * 1024 * 1024 ;  //上传图片最大2M
var maxFileNum = 20;

$(function() {

	// 左移
	$("body").delegate(".moveLeft", "click", function(){
		var $sli = $(this).parent();
		if ($sli.prev("li").size() < 1) {
			return;
		}
		$sli.clone(true).insertBefore($sli.prev("li"));
		$sli.remove();
	});

	// 右移
	$("body").delegate(".moveRight", "click", function(){
		var $sli = $(this).parent();
		if ($sli.next("li:has(img)").size() < 1) {
			return;
		}
		$sli.clone(true).insertAfter($sli.next("li"));
		$sli.remove();
	});

	// 删除
	$("body").delegate(".hoverDelete", "click", function(){
		$(this).parents('li').nextAll(":last").show();
		$(this).parents('li').remove();
		controlAddImg(this);
	});

});

function uploadImg(file, maxNum) {
	if(!strNB(maxNum)){
		maxFileNum = maxNum;
	}
		//检查图片格式
		var filePath = $(file).val();
		var pos = filePath.lastIndexOf(".");
		var fileName = filePath.substring(pos+1,filePath.length).toLowerCase();
		if(fileName == ''){
			return;
		}
	    if(fileName != "jpg" && fileName != "png" && fileName != "jpeg"){
	    	$(file).val("");
	    	alert("只能上传jpg，png , jpeg格式图片");
             return;
	    }
	    //检查图片大小
	    var fileSize = file.files[0].size;
	    if( fileSize > imgMaxSize ){
	    	$(file).val("");
	    	alert("图片不能超过2M");
	    	return;
	    }

	    var $li = $(file).parent().parent().parent();
	    $(file).parents("form").ajaxSubmit({
			dataType : "json",
			success : function(response) {
				if(response.res_code == 'SO_PROD_1001'){
					var pictureId = response.res_msg;
					if(pictureId){
						callback(file, pictureId);
					}else{
						$li.show();
						alert("上传失败，请稍后重试");
					}
		    	}else{
		    		$li.show();
		    		alert('上传失败，请稍后重试');
		    	}
				controlAddImg(file);
				$(file).val("");
			}
		});
}

function callback(file, pictureId) {
	var li = $("<li></li>");
	li.append('<img src="' + mediaserver + 'imageservice?mediaImageId='
			+ pictureId + '" draggable="true">');
	li.append('<input name="pictureId" value="'+pictureId+'" type="hidden">');
	li.append('<div class="moveLeft">左移</div>');
	li.append('<div class="moveRight">右移</div>');
	li.append('<div class="hoverDelete"><i class="delIcon"></i>删除</div>');
	li.insertBefore($(file).parent().parent().parent());
}

function controlAddImg(file) {
	var $autoUpload = $(file).parent().parent().parent();
	if($autoUpload.prevAll("li").size() <maxFileNum){
		$(file).parent().removeClass();
		$(file).addClass("upLoading");
		$(file).show();
	}else if($autoUpload.prevAll("li").size() >=maxFileNum){
		$(file).removeClass();
		$(file).parent().addClass("addImg");
		$(file).hide();
	}
}
