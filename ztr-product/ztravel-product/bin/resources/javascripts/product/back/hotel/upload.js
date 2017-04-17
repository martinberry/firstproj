var imgMaxSize = 2 * 1024 * 1024 ;  //上传图片最大2M

$(function() {
	initPictureUpload();
	pictureEdit.init();
	controlAddImg();
});

function initPictureUpload() {
	// 自动上传图片
	$("body").delegate("#file", "change", function(){
//		var callback = eval("(" + $(this).attr("callback") + ")");
		//检查图片格式
		var filePath = $(this).val();
		var pos = filePath.lastIndexOf(".");
		var fileName = filePath.substring(pos+1,filePath.length).toLowerCase();
		if(fileName == ''){
			return;
		}
	    if(fileName != "jpg" && fileName != "png" && fileName != "jpeg"){
	    	$("#file").val("");
	    	alert("只能上传jpg，png , jpeg格式图片");
             return;
	    }
	    //检查图片大小
	    var fileSize = this.files[0].size;
	    if( fileSize > imgMaxSize ){
	    	$("#file").val("");
	    	alert("图片不能超过2M");
	    	return;
	    }

	    var $li = $(this).parent().parent().parent();
	    $(this).parents("form").ajaxSubmit({
			dataType : "json",
			success : function(response) {
				if(response.res_code == 'SO_PROD_1001'){
					var pictureId = response.res_msg;
					if(pictureId){
						callback(pictureId);
					}else{
						$li.show();
						alert("上传失败，请稍后重试");
					}
		    	}else{
		    		$li.show();
		    		alert('上传失败，请稍后重试');
		    	}
				controlAddImg();
				$("#file").val("");
			}
		});
	});
}


function callback(pictureId) {
	var li = $("<li></li>");
	li.append('<img src="' + mediaserver + 'imageservice?mediaImageId='
			+ pictureId + '" draggable="true">');
	li.append('<input name="pictureId" value="'+pictureId+'" type="hidden">');
	li.append('<div class="moveLeft">左移</div>');
	li.append('<div class="moveRight">右移</div>');
	li.append('<div class="hoverDelete"><i class="delIcon"></i>删除</div>');
	li.insertBefore($("#file").parent().parent().parent());
	pictureEdit.initDel();
}

var pictureEdit = {
		//初始化
	init : function(){
          this.initDel();
		},

	initDel : function() {
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
			controlAddImg();
			});

	}
	}


function controlAddImg() {
	var $autoUpload = $("#file").parent().parent().parent();
	if($autoUpload.prevAll("li").size() <5){
		$("#file").parent().removeClass();
		$("#file").addClass("upLoading");
	}else if($autoUpload.prevAll("li").size() >=5 && $autoUpload.prevAll("li").size()<10){
		$("#file").removeClass();
		$("#file").parent().addClass("addImg");
	}else if($autoUpload.prevAll("li").size() >=10){
		$autoUpload.hide();
	}
}
