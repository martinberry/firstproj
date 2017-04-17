var varAllImgExt="image/jpeg,image/png"//全部图片格式类型
var varAllImgMaxSize = 2 * 1024 * 1024 ;
var hasImage = false ;

$(function(){

	giveMeOthers() ;

	$(".sec-tab span").click(function(){
		$(".sec-tab span").removeClass("current");
		$(this).addClass("current");

		if ($(this).hasClass("uploadBtn")) {
			$(".pickup-default-avatar").hide();
			$(".upload-avatar").show();
			$(".commonBtn.blueBtn").removeClass("pickup") ;
			if(!$(".commonBtn.blueBtn").hasClass("upload")){
				$(".commonBtn.blueBtn").addClass("upload") ;
			}
			$("#headImg_blueBtn").attr('flag', 'upload');
		} else if ($(this).hasClass("pickupBtn")) {
			$(".upload-avatar").hide();
			$(".pickup-default-avatar").show();
			$(".commonBtn.blueBtn").removeClass("upload") ;
			$(".commonBtn.blueBtn").addClass("pickup") ;
			//提交头像  pickup
			$("#headImg_blueBtn").attr('flag', 'pickup');
		}
	});

	$(".default-avatar-item").click(function(){
		$(".default-avatar-item").removeClass("current");
		$(this).addClass("current");
	});

	//随机换取数据库头像
	$(".changeViewpoints").click(function(){
		giveMeOthers() ;
	})


})

function giveMeOthers(){
	$.ajax({
	    type: 'POST',
	    url: basepath + '/member/refreshImage' ,
	    dataType: 'json' ,
	    success: function(data){
	    	if(data != null && data != ''){
	    		var randImages = '' ;
	    		for(var i in data){
	    			if(i == 0){
	    				randImages += '<span class=\"default-avatar-item current\" id=\"' + data[i] + '\">' ;
	    			}else{
	    				randImages += '<span class=\"default-avatar-item\" id=\"' + data[i] + '\">' ;
	    			}
	    			randImages += '<img src=\"' + mediaserver + 'imageservice?mediaImageId=' + data[i] + '\" alt>' ;
	    			randImages += '</span>' ;
	    		}
	    		$(".default-avatar-list").html(randImages) ;
	    		$(".default-avatar-item").click(function(){
	    			$(".default-avatar-item").removeClass("current");
	    			$(this).addClass("current");
	    		});
	    	}
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
}

function pickup(){
	var imgId = $(".default-avatar-item.current").attr('id');
	$.ajax({
	    type: 'POST',
	    url: basepath + '/member/saveHeadPickup' ,
	    data: "headImageId=" + imgId ,
	    dataType: 'json' ,
	    success: function(data){
	    	if(data.res_code == 'EF_MEMB_1015' || data.res_code == 'EF_MEMB_1016'){
	    		console.log(data.res_msg);
	    	}else if(data.res_code == 'SF_MEMB_1002'){
	    		$('#headIconImg').attr('src',mediaserver+'imageservice?mediaImageId='+imgId);
	    		$("#headImg_grayBtn").click();
	    	}
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
}

function upload(){
	if(hasImage){
		$("#form1").ajaxSubmit({
			success: function(data){
		    	if(data.res_code == 'success'){
		    		$('#headIconImg').attr('src',mediaserver+'imageservice?mediaImageId='+data.res_msg);
		    		$("#headImg_grayBtn").click();
		    	}else{
		    		var errMsg = '上传图片失败';
		    		if(data.res_msg)errMsg = errMsg+ ':' +data.res_msg;
		    		popAlert(errMsg);
		    	}
		    }
		});
	}
}


//图片上传预览
function previewImage(file){
	var MAXWIDTH  = 260;
	var MAXHEIGHT = 180;
	toggleErrorHint('','#img_errorHint') ;
	var div = document.getElementById('imagePreviewerDIV');
	if (file.files && file.files[0]){
		if(varAllImgExt.indexOf(file.files[0].type) == -1){
			toggleErrorHint('非法格式图片','#img_errorHint') ;
			return ;
		}
		if(file.files[0].size > varAllImgMaxSize){
			toggleErrorHint('图片大小限制2M','#img_errorHint') ;
			return ;
		}
		var img = document.getElementById('imagePreviewer');
		img.onload = function(){
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
			img.width  =  rect.width;
			img.height =  rect.height;
			img.style.marginTop = rect.top+'px';
	    }
	    var reader = new FileReader();
	    reader.onload = function(evt){img.src = evt.target.result;}
	    reader.readAsDataURL(file.files[0]);
	}else{//兼容IE
        var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
        file.select();
        var src = document.selection.createRange().text;
        var img = document.getElementById('imagePreviewer');
        img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
        div.innerHTML = "<div class=title>预览</div><div id=imagePreviewer style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	}
	hasImage = true ;
}

function clearFile(obj){
	var $prev = $(obj).prev();
	$prev.next().remove();
	$prev.after('<input id="imageInputer" name="imageInputer" type="file" onchange="previewImage(this);"/>');
}


function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight )
    {
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;

        if( rateWidth > rateHeight )
        {
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else
        {
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    return param;
}