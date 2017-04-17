var disappear = '' ;
var nickname_hint_Illegal = '您的昵称格式不对哦' ;
var nickname_hint_lengtherror = '您的昵称格式不对哦' ;
var email_hint_Illegal = '邮箱格式有误' ;
var mobile_hint_Illegal = '手机号码格式有误' ;
var email_hint_isalreadyexists = '邮箱已经被注册' ;

var img_size_error = "上传头像大小限制2M";
var img_type_error = "上传头像类型限制png,jpg,jpeg";

var varAllImgExt="image/jpeg,image/png"//全部图片格式类型
var varAllImgMaxSize = 2 * 1024 * 1024 ;
var hasImage = false ;
var save_lock = false ;
var random_head_lock = false ;
var random_name_lock = false ;
var pickup_lock = false ;
var upload_lock = false ;
var skip_lock = false ;


$(function(){

	$(".skip-text.info").click(function(){
		var nickName = trim($("#nickNameInputer")) ;
		var imageId = trim($("#selectedImageId")) ;

		if(nickName.length < 1 || nickName.length > 10){
			toggleErrorHint(disappear) ;
			toggleErrorHint(nickname_hint_lengtherror) ;
			return ;
		}else{
			if(!checkNickName(trim("#nickNameInputer"))){
				toggleErrorHint(disappear) ;
				toggleErrorHint(nickname_hint_Illegal) ;
				return ;
			}else{
				if(!skip_lock){
					skip_lock = true ;
					$.ajax({
					    type: 'POST',
					    url: ssoServer + '/sso/skipImprovePersonalData' ,
					    data: "nickName=" + nickName + "&imageId=" + imageId ,
					    dataType: 'json' ,
					    success: function(data){
					    	skip_lock = false ;
					    	if(data.res_code == 'EF_MEMB_1019'){
					    		toggleErrorHint(disappear) ;
					    		toggleErrorHint(nickname_hint_Illegal) ;
					    	}else if(data.res_code == 'EF_MEMB_1035'){
					    		toggleErrorHint(disappear) ;
					    		toggleErrorHint("请再点一下~~~~~~") ;
					    	}else if(data.res_code == 'SF_MEMB_1009'){
					    		toggleErrorHint(disappear) ;
					    		window.location.href = data.res_msg ;
					    	}
					    },
					    error: function(data){
					    	skip_lock = false ;
					    }
					});
				}
			}
		}
	});

	//修改图标
	$(".commonIcon.smallEditIcon").click(function(){
		showHeadPage() ;
	});

	//刷新昵称
	var random_name_lock = false ;
	$(".commonIcon.refreshIcon").click(function(){
		if(!random_name_lock){
			random_name_lock = true ;
			$.ajax({
			    type: 'POST',
			    url: ssoServer + '/sso/refreshNickName' ,
			    dataType: 'json' ,
			    success: function(data){
			    	random_name_lock = false ;
			    	$("#nickNameInputer").val(data.res_code) ;
			    	if(nickname_hint_lengtherror == $(".errorHint").html()
			    			|| nickname_hint_Illegal == $(".errorHint").html()){
			    		toggleErrorHint(disappear) ;
			    	}
			    },
			    error: function(data){
			    	random_name_lock = false ;
			    }
			});
		}
	});

	//昵称
	$("#nickNameInputer").blur(function() {
		if(trim("#nickNameInputer") != ''){
			if(trim("#nickNameInputer").length < 1 || trim("#nickNameInputer").length > 11){
				toggleErrorHint(disappear) ;
				toggleErrorHint(nickname_hint_lengtherror) ;
			}else{
				if(!checkNickName(trim("#nickNameInputer"))){
					toggleErrorHint(disappear) ;
					toggleErrorHint(nickname_hint_Illegal) ;
				}else{
					toggleErrorHint(disappear) ;
				}
			}
		}else{
			toggleErrorHint(disappear) ;
		}
	});

	//email校验
	$("#emailInputer").blur(function() {
		if(trim("#emailInputer") != ''){
			if(!checkEmail(trim("#emailInputer"))){
				toggleErrorHint(disappear) ;
				toggleErrorHint(email_hint_Illegal) ;
			}else{
				toggleErrorHint(disappear) ;
				isEmailAlreadyExists(trim("#emailInputer")) ;
			}
		}else{
			toggleErrorHint(disappear) ;
		}
	});

	//推荐人手机号码校验
	$("#recommenderInputer").blur(function() {
		if(trim("#recommenderInputer") != ''){
			$.ajax({
			    type: 'POST',
			    url: ssoServer + '/sso/selectByMobile' ,
			    data: "mobile=" + trim("#recommenderInputer") ,
			    dataType: 'json' ,
			    success: function(data){
			    	if(data.res_code == 'EF_MEMB_1002'){
			    		var id = data.res_msg.split(":")[0] ;
			    		var name = data.res_msg.split(":")[1] ;
			    		$("#recommender").html(name) ;
			    		$("#recommenderInputerId").val(id) ;
			    		$(".rewardPlan.a").show() ;
						$(".rewardPlan.b").hide() ;
			    	}else if(data.res_code == 'EF_MEMB_1017'){
			    		$("#recommenderInputerId").val('') ;
			    		$(".rewardPlan.a").hide() ;
			    		$(".rewardPlan.b").html("<em>用户手机号码不合法</em>") ;
						$(".rewardPlan.b").show() ;
			    	}else if(data.res_code == 'fail'){
			    		$("#recommenderInputerId").val('') ;
			    		$(".rewardPlan.a").hide() ;
			    		$(".rewardPlan.b").html("<em>"+data.res_msg+"</em>") ;
						$(".rewardPlan.b").show() ;
			    	}else{
			    		$("#recommenderInputerId").val('') ;
			    		$(".rewardPlan.a").hide() ;
			    		$(".rewardPlan.b").html("<em>该手机号码未注册</em>") ;
						$(".rewardPlan.b").show() ;
			    	}
			    }
			});
		}else{
			$("#recommenderInputerId").val('') ;
			$(".rewardPlan.a").hide() ;
			$(".rewardPlan.b").hide() ;
		}
	});

	$(".bigOrangeBtn.active").click(function(){
		var imageId = trim($("#selectedImageId")) ;
		var email = trim($("#emailInputer")) ;
		var recommender = trim("#recommenderInputer") ;
		if(email.length > 0 && !checkEmail(email)){
			toggleErrorHint(disappear) ;
			toggleErrorHint(email_hint_Illegal) ;
			return ;
		}else{
			toggleErrorHint(disappear) ;
		}

		var nickName = trim($("#nickNameInputer")) ;
		if(nickName.length < 1 || nickName.length > 11){
			toggleErrorHint(disappear) ;
			toggleErrorHint(nickname_hint_lengtherror) ;
			return ;
		}else{
			if(!checkNickName(trim("#nickNameInputer"))){
				toggleErrorHint(disappear) ;
				toggleErrorHint(nickname_hint_Illegal) ;
				return ;
			}else{
				saveInfo(nickName, email, recommender, imageId) ;
			}
		}

	});

	giveMeOthers() ;

	$("#headImg_blueBtn").click(function(){
		if($(this).attr('flag') == 'upload'){
			upload();
		}else if($(this).attr('flag') == 'pickup'){
			pickup();
		}
	});

	$(".sec-tab span").click(function(){
		$(".sec-tab span").removeClass("current");
		$(this).addClass("current");

		if($(this).index()==0){
			$(".sec-tab .slidline").animate({'margin-left':'-108'},500);
		}else{
			$(".sec-tab .slidline").animate({'margin-left':'7'},500);
		}

		if ($(this).hasClass("uploadBtn")) {
			$(".pickup-default-avatar").hide();
			$(".upload-avatar").show();
			$(".commonBtn.blueBtn").removeClass("pickup") ;
			if(!$(".commonBtn.blueBtn").hasClass("upload")){
				$(".commonBtn.blueBtn").addClass("upload") ;
			}
			//提交头像  upload
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

	//取消返回页面
	$(".commonBtn.grayBtn").click(function(){
		showImprovePage() ;
		$('#imagePreviewer').attr('src', host + '/images/client/review-avatar-blank.jpg');
		toggleErrorHint(disappear);
		clearFile("#imageInputer");
	});

	//跳过页面
	$(".skip-text.avatar").click(function(){
		showImprovePage() ;
	});

	//随机换取数据库头像
	$(".changeViewpoints").click(function(){
		giveMeOthers() ;
	});

})

function saveInfo(nickName, email, recommender, imageId){
	if(!save_lock){
		save_lock = true ;
		$.ajax({
		    type: 'POST',
		    url: ssoServer + '/sso/saveImproveData' ,
		    data: "nickName=" + nickName + "&email=" + email + "&recommender=" + recommender + "&imageId=" + imageId ,
		    dataType: 'json' ,
		    success: function(data){
		    	save_lock = false ;
		    	if(data.res_code == 'EF_MEMB_1020'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(email_hint_isalreadyexists) ;
		    	}else if(data.res_code == 'EF_MEMB_1018'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(email_hint_Illegal) ;
		    	}else if(data.res_code == 'EF_MEMB_1019'){
		    		toggleErrorHint(disappear) ;
		    		toggleErrorHint(nickname_hint_Illegal) ;
		    	}else if(data.res_code == 'TAKE_ME_BACK'){
		    		toggleErrorHint(disappear) ;
		    		window.location.href = data.res_msg ;
		    	}
		    },
		    error: function(data){
		    	save_lock = false ;
		    }
		});
	}
}

function checkNickName(nickName){
	var reg = /^([0-9a-zA-Z\*\u4E00-\u9FA5]{1,11})$/;
	return nickName.match(reg) != null;
}

function checkEmail(email){
	var isEmail = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
	return isEmail.test(email) ;
}

function isEmailAlreadyExists(email){
	$.ajax({
	    type: 'POST',
	    url: ssoServer + '/sso/isEmailAlreadyExists' ,
	    data: "email=" + email ,
	    dataType: 'json' ,
	    success: function(data){
	    	if(data.res_code == 'EF_MEMB_1020'){
	    		toggleErrorHint(disappear) ;
	    		toggleErrorHint(email_hint_isalreadyexists) ;
	    	}else if(data.res_code == 'EF_MEMB_1023'){

	    	}else if(data.res_code == 'SF_MEMB_1004'){
	    		toggleErrorHint(disappear) ;
	    	}
	    }
	});
}

function showImprovePage(){
	$(".main-box.avatar").hide();
	$(".main-box.info").show();
	setWrapperHeight();
	setMainBoxPosition();
}

function showHeadPage(){
	$(".main-box.avatar").show();
	$(".main-box.info").hide();
	setWrapperHeight();
	setMainBoxPosition();
}

function giveMeOthers(){
	if(!random_head_lock){
		random_head_lock = true ;
		$.ajax({
		    type: 'GET',
		    url: ssoServer + '/sso/refreshImage' ,
		    dataType: 'json' ,
		    data: "banCache=" + Math.random() ,
		    cache: false,
		    success: function(data){
		    	random_head_lock = false ;
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
		    error: function(data){
		    	random_head_lock = false ;
		    }
		});
	}
}

function pickup(){
	if(!pickup_lock){
		pickup_lock = true ;
		$.ajax({
		    type: 'POST',
		    url: ssoServer + '/sso/saveHeadPickup' ,
		    data: "headImageId=" + $(".default-avatar-item.current").attr('id') ,
		    dataType: 'json' ,
		    success: function(data){
		    	pickup_lock = false ;
		    	if(data.res_code == 'EF_MEMB_1015' || data.res_code == 'EF_MEMB_1016'){

		    	}else if(data.res_code == 'SF_MEMB_1002'){
		    		$('.round_photo').attr('src',mediaserver+'imageservice?mediaImageId='+$(".default-avatar-item.current").attr('id'));
		    		$("#selectedImageId").val($(".default-avatar-item.current").attr('id')) ;
		    		showImprovePage() ;
		    		$('#imagePreviewer').attr('src', host + '/images/client/review-avatar-blank.jpg');
		    	}
		    },
		    error: function(data){
		    	pickup_lock = false ;
		    }
		});
	}
}

function upload(){
	if(!upload_lock){
		upload_lock = true ;
		if(hasImage){
			$("#form1").ajaxSubmit({
				success: function(data){
					upload_lock = false ;
			    	if(data.res_code == 'success'){
			    		$('.round_photo').attr('src',mediaserver+'imageservice?mediaImageId='+data.res_msg);
			    		$("#selectedImageId").val(data.res_msg) ;
			    		showImprovePage() ;
			    		$('#imagePreviewer').attr('src', host + '/images/client/review-avatar-blank.jpg');
			    		clearFile("#imageInputer");
			    	}
			    },
			    error: function(data){
			    	upload_lock = false ;
			    }
			});
		}
	}
}


//图片上传预览
function previewImage(file){
	var MAXWIDTH  = 260;
	var MAXHEIGHT = 180;
	toggleErrorHint(disappear,".img_errorHint") ;
	var div = document.getElementById('imagePreviewerDIV');
	if (file.files && file.files[0]){
		if(varAllImgExt.indexOf(file.files[0].type) == -1){
			toggleErrorHint(img_type_error,".img_errorHint") ;
			return ;
		}
		if(file.files[0].size > varAllImgMaxSize){
			toggleErrorHint(img_size_error,".img_errorHint") ;
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
