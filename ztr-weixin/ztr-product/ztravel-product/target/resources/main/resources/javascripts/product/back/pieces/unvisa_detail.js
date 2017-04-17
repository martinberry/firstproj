$(function(){
	$('button.commonButton.red-fe6869Button.top').click(function(){
		submitForm(false);
	});
	$('button.commonButton.whiteBtn.top').click(function(){
		submitForm(true);
	});

	initLanguageType();
});

function initLanguageType(){
	var languageType = $('#languageType_hidden').val();
	if (languageType === undefined || languageType == '') {
		$('#languageType_dropdown').find('li[value=CHINESE]').addClass("active");
		$('#languageTypeName').html("中文");
	} else {
		$('#languageType_dropdown').find('li').each(function(){
			if ($(this).attr("value") == languageType) {
				$(this).addClass("active");
				$('#languageTypeName').html($(this).find('a').html());
			}
	    });
	}
}

/*提交基本信息*/
function submitForm(withNext){
	var detailInfo = buildParams();
	if(withNext === undefined || !withNext){
		detailInfo.withNext = false;
	}else{
		if(!checkParams(detailInfo)) return;
		detailInfo.withNext = true;
	}
	$.ajax({
	    type: 'POST',
	    url: basepath + '/pieces/detailInfo/save/unvisa' ,
	    data: JSON.stringify(detailInfo) ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    async:false,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		if(withNext === undefined || !withNext){
	    			window.location.href = basepath + "/pieces/detailInfo/edit/UNVISA/" + detailInfo.id;
	    		}else{
	    			window.location.href = basepath + "/pieces/priceInfo/edit/cost/UNVISA/" + detailInfo.id;
	    		}
	    	}else{
	    		alert(result.res_msg);
	    	}
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
}

/*校验*/
function checkParams(params){
	var flag = true;
	if(params.languageType === undefined || params.languageType.length <= 0){
		alert("服务语言必填");
		return false;
	}
	if(!strNB(params.serviceTime)){
		alert("服务时间必填");
		return false;
	}
	if(!strNB(params.images) ||params.images.length < 1 || params.images.length > 4){
		alert("需上传1-4张高清（XXX*XXX）JPG/PNG图");
		return false;
	}
	return flag;
}

/*拼装参数*/
function buildParams(){
	var detailInfoVo = {};
	detailInfoVo.id = $('#productId_hidden').val();
	detailInfoVo.pid  = $('#productPid_hidden').val();
	detailInfoVo.progress = parseInt($('#progress_hidden').val());
	detailInfoVo.languageType = $('#languageType_dropdown').find('li.active').attr("value");
	detailInfoVo.serviceTime = $.trim($('#serviceTime_input').val());

	var imageIds =[];
	 var t=$('.hotelImgList').children('li').children("input[name='pictureId']").length;
	 for (var i=0;i<t;i++){
		 imageIds[i]=$('.hotelImgList').children('li').children("input[name='pictureId']")[i].value;
	}
	detailInfoVo.images= imageIds;

	return detailInfoVo;
}
