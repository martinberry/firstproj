$(function(){
	$('button.commonButton.red-fe6869Button.top').click(function(){
		submitForm(false);
	});
	$('button.commonButton.whiteBtn.top').click(function(){
		submitForm(true);
	});

});

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
	    url: basepath + '/pieces/detailInfo/save/visa' ,
	    data: JSON.stringify(detailInfo) ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    async:false,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		if(withNext === undefined || !withNext){
	    			window.location.href = basepath + "/pieces/detailInfo/edit/VISA/" + detailInfo.id;
	    		}else{
	    			window.location.href = basepath + "/pieces/priceInfo/edit/cost/VISA/" + detailInfo.id;
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
	if(params.isInterview === undefined || !strNB(params.isInterview)){
		alert("面试要求必选");
		return false;
	}
	if(!strNB(params.validate)){
		alert("签证有效期必填");
		return false;
	}else if(getStrLength(params.validate) < 1 || getStrLength(params.validate) > 50){
		alert("签证有效期仅可输入1-50个字符");
		return false;
	}
	if(!strNB(params.stayTime)){
		alert("可停留日期必填");
		return false;
	}else if(getStrLength(params.stayTime) < 1 || getStrLength(params.stayTime) > 50){
		alert("可停留日期仅可输入1-50个字符");
		return false;
	}
	if(!strNB(params.times)){
		alert("可入境次数必填");
		return false;
	}else if(getStrLength(params.times) < 1 || getStrLength(params.times) > 50){
		alert("可入境次数仅可输入1-50个字符");
		return false;
	}
	if(!strNB(params.scope)){
		alert("受理范围必填");
		return false;
	}else if(getStrLength(params.scope) < 1 || getStrLength(params.scope) > 500){
		alert("受理范围仅可输入1-500个字符");
		return false;
	}
	if(!strNB(params.images) ||params.images.length < 1){
		alert("需上传至少1张图片");
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

	var isInterviewValue = $('#isInterview_radio').find('label.active').attr("value");
	if (isInterviewValue == "NEED") {
		detailInfoVo.isInterview = true;
	} else if (isInterviewValue == "NOTNEED") {
		detailInfoVo.isInterview = false;
	}
	detailInfoVo.validate = $.trim($('#validate_input').val());
	detailInfoVo.stayTime = $.trim($('#stayTime_input').val());
	detailInfoVo.times = $.trim($('#times_input').val());
	detailInfoVo.scope = $.trim($('#scope_input').val());

	var imageIds =[];
	 var t=$('.hotelImgList').children('li').children("input[name='pictureId']").length;
	 for (var i=0;i<t;i++){
		 imageIds[i]=$('.hotelImgList').children('li').children("input[name='pictureId']")[i].value;
	}
	detailInfoVo.images= imageIds;

	return detailInfoVo;
}
