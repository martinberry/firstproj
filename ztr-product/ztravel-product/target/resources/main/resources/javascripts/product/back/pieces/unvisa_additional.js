var KEDITOR = [];

$(function(){

	$('button.commonButton.red-fe6869Button.top').click(function(){
		submitForm();
	});
	$('.commonButton.orange-f79767Btn.top').click(function(){
		release();
	});
	$('#prevBtn').click(function(){
		window.open(basepath + "/piece/prevDetail/unvisa/"+$('#productId_hidden').val());
	});

  //初始化富文本框
	KindEditor.ready(function(K) {
        K.options.items = ['forecolor', 'bold', 'insertorderedlist', 'insertfile'];
        $('textarea.editorText').each(function(){
        	var ed = K.create(this, {
        		allowFileManager : false,
        		allowFileUpload : true,
        		uploadJson : operaServer+'/upload/kindFile',
                afterCreate : function() {
                    this.loadPlugin('autoheight');
                    $(".facilityContent .ke-container:gt(0)").hide();
                },
            });
        	KEDITOR.push(ed);
        });
    });

});

function release(){
	var params = buildParams();
	if(!checkParams(params)) return;
	$.ajax({
	    type: 'POST',
	    url: basepath + '/pieces/additionalInfo/release/UNVISA/' + $('#productId_hidden').val(),
	    contentType : 'application/json',
	    dataType: 'json' ,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		window.location.href = basepath + '/pieces/maintain/index' ;
	    	}else{
	    		alert(result.res_msg);
	    	}
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
}

/*提交基本信息*/
function submitForm(){
	var additionalInfo = buildParams();
	if(!checkParams(additionalInfo)) return;
	$.ajax({
	    type: 'POST',
	    url: basepath + '/pieces/additionalInfo/save/unvisa' ,
	    data: JSON.stringify(additionalInfo) ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    async:false,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		window.location.href = basepath + "/pieces/additionalInfo/edit/UNVISA/" + additionalInfo.id;
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
	if(strNB(params.additionalInfos.FEATURES)&&(params.additionalInfos.FEATURES.length <= 0||params.additionalInfos.FEATURES.length > 5000)){
		alert("产品介绍仅可输入1-5000个字符");
		return false;
	}else if(strNB(params.additionalInfos.INTRODUCTIONS)&&(params.additionalInfos.INTRODUCTIONS.length <= 0||params.additionalInfos.INTRODUCTIONS.length > 5000)){
		alert("使用说明仅可输入1-5000个字符");
		return false;
	}else if(strNB(params.additionalInfos.BOOKING)&&(params.additionalInfos.BOOKING.length <= 0||params.additionalInfos.BOOKING.length > 5000)){
		alert("预订须知仅可输入1-5000个字符");
		return false;
	}else if(strNB(params.additionalInfos.FEE_DETAIL)&&(params.additionalInfos.FEE_DETAIL.length <= 0||params.additionalInfos.FEE_DETAIL.length > 5000)){
		alert("费用说明仅可输入1-5000个字符");
		return false;
	}else if(strNB(params.additionalInfos.REFUND_POLICY)&&(params.additionalInfos.REFUND_POLICY.length <= 0||params.additionalInfos.REFUND_POLICY.length > 5000)){
		alert("退改政策仅可输入1-5000个字符");
		return false;
	}
	return flag;
}

/*拼装参数*/
function buildParams(){
	var additionalInfoVo = {};
	additionalInfoVo.id = $('#productId_hidden').val();
	additionalInfoVo.pid  = $('#productPid_hidden').val();
	additionalInfoVo.progress = parseInt($('#progress_hidden').val());

	var additionalInfos = {};
	additionalInfos.FEATURES = $.trim(KEDITOR[0].html());
	additionalInfos.INTRODUCTIONS = $.trim(KEDITOR[1].html());
	additionalInfos.BOOKING = $.trim(KEDITOR[2].html());
	additionalInfos.FEE_DETAIL = $.trim(KEDITOR[3].html());
	additionalInfos.REFUND_POLICY = $.trim(KEDITOR[4].html());
	additionalInfoVo.additionalInfos = additionalInfos;

	return additionalInfoVo;
}
