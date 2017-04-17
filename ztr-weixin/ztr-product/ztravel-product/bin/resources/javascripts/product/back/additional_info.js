var addInfoReg = /^$/;
var KEDITOR = [];
var isReadonly = true;
var infoMapper = {
		FEE_INCLUDE:'费用包含',
		GIFT_ITEMS:'真旅行赠送项目',
		FEE_NOT_INCLUDE:'费用不含',
		VISA:'签证须知',
		REFUND_POLICY:'退改政策',
		BOOKING:'预定须知',
};
var tipMapper ={
		WEATHER:'气候',
		EXPEND:'消费',
		COMMUNICATION:'通信',
		CUSTOM:'当地风俗',
		OTHER:'其他'
};
$(function(){
	if($('#accessType').val() == 'edit'){
		isReadonly= false;
	}
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
                    if(isReadonly)this.readonly();
                    $(".facilityContent .ke-container:gt(0)").hide();
                },
            });
        	KEDITOR.push(ed);
        });
    });
	$(".facilityList li").each(function(index){
        $(this).click(function(){
            $(".facilityContent .ke-container").hide();
            $(".facilityContent .ke-container:eq(" + index + ")").show();
            $(".facilityList li").removeClass("active");
            $(this).addClass("active");
        });
    });

	$('.commonButton.red-fe6869Button.top').click(function(){
		submitForm();
	});
	$('.commonButton.orange-f79767Btn.top').click(function(){
		release();
	});
	$('#prevBtn').click(function(){
		window.open(basepath + "/product/back/prevDetail/"+$('#productId_hidden').val());
	});
	if($('#accessType').val() != 'edit'){
	}
});
/*提交表单*/
function submitForm(){
	var params = buildParams();
	if(!checkParams(params))return;
	var paramStr = JSON.stringify(params);
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/additionalInfo/save' ,
	    data: paramStr ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		window.location.href = basepath + "/product/additionalInfo/edit/"+params.id;
	    	}else{
	    		alert(result.res_msg);
	    	}
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
}
/**/
function release(){
	var params = buildParams();
	if(!checkParams(params))return;
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/additionalInfo/release/'+$('#productId_hidden').val() ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		window.location.href = basepath + '/product/productMaintain/index' ;
	    	}else{
	    		alert(result.res_msg);
	    	}
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
}
/*设置编辑框只读*/
function setReadonly(flag){
	for(var i = 0; i < KEDITOR.length; i++){
		KEDITOR[i].readonly(flag);
	}
}
/*拼装参数*/
function buildParams(){
	var params = {};
	params.id = $('#productId_hidden').val();
	params.status = $('#status_hidden').val();
	params.progress = parseInt($('#progress_hidden').val());
	var additionalInfos = new Object();
	$('.editorCommonModel').each(function(index){
		var key = $(this).find('h4').attr('value');
		var value = KEDITOR[index].html();
		additionalInfos[key] = value;
	});
	params.additionalInfos = additionalInfos;

	var travelTips = {};
	$('.facilityContent textarea.editorText').each(function(index){
		var key = $(this).attr('name');
		var value = KEDITOR[index+6].html();
		if(value !== undefined){
			travelTips[key] = value;
		}
	});
	params.travelTips = travelTips;
	return params;
}
function checkParams(params){
	var flag = true;
	if(params.id == ''){
		alert("产品ID出错");
		return false;
	}
	if(isNaN(params.progress)){
		alert("产品进度出错");
		return false;
	}

	if(params.additionalInfos){
		var addInfos = params.additionalInfos;
		for(key in addInfos){
			var content = addInfos[key];
			if(strNB(content) && content.length > 5000){
				alert(infoMapper[key] + "字符长度超过限制");
				return false;
			}
		}
	}

	if(params.travelTips){
		var tips = params.travelTips;
		for(key in tips){
			var content = tips[key];
			if(strNB(content) && content.length > 5000){
				alert(tipMapper[key] + "字符长度超过限制");
				return false;
			}
		}
	}

	return flag;
}