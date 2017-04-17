var KEDITOR = [];

$(function(){

	$('button.commonButton.red-fe6869Button.top').click(function(){
		submitForm();
	});
	$('.commonButton.orange-f79767Btn.top').click(function(){
		release();
	});
	$('#prevBtn').click(function(){
		window.open(basepath + "/piece/prevDetail/visa/"+$('#productId_hidden').val());
	});

    KindEditor.ready(function(K) {
        K.options.items = ['forecolor', 'insertorderedlist'];
        K.create('textarea[name="content"]', {
            width: '776px',
            afterCreate : function() {
                this.loadPlugin('autoheight');
            }
        });
    });

    $(".facilityList li").each(function(index){
        $(this).click(function(){
            $(this).addClass("active").siblings().removeClass("active");
            $(".visaMaterial > .sectionDiv").eq(index).removeClass("hidden").siblings().addClass("hidden");
        });

    });

	$("body").on("click",".addrow",function(e){
	    var addContent = $("#template").html();
	    console.log(addContent);
	    $(this).closest(".sectionDiv").find("table:last").after(addContent);
	    KindEditor.create('.ke-textra .editorBox',{
	     width: '776px',
	     items: ['forecolor', 'insertorderedlist']
	     });
	});

	$("body").on("click",".delrow",function(){
        $(this).closest(".extraInfo").remove();
	});

});

function release(){
	var params = buildParams();
	if(!checkParams(params)) return;
	$.ajax({
	    type: 'POST',
	    url: basepath + '/pieces/additionalInfo/release/VISA/' + $('#productId_hidden').val(),
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
	    url: basepath + '/pieces/additionalInfo/save/visa' ,
	    data: JSON.stringify(additionalInfo) ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    async:false,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		window.location.href = basepath + "/pieces/additionalInfo/edit/VISA/" + additionalInfo.id;
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

	if(strNB(params.materias.EMPLOYED)){
		for(var i in params.materias.EMPLOYED){
			var material = params.materias.EMPLOYED[i];
			if(strNB(material.title)&&(material.title.length <= 0 ||material.title.length > 30)){
				alert("在职材料标题仅可输入1-30个字符");
				return false;
			}
			if(strNB(material.content)&&(material.content.length <= 0 ||material.content.length > 5000)){
				alert("在职材料内容仅可输入1-5000个字符");
				return false;
			}
			if(strNB(params.images) && material.images.length>1){
				alert("在职材料至多可上传1张图片");
				return false;
			}
		}
	}

	if(strNB(params.materias.FREE)){
		for(var i in params.materias.FREE){
			var material = params.materias.FREE[i];
			if(strNB(material.title)&&(material.title.length <= 0 ||material.title.length > 30)){
				alert("自由职业者材料标题仅可输入1-30个字符");
				return false;
			}
			if(strNB(material.content)&&(material.content.length <= 0 ||material.content.length > 5000)){
				alert("自由职业者材料内容仅可输入1-5000个字符");
				return false;
			}
			if(strNB(params.images) && material.images.length>1){
				alert("自由职业者材料至多可上传1张图片");
				return false;
			}
		}
	}

	if(strNB(params.materias.STUDENT)){
		for(var i in params.materias.STUDENT){
			var material = params.materias.STUDENT[i];
			if(strNB(material.title)&&(material.title.length <= 0 ||material.title.length > 30)){
				alert("在校学生材料标题仅可输入1-30个字符");
				return false;
			}
			if(strNB(material.content)&&(material.content.length <= 0 ||material.content.length > 5000)){
				alert("在校学生材料内容仅可输入1-5000个字符");
				return false;
			}
			if(strNB(params.images) && material.images.length>1){
				alert("在校学生材料至多可上传1张图片");
				return false;
			}
		}
	}

	if(strNB(params.materias.RETIRE)){
		for(var i in params.materias.RETIRE){
			var material = params.materias.RETIRE[i];
			if(strNB(material.title)&&(material.title.length <= 0 ||material.title.length > 30)){
				alert("退休材料标题仅可输入1-30个字符");
				return false;
			}
			if(strNB(material.content)&&(material.content.length <= 0 ||material.content.length > 5000)){
				alert("退休材料内容仅可输入1-5000个字符");
				return false;
			}
			if(strNB(params.images) && material.images.length>1){
				alert("退休材料至多可上传1张图片");
				return false;
			}
		}
	}

	if(strNB(params.materias.CHILD)){
		for(var i in params.materias.CHILD){
			var material = params.materias.CHILD[i];
			if(strNB(material.title)&&(material.title.length <= 0 ||material.title.length > 30)){
				alert("学龄前儿童材料标题仅可输入1-30个字符");
				return false;
			}
			if(strNB(material.content)&&(material.content.length <= 0 ||material.content.length > 5000)){
				alert("学龄前儿童材料内容仅可输入1-5000个字符");
				return false;
			}
			if(strNB(params.images) && material.images.length>1){
				alert("学龄前儿童材料至多可上传1张图片");
				return false;
			}
		}
	}

	if(strNB(params.processes)){
		for(var i in params.processes){
			var process = params.processes[i];
			if(strNB(process.title)&&(process.title.length <= 0 ||process.title.length > 30)){
				alert("办理流程标题仅可输入1-30个字符");
				return false;
			}
			if(strNB(process.content)&&(process.content.length <= 0 ||process.content.length > 5000)){
				alert("办理流程内容仅可输入1-5000个字符");
				return false;
			}
			if(strNB(process.images) && process.images.length>1){
				alert("办理流程至多可上传1张图片");
				return false;
			}
		}
	}

	if(strNB(params.additionalInfos.BOOKING)&&(params.additionalInfos.BOOKING.length <= 0 ||params.additionalInfos.BOOKING.length > 5000)){
		alert("预订须知仅可输入1-5000个字符");
		return false;
	}else if(strNB(params.additionalInfos.FEE_DETAIL)&&(params.additionalInfos.FEE_DETAIL.length <= 0 ||params.additionalInfos.FEE_DETAIL.length > 5000)){
		alert("费用说明仅可输入1-5000个字符");
		return false;
	}else if(strNB(params.additionalInfos.REFUND_POLICY)&&(params.additionalInfos.REFUND_POLICY.length <= 0 ||params.additionalInfos.REFUND_POLICY.length > 5000)){
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

	var index = 0;

	var materias = {};
	$('.visaMaterial').find(".sectionDiv").each(function(i){
		var materiaList = [];
		$(this).find("table").each(function(){
			var materia = {};
			materia.title = $.trim($(this).find("input[name='title_input']").val());
			materia.content = $(document.getElementsByTagName('iframe')[index].contentWindow.document.body).html();
			var imageIds =[];
			 var t=$(this).find('.hotelImgList').children('li').children("input[name='pictureId']").length;
			 for (var i=0;i<t;i++){
				 imageIds[i]=$(this).find('.hotelImgList').children('li').children("input[name='pictureId']")[i].value;
			}
			 materia.images= imageIds;
			 materiaList.push(materia);
			index ++;
		});
		if (i == 0) {
			materias.EMPLOYED = materiaList;
		} else if (i == 1) {
			materias.FREE = materiaList;
		} else if (i == 2) {
			materias.STUDENT = materiaList;
		} else if (i == 3) {
			materias.RETIRE = materiaList;
		} else if (i == 4) {
			materias.CHILD = materiaList;
		}
	});
	additionalInfoVo.materias = materias;

	var processList = [];
	$('.visaProcess').find(".sectionDiv table").each(function(){
		var process = {};
		process.title = $.trim($(this).find("input[name='title_input']").val());
		process.content = $(document.getElementsByTagName('iframe')[index].contentWindow.document.body).html();
		var imageIds =[];
		 var t=$(this).find('.hotelImgList').children('li').children("input[name='pictureId']").length;
		 for (var i=0;i<t;i++){
			 imageIds[i]=$(this).find('.hotelImgList').children('li').children("input[name='pictureId']")[i].value;
		}
		 process.images= imageIds;
		 processList.push(process);
		index ++;
	});
	additionalInfoVo.processes = processList;

	var additionalInfos = {};
	additionalInfos.BOOKING = $(document.getElementsByTagName('iframe')[index].contentWindow.document.body).html();
	additionalInfos.FEE_DETAIL = $(document.getElementsByTagName('iframe')[index+1].contentWindow.document.body).html();
	additionalInfos.REFUND_POLICY = $(document.getElementsByTagName('iframe')[index+2].contentWindow.document.body).html();
	additionalInfoVo.additionalInfos = additionalInfos;

	return additionalInfoVo;
}