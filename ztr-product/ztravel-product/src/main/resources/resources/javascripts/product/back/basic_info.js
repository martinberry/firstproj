var productNameReg = /^([^<>]){2,60}$/;
var productSubNameReg = /^([^<>]){0,60}$/;
var dayNumReg = /^[1-9]?[0-9]{1}$/;
var RGBReg = /^[0-9a-fA-F]{6,6}$/;
$(function(){
	initTagInputs();
	$('button.commonButton.red-fe6869Button.top').click(function(){
		submitForm(false);
	});
	$('button.commonButton.whiteBtn.top').click(function(){
		submitForm(true);
	});
	$('#addCity_button').click(function(){
		var continentName = $('#address_continent').find('ul.dropdown-menu li.active a').html();
		var countryName = $('#address_country').find('ul.dropdown-menu li.active a').html();
		var cityName = $('#address_region').find('ul.dropdown-menu li.active a').html();
		if(cityName !== undefined){
			var isExist = false;
			$('#to_labels').find('label span.closeFonts').each(function(){
				if($(this).html() == cityName)isExist = true;
			});
			if(isExist)return;
			var labelHtml = '<label class="smallTagStyle"><span class="closeFonts" continent="'+ continentName +'" country="'+ countryName +'">'+cityName+'</span><span class="closeIcon" onclick="closeSelf(this);"></span></label>';
			$('#to_labels').prepend(labelHtml);
		}
	});
	CONTINENTDD = new ZtrDropDown('#address_continent',{
		getData: function(request){
			var data = [];
			$.each(ADDRESS.country, function(index){
				data.push(index);
			});
			return data;
		},
	});
	COUNTRYDD = new ZtrDropDown('#address_country', {
		getData: function(request){
			var data = [];
			$.each(ADDRESS.country, function(index,item){
				if(request == index){
					data = item;
				}
			});
			return data;
		},
	});
	REGIONDD = new ZtrDropDown('#address_region', {
		getData: function(request){
			var data = [];
			$.each(ADDRESS.region, function(index,item){
				if(request == index){
					data = item;
				}
			});
			return data;
		},
	});
	CONTINENTDD.casCading(COUNTRYDD);
	COUNTRYDD.casCading(REGIONDD);
	CONTINENTDD.init();
	CONTINENTDD.unSelect();

	if(parseInt($('#progress_hidden').val()) >= 1){
		setReadonly();
	}
});


function initTagInputs(){
    var slideDownBtn = $("#hotTagSlideBtn-arrow").find("i");
    var hotTag = $(".hotTag");
    var h = hotTag.height();
    hotTag.css("height", "35px");
    var hotTagAll = $(".hotTag").find("li");
    var tagInput = $(".tagInput");

    var flag = 1;
    slideDownBtn.on("click",function(){
        if(1 == flag)
        {
            hotTag.animate({
                height: h + "px"
            }, 500);
            $(this).removeClass();
            $(this).addClass("hotTagSlideUp");
            flag = 2;
        }
        else if(2 == flag){
            hotTag.animate({
                height: "35px"
            }, 500);
            $(this).removeClass();
            $(this).addClass("hotTagSlideBtn");
            flag = 1;
        }
    });
    $('#form_tags_input').val('');
    var tagsPlugin = $("#form_tags_input").tagsInput({
    	interactive:false,
        "width":"700px",
        "height":"35px",
        "maxChars":"6",
        placeholderColor:'#888',
        "keySpace":"space"
    });

    if(typeof(tagsArray) != 'undefined' && tagsArray.length > 0){
    	for(index in tagsArray){
    		tagsPlugin.addTag(tagsArray[index]);
    	}
    }

    hotTagAll.on("click", function(){
        if(tagsPlugin.tagExist($(this).text()))return;
        if($('#form_tags_input_tagsinput').find('span.tag').length >= 5)return;
        tagsPlugin.addTag($(this).text());
    });
}
/*提交基本信息*/
function submitForm(withNext){
	var basicInfo = buildParams();
	if(withNext === undefined || !withNext){
		if(getStrLength(basicInfo.productName) < 4 || getStrLength(basicInfo.productName) > 60 || !productNameReg.test(basicInfo.productName)){
			//中文(2-30)
			alert("产品主标题仅可输入4-60个字符（2-30个中文）");
			return;
		}
		if(getStrLength(basicInfo.productSubName) > 60 || !productSubNameReg.test(basicInfo.productSubName)){
			alert("产品副标题不得超过60个字符");
			return;
		}
		if(basicInfo.tripDays != '' && (isNaN(basicInfo.tripDays) || parseInt(basicInfo.tripDays) == 0 || !dayNumReg.test(basicInfo.tripDays))){
			alert("天数请输入2位以内数字,且不为0");
			return;
		}
		if(basicInfo.tripNights != '' && (isNaN(basicInfo.tripNights) || !dayNumReg.test(basicInfo.tripNights))){
			alert("晚数请输入2位以内数字");
			return;
		}
		if((basicInfo.tripNights === '' || parseInt(basicInfo.tripNights) == 0) && $('#contents_checkbox label.active[value="HOTEL"]').length > 0){
			alert("晚数大于0才可以勾选产品内容的[酒店]选项");
			return;
		}
//		if(basicInfo.highLights !== undefined && basicInfo.highLights.length > 0){
//			for(var i = 0; i < basicInfo.highLights.length; i++){
//				var hl = basicInfo.highLights[i];
//				if(getStrLength(hl) <2 || getStrLength(hl) > 100){
//					alert("体验亮点仅可输入2-100个字符（1-50个中文）");
//					return;
//				}
//			}
//		}
		basicInfo.withNext = false;;
	}else{
		if(!checkParams(basicInfo))return;
		basicInfo.withNext = true;
	}
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/basicInfo/save' ,
	    data: JSON.stringify(basicInfo) ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    async:false,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		if(withNext === undefined || !withNext){
	    			window.location.href = basepath + "/product/basicInfo/edit/"+result.res_msg;
	    		}else{
	    			if(basicInfo.nature == 'PACKAGE'){
	    				window.location.href = basepath + "/product/cost/edit/"+result.res_msg;
	    			}else{
	    				window.location.href = basepath + "/product/cost2/edit/"+result.res_msg;
	    			}
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
	if(!strNB(params.productName)){
		alert("产品主标题必填");
		return false;
	}else if(getStrLength(params.productName) < 4 || getStrLength(params.productName) > 60 || !productNameReg.test(params.productName)){
		alert("产品主标题仅可输入4-60个字符（2-30个中文）");
		return false;
	}
	if(!strNB(params.productSubName)){
		alert("产品副标题必填");
		return false;
	}else if(getStrLength(params.productSubName) > 60 || !productSubNameReg.test(params.productSubName)){
		alert("产品副标题不得超过60个字符");
		return;
	}
	if(!strNB(params.recommendWords)){
		alert("推荐语必填");
		return false;
	}else if(getStrLength(params.recommendWords) > 60){
		alert("推荐语言不得超过60个字符");
		return;
	}
	if(!strNB(params.theme)){
		alert("主题必填");
		return false;
	}
	if(isNaN(params.tripDays) || parseInt(params.tripDays) == 0 || !dayNumReg.test(params.tripDays)){
		alert("天数请输入2位以内数字,且不为0");
		return false;
	}
	if(isNaN(params.tripNights) || !dayNumReg.test(params.tripNights)){
		alert("晚数请输入2位以内数字");
		return false;
	}
	if((params.tags !== undefined) && params.tags.length >0){
		for(var i=0; i<params.tags.length; i++){
			var tag1 = params.tags[i];
			for(var j=0; j<params.tags.length; j++){
				if(i != j){
					var tag2 = params.tags[j];
					if(tag1 == tag2){
						alert("["+tag1+"]标签重复了");
						return false;
					}
				}
			}
		}
	}else{

	}
	if(!strNB(params.from)){
		alert("出发地必填");
		return false;
	}
	if(params.to === undefined || params.to.length <= 0){
		alert("目的地必填");
		return false;
	}
	if(!strNB(params.destinationType)){
		alert("目的地类型必填");
		return false;
	}
	if(!strNB(params.nature)){
		alert("产品性质必填");
		return false;
	}
	if(params.contents === undefined || params.contents.length <=0){
		alert("产品内容必填");
		return false;
	}
	if(params.highLightTitles === undefined || params.highLightTitles.length <4){
		alert("体验亮点标题四条必填");
		return false;
	}else{
		for(var i = 0; i < params.highLightTitles.length; i++){
			if(strB(params.highLightTitles[i])){
				alert("体验亮点标题不能为空");
				return false;
			}
		}
	}
	if(params.highLights === undefined || params.highLights.length <4){
		alert("体验亮点四条必填");
		return false;
	}else{
		for(var i = 0; i < params.highLights.length; i++){
			var hl = params.highLights[i];
			if(getStrLength(hl) <2 || getStrLength(hl) > 100 ){
				alert("体验亮点仅可输入2-100个字符（1-50个中文）");
				return false;
			}
		}
	}
	if(strB(params.lightColor)){
		alert("亮点配色必填");
		return false;
	}else if(!RGBReg.test(params.lightColor)){
		alert("亮点配色为6位数字，字母组合");
		return false;
	}
	if(params.images === undefined || params.images.length <=0){
		alert("必须上传一张高清大图");
		return false;
	}else if(params.images.length >1){
		alert("至多上传一张高清大图");
		return false;
	}
	if(params.titleImages === undefined || params.titleImages.length <=0){
		alert("必须上传一张标题图层");
		return false;
	}else if(params.titleImages.length >1){
		alert("至多上传一张标题图层");
		return false;
	}
	return flag;
}
/*拼装参数*/
function buildParams(){
	var basicInfoVo = {};
	basicInfoVo.id = $('#productId_hidden').val();
	basicInfoVo.pid  = $('#productPid_hidden').val();
	basicInfoVo.status = $('#status_hidden').val();
	basicInfoVo.progress = parseInt($('#progress_hidden').val());
	basicInfoVo.productName  = $('#productName_input').val();
	basicInfoVo.productSubName  = $('#productSubName_input').val();
	basicInfoVo.theme = $('#productTheme_dropdown').find('li.active a').html();
	basicInfoVo.tripDays  = $('#tripDays_input').val();
	basicInfoVo.tripNights  = $('#tripNights_input').val();
	basicInfoVo.recommendWords = $('#recommendWords').val();
	var tags = [];
	$('#form_tags_input_tagsinput').find('span.tag').each(function(){
		var tag = $(this).find('span').html().trim();
		tag = tag.replace('&nbsp;&nbsp;','');
		if(tag != '')tags.push(tag);
	});
	basicInfoVo.tags  = tags;
	basicInfoVo.from  = $('#from_input').find('li.active a').html();
	var toContinent = [];
	var toCountry = [];
	var toCities = [];
	$('#to_labels').find('label span.closeFonts').each(function(){
		if($(this).html() != ''){
			toContinent.push($(this).attr('continent'));
			toCountry.push($(this).attr('country'));
			toCities.push($(this).html());
		}
	});
	basicInfoVo.to  = toCities;
	basicInfoVo.toContinent = toContinent;
	basicInfoVo.toCountry = toCountry;
	basicInfoVo.nature  = $('#nature_radio').find('label.active').attr('value');
	basicInfoVo.destinationType = $('#destination_radio').find('label.active').attr('value');
	var contents = [];
	$('#contents_checkbox').find('label.active').each(function(){
		if($(this).attr('value') != ''){
			contents.push($(this).attr('value'));
		}
	});
	basicInfoVo.contents = contents;
	var highLights = [];
	var highLightTitles = [];
	$('#highLights_inputs').find('textarea').each(function(){
		highLights.push($(this).val());
		highLightTitles.push($(this).prev().val());
	});
	basicInfoVo.highLights = highLights;
	basicInfoVo.highLightTitles = highLightTitles;
	basicInfoVo.lightColor = $("#lightColor_input").val();
	var images = [];
	$('#imgUl').find('li img').each(function(){
		var imgId = $(this).attr('value');
		if(typeof(imgId) != undefined && imgId != ''){
			images.push(imgId);
		}
	});
	basicInfoVo.images  = images;

	var titleImages = [];
	$('#titleImgUl').find('li img').each(function(){
		var imgId = $(this).attr('value');
		if(typeof(imgId) != undefined && imgId != ''){
			titleImages.push(imgId);
		}
	});
	basicInfoVo.titleImages  = titleImages;
	var detailTitleImages = [];
	$('#detaiTitleImgUl').find('li img').each(function(){
		var imgId = $(this).attr('value');
		if(typeof(imgId) != undefined && imgId != ''){
			detailTitleImages.push(imgId);
		}
	});
	basicInfoVo.detailTitleImages  = detailTitleImages;
	return basicInfoVo;
}
/*上传图片*/
function uploadImg(file){
	var varAllImgExt="image/jpeg,image/png";//全部图片格式类型
	var varAllImgMaxSize = 2 * 1024 * 1024 ;
	if (file.files && file.files[0]){
		if(varAllImgExt.indexOf(file.files[0].type) == -1){
			alert('非法格式图片') ;
			$(file).val("") ;
			return ;
		}
		if(file.files[0].size > varAllImgMaxSize){
			alert('图片大小限制2M') ;
			$(file).val("") ;
			return ;
		}
	}
	$(file).parents('form').ajaxSubmit({
		success: function(data){
	    	if(data.res_code == 'success'){
	    		var imgLi = '<li><img src="'+ mediaserver +'imageservice?mediaImageId='+ data.res_msg +'" value="'+data.res_msg+'">'
	    					+'<div class="hoverDelete" onclick="rmImgSelf(this);"><i class="delIcon"></i>删除</div></li>';
	    		$(file).parents('tr').next().find('ul').prepend(imgLi);
	    		$(file).parents('tr').css('display','none');
	    		$(file).parents('tr').next().css('display', '');
	    		var imgStyle = $(file).attr('imgStyle');
	    		$(file).parents('tr').next().find('ul li img').attr('style', $(file).attr('imgStyle'));

//	    		var newForm = '<form action="'+ basepath +'/upload/image" method="post" enctype="multipart/form-data"><ul class="hotelImgList clearfix"><li>';
//	    		newForm += '<input type="file" name="imageInputer" class="upLoading" onchange="uploadImg(this);" imgStyle="'+ imgStyle +'">';
//	    		newForm += '</li></ul></form>';
	    		var $td = $(file).parents('td');
	    		var newForm = $td.find('form').prop('outerHTML');
	    		$td.find('form').remove();
	    		$td.append(newForm);
	    	}else{
	    		alert(data.res_msg);
	    		$(file).val("") ;
	    	}
	    }
	});
}
/**/
function setReadonly(){
	$("#tripNights_input").prop('readonly',true);
	$("#tripDays_input").prop('readonly',true);
	$("#from_input").find("li a").unbind("click");
	$(".checkboxContent .checkboxInfo").unbind('click');
    $(".radioContent .radio").unbind('click');
    //$('#to_labels').find("label span.closeIcon").attr('onclick','');
}
/*标签自我删除*/
function closeSelf(obj){
	$(obj).parent().remove();
}
/*图片自我删除*/
function rmImgSelf(obj){
	$(obj).parents('tr').css('display', 'none');
	$(obj).parents('tr').prev().css('display','');
	$(obj).parent().remove();
}
function fixStyle(obj,style){
	if(style !== undefined){
		$(obj).parents('tr').next().find('ul li img').attr('style', style);
	}
}