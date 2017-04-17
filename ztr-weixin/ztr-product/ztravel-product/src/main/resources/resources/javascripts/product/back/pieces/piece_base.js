$(function(){

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

	initProductType();

});

function initProductType(){
	var type = $('#productType_hidden').val();
	if (type === undefined || type == '') {
		$('#pieceType_dropdown').find('li[value=TICKET]').addClass("active");
		$('#productTypeName').html("门票");
	} else {
		$('#pieceType_dropdown').find('li').each(function(){
			if ($(this).attr("value") == type) {
				$(this).addClass("active");
				$('#productTypeName').html($(this).find('a').html());
			}
	    });
	}
}

/*标签自我删除*/
function closeSelf(obj){
	$(obj).parent().remove();
}

/*提交基本信息*/
function submitForm(withNext){
	var basicInfo = buildParams();
	if(withNext === undefined || !withNext){
		if(getStrLength(basicInfo.pname) < 1 || getStrLength(basicInfo.pname) > 100){
			alert("产品标题仅可输入1-100个字符");
			return;
		}
		basicInfo.withNext = false;
	}else{
		if(!checkParams(basicInfo)) return;
		basicInfo.withNext = true;
	}

	var nature = basicInfo.nature;
	if (nature === undefined || nature == '') {
		if (basicInfo.type == 'VISA') {
			nature = basicInfo.type;
		} else {
			nature = 'UNVISA';
		}
	}

	$.ajax({
	    type: 'POST',
	    url: basepath + '/pieces/basicInfo/save' ,
	    data: JSON.stringify(basicInfo) ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    async:false,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		if(withNext === undefined || !withNext){
	    			window.location.href = basepath + "/pieces/basicInfo/edit/" +nature + "/" + result.res_msg;
	    		}else{
	    			window.location.href = basepath + "/pieces/detailInfo/edit/" +nature + "/" + result.res_msg;
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
	if(!strNB(params.pname)){
		alert("产品标题必填");
		return false;
	}else if(getStrLength(params.pname) < 1 || getStrLength(params.pname) > 100){
		alert("产品标题仅可输入1-100个字符");
		return false;
	}
	if(params.toCity === undefined || params.toCity.length <= 0){
		alert("目的地必填");
		return false;
	}
	if(!strNB(params.type)){
		alert("产品种类必填");
		return false;
	}
	return flag;
}

/*拼装参数*/
function buildParams(){
	var basicInfoVo = {};
	basicInfoVo.id = $('#productId_hidden').val();
	basicInfoVo.pid  = $('#productPid_hidden').val();
	basicInfoVo.progress = parseInt($('#progress_hidden').val());
	basicInfoVo.nature  = $('#productNature_hidden').val();
	basicInfoVo.pname  = $.trim($('#pieceName_input').val());
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
	basicInfoVo.toCity  = toCities;
	basicInfoVo.toCountry = toCountry;
	basicInfoVo.toContinent = toContinent;
	basicInfoVo.type = $('#pieceType_dropdown').find('li.active').attr("value");

	return basicInfoVo;
}
