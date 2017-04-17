var priceReg =/^(([1-9][0-9]{0,4}|0)(\.[0-9]{0,2})?)$/;

$(function(){

	$('button.commonButton.red-fe6869Button.top').click(function(){
		submitForm(false);
	});
	$('button.commonButton.whiteBtn.top').click(function(){
		submitForm(true);
	});

    $("body").on("click",".checkboxContent .checkboxInfo",function(e){
        var childItem = $(e.target).closest("tr").find('input[type="text"]');
        if($(this).hasClass("active")){
            childItem.prop("disabled", false);
        }else{
            childItem.prop("disabled", true);
        }
    });

    $("body").on("click",".addrow",function(e){
        var addContent = $(".addSectionTemp").html();
        $(this).parents(".sectionDiv").after(addContent);

    });
    $("body").on("click",".delrow",function(){
        $(this).parents(".sectionDiv").remove();
    });

});

/*提交基本信息*/
function submitForm(withNext){
	var priceInfo = buildParams();
	if(withNext === undefined || !withNext){
		priceInfo.withNext = false;
	}else{
		priceInfo.withNext = true;
	}
	if(!checkParams(priceInfo)) return;

	var nature = priceInfo.nature;
	var priceType = priceInfo.priceType;
	$.ajax({
	    type: 'POST',
	    url: basepath + '/pieces/priceInfo/save',
	    data: JSON.stringify(priceInfo),
	    contentType : 'application/json',
	    dataType: 'json',
	    async:false,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		if(withNext === undefined || !withNext){
	    			window.location.href = basepath + "/pieces/priceInfo/edit/cost/" +nature + "/" + priceInfo.id;
	    		}else{
	    			window.location.href = basepath + "/pieces/priceInfo/edit/sale/" +nature + "/" + priceInfo.id;
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
	for(var i in params.priceInfos) {
		var price = params.priceInfos[i];
		if(price.withNext){
			if(!strNB(price.name)){
				alert("价格类型名称必填");
				return false;
			}
			if(!strNB(price.adultCost)){
				alert("成人底价必填");
				return false;
			}
			if(price.hasChildPrice){
				if(!strNB(price.childCost)){
					alert("儿童底价必填");
					return false;
				}
			}
		}
		if(strNB(price.name) && (price.name.length <= 0 ||price.name.length > 30)){
			alert("价格类型名称仅可输入1-30个字符");
			return false;
		}
		if (strNB(price.adultCost) && !priceReg.test(price.adultCost)) {
			alert("成人底价范围为：0-99999，可输入两位小数");
			return false;
		}
		if(price.hasChildPrice){
			if (strNB(price.childCost) && !priceReg.test(price.childCost)) {
				alert("儿童底价范围为：0-99999，可输入两位小数");
				return false;
			}
		}
	}
	return flag;
}

function checkSale(opt,maxlen){
	var re = /^[1-9]+[0-9]*]*$/;
	if(!re.test(opt) || parseInt(opt).toString().length < 1 || parseInt(opt).toString().length > maxlen){
		return false ;
	}else{
		return true ;
	}
}

/*拼装参数*/
function buildParams(){
	var priceInfoVo = {};
	priceInfoVo.id = $('#productId_hidden').val();
	priceInfoVo.pid  = $('#productPid_hidden').val();
	priceInfoVo.progress = parseInt($('#progress_hidden').val());
	priceInfoVo.nature  = $('#productNature_hidden').val();
	priceInfoVo.priceType  = $('#priceType_hidden').val();

	var priceInfos = [];
	$(".changeMainContent").find("section").each(function(){
		var price = {};
		if ($(this).find("input[name='priceId_hidden']").val() != undefined) {
			price.id = $(this).find("input[name='priceId_hidden']").val();
		}
		price.name = $.trim($(this).find("input[name='priceName_input']").val());
		price.adultCost = $.trim($(this).find("input[name='adultCost_input']").val());
		if ($(this).find(".checkboxContent label").hasClass("active")) {
			price.hasChildPrice = true;
			price.childCost = $.trim($(this).find("input[name='childCost_input']").val());
		} else {
			price.hasChildPrice = false;
		}
		priceInfos.push(price);
	});
	priceInfoVo.priceInfos = priceInfos;

	return priceInfoVo;
}