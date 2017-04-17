var priceReg =/^(([1-9][0-9]{0,4}|0)(\.[0-9]{0,2})?)$/;

$(function(){

	$('button.commonButton.red-fe6869Button.top').click(function(){
		submitForm(false);
	});
	$('button.commonButton.whiteBtn.top').click(function(){
		submitForm(true);
	});

	$("body").on("click",".saleGroup button",function(){
        var adult = $(this).prevAll(".adult"),
                child = $(this).prevAll(".child");
        if($(this).is(".add")){
            $('<span class="salePrice">'+ adult.val()+'</span>').insertAfter(adult);
            if(child.is(":disabled")){
                $('<span class="salePrice">--</span>').insertAfter(child);
            }else{
                $('<span class="salePrice">'+ child.val()+'</span>').insertAfter(child);
            }
            $(this).prevAll("input").hide();
        }else if($(this).is(".edit")){
            $(this).prevAll(".salePrice").remove();
            $(this).prevAll("input").show();
        }
        $(this).hide().siblings("button").show();
    });

});

/*提交基本信息*/
function submitForm(withNext){
	var priceInfo = buildParams();
	if(!checkParams(priceInfo)) return;
	if(withNext === undefined || !withNext){
		priceInfo.withNext = false;
	}else{
		priceInfo.withNext = true;
	}
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
	    			window.location.href = basepath + "/pieces/priceInfo/edit/" +priceType + "/" +nature + "/" + priceInfo.id;
	    		}else{
	    			window.location.href = basepath + "/pieces/additionalInfo/edit/" +nature + "/" + priceInfo.id;
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

		if (price.adultPrice != undefined && price.adultPrice != "" && !priceReg.test(price.adultPrice)) {
			alert("成人售价范围为：0-99999，可输入两位小数");
			return false;
		}
		if (price.childPrice != undefined && price.childPrice != "" && !priceReg.test(price.childPrice)) {
			alert("儿童售价范围为：0-99999，可输入两位小数");
			return false;
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
	$(".changeMainContent").find(".priceContain").each(function(){
		var price = {};
		if ($(this).find("input[name='priceId_hidden']").val() != undefined) {
			price.id = $(this).find("input[name='priceId_hidden']").val();
		}
		if ($(this).find("input[name='priceName_hidden']").val() != undefined) {
			price.name = $(this).find("input[name='priceName_hidden']").val();
		}
		if ($(this).find("input[name='hasChildPrice_hidden']").val() != undefined && $(this).find("input[name='hasChildPrice_hidden']").val() == "true") {
			price.hasChildPrice = true;
		} else {
			price.hasChildPrice = false;
		}

		price.adultPrice = $.trim($(this).find("input[name='adultPrice_input']").val());
		if (price.hasChildPrice) {
			price.childPrice = $.trim($(this).find("input[name='childPrice_input']").val());
		}
		priceInfos.push(price);
	});
	priceInfoVo.priceInfos = priceInfos;

	return priceInfoVo;
}