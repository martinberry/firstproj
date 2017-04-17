var lock = false;

var isFeesDetailEdit = false;

$(function(){

	$('.defaultRowSpan').children('th').attr('rowspan', $('.defaultRowSpan').size());

	//价格明细
    $(".priceEditBtn").click(function(){

    	isFeesDetailEdit = true;
		getFeesDetailData();
        $(this).hide();
        $priceEdit = $(this).parents(".commonSmallTitleModel").siblings(".commonStyle");
        $priceEdit.find(".requiredContent").hide();
        $priceEdit.find(".requiredEditContent").show();
        $priceEdit.find(".requiredSave").show();
        $priceEdit.find(".requiredAddDelete").show();
    });

    $(".requiredSave .commonBtn").click(function(){
    	if ($('.feesDetailTab .verifyStyle:visible').size() == 0) {
			saveFeesDetailData(this);
		}
    	isFeesDetailEdit = false;
    });

    $(".requiredSave .cancelBtn").click(function(){
    	isFeesDetailEdit = false;
        $(this).parents(".requiredSave").hide();
        $requiredCcancel = $(this).parents(".requiredSave").siblings(".feesDetailTab");
        $requiredCcancel.find(".requiredContent").show();
        $requiredCcancel.find(".requiredEditContent").hide();
        $requiredCcancel.find(".requiredAddDelete").hide();
        $(this).parents(".commonStyle").siblings(".commonSmallTitleModel").find(".priceEditBtn").show();

    	$(".requiredEditContent").each(function(){
    		$(this).find(".verifyStyle").hide();
    	});
    });

    validateItems();

});

function validateItems(){
	$(".requiredEditContent").delegate('input[name="packagePriceInputer"]', "change", function(){
    	var val = $.trim($(this).val());
    	if(checkSale(val,5)){
    		$(this).siblings(".price-error").hide();
    		var packageNum = new Number($("#packageNum").html());
    		$("#totalPackagePriceInputer").html(new Number(val) * packageNum + ".00");
    	}else{
    		$(this).siblings(".price-error").show();
    	}
    });

    $(".requiredEditContent").delegate('input[name="adultPriceInputer"]', "change", function(){
    	var val = $.trim($(this).val());
    	if(checkSale(val,5)){
    		$(this).siblings(".price-error").hide();
    		var adultNum = new Number($("#adultNum").html());
    		$("#totalAdultPriceInputer").html(new Number(val) * adultNum + ".00");
    	}else{
    		$(this).siblings(".price-error").show();
    	}
    });

    $(".requiredEditContent").delegate('input[name="childrenPriceInputer"]', "change", function(){
    	var val = $.trim($(this).val());
    	if(checkSale(val,5)){
    		$(this).siblings(".price-error").hide();
    		var childNum = new Number($("#childNum").html());
    		$("#totalChildPriceInputer").html(new Number(val) * childNum + ".00");
    	}else{
    		$(this).siblings(".price-error").show();
    	}
    });

    $(".requiredEditContent").delegate('input[name="singleRoomDiffInputer"]', "change", function(){
    	var val = $.trim($(this).val());
    	if(checkSale(val,5)){
    		$(this).siblings(".price-error").hide();
    		var singleNum = new Number($("#singleNum").html());
    		$("#totalSingleDiffInputer").html(new Number(val) * singleNum + ".00");
    	}else{
    		$(this).siblings(".price-error").show();
    	}
    });
}

function getFeesDetailData(){
	if($("#packagePrice").length > 0){
		$('input[name="packagePriceInputer"]').val($('#packagePrice').val());
		$('#totalPackagePriceInputer').html($('#totalPackagePrice').val());
	}
	if($("#adultPrice").length > 0){
		$('input[name="adultPriceInputer"]').val($('#adultPrice').val());
		$('#totalAdultPriceInputer').html($('#totalAdultPrice').val());
	}
	if($("#childrenPrice").length > 0){
		$('input[name="childrenPriceInputer"]').val($('#childrenPrice').val());
		$('#totalChildPriceInputer').html($('#totalChildPrice').val());
	}
	if($("#singleRoomDiff").length > 0){
		$('input[name="singleRoomDiffInputer"]').val($('#singleRoomDiff').val());
		$('#totalSingleDiffInputer').html($('#totalSingleDiff').val());
	}
}

function checkSale(opt,maxlen){
	var re = /^[1-9]+[0-9]*]*$/;
	if(!re.test(opt) || parseInt(opt).toString().length < 1 || parseInt(opt).toString().length > maxlen){
		return false ;
	}else{
		return true ;
	}
}

function saveFeesDetailData(scope){
	if(lock){
		return ;
	}else{
		lock = true;
	}
	 var price = {};
	 if($("#packagePrice").length > 0){
		 var packagePrice = $('input[name="packagePriceInputer"]').val().trim();
		 price.packageId = $("#packageId").val();
		 price.packageName = $("#packageName").html();
		 price.sumNum = $("#sumNum").val();
		 if ($("#sumNum").val() == '') {
			 price.sumNum = 1;
		 }

		 $("#totalPackagePriceInputer").html(new Number(packagePrice) * price.sumNum + ".00");
		 price.totalPackagePrice = $('#totalPackagePriceInputer').html().trim();
	 }

	 if($("#adultPrice").length > 0){
		 var adultPrice = $('input[name="adultPriceInputer"]').val().trim();
		 price.adultNum = $("#adultNum").html().trim();
		 price.adultPrice = adultPrice;

		 $("#totalAdultPriceInputer").html(new Number(adultPrice) * price.adultNum + ".00");
		 price.totalAdultPrice = $('#totalAdultPriceInputer').html();
	 }

	 if($("#childrenPrice").length > 0){
		 var childrenPrice = $('input[name="childrenPriceInputer"]').val().trim();
		 price.childNum = $("#childNum").html().trim();
		 price.childrenPrice = childrenPrice;

		 $("#totalChildPriceInputer").html(new Number(childrenPrice) * price.childNum + ".00");
		 price.totalChildPrice = $('#totalChildPriceInputer').html();
	 }

	 if($("#singleRoomDiff").length > 0){
		 var singleRoomDiff = $('input[name="singleRoomDiffInputer"]').val().trim();
		 price.singleNum = $("#singleNum").html().trim();
		 price.singleRoomDiff = singleRoomDiff;

		 $("#totalSingleDiffInputer").html(new Number(singleRoomDiff) * price.singleNum + ".00");
		 price.totalSingleDiff = $('#totalSingleDiffInputer').html();
	 }

	 if($("#couponId").length > 0){
		 price.couponId = $("#couponId").val();
		 price.couponName = $('#couponName').html();
	 }

	 var orderId = $('#orderId').val();
	 var data = {
			"orderId":orderId,
			"price": price
	}

	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/updateFeesDetail',
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : JSON.stringify(data),
		   dataType: "json",
		   success: function(resp){
			   lock = false;
			   if (resp.res_code == 'SF_ORDE_1002') {
				   refreshFeesDetail(scope);
				}else if (resp.res_code == 'FF_ORDE_1005') {
					alert(resp.res_msg);
				}else if (resp.res_code == 'FAILURE') {
					alert(resp.res_msg);
				}else{
					alert("网络异常，请稍后重试！");
				}
		   },
		   error: function(result){
			   lock = false;
		   }
	 });
}

function refreshFeesDetail(scope){
	var orderId = $('#orderId').val();
	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/feesDetail/refresh/' + orderId,
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : orderId,
		   dataType: "html",
		   success: function(json){
			   $(".feesDetailTab tbody").html(json);
			   $('.defaultRowSpan').children('th').attr('rowspan', $('.defaultRowSpan').size());

			   $(scope).parents(".requiredSave").hide();
		        $requiredCcancel = $(scope).parents(".requiredSave").siblings(".feesDetailTab");
		        $requiredCcancel.find(".requiredContent").show();
		        $requiredCcancel.find(".requiredEditContent").hide();
		        $requiredCcancel.find(".requiredAddDelete").hide();
		        $(scope).parents(".commonStyle").siblings(".commonSmallTitleModel").find(".priceEditBtn").show();

		        validateItems();
		   }
	 });
}