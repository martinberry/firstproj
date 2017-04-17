var lock = false;

var isContactorEdit = false;

$(function(){

	//编辑联系人信息
    $(".editContactInfo").click(function(){
    	isContactorEdit = true;
    	getContactorData();
        $(".contactInfoTab").hide();
        $(".editContactInfoContent").find('.verifyStyle').hide();
        $(".editContactInfoContent").slideDown(function(){
            $(window).trigger("resize");
        });
    });

    $(".editContactInfoTab .commonBtn").click(function(){
    	if ($("#province").html() == "" || $("#city").html() == "" || $("#county").html() == "") {
    		$(".dropdown-error").show();
		}else{
			$(".dropdown-error").hide();
		}
    	if($('.editContactInfoContent .verifyStyle:visible').size() == 0){
			saveContactInfo();
		}
    	isContactorEdit = false;
    });

    $(".editContactInfoTab .cancelBtn").click(function(){
        isContactorEdit = false;
        $(this).parents(".editContactInfoContent").slideUp(function(){
            $(".contactInfoTab").show();
            $(window).trigger("resize");
        });
    });

    //初始化省/直辖市
    ajaxLoadProvince();

    //省/直辖市
 	$("#province-menu").on("click", "li", function(){

		$("#city-menu li").remove();
		$('#city').html('');
		$("#county-menu li").remove();
		$('#county').html('');

		$(this).addClass("active");
	    $(this).siblings().removeClass();
		$("#province").html($("#province-menu").children("li[class='active']").children().html());

		ajaxLoadCity($.trim($("#province").html()));

 	});

 	  //市
 	$("#city-menu").on("click", "li", function(){

		$("#county-menu li").remove();
		$('#county').html('');

		$(this).addClass("active");
	    $(this).siblings().removeClass();
		$("#city").html($("#city-menu").children("li[class='active']").children().html());

 		ajaxLoadCounty($.trim($("#city").html()));

 	});

 	$("#county-menu").on("click", "li", function(){
 		$(this).siblings().removeClass();
 		$(this).addClass("active");
		$("#county").html($("#county-menu").children("li[class='active']").children().html());
 	});

});

function getContactorData(){

	ajaxLoadProvince();
	ajaxLoadCity($.trim($("#provinceTd").html()));
	ajaxLoadCounty($.trim($("#cityTd").html()));

	$('input[name="contactorId"]').val($('input[name="contactorIdTd"]').val());
	$('input[name="contactor"]').val($('#contactorTd').html());
	$('input[name="phone"]').val($('#phoneTd').html());
	$('input[name="email"]').val($('#emailTd').html());
	$('#province').html($('#provinceTd').html());
	$('#city').html($('#cityTd').html());
	$('#county').html($('#countyTd').html());
	$('input[name="address"]').val($('#addressTd').html());
}

function ajaxLoadProvince(){
	$.ajax({
	    type: "POST",
	    url: basepath + '/order/travelConfirm/loadProvince',
	    headers : {
		    'Accept' : 'application/json',
		    'Content-Type' : 'application/json'
	    },
	    success: function(result){
	    	$("#province-menu").html(result);
	    }
    });
}

function ajaxLoadCity(provinceName){

	if(provinceName !=''){
			$.ajax({
		    type: "POST",
		    url: basepath + '/order/travelConfirm/loadCity',
		    data: provinceName,
		    headers : {
			    'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    success: function(result){
		    	$("#city-menu").html(result);
		    }
	    });
		}
}

function ajaxLoadCounty(cityName){
	if(cityName !=''){
			$.ajax({
		    type: "POST",
		    url: basepath + '/order/travelConfirm/loadCounty',
		    data: cityName,
		    headers : {
			    'Accept' : 'application/json',
			    'Content-Type' : 'application/json'
		    },
		    success: function(result){
		    	$("#county-menu").html(result);
		    }
	    });
		}
}

function saveContactInfo(){

	if(lock){
		return ;
	}else{
		lock = true;
	}
	 var contactor = {};
	 contactor.id= $.trim($('input[name="contactorIdTd"]').val());
	 contactor.contactor= $.trim($('input[name="contactor"]').val());
	 contactor.phone= $.trim($('input[name="phone"]').val());
	 contactor.email= $.trim($('input[name="email"]').val());
	 contactor.province= $.trim($('#province').html());
	 contactor.city= $.trim($('#city').html());
	 contactor.county= $.trim($('#county').html());
	 contactor.address= $.trim($('input[name="address"]').val());

	 var orderId = $('#orderId').val();
	 var data = {
			"orderId":orderId,
			"orderContactor": contactor
	}

	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/updateContactor',
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : JSON.stringify(data),
		   dataType: "json",
		   success: function(resp){
			   lock = false;
			   if (resp.res_code == 'SF_ORDE_1001') {
				   $(".editContactInfoContent").slideUp(function(){
			            $(window).trigger("resize");
			        });
				   $(".contactInfoTab").show();
			       getContactInfoList();
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

function getContactInfoList(){
	var orderId = $('#orderId').val();
	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/contactor/refresh/' + orderId,
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : orderId,
		   dataType: "html",
		   success: function(json){
			   $(".contactInfoTab tbody").html(json);
				 }
		});
}