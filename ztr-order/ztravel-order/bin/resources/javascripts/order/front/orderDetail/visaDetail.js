/**
 *
 */

$(function(){

	//订单状态条
	$('.orderFlowModel').find('.passed').last().addClass('current');
	$('.orderFlowModel').find('.current').removeClass('passed');

	$('.price').children('th').attr('rowspan', $('.price').size());

	//初始化联系人信息列表
	getContactInfoList();

    //编辑联系人信息
    $("#edit-contact").click(function(){
    	if($(".contactInfoTab tbody tr").size() >0){
    		getContactInfoData();
            $(".contactInfoTab").hide();
            $(".editContactInfoContent").find('.verifyStyle').hide();
            $(".editContactInfoContent").slideDown(function(){
                $(window).trigger("resize");
            });
    	}
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
    });

    $(".editContactInfoTab .cancelBtn").click(function(){
        $(".editContactInfoContent").slideUp(function(){
            $(window).trigger("resize");
        });
        $(".contactInfoTab").show();
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



function saveContactInfo(){
	 var data = {};
	 data.id= $.trim($('input[name="contactorId"]').val());
	 data.contactor= $.trim($('input[name="contactor"]').val());
	 data.phone= $.trim($('input[name="phone"]').val());
	 data.email= $.trim($('input[name="email"]').val());
	 data.province= $.trim($('#province').html());
	 data.city= $.trim($('#city').html());
	 data.county= $.trim($('#county').html());
	 data.address= $.trim($('input[name="address"]').val());

	 $.ajax({
		   type: "POST",
		   url: basepath + '/visaorder/front/contactor/update',
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : JSON.stringify(data),
		   dataType: "json",
		   success: function(resp){
			   if (resp.res_code == 'SF_ORDE_1001') {
				   _paq.push(['trackEvent', 'myorderpage', 'ztrchangeconinfo', 'SUCCESS']);
				   $(".editContactInfoContent").slideUp(function(){
			            $(window).trigger("resize");
			        });
				   $(".contactInfoTab").show();
			       getContactInfoList();
				}else if (resp.res_code == 'FF_ORDE_1005') {
					   _paq.push(['trackEvent', 'myorderpage', 'ztrchangeconinfo', 'FAIL']);
					   alert(resp.res_msg);
					}else{
					_paq.push(['trackEvent', 'myorderpage', 'ztrchangeconinfo', 'FAIL']);
					alert("网络异常，请稍后重试！");
				}
				 }
		});
}

function getContactInfoList(){
	var orderId = $.trim($('input[name="orderId"]').val());
	 $.ajax({
		   type: "POST",
		   url: basepath + '/visaorder/front/contactor/list',
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


function ajaxLoadProvince(){
	$.ajax({
	    type: "POST",
	    url: basepath + '/visaorder/front/loadProvince',
//	    data: {},
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
		    url: basepath + '/visaorder/front/loadCity',
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
		    url: basepath + '/visaorder/front/loadCounty',
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

function getContactInfoData(){

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




