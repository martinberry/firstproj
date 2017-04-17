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

    //初始化游客信息列表
    getTouristInfoList();

	//游客信息　证件类型
	$(".editTouristInfoContent").delegate(".dropdown-menu li", "click", function(){
		$(this).parent('.dropdown-menu').children('li').removeClass('active');
		$(this).addClass('active');
		$(this).parent('.dropdown-menu').parent('.dropdown').find('.credentialType').html($(this).children('a').html());
		$(this).parent('.dropdown-menu').parent('.dropdown').find('.credentialType').attr('data-val', $(this).attr('data-val'));
		$(this).parent('.dropdown-menu').parent('.dropdown').siblings('input[name="credentialNum"]').val('');
		$(this).parent('.dropdown-menu').parent('.dropdown').siblings(".credentialNum-error").hide();
		$(this).parent('.dropdown-menu').parent('.dropdown').siblings('input[name="credentialNum"]').focus();
	});

	//游客信息　旅客性别
	$(".editTouristInfoContent").delegate(".gender label", "click", function(){
		$(this).parent('.gender').children('label').removeClass('active');
		$(this).addClass('active');
	});

    //编辑游客信息
    $("#edit-tourist").click(function(){
    	$('#saveBtn').hide();
    	if($(".touristInfoTab tbody tr").size() >0){
    		getTouristInfoData();
            $(".touristInfoTab").hide();
            $(".editTouristInfoContent").find('.verifyStyle').hide();
            $(".editTouristInfoContent").slideDown(function(){
                $(window).trigger("resize");
            });
    	}
    });

    $(".editTouristInfoContent").delegate(".commonBtn", "click", function(){
    	if ($('.editTouristInfoContent .verifyStyle:visible').size() == 0) {
			saveTouristInfo();
		}
    });

    $(".editTouristInfoContent").delegate(".cancelBtn", "click", function(){
    	$(".editTouristInfoContent").slideUp(function(){
    		$(window).trigger("resize");
    	});
    	$(".touristInfoTab").show();
    });

    //编辑床型
    $("#edit-bedType").click(function(){
    	getBedTypeData();
        $(this).hide();
        $(".editBedTypeContent").show();
    });

    $(".editBedTypeContent .sureBtn").click(function(){
    	saveBedType();
    });

    $(".editBedTypeContent .cancelBtn").click(function(){
        $(".editBedTypeContent").hide();
        $(".editBedType").show();
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
 	
 	$("span.editBedTypeContent label").click(function(){
 		if($(this).attr("val") == "double"){
 			$("#bedTips").attr("style","display;");
 		}else{
 			$("#bedTips").attr("style","display: none;");
 		}
 	});

});

function saveBedType(){

	var orderId = $.trim($('input[name="orderId"]').val());
	var bedPrefer = $.trim($('.editBedTypeContent .radioContent').children('.active').attr('val'));

	$.ajax({
		   type: "POST",
		   url: basepath + '/order/front/bedPrefer/update',
//		   headers : {
//				'Accept' : 'application/json',
//				'Content-Type' : 'application/json'
//			},
		   data : "bedPrefer="+bedPrefer+"&orderId="+orderId,
		   dataType: "json",
		   success: function(resp){
			   if (resp.res_code == 'SF_ORDE_1003') {
				   _paq.push(['trackEvent', 'myorderpage', 'ztrchangebedtype', 'SUCCESS']);
				   $(".editBedTypeContent").hide();
			       $(".editBedType").show();
			       $('.editBedType span:first').html($('.editBedTypeContent .radioContent').children('.active').children('.genderSelect').html());
				}else{
					_paq.push(['trackEvent', 'myorderpage', 'ztrchangebedtype', 'FAIL']);
					alert("网络异常，请稍后重试！");
				}
				 }
		});
}

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
		   url: basepath + '/order/front/contactor/update',
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
		   url: basepath + '/order/front/contactor/list',
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

function saveTouristInfo(){
	var list = {};
	var passengers = [];
	$(".editTouristInfo-tab .editTouristInfoTab").each(function (i) {
		var passenger = {};
		 passenger.id= $.trim($(this).find('input[name="passengerId"]').val());
		 passenger.firstName= $.trim($(this).find('input[name="firstName"]').val());
		 passenger.lastName= $.trim($(this).find('input[name="lastName"]').val());
		 passenger.firstEnName= $.trim($(this).find('input[name="firstNameEn"]').val());
		 passenger.lastEnName= $.trim($(this).find('input[name="lastNameEn"]').val());
		 passenger.credentialType= $.trim($(this).find('.credentialType').attr('data-val'));
		 passenger.credentialNum= $.trim($(this).find('input[name="credentialNum"]').val());
		 passenger.credentialDeadLine= $.trim($(this).find('input[name="deadLine"]').val());
		 passenger.country= $.trim($(this).find('input[name="country"]').val());
		 passenger.birthday= $.trim($(this).find('input[name="birthday"]').val());
		 passenger.gender= $.trim($(this).find('span').find('.active').attr('data-val'));
		 passengers[i] = passenger;
		 });
	list.passengers = passengers;

	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/front/passenger/update',
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : JSON.stringify(list),
		   dataType: "json",
//		   traditional: true,
		   success: function(resp){
			   if (resp.res_code == 'SF_ORDE_1002') {
				   _paq.push(['trackEvent', 'myorderpage', 'ztrchangepassinfo', 'SUCCESS']);
				   $(".editTouristInfoContent").slideUp(function(){
			            $(window).trigger("resize");
			        });
			       $(".touristInfoTab").show();
			       getTouristInfoList();
			       }else if (resp.res_code == 'FF_ORDE_1010') {
			    	   _paq.push(['trackEvent', 'myorderpage', 'ztrchangepassinfo', 'FAIL']);
			    	   alert(resp.res_msg);
				       }else{
			    	   _paq.push(['trackEvent', 'myorderpage', 'ztrchangepassinfo', 'FAIL']);
			    	   alert("网络异常，请稍后重试！");
			       }
			   }
			});
	 }

function getTouristInfoList(){
	var orderId = $.trim($('input[name="orderId"]').val());
	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/front/passenger/list',
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : orderId,
//		   dataType: "html",
		   success: function(json){
			   $(".touristInfoTab tbody").html(json);
			   }
		});
}

function isExistNationality(data,i){
	var isExist= false;
	var nationality = $(".nationality").eq(i).val();
	if(null!=nationality ){
		for(var i=0;i<data.length;i++){
			if(data[i].indexOf(nationality) != -1){
				isExist=true;
				break;
				}
			}
		}
	return isExist;
	}

function ajaxLoadProvince(){
	$.ajax({
	    type: "POST",
	    url: basepath + '/order/front/loadProvince',
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
		    url: basepath + '/order/front/loadCity',
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
		    url: basepath + '/order/front/loadCounty',
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

function getTouristInfoData(){

	$(".editTouristInfo-tab").html('');

	$(".touristInfoTab tbody tr").each(function(i){

		if($(".commonOrderModel").find('input[name="isDomestic"]').val() == "1"){
			$("#isDomestic").find('input[name="passengerId"]').val($(this).find('input[name="passengerIdTd"]').val());
			$("#isDomestic").find('.numberFonts').html(""+(i+1));
			$("#isDomestic").find('input[name="firstName"]').first().val($(this).find('.firstNameTd').val());
			$("#isDomestic").find('input[name="lastName"]').val($(this).find('.lastNameTd').val());
			$("#isDomestic").find('input[name="firstNameEn"]').val($(this).find('.firstNameEnTd').val());
			$("#isDomestic").find('input[name="lastNameEn"]').val($(this).find('.lastNameEnTd').val());
			$("#isDomestic").find('.passengerType').html($(this).find('.passengerTypeTd').html());
			$("#isDomestic").find('.credentialType').html($(this).find('.credentialTypeTd').html());
			$("#isDomestic").find('.credentialType').attr('data-val', $(this).find('input[name="credentialType-hidden"]').val());
			$("#isDomestic").find('input[name="credentialNum"]').val($(this).find('.credentialNumTd').html());
			$("#isDomestic").find('input[name="birthday"]').val($(this).find('.birthdayTd').html());
			$("#isDomestic").find(".gender").find('label').removeClass('active');

			if($(this).find('.genderTd').html()=="男"){
				$("#isDomestic").find(".gender").find('label[data-val="MALE"]').addClass('active');
			}else if($(this).find('.genderTd').html()=="女"){
				$("#isDomestic").find(".gender").find('label[data-val="FEMALE"]').addClass('active');
			}

			$("#isDomestic").children('.editTouristInfoTab').clone().appendTo(".editTouristInfo-tab");

		}else{
			$("#isNotDomestic").find('input[name="passengerId"]').val($(this).find('input[name="passengerIdTd"]').val());
			$("#isNotDomestic").find('.numberFonts').html(""+(i+1));
			$("#isNotDomestic").find('input[name="firstName"]').first().val($(this).find('.firstNameTd').val());
			$("#isNotDomestic").find('input[name="lastName"]').val($(this).find('.lastNameTd').val());
			$("#isNotDomestic").find('input[name="firstNameEn"]').val($(this).find('.firstNameEnTd').val());
			$("#isNotDomestic").find('input[name="lastNameEn"]').val($(this).find('.lastNameEnTd').val());
			$("#isNotDomestic").find('.passengerType').html($(this).find('.passengerTypeTd').html());
			$("#isNotDomestic").find('.credentialType').html($(this).find('.credentialTypeTd').html());
			$("#isNotDomestic").find('.credentialType').attr('data-val', $(this).find('input[name="credentialType-hidden"]').val());
			$("#isNotDomestic").find('input[name="credentialNum"]').val($(this).find('.credentialNumTd').html());
			$("#isNotDomestic").find('input[name="deadLine"]').val($(this).find('.credentialDeadLineTd').html());
			$("#isNotDomestic").find('input[name="country"]').val($(this).find('.countryTd').html());
			$("#isNotDomestic").find(".nationalityDropList").val($(this).find('.countryTd').html());
			$("#isNotDomestic").find('input[name="birthday"]').val($(this).find('.birthdayTd').html());
			$("#isNotDomestic").find(".gender").find('label').removeClass('active');

			if($(this).find('.genderTd').html()=="男"){
				$("#isNotDomestic").find(".gender").find('label[data-val="MALE"]').addClass('active');
			}else if($(this).find('.genderTd').html()=="女"){
				$("#isNotDomestic").find(".gender").find('label[data-val="FEMALE"]').addClass('active');
			}

			$("#isNotDomestic").children('.editTouristInfoTab').clone().appendTo(".editTouristInfo-tab");

		}
	});
	$('#saveBtn').show();//显示保存取消按钮
	initDatepicker();//初始化日历控件
	initNationality();//初始化国籍自动补全
}

function getBedTypeData(){

	$(".editBedTypeContent").find('label').removeClass('active');

	if($(".editBedType").children('span:first').html()=="优先大床房"){
		$(".editBedTypeContent").find('label[val="big"]').addClass('active');
	}else if($(".editBedType").children('span:first').html()=="优先双床房"){
		$(".editBedTypeContent").find('label[val="double"]').addClass('active');
	}

}

function initDatepicker(){
	$("input.datepicker.default").datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        autoclose: true,
        todayHighlight: true,
        weekStart: 0
      }).on("show", function(){
          $("div.datepicker table thead .prev").html("");
          $("div.datepicker table thead .next").html("");
      });

	//编辑游客出生日期和证件有效期
	$('.editTouristInfoContent input[name="birthday"]').each(function () {
		$(this).datepicker('setStartDate', '1920--01-01');
		$(this).datepicker('setEndDate', '2015-12-31');
	});
	$('.editTouristInfoContent input[name="deadLine"]').each(function () {
		$(this).datepicker('setStartDate', new Date());
	});
}

function initNationality(){
	  //国籍自动补全
    $(".nationality").each(function (i) {
    	$(".nationality").eq(i).typeahead({
    		source: function (query, process) {
    			$($(".nationality").eq(i)[0]).val($($(".nationality").eq(i)[0]).val().replace(/</g,"&lt").replace(/>/g,"&gt"));
    	    	var parameter = {query: query};
        	    $.post(basepath + "/order/front/countryAutoComplete", parameter, function (data) {
        	    	if(data.length==0 || !isExistNationality(data,i)){
            	    	notFindHint ="对不起，找不到："+$(".nationality").eq(i).val();
            	    	$(".nationality").eq(i).siblings(".nationalityDropList").val(notFindHint);
            	    	data=[notFindHint];
            	    	}else{
            	    		$(".nationality").eq(i).siblings(".nationalityDropList").val(data);
            	    		}
        	    	process(data);
        	    	});
        	    }
    	});
    });

    $(".nationality").each(function (i) {
    	$(".nationality").eq(i).blur(function(){
    	    var nationalityDropList = $(".nationality").eq(i).siblings(".nationalityDropList").val();
    	    if(nationalityDropList.indexOf("对不起，找不到：") != -1){
    	    	$(".nationality").eq(i).val('');
    	    	}
    	    if(null!=nationalityDropList && $.inArray($(".nationality").eq(i).val(), nationalityDropList.split(',')) <0){
    	    	$(".nationality").eq(i).val('');
    	    	}
    	    if ($.trim($(".nationality").eq(i).val())=='' && $(".nationality").eq(i).siblings("ul:hidden").size()>0) {
        		$(".nationality").eq(i).siblings(".country-error").show();
        		}else{
        			$(".nationality").eq(i).siblings(".country-error").hide();
        			}
    	    });
    	});

}
