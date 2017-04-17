var lock = false;

var isTravellerEdit = false;

$(function(){

	//编辑游客信息
    $(".editTouristInfo").click(function(){
    	isTravellerEdit = true;
        $(".touristInfoTab").hide();
        getTouristInfoData();
        $(".editTouristInfoContent").find('.verifyStyle').hide();
        $(".editTouristInfoContent").slideDown(function(){
            $(window).trigger("resize");
        });
    });

    $(".editTouristInfoContent").delegate(".commonBtn", "click", function(){
    	if ($('.editTouristInfoContent .verifyStyle:visible').size() == 0) {
			saveTouristInfo();
		}
    	isTravellerEdit = false;
    });

    $(".editTouristInfoContent").delegate(".cancelBtn", "click", function(){
    	isTravellerEdit = false;
    	$(".editTouristInfoContent").slideUp(function(){
    		$(window).trigger("resize");
    	});
    	$(".touristInfoTab").show();
    });

});

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
        	    $.post(basepath + "/order/travelConfirm/countryAutoComplete", parameter, function (data) {
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

function saveTouristInfo(){
	if(lock){
		return ;
	}else{
		lock = true;
	}
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

	var orderId = $('#orderId').val();
	var data = {
			"orderId":orderId,
			"passengers": passengers
	}

	$.ajax({
		type: "POST",
		url: basepath + '/order/travelConfirm/updatePassengers',
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
	   data : JSON.stringify(data),
	   dataType: "json",
	   success: function(resp){
		   lock = false;
		   if (resp.res_code == 'SF_ORDE_1002') {
			   $(".editTouristInfoContent").slideUp(function(){
		            $(window).trigger("resize");
		        });
		       $(".touristInfoTab").show();
		       getTouristInfoList();
		   }else if (resp.res_code == 'FF_ORDE_1010') {
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

function getTouristInfoList(){
	var orderId = $('#orderId').val();
	 $.ajax({
		   type: "POST",
		   url: basepath + '/order/travelConfirm/passengers/refresh/' + orderId,
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   data : orderId,
		   success: function(json){
			   $(".touristInfoTab tbody").html(json);
			   }
		});
}