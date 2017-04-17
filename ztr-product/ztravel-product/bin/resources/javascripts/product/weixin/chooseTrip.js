var data = [];

$(function(){

	getWxCalendarData();

	clickDate();

	clickCalendarTab();

	clickCheckbox();

	changePackage();

	plusMinusNum();

    clickToBook();

	clickWantGo();

	preBookWaitPhone();

	errorBtnConfirm();

});


function getWxCalendarData(){
	var productPid = $("#pid").val();
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/weixin/detail/getWxCalendarData/'+productPid ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    success: function(result){
	    	data = result;
	    	$(".calendar-wrapper").html(_.template($("#calendarTemplate").html(), data));
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
}

function clickDate(){
	//选中日期
	$("body").delegate('.tdContWrapper:not(.disabled)','click', function(){

    	//选中日期赋值
    	var dateWithYear = $(this).find("input[name='dateWithYear']").val();
    	$("#selectedDay").val(dateWithYear);
    	//显示该选中日期库存
    	var availableStock = $(this).find("input[name='availableStock']").val();
    	$(".trip-num-wrapper .head.clearfix .title.fl").html("出行人数 <em>(余</em><em id='availableStock'>" + availableStock + "</em><em>)</em>");
    	//成人价
    	var adultPrice = $(this).find("input[name='adultPrice']").val();
    	$(".trip-num-wrapper .trip-num-block .passenger-type.adult.clearfix .fl .price").html("¥" + "<em id= 'currentAdultPrice'>" + adultPrice + "</em>");

    	//儿童价
    	var childPrice = $(this).find("input[name='childPrice']").val();
    	if(childPrice == undefined || childPrice == "null" || childPrice == ""){//儿童与成人同价
    		$(".trip-num-wrapper .trip-num-block .passenger-type.child.clearfix").hide();
    	}else{
    		$(".trip-num-wrapper .trip-num-block .passenger-type.child.clearfix").show();
        	$(".trip-num-wrapper .trip-num-block .passenger-type.child.clearfix .fl .price").html("¥" + "<em id= 'currentChildPrice'>" + childPrice + "</em>");
    	}
    	//单房差
    	var singleRoomPrice = $(this).find("input[name='singleRoomPrice']").val();
    	$("#singleRoomPrice").val(singleRoomPrice);

    	//产品状态
    	var saleStatus = $(this).find("input[name='saleStatus']").val();
    	//为开放产品
    	if (saleStatus == "NOT_SCHEDULED") {
    		$(".tdContWrapper").removeClass("current");
            $(this).addClass("current");

            $("#totalPrice").html("¥" + adultPrice * 2);

          //默认关闭套餐滑块
            if ($(".switch-checkbox").hasClass("on")) {
            	$(".switch-checkbox").toggleClass("on");
        	}
    		$(".trip-num-block").show();
            $(".menu-block").hide();

        	$(".next.fl.submit").hide() ;
			$(".next.fl.wantgo").show() ;
			return ;
    	}else{
    		$(".next.fl.submit").show() ;
			$(".next.fl.wantgo").hide() ;
    	}

    	resetPlusMinusNum();

    	var containsPackage = $(this).find("input[name='containsPackage']").val();
    	if (containsPackage == "true") {
    		//存在套餐，则获取套餐
    		var url =basepath+"/product/weixin/detail/queryPackageInfo";
    		var productPid = $("#pid").val();
            $.ajax({
         	   type:"POST",
         	   url:url,
         	   data:{
         		   pid:productPid,
         		   chooseDay:dateWithYear
         	   },
         	   success:function(data){
         		   $("#packageInfoDiv").html(data);

		           	$(".menu-item label").each(function(){
		   				if ($(this).find("input[type='radio']").prop("checked")) {
		   		        	var totalPrice = $(this).parents(".menu-item .menu-info.clearfix").children(".menu-price.fr").html();
		   		        	$("#totalPrice").html(totalPrice);
		   				}
		   			});
         	   }
            });
            //默认开启套餐滑块
            if (!$(".switch-checkbox").hasClass("on")) {
            	$(".switch-checkbox").toggleClass("on");
        	}
    		$(".trip-num-block").hide();
            $(".menu-block").show();
    	}else{
    		//不存在套餐
    		$("#packageInfoDiv").html("");
  		   $("#totalPrice").html(adultPrice * 2);

    		//默认关闭套餐滑块
            if ($(".switch-checkbox").hasClass("on")) {
            	$(".switch-checkbox").toggleClass("on");
        	}
    		$(".trip-num-block").show();
            $(".menu-block").hide();
    	}

        $(".tdContWrapper").removeClass("current");
        $(this).addClass("current");

    });
}

function resetPlusMinusNum(){
	$(".trip-num-wrapper .trip-num-block .passenger-type.adult.clearfix .fr .result").html("2");
    $(".trip-num-wrapper .trip-num-block .passenger-type.adult.clearfix .fr .minus-mark").removeClass("disabled");
    $(".trip-num-wrapper .trip-num-block .passenger-type.child.clearfix .fr .result").html("0");
    $(".trip-num-wrapper .trip-num-block .passenger-type.child.clearfix .fr .minus-mark").addClass("disabled");
}

function resetChosenContent(){
	$("#selectedDay").val("");
	$(".trip-num-wrapper .head.clearfix .title.fl").html("出行人数");
	$("#singleRoomPrice").val("");
	$(".trip-num-wrapper .trip-num-block .passenger-type.adult.clearfix .fl .price").html("-- --");
	$(".trip-num-wrapper .trip-num-block .passenger-type.child.clearfix .fl .price").html("-- --");
	resetPlusMinusNum()

	$("#totalPrice").html("-- --");
	if ($(".switch-checkbox").hasClass("on")) {
		$(".switch-checkbox").toggleClass("on");
		$(".trip-num-block").show();
        $(".menu-block").hide();
	}
	$(".tdContWrapper").removeClass("current");
}

function clickCalendarTab(){
	// 日历翻动事件
	$("body").delegate('.calendar-nav .nav-btn span','click', function(){

		resetChosenContent();

        if ($(this).hasClass("disabled")) {
            return;
        } else {

        	var $wrapper = $(this).parents(".calendar-wrapper");

            var $calList = $wrapper.find(".calendar-body .calendar-item");
            var $navList = $wrapper.find(".calendar-nav .tablist li");

            var calCurIndex = $wrapper.find(".calendar-body .calendar-item.current").index();
            var navCurIndex = $wrapper.find(".calendar-nav .tablist li.current").index();

            $calList.removeClass("current");
            $navList.removeClass("current");

            if ($(this).hasClass("prev")) {
                $calList.eq(calCurIndex-1).addClass("current");
                $navList.eq(navCurIndex-1).addClass("current");
            } else if ($(this).hasClass("next")) {
                $calList.eq(calCurIndex+1).addClass("current");
                $navList.eq(navCurIndex+1).addClass("current");
            }

            var $calCur = $wrapper.find(".calendar-body .calendar-item.current");

            var _calCurIndex = $wrapper.find(".calendar-body .calendar-item.current").index();
            var _navCurIndex = $wrapper.find(".calendar-nav .tablist li.current").index();

            if ($calCur.prev("li").length <= 0) {
                $(".calendar-nav .nav-btn .prev").addClass("disabled");
            } else {
            	$(".calendar-nav .nav-btn .prev").removeClass("disabled");
            }

            if ($calCur.next("li").length <= 0) {
                $(".calendar-nav .nav-btn .next").addClass("disabled");
            } else {
            	$(".calendar-nav .nav-btn .next").removeClass("disabled");
            }

            $wrapper.find(".calendar-body").css({
                "margin-left": -_calCurIndex * 100 + "%"
            });

            $wrapper.find(".calendar-nav .tablist").css({
                "margin-left": -_navCurIndex * 100 + "%"
            });
        }
    });
}

function clickCheckbox(){
	$(".switch-checkbox").click(function(){

    	var selectedDay = $("#selectedDay").val();
    	if(selectedDay == undefined || selectedDay == "") {
    		return ;
    	}

    	var adultPrice = $("#currentAdultPrice").html();
    	if(adultPrice == undefined || adultPrice == ""){
    		$("#selectedDay").val("");
    		return ;
    	}

    	var containsPackage = $(".tdContWrapper.current").find("input[name='containsPackage']").val();
    	if (containsPackage == "false") {
    		return ;
    	}

    	$(this).toggleClass("on");

    	if ($(this).hasClass("on")) {

    		$(".menu-item label").each(function(){
   				if ($(this).find("input[type='radio']").prop("checked")) {
   		        	var totalPrice = $(this).parents(".menu-item .menu-info.clearfix").children(".menu-price.fr").html();
   		        	$("#totalPrice").html(totalPrice);
   				}
   			});

    		$(".trip-num-block").hide();
            $(".menu-block").show();
    	} else {

        	var adultPrice = $("#currentAdultPrice").html();
        	$("#totalPrice").html("¥" + adultPrice * 2);

            $(".trip-num-block").show();
            $(".menu-block").hide();
    	}
    });
}

function changePackage(){
	$("body").delegate('.menu-item label','click', function(){
		if (!$(this).hasClass('disabled')) {
	        $(this).parents(".menu-list").find(".menu-short-desc").hide();
	        if ($(this).find("input[type='radio']").prop("checked")) {
	            $(this).parents(".menu-item").find(".menu-short-desc").show();
	        	var totalPrice = $(this).parents(".menu-item .menu-info.clearfix").children(".menu-price.fr").html();
	        	$("#totalPrice").html(totalPrice);
	        }
		}
    });
}

function plusMinusNum(){
	$(".plus-minus-comp span").click(function(){
        var $that = $(this);
        if (!$that.hasClass("disabled")) {
            if ($that.hasClass("plus-mark")) {
                var val = parseInt($(this).siblings(".result").html());
                $(this).siblings(".result").html(val+1);
                if($that.parent().children(".minus-mark").hasClass("disabled")){
                	$that.parent().children(".minus-mark").removeClass("disabled");
                }
            } else if ($that.hasClass("minus-mark")) {
                var val = parseInt($(this).siblings(".result").html());
                if (val ==2 && $(this).siblings(".result").parents().hasClass("adult")) {
                	$(this).addClass("disabled");
                }
                if (val <= 1) {
                    $(this).addClass("disabled");
                }
                $(this).siblings(".result").html(val-1);
            }
        	var adultPrice = $("#currentAdultPrice").html();
        	var singleRoomPrice = $("#singleRoomPrice").val();
        	if(adultPrice != undefined && adultPrice != ""){
            	var adultNum = $(".trip-num-wrapper .trip-num-block .passenger-type.adult.clearfix .fr .result").html();
        		var totalPrice = adultPrice * adultNum;
            	if(adultNum % 2 == 1){
            		totalPrice += singleRoomPrice * 1;
            	}
        		var childPrice = $("#currentChildPrice").html();
            	if(childPrice != undefined && childPrice != ""){
                	var childNum = $(".trip-num-wrapper .trip-num-block .passenger-type.child.clearfix .fr .result").html();
                	totalPrice += childPrice * childNum;
            	}
            	$("#totalPrice").html("¥" + totalPrice);
        	}
        }
    });
}

function clickToBook(){
	$('.next.fl.submit').click(function(){
		var selectDay = $("#selectedDay").val();
		var url = wxServer +'/book/weixin/tobook';
		if(selectDay == undefined || isNaN(new Date(selectDay).getTime()) ){
			$("#errorHintMsg").html("请选择日期");
			$("#alert-errorhint-dialog").popup("open");
		}else{
	    	var adultPrice = $("#currentAdultPrice").html();
	    	if(adultPrice == undefined || adultPrice == ""){
	    		$("#selectedDay").val("");
				$("#errorHintMsg").html("请选择日期");
				$("#alert-errorhint-dialog").popup("open");
	    		return ;
	    	}
			// 判断是套餐 还是非套餐
			if ($(".switch-checkbox").hasClass("on")) {
				packgeToBook(selectDay ,url);
        	} else {
        		toBook(selectDay,url);
        	}
		}
	});
}

function clickWantGo(){
	   $("body").delegate(".next.fl.wantgo","click",function(){
	        if ($("#popupMenu-screen").is(":visible")) {
	            $("#popupMenu").popup("close");
	            setTimeout(function(){
	                $("#wantgoWindow").popup("open");
	            }, 600);
	        } else {
	            $("#wantgoWindow").popup("open");
	        }
	    });
}

//按成人儿童数进行预订
function toBook(selectDay,url){
    var adult_num = $(".trip-num-wrapper .trip-num-block .passenger-type.adult.clearfix .fr .result").html().trim();
    var child_num = $(".trip-num-wrapper .trip-num-block .passenger-type.child.clearfix .fr .result").html().trim();
	var productId = $("#productId").val();
	var availableStock = $("#availableStock").html();
	var personNum = parseInt(adult_num) + parseInt(child_num);
	if( isNaN(availableStock)|| availableStock ==null || personNum > availableStock){
		$("#errorHintMsg").html("库存不够");
		$("#alert-errorhint-dialog").popup("open");
		return false;
	}
	var cpid = "cp";
	var pdn = $("#nature").val();

//	_paq.push(['trackEvent', 'detailpage', 'ztrcrorder', productId,$form.serialize()]);
	window.location.href = url + "/" + adult_num + "/" + child_num + "/" + selectDay + "/" + productId+"/"+cpid+"/"+pdn;
	
}

//选择套餐进行预订
function packgeToBook(selectDay,url){

	var packageId = "";
	var sumNum = 0;
	$(".menu-item label").each(function(){
		if ($(this).find("input[type='radio']").prop("checked")) {
			packageId = $(this).parents(".menu-item").find("input[name='pkgId']").val();
			var sumNumStr = $(this).parents(".menu-item").find("input[name='sumNum']").val();
			if(sumNumStr != ""){
				sumNum =parseInt(sumNumStr);
			}
		}
	});

	if (packageId == "") {
		$("#errorHintMsg").html("请先选择套餐");
		$("#alert-errorhint-dialog").popup("open");
		return;
	}

	var availableStockStr = $("#availableStock").html();
	var availableStock = 0;
	if(availableStockStr != ""){
		availableStock = parseInt(availableStockStr);
	}
	if(sumNum == 0){
		// 提示选择套餐
		$("#errorHintMsg").html("请选择出行方式");
		$("#alert-errorhint-dialog").popup("open");
		return;
	}else if(availableStock == 0 || sumNum > availableStock){
		// 提示库存不够
		$("#errorHintMsg").html("库存不够所选套餐包含人数");
		$("#alert-errorhint-dialog").popup("open");
		return;
	}

	var productId = $("#productId").val();
	var param = {
			productId: productId,
			selectDay: selectDay,
			packageId: packageId
	};
	_paq.push(['trackEvent', 'detailpage', 'ztrcrorder', JSON.stringify(param)]);
	window.location.href = url + "/" + packageId + "/" + selectDay + "/" + productId;

}

//想去未开放产品
function preBookWaitPhone(){
	$(".btn-confirm.pre-order-btn").click(function(){
		var phone = $("#want-go-tel").val() ;
		var isMobile = /^\d{11}$/ ;
		var clear = true ;
		if(phone != '' && !isMobile.test(phone)){
			clear = false ;
			$("#want-go-tel").val("") ;
			$("#want-go-tel").attr("placeholder","请输入正确的手机号") ;
		}
		if(clear){
			$("#wantgoWindow").popup("close");
        	$(".next.fl.submit").show() ;
			$(".next.fl.wantgo").hide() ;
			var chooseDay = $("#selectedDay").val();
			$(".menu-row .ui-grid-c").removeClass("menu-row-focus");
			$("#want-go-tel").attr("placeholder","请输入手机号") ;
			$("#want-go-tel").val('') ;
			_paq.push(['trackEvent', 'chooseTrip', 'ztrwantbook', phone, chooseDay.replace(/\//g, "-" )]);
		}

		resetChosenContent();
	});
}

function errorBtnConfirm(){
	   $("body").delegate(".btn-confirm","click",function(){
			$("#errorHintMsg").html("");
			$("#alert-errorhint-dialog").popup("close");
		})
}

