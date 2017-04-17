var isFlightSupplierOk = true ;
$(function(){

    //编辑视图
    $(".commonButton.blue-45c8dcButton.top").click(function(){
    	window.location.href = basepath + '/product/cost2/edit/' + $("#id").html() ;
    }) ;

    //添加成本 机票
    $(".commonButton.blue-45c8dcButton.flight.cost.add").click(function(){
    	var cost = getCostVar('flight', $(this).hasClass("batch")) ;
    	if(checkCostPrice(cost.adultPrice) && checkCostPrice(cost.childPrice)){
	    	$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/cost2/addFlightCost' ,
	    	    data: JSON.stringify(cost) ,
	    	    contentType : 'application/json',
	    	    success: function(result){
	    	    	result = result == '' ? '{}' : result ;
	    	    	geneDoublePriceCalendar(".double-price-calendar.flight", strToJson(result));
	    	    }
	    	});
    	}else{
    		alert('填入正确的机票成本价') ;
    	}
    }) ;

    //清除成本 机票
    $(".commonButton.blue-45c8dcButton.flight.cost.remove").click(function(){
    	var cost = getCostVar('flight', $(this).hasClass("batch")) ;
    	$.ajax({
    	    type: 'POST',
    	    url: basepath + '/product/cost2/removeFlightCost' ,
    	    data: JSON.stringify(cost) ,
    	    contentType : 'application/json',
    	    success: function(result){
    	    	result = result == '' ? '{}' : result ;
    	    	geneDoublePriceCalendar(".double-price-calendar.flight", strToJson(result));
    	    }
    	});
    }) ;

    //添加成本 酒店
    $(".commonButton.blue-45c8dcButton.hotel.cost.add").click(function(){
    	var cost = getCostVar('hotel', $(this).hasClass("batch")) ;
    	if(checkCostPrice(cost.roomPrice)){
	    	$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/cost2/addHotelCost' ,
	    	    data: JSON.stringify(cost) ,
	    	    contentType : 'application/json',
	    	    success: function(result){
	    	    	result = result == '' ? '{}' : result ;
	    	    	geneDoublePriceCalendar(".double-price-calendar.hotel", strToJson(result));
	    	    }
	    	});
    	}else{
    		alert('填入正确的酒店成本价') ;
    	}
    }) ;

    //清除成本 酒店
    $(".commonButton.blue-45c8dcButton.hotel.cost.remove").click(function(){
    	var cost = getCostVar('hotel', $(this).hasClass("batch")) ;
    	$.ajax({
    	    type: 'POST',
    	    url: basepath + '/product/cost2/removeHotelCost' ,
    	    data: JSON.stringify(cost) ,
    	    contentType : 'application/json',
    	    success: function(result){
    	    	result = result == '' ? '{}' : result ;
    	    	geneDoublePriceCalendar(".double-price-calendar.hotel", strToJson(result));
    	    }
    	});
    }) ;

    $(".priceOperType label.radio").click(function(){
    	var className = "hotel" ;
    	if($(this).parent().hasClass("flight")){
    		className = "flight" ;
    	}
        if ($(this).attr("name") == "1") {   //  批量操作
            $(".batch-oper-block."+className).show();
            $(".single-oper-block."+className).hide();

            //  切换为批量操作时 视情况处理单日操作时已点击过的单元格
            $(".double-price-calendar."+className).find(".tdCont").removeClass("active");
        }

        if ($(this).attr("name") == "2") {   //  单日操作
            $(".batch-oper-block."+className).hide();
            $(".single-oper-block."+className).show();
        }
    });

});

	function saveCheck(nextStep){
		var isContainFlight = $("#isContainFlight").val() == 'true' ? true : false ;

		var bean = {} ;
		bean.id = $("#id").html() ;
		if(isContainFlight){
			bean.flightSupplier = $("#flightSupplierId").val() ;
		}
		bean.isNextStep = nextStep ;
		$.ajax({
		    type: 'POST',
		    url: basepath + '/product/cost2/saveCombinationCheck' ,
		    data: JSON.stringify(bean) ,
		    dataType: 'json' ,
		    contentType : 'application/json',
		    success: function(result){
		    	if(result && nextStep){
	    			//跳转价格日历
		    		window.location.href = basepath + '/product/calendar/'+mode+'/' + bean.id ;
		    	}else if(result && !nextStep){
		    		window.location.href = basepath + '/product/cost2/'+mode+'/' + bean.id ;
		    	}
		    }
		});
	}

	function checkFlightSupplier(that, val, regex, message){
		if(!regex.test(val)){
			that.focus() ;
			that.val("");
			that.attr("placeholder",message) ;
			isFlightSupplierOk = false ;
		}
	}

	function getCostVar(className, isBatch){
		var cost = {} ;
		cost.id = $("#id").html() ;
		if(className == 'flight'){
			if(isBatch){
				cost.adultPrice = $(".adultCost.batch").val() == '' ? null : $(".adultCost.batch").val() ;
				cost.childPrice = $(".childCost.batch").val() == '' ? null : $(".childCost.batch").val() ;
			}else{
				cost.adultPrice = $(".adultCost.single").val() == '' ? null : $(".adultCost.single").val() ;
				cost.childPrice = $(".childCost.single").val() == '' ? null : $(".childCost.single").val() ;
				cost.start = $("#flightSingleDay").val() ;
				cost.end = $("#flightSingleDay").val() ;
			}
		}else if(className == 'hotel'){
			if(isBatch){
				var roomPrice = [] ;
				for(var i=0;i<$(".roomCost.batch").length;i++){
					roomPrice[i] = $($(".roomCost.batch")[i]).val() == '' ? null : $($(".roomCost.batch")[i]).val() ;
				}
				cost.roomPrice = roomPrice ;
			}else{
				var roomPrice = [] ;
				for(var i=0;i<$(".roomCost.single").length;i++){
					roomPrice[i] = $($(".roomCost.single")[i]).val() == '' ? null : $($(".roomCost.single")[i]).val() ;
				}
				cost.roomPrice = roomPrice ;
				cost.start = $("#hotelSingleDay").val() ;
				cost.end = $("#hotelSingleDay").val() ;
			}
		}
		if(isBatch){
			cost.start = $(".datepicker.startDate.hasIcon."+className).val() ;
			cost.end = $(".datepicker.endDate.hasIcon."+className).val() ;
			var weekDays = '' ;
			$(".checkboxContent.week-date."+className+" .active").each(function(){
				var html = $(this).html().replace(/\ +/g,"")
				weekDays += html.substring(35,36) + " " ;
			})
			cost.weekDays = weekDays ;
		}else{
			cost.weekDays = '一 二 三 四 五 六 日' ;
		}
		return cost ;
	}

	function resetDpCalendar(ele, leftDate, rightDate, data) {

        $(ele).find(".datepicker-inline td.active").removeClass("active");
        $(ele).find(".datepicker-inline td").click(function(){
            return false;
        });

        geneTdCont($(ele).find(".left-calendar"), leftDate, data);
        geneTdCont($(ele).find(".right-calendar"), rightDate, data);

        setDpCalendarTitle(ele, leftDate, rightDate);

        var hasFlight = false ;
        var className = "hotel" ;
        if($(ele).hasClass("flight")){
        	hasFlight = true ;
        	className = "flight" ;
        }

        $(ele).find(".tdCont").not(".disabled").click(function(){
            if ($(".priceOperType."+className+" label.radio.active").attr("name") == 1) {
            //  如果是批量操作，可视情况解除点击事件
                return false;
            } else {
                $(ele).find(".tdCont").removeClass("active");
                $(this).addClass("active");
                if(hasFlight){
                	$("#flightSingleDay").val($(this).find(".date").attr("data-daynum")) ;
                }else{
                	$("#hotelSingleDay").val($(this).find(".date").attr("data-daynum")) ;
                }
            }
        });
    }