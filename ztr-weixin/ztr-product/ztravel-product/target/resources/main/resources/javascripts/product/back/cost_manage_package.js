var isSupplierOk = true ;

$(function(){

	//编辑视图
    $(".commonButton.blue-45c8dcButton.top").click(function(){
    	window.location.href = basepath + '/product/cost/edit/' + $("#id").html() ;
    }) ;

    //添加成本
    $(".commonButton.blue-45c8dcButton.cost.add").click(function(){
    	var cost = getCostVar($(this).hasClass("batch")) ;
    	if(checkCostPrice(cost.adultPrice) && checkCostPrice(cost.childPrice)){
    		if(cost != null){
        		$.ajax({
            	    type: 'POST',
            	    url: basepath + '/product/cost/addCost' ,
            	    data: JSON.stringify(cost) ,
            	    contentType : 'application/json',
            	    success: function(result){
            	    	result = result == '' ? '{}' : result ;
            	    	geneDoublePriceCalendar(".double-price-calendar", strToJson(result));
            	    }
            	});
        	}
    	}else{
    		alert('填入正确的成本价') ;
    	}
    }) ;

    //清除成本
    $(".commonButton.blue-45c8dcButton.cost.remove").click(function(){
    	var cost = getCostVar($(this).hasClass("batch")) ;
    	if(cost != null){
	    	$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/cost/removeCost' ,
	    	    data: JSON.stringify(cost) ,
	    	    contentType : 'application/json',
	    	    success: function(result){
	    	    	result = result == '' ? '{}' : result ;
	    	    	geneDoublePriceCalendar(".double-price-calendar", strToJson(result));
	    	    }
	    	});
    	}
    }) ;

    $(".priceOperType label.radio").click(function(){
        if ($(this).attr("name") == "1") {   //  批量操作
            $(".batch-oper-block").show();
            $(".single-oper-block").hide();

            //  切换为批量操作时 视情况处理单日操作时已点击过的单元格
            $(".double-price-calendar").find(".tdCont").removeClass("active");
        }

        if ($(this).attr("name") == "2") {   //  单日操作
            $(".batch-oper-block").hide();
            $(".single-oper-block").show();
        }
    });

});

	function saveCheck(nextStep){
		var bean = {} ;
		bean.id = $("#id").html() ;
		bean.supplier = $("#packageSupplierId").val() ;
		bean.isNextStep = nextStep ;
		$.ajax({
		    type: 'POST',
		    url: basepath + '/product/cost/saveCheck' ,
		    data: JSON.stringify(bean) ,
		    dataType: 'json' ,
		    contentType : 'application/json',
		    success: function(result){
		    	if(result && nextStep){
	    			//跳转时间价格
		    		window.location.href = basepath + '/product/calendar/'+mode+'/' + bean.id ;
		    	}else if(result && !nextStep){
		    		window.location.href = basepath + '/product/cost/'+mode+'/' + bean.id ;
		    	}
		    }
		});
	}

	function checkSupplier(that, val, regex, message){
		if(!regex.test(val)){
			that.focus() ;
			that.val("");
			that.attr("placeholder",message) ;
			isSupplierOk = false ;
		}
	}

	function getCostVar(isBatch){
		var cost = {} ;
		cost.id = $("#id").html() ;
		if(isBatch){
			cost.adultPrice = $(".adultCost.batch").val() == '' ? null : $(".adultCost.batch").val() ;
			cost.childPrice = $(".childCost.batch").val() == '' ? null : $(".childCost.batch").val() ;
			cost.start = $(".datepicker.startDate.hasIcon").val() ;
			cost.end = $(".datepicker.endDate.hasIcon").val() ;
			var weekDays = '' ;
			$(".checkboxContent.week-date .active").each(function(){
				var html = $(this).html().replace(/\ +/g,"")
				weekDays += html.substring(35,36) + " " ;
			})
			cost.weekDays = weekDays ;
		}else{
			cost.adultPrice = $(".adultCost.single").val() == '' ? null : $(".adultCost.single").val() ;
			cost.childPrice = $(".childCost.single").val() == '' ? null : $(".childCost.single").val() ;
			cost.start = $("#packageSingleDay").val() ;
			cost.end = $("#packageSingleDay").val() ;
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

        $(ele).find(".tdCont").not(".disabled").click(function(){
            if ($(".priceOperType label.radio.active").attr("name") == 1) {
            //  如果是批量操作，可视情况解除点击事件
                return false;
            } else {
                $(ele).find(".tdCont").removeClass("active");
                $(this).addClass("active");
                $("#packageSingleDay").val($(this).find(".date").attr("data-daynum")) ;
            }
        });
    }