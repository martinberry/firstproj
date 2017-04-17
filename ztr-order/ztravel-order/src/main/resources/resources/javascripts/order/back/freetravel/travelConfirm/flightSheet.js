var orderFlight = new Object();

var old_flight_html = '' ;
var mode = 'view' ;

//第几天
var p1 = /^[1-9]\d{0,1}$/ ;
//航司名称
var p2 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D]{1,30}$/ ;
//航班号
var p3 = /^[a-zA-Z0-9]{1,7}$/ ;
//舱位
var p4 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D]{0,10}$/ ;
//机型
var p5 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D]{1,10}$/ ;
//起飞/到达机场
var p6 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D\s]{1,50}$/ ;
//起飞/到达时间
var p7 = /^(([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1})$/ ;
//起飞/到达城市
var p8 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D]{1,50}$/ ;
//经停
var p9 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D:：\/\s]{0,50}$/ ;
//航班错误信息
var m1 = '信息输入有误' ;
//航班校验结果
var isFlightOk = true ;

//备注信息
var c1 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D·~!！@#￥%……&*（）\(\)——+=-\s\$\{\}\[\]【】<>《》,\.，。\?\/、\\:;：；“”’]{0,200}$/ ;
//备注信息
var c2 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D·~!！@#￥%……&*（）\(\)——+=-\s\$\{\}\[\]【】<>《》,\.，。\?\/、\\:;：；“”’]{1,500}$/ ;
//错误信息
var m2 = '输入格式有误，请重新输入' ;

$(function(){

	selectOrderFlight();

	//附加产品编辑
    $(".flightEditBtn").click(function(){
    	editFlight();
    });

	mode = $("#mode").val() ;

	if(mode == 'view'){
		$(".oper-link").hide() ;
		$(".add-product-oper").hide() ;
		$(".oper-price-btn").hide() ;
		$("input").prop("readonly",true) ;
	}else{
		$(".oper-link").show() ;
		$(".add-product-oper").show() ;
		$(".oper-price-btn").show() ;
		$("input").prop("readonly",false) ;
	}

	$(".go-air-range-block").append($("#flight_form_table_template").html()) ;

	old_flight_html = $(".flight-info.edit").html() ;

	$(".commonButton.gray-bbbButton.flight.cancel").click(function(){
		$(".flight-info.edit").html(old_flight_html) ;
	});

    $(".commonButton.red-fe6869Button.flight.save").click(function(){
        /**
    	 * 航程排序------为减少复杂度,在此做约定
    	 * 如果是去程/返程,则按照普通方式排序
    	 * 如果是中间程
    	 * 第一程:1000+index = airRangeIndex
    	 * 第二程:2000+index = airRangeIndex
    	 * 如第一程第一二次中转分别为1001,1002
    	 * 第二程第一二次中转分别为2001,2002
    	 * 去程1,2,3
    	 * 返程1,2,3
    	 * */
    	isFlightOk = true ;
        var req = {} ;

        var gos = [] ;
        $(".go-air-range-block").find(".flight-form-table").each(function(index){
        	gos[index] = getFlightJson($(this), 'GO', index) ;
        });

        var middles = [] ;
        var actul_index = 0 ;
        $(".middle-air-range-block").each(function(range_index){
        	var middle = {} ;
        	var index = 1000 * (range_index + 1) ;
        	$(this).find(".flight-form-table").each(function(transfer_index){
        		middles[actul_index] = getFlightJson($(this), 'MIDDLE', index + transfer_index) ;
        		actul_index ++ ;
        	});
        });

        var backs = [] ;
        $(".back-air-range-block").find(".flight-form-table").each(function(index){
        	backs[index] = getFlightJson($(this), 'BACK', index) ;
        });

        req.gos = gos ;
        req.backs = backs ;
        req.middles = middles ;
        req.orderId = $("#orderId").val() ;

        if(isFlightOk){
        	$.ajax({
        	    type: 'POST',
        	    url: basepath + '/order/travelConfirm/saveOrderFlight' ,
        	    data: JSON.stringify(req) ,
        	    contentType : 'application/json',
        	    dataType: 'json' ,
        	    success: function(result){
        	    	if(typeof(result.go.length)  != "undefined"){
        	    		resetFlightView(result) ;
        	    		$("#add-flight-popup").modal("hide");
        	    		window.location.href = window.location.href;
        	    	}
        	    }
        	});
        }
    });

    $(".modal").modal({
        backdrop: 'static',
        show: false,
        keyboard: false
    }).on("show.bs.modal", function(){
        // 如有需要，弹窗弹出前，可绑定事件
        // 使用方法自行参考bootstrap官网
    });

    $(".add-flight-btn").click(function(){
        $("#add-flight-popup").modal("show");
    });

    $(".modal-header-oper .cancel").click(function(){
       $(this).parents(".modal").modal("hide");
    });

    //  添加航程 -- 中间
    $("body").delegate(".add-middle-flight", "click", function(){

        var $container = $(".middle-air-range-container");

        if ($container.find(".flight-form-table").length == 0) {
            $container.find(".middle-air-range-block:eq(0)").append($("#first_middle_flight_template_front").html() + $("#flight_form_table_template").html() + $("#first_middle_flight_template_back").html());
            $container.find(".middle-air-range-block:eq(0)").find(".add-transfer").show();
        } else {
        	$container.append($("#normal_middle_flight_template_front").html() + $("#flight_form_table_template").html() + $("#normal_middle_flight_template_back").html());
        }
        resetMiddleFlight();
    });

    //  添加航程 -- 返程
    $("body").delegate(".add-back-flight", "click", function(){

        var $container = $(".back-air-range-block");

        if ($container.find(".flight-form-table").length == 0) {
            $container.append($("#first_back_flight_template_front").html() + $("#flight_form_table_template").html() + $("#first_back_flight_template_back").html());
            $container.find(".add-transfer").show();
            $container.find(".add-back-flight").hide() ;
        }
    });

    //  删除航程 -- 中间
    $("body").delegate(".del-middle-flight", "click", function(){

        var $container = $(".middle-air-range-container");

        if ($container.find(".middle-air-range-block").length == 1) {
        	$(this).closest(".middle-air-range-block").find(".add-transfer").hide() ;
            $(this).closest(".middle-air-range-block").find(".transfer-flight-block").remove();
            $(this).closest(".middle-air-range-block").find(".flight-form-table").remove();
            $(this).closest(".middle-air-range-block").find(".del-oper").remove();
        } else {
            $(this).closest(".middle-air-range-block").remove();
        }
        if ($container.find(".middle-air-range-block").length == 1) {
        	$(this).closest(".middle-air-range-block").find(".add-middle-flight").show() ;
        }
        resetMiddleFlight();
    });

    //  删除航程 -- 返程
    $("body").delegate(".del-back-flight", "click", function(){

    	$(this).closest(".back-air-range-block").find(".add-transfer").hide() ;
        $(this).closest(".back-air-range-block").find(".transfer-flight-block").remove();
        $(this).closest(".back-air-range-block").find(".flight-form-table").remove();
        $(this).closest(".back-air-range-block").find(".del-oper").remove();
        var $container = $(".back-air-range-block");
        $container.find(".add-back-flight").show() ;
    });

    //  添加中转
    $("body").delegate(".add-transfer", "click", function(){
    	$(this).closest("section").append($("#transfer_flight_template_front").html() + $("#flight_form_table_template").html() + $("#transfer_flight_template_back").html());
    });

    //  删除中转
    $("body").delegate(".del-transfer", "click", function(){
        $(this).closest(".transfer-flight-block").remove();
    })

    $(".day-item").not(".disable").click(function(){
    	pickOne(this) ;
    });

    $(".pick-all").click(function(){
        $(this).toggleClass("active");

        if ($(this).hasClass("active")) {
            $(".day-item").not(".disable").addClass("active");
        } else {
            $(".day-item").not(".disable").removeClass("active");
        }
    });

});

	function setDropDownMenu(that,val,desc,className){
		$(that).parent().parent().find(className).val(val) ;
		$(that).parent().parent().find(".activeFonts").html(desc) ;
	}

	function getFlightJson(that, airRange, index){
		var flight = {} ;
		flight.stop = that.find(".stop").val() ;
		checkFlight(that.find(".stop"), flight.stop, p9, m1) ;

		flight.offsetDays = that.find(".offsetDays").val() ;
		checkFlight(that.find(".offsetDays"), flight.offsetDays, p1, m1) ;

		flight.airLineName = that.find(".airLine").val() ;
		checkFlight(that.find(".airLine"), flight.airLineName, p2, m1) ;

		flight.flightNum = that.find(".flightNo").val() ;
		checkFlight(that.find(".flightNo"), flight.flightNum, p3, m1) ;

		flight.cabin = that.find(".cabin").val() ;
		checkFlight(that.find(".cabin"), flight.cabin, p4, m1) ;

		flight.planeCode = that.find(".flightModel").val() ;
		checkFlight(that.find(".flightModel"), flight.planeCode, p5, m1) ;

		flight.departAirPort = that.find(".fromAirPort").val() ;
		checkFlight(that.find(".fromAirPort"), flight.departAirPort, p6, m1) ;

		flight.departTime = that.find(".departureTime").val() ;
		checkFlight(that.find(".departureTime"), flight.departTime, p7, m1) ;

		flight.arriveAirPort = that.find(".toAirPort").val() ;
		checkFlight(that.find(".toAirPort"), flight.arriveAirPort, p6, m1) ;

		flight.arriveTime = that.find(".arrivalTime").val() ;
		checkFlight(that.find(".arrivalTime"), flight.arriveTime, p7, m1) ;

		flight.addDays = that.find(".addDays").val() ;
		flight.airRangeIndex = index + 1 ;
		flight.fromCity = that.find(".fromCity").val() ;
		checkFlight(that.find(".fromCity"), flight.fromCity, p8, m1) ;
		flight.toCity = that.find(".toCity").val() ;
		checkFlight(that.find(".toCity"), flight.toCity, p8, m1) ;
    	return flight ;
	}

	function checkFlight(that, val, regex, message){
		if(!regex.test(val)){
			that.focus() ;
			that.val("");
			that.attr("placeholder",message) ;
			isFlightOk = false ;
		}
	}

	function resetFlightView(flight){
		$(".add-btn.add-flight-btn").hide() ;
		var base_html = '<div class="table-top-block clearfix"><ul class="table-tab"><li class=""><span>'+flight.from+'出发</span></li></ul>' ;
		base_html += '<span class="oper-link"><a href="javascript:editFlight() ;">编辑</a><a href="javascript:deleteFlight() ;">删除</a>' ;
		base_html += '</span></div><table class="flight-info-table"><colgroup><col width="79"/><col width="80"/><col width="79"/><col width="80"/><col width="79"/><col width="80"/>' ;
		base_html += '<col width="79"/><col width="80"/><col width="85"/><col width="80"/><col width="85"/><col width="90"/>' ;
		base_html += '<col width="85"/><col width="90"/><col width="85"/></colgroup><tbody>' ;

		var go_html = '' ;
		for(var i=0;i<flight.go.length;i++){
			var go = flight.go[i] ;
			go_html += '<tr>' ;
			if(i==0){
				go_html += "<td rowspan='"+flight.go.length*2+"' class='go-air-range'><span class='go-flight-icon'></span><br/><span>去程</span></td>" ;
			}
			go_html += "<th>第几天</th><td>"+go.offsetDays+"</td><th>航司名称</th><td>"+go.airLineName+"</td>" ;
			go_html += "<th>航班号</th><td>"+go.flightNum+"</td><th>舱位</th><td>"+go.cabin+"</td>" ;
			go_html += "<th>机型</th><td>"+go.planeCode+"</td><th>经停信息</th><td colspan='3'>"+go.stop+"</td></tr>" ;
			go_html += "<tr><th>出发机场</th><td>"+go.departAirPort+"</td><th>起飞时间</th><td>"+go.departTime+"</td>" ;
			go_html += "<th>到达机场</th><td>"+go.arriveAirPort+"</td><th>到达时间</th><td>"+go.arriveTime+"</td><th>到达偏移时间</th><td>"+go.addDays+"</td><th>出发城市</th><td>"+go.fromCity+"</td><th>到达城市</th><td>"+go.toCity+"</td></tr>" ;
		}

		var mrange = flight.middle;

		var middle_html = '' ;
		for(var prop in mrange){
		    if(mrange.hasOwnProperty(prop)){
		        for(var i=0;i<mrange[prop].length;i++){
					var middle = mrange[prop][i] ;
					middle_html += '<tr>' ;
					if(i==0){
						middle_html += "<td rowspan='"+mrange[prop].length*2+"' class='middle-air-range'><span class='middle-flight-icon'></span><br/><span>第"+prop+"程</span></td>" ;
					}
					middle_html += "<th>第几天</th><td>"+middle.offsetDays+"</td><th>航司名称</th><td>"+middle.airLineName+"</td>" ;
					middle_html += "<th>航班号</th><td>"+middle.flightNum+"</td><th>舱位</th><td>"+middle.cabin+"</td>" ;
					middle_html += "<th>机型</th><td>"+middle.planeCode+"</td><th>经停信息</th><td colspan='3'>"+middle.stop+"</td></tr>" ;
					middle_html += "<tr><th>出发机场</th><td>"+middle.departAirPort+"</td><th>起飞时间</th><td>"+middle.departTime+"</td>" ;
					middle_html += "<th>到达机场</th><td>"+middle.arriveAirPort+"</td><th>到达时间</th><td>"+middle.arriveTime+"</td><th>到达偏移时间</th><td>"+middle.addDays+"</td><th>出发城市</th><td>"+middle.fromCity+"</td><th>到达城市</th><td>"+middle.toCity+"</td></tr>" ;
				}
		    }
		}

		var back_html = '' ;
		for(var i=0;i<flight.back.length;i++){
			var back = flight.back[i] ;
			back_html += '<tr>' ;
			if(i==0){
				back_html += "<td rowspan='"+flight.back.length*2+"' class='back-air-range'><span class='back-flight-icon'></span><br/><span>返程</span></td>" ;
			}
			back_html += "<th>第几天</th><td>"+back.offsetDays+"</td><th>航司名称</th><td>"+back.airLineName+"</td>" ;
			back_html += "<th>航班号</th><td>"+back.flightNum+"</td><th>舱位</th><td>"+back.cabin+"</td>" ;
			back_html += "<th>机型</th><td>"+back.planeCode+"</td><th>经停信息</th><td colspan='3'>"+back.stop+"</td></tr>" ;
			back_html += "<tr><th>出发机场</th><td>"+back.departAirPort+"</td><th>起飞时间</th><td>"+back.departTime+"</td>" ;
			back_html += "<th>到达机场</th><td>"+back.arriveAirPort+"</td><th>到达时间</th><td>"+back.arriveTime+"</td><th>到达偏移时间</th><td>"+back.addDays+"</td><th>出发城市</th><td>"+back.fromCity+"</td><th>到达城市</th><td>"+back.toCity+"</td></tr>" ;
		}

		$(".flight-info.view").html(base_html + go_html + middle_html + back_html) ;

	}

	function resetFlightEditView(flight){
		$(".flight-info.edit").find(".go-air-range-block").remove() ;
		$(".flight-info.edit").find(".middle-air-range-container").remove() ;
		$(".flight-info.edit").find(".back-air-range-block").remove() ;
		$(".flight-info.edit").find(".long-split-line").remove() ;

		var go_html = '<section class="go-air-range-block">' ;
		go_html += '<div class="table-top-title"><span class="go-flight title-flag"><span class="go-flight-icon"></span>' ;
		go_html += '<span>去程</span></span><span class="add-oper add-transfer"><span class="plus-icon"></span><span>添加中转</span></span>' ;
		go_html += '</div>' ;
		for(var i=0;i<flight.go.length;i++){
			if(i != 0){
				go_html += '<section class="transfer-flight-block clearfix"> <div class="short-split-line"></div>' ;
			}
			go_html += getFlightEditTable(flight.go[i]) ;
			if(i != 0){
				go_html += '<div class="del-oper del-transfer"><span class="del-icon"></span><span>删除中转信息</span></div>' ;
			}
			if(i != 0){
				go_html += '</section>' ;
			}
		}
		go_html += '</section>' ;

		$(".flight-info.edit").append(go_html) ;

		var mrange = flight.middle;
		var middle_html = '<section class="middle-air-range-container"><div class="long-split-line"></div>' ;
		var mflag = false ;
		var idx = 0 ;
		for(var prop in mrange){
			mflag = true ;
			middle_html += '<section class="middle-air-range-block clearfix"><div class="table-top-title">' ;
			middle_html += '<span class="middle-flight title-flag"><span class="middle-flight-icon"></span><span class="flight-num">第'+prop+'程</span></span>' ;
			if(idx == 0){
				middle_html += '<span class="add-oper add-middle-flight"><span class="plus-icon"></span><span>添加航程</span></span>' ;
			}else{
				middle_html += '<span class="add-oper add-middle-flight" styke="display:none;"><span class="plus-icon"></span><span>添加航程</span></span>' ;
			}
			idx ++ ;
			middle_html += '<span class="add-oper add-transfer"><span class="plus-icon"></span><span>添加中转</span></span>' ;
			middle_html += '</div>' ;
			if(mrange.hasOwnProperty(prop)){
		        for(var i=0;i<mrange[prop].length;i++){
					var middle = mrange[prop][i] ;
		            if(i==0){
		            	middle_html += '<section class="clearfix">' ;
		            }else{
		            	middle_html += '<section class="transfer-flight-block clearfix"><div class="short-split-line"></div>' ;
		            }

					middle_html += getFlightEditTable(middle) ;
					if(i==0){
						middle_html += '<div class="del-oper del-middle-flight"><span class="del-icon"></span><span>删除中间程信息</span></div></section>' ;
					}else{
						middle_html += '<div class="del-oper del-transfer"><span class="del-icon"></span><span>删除中转信息</span></div></section>' ;
					}
		        }
		    }
			middle_html += '</section>' ;
		}
		if(!mflag){
        	middle_html += '<div class="long-split-line"></div><section class="middle-air-range-block clearfix">' ;
			middle_html += '<div class="table-top-title"><span class="middle-flight title-flag"><span class="middle-flight-icon"></span>' ;
			middle_html += '<span class="flight-num">中间程</span></span><span class="add-oper add-middle-flight">' ;
			middle_html += '<span class="plus-icon"></span><span>添加航程</span></span><span class="add-oper add-transfer" style="display: none;">' ;
			middle_html += '<span class="plus-icon"></span><span>添加中转</span></span>' ;
			middle_html += '</div></section>' ;
		}
		middle_html += '</section>' ;
		$(".flight-info.edit").append(middle_html) ;

		var back_html = '<section class="back-air-range-block"><div class="long-split-line"></div>' ;
		back_html += '<div class="table-top-title"><span class="back-flight title-flag"><span class="back-flight-icon"></span>' ;
		back_html += '<span>返程</span></span>' ;
		if(flight.back.length > 0){
			back_html += '<span class="add-oper add-transfer"><span class="plus-icon"></span><span>添加中转</span></span>' ;
			back_html += '<span class="add-oper add-back-flight" style="display: none;"><span class="plus-icon"></span><span>添加航程</span></span>' ;
		}else{
			back_html += '<span class="add-oper add-transfer" style="display: none;"><span class="plus-icon"></span><span>添加中转</span></span>' ;
			back_html += '<span class="add-oper add-back-flight"><span class="plus-icon"></span><span>添加航程</span></span>' ;
		}
		back_html += '</div>' ;
		for(var i=0;i<flight.back.length;i++){
			if(i != 0){
				back_html += '<section class="transfer-flight-block clearfix"> <div class="short-split-line"></div>' ;
			}else{
				back_html += '<section class="clearfix">' ;
			}
			back_html += getFlightEditTable(flight.back[i]) ;
			if(i != 0){
				back_html += '<div class="del-oper del-transfer"><span class="del-icon"></span><span>删除中转信息</span></div>' ;
			}else{
				back_html += '<div class="del-oper del-back-flight"><span class="del-icon"></span><span>删除返程信息</span></div>' ;
			}
			back_html += '</section>' ;
		}
		back_html += '</section>' ;
		$(".flight-info.edit").append(back_html) ;

		$(".flight-info.edit").append('<div class="long-split-line"></div>') ;
	}

//	function getFlightEditTable(flight){
//		var html = '<table class="flight-form-table"><colgroup><col width="79"/><col width="80"/><col width="79"/><col width="80"/><col width="79"/><col width="80"/>' ;
//		html += '<col width="79"/><col width="80"/><col width="85"/><col width="80"/><col width="85"/><col width="90"/><col width="85"/><col width="90"/><col width="85"/></colgroup>' ;
//        html += '<tbody><tr>' ;
//        html +=	'<th><span class="red-star">*</span>第几天</th>' ;
//        html += '<td><input type="text" class="offsetDays" style="width:100px;" data-cv="required" value="'+flight.offsetDays+'"/></td>' ;
//        html += '<th><span class="red-star">*</span>航司名称</th>' ;
//        html += '<td><input type="text" class="airLine" style="width:100px;" value="'+flight.airLine+'" data-cv="required"/></td>' ;
//        html += '<th><span class="red-star">*</span>航班号</th>' ;
//        html += '<td><input type="text" class="flightNo" style="width:100px;" value="'+flight.flightNo+'" data-cv="required"/></td>' ;
//        html += '<th>舱位</th>' ;
//        html += '<td><input type="text" class="cabin" value="'+flight.cabin+'" style="width:80px;" /></td>' ;
//        html += '<th><span class="red-star">*</span>机型</th>' ;
//        html += '<td><input type="text" class="flightModel" value="'+flight.flightModel+'" style="width:80px;" /></td>' ;
//        html += '<th>经停信息</th>' ;
//        html += '<td colspan="3"><input type="text" class="stop" value="'+flight.stop+'" style="width:200px;" /></td></tr>' ;
//        html += '<tr>' ;
//        html += '<th><span class="red-star">*</span>起飞机场</th>' ;
//        html += '<td><input type="text" class="fromAirPort" style="width:100px;" value="'+flight.fromAirPort+'" data-cv="required"/></td>' ;
//        html += '<th><span class="red-star">*</span>起飞时间</th>' ;
//        html += '<td><input type="text" class="departureTime" style="width:100px;" value="'+flight.departureTime+'" data-cv="required"/></td>' ;
//        html += '<th><span class="red-star">*</span>到达机场</th>' ;
//        html += '<td><input type="text" class="toAirPort" style="width:100px;" value="'+flight.toAirPort+'" data-cv="required"/></td>' ;
//        html += '<th><span class="red-star">*</span>到达时间</th>' ;
//        html += '<td colspan="3">' ;
//        html += '<input type="text" class="arrivalTime" style="width:100px;" value="'+flight.arrivalTime+'" data-cv="required"/>' ;
//        html += '<div class="dropdown" style="width: 67px;top:1px;">' ;
//        html += '<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="true">' ;
//        html += '<span class="activeFonts">+'+flight.addDays+'日</span><span class="caret"></span></a>' ;
//        html += '<ul class="dropdown-menu">' ;
//        html += '<li onclick=setDropDownMenu(this,0,\"+0日\",\".addDays\")><a href="javascript:void(0);">+0日</a></li>' ;
//        html += '<li onclick=setDropDownMenu(this,1,\"+1日\",\".addDays\")><a href="javascript:void(0);">+1日</a></li>' ;
//        html += '<li onclick=setDropDownMenu(this,2,\"+2日\",\".addDays\")><a href="javascript:void(0);">+2日</a></li>' ;
//        html += '<li onclick=setDropDownMenu(this,3,\"+3日\",\".addDays\")><a href="javascript:void(0);">+3日</a></li>' ;
//        html += '</ul>' ;
//        html += '<input type="hidden" class="addDays" value="'+flight.addDays+'">' ;
//        html += '</div></td>' ;
//        html += '<th><span class="red-star">*</span>出发城市</th><td><input type="text" class="fromCity" value="'+flight.fromCity+'" style="width:80px;" /></td>' ;
//        html += '<th><span class="red-star">*</span>到达城市</th><td><input type="text" class="toCity" value="'+flight.toCity+'" style="width:80px;" /></td></tr>' ;
//        html += '</tbody>' ;
//        html += '</table>' ;
//        return html ;
//	}

	function getFlightEditTable(flight){
		var html = '<table class="flight-form-table"><colgroup><col width="79"/><col width="80"/><col width="79"/><col width="80"/><col width="79"/><col width="80"/>' ;
		html += '<col width="79"/><col width="80"/><col width="85"/><col width="80"/><col width="85"/><col width="90"/><col width="85"/><col width="90"/><col width="85"/></colgroup>' ;
        html += '<tbody><tr>' ;
        html +=	'<th><span class="red-star">*</span>第几天</th>' ;
        html += '<td><input type="text" class="offsetDays" style="width:100px;" data-cv="required" value="'+flight.offsetDays+'"/></td>' ;
        html += '<th><span class="red-star">*</span>航司名称</th>' ;
        html += '<td><input type="text" class="airLine" style="width:100px;" value="'+flight.airLineName+'" data-cv="required"/></td>' ;
        html += '<th><span class="red-star">*</span>航班号</th>' ;
        html += '<td><input type="text" class="flightNo" style="width:100px;" value="'+flight.flightNum+'" data-cv="required"/></td>' ;
        html += '<th>舱位</th>' ;
        html += '<td><input type="text" class="cabin" value="'+flight.cabin+'" style="width:80px;" /></td>' ;
        html += '<th><span class="red-star">*</span>机型</th>' ;
        html += '<td><input type="text" class="flightModel" value="'+flight.planeCode+'" style="width:80px;" /></td>' ;
        html += '<th>经停信息</th>' ;
        html += '<td colspan="3"><input type="text" class="stop" value="'+flight.stop+'" style="width:200px;" /></td></tr>' ;
        html += '<tr>' ;
        html += '<th><span class="red-star">*</span>起飞机场</th>' ;
        html += '<td><input type="text" class="fromAirPort" style="width:100px;" value="'+flight.departAirPort+'" data-cv="required"/></td>' ;
        html += '<th><span class="red-star">*</span>起飞时间</th>' ;
        html += '<td><input type="text" class="departureTime" style="width:100px;" value="'+flight.departTime+'" data-cv="required"/></td>' ;
        html += '<th><span class="red-star">*</span>到达机场</th>' ;
        html += '<td><input type="text" class="toAirPort" style="width:100px;" value="'+flight.arriveAirPort+'" data-cv="required"/></td>' ;
        html += '<th><span class="red-star">*</span>到达时间</th>' ;
        html += '<td colspan="3">' ;
        html += '<input type="text" class="arrivalTime" style="width:100px;" value="'+flight.arriveTime+'" data-cv="required"/>' ;
        html += '<div class="dropdown" style="width: 67px;top:1px;">' ;
        html += '<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="true">' ;
        html += '<span class="activeFonts">+'+flight.addDays+'日</span><span class="caret"></span></a>' ;
        html += '<ul class="dropdown-menu">' ;
        html += '<li onclick=setDropDownMenu(this,0,\"+0日\",\".addDays\")><a href="javascript:void(0);">+0日</a></li>' ;
        html += '<li onclick=setDropDownMenu(this,1,\"+1日\",\".addDays\")><a href="javascript:void(0);">+1日</a></li>' ;
        html += '<li onclick=setDropDownMenu(this,2,\"+2日\",\".addDays\")><a href="javascript:void(0);">+2日</a></li>' ;
        html += '<li onclick=setDropDownMenu(this,3,\"+3日\",\".addDays\")><a href="javascript:void(0);">+3日</a></li>' ;
        html += '</ul>' ;
        html += '<input type="hidden" class="addDays" value="'+flight.addDays+'">' ;
        html += '</div></td>' ;
        html += '<th><span class="red-star">*</span>出发城市</th><td><input type="text" class="fromCity" value="'+flight.fromCity+'" style="width:80px;" /></td>' ;
        html += '<th><span class="red-star">*</span>到达城市</th><td><input type="text" class="toCity" value="'+flight.toCity+'" style="width:80px;" /></td></tr>' ;
        html += '</tbody>' ;
        html += '</table>' ;
        return html ;
	}


	function selectOrderFlight(){
		$.ajax({
    	    type: 'POST',
    	    url: basepath + '/order/travelConfirm/selectOrderFlight' ,
    	    data: 'orderId='+$('#orderId').val(),
    	    dataType: 'json' ,
    	    success: function(result){
    	    	orderFlight = result;
    	    }
    	});
	}

	function editFlight(){
		resetFlightEditView(orderFlight) ;
		$("#add-flight-popup").modal("show");
	}

//	function editFlight(){
//		$.ajax({
//    	    type: 'POST',
//    	    url: basepath + '/product/cost/editFlight' ,
//    	    data: 'id='+$("#productId").val(),
//    	    dataType: 'json' ,
//    	    success: function(result){
//    	    	if(result.flag){
//    	    		resetFlightEditView(result.flight) ;
//    	    		$("#add-flight-popup").modal("show");
//    	    	}
//    	    }
//    	});
//	}

	function deleteFlight(){
		$.ajax({
    	    type: 'POST',
    	    url: basepath + '/product/cost/deleteFlight' ,
    	    data: 'id='+$("#id").html() ,
    	    dataType: 'json' ,
    	    success: function(result){
    	    	if(result){
    	    		$(".add-btn.add-flight-btn").show() ;
    	    		$('.flight-info.view').html('') ;
    	    	}
    	    }
    	});
	}

    //  添加/删除 中间航程 调整页面展示元素
    function resetMiddleFlight() {

        var $container = $(".middle-air-range-container");
        $container.children(".middle-air-range-block").find(".add-middle-flight").hide();
        $container.children(".middle-air-range-block:eq(0)").find(".add-middle-flight").show();

        $container.find(".middle-air-range-block").each(function(index){
            $(this).find(".flight-num").html("第" + (index+2) + "程");
        });

        if ($container.find(".middle-air-range-block").length == 1) {
            $container.find(".middle-air-range-block:eq(0)").find(".long-split-line").remove();
        }

        if ($container.find(".flight-form-table").length == 0) {
            $container.find(".middle-air-range-block").find(".flight-num").html("中间程");
        }
    }
