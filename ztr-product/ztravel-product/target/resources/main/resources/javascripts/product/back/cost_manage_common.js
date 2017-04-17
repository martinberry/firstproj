var hotels = [] ;
var old_hotel_html = '' ;
var old_flight_html = '' ;
var old_hotel_list_table = '' ;
var old_shuttle_supplier = '' ;
var old_zenbook_supplier = '' ;
var old_wifi_supplier = '' ;
var old_other_supplier = '' ;
var mode = 'view' ;

//额外内容成本
var addition_reg1 = /^\d{1,5}$/ ;

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

//【房型】
var h1 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D（）\(\)——,\.，。\/\\:;：；“”’]{1,20}$/ ;
//酒店校验结果
var isHotelOk = true ;

//【供应商,开户银行】
var s1 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D·~!！@#￥%……&*（）\(\)——+=\-\s\$\{\}\[\]【】<>《》,\.，。\?\/、\\:;：；“”’]{0,60}$/ ;
//【业务联系人、开户名】
var s2 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D·~!！@#￥%……&*（）\(\)——+=\-\$\{\}\[\]【】<>《》,\.，。\?\/、\\:;：；“”’]{0,10}$/ ;
//【内部联系人】
var s3 = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D·~!！@#￥%……&*（）\(\)——+=\-\$\{\}\[\]【】<>《》,\.，。\?\/、\\:;：；“”’]{1,10}$/ ;
//【电话】
var s4 = /^[\d]{11}$/ ;
//【账号】
var s5 = /^[\d]{0,19}$/ ;
//【电邮】
var s6 = /^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/ ;
//【传真】
var s7 = /^(\d{3,4}-)?\d{7,8}$/ ;


$(function(){

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

	old_hotel_html = $(".hotel-form-module").html() ;

	old_hotel_list_table = $(".commonTab.hotel-list-table").html() ;
	
	initSupplier(".hotel-pickup.addi-info-block","shuttle") ;
	initSupplier(".travel-book.addi-info-block","zenbook") ;
	initSupplier(".wifi-service.addi-info-block","wifi") ;
	initSupplier(".other-service.addi-info-block","other") ;
	
	$(".commonButton.red-fe6869Button.hotel.save").click(function(){
		isHotelOk = true ;
		var bean = {} ;
		var hs = [] ;
		var errMsg = '' ;
		$(".hotel-form-table").each(function(){
			var h = {} ;
			h.id = $(this).attr('class').replace('hotel-form-table ','') ;
			var hotelName = $(this).find(".hotelName").val() ;
			h.checkinDays = [] ;
			var msg = '' ;
			$(this).find(".day-item").each(function(){
				if($(this).hasClass('active')){
					h.checkinDays[h.checkinDays.length] = $(this).html() ;
				}
			}) ;
			h.highLights = $(this).find(".highLights").val().replace(new RegExp(/(>)/g),'&gt').replace(new RegExp(/(<)/g),'&lt') ;
			if(!c2.test(h.highLights)){
				isHotelOk = false ;
				msg += '特色,' ;
			}
			h.roomType = $(this).find(".roomType").val() ;
			if(!h1.test(h.roomType)){
				isHotelOk = false ;
				msg += '房型,' ;
			}
			h.bedType = $(this).find(".bedType").val() ;
			h.breakFestType = $(this).find(".breakFestType").val() ;
			h.hotelRemark = $(this).find(".hotelRemark").val().replace(new RegExp(/(>)/g),'&gt').replace(new RegExp(/(<)/g),'&lt') ;
			if(!c1.test(h.hotelRemark)){
				isHotelOk = false ;
				msg += '酒店备注,' ;
			}
			h.innerRemark = $(this).find(".innerRemark").val().replace(new RegExp(/(>)/g),'&gt').replace(new RegExp(/(<)/g),'&lt') ;
			if(!c1.test(h.innerRemark)){
				isHotelOk = false ;
				msg += '内部备注,' ;
			}
			if($("#producttype").val() == 'combination'){
				h.supplier = $(this).find(".hotelSupplierId").val() == 'null' ? '' : $(this).find(".hotelSupplierId").val() ;
			}

			if(msg.length > 0){
				errMsg += hotelName + ":" + msg + m2 + ";" ;
			}
			hs[hs.length] = h ;
		})
		bean.hotels = hs ;
		bean.id = $("#id").html() ;
		if(isHotelOk){
			$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/cost/saveHotel' ,
	    	    data: JSON.stringify(bean) ,
	    	    contentType : 'application/json',
	    	    dataType: 'json' ,
	    	    success: function(result){
	    	    	if(result.flag){
	    	    		resetHotelView(result.hotels) ;
	    	    		$("#add-hotel-popup").modal("hide");
	    	    	}
	    	    }
	    	});
		}else{
			alert(errMsg) ;
		}
	});

	$(".commonButton.gray-bbbButton.flight.cancel").click(function(){
		$(".flight-info.edit").html(old_flight_html) ;
	});

	$(".commonButton.gray-bbbButton.hotellist.cancel").click(function(){
		resetHotelList() ;
		$("#hotel-list-popup").modal("hide");
		$("#add-hotel-popup").modal("show");
	});

	$(".commonButton.gray-bbbButton.hotel.cancel").click(function(){
		$(".hotel-form-module").html(old_hotel_html) ;
		window.location.reload() ;
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
        req.airRangeRemark = $("#airRange-remark").val() ;
        if(!c1.test(req.airRangeRemark)){
        	isFlightOk = false ;
        	alert("航程备注" + m2) ;
        }
        req.innerRemark = $("#inner-remark").val() ;
        if(!c1.test(req.innerRemark)){
        	isFlightOk = false ;
        	alert("内部备注" + m2) ;
        }
        req.id = $("#id").html() ;

        if(isFlightOk){
        	$.ajax({
        	    type: 'POST',
        	    url: basepath + '/product/cost/saveFlight' ,
        	    data: JSON.stringify(req) ,
        	    contentType : 'application/json',
        	    dataType: 'json' ,
        	    success: function(result){
        	    	if(result.flag){
        	    		resetFlightView(result.flight) ;
        	    		$("#add-flight-popup").modal("hide");
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

    $(".add-hotel-btn").click(function(){
        $("#hotel-list-popup").modal("show");
    });

    $(".hotel-list-table a").click(function(){
        $("#hotel-list-popup").modal("hide");
        $("#add-hotel-popup").modal("show");
    });

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

    //酒店搜索
    $(".commonButton.blue-45c8dcButton.haveIconButton").click(function(){
    	submitFunc2() ;
    }) ;

    //保存
    $(".commonButton.red-fe6869Button.top").click(function(){
    	saveCheck(false) ;
    }) ;

    //保存&下一步
    $(".commonButton.whiteBtn.top").click(function(){
    	saveCheck(true) ;
    }) ;

});

	function checkHotelSupplier(that, val, regex, message){
		if(!regex.test(val)){
			that.focus() ;
			that.val("");
			that.attr("placeholder",message) ;
			return false ;
		}else{
			return true ;
		}
	}

	function addHotel(id){
		$.ajax({
    	    type: 'POST',
    	    url: basepath + '/product/cost/addHotel' ,
    	    data: 'hotelId='+id ,
    	    dataType: 'json' ,
    	    success: function(result){
    	    	if(result){
    	    		var othersCheckedinDays = {} ;
    	    		var checkedinDays = {} ;
    	    		for(var i=0;i<hotels.length;i++){
    	    			if(result.id == hotels[i].id){
    	    				alert('已选择该酒店') ;
    	    				return ;
    	    			}
    	    			for(var j=0;j<hotels[i].checkinDays.length;j++){
	    					othersCheckedinDays[hotels[i].checkinDays[j]] = hotels[i].checkinDays[j] ;
    	    			}
    	    		}

    	    		var hotel = {} ;
    	    		hotel.id = result.id ;
    	    		hotel.name = result.hotelNameCn ;
    	    		hotel.checkinDays = [] ;
    	    		hotels[hotels.length] = hotel ;
    	    		$("#hotel-list-popup").modal("hide") ;
    	    		$(".table-tab-switch.hotel.edit").find("li").each(function(){
    	    			$(this).removeClass("current") ;
    	    		});
    	    		$(".table-tab-switch.hotel.edit").find("li:eq(-1)").remove() ;
    	    		var cutname = result.hotelNameCn ;
    	    		if(result.hotelNameCn != null && result.hotelNameCn != undefined){
    	    			if(result.hotelNameCn.length > 6){
    	    				cutname = result.hotelNameCn.substr(0,6) + "..." ;
    	    			}
    	    		}
    	    		$(".table-tab-switch.hotel.edit").append("<li class='current'><span onclick=swapHotelEditTab(this,'"+hotel.id+"')>"+cutname+"</span><span onclick=deleteCurrentHotel('"+hotel.id+"')>x</span></li>");
    	    		$(".table-tab-switch.hotel.edit").append("<li onclick='hotelSearchListPage();'><span>+ 添加酒店</span></li>");
    	    		$(".hotel-form-module").find(".hotel-form-table").hide() ;
    	    		$(".hotel-form-module").append(createHotelEditForm(hotel,othersCheckedinDays,checkedinDays)) ;
    	    		$("#add-hotel-popup").modal("show");
    	    	}
    	    }
    	});
	}

	function pickOne(that,id){
		if($(that).hasClass("disable")){
			return ;
		}
		var oper = '+' ;
		if($(that).hasClass("active")){
			oper = '-' ;
		}
		$(that).toggleClass("active");
		var days = [] ;
		days[0] = $(that).html() ;
		setCheckinDays(id,days,oper) ;
	}
	
	function setCheckinDays(id,days,oper){
		for(var i=0;i<hotels.length;i++){
			if(id == hotels[i].id){
				if(oper == '+'){
					hotels[i].checkinDays = $.merge(hotels[i].checkinDays,days) ;
				}else{
					for(var j=0;j<days.length;j++){
						var ary = hotels[i].checkinDays ;
						ary.splice($.inArray(days[j],ary),1) ;
						hotels[i].checkinDays = ary ;
					}
				}
			}
		}
	}

	function pickAll(that,id){
		$(that).toggleClass("active");
		var days = [] ;
        if ($(that).hasClass("active")) {
        	$(".hotel-form-table."+id).find(".day-item").not(".disable").each(function(){
        		$(this).addClass("active") ;
        		days[days.length] = $(this).html() ;
        	});
        	setCheckinDays(id,days,'+') ;
        } else {
        	$(".hotel-form-table."+id).find(".day-item").not(".disable").each(function(){
        		$(this).removeClass("active") ;
        		days[days.length] = $(this).html() ;
        	});
        	setCheckinDays(id,days,'-') ;
        }
	}

	function createHotelEditForm(hotel,othersCheckedinDays,checkedinDays){
		var html = '<table class="hotel-form-table '+hotel.id+'"><colgroup><col width="65"/><col width="340"/><col width="85"/><col width="158"/><col width="67"/><col width=""/></colgroup>' ;
		html += '<tbody><tr><th>入住时间</th><td colspan="5"><div class="pick-up-night">第<span class="days-list">' ;
		var tripNights = parseInt($("#tripNights").html()) ;
		for(var i=1;i<=tripNights;i++){
		    if(othersCheckedinDays.hasOwnProperty(i)){
		    	html += '<span class="day-item disable" onclick=pickOne(this,"'+hotel.id+'")>'+i+'</span>' ;
		    }else if(checkedinDays.hasOwnProperty(i)){
		    	html += '<span class="day-item active" onclick=pickOne(this,"'+hotel.id+'")>'+i+'</span>' ;
		    }else{
		    	html += '<span class="day-item" onclick=pickOne(this,"'+hotel.id+'")>'+i+'</span>' ;
		    }
		}
		html += '</span>晚<span class="pick-all" onclick=pickAll(this,"'+hotel.id+'")>全程</span></div></td></tr>' ;
		html += '<tr><th style="vertical-align: top;"><span class="red-star">*</span>酒店特色</th><td colspan="5"><a class="view-hotel-detail" href=javascript:viewHotel("'+hotel.id+'");>查看酒店详情</a><br/>' ;
		if(hotel != null && hotel.highLights != null){
			html += '<textarea style="height: 160px;" class="highLights">'+ hotel.highLights +'</textarea>' ;
		}else{
			html += '<textarea style="height: 160px;" class="highLights"></textarea>' ;
		}
		html += '</td></tr><tr><th><span class="red-star">*</span>房型名称</th>' ;
		if(hotel != null && hotel.name != null){
			html += '<input type="hidden" class="hotelName" value="'+hotel.name+'"></input>' ;
		}else{
			html += '<input type="hidden" class="hotelName"></input>' ;
		}
		if(hotel != null && hotel.roomType != null){
			html += '<td><input class="roomType" type="text" style="width:326px;" value="'+ hotel.roomType +'" /></td>' ;
		}else{
			html += '<td><input class="roomType" type="text" style="width:326px;"/></td>' ;
		}
		html += '<th><span class="red-star">*</span>床型</th><td><div class="dropdown" style="width: 148px;"><a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">' ;
		if(hotel != null && hotel.bedTypeCN != null){
			html += '<span class="activeFonts">'+hotel.bedTypeCN+'</span><span class="caret"></span></a>' ;
		}else{
			html += '<span class="activeFonts">大床</span><span class="caret"></span></a>' ;
		}
		html += '<ul class="dropdown-menu">' ;
		html += '<li onclick=setDropDownMenu(this,\"BIG\",\"大床\",\".bedType\")><a href="javascript:void(0);">大床</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"DOUBLE\",\"双床\",\".bedType\")><a href="javascript:void(0);">双床</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"INDEFINITE\",\"不定\",\".bedType\")><a href="javascript:void(0);">不定</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"OTHERS\",\"其他\",\".bedType\")><a href="javascript:void(0);">其他</a></li>' ;
		html += '</ul>' ;
		if(hotel != null && hotel.bedType != null){
			html += '<input class="bedType" type="hidden" value="'+hotel.bedType+'" />' ;
		}else{
			html += '<input class="bedType" type="hidden" value="BIG" />' ;
		}
		html += '</div></td></td><th>早餐</th><td><div class="dropdown" style="width: 148px;"><a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">' ;
		if(hotel != null && hotel.breakFestTypeCN != null){
			html += '<span class="activeFonts">'+hotel.breakFestTypeCN+'</span>' ;
		}else{
			html += '<span class="activeFonts">双早</span>' ;
		}
		html += '<span class="caret"></span></a><ul class="dropdown-menu">' ;
		html += '<li onclick=setDropDownMenu(this,\"DOUBLE\",\"双早\",\".breakFestType\")><a href="javascript:void(0);">双早</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"SINGLE\",\"单早\",\".breakFestType\")><a href="javascript:void(0);">单早</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"NONE\",\"无早\",\".breakFestType\")><a href="javascript:void(0);">无早</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"MORE\",\"更多\",\".breakFestType\")><a href="javascript:void(0);">更多</a></li>' ;
		html += '</ul>' ;
		if(hotel != null && hotel.breakFestTypeCN != null){
			html += '<input class="breakFestType" type="hidden" value="'+hotel.breakFestType+'" />' ;
		}else{
			html += '<input class="breakFestType" type="hidden" value="DOUBLE" />' ;
		}
		html += '</div></td></td></tr><tr><th>酒店备注</th><td colspan="5">' ;
		if(hotel != null && hotel.hotelRemark != null){
			html += '<textarea class="hotelRemark" style="height: 60px;">'+ hotel.hotelRemark +'</textarea>' ;
		}else{
			html += '<textarea class="hotelRemark" style="height: 60px;"></textarea>' ;
		}
		html += '</td></tr><tr><th>内部备注</th><td colspan="5">' ;
		if(hotel != null && hotel.innerRemark != null){
			html += '<textarea class="innerRemark" style="height: 60px;">'+ hotel.innerRemark +'</textarea>' ;
		}else{
			html += '<textarea class="innerRemark" style="height: 60px;"></textarea>' ;
		}
		html += '</td></tr>' ;
		if($("#producttype").val() == 'combination'){
			var supplierName_tmp = '' ;
			var supplierId_tmp = '' ;
			if(hotel.supplier != null){
				supplierName_tmp = hotel.supplier.supplierName ;
				supplierId_tmp = hotel.supplier.supplierId ;
			}
			html += '<tr><th>供应商</th><td><div class="dropdown" style="width: 320px;"><a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">' ;
			html += '<span class="activeFonts">'+supplierName_tmp+'</span><span class="caret"></span></a>' ;
			html += $("#all_suppliers").html() ;
			html += '<input type="hidden" class="hotelSupplierId" value="'+supplierId_tmp+'"></div></td></tr>' ;
		}
		html += '</tbody></table>' ;
		return html ;
	}

	function hotelSearchListPage(){
		resetHotelList() ;
		$("#hotel-list-popup").modal("show");
        $("#add-hotel-popup").modal("hide");
	}

	function resetHotelList(){
		$("#hotelNameInputer").val("") ;
		$("#pagination").html("") ;
		$("input[name=pageNo]").val(1) ;
		$("input[name=pageSize]").val(10) ;
		$(".commonTab.hotel-list-table").html(old_hotel_list_table) ;
	}

	function setDropDownMenu(that,val,desc,className){
		$(that).parent().parent().find(className).val(val) ;
		$(that).parent().parent().find(".activeFonts").html(desc) ;
	}

	//提交查询请求
	function submitFunc(){
		var criteria = {};
		var reg = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D（）\s\(\)——,\.，。\/\\:;：；“”’""']{0,60}$/ ;
		criteria.hotelNameCn = $("#hotelNameInputer").val();
		if(!reg.test(criteria.hotelNameCn)){
			$("#hotelNameInputer").val("") ;
			$("#hotelNameInputer").attr("placeholder","输入格式有误，请重新输入") ;
			return ;
		}
		criteria.pageNo = $("input[name=pageNo]").val();
		criteria.pageSize = $("input[name=pageSize]").val();

		$.ajax({
			type : "POST",
			url : basepath + '/product/cost/searchHotel' ,
			data : JSON.stringify(criteria),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "html",
			success : function(result) {
				var dataArray = result.split("<-split->");
				var tableData = dataArray[0];
				var paginationData = dataArray[1];
				$(".commonTab.hotel-list-table tbody").html(tableData);
				$("#pagination").html(paginationData);
			},
		});
	}

	//提交查询请求
	function submitFunc2(){
		var criteria = {};
		var reg = /^[a-zA-Z0-9\u4E00-\u9FA5\uF900-\uFA2D（）\s\(\)——,\.，。\/\\:;：；“”’""']{0,60}$/ ;
		criteria.hotelNameCn = $("#hotelNameInputer").val();
		if(!reg.test(criteria.hotelNameCn)){
			$("#hotelNameInputer").val("") ;
			$("#hotelNameInputer").attr("placeholder","输入格式有误，请重新输入") ;
			return ;
		}
		criteria.pageNo = 1;
		criteria.pageSize = $("input[name=pageSize]").val();

		$.ajax({
			type : "POST",
			url : basepath + '/product/cost/searchHotel' ,
			data : JSON.stringify(criteria),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "html",
			success : function(result) {
				var dataArray = result.split("<-split->");
				var tableData = dataArray[0];
				var paginationData = dataArray[1];
				$(".commonTab.hotel-list-table tbody").html(tableData);
				$("#pagination").html(paginationData);
			},
		});
	}

	function getFlightJson(that, airRange, index){
		var flight = {} ;
		flight.stop = that.find(".stop").val() ;
		checkFlight(that.find(".stop"), flight.stop, p9, m1) ;

		flight.offsetDays = that.find(".offsetDays").val() ;
		checkFlight(that.find(".offsetDays"), flight.offsetDays, p1, m1) ;

		flight.airLine = that.find(".airLine").val() ;
		checkFlight(that.find(".airLine"), flight.airLine, p2, m1) ;

		flight.flightNo = that.find(".flightNo").val() ;
		checkFlight(that.find(".flightNo"), flight.flightNo, p3, m1) ;

		flight.cabin = that.find(".cabin").val() ;
		checkFlight(that.find(".cabin"), flight.cabin, p4, m1) ;

		flight.flightModel = that.find(".flightModel").val() ;
		checkFlight(that.find(".flightModel"), flight.flightModel, p5, m1) ;

		flight.fromAirPort = that.find(".fromAirPort").val() ;
		checkFlight(that.find(".fromAirPort"), flight.fromAirPort, p6, m1) ;

		flight.departureTime = that.find(".departureTime").val() ;
		checkFlight(that.find(".departureTime"), flight.departureTime, p7, m1) ;

		flight.toAirPort = that.find(".toAirPort").val() ;
		checkFlight(that.find(".toAirPort"), flight.toAirPort, p6, m1) ;

		flight.arrivalTime = that.find(".arrivalTime").val() ;
		checkFlight(that.find(".arrivalTime"), flight.arrivalTime, p7, m1) ;

		flight.addDays = that.find(".addDays").val() ;
		flight.airRange = airRange ;
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
	var deleteHotelLock = false ;
	function deleteCurrentHotel(id){
		if(!deleteHotelLock){
			deleteHotelLock = true ;
			$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/product/cost/deleteSingleHotel' ,
	    	    data: 'id='+$("#id").html() + "&hotelId="+id ,
	    	    dataType: 'json' ,
	    	    success: function(result){
	    	    	deleteHotelLock = false ;
	    	    	if(result){
	    	    		var checkedDays = {} ;
	    	    		$(".hotel-form-module").find(".hotel-form-table").each(function(){
	    	    			if($(this).hasClass(id)){
	    	    				$(this).find(".day-item").each(function(){
	    	    					if($(this).hasClass("active")){
	    	    						checkedDays[$(this).html()] = $(this).html() ;
	    	    					}
	    	    				});
	    	    				$(this).remove() ;
	    	    			}
	    	    		});
	    	    		var tripNights = parseInt($("#tripNights").html()) ;
	    	    		$(".hotel-form-module").find(".hotel-form-table").each(function(){
	    	    			$(this).find(".day-item").each(function(){
	    	    				if(checkedDays.hasOwnProperty($(this).html())){
	    	    					$(this).removeClass("disable") ;
		    	    		    }
	    	    			});
	    	    		});
	    	    		$(".table-tab-switch.hotel.edit").find("li").each(function(){
	    	    			if($(this).hasClass('current')){
	    	    				$(this).remove() ;
	    	    			}
	    	    		})
	    	    		for(var h=0;h<hotels.length;h++){
	    	    			if(hotels[h].id == id){
	    	    				hotels.splice(h,1);
	    	    			}
	    	    		}
	    	    		if($(".hotel-form-module").find(".hotel-form-table").size() > 0){
	    	    			swapHotelEditTab($($(".table-tab-switch.hotel.edit").find('span')[0]),$($(".hotel-form-module").find(".hotel-form-table")[0]).attr('class').split(" ")[1]) ;
	    	    		}
	    	    	}else{
	    	    		alert("删除酒店失败") ;
	    	    	}
	    	    },error: function(){
	    	    	deleteHotelLock = false ;
	    	    	alert("删除酒店失败") ;
	    	    }
	    	});
		}
		
	}

	function swapHotelEditTab(that,id){
		$(".hotel-form-module").find(".hotel-form-table").hide() ;
		$(".hotel-form-module").find(".hotel-form-table").each(function(){
			if($(this).hasClass(id)){
				$(this).show() ;
			}
		});
		$(".table-tab-switch.hotel.edit").find("li").removeClass("current") ;
		$(that).parent().addClass('current') ;
		var othersCheckedinDays = {} ;
		var checkedinDays = {} ;
		for(var i=0;i<hotels.length;i++){
			if(id != hotels[i].id){
				for(var j=0;j<hotels[i].checkinDays.length;j++){
					othersCheckedinDays[hotels[i].checkinDays[j]] = hotels[i].checkinDays[j] ;
				}
			}else{
				for(var j=0;j<hotels[i].checkinDays.length;j++){
					checkedinDays[hotels[i].checkinDays[j]] = hotels[i].checkinDays[j] ;
				}
			}
		}

		$(".hotel-form-table."+id).find(".day-item").each(function(){
			if(othersCheckedinDays.hasOwnProperty($(this).html())){
				$(this).addClass('disable') ;
				$(this).removeClass('active') ;
			}else if(checkedinDays.hasOwnProperty($(this).html())){
				$(this).removeClass('disable') ;
				$(this).addClass('active') ;
			}else{
				$(this).removeClass('disable') ;
				$(this).removeClass('active') ;
			}
		});
	}

	function swapHotelViewTab(that,id){
		$(".hotel-info.view").find(".hotel-info-table").hide() ;
		$(".hotel-info.view").find(".hotel-info-table").each(function(){
			if($(this).hasClass(id)){
				$(this).show() ;
			}
		});
		$(".table-tab-switch.hotel.view").find("li").removeClass("current") ;
		$(that).addClass('current') ;
	}

	function checkCostPrice(price){
		if(price != null){
			var re = /^[1-9]+[0-9]*]*$/;
			var flag = true ;
			if(price instanceof Array){
				for(var i=0;i<price.length;i++){
					if(price[i] != null && (!re.test(price[i]) || parseInt(price[i]).toString().length < 1 || parseInt(price[i]).toString().length > 6)){
						flag = false ;
					}
				}
			}else{
				if(!re.test(price) || parseInt(price).toString().length < 1 || parseInt(price).toString().length > 6){
					flag = false ;
				}
			}
			return flag ;
		}else{
			return true ;
		}
	}

	function resetHotelView(hotels){
		$(".add-btn.add-hotel-btn").hide() ;
		var hotel_html = '<div class="table-top-block clearfix"><ul class="table-tab-switch hotel view">' ;
		for(var i=0;i<hotels.length;i++){
			if(i == 0){
				hotel_html += "<li class='current' onclick=swapHotelViewTab(this,'"+hotels[i].id+"')><span>第"+hotels[i].checkinDaysStr+"晚</span></li>" ;
			}else{
				hotel_html += "<li onclick=swapHotelViewTab(this,'"+hotels[i].id+"')><span>第"+hotels[i].checkinDaysStr+"晚</span></li>" ;
			}
		}
		hotel_html += '</ul><span class="oper-link"><a href="javascript:editHotel() ;">编辑</a><a href="javascript:deleteHotel() ;">删除</a>' ;
		hotel_html += '</span></div>' ;
		for(var i=0;i<hotels.length;i++){
			if(i == 0){
				hotel_html += '<table class="hotel-info-table '+hotels[i].id+'">' ;
			}else{
				hotel_html += '<table class="hotel-info-table '+hotels[i].id+'" style="display:none">' ;
			}
			hotel_html += '<colgroup><col width="79"/><col width="330"/><col width="80"/><col width="100"/><col width="80"/><col width="115"/><col width="80"/><col width="110"/></colgroup><tbody>' ;
			hotel_html += '<tr><th>酒店名称</th><td>'+hotels[i].name+'</td><th class="normal">星级</th><td>'+hotels[i].rate+'</td>' ;
			hotel_html += '<th class="normal">目的地</th><td>'+hotels[i].dest+'</td><th class="normal">房型</th><td>'+hotels[i].roomType+'</td>' ;
			hotel_html += '</tr><tr><th style="height:80px;">酒店特色</th><td colspan="7" style="height:80px;">'+hotels[i].highLights+'</td></tr>' ;
			hotel_html += '<tr><th>酒店备注</th><td colspan="7">'+hotels[i].hotelRemark+'</td></tr><tr><th>内部备注</th><td colspan="7">'+hotels[i].innerRemark+'</td>' ;
			hotel_html += '</tr></tbody></table>' ;
		}
		$(".hotel-info.view").html(hotel_html) ;
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
			go_html += "<th>第几天</th><td>"+go.offsetDays+"</td><th>航司名称</th><td>"+go.airLine+"</td>" ;
			go_html += "<th>航班号</th><td>"+go.flightNo+"</td><th>舱位</th><td>"+go.cabin+"</td>" ;
			go_html += "<th>机型</th><td>"+go.flightModel+"</td><th>经停信息</th><td colspan='3'>"+go.stop+"</td></tr>" ;
			go_html += "<tr><th>出发机场</th><td>"+go.fromAirPort+"</td><th>起飞时间</th><td>"+go.departureTime+"</td>" ;
			go_html += "<th>到达机场</th><td>"+go.toAirPort+"</td><th>到达时间</th><td>"+go.arrivalTime+"</td><th>到达偏移时间</th><td>"+go.addDays+"</td><th>出发城市</th><td>"+go.fromCity+"</td><th>到达城市</th><td>"+go.toCity+"</td></tr>" ;
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
					middle_html += "<th>第几天</th><td>"+middle.offsetDays+"</td><th>航司名称</th><td>"+middle.airLine+"</td>" ;
					middle_html += "<th>航班号</th><td>"+middle.flightNo+"</td><th>舱位</th><td>"+middle.cabin+"</td>" ;
					middle_html += "<th>机型</th><td>"+middle.flightModel+"</td><th>经停信息</th><td colspan='3'>"+middle.stop+"</td></tr>" ;
					middle_html += "<tr><th>出发机场</th><td>"+middle.fromAirPort+"</td><th>起飞时间</th><td>"+middle.departureTime+"</td>" ;
					middle_html += "<th>到达机场</th><td>"+middle.toAirPort+"</td><th>到达时间</th><td>"+middle.arrivalTime+"</td><th>到达偏移时间</th><td>"+middle.addDays+"</td><th>出发城市</th><td>"+middle.fromCity+"</td><th>到达城市</th><td>"+middle.toCity+"</td></tr>" ;
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
			back_html += "<th>第几天</th><td>"+back.offsetDays+"</td><th>航司名称</th><td>"+back.airLine+"</td>" ;
			back_html += "<th>航班号</th><td>"+back.flightNo+"</td><th>舱位</th><td>"+back.cabin+"</td>" ;
			back_html += "<th>机型</th><td>"+back.flightModel+"</td><th>经停信息</th><td colspan='3'>"+back.stop+"</td></tr>" ;
			back_html += "<tr><th>出发机场</th><td>"+back.fromAirPort+"</td><th>起飞时间</th><td>"+back.departureTime+"</td>" ;
			back_html += "<th>到达机场</th><td>"+back.toAirPort+"</td><th>到达时间</th><td>"+back.arrivalTime+"</td><th>到达偏移时间</th><td>"+back.addDays+"</td><th>出发城市</th><td>"+back.fromCity+"</td><th>到达城市</th><td>"+back.toCity+"</td></tr>" ;
		}

		var other_html = '' ;
		other_html += '<tr><th class="boldTh">机票备注</th><td colspan="15">'+flight.airRangeRemark+'</td></tr>' ;
		other_html += '<tr><th class="boldTh">内部备注</th><td colspan="15">'+flight.innerRemark+'</td></tr></tbody></table></div>' ;

		$(".flight-info.view").html(base_html + go_html + middle_html + back_html + other_html) ;

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
		$("#airRange-remark").html(flight.airRangeRemark) ;
		$("#inner-remark").html(flight.innerRemark) ;
	}

	function resetHotelEditView(hotel){
		var checkedinDays = {} ;
		var othersCheckedinDays = {} ;
		for(var i=0;i<hotels.length;i++){
			if(hotels[i].id != hotel.id){
				for(var j=0;j<hotels[i].checkinDays.length;j++){
					othersCheckedinDays[hotels[i].checkinDays[j]] = hotels[i].checkinDays[j] ;
				}
			}
		}
		for(var j=0;j<hotel.checkinDays.length;j++){
			checkedinDays[hotel.checkinDays[j]] = hotel.checkinDays[j] ;
		}
		$("#hotel-list-popup").modal("hide") ;
		$(".table-tab-switch.hotel.edit").find("li").each(function(){
			$(this).removeClass("current") ;
		});
		$(".table-tab-switch.hotel.edit").find("li:eq(-1)").remove() ;
		var cutname = hotel.name ;
		if(hotel.name != null && hotel.name != undefined){
			if(hotel.name.length > 6){
				cutname = hotel.name.substr(0,6) + "..." ;
			}
		}
		$(".table-tab-switch.hotel.edit").append("<li class='current'><span onclick=swapHotelEditTab(this,'"+hotel.id+"')>"+cutname+"</span><span onclick=deleteCurrentHotel('"+hotel.id+"')>x</span></li>");
		$(".table-tab-switch.hotel.edit").append("<li onclick='hotelSearchListPage();'><span>+ 添加酒店</span></li>");
		$(".hotel-form-module").find(".hotel-form-table").hide() ;
		$(".hotel-form-module").append(createHotelEditForm(hotel,othersCheckedinDays,checkedinDays)) ;
	}

	function getFlightEditTable(flight){
		var html = '<table class="flight-form-table"><colgroup><col width="79"/><col width="80"/><col width="79"/><col width="80"/><col width="79"/><col width="80"/>' ;
		html += '<col width="79"/><col width="80"/><col width="85"/><col width="80"/><col width="85"/><col width="90"/><col width="85"/><col width="90"/><col width="85"/></colgroup>' ;
        html += '<tbody><tr>' ;
        html +=	'<th><span class="red-star">*</span>第几天</th>' ;
        html += '<td><input type="text" class="offsetDays" style="width:100px;" data-cv="required" value="'+flight.offsetDays+'"/></td>' ;
        html += '<th><span class="red-star">*</span>航司名称</th>' ;
        html += '<td><input type="text" class="airLine" style="width:100px;" value="'+flight.airLine+'" data-cv="required"/></td>' ;
        html += '<th><span class="red-star">*</span>航班号</th>' ;
        html += '<td><input type="text" class="flightNo" style="width:100px;" value="'+flight.flightNo+'" data-cv="required"/></td>' ;
        html += '<th>舱位</th>' ;
        html += '<td><input type="text" class="cabin" value="'+flight.cabin+'" style="width:80px;" /></td>' ;
        html += '<th><span class="red-star">*</span>机型</th>' ;
        html += '<td><input type="text" class="flightModel" value="'+flight.flightModel+'" style="width:80px;" /></td>' ;
        html += '<th>经停信息</th>' ;
        html += '<td colspan="3"><input type="text" class="stop" value="'+flight.stop+'" style="width:200px;" /></td></tr>' ;
        html += '<tr>' ;
        html += '<th><span class="red-star">*</span>起飞机场</th>' ;
        html += '<td><input type="text" class="fromAirPort" style="width:100px;" value="'+flight.fromAirPort+'" data-cv="required"/></td>' ;
        html += '<th><span class="red-star">*</span>起飞时间</th>' ;
        html += '<td><input type="text" class="departureTime" style="width:100px;" value="'+flight.departureTime+'" data-cv="required"/></td>' ;
        html += '<th><span class="red-star">*</span>到达机场</th>' ;
        html += '<td><input type="text" class="toAirPort" style="width:100px;" value="'+flight.toAirPort+'" data-cv="required"/></td>' ;
        html += '<th><span class="red-star">*</span>到达时间</th>' ;
        html += '<td colspan="3">' ;
        html += '<input type="text" class="arrivalTime" style="width:100px;" value="'+flight.arrivalTime+'" data-cv="required"/>' ;
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

	function editFlight(){
		$.ajax({
    	    type: 'POST',
    	    url: basepath + '/product/cost/editFlight' ,
    	    data: 'id='+$("#id").html() ,
    	    dataType: 'json' ,
    	    success: function(result){
    	    	if(result.flag){
    	    		resetFlightEditView(result.flight) ;
    	    		$("#add-flight-popup").modal("show");
    	    	}
    	    }
    	});
	}

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

	function editHotel(){
		$.ajax({
    	    type: 'POST',
    	    url: basepath + '/product/cost/editHotel' ,
    	    data: 'id='+$("#id").html() ,
    	    dataType: 'json' ,
    	    success: function(result){
    	    	if(result.flag){
    	    		$(".hotel-form-module").html(old_hotel_html) ;
    	    		hotels = result.hotels ;
    	    		for(var i=0 ; i<result.hotels.length;i++){
    	    			resetHotelEditView(result.hotels[i]) ;
    	    		}
    	    		$("#add-hotel-popup").modal("show");
    	    	}
    	    }
    	});
	}

	function deleteHotel(){
		$.ajax({
    	    type: 'POST',
    	    url: basepath + '/product/cost/deleteHotel' ,
    	    data: 'id='+$("#id").html() ,
    	    dataType: 'json' ,
    	    success: function(result){
    	    	if(result){
    	    		window.location.reload() ;
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

    //  生成价格双日历
    function geneDoublePriceCalendar(ele, data) {
        var leftDate = new Date(),
            rightDate = moveMonth(leftDate, 1);  //  moveDate() 方法为上面的价格日历中的方法，可通用

        $(ele).find(".left-calendar").datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            weekStart: 0
        });
        $(ele).find(".left-calendar").datepicker("setDate", leftDate);

        $(ele).find(".right-calendar").datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            weekStart: 0
        });
        $(ele).find(".right-calendar").datepicker("setDate", rightDate);

        resetDpCalendar(ele, leftDate, rightDate, data);

        $(ele).find(".prev").click(function(){

            leftDate = moveMonth(leftDate, -2);
            rightDate = moveMonth(rightDate, -2);

            $(ele).find(".left-calendar").datepicker("setDate", leftDate);
            $(ele).find(".right-calendar").datepicker("setDate", rightDate);

            resetDpCalendar(ele, leftDate, rightDate, data);
        });

        $(ele).find(".next").click(function(){

            leftDate = moveMonth(leftDate, 2);
            rightDate = moveMonth(rightDate, 2);

            $(ele).find(".left-calendar").datepicker("setDate", leftDate);
            $(ele).find(".right-calendar").datepicker("setDate", rightDate);

            resetDpCalendar(ele, leftDate, rightDate, data);
        });
    };

    function moveMonth(date, dir) {

        if (!(date instanceof Date)) {   //  如果date不是日期类型
            return undefined;
        }

        if (!dir) {   // 如果 dir 参数为空，返回date
            return (date instanceof Date) ? date : undefined;
        }

        var dir_num = parseInt(dir),
            dir_absNum = Math.abs(dir_num),
            year = date.getFullYear(),
            month = date.getMonth(),
            day = date.getDate(),
            result_date;

        if (dir_num < 0) {   //  日期往前滚
            result_date = new Date(year, month - dir_absNum);

        } else if (dir_num > 0) {  // 日期往后滚
            result_date = new Date(year, month + dir_absNum);
        }
        return result_date;
    }

    //  方法生成每月的结构，左右日历分别调用
    function geneTdCont($ele, date, data) {

        var y = date.getFullYear(),
            m = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1),
            monthStr = y + "-" + m,
            monthDataArr = [];

        //  如果没有数据，返回undefined
        var monthDataObject = _.findWhere(data, {month: monthStr});

        if (!!monthDataObject) {
            monthDataArr = _.findWhere(data, {month: monthStr}).monthData;
        }

        $ele.find(".datepicker-inline .datepicker-days").find("tbody td").each(function(){
            var dayText = $(this).html();

            if ($(this).hasClass("old") || $(this).hasClass("new")) {
                $(this).html("<div class='tdCont disabled'></div>");
            } else {
                $(this).html(geneDpTdContTemplate(y, m, dayText, monthDataArr));
            }
        });
    }

    function geneDpTdContTemplate(year, month, day, monthDataArr) {

        var template = "";

        var priceObj = _.findWhere(monthDataArr, {day: day*1});

        if (priceObj === undefined) {

            template += "<div class='tdCont'><div class='date' data-dayNum='" + year + "-" + month + "-" + day + "'>" + day + "</div>";
        } else {
        	var price = priceObj.price;
        	if (price instanceof Array) {
        		template += "<div class='tdCont'>" ;
        	}else{
        		var prices = price.split("/") ;
            	var full = "" ;
            	for(var i=0;i<prices.length;i++){
            		full += "第" + (i+1) + "晚:" ;
            		if(isNaN(prices[i])){
            			full += "无  " ;
            		}else{
            			full += parseInt(prices[i]) + " " ;
            		}
            	}
        		template += "<div class='tdCont' title='"+full+"'>" ;
        	}

        	template += "<div class='date' data-dayNum='" + year + "-" + month + "-" + day + "'>" + day + "</div>";

            if (price instanceof Array) {  //  数组类型为机票房价
            	var ap = (price[0] == undefined) ? "" : price[0],
                    cp = (price[1] == undefined) ? "" : price[1];
                template += "<div class='price'><div class='adultPrice'>" + ap + "</div><div class='childPrice'>" + cp + "</div></div>";
            } else {   //  其他为酒店价格
            	var prices = price.split("/") ;
            	var full = "" ;
            	for(var i=0;i<prices.length;i++){
            		if(isNaN(prices[i])){
            			full += "无/" ;
            		}else{
            			full += parseInt(prices[i]) + "/" ;
            		}
            	}
            	full = full.substr(0,full.length < 7 ? (full.length - 1) : 7) + "..." ;
            	template += "<div class='price'><div class='hotelPrice'>" + full + "</div></div>";
            }
        }

        template += "</div>";
        return template;

    }

    function setDpCalendarTitle(ele, leftDate, rightDate) {
        var leftYear = leftDate.getFullYear(),
            leftMonth = leftDate.getMonth(),
            rightYear = rightDate.getFullYear(),
            rightMonth = rightDate.getMonth();

        $(ele).find(".left-calendar-title").html(leftYear + "年" + (leftMonth+1) + "月");
        $(ele).find(".right-calendar-title").html(rightYear + "年" + (rightMonth+1) + "月");
    }

    function viewHotel(id){
    	window.open(basepath + '/product/back/hotel/edit/'+id) ;
    }
    
    function isElementExists(selector){
    	return $(selector).length > 0 ;
    }
    function initSupplier(selector, type){
    	if(isElementExists(selector)){
    		$("body").append($("<input type=\"hidden\" id=\"old_"+type+"_cost\"\/>")) ;
    		$("body").append($("<input type=\"hidden\" id=\"old_"+type+"_sid\"\/>")) ;
    		$("body").append($("<input type=\"hidden\" id=\"old_"+type+"_sname\"\/>")) ;
    		$("body").delegate(".commonButton.blue-45c8dcButton."+type+".set","click",function(){
				$("#old_"+type+"_cost").val($("."+type+".cost").val()) ;
				$("#old_"+type+"_sid").val($("#"+type+"SupplierId").val()) ;
				$("#old_"+type+"_sname").val($(".dropdown."+type+" .activeFonts").html()) ;
				
				$(".oper-block."+type+".set").hide() ;
				$(".oper-block."+type+".save").show() ;
				$("."+type+".cost").removeAttr("readonly") ;
				$(".dropdown."+type).removeClass("disabled") ;
				$(".dropdown."+type+" .dropdownBtn").attr("data-toggle","dropdown") ;
    		}) ;
    		
    		$("body").delegate(".commonButton.red-fe6869Button."+type+".save","click",function(){
    			var bean = {} ;
    			bean.sid = $("#"+type+"SupplierId").val() ;
    			bean.cost = trim($("."+type+".cost")) ;
    			if(!addition_reg1.test(bean.cost)){
    				$("."+type+".cost").val("") ;
    				$("."+type+".cost").attr("placeholder","信息输入有误") ;
    				return;
    			}
    			if($("#"+type+"SupplierId").val() == ''){
    				$(".dropdown."+type+" .activeFonts").html("供应商必填")
    				return;
    			}
    			bean.id =$("#id").html() ;
    			bean.type = type ;
    			$.ajax({
    	    	    type: 'POST',
    	    	    url: basepath + '/product/cost/saveAdditionalCostSupplier' ,
    	    	    data: JSON.stringify(bean) ,
    	    	    dataType: 'json' ,
    	    	    contentType : 'application/json',
    	    	    success: function(result){
    	    	    	if(!result){
    	    	    		alert("保存" + type + "供应商成本失败") ;
    	    	    	}else{
    	    	    		$(".oper-block."+type+".set").show() ;
    	        			$(".oper-block."+type+".save").hide() ;
    	    	    		banWrite(type) ;
    	    	    	}
    	    	    }
    	    	});
    		}) ;
    		
    		$("body").delegate(".commonButton.gray-bbbButton."+type+".cancel","click",function(){
    			$("."+type+".cost").val($("#old_"+type+"_cost").val()) ;
    			$(".dropdown."+type+" .activeFonts").html($("#old_"+type+"_sname").val()) ;
    			$(".dropdown."+type).removeClass("open") ;
				$("#"+type+"SupplierId").val($("#old_"+type+"_sid").val()) ;
				$("."+type+".cost").attr("placeholder","") ;
    			$(".oper-block."+type+".set").show() ;
    			$(".oper-block."+type+".save").hide() ;
    			banWrite(type) ;
    		}) ;
    		
    		banWrite(type) ;
    	}
    	
    }
    
    function banWrite(type){
		$("."+type+".cost").attr("readonly","readonly") ;
		$(".dropdown."+type).addClass("disabled") ;
		$(".dropdown."+type+" .dropdownBtn").attr("data-toggle","") ;
	}
    
    