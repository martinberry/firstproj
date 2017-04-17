var hotels = [] ;
var allTripNights = 0;
var old_hotel_html = '' ;
var old_hotel_list_table = '' ;
var mode = 'view' ;

//额外内容成本
var addition_reg1 = /^\d{1,5}$/ ;

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


$(function(){

	//附加产品编辑
    $(".hotelEditBtn").click(function(){
    	editHotel();
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

	old_hotel_html = $(".hotel-form-module").html() ;

	old_hotel_list_table = $(".commonTab.hotel-list-table").html() ;

	$(".commonButton.red-fe6869Button.hotel.save").click(function(){
		isHotelOk = true ;
		var bean = {} ;
		var hs = [] ;
		var errMsg = '' ;
		var chooseNights = 0;
		$(".hotel-form-table").each(function(){
			var h = {} ;
			h.hotelId = $(this).attr('class').replace('hotel-form-table ','') ;
			var hotelName = $(this).find(".hotelName").val() ;
			h.hotelName = hotelName;
			h.checkinDays = [] ;
			var msg = '' ;
			$(this).find(".day-item").each(function(){
				if($(this).hasClass('active')){
					h.checkinDays[h.checkinDays.length] = $(this).html() ;
				}
			}) ;
			chooseNights += h.checkinDays.length;
			h.roomType = $(this).find(".roomType").val() ;
			if(!h1.test(h.roomType)){
				isHotelOk = false ;
				msg += '房型,' ;
			}
			h.hotelType = $(this).find(".bedType").val() ;

			if(msg.length > 0){
				isHotelOk = false ;
				errMsg += hotelName + ":" + msg + m2 + ";" ;
			}else if(h.checkinDays.length == 0){
				isHotelOk = false ;
				errMsg += hotelName + ":该酒店尚未选择入住时间;" ;
			}
			hs[hs.length] = h ;
		});
		if(chooseNights < allTripNights){
			isHotelOk = false ;
			errMsg += "选择酒店天数不足;" ;
		}
		bean.hotels = hs ;
		bean.orderId = $("#orderId").val() ;
		if(isHotelOk){
			$.ajax({
	    	    type: 'POST',
	    	    url: basepath + '/order/travelConfirm/saveOrderHotel' ,
	    	    data: JSON.stringify(bean) ,
	    	    contentType : 'application/json',
	    	    dataType: 'json' ,
	    	    success: function(result){
    	    		resetHotelView(result) ;
    	    		$("#add-hotel-popup").modal("hide");
    	    		window.location.href = window.location.href;
	    	    }
	    	});
		}else{
			alert(errMsg) ;
		}
	});

	$(".commonButton.gray-bbbButton.hotellist.cancel").click(function(){
		resetHotelList() ;
		$("#hotel-list-popup").modal("hide");
		$("#add-hotel-popup").modal("show");
	});

//	$(".commonButton.gray-bbbButton.hotel.cancel").click(function(){
//		$(".hotel-form-module").html(old_hotel_html) ;
//		window.location.reload() ;
//	});

    $(".modal").modal({
        backdrop: 'static',
        show: false,
        keyboard: false
    }).on("show.bs.modal", function(){
        // 如有需要，弹窗弹出前，可绑定事件
        // 使用方法自行参考bootstrap官网
    });

    $(".modal-header-oper .cancel").click(function(){
       $(this).parents(".modal").modal("hide");
    });

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

});

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
    	    			if(result.hotelNameCn == hotels[i].hotelName){
    	    				alert('已选择该酒店') ;
    	    				return ;
    	    			}
    	    			for(var j=0;j<hotels[i].checkinDays.length;j++){
	    					othersCheckedinDays[hotels[i].checkinDays[j]] = hotels[i].checkinDays[j] ;
    	    			}
    	    		}

    	    		var hotel = {} ;
    	    		hotel.hotelId = result.hotelId ;
    	    		hotel.hotelName = result.hotelNameCn ;
    	    		hotel.checkinDays = [] ;
    	    		hotels[hotels.length] = hotel ;
    	    		$("#hotel-list-popup").modal("hide") ;
    	    		$(".table-tab-switch.hotel.edit").find("li").each(function(){
    	    			$(this).removeClass("current") ;
    	    		});
    	    		$(".table-tab-switch.hotel.edit").find("li:eq(-1)").remove() ;
    	    		var cutname = result.hotelNameCn;
    	    		if(result.hotelNameCn != null && result.hotelNameCn != undefined){
    	    			if(result.hotelNameCn.length > 6){
    	    				cutname = result.hotelNameCn.substr(0,6) + "..." ;
    	    			}
    	    		}
    	    		$(".table-tab-switch.hotel.edit").append("<li class='current'><span onclick=swapHotelEditTab(this,'"+hotel.hotelId+"')>"+cutname+"</span><span onclick=deleteCurrentHotel('"+hotel.hotelId+"')>x</span></li>");
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
			if(id == hotels[i].hotelId){
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
		var html = '<table class="hotel-form-table '+hotel.hotelId+'"><colgroup><col width="65"/><col width="340"/><col width="85"/><col width="158"/><col width="67"/><col width=""/></colgroup>' ;
		html += '<tbody><tr><th>入住时间</th><td colspan="5"><div class="pick-up-night">第<span class="days-list">' ;
		var tripNights = allTripNights;
		for(var i=1;i<=tripNights;i++){
		    if(othersCheckedinDays.hasOwnProperty(i)){
		    	html += '<span class="day-item disable" onclick=pickOne(this,"'+hotel.hotelId+'")>'+i+'</span>' ;
		    }else if(checkedinDays.hasOwnProperty(i)){
		    	html += '<span class="day-item active" onclick=pickOne(this,"'+hotel.hotelId+'")>'+i+'</span>' ;
		    }else{
		    	html += '<span class="day-item" onclick=pickOne(this,"'+hotel.hotelId+'")>'+i+'</span>' ;
		    }
		}
		html += '</span>晚<span class="pick-all" onclick=pickAll(this,"'+hotel.hotelId+'")>全程</span></div></td></tr>' ;
		html += '</td></tr><tr><th><span class="red-star">*</span>房型名称</th>' ;
		if(hotel != null && hotel.hotelName != null){
			html += '<input type="hidden" class="hotelName" value="'+hotel.hotelName+'"></input>' ;
		}else{
			html += '<input type="hidden" class="hotelName"></input>' ;
		}
		if(hotel != null && hotel.roomType != null){
			html += '<td><input class="roomType" type="text" style="width:326px;" value="'+ hotel.roomType +'" /></td>' ;
		}else{
			html += '<td><input class="roomType" type="text" style="width:326px;"/></td>' ;
		}
		html += '<th><span class="red-star">*</span>床型</th><td><div class="dropdown" style="width: 148px;"><a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">' ;
		if(hotel != null && hotel.hotelType != null){
			html += '<span class="activeFonts">'+hotel.hotelType+'</span><span class="caret"></span></a>' ;
		}else{
			html += '<span class="activeFonts">大床</span><span class="caret"></span></a>' ;
		}
		html += '<ul class="dropdown-menu">' ;
		html += '<li onclick=setDropDownMenu(this,\"大床\",\"大床\",\".bedType\")><a href="javascript:void(0);">大床</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"双床\",\"双床\",\".bedType\")><a href="javascript:void(0);">双床</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"不定\",\"不定\",\".bedType\")><a href="javascript:void(0);">不定</a></li>' ;
		html += '<li onclick=setDropDownMenu(this,\"其他\",\"其他\",\".bedType\")><a href="javascript:void(0);">其他</a></li>' ;
		html += '</ul>' ;
		if(hotel != null && hotel.hotelType != null){
			html += '<input class="bedType" type="hidden" value="'+hotel.hotelType+'" />' ;
		}else{
			html += '<input class="bedType" type="hidden" value="大床" />' ;
		}
		html += '</td></tr>' ;
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

	function deleteCurrentHotel(hotelId){
		var checkedDays = {} ;
		$(".hotel-form-module").find(".hotel-form-table").each(function(){
			if($(this).hasClass(hotelId)){
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
			if(hotels[h].hotelId == hotelId){
				hotels.splice(h,1);
			}
		}
		if($(".hotel-form-module").find(".hotel-form-table").size() > 0){
			swapHotelEditTab($($(".table-tab-switch.hotel.edit").find('span')[0]),$($(".hotel-form-module").find(".hotel-form-table")[0]).attr('class').split(" ")[1]) ;
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
			if(id != hotels[i].hotelId){
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

	function resetHotelView(hotels){
		$(".add-btn.add-hotel-btn").hide() ;
		var hotel_html = '<div class="table-top-block clearfix"><ul class="table-tab-switch hotel view">' ;
		for(var i=0;i<hotels.length;i++){
			if(i == 0){
				hotel_html += "<li class='current' onclick=swapHotelViewTab(this,'"+hotels[i].hotelId+"')><span>第"+hotels[i].checkinDaysStr+"晚</span></li>" ;
			}else{
				hotel_html += "<li onclick=swapHotelViewTab(this,'"+hotels[i].hotelId+"')><span>第"+hotels[i].checkinDaysStr+"晚</span></li>" ;
			}
		}
		hotel_html += '</ul><span class="oper-link"><a href="javascript:editHotel() ;">编辑</a><a href="javascript:deleteHotel() ;">删除</a>' ;
		hotel_html += '</span></div>' ;
		for(var i=0;i<hotels.length;i++){
			if(i == 0){
				hotel_html += '<table class="hotel-info-table '+hotels[i].hotelId+'">' ;
			}else{
				hotel_html += '<table class="hotel-info-table '+hotels[i].hotelId+'" style="display:none">' ;
			}
			hotel_html += '<colgroup><col width="79"/><col width="330"/><col width="80"/><col width="100"/><col width="80"/><col width="115"/><col width="80"/><col width="110"/></colgroup><tbody>' ;
			hotel_html += '<tr><th>酒店名称</th><td>'+hotels[i].name+'</td><th class="normal">星级</th><td>'+hotels[i].rate+'</td>' ;
			hotel_html += '<th class="normal">目的地</th><td>'+hotels[i].dest+'</td><th class="normal">房型</th><td>'+hotels[i].roomType+'</td>' ;
			hotel_html += '</tr>' ;
			hotel_html += '</tbody></table>' ;
		}
		$(".hotel-info.view").html(hotel_html) ;
	}

	function resetHotelEditView(hotel){
		var checkedinDays = {} ;
		var othersCheckedinDays = {} ;
		for(var i=0;i<hotels.length;i++){
			if(hotels[i].hotelId != hotel.hotelId){
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
		var cutname = hotel.hotelName ;
		if(hotel.hotelName != null && hotel.hotelName != undefined){
			if(hotel.hotelName.length > 6){
				cutname = hotel.hotelName.substr(0,6) + "..." ;
			}
		}
		$(".table-tab-switch.hotel.edit").append("<li class='current'><span onclick=swapHotelEditTab(this,'"+hotel.hotelId+"')>"+cutname+"</span><span onclick=deleteCurrentHotel('"+hotel.hotelId+"')>x</span></li>");
		$(".table-tab-switch.hotel.edit").append("<li onclick='hotelSearchListPage();'><span>+ 添加酒店</span></li>");
		$(".hotel-form-module").find(".hotel-form-table").hide() ;
		$(".hotel-form-module").append(createHotelEditForm(hotel,othersCheckedinDays,checkedinDays)) ;
	}

	function editHotel(){
		$.ajax({
    	    type: 'POST',
    	    url: basepath + '/order/travelConfirm/selectOrderHotel' ,
    	    data: 'orderId='+$("#orderId").val(),
    	    dataType: 'json' ,
    	    success: function(result){
	    		$(".hotel-form-module").html(old_hotel_html) ;
	    		hotels = result;
	    		allTripNights = 0;
	    		for(var i=0 ; i<result.length;i++){
	    			allTripNights +=  result[i].tripNights;
	    		}
	    		for(var i=0 ; i<result.length;i++){
	    			resetHotelEditView(result[i]) ;
	    		}
	    		$("#add-hotel-popup").modal("show");
    	    }
    	});
	}

	function deleteHotel(){
		$.ajax({
    	    type: 'POST',
    	    url: basepath + '/product/cost/deleteHotel' ,
    	    data: 'id='+$("#productId").val(),
    	    dataType: 'json' ,
    	    success: function(result){
    	    	if(result){
    	    		window.location.reload() ;
    	    	}
    	    }
    	});
	}
