var CALENDARDATA = {};
var lowestAdultPrice;
var highestMarketPrice;
var selected_stock = 0;
var currentThat ;
var child_describe = '';
var MAX_INT = 100000000;
$(function(){
	child_describe = $("#childNumDD").prev().html() ;
	$(".price-tooltip").attr("title", $("#priceTip_template").html());

	$(".price-tooltip").tooltip({
        html: true,
        placement: 'bottom',
        template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
    });

	$(".green-border-tooltip").tooltip({
        html: true,
        placement: 'bottom',
        template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
    });

	$('.fixed-want-order-btn,#wantBookBtn').click(function(){
		popAlert2("#calendar-container") ;
	});

	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/front/detail/getCalendarData/'+productPid ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    success: function(result){
	    	lowestAdultPrice = result.lowestAdultPrice;
    		highestMarketPrice = result.highestMarketPrice;
    		$("#initMarketPrice").val(highestMarketPrice) ;
    		$("#initAdultPrice").val(lowestAdultPrice) ;
	    	if(result.calendar !== undefined)CALENDARDATA = result.calendar;
	    	initCalendar(result);
	    	selectTrip();
	    	//进入页面,默认选择第一个有效天
	    	//setDefaultData();
	  
	    	//默认选择年货固定日期
	      	setNewYearDefaultData();
	    	
	    	rebindData();
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
	$(".modal.fade").modal({
        backdrop:"static",
        keyboard: false,
        show: false
    });

	$('.fixed-order-btn,#bookBtn').click(function(){
		var anStr = $("#adultNumDD").find("ul.dropdown-menu li.active a").html();
		var cnStr = $("#childNumDD").find("ul.dropdown-menu li.active a").html();
		var productNature = $("#nature").val();
		var priceId = $("#priceId").val();
		var selectedDay = $("#selectedDay").html();
		var selectedPkg = $('#packageRadio').find('label.active').attr('value');
		if (selectedPkg == undefined) {
			selectedPkg = "";
		}
		if(typeof(priceId) == undefined){
			priceId = "";
		}
		var isBefore = $("#isBefore").val();
		var url = memberServer +'/product/book/tobookpage';
		if(selectedDay == undefined || isNaN(new Date(selectedDay).getTime()) ){
			popAlert("请在日历中选择出行日期", "#calendar-container");
			return;
		}

		if($("#packageBlock").hasClass("prod-type-item current") && selectedPkg == ""){
			popAlert("请选择需要出行的套餐或自助选择出行人数", "#packageBlock");
			return;
		}

		if(isBefore == 'false'){
			popAlert3("该产品需提前" + $("#inAdvanceDays").val() + "天预订，请重新选择日期哦", "#calendar-container");
			return;
		}
		var day = CALENDARDATA[selectedDay];
		if(day){
			var stock = day.stock;
			var needNum = 0;
			var pkgs = day.salesPackages;
			if(pkgs){
				for(var i=0; i< pkgs.length; i++){
					var pkg = pkgs[i];
					if(pkg.pkgId == selectedPkg){
						if(pkg.adultNum)needNum += pkg.adultNum;
						if(pkg.childrenNum)needNum += pkg.childrenNum;
						break;
					}
				}
			}
			if(needNum > stock){
				popAlert("额，好像库存不够了。请联系微信客服告知。");
				return;
				/**
				 * 从建单页返回时回填数据
				 */
				//function rebindData(){
//					if(de_SelectedDay !== undefined && de_SelectedDay !== ''){
//						var tmpDate = tmpMonth = new Date(de_SelectedDay);
//						tmpMonth.setDate(1);
//						$(".calendar-month-tab li.tab-item.current").removeClass("current");
//						$(".calendar-month-tab li.tab-item[name='"+ tmpMonth.Format("yyyy/MM/dd") +"']").click();
//						var $day = $("div.priceCover.onsale div.dateDay[data-dayvalue='"+ tmpDate.Format('yyyy/MM/dd') +"']").parent();
//						if($day && de_packageId){
//							$day.click();
//							$("#pkg-list").find("li[pkgId='"+ de_packageId +"']").click();
//						}
//					}
				//}
			}
		}
		var $form = $('<form action="'+ url +'" method="post" style="display:none;"></form>');
		$form.append('<input name="productId" value="'+ productId +'"/>');
		$form.append('<input name="bookDate" value="'+ selectedDay.replace(/\//ig,'-') +'"/>');
		$form.append('<input name="adultNum" value="'+ anStr +'"/>');
		$form.append('<input name="childNum" value="'+ cnStr +'"/>');
		$form.append('<input name="costPriceId" value="'+priceId+'"/>');
		$form.append('<input name="productNature" value="'+productNature+'"/>');
		
		$form.append('<input name="packageId" value="'+ selectedPkg +'"/>');
		$form.appendTo('body');
		_paq.push(['trackEvent', 'detailpage', 'ztrcrorder', productId,$form.serialize()]);
		$form.submit();
	});

	$(".pre-mon-btn").click(function(){
		var $this = $(this);
		if (!$this.hasClass("disabled")) {
	        if ($(".tab-item.current").prevAll('.tab-item').length > 0) {
	            var offsetLeft = $(".calendar-month-tab .tab-item.current").position().left,
	                curOff = $(".calendar-month-tab").css("margin-left") != "auto" ? parseInt($(".calendar-month-tab").css("margin-left")) : 0;
	            if (offsetLeft < 100) {
	                var w = $(".calendar-month-tab .tab-item").outerWidth() + $(".calendar-month-tab .placement").outerWidth();
	                $(".calendar-month-tab").css("margin-left", curOff + w + "px");
	            }
	            $(".tab-item.current").prevAll('.tab-item').eq(0).trigger("click");
	        }
		}
    });

    $(".next-mon-btn").click(function(){
    	var $this = $(this);
    	if (!$this.hasClass("disabled")) {
	        if ($(".tab-item.current").nextAll('.tab-item').length > 0) {
	            var offsetLeft = $(".calendar-month-tab .tab-item.current").position().left,
	                curOff = $(".calendar-month-tab").css("margin-left") != "auto" ? parseInt($(".calendar-month-tab").css("margin-left")) : 0;
	            if (offsetLeft > 500) {
	                var w = $(".calendar-month-tab .tab-item").outerWidth() + $(".calendar-month-tab .placement").outerWidth();
	                $(".calendar-month-tab").css("margin-left", curOff - w + "px");
	            }
	            $(".tab-item.current").nextAll('.tab-item').eq(0).trigger("click");
	        }
    	}
    });
});

function rebindData(){
	if(de_SelectedDay !== undefined && de_SelectedDay != ''){
		var tmpDate = new Date(de_SelectedDay);
		var tmpMonth = new Date(de_SelectedDay);
		tmpMonth.setDate(1);
		$(".calendar-month-tab li.tab-item.current").removeClass("current");
		$(".calendar-month-tab li.tab-item[name='"+ tmpMonth.Format("yyyy/MM/dd") +"']").click();
		var $day = $("div.priceCover.onsale div.dateDay[data-dayvalue='"+ tmpDate.Format('yyyy/MM/dd') +"']").parent();
		if($day){
			$day.click();
			if(de_packageId){
				$('#packageRadio').find('label.radio').each(function(){
					if($(this).attr('value') == de_packageId){
						$(this).addClass("active");
						$(".prod-type-item").removeClass("current");
						$('#packageBlock').addClass("current");
					}
				});
			}else{
				$("#adultNumDD").find("ul.dropdown-menu li a").each(function(){
					if($(this).html() == de_AdultNum)$(this).click();
				});
				$("#childNumDD").find("ul.dropdown-menu li a").each(function(){
					if($(this).html() == de_ChildNum)$(this).click();
				});
			}
		}
	}
}

//默认选择年货固定日期
function setNewYearDefaultData(){
	var saleTdobj;
	if(productPid=="Z10043"||productPid=="Z10021"||productPid=="Z10023"||productPid=="Z10002"||productPid=="Z10032"||productPid=="Z10024"){
		$("div.priceCover").not(".notBefore").find("div.dateDay").each(function(){
			
			if(productPid=="Z10043" && $(this).attr("data-dayvalue")=="2016/03/08"){
				 saleTdobj=$(this).parents(".priceCover");
			}else if(productPid=="Z10021" && $(this).attr("data-dayvalue")=="2016/03/22"){
				 saleTdobj=$(this).parents(".priceCover");
			}else if(productPid=="Z10023" && $(this).attr("data-dayvalue")=="2016/03/11"){
				 saleTdobj=$(this).parents(".priceCover");
			}else if(productPid=="Z10032" && $(this).attr("data-dayvalue")=="2016/06/14"){
				 saleTdobj=$(this).parents(".priceCover");
			}else if(productPid=="Z10024" && $(this).attr("data-dayvalue")=="2016/03/12"){
				 saleTdobj=$(this).parents(".priceCover");
			}else if(productPid=="Z10002" && $(this).attr("data-dayvalue")=="2016/03/22"){
				 saleTdobj=$(this).parents(".priceCover");
			}else{
				
			}
			
		})
		var activeDate= saleTdobj.find("div.dateDay").attr("data-dayvalue");
		var activeArr= activeDate.split("/");	
		var activeMonth= activeArr[0]+"/"+activeArr[1]+"/01";	
		$(".calendar-month-tab .tab-item[name='"+ activeMonth +"']").click();
		$(saleTdobj).click();
		$('.wrap-cover-bg').click();
		$(saleTdobj).find("div.dateDay").before('<i class="nianIcon"></i>');
		if(productPid=="Z10043"){
			$(".calTable.ct-2016-02").find("tr").eq(2).find("td").eq(2).find(".priceCover").find("div.dateDay").before('<i class="nianIcon"></i>');	
		}
		var minPerPrice = MAX_INT;
		var $minLi;
		$("#pkg-list").find("li[perPrice]").each(function(){
			var perPrice = parseInt($(this).attr("perPrice"));
			if((perPrice || perPrice === 0) && perPrice < minPerPrice ){
				minPerPrice = perPrice;
				$minLi = $(this);
			}
		});
		if(minPerPrice < MAX_INT){
			$minLi.click();
		}
			
			
	}
}







function setDefaultData(){
	var saleTd = $("div.priceCover.onsale").not(".notBefore").first();
	saleTd = saleTd[0];
	var activeDate = $(saleTd).find("div.dateDay").attr("data-dayvalue");
	var activeArr = activeDate.split("/");
	var activeMonth = activeArr[0]+ "/" +activeArr[1]+ "/01";
	$(".calendar-month-tab .tab-item[name='"+ activeMonth +"']").click();
	$(saleTd).click();
	$('.wrap-cover-bg').click();
	var minPerPrice = MAX_INT;
	var $minLi;
	$("#pkg-list").find("li[perPrice]").each(function(){
		var perPrice = parseInt($(this).attr("perPrice"));
		if((perPrice || perPrice === 0) && perPrice < minPerPrice ){
			minPerPrice = perPrice;
			$minLi = $(this);
		}
	});
	if(minPerPrice < MAX_INT){
		$minLi.click();
	}
}

function initCalendar(data){
	var allDays = getAllDays(data.calendar);
	setCalendar(allDays, ".calendarTables");
	//  生成月份
	$(".custom-price-calendar-container ul.common-tab").append(geneTabSwitchMonth(allDays));
	var w = $(".calendar-month-tab .tab-item").outerWidth() + $(".calendar-month-tab .placement").outerWidth();
	if (allDays.length > 6) {
		$("ul.calendar-month-tab").css("width", w * allDays.length + $(".calendar-month-tab .placement").outerWidth() + "px");
		$(".cal-mon-tab-container").addClass("limit-width");
	}
	//  设置月份样式
	$(".common-tab").tabBar();
	var i = 1 ;
	var now = new Date() ; style="display: none;"
	var nowMills = new Date(now.getFullYear() + "/" + parseInt(now.getMonth() + 1) + "/01").getTime() ;
	var hasNow = false ;
	var hasEarly = false ;
	$(".calendar-month-tab .tab-item").each(function(index){
		var calDate = $(this).attr('name') ;
		var currentMills = new Date(calDate).getTime() ;
		if(nowMills === currentMills){
			i = index ;
			hasNow = true ;
		}
		if(nowMills > currentMills){
			hasEarly = true ;
		}
	}) ;
	if(!hasNow){
		if(hasEarly){
			i = 1 ;
		}else{
			i = 0 ;
		}
	}
	if(allDays.length === 1){
		i = 0 ;
	}
	$(".calendar-month-tab .tab-item").eq(i).addClass("current");
	$(".common-tab").tabBar();
	$(".calTable").hide();
	$(".calTable").eq(i).show();
	checkMonthNav();
	$(".calendar-month-tab .tab-item").each(function(index){
		$(this).click(function(){
			$(".calTable").hide();
			$(".calTable").eq(index).show();
			if (!$(".calTable").eq(index).find(".tripCover").length > 0) {
				$(".wrap-cover-bg").hide();
			} else {
				$(".wrap-cover-bg").show();
			}
			checkMonthNav();
		});
	});
	$(".priceCover.onsale").not(".noPrice").click(function(){
		var needRefresh = true;
		if($(this).hasClass("current")){
			needRefresh = false;
		}
		var $thisTd = $(this).closest("td"),
		$thisTable = $(this).closest('table'),
		tdIndex = $thisTable.find("td").index($thisTd),
		restValidTdNum = $thisTable.find("td:gt("+tdIndex+")").not(".empty").length;

		$(".wrap-cover-bg").show();
		currentThat = this ;
		$(".week-day-title.top").show();
		$('.fixed-want-order-btn,#wantBookBtn').hide();
		$('.fixed-order-btn,#bookBtn').show();
		geneTripCover($(this), data.fhTips);
		selectTrip(this, needRefresh);
	});
	$(".priceCover.canntorder").click(function(){
//		var needRefresh = true;
//		if($(this).hasClass("current")){
//			needRefresh = false;
//		}
		var $thisTd = $(this).closest("td"),
		$thisTable = $(this).closest('table'),
		tdIndex = $thisTable.find("td").index($thisTd),
		restValidTdNum = $thisTable.find("td:gt("+tdIndex+")").not(".empty").length;
		currentThat = this ;
		$(".wrap-cover-bg").show();
		$(".week-day-title.top").show();
		$('.fixed-want-order-btn,#wantBookBtn').show();
		$('.fixed-order-btn,#bookBtn').hide();
		geneTripCover($(this), data.fhTips);
		selectTrip(this, false);
	});

	$("body").delegate(".wrap-cover-bg,.fixed-want-order-btn,#wantBookBtn", "click", function(event){
		removeTripCover() ;
		event.stopPropagation();
	});

	//单选框
    $("body").delegate(".radioContent .radio:not('.disabled')", "click", function(){
        //$(this).addClass("active");
        //$(this).siblings().removeClass("active");
        var perPrice = $(this).attr('data-perPrice');
        $('#currentPrice').html(perPrice);
    });

	// 选择人数 or 套餐
    $("body").delegate('.prod-type-item', 'click', function(event) {
    	var selectedId = $(this.id).selector;
    	if (selectedId == "normalBlock") {
    		$(".prod-by-menu .radioContent label").removeClass("active");
    		if (!$("#selectedDay").hasClass("grayFont")) {
    			var selectedDay = $("#selectedDay").html();
    			$('#currentPrice').html(CALENDARDATA[selectedDay].adultPrice);
    		}
    	}
        $(".prod-type-item").removeClass("current");
        $(this).addClass("current");
    });

    // 更多套餐展示
    $("body").delegate(".prod-by-menu .more", "click", function(){

        if (!$(this).hasClass("fold")) {    //  展开操作

            $(".prod-by-menu .radioContent label").show();
            $(this).html("- 收起");
            $(this).addClass("fold");
        } else {    //  收起操作

            $(".prod-by-menu .radioContent label").each(function(index){
                if (index < 3) {    //  只显示前三个
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
            $(this).html("+ 更多");
            $(this).removeClass("fold");
        }
    });
}

function removeTripCover(){
	$(".wrap-cover-bg").hide();
	$(".week-day-title.top").hide();
	$(".calTable .tripCover").remove();
	$(".calTable .popover-trip-container").remove();
	$(".calTable tr.append").remove();
}

/*选择某一天出发后刷新右侧出发日期，可选人数等*/
function selectTrip(obj, needReselelct){
	var leftNum = 0;
	var selectedDay = "";
	if(obj !== undefined){
		var leftNumStr = $($(obj).find("div.right-block div.leftNum span")[1]).html();
		if(!isNaN(leftNumStr)){
			leftNum = parseInt(leftNumStr);
		}
		selectedDay = $(obj).find("div.dateDay").attr("data-dayvalue");
		var adultPrice = CALENDARDATA[selectedDay].adultPrice;
		var marketPrice = CALENDARDATA[selectedDay].marketPrice;
		$('#currentPrice').html(adultPrice);
		$('#marketPrice').html(marketPrice);
		var day = CALENDARDATA[selectedDay];
		$("#isBefore").val(day.before);
		$("#inAdvanceDays").val(day.inAdvanceDays);
		$('#currentPrice').next().html("/人");
		$('#marketPrice').next().html("/人");
		if(marketPrice <= adultPrice){
			$('#marketPriceBlock').hide();
		}else{
			$('#marketPriceBlock').show();
		}
		$("#selectedDay").html(selectedDay);
		$("#selectedDay").removeClass('grayFont');
		selected_stock = leftNum;
		initNumDD(selected_stock,CALENDARDATA[selectedDay].hasChildPrice, needReselelct);

		setPkgSelect(day, leftNum);
	}else{
		//默认选则最近有效天
		//最低成人价格 最高市场价格
		$('#currentPrice').html(lowestAdultPrice);
		$('#marketPrice').html(highestMarketPrice);
		if(highestMarketPrice <= lowestAdultPrice){
			$('#marketPriceBlock').hide();
		}else{
			$('#marketPriceBlock').show();
		}
	}
}

/*设置人数选择下拉框*/
function initNumDD(stock, haChildPrice, onsale){
	var anStr = $("#adultNumDD").find("ul.dropdown-menu li.active a").html();
	var cnStr = $("#childNumDD").find("ul.dropdown-menu li.active a").html();
	anStr === undefined? 0: anStr;
	cnStr === undefined? 0: cnStr;
	var length = stock<=9? stock: 9;
	var stock_enough = checkTotalNum(true);
	if(!onsale){
		length = 9 ;
		stock_enough = true ;
	}
	$("#adultNumDD").find("ul.dropdown-menu").html(function(){
		var lis = '';
		for(var i = 1; i <= length; i++){
			lis += '<li><a href="javascript:void(0);">' + i + '</a></li>';
		}
		return lis;
	});
	if(onsale){
		bindDDClick("#adultNumDD");
	}else{
		bindDDClick2("#adultNumDD");
	}
	if(stock_enough){
		selectDD("#adultNumDD", anStr);
	}else{
		var $adultLas = $("#adultNumDD").find("ul.dropdown-menu li a");
		if($adultLas.length >= 2){
			$adultLas['1'].click();
		}else{
			$adultLas.first().click();
		}
	}
	if(haChildPrice){
		$("#childNumDD").css("display", "");
		$("#childNumDD").prev().html(child_describe) ;
		$("#childNumDD").find("ul.dropdown-menu").html(function(){
			var lis = '';
			for(var i = 0; i <= length; i++){
				lis += '<li><a href="javascript:void(0);">' + i + '</a></li>';
			}
			return lis;
		});
		if(onsale){
			bindDDClick("#childNumDD");
		}else{
			bindDDClick2("#childNumDD");
		}
		if(stock_enough){
			selectDD("#childNumDD", cnStr);
		}else{
			$("#childNumDD").find("ul.dropdown-menu li a").first().click();
		}
	}else{
		$("#childNumDD").css("display", "none");
		$("#childNumDD").prev().html("儿童与成人同价");
	}
}
function bindDDClick(trigger){
	$(trigger).find("ul.dropdown-menu li a").click(function(){
		var $thisParent = $(this).parents("li");
		var thisHtml = $(this).html();
		$(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
        $thisParent.addClass("active");
        $thisParent.siblings().removeClass("active");
		if(!checkTotalNum()){
			$thisParent = $(trigger).find("ul.dropdown-menu li").first();
			thisHtml = $thisParent.find("a").html();
			$(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
	        $thisParent.addClass("active");
	        $thisParent.siblings().removeClass("active");
		}
	});
}

function bindDDClick2(trigger){
	$(trigger).find("ul.dropdown-menu li a").click(function(){
		var $thisParent = $(this).parents("li");
		var thisHtml = $(this).html();
		$(this).parents(".dropdown-menu").siblings(".dropdownBtn").find(".activeFonts").html(thisHtml);
        $thisParent.addClass("active");
        $thisParent.siblings().removeClass("active");
	});
}
function selectDD(trigger, val){
	$(trigger).find("ul.dropdown-menu li a").each(function(){
		if($(this).html() == val)$(this).click();
	});
}
function checkTotalNum(notAlert){
	var adultNum = 0;
	var childNum = 0;
	var anStr = $("#adultNumDD").find("ul.dropdown-menu li.active a").html();
	var cnStr = $("#childNumDD").find("ul.dropdown-menu li.active a").html();
	if(!isNaN(anStr)){
		adultNum = parseInt(anStr);
	}
	if(!isNaN(cnStr)){
		childNum = parseInt(cnStr);
	}
	if(adultNum+childNum > selected_stock){
		if(!notAlert)popAlert("可用库存为："+selected_stock);
		return false;
	}
	return true;
}
//选择天后,初始化对应的套餐下拉列表,并默认设为请选择
function setPkgSelect(day, leftNum){

	var $ul = $("#packageBlock");
	var html = '<div class="prod-by-menu" style="margin-top: 5px;">';
	html += '<span class="present-icon" style="margin-top:-5px;"></span>'
		+ '<span class="title" style="margin-left:8px;">选择套餐:</span>';
	var pkgPerPrice = MAX_INT, lowestPkgId;
	if(day.salesPackages){
		var pkgs = day.salesPackages;
		html += '<div class="radioContent" id="packageRadio">';
		for(var i = 0; i< pkgs.length; i++){
			var pkg = pkgs[i];
			var name = replaceHtml(pkg.name);

			if(pkg.adultNum + pkg.childrenNum > leftNum) {
				html += '<label class="radio disabled"';
			}else{
				html += '<label class="radio"';
			}
			if (i >= 3) {
				html += ' style="display: none;"';
			}
			html += ' value="' + pkg.pkgId + '" data-placement="bottom" data-toggle="tooltip" title="' + pkg.desc + '" data-perPrice="' + pkg.perPrice + '">'
			+ '<span class="commonIcon radioIcon"></span>'
			+ '<span class="menu-title">' + name + '</span>'
			+ '<span class="price">￥' + pkg.price + '</span>'
			+ '</label>';
		}
		html += '</div>';
		if (pkgs.length > 3) {
			html += '<span class="more">+ 更多</span>';
		}
	}
	html += '</div>';
	$ul.html(html);
	if(day.salesPackages){
		$ul.show();
	}else{
		$ul.hide();
	}
	showPackageDesc();
}

function showPackageDesc() {
	$(".radioContent label").tooltip(function(){
	    placement: 'bottom'
	});
}

//按钮置灰不可点
function checkMonthNav() {
    if ($(".tab-item.current").nextAll('.tab-item').length == 0) {
        $(".next-mon-btn").addClass("disabled");
    } else {
        $(".next-mon-btn").removeClass("disabled");
    }
    if ($(".tab-item.current").prevAll('.tab-item').length == 0) {
        $(".pre-mon-btn").addClass("disabled");
    } else {
        $(".pre-mon-btn").removeClass("disabled");
    }
}

function cpg(flag){
	var phone = $("#interestedPhone").val() ;
	var isMobile = /^\d{11}$/ ;
	var clear = true ;
	if(flag && phone !== '' && !isMobile.test(phone)){
		clear = false ;
		$("#interestedPhone").val("") ;
		$("#interestedPhone").attr("placeholder","请输入正确的手机号") ;
	}
	if(clear){
		$('#ac-payHintWindow2').modal('hide');
		$('.fixed-want-order-btn,#wantBookBtn').hide();
		$('.fixed-order-btn,#bookBtn').show();
		var sday = $("#selectedDay").html();
		//setDefaultData();
		$("#selectedDay").html("请选择出行日期").addClass('grayFont');
		var initAdultPrice = $("#initAdultPrice").val();
		var initMarketPrice = $("#initMarketPrice").val();
		$('#currentPrice').html(initAdultPrice + "<em>起</em>");
		$('#marketPrice').html(initMarketPrice + "<em>起</em>");
		if(initMarketPrice <= initAdultPrice){
			$('#marketPriceBlock').hide();
		}else{
			$('#marketPriceBlock').show();
		}
		bindDDClick("#adultNumDD");
		$(".prod-type-item").removeClass("current");
		var $ul = $("#packageBlock");
		$ul.hide();
		$("#isBefore").val('');
		$("#inAdvanceDays").val('');
		$(currentThat).removeClass("current") ;
		$("#interestedPhone").attr("placeholder","请输入手机号") ;
		$("#interestedPhone").val("") ;
		if(flag){
			_paq.push(['trackEvent', 'detailpage', 'ztrwantbook', phone, sday.replace(/\//g, "-" )]);
		}
	}
}

function cpg2(){
	$('#ac-payHintWindow3').modal('hide');
	$("#selectedDay").html("请选择出行日期").addClass('grayFont');
	$('#currentPrice').html($("#initAdultPrice").val() + "<em>起</em>");
	$("#isBefore").val('');
	$("#inAdvanceDays").val('');
	removeTripCover() ;
	setDefaultData();
	$(currentThat).removeClass("current") ;
}

function getAllDays(calendar){
	var allDays = [];
	var month = "";
	var monthObj = {};
	var avariableCount = 0;
	for(var key in calendar){
		var keyMonth = new Date(key);
		keyMonth.setDate(1);
		var keyDate = new Date(key);
		var day = calendar[key];
		var tmpDay = {};
		tmpDay.dayDate = new Date(key).getDate();
		if(day.saleStatus != null && day.saleStatus != 'CLOSED'){
			tmpDay.leftNum = day.stock === null? 0: day.stock;;
			tmpDay.usedNum = day.usedStock === null? 0: day.usedStock;
			tmpDay.price = day.adultPrice === null? "": day.adultPrice;
			tmpDay.childPrice = day.childPrice === null? "": day.childPrice;
			tmpDay.saleStatus = day.saleStatus;
			tmpDay.isBefore = day.before ;
			tmpDay.inAdvanceDays = day.inAdvanceDays ;
			tmpDay.inAdvanceHours = day.inAdvanceHours ;
//			if(day.salesPackages){
//				var lowestPerPrice = MAX_INT;
//				var pkgs = day.salesPackages;
//				for(var i = 0; i < pkgs.length; i++){
//					var pkg = pkgs[i];
//					if(pkg.perPrice != null && pkg.perPrice < lowestPerPrice){
//						lowestPerPrice = pkg.perPrice;
//					}
//				}
//				if(lowestPerPrice < MAX_INT){
//					tmpDay.price = lowestPerPrice;
//				}
//			}
		}else{
			continue;
		}

		if(month == keyMonth.Format('yyyy/MM/dd')){
			monthObj.info.push(tmpDay);
		}else{
			month = keyMonth.Format('yyyy/MM/dd');
			monthObj = {};
			monthObj.month = month;
			var info = [];
			info.push(tmpDay);
			monthObj.info = info;
			allDays.push(monthObj);
			avariableCount++;
		}
	}
	if(avariableCount == 0){
		var now = new Date();
		now.setDate(1);
		month = now.Format("yyyy/MM/dd");
		monthObj = {};
		monthObj.month = month;
		monthObj.info = [];
		allDays.push(monthObj);
		return allDays;
	}
	return allDays;
}

function popAlert(msg, anchor){
	if(msg === undefined)msg = "验证失败！";
	if(anchor){
		$('html,body').animate({scrollTop:$(anchor).offset().top}, 600);
	}
	$("#ac-payHintWindow").find("div.modal-body p").html(msg)
	$("#ac-payHintWindow").modal("show");
}

function popAlert2(anchor){
	if(anchor){
		$('html,body').animate({scrollTop:$(anchor).offset().top}, 600);
	}
	$("#ac-payHintWindow2").modal("show");
}

function popAlert3(msg, anchor){
	if(anchor){
		$('html,body').animate({scrollTop:$(anchor).offset().top}, 600);
	}
	$("#ac-payHintWindow3").find("div.modal-body p").html(msg) ;
	$("#ac-payHintWindow3").modal("show");
}

function setCalendar(data, ele) {
    for (var i = 0; i < data.length; i++) {
        var monthInfo = data[i];  // 获得当前月份的数据信息

        var calStr = "<table class='calTable ct-" + data[i].month.split('/')[0] + "-" +
                        data[i].month.split('/')[1] + "'><tr>";  // 拼接字符串生成表格日期内容

        var curentMonthDate = new Date(monthInfo.month);
        var nextMonthDate = new Date(curentMonthDate.getFullYear(), curentMonthDate.getMonth() + 1,
            curentMonthDate.getDate());
        var monthDays = (nextMonthDate - curentMonthDate)/(1000*60*60*24);  // 获得当前月天数

        var firstWeekDay = curentMonthDate.getDay();  // 获得当前月1号是周几, 范围0-6
        var lastWeekDay = nextMonthDate.getDay() - 1 < 0 ? 6 : nextMonthDate.getDay() - 1;
        // 计算当月最后一天是周几，填补日历最后的空格

        var currentDay = 1;  // 从1号开始排列当月的日期

        for (var j = 0; j < firstWeekDay; j++) {  // 当月1号之前td为空
            calStr += "<td class='empty'><div class='tdCont'></div></td>";
        }

        for (; currentDay <= monthDays; currentDay++) {

            var w1 = firstWeekDay == 0 ? 7 : firstWeekDay;

            if (currentDay % 7 == (7 - w1) && currentDay != monthDays) {
                calStr += "<td>" + geneTdContent(currentDay, monthInfo) + "</td></tr><tr>";
            } else if (currentDay == monthDays) {
                calStr += "<td>" + geneTdContent(currentDay, monthInfo) + "</td>";
                for (var t = 0; t < 6 - lastWeekDay; t++) {
                    calStr += "<td class='empty'><div class='tdCont'></div></td>";
                }
                calStr += "</tr></table>";
            } else {
                calStr += "<td>" + geneTdContent(currentDay, monthInfo) + "</td>";
            }
        }
        $(ele).append(calStr);
    }
}

function getDayInfo(currentDay, monthInfo) {
    for (var i = 0; i < monthInfo.info.length; i++) {
        if (monthInfo.info[i].dayDate == currentDay) {
            return monthInfo.info[i];
        }
    }
    return null;
}

function geneTdContent(currentDay, monthInfo) {
    var dayInfo = getDayInfo(currentDay, monthInfo);
    var tdCont = "";

    var d = currentDay < 10 ? '0' + currentDay : currentDay;
    var dateStr = monthInfo.month.slice(0, 7) + "/" + d;
    var isAfterToday = false;
    if (!!dayInfo && dayInfo.saleStatus != 'CLOSED') {
    	if (dayInfo.leftNum == 0) {
            tdCont += "<div class='tdCont'><div class='priceCover soldout clearfix'><div class='dateDay' data-dayvalue='" + dateStr + "'>" + dayInfo.dayDate + "</div>";
            tdCont += "<div class='right-block'><div class='leftNum'>售罄<span></span></div>";
            if(dayInfo.price !== undefined && dayInfo.price != ""){
            	tdCont += "<div class='price'><em>成人</em>￥<span>" + dayInfo.price + "</span></div>";
            }
            if(dayInfo.childPrice !== undefined && dayInfo.childPrice != ""){
            	tdCont += "<div class='childPrice'><em>儿童</em>￥<span>" + dayInfo.childPrice + "</span></div>";
            }
            tdCont += "</div></div></div>";
        }else{
        	if(dayInfo.saleStatus != 'NOT_SCHEDULED') {
        		tdCont += "<div class='tdCont'><div class='priceCover ";
                tdCont += "onsale";
                if(!(dayInfo.isBefore)){//标记超过提前预订天的
                	tdCont += " notBefore";
                }
                tdCont += " clearfix'><div class='dateDay' data-dayvalue='" + dateStr + "'>" + dayInfo.dayDate + "</div>";
                if(parseInt(dayInfo.leftNum) > 9){
                	tdCont += "<div class='right-block'><div class='leftNum'><span>充足</span><span style='display:none;'>" + dayInfo.leftNum + "</span></div>";
                }else{
                	tdCont += "<div class='right-block'><div class='leftNum'>余<span style='display:none;'></span><span>" + dayInfo.leftNum + "</span></div>";
                }
                if(dayInfo.price !== undefined && dayInfo.price !== ""){
                	tdCont += "<div class='price'><em>成人</em>￥<span>" + dayInfo.price + "</span></div>";
                }
                if(dayInfo.childPrice !== undefined && dayInfo.childPrice !== ""){
                	tdCont += "<div class='childPrice'><em>儿童</em>￥<span>" + dayInfo.childPrice + "</span></div>";
                }
                tdCont += "</div></div></div>";
            }else{
            	tdCont += "<div class='tdCont'><div class='priceCover canntorder";
                tdCont += " clearfix'><div class='dateDay' data-dayvalue='" + dateStr + "'>" + dayInfo.dayDate + "</div>";
                tdCont += "<div class='right-block'><div class='leftNum'>未开放</span></div>";
                if(dayInfo.price !== undefined && dayInfo.price !== ""){
                	tdCont += "<div class='price'><em>成人</em>￥<span>" + dayInfo.price + "</span></div>";
                }
                if(dayInfo.childPrice !== undefined && dayInfo.childPrice !== ""){
                	tdCont += "<div class='childPrice'><em>儿童</em>￥<span>" + dayInfo.childPrice + "</span></div>";
                }
                tdCont += "</div></div></div>";
            }
        }
    } else {
        tdCont += "<div class='tdCont'><div class='priceCover noPrice'><div class='dateDay' data-dayvalue='" + dateStr + "'>" + currentDay + "</div></div></div>";
    }
    return tdCont;
}

function geneTabSwitchMonth(data) {
    var liStr = "";
    for (var i = 0; i < data.length; i++) {
        var temp = new Date(data[i].month);

        liStr += "<li class='placement'></li>";
        liStr += "<li name='" + data[i].month + "' class='tab-item'>" + temp.getFullYear() + "年" +
                    (temp.getMonth() + 1 < 10 ? '0' + (temp.getMonth() + 1) : (temp.getMonth() + 1)) + "月</li>";
    }
    if (data.length > 6) {
    	$(".mon-nav-btns").show();
    	liStr += "<li class='placement'></li>";
    } else if (data.length == 6) {
    	liStr += "<li class='placement'></li>";
    } else{
    	liStr += "<li class='placement'></li><li class='rest'></li>";
    }
    return liStr;
}

function geneTripCover($ele, dataList) {
	//  找到需要重新覆盖的td元素
	var isCoveredEleTd = [];
	isCoveredEleTd.push($ele.closest("td"));
	var curTdNextEleArr = $ele.closest("td").nextAll("td");
	// .not(".empty");
	//  往下一行追加(暂时只考虑不跨月的情况)
	if (curTdNextEleArr.length < (dataList.length - 1)) {
		isCoveredEleTd = _.union(isCoveredEleTd, _.map(curTdNextEleArr, function(value){ return $(value); }));
		var s_tr = parseInt((dataList.length - curTdNextEleArr.length - 1) / 7),
			s_td = (dataList.length - curTdNextEleArr.length - 1) % 7;
		var $tr = $ele.closest("tr");
		for (var i = s_tr; i >= 0; i--) {
			if ($tr.next("tr").length == 0) {  //  跨月情况，表格需要追加行数
				$ele.closest('tbody').append("<tr class='append'><td class='empty'><div class='tdCont'></div></td><td class='empty'><div class='tdCont'></div></td><td class='empty'><div class='tdCont'></div></td><td class='empty'><div class='tdCont'></div></td><td class='empty'><div class='tdCont'></div></td><td class='empty'><div class='tdCont'></div></td><td class='empty'><div class='tdCont'></div></td></tr>");
			}
			$tr = $tr.next("tr");
			var tdList;
			if (i == 0) {
				tdList = $tr.find("td:lt("+s_td+")");
				// .not(".empty");
			} else {
				tdList = $tr.find("td");
				// .not(".empty");
			}
			isCoveredEleTd = _.union(isCoveredEleTd, _.map(tdList, function(value){ return $(value); }));
		}
	} else {
		for (var i = 0; i < dataList.length - 1; i++) {
			isCoveredEleTd.push($(curTdNextEleArr[i]));
		}
	}
	// ======================  isCoveredEleTd  填充完毕
	var eleArr = []; //  生成覆盖元素节点  存放在该数组中（只有右侧部分，左侧复用原来priceCover的结构）
	var popoverEleArr = [];   //  存放弹窗层结构
	for (var i = 0; i < dataList.length; i++) {
		var coverEleStr = "<div class='right-block'><div class='trip-day' data-tripday='"+(i+1)+"'>";
		var popoverEleStr = "<div class='popover-trip-container'>";//start of popover-trip-container
		var topBlockStr = "";
		var bottomBlockStr = "";
		var flightTableStrN = "<table class='flight-info'><tbody>";//start of flightTableNight
		var withNightF = false;
		var withDayF = false;
		topBlockStr += "<div class='top-block clearfix'><div class='left-block'><span class='day-icon'></span></div>";//start of top-block
		topBlockStr += "<div class='right-block'>";//start of right-block
		coverEleStr += parseNumToString(i+1) + "</div><div class='icon'>";
		var dayData = dataList[i],
			fi = dayData.flightInfos,
			hi = dayData.hotelInfo;
		if (!!fi && fi.length >0){//有航班信息,显示飞机icon，popover上显示航班信息
			coverEleStr += "<span class='flight-icon'></span>";
			var flightTableStr = "<table class='flight-info'><tbody>";//start of flightTable
			for(index in fi){
				var fTr = "<tr><td class='city'>"+fi[index].fromCity+"→"
							+fi[index].toCity+"</td>"
							+"<td>"+fi[index].departureTime+"-"+fi[index].arrivalTime+"</td>"
							+"<td>"+fi[index].flightNo+"</td></tr>";
				if( (fi[index].departureTime > "20:00" && fi[index].departureTime < "24:00") || (fi[index].departureTime >= "00:00" && fi[index].departureTime < "06:00") ){
					//20:00~06:00
					withNightF = true;
					flightTableStrN += fTr;
				}else{
					//其他时间段
					withDayF = true;
					flightTableStr += fTr;
				}
			}
			flightTableStr += "</tbody></table>";//end of flightTable
			if(withDayF){
				topBlockStr += flightTableStr;
			}else{
				topBlockStr += "<div class='freetime'>自由活动</div>";
			}
		} else {  //  popover 上显示自由活动
			topBlockStr += "<div class='freetime'>自由活动</div>";
		}

		topBlockStr += "</div>";//end of right-block
		topBlockStr += "</div>";//end of top-block
		popoverEleStr += topBlockStr;

		if (!!hi || withNightF) {  //  有酒店信息,显示酒店icon，popover上显示酒店信息
			bottomBlockStr += "<div class='bottom-block clearfix'>";//start of bottom-block  bottomBlockStr
			bottomBlockStr += "<div class='left-block'><span class='night-icon'></span></div>";
			bottomBlockStr += "<div class='right-block clearfix'>";//start of right-block
			//add hotel
			if(!!hi){
				coverEleStr += "<span class='hotel-icon'></span>";
				bottomBlockStr += "<div class='hotel-name'><div class='ch-name'>"+hi.hotelNameCn+"</div><div class='en-name'>"+hi.hotelNameEn+"</div></div>"
				bottomBlockStr += "<div class='star-rating'><div class='commonStarLevelIcon starLevelIcon starLevel-"+ hi.rate +" noLeftStar'><span class='commonStarLevelIcon'></span></div></div>";
			}
			//add night flight
			if(withNightF){
				flightTableStrN += "</tbody></table>";//end of flightTableNight
				bottomBlockStr += flightTableStrN;
			}
			bottomBlockStr += "</div>";//end of right-block
			bottomBlockStr += "</div>";//end of bottom-block
		}
		popoverEleStr += bottomBlockStr;
		coverEleStr += "</div></div>";
		popoverEleStr += "</div>";//end of popover-trip-container

		eleArr.push($(coverEleStr));
		popoverEleArr.push($(popoverEleStr));
	}
	//  ====================  覆盖元素右侧部分  填充完毕
	//  依次追加节点，填充覆盖
	var overDayCount = 0;  //  记录跨月的天数
	for (var i = 0; i < isCoveredEleTd.length; i++) {
		var day = isCoveredEleTd[i].find(".dateDay").eq(0);
		var day_value = isCoveredEleTd[i].find(".dateDay").eq(0).data("dayvalue");
		if (!day.html() && overDayCount == 0) {
			overDayCount = isCoveredEleTd.length - i + 1;
		}
		isCoveredEleTd[i].find(".tdCont").append("<div class='tripCover clearfix'><div class='dateDay' data-dayvalue='" + day_value + "'>" + (day.html() || overDayCount + i - isCoveredEleTd.length) + "</div><div class='right-block'>" + eleArr[i].html() + "</div></div>");
		isCoveredEleTd[i].find(".tdCont").append("<div class='popover-trip-container'>" + popoverEleArr[i].html() + "</div>");
	}
	$(".calTable .tripCover").click(function(){
		if ($(this).hasClass("current")) {
            $(this).removeClass("current");//已经展开，再次点击就收起浮层
        } else {
        	$(this).closest(".calTable").find(".tripCover").removeClass("current");
    		$(this).addClass("current");
    		$(this).closest(".calendarTables").find(".calTable .priceCover").removeClass("current");//remove所有月份的
            $(this).siblings(".priceCover").addClass("current");
        }
		var $popover = $(this).closest("td").find(".popover-trip-container"),
			trIndex = $(this).closest("tr").index(),
			tdIndex = $(this).closest("td").index();
        if ($popover.is(":visible")) {
            $popover.hide();//已经展开，再次点击就收起浮层
            return;
        } else {
            $(this).closest(".calTable").find(".popover-trip-container").hide();
        }
		var tdHeight = $(this).closest("td").outerHeight(),
			popoverHeight = $(this).closest("td").find(".popover-trip-container").outerHeight();
		if (trIndex <= 0) { //  第一行向下显示
			if (tdIndex <= 3) {  //  向右显示
				$popover.css({
					left: 0,
					top: tdHeight + "px"
				}).show();
			} else {  //  向左显示
				$popover.css({
					right: 0,
					top: tdHeight + "px"
				}).show();
			}
		} else {  //  其他向上显示
			if (tdIndex <= 3) {  //  向右显示
				$popover.css({
					left: 0,
					top: -popoverHeight-1 + "px"
				}).show();
			} else {  //  向左显示
				$popover.css({
					right: 0,
					top: -popoverHeight-1 + "px"
				}).show();
			}
		}
	});
	//默认触发第一天
    $(".calTable .tripCover").eq(0).trigger("click");
}

/**
 * 把数字转化成天数
 * @param num
 * @returns {String}
 */
function parseNumToString(num) {
	var result = "";
	switch(num) {
		case 1:
			result = "第一天";
			break;
		case 2:
			result = "第二天";
			break;
		case 3:
			result = "第三天";
			break;
		case 4:
			result = "第四天";
			break;
		case 5:
			result = "第五天";
			break;
		case 6:
			result = "第六天";
			break;
		case 7:
			result = "第七天";
			break;
		case 8:
			result = "第八天";
			break;
		case 9:
			result = "第九天";
			break;
		case 10:
			result = "第十天";
			break;
		case 11:
			result = "第十一天";
			break;
		case 12:
			result = "第十二天";
			break;
		case 13:
			result = "第十三天";
			break;
		case 14:
			result = "第十四天";
			break;
		case 15:
			result = "第十五天";
			break;
	}
	return result;
}
/*公共*/
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function replaceHtml(str){
	return str.replace(/</g, "&lt;").replace(/>/g, "&gt;");
}