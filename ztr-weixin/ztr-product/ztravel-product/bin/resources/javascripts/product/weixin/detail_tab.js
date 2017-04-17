var CALENDARDATA = {};
var lowest_PerPrice;
var lowest_Price;
var lowest_Date ;
var selected_stock = 0;
var initCalendarReady = false ;
var MAX_INT = 100000000;
$(function(){

	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/weixin/detail/getCalendarData/'+productPid ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    success: function(result){
	    	lowest_PerPrice = result.lowestPerPrice;
    		lowest_Price = result.lowestPrice;
    		$("#initMarketPrice").val(lowest_Price) ;
    		$("#initAdultPrice").val(lowest_PerPrice);
    		lowest_Date = result.lowestDate;
	    	if(result.calendar !== undefined)CALENDARDATA = result.calendar;
	    	initCalendar(result);
	    	initCalendarReady = true ;
	    	setPkgList();
	    	rebindData();
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});

	$("body").delegate(".wantgo-link","click",function(){
        if ($("#popupMenu-screen").is(":visible")) {
            $("#popupMenu").popup("close");
            setTimeout(function(){
                $("#wantgoWindow").popup("open");
            }, 600);
        } else {
            $("#wantgoWindow").popup("open");
        }
    });

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
			$(".order-link").show() ;
			$(".wantgo-link").hide() ;
			$(".menu-row .ui-grid-c").removeClass("menu-row-focus");
			var sday = $("#selectDay").val();
			$(".outDate").html("请选择出行时间");
			$("#selectDay").val("");
			setPkgList();
			$("#want-go-tel").attr("placeholder","请输入手机号") ;
			$("#want-go-tel").val('') ;
			_paq.push(['trackEvent', 'detailpage', 'ztrwantbook', phone, sday.replace(/\//g, "-" )]);
		}
	});

	$('.order-link').click(function(){
		var selectDay = $("#selectDay").val();
		var packageId = $('#pkgId').val();
		var url = wxServer +'/book/weixin/tobook';
		if(selectDay == undefined || isNaN(new Date(selectDay).getTime()) ){
			if($("#popupMenu-screen").is(":visible")){
			}else{
				$("#popupMenu").popup("open");
			}
			$(".windowContent").first().click();
			return;
		}
		if(!packageId){
			if($("#popupMenu-screen").is(":visible")){
			}else{
				$("#popupMenu").popup("open");
			}
			$(".windowContent").last().click();
			return;
		}
		var day = CALENDARDATA[selectDay.replace(/-/g, "/")];
		if(day){
			var stock = day.stock;
			var needNum = 0;
			var pkgs = day.salesPackages;
			if(pkgs){
				for(var i=0; i< pkgs.length; i++){
					var pkg = pkgs[i];
					if(pkg.pkgId == packageId){
						if(pkg.adultNum)needNum += pkg.adultNum;
						if(pkg.childrenNum)needNum += pkg.childrenNum;
						break;
					}
				}
			}
			if(needNum > stock){
				$("#lackstock-a").click();
				return;
			}
		}
		var param = {
				productId: productId,
				selectDay: selectDay,
				packageId: packageId
		};
		_paq.push(['trackEvent', 'detailpage', 'ztrcrorder', JSON.stringify(param)]);
		window.location.href = url + "/" + packageId + "/" + selectDay + "/" + productId;
	});

    $(".setMeal02List").delegate("li", "click", function(){
        var liTitleVal = $(this).find(".liTitle").text();
        $(this).addClass("current");
        $(this).siblings("li").removeClass("current");
        $(".setMealVal").text(liTitleVal);
        $("#pkgId").val($(this).attr("value"));
        //设置对应套餐起价显示
        var activeDate = $('#selectDay').val();
        if(!activeDate)activeDate = lowest_Date;
        setPkg(activeDate, $("#pkgId").val());
    });
});
/**
 * 从建单页返回时回填数据
 */
function rebindData(){
	if (de_SelectedDay !== undefined && de_SelectedDay != '') {
		$("#menu-row-box .ui-grid-c > input").each(function() {
			if ($(this).val() == de_SelectedDay) {
				$(this).parent().click();
			}
		});
	}
	if (de_packageId !== undefined && de_packageId != '') {
		$(".setMeal02List").find("li[value='" + de_packageId + "']").click();
	}
}
/**
 * 初始化日历
 * @param data
 */
function initCalendar(data){
	var allDays = getAllDays(data.calendar);
	setDayPrice(allDays, "#menu-row-box");
	//  生成月份
	$("#tab_month").append(geneTabSwitchMonth(allDays));
	var monthtab_w = parseInt($('.menu-list.changemenu').width()-$('.left-month-btn').outerWidth()*2);
	$('.monthtab').css('width',monthtab_w + 'px');
	var monthtab_li_lenght = $('.monthtab ul li').length;
	var monthtab_li_w;   //底部弹窗 月份内 每月宽度定义
	if(monthtab_li_lenght<4){
        monthtab_li_w = monthtab_w/monthtab_li_lenght;
    }else{
        monthtab_li_w = monthtab_w/4;
    }
	$('.monthtab ul li').css('width',monthtab_li_w+'px');
	if(monthtab_li_lenght == 1){
		$('.right-month-btn').addClass('disable') ;
	}
	//  设置月份样式
	$(".menu-row").bind("touchmove", function(event){
        $("#popupMenu-screen").touchmove(function(){
            return false;
        })
    });

    $(".menu-list ul li").click(function(){
        var listInd = $(this).index();
        var listObj = $(".menu-list ul li").eq(listInd);

        listObj.siblings().removeClass("list-cut");
        if(!listObj.hasClass("list-cut")){
            listObj.addClass("list-cut");
        }

        var rowboxObj = $("#menu-row-box .menu-row").eq(listInd);
        rowboxObj.siblings().addClass("hidden");
        if(rowboxObj.hasClass("hidden")){
            rowboxObj.removeClass("hidden");
        }

        var currentmonth=$('.monthtab ul').find('.list-cut').index();
        if(currentmonth==0){
        	$('.left-month-btn').addClass('disable');
        	$('.right-month-btn').removeClass('disable');
        }else if(0<currentmonth&&currentmonth<($('.monthtab ul li').length-1)){
        	$('.left-month-btn').removeClass('disable');
        	$('.right-month-btn').removeClass('disable');
        }else if(currentmonth==($('.monthtab ul li').length-1)){
        	$('.left-month-btn').removeClass('disable');
        	$('.right-month-btn').addClass('disable');
        }
    });

    $(".menu-row .ui-grid-c").click(function(){
    	if($(this).find(".ui-block-c").hasClass('sold-out')){
    		return;
    	}
        $(".menu-row .ui-grid-c").removeClass("menu-row-focus");
        $(this).toggleClass("menu-row-focus");
        $(".outDate").html($(this).find(".ui-block-a").html() + ' ' + $(this).find(".ui-block-b").html());
        $("#selectDay").val($(this).find("input").val());
		if($(this).find(".ui-block-c").hasClass('cannt-order')){
			$(".order-link").hide() ;
			$(".wantgo-link").show() ;
		}else{
			$(".order-link").show() ;
			$(".wantgo-link").hide() ;
		}
		//初始化当天对应的可选套餐
		setPkgList($("#selectDay").val());
    });

}
/**
 * 将数据转化成日历需要的样式
 * @param calendar
 * @returns {Array}
 */
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
			tmpDay.marketPrice = day.marketPrice === null? "": day.marketPrice;
			tmpDay.saleStatus = day.saleStatus;
			if(day.salesPackages){
				var lowestPerPrice = MAX_INT;
				var pkgs = day.salesPackages;
				for(var i = 0; i < pkgs.length; i++){
					var pkg = pkgs[i];
					if(pkg.perPrice != null && pkg.perPrice < lowestPerPrice){
						lowestPerPrice = pkg.perPrice;
					}
				}
				if(lowestPerPrice < MAX_INT){
					tmpDay.price = lowestPerPrice;
				}
			}
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

function setDayPrice(data, ele) {
    for (var i = 0; i < data.length; i++) {
        var priceInfo = data[i].info;  // 获得当前价格信息
        var dateinfo = new Date(data[i].month);
        var year = dateinfo.getFullYear();
        var month = dateinfo.getMonth()+1;
        var displayStyle = ''
        if(i != 0){
        	displayStyle = 'hidden' ;
        }
        var calStr = "<div class='menu-row "+displayStyle+"'>";  // 拼接字符串生成每日价格
        for(var j in priceInfo){
        	dateinfo.setDate(priceInfo[j].dayDate);
        	calStr += "<div class='ui-grid-c'>";
        	calStr += "<input type='hidden' value='"+dateinfo.Format("yyyy-MM-dd")+"'/>";
        	calStr += '<div class="ui-block-a">'+month+'月'+priceInfo[j].dayDate+'日</div>';
        	calStr += '<div class="ui-block-b">周'+parseNumToWeekDay(dateinfo.getDay()+1)+'</div>';
        	if(priceInfo[j].saleStatus == 'NOT_SCHEDULED'){
        		calStr += '<div class="ui-block-c cannt-order">未开放</div>';
        	}else{
        		if(parseInt(priceInfo[j].leftNum) > 9){
        			calStr += '<div class="ui-block-c">余票'+'<span>充足</span><span style="display:none;">'+priceInfo[j].leftNum+'</span></div>';
        		}else if(parseInt(priceInfo[j].leftNum)==0){
        			calStr += '<div class="ui-block-c sold-out">售罄</div>';
        		}else{
        			calStr += '<div class="ui-block-c">余票'+'<span style="display:none;"></span><span>'+priceInfo[j].leftNum+'</span></div>';
        		}
        	}
        	if(priceInfo[j].childPrice){
        		calStr += '<div class="ui-block-d">';
        		calStr += '<p><span class="menu-row-til">成人：</span>￥'+priceInfo[j].price+'<input type="hidden" value='+priceInfo[j].price+' name="chooseDayPrice"/><input type="hidden" value='+priceInfo[j].marketPrice+' name="marketPrice"/></p>';
        		calStr += '<p><span class="menu-row-til">儿童：</span><span class="nenu-row-num">￥'+priceInfo[j].childPrice+'</span></p>';
        		calStr += '</div>';
        	}else{
        		calStr += '<div class="ui-block-d">￥'+priceInfo[j].price+'<input type="hidden" value='+priceInfo[j].price+' name="chooseDayPrice"/><input type="hidden" value='+priceInfo[j].marketPrice+' name="marketPrice"/></div>';
        	}
        	calStr += "</div>";
        }
        calStr += "</div>";
        $(ele).append(calStr);
    }
}
function setPkgList(activeDate){
	if(activeDate){
		activeDate = new Date(activeDate).Format("yyyy/MM/dd");
	}else{
		activeDate = lowest_Date;
	}
	var activeDay = CALENDARDATA[activeDate];
	var html = "";
	var pkgLowestPerPrice = MAX_INT, pkgPersonNum = 0, pkgPrice = 0;
	if(activeDay.salesPackages){
		var salePkgs = activeDay.salesPackages;
		for(var i=0; i< salePkgs.length; i++){
			var pkg = salePkgs[i];
			var name = replaceHtml(pkg.name);
			var desc = replaceHtml(pkg.desc);
			html += '<li value="'+ pkg.pkgId +'"><div class="liTitle">'+ name +'</div><div class="liDetails">'+ desc +'</div></li>';
			if(pkg.perPrice < pkgLowestPerPrice ){
				pkgLowestPerPrice =  pkg.perPrice;
				pkgPrice = pkg.price;
				pkgPersonNum = 0;
				if(pkg.adultNum)pkgPersonNum += pkg.adultNum;
				if(pkg.childrenNum)pkgPersonNum += pkg.childrenNum;
			}
		}
	}
	$(".setMeal02List").html(html);
	$('#pkgId').val("");
	$(".setMealVal").text("请选择出行套餐");
	//设置默认起价显示
	if(pkgLowestPerPrice < MAX_INT){
		$("#currentPerPrice").html(pkgLowestPerPrice);
		$("#currentTotalPrice").html(pkgPrice);
		$("#qi").html("起");
	}
	$("#pop-marketPrice").html(activeDay.marketPrice);
	var savePrice = pkgPersonNum * activeDay.marketPrice - pkgPrice;
	if(savePrice >0){
		$("#savePrice").html(savePrice);
		$("#pop-savePrice").html(savePrice);
		$('#savePrice').parent().show();
	}else{
		$("#savePrice").html("");
		$("#pop-savePrice").html("");
		$('#savePrice').parent().hide();
	}
}
function setPkg(activeDate, activePkg){
	if(activeDate){
		var activeDay = CALENDARDATA[new Date(activeDate).Format("yyyy/MM/dd")];
		if(activeDay.salesPackages){
			var salePkgs = activeDay.salesPackages;
			for(var i=0; i< salePkgs.length; i++){
				var pkg = salePkgs[i];
				if(activePkg == pkg.pkgId){
					$("#currentPerPrice").html(pkg.perPrice);
					$("#qi").html("");
					var savePrice = num = 0;
					if(pkg.adultNum)num += pkg.adultNum;
					if(pkg.childrenNum)num += pkg.childrenNum;
					$("#currentTotalPrice").html(pkg.price);
					$("#pop-marketPrice").html(activeDay.marketPrice);
					savePrice = activeDay.marketPrice * num - pkg.price;
					if(savePrice >0){
						$("#savePrice").html(savePrice);
						$("#pop-savePrice").html(savePrice);
						$('#savePrice').parent().show();
					}else{
						$("#savePrice").html("");
						$("#pop-savePrice").html("");
						$('#savePrice').parent().hide();
					}
					break;
				}
			}
		}
	}
}

function geneTabSwitchMonth(data) {
    var liStr = "";
    for (var i = 0; i < data.length; i++) {
        var temp = new Date(data[i].month);
        var current = "";
        if(i == 0){
        	current = "list-cut";
        }
        liStr += "<li class='" + current + "'>" + (temp.getMonth() + 1) + "月</li>";
    }
    return liStr;
}
/**
 * 把数字转化成星期天数
 * @param num
 * @returns {String}
 */
function parseNumToWeekDay(num) {
	var result = "";
	switch(num) {
		case 2:
			result = "一";
			break;
		case 3:
			result = "二";
			break;
		case 4:
			result = "三";
			break;
		case 5:
			result = "四";
			break;
		case 6:
			result = "五";
			break;
		case 7:
			result = "六";
			break;
		case 1:
			result = "日";
			break;
	}
	return result;
}
/*放公共*/
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