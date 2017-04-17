$(function(){
	var root;
    var _w;
    var w;
    screenchange();
    window.addEventListener('orientationchange', function(event){
        /*旋转至竖屏*/
        if ( (window.orientation == 180 || window.orientation==0)&&(sessionStorage.s_fontsize==undefined)) {/*初始横屏加载切换至竖屏*/
            _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
            w = _w >= 640 ? 640 : _w;

            root.style.fontSize = (w / 320) * 20 + "px";
            sessionStorage.s_fontsize = root.style.fontSize;
        }

        root.style.minHeight = window.innerHeight + "px";
    });
    function screenchange(){
        root = document.getElementsByTagName("html")[0];

        if(window.orientation==undefined){
            _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
            w = _w >= 640 ? 640 : _w;

            root.style.fontSize = (w / 320) * 20 + "px";
        }else{
            if ( window.orientation == 180 || window.orientation==0 ) {  /*竖屏加载*/
                _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
                w = _w >= 640 ? 640 : _w;

                root.style.fontSize = (w / 320) * 20 + "px";
                sessionStorage.s_fontsize = root.style.fontSize;
            }

            if( window.orientation == 90 || window.orientation == -90 ) {  /*横屏加载*/
                if(sessionStorage.s_fontsize==undefined){   /*初始横屏加载*/
                    var w1 = window.innerHeight<= window.screen.height ? window.innerHeight : window.screen.height;
                    var w2 = w1 >= 640 ? 640 : w1;

                    root.style.fontSize = (w2 / 320) * 20 + "px";

                }else{
                    root.style.fontSize = sessionStorage.s_fontsize;
                }
            }
        }
        root.style.minHeight = window.innerHeight + "px";
    }

    $(".tdContWrapper").not(".disabled").click(function(){
        $(".tdContWrapper").removeClass("current");
        $(this).addClass("current");
    });

    /** jquery ui datepicker本地化 **/
    $.datepicker.regional['zh-CN'] = {
        closeText: '关闭',
        prevText: '<上月',
        nextText: '下月>',
        currentText: '今天',
        monthNames: ['01月','02月','03月','04月','05月','06月',
            '07月','08月','09月','10月','11月','12月'],
        monthNamesShort: ['一','二','三','四','五','六',
            '七','八','九','十','十一','十二'],
        dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
        dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
        dayNamesMin: ['日','一','二','三','四','五','六'],
        weekHeader: '周',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: '年'};
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
    $( "#orderDateBox" ).datepicker({
        firstDay: 0,
        minDate: new Date(),
        onSelect: function(dateText, inst) {
            if($(".priceGroup li:first").hasClass("priceActive")){
                $(".priceGroup li:first").removeClass("priceActive");
            }else{
                $(".priceGroup li:first").addClass("priceActive").siblings().removeClass("priceActive");
            }
            getPriceInfo();
        }
    });

    $(".switch-checkbox").click(function(){
       $(this).toggleClass("on");

        if ($(this).hasClass("on")) {
            $(".trip-num-block").hide();
            $(".menu-block").show();
        } else {
            $(".trip-num-block").show();
            $(".menu-block").hide();
        }
    });

    $("body").delegate('.menu-item label', 'click', function() {
        if (!$(this).hasClass('disabled')) {
            $(this).parents(".menu-list").find(".menu-short-desc").hide();
            if ($(this).find("input[type='radio']").prop("checked")) {
                $(this).parents(".menu-item").find(".menu-short-desc").show();
            }
        }
    });

    //加减法初始化
    $(".plus-minus-comp span").click(function(){
        var $that = $(this);
        if (!$that.hasClass("disabled")) {
            if ($that.hasClass("plus-mark")) {
                var val = parseInt($(this).siblings(".result").html());
                if(val+1 <= 10){
                $(this).siblings(".result").html(val+1);
                }
                if((val+1) >=1 ){
                	$(this).siblings("span.minus-mark").removeClass("disabled");
                }else{
                	$(this).siblings("span.minus-mark").add("disabled");
                }
                getPriceInfo();
            } else if ($that.hasClass("minus-mark")) {
                var val = parseInt($(this).siblings(".result").html());
				if($that.closest("div.passenger-type").hasClass("adult")){
					if (val <= 2) {
                            $(this).addClass("disabled");
                        }
				}else{
                    if (val <= 1) {
                        $(this).addClass("disabled");
                    }
				}
                $(this).siblings(".result").html(val-1);
                getPriceInfo();
            }
        }
    });

    //价格类型切换
    $(".menu-select > .priceType-link").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
        getPriceInfo();
    });
    //初始化第一个选中的价格类型
    $(".menu-select a.active:first").click();
    //点击下一步
    $("div.total-price-block span.next").click(function(){
    	if(validate()){
    		toBook();
    	}
    });
    //错误提示
    errorBtnConfirm();

});

function getPriceInfo(){
	var pid = $("#pid").val();
	var prid = $("div.trip-num-wrapper div.menu-select a.active").attr("cosid");
	var priceUrl = wxServer + "/local/product/weixin/getPrice/"+pid+"/"+prid;
	$.ajax({
		url : priceUrl,
		type:'POST',
		contentType:"application/json",
		dataType:'json',
		success:function(result){
			if(result.status == "SUCCESS"){
				$("#adultPrice").html(new Number(result.price.adultPrice).toFixed(2));
				if(null != result.price.hasChildPrice){
					if(result.price.hasChildPrice){
						if(new Number(result.price.childPrice) > 0){
							$("#childPrice").html(new Number(result.price.childPrice).toFixed(2));
							$("#childDiv").attr("style","display");
						}else{
							$("#childDiv").attr("style","display:none;");
						}
					}else{
						$("#childDiv").attr("style","display:none;");
					}
				}else{
					$("#childDiv").attr("style","display:none;");
				}
				var total = countPrice();
				$("#total").html(new Number(total).toFixed(2));
			}else{
				$("#errorHintMsg").html("查询售价报错");
				$("#alert-errorhint-dialog").popup("open");
			}
		}
	});
}

function countPrice(){
	var adultNum = typeof($("div.trip-num-block div.adult span.result").html()) == 'undefined'? 0 :$("div.trip-num-block div.adult span.result").html();
	var adultPrice = $("#adultPrice").html();
	var totalAdult = 0;
	var totalChild = 0;
	if(adultNum != 0){
		totalAdult = new Number(adultPrice).multiply(adultNum);
	}
	if($("#childDiv").attr("style") == "display"){
		var childNum = typeof($("div.trip-num-block div.child span.result").html()) == 'undefined'? 0 : $("div.trip-num-block div.child span.result").html();
		var childPrice = $("#childPrice").html();
		if(childNum != 0){
			totalChild = new Number(childPrice).multiply(childNum);
		}
	}
	var total = totalChild + totalAdult;
	return total.toFixed(2);
}

function toBook(){
	var url = wxServer +'/book/weixin/tobook';
	var adultNum = typeof($("div.trip-num-block div.adult span.result").html()) == 'undefined'? 0 :$("div.trip-num-block div.adult span.result").html();
	var childNum = typeof($("div.trip-num-block div.child span.result").html()) == 'undefined'? 0 : $("div.trip-num-block div.child span.result").html();
	var prodId = $("#prodId").val();
	var nature = $("#nature").val();
	var bookDate=getBookDay();
	var prid = $("div.trip-num-wrapper div.menu-select a.active").attr("cosid");
	_paq.push(['trackEvent', 'detailpage', 'ztrcrorder', prid]);
	window.location.href = url + "/" + adultNum + "/" + childNum + "/" + bookDate + "/" + prodId+"/"+prid+"/"+nature;
}

function getBookDay(){
	var month = $("table.ui-datepicker-calendar").find("a.ui-state-active").closest("td").attr("data-month");
	var monthStr = "";
	var dayStr = "";
	if(new Number(month).add(1) < 10){
		monthStr = "0"+(new Number(month).add(1));
	}else{
		monthStr = new Number(month).add(1);
	}
	var year = $("table.ui-datepicker-calendar").find("a.ui-state-active").closest("td").attr("data-year");
	var day = $("table.ui-datepicker-calendar").find("a.ui-state-active").html();
	if(new Number(day) < 10){
		dayStr = "0"+day;
	}else{
		dayStr = day;
	}
	return (year +"-"+ monthStr +"-"+ dayStr);
}

function errorBtnConfirm(){
   $("body").delegate(".btn-confirm","click",function(){
		$("#errorHintMsg").html("");
		$("#alert-errorhint-dialog").popup("close");
	});
}

function validate(){
	var res = true;
	var adultNum = typeof($("div.trip-num-block div.adult span.result").html()) == 'undefined'? 0 :$("div.trip-num-block div.adult span.result").html();
	var childNum = typeof($("div.trip-num-block div.child span.result").html()) == 'undefined'? 0 : $("div.trip-num-block div.child span.result").html();
	var prodId = $("#prodId").val();
	var nature = $("#nature").val();
	var bookDate=getBookDay();
	var prid = $("div.trip-num-wrapper div.menu-select a.active").attr("cosid");
	if(adultNum < 1){
		res = false;
		$("#errorHintMsg").html("成人必选");
		$("#alert-errorhint-dialog").popup("open");
		return res;
	}
	if(childNum < 0){
		res = false;
		$("#errorHintMsg").html("儿童数不能小于0");
		$("#alert-errorhint-dialog").popup("open");
		return res;
	}
	if(typeof(prodId) == undefined ||prodId == ''){
		res = false;
		$("#errorHintMsg").html("产品不存在");
		$("#alert-errorhint-dialog").popup("open");
		return res;
	}
	if(typeof(nature) == undefined ||nature == ''){
		res = false;
		$("#errorHintMsg").html("产品不存在");
		$("#alert-errorhint-dialog").popup("open");
	}
	if(typeof(prid) == undefined ||prid == ''){
		$("#errorHintMsg").html("请选择价格类型");
		$("#alert-errorhint-dialog").popup("open");
		return res;
	}
	if(typeof(bookDate) == undefined ||bookDate == ''){
		res = false;
		$("#errorHintMsg").html("请选择出行日期");
		$("#alert-errorhint-dialog").popup("open");
		return res;
	}else{
		var now = new Date();
		var nowYear = now.getFullYear();
		var nowMonth = now.getMonth();
		var nowDay = now.getDate();
		var nowDate = new Date(nowYear,nowMonth,nowDay,0,0,0);
		var selectYear = new Number($("table.ui-datepicker-calendar").find("a.ui-state-active").closest("td").attr("data-year"));
		var selectMonth = new Number($("table.ui-datepicker-calendar").find("a.ui-state-active").closest("td").attr("data-month"));
		var selectDay = new Number($("table.ui-datepicker-calendar").find("a.ui-state-active").html());
		var selectDate = new Date(selectYear,selectMonth,selectDay,23,59,59);
		if(nowDate > selectDate){
			res = false;
			$("#errorHintMsg").html("出发日期不能小于当前日期");
			$("#alert-errorhint-dialog").popup("open");
			return res;
		}
	}
	return res;
}