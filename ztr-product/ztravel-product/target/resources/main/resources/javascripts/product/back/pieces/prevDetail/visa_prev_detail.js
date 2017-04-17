$(function(){
		var docEle = document.body || document.getElementsByTagName("body")[0];
	    docEle.setAttribute("data-spy", "scroll");
	    docEle.setAttribute("data-target", "#navbar");
        $(".fixedList").affix({
            offset: {
                top: function(){
                    return this.top = $("#leftFlag").offset().top;
                }
            }
        });

        $(".jobGroup > li").bind("click", function(e){
            var index = $(this).index();
            $(this).addClass("active").siblings().removeClass("active");
            $(".materialModel > .jobElement").eq(index).removeClass("hidden").siblings(".jobElement").addClass("hidden");
        });

        /** jquery ui datepicker本地化 **/
        $.datepicker.regional['zh-CN'] = {
            closeText: '关闭',
            prevText: '<上月',
            nextText: '下月>',
            currentText: '今天',
            monthNames: ['一月','二月','三月','四月','五月','六月',
                '七月','八月','九月','十月','十一月','十二月'],
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
        $( "#visaCalendar" ).datepicker({
            firstDay: 1,
            minDate: new Date(),
            onSelect: function(dateText, inst) {
                if(!($(".priceGroup li:first").hasClass("priceActive"))){
                    $(".priceGroup li:first").addClass("priceActive").siblings().removeClass("priceActive");
                }
                getPriceInfo();
            }
        });
        //价格类型切换
        $("div.panel-wrap").find("ul.priceGroup li").click(function(){
        	$(this).addClass("priceActive").siblings().removeClass("priceActive");
            //初始化总价
            getPriceInfo();
        });
        //初始化第一个选中的价格类型
        $(".priceGroup li:first").click();
        //选择人数，计算总价
        $("div.panel-wrap").find("ul.dropdown-menu li").click(function(){
        	var total = countPrice();
			$("#total").html(total);
        });
        //点击预定
        $(".subBtn").click(function(){
        	//toBook();
        });
        //查看样图
        $(".post-link").click(function(){
        	var imageId = $(this).attr("data-image");
        	var url = mediaServer+"imageservice?mediaImageId="+imageId;
            $(this).popupimg(url);
        });
        //弹窗提示
        initTips();

    })

    function getPriceInfo(){
		var pid = $("#pid").val();
		var prid = $("div.panel-wrap").find("ul.priceGroup li.priceActive a").attr("cosid");
		var priceUrl = basepath + "/piece/prevDetail/visa/getPrice/"+pid+"/"+prid;
		$.ajax({
			url : priceUrl,
			type:'POST',
			contentType:"application/json",
			dataType:'json',
			success:function(result){
				if(result.status == "SUCCESS"){
					$("#adultPrice").html(new Number(result.price.adultPrice).toFixed(2));
					$("#childPrice").html(new Number(result.price.childPrice).toFixed(2));
					var total = countPrice();
					$("#total").html(new Number(total).toFixed(2));
				}else{
					$("#priceError .modal-body p").html("获取价格信息错误");
			  	  	$("#priceError").modal("show");
				}
			}
		});
	}

	function countPrice(){
		var adultNum = typeof($("div.panel-wrap").find("ul.adult li.active a").html()) == 'undefined'? 0 :$("div.panel-wrap").find("ul.adult li.active a").html();
		var childNum = typeof($("div.panel-wrap").find("ul.child li.active a").html()) == 'undefined'? 0 : $("div.panel-wrap").find("ul.child li.active a").html();
		var adultPrice = $("#adultPrice").html();
		var childPrice = $("#childPrice").html();
		var totalChild = 0;
		var totalAdult = 0;
		if(childNum != 0){
			totalChild = new Number(childPrice).multiply(childNum);
		}
		if(adultNum != 0){
			totalAdult = new Number(adultPrice).multiply(adultNum);
		}
		var total = totalChild + totalAdult;
		return total.toFixed(2);
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

	function initTips(){
		$("#priceError .blueBtn").click(function(){
			$("#priceError").modal("hide");
		});
	}