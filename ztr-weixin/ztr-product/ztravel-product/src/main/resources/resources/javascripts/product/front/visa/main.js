/**
 * 
 */
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
//                if(!($(".priceGroup li:first").hasClass("priceActive"))){
//                    $(".priceGroup li:first").addClass("priceActive").siblings().removeClass("priceActive");
//                }
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
        	if(validate()){
        		toBook();
        	}
        });
        //查看样图
        $(".post-link").click(function(){
        	var imageId = $(this).attr("data-image");
        	var url = mediaServer+"imageservice?mediaImageId="+imageId;
            $(this).popupimg(url);
        });
        //弹窗提示
        initTips();
        //初始化价格
        dataBind();
        
    })
    
    function getPriceInfo(){
		var pid = $("#pid").val();
		var prid = $("div.panel-wrap").find("ul.priceGroup li.priceActive a").attr("cosid");
		var priceUrl = memberServer + "/product/visa/detail/getPrice/"+pid+"/"+prid;
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
					$("#priceError .modal-body p").html("获取价格信息错误");
			  	  	$("#priceError").modal("show");
				}
			}
		});
	}

	function countPrice(){
		var adultNum = typeof($("div.panel-wrap").find("ul.adult li.active a").html()) == 'undefined'? 0 :$("div.panel-wrap").find("ul.adult li.active a").html();
		var adultPrice = $("#adultPrice").html();
		var totalAdult = 0;
		var totalChild = 0;
		if(adultNum != 0){
			totalAdult = new Number(adultPrice).multiply(adultNum);
		}
		if($("#childDiv").attr("style") == "display"){
			var childNum = typeof($("div.panel-wrap").find("ul.child li.active a").html()) == 'undefined'? 0 : $("div.panel-wrap").find("ul.child li.active a").html();
			var childPrice = $("#childPrice").html();
			if(childNum != 0){
				totalChild = new Number(childPrice).multiply(childNum);
			}
		}
		var total = totalChild + totalAdult;
		return total.toFixed(2);
	}
	
	function toBook(){
		var url = memberServer +'/product/book/tobookpage';
		var adultNum = typeof($("div.panel-wrap").find("ul.adult li.active a").html()) == 'undefined'? 0 :$("div.panel-wrap").find("ul.adult li.active a").html();
		var childNum = typeof($("div.panel-wrap").find("ul.child li.active a").html()) == 'undefined'? 0 : $("div.panel-wrap").find("ul.child li.active a").html();
		var prodId = $("#prodId").val();
		var nature = $("#nature").val();
		var bookDate=getBookDay();
		var prid = $("div.panel-wrap").find("ul.priceGroup li.priceActive a").attr("cosid");
		var $form = $('<form action="'+ url +'" method="post" style="display:none;"></form>');
		$form.append('<input name="productId" value="'+ prodId +'"/>');
		$form.append('<input name="bookDate" value="'+ bookDate +'"/>');
		$form.append('<input name="adultNum" value="'+ adultNum +'"/>');
		$form.append('<input name="childNum" value="'+ childNum +'"/>');
		$form.append('<input name="costPriceId" value="'+prid+'"/>');
		$form.append('<input name="productNature" value="'+nature+'"/>');
		$form.appendTo('body');
		_paq.push(['trackEvent', 'detailpage', 'ztrcrorder', prid,$form.serialize()]);
		$form.submit();
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
	
	function validate(){
		var res = true;
		var adultNum = typeof($("div.panel-wrap").find("ul.adult li.active a").html()) == 'undefined'? 0 :$("div.panel-wrap").find("ul.adult li.active a").html();
		var childNum = typeof($("div.panel-wrap").find("ul.child li.active a").html()) == 'undefined'? 0 : $("div.panel-wrap").find("ul.child li.active a").html();
		var prodId = $("#prodId").val();
		var nature = $("#nature").val();
		var bookDate=getBookDay();
		var prid = $("div.panel-wrap").find("ul.priceGroup li.priceActive a").attr("cosid");
		if(adultNum < 1){
			res = false;
			$("#priceError .modal-body p").html("成人必选");
	  	  	$("#priceError").modal("show");
			return res;
		}
		if(childNum < 0){
			res = false;
			$("#priceError .modal-body p").html("儿童数不能小于0");
	  	  	$("#priceError").modal("show");
			return res;
		}
		if(typeof(prodId) == undefined ||prodId == ''){
			res = false;
			$("#priceError .modal-body p").html("产品不存在");
	  	  	$("#priceError").modal("show");
			return res;
		}
		if(typeof(nature) == undefined ||nature == ''){
			res = false;
			$("#priceError .modal-body p").html("产品不存在");
	  	  	$("#priceError").modal("show");
		}
		if(typeof(prid) == undefined ||prid == ''){
			$("#priceError .modal-body p").html("请选择价格类型");
	  	  	$("#priceError").modal("show");
			return res;
		}
		if(typeof(bookDate) == undefined ||bookDate == ''){
			res = false;
			$("#priceError .modal-body p").html("请选择出行日期");
	  	  	$("#priceError").modal("show");
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
				$("#priceError .modal-body p").html("出发日期不能小于当前日期");
		  	  	$("#priceError").modal("show");
				return res;
			}
		}
		return res;
	}
	
	function dataBind(){
		var selectDay = $("#selectedDay").val();
		var adultNum = $("#adultNum").val();
		var childNum = $("#childNum").val();
		var costPriceId = $("#costPriceId").val();
		if(typeof(selectDay) != undefined && selectDay != ''){
			var tempDay = selectDay.split("-");
			var year = new Number(tempDay[0]);
			var month = new Number(tempDay[1]).subtract(1);
			var day = new Number(tempDay[2]);
			$( "#visaCalendar" ).datepicker("setDate",new Date(year,month,day,0,0,0));
		}
		if(typeof(costPriceId) != undefined && costPriceId != ''){
			$("ul.priceGroup li").each(function(){
				if($(this).find("a").attr("cosid") == costPriceId){
					$(this).click();
				}
			});
		}else{
			$("ul.priceGroup li").removeClass("priceActive");
			$("ul.priceGroup li:first").addClass("priceActive");
		}
		if(typeof(adultNum) != undefined && adultNum != ''){
			$("div.panel-wrap").find("ul.adult li").each(function(){
				var num = new Number($(this).find("a").html());
				if(num == adultNum){
					$(this).siblings("li").removeClass("active");
					$(this).addClass("active");
					$(this).closest("div.dropdown").find("span.activeFonts").html(adultNum);
				}
			});
		}
		if(typeof(childNum) != undefined && childNum != ''){
			$("div.panel-wrap").find("ul.child li").each(function(){
				var num = new Number($(this).find("a").html());
				if(num == childNum){
					$(this).siblings("li").removeClass("active");
					$(this).addClass("active");
					$(this).closest("div.dropdown").find("span.activeFonts").html(childNum);
				}
			});
		}
		countPrice();
	}
