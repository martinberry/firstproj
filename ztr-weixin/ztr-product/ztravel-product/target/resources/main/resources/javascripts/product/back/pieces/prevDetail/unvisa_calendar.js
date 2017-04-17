  var costIndex=0;
  var adultnum=1;
  var childnum=1;
$(function(){
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

        }
    });

	initCalendar();

	$(".costPrice").click(function(){
		obj=this;
		$(".costPrice").removeClass("active");
		$(".costPrice").removeClass("priceActive");
		costIndex= $(".priceGroup li").index($(this));
		$(obj).addClass("active");
		$(obj).addClass("priceActive");
		$(".panelWrap-box .priceNum").attr("style",'display:none');
		$(".panelWrap-box-child .priceNum").attr("style",'display:none');
		$(".panelWrap-box-child").attr("style",'display:block;');
		$(".panelWrap-box .priceNum").eq(costIndex).removeAttr("style");
		adultnum=$(".panelWrap-box li.active a").html();
		var childPrice=$(".panelWrap-box-child .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		var adultPrice=$(".panelWrap-box .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		var totalPrice;
		if(childPrice=="''"){
			$(".panelWrap-box-child").attr("style",'display:none;');
			totalPrice =adultPrice*adultnum;
		}else{
			$(".panelWrap-box-child .priceNum").eq(costIndex).removeAttr("style");
			childnum=$(".panelWrap-box-child li.active a").html();
			totalPrice=adultPrice*adultnum+childPrice*childnum;
		}
		totalPrice=totalPrice.toFixed(2);
		$("#totalPrice").html("￥"+totalPrice);
	});

	$(".dropdown-menu.adultnum").click(function(){
		adultnum=$(this).find("li.active a").html();
		costIndex= $(".priceGroup li").index($(".costPrice.active"));
		var adultPrice=$(".panelWrap-box .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		var childPrice=$(".panelWrap-box-child .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		var totalPrice;
		if(childPrice=="''"){
   		   	totalPrice=adultPrice*adultnum;
   	   	}else{
   	   		childnum=$(".panelWrap-box-child li.active a").html();
   	   		totalPrice=adultPrice*adultnum+childPrice*childnum;
   	   	}
   	   	totalPrice=totalPrice.toFixed(2);
   	   	$("#totalPrice").html("￥"+totalPrice);
	});

	$(".dropdown-menu.childnum").click(function(){
		childnum=$(this).find("li.active a").html();
		adultnum=$(".panelWrap-box li.active a").html();
		costIndex= $(".priceGroup li").index($(".costPrice.active"));
		var adultPrice=$(".panelWrap-box .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
		var childPrice=$(".panelWrap-box-child .priceNum").eq(costIndex).html().replace("￥","").replace("<span>/人</span>","");
   	   	var totalPrice=adultPrice*adultnum+childPrice*childnum;
   	   	totalPrice=totalPrice.toFixed(2);
   	   	$("#totalPrice").html("￥"+totalPrice);
	});

	$("#orderinstance").click(function(){

	});

	$(".fixOrder").click(function(){

	});

});

function initCalendar(){
	if($(".priceGroup li").size()==0){
    	$(".panelWrap-title").eq(1).attr("style",'display:none;');
    	$(".panelWrap-head").attr("style",'display:none;');
    	$(".panelWrap-price").attr("style",'display:none;');
    	$(".visaSubmit").attr("style","display:none;");

    }else{
        $(".priceGroup li:first").addClass("priceActive");
        $(".priceGroup li:first").addClass("active");
        $(".panelWrap-box .priceNum").eq(0).removeAttr("style");

    	var initadultPrice=$(".panelWrap-box .priceNum").eq(0).html().replace("￥","").replace("<span>/人</span>","");
    	var initchildPrice=$(".panelWrap-box-child .priceNum").eq(0).html().replace("￥","").replace("<span>/人</span>","");
        if(initchildPrice=="''"){
        	$(".panelWrap-box-child").attr("style",'display:none;');
        }else{
        	 $(".panelWrap-box-child .priceNum").eq(0).removeAttr("style");
        }
    	var totalPrice=initadultPrice*2;
    	totalPrice=totalPrice.toFixed(2);
    	$("#totalPrice").html("￥"+totalPrice);
    }

}