/**
 *
 */
$(function(){
	init.tipBtnInit();
	init.initAgree();
	passenger.init();
	contactor.getCommonContactor();
	coupon.refresh();
	init.initProtocol();
	init.initUserRegister();
	applyOrder.init();
});


$(function(){
	$("body").delegate(".pop-ul-list","touchstart",function(){
		$(this).closest("#scroller").find("li").removeClass("current");
		$(this).addClass("current");
	});

	$(".order-mark .order-hotel-bed .order-hotel-op").find("input[value='big']").click(function(){
		$(this).closest("div").find(".hotel-tip").attr("style","display:none;");
	});

	$(".order-mark .order-hotel-bed .order-hotel-op").find("input[value='double']").click(function(){
		$(this).closest("div").find(".hotel-tip").attr("style","display;");
	});
	
	 $("body").delegate("#address","click",function(){
    	$("#addressWrapperContainer").closest("div[data-role='popup']").popup("open");
    	contactor.initAddress();
	});

});