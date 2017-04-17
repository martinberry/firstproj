$(function(){
    FastClick.attach(document.body);
//    $(window).scroll(function(){
//        if($(this).scrollTop() >= $(".nav-content").offset().top - $(".headerBar").outerHeight() - $(".narbar").outerHeight()){
//            $(".navbar").addClass("navbarFix");
//            $(".nav-fixed-placeholder").show();
//        }else{
//            $(".navbar").removeClass("navbarFix");
//            $(".nav-fixed-placeholder").hide();
//        }
//    });

    var til = $(".title").text().trim();
    var subtil = '';
    var maxwidth = 11;
    if (til.length > maxwidth) {
        subtil = til.substring(0, maxwidth);
        $(".title").html(subtil + '...');
    }

//    $(".swiper-container").find(".lazyload-img").trigger("appear");

//    $(".nav-content > div:first").nextAll().hide("fast");
//    $("#navMenu > li").click(function(){
//	  $("html,body").animate({scrollTop: $("#scroll-top").offset().top-$(".navbar").outerHeight()}, 50);
//	  var navBar = $(this).prop("id");
//	  var navItem = $(".nav-content").children("."+navBar);
//	  if(!$(this).hasClass("link-active")){
//	      $("#navMenu > li").removeClass("link-active");
//	      $(this).addClass("link-active");
//
//	      if(navItem.is(":hidden")){
//	          navItem.siblings().fadeOut("fast");
//	          navItem.fadeIn("fast");
//	      }
//	      if (navBar == 'navbar-hotel') {
//              $(".navWrap-hotel .swiper-container").eq(0).find(".lazyload-img").trigger("appear");
//          }
//	  }
//    });
    var pressArr = ["tips-sun-press", "tips-bag-press", "tips-tower-press", "tips-loca-press", "tips-list-press"];
    var tipsObj = $(".tips-wrap > li");
    tipsObj.click(function(){
        var tipsInd = $(this).index();
        var pressInd = pressArr[tipsInd];

        if(!$(this).hasClass("live")){
        	var theInd = tipsObj.parent().find(".live").index();
            tipsObj.eq(theInd).find(".common-tips").removeClass(pressArr[theInd]);
            $(this).siblings().removeClass("live");
            tipsObj.eq(tipsInd).find(".common-tips").addClass(pressInd);
            $(this).addClass("live");
            $("#tips-bot").attr("class",'com-bot'+tipsInd+'');
            $("#tips-top").attr("class",'com-top'+tipsInd+'');
            var boxObj = $(".tips-content > .tips-detail").eq(tipsInd);
            if(boxObj.hasClass("hidden")){
                boxObj.removeClass("hidden");
                boxObj.siblings(".tips-detail").addClass("hidden");
            }
        }
    });

//    $("body").delegate(".hotelNotice", "click", function(){
//        if($(this).find("i").hasClass("bottomArrow")){
//            $(this).find("i").removeClass("bottomArrow");
//            $(this).find("i").addClass("upRightArrow");
//            $(this).siblings(".seeDefault").show(300)
//        }else if($(this).find("i").hasClass("upRightArrow")){
//            $(this).find("i").removeClass("upRightArrow");
//            $(this).find("i").addClass("bottomArrow");
//            $(this).siblings(".seeDefault").hide(300)
//        }
//    });

    $("body").delegate(".hotelBottomPackUp", "click", function(){
        $(this).parents(".seeDefault").hide(300);
        $(this).parents(".seeDefault").siblings(".hotelNotice").find("i").removeClass("upRightArrow");
        $(this).parents(".seeDefault").siblings(".hotelNotice").find("i").addClass("bottomArrow");
    });

  //预订须知
//    $("body").delegate(".noticeFold", "click", function(){
//        if ($(this).find("i").hasClass("downArrow")) { // 当前为收起状态
//            $(this).find("i").removeClass("downArrow");
//            $(this).find("i").addClass("upArrow");
//            $(this).find("strong").html("收起");
//            $(this).siblings(".costDetails").find(".costList").removeClass("maxHeight");
//        } else if ($(this).find("i").hasClass("upArrow")) {
//            $(this).find("i").removeClass("upArrow");
//            $(this).find("i").addClass("downArrow");
//            $(this).find("strong").html("查看更多");
//            $(this).siblings(".costDetails").find(".costList").addClass("maxHeight");
//        }
//    });
    //统一初始化展开收起
    initUpAndDown();

    //用户评论
//    $("body").delegate(".evaluateFold", "click", function(){
//        if ($(this).find("i").hasClass("downArrow")) { // 当前为收起状态
//            $(this).find("i").removeClass("downArrow");
//            $(this).find("i").addClass("upArrow");
//            $(this).find("strong").html("收起");
//            $(this).siblings(".evaluateDetail").find(".judge-cnt:gt(4)").removeClass("hidden");
//        } else if ($(this).find("i").hasClass("upArrow")) {
//            $(this).find("i").removeClass("upArrow");
//            $(this).find("i").addClass("downArrow");
//            $(this).find("strong").html("查看更多评论");
//            $(this).siblings(".evaluateDetail").find(".judge-cnt:gt(4)").addClass("hidden");
//        }
//    });

    $(".hotel-tips").click(function(){
        $(this).find(".arr-upside").toggleClass("arr-downside");
        $(this).next().next().find(".arr-upside").toggleClass("arr-downside");
        $(this).next().slideToggle("fast");
        if($(this).next().next().find(".arr-upside").hasClass("arr-downside")){
        	$(this).next().next().slideUp("fast");
        }else{
        	$(this).next().next().slideDown("fast");
        }
    });

    $(".pack-up").click(function(){
        $(this).find(".arr-upside").toggleClass("arr-downside");
        $(this).prev().prev().find(".arr-upside").toggleClass("arr-downside");
        $(this).prev().slideToggle("fast");
        if($(this).find(".arr-upside").hasClass("arr-downside")){
            $(this).slideUp("fast");
        }else{
            $(this).slideDown("fast");
        }
    });

    $(".popup-toggle").click(function(){
    	if($(this).has("input").length > 0){
    		$("#popupMenu").popup("close");
    	}else{
    		if(!initCalendarReady){
    			return false ;
    		}
    		$("#popupMenu").popup("open");
    	}
    });

    $("img.lazyload-img").lazyload({
    	threshold: 500,
    	placeholder: ''
    });

    var monthtab_marL=0;
    var ml_max_dis;
    var currentmonth;
    var currentmonthL;
    var monthtabL;
    $('.right-month-btn').click(function(){
        if(!($(this).hasClass('disable'))){
        	currentmonthL=parseInt($('.monthtab ul').find('.list-cut').offset().left);
        	monthtabL=parseInt($('.monthtab').offset().left+3*$('.monthtab li').width()-4);
        	if(currentmonthL+1 < monthtabL){
        		currentmonth=$('.monthtab ul').find('.list-cut').index();
        		$('.monthtab li').eq(currentmonth+1).trigger('click');
	        }else{
	            monthtab_marL = monthtab_marL - $('.monthtab li').width();
	            ml_max_dis=-$('.monthtab li').width()*($('.monthtab li').length);
	            if(monthtab_marL>=ml_max_dis){
	                $('.monthtab ul').css('margin-left',monthtab_marL+'px');
	            }
	            currentmonth=$('.monthtab ul').find('.list-cut').index();
	            $('.monthtab li').eq(currentmonth+1).trigger('click');
	        }
        }
    });
    $('.left-month-btn').click(function(){
        if(!($(this).hasClass('disable'))){
        	currentmonthL=parseInt($('.monthtab ul').find('.list-cut').offset().left);
        	monthtabL=parseInt($('.monthtab').offset().left);
        	if(currentmonthL > monthtabL){
                currentmonth=$('.monthtab ul').find('.list-cut').index();
                $('.monthtab li').eq(currentmonth-1).trigger('click');
            }else{
                monthtab_marL = monthtab_marL + $('.monthtab li').width();
                if(monthtab_marL<=0){
                    $('.monthtab ul').css('margin-left',monthtab_marL+'px');
                }
                currentmonth=$('.monthtab ul').find('.list-cut').index();
                $('.monthtab li').eq(currentmonth-1).trigger('click');
            }
        }
	});
    $(".windowContent").click(function(){
        $(this).addClass("current");
        $(this).siblings(".windowContent").removeClass("current");
    });
});

function closePriceInfoTips(){
	$("#inst-dialog").popup('close') ;
}

$(function(){
	$("#preOrder").on("click","",function(){
		var pid = $("#pid").val();
		window.location.href=basepath+"/product/weixin/detail/chooseTrip/"+pid;
	})
})

	function initUpAndDown(){
	$("body").delegate(".commonBottomStyle", "click", function(e){
        if($(this).is(".hotelNotice")){
            $(e.target).closest(".hotelOp").find(".seeDefault").show(300);
            $(e.target).closest(".hotelOp").find(".hotelNotice").css("display", "none");
            $(e.target).closest(".hotelOp").find(".closeHotelNotice").css("display", "block");
        }else if($(this).is(".closeHotelNotice")){
            $(e.target).closest(".hotelOp").find(".seeDefault").hide(300);
            $(e.target).closest(".hotelOp").find(".hotelNotice").css("display", "block");
            $(e.target).closest(".hotelOp").find(".closeHotelNotice").css("display", "none");
            $("body").animate({scrollTop: $("#hotelPoint").offset().top}, 300);
        }else if($(this).is(".orderNotice")){
            $("#orderWrap").removeClass("maxHeight");
            $(".orderNotice").css("display", "none");
            $(".closeOrderNotice").css("display", "block");
        }else if($(this).is(".closeOrderNotice")){
            $("#orderWrap").addClass("maxHeight");
            $(".orderNotice").css("display", "block");
            $(".closeOrderNotice").css("display", "none");
            $("body").animate({scrollTop: $("#orderPoint").offset().top}, 300);
        }else if($(this).is(".visaNotice")){
            $("#visaWrap").removeClass("maxHeight");
            $(".visaNotice").css("display", "none");
            $(".closeVisaNotice").css("display", "block");
        }else if($(this).is(".closeVisaNotice")){
            $("#visaWrap").addClass("maxHeight");
            $(".visaNotice").css("display", "block");
            $(".closeVisaNotice").css("display", "none");
            $("body").animate({scrollTop: $("#visaPoint").offset().top}, 300);
        }else if($(this).is(".judgeNotice")){
            $(".evaluateDetail").css({
                height: "auto",
                overflow: "visible"
            });
            $(".judgeNotice").css("display", "none");
            $(".closeJudgeNotice").css("display", "block");
        }else if($(this).is(".closeJudgeNotice")){
            $(".evaluateDetail").css({
                height: "7.25rem",
                overflow: "hidden"
            });
            $(".judgeNotice").css("display", "block");
            $(".closeJudgeNotice").css("display", "none");
            $("body").animate({scrollTop: $("#judgePoint").offset().top}, 300);
        }
    });
}

