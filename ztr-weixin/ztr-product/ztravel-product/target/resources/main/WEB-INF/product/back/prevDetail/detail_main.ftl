<#import "/common/front/userHtmlBase2.ftl" as html />
<#import "/product/back/prevDetail/content_picture.ftl" as mainContent/>
<#import "/product/back/prevDetail/price_calendar.ftl" as priceCalendar/>
<#import "/product/back/prevDetail/air_info.ftl" as airInfo/>
<#import "/product/back/prevDetail/hotel_info.ftl" as hotelInfo/>
<#import "/product/back/prevDetail/price_explain.ftl" as priceExplain/>
<#import "/product/back/prevDetail/journey_recommend.ftl" as journeyRecommend/>
<#import "/product/back/prevDetail/booking_note.ftl" as bookingNote/>
<#import "/product/back/prevDetail/user_evaluation.ftl" as userEvaluation/>

<@html.htmlIndex remoteJsFiles=["js/base/jquery-1.11.2.min.js", "js/bootdist/bootstrap.min.js", "js/vendor/bootstrap-datepicker.min.js",
								"js/vendor/bootstrap-datepicker.zh-CN.min.js",
								"js/global/global.js",
								"js/client/common.js", "js/vendor/underscore.js","js/client/workplatform.js"]
								  localCssFiles=["member/front/round_image.css"]
                                  remoteCssFiles=["css/bootstrap.min.css", "css/client/bootstrap.reset.css",
                                  "css/bootstrap-datepicker.min.css", "css/common.css", "css/client/common.css",
                                  "css/client/flight_logo.css", "css/client/productInfo.css","css/client/workplatform.css"]
                                  localJsFiles=["common/jquery.qrcode.min.js"] title="产品详情预览">


	<header>
		<div class="head clearfix">
            <div class="logo pull-left"></div>
            <div class="proHeadNavBlock pull-right">
                <a href="javascript:void(0);" title="上一个" class="commonIcon headLeftArrowIcon"></a>
                <a href="javascript:void(0);" title="返回首页" class="commonIcon headHomeIcon"></a>
                <a href="javascript:void(0);" title="返回列表" class="commonIcon headListIcon"></a>
                <a href="javascript:void(0);" title="下一个" class="commonIcon headRightArrowIcon"></a>
            </div>
		</div>
	</header>

	<div class="mainContent">

      <@mainContent.mainContent></@mainContent.mainContent>

        <section class="mainWrapper">

            <@priceCalendar.priceCalendar></@priceCalendar.priceCalendar>

            <div class="productFixedContent">
                <div id="navbar" class="fixedList-container">
                    <ul class="nav fixedList clearfix">
                        <li class="active">
                            <a href="#airInfo">航班信息</a>
                        </li>
                        <li>
                            <a href="#hotelInfo">酒店信息</a>
                        </li>
                        <li>
                            <a href="#priceExplain">费用说明</a>
                        </li>
                        <li>
                            <a href="#journeyRecommend">行程推荐</a>
                        </li>
                        <li>
                            <a href="#bookingNote">预定须知</a>
                        </li>
                        <li>
                            <a href="#userEvaluation">用户评价</a>
                        </li>
                        <li class="last">
                            <div class="fixed-order-btn">立即预订</div>
                            <div class="fixed-want-order-btn" style="display:none">想去</div>
                        </li>
                    </ul>
                </div>

                <@airInfo.airInfo></@airInfo.airInfo>
                <@hotelInfo.hotelInfo></@hotelInfo.hotelInfo>
                <@priceExplain.priceExplain></@priceExplain.priceExplain>
                <@journeyRecommend.journeyRecommend></@journeyRecommend.journeyRecommend>
                <@bookingNote.bookingNote></@bookingNote.bookingNote>
                <@userEvaluation.userEvaluation></@userEvaluation.userEvaluation>

            </div>
        </section>
        <#include "/common/front/header/footer.ftl"/>
        <div class="qrCodeRealBlock" style="display:none;"></div>
	</div>
</@html.htmlIndex>
<script type="text/javascript">
    $(function(){
    	/*为body附加滚动事件标记*/
    	$('body').attr('data-spy', 'scroll');
    	$('body').attr('data-target', '#navbar');
    	$('body').attr('data-offset', '75');
    	$(".top-nav-list").slideNav({
        	fx: "swing",
        	speed: 300,
        	changeTextColor: "#fff"
		});
        $(".fixedList").affix({
            offset: {
                top: function(){
                    return this.top = $(".fixedList").offset().top;
                }
            }
        });

        $(".asideFixed").affix({
            offset: {
                top: $(".asideFixed").offset().top - 70,
                bottom: $("body").height() - $(".journeyRecommendModel").offset().top -
                            $(".journeyRecommendModel").outerHeight(true) + 80
            }
        });

        /*滚动监听有蒙版效果*/
        $(window).scroll(function(){
            $(".journeyRecommendList").children("li").each(function(index){
                showLi(this);
                if ($(this).hasClass("active")) {
                    $(".asideFixed > li").removeClass("active");
                    $(".asideFixed > li").eq(index).addClass("active");
                }
            });
            //  概览位置 取消变白效果
           // $(".journeyRecommendList > li:eq(0)").addClass("active");
        });

		//$(".journeyRecommendList > li").addClass("active");

        function showLi(ele) {
            var top = $(ele).offset().top,
                eleHeight = $(ele).height(),
                scrollTop = $(window).scrollTop(),
                clientHeight = document.documentElement.clientHeight,
                nextEleTop;

            if ($(ele).next("li").length == 0) {
                nextEleTop = top + eleHeight;
            } else {
                nextEleTop = $(ele).next("li").offset().top;
            }
            if (scrollTop > (top - clientHeight*0.5) && scrollTop < (nextEleTop - clientHeight*0.5)) {
                $(ele).addClass("active");
                $(ele).siblings("li").removeClass("active");
            }
        }

        /**酒店图片mouseover效果*/
        $(".hotelSmallImg li").mouseover(function(){
            var changeSrc = $(this).parents(".hotelSmallImg").siblings(".hotelBigImg").find("img");
            var thisSrc = $(this).find("img").attr("src");
            changeSrc.attr("src",thisSrc);
            $(this).addClass("active");
            $(this).siblings("li").removeClass("active");
        });

		//二维码生成
	    create_qrcode();
	    $(".qrCode-icon").attr("title", $("div.qrCodeRealBlock").html());
	    $(".qrCode-tooltip").tooltip({
	        html: true,
	        placement: 'bottom',
	        template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
	    });

		/*当地Tips tab*/
        $(".tripTipsIcon").each(function(index){
            $(".tripTipsIcon").removeClass("current");
            $(".tripTipsIcon").eq(0).addClass("current");
            $(".tipsContentBlock > div").hide();
            $(".tipsContentBlock > div").eq(0).show();

            $(this).mouseover(function(){
                $(".tripTipsIcon").removeClass("current");
                $(this).addClass("current");
                $(".tipsContentBlock > div").hide();
                $(".tipsContentBlock > div").eq(index).show();
            });
        });

        //亮点配色初始化
		var lightColor = $('#lightColor_hidden').val();
		if(lightColor)setBannerBgColor(lightColor);

    });
    //生成二维码跳转到微信共享产品详情页面
	function create_qrcode() {
		var sharePath = window.location.href;
		$("div.qrCodeRealBlock").empty();
		$("div.qrCodeRealBlock").qrcode({
			render : "table",
			maxVersion:40,
			minVersion:3,
			size: 180,
			width : 170,
			height : 170,
			fill: '#000',
			background : "#fff",
			foreground : "#f00",
			quiet: 1,
			text : sharePath,
		});
	}
	//设置亮点Banner配色
	function setBannerBgColor(color) {
	    $(".bannerBottomList .middleListContent").css({
	        "background-color": "#" + color
	    });
	    $(".productDetailModel").css({
	        "border-bottom-color": "#" + color
	    });
	}

    //默认收起三个以后的
    var hotelInfoModelCount = 0;
    $(".hotelInfoModel").each(function(index){
    	hotelInfoModelCount++;
        if (index > 2) {
            $(this).hide();
        }
    });
	if(hotelInfoModelCount <4){
    	$(".expand-btn").parent().hide();
    }else{
	    // 展开/收起更多酒店
	    $(".expand-btn").click(function(){
	        if ($(this).hasClass("collapse")) {
	            $(this).removeClass("collapse");
	            $(this).find("em").html("展开更多酒店信息");
	            $(".hotelInfoModel").each(function(index){
	                if (index > 2) {
	                    $(this).hide();
	                }
	            });
	        } else {
	            $(this).addClass("collapse");
	            $(this).find("em").html("收起酒店信息");

	            $(".hotelInfoModel").each(function(){
	                if (!$(this).is(":visible")) {
	                    $(this).show();
	                }
	            });
	        }
	    });
    }


</script>