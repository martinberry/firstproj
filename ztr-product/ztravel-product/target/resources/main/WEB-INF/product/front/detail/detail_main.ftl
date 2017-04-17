<#import "/common/front/userHtmlBase.ftl" as html />
<#import "/product/front/detail/content_picture.ftl" as mainContent/>
<#import "/product/front/detail/price_calendar.ftl" as priceCalendar/>
<#import "/product/front/detail/air_info.ftl" as airInfo/>
<#import "/product/front/detail/hotel_info.ftl" as hotelInfo/>
<#import "/product/front/detail/price_explain.ftl" as priceExplain/>
<#import "/product/front/detail/journey_recommend.ftl" as journeyRecommend/>
<#import "/product/front/detail/booking_note.ftl" as bookingNote/>
<#import "/product/front/detail/user_evaluation.ftl" as userEvaluation/>


<@html.htmlIndex remoteJsFiles=["js/base/jquery-1.11.2.min.js", "js/bootdist/bootstrap.min.js", "js/vendor/bootstrap-datepicker.min.js",
								"js/vendor/bootstrap-datepicker.zh-CN.min.js", "js/global/global.js",
								"js/client/common.js", "js/vendor/underscore.js","js/client/workplatform.js","js/vendor/jquery.lazyload.min.js"]
								  localCssFiles=["member/front/round_image.css"]
                                  remoteCssFiles=["css/bootstrap.min.css", "css/client/bootstrap.reset.css", "css/bootstrap-datepicker.min.css", "css/common.css",
                                  "css/client/common.css", "css/client/flight_logo.css", "css/client/productInfo.css","css/client/workplatform.css"]
                                  localJsFiles=["product/front/productDetail.js","member/front/login.js","common/jquery.qrcode.min.js"]
                                  title="${title!((product.pName)!)}" keywords="${keywords!}" description="${description!}">

<#include "/common/front/work_platform.ftl" />

	<header>
		<div class="head clearfix">
		    <a href="${memberServer}/home">
	  	        <div class="logo pull-left"></div>
	        </a>
            <div class="proHeadNavBlock pull-right">
                <a <#if prePid??>href="${basepath}/product/front/detail/${prePid}"<#else>href="javascript:void(0);"</#if> title="上一个" class="commonIcon headLeftArrowIcon"></a>
                <a href="${memberServer}/home" title="返回首页" class="commonIcon headHomeIcon"></a>
                <a href="${memberServer}/product/list" title="返回列表" class="commonIcon headListIcon"></a>
                <a <#if posPid??>href="${basepath}/product/front/detail/${posPid}"<#else>href="javascript:void(0);"</#if> title="下一个" class="commonIcon headRightArrowIcon"></a>
            </div>
		</div>
	</header>

	<div class="mainContent">

      <@mainContent.mainContent></@mainContent.mainContent>

        <section class="mainWrapper">

            <@priceCalendar.priceCalendar></@priceCalendar.priceCalendar>

            <div class="productFixedContent">
            	<input type="hidden" id="nature" value=${(product.nature)!''}>
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
        <#include "/common/front/right_side.ftl"/>
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

    });

</script>
