<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
<@html.htmlIndex title="行程推荐"
            remoteCssFiles=["mstatic/css/product.css", "mstatic/css/swiper.min.css", "mstatic/sass/routeRecommend.css"]
            remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js", "mstatic/js/vendor/cus-list-pop.js","mstatic/js/vendor/jquery.lazyload.min.js"]
            localCssFiles=["product/weixin/productDetail.css"]
            localJsFiles=["product/weixin/productDetail.js"]>


<div class="viewport ui-page ui-page-theme-a ui-page-active" data-role="page" data-url="/真旅行移动端/首页/产品详情.html" tabindex="0">

        <@head.headerBar title="行程推荐"></@head.headerBar>

    <section class="main">
        <div class="productBanner">
            <img src="${mediaserver}imageservice?mediaImageId=s1${(product.recommendTrips[0].desItems[0].images[0])!}">
        </div>
        <section class="commonModule noBorderTop">
            <div class="routeRecommendContent">
                <p class="wuge-txt"><#noescape><#if (product.recommendTrips[0].content)??>${(product.recommendTrips[0].content)?replace("\n","<br>")}</#if></#noescape></p>
            </div>
        </section>
        <section class="commonModule">
            <h2 class="commonTitleH2">当地Tips
                <span class="localTipsSpan">
                    <a href="javascript:;" class="live"><i class="weather"></i></a>
                    <a href="javascript:;"><i class="shopping"></i></a>
                    <a href="javascript:;"><i class="communication"></i></a>
                    <a href="javascript:;"><i class="custom"></i></a>
                    <a href="javascript:;"><i class="other"></i></a>
                </span>
            </h2>
            <div class="routeRecommendContent">
                <div class="tips-detail weatherDetail">
                    <#noescape>${(product.travelTips['WEATHER'])!}</#noescape>
                </div>
                <div class="tips-detail shoppingDetail hidden">
                    <#noescape>${(product.travelTips['EXPEND'])!}</#noescape>
                </div>
                <div class="tips-detail communicationDetail hidden">
                    <#noescape>${(product.travelTips['COMMUNICATION'])!}</#noescape>
                </div>
                <div class="tips-detail custonDetail hidden">
                    <#noescape>${(product.travelTips['CUSTOM'])!}</#noescape>
                </div>
                <div class="tips-detail otherDetail hidden">
                    <#noescape>${(product.travelTips['OTHER'])!}</#noescape>
                </div>
            </div>
        </section>

        <#list 1..(product.tripDays)! as index>
        <#assign trip = (product.recommendTrips[index])!/>
        <section class="commonModule noLRpadding <#if (index > 1)> unfoldContent </#if>">
            <h2 class="commonTitleH2 paddingL"><i class="dayBlock">D${(index)!}</i>第${(index)!}天: ${(trip.title)!}</h2>
            <div class="routeRecommendContent hasLRpadding">
                <ul class="daysArrange">
                    <li>
                        <i class="routeHotelIcon"></i>
                        <p class="liPcontent">${(trip.hotelInfo.name)!}</p>
                        <p class="liPcontent">
                        <#if (trip.hotelInfo.rate)??>
                            <#list 1..((trip.hotelInfo.rate)?substring(0, 1))?number as count>
                                <span class="yellowSmallStar"></span>
                            </#list>
                            <#if (trip.hotelInfo.rate)?length gt 1>
                                <span class="half-yellow-star"></span>
                            </#if>
                        </#if>
                        </p>
                    </li>
                    <li>
                        <i class="routeDinnerIcon"></i>
                        <p class="liPcontent">早餐: ${(trip.breakfest)!} 午餐: ${(trip.lunch)!}<br>晚餐: ${(trip.dinner)!}</p>
                    </li>
                    <li>
                        <i class="routeTimeLagIcon"></i>
                        <p class="liPcontent"><#noescape><#if trip.content??>${(trip.content)?replace("\n","<br>")}</#if></#noescape></p>
                    </li>
                    <#if trip.desItems??>
                        <#list trip.desItems as item>
                            <#list (item.images)! as img>
                            <li class="adjustPaddingL">
                                <img src="${mediaserver}imageservice?mediaImageId=s1${img!}">
                            </li>
                            </#list>
                            <li class="adjustPaddingL">
                                <p class="liPcontent"><#noescape><#if item.describe??>${(item.describe)?replace("\n","<br>")}</#if></#noescape></p>
                            </li>
                        </#list>
                    </#if>
                </ul>
            </div>
            <div class="flexBlock noLRpadding">
                <p class="unfoldBtn flexBtn" data-rel="popup" <#if (index == 1)>style="display: none;"</#if>><label class="arrow_down_icon"></label><span>展开</span></p>
                <p class="foldBtn flexBtn" data-rel="popup" <#if (index > 1)>style="display: none;"</#if>><label class="arrow_up_icon"></label><span>收起</span></p>
            </div>
        </section>
        </#list>
    </section>
    	<a data-role=none onclick="javascript:window.open(wxServer+'/product/weixin/detail/${(product.pid)!}','_self');" class="bottom-oper-bar ui-link">返回预订产品</a>
	</div>

<script>
    $(function(){
        FastClick.attach(document.body);

        $(".menu-pop").click(function(event){
            var pop = $(this).prev().find(".menu-pop-container");
            if(pop.is(":visible")){
                pop.slideUp("fast");
            }
            $(this).find(".menu-list-container").toggle();
            if($(".menu-list-container").is(":visible")){
                $(document).click(function(){
                    $(".menu-list-container").fadeOut("fast");
                });
            }
            event.stopPropagation();
        });

        //tips switchover

        $(".localTipsSpan a").each(function(index){
            $(this).click(function(){
                $(".routeRecommendContent .tips-detail").hide();
                $(".routeRecommendContent .tips-detail:eq(" + index + ")").show();
                $(".localTipsSpan a").removeClass("live");
                $(this).addClass("live");
            });
        });

        $(".flexBlock").each(function () {
           $(this).click(function(){
               $(this).parent(".commonModule").toggleClass("unfoldContent");
               if($(this).parent(".commonModule").hasClass("unfoldContent")){
                   $(this).children(".unfoldBtn").show();
                   $(this).children(".foldBtn").hide();
               }else{
                   $(this).children(".unfoldBtn").hide();
                   $(this).children(".foldBtn").show();
               }
           })
        });

    });
</script>
</@html.htmlIndex>
