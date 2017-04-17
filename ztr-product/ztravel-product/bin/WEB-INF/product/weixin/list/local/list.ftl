<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar1.ftl" as head/>
<@html.htmlIndex title="当地游首页"
		  	remoteCssFiles=["mstatic/css/homePage.css","mstatic/css/productList.css","mstatic/css/swiper.min.css"]
		  	remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js"]
		  	localJsFiles=["product/weixin/list/localList.js"]>
<div class="viewport" data-role="page">
    <@head.headerBar title="当地游首页" menuType="LOCAL"></@head.headerBar>
    <div class="localtrip-wrap">
        <ul class="localtrip-list clearfix">
        </ul>
    </div>
    <div class="navLayout"></div>
    <div class="navlist-box">
        <div class="ui-grid-a">
            <div class="ui-block-a">
                <a class="address-list address-org" href="javascript:void(0);">
                    <span id="origin-name">${(smv.departPlace)!}</span><span class="listItem"></span>
                </a>
            </div>
            <div class="ui-block-b">
                <a class="address-list address-des" href="javascript:void(0);">
                    <span id="dest-name">${(smv.defaultDestination)!}</span><span class="listItem"></span>
                </a>
                <input id="selectedDestLevel" type="hidden" value="${(smv.destinationLevel)!'2'}" />
            </div>
        </div>
    </div>
    
    <div class="navlist-wrap">
        <div class="address-start">
            <div class="address-start">
            <ul>
                <li class="add-current">上海</li>
            </ul>
        </div>
        </div>
        <div class="address-stop clearfix">
            <ul class="stop-first-menu">
                <#if smv.destinations??>
	                <#list smv.destinations as dest>
		                <li <#if dest_index == 0 >class="stop-current"</#if>>
		                    <span>${(dest.destName)!''}</span>
		                </li>
	                </#list>
	             </#if>
            </ul>
            <div class="stop-second-menu">
            	<#if smv.destinations??>
                	<#list smv.destinations as dest>
		                <ul <#if dest_index == 0 >style="display: block;"<#else>style="display: none;"</#if>>
		                	<#if (dest.subDestinations)??>
                                <#list dest.subDestinations as secLevelDest>
		                   			 <li>${secLevelDest!}</li>
                   			 	</#list>
                   			 </#if>
		                </ul>
		                </#list>
                </#if>
            </div>
        </div>
    </div>
  
</div>
<script>
    $(function(){
        FastClick.attach(document.body);
        var h1 = document.body.offsetHeight;
        var h2 = document.body.scrollHeight;
        if(h1 == h2){
            $(".homeHeader").css({
                "background-color":'rgba(70,209,190,0)'
            });
        }

        $(window).scroll(function(){
            var allHeight = $(".localtrip-banner").outerHeight() - $(".homeHeader").outerHeight();
            var theHeight = $("body").scrollTop();
            var rate = theHeight/allHeight;
            $(".homeHeader").css({
                "background-color":'rgba(70,209,190, '+rate+')'
            });
        });

        var mySwiper = new Swiper('.swiper-container', {
            autoplay:1500,//可选选项，自动滑动
            autoplayDisableOnInteraction : false, //用户操作后不会停止自动切换
            speed:1000,
            loop : true,
            pagination: '.swiper-pagination'
        });

        $(".prdlist-tips").each(function(){
            var til = $(this).text().trim();
            var subtil = '';
            var maxwidth = 15;
            if (til.length > maxwidth) {
                subtil = til.substring(0, maxwidth);
                $(this).html(subtil + '...');
            }
        });

        $(".address-list").click(function(){
            var start = $(".address-start");
            var stop = $(".address-stop");

            $(".navLayout").show();
            if($(this).is(".address-org")){
                $(".address-des").removeClass("listCurrent");
                showLayout(start, stop);
            }else{
                showLayout(stop, start);
                $(".address-org").removeClass("listCurrent");
                $(".address-stop .stop-current").children(".stop-second-menu").show();
            }
            $(this).toggleClass("listCurrent");
        });

        $(".address-start ul li").click(function(){
            $(this).siblings().removeClass("add-current");
            $(this).addClass("add-current");
            $("#origin-name").text($(this).text());
            $(".address-start").slideUp();
            $(".address-org").removeClass("listCurrent");
            $(".navLayout").hide();
            submit();
        });

        function showLayout(showBox, hideBox){
            showBox.slideToggle(function(){
                if($(this).is(":hidden")){
                    $(".navLayout").hide();
                }
            });
            hideBox.hide();
        }

        $(".stop-first-menu > li").click(function(){
            var index = $(this).index();
            $(this).addClass("stop-current").siblings().removeClass("stop-current");
            $(".stop-second-menu > ul").eq(index).show().siblings().hide();

        });
        $(".stop-second-menu ul li").click(function(){
            $(this).addClass("stop-current").siblings().removeClass("stop-current");
            $("#dest-name").text($(this).text());
            $(".address-des").removeClass("listCurrent");
            $(".address-stop").slideUp("fast");
            $(".navLayout").hide();
            submit();
            return false;
        });

    })
</script>
</@html.htmlIndex>