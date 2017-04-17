<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>

<@html.htmlIndex title="真旅行"
		  	remoteCssFiles=["mstatic/css/homePage.css","mstatic/css/productList.css","mstatic/css/swiper.min.css"]
		  	remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js"]
		  	localJsFiles=["product/weixin/productList.js"]>

<div class="viewport ui-page ui-page-theme-a ui-page-active" data-role="page" data-url="/真旅行移动端/首页/产品列表.html" tabindex="0">

    
	<@head.headerBar title="真旅行"></@head.headerBar>
    <div style="margin-bottom: 2.9rem;" class="list-cont-block">
        <div class="prdlist-address">
            <div class="ui-grid-a">
                <div class="ui-block-a"><a class="addresslist-org add-current ui-link" href="javascript:void(0);"><span id="origin-name">${(smv.departPlace)!}</span><span class="list-down down02"></span></a></div>
                <div class="ui-block-b"><a class="addresslist-des ui-link" href="javascript:void(0);"><span id="dest-name">${(smv.defaultDestination)!}</span><span class="list-down down01"></span></a></div>
                <input id="selectedDestLevel" type="hidden" value="${(smv.destinationLevel)!'2'}" />
            </div>
            <div class="address-origin" style="display: none;">
                <ul>
                    <#if smv.departurePlaceList??>
                    	<#list smv.departurePlaceList as departurePlace>
                    		<li <#if ((smv.departPlace)!)==(departurePlace!)>class="add-current"</#if>>${departurePlace!}</li>
                    	</#list>
                    </#if>
                </ul>
            </div>
            <div class="address-destination" style="display: none;">
                <ul>
                	<#if smv.destinations??>
                         <#list smv.destinations as dest>
                         	<li>
	                         	<span>${(dest.destName)!''}</span>
                                <ul class="add-second-menu" style="display: block;">
	                                <#if (dest.subDestinations)??>
	                                <#list dest.subDestinations as secLevelDest>
	                                    <li class="sl-destName">${secLevelDest!''}</li>
	                                </#list>
	                                </#if>
                                </ul>
                         </#list>
                    </#if>

                </ul>
            </div>
        </div>
        
        <div class="banner">
            <div class="swiper-container swiper-container-horizontal">
                <div class="swiper-wrapper" style="transition-duration: 0ms; transform: translate3d(-7200px, 0px, 0px);"><a class="swiper-slide ui-link swiper-slide-duplicate" href="javascript:void(0);" data-swiper-slide-index="2" style="width: 1800px;"><img class="introduce" src="${host}/mstatic/images/banner3.png"></a>
                    <a class="swiper-slide ui-link" href="javascript:void(0);" data-swiper-slide-index="0" style="width: 1800px;"><img class="introduce" src="${host}/mstatic/images/banner1.png"></a>
                    <a class="swiper-slide ui-link" href="javascript:void(0);" data-swiper-slide-index="1" style="width: 1800px;"><img class="introduce" src="${host}/mstatic/images/banner2.png"></a>
                    <a class="swiper-slide ui-link swiper-slide-prev" href="javascript:void(0);" data-swiper-slide-index="2" style="width: 1800px;"><img class="introduce" src="${host}/mstatic/images/banner3.png"></a>
                <a class="swiper-slide ui-link swiper-slide-duplicate swiper-slide-active" href="javascript:void(0);" data-swiper-slide-index="0" style="width: 1800px;"><img class="introduce" src="${host}/mstatic/images/banner1.png"></a></div>
            	<div class="swiper-pagination"></div>
            </div>
        </div>
        
        <div class="prdlist-box">
        </div>
    </div>
      <div class="bottom-footer prd-footer">
	        <p>服务热线：400-620-6266 转6(9:00-18:00)</p>
	            <p style="text-align:center"> Copyright © 2015 真旅行 All Rights Reserved.</p>
	            <p>沪ICP备08004120号-5</p>
	    </div>
</div>

<div class="ui-loader ui-corner-all ui-body-a ui-loader-default"><span class="ui-icon-loading"></span><h1>loading</h1></div>
</@html.htmlIndex>