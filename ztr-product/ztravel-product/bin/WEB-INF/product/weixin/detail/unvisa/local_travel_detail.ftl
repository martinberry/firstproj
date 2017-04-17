<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
<#import "local_content_main.ftl" as contentMain/>
<#import "local_product_introduction.ftl" as productIntroduction/>
<#import "local_use_introduction.ftl" as useIntroduction/>
<#import "local_attention.ftl" as attention/>
<#import "local_price_explain.ftl" as priceExplain/>
<#import "local_use_evaluation.ftl" as userEvaluation/>

<@html.htmlIndex title="真旅行-${(product.pName)!}"
		  	remoteCssFiles=["mstatic/css/product.css", "mstatic/css/swiper.min.css"]
		  	remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js","mstatic/js/vendor/jquery.lazyload.js"]
		  	localJsFiles=["product/weixin/detail/unvisa/local_travel_detail.js"]>

	<div class="viewport ui-page ui-page-theme-a ui-page-active" data-role="page" data-url="/真旅行移动端/首页/当地游产品详情.html" tabindex="0">
	   <@head.headerBar title="真旅行-${(product.pName)!}"></@head.headerBar>
	    <section class="main">
            <@contentMain.contentMain></@contentMain.contentMain>
            <!-- 产品介绍 -->
            <@productIntroduction.productIntroduction></@productIntroduction.productIntroduction>
            <!-- 使用说明 -->
            <@useIntroduction.useIntroduction></@useIntroduction.useIntroduction>
            <!-- 预定须知 -->
            <@attention.attention></@attention.attention>
            <!-- 费用说明 -->
            <@priceExplain.priceExplain></@priceExplain.priceExplain>
            <div id="orderPoint"></div>
            <!-- 用户评价 -->
            <@userEvaluation.userEvaluation></@userEvaluation.userEvaluation>
            <div class="bottomFixed clearfix">
            <div class="leftPrice">
                <div class="priceFonts">价格 <strong>¥${product.lowestPrice!}起</strong></div>
            </div>
                <input type="hidden" id="pid" value="${product.pid!''}">
                <a class="rightBtn" href="javascript:void(0);" id= "preOrder">下一步</a>
            </div>
	    </section>

	<div class="ui-loader ui-corner-all ui-body-a ui-loader-default"><span class="ui-icon-loading"></span><h1>loading</h1></div>
</@html.htmlIndex>
