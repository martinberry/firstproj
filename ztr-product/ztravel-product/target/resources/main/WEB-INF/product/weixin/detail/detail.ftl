<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>

<#import "content_picture.ftl" as contentPicture/>
<#import "air_info.ftl" as airInfo/>
<#import "hotel_info.ftl" as hotelInfo/>
<#import "price_explain.ftl" as priceExplain/>
<#import "booking_note.ftl" as bookingNote/>
<#import "user_evaluation.ftl" as userEvaluation/>
<#import "price_tab.ftl" as priceTab/>

<@html.htmlIndex title="真旅行-${(product.pName)!}"
		  	remoteCssFiles=["mstatic/css/product.css", "mstatic/css/swiper.min.css"]
		  	remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js", "mstatic/js/vendor/cus-list-pop.js","mstatic/js/vendor/jquery.lazyload.min.js"]
		  	localCssFiles=["product/weixin/productDetail.css"]
		  	localJsFiles=["product/weixin/productDetail.js"]>

	<div class="viewport ui-page ui-page-theme-a ui-page-active" data-role="page" data-url="/真旅行移动端/首页/产品详情.html" tabindex="0">

	    <@head.headerBar title="真旅行-${(product.pName)!}"></@head.headerBar>
	    <section class="main">
            <@contentPicture.contentPicture></@contentPicture.contentPicture>
            <@airInfo.airInfo></@airInfo.airInfo>
            <@hotelInfo.hotelInfo></@hotelInfo.hotelInfo>
            <@priceExplain.priceExplain></@priceExplain.priceExplain>
            <@bookingNote.bookingNote></@bookingNote.bookingNote>
	        <@userEvaluation.userEvaluation></@userEvaluation.userEvaluation>

            <div class="bottomFixed clearfix">
                <div class="leftPrice">
                    <div class="priceFonts">价格 <strong>¥${product.lowestPrice!}起</strong></div>
                    <div class="lineTrough">市场价 ¥${product.marketPrice!}</div>
                </div>
                <input type="hidden" id="pid" value="${product.pid!}">
                <a class="rightBtn" href="javascript:void(0);" id= "preOrder">选择出行日期</a>
            </div>
	    </section>

	<div class="ui-loader ui-corner-all ui-body-a ui-loader-default"><span class="ui-icon-loading"></span><h1>loading</h1></div>
</@html.htmlIndex>

    <!-- 联系客服弹窗 -->
    <div class="ui-content" data-role="popup" id="contactServiceWindow" data-transition="none" data-history="false" data-position-to="window" data-theme="a" data-overlay-theme="b">
        <div class="dlg-cnt pre-order-cnt">
            <p>Hi，旅行家 ^ ^。</p>
            <p>长按二维码联系旅行管家真小行，<br/>有问题随时沟通，别客气~</p>
            <img src="${host}/mstatic/images/contact_service.png" class="wxServiceImg" alt="">
        </div>
    </div>