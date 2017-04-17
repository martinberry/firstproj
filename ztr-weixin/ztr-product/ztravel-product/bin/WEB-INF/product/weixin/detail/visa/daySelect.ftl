<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
<@html.htmlIndex title="签证产品详情"
		  	remoteCssFiles=["mstatic/css/book.css", "mstatic/css/swiper.min.css"]
		  	remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js", "mstatic/js/base/fastclick.js","mstatic/js/vendor/jquery.lazyload.min.js","mstatic/js/base/jquery-ui.min.js","mstatic/js/vendor/underscore-min.js","mstatic/js/vendor/hammer.min.js"]
		  	localCssFiles=[]
		  	localJsFiles=["product/weixin/detail/visa/daySelect.js","common/caculate.js"]>
    <div class="viewport" data-role="page">
        <@head.headerBar title="选择日期和出行人数"></@head.headerBar>
        <section class="main-container">
            <div class="orderCalendar" id="orderDateBox">
                <a class="priceNot" href="#inst-dialog" data-rel="popup" data-role="none" class="price-tip"><i class="qusIcon"></i> 价格说明</a>
            </div>
            <input type="hidden" id="pid" value="${(product.pid)!''}">
            <input id="prodId" type="hidden" value="${(product.proId)!''}">
            <input id="nature" type="hidden" value="${(product.natureType)!''}">
            <!--  出行人数及套餐  -->
            <#if (product.priceInfos)?? && (product.priceInfos)?size gt 0>
	            <div class="trip-num-wrapper">
	                <div class="head">
	                    <div class="title">价格类型</div>
						<#assign prices = product.priceInfos>
		                    <div class="menu-select">
		                    	<#list prices as price>
		                        	<a class="priceType-link <#if price_index == 0>active</#if>" cosid="${(price.id)!}" href="javascript:;">${(price.name)!}</a>
		                        </#list>
		                    </div>
	                </div>
	                <div class="trip-num-block">
	                    <div class="passenger-type adult clearfix">
	                        <div class="fl">
	                            <span>成人</span><Br>
	                            <span class="price">¥<span id="adultPrice">${(price.adultPrice)!''}</span></span>
	                        </div>
	                        <div class="fr">
	                            <div class="plus-minus-comp clearfix">
	                                <span class="minus-mark">-</span>
	                                <span class="result">2</span>
	                                <span class="plus-mark">+</span>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="passenger-type child clearfix" id="childDiv">
	                        <div class="fl">
	                            <span>儿童<i>（2-12周岁）</i></span><Br>
	                            <span class="price">¥<span id="childPrice">${(price.childPrice)!''}</span></span>
	                        </div>
	                        <div class="fr">
	                            <div class="plus-minus-comp clearfix">
	                                <span class="minus-mark disabled">-</span>
	                                <span class="result">0</span>
	                                <span class="plus-mark">+</span>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                </#if>
            </div>
            <!--  下一步  -->
            <div class="total-price-block clearfix">
                <span class="total-price fl">
                    总价<em>¥<span id="total">--</span></em>
                </span>
                <span class="next fl">下一步</span>
            </div>
        </section>
    </div>
	
	<!--错误提示-->
    <div class="ui-content" data-role="popup" id="alert-errorhint-dialog" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
        <p class="dlg-cnt" id="errorHintMsg"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" >好的</a>
        </div>
    </div>
    <!--价格说明弹窗-->
    <div class="inst-dialog" id="inst-dialog" data-role="popup" data-position-to="window" data-theme="a" data-overlay-theme="b" data-history="false">
        <div class="inst-dialog-content">
            <p>1、本起价是所有产品价格类型中的最低售价。</p>
            <p>2、您最终提交的价格会因所选的产品类型不同而有所差别。</p>
        </div>
        <div class="inst-dialog-foot">
            <a class="btn-com btn-close" data-role="none" href="javascript:void(0);" data-rel="back">关 闭</a>
        </div>
    </div>
</@html.htmlIndex>