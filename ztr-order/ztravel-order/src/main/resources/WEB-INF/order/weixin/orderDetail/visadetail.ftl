<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>

<#import "/order/weixin/orderDetail/visaadditional.ftl" as additional/>
<#import "/order/weixin/orderDetail/basic.ftl" as basic/>
<#import "/order/weixin/orderDetail/contactor.ftl" as contactor/>
<#import "/order/weixin/orderDetail/footer.ftl" as footer />
<#import "/order/weixin/orderDetail/price.ftl" as price/>
<#import "/order/weixin/orderDetail/visaprogress.ftl" as progress/>

<@html.htmlIndex title="真旅行"
		  	remoteCssFiles=["mstatic/css/orderList.css",
		  					"mstatic/css/common.css"]
		  	localJsFiles=["order/weixin/orderDetail/visadetail.js",
		  				  "order/weixin/orderDetail/visacontactor.js",
		  				  "order/weixin/orderDetail/init.js",
		  				  "common/typeahead.js",
		  				  "common/weixin/cityArea.js",
		  				  "common/weixin/validate.js",
		  				  "common/weixin/common_utils.js",
		  				  "common/ChinesePY.js",
		  				  "common/cusDateFormat.js"]
		  				                   

			  remoteJsFiles=["mstatic/js/vendor/datepicker-scroll.js",
				"mstatic/js/vendor/underscore.js",
				"mstatic/js/vendor/addresspicker-scroll.js",
				"mstatic/js/vendor/cus-list-pop.js"]>

	<!--订单列表菜单开始-->
	 <@head.headerBar title="订单详情"></@head.headerBar>
	<!--订单列表菜单结束-->

	<!-- 订单基本信息开始 -->
	<@basic.orderBasic />
    <!-- 订单基本信息结束 -->
    
	<input type="hidden" id="isDomestic" value="${isDomestic?c}"/>
	<input type="hidden" id="orderId" value="${(orderVo.orderId)!}"/>
    <div class="divide-space"></div>
	
	<div class="order-pop">
	    <!-- 订单联系人信息开始 -->
	    <div id="contactorContainer"></div>
	    <!-- 订单联系人信息结束 -->
	
	    <div class="divide-space"></div>
	</div>
    
 
    <!-- 订单附加产品信息结束 -->
    <div class="divide-space"></div>

    <!-- 订单费用说明信息开始 -->
    <@additional.orderAdditional />
    <!-- 订单费用说明信息结束 -->
    <div class="divide-space"></div>

    <!-- 订单价格明细信息开始 -->
    <@price.orderPrice /></div>
    <!-- 订单价格明细信息结束 -->
	<!-- 订单详情底部信息开始 -->
    <@footer.orderFooter />
    <!-- 订单详情底部信息结束 -->

    <!--提示模板-->
   <!--校验提示-->
	    <div class="ui-content" data-role="popup" id="validateTip" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt" id="tipMsg"></p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" style="background:transparent;" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
	        </div>
	    </div>
    <#include "addressTemplate.ftl" />
</@html.htmlIndex>